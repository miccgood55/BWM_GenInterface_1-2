package com.wmsl.duplicate.core;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

//import com.wealth.bwm.batch.impl.entity.cp.account.PortHoldingBySubAccountBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.execution.ExecutionBatch;
import com.wealth.bwm.batch.impl.entity.crm.aum.PortHoldingBySubAccountBatch;
import com.wealth.bwm.common.impl.daomini.crm.aum.PortHoldingBySubAccountDaomini;
import com.wealth.exception.dao.InfoEntityServiceException;
import com.wealth.exception.dao.ServerEntityServiceException;
import com.wmsl.Constants;
import com.wmsl.bean.GenResult;
import com.wmsl.core.Core;
import com.wmsl.dao.impl.PortHoldingDao;
import com.wmsl.dao.impl.PortHoldingDao;
import com.wmsl.utils.GenDataDBUtils;

import java.io.BufferedWriter;

@Component
public class GenPortHoldingDataCore extends Core implements InitializingBean{

	private static final Logger log = LoggerFactory.getLogger(GenPortHoldingDataCore.class);

	protected final static String COMMA_STRING = ",";
	protected final static String DOUBLE_C = "\"";
	private int startYear;
	private int startMonth;
	private int startDay;

	private int stopYear;
	private int stopMonth;
	private int stopDay;
	private static int PORTHOLDING_NEXT_ID;

	private Map<String, BufferedWriter> bufferedWriterPortHoldingMap = new HashMap<String, BufferedWriter>();
//	private Map<String, BufferedWriter> bufferedWriterPortHoldingMap = new HashMap<String, BufferedWriter>();;
	private Map<Date, String> bufferedWriterDateMap = new HashMap<Date, String>();;

	private PortHoldingDao portHoldingDao;
	public void setPortHoldingDao(PortHoldingDao portHoldingDao){
		this.portHoldingDao = portHoldingDao;
	}

	public String getDir(String dir) {
		return Constants.DIR_BIG_PORTHOLDING + dir;
	}

	public String getFilenamePortHolding() {
		return Constants.FILE_NAME_PORTHOLDING + getStartDate().get(Calendar.YEAR);
	}

	public int getNextportHoldingId() {
		return PORTHOLDING_NEXT_ID++;
	}

	public void init() {}

	@Override
	public GenResult execute() throws ServerEntityServiceException, InfoEntityServiceException, IOException {

		log.debug("Start " + this.getClass().getName());

		long t1 = System.currentTimeMillis();

		GenResult genResult = this.executeDetail();

		long t2 = System.currentTimeMillis();

		genResult.setTime((t2 - t1) / 1000);

		return genResult;
	}

	public String getCurrentYearMonth(Calendar calendar) {
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;

		return year + "_" + month;
	}

	public GenResult executeDetail() throws ServerEntityServiceException, InfoEntityServiceException, IOException {

		this.init();

		Calendar startDate = this.getStartDate();
		Calendar stopDate = this.getStopDate();

		GenResult genResult = new GenResult();
		genResult.setYear(startDate);

		Date startDateD = startDate.getTime();
//		Date stopDateD = stopDate.getTime();
		log.debug("Start Date : " + startDateD);

		try {

			List<PortHoldingBySubAccountBatch> portHoldingList = portHoldingDao.getObjectList();

			for (Calendar snapShotAccountDate = (Calendar) startDate.clone(); 
					snapShotAccountDate.before(stopDate) || snapShotAccountDate.equals(stopDate); 
					snapShotAccountDate.add(Calendar.DATE, 1)) {
				
				String dateFormat = getDateFromMap(bufferedWriterDateMap, snapShotAccountDate);

				String snapShotAccountYearMonth = getCurrentYearMonth(snapShotAccountDate);
				
				for (PortHoldingBySubAccountBatch portHoldingInfo : portHoldingList) {

					try {

						PortHoldingBySubAccountBatch snapShotAccount = portHoldingInfo;

						BufferedWriter bufferedWriterPortHolding = getBufferedWriterFromMap(bufferedWriterPortHoldingMap, snapShotAccountYearMonth,
								getDir(Constants.DIR_POS), getFilenamePortHolding(), Constants.FILENAME_BIG_EXT);

						setPortHoldingValue(snapShotAccount, dateFormat);

						snapShotAccountToString(bufferedWriterPortHolding, snapShotAccount);

						genResult.addAccountCount();

					} catch (UnsupportedOperationException e) {
						break;
					}

				}
				// Do your job here with `date`.
				System.out.println(dateFormat);
			}
		} catch (

		IOException e) {
			throw new IOException();
		} finally {

//			for (Entry<String, BufferedWriter> entry : bufferedWriterPortHoldingMap.entrySet()) {
//
//				BufferedWriter bufferedWriterOutStanding = entry.getValue();
//				bufferedWriterOutStanding.flush();
//				bufferedWriterOutStanding.close();
//			}

			for (Entry<String, BufferedWriter> entry : bufferedWriterPortHoldingMap.entrySet()) {

				BufferedWriter bufferedWriterPosition = entry.getValue();
				bufferedWriterPosition.flush();
				bufferedWriterPosition.close();
			}

		}

		return genResult;
		// return executeFormDataInDB();
	}


	@Override
    public void afterPropertiesSet() throws Exception {
		log.debug(" === Post GenPortHoldingDataCore === ");

		if(PORTHOLDING_NEXT_ID == 0){
			Integer snapShotAccountNextId = coreDao.getNextPortHoldingId();
			if(snapShotAccountNextId != null){
				PORTHOLDING_NEXT_ID = snapShotAccountNextId.intValue();
				log.debug("PortHolding nextId : " + PORTHOLDING_NEXT_ID);
			}
		}

		
	}

	public BufferedWriter getBufferedWriterFromMap(Map<String, BufferedWriter> bufferedWriterMap, String key, String path, String filename,
			String ext) throws IOException {

//		this.getStartYear();

		if (bufferedWriterMap.containsKey(key)) {
			return bufferedWriterMap.get(key);
		} else {

			BufferedWriter bufferedWriter = genFilesUtils.getBufferedWriter(path, filename + key, ext);

			bufferedWriterMap.put(key, bufferedWriter);

			return bufferedWriterMap.get(key);
		}
	}

	public String getDateFromMap(Map<Date, String> bufferedWriterMap, Calendar calendar) throws IOException {

//		this.getStartYear();

		Date date = calendar.getTime();
		if (bufferedWriterMap.containsKey(date)) {
			return bufferedWriterMap.get(date);
		} else {
			String dateFormat = SDF.format(date);
			// BufferedWriter bufferedWriter =
			// genFilesUtils.getBufferedWriter(path, filename + key, ext);

			bufferedWriterMap.put(date, dateFormat);

			return bufferedWriterMap.get(date);
		}
	}

	public void snapShotAccountToString(BufferedWriter bufferedWriter, PortHoldingBySubAccountBatch snapShotAccount) throws IOException {

		bufferedWriter.write(prepareData(snapShotAccount.getPortHoldingId()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getSubAccountId()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getSubAccountNo()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getAccountId()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getAccountName()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getInstrumentId()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getSymbol()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getSecurityTypeId()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getSecurityTypeCode()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getProductTypeId()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getProductTypeCode()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getProductTypeGroupId()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getProductTypeGroupCode()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getProductClassId()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getProductClassCode()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getBalanceSheetGroupId()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getBalanceSheetGroupCode()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getBranchId()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getBranchCode()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getCurrencyId()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getCurrencyCode()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getCustomerId()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getUnrealizedGL()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getPortfolioId()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getPortfolioCode()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getAumDate()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getLocalMarketValue()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getLocalAccruedInterest()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getMarketValue()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getRemark()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getCreateDate()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getCreateTime()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getCreateBy()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getCreateByName()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getLastUpdateDate()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getLastUpdateTime()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getLastUpdateBy()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getLastUpdateByName()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getActualRate()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getBranchNameEn()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getBranchNameTh()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getProductClassNameEn()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getProductClassNameTh()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getProductTypeRiskLevel()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getProductClassNameEn()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getProductClassNameTh()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getProductClassRiskLevel()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getIns_issueDate()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getIns_matureDate()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getSubAcc_issueDate()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getSubAcc_matureDate()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getInternalTypeCode()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getUnit()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getCostPerUnit()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getMarketPerUnit()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getLocalCostValue()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getMarketYield()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getInvestment_Property()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getMtmDate()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getAccruedInterest()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getOutstandingId()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getPortfolioNameEn()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getPortfolioNameTh()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getAccountType()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getIsActive()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getCouponRate()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getLocalUnrealizedGL()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getHoldAllFundFlag()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getHoldAmount()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getCostValue()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getAnnounceRate()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getMarketExchangeRate()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getCreditLimit()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getCompanyCode()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getFaceValue()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getLocalPremium()));
		
		bufferedWriter.newLine();

	}

	public void setPortHoldingValue(PortHoldingBySubAccountBatch portHolding, String dateFormat) {

		portHolding.setPortHoldingId(getNextportHoldingId());
		portHolding.setAumDate(dateFormat);
		portHolding.setIns_issueDate(dateFormat);
		portHolding.setIns_matureDate(dateFormat);
		portHolding.setSubAcc_issueDate(dateFormat);
		portHolding.setSubAcc_matureDate(dateFormat);
		portHolding.setMtmDate(dateFormat);
		
		portHolding.setCreateDate(dateFormat);
		portHolding.setCreateTime("00:00:00");
		portHolding.setCreateBy(1);
		portHolding.setCreateByName("BWMADMIN");
		portHolding.setLastUpdateDate(dateFormat);
		portHolding.setLastUpdateTime("00:00:00");
		portHolding.setLastUpdateBy(1);
		portHolding.setLastUpdateByName("BWMADMIN");
	}
	
//	public void setStartYear(int startYear) {
//		this.startYear = startYear;
//	}
	public void setStartYear(String startYear) {
		String[] startYears = startYear.split("\\|");
		
		log.debug("Process Stop Year");
		for (int i = 0; i < startYears.length; i++) {
			String startYearStr = startYears[i];
			log.debug(startYearStr);
			this.startYear = Integer.valueOf(startYearStr);
			break;
//			this.stopYear.add(Integer.valueOf(stopYearStr));
		}
	}
	public void setStartMonth(int startMonth) {
		this.startMonth = startMonth;
	}

	public void setStartDay(int startDay) {
		this.startDay = startDay;
	}

//	public void setStopYear(int stopYear) {
//		this.stopYear = stopYear;
//	}
	public void setStopYear(String stopYear) {
		String[] stopYears = stopYear.split("\\|");
		
		log.debug("Process Stop Year");
		for (int i = 0; i < stopYears.length; i++) {
			String stopYearStr = stopYears[i];
			log.debug(stopYearStr);
			this.stopYear = Integer.valueOf(stopYearStr);
			break;
//			this.stopYear.add(Integer.valueOf(stopYearStr));
		}
	}
	public void setStopMonth(int stopMonth) {
		this.stopMonth = stopMonth;
	}

	public void setStopDay(int stopDay) {
		this.stopDay = stopDay;
	}

	public Calendar getStartDate() {
		return GenDataDBUtils.getCalendar(startYear, startMonth, startDay);
	}

	public Calendar getStopDate() {
		return GenDataDBUtils.getCalendar(stopYear, stopMonth, stopDay);
	}

	protected String prepareData(String val) {
		if (val == null) {
			return "";
		}
		return new StringBuilder(DOUBLE_C).append(val).append(DOUBLE_C).toString();
	}

	protected String prepareData(BigDecimal val) {
		if (val == null) {
			return "";
		}
		return val.toString();
	}

	protected String prepareData(Integer val) {
		if (val == null) {
			return "";
		}
		return val.toString();
	}

	protected String prepareData(Character val) {
		if (val == null) {
			return "";
		}
		return val.toString();
	}

//	private String prepareData(AccountBatch account) {
//		if (account == null) {
//			return "";
//		}
//		return account.getAccountId().toString();
//	}

}
