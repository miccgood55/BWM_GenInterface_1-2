package com.wmsl.outstanding.core;

import java.io.IOException;
import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

//import com.wealth.bwm.batch.impl.entity.cp.account.AccountBatch;
//import com.wealth.bwm.batch.impl.entity.cp.account.SubAccountBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.outstanding.OutstandingBatch;
//import com.wealth.bwm.impl.entity.cp.account.outstanding.Outstanding;
import com.wealth.exception.dao.InfoEntityServiceException;
import com.wealth.exception.dao.ServerEntityServiceException;
import com.wmsl.Constants;
import com.wmsl.bean.GenResult;
//import com.wmsl.bean.dao.CustomerInfo;
import com.wmsl.core.Core;
//import com.wmsl.core.GenBigDataCore;
import com.wmsl.utils.GenDataDBUtils;

import java.io.BufferedWriter;

@Component
public abstract class GenOutStandingDataCore extends Core implements InitializingBean{

	private static final Logger log = LoggerFactory.getLogger(GenOutStandingDataCore.class);

	protected final static String COMMA_STRING = ",";
	protected final static String DOUBLE_C = "\"";
	private int startYear;
	private int startMonth;
	private int startDay;

	private int stopYear;
	private int stopMonth;
	private int stopDay;
	private static int OUTSTANDING_NEXT_ID;

	private Map<String, BufferedWriter> bufferedWriterPositionMap = new HashMap<String, BufferedWriter>();
	private Map<String, BufferedWriter> bufferedWriterOutStandingMap = new HashMap<String, BufferedWriter>();;
	private Map<Date, String> bufferedWriterDateMap = new HashMap<Date, String>();;

//	abstract protected OutstandingBatch getOutstanding();

	abstract protected List<OutstandingBatch> getOutstandingList() throws InfoEntityServiceException, ServerEntityServiceException;
	abstract public String getDir(String dir);
	abstract public String getFilenameAcc();
	abstract public String getFilenameSubAcc();
	abstract public String getFilenamePos();
	abstract public String getFilenameTx();
	abstract public String getFilenameExecution();
	abstract public String getFilenameOutstanding();
	abstract public String getFilenameAccount();
	abstract public String getFilenameSubAccount();

	abstract public void subOutstandingToString(BufferedWriter bufferedWriter, OutstandingBatch outstanding) throws IOException;
	
	public int getNextOutstandingId() {
		return OUTSTANDING_NEXT_ID++;
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

//			long accountSeq = 0;
//			long seq = 100000000;

			List<OutstandingBatch> outstandingList = this.getOutstandingList();

//			String startDateFormat = SDF.format(startDateD);

			for (Calendar outStandingDate = (Calendar) startDate.clone(); outStandingDate.before(stopDate); 
					outStandingDate.add(Calendar.DATE, 1)) {

				for (OutstandingBatch outstandingInfo : outstandingList) {

					try {

						OutstandingBatch outstanding = outstandingInfo;
								
						String dateFormat = getDateFromMap(bufferedWriterDateMap, outStandingDate);// sdf.format(outStandingDate.getTime());

						String outstandingYearMonth = getCurrentYearMonth(outStandingDate);

						BufferedWriter bufferedWriterOutStanding = getBufferedWriterFromMap(bufferedWriterOutStandingMap, outstandingYearMonth,
								getDir(Constants.DIR_OUTSTANDING), getFilenameOutstanding(), Constants.FILENAME_BIG_EXT);

						BufferedWriter bufferedWriterPosition = getBufferedWriterFromMap(bufferedWriterPositionMap, outstandingYearMonth,
								getDir(Constants.DIR_POS), getFilenamePos(), Constants.FILENAME_BIG_EXT);

						setOutstandingValue(outstanding, dateFormat);

						outstandingToString(bufferedWriterOutStanding, outstanding);
						// Position
						subOutstandingToString(bufferedWriterPosition, outstanding);

						genResult.addOutstandingCount();

					} catch (UnsupportedOperationException e) {
						break;
					}

				}
				// Do your job here with `date`.
				System.out.println(outStandingDate.getTime());
			}
		} catch (

		IOException e) {
			throw new IOException();
		} finally {

			for (Entry<String, BufferedWriter> entry : bufferedWriterOutStandingMap.entrySet()) {

				BufferedWriter bufferedWriterOutStanding = entry.getValue();
				bufferedWriterOutStanding.flush();
				bufferedWriterOutStanding.close();
			}

			for (Entry<String, BufferedWriter> entry : bufferedWriterPositionMap.entrySet()) {

				BufferedWriter bufferedWriterPosition = entry.getValue();
				bufferedWriterPosition.flush();
				bufferedWriterPosition.close();
			}

		}

		return genResult;
		// return executeFormDataInDB();
	}


	public void outstandingToString(BufferedWriter bufferedWriter, OutstandingBatch outstanding) throws IOException {

		// List<String> list = new ArrayList<String>();

		// 4649820,"20150101",,,,,1,,,,,,1,1,,,"20150101","00:00:00",1,"BWMADMIN",,,,,"INVEST",,,,,,,,,,4216,,,,,,,,,,,,,,,,
		bufferedWriter.write(prepareData(outstanding.getOutstandingId()));bufferedWriter.write(COMMA_STRING);
		bufferedWriter.write(prepareData(outstanding.getOutstandingDate()));bufferedWriter.write(COMMA_STRING);
//		bufferedWriter.write(prepareData(outstanding.getExternalId()) + COMMA_STRING);
//		bufferedWriter.write(prepareData(outstanding.getExternalSequenceNo()) + COMMA_STRING);
//		bufferedWriter.write(prepareData(outstanding.getPortfolio().getPortfolioId())+ COMMA_STRING);
//		bufferedWriter.write(prepareData(outstanding.getBaseCurrency()) + COMMA_STRING);
		bufferedWriter.write(COMMA_STRING + COMMA_STRING + COMMA_STRING + COMMA_STRING);
		bufferedWriter.write(prepareData(outstanding.getUnit()) + COMMA_STRING);
//		bufferedWriter.write(prepareData(outstanding.getCostPerUnit()) + COMMA_STRING);
//		bufferedWriter.write(prepareData(outstanding.getCostValue()) + COMMA_STRING);
//		bufferedWriter.write(prepareData(outstanding.getLocalCostValue()) + COMMA_STRING);
//		bufferedWriter.write(prepareData(outstanding.getCostExchangeRate()) + COMMA_STRING);
//		bufferedWriter.write(prepareData(outstanding.getMarketPerUnit()) + COMMA_STRING);
		bufferedWriter.write(COMMA_STRING + COMMA_STRING);
		bufferedWriter.write(COMMA_STRING + COMMA_STRING + COMMA_STRING);
		
		bufferedWriter.write(prepareData(outstanding.getMarketValue()) + COMMA_STRING);
		bufferedWriter.write(prepareData(outstanding.getLocalMarketValue()) + COMMA_STRING);
		
//		bufferedWriter.write(prepareData(outstanding.getMarketExchangeRate()) + COMMA_STRING);
//		bufferedWriter.write(prepareData(outstanding.getIsImported()) + COMMA_STRING);
		bufferedWriter.write(COMMA_STRING + COMMA_STRING);
		
		bufferedWriter.write(prepareData(outstanding.getLastUpdateDate()) + COMMA_STRING);
		bufferedWriter.write(prepareData(outstanding.getLastUpdateTime()) + COMMA_STRING);
		bufferedWriter.write(prepareData(outstanding.getLastUpdateBy()) + COMMA_STRING);
		bufferedWriter.write(prepareData(outstanding.getLastUpdateByName()) + COMMA_STRING);
		
//		bufferedWriter.write(prepareData(outstanding.getProductClass()) + COMMA_STRING);
//		bufferedWriter.write(prepareData(outstanding.getProductType()) + COMMA_STRING);
//		bufferedWriter.write(prepareData(outstanding.getUnitHolder()) + COMMA_STRING);
// 		bufferedWriter.write(prepareData(depO.getInstrument().getInstrumentId()) + COMMA_STRING);
		bufferedWriter.write(COMMA_STRING + COMMA_STRING + COMMA_STRING + COMMA_STRING);
		
		bufferedWriter.write(prepareData(outstanding.getAccountSubType()) + COMMA_STRING);
		
//		bufferedWriter.write(prepareData(outstanding.getUnrealizedGL()) + COMMA_STRING);
//		bufferedWriter.write(prepareData(outstanding.getAccruedInterest()) + COMMA_STRING);
//		bufferedWriter.write(prepareData(outstanding.getActualRate()) + COMMA_STRING);
//		bufferedWriter.write(prepareData(outstanding.getAnnounceRate()) + COMMA_STRING);
//		bufferedWriter.write(prepareData(outstanding.getCurrentFaceValue()) + COMMA_STRING);
//		bufferedWriter.write(prepareData(outstanding.getProductClassId()) + COMMA_STRING);
//		bufferedWriter.write(prepareData(outstanding.getProductTypeId()) + COMMA_STRING);
//		bufferedWriter.write(prepareData(outstanding.getFormatLayout()) + COMMA_STRING);
//		bufferedWriter.write(prepareData(outstanding.getTemplateId()) + COMMA_STRING);
		bufferedWriter.write(COMMA_STRING + COMMA_STRING + COMMA_STRING);
		bufferedWriter.write(COMMA_STRING + COMMA_STRING + COMMA_STRING);
		bufferedWriter.write(COMMA_STRING + COMMA_STRING + COMMA_STRING);
		
		bufferedWriter.write(prepareData(outstanding.getSubAccountId()) + COMMA_STRING);
		
//		bufferedWriter.write(prepareData(outstanding.getMarketExchangeDate()) + COMMA_STRING);
//		bufferedWriter.write(prepareData(outstanding.getAccruedInterestExchangeDate()) + COMMA_STRING);
//		bufferedWriter.write(prepareData(outstanding.getLocalAccruedInterest()) + COMMA_STRING);
//		bufferedWriter.write(prepareData(outstanding.getAccruedInterestExchangeRate()) + COMMA_STRING);
//		bufferedWriter.write(prepareData(outstanding.getMtmDate()) + COMMA_STRING);
//		bufferedWriter.write(prepareData(outstanding.getCostValueFifo()) + COMMA_STRING);
//		bufferedWriter.write(prepareData(outstanding.getLocalCostValueFifo()) + COMMA_STRING);
//		bufferedWriter.write(prepareData(outstanding.getCostFifoExchangeRate()) + COMMA_STRING);
//		bufferedWriter.write(prepareData(outstanding.getCostExchangeDate()) + COMMA_STRING);
//		bufferedWriter.write(prepareData(outstanding.getCostFifoExchangeDate()) + COMMA_STRING);
//		bufferedWriter.write(prepareData(outstanding.getIsAutoGen()) + COMMA_STRING);
//		bufferedWriter.write(prepareData(outstanding.getHoldAllFundFlag()) + COMMA_STRING);
//		bufferedWriter.write(prepareData(outstanding.getHoldAmount()) + COMMA_STRING);
//		bufferedWriter.write(prepareData(outstanding.getLocalUnrealizedGL()) + COMMA_STRING);
//		bufferedWriter.write(prepareData(outstanding.getUnrealizedGLExchangeRate()) + COMMA_STRING);
//		bufferedWriter.write(prepareData(outstanding.getUnrealizedGLExchangeDate()));
		bufferedWriter.write(COMMA_STRING + COMMA_STRING + COMMA_STRING);
		bufferedWriter.write(COMMA_STRING + COMMA_STRING + COMMA_STRING);
		bufferedWriter.write(COMMA_STRING + COMMA_STRING + COMMA_STRING);
		bufferedWriter.write(COMMA_STRING + COMMA_STRING + COMMA_STRING);
		bufferedWriter.write(COMMA_STRING + COMMA_STRING + COMMA_STRING);
		

		bufferedWriter.newLine();
		// bufferedWriter.flush();

	}
	@Override
    public void afterPropertiesSet() throws Exception {
		log.debug(" === Post GenOutstandingDataCore === ");
		
		if(OUTSTANDING_NEXT_ID == 0){
			Integer outstandingNextId = coreDao.getNextOutstandingId();
			if(outstandingNextId != null){
				OUTSTANDING_NEXT_ID = outstandingNextId.intValue();
						log.debug("Outstanding nextId : " + OUTSTANDING_NEXT_ID);
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

	public List<Integer> getOutstandingRandom(int outstandPerSubAcc) {
		return GenDataDBUtils.getListInteger(1, 365);
	}

	public List<Integer> getExecutionRandom(int executionPerSubAcc) {
		return GenDataDBUtils.getRandList(executionPerSubAcc, 1, 365);
	}

	public OutstandingBatch setOutstandingValue(OutstandingBatch outstanding, String dateFormat) {
		
//		OutstandingBatch outstanding = (OutstandingBatch) SerializationUtils.clone(outstandingInfo);
		
		outstanding.setOutstandingId(getNextOutstandingId());
		outstanding.setOutstandingDate(dateFormat);
//		outstanding.setUnit(outstandingInfo.getUnit());
//		outstanding.setMarketValue(outstandingInfo.getMarketValue());
//		outstanding.setLocalMarketValue(outstandingInfo.getLocalMarketValue());
		outstanding.setLastUpdateDate(dateFormat);
		outstanding.setLastUpdateTime("00:00:00");
		outstanding.setLastUpdateBy(1);
		outstanding.setLastUpdateByName("BWMADMIN");
//		outstanding.setAccountSubType(outstandingInfo.getAccountSubType());
//		outstanding.setSubAccountId(outstandingInfo.getSubAccountId());
		
		return outstanding;
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
