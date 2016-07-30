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

import com.wealth.bwm.batch.impl.entity.cp.account.SnapshotAccountBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.execution.ExecutionBatch;
import com.wealth.exception.dao.InfoEntityServiceException;
import com.wealth.exception.dao.ServerEntityServiceException;
import com.wmsl.Constants;
import com.wmsl.bean.GenResult;
import com.wmsl.core.Core;
import com.wmsl.dao.impl.SnapshotAccountDao;
import com.wmsl.utils.GenDataDBUtils;

import java.io.BufferedWriter;

@Component
public class GenSnapShotDataCore extends Core implements InitializingBean{

	private static final Logger log = LoggerFactory.getLogger(GenSnapShotDataCore.class);

	protected final static String COMMA_STRING = ",";
	protected final static String DOUBLE_C = "\"";
	private int startYear;
	private int startMonth;
	private int startDay;

	private int stopYear;
	private int stopMonth;
	private int stopDay;
	private static int SNAPSHOTACCOUNT_NEXT_ID;

	private Map<String, BufferedWriter> bufferedWriterSnapshotMap = new HashMap<String, BufferedWriter>();
//	private Map<String, BufferedWriter> bufferedWriterSnapshotAccountMap = new HashMap<String, BufferedWriter>();;
	private Map<Date, String> bufferedWriterDateMap = new HashMap<Date, String>();;

	private SnapshotAccountDao snapshotAccountDao;
	public void setSnapshotAccountDao(SnapshotAccountDao snapshotAccountDao){
		this.snapshotAccountDao = snapshotAccountDao;
	}

	public String getDir(String dir) {
		return Constants.DIR_BIG_SNAPSHOTACCOUNT + dir;
	}

	public String getFilenameSnapshotAccount() {
		return Constants.FILE_NAME_SNAPSHOTACCOUNT + getStartDate().get(Calendar.YEAR);
	}

	public int getNextSnapshotAccountId() {
		return SNAPSHOTACCOUNT_NEXT_ID++;
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

			List<SnapshotAccountBatch> snapShotAccountList = snapshotAccountDao.getObjectList();

			for (Calendar snapShotAccountDate = (Calendar) startDate.clone(); 
					snapShotAccountDate.before(stopDate) || snapShotAccountDate.equals(stopDate); 
					snapShotAccountDate.add(Calendar.DATE, 1)) {
				
				String dateFormat = getDateFromMap(bufferedWriterDateMap, snapShotAccountDate);

				String snapShotAccountYearMonth = getCurrentYearMonth(snapShotAccountDate);
				
				for (SnapshotAccountBatch snapShotAccountInfo : snapShotAccountList) {

					try {

						SnapshotAccountBatch snapShotAccount = snapShotAccountInfo;

						BufferedWriter bufferedWriterSnapshot = getBufferedWriterFromMap(bufferedWriterSnapshotMap, snapShotAccountYearMonth,
								getDir(Constants.DIR_POS), getFilenameSnapshotAccount(), Constants.FILENAME_BIG_EXT);

						setSnapshotAccountValue(snapShotAccount, dateFormat);

						snapShotAccountToString(bufferedWriterSnapshot, snapShotAccount);

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

//			for (Entry<String, BufferedWriter> entry : bufferedWriterSnapshotAccountMap.entrySet()) {
//
//				BufferedWriter bufferedWriterOutStanding = entry.getValue();
//				bufferedWriterOutStanding.flush();
//				bufferedWriterOutStanding.close();
//			}

			for (Entry<String, BufferedWriter> entry : bufferedWriterSnapshotMap.entrySet()) {

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
		log.debug(" === Post GenSnapshotAccountDataCore === ");

		if(SNAPSHOTACCOUNT_NEXT_ID == 0){
			Integer snapShotAccountNextId = coreDao.getNextSnapshotAccountId();
			if(snapShotAccountNextId != null){
				SNAPSHOTACCOUNT_NEXT_ID = snapShotAccountNextId.intValue();
				log.debug("SnapshotAccount nextId : " + SNAPSHOTACCOUNT_NEXT_ID);
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

	public void snapShotAccountToString(BufferedWriter bufferedWriter, SnapshotAccountBatch snapShotAccount) throws IOException {

		// 367020,,"20160302",,"1",,10.00,810.00,,,"20160302","11:59:16",1,"BWMAdmin",,,,,,,"A","20160302",81.00,,,,,"20160302",,
		// 0.00,,660,,,,,,,"BOND_TX_NO_00000001",68128,,"6",,,,"20160302","11:59:16",1,"BWMAdmin",,,,,,,,,,,,,,,,,,,,,"BAY-DEBENTURE","-","-"
		//
		// EXECUTIONID * EXTERNALID EXECUTEDATE PORTFOLIOID TRANSACTIONTYPE
		// PAYMENTTYPE UNIT AMOUNT SOURCEOFFUND

		bufferedWriter.write(prepareData(snapShotAccount.getSnapshotAccountId()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getAsOfDate()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getAccountId()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getAccountNo()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getSource()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getCustomerId()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getOwnerType()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getBranchId()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getBranchCode()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getBranchNameEn()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getBranchNameOther()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getBankCode()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getBankNameEn()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getBankNameOther()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getSubAccountId()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getSubAccountNo()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getInstrumentId()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getPortfolioId()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getPortfolioCode()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getPortfolioNameEn()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getPortfolioNameOther()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getPortfolioIsDefault()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getPortfolioType()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getCreateDate()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getCreateTime()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getCreateBy()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getCreateByName()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getAccountName()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getIssueDate()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getMatureDate()) + COMMA_STRING);
		bufferedWriter.write(prepareData(snapShotAccount.getIsActive()));
		
		bufferedWriter.newLine();

	}

	public void setSnapshotAccountValue(SnapshotAccountBatch snapShotAccount, String dateFormat) {

		snapShotAccount.setSnapshotAccountId(getNextSnapshotAccountId());
		snapShotAccount.setAsOfDate(dateFormat);
		snapShotAccount.setCreateDate(dateFormat);
		snapShotAccount.setCreateTime("00:00:00");
		snapShotAccount.setCreateBy(1);
		snapShotAccount.setCreateByName("BWMADMIN");
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
