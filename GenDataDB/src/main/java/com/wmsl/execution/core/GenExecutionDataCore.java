package com.wmsl.execution.core;

import java.io.IOException;
import java.math.BigDecimal;
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

import com.wealth.bwm.batch.impl.entity.cp.account.SubAccountBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.execution.ExecutionBatch;
import com.wealth.exception.dao.InfoEntityServiceException;
import com.wealth.exception.dao.ServerEntityServiceException;
import com.wmsl.Constants;
import com.wmsl.bean.GenResult;
import com.wmsl.core.Core;
import com.wmsl.utils.GenDataDBUtils;

import java.io.BufferedWriter;

@Component
public abstract class GenExecutionDataCore extends Core implements InitializingBean{

	private static final Logger log = LoggerFactory.getLogger(GenExecutionDataCore.class);

	protected final static String COMMA_STRING = ",";
	protected final static String DOUBLE_C = "\"";
	private int startYear;
	private int startMonth;
	private int startDay;

	private int stopYear;
	private int stopMonth;
	private int stopDay;
	private static int EXECUTION_NEXT_ID;

	private Map<String, BufferedWriter> bufferedWriterTxMap = new HashMap<String, BufferedWriter>();
	private Map<String, BufferedWriter> bufferedWriterExecutionMap = new HashMap<String, BufferedWriter>();;
	private Map<Date, String> bufferedWriterDateMap = new HashMap<Date, String>();;

//	abstract protected ExecutionBatch getExecution();

	abstract protected List<ExecutionBatch> getExecutionList() throws InfoEntityServiceException, ServerEntityServiceException;
	abstract public String getDir(String dir);
	abstract public String getFilenameTx();
	abstract public String getFilenameExecution();

	abstract public void subExecutionToString(BufferedWriter bufferedWriter, ExecutionBatch execution) throws IOException;
	
	private String prefixTx;
	public void setPrefixTx(String prefixTx) {
		this.prefixTx = prefixTx;
	}

	protected String getTxSeq(long seq){
		return new StringBuilder(prefixTx).append(seq).toString();
	}

	public int getNextExecutionId() {
		return EXECUTION_NEXT_ID++;
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
			long seq = 500000000;

			List<ExecutionBatch> executionList = this.getExecutionList();

//			String startDateFormat = SDF.format(startDateD);

			for (Calendar executionDate = (Calendar) startDate.clone(); 
					executionDate.before(stopDate) || executionDate.equals(stopDate); 
					executionDate.add(Calendar.DATE, 1)) {
				
				String dateFormat = getDateFromMap(bufferedWriterDateMap, executionDate);

				String executionYearMonth = getCurrentYearMonth(executionDate);
				
				for (ExecutionBatch executionInfo : executionList) {

					try {

						ExecutionBatch execution = executionInfo;

						BufferedWriter bufferedWriterOutStanding = getBufferedWriterFromMap(bufferedWriterExecutionMap, executionYearMonth,
								getDir(Constants.DIR_EXECUTION), getFilenameExecution(), Constants.FILENAME_BIG_EXT);

						BufferedWriter bufferedWriterPosition = getBufferedWriterFromMap(bufferedWriterTxMap, executionYearMonth,
								getDir(Constants.DIR_POS), getFilenameTx(), Constants.FILENAME_BIG_EXT);

						setExecutionValue(execution, dateFormat, getTxSeq(seq++));

						executionToString(bufferedWriterOutStanding, execution);
						// Position
						subExecutionToString(bufferedWriterPosition, execution);

						genResult.addTransectionCount();

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

			for (Entry<String, BufferedWriter> entry : bufferedWriterExecutionMap.entrySet()) {

				BufferedWriter bufferedWriterOutStanding = entry.getValue();
				bufferedWriterOutStanding.flush();
				bufferedWriterOutStanding.close();
			}

			for (Entry<String, BufferedWriter> entry : bufferedWriterTxMap.entrySet()) {

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
		log.debug(" === Post GenExecutionDataCore === ");

		if(EXECUTION_NEXT_ID == 0){
			Integer executionNextId = coreDao.getNextExecutionId();
			if(executionNextId != null){
				EXECUTION_NEXT_ID = executionNextId.intValue();
				log.debug("Execution nextId : " + EXECUTION_NEXT_ID);
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

	public void executionToString(BufferedWriter bufferedWriter, ExecutionBatch execution) throws IOException {

		// 367020,,"20160302",,"1",,10.00,810.00,,,"20160302","11:59:16",1,"BWMAdmin",,,,,,,"A","20160302",81.00,,,,,"20160302",,
		// 0.00,,660,,,,,,,"BOND_TX_NO_00000001",68128,,"6",,,,"20160302","11:59:16",1,"BWMAdmin",,,,,,,,,,,,,,,,,,,,,"BAY-DEBENTURE","-","-"
		//
		// EXECUTIONID * EXTERNALID EXECUTEDATE PORTFOLIOID TRANSACTIONTYPE
		// PAYMENTTYPE UNIT AMOUNT SOURCEOFFUND

		bufferedWriter.write(prepareData(execution.getExecutionId()) + COMMA_STRING);
		// bufferedWriter.write(prepareData(depExe.getExternalId()) +
		// COMMA_STRING);
		bufferedWriter.write(COMMA_STRING);

		bufferedWriter.write(prepareData(execution.getExecuteDate()) + COMMA_STRING);
		// bufferedWriter.write(prepareData(depExe.getPortfolio()) +
		// COMMA_STRING);
		bufferedWriter.write(COMMA_STRING);

		bufferedWriter.write(prepareData(execution.getTransactionType()) + COMMA_STRING);
		// bufferedWriter.write(prepareData(depExe.getPaymentType()) +
		// COMMA_STRING);
		bufferedWriter.write(COMMA_STRING);

		bufferedWriter.write(prepareData(execution.getUnit()) + COMMA_STRING);
		bufferedWriter.write(prepareData(execution.getAmount()) + COMMA_STRING);
		// bufferedWriter.write(prepareData(depExe.getSourceOfFund()) +
		// COMMA_STRING);
		// bufferedWriter.write(prepareData(depExe.getIsImported()) +
		// COMMA_STRING);
		bufferedWriter.write(COMMA_STRING + COMMA_STRING);

		bufferedWriter.write(prepareData(execution.getLastUpdateDate()) + COMMA_STRING);
		bufferedWriter.write(prepareData(execution.getLastUpdateTime()) + COMMA_STRING);
		bufferedWriter.write(prepareData(execution.getLastUpdateBy()) + COMMA_STRING);
		bufferedWriter.write(prepareData(execution.getLastUpdateByName()) + COMMA_STRING);

		// bufferedWriter.write(prepareData(depExe.getCreateUserId()) +
		// COMMA_STRING);
		// bufferedWriter.write(prepareData(depExe.getCreateUserName()) +
		// COMMA_STRING);
		// bufferedWriter.write(prepareData(depExe.getCreateUserCode()) +
		// COMMA_STRING);
		// bufferedWriter.write(prepareData(depExe.getCreateUserBranch()) +
		// COMMA_STRING);
		// bufferedWriter.write(prepareData(depExe.getExecuteTime()) +
		// COMMA_STRING);
		// bufferedWriter.write(prepareData(depExe.getMainCashAccount()) +
		// COMMA_STRING);
		bufferedWriter.write(COMMA_STRING + COMMA_STRING + COMMA_STRING);
		bufferedWriter.write(COMMA_STRING + COMMA_STRING + COMMA_STRING);

		bufferedWriter.write(prepareData(execution.getStatus()) + COMMA_STRING);
		bufferedWriter.write(prepareData(execution.getSettlementDate()) + COMMA_STRING);
		bufferedWriter.write(prepareData(execution.getCostPerUnit()) + COMMA_STRING);

		// bufferedWriter.write(prepareData(depExe.getCashTransactionType()) +
		// COMMA_STRING);
		// bufferedWriter.write(prepareData(depExe.getTransactionInOut()) +
		// COMMA_STRING);
		// bufferedWriter.write(prepareData(depExe.getProductClass()) +
		// COMMA_STRING);
		// bufferedWriter.write(prepareData(depExe.getProductType()) +
		// COMMA_STRING);
		bufferedWriter.write(COMMA_STRING + COMMA_STRING);
		bufferedWriter.write(COMMA_STRING + COMMA_STRING);

		bufferedWriter.write(prepareData(execution.getTradeDate()) + COMMA_STRING);
		// bufferedWriter.write(prepareData(depExe.getSettleAmount()) +
		// COMMA_STRING);
		bufferedWriter.write(COMMA_STRING);

		// 367020,,"20160302",,"1",,10.00,810.00,,,"20160302","11:59:16",1,"BWMAdmin",,,,,,,"A","20160302",81.00,,,,,"20160302",,
		// 0.00,,660,,,,,,,"BOND_TX_NO_00000001",68128,,"6",,,,"20160302","11:59:16",1,"BWMAdmin",,,,,,,,,,,,,,,,,,,,,"BAY-DEBENTURE","-","-"

		bufferedWriter.write(prepareData(execution.getNetAmount()) + COMMA_STRING);
		// bufferedWriter.write(prepareData(depExe.getRealizedGL()) +
		// COMMA_STRING);
		bufferedWriter.write(COMMA_STRING);

		bufferedWriter.write(prepareData(execution.getInstrumentId()) + COMMA_STRING);
		// bufferedWriter.write(prepareData(depExe.getAccruedInterest()) +
		// COMMA_STRING);
		// bufferedWriter.write(prepareData(depExe.getProductClassId()) +
		// COMMA_STRING);
		// bufferedWriter.write(prepareData(depExe.getProductTypeId()) +
		// COMMA_STRING);
		// bufferedWriter.write(prepareData(depExe.getFormatLayout()) +
		// COMMA_STRING);
		// bufferedWriter.write(prepareData(depExe.getCounterParty()) +
		// COMMA_STRING);
		// bufferedWriter.write(prepareData(depExe.getBroker()) + COMMA_STRING);
		bufferedWriter.write(COMMA_STRING + COMMA_STRING + COMMA_STRING);
		bufferedWriter.write(COMMA_STRING + COMMA_STRING + COMMA_STRING);

		bufferedWriter.write(prepareData(execution.getExternalTxNo()) + COMMA_STRING);
		bufferedWriter.write(prepareData(execution.getSubAccountId()) + COMMA_STRING);
		// bufferedWriter.write(prepareData(depExe.getExternalId()) +
		// COMMA_STRING);
		// bufferedWriter.write(prepareData(depExe.getSUBTRANSACTIONTYPE) +
		// COMMA_STRING);
		// bufferedWriter.write(prepareData(depExe.getAmountExchangeDate()) +
		// COMMA_STRING);
		// bufferedWriter.write(prepareData(depExe.getLocalCostAmount()) +
		// COMMA_STRING);
		// bufferedWriter.write(prepareData(depExe.getCostAmountExchangeRate())
		// + COMMA_STRING);
		bufferedWriter.write(COMMA_STRING + COMMA_STRING);
		bufferedWriter.write(COMMA_STRING + COMMA_STRING);
		bufferedWriter.write(COMMA_STRING);

		bufferedWriter.write(prepareData(execution.getCreateDate()) + COMMA_STRING);
		bufferedWriter.write(prepareData(execution.getCreateTime()) + COMMA_STRING);
		bufferedWriter.write(prepareData(execution.getCreateBy()) + COMMA_STRING);
		bufferedWriter.write(prepareData(execution.getCreateByName()) + COMMA_STRING);

		// bufferedWriter.write(prepareData(depExe.getBranch()) + COMMA_STRING);
		// bufferedWriter.write(prepareData(depExe.getBalanceUnit()) +
		// COMMA_STRING);
		// bufferedWriter.write(prepareData(depExe.getLocalRealizedGL()) +
		// COMMA_STRING);
		// bufferedWriter.write(prepareData(depExe.getRealizedGLExchangeRate())
		// + COMMA_STRING);
		// bufferedWriter.write(prepareData(depExe.getRealizedGLFifo()) +
		// COMMA_STRING);
		// bufferedWriter.write(prepareData(depExe.getLocalRealizedGLFifo()) +
		// COMMA_STRING);
		// bufferedWriter.write(prepareData(depExe.getRealizedGLFifoExchangeRate())
		// + COMMA_STRING);
		// bufferedWriter.write(prepareData(depExe.getRemark()) + COMMA_STRING);
		// bufferedWriter.write(prepareData(depExe.getIcCode()) + COMMA_STRING);
		// bufferedWriter.write(prepareData(depExe.getRmCode()) + COMMA_STRING);
		// bufferedWriter.write(prepareData(depExe.getReferralCode()) +
		// COMMA_STRING);
		// bufferedWriter.write(prepareData(depExe.getRealizedGLExchangeDate())
		// + COMMA_STRING);
		// bufferedWriter.write(prepareData(depExe.getRealizedGLFifoExchangeDate())
		// + COMMA_STRING);
		// bufferedWriter.write(prepareData(depExe.getLocalAccruedInterest()) +
		// COMMA_STRING);
		// bufferedWriter.write(prepareData(depExe.getAccruedInterestExchangeRate())
		// + COMMA_STRING);
		// bufferedWriter.write(prepareData(depExe.getAccruedInterestExchangeDate())
		// + COMMA_STRING);
		// bufferedWriter.write(prepareData(depExe.getLocalNetAmount()) +
		// COMMA_STRING);
		// bufferedWriter.write(prepareData(depExe.getNetAmountExchangeRate()) +
		// COMMA_STRING);
		// bufferedWriter.write(prepareData(depExe.getNetAmountExchangeDate()) +
		// COMMA_STRING);
		// bufferedWriter.write(prepareData(depExe.getSeq()) + COMMA_STRING);
		bufferedWriter.write(COMMA_STRING + COMMA_STRING + COMMA_STRING);
		bufferedWriter.write(COMMA_STRING + COMMA_STRING + COMMA_STRING);
		bufferedWriter.write(COMMA_STRING + COMMA_STRING + COMMA_STRING);
		bufferedWriter.write(COMMA_STRING + COMMA_STRING + COMMA_STRING);
		bufferedWriter.write(COMMA_STRING + COMMA_STRING + COMMA_STRING);
		bufferedWriter.write(COMMA_STRING + COMMA_STRING + COMMA_STRING);
		bufferedWriter.write(COMMA_STRING + COMMA_STRING);

		bufferedWriter.write(prepareData(execution.getSource()) + COMMA_STRING);
		bufferedWriter.write(prepareData(execution.getAccountNo()) + COMMA_STRING);
		bufferedWriter.write(prepareData(execution.getSubAccountNo()));
		bufferedWriter.newLine();

	}

	public void setExecutionValue(ExecutionBatch execution, String dateFormat, String externalTxNo) {

		execution.setExecutionId(getNextExecutionId());
		execution.setExecuteDate(dateFormat);
		execution.setSettlementDate(dateFormat);
		execution.setTradeDate(dateFormat);

		execution.setAmountExchangeDate(dateFormat);
		execution.setCreateDate(dateFormat);
		execution.setCreateTime("00:00:00");
		execution.setCreateBy(1);
		execution.setCreateByName("BWMADMIN");
		
		execution.setLastUpdateDate(dateFormat);
		execution.setLastUpdateTime("00:00:00");
		execution.setLastUpdateBy(1);
		execution.setLastUpdateByName("BWMADMIN");
		execution.setExternalTxNo(externalTxNo);
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
