package com.wmsl.core;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.wealth.bwm.impl.entity.cp.account.SubAccount;
import com.wealth.bwm.impl.entity.cp.account.execution.Execution;
import com.wealth.bwm.impl.entity.cp.account.outstanding.Outstanding;
import com.wealth.exception.dao.InfoEntityServiceException;
import com.wealth.exception.dao.ServerEntityServiceException;
import com.wmsl.Constants;
import com.wmsl.utils.GenDataDBUtils;

@Component
public abstract class GenBigDataCore extends Core implements InitializingBean {

	private static final Logger log = LoggerFactory.getLogger(GenBigDataCore.class);

//	public static final String DEFAULT_LINE_SEPARATOR = System.getProperty("line.separator");
//	public static final String ENCODING = "UTF-8";
//	public static final String FILENAME_EXT = ".big";

	private static int OUTSTANDING_NEXT_ID;
	private static int EXECUTION_NEXT_ID;
	private static Integer INSTRUMENT_ID;

	private int startYear;
	private int startMonth;
	private int startDay;
	private int stopYear;
	private int stopMonth;
	private int stopDay;
	private Integer dataFrom;
	private Integer dataTo;

	private Integer outstandingLimit;
	private Integer executionLimit;
	
	protected final static String COMMA_STRING = ",";
	protected final static String DOUBLE_C = "\"";
	protected final static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

//	@Autowired
//	protected CoreDao coreDao;
//	public void setCoreDao(CoreDao coreDao) {
//		this.coreDao = coreDao;
//	}
//
//	@Autowired
//	protected GenFilesUtils genFilesUtils;
//	public void setGenFilesUtils(GenFilesUtils genFilesUtils) {
//		this.genFilesUtils = genFilesUtils;
//	}

	public abstract List<SubAccount> getSubAccount() throws InfoEntityServiceException, ServerEntityServiceException;
	public abstract String getFilenamePos();
	public abstract String getFilenameTx();
	public abstract String getFilenameExecution();
	public abstract String getFilenameOutstanding();
	public abstract Outstanding getOutstanding();
	public abstract Execution getExecution();
	public abstract void subOutstandingToString(BufferedWriter bufferedWriter, Outstanding outstanding) throws IOException;
	public abstract void subExecutionToString(BufferedWriter bufferedWriter, Execution execution) throws IOException;
	
	public int getNextOutstandingId() {
		return OUTSTANDING_NEXT_ID++;
	}

	public int getNextExecutionId() {
		return EXECUTION_NEXT_ID++;
	}

	public Integer getInstrumentId() {
		return INSTRUMENT_ID;
	}

	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}
	public void setStartMonth(int startMonth) {
		this.startMonth = startMonth;
	}
	public void setStartDay(int startDay) {
		this.startDay = startDay;
	}
	public void setStopYear(int stopYear) {
		this.stopYear = stopYear;
	}
	public void setStopMonth(int stopMonth) {
		this.stopMonth = stopMonth;
	}
	public void setStopDay(int stopDay) {
		this.stopDay = stopDay;
	}

	public void setDataFrom(Integer dataFrom) {
		this.dataFrom = dataFrom;
	}
	public Integer getDataFrom() {
		return this.dataFrom;
	}
	public void setDataTo(Integer dataTo) {
		this.dataTo = dataTo;
	}
	public Integer getDataTo() {
		return this.dataTo;
	}

	public Integer getOutstandingLimit() {
		return outstandingLimit;
	}

	public void setOutstandingLimit(Integer outstandingLimit) {
		this.outstandingLimit = outstandingLimit;
	}

	public Integer getExecutionLimit() {
		return executionLimit;
	}

	public void setExecutionLimit(Integer executionLimit) {
		this.executionLimit = executionLimit;
	}

	public Calendar getStartDate() {
		return GenDataDBUtils.getCalendar(startYear, startMonth, startDay);
	}

	public Calendar getStopDate() {
		return GenDataDBUtils.getCalendar(stopYear, stopMonth, stopDay);
	}
	
	private String instrumentCode;
	public void setInstrumentCode(String instrumentCode) {
		this.instrumentCode = instrumentCode;
	}
	
	private String prefixTx;
	public void setPrefixTx(String prefixTx) {
		this.prefixTx = prefixTx;
	}

	protected String getTxSeq(String prefixTx, long seq){
		return new StringBuilder(prefixTx).append(seq).toString();
	}
	
	@Override
    public void afterPropertiesSet() throws Exception {
		log.debug(" === PostGenDataCore === ");
		if(OUTSTANDING_NEXT_ID == 0){
			Integer outstandingNextId = coreDao.getNextOutstandingId();
			if(outstandingNextId != null){
				OUTSTANDING_NEXT_ID = outstandingNextId.intValue();
						log.debug("Outstanding nextId : " + OUTSTANDING_NEXT_ID);
			}
		}
		if(EXECUTION_NEXT_ID == 0){
			Integer executionNextId = coreDao.getNextExecutionId();
			if(executionNextId != null){
				EXECUTION_NEXT_ID = executionNextId.intValue();
				log.debug("Execution nextId : " + EXECUTION_NEXT_ID);
			}
		}

		if(instrumentCode != null){
			INSTRUMENT_ID = coreDao.getInstrumentId(instrumentCode);
			log.debug(instrumentCode + " InstrumentId : " + INSTRUMENT_ID);
		}
	}

	@Override
	public long execute() throws ServerEntityServiceException, InfoEntityServiceException, IOException{
		log.debug("Start GenBigDataCore.execute ");

		Calendar startDate = this.getStartDate();
		Calendar stopDate = this.getStopDate();
		
		List<SubAccount> subAccounts = getSubAccount();
		int countSubAccount = subAccounts.size();
		
		log.debug("SubAccount size : " + countSubAccount);
		if(countSubAccount == 0){
			return 0;
		}
		
		BufferedWriter bufferedWriterPosition = genFilesUtils.getBufferedWriter(
				Constants.DIR_POS, getFilenamePos(), Constants.FILENAME_BIG_EXT);

		BufferedWriter bufferedWriterTx = genFilesUtils.getBufferedWriter(
				Constants.DIR_TX, getFilenameTx(), Constants.FILENAME_BIG_EXT);

		BufferedWriter bufferedWriterOutStanding = genFilesUtils.getBufferedWriter(
				Constants.DIR_OUTSTANDING, getFilenameOutstanding(), Constants.FILENAME_BIG_EXT);

		BufferedWriter bufferedWriterExe = genFilesUtils.getBufferedWriter(
				Constants.DIR_EXECUTION, getFilenameExecution(), Constants.FILENAME_BIG_EXT);
		
		long countRecord = 0;
		try {

			long seq = 1000000;
			int lastMonth = -1;
			
			
//			executionLimit 
			int totalDay = stopDate.get(Calendar.DAY_OF_YEAR);
			int executionLimitPerDay = ( executionLimit == null ? countSubAccount +1 : Math.round(executionLimit / totalDay));
			int outstandingLimitPerDay = ( outstandingLimit == null ? countSubAccount +1 : Math.round(outstandingLimit / totalDay));
			
			for (int i = 0; i < totalDay; i++) {

				String dateFormat = sdf.format(startDate.getTime());
				
				int currentMonth = startDate.get(Calendar.MONTH);
				if(currentMonth != lastMonth){
					log.debug("Date : " + dateFormat);
					lastMonth = currentMonth;
				}
				int executionCount = 0;
				int outstandingCount = 0;
				for (SubAccount subAccount : subAccounts) {

					boolean isOutstandingSuccess = false;
					boolean isExecutionSuccess = false;
					
//					Position
					Outstanding outstanding = getOutstanding();
					setOutstandingValue(outstanding, dateFormat, subAccount);
					
//					Transection
					Execution execution = getExecution();
					setExecutionValue(execution, dateFormat, subAccount, getTxSeq(prefixTx, seq++));

					
					if(outstandingCount < outstandingLimitPerDay){
						outstandingToString(bufferedWriterOutStanding, outstanding);
						subOutstandingToString(bufferedWriterPosition, outstanding);
						
						outstandingCount++;
					} else {
						isOutstandingSuccess = true;
					}
					
					
					if(executionCount < executionLimitPerDay){
						executionToString(bufferedWriterExe, execution);
						subExecutionToString(bufferedWriterTx, execution);
						
//						CP_DEPOSITEXECUTION
						executionCount++;
					} else {
						isExecutionSuccess = true;
					}
					
					countRecord++;
					
					if(isOutstandingSuccess && isExecutionSuccess){
						break;
					}
				}
				startDate.add(Calendar.DATE, 1);

			}
			
		} catch (IOException e) {
			throw new IOException();
		} finally {
			bufferedWriterOutStanding.flush();
			bufferedWriterOutStanding.close();

			bufferedWriterExe.flush();
			bufferedWriterExe.close();
			
			bufferedWriterPosition.flush();
			bufferedWriterPosition.close();

			bufferedWriterTx.flush();
			bufferedWriterTx.close();
		}
		
		return countRecord;
	}

	public void setOutstandingValue(Outstanding outStand, String dateFormat, SubAccount subAccount) {
		outStand.setOutstandingId(getNextOutstandingId());
		outStand.setOutstandingDate(dateFormat);
		outStand.setUnit(BigDecimal.ONE);
		outStand.setMarketValue(BigDecimal.ONE);
		outStand.setLocalMarketValue(BigDecimal.ONE);
		outStand.setLastUpdateDate(dateFormat);
		outStand.setLastUpdateTime("00:00:00");
		outStand.setLastUpdateBy(1);
		outStand.setLastUpdateByName("BWMADMIN");
		outStand.setAccountSubType("INVEST");
		outStand.setSubAccount(subAccount);
	}

	public void setExecutionValue(Execution execution, String dateFormat, SubAccount subAccount, String externalTxNo) {

		execution.setExecutionId(getNextExecutionId());
		execution.setExecuteDate(dateFormat);
		execution.setTransactionType("1");
		execution.setUnit(BigDecimal.ONE);
		execution.setAmount(BigDecimal.ONE);
		execution.setLastUpdateDate(dateFormat);
		execution.setLastUpdateTime("00:00:00");
		execution.setLastUpdateBy(1);
		execution.setLastUpdateByName("BWMADMIN");
		execution.setStatus("A");
		execution.setSettlementDate(dateFormat);
		execution.setCostPerUnit(BigDecimal.ONE);
		execution.setTradeDate(dateFormat);
		execution.setNetAmount(BigDecimal.ONE);
		execution.setInstrumentId(getInstrumentId());

		// String seqStr = StringUtils.leftPad(String.valueOf(seq++), 7, "0");
		// String.format("%07d", 1)

		execution.setExternalTxNo(externalTxNo);
		execution.setSubAccount(subAccount);

		execution.setLocalCostAmount(BigDecimal.ONE);
		execution.setCreateDate(dateFormat);
		execution.setCreateTime("00:00:00");
		execution.setCreateBy(1);
		execution.setCreateByName("BWMADMIN");
		execution.setSource("BAY-DEBENTURE");
		execution.setAccountNo("-");
		execution.setSubAccountNo("-");
	}

	public void outstandingToString(BufferedWriter bufferedWriter, Outstanding outstanding) throws IOException {

		// List<String> list = new ArrayList<String>();

		// 4649820,"20150101",,,,,1,,,,,,1,1,,,"20150101","00:00:00",1,"BWMADMIN",,,,,"INVEST",,,,,,,,,,4216,,,,,,,,,,,,,,,,
		bufferedWriter.write(prepareData(outstanding.getOutstandingId()));bufferedWriter.write(COMMA_STRING);
		bufferedWriter.write(prepareData(outstanding.getOutstandingDate()));bufferedWriter.write(COMMA_STRING);
//		bufferedWriter.write(prepareData(outstanding.getExternalId()) + COMMA_STRING);
//		bufferedWriter.write(prepareData(outstanding.getExternalSequenceNo()) + COMMA_STRING);
		bufferedWriter.write(COMMA_STRING);bufferedWriter.write(COMMA_STRING);
		
//		bufferedWriter.write(prepareData(outstanding.getPortfolio().getPortfolioId())+ COMMA_STRING);
		bufferedWriter.write(COMMA_STRING);
		
//		bufferedWriter.write(prepareData(outstanding.getBaseCurrency()) + COMMA_STRING);
		bufferedWriter.write(COMMA_STRING);
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
		bufferedWriter.write(COMMA_STRING + COMMA_STRING);
		bufferedWriter.write(COMMA_STRING + COMMA_STRING);
		
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
		
		bufferedWriter.write(prepareData(outstanding.getSubAccount()) + COMMA_STRING);
		
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
		bufferedWriter.write(COMMA_STRING + COMMA_STRING + COMMA_STRING);
		bufferedWriter.write(COMMA_STRING + COMMA_STRING + COMMA_STRING);
		bufferedWriter.write(COMMA_STRING + COMMA_STRING + COMMA_STRING);
		bufferedWriter.write(COMMA_STRING + COMMA_STRING + COMMA_STRING);
		bufferedWriter.write(COMMA_STRING + COMMA_STRING);
		
//		bufferedWriter.write(prepareData(outstanding.getUnrealizedGLExchangeRate()) + COMMA_STRING);
//		bufferedWriter.write(prepareData(outstanding.getUnrealizedGLExchangeDate()));
		bufferedWriter.write(COMMA_STRING);

		bufferedWriter.newLine();
		// bufferedWriter.flush();

	}

	public void executionToString(BufferedWriter bufferedWriter, Execution execution) throws IOException {

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
		bufferedWriter.write(prepareData(execution.getSubAccount()) + COMMA_STRING);
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

//	private String prepareData(Currency val) {
//		if (val == null) {
//			return "";
//		}
//		return val.getCurrencyId().toString();
//	}

	private String prepareData(SubAccount val) {
		if (val == null) {
			return "";
		}
		return val.getSubAccountId().toString();
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

}
