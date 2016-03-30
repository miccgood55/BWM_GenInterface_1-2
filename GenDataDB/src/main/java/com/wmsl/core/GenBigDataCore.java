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

import com.wealth.bwm.batch.impl.entity.cp.account.AccountBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.SubAccountBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.execution.ExecutionBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.outstanding.OutstandingBatch;
import com.wealth.exception.dao.InfoEntityServiceException;
import com.wealth.exception.dao.ServerEntityServiceException;
import com.wmsl.utils.GenDataDBUtils;

@Component
public abstract class GenBigDataCore extends Core implements InitializingBean {

	private static final Logger log = LoggerFactory.getLogger(GenBigDataCore.class);

//	public static final String DEFAULT_LINE_SEPARATOR = System.getProperty("line.separator");
//	public static final String ENCODING = "UTF-8";
//	public static final String FILENAME_EXT = ".big";

	private static int ACCOUNT_NEXT_ID;
	private static int SUBACCOUNT_NEXT_ID;
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

	private Integer accountLimit;
	private Integer subAccountLimit;
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

	public abstract List<SubAccountBatch> getSubAccountDB() throws InfoEntityServiceException, ServerEntityServiceException;
	public abstract String getDir(String dir);
	public abstract String getFilenameAcc();
	public abstract String getFilenameSubAcc();
	public abstract String getFilenamePos();
	public abstract String getFilenameTx();
	public abstract String getFilenameExecution();
	public abstract String getFilenameOutstanding();
	public abstract String getFilenameAccount();
	public abstract String getFilenameSubAccount();
	
	public abstract AccountBatch getAccount();
	public abstract SubAccountBatch getSubAccount();
	public abstract OutstandingBatch getOutstanding();
	public abstract ExecutionBatch getExecution();
	public abstract void accToString(BufferedWriter bufferedWriter, AccountBatch account) throws IOException;
	public abstract void subAccToString(BufferedWriter bufferedWriter, SubAccountBatch subAccount) throws IOException;
	public abstract void subOutstandingToString(BufferedWriter bufferedWriter, OutstandingBatch outstanding) throws IOException;
	public abstract void subExecutionToString(BufferedWriter bufferedWriter, ExecutionBatch execution) throws IOException;
	
	public Integer getBranchId(){
		return 1;
	}
	
	public int getNextAccountId() {
		return ACCOUNT_NEXT_ID++;
	}

	public int getNextSubAccountId() {
		return SUBACCOUNT_NEXT_ID++;
	}

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
	public Integer getAccountLimit() {
		return accountLimit;
	}
	public void setAccountLimit(Integer accountLimit) {
		this.accountLimit = accountLimit;
	}
	public Integer getSubAccountLimit() {
		return subAccountLimit;
	}
	public void setSubAccountLimit(Integer subAccountLimit) {
		this.subAccountLimit = subAccountLimit;
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

	public String getInstrumentCode() {
		return this.instrumentCode;
	}
	
	
	private String prefixTx;
	public void setPrefixTx(String prefixTx) {
		this.prefixTx = prefixTx;
	}

	protected String getTxSeq(long seq){
		return new StringBuilder(prefixTx).append(seq).toString();
	}
	
	@Override
    public void afterPropertiesSet() throws Exception {
		log.debug(" === PostGenDataCore === ");
		
		if(ACCOUNT_NEXT_ID == 0){
			Integer accountId = coreDao.getNextAccountId();
			if(accountId != null){
				ACCOUNT_NEXT_ID = accountId.intValue();
						log.debug("Account nextId : " + ACCOUNT_NEXT_ID);
			}
		}
		if(SUBACCOUNT_NEXT_ID == 0){
			Integer subAccountId = coreDao.getNextSubAccountId();
			if(subAccountId != null){
				SUBACCOUNT_NEXT_ID = subAccountId.intValue();
						log.debug("SubAccount nextId : " + SUBACCOUNT_NEXT_ID);
			}
		}
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

	public void setSubAccountValue(SubAccountBatch subAccount, String startDateFormat, AccountBatch account, String accountNo) {
		
		subAccount.setSubAccountId(getNextSubAccountId());
		subAccount.setAccount(account);
		subAccount.setSubAccountNo(accountNo);
		subAccount.setCreateDate(startDateFormat);
		subAccount.setCreateTime("00:00:00");
		subAccount.setCreateBy(1);
		subAccount.setCreateByName("BWMADMIN");
		subAccount.setLastUpdateDate(startDateFormat);
		subAccount.setLastUpdateTime("00:00:00");
		subAccount.setLastUpdateBy(1);
		subAccount.setLastUpdateByName("BWMADMIN");
		subAccount.setInstrumentId(getInstrumentId());
		subAccount.setIsActive('Y');
		
	}

	public String getAccountNumber(String cifCode, int accountIndex) {
		return getAccountNumber("ACC_", cifCode, accountIndex);
	}
	
	public String getAccountNumber(String prefix, String cifCode, int accountIndex) {
		return new StringBuilder(prefix).append(cifCode).append("_").append(String.format("%02d", accountIndex)).toString();
	}
	
	public void setAccountValue(AccountBatch account, String startDateFormat, String accountNumber) {
		
		account.setAccountId(getNextAccountId());
		account.setAccountNumber(accountNumber);
		account.setAccountName("NAME_" + accountNumber);
		account.setCreateDate(startDateFormat);
		account.setCreateTime("00:00:00");
		account.setCreateBy(1);
		account.setCreateByName("BWMADMIN");
		account.setLastUpdateDate(startDateFormat);
		account.setLastUpdateTime("00:00:00");
		account.setLastUpdateBy(1);
		account.setLastUpdateByName("BWMADMIN");
		account.setBranchId(getBranchId());
		account.setSource("BAY-CA");
		account.setAccountNameOther("NAME_" + accountNumber);
	}
	
	public void setOutstandingValue(OutstandingBatch outstanding, String dateFormat, SubAccountBatch subAccount) {
		outstanding.setOutstandingId(getNextOutstandingId());
		outstanding.setOutstandingDate(dateFormat);
		outstanding.setUnit(BigDecimal.ONE);
		outstanding.setMarketValue(BigDecimal.ONE);
		outstanding.setLocalMarketValue(BigDecimal.ONE);
		outstanding.setLastUpdateDate(dateFormat);
		outstanding.setLastUpdateTime("00:00:00");
		outstanding.setLastUpdateBy(1);
		outstanding.setLastUpdateByName("BWMADMIN");
		outstanding.setAccountSubType("INVEST");
		outstanding.setSubAccountId(subAccount.getSubAccountId());
		
	}

	public void setExecutionValue(ExecutionBatch execution, String dateFormat, SubAccountBatch subAccount, String externalTxNo) {

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

		execution.setExternalTxNo(externalTxNo);
		execution.setSubAccountId(subAccount.getSubAccountId());

		execution.setLocalCostAmount(BigDecimal.ONE);
		execution.setCreateDate(dateFormat);
		execution.setCreateTime("00:00:00");
		execution.setCreateBy(1);
		execution.setCreateByName("BWMADMIN");
		execution.setSource("BAY-DEBENTURE");
		execution.setAccountNo("-");
		execution.setSubAccountNo("-");
	}

	

	public void subAccountToString(BufferedWriter bufferedWriter, SubAccountBatch subAccount) throws IOException {
		bufferedWriter.write(prepareData(subAccount.getSubAccountId()));bufferedWriter.write(COMMA_STRING);
		bufferedWriter.write(prepareData(subAccount.getAccount()));bufferedWriter.write(COMMA_STRING);
		bufferedWriter.write(prepareData(subAccount.getSubAccountNo()));bufferedWriter.write(COMMA_STRING);
		

		bufferedWriter.write(prepareData(subAccount.getCreateDate()) + COMMA_STRING);
		bufferedWriter.write(prepareData(subAccount.getCreateTime()) + COMMA_STRING);
		bufferedWriter.write(prepareData(subAccount.getCreateBy()) + COMMA_STRING);
		bufferedWriter.write(prepareData(subAccount.getCreateByName()) + COMMA_STRING);

		bufferedWriter.write(prepareData(subAccount.getLastUpdateDate()) + COMMA_STRING);
		bufferedWriter.write(prepareData(subAccount.getLastUpdateTime()) + COMMA_STRING);
		bufferedWriter.write(prepareData(subAccount.getLastUpdateBy()) + COMMA_STRING);
		bufferedWriter.write(prepareData(subAccount.getLastUpdateByName()) + COMMA_STRING);
		
		bufferedWriter.write(prepareData(subAccount.getInstrumentId()));bufferedWriter.write(COMMA_STRING);
//		bufferedWriter.write(prepareData(subAccount.getRemark()));bufferedWriter.write(COMMA_STRING);
//		bufferedWriter.write(prepareData(subAccount.getIssueDate()));bufferedWriter.write(COMMA_STRING);
		bufferedWriter.write(COMMA_STRING + COMMA_STRING + COMMA_STRING);
		bufferedWriter.write(prepareData(subAccount.getIsActive()));bufferedWriter.write(COMMA_STRING);
		bufferedWriter.write(COMMA_STRING + COMMA_STRING + 'N');
	
	}
	
	
	public void accountToString(BufferedWriter bufferedWriter, AccountBatch account) throws IOException {
		
//		ACCOUNTID *	ACCOUNTNUMBER	ACCOUNTNAME	
//		CREATEDATE	CREATETIME	CREATEBY	CREATEBYNAME	
//		LASTUPDATEDATE	LASTUPDATETIME	LASTUPDATEBY	LASTUPDATEBYNAME	
//		BRANCHID	SOURCE	ACCOUNTNAMEOTHER

		
		bufferedWriter.write(prepareData(account.getAccountId()));bufferedWriter.write(COMMA_STRING);
		bufferedWriter.write(prepareData(account.getAccountNumber()));bufferedWriter.write(COMMA_STRING);
		bufferedWriter.write(prepareData(account.getAccountName()));bufferedWriter.write(COMMA_STRING);

		bufferedWriter.write(prepareData(account.getCreateDate()) + COMMA_STRING);
		bufferedWriter.write(prepareData(account.getCreateTime()) + COMMA_STRING);
		bufferedWriter.write(prepareData(account.getCreateBy()) + COMMA_STRING);
		bufferedWriter.write(prepareData(account.getCreateByName()) + COMMA_STRING);
		bufferedWriter.write(prepareData(account.getLastUpdateDate()) + COMMA_STRING);
		bufferedWriter.write(prepareData(account.getLastUpdateTime()) + COMMA_STRING);
		bufferedWriter.write(prepareData(account.getLastUpdateBy()) + COMMA_STRING);
		bufferedWriter.write(prepareData(account.getLastUpdateByName()) + COMMA_STRING);

//		INSTRUMENTID	OPENDATE	CLOSEDATE	PORTFOLIOID	INTERNALTYPEID	
		bufferedWriter.write(COMMA_STRING + COMMA_STRING);
		bufferedWriter.write(COMMA_STRING + COMMA_STRING + COMMA_STRING);

		bufferedWriter.write(prepareData(account.getBranchId()) + COMMA_STRING);
		bufferedWriter.write(prepareData(account.getSource()) + COMMA_STRING);
		bufferedWriter.write(prepareData(account.getAccountNameOther()));bufferedWriter.write(COMMA_STRING);

//		ISPRIVATEFUND	REFERENCEPRIVATEFUND
		bufferedWriter.write(COMMA_STRING + COMMA_STRING);
	
	}
	
	public void outstandingToString(BufferedWriter bufferedWriter, OutstandingBatch outstanding) throws IOException {

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

//	private String prepareData(Currency val) {
//		if (val == null) {
//			return "";
//		}
//		return val.getCurrencyId().toString();
//	}

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

	private String prepareData(AccountBatch account) {
		if (account == null) {
			return "";
		}
		return account.getAccountId().toString();
	}

}
