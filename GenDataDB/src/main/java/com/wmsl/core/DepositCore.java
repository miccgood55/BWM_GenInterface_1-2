package com.wmsl.core;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wealth.bwm.batch.impl.entity.cp.account.AccountBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.BankAccountBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.SubAccountBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.SubBankAccountBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.execution.DepositExecutionBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.execution.ExecutionBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.outstanding.DepositOutstandingBacth;
import com.wealth.bwm.batch.impl.entity.cp.account.outstanding.OutstandingBatch;
import com.wealth.bwm.impl.dao.cp.account.SubBankAccountDao;
import com.wealth.exception.dao.InfoEntityServiceException;
import com.wealth.exception.dao.ServerEntityServiceException;
import com.wmsl.Constants;

@Component
public class DepositCore extends GenBigDataBizCore {

//	private final Logger log = LoggerFactory.getLogger(DepositCore.class);
 
	@Autowired
	protected SubBankAccountDao subBankAccountDao;
	public void setSubBankAccountDao(SubBankAccountDao subBankAccountDao) {
		this.subBankAccountDao = subBankAccountDao;
	}

	@Override
	public void accToString(BufferedWriter bufferedWriter, AccountBatch account) throws IOException {
		BankAccountBatch bankAccount = (BankAccountBatch)account;

//		ACCOUNTID *	ACCOUNTTYPE	CURRENCYID	BRANCHID	COUPONRATE	COUPONFREQUENCY
//		37010	1		

		bufferedWriter.write(prepareData(bankAccount.getAccountId()));bufferedWriter.write(COMMA_STRING);
		bufferedWriter.write(prepareData(bankAccount.getAccountType()) + COMMA_STRING);
		bufferedWriter.write(COMMA_STRING + COMMA_STRING + COMMA_STRING);

		bufferedWriter.newLine();
		
	}

	@Override
	public void subAccToString(BufferedWriter bufferedWriter, SubAccountBatch subAccount) throws IOException {
		SubBankAccountBatch subBankAccount = (SubBankAccountBatch)subAccount;
		bufferedWriter.write(prepareData(subBankAccount.getSubAccountId()));
//		SUBACCOUNTID *	EFFECTIVERATE	PARVALUE	PRODUCTTERMUNIT	PRODUCTTERM
		bufferedWriter.write(COMMA_STRING + COMMA_STRING + COMMA_STRING + COMMA_STRING);

		bufferedWriter.newLine();
	}

	@Override
	public void subOutstandingToString(BufferedWriter bufferedWriter, OutstandingBatch outstanding) throws IOException {
		DepositOutstandingBacth depOut = (DepositOutstandingBacth)outstanding;
		
		bufferedWriter.write(prepareData(depOut.getOutstandingId()) + COMMA_STRING);
		bufferedWriter.write(prepareData(depOut.getCallMargin()) + COMMA_STRING);
		bufferedWriter.write(prepareData(depOut.getEquityBalance()) + COMMA_STRING);
		bufferedWriter.write(prepareData(depOut.getExcessEquity()) + COMMA_STRING);
		bufferedWriter.write(prepareData(depOut.getInitialMargin()) + COMMA_STRING);
		bufferedWriter.write(prepareData(depOut.getLiquidationValue()) + COMMA_STRING);
		bufferedWriter.write(prepareData(depOut.getMaintenanceMargin()));

		bufferedWriter.newLine();
	}

	@Override
	public void subExecutionToString(BufferedWriter bufferedWriter, ExecutionBatch execution) throws IOException {
		DepositExecutionBatch depExe = (DepositExecutionBatch)execution;
		bufferedWriter.write(prepareData(depExe.getExecutionId()));
		bufferedWriter.newLine();
	}

	@Override
	public AccountBatch getAccount() {
		BankAccountBatch bankAccount = new BankAccountBatch();
		bankAccount.setSource(Constants.SOURCE_DEP);
		
		bankAccount.setAccountType(1);
		return bankAccount;
	}

	@Override
	public SubAccountBatch getSubAccount() {
		return new SubBankAccountBatch();
	}
	
	@Override
	public OutstandingBatch getOutstanding() {
		return new DepositOutstandingBacth();
	}

	@Override
	public ExecutionBatch getExecution() {
		return new DepositExecutionBatch();
	}

	
	/* 
	 * get Dir
	 * 
	 * (non-Javadoc)
	 * @see com.wmsl.core.GenBigDataCore#getDir(java.lang.String)
	 * 
	 */
	@Override
	public String getDir(String dir) {
		return Constants.DIR_DEP + dir;
	}
	@Override
	public String getFilenameAcc() {
		return Constants.FILE_NAME_DEP_ACC + getStartDate().get(Calendar.YEAR);
	}

	@Override
	public String getFilenameSubAcc() {
		return Constants.FILE_NAME_DEP_SUBACC + getStartDate().get(Calendar.YEAR);
	}

	@Override
	public String getFilenamePos() {
//		return Constants.FILE_NAME_DEP_POS + getStartDate().get(Calendar.YEAR);
		return Constants.FILE_NAME_DEP_POS;
	}

	@Override
	public String getFilenameTx() {
		return Constants.FILE_NAME_DEP_TX + getStartDate().get(Calendar.YEAR);
	}
	
	@Override
	public String getFilenameAccount() {
		return Constants.FILE_NAME_ACCOUNT_DEPOSIT + getStartDate().get(Calendar.YEAR);
	}

	@Override
	public String getFilenameSubAccount() {
		return Constants.FILE_NAME_SUBACCOUNT_DEPOSIT + getStartDate().get(Calendar.YEAR);
	}

	@Override
	public String getFilenameExecution(){
		return Constants.FILE_NAME_EXECUTION_DEPOSIT + getStartDate().get(Calendar.YEAR);
	}
	
	@Override
	public String getFilenameOutstanding() {
//		return Constants.FILE_NAME_OUTSTANDING_DEPOSIT + getStartDate().get(Calendar.YEAR);
		return Constants.FILE_NAME_OUTSTANDING_DEPOSIT;
	}


	@Override
	public List<SubAccountBatch> getSubAccountDB() throws InfoEntityServiceException, ServerEntityServiceException {
		
//		Integer dataFrom = getDataFrom();
//		Integer dataTo = getDataTo();
//		List<SubBankAccount> subBankAccounts;
//		if(dataFrom == null || dataTo == null){
//			subBankAccounts = subBankAccountDao.getObjectList();
//		} else {
//			subBankAccounts = subBankAccountDao.getObjectList(dataFrom, dataTo , true, false);
//		}
//
//		List<SubAccountBatch> subBankAccountBatchs = new ArrayList<SubAccountBatch>();
//		for (SubBankAccount subBankAccount : subBankAccounts) {
//			SubBankAccountBatch subBankAccountBatch = new SubBankAccountBatch();
//			subBankAccountBatch.setSubAccountId(subBankAccount.getSubAccountId());
//			subBankAccountBatchs.add(subBankAccountBatch);
//		}
//		List<? extends SubAccountBatch> subAccounts = subBankAccountBatchs;
//		return (List<SubAccountBatch>) subAccounts;
		
		return new ArrayList<SubAccountBatch>();
	}
	
}
