package com.wmsl.core;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Component;

import com.wealth.bwm.batch.impl.entity.cp.account.AccountBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.FixedIncomeAccountBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.SubAccountBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.SubBankAccountBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.SubFixedIncomeAccountBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.execution.ExecutionBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.execution.FixedIncomeExecutionBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.outstanding.OutstandingBatch;
import com.wealth.bwm.impl.entity.cp.account.SubBankAccount;
import com.wealth.exception.dao.InfoEntityServiceException;
import com.wealth.exception.dao.ServerEntityServiceException;
import com.wmsl.Constants;

@Component
public class NonBayDebCore extends GenBigDataInstrumentsCore{

	/*
	 * override to modifile
	 * (non-Javadoc)
	 * @see com.wmsl.core.GenBigDataCore#getAccountNumber(java.lang.String, int)
	 */
	@Override
	public String getAccountNumber(String cifCode, int accountIndex) {
		return "-";
	}

	@Override
	public void setAccountValue(AccountBatch account, String startDateFormat, String accountNumber) {
		super.setAccountValue(account, startDateFormat, accountNumber);

		account.setAccountNumber("-");
		account.setAccountName("-");

	}

	public void setSubAccountValue(SubAccountBatch subAccount, String startDateFormat, AccountBatch account, String accountNo) {
		super.setSubAccountValue(subAccount, startDateFormat, account, accountNo);
		subAccount.setSubAccountNo("-");

//		subAccount.setIssueDate(startDateFormat);
//		subAccount.setMatureDate(startDateFormat);
//		subAccount.setCloseDate(startDateFormat);
	}

	
	@Override
	public void setExecutionValue(ExecutionBatch execution, String dateFormat, SubAccountBatch subAccount, String externalTxNo) {
		super.setExecutionValue(execution, dateFormat, subAccount, externalTxNo);
		
		execution.setUnit(BigDecimal.ZERO);
		execution.setAmount(BigDecimal.ZERO);
		execution.setCostPerUnit(BigDecimal.ZERO);
		execution.setNetAmount(BigDecimal.ZERO);
		execution.setSubtransactiontype("8");
		execution.setLocalCostAmount(null);
		execution.setSource(Constants.SOURCE_NONBAY_DEB);
	}

	/*
	 * write object to file
	 * (non-Javadoc)
	 * @see com.wmsl.core.GenBigDataCore#accToString(java.io.BufferedWriter, com.wealth.bwm.batch.impl.entity.cp.account.AccountBatch)
	 */
	@Override
	public void accToString(BufferedWriter bufferedWriter, AccountBatch account) throws IOException {
		FixedIncomeAccountBatch fixedIncomeAccount = (FixedIncomeAccountBatch)account;

//		ACCOUNTID *	FixedIncomeTYPE	
		bufferedWriter.write(prepareData(fixedIncomeAccount.getAccountId()));

		bufferedWriter.newLine();
	}

	@Override
	public void subAccToString(BufferedWriter bufferedWriter, SubAccountBatch subAccount) throws IOException {
		SubFixedIncomeAccountBatch subFixedIncomeAccount= (SubFixedIncomeAccountBatch)subAccount;
		bufferedWriter.write(prepareData(subFixedIncomeAccount.getSubAccountId()));
		bufferedWriter.newLine();

	}

	@Override
	public void subOutstandingToString(BufferedWriter bufferedWriter, OutstandingBatch outstanding) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void subExecutionToString(BufferedWriter bufferedWriter, ExecutionBatch execution) throws IOException {
		FixedIncomeExecutionBatch fixedIncomeExecution = (FixedIncomeExecutionBatch)execution;
		
		bufferedWriter.write(prepareData(fixedIncomeExecution.getExecutionId()));
		bufferedWriter.write(COMMA_STRING + COMMA_STRING + COMMA_STRING + COMMA_STRING);
		bufferedWriter.write(COMMA_STRING + COMMA_STRING + COMMA_STRING);
		
		bufferedWriter.newLine();

	}

	/*
	 * Get Object Account
	 * (non-Javadoc)
	 * @see com.wmsl.core.GenBigDataCore#getAccount()
	 */
	@Override
	public AccountBatch getAccount() {
		FixedIncomeAccountBatch FixedIncomeAccount = new FixedIncomeAccountBatch();
		FixedIncomeAccount.setSource(Constants.SOURCE_NONBAY_DEB);
		return FixedIncomeAccount;
	}

	@Override
	public SubAccountBatch getSubAccount() {
		return new SubFixedIncomeAccountBatch();
	}
	@Override
	public OutstandingBatch getOutstanding() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ExecutionBatch getExecution() {
		return new FixedIncomeExecutionBatch();
	}


	/*
	 * get Dir
	 * (non-Javadoc)
	 * @see com.wmsl.core.GenBigDataCore#getDir(java.lang.String)
	 * 
	 * 
	 */
	@Override
	public String getDir(String dir) {
		return Constants.DIR_FIXED + dir;
	}
	@Override
	public String getFilenameAcc() {
		return Constants.FILE_NAME_FIXED_ACC + getStopDate().get(Calendar.YEAR);
	}

	@Override
	public String getFilenameSubAcc() {
		return Constants.FILE_NAME_FIXED_SUBACC + getStopDate().get(Calendar.YEAR);
	}

	@Override
	public String getFilenamePos() {
		return Constants.FILE_NAME_FIXED_POS + getStopDate().get(Calendar.YEAR);
	}

	@Override
	public String getFilenameTx() {
		return Constants.FILE_NAME_FIXED_TX + getStopDate().get(Calendar.YEAR);
	}
	
	@Override
	public String getFilenameAccount() {
		return Constants.FILE_NAME_ACCOUNT_FIXED + getStopDate().get(Calendar.YEAR);
	}

	@Override
	public String getFilenameSubAccount() {
		return Constants.FILE_NAME_SUBACCOUNT_FIXED + getStopDate().get(Calendar.YEAR);
	}

	@Override
	public String getFilenameExecution(){
		return Constants.FILE_NAME_EXECUTION_FIXED + getStopDate().get(Calendar.YEAR);
	}
	
	@Override
	public String getFilenameOutstanding() {
		return Constants.FILE_NAME_OUTSTANDING_FIXED + getStopDate().get(Calendar.YEAR);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubAccountBatch> getSubAccountDB() throws InfoEntityServiceException, ServerEntityServiceException {
		
//		Integer dataFrom = getDataFrom();
//		Integer dataTo = getDataTo();
		List<SubBankAccount> subBankAccounts = new ArrayList<SubBankAccount>();
//		if(dataFrom == null || dataTo == null){
//			subBankAccounts = subBankAccountDao.getObjectList();
//		} else {
//			subBankAccounts = subBankAccountDao.getObjectList(dataFrom, dataTo , true, false);
//		}

		List<SubAccountBatch> subBankAccountBatchs = new ArrayList<SubAccountBatch>();
		for (SubBankAccount subBankAccount : subBankAccounts) {
			SubBankAccountBatch subBankAccountBatch = new SubBankAccountBatch();
			subBankAccountBatch.setSubAccountId(subBankAccount.getSubAccountId());
			subBankAccountBatchs.add(subBankAccountBatch);
		}
		
		List<? extends SubAccountBatch> subAccounts = subBankAccountBatchs;
		return (List<SubAccountBatch>) subAccounts;
//		List<? extends SubAccountBatch> subAccounts = subBankAccountBatchs;
//		return (List<SubAccountBatch>) subAccounts;
	}
	
}
