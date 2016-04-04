package com.wmsl.core;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Component;

import com.wealth.bwm.batch.impl.entity.cp.account.AccountBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.FixedIncomeAccountBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.SubAccountBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.SubFixedIncomeAccountBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.execution.ExecutionBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.execution.FixedIncomeExecutionBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.outstanding.OutstandingBatch;
import com.wealth.exception.dao.InfoEntityServiceException;
import com.wealth.exception.dao.ServerEntityServiceException;
import com.wmsl.Constants;
import com.wmsl.utils.GenDataDBUtils;

@Component
public class NonBayDebCore extends GenBigDataInstrumentsCore{

	private static List<Integer> LIST_ALL = new ArrayList<Integer>();
	private static final int SEND_DATE = 0;
	@Override
	public void init() {
		List<Integer> listWeek = GenDataDBUtils.getListInteger(1, 53);
		
		Calendar c = Calendar.getInstance(Locale.ENGLISH);
		int startYear = this.getStartYear();
		c.set(startYear, this.getStartMonth(), this.getStartDay());
		c.set(Calendar.DAY_OF_WEEK, SEND_DATE);
		
		for (Integer week : listWeek) {
			c.set(Calendar.WEEK_OF_YEAR, week);
			
			if(startYear == c.get(Calendar.YEAR)){
				LIST_ALL.add(c.get(Calendar.DAY_OF_YEAR));
			}
		}
	}

	@Override
	public List<Integer> getOutstandingRandom(int outstandPerSubAcc) {
		return LIST_ALL;
	}

	@Override
	public List<Integer> getExecutionRandom(int executionPerSubAcc) {
		List<Integer> listRet = new ArrayList<Integer>(LIST_ALL);
		
		Collections.shuffle(listRet);
		
		return listRet;
	}

	/*
	 * override to modifile
	 * (non-Javadoc)
	 * @see com.wmsl.core.GenBigDataCore#getAccountNumber(java.lang.String, int)
	 */
	@Override
	public String getAccountNumber(String cifCode, long accountSeq) {
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
		return Constants.DIR_NONBAY + dir;
	}
	@Override
	public String getFilenameAcc() {
		return Constants.FILE_NAME_FIXED_ACC + getStartDate().get(Calendar.YEAR);
	}

	@Override
	public String getFilenameSubAcc() {
		return Constants.FILE_NAME_FIXED_SUBACC + getStartDate().get(Calendar.YEAR);
	}

	@Override
	public String getFilenamePos() {
		return Constants.FILE_NAME_FIXED_POS;
	}

	@Override
	public String getFilenameTx() {
		return Constants.FILE_NAME_FIXED_TX + getStartDate().get(Calendar.YEAR);
	}
	
	@Override
	public String getFilenameAccount() {
		return Constants.FILE_NAME_ACCOUNT_FIXED + getStartDate().get(Calendar.YEAR);
	}

	@Override
	public String getFilenameSubAccount() {
		return Constants.FILE_NAME_SUBACCOUNT_FIXED + getStartDate().get(Calendar.YEAR);
	}

	@Override
	public String getFilenameExecution(){
		return Constants.FILE_NAME_EXECUTION_FIXED + getStartDate().get(Calendar.YEAR);
	}
	
	@Override
	public String getFilenameOutstanding() {
		return Constants.FILE_NAME_OUTSTANDING_FIXED;
	}

	@Override
	public List<SubAccountBatch> getSubAccountDB() throws InfoEntityServiceException, ServerEntityServiceException {
		return new ArrayList<SubAccountBatch>();
	}
	
}
