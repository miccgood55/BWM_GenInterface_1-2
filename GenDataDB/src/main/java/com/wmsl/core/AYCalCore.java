package com.wmsl.core;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Component;

import com.wealth.bwm.batch.impl.entity.cp.account.AccountBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.CreditloanAccountBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.SubAccountBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.SubBankAccountBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.SubCreditLoanAccountBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.execution.ExecutionBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.outstanding.CreditLoanOutstandingBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.outstanding.OutstandingBatch;
import com.wealth.bwm.impl.entity.cp.account.SubBankAccount;
import com.wealth.bwm.impl.entity.cp.account.outstanding.CreditLoanOutstanding;
import com.wealth.exception.dao.InfoEntityServiceException;
import com.wealth.exception.dao.ServerEntityServiceException;
import com.wmsl.Constants;

@Component
public class AYCalCore extends GenBigDataInstrumentsCore{


	private static final BigDecimal MARKETVALUE = new BigDecimal(16733.58).setScale(4, RoundingMode.HALF_UP);

	private static final BigDecimal INTERESTRATE = BigDecimal.ONE.setScale(4, RoundingMode.HALF_UP);
	private static final BigDecimal FACEVALUE = new BigDecimal(250000).setScale(4, RoundingMode.HALF_UP);
	private static final BigDecimal INSTALLMENTAMONT = new BigDecimal(4462.62).setScale(4, RoundingMode.HALF_UP);
	
	/*
	 * override to modifile
	 * (non-Javadoc)
	 * @see com.wmsl.core.GenBigDataCore#getAccountNumber(java.lang.String, int)
	 */
	
	private long accountNo = 1000000000000000L; 
	private String prefixAccountName = "ACCOUNT_NAME_AYCAL_";
	
	@Override
	public String getAccountNumber(String cifCode, int accountIndex) {
		return String.valueOf(accountNo + accountIndex);
		
	}

	@Override
	public void setAccountValue(AccountBatch account, String startDateFormat, String accountNumber) {
		super.setAccountValue(account, startDateFormat, accountNumber);

//		accountNo

		Long accountNumberL = Long.valueOf(accountNumber);
		StringBuilder sb = new StringBuilder(accountNumber.substring(0, 4))
				.append(accountNumber.substring(5, 12))
				.append(accountNumber.substring(13, 16));
		sb.toString();
		
		account.setAccountNumber(sb.toString());
		account.setAccountName("ACCOUNT_NAME_AYCAL_" + (accountNumberL - accountNo));

		account.setCreateBy(2);
		account.setCreateByName("System");
		account.setLastUpdateBy(2);
		account.setLastUpdateByName("System");
		account.setOpenDate(startDateFormat);
		account.setCloseDate(startDateFormat);
		
		account.setSource(Constants.SOURCE_AYCAL);
		account.setAccountNameOther(sb.toString());
		
	}

	@Override
	public void setSubAccountValue(SubAccountBatch subAccount, String startDateFormat, AccountBatch account, String accountNo) {
		super.setSubAccountValue(subAccount, startDateFormat, account, accountNo);

		subAccount.setCreateBy(2);
		subAccount.setCreateByName("System");
		subAccount.setLastUpdateBy(2);
		subAccount.setLastUpdateByName("System");
		
		subAccount.setIssueDate(startDateFormat);
//		subAccount.setMatureDate(startDateFormat);
		subAccount.setCloseDate(startDateFormat);
		

		SubCreditLoanAccountBatch subCreditloanAccount= (SubCreditLoanAccountBatch)subAccount;
		
		String accountName = account.getAccountName();
		String seq = accountName.replace(prefixAccountName, "");
		
		subCreditloanAccount.setAccountNoCreditCard(String.valueOf(this.accountNo + Long.valueOf(seq)));
	}

	
	@Override
	public void setOutstandingValue(OutstandingBatch outstanding, String dateFormat, SubAccountBatch subAccount) {
		outstanding.setMarketValue(MARKETVALUE);
		outstanding.setLastUpdateBy(2);
		outstanding.setLastUpdateByName("System");
		outstanding.setMtmDate(dateFormat);
		


		CreditLoanOutstandingBatch creditLoanOutstanding = (CreditLoanOutstandingBatch)outstanding;
		creditLoanOutstanding.setDueDate(dateFormat);
		creditLoanOutstanding.setMaturityDate(dateFormat);

		creditLoanOutstanding.setInterestRate(INTERESTRATE);
		creditLoanOutstanding.setFaceValue(FACEVALUE);
		creditLoanOutstanding.setInstallmentAmount(INSTALLMENTAMONT);

		creditLoanOutstanding.setDelinquencyDesc("30 DAYS");
	}
	/*
	 * write object to file
	 * (non-Javadoc)
	 * @see com.wmsl.core.GenBigDataCore#accToString(java.io.BufferedWriter, com.wealth.bwm.batch.impl.entity.cp.account.AccountBatch)
	 */
	@Override
	public void accToString(BufferedWriter bufferedWriter, AccountBatch account) throws IOException {
		CreditloanAccountBatch creditloanAccount = (CreditloanAccountBatch)account;

		bufferedWriter.write(prepareData(creditloanAccount.getAccountId()));
		bufferedWriter.write(COMMA_STRING + COMMA_STRING);
	}

	@Override
	public void subAccToString(BufferedWriter bufferedWriter, SubAccountBatch subAccount) throws IOException {
		SubCreditLoanAccountBatch subCreditloanAccount= (SubCreditLoanAccountBatch)subAccount;
		bufferedWriter.write(prepareData(subCreditloanAccount.getSubAccountId()));
		bufferedWriter.write(COMMA_STRING + COMMA_STRING + COMMA_STRING);
		bufferedWriter.write(prepareData(subCreditloanAccount.getAccountNoCreditCard()));
	}

	@Override
	public void subOutstandingToString(BufferedWriter bufferedWriter, OutstandingBatch outstanding) throws IOException {
		CreditLoanOutstandingBatch CreditloanOutstanding = (CreditLoanOutstandingBatch)outstanding;
		
		bufferedWriter.write(prepareData(CreditloanOutstanding.getOutstandingId()));
		bufferedWriter.write(COMMA_STRING + COMMA_STRING + COMMA_STRING + COMMA_STRING + COMMA_STRING);
		
		bufferedWriter.write(prepareData(CreditloanOutstanding.getDueDate()) + COMMA_STRING);
		bufferedWriter.write(COMMA_STRING);
		bufferedWriter.write(prepareData(CreditloanOutstanding.getMaturityDate()) + COMMA_STRING);
		bufferedWriter.write(COMMA_STRING + COMMA_STRING + COMMA_STRING);
		bufferedWriter.write(prepareData(CreditloanOutstanding.getInterestRate()) + COMMA_STRING);
		bufferedWriter.write(COMMA_STRING);
		bufferedWriter.write(prepareData(CreditloanOutstanding.getFaceValue()) + COMMA_STRING);
		bufferedWriter.write(prepareData(CreditloanOutstanding.getInstallmentAmount()) + COMMA_STRING);
		bufferedWriter.write(COMMA_STRING + COMMA_STRING + COMMA_STRING + COMMA_STRING + COMMA_STRING);
		bufferedWriter.write(prepareData(CreditloanOutstanding.getDelinquencyDesc()) + COMMA_STRING);

		bufferedWriter.newLine();
	}

	@Override
	public void subExecutionToString(BufferedWriter bufferedWriter, ExecutionBatch execution) throws IOException {
		throw new UnsupportedOperationException();
	}

	
	
	/*
	 * Get Object Account
	 * (non-Javadoc)
	 * @see com.wmsl.core.GenBigDataCore#getAccount()
	 */
	@Override
	public AccountBatch getAccount() {
		CreditloanAccountBatch CreditloanAccount = new CreditloanAccountBatch();
//		CreditloanAccount.setCreditloanType(1);
		return CreditloanAccount;
	}

	@Override
	public SubAccountBatch getSubAccount() {
		return new SubCreditLoanAccountBatch();
	}
	@Override
	public OutstandingBatch getOutstanding() {
		CreditLoanOutstandingBatch CreditloanOutstanding = new CreditLoanOutstandingBatch();
//		CreditloanOutstanding.setDueDate(dueDate);
		return CreditloanOutstanding;
	}

	@Override
	public ExecutionBatch getExecution() {
		throw new UnsupportedOperationException();
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
		return Constants.DIR_LIAB_AYCAL + dir;
	}
	@Override
	public String getFilenameAcc() {
		return Constants.FILE_NAME_LIAB_ACC + getStopDate().get(Calendar.YEAR);
	}

	@Override
	public String getFilenameSubAcc() {
		return Constants.FILE_NAME_LIAB_SUBACC + getStopDate().get(Calendar.YEAR);
	}

	@Override
	public String getFilenamePos() {
		return Constants.FILE_NAME_LIAB_POS + getStopDate().get(Calendar.YEAR);
	}

	@Override
	public String getFilenameTx() {
		return Constants.FILE_NAME_LIAB_TX + getStopDate().get(Calendar.YEAR);
	}
	
	@Override
	public String getFilenameAccount() {
		return Constants.FILE_NAME_ACCOUNT_LIAB + getStopDate().get(Calendar.YEAR);
	}

	@Override
	public String getFilenameSubAccount() {
		return Constants.FILE_NAME_SUBACCOUNT_LIAB + getStopDate().get(Calendar.YEAR);
	}

	@Override
	public String getFilenameExecution(){
		return Constants.FILE_NAME_EXECUTION_LIAB + getStopDate().get(Calendar.YEAR);
	}
	
	@Override
	public String getFilenameOutstanding() {
		return Constants.FILE_NAME_OUTSTANDING_LIAB + getStopDate().get(Calendar.YEAR);
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
