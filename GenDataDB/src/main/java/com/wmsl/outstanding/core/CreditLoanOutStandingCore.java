package com.wmsl.outstanding.core;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wealth.bwm.batch.impl.entity.cp.account.outstanding.CreditLoanOutstandingBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.outstanding.OutstandingBatch;
import com.wealth.exception.dao.InfoEntityServiceException;
import com.wealth.exception.dao.ServerEntityServiceException;
import com.wmsl.Constants;
import com.wealth.bwm.batch.impl.dao.cp.account.CreditLoanOutstandingDao;

@Component
public class CreditLoanOutStandingCore extends GenOutStandingDataCore{

	@Autowired
	private CreditLoanOutstandingDao creditLoanOutstandingDao;
	public void setCreditLoanOutstandingDao(CreditLoanOutstandingDao creditLoanOutstandingDao) {
		this.creditLoanOutstandingDao = creditLoanOutstandingDao;
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
		bufferedWriter.write(prepareData(CreditloanOutstanding.getDelinquencyDesc()));

		bufferedWriter.newLine();
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
		return Constants.DIR_CREDITLOAN_OUTSTANDING + dir;
	}
	@Override
	public String getFilenameAcc() {
		return Constants.FILE_NAME_LIAB_ACC + getStartDate().get(Calendar.YEAR);
	}

	@Override
	public String getFilenameSubAcc() {
		return Constants.FILE_NAME_LIAB_SUBACC + getStartDate().get(Calendar.YEAR);
	}

	@Override
	public String getFilenamePos() {
		return Constants.FILE_NAME_LIAB_POS;
	}

	@Override
	public String getFilenameTx() {
		return Constants.FILE_NAME_LIAB_TX + getStartDate().get(Calendar.YEAR);
	}
	
	@Override
	public String getFilenameAccount() {
		return Constants.FILE_NAME_ACCOUNT_LIAB + getStartDate().get(Calendar.YEAR);
	}

	@Override
	public String getFilenameSubAccount() {
		return Constants.FILE_NAME_SUBACCOUNT_LIAB + getStartDate().get(Calendar.YEAR);
	}

	@Override
	public String getFilenameExecution(){
		return Constants.FILE_NAME_EXECUTION_LIAB + getStartDate().get(Calendar.YEAR);
	}
	
	@Override
	public String getFilenameOutstanding() {
		return Constants.FILE_NAME_OUTSTANDING_LIAB;
	}

	@Override
	protected List<OutstandingBatch> getOutstandingList() throws InfoEntityServiceException, ServerEntityServiceException {
		List<? extends OutstandingBatch> creditLoanOutstandingList = creditLoanOutstandingDao.getObjectList();
		return (List<OutstandingBatch>) creditLoanOutstandingList;
	}
	
}
