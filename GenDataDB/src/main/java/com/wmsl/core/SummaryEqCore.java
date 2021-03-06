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
import com.wealth.bwm.batch.impl.entity.cp.account.MarginAccountBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.SubAccountBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.SubMarginAccountBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.execution.ExecutionBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.outstanding.MarginOutstandingBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.outstanding.OutstandingBatch;
import com.wealth.exception.dao.InfoEntityServiceException;
import com.wealth.exception.dao.ServerEntityServiceException;
import com.wmsl.Constants;

@Component
public class SummaryEqCore extends GenBigDataBizCore {

	private static final BigDecimal APTRADE = new BigDecimal(31436.83).setScale(4, RoundingMode.HALF_UP);
	private static final BigDecimal ARTRADE = BigDecimal.ZERO.setScale(4, RoundingMode.HALF_UP);
	private static final BigDecimal CASHBALANCE = new BigDecimal(10.64).setScale(4, RoundingMode.HALF_UP);

	public final static BigDecimal COSTVALUE = new BigDecimal(262678.58).setScale(4, RoundingMode.HALF_UP);
	public final static BigDecimal MARKETVALUE = new BigDecimal(220560.26).setScale(4, RoundingMode.HALF_UP);
	public final static BigDecimal UNREALIZEDGL = new BigDecimal(-42118.32).setScale(4, RoundingMode.HALF_UP);

	/*
	 * override to modifile
	 * (non-Javadoc)
	 * @see com.wmsl.core.GenBigDataCore#getAccountNumber(java.lang.String, int)
	 */
	@Override
	public String getAccountNumber(String cifCode, long accountSeq) {
		return getAccountNumber(Constants.PREFIX_EQ, cifCode, accountSeq);
	}

	public void setSubAccountValue(SubAccountBatch subAccount, String startDateFormat, AccountBatch account, String accountNo) {
		super.setSubAccountValue(subAccount, startDateFormat, account, accountNo);
		subAccount.setIssueDate(startDateFormat);
		subAccount.setMatureDate(startDateFormat);
		subAccount.setCloseDate(startDateFormat);
	}

	
	
	@Override
	public void setOutstandingValue(OutstandingBatch outstanding, String dateFormat, SubAccountBatch subAccount) {
		super.setOutstandingValue(outstanding, dateFormat, subAccount);
		outstanding.setCostValue(COSTVALUE);
		outstanding.setMarketValue(MARKETVALUE);
		outstanding.setAccountSubType("INVEST");
		outstanding.setUnrealizedGL(UNREALIZEDGL);

	}

	/*
	 * write object to file
	 * (non-Javadoc)
	 * @see com.wmsl.core.GenBigDataCore#accToString(java.io.BufferedWriter, com.wealth.bwm.batch.impl.entity.cp.account.AccountBatch)
	 */
	@Override
	public void accToString(BufferedWriter bufferedWriter, AccountBatch account) throws IOException {
		MarginAccountBatch marginAccount = (MarginAccountBatch)account;

//		ACCOUNTID *	MARGINTYPE	
		bufferedWriter.write(prepareData(marginAccount.getAccountId()));bufferedWriter.write(COMMA_STRING);
		bufferedWriter.write(prepareData(marginAccount.getMarginType()));

		bufferedWriter.newLine();
	}

	@Override
	public void subAccToString(BufferedWriter bufferedWriter, SubAccountBatch subAccount) throws IOException {
		SubMarginAccountBatch subMarginAccount= (SubMarginAccountBatch)subAccount;
		bufferedWriter.write(prepareData(subMarginAccount.getSubAccountId()));

		bufferedWriter.newLine();
	}

	@Override
	public void subOutstandingToString(BufferedWriter bufferedWriter, OutstandingBatch outstanding) throws IOException {
		MarginOutstandingBatch marginOutstanding = (MarginOutstandingBatch)outstanding;
//		OUTSTANDINGID *	APTRADE	ARTRADE	CASHBALANCE	
//		BEGINNINGBALANCE	
//		REALIZEDPROFIT	REALIZEDLOSS	PREMIUMAMOUNTIN	PREMIUMAMOUNTOUT	
//		DEPOSIT	WITHDRAWAL	TOTALFEEANDCHARGES	TOTALINTERESTIN	TOTALINTERESTOUT	
//		ENDINGBALANCE	ENDINGBALANCEINTHB	TOTALCASHCOLLATERALTHB	
//		INITIALMARGINNORMAL	INITIALMARGINSPREAD	INITIALMARGINTOTAL	
//		TOTALNETEQUITYBALANCE	EXCESSINSUFFICIENT	MAINTENANCEMARGINNORMAL	
//		MAINTENANCEMARGINSPREAD	MAINTENANCEMARGINTOTAL	LOCALCASHBALANCE	
//		CASHBALANCEEXCHANGERATE	CASHBALANCEEXCHANGEDATE
//		215011	31436.83	0	10.64
		
		bufferedWriter.write(prepareData(marginOutstanding.getOutstandingId()));bufferedWriter.write(COMMA_STRING);
		bufferedWriter.write(prepareData(marginOutstanding.getApTrade()) + COMMA_STRING);
		bufferedWriter.write(prepareData(marginOutstanding.getArTrade()) + COMMA_STRING);
		bufferedWriter.write(prepareData(marginOutstanding.getCashBalance()));


		bufferedWriter.write(COMMA_STRING + COMMA_STRING + COMMA_STRING);
		bufferedWriter.write(COMMA_STRING + COMMA_STRING + COMMA_STRING);
		bufferedWriter.write(COMMA_STRING + COMMA_STRING + COMMA_STRING);
		bufferedWriter.write(COMMA_STRING + COMMA_STRING + COMMA_STRING);
		bufferedWriter.write(COMMA_STRING + COMMA_STRING + COMMA_STRING);
		bufferedWriter.write(COMMA_STRING + COMMA_STRING + COMMA_STRING);
		bufferedWriter.write(COMMA_STRING + COMMA_STRING + COMMA_STRING);
		bufferedWriter.write(COMMA_STRING + COMMA_STRING + COMMA_STRING);

		bufferedWriter.newLine();
//		bufferedWriter.flush();
	}

	@Override
	public void subExecutionToString(BufferedWriter bufferedWriter, ExecutionBatch execution) throws IOException {
		throw new UnsupportedOperationException();
	}

	/* Get Object Account
	 * 
	 * (non-Javadoc)
	 * @see com.wmsl.core.GenBigDataCore#getAccount()
	 * 
	 */
	
	@Override
	public AccountBatch getAccount() {
		MarginAccountBatch marginAccount = new MarginAccountBatch();
		marginAccount.setSource(Constants.SOURCE_SUMMARY_ACC);
		
		marginAccount.setMarginType(1);
		return marginAccount;
	}

	@Override
	public SubAccountBatch getSubAccount() {
		return new SubMarginAccountBatch();
	}
	@Override
	public OutstandingBatch getOutstanding() {
		MarginOutstandingBatch marginOutstanding = new MarginOutstandingBatch();
		marginOutstanding.setApTrade(APTRADE);
		marginOutstanding.setArTrade(ARTRADE);
		marginOutstanding.setCashBalance(CASHBALANCE);
		return marginOutstanding;
	}

	@Override
	public ExecutionBatch getExecution() {
		throw new UnsupportedOperationException();
	}

	/* Get Dir
	 * (non-Javadoc)
	 * @see com.wmsl.core.GenBigDataCore#getDir()
	 * 
	 */
	
	@Override
	public String getDir(String dir) {
		return Constants.DIR_MARGIN_SUM_ACC + dir;
	}
	@Override
	public String getFilenameAcc() {
		return Constants.FILE_NAME_MARGIN_ACC + getStartDate().get(Calendar.YEAR);
	}

	@Override
	public String getFilenameSubAcc() {
		return Constants.FILE_NAME_MARGIN_SUBACC + getStartDate().get(Calendar.YEAR);
	}

	@Override
	public String getFilenamePos() {
		return Constants.FILE_NAME_MARGIN_POS;
	}

	@Override
	public String getFilenameTx() {
		return Constants.FILE_NAME_MARGIN_TX + getStartDate().get(Calendar.YEAR);
	}
	
	@Override
	public String getFilenameAccount() {
		return Constants.FILE_NAME_ACCOUNT_MARGIN + getStartDate().get(Calendar.YEAR);
	}

	@Override
	public String getFilenameSubAccount() {
		return Constants.FILE_NAME_SUBACCOUNT_MARGIN + getStartDate().get(Calendar.YEAR);
	}

	@Override
	public String getFilenameExecution(){
		return Constants.FILE_NAME_EXECUTION_MARGIN + getStartDate().get(Calendar.YEAR);
	}
	
	@Override
	public String getFilenameOutstanding() {
		return Constants.FILE_NAME_OUTSTANDING_MARGIN;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubAccountBatch> getSubAccountDB() throws InfoEntityServiceException, ServerEntityServiceException {
		List<? extends SubAccountBatch> subAccounts = new ArrayList<SubAccountBatch>();
		return (List<SubAccountBatch>) subAccounts;
	}

}
