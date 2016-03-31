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
public class SummaryDeriCore extends GenBigDataBizCore {

	public final static BigDecimal MARKETVALUE = new BigDecimal(220560.26).setScale(4, RoundingMode.HALF_UP);

	public final static BigDecimal BEGINNING_BALANCE = new BigDecimal(236139.77).setScale(4, RoundingMode.HALF_UP);
	public final static BigDecimal REALIZED_PROFIT = new BigDecimal(703800).setScale(4, RoundingMode.HALF_UP);
	public final static BigDecimal REALIZED_LOSS = new BigDecimal(832980).setScale(4, RoundingMode.HALF_UP);
	public final static BigDecimal PREMIUM_AMOUNT_IN = BigDecimal.ZERO;
	public final static BigDecimal PREMIUM_AMOUNT_OUT = BigDecimal.ZERO;
	public final static BigDecimal DEPOSIT = new BigDecimal(100000).setScale(4, RoundingMode.HALF_UP);
	public final static BigDecimal WITHDRAWAL = BigDecimal.ZERO;
	public final static BigDecimal TOTAL_FEE_AND_CHARGES = new BigDecimal(42718.68).setScale(4, RoundingMode.HALF_UP);
	public final static BigDecimal TOTAL_INTEREST_IN = BigDecimal.ZERO;
	public final static BigDecimal TOTAL_INTEREST_OUT = BigDecimal.ZERO;
	public final static BigDecimal ENDING_BALANCE = new BigDecimal(164241.09).setScale(4, RoundingMode.HALF_UP);
	public final static BigDecimal ENDING_BALANCE_IN_THB_EQUITY = new BigDecimal(164241.09).setScale(4, RoundingMode.HALF_UP);
	public final static BigDecimal TOTAL_CASH_COLLATERAL_IN_THB = new BigDecimal(164241.09).setScale(4, RoundingMode.HALF_UP);
//	public final static BigDecimal TOTAL_NET_EQUITY = new BigDecimal(164241.09).setScale(4, RoundingMode.HALF_UP);
	public final static BigDecimal INITIAL_MARGIN_NORMAL = new BigDecimal(161500).setScale(4, RoundingMode.HALF_UP);
	public final static BigDecimal INITIAL_MARGIN_SPREAD = BigDecimal.ZERO;
	public final static BigDecimal INITIAL_MARGIN_TOTAL = new BigDecimal(161500).setScale(4, RoundingMode.HALF_UP);
	public final static BigDecimal TOTAL_NET_EQUITY_BALANCE = new BigDecimal(149081.09).setScale(4, RoundingMode.HALF_UP);
	public final static BigDecimal EXCESS_INSUFFICIENT = new BigDecimal(-12418.91).setScale(4, RoundingMode.HALF_UP);
	public final static BigDecimal MAINTENANCE_MARGIN_NORMAL = new BigDecimal(113050).setScale(4, RoundingMode.HALF_UP);
	public final static BigDecimal MAINTENANCE_MARGIN_SPREAD = BigDecimal.ZERO;
	public final static BigDecimal MAINTENANCE_MARGIN_TOTAL = new BigDecimal(113050).setScale(4, RoundingMode.HALF_UP);
	

	/*
	 * override to modifile
	 * (non-Javadoc)
	 * @see com.wmsl.core.GenBigDataCore#getAccountNumber(java.lang.String, int)
	 */
	@Override
	public String getAccountNumber(String cifCode, int accountIndex) {
		return getAccountNumber(Constants.PREFIX_DERI, cifCode, accountIndex);
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
		outstanding.setMarketValue(MARKETVALUE);
		outstanding.setAccountSubType("INVEST");
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
	}

	@Override
	public void subAccToString(BufferedWriter bufferedWriter, SubAccountBatch subAccount) throws IOException {
		SubMarginAccountBatch subMarginAccount= (SubMarginAccountBatch)subAccount;
		bufferedWriter.write(prepareData(subMarginAccount.getSubAccountId()));
	}

	@Override
	public void subOutstandingToString(BufferedWriter bufferedWriter, OutstandingBatch outstanding) throws IOException {
		MarginOutstandingBatch marginOutstanding = (MarginOutstandingBatch)outstanding;
		
//		OUTSTANDINGID *	APTRADE	ARTRADE	CASHBALANCE	
		bufferedWriter.write(prepareData(marginOutstanding.getOutstandingId()));
		bufferedWriter.write(COMMA_STRING + COMMA_STRING + COMMA_STRING + COMMA_STRING);
//		bufferedWriter.write(prepareData(marginOutstanding.getApTrade()) + COMMA_STRING);
//		bufferedWriter.write(prepareData(marginOutstanding.getArTrade()) + COMMA_STRING);
//		bufferedWriter.write(prepareData(marginOutstanding.getCashBalance()));


//		BEGINNINGBALANCE REALIZEDPROFIT	REALIZEDLOSS	PREMIUMAMOUNTIN	PREMIUMAMOUNTOUT	
//		DEPOSIT	WITHDRAWAL	TOTALFEEANDCHARGES	TOTALINTERESTIN	TOTALINTERESTOUT	
//		ENDINGBALANCE	ENDINGBALANCEINTHB	TOTALCASHCOLLATERALTHB	
		bufferedWriter.write(prepareData(marginOutstanding.getBeginningBalance()) + COMMA_STRING);
		bufferedWriter.write(prepareData(marginOutstanding.getRealizedProfit()) + COMMA_STRING);
		bufferedWriter.write(prepareData(marginOutstanding.getRealizedLoss()) + COMMA_STRING);
		bufferedWriter.write(prepareData(marginOutstanding.getPremiumAmountIn()) + COMMA_STRING);
		bufferedWriter.write(prepareData(marginOutstanding.getPremiumAmountOut()) + COMMA_STRING);
		bufferedWriter.write(prepareData(marginOutstanding.getDeposit()) + COMMA_STRING);
		bufferedWriter.write(prepareData(marginOutstanding.getWithdrawal()) + COMMA_STRING);
		bufferedWriter.write(prepareData(marginOutstanding.getTotalFeeAndCharges()) + COMMA_STRING);
		bufferedWriter.write(prepareData(marginOutstanding.getTotalInterestIn()) + COMMA_STRING);
		bufferedWriter.write(prepareData(marginOutstanding.getTotalInterestOut()) + COMMA_STRING);
		bufferedWriter.write(prepareData(marginOutstanding.getEndingBalance()) + COMMA_STRING);
		bufferedWriter.write(prepareData(marginOutstanding.getEndingBalanceInTHB()) + COMMA_STRING);
		bufferedWriter.write(prepareData(marginOutstanding.getTotalCashCollateralTHB()) + COMMA_STRING);
		

//		INITIALMARGINNORMAL	INITIALMARGINSPREAD	INITIALMARGINTOTAL	
//		TOTALNETEQUITYBALANCE	EXCESSINSUFFICIENT	MAINTENANCEMARGINNORMAL	
//		MAINTENANCEMARGINSPREAD	MAINTENANCEMARGINTOTAL	
//		LOCALCASHBALANCE CASHBALANCEEXCHANGERATE	CASHBALANCEEXCHANGEDATE
		
//		bufferedWriter.write(prepareData(marginOutstanding.getTotalNetEquity()) + COMMA_STRING);
		bufferedWriter.write(prepareData(marginOutstanding.getInitialMarginNormal()) + COMMA_STRING);
		bufferedWriter.write(prepareData(marginOutstanding.getInitialMarginSpread()) + COMMA_STRING);
		bufferedWriter.write(prepareData(marginOutstanding.getInitialMarginTotal()) + COMMA_STRING);
		bufferedWriter.write(prepareData(marginOutstanding.getTotalNetEquityBalance()) + COMMA_STRING);
		bufferedWriter.write(prepareData(marginOutstanding.getExcessInsufficient()) + COMMA_STRING);
		bufferedWriter.write(prepareData(marginOutstanding.getMaintenanceMarginNormal()) + COMMA_STRING);
		bufferedWriter.write(prepareData(marginOutstanding.getMaintenanceMarginSpread()) + COMMA_STRING);
		bufferedWriter.write(prepareData(marginOutstanding.getMaintenanceMarginTotal()) + COMMA_STRING);
		bufferedWriter.write(prepareData(marginOutstanding.getLocalCashBalance()) + COMMA_STRING);
		bufferedWriter.write(prepareData(marginOutstanding.getCashBalanceExchangeRate()) + COMMA_STRING);
		bufferedWriter.write(prepareData(marginOutstanding.getCashBalanceExchangeDate()));
		
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
		marginAccount.setSource(Constants.SOURCE_SUMMARY_DERI);
		
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
		
		marginOutstanding.setBeginningBalance(BEGINNING_BALANCE);
		marginOutstanding.setRealizedProfit(REALIZED_PROFIT);
		marginOutstanding.setRealizedLoss(REALIZED_LOSS);
		marginOutstanding.setPremiumAmountIn(PREMIUM_AMOUNT_IN);
		marginOutstanding.setPremiumAmountOut(PREMIUM_AMOUNT_OUT);
		marginOutstanding.setDeposit(DEPOSIT);
		marginOutstanding.setWithdrawal(WITHDRAWAL);
		marginOutstanding.setTotalFeeAndCharges(TOTAL_FEE_AND_CHARGES);
		marginOutstanding.setTotalInterestIn(TOTAL_INTEREST_IN);
		marginOutstanding.setTotalInterestOut(TOTAL_INTEREST_OUT);
		marginOutstanding.setEndingBalance(ENDING_BALANCE);
		
		marginOutstanding.setEndingBalanceInTHB(ENDING_BALANCE_IN_THB_EQUITY);
		marginOutstanding.setTotalCashCollateralTHB(TOTAL_CASH_COLLATERAL_IN_THB);
//		marginOutstanding.setTotalNetEquity(TOTAL_NET_EQUITY);
		marginOutstanding.setInitialMarginNormal(INITIAL_MARGIN_NORMAL);
		marginOutstanding.setInitialMarginSpread(INITIAL_MARGIN_SPREAD);
		marginOutstanding.setInitialMarginTotal(INITIAL_MARGIN_TOTAL);
		marginOutstanding.setTotalNetEquityBalance(TOTAL_NET_EQUITY_BALANCE);
		marginOutstanding.setExcessInsufficient(EXCESS_INSUFFICIENT);
		marginOutstanding.setMaintenanceMarginNormal(MAINTENANCE_MARGIN_NORMAL);
		marginOutstanding.setMaintenanceMarginSpread(MAINTENANCE_MARGIN_SPREAD);
		marginOutstanding.setMaintenanceMarginTotal(MAINTENANCE_MARGIN_TOTAL);
		
		
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
		return Constants.DIR_MARGIN_SUM_DERI + dir;
	}
	@Override
	public String getFilenameAcc() {
		return Constants.FILE_NAME_MARGIN_ACC + getStopDate().get(Calendar.YEAR);
	}

	@Override
	public String getFilenameSubAcc() {
		return Constants.FILE_NAME_MARGIN_SUBACC + getStopDate().get(Calendar.YEAR);
	}

	@Override
	public String getFilenamePos() {
		return Constants.FILE_NAME_MARGIN_POS + getStopDate().get(Calendar.YEAR);
	}

	@Override
	public String getFilenameTx() {
		return Constants.FILE_NAME_MARGIN_TX + getStopDate().get(Calendar.YEAR);
	}
	
	@Override
	public String getFilenameAccount() {
		return Constants.FILE_NAME_ACCOUNT_MARGIN + getStopDate().get(Calendar.YEAR);
	}

	@Override
	public String getFilenameSubAccount() {
		return Constants.FILE_NAME_SUBACCOUNT_MARGIN + getStopDate().get(Calendar.YEAR);
	}

	@Override
	public String getFilenameExecution(){
		return Constants.FILE_NAME_EXECUTION_MARGIN + getStopDate().get(Calendar.YEAR);
	}
	
	@Override
	public String getFilenameOutstanding() {
		return Constants.FILE_NAME_OUTSTANDING_MARGIN + getStopDate().get(Calendar.YEAR);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubAccountBatch> getSubAccountDB() throws InfoEntityServiceException, ServerEntityServiceException {
		
		List<? extends SubAccountBatch> subAccounts = new ArrayList<SubAccountBatch>();;
		return (List<SubAccountBatch>) subAccounts;
	}

}
