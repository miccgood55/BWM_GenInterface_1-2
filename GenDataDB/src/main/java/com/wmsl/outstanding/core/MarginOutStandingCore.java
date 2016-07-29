package com.wmsl.outstanding.core;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wealth.bwm.batch.impl.dao.cp.account.MarginOutstandingDao;
import com.wealth.bwm.batch.impl.entity.cp.account.outstanding.MarginOutstandingBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.outstanding.OutstandingBatch;
import com.wealth.exception.dao.InfoEntityServiceException;
import com.wealth.exception.dao.ServerEntityServiceException;
import com.wmsl.Constants;

@Component
public class MarginOutStandingCore extends GenOutStandingDataCore {

	@Autowired
	private MarginOutstandingDao marginOutstandingDao;
	public void setMarginOutstandingDao(MarginOutstandingDao marginOutstandingDao) {
		this.marginOutstandingDao = marginOutstandingDao;
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

	/* Get Dir
	 * (non-Javadoc)
	 * @see com.wmsl.core.GenBigDataCore#getDir()
	 * 
	 */
	
	@Override
	public String getDir(String dir) {
		return Constants.DIR_MARGIN_OUTSTANDING + dir;
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

	@Override
	protected List<OutstandingBatch> getOutstandingList() throws InfoEntityServiceException, ServerEntityServiceException {
		List<? extends OutstandingBatch> marginOutstandingList = marginOutstandingDao.getObjectList();
		return (List<OutstandingBatch>) marginOutstandingList;
	}

}
