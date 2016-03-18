package com.wmsl.core.inter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.stereotype.Component;

import com.wmsl.bean.SummaryPosition;
import com.wmsl.bean.SummaryPositionDeri;
import com.wealth.bwm.impl.entity.cp.account.MarginAccount;
import com.wmsl.Constants;

@Component
public class SummaryPositionDeriCore extends SummaryPositionCore {
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
	public final static BigDecimal TOTAL_NET_EQUITY = new BigDecimal(164241.09).setScale(4, RoundingMode.HALF_UP);
	public final static BigDecimal INITIAL_MARGIN_NORMAL = new BigDecimal(161500).setScale(4, RoundingMode.HALF_UP);
	public final static BigDecimal INITIAL_MARGIN_SPREAD = BigDecimal.ZERO;
	public final static BigDecimal INITIAL_MARGIN_TOTAL = new BigDecimal(161500).setScale(4, RoundingMode.HALF_UP);
	public final static BigDecimal TOTAL_NET_EQUITY_BALANCE = new BigDecimal(149081.09).setScale(4, RoundingMode.HALF_UP);
	public final static BigDecimal EXCESS_INSUFFICIENT = new BigDecimal(-12418.91).setScale(4, RoundingMode.HALF_UP);
	public final static BigDecimal MAINTENANCE_MARGIN_NORMAL = new BigDecimal(113050).setScale(4, RoundingMode.HALF_UP);
	public final static BigDecimal MAINTENANCE_MARGIN_SPREAD = BigDecimal.ZERO;
	public final static BigDecimal MAINTENANCE_MARGIN_TOTAL = new BigDecimal(113050).setScale(4, RoundingMode.HALF_UP);
	
	@Override
	public String getFilename() {
		return Constants.FILE_NAME_DERI_POS + CURRENT_DATE_FORMAT;
	}

	@Override
	public List<MarginAccount> getMarginAccounts() {
		return coreDao.getMarginAccountByCode(Constants.PREFIX_DERI);
	}

	@Override
	public SummaryPosition getSummaryPosition() {
		return new SummaryPositionDeri();
	}

	@Override
	public void writeSummaryPosition(BufferedWriter bufferedWriter, SummaryPosition summaryPosition) throws IOException {

		SummaryPositionDeri summaryPositionDeri = (SummaryPositionDeri) summaryPosition;
		bufferedWriter.write(summaryPositionDeri.getAccountNo());
		bufferedWriter.write(Constants.DEFAULT_PIPE);
		
		bufferedWriter.write(summaryPositionDeri.getAsOfDate() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(summaryPositionDeri.getBeginningBalance() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(summaryPositionDeri.getRealizedProfit() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(summaryPositionDeri.getRealizedLoss() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(summaryPositionDeri.getPremiumAmountIn() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(summaryPositionDeri.getPremiumAmountOut() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(summaryPositionDeri.getDeposit() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(summaryPositionDeri.getWithdrawal() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(summaryPositionDeri.getTotalFeeAndCharges() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(summaryPositionDeri.getTotalInterestIn() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(summaryPositionDeri.getTotalInterestOut() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(summaryPositionDeri.getEndingBalance() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(summaryPositionDeri.getEndingBalanceInTHBEquity() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(summaryPositionDeri.getTotalCashCollateralInTHB() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(summaryPositionDeri.getTotalNetEquity() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(summaryPositionDeri.getInitialMarginNormal() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(summaryPositionDeri.getInitialMarginSpread() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(summaryPositionDeri.getInitialMarginTotal() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(summaryPositionDeri.getTotalNetEquityBalance() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(summaryPositionDeri.getExcessInsufficient() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(summaryPositionDeri.getMaintenanceMarginNormal() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(summaryPositionDeri.getMaintenanceMarginSpread() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(summaryPositionDeri.getMaintenanceMarginTotal() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(summaryPositionDeri.getSecurityCode() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(summaryPositionDeri.getSource() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(summaryPositionDeri.getIssuerCode());

		bufferedWriter.write(Constants.DEFAULT_LINE_SEPARATOR);
		
	}

	@Override
	public SummaryPosition setPositionEquity(SummaryPosition summaryPosition, String accountNo) {
		SummaryPositionDeri summaryPositionDeri = (SummaryPositionDeri) summaryPosition;
		

		summaryPositionDeri.setAccountNo(accountNo);
		summaryPositionDeri.setAsOfDate(CURRENT_DATE_FORMAT);
		summaryPositionDeri.setBeginningBalance(BEGINNING_BALANCE);
		summaryPositionDeri.setRealizedProfit(REALIZED_PROFIT);
		summaryPositionDeri.setRealizedLoss(REALIZED_LOSS);
		summaryPositionDeri.setPremiumAmountIn(PREMIUM_AMOUNT_IN);
		summaryPositionDeri.setPremiumAmountOut(PREMIUM_AMOUNT_OUT);
		summaryPositionDeri.setDeposit(DEPOSIT);
		summaryPositionDeri.setWithdrawal(WITHDRAWAL);
		summaryPositionDeri.setTotalFeeAndCharges(TOTAL_FEE_AND_CHARGES);
		summaryPositionDeri.setTotalInterestIn(TOTAL_INTEREST_IN);
		summaryPositionDeri.setTotalInterestOut(TOTAL_INTEREST_OUT);
		summaryPositionDeri.setEndingBalance(ENDING_BALANCE);
		
		summaryPositionDeri.setEndingBalanceInTHBEquity(ENDING_BALANCE_IN_THB_EQUITY);
		summaryPositionDeri.setTotalCashCollateralInTHB(TOTAL_CASH_COLLATERAL_IN_THB);
		summaryPositionDeri.setTotalNetEquity(TOTAL_NET_EQUITY);
		summaryPositionDeri.setInitialMarginNormal(INITIAL_MARGIN_NORMAL);
		summaryPositionDeri.setInitialMarginSpread(INITIAL_MARGIN_SPREAD);
		summaryPositionDeri.setInitialMarginTotal(INITIAL_MARGIN_TOTAL);
		summaryPositionDeri.setTotalNetEquityBalance(TOTAL_NET_EQUITY_BALANCE);
		summaryPositionDeri.setExcessInsufficient(EXCESS_INSUFFICIENT);
		summaryPositionDeri.setMaintenanceMarginNormal(MAINTENANCE_MARGIN_NORMAL);
		summaryPositionDeri.setMaintenanceMarginSpread(MAINTENANCE_MARGIN_SPREAD);
		summaryPositionDeri.setMaintenanceMarginTotal(MAINTENANCE_MARGIN_TOTAL);
		
		summaryPositionDeri.setSecurityCode("Derivative");
		summaryPositionDeri.setSource("KSS-Derivative");
		summaryPositionDeri.setIssuerCode("KSS-Derivative");
		
		return summaryPositionDeri;
	}

}
