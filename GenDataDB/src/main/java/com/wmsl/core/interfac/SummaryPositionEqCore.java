package com.wmsl.core.interfac;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.stereotype.Component;

import com.wmsl.bean.SummaryPosition;
import com.wmsl.bean.SummaryPositionEq;
import com.wealth.bwm.impl.entity.cp.account.MarginAccount;
import com.wmsl.Constants;

@Component
public class SummaryPositionEqCore extends SummaryPositionCore {

	public final static BigDecimal AP_TRADE = new BigDecimal(31436.83).setScale(4, RoundingMode.HALF_UP);
	public final static BigDecimal AR_TRADE = new BigDecimal(0.00).setScale(4, RoundingMode.HALF_UP);
	public final static BigDecimal TOTAL_COST = new BigDecimal(262678.58).setScale(4, RoundingMode.HALF_UP);
	public final static BigDecimal TOTAL_NET_EQUITY = new BigDecimal(220560.26).setScale(4, RoundingMode.HALF_UP);
	public final static BigDecimal UNREALIZEDGL = new BigDecimal(-42118.32).setScale(4, RoundingMode.HALF_UP);
	public final static BigDecimal CASH_BALANCE = new BigDecimal(10.64).setScale(4, RoundingMode.HALF_UP);

	@Override
	public String getFilename() {
		return Constants.FILE_NAME_EQ_POS + CURRENT_DATE_FORMAT;
	}

	@Override
	public List<MarginAccount> getMarginAccounts() {
		return coreDao.getMarginAccountByCode(Constants.PREFIX_EQ);
	}

	@Override
	public SummaryPosition getSummaryPosition() {
		return new SummaryPositionEq();
	}

	@Override
	public void writeSummaryPosition(BufferedWriter bufferedWriter, SummaryPosition summaryPosition) throws IOException {

		SummaryPositionEq summaryPositionEq = (SummaryPositionEq) summaryPosition;
		bufferedWriter.write(summaryPositionEq.getAccountNo());
		bufferedWriter.write(Constants.DEFAULT_PIPE);
		
		bufferedWriter.write(summaryPositionEq.getAsOfDate() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(summaryPositionEq.getApTrade() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(summaryPositionEq.getArTrade() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(summaryPositionEq.getTotalCost() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(summaryPositionEq.getTotalNetEquity() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(summaryPositionEq.getUnrealizedGL() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(summaryPositionEq.getCashBalance() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(summaryPositionEq.getSecurityCode() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(summaryPositionEq.getSource() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(summaryPositionEq.getIssuerCode());
		bufferedWriter.write(Constants.DEFAULT_LINE_SEPARATOR);
		
	}

	@Override
	public SummaryPosition setPositionEquity(SummaryPosition summaryPosition, String accountNo) {
		SummaryPositionEq summaryPositionEq = (SummaryPositionEq) summaryPosition;
		
		summaryPositionEq.setAccountNo(accountNo);
		summaryPositionEq.setAsOfDate(CURRENT_DATE_FORMAT);
		summaryPositionEq.setApTrade(AP_TRADE);
		summaryPositionEq.setArTrade(AR_TRADE);
		summaryPositionEq.setTotalCost(TOTAL_COST);
		summaryPositionEq.setTotalNetEquity(TOTAL_NET_EQUITY);
		summaryPositionEq.setUnrealizedGL(UNREALIZEDGL);
		summaryPositionEq.setCashBalance(CASH_BALANCE);
		summaryPositionEq.setSecurityCode("Equity");
		summaryPositionEq.setSource("KSS-Equity");
		summaryPositionEq.setIssuerCode("KSS-Equity");
		
		return summaryPositionEq;
	}

}
