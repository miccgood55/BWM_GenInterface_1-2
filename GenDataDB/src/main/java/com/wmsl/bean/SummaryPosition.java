package com.wmsl.bean;

import java.math.BigDecimal;

public class SummaryPosition {

	private String accountNo;
	private String asOfDate;
	private BigDecimal totalNetEquity;
	private String securityCode;
	private String Source;
	private String issuerCode;
	
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getAsOfDate() {
		return asOfDate;
	}
	public void setAsOfDate(String asOfDate) {
		this.asOfDate = asOfDate;
	}
	public BigDecimal getTotalNetEquity() {
		return totalNetEquity;
	}
	public void setTotalNetEquity(BigDecimal totalNetEquity) {
		this.totalNetEquity = totalNetEquity;
	}
	public String getSecurityCode() {
		return securityCode;
	}
	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}
	public String getSource() {
		return Source;
	}
	public void setSource(String source) {
		Source = source;
	}
	public String getIssuerCode() {
		return issuerCode;
	}
	public void setIssuerCode(String issuerCode) {
		this.issuerCode = issuerCode;
	}
	
}
