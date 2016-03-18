package com.wmsl.bean;

import java.math.BigDecimal;

public class AYPosition {
	
	private String securityCode;
	private String asOfDate;
	private String accountNo;
	private BigDecimal balance;
//	private BigDecimal creditLimit;
//	private BigDecimal availableCreditLimit;
	private String paymentDueDate;
//	private BigDecimal currentDueAmount;
	private String accountName;
	private String issuerCode;
	private String cifCode;
	private String mergeCIFCode;
	private String startDate;
	private String delinquencyDesc;
	private String closeDate;
	private String agentCode;
	private String agentBranchCode;
	private String source;
	private Integer jointType;
	
	public String getSecurityCode() {
		return securityCode;
	}
	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}
	public String getAsOfDate() {
		return asOfDate;
	}
	public void setAsOfDate(String asOfDate) {
		this.asOfDate = asOfDate;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
//	public BigDecimal getCreditLimit() {
//		return creditLimit;
//	}
//	public void setCreditLimit(BigDecimal creditLimit) {
//		this.creditLimit = creditLimit;
//	}
//	public BigDecimal getAvailableCreditLimit() {
//		return availableCreditLimit;
//	}
//	public void setAvailableCreditLimit(BigDecimal availableCreditLimit) {
//		this.availableCreditLimit = availableCreditLimit;
//	}
	public String getPaymentDueDate() {
		return paymentDueDate;
	}
	public void setPaymentDueDate(String paymentDueDate) {
		this.paymentDueDate = paymentDueDate;
	}
//	public BigDecimal getCurrentDueAmount() {
//		return currentDueAmount;
//	}
//	public void setCurrentDueAmount(BigDecimal currentDueAmount) {
//		this.currentDueAmount = currentDueAmount;
//	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getIssuerCode() {
		return issuerCode;
	}
	public void setIssuerCode(String issuerCode) {
		this.issuerCode = issuerCode;
	}
	public String getCifCode() {
		return cifCode;
	}
	public void setCifCode(String cifCode) {
		this.cifCode = cifCode;
	}
	public String getMergeCIFCode() {
		return mergeCIFCode;
	}
	public void setMergeCIFCode(String mergeCIFCode) {
		this.mergeCIFCode = mergeCIFCode;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getDelinquencyDesc() {
		return delinquencyDesc;
	}
	public void setDelinquencyDesc(String delinquencyDesc) {
		this.delinquencyDesc = delinquencyDesc;
	}
	public String getCloseDate() {
		return closeDate;
	}
	public void setCloseDate(String closeDate) {
		this.closeDate = closeDate;
	}
	public String getAgentCode() {
		return agentCode;
	}
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	public String getAgentBranchCode() {
		return agentBranchCode;
	}
	public void setAgentBranchCode(String agentBranchCode) {
		this.agentBranchCode = agentBranchCode;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Integer getJointType() {
		return jointType;
	}
	public void setJointType(Integer jointType) {
		this.jointType = jointType;
	}
	
}
