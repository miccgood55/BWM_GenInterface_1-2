package com.wmsl.bean;

import java.math.BigDecimal;

public class NonBayDebMaster {
	
	private String securityCode;
	private String securityNameEN;
	private String securityNameTH;
	private String couponFrequency;
	private String currencyCode;
	private BigDecimal couponRate;
	private String securityTypeCode;
	private String issueDate;
	private String matureDate;
	private BigDecimal parValue;
	private String issuerCode;
	
	public String getSecurityCode() {
		return securityCode;
	}
	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}
	public String getSecurityNameEN() {
		return securityNameEN;
	}
	public void setSecurityNameEN(String securityNameEN) {
		this.securityNameEN = securityNameEN;
	}
	public String getSecurityNameTH() {
		return securityNameTH;
	}
	public void setSecurityNameTH(String securityNameTH) {
		this.securityNameTH = securityNameTH;
	}
	public String getCouponFrequency() {
		return couponFrequency;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public void setCouponFrequency(String couponFrequency) {
		this.couponFrequency = couponFrequency;
	}
	public BigDecimal getCouponRate() {
		return couponRate;
	}
	public void setCouponRate(BigDecimal couponRate) {
		this.couponRate = couponRate;
	}
	public String getSecurityTypeCode() {
		return securityTypeCode;
	}
	public void setSecurityTypeCode(String securityTypeCode) {
		this.securityTypeCode = securityTypeCode;
	}
	public String getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}
	public String getMatureDate() {
		return matureDate;
	}
	public void setMatureDate(String matureDate) {
		this.matureDate = matureDate;
	}
	public BigDecimal getParValue() {
		return parValue;
	}
	public void setParValue(BigDecimal parValue) {
		this.parValue = parValue;
	}
	public String getIssuerCode() {
		return issuerCode;
	}
	public void setIssuerCode(String issuerCode) {
		this.issuerCode = issuerCode;
	}
	
//	Security Code	STRING			Ex. BE096F
//	Security Name EN	STRING		1. Ex.Short-term Debenture of Bank of Ayudhya Public Company Limited No.104/2557
//									2. Source ไม่มี Name EN จึงขอส่ง  Name TH มาแทน
//	Security Name TH	STRING		Ex. หุ้นกู้ระยะสั้น ธนาคารกรุงศรีอยุธยา จำกัด (มหาชน) ครั้งที่ 104/2557
//	Coupon Frequency	STRING		1. Look up : CM_COUPONFREQUENCY[SYMBOL]
//									2. EX.0
//	Currency Code	STRING			1. Look up : CM_CURRENCY[CURRENCYCODE]
//									2. Ex. THB
//	Coupon Rate	BIGDECIMAL			EX. 000000000000.000000
//	Security Type Code	STRING		Ex . NONBAY-DEBENTURE
//	Issue Date	DATE				1. ระบุเป็นปี คศ. 
//									2. Ex. 20080627
//	Mature Date	DATE				1. ระบุเป็นปี คศ. 
//									2. Ex. 20160122
//	Par Value	BIGDECIMAL			EX. 0000000000000001.000000
//	Issuer Code	STRING				1. Look up : CM_COMPANY[COMPANYCODE]
//									2. Default : BAY-Other

	
}
