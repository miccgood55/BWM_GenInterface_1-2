package com.wmsl.bean;

import java.math.BigDecimal;

public class NonBayDebTx {
	
	private String tradeDate;
	private String settleDate;
	private String SecurityCode;
	private String cifCode;
	private String transactionNo;
	private String transactionType;
	private String subTransactionType;
	private BigDecimal costAmount;
	private BigDecimal unit;
	private BigDecimal pricePerUnit;
	private String agentCode;
	private String agentBranchCode;
	private String iCCode;
	private String status;
	private String source;
	private BigDecimal netAmount;
	private String issuerCode;
	public String getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}
	public String getSettleDate() {
		return settleDate;
	}
	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}
	public String getSecurityCode() {
		return SecurityCode;
	}
	public void setSecurityCode(String securityCode) {
		SecurityCode = securityCode;
	}
	public String getCifCode() {
		return cifCode;
	}
	public void setCifCode(String cifCode) {
		this.cifCode = cifCode;
	}
	public String getTransactionNo() {
		return transactionNo;
	}
	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getSubTransactionType() {
		return subTransactionType;
	}
	public void setSubTransactionType(String subTransactionType) {
		this.subTransactionType = subTransactionType;
	}
	public BigDecimal getCostAmount() {
		return costAmount;
	}
	public void setCostAmount(BigDecimal costAmount) {
		this.costAmount = costAmount;
	}
	public BigDecimal getUnit() {
		return unit;
	}
	public void setUnit(BigDecimal unit) {
		this.unit = unit;
	}
	public BigDecimal getPricePerUnit() {
		return pricePerUnit;
	}
	public void setPricePerUnit(BigDecimal pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
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
	public String getiCCode() {
		return iCCode;
	}
	public void setiCCode(String iCCode) {
		this.iCCode = iCCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public BigDecimal getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}
	public String getIssuerCode() {
		return issuerCode;
	}
	public void setIssuerCode(String issuerCode) {
		this.issuerCode = issuerCode;
	}
	
//	1	Trade Date	DATE						1. ระบุเป็นปี คศ. 
//												2. Ex. 20061215
//	2	Settle Date	DATE						1. ระบุเป็นปี คศ. 
//												2. Ex. 20061218
//	3	Security Code	STRING					1. ใช้ในการหา Sub Account ใช้คู่กับ Issuer Code
//												2. Ex. BE096F
//												3. Lookup : CM_INSTRUMENT[SYMBOL]
//	4	CIF Code	STRING						1. ใช้ในการหา Account
//												2. EX. 00000001114404
//	5	Transaction No	STRING	 				EX. TRUE1A370155000008
//	6	Transaction Type	STRING				1. ระบบเก็บ BUY = 1
//												2. กรณีที่รายการซื้อตั๋ว ให้ส่งค่าเป็น BUY 
//												3. EX.BUY
//	7	Sub Transaction Type	STRING			1. Interest = 6, Investment = 8
//												2. ระบบรับเป็น ID
//												3. EX.8
//	8	Cost Amount	BIGDECIMAL					1. กรณีซื้อ แต่การขาย ไม่ใช่ต้นทุน แต่เป็นเงินที่ขาย
//												2. EX. 000000000002000000.00
//	9	Unit	BIGDECIMAL						EX. 0000000000002000.000000
//	10	Price/Unit	BIGDECIMAL					EX. 0000000000001000.000000|
//	11	Agent Code	STRING						1. Look up : CM_BANK[BANKCODE]
//												2. EX. BAY
//	12	Agent Branch Code	STRING				1. Ex. 00155
//												2. Look up : CRM_BRANCH[BANKBRANCHCODE]
//	13	IC Code	STRING							EX. 00000000000000012345
//	14	Status	STRING							1. A=Active transaction, D=Deleted transaction
//												2. กรณีไม่ระบุ Status จะถือว่า Active เสมอ
//												3. กรณีที่ระบุ Status  = D และ มาพร้อมกับ Transaction No. ระบบจะไปทำการค้นหาว่าเ
//												คยมี  Transaction No. ในระบบรึยัง ถ้ามี ระบบจะทำการ Update Status รายการเดิมเป็น D 
//												4. กรณีที่ระบุ Status  = D และ มาพร้อมกับ Transaction No. ระบบจะไปทำการค้นหาว่าเคยมี  Transaction No. ในระบบรึยัง ถ้าไม่มี ระบบจะสร้างรายการใหม่ให้ตามข้อมูลที่ส่งมา โดยระบุ Status = D
//												5. EX.A
//	15	Source	STRING							1. Default : NONBAY-DEBENTURE
//												2. ใช้ในการหา Account เนื่องจากเลขบัญชีของแต่ละ Source อาจมีเลขซ้ำกันจึงต้องใช้ข้อมูล Source เป็นเงื่อนไขค้นหาเพิ่มเติม
//	16	Net Amount	BIGDECIMAL					Default : 0
//	17	Issuer Code	STRING						1. ใช้ในการหา Sub Account ใช้คู่กับ Security Code
//												2.Default : BAY-Other

}
