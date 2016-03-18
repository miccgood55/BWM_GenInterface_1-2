package com.wmsl.bean;

import java.math.BigDecimal;

public class AYCapPos extends AYPosition {

	private BigDecimal creditLimit;
	private BigDecimal availableCreditLimit;
	private BigDecimal currentDueAmount;
	
	public BigDecimal getCreditLimit() {
		return creditLimit;
	}
	public void setCreditLimit(BigDecimal creditLimit) {
		this.creditLimit = creditLimit;
	}
	public BigDecimal getAvailableCreditLimit() {
		return availableCreditLimit;
	}
	public void setAvailableCreditLimit(BigDecimal availableCreditLimit) {
		this.availableCreditLimit = availableCreditLimit;
	}
	public BigDecimal getCurrentDueAmount() {
		return currentDueAmount;
	}
	public void setCurrentDueAmount(BigDecimal currentDueAmount) {
		this.currentDueAmount = currentDueAmount;
	}

//	Security Code							STRING			1. Lookup : CM_INSTRUMENT[SYMBOL]
//															2. EX.LG
//	As Of Date								DATE			1. ระบุเป็นปี คศ. 
//															2. Ex. 20160122
//	Account No								STRING			1.จะทำการนำข้อมูลไปเขียนใน Account No Credit Card และ Blur Data ด้วยรูปแบบ 1234xxxxxxxx5678
//															2. EX. 4093380109602751
//	Balance									BIGDECIMAL		Ex. 16733.58
//	Credit Limit							BIGDECIMAL		EX. 30000.00
//	Available Credit Limit					BIGDECIMAL		EX. 13266.42
//	Payment Due Date						DATE			ระบุเป็นปี คศ. 
//															Ex. 20160122
//	Current Due Amount						BIGDECIMAL		Ex. 16733.58
//	Account Name							STRING			EX. นาย J
//	Issuer Code								STRING			1. KCC,AYCAP,TCS,GCS
//															2. EX.TCS
//	CIF Code(Layout1)						STRING			EX. 00000000000003
//	Merge CIF Code(Layout1)					STRING			EX. 00000000000004
//	Start Date								DATE			1. ระบุเป็นปี คศ. 
//															2. Ex. 20160122
//	DelinquencyDesc							STRING			EX. CURRENT DUE
//	Close Date								DATE	
//	Agent Code								STRING			1. Default : ข้อมูลในตาราง CM_BANK
//															2. Look up : CM_BANK[BANKCODE]
//															3. EX.BAY
//	Agent Branch Code						STRING			1. Default : ข้อมูลในตาราง CRM_BRANCH
//															2. Look up : CRM_BRANCH[BANKBRANCHCODE]
//															3.EX. 00001
//	Source									STRING			Default : AYCAP
//	Joint Type(Layout1)						INTEGER			1. Default: 2
//															2. ประเภทของบัญชี
//															2=Single Account, 1=Joint Account-Primary, 0=Joint Account-Secondary

}
