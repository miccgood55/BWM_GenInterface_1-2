package com.wmsl.bean;

import java.math.BigDecimal;

public class AYCalPos extends AYPosition {
	
	private BigDecimal interestRate;
	private BigDecimal faceValue;
	private String maturityDate;
	private BigDecimal installmentAmount;
	
	public BigDecimal getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(BigDecimal interestRate) {
		this.interestRate = interestRate;
	}
	public BigDecimal getFaceValue() {
		return faceValue;
	}
	public void setFaceValue(BigDecimal faceValue) {
		this.faceValue = faceValue;
	}
	public String getMaturityDate() {
		return maturityDate;
	}
	public void setMaturityDate(String maturityDate) {
		this.maturityDate = maturityDate;
	}
	public BigDecimal getInstallmentAmount() {
		return installmentAmount;
	}
	public void setInstallmentAmount(BigDecimal installmentAmount) {
		this.installmentAmount = installmentAmount;
	}
	
//	1	Security Code				STRING			1. Lookup : CM_INSTRUMENT[SYMBOL]
//													2. EX. HP
//	2	As Of Date					DATE			1. ระบุเป็นปี คศ. 
//													2. Ex. 20160122
//	3	Account No					STRING			EX. 2500026
//	4	Interest Rate				BIGDECIMAL		EX. 1.000000
//	5	Balance						BIGDECIMAL		EX. 253548.20
//	6	Face Value					BIGDECIMAL		EX. 250000.00
//	7	Maturity Date				DATE			1. ระบุเป็นปี คศ. 
//													2. Ex. 20160122
//	8	Payment Due Date			DATE			1. ระบุเป็นปี คศ. 
//													2. Ex. 20160122
//	9	Start Date					DATE			1. ระบุเป็นปี คศ. 
//													2. Ex. 20160122
//	10	Installment Amount			BIGDECIMAL		EX. 4462.62
//	11	Account Name				STRING			EX. นาย J
//	12	Issuer Code					STRING			1. CAL/BAY
//													2. Look up : CM_COMPANY[COMPANYCODE]
//													3. Ex.CAL
//	13	CIF Code(Layout1)			STRING			EX. 00000000000003
//	14	Merge CIF Code(Layout1)		STRING			1. ต้อง map เลือกระหว่าง layout1 หรือ  อย่างใดอย่างหนึ่งเท่านั้น
//													EX. 00000000000003
//	15	DelinquencyDesc				STRING			EX. 30 DAYS
//	16	Close Date					DATE			-
//	17	Agent Code					STRING			1. Default : ข้อมูลในตาราง CM_BANK
//													Look up : CM_BANK[BANKCODE]
//													2. EX. BAY
//	18	Agent Branch Code			STRING			1. Default : ข้อมูลในตาราง CRM_BRANCH
//													Look up : CRM_BRANCH[BANKBRANCHCODE]
//													2. EX. 00001
//	19	Source						STRING			AYCAL
//	20	Joint Type(Layout1)			INTEGER			1. Default: 2
//													2. ประเภทของบัญชี
//												    2=Single Account, 1=Joint Account-Primary, 0=Joint Account-Secondary

}
