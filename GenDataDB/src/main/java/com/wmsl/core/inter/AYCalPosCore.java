package com.wmsl.core.inter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import com.wmsl.bean.AYCalPos;
import com.wmsl.bean.AYPosition;
import com.wmsl.bean.dao.CustomerInfo;
import com.wealth.bwm.common.impl.entity.instrument.Instrument;
import com.wmsl.Constants;

@Component
public class AYCalPosCore extends AYPositionCore {


	private static final BigDecimal INTEREST_RATE = new BigDecimal(1.000000);
	private static final BigDecimal BALANCE = new BigDecimal(253548.20);
	private static final BigDecimal FACE_VALUE = new BigDecimal(250000.00);
	private static final BigDecimal INSTALLMENT_AMOUNT	 = new BigDecimal(4462.62);


	@Override
	public String getDir() {
		return Constants.DIR_AYCAL;
	}

	@Override
	public String getFilename() {
		return Constants.FILE_NAME_AYCAL_POS + CURRENT_DATE_FORMAT;
	}

	@Override
	public AYPosition getAYPosition() {
		return new AYCalPos();
	}

	@Override
	public List<Instrument> getInstruments() {
		return coreDao.getInstrumentBySymbol(Constants.PREFIX_AYCAL);
	}
	
	@Override
	public void writeAYPosition(BufferedWriter bufferedWriter, AYPosition ayPosition) throws IOException {
		AYCalPos ayCalPos = (AYCalPos) ayPosition;
		
		bufferedWriter.write(ayCalPos.getSecurityCode());
		bufferedWriter.write(Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCalPos.getAsOfDate() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCalPos.getAccountNo() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCalPos.getInterestRate() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCalPos.getBalance() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCalPos.getFaceValue() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCalPos.getMaturityDate() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCalPos.getPaymentDueDate() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCalPos.getStartDate() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCalPos.getInstallmentAmount() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCalPos.getAccountName());
		bufferedWriter.write(Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCalPos.getIssuerCode() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCalPos.getCifCode());
		bufferedWriter.write(Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCalPos.getMergeCIFCode() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCalPos.getDelinquencyDesc() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCalPos.getCloseDate() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCalPos.getAgentCode() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCalPos.getAgentBranchCode() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCalPos.getSource() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCalPos.getJointType());
		
		bufferedWriter.write(Constants.DEFAULT_LINE_SEPARATOR);
		
	}

	@Override
	public AYPosition setAYPositionValue(AYPosition ayPosition, CustomerInfo customerInfo, Instrument instrument,
			int seq) {
		AYCalPos ayCalPos = (AYCalPos) ayPosition;

		long accountNo = 2000000000000000L + seq; 
		
		ayCalPos.setSecurityCode(instrument.getSymbol());
		ayCalPos.setAsOfDate(CURRENT_DATE_FORMAT);
		ayCalPos.setAccountNo(String.valueOf(accountNo));
		ayCalPos.setInterestRate(INTEREST_RATE);
		ayCalPos.setBalance(BALANCE);
		ayCalPos.setFaceValue(FACE_VALUE);
		ayCalPos.setMaturityDate(CURRENT_DATE_FORMAT);
		ayCalPos.setPaymentDueDate(CURRENT_DATE_FORMAT);
		ayCalPos.setStartDate(CURRENT_DATE_FORMAT);
		ayCalPos.setInstallmentAmount(INSTALLMENT_AMOUNT);
		ayCalPos.setAccountName("ACCOUNT_NAME_" + seq);
		ayCalPos.setIssuerCode("CAL");
		ayCalPos.setCifCode(customerInfo.getCifCode());
//		ayCalPos.setMergeCIFCode(mergeCIFCode);
		ayCalPos.setDelinquencyDesc("30 DAYS");
		ayCalPos.setCloseDate(CURRENT_DATE_FORMAT);
		ayCalPos.setAgentCode("BAY");
		ayCalPos.setAgentBranchCode("00001");
		ayCalPos.setSource("AYCAL");
		ayCalPos.setJointType(2);
		

//		1	Security Code				STRING			1. Lookup : CM_INSTRUMENT[SYMBOL]
//														2. EX. HP
//		2	As Of Date					DATE			1. ระบุเป็นปี คศ. 
//														2. Ex. 20160122
//		3	Account No					STRING			EX. 2500026
//		4	Interest Rate				BIGDECIMAL		EX. 1.000000
//		5	Balance						BIGDECIMAL		EX. 253548.20
//		6	Face Value					BIGDECIMAL		EX. 250000.00
//		7	Maturity Date				DATE			1. ระบุเป็นปี คศ. 
//														2. Ex. 20160122
//		8	Payment Due Date			DATE			1. ระบุเป็นปี คศ. 
//														2. Ex. 20160122
//		9	Start Date					DATE			1. ระบุเป็นปี คศ. 
//														2. Ex. 20160122
//		10	Installment Amount			BIGDECIMAL		EX. 4462.62
//		11	Account Name				STRING			EX. นาย J
//		12	Issuer Code					STRING			1. CAL/BAY
//														2. Look up : CM_COMPANY[COMPANYCODE]
//														3. Ex.CAL
//		13	CIF Code(Layout1)			STRING			EX. 00000000000003
//		14	Merge CIF Code(Layout1)		STRING			1. ต้อง map เลือกระหว่าง layout1 หรือ  อย่างใดอย่างหนึ่งเท่านั้น
//														EX. 00000000000003
//		15	DelinquencyDesc				STRING			EX. 30 DAYS
//		16	Close Date					DATE			-
//		17	Agent Code					STRING			1. Default : ข้อมูลในตาราง CM_BANK
//														Look up : CM_BANK[BANKCODE]
//														2. EX. BAY
//		18	Agent Branch Code			STRING			1. Default : ข้อมูลในตาราง CRM_BRANCH
//														Look up : CRM_BRANCH[BANKBRANCHCODE]
//														2. EX. 00001
//		19	Source						STRING			AYCAL
//		20	Joint Type(Layout1)			INTEGER			1. Default: 2
//														2. ประเภทของบัญชี
//			
		return ayCalPos;
	}

}
