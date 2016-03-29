package com.wmsl.core.inter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.stereotype.Component;

import com.wmsl.bean.AYCapPos;
import com.wmsl.bean.AYPosition;
import com.wmsl.bean.dao.CustomerInfo;
import com.wealth.bwm.common.impl.entity.instrument.Instrument;
import com.wmsl.Constants;

@Component
public class AYCapPosCore extends AYPositionCore {

	private static final BigDecimal BALANCE = new BigDecimal(16733.58).setScale(4, RoundingMode.HALF_UP);
	private static final BigDecimal CREDIT_LIMIT = new BigDecimal(30000.00).setScale(4, RoundingMode.HALF_UP);
	private static final BigDecimal AVAILABLE_CREDIT_LIMIT = new BigDecimal(13266.42).setScale(4, RoundingMode.HALF_UP);
	private static final BigDecimal CURRENT_DUE_AMOUNT = new BigDecimal(16733.58).setScale(4, RoundingMode.HALF_UP);


	@Override
	public String getDir() {
		return Constants.DIR_AYCAP;
	}

	@Override
	public String getFilename() {
		return Constants.FILE_NAME_AYCAP_POS + CURRENT_DATE_FORMAT;
	}

	@Override
	public AYPosition getAYPosition() {
		return new AYCapPos();
	}

	@Override
	public List<Instrument> getInstruments() {
		return coreDao.getInstrumentBySymbol(Constants.PREFIX_AYCAP);
	}
	
	@Override
	public void writeAYPosition(BufferedWriter bufferedWriter, AYPosition ayPosition) throws IOException {
		AYCapPos ayCapPos = (AYCapPos) ayPosition;
		
		bufferedWriter.write(ayCapPos.getSecurityCode());
		bufferedWriter.write(Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCapPos.getAsOfDate() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCapPos.getAccountNo());
		bufferedWriter.write(Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCapPos.getBalance() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCapPos.getCreditLimit() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCapPos.getAvailableCreditLimit() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCapPos.getPaymentDueDate() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCapPos.getCurrentDueAmount() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCapPos.getAccountName());
		bufferedWriter.write(Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCapPos.getIssuerCode() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCapPos.getCifCode());
		bufferedWriter.write(Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCapPos.getMergeCIFCode() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCapPos.getStartDate() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCapPos.getDelinquencyDesc() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCapPos.getCloseDate() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCapPos.getAgentCode() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCapPos.getAgentBranchCode() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCapPos.getSource() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCapPos.getJointType());

		bufferedWriter.write(Constants.DEFAULT_LINE_SEPARATOR);
		
	}

	@Override
	public AYPosition setAYPositionValue(AYPosition ayPosition, CustomerInfo customerInfo, Instrument instrument,
			int seq) {
		AYCapPos ayCapPos = (AYCapPos) ayPosition;

		long accountNo = 1000000000000000L + seq; 
		
		ayCapPos.setSecurityCode(instrument.getSymbol());
		ayCapPos.setAsOfDate(CURRENT_DATE_FORMAT);
		ayCapPos.setAccountNo(String.valueOf(accountNo));
		ayCapPos.setBalance(BALANCE);
		ayCapPos.setCreditLimit(CREDIT_LIMIT);
		ayCapPos.setAvailableCreditLimit(AVAILABLE_CREDIT_LIMIT);
		ayCapPos.setPaymentDueDate(CURRENT_DATE_FORMAT);
		ayCapPos.setCurrentDueAmount(CURRENT_DUE_AMOUNT);
		ayCapPos.setAccountName("ACCOUNT_NAME_AYCAP_" + seq);
		ayCapPos.setIssuerCode("TCS");
		ayCapPos.setCifCode(customerInfo.getCifCode());
		ayCapPos.setMergeCIFCode("");
		ayCapPos.setStartDate(CURRENT_DATE_FORMAT);
		ayCapPos.setDelinquencyDesc(CURRENT_DATE_FORMAT);
		ayCapPos.setCloseDate(CURRENT_DATE_FORMAT);
		ayCapPos.setAgentCode("BAY");
		ayCapPos.setAgentBranchCode("00001");
		ayCapPos.setSource("AYCAP");
		ayCapPos.setJointType(2);
		
		return ayCapPos;
//		Security Code							STRING			1. Lookup : CM_INSTRUMENT[SYMBOL]
//																2. EX.LG
//		As Of Date								DATE			1. ระบุเป็นปี คศ. 
//																2. Ex. 20160122
//		Account No								STRING			1.จะทำการนำข้อมูลไปเขียนใน Account No Credit Card และ Blur Data ด้วยรูปแบบ 1234xxxxxxxx5678
//																2. EX. 4093380109602751
//		Balance									BIGDECIMAL		Ex. 16733.58
//		Credit Limit							BIGDECIMAL		EX. 30000.00
//		Available Credit Limit					BIGDECIMAL		EX. 13266.42
//		Payment Due Date						DATE			ระบุเป็นปี คศ. 
//																Ex. 20160122
//		Current Due Amount						BIGDECIMAL		Ex. 16733.58
//		Account Name							STRING			EX. นาย J
//		Issuer Code								STRING			1. KCC,AYCAP,TCS,GCS
//																2. EX.TCS
//		CIF Code(Layout1)						STRING			EX. 00000000000003
//		Merge CIF Code(Layout1)					STRING			EX. 00000000000004
//		Start Date								DATE			1. ระบุเป็นปี คศ. 
//																2. Ex. 20160122
//		DelinquencyDesc							STRING			EX. CURRENT DUE
//		Close Date								DATE	
//		Agent Code								STRING			1. Default : ข้อมูลในตาราง CM_BANK
//																2. Look up : CM_BANK[BANKCODE]
//																3. EX.BAY
//		Agent Branch Code						STRING			1. Default : ข้อมูลในตาราง CRM_BRANCH
//																2. Look up : CRM_BRANCH[BANKBRANCHCODE]
//																3.EX. 00001
//		Source									STRING			Default : AYCAP
//		Joint Type(Layout1)						INTEGER			1. Default: 2
//																2. ประเภทของบัญชี
//																2=Single Account, 1=Joint Account-Primary, 0=Joint Account-Secondary
		
	}

}
