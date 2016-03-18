package com.wmsl.core.inter;

import java.io.BufferedWriter;
import java.io.IOException;

import org.springframework.stereotype.Component;

import com.wmsl.bean.AYCalMaster;
import com.wmsl.bean.AYMaster;
import com.wmsl.Constants;

@Component
public class AYCalMasterCore extends AYMasterCore{

	@Override
	public String getDir() {
		return Constants.DIR_AYCAL;
	}

	@Override
	public String getFilename() {
		return Constants.FILE_NAME_AYCAL_MASTER + CURRENT_DATE_FORMAT;
	}

	@Override
	public AYMaster getAYMaster() {
		return new AYCalMaster();
	}

	@Override
	public void writeAYMaster(BufferedWriter bufferedWriter, AYMaster ayMaster) throws IOException {

		AYCalMaster ayCaLMaster = (AYCalMaster) ayMaster;
		
		bufferedWriter.write(ayCaLMaster.getIssuerCode() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCaLMaster.getSecurityCode());
		bufferedWriter.write(Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCaLMaster.getSecurityNameEN());
		bufferedWriter.write(Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCaLMaster.getSecurityNameOther());
		bufferedWriter.write(Constants.DEFAULT_PIPE);
		
		bufferedWriter.write(ayCaLMaster.getSecurityTypeCode() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCaLMaster.getIssueDate() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCaLMaster.getMatureDate() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCaLMaster.getReferenceSecurityCode() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCaLMaster.getcurrencyCode());
		
		bufferedWriter.write(Constants.DEFAULT_LINE_SEPARATOR);
				
	}

	@Override
	public AYMaster setAYMasterValue(AYMaster ayMaster, String seq) {
		
		AYCalMaster ayCaLMaster = (AYCalMaster) ayMaster;
		
		String securityCode = Constants.PREFIX_AYCAL + seq;
		String securityNameEN = "NAME_EN_" + seq;
		String securityNameOTH = "NAME_OTH_" + seq;
		

		ayCaLMaster.setIssuerCode("TCS");
		ayCaLMaster.setSecurityCode(securityCode);
		ayCaLMaster.setSecurityNameEN(securityNameEN);
		ayCaLMaster.setSecurityNameOther(securityNameOTH);
		ayCaLMaster.setSecurityTypeCode("AYCAP");
		ayCaLMaster.setIssueDate(CURRENT_DATE_FORMAT);
		ayCaLMaster.setMatureDate(CURRENT_DATE_FORMAT);
//		ayCaLMaster.setReferenceSecurityCode(null);
		ayCaLMaster.setcurrencyCode("THB");
		
//		1	Issuer Code				STRING			1.Look up : CM_COMPANY[COMPANYCODE].
//													2.EX. TCS
//		2	Security Code			STRING			EX. LG
//		3	Security Name EN		STRING	 		EX. TESCO VISA GOLD
//		4	Security Name Other		STRING	 		EX. TESCO VISA GOLD
//		5	Security Type Code		STRING			1. Look up : CM_SECURITYTYPE[SECURITYTYPECODE]
//													2. EX. AYCAP
//		6	Issue Date				DATE			ระบุเป็นปี คศ. 
//													Ex. 20160122
//		7	Mature Date				DATE			ระบุเป็นปี คศ. 
//													Ex. 20160122
//		8	Reference Security Code	STRING			1. พิจารณาคู่กับ Issuer Code
//													2. . LG2
//		9	Currency Code			STRING			1. Look up : CM_CURRENCY[CURRENCYCODE]
//													2. Default : THB
		
		return ayCaLMaster;
	}

}
