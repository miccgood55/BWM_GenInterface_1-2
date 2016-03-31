package com.wmsl.core.interfac;

import java.io.BufferedWriter;
import java.io.IOException;

import org.springframework.stereotype.Component;

import com.wmsl.bean.AYCapMaster;
import com.wmsl.bean.AYMaster;
import com.wmsl.Constants;

@Component
public class AYCapMasterCore extends AYMasterCore{

	@Override
	public String getDir() {
		return Constants.DIR_AYCAP;
	}

	@Override
	public String getFilename() {
		return Constants.FILE_NAME_AYCAP_MASTER + CURRENT_DATE_FORMAT;
	}

	@Override
	public AYMaster getAYMaster() {
		return new AYCapMaster();
	}

	@Override
	public void writeAYMaster(BufferedWriter bufferedWriter, AYMaster ayMaster) throws IOException {

		AYCapMaster ayCapMaster = (AYCapMaster) ayMaster;
		
		bufferedWriter.write(ayCapMaster.getIssuerCode() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCapMaster.getSecurityCode());
		bufferedWriter.write(Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCapMaster.getSecurityNameEN());
		bufferedWriter.write(Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCapMaster.getSecurityNameOther());
		bufferedWriter.write(Constants.DEFAULT_PIPE);
		
		bufferedWriter.write(ayCapMaster.getSecurityTypeCode() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCapMaster.getIssueDate() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCapMaster.getMatureDate() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCapMaster.getReferenceSecurityCode() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(ayCapMaster.getcurrencyCode());
		
		bufferedWriter.write(Constants.DEFAULT_LINE_SEPARATOR);
				
	}

	@Override
	public AYMaster setAYMasterValue(AYMaster ayMaster, String seq) {
		
		AYCapMaster ayCapMaster = (AYCapMaster) ayMaster;
		
		String securityCode = Constants.PREFIX_AYCAP + seq;
		String securityNameEN = "NAME_EN_" + seq;
		String securityNameOTH = "NAME_OTH_" + seq;
		

		ayCapMaster.setIssuerCode("TCS");
		ayCapMaster.setSecurityCode(securityCode);
		ayCapMaster.setSecurityNameEN(securityNameEN);
		ayCapMaster.setSecurityNameOther(securityNameOTH);
		ayCapMaster.setSecurityTypeCode("AYCAP");
		ayCapMaster.setIssueDate(CURRENT_DATE_FORMAT);
		ayCapMaster.setMatureDate(CURRENT_DATE_FORMAT);
//		ayCapMaster.setReferenceSecurityCode(null);
		ayCapMaster.setcurrencyCode("THB");
		
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
		
		return ayCapMaster;
	}

}
