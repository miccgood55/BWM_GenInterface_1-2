package com.wmsl.core.interfac;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.wmsl.bean.GenResult;
import com.wmsl.bean.NonBayDebMaster;
import com.wmsl.core.Core;
import com.wealth.exception.dao.InfoEntityServiceException;
import com.wealth.exception.dao.ServerEntityServiceException;
import com.wmsl.Constants;

@Component
public class NonBayDebMasterCore extends Core{

	private final Logger log = LoggerFactory.getLogger(NonBayDebMasterCore.class);

	private Integer nonBayDebMasterLimit;
	public void setNonBayDebMasterLimit(Integer nonBayDebMasterLimit) {
		this.nonBayDebMasterLimit = nonBayDebMasterLimit;
	}
	
	public String getFilename() {
		return Constants.FILE_NAME_NON_BAY_DEBENTURE_MASTER + CURRENT_DATE_FORMAT;
	}

	@Override
	public List<GenResult> execute() throws ServerEntityServiceException, InfoEntityServiceException, IOException {
		log.debug("Start NonBayDebMasterCore.execute ");

		BufferedWriter bufferedWriter = genFilesUtils.getBufferedWriter(Constants.DIR_NON_BAY_DEBENTURE,
				getFilename(), Constants.FILENAME_TXT_EXT);

		genFilesUtils.writeHeaderFile(bufferedWriter, CURRENT_DATE_FORMAT, nonBayDebMasterLimit.intValue());
		
		long countRecord = 0;
		try {

			long seq = 100000;
			for (int i = 0; i < nonBayDebMasterLimit.intValue(); i++) {

				writeNonBayDebMaster(bufferedWriter, setNonBayDebMasterValue(new NonBayDebMaster(), String.valueOf(seq++)));

				countRecord++;
			}

		} catch (IOException e) {
			throw new IOException();
		} finally {
			bufferedWriter.flush();
			bufferedWriter.close();
		}

		List<GenResult> genResultList = new ArrayList<GenResult> ();
		genResultList.add(new GenResult().setTotalCount(countRecord));
		
		return genResultList;
	}

	public void writeNonBayDebMaster(BufferedWriter bufferedWriter, NonBayDebMaster nonBayDebMaster) throws IOException {

		bufferedWriter.write(nonBayDebMaster.getSecurityCode());
		bufferedWriter.write(Constants.DEFAULT_PIPE);
		bufferedWriter.write(nonBayDebMaster.getSecurityNameEN());
		bufferedWriter.write(Constants.DEFAULT_PIPE);
		bufferedWriter.write(nonBayDebMaster.getSecurityNameTH());
		bufferedWriter.write(Constants.DEFAULT_PIPE);
		bufferedWriter.write(nonBayDebMaster.getCouponFrequency() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(nonBayDebMaster.getCurrencyCode() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(nonBayDebMaster.getCouponRate() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(nonBayDebMaster.getSecurityTypeCode() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(nonBayDebMaster.getIssueDate() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(nonBayDebMaster.getMatureDate() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(nonBayDebMaster.getParValue() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(nonBayDebMaster.getIssuerCode() + Constants.DEFAULT_PIPE);
		
		bufferedWriter.write(Constants.DEFAULT_LINE_SEPARATOR);
		
		

	}

	public NonBayDebMaster setNonBayDebMasterValue(NonBayDebMaster nonBayDebMaster, String seq) {
		

		String securityCode = Constants.PREFIX_NONBAY_DEB + seq;
		String securityNameEN = "NAME_EN_" + seq;
		String securityNameTH = "NAME_TH_" + seq;
		
		nonBayDebMaster.setSecurityCode(securityCode);
		nonBayDebMaster.setSecurityNameEN(securityNameEN);
		nonBayDebMaster.setSecurityNameTH(securityNameTH);
		nonBayDebMaster.setCouponFrequency("1");
		nonBayDebMaster.setCurrencyCode("THB");
		nonBayDebMaster.setCouponRate(BigDecimal.ZERO);
		nonBayDebMaster.setSecurityTypeCode("NONBAY-DEBENTURE");
		nonBayDebMaster.setIssueDate(CURRENT_DATE_FORMAT);
		nonBayDebMaster.setMatureDate(CURRENT_DATE_FORMAT);
		nonBayDebMaster.setParValue(BigDecimal.ONE);
		nonBayDebMaster.setIssuerCode("BAY-Other");
		
		return nonBayDebMaster;
	}

}
