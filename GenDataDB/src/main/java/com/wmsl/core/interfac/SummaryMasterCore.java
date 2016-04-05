package com.wmsl.core.interfac;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.wmsl.bean.GenResult;
import com.wmsl.bean.SummaryMaster;
import com.wmsl.bean.dao.CustomerInfo;
import com.wmsl.core.Core;
import com.wealth.exception.dao.InfoEntityServiceException;
import com.wealth.exception.dao.ServerEntityServiceException;
import com.wmsl.Constants;

@Component
public class SummaryMasterCore extends Core {

	private final Logger log = LoggerFactory.getLogger(SummaryMasterCore.class);

	public String getFilenameEq() {
		return Constants.FILE_NAME_EQ + CURRENT_DATE_FORMAT;
	}

	public String getFilenameDeri() {
		return Constants.FILE_NAME_DERI + CURRENT_DATE_FORMAT;
	}

	public List<CustomerInfo> getCustomers() throws InfoEntityServiceException, ServerEntityServiceException {

		return coreDao.getPersonCustomerByFirstNameEn("NAME_P");
	}

	@Override
	public GenResult execute() throws ServerEntityServiceException, InfoEntityServiceException, IOException {
		log.debug("Start SummaryMasterCore.execute ");

		long countRecord = 0;
		List<CustomerInfo> customerInfos = getCustomers();

		if (customerInfos.size() <= 0) {
//			List<GenResult> genResultList = new ArrayList<GenResult> ();
//			genResultList.add(new GenResult().setTotalCount(countRecord));
			
			return new GenResult().setTotalCount(countRecord);
		}

		BufferedWriter bufferedWriterEq = genFilesUtils.getBufferedWriter(Constants.DIR_MARGIN, getFilenameEq(), Constants.FILENAME_TXT_EXT);

		genFilesUtils.writeHeaderFile(bufferedWriterEq, CURRENT_DATE_FORMAT, customerInfos.size());

		BufferedWriter bufferedWriterDeri = genFilesUtils.getBufferedWriter(Constants.DIR_MARGIN, getFilenameDeri(), Constants.FILENAME_TXT_EXT);

		genFilesUtils.writeHeaderFile(bufferedWriterDeri, CURRENT_DATE_FORMAT, customerInfos.size());

		try {

			for (CustomerInfo customerInfo : customerInfos) {

				String cifCode = customerInfo.getCifCode();
				String accountNoEQ = Constants.PREFIX_EQ + cifCode + "_" + String.format("%02d", 1);
				String accountNoDT = Constants.PREFIX_DERI + cifCode + "_" + String.format("%02d", 1);

				writeSummaryMaster(bufferedWriterEq,
						setSummaryMasterValue(new SummaryMaster(), customerInfo, Constants.SOURCE_SUMMARY_ACC, accountNoEQ));

				writeSummaryMaster(bufferedWriterDeri,
						setSummaryMasterValue(new SummaryMaster(), customerInfo, Constants.SOURCE_SUMMARY_DERI, accountNoDT));

				countRecord++;
			}

		} catch (IOException e) {
			throw new IOException();
		} finally {
			bufferedWriterEq.flush();
			bufferedWriterEq.close();

			bufferedWriterDeri.flush();
			bufferedWriterDeri.close();
		}

//		List<GenResult> genResultList = new ArrayList<GenResult> ();
//		genResultList.add(new GenResult().setTotalCount(countRecord));
		
		return new GenResult().setTotalCount(countRecord);
	}

	public void writeSummaryMaster(BufferedWriter bufferedWriter, SummaryMaster equity) throws IOException {

		bufferedWriter.write(equity.getSource() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(equity.getAccountName());
		bufferedWriter.write(Constants.DEFAULT_PIPE);
		bufferedWriter.write(equity.getAccountNo());
		bufferedWriter.write(Constants.DEFAULT_PIPE);
		bufferedWriter.write(equity.getOpenAccountDate() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(equity.getCloseAccountDate() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(equity.getAccountType() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(equity.getMarginType() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(equity.getCardType());
		bufferedWriter.write(Constants.DEFAULT_PIPE);
		bufferedWriter.write(equity.getCardNo());
		bufferedWriter.write(Constants.DEFAULT_PIPE);
		bufferedWriter.write(equity.getCpType() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(equity.getAgentCode() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(equity.getAgentBranchCode());
		bufferedWriter.write(Constants.DEFAULT_LINE_SEPARATOR);

	}

	public SummaryMaster setSummaryMasterValue(SummaryMaster summaryMaster, CustomerInfo customerInfo, String source, String accountNo) {
		summaryMaster.setSource(source);
		summaryMaster.setAccountName(customerInfo.getFirstNameEn());
		summaryMaster.setAccountNo(accountNo);
		summaryMaster.setOpenAccountDate(CURRENT_DATE_FORMAT);
		summaryMaster.setCloseAccountDate(CURRENT_DATE_FORMAT);
		summaryMaster.setAccountType(0);
		summaryMaster.setMarginType(1);
		summaryMaster.setCardType(customerInfo.getCardTypeCode());
		summaryMaster.setCardNo(customerInfo.getIdNember());
		summaryMaster.setCpType("1");
		summaryMaster.setAgentCode("BAY");
		summaryMaster.setAgentBranchCode("00001");
		return summaryMaster;
	}

}
