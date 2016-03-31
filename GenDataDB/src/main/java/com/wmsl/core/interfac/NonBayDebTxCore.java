package com.wmsl.core.interfac;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.wmsl.bean.GenResult;
import com.wmsl.bean.NonBayDebTx;
import com.wmsl.bean.dao.CustomerInfo;
import com.wmsl.core.Core;
import com.wealth.bwm.common.impl.entity.instrument.Instrument;
import com.wealth.exception.dao.InfoEntityServiceException;
import com.wealth.exception.dao.ServerEntityServiceException;
import com.wmsl.Constants;

@Component
public class NonBayDebTxCore extends Core {

	private final Logger log = LoggerFactory.getLogger(NonBayDebTxCore.class);

	private Integer nonBayDebTxLimit;

	public void setNonBayDebTxLimit(Integer nonBayDebTxLimit) {
		this.nonBayDebTxLimit = nonBayDebTxLimit;
	}

	public String getFilename() {
		return Constants.FILE_NAME_NON_BAY_DEBENTURE_TX + CURRENT_DATE_FORMAT;
	}

	public List<CustomerInfo> getCustomers() {
		return coreDao.getPersonCustomerByFirstNameEn("NAME_P");
	}

	public List<Instrument> getInstruments() {
		return coreDao.getInstrumentBySymbol(Constants.PREFIX_NONBAY_DEB);
	}

	@Override
	public GenResult execute() throws ServerEntityServiceException, InfoEntityServiceException, IOException {
		log.debug("Start NonBayDebTxCore.execute ");

		long countRecord = 0;
		
		List<CustomerInfo> customerInfos = getCustomers();
		List<Instrument> instruments = getInstruments();
		int customerSize = customerInfos.size();
		int instrumentSize = instruments.size();
		
		if(customerSize <= 0 || instrumentSize <= 0){
			return new GenResult().setTotalCount(countRecord);
		}
		
		BufferedWriter bufferedWriter = genFilesUtils.getBufferedWriter(Constants.DIR_NON_BAY_DEBENTURE,
				getFilename(), Constants.FILENAME_TXT_EXT);

		genFilesUtils.writeHeaderFile(bufferedWriter, CURRENT_DATE_FORMAT, nonBayDebTxLimit.intValue());
		
		try {

			long seq = 100000;

			for (int i = 0; i < nonBayDebTxLimit.intValue(); i++) {
				CustomerInfo customerInfo = customerInfos.get(i % customerSize);
				Instrument instrument = instruments.get(i % instrumentSize);

				writeNonBayDebTx(bufferedWriter,
						setNonBayDebTxValue(new NonBayDebTx(), customerInfo, instrument, String.valueOf(seq++)));

				countRecord++;
			}

		} catch (IOException e) {
			throw new IOException();
		} finally {
			bufferedWriter.flush();
			bufferedWriter.close();
		}

		return new GenResult().setTotalCount(countRecord);
	}

	public void writeNonBayDebTx(BufferedWriter bufferedWriter, NonBayDebTx nonBayDebTx) throws IOException {

		bufferedWriter.write(nonBayDebTx.getTradeDate() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(nonBayDebTx.getSettleDate() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(nonBayDebTx.getSecurityCode());
		bufferedWriter.write(Constants.DEFAULT_PIPE);
		bufferedWriter.write(nonBayDebTx.getCifCode());
		bufferedWriter.write(Constants.DEFAULT_PIPE);
		bufferedWriter.write(nonBayDebTx.getTransactionNo());
		bufferedWriter.write(Constants.DEFAULT_PIPE);

		bufferedWriter.write(nonBayDebTx.getTransactionType() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(nonBayDebTx.getSubTransactionType() + Constants.DEFAULT_PIPE);

		bufferedWriter.write(String.valueOf(nonBayDebTx.getCostAmount()));
		bufferedWriter.write(Constants.DEFAULT_PIPE);
		bufferedWriter.write(String.valueOf(nonBayDebTx.getUnit()));
		bufferedWriter.write(Constants.DEFAULT_PIPE);
		bufferedWriter.write(String.valueOf(nonBayDebTx.getPricePerUnit()));
		bufferedWriter.write(Constants.DEFAULT_PIPE);

		bufferedWriter.write(nonBayDebTx.getAgentCode() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(nonBayDebTx.getAgentBranchCode() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(nonBayDebTx.getiCCode() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(nonBayDebTx.getStatus() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(nonBayDebTx.getSource() + Constants.DEFAULT_PIPE);
		bufferedWriter.write(String.valueOf(nonBayDebTx.getNetAmount()));
		bufferedWriter.write(Constants.DEFAULT_PIPE);

		bufferedWriter.write(nonBayDebTx.getIssuerCode());

		bufferedWriter.write(Constants.DEFAULT_LINE_SEPARATOR);

	}

	public NonBayDebTx setNonBayDebTxValue(NonBayDebTx nonBayDebTx, CustomerInfo customerInfo, Instrument instrument,
			String seq) {

		String transactionNo = Constants.PREFIX_NONBAY_DEB_TX + seq;

		nonBayDebTx.setTradeDate(CURRENT_DATE_FORMAT);
		nonBayDebTx.setSettleDate(CURRENT_DATE_FORMAT);
		nonBayDebTx.setSecurityCode(instrument.getSymbol());
		nonBayDebTx.setCifCode(customerInfo.getCifCode());
		nonBayDebTx.setTransactionNo(transactionNo);
		nonBayDebTx.setTransactionType("BUY");
		nonBayDebTx.setSubTransactionType("8");
		nonBayDebTx.setCostAmount(BigDecimal.ZERO);
		nonBayDebTx.setUnit(BigDecimal.ZERO);
		nonBayDebTx.setPricePerUnit(BigDecimal.ZERO);
		nonBayDebTx.setAgentCode("BAY");
		nonBayDebTx.setAgentBranchCode("00001");
		// NonBayDebTx.setiCCode("00000000000000012345");
		nonBayDebTx.setStatus("A");
		nonBayDebTx.setSource("NONBAY-DEBENTURE");
		nonBayDebTx.setNetAmount(BigDecimal.ZERO);
		nonBayDebTx.setIssuerCode("BAY-Other");

		return nonBayDebTx;
	}

}
