package com.wmsl.core.inter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.wmsl.bean.AYPosition;
import com.wmsl.bean.GenResult;
import com.wmsl.bean.dao.CustomerInfo;
import com.wmsl.core.Core;
import com.wealth.bwm.common.impl.entity.instrument.Instrument;
import com.wealth.exception.dao.InfoEntityServiceException;
import com.wealth.exception.dao.ServerEntityServiceException;
import com.wmsl.Constants;

@Component
public abstract class AYPositionCore extends Core {

	private final Logger log = LoggerFactory.getLogger(AYPositionCore.class);

	private Integer ayPositionLimit;
	public void setAYPositionLimit(Integer ayPositionLimit) {
		this.ayPositionLimit = ayPositionLimit;
	}

	public abstract String getDir();

	public abstract String getFilename();

	public abstract AYPosition getAYPosition();
	
	public List<CustomerInfo> getCustomers() {
		return coreDao.getPersonCustomerByFirstNameEn("NAME_P");
	}

	public abstract List<Instrument> getInstruments();

	@Override
	public GenResult execute() throws ServerEntityServiceException, InfoEntityServiceException, IOException {
		log.debug("Start AYPositionCore.execute ");

		long countRecord = 0;

		List<CustomerInfo> customerInfos = getCustomers();
		List<Instrument> instruments = getInstruments();
		int customerSize = customerInfos.size();
		int instrumentSize = instruments.size();

		if (customerSize <= 0 || instrumentSize <= 0) {
			return new GenResult().setTotalCount(countRecord);
		}

		BufferedWriter bufferedWriter = genFilesUtils.getBufferedWriter(getDir(), getFilename(),
				Constants.FILENAME_TXT_EXT);

		genFilesUtils.writeHeaderFile(bufferedWriter, CURRENT_DATE_FORMAT, ayPositionLimit.intValue());
		try {

			int seq = 0;

//			int customerIndex = 0;
//			int instrumentIndex = 0;

			for (int i = 0; i < ayPositionLimit.intValue(); i++) {
				CustomerInfo customerInfo = customerInfos.get(i % customerSize);
				Instrument instrument = instruments.get(i % instrumentSize);

				writeAYPosition(bufferedWriter,
						setAYPositionValue(getAYPosition(), customerInfo, instrument, seq++));

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

	public abstract void writeAYPosition(BufferedWriter bufferedWriter, AYPosition ayPosition) throws IOException;

	public abstract AYPosition setAYPositionValue(AYPosition ayPosition, CustomerInfo customerInfo,
			Instrument instrument, int seq);

}
