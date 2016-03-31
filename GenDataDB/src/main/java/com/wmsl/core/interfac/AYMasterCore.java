package com.wmsl.core.interfac;

import java.io.BufferedWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.wmsl.bean.AYMaster;
import com.wmsl.bean.GenResult;
import com.wmsl.core.Core;
import com.wealth.exception.dao.InfoEntityServiceException;
import com.wealth.exception.dao.ServerEntityServiceException;
import com.wmsl.Constants;

@Component
public abstract class AYMasterCore extends Core{

	private final Logger log = LoggerFactory.getLogger(AYMasterCore.class);

	private Integer ayMasterLimit;
	public void setAYMasterLimit(Integer ayMasterLimit) {
		this.ayMasterLimit = ayMasterLimit;
	}

	public abstract String getDir();
	public abstract String getFilename();
	public abstract AYMaster getAYMaster();

	@Override
	public GenResult execute() throws ServerEntityServiceException, InfoEntityServiceException, IOException {
		log.debug("Start AYMasterCore.execute ");

		BufferedWriter bufferedWriter = genFilesUtils.getBufferedWriter(getDir(),
				getFilename(), Constants.FILENAME_TXT_EXT);

		genFilesUtils.writeHeaderFile(bufferedWriter, CURRENT_DATE_FORMAT, ayMasterLimit.intValue());
		
		long countRecord = 0;
		try {

			long seq = 100000;
			for (int i = 0; i < ayMasterLimit.intValue(); i++) {

				writeAYMaster(bufferedWriter, setAYMasterValue(getAYMaster(), String.valueOf(seq++)));

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

	public abstract void writeAYMaster(BufferedWriter bufferedWriter, AYMaster ayMaster) throws IOException;

	public abstract AYMaster setAYMasterValue(AYMaster ayMaster, String seq);

}
