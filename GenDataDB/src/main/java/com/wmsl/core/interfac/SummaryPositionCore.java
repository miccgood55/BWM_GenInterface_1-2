package com.wmsl.core.interfac;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.wmsl.bean.GenResult;
import com.wmsl.bean.SummaryPosition;
import com.wmsl.core.Core;
import com.wealth.bwm.impl.entity.cp.account.MarginAccount;
import com.wealth.exception.dao.InfoEntityServiceException;
import com.wealth.exception.dao.ServerEntityServiceException;
import com.wmsl.Constants;

@Component
public abstract class SummaryPositionCore extends Core {

	private final Logger log = LoggerFactory.getLogger(SummaryPositionCore.class);

	/*  abstract method
	 * 
	 */
	
	public abstract String getFilename();

	public abstract List<MarginAccount> getMarginAccounts();

	public abstract SummaryPosition getSummaryPosition();

	public abstract void writeSummaryPosition(BufferedWriter bufferedWriter, SummaryPosition summaryPosition) throws IOException;

	public abstract SummaryPosition setPositionEquity(SummaryPosition summaryPosition, String accountNo);
	
	/*   */

	@Override
	public GenResult execute() throws ServerEntityServiceException, InfoEntityServiceException, IOException {
		log.debug("Start SummaryPositionCore.execute ");

		long countRecord = 0;
		List<MarginAccount> marginAccounts = getMarginAccounts();

		if(marginAccounts.size() <= 0 ){
			return new GenResult().setTotalCount(countRecord);
		}
		
		BufferedWriter bufferedWriter = genFilesUtils.getBufferedWriter(Constants.DIR_MARGIN, getFilename(),
				Constants.FILENAME_TXT_EXT);

		genFilesUtils.writeHeaderFile(bufferedWriter, CURRENT_DATE_FORMAT, marginAccounts.size());

		try {

			for (MarginAccount marginAccount : marginAccounts) {

				writeSummaryPosition(bufferedWriter, setPositionEquity(getSummaryPosition(), marginAccount.getAccountNumber()));
				countRecord++;
			}
		} finally {
			bufferedWriter.flush();
			bufferedWriter.close();
		}

		return new GenResult().setTotalCount(countRecord);
	}

}
