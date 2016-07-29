package com.wmsl.outstanding.core;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wealth.bwm.batch.impl.dao.cp.account.DepositOutstandingDao;
import com.wealth.bwm.batch.impl.entity.cp.account.outstanding.DepositOutstandingBacth;
import com.wealth.bwm.batch.impl.entity.cp.account.outstanding.OutstandingBatch;
import com.wealth.exception.dao.InfoEntityServiceException;
import com.wealth.exception.dao.ServerEntityServiceException;
import com.wmsl.Constants;

@Component
public class DepositOutstandingCore extends GenOutStandingDataCore {

//	private final Logger log = LoggerFactory.getLogger(DepositCore.class);
 
	@Autowired
	protected DepositOutstandingDao depositOutstandingDao;
	public void setDepositOutstandingDao(DepositOutstandingDao depositOutstandingDao) {
		this.depositOutstandingDao = depositOutstandingDao;
	}

	@Override
	public void subOutstandingToString(BufferedWriter bufferedWriter, OutstandingBatch outstanding) throws IOException {
		DepositOutstandingBacth depOut = (DepositOutstandingBacth)outstanding;
		
		bufferedWriter.write(prepareData(depOut.getOutstandingId()) + COMMA_STRING);
		bufferedWriter.write(prepareData(depOut.getCallMargin()) + COMMA_STRING);
		bufferedWriter.write(prepareData(depOut.getEquityBalance()) + COMMA_STRING);
		bufferedWriter.write(prepareData(depOut.getExcessEquity()) + COMMA_STRING);
		bufferedWriter.write(prepareData(depOut.getInitialMargin()) + COMMA_STRING);
		bufferedWriter.write(prepareData(depOut.getLiquidationValue()) + COMMA_STRING);
		bufferedWriter.write(prepareData(depOut.getMaintenanceMargin()));

		bufferedWriter.newLine();
	}

//	@Override
//	public OutstandingBatch getOutstanding() {
//		return new DepositOutstandingBacth();
//	}

	/* 
	 * get Dir
	 * 
	 * (non-Javadoc)
	 * @see com.wmsl.core.GenBigDataCore#getDir(java.lang.String)
	 * 
	 */
	@Override
	public String getDir(String dir) {
		return Constants.DIR_DEP_OUTSTANDING + dir;
	}
	@Override
	public String getFilenameAcc() {
		return Constants.FILE_NAME_DEP_ACC + getStartDate().get(Calendar.YEAR);
	}

	@Override
	public String getFilenameSubAcc() {
		return Constants.FILE_NAME_DEP_SUBACC + getStartDate().get(Calendar.YEAR);
	}

	@Override
	public String getFilenamePos() {
//		return Constants.FILE_NAME_DEP_POS + getStartDate().get(Calendar.YEAR);
		return Constants.FILE_NAME_DEP_POS;
	}

	@Override
	public String getFilenameTx() {
		return Constants.FILE_NAME_DEP_TX + getStartDate().get(Calendar.YEAR);
	}
	
	@Override
	public String getFilenameAccount() {
		return Constants.FILE_NAME_ACCOUNT_DEPOSIT + getStartDate().get(Calendar.YEAR);
	}

	@Override
	public String getFilenameSubAccount() {
		return Constants.FILE_NAME_SUBACCOUNT_DEPOSIT + getStartDate().get(Calendar.YEAR);
	}

	@Override
	public String getFilenameExecution(){
		return Constants.FILE_NAME_EXECUTION_DEPOSIT + getStartDate().get(Calendar.YEAR);
	}
	
	@Override
	public String getFilenameOutstanding() {
//		return Constants.FILE_NAME_OUTSTANDING_DEPOSIT + getStartDate().get(Calendar.YEAR);
		return Constants.FILE_NAME_OUTSTANDING_DEPOSIT;
	}

	@Override
	protected List<OutstandingBatch> getOutstandingList() throws InfoEntityServiceException, ServerEntityServiceException {
		
		List<? extends OutstandingBatch> subBankAccountList = depositOutstandingDao.getObjectList();
		return (List<OutstandingBatch>) subBankAccountList;
	}
	
}
