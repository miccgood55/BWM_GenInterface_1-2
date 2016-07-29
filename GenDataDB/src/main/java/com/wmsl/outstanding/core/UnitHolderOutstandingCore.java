package com.wmsl.outstanding.core;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wealth.bwm.batch.impl.dao.cp.account.UnitTrustOutstandingDao;
import com.wealth.bwm.batch.impl.entity.cp.account.outstanding.OutstandingBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.outstanding.UnitTrustOutstandingBatch;
import com.wealth.exception.dao.InfoEntityServiceException;
import com.wealth.exception.dao.ServerEntityServiceException;
import com.wmsl.Constants;

@Component
public class UnitHolderOutstandingCore extends GenOutStandingDataCore {

//	private final Logger log = LoggerFactory.getLogger(UnitHolderCore.class);
 
	@Autowired
	protected UnitTrustOutstandingDao unitTrustOutstandingDao;
	public void setUnitTrustOutstandingDao(UnitTrustOutstandingDao unitTrustOutstandingDao) {
		this.unitTrustOutstandingDao = unitTrustOutstandingDao;
	}

	@Override
	public void subOutstandingToString(BufferedWriter bufferedWriter, OutstandingBatch outstanding) throws IOException {
		UnitTrustOutstandingBatch unitOut = (UnitTrustOutstandingBatch)outstanding;
		bufferedWriter.write(prepareData(unitOut.getOutstandingId()));
//		bufferedWriter.write(COMMA_STRING);bufferedWriter.write(COMMA_STRING);bufferedWriter.write(COMMA_STRING );
		bufferedWriter.newLine();
	}

	/*
	 * get Dir 
	 * 
	 * (non-Javadoc)
	 * @see com.wmsl.core.GenBigDataCore#getDir(java.lang.String)
	 */
	@Override
	public String getDir(String dir) {
		return Constants.DIR_UNITTRUST_OUTSTANDING + dir;
	}
	@Override
	public String getFilenameAcc() {
		return Constants.FILE_NAME_UT_ACC + getStartDate().get(Calendar.YEAR);
	}

	@Override
	public String getFilenameSubAcc() {
		return Constants.FILE_NAME_UT_SUBACC + getStartDate().get(Calendar.YEAR);
	}

	@Override
	public String getFilenamePos() {
		return Constants.FILE_NAME_UT_POS;
	}

	@Override
	public String getFilenameTx() {
		return Constants.FILE_NAME_UT_TX + getStartDate().get(Calendar.YEAR);
	}

	@Override
	public String getFilenameAccount() {
		return Constants.FILE_NAME_ACCOUNT_UNITTRUST + getStartDate().get(Calendar.YEAR);
	}

	@Override
	public String getFilenameSubAccount() {
		return Constants.FILE_NAME_SUBACCOUNT_UNITTRUST + getStartDate().get(Calendar.YEAR);
	}

	@Override
	public String getFilenameOutstanding() {
		return Constants.FILE_NAME_OUTSTANDING_UNITTRUST;
	}
	
	@Override
	public String getFilenameExecution(){
		return Constants.FILE_NAME_EXECUTION_UNITTRUST + getStartDate().get(Calendar.YEAR);
	}

	@Override
	protected List<OutstandingBatch> getOutstandingList() throws InfoEntityServiceException, ServerEntityServiceException {


		List<? extends OutstandingBatch> unitTrustOutstandingList = unitTrustOutstandingDao.getObjectList();
		return (List<OutstandingBatch>) unitTrustOutstandingList;
	}

}
