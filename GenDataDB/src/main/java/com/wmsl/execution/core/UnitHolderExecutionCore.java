package com.wmsl.execution.core;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wealth.bwm.batch.impl.dao.cp.account.UnitTrustOutstandingDao;
import com.wealth.bwm.batch.impl.entity.cp.account.execution.ExecutionBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.execution.UnitTrustExecutionBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.outstanding.OutstandingBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.outstanding.UnitTrustOutstandingBatch;
import com.wealth.exception.dao.InfoEntityServiceException;
import com.wealth.exception.dao.ServerEntityServiceException;
import com.wmsl.Constants;
import com.wmsl.dao.impl.UnitTrustExecutionDao;

@Component
public class UnitHolderExecutionCore extends GenExecutionDataCore {

//	private final Logger log = LoggerFactory.getLogger(UnitHolderCore.class);
 
	@Autowired
	protected UnitTrustExecutionDao unitTrustExecutionDao;
	public void setUnitTrustExecutionDao(UnitTrustExecutionDao unitTrustExecutionDao) {
		this.unitTrustExecutionDao = unitTrustExecutionDao;
	}

	@Override
	public void subExecutionToString(BufferedWriter bufferedWriter, ExecutionBatch execution) throws IOException {
		UnitTrustExecutionBatch unitExe = (UnitTrustExecutionBatch)execution;
		
		bufferedWriter.write(prepareData(unitExe.getExecutionId()) + COMMA_STRING);
		bufferedWriter.write(prepareData(unitExe.getNav()) + COMMA_STRING);
		bufferedWriter.write(prepareData(unitExe.getSwinAllotDate()) + COMMA_STRING);
		bufferedWriter.write(prepareData(unitExe.getSwitchingFundCode()));
		
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
		return Constants.DIR_UNITTRUST_EXECUTION + dir;
	}

	@Override
	public String getFilenameTx() {
		return Constants.FILE_NAME_UT_TX + getStartDate().get(Calendar.YEAR);
	}

	@Override
	public String getFilenameExecution(){
		return Constants.FILE_NAME_EXECUTION_UNITTRUST + getStartDate().get(Calendar.YEAR);
	}

	@Override
	protected List<ExecutionBatch> getExecutionList() throws InfoEntityServiceException, ServerEntityServiceException {
		List<? extends ExecutionBatch> unitTrustExecutionList = unitTrustExecutionDao.getObjectList();
		return (List<ExecutionBatch>) unitTrustExecutionList;
	}

}
