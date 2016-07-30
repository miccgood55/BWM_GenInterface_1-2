package com.wmsl.execution.core;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Component;

import com.wealth.bwm.batch.impl.entity.cp.account.execution.ExecutionBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.execution.FixedIncomeExecutionBatch;
import com.wealth.exception.dao.InfoEntityServiceException;
import com.wealth.exception.dao.ServerEntityServiceException;
import com.wmsl.Constants;
import com.wmsl.dao.impl.FixedIncomeExecutionDao;

@Component
public class FixedIncomeExecutionCore extends GenExecutionDataCore{

	private FixedIncomeExecutionDao fixedIncomeExecutionDao;
	public void setFixedIncomeExecutionDao(FixedIncomeExecutionDao fixedIncomeExecutionDao) {
		this.fixedIncomeExecutionDao = fixedIncomeExecutionDao;
	}


	@Override
	public void subExecutionToString(BufferedWriter bufferedWriter, ExecutionBatch execution) throws IOException {
		FixedIncomeExecutionBatch fixedIncomeExecution = (FixedIncomeExecutionBatch)execution;
		
		bufferedWriter.write(prepareData(fixedIncomeExecution.getExecutionId()));
		bufferedWriter.write(COMMA_STRING + COMMA_STRING + COMMA_STRING + COMMA_STRING);
		bufferedWriter.write(COMMA_STRING + COMMA_STRING + COMMA_STRING);
		
		bufferedWriter.newLine();

	}


	/*
	 * get Dir
	 * (non-Javadoc)
	 * @see com.wmsl.core.GenBigDataCore#getDir(java.lang.String)
	 * 
	 * 
	 */
	@Override
	public String getDir(String dir) {
		return Constants.DIR_NONBAY_EXECUTION + dir;
	}

	@Override
	public String getFilenameTx() {
		return Constants.FILE_NAME_FIXED_TX + getStartDate().get(Calendar.YEAR);
	}
	
	@Override
	public String getFilenameExecution(){
		return Constants.FILE_NAME_EXECUTION_FIXED + getStartDate().get(Calendar.YEAR);
	}
	
	@Override
	protected List<ExecutionBatch> getExecutionList() throws InfoEntityServiceException, ServerEntityServiceException {
		List<? extends ExecutionBatch> fixedIncomeExecutionList = fixedIncomeExecutionDao.getObjectList();
		return (List<ExecutionBatch>) fixedIncomeExecutionList;
	}
	
}
