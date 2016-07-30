package com.wmsl.execution.core;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wealth.bwm.batch.impl.dao.cp.account.DepositOutstandingDao;
import com.wealth.bwm.batch.impl.entity.cp.account.execution.DepositExecutionBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.execution.ExecutionBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.outstanding.DepositOutstandingBacth;
import com.wealth.bwm.batch.impl.entity.cp.account.outstanding.OutstandingBatch;
import com.wealth.bwm.impl.entity.cp.account.execution.UnitTrustExecution;
import com.wealth.exception.dao.InfoEntityServiceException;
import com.wealth.exception.dao.ServerEntityServiceException;
import com.wmsl.Constants;
import com.wmsl.dao.impl.DepositExecutionDao;

@Component
public class DepositExecutionCore extends GenExecutionDataCore {

//	private final Logger log = LoggerFactory.getLogger(DepositCore.class);
 
	@Autowired
	protected DepositExecutionDao depositExecutionDao;
	public void setDepositExecutionDao(DepositExecutionDao depositExecutionDao) {
		this.depositExecutionDao = depositExecutionDao;
	}

	@Override
	public void subExecutionToString(BufferedWriter bufferedWriter, ExecutionBatch execution) throws IOException {
		DepositExecutionBatch depExe = (DepositExecutionBatch)execution;
		bufferedWriter.write(prepareData(depExe.getExecutionId()));
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
		return Constants.DIR_DEP_EXECUTION + dir;
	}

	@Override
	public String getFilenameTx() {
		return Constants.FILE_NAME_DEP_TX + getStartDate().get(Calendar.YEAR);
	}
	
	@Override
	public String getFilenameExecution(){
		return Constants.FILE_NAME_EXECUTION_DEPOSIT + getStartDate().get(Calendar.YEAR);
	}
	
	@Override
	protected List<ExecutionBatch> getExecutionList() throws InfoEntityServiceException, ServerEntityServiceException {
		List<? extends ExecutionBatch> depositExecutionList = depositExecutionDao.getObjectList();
		return (List<ExecutionBatch>) depositExecutionList;
	}

}
