package com.wmsl.core;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wealth.bwm.impl.entity.cp.account.SubUnitTrustAccount;
import com.wealth.bwm.batch.impl.entity.cp.account.AccountBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.SubAccountBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.SubBankAccountBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.execution.ExecutionBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.execution.UnitTrustExecutionBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.outstanding.OutstandingBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.outstanding.UnitTrustOutstandingBatch;
import com.wealth.exception.dao.InfoEntityServiceException;
import com.wealth.exception.dao.ServerEntityServiceException;
import com.wmsl.Constants;
import com.wmsl.dao.impl.SubUnitTrustAccountDao;

@Component
public class UnitHolderCore extends GenBigDataBizCore {

//	private final Logger log = LoggerFactory.getLogger(UnitHolderCore.class);
 
	@Autowired
	protected SubUnitTrustAccountDao subUnitTrustAccountDao;
	public void setSubUnitTrustAccountDao(SubUnitTrustAccountDao subUnitTrustAccountDao) {
		this.subUnitTrustAccountDao = subUnitTrustAccountDao;
	}
	
	@Override
	public void subOutstandingToString(BufferedWriter bufferedWriter, OutstandingBatch outstanding) throws IOException {
		UnitTrustOutstandingBatch unitOut = (UnitTrustOutstandingBatch)outstanding;
		bufferedWriter.write(prepareData(unitOut.getOutstandingId()));
		bufferedWriter.write(COMMA_STRING);bufferedWriter.write(COMMA_STRING);bufferedWriter.write(COMMA_STRING );
		bufferedWriter.newLine();
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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SubAccountBatch> getSubAccountDB() throws InfoEntityServiceException, ServerEntityServiceException {
		

		Integer dataFrom = getDataFrom();
		Integer dataTo = getDataTo();
		List<SubUnitTrustAccount> subUnitTrustAccounts;
		if(dataFrom == null || dataTo == null){
			subUnitTrustAccounts = subUnitTrustAccountDao.getObjectList();
		} else {
			subUnitTrustAccounts = subUnitTrustAccountDao.getObjectList(dataFrom, dataTo , true, false);
		}

		List<SubAccountBatch> subBankAccountBatchs = new ArrayList<SubAccountBatch>();
		for (SubUnitTrustAccount subUnitTrustAccount : subUnitTrustAccounts) {
			SubBankAccountBatch subBankAccountBatch = new SubBankAccountBatch();
			subBankAccountBatch.setSubAccountId(subUnitTrustAccount.getSubAccountId());
			subBankAccountBatchs.add(subBankAccountBatch);
		}
		
		List<? extends SubAccountBatch> subAccounts = subBankAccountBatchs;
		return (List<SubAccountBatch>) subAccounts;
		
//		List<? extends SubAccount> subAccounts = subUnitTrustAccounts;
//		return (List<SubAccount>) subAccounts;
	}

	@Override
	public String getFilenamePos() {
		return Constants.FILE_NAME_UT_POS + getStopDate().get(Calendar.YEAR);
	}

	@Override
	public String getFilenameTx() {
		return Constants.FILE_NAME_UT_TX + getStopDate().get(Calendar.YEAR);
	}
	
	@Override
	public String getFilenameExecution(){
		return Constants.FILE_NAME_EXECUTION_UNITTRUST + getStopDate().get(Calendar.YEAR);
	}

	@Override
	public String getFilenameOutstanding() {
		return Constants.FILE_NAME_OUTSTANDING_UNITTRUST + getStopDate().get(Calendar.YEAR);
	}

	@Override
	public OutstandingBatch getOutstanding() {
		return new UnitTrustOutstandingBatch();
	}

	@Override
	public ExecutionBatch getExecution() {
		return new UnitTrustExecutionBatch();
	}

	@Override
	public String getFilenameAcc() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFilenameSubAcc() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFilenameAccount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFilenameSubAccount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AccountBatch getAccount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SubAccountBatch getSubAccount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getBranchId() {
		return 1;
	}


	@Override
	public void accToString(BufferedWriter bufferedWriter, AccountBatch account) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void subAccToString(BufferedWriter bufferedWriter, SubAccountBatch subAccount) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
