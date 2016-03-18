package com.wmsl.core;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wealth.bwm.impl.entity.cp.account.SubAccount;
import com.wealth.bwm.impl.entity.cp.account.SubUnitTrustAccount;
import com.wealth.bwm.impl.entity.cp.account.execution.Execution;
import com.wealth.bwm.impl.entity.cp.account.execution.UnitTrustExecution;
import com.wealth.bwm.impl.entity.cp.account.outstanding.UnitTrustOutstanding;
import com.wealth.bwm.impl.entity.cp.account.outstanding.Outstanding;
import com.wealth.exception.dao.InfoEntityServiceException;
import com.wealth.exception.dao.ServerEntityServiceException;
import com.wmsl.Constants;
import com.wmsl.dao.impl.SubUnitTrustAccountDao;

@Component
public class UnitHolderCore extends GenBigDataCore {

//	private final Logger log = LoggerFactory.getLogger(UnitHolderCore.class);
 
	@Autowired
	protected SubUnitTrustAccountDao subUnitTrustAccountDao;
	public void setSubUnitTrustAccountDao(SubUnitTrustAccountDao subUnitTrustAccountDao) {
		this.subUnitTrustAccountDao = subUnitTrustAccountDao;
	}
	
	@Override
	public void subOutstandingToString(BufferedWriter bufferedWriter, Outstanding outstanding) throws IOException {
		UnitTrustOutstanding unitOut = (UnitTrustOutstanding)outstanding;
		bufferedWriter.write(prepareData(unitOut.getOutstandingId()));
		bufferedWriter.write(COMMA_STRING);bufferedWriter.write(COMMA_STRING);bufferedWriter.write(COMMA_STRING );
		bufferedWriter.newLine();
	}

	@Override
	public void subExecutionToString(BufferedWriter bufferedWriter, Execution execution) throws IOException {
		UnitTrustExecution unitExe = (UnitTrustExecution)execution;
		
		bufferedWriter.write(prepareData(unitExe.getExecutionId()) + COMMA_STRING);
		bufferedWriter.write(prepareData(unitExe.getNav()) + COMMA_STRING);
		bufferedWriter.write(prepareData(unitExe.getSwinAllotDate()) + COMMA_STRING);
		bufferedWriter.write(prepareData(unitExe.getSwitchingFundCode()));
		
		bufferedWriter.newLine();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SubAccount> getSubAccount() throws InfoEntityServiceException, ServerEntityServiceException {
		

		Integer dataFrom = getDataFrom();
		Integer dataTo = getDataTo();
		List<SubUnitTrustAccount> subUnitTrustAccounts;
		if(dataFrom == null || dataTo == null){
			subUnitTrustAccounts = subUnitTrustAccountDao.getObjectList();
		} else {
			subUnitTrustAccounts = subUnitTrustAccountDao.getObjectList(dataFrom, dataTo , true, false);
		}
		List<? extends SubAccount> subAccounts = subUnitTrustAccounts;
		return (List<SubAccount>) subAccounts;
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
	public Outstanding getOutstanding() {
		return new UnitTrustOutstanding();
	}

	@Override
	public Execution getExecution() {
		return new UnitTrustExecution();
	}

}
