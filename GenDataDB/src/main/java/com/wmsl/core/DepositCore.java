package com.wmsl.core;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//import com.wealth.bwm.common.impl.entity.company.Counterparty;
import com.wealth.bwm.impl.dao.cp.account.SubBankAccountDao;
import com.wealth.bwm.impl.entity.cp.account.SubAccount;
import com.wealth.bwm.impl.entity.cp.account.SubBankAccount;
import com.wealth.bwm.impl.entity.cp.account.execution.DepositExecution;
import com.wealth.bwm.impl.entity.cp.account.execution.Execution;
import com.wealth.bwm.impl.entity.cp.account.outstanding.DepositOutstanding;
import com.wealth.bwm.impl.entity.cp.account.outstanding.Outstanding;
import com.wealth.exception.dao.InfoEntityServiceException;
import com.wealth.exception.dao.ServerEntityServiceException;
import com.wmsl.Constants;

@Component
public class DepositCore extends GenBigDataCore {

//	private final Logger log = LoggerFactory.getLogger(DepositCore.class);
 
	@Autowired
	protected SubBankAccountDao subBankAccountDao;
	public void setSubBankAccountDao(SubBankAccountDao subBankAccountDao) {
		this.subBankAccountDao = subBankAccountDao;
	}

	@Override
	public void subOutstandingToString(BufferedWriter bufferedWriter, Outstanding outstanding) throws IOException {
		DepositOutstanding depOut = (DepositOutstanding)outstanding;
		
		bufferedWriter.write(prepareData(depOut.getOutstandingId()) + COMMA_STRING);
		bufferedWriter.write(prepareData(depOut.getCallMargin()) + COMMA_STRING);
		bufferedWriter.write(prepareData(depOut.getEquityBalance()) + COMMA_STRING);
		bufferedWriter.write(prepareData(depOut.getExcessEquity()) + COMMA_STRING);
		bufferedWriter.write(prepareData(depOut.getInitialMargin()) + COMMA_STRING);
		bufferedWriter.write(prepareData(depOut.getLiquidationValue()) + COMMA_STRING);
		bufferedWriter.write(prepareData(depOut.getMaintenanceMargin()));

		bufferedWriter.newLine();
//		bufferedWriter.flush();
	}

	@Override
	public void subExecutionToString(BufferedWriter bufferedWriter, Execution execution) throws IOException {
		DepositExecution depExe = (DepositExecution)execution;
		
		bufferedWriter.write(prepareData(depExe.getExecutionId()));

		bufferedWriter.newLine();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubAccount> getSubAccount() throws InfoEntityServiceException, ServerEntityServiceException {
		
		Integer dataFrom = getDataFrom();
		Integer dataTo = getDataTo();
		List<SubBankAccount> subBankAccounts;
		if(dataFrom == null || dataTo == null){
			subBankAccounts = subBankAccountDao.getObjectList();
		} else {
			subBankAccounts = subBankAccountDao.getObjectList(dataFrom, dataTo , true, false);
		}
		List<? extends SubAccount> subAccounts = subBankAccounts;
		return (List<SubAccount>) subAccounts;
	}

	@Override
	public String getFilenamePos() {
		return Constants.FILE_NAME_DEP_POS + getStopDate().get(Calendar.YEAR);
	}

	@Override
	public String getFilenameTx() {
		return Constants.FILE_NAME_DEP_TX + getStopDate().get(Calendar.YEAR);
	}

	@Override
	public String getFilenameExecution(){
		return Constants.FILE_NAME_EXECUTION_DEPOSIT + getStopDate().get(Calendar.YEAR);
	}
	
	@Override
	public String getFilenameOutstanding() {
		return Constants.FILE_NAME_OUTSTANDING_DEPOSIT + getStopDate().get(Calendar.YEAR);
	}

	@Override
	public Outstanding getOutstanding() {
		return new DepositOutstanding();
	}

	@Override
	public Execution getExecution() {
		return new DepositExecution();
	}

}
