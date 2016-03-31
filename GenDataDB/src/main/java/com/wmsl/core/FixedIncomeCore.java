package com.wmsl.core;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wealth.bwm.batch.impl.entity.cp.account.outstanding.OutstandingBatch;

import com.wealth.bwm.batch.impl.entity.cp.account.AccountBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.SubAccountBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.SubBankAccountBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.execution.ExecutionBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.execution.FixedIncomeExecutionBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.outstanding.FixedIncomeOutstandingBatch;

import com.wealth.bwm.impl.entity.cp.account.SubFixedIncomeAccount;
import com.wealth.exception.dao.InfoEntityServiceException;
import com.wealth.exception.dao.ServerEntityServiceException;
import com.wmsl.Constants;
import com.wmsl.dao.impl.SubFixedIncomeAccountDao;

@Component
public class FixedIncomeCore extends GenBigDataBizCore {

//	private final Logger log = LoggerFactory.getLogger(FixedIncomeCore.class);
 
	@Autowired
	protected SubFixedIncomeAccountDao subFixedIncomeAccountDao;

	public void setSubFixedIncomeAccountDao(SubFixedIncomeAccountDao subFixedIncomeAccountDao) {
		this.subFixedIncomeAccountDao = subFixedIncomeAccountDao;
	}

	@Override
	public void subOutstandingToString(BufferedWriter bufferedWriter, OutstandingBatch outstanding) throws IOException {
		FixedIncomeOutstandingBatch fixOut = (FixedIncomeOutstandingBatch)outstanding;
		bufferedWriter.write(prepareData(fixOut.getOutstandingId()) + COMMA_STRING);
		bufferedWriter.write(prepareData(fixOut.getMarketYield()));

		bufferedWriter.newLine();
	}
	

	@Override
	public void subExecutionToString(BufferedWriter bufferedWriter, ExecutionBatch execution) throws IOException {
		FixedIncomeExecutionBatch fixedExe = (FixedIncomeExecutionBatch)execution;
		
		bufferedWriter.write(prepareData(fixedExe.getExecutionId()) + COMMA_STRING);

		bufferedWriter.write(prepareData(fixedExe.getUnitOffer()) + COMMA_STRING);
		bufferedWriter.write(prepareData(fixedExe.getIsYieldPercentOffer()) + COMMA_STRING);
		bufferedWriter.write(prepareData(fixedExe.getYieldPercentOffer()) + COMMA_STRING);
		bufferedWriter.write(prepareData(fixedExe.getPriceOffer()) + COMMA_STRING);
		bufferedWriter.write(prepareData(fixedExe.getIsYieldPercentBid()) + COMMA_STRING);
		bufferedWriter.write(prepareData(fixedExe.getYieldPercentBid()) + COMMA_STRING);
		bufferedWriter.write(prepareData(fixedExe.getYield()));

		bufferedWriter.newLine();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SubAccountBatch> getSubAccountDB() throws InfoEntityServiceException, ServerEntityServiceException {
		
		Integer dataFrom = getDataFrom();
		Integer dataTo = getDataTo();
		List<SubFixedIncomeAccount> subFixedIncomeAccounts;
		if(dataFrom == null || dataTo == null){
			subFixedIncomeAccounts = subFixedIncomeAccountDao.getObjectList();
		} else {
			subFixedIncomeAccounts = subFixedIncomeAccountDao.getObjectList(dataFrom, dataTo , true, false);
		}
		

		List<SubAccountBatch> subBankAccountBatchs = new ArrayList<SubAccountBatch>();
		for (SubFixedIncomeAccount subFixedIncomeAccount : subFixedIncomeAccounts) {
			SubBankAccountBatch subBankAccountBatch = new SubBankAccountBatch();
			subBankAccountBatch.setSubAccountId(subFixedIncomeAccount.getSubAccountId());
			subBankAccountBatchs.add(subBankAccountBatch);
		}
		
		List<? extends SubAccountBatch> subAccounts = subBankAccountBatchs;
		return (List<SubAccountBatch>) subAccounts;
		
//		List<? extends SubAccount> subAccounts = subFixedIncomeAccounts;
//		return (List<SubAccount>) subAccounts;
	}
	@Override
	public String getDir(String dir) {
		return Constants.DIR_FIXED + dir;
	}
	
	@Override
	public String getFilenamePos() {
		return Constants.FILE_NAME_FIXED_POS + getStopDate().get(Calendar.YEAR);
	}

	@Override
	public String getFilenameTx() {
		return Constants.FILE_NAME_FIXED_TX + getStopDate().get(Calendar.YEAR);
	}

	@Override
	public String getFilenameExecution(){
		return Constants.FILE_NAME_EXECUTION_FIXED + getStopDate().get(Calendar.YEAR);
	}
	
	@Override
	public String getFilenameOutstanding() {
		return Constants.FILE_NAME_OUTSTANDING_FIXED + getStopDate().get(Calendar.YEAR);
	}

	@Override
	public OutstandingBatch getOutstanding() {
		FixedIncomeOutstandingBatch fixOut = new FixedIncomeOutstandingBatch();
		fixOut.setMarketYield(BigDecimal.ONE);
		return fixOut;
	}

	@Override
	public ExecutionBatch getExecution() {
		return new FixedIncomeExecutionBatch();
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
	public void accToString(BufferedWriter bufferedWriter, AccountBatch account) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void subAccToString(BufferedWriter bufferedWriter, SubAccountBatch subAccount) throws IOException {
		// TODO Auto-generated method stub
		
	}


}