package com.wmsl.core;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wealth.bwm.impl.entity.cp.account.SubAccount;
import com.wealth.bwm.impl.entity.cp.account.SubFixedIncomeAccount;
import com.wealth.bwm.impl.entity.cp.account.execution.Execution;
import com.wealth.bwm.impl.entity.cp.account.execution.FixedIncomeExecution;
import com.wealth.bwm.impl.entity.cp.account.outstanding.FixedIncomeOutstanding;
import com.wealth.bwm.impl.entity.cp.account.outstanding.Outstanding;
import com.wealth.exception.dao.InfoEntityServiceException;
import com.wealth.exception.dao.ServerEntityServiceException;
import com.wmsl.Constants;
import com.wmsl.dao.impl.SubFixedIncomeAccountDao;

@Component
public class FixedIncomeCore extends GenBigDataCore {

//	private final Logger log = LoggerFactory.getLogger(FixedIncomeCore.class);
 
	@Autowired
	protected SubFixedIncomeAccountDao subFixedIncomeAccountDao;

	public void setSubFixedIncomeAccountDao(SubFixedIncomeAccountDao subFixedIncomeAccountDao) {
		this.subFixedIncomeAccountDao = subFixedIncomeAccountDao;
	}

	@Override
	public void subOutstandingToString(BufferedWriter bufferedWriter, Outstanding outstanding) throws IOException {
		FixedIncomeOutstanding fixOut = (FixedIncomeOutstanding)outstanding;
		bufferedWriter.write(prepareData(fixOut.getOutstandingId()) + COMMA_STRING);
		bufferedWriter.write(prepareData(fixOut.getMarketYield()));

		bufferedWriter.newLine();
	}
	

	@Override
	public void subExecutionToString(BufferedWriter bufferedWriter, Execution execution) throws IOException {
		FixedIncomeExecution fixedExe = (FixedIncomeExecution)execution;
		
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
	public List<SubAccount> getSubAccount() throws InfoEntityServiceException, ServerEntityServiceException {
		
		Integer dataFrom = getDataFrom();
		Integer dataTo = getDataTo();
		List<SubFixedIncomeAccount> subFixedIncomeAccounts;
		if(dataFrom == null || dataTo == null){
			subFixedIncomeAccounts = subFixedIncomeAccountDao.getObjectList();
		} else {
			subFixedIncomeAccounts = subFixedIncomeAccountDao.getObjectList(dataFrom, dataTo , true, false);
		}
		
		List<? extends SubAccount> subAccounts = subFixedIncomeAccounts;
		return (List<SubAccount>) subAccounts;
	}

	@Override
	public String getFilenamePos() {
		return Constants.FILE_NAME_BOND_POS + getStopDate().get(Calendar.YEAR);
	}

	@Override
	public String getFilenameTx() {
		return Constants.FILE_NAME_BOND_TX + getStopDate().get(Calendar.YEAR);
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
	public Outstanding getOutstanding() {
		FixedIncomeOutstanding fixOut = new FixedIncomeOutstanding();
		fixOut.setMarketYield(BigDecimal.ONE);
		return fixOut;
	}

	@Override
	public Execution getExecution() {
		return new FixedIncomeExecution();
	}

}