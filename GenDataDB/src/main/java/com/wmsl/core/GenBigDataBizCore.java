package com.wmsl.core;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.wealth.bwm.batch.impl.entity.cp.account.AccountBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.SubAccountBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.execution.ExecutionBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.outstanding.OutstandingBatch;
import com.wealth.exception.dao.InfoEntityServiceException;
import com.wealth.exception.dao.ServerEntityServiceException;
import com.wmsl.Constants;
import com.wmsl.bean.dao.CustomerInfo;

@Component
public abstract class GenBigDataBizCore extends GenBigDataCore {

	private static final Logger log = LoggerFactory.getLogger(GenBigDataBizCore.class);

	@Override
	public long execute() throws ServerEntityServiceException, InfoEntityServiceException, IOException{
		log.debug("Start GenBigDataCore.execute ");
	
		Calendar startDate = this.getStartDate();
//		Calendar stopDate = this.getStopDate();

		BufferedWriter bufferedWriterAcc = genFilesUtils.getBufferedWriter(
				Constants.DIR_ACC, getFilenameAcc(), Constants.FILENAME_BIG_EXT);

		BufferedWriter bufferedWriterSubAcc = genFilesUtils.getBufferedWriter(
				Constants.DIR_SUBACC, getFilenameSubAcc(), Constants.FILENAME_BIG_EXT);
		
		BufferedWriter bufferedWriterPosition = genFilesUtils.getBufferedWriter(
				Constants.DIR_POS, getFilenamePos(), Constants.FILENAME_BIG_EXT);

		BufferedWriter bufferedWriterTx = genFilesUtils.getBufferedWriter(
				Constants.DIR_TX, getFilenameTx(), Constants.FILENAME_BIG_EXT);


		BufferedWriter bufferedWriterAccount = genFilesUtils.getBufferedWriter(
				Constants.DIR_ACCOUNT, getFilenameAccount(), Constants.FILENAME_BIG_EXT);

		BufferedWriter bufferedWriterSubAccount = genFilesUtils.getBufferedWriter(
				Constants.DIR_SUBACCOUNT, getFilenameSubAccount(), Constants.FILENAME_BIG_EXT);
		
		BufferedWriter bufferedWriterOutStanding = genFilesUtils.getBufferedWriter(
				Constants.DIR_OUTSTANDING, getFilenameOutstanding(), Constants.FILENAME_BIG_EXT);

		BufferedWriter bufferedWriterExe = genFilesUtils.getBufferedWriter(
				Constants.DIR_EXECUTION, getFilenameExecution(), Constants.FILENAME_BIG_EXT);
		
		
		long countRecord = 0;
		try {

			long seq = 1000000;
			
			List<CustomerInfo> customerList = coreDao.getPersonCustomerByFirstNameEn("NAME_P");

			
			log.debug("customer : " + customerList.size() + " Rows ");
			log.debug("AccountLimit : " + getAccountLimit() + " Rows ");
			log.debug("SubAccountLimit : " + getSubAccountLimit() + " Rows ");
			log.debug("OutstandingLimit : " + getOutstandingLimit() + " Rows ");
			log.debug("ExecutionLimit : " + getExecutionLimit() + " Rows ");
			
			int accountPerAcc = getAccountLimit() / customerList.size();
			int subAccPerAcc = getSubAccountLimit() / getAccountLimit();
			int outstandingPerSubAcc = getOutstandingLimit() / getSubAccountLimit();
			int executionPerSubAcc = getExecutionLimit() / getSubAccountLimit();
			
			
			for (CustomerInfo customerInfo : customerList) {

				String cifCode = customerInfo.getCifCode();
				// Account
				for (int accountIndex = 0; accountIndex < accountPerAcc; accountIndex++) {


					String accNo = "ACC_" + cifCode + "_" + String.format("%02d", accountIndex);
					
					AccountBatch account = getAccount();
					setAccountValue(account, accNo);


					accountToString(bufferedWriterAccount, account);
					accToString(bufferedWriterAcc, account);
					
					// SubAccount
					for (int subAccountIndex = 0; subAccountIndex < subAccPerAcc; subAccountIndex++) {

						SubAccountBatch subAccount = getSubAccount();
						setSubAccountValue(subAccount, account, accNo);

						String dateFormat = sdf.format(startDate.getTime());
						
						subAccountToString(bufferedWriterSubAccount, subAccount);
						subAccToString(bufferedWriterSubAcc, subAccount);
						
						
						// OutStanding
						for (int outStandingIndex = 0; outStandingIndex < outstandingPerSubAcc; outStandingIndex++) {

							OutstandingBatch outstanding = getOutstanding();
							setOutstandingValue(outstanding, dateFormat, subAccount);

							outstandingToString(bufferedWriterOutStanding, outstanding);
							// Position
							subOutstandingToString(bufferedWriterPosition, outstanding);

							countRecord++;

						}

						// Execution
						for (int executionIndex = 0; executionIndex < executionPerSubAcc; executionIndex++) {

							ExecutionBatch execution = getExecution();
							setExecutionValue(execution, dateFormat, subAccount, getTxSeq(seq++));

							executionToString(bufferedWriterExe, execution);
							// Transection
							subExecutionToString(bufferedWriterTx, execution);
						}

					}

					startDate.add(Calendar.DATE, 1);
				}

			}
			
		} catch (IOException e) {
			throw new IOException();
		} finally {

			bufferedWriterAccount.flush();
			bufferedWriterAccount.close();

			bufferedWriterSubAccount.flush();
			bufferedWriterSubAccount.close();
			
			bufferedWriterOutStanding.flush();
			bufferedWriterOutStanding.close();

			bufferedWriterExe.flush();
			bufferedWriterExe.close();

			bufferedWriterAcc.flush();
			bufferedWriterAcc.close();

			bufferedWriterSubAcc.flush();
			bufferedWriterSubAcc.close();
			
			bufferedWriterPosition.flush();
			bufferedWriterPosition.close();

			bufferedWriterTx.flush();
			bufferedWriterTx.close();
		}
		
		return countRecord;	
//		return executeFormDataInDB();
	}
	
	public long executeFormDataInDB() throws ServerEntityServiceException, InfoEntityServiceException, IOException{
		log.debug("Start GenBigDataCore.executeFormDataInDB ");

		Calendar startDate = this.getStartDate();
		Calendar stopDate = this.getStopDate();
		
		List<SubAccountBatch> subAccounts = getSubAccountDB();
		int countSubAccount = subAccounts.size();
		
		log.debug("SubAccount size : " + countSubAccount);
		if(countSubAccount == 0){
			return 0;
		}

		BufferedWriter bufferedWriterPosition = genFilesUtils.getBufferedWriter(
				Constants.DIR_POS, getFilenamePos(), Constants.FILENAME_BIG_EXT);

		BufferedWriter bufferedWriterTx = genFilesUtils.getBufferedWriter(
				Constants.DIR_TX, getFilenameTx(), Constants.FILENAME_BIG_EXT);


		BufferedWriter bufferedWriterOutStanding = genFilesUtils.getBufferedWriter(
				Constants.DIR_OUTSTANDING, getFilenameOutstanding(), Constants.FILENAME_BIG_EXT);

		BufferedWriter bufferedWriterExe = genFilesUtils.getBufferedWriter(
				Constants.DIR_EXECUTION, getFilenameExecution(), Constants.FILENAME_BIG_EXT);
		
		long countRecord = 0;
		try {

			long seq = 1000000;
			int lastMonth = -1;
			int totalDay = stopDate.get(Calendar.DAY_OF_YEAR);
			int executionLimitPerDay = ( getExecutionLimit() == null ? countSubAccount +1 : Math.round(getExecutionLimit() / totalDay));
			int outstandingLimitPerDay = ( getOutstandingLimit() == null ? countSubAccount +1 : Math.round(getOutstandingLimit() / totalDay));
			
			for (int i = 0; i < totalDay; i++) {

				String dateFormat = sdf.format(startDate.getTime());
				
				int currentMonth = startDate.get(Calendar.MONTH);
				if(currentMonth != lastMonth){
					log.debug("Date : " + dateFormat);
					lastMonth = currentMonth;
				}
				int executionCount = 0;
				int outstandingCount = 0;
				for (SubAccountBatch subAccount : subAccounts) {

					boolean isOutstandingSuccess = false;
					boolean isExecutionSuccess = false;
					
//					Position
					OutstandingBatch outstanding = getOutstanding();
					setOutstandingValue(outstanding, dateFormat, subAccount);
					
//					Transection
					ExecutionBatch execution = getExecution();
					setExecutionValue(execution, dateFormat, subAccount, getTxSeq(seq++));

					
					if(outstandingCount < outstandingLimitPerDay){
						outstandingToString(bufferedWriterOutStanding, outstanding);
						subOutstandingToString(bufferedWriterPosition, outstanding);
						
						outstandingCount++;
					} else {
						isOutstandingSuccess = true;
					}
					
					
					if(executionCount < executionLimitPerDay){
						executionToString(bufferedWriterExe, execution);
						subExecutionToString(bufferedWriterTx, execution);
						
//						CP_DEPOSITEXECUTION
						executionCount++;
					} else {
						isExecutionSuccess = true;
					}
					
					countRecord++;
					
					if(isOutstandingSuccess && isExecutionSuccess){
						break;
					}
				}
				startDate.add(Calendar.DATE, 1);

			}
			
		} catch (IOException e) {
			throw new IOException();
		} finally {
			bufferedWriterOutStanding.flush();
			bufferedWriterOutStanding.close();

			bufferedWriterExe.flush();
			bufferedWriterExe.close();
			
			bufferedWriterPosition.flush();
			bufferedWriterPosition.close();

			bufferedWriterTx.flush();
			bufferedWriterTx.close();
		}
		
		return countRecord;
	}
	
}
