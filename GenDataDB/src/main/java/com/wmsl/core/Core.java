package com.wmsl.core;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.apache.commons.lang.exception.ExceptionUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wealth.exception.dao.InfoEntityServiceException;
import com.wealth.exception.dao.ServerEntityServiceException;
//import com.wmsl.App;
import com.wmsl.bean.GenResult;
import com.wmsl.dao.impl.CoreDao;
import com.wmsl.utils.GenFilesUtils;

public abstract class Core implements Runnable{


//	private static final Logger loggerCore = LoggerFactory.getLogger(App.class);
	
	protected final static SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMdd");
	public final static String CURRENT_DATE_FORMAT;
	static {
//		CURRENT_DATE_FORMAT = SDF.format(Calendar.getInstance().getTime());
		Calendar calendar = Calendar.getInstance();
		calendar.set(2015, 11, 31);
		CURRENT_DATE_FORMAT = SDF.format(calendar.getTime());
	}
	
	@Autowired
	protected CoreDao coreDao;

	public void setCoreDao(CoreDao coreDao) {
		this.coreDao = coreDao;
	}

	@Autowired
	protected GenFilesUtils genFilesUtils;

	public void setGenFilesUtils(GenFilesUtils genFilesUtils) {
		this.genFilesUtils = genFilesUtils;
	}


	protected GenResult genResult;
	
	@Override
	public void run() {

		try {
			genResult = this.execute();
		} catch (ServerEntityServiceException | InfoEntityServiceException | IOException e) {
			genResult.setError(" Error : " + e.getMessage());
			genResult.setError(" Stack : " + ExceptionUtils.getStackTrace(e));
		}
		
	}


	public abstract GenResult execute() throws ServerEntityServiceException, InfoEntityServiceException, IOException;
}
