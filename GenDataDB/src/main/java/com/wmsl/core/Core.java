package com.wmsl.core;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.wealth.exception.dao.InfoEntityServiceException;
import com.wealth.exception.dao.ServerEntityServiceException;
import com.wmsl.dao.impl.CoreDao;
import com.wmsl.utils.GenFilesUtils;

public abstract class Core {

	protected final static SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMdd");
	public final static String CURRENT_DATE_FORMAT = SDF.format(new Date());

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

	public abstract long execute() throws ServerEntityServiceException, InfoEntityServiceException, IOException;
}
