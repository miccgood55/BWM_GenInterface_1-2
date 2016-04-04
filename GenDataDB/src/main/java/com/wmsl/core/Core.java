package com.wmsl.core;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;

import com.wealth.exception.dao.InfoEntityServiceException;
import com.wealth.exception.dao.ServerEntityServiceException;
import com.wmsl.bean.GenResult;
import com.wmsl.dao.impl.CoreDao;
import com.wmsl.utils.GenFilesUtils;

public abstract class Core {

	protected final static SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
	public final static String CURRENT_DATE_FORMAT = SDF.format(Calendar.getInstance().getTime());

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

	public abstract List<GenResult> execute() throws ServerEntityServiceException, InfoEntityServiceException, IOException;
}
