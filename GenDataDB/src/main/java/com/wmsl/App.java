package com.wmsl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wealth.exception.dao.InfoEntityServiceException;
import com.wealth.exception.dao.ServerEntityServiceException;
import com.wmsl.bean.GenResult;
import com.wmsl.core.Core;
import com.wmsl.utils.GenFilesUtils;

public class App {

	private static final Logger logger = LoggerFactory.getLogger(App.class);

	private static ApplicationContext appContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

	public static void main(String[] args) throws ServerEntityServiceException, InfoEntityServiceException, IOException {

		logger.debug("App.Start");

		Properties config = (Properties) appContext.getBean("props");
		GenFilesUtils genFilesUtils = (GenFilesUtils) appContext.getBean("genFilesUtils");
		List<String> beanList = getPropertyList(config, "beanlist");

		List<String> logs = new ArrayList<String>();
		for (String beanNames : beanList) {
			boolean isProcess = false;
			for (String beanName : beanNames.split("\\|")) {
				if (beanName != null && !beanName.equals("")) {

					String isEnableKey = String.valueOf(config.get(beanName));
					boolean isEnable = Boolean.valueOf((String) config.get(isEnableKey));

					if (isEnable) {

						long t1 = System.currentTimeMillis();

						Core core = (Core) appContext.getBean(beanName);
						GenResult genResult = core.execute();

						long t2 = System.currentTimeMillis();

						logs.add(beanName + " : " + (t2 - t1) / 1000 + " sec");
						logs.add("Account : " + genResult.getAccountCount() + " Rows");
						logs.add("SubAccount : " + genResult.getSubAccountCount() + " Rows");
						logs.add("Outstanding : " + genResult.getOutstandingCount() + " Rows");
						logs.add("Transection : " + genResult.getTransectionCount() + " Rows");

						isProcess = true;
					}
				}
			}
			if (isProcess)
				logs.add(" ----------------------------------- ");
		}

		BufferedWriter bw = genFilesUtils.getBufferedWriter(Constants.DIR_LOG, "log", Constants.FILENAME_TXT_EXT);
		
		logger.debug(" =================================== ");

		for (String log : logs) {
			logger.debug(log);
			bw.write(log);
			bw.newLine();
		}

		bw.flush();
		bw.close();
		logger.debug(" =================================== ");

		logger.debug("App.Stop");
	}

	public static List<String> getPropertyList(Properties properties, String name) {
		List<String> result = new ArrayList<String>();
		for (Entry<Object, Object> entry : properties.entrySet()) {
			if (((String) entry.getKey()).matches(name + "(.*)")) {
				result.add((String) entry.getValue());
			}
		}
		return result;
	}
}
