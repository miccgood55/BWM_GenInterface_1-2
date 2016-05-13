package com.wmsl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wealth.exception.dao.InfoEntityServiceException;
import com.wealth.exception.dao.ServerEntityServiceException;
import com.wmsl.bean.GenResult;
import com.wmsl.core.Core;
import com.wmsl.core.GenBigDataCore;
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

		String startYearConfig = String.valueOf(config.get("start.year"));

		long t1 = System.currentTimeMillis();
		
		List<Thread> threadList = new ArrayList<Thread>();
		for (String startYear : startYearConfig.split("\\|")) {

			for (String beanNames : beanList) {
				boolean isProcess = false;
				for (String beanName : beanNames.split("\\|")) {
					if (beanName != null && !beanName.equals("")) {
						String isEnableKey = String.valueOf(config.get(beanName));
						boolean isEnable = Boolean.valueOf((String) config.get(isEnableKey));
						if (isEnable) {

							try {

								Core core = (Core) appContext.getBean(beanName);

								logs.add("Process Name : " + core.getClass().getName());

								if(core instanceof GenBigDataCore){
									((GenBigDataCore)core).setYearIndex(startYear);
								}
								
								GenResult genResult = core.execute();

//								for (GenResult genResult : genResultList) {

									logs.add(" ----------------------------- ");
									logs.add(" ====== " + beanName + " ====== ");
									logs.add(" === Year : " + genResult.getYear() .getTime() + " === ");
									logs.add(" === Time : " + genResult.getTime() + " sec === ");
									logs.add(" ----------------------------- ");

									logs.add("Account : " + genResult.getAccountCount() + " Rows");
									logs.add("SubAccount : " + genResult.getSubAccountCount() + " Rows");
									logs.add("Outstanding : " + genResult.getOutstandingCount() + " Rows");
									logs.add("Transection : " + genResult.getTransectionCount() + " Rows");
									logs.add("Total : " + genResult.getTotalCount() + " Rows");

									logs.add(genResult.getError());
									logs.add(genResult.getStack());
									
//								}

								isProcess = true;
							} catch (Exception e) {
								logs.add(beanName + " Error : " + e.getMessage());
								logs.add(beanName + " Stack : " + ExceptionUtils.getStackTrace(e));
								logger.error(beanName, "", e);

							}
						}
					}

					if (isProcess)
						logs.add(" ------------------------------------------ ");
					isProcess = false;
				}
			}
		}

		long t2 = System.currentTimeMillis();
		
//		genResult.setTime((t2 - t1) / 1000);

		logs.add(" === Total Time : " + (t2 - t1) / 1000 / 60 + " min === ");
		

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
