package com.wmsl.core;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException;
import org.springframework.stereotype.Component;

import com.wealth.bwm.common.impl.entity.instrument.Instrument;

@Component
public abstract class GenBigDataInstrumentsCore extends GenBigDataBizCore {

	private static final Logger log = LoggerFactory.getLogger(GenBigDataInstrumentsCore.class);

	private static List<Instrument> instrumentList = new ArrayList<Instrument>();
	
	private static int INSTRUMENT_COUNT = 0;
	
	@Override
	public Integer getInstrumentId() {

		int instrumentMaxIndex =  instrumentList.size();
		Instrument instrument = instrumentList.get(INSTRUMENT_COUNT % instrumentMaxIndex);
		INSTRUMENT_COUNT++;
		return instrument.getInstrumentId();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		
		String instrumentCode = this.getInstrumentCode(); 
		this.setInstrumentCode(null);
		super.afterPropertiesSet();
		
		instrumentList = coreDao.getInstrumentBySymbol(instrumentCode);
		log.debug(instrumentCode + " : " + instrumentList.size() + " Rows");
		
		if(instrumentList.size() <= 0){
			throw new DataSourceLookupFailureException("instrumentCode : " + instrumentCode + "% Not Found In DB");
		}
		
	}
	
}
