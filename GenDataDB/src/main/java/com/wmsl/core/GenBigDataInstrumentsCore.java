package com.wmsl.core;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.wealth.bwm.batch.impl.entity.cp.account.AccountBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.MarginAccountBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.SubAccountBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.SubMarginAccountBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.execution.ExecutionBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.outstanding.MarginOutstandingBatch;
import com.wealth.bwm.batch.impl.entity.cp.account.outstanding.OutstandingBatch;
import com.wealth.bwm.common.impl.entity.instrument.Instrument;
import com.wmsl.Constants;

@Component
public abstract class GenBigDataInstrumentsCore extends GenBigDataBizCore {

	private static final Logger log = LoggerFactory.getLogger(GenBigDataInstrumentsCore.class);

	private static List<Instrument> instrumentList = new ArrayList<Instrument>();
	
	private static int INSTRUMENT_COUNT = 0;
	
	@Override
	public Integer getInstrumentId() {

		int instrumentMaxIndex =  instrumentList.size() - 1;
		Instrument instrument = instrumentList.get(INSTRUMENT_COUNT % instrumentMaxIndex);
		
		INSTRUMENT_COUNT++;
		return instrument.getInstrumentId();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		
		String instrumentCode = this.getInstrumentCode(); 
		this.setInstrumentCode(null);
		super.afterPropertiesSet();
		
		List<Instrument> instrumentList = coreDao.getInstrumentBySymbol(instrumentCode);
		log.debug(instrumentCode + " : " + instrumentList.size() + " Rows");
		
	}
	
}
