package com.wmsl.dao;

import java.util.List;

import com.wealth.bwm.common.impl.entity.instrument.Instrument;
import com.wealth.bwm.impl.entity.cp.account.MarginAccount;
import com.wealth.dao.IGenericDAO;
import com.wmsl.bean.dao.CustomerInfo;

public interface ICoreDao extends IGenericDAO<Integer, Integer> {
	public Integer getNextOutstandingId();
	public Integer getNextExecutionId();
	public Integer getInstrumentId(String symbol);
	List<CustomerInfo> getPersonCustomerByFirstNameEn(String firstNameEn);
	List<MarginAccount> getMarginAccountByCode(String accountCode);
	List<Instrument> getInstrumentBySymbol(String symbol);
}
