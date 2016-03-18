package com.wmsl.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.wealth.bwm.common.impl.entity.instrument.Instrument;
import com.wealth.bwm.impl.entity.cp.account.MarginAccount;
import com.wealth.dao.impl.GenericDAOHibernate;
import com.wmsl.bean.dao.CustomerInfo;
import com.wmsl.dao.ICoreDao;

@SuppressWarnings("deprecation")
public class CoreDao extends GenericDAOHibernate<Integer, Integer> implements ICoreDao {

	private static final long serialVersionUID = -6317417784671642751L;

	public Integer getNextOutstandingId() {

		Session session = this.getSession();
		Query query = session.createQuery("SELECT MAX(o.outstandingId) + 1 from Outstanding o ");

		Integer max = (Integer) query.uniqueResult();
		return max;
	}

	public Integer getNextExecutionId() {

		Session session = this.getSession();
		session.beginTransaction();
		Query query = session.createQuery("SELECT MAX(o.executionId) + 1 from Execution o ");

		Integer max = (Integer) query.uniqueResult();
		return (max == null ? 1 : max);
	}

	public Integer getInstrumentId(String symbol) {

		Session session = this.getSession();
		session.beginTransaction();
		Query query = session.createQuery("SELECT i.instrumentId from Instrument i where i.symbol = :symbol")
				.setParameter("symbol", symbol);

		Integer instrumentId = (Integer) query.uniqueResult();

		return instrumentId;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerInfo> getPersonCustomerByFirstNameEn(String firstNameEn) {
		List<CustomerInfo> result = new ArrayList<CustomerInfo>();
		try {
			Session session = this.getSession();

			//SELECT *
			//FROM CP_PERSONCUSTOMER p
			//JOIN CP_CUSTOMER c on (p.CUSTOMERID = c.CUSTOMERID)
			//JOIN CP_IDTYPE i on (i.CUSTOMERID = c.CUSTOMERID)
			//JOIN CP_CARDTYPE t on (i.CARDTYPEID = t.CARDTYPEID)
			//WHERE FIRSTNAMEEN like 'NAME_P%'

			String command = "SELECT "
					+ "p.CUSTOMERID, p.FIRSTNAMEEN, c.CIFCODE, i.IDTYPEID, i.IDNUMBER, i.CARDTYPEID, t.CARDTYPECODE "
					+ "FROM CP_PERSONCUSTOMER p "
					+ "JOIN CP_CUSTOMER c on (p.CUSTOMERID = c.CUSTOMERID) "
					+ "JOIN CP_IDTYPE i on (i.CUSTOMERID = c.CUSTOMERID) "
					+ "JOIN CP_CARDTYPE t on (i.CARDTYPEID = t.CARDTYPEID) "
					
					+ "WHERE FIRSTNAMEEN like :firstNameEn ";
					
			Query query = session.createSQLQuery(command);
			query.setParameter("firstNameEn", firstNameEn + "%");
			List<Object[]> lists = query.list();
			
			for (Object[] object : lists) {
				CustomerInfo customerInfo = new CustomerInfo();
				customerInfo.setCustomerId((Integer) object[0]);
				customerInfo.setFirstNameEn((String) object[1]);
				customerInfo.setCifCode((String) object[2]);
				customerInfo.setTypeId((Integer) object[3]);
				customerInfo.setIdNember((String) object[4]);
				customerInfo.setCardTypeId((Integer) object[5]);
				customerInfo.setCardTypeCode((String) object[6]);
				
				result.add(customerInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MarginAccount> getMarginAccountByCode(String accountNumber) {
		List<MarginAccount> result = new ArrayList<MarginAccount>();
		try {
			Session session = this.getSession();

//			Query query = session.createQuery("SELECT i.instrumentId from Instrument i where i.symbol = :symbol")
			String command = "select m from MarginAccount m WHERE m.accountNumber like :accountNumber ";
			Query query = session.createQuery(command);
			query.setParameter("accountNumber", accountNumber + "%");
			result = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Instrument> getInstrumentBySymbol(String symbol) {
		List<Instrument> result = new ArrayList<Instrument>();
		try {
			Session session = this.getSession();
			String command = "select i from Instrument i WHERE i.symbol like :symbol ";
			Query query = session.createQuery(command);
			query.setParameter("symbol", symbol + "%");
			result = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
