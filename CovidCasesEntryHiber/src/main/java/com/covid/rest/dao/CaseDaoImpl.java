package com.covid.rest.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.covid.rest.model.Case;

public class CaseDaoImpl implements CaseDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public Case findCasesByCountryId(Long countryId) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Case caseObj = new Case();
		try {
			caseObj = (Case) session.get(Case.class, countryId);
			transaction.commit();
			session.close();
		}catch(Exception e) {
			transaction.rollback();
			session.close();
		}
		return caseObj;
	}

	@Override
	public Case findCasesByCountryName(String countryName) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Case caseObj = new Case();
		String hql ="from Case where countryName = ?";
		try {
			Query query = session.createQuery(hql);
			query.setParameter(0, countryName);
			caseObj = (Case) query.uniqueResult();
			transaction.commit();
			session.close();
		}catch (Exception e) {
			transaction.rollback();
			session.close();
		}
		return caseObj;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Case> getAllCases() {
		Session session = sessionFactory.openSession();
		List<Case> caseList = new ArrayList<Case>();
		caseList = session.createQuery("from Case").list();
		return caseList;
	}

	@Override
	public void addCaseByCountry(Case caseObj) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		if(caseObj != null) {
			try {
				session.save(caseObj);
				transaction.commit();
			}catch (Exception e) {
				transaction.rollback();
				session.close();
			}
		}
	}

	@Override
	public void updateCaseByCountryId(Case caseObj) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		if(caseObj != null) {
			try {
				session.update(caseObj);
				transaction.commit();
			}catch (Exception e) {
				transaction.rollback();
				session.close();
			}
		}
	}

	@Override
	public void deleteCaseByCountryId(Long countryId) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Case caseObj = new Case();
		try {
			caseObj = (Case) session.get(Case.class, countryId);
			session.delete(caseObj);
			transaction.commit();
			session.close();
		}catch (Exception e) {
			transaction.rollback();
			session.close();
		}
	}

	@Override
	public boolean isCaseExistForCountry(Case caseObj) {
		return findCasesByCountryName(caseObj.getCountryName())!= null;
	}

	@Override
	public void deleteAllCases() {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			session.createQuery("delete from Case").executeUpdate();
			transaction.commit();
			session.close();
		}catch (Exception e) {
			transaction.rollback();
			session.close();
		}
	}
}
