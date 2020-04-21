package com.covid.rest.service;

import java.util.List;

import com.covid.rest.model.Case;

public interface CaseService {
	/*Country Wise Case CRUD Operation*/
	public Case findCasesByCountryId(Long countryId);
	public Case findCasesByCountryName(String countryName);
	public List<Case> getAllCases();
	public void addCaseByCountry(Case caseObj);
	public void updateCaseByCountryId(Case caseObj);
	public void deleteCaseByCountryId(Long countryId);
	boolean isCaseExistForCountry(Case caseObj);
	public void deleteAllCases();
}
