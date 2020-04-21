package com.covid.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.covid.rest.model.Case;
import com.covid.rest.service.CaseService;

@RestController
public class CaseController {
	
	@Autowired
	private CaseService caseService;
	
	public void setCaseService(CaseService caseService) {
		this.caseService = caseService;
	}
	
	@RequestMapping(value="/case", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Void> addCaseByCountry(@RequestBody Case caseObj){
		if(caseObj == null) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}else if(caseService.isCaseExistForCountry(caseObj)) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}else {
			caseService.addCaseByCountry(caseObj);
			HttpHeaders header = new HttpHeaders();
			header.add("Case Added  - ", String.valueOf(caseObj.getCountryId()));
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}
	}
	
	@RequestMapping(value="/case/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Case> findCasesByCountryId(@PathVariable("id") Long countryId){
		Case caseObj = caseService.findCasesByCountryId(countryId);
		if(caseObj == null) {
			return new ResponseEntity<Case>(HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<Case>(caseObj, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/case", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Case>> getAllCases(){
		List<Case> dataList = caseService.getAllCases();
		if(dataList.isEmpty()) {
			return new ResponseEntity<List<Case>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Case>>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/case", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<Case> updateCaseByCountryId(@PathVariable("id") Long countryId, @RequestBody Case caseData){
		Case caseObj = caseService.findCasesByCountryId(countryId);
		if(caseObj == null) {
			return new ResponseEntity<Case>(HttpStatus.NOT_FOUND);
		}else {
			caseObj.setCountryName(caseData.getCountryName()); caseObj.setTotalCases(caseData.getTotalCases());
			caseObj.setNewCases(caseData.getNewCases()); caseObj.setTotalDeaths(caseData.getTotalDeaths());
			caseObj.setNewDeaths(caseData.getNewDeaths()); caseObj.setTotalRecovered(caseData.getTotalRecovered());
			caseObj.setActiveCases(caseData.getActiveCases()); caseObj.setSeriousCritical(caseData.getSeriousCritical());
			caseObj.setTotCasesMpop(caseData.getTotCasesMpop()); caseObj.setDeathsMpop(caseData.getDeathsMpop());
			caseObj.setTotalTests(caseData.getTotalTests()); caseObj.setTestsMpop(caseData.getTestsMpop());
		}
		caseService.updateCaseByCountryId(caseObj);
		return new ResponseEntity<Case>(caseObj, HttpStatus.OK);
	}

	@RequestMapping(value="case/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Case> deleteCaseByCountryId(@PathVariable("id") Long countryId){
		Case caseObj = caseService.findCasesByCountryId(countryId);
		if(caseObj == null) {
			return new ResponseEntity<Case>(HttpStatus.NOT_FOUND);
		}
		caseService.deleteCaseByCountryId(countryId);
		return new ResponseEntity<Case>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value="case", method = RequestMethod.DELETE, produces = "applicatioin/json")
	public ResponseEntity<Case> deleteAllCases(){
		caseService.deleteAllCases();
		return new ResponseEntity<Case>(HttpStatus.NO_CONTENT);
	}
}
