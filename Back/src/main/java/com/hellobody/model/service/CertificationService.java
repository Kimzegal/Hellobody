package com.hellobody.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hellobody.model.dao.CertificationDao;
import com.hellobody.model.dto.Certification;

@Service
public class CertificationService {
	private CertificationDao cDao;
	
	public CertificationService(@Autowired CertificationDao cDao) {
		this.cDao = cDao;
	}
	
	public Certification selectCertByExName(String exName) {
		return cDao.checkCertificationStandard(exName);
	}
}
