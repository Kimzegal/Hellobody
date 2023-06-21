package com.hellobody.model.dao;

import com.hellobody.model.dto.Certification;

public interface CertificationDao {

	public Certification checkCertificationStandard(String exName);

	//운동의 환산 점수를 가져옵니다.
	public int selectExRate(String exName);
	
	public int insertCertificationStandard(Certification cert);

	public int updateCertificationStandard(Certification cert);


}
