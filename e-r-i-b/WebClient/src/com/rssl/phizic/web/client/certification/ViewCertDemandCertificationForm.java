package com.rssl.phizic.web.client.certification;

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.certification.CertDemand;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author Omeliyanchuk
 * @ created 22.05.2007
 * @ $Author$
 * @ $Revision$
 */

public class ViewCertDemandCertificationForm extends EditFormBase
{
	private ActivePerson person;
	private String certAnswer;
	private String certAnswerFile;
	private Boolean isInstall = false;
	private CertDemand demand;

	public CertDemand getDemand()
	{
		return demand;
	}

	public void setDemand(CertDemand demand)
	{
		this.demand = demand;
	}

	public Boolean getInstall()
	{
		return isInstall;
	}

	public void setInstall(Boolean install)
	{
		isInstall = install;
	}

	public String getCertAnswer()
	{
		return certAnswer;
	}

	public void setCertAnswer(String certAnswer)
	{
		this.certAnswer = certAnswer;
	}

	public String getCertAnswerFile()
	{
		return certAnswerFile;
	}

	public void setCertAnswerFile(String certAnswerFile)
	{
		this.certAnswerFile = certAnswerFile;
	}

	public ActivePerson getPerson()
	{
		return person;
	}

	public void setPerson(ActivePerson person)
	{
		this.person = person;
	}
}
