package com.rssl.phizic.web.client.certification;

import com.rssl.phizic.business.certification.CertDemand;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.Date;

/**
 * Created by IntelliJ IDEA. User: Omeliyanchuk Date: 17.11.2006 Time: 17:01:55 To change this template use
 * File | Settings | File Templates.
 */
public class EditCertDemandCertificationForm extends ActionFormBase
{
	private ActivePerson person;
	private Date date;
	private String dateString;
	private Long personId;
	private Long id;
	private String certRequest;
	private String certRequestFile;
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

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public ActivePerson getPerson()
	{
		return person;
	}

	public void setPerson(ActivePerson person)
	{
		this.person = person;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public void setDateString(String date)
	{
		this.dateString = date;
	}

	public String getDateString()
	{
		return dateString;		
	}

	public void setPersonId(Long personId)
	{
		this.personId = personId;
	}

	public Long getPersonId()
	{
		return personId;
	}

	public String getCertRequest()
	{
		return certRequest;
	}

	public void setCertRequest(String certRequest)
	{
		this.certRequest = certRequest;
	}

	public String getCertRequestFile()
	{
		return certRequestFile;
	}

	public void setCertRequestFile(String certRequestFile)
	{
		this.certRequestFile = certRequestFile;
	}
}
