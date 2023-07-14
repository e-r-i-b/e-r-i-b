package com.rssl.phizic.common.type;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.person.PersonDocumentType;

import java.util.Calendar;

/**
 * User: moshenko
 * Date: 18.03.2013
 * Time: 10:23:37
 * —тарые идентификационные данные клиента
 */
public class PersonOldIdentity implements PersonIdentity
{
	private Long id;
	private Person person;
	private String firstName;
	private String surName;
	private String patrName;
	private Calendar birthDay;
	private PersonDocumentType docType;
	private String docName;
	private String docNumber;
	private String docSeries;
	private Calendar docIssueDate;
	private String docIssueBy;
	private String docIssueByCode;
	private boolean isDocMain;
	private Calendar docTimeUpDate;
	private boolean isDocIdentify = true;
	private CommonLogin employee;//—отрудник последний раз измен€вший запись
	private Calendar dateChange;//дата последнего изменени€
	private PersonOldIdentityStatus status = PersonOldIdentityStatus.NOT_ACTIVE;
	private String region;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Person getPerson()
	{
		return person;
	}

	public void setPerson(Person person)
	{
		this.person = person;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getSurName()
	{
		return surName;
	}

	public void setSurName(String surName)
	{
		this.surName = surName;
	}

	public String getPatrName()
	{
		return patrName;
	}

	public void setPatrName(String patrName)
	{
		this.patrName = patrName;
	}

	public Calendar getBirthDay()
	{
		return birthDay;
	}

	public void setBirthDay(Calendar birthDay)
	{
		this.birthDay = birthDay;
	}

	public PersonDocumentType getDocType()
	{
		return docType;
	}

	public void setDocType(PersonDocumentType docType)
	{
		this.docType = docType;
	}

	public String getDocName()
	{
		return docName;
	}

	public void setDocName(String docName)
	{
		this.docName = docName;
	}

	public String getDocNumber()
	{
		return docNumber;
	}

	public void setDocNumber(String docNumber)
	{
		this.docNumber = docNumber;
	}

	public String getDocSeries()
	{
		return docSeries;
	}

	public void setDocSeries(String docSeries)
	{
		this.docSeries = docSeries;
	}

	public Calendar getDocIssueDate()
	{
		return docIssueDate;
	}

	public void setDocIssueDate(Calendar docIssueDate)
	{
		this.docIssueDate = docIssueDate;
	}

	public String getDocIssueBy()
	{
		return docIssueBy;
	}

	public void setDocIssueBy(String docIssueBy)
	{
		this.docIssueBy = docIssueBy;
	}

	public String getDocIssueByCode()
	{
		return docIssueByCode;
	}

	public void setDocIssueByCode(String docIssueByCode)
	{
		this.docIssueByCode = docIssueByCode;
	}

	public boolean isDocMain()
	{
		return isDocMain;
	}

	public void setDocMain(boolean docMain)
	{
		isDocMain = docMain;
	}

	public Calendar getDocTimeUpDate()
	{
		return docTimeUpDate;
	}

	public void setDocTimeUpDate(Calendar docTimeUpDate)
	{
		this.docTimeUpDate = docTimeUpDate;
	}

	public boolean isDocIdentify()
	{
		return isDocIdentify;
	}

	public void setDocIdentify(boolean docIdentify)
	{
		isDocIdentify = docIdentify;
	}

	public CommonLogin getEmployee()
	{
		return employee;
	}

	public void setEmployee(CommonLogin employee)
	{
		this.employee = employee;
	}

	public Calendar getDateChange()
	{
		return dateChange;
	}

	public void setDateChange(Calendar dateChange)
	{
		this.dateChange = dateChange;
	}

	public PersonOldIdentityStatus getStatus()
	{
		return status;
	}

	public void setStatus(PersonOldIdentityStatus status)
	{
		this.status = status;
	}

	public String getRegion()
	{
		return region;
	}

	public void setRegion(String region)
	{
		this.region = region;
	}
}
