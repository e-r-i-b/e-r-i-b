package com.rssl.phizic.business.persons;

import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.person.PersonDocument;

import java.util.Calendar;

/**
 * @author hudyakov
 * @ created 08.05.2008
 * @ $Author$
 * @ $Revision$
 */
public class PersonDocumentImpl implements PersonDocument
{
	private Long id;
	private PersonDocumentType documentType;
	private String documentName;
	private String documentNumber;
	private String documentSeries;
	private Calendar documentIssueDate;
	private String documentIssueBy;
	private String documentIssueByCode;
	private boolean documentMain;
	private Calendar documentTimeUpDate;
	private boolean documentIdentify;

	public PersonDocumentImpl(){};

	public PersonDocumentImpl(ClientDocument clientDocument)
	{
		this.documentName = clientDocument.getDocTypeName();
		this.documentNumber = clientDocument.getDocNumber();
        this.documentSeries = clientDocument.getDocSeries();
		this.documentType = PersonDocumentType.valueOf(clientDocument.getDocumentType());
		this.documentIssueDate = clientDocument.getDocIssueDate();
		this.documentIssueBy = clientDocument.getDocIssueBy();
		this.documentIssueByCode = clientDocument.getDocIssueByCode();
		this.documentTimeUpDate = clientDocument.getDocTimeUpDate();
		this.documentIdentify = clientDocument.isDocIdentify();
	 }

	public PersonDocumentImpl(PersonDocument personDocument)
	{
		this.id = personDocument.getId();
		this.documentName =  	personDocument.getDocumentName();
		this.documentNumber = personDocument.getDocumentNumber();
        this.documentSeries = personDocument.getDocumentSeries();
		this.documentType = personDocument.getDocumentType();
		this.documentIssueDate =personDocument.getDocumentIssueDate();
		this.documentIssueBy = personDocument.getDocumentIssueBy();
		this.documentIssueByCode = personDocument.getDocumentIssueByCode();
		this.documentTimeUpDate = personDocument.getDocumentTimeUpDate();
		this.documentMain = personDocument.getDocumentMain();
		this.documentIdentify = personDocument.isDocumentIdentify();
	 }

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public PersonDocumentType getDocumentType()
	{
		return documentType;
	}

	public void setDocumentType(PersonDocumentType documentType)
	{
		this.documentType = documentType;
	}

	public String getDocumentName()
	{
		return documentName;
	}

	public void setDocumentName(String documentName)
	{
		 this.documentName = documentName;
	}

	public String getDocumentNumber()
	{
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber)
	{
		this.documentNumber = documentNumber;
	}

	public String getDocumentSeries()
	{
		return documentSeries;
	}

	public void setDocumentSeries(String documentSeries)
	{
		this.documentSeries = documentSeries;
	}

	public Calendar getDocumentIssueDate()
	{
		return documentIssueDate;
	}

	public void setDocumentIssueDate(Calendar documentIssueDate)
	{
		this.documentIssueDate = documentIssueDate;
	}

	public String getDocumentIssueBy()
	{
		return documentIssueBy;
	}

	public void setDocumentIssueBy(String documentIssueBy)
	{
		this.documentIssueBy = documentIssueBy;
	}

	public String getDocumentIssueByCode()
	{
		return documentIssueByCode;
	}

	public void setDocumentIssueByCode(String documentIssueByCode)
	{
		this.documentIssueByCode = documentIssueByCode;
	}

	public boolean getDocumentMain()
	{
		return documentMain;
	}
	
	public void setDocumentMain(boolean documentMain)
	{
		this.documentMain = documentMain;
	}

	public Calendar getDocumentTimeUpDate()
	{
		return documentTimeUpDate;
	}

	public void setDocumentTimeUpDate(Calendar documentTimeUpDate)
	{
		this.documentTimeUpDate = documentTimeUpDate;
	}

	public boolean isDocumentIdentify()
	{
		return documentIdentify;
	}

	public void setDocumentIdentify(boolean documentIdentify)
	{
		this.documentIdentify = documentIdentify;
	}

	/*
	* PersonDocument пуст, если пусты все его смысловые поля (все, кроме типа)
	*/
	public boolean isEmpty()
	{
		return (StringHelper.isEmpty(getDocumentName()) && StringHelper.isEmpty(getDocumentNumber())
				&& StringHelper.isEmpty(getDocumentSeries()) && StringHelper.isEmpty(getDocumentIssueBy())
				&& StringHelper.isEmpty(getDocumentIssueByCode()) && getDocumentIssueDate() == null && getDocumentTimeUpDate() == null);
	}
}

