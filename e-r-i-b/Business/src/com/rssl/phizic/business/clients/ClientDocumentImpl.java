package com.rssl.phizic.business.clients;

import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.person.PersonDocumentType;

import java.util.Calendar;

/**
 * @author Omeliyanchuk
 * @ created 06.06.2008
 * @ $Author$
 * @ $Revision$
 */

public class ClientDocumentImpl implements ClientDocument
{
   ClientDocumentType documentType;
   String docTypeName;
   String docNumber;
   String docSeries;
   Calendar docIssueDate;
   String docIssueBy;
   String docIssueByCode;
   Calendar docTimeUpDate;
	boolean docIdentify;

	public ClientDocumentImpl()
	{

	}

	public ClientDocumentImpl(String series, ClientDocumentType documentType)
	{
		setDocSeries(series);
		setDocumentType(documentType);
	}

	public ClientDocumentImpl(String series, String number, ClientDocumentType documentType)
	{
		setDocSeries(series);
		setDocNumber(number);
		setDocumentType(documentType);
	}

	public ClientDocumentImpl(PersonDocument personDocument)
	{
		setDocNumber(personDocument.getDocumentNumber());
		setDocSeries(personDocument.getDocumentSeries());
		setDocIssueBy(personDocument.getDocumentIssueBy());
		setDocIssueByCode(personDocument.getDocumentIssueByCode());
  		setDocIssueDate(personDocument.getDocumentIssueDate());
		setDocTypeName(personDocument.getDocumentName());
		setDocTimeUpDate(personDocument.getDocumentTimeUpDate());

		PersonDocumentType type = personDocument.getDocumentType();
		if (type != null)
		{
			setDocumentType(PersonDocumentType.valueFrom(type));
		}
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

	public Calendar getDocIssueDate()
	{
		return docIssueDate;
	}

	public void setDocIssueDate(Calendar docIssueDate)
	{
		this.docIssueDate = docIssueDate;
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

	public String getDocTypeName()
	{
		return docTypeName;
	}

	public void setDocTypeName(String docTypeName)
	{
		this.docTypeName = docTypeName;
	}

	public ClientDocumentType getDocumentType()
	{
		return documentType;
	}

	public void setDocumentType(ClientDocumentType documentType)
	{
		this.documentType = documentType;
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
		return docIdentify;
	}

	public void setDocIdentify(boolean docIdentify)
	{
		this.docIdentify = docIdentify;
	}
}
