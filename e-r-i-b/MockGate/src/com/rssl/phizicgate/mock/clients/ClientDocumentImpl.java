package com.rssl.phizicgate.mock.clients;

import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.gate.clients.ClientDocument;

import java.util.Calendar;

/**
 * @author egorova
 * @ created 31.08.2010
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

	public ClientDocumentType getDocumentType()
	{
		return documentType;
	}

	public void setDocumentType(ClientDocumentType documentType)
	{
		this.documentType = documentType;
	}

	public String getDocTypeName()
	{
		return docTypeName;
	}

	public void setDocTypeName(String docTypeName)
	{
		this.docTypeName = docTypeName;
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
