package com.rssl.phizicgate.esberibgate.types;

import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.common.types.client.ClientDocumentType;

import java.util.Calendar;

/**
 @author Pankin
 @ created 22.09.2010
 @ $Author$
 @ $Revision$
 */
public class ClientDocumentImpl implements ClientDocument
{
	private ClientDocumentType documentType;
	private String docTypeName;
	private String docNumber;
	private String docSeries;
	private Calendar docIssueDate;
	private String docIssueBy;
	private String docIssueByCode;
	private boolean docIdentify;
	private Calendar docTimeUpDate;

	public ClientDocumentType getDocumentType()
	{
		return documentType;
	}

	public String getDocTypeName()
	{
		return docTypeName;
	}

	public String getDocNumber()
	{
		return docNumber;
	}

	public String getDocSeries()
	{
		return docSeries;
	}

	public Calendar getDocIssueDate()
	{
		return docIssueDate;
	}

	public String getDocIssueBy()
	{
		return docIssueBy;
	}

	public String getDocIssueByCode()
	{
		return docIssueByCode;
	}

	public boolean isDocIdentify()
	{
		return docIdentify;
	}

	public Calendar getDocTimeUpDate()
	{
		return docTimeUpDate;
	}

	public void setDocumentType(ClientDocumentType documentType)
	{
		this.documentType = documentType;
	}

	public void setDocTypeName(String docTypeName)
	{
		this.docTypeName = docTypeName;
	}

	public void setDocNumber(String docNumber)
	{
		this.docNumber = docNumber;
	}

	public void setDocSeries(String docSeries)
	{
		this.docSeries = docSeries;
	}

	public void setDocIssueDate(Calendar docIssueDate)
	{
		this.docIssueDate = docIssueDate;
	}

	public void setDocIssueBy(String docIssueBy)
	{
		this.docIssueBy = docIssueBy;
	}

	public void setDocIssueByCode(String docIssueByCode)
	{
		this.docIssueByCode = docIssueByCode;
	}

	public void setDocIdentify(boolean docIdentify)
	{
		this.docIdentify = docIdentify;
	}

	public void setDocTimeUpDate(Calendar docTimeUpDate)
	{
		this.docTimeUpDate = docTimeUpDate;
	}
}
