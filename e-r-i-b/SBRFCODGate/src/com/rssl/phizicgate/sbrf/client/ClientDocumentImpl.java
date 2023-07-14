package com.rssl.phizicgate.sbrf.client;

import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.common.types.client.ClientDocumentType;

import java.util.Calendar;

/**
 * @author Omeliyanchuk
 * @ created 07.07.2008
 * @ $Author$
 * @ $Revision$
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
   private Calendar docTimeUpDate;
	private boolean docIdentify;

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
