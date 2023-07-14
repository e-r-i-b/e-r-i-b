package com.rssl.phizic.web.gate.services.types;

import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.gate.clients.ClientDocument;

import java.util.Calendar;

/**
 * @author: Pakhomova
 * @created: 03.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class ClientDocumentImpl implements ClientDocument
{
	private ClientId longId;
	private String docNumber;
	private String docSeries;
	private Calendar docIssueDate;
	private String docIssueBy;
	private String docTypeName;
	private ClientDocumentType documentType;
	private String docIssueByCode;
	private Calendar docTimeUpDate;
	private boolean docIdentify;

	private Long personId;
	private Long paperKind;

	public String getDocTypeName()
	{
		return docTypeName;
	}

	public ClientDocumentType getDocumentType()//тип документа
	{
		return documentType;
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

	public void setDocIssueByCode(String docIssueByCode)
	{
		this.docIssueByCode = docIssueByCode;
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

	public ClientId getLongId()
	{
		return longId;
	}

	public void setLongId(ClientId longId)
	{
		this.longId = longId;
	}

	public void setDocTypeName(String docTypeName)
	{
		this.docTypeName = docTypeName;
	}

	public void setDocumentType(ClientDocumentType documentType)
	{
		this.documentType = documentType;
	}

	public void setDocumentType(String documentType)
	{
		if (documentType == null || documentType.trim().length() == 0)
		{
			return;
		}
		this.documentType = Enum.valueOf(ClientDocumentType.class, documentType);
	}

	public Long getPersonId()
	{
		return personId;
	}

	public void setPersonId(Long personId)
	{
		this.personId = personId;
	}

	public Long getPaperKind()
	{
		return paperKind;
	}

	public void setPaperKind(Long paperKind)
	{
		this.paperKind = paperKind;
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
