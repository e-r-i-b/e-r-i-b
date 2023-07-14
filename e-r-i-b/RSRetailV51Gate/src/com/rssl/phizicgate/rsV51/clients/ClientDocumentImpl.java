package com.rssl.phizicgate.rsV51.clients;

import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.common.types.client.ClientDocumentType;

import java.util.Calendar;

/**
 * @author Danilov
 * @ created 19.12.2007
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
	private Calendar docTimeUpDate;
	private boolean docIdentify = true;

	private String personId;
	private String paperKind;

	public String getDocTypeName()
	{
		return "Паспорт";
	}

	public ClientDocumentType getDocumentType()//тип документа
	{
		return ClientDocumentType.REGULAR_PASSPORT_RF;
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
		return null;  //To change body of implemented methods use File | Settings | File Templates.
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

	public String getPersonId()
	{
		return personId;
	}

	public void setPersonId(String personId)
	{
		this.personId = personId;
	}

	public String getPaperKind()
	{
		return paperKind;
	}

	public void setPaperKind(String paperKind)
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
