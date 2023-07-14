package com.rssl.phizic.person;

import com.rssl.phizic.common.types.annotation.PlainOldJavaObject;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author hudyakov
 * @ created 08.05.2008
 * @ $Author$
 * @ $Revision$
 */
@PlainOldJavaObject
public interface PersonDocument extends Serializable
{
	Long getId();

	void setId(Long id);

    PersonDocumentType getDocumentType();

	void setDocumentType(PersonDocumentType documentType);

    String getDocumentName();

	void setDocumentName(String documentName);

	String getDocumentNumber();

	void setDocumentNumber(String documentNumber);

    String getDocumentSeries();

    void setDocumentSeries(String documentSeries);

    Calendar getDocumentIssueDate();

    void setDocumentIssueDate(Calendar documentIssueDate);

    String getDocumentIssueBy();

    void setDocumentIssueBy(String documentIssueBy);

    public String getDocumentIssueByCode();

    void setDocumentIssueByCode(String documentIssueByCode);

	public boolean getDocumentMain();

	void setDocumentMain(boolean documentMain);

	boolean isEmpty();

	void setDocumentTimeUpDate(Calendar documentTimeUpDate);

	Calendar getDocumentTimeUpDate();

	public boolean isDocumentIdentify();

	void setDocumentIdentify(boolean documentIdentify);
}
