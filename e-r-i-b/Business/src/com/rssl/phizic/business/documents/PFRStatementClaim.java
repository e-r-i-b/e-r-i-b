package com.rssl.phizic.business.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.TypeOfPayment;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.pfr.StatementStatus;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Calendar;

/**
 * @author Erkin
 * @ created 04.02.2011
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings({"ClassNameSameAsAncestorName"})
public class PFRStatementClaim extends GateExecutableDocument implements com.rssl.phizic.gate.claims.pfr.PFRStatementClaim
{
	private static final String SNILS_ATTRIBUTE_NAME = "SNILS";
	private static final String FIRSTNAME_ATTRIBUTE_NAME = "firstname";
	private static final String SURNAME_ATTRIBUTE_NAME = "surname";
	private static final String PATRNAME_ATTRIBUTE_NAME = "patrname";
	private static final String BIRTHDAY_ATTRIBUTE_NAME = "birthday";
	private static final String DOC_NUMBER_ATTRIBUTE_NAME = "person-doc-number";
	private static final String DOC_SERIES_ATTRIBUTE_NAME = "person-doc-series";
	private static final String DOC_ISSUE_DATE_ATTRIBUTE_NAME = "person-doc-issue-date";
	private static final String DOC_ISSUE_BY_ATTRIBUTE_NAME = "person-doc-issue-by";
	private static final String DOC_ISSUE_BY_CODE_ATTRIBUTE_NAME = "person-doc-issue-by-code";
	private static final String READY_ATTRIBUTE_NAME = "claim-ready-state";
	private static final String READY_DESCRIPTION_ATTRIBUTE_NAME = "claim-ready-state-description";
	private static final String SEND_METHOD_ATTRIBUTE_NAME = "claim-send-method";
	private static final String REFUSING_REASON_ATTRIBUTE_NAME = "refusing-reason";
	private String receiverName;

	///////////////////////////////////////////////////////////////////////////

	public Class<? extends GateDocument> getType()
	{
		return com.rssl.phizic.gate.claims.pfr.PFRStatementClaim.class;
	}

	public Document convertToDom() throws DocumentException
	{
		Document document = super.convertToDom();
		Element root = document.getDocumentElement();
		appendNullSaveString(root, REFUSING_REASON_ATTRIBUTE_NAME, getRefusingReason());
		appendNullSaveString(root, RECEIVER_NAME_ATTRIBUTE_NAME, getReceiverName());
		return document;
	}

	protected void readFromDom(Document document, InnerUpdateState updateState, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		super.readFromDom(document, updateState, messageCollector);
		Element root = document.getDocumentElement();
	    setReceiverName(XmlHelper.getSimpleElementValue(root, RECEIVER_NAME_ATTRIBUTE_NAME));
	}

	public TypeOfPayment getTypeOfPayment()
	{
		return TypeOfPayment.NOT_PAYMENT_OPEATION;
	}

	public String getSNILS()
	{
		return getNullSaveAttributeStringValue(SNILS_ATTRIBUTE_NAME);
	}

	public void setSNILS(String SNILS)
	{
		setNullSaveAttributeStringValue(SNILS_ATTRIBUTE_NAME, SNILS);
	}

	public String getFirstName()
	{
		return getNullSaveAttributeStringValue(FIRSTNAME_ATTRIBUTE_NAME);
	}

	public void setFirstName(String firstName)
	{
		setNullSaveAttributeStringValue(FIRSTNAME_ATTRIBUTE_NAME, firstName);
	}

	public String getSurName()
	{
		return getNullSaveAttributeStringValue(SURNAME_ATTRIBUTE_NAME);
	}

	public void setSurName(String surName)
	{
		setNullSaveAttributeStringValue(SURNAME_ATTRIBUTE_NAME, surName);
	}

	public String getPatrName()
	{
		return getNullSaveAttributeStringValue(PATRNAME_ATTRIBUTE_NAME);
	}

	public void setPatrName(String patrName)
	{
		setNullSaveAttributeStringValue(PATRNAME_ATTRIBUTE_NAME, patrName);
	}

	public Calendar getBirthDay()
	{
		return getNullSaveAttributeCalendarValue(BIRTHDAY_ATTRIBUTE_NAME);
	}

	public void setBirthDay(Calendar birthDay)
	{
		setNullSaveAttributeCalendarValue(BIRTHDAY_ATTRIBUTE_NAME, birthDay);
	}

	public ClientDocumentType getDocumentType()
	{
		return ClientDocumentType.PASSPORT_WAY;
	}

	public String getDocNumber()
	{
		return getNullSaveAttributeStringValue(DOC_NUMBER_ATTRIBUTE_NAME);
	}

	public void setDocNumber(String docNumber)
	{
		setNullSaveAttributeStringValue(DOC_NUMBER_ATTRIBUTE_NAME, docNumber);
	}

	public String getDocSeries()
	{
		return getNullSaveAttributeStringValue(DOC_SERIES_ATTRIBUTE_NAME);
	}

	public void setDocSeries(String docSeries)
	{
		setNullSaveAttributeStringValue(DOC_SERIES_ATTRIBUTE_NAME, docSeries);
	}

	public Calendar getDocIssueDate()
	{
		return getNullSaveAttributeCalendarValue(DOC_ISSUE_DATE_ATTRIBUTE_NAME);
	}

	public void setDocIssueDate(Calendar docIssueDate)
	{
		setNullSaveAttributeCalendarValue(DOC_ISSUE_DATE_ATTRIBUTE_NAME, docIssueDate);
	}

	public String getDocIssueBy()
	{
		return getNullSaveAttributeStringValue(DOC_ISSUE_BY_ATTRIBUTE_NAME);
	}

	public void setDocIssueBy(String docIssueBy)
	{
		setNullSaveAttributeStringValue(DOC_ISSUE_BY_ATTRIBUTE_NAME, docIssueBy);
	}

	public String getDocIssueByCode()
	{
		return getNullSaveAttributeStringValue(DOC_ISSUE_BY_CODE_ATTRIBUTE_NAME);
	}

	public void setDocIssueByCode(String docIssueByCode)
	{
		setNullSaveAttributeStringValue(DOC_ISSUE_BY_CODE_ATTRIBUTE_NAME, docIssueByCode);
	}

	public StatementStatus isReady()
	{
		return getNullSaveAttributeEnumValue(StatementStatus.class, READY_ATTRIBUTE_NAME);
	}

	public void setReady(StatementStatus isReady)
	{
		setNullSaveAttributeEnumValue(READY_ATTRIBUTE_NAME, isReady);
	}

	public String getReadyDescription()
	{
		return getNullSaveAttributeStringValue(READY_DESCRIPTION_ATTRIBUTE_NAME);
	}

	public void setReadyDescription(String readyDescription)
	{
		setNullSaveAttributeStringValue(READY_DESCRIPTION_ATTRIBUTE_NAME, readyDescription);
	}

	/**
	 * @return способ отправки заявки
	 */
	public PFRStatementClaimSendMethod getSendMethod()
	{
		return getNullSaveAttributeEnumValue(PFRStatementClaimSendMethod.class, SEND_METHOD_ATTRIBUTE_NAME);
	}

	public void setSendMethod(PFRStatementClaimSendMethod sendMethod)
	{
		setNullSaveAttributeEnumValue(SEND_METHOD_ATTRIBUTE_NAME, sendMethod);
	}

	public String getReceiverName()
	{
		return receiverName;
	}

	public void setReceiverName(String receiverName)
	{
		this.receiverName = receiverName;
	}
}
