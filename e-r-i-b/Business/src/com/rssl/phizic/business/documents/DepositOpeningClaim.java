package com.rssl.phizic.business.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.TypeOfPayment;
import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Calendar;
import java.util.Date;

/**
 * Заявка на открытие счета/вклада
 * @author Kidyaev
 * @ created 21.11.2006
 * @ $Author$
 * @ $Revision$
 */
public class DepositOpeningClaim extends AbstractAccountsTransfer implements com.rssl.phizic.gate.deposit.DepositOpeningClaim
{
	public static final String ACCOUNT_TYPE_ATTRIBUTE_NAME = "account-type";
	public static final String TRANSFER_ACCOUNT_ATTRIBUTE_TYPE = "to-account";
	public static final String IS_RENEWAL_ATTRIBUTE_NAME = "is-renewal";
	public static final String PERIOD_ATTRIBUTE_NAME = "period";
	public static final String VISIT_DATE_ATTRIBUTE_NAME = "visit-date";
	public static final String BANK_OFFICE_ATTRIBUTE_NAME = "department";
	public static final String ACCOUNT_EXTERNAL_ID_ATTRIBUTE_NAME = "account-id";
	public static final String PAYER_ACCOUNT_ATTRIBUTE_NAME = "payer-account";

	private String fromAccount;

	public Class<? extends GateDocument> getType()
	{
		return com.rssl.phizic.gate.deposit.DepositOpeningClaim.class;
	}

	public String getDepositConditionsId()
	{
		return getNullSaveAttributeStringValue(ACCOUNT_TYPE_ATTRIBUTE_NAME);
	}

	public DateSpan getPeriod()
	{
		String period = getNullSaveAttributeStringValue(PERIOD_ATTRIBUTE_NAME);
		if (period == null)
		{
			return null;
		}
		return new DateSpan(period);
	}

	public String getTransferAccount()
	{
		return getNullSaveAttributeStringValue(TRANSFER_ACCOUNT_ATTRIBUTE_TYPE);
	}

	public Calendar getVisitDate()
	{
		return DateHelper.toCalendar((Date) getNullSaveAttributeValue(VISIT_DATE_ATTRIBUTE_NAME));
	}

	public boolean isAutomaticRenewal()
	{
		return (Boolean) getNullSaveAttributeValue(IS_RENEWAL_ATTRIBUTE_NAME);
	}

	public String getOfficeExternalId()
	{
		return getNullSaveAttributeStringValue(BANK_OFFICE_ATTRIBUTE_NAME);
	}

	public void setAccount(String account)
	{
		setNullSaveAttributeStringValue(ACCOUNT_EXTERNAL_ID_ATTRIBUTE_NAME, account);
	}

	public String getAccount() throws GateLogicException, GateException
	{
		return getNullSaveAttributeStringValue(ACCOUNT_EXTERNAL_ID_ATTRIBUTE_NAME);
	}

	public Document convertToDom () throws DocumentException
	{
		Document document = super.convertToDom();
		Element root = document.getDocumentElement();
		appendNullSaveString(root, PAYER_ACCOUNT_ATTRIBUTE_NAME, getFromAccount());
		return document;
	}

	protected void readFromDom(Document document, InnerUpdateState updateState, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		super.readFromDom(document, updateState, messageCollector);
		Element root = document.getDocumentElement();
		setFromAccount(XmlHelper.getSimpleElementValue(root, PAYER_ACCOUNT_ATTRIBUTE_NAME));
	}

	public TypeOfPayment getTypeOfPayment()
	{
		return TypeOfPayment.INTERNAL_PAYMENT_OPERATION;
	}

	public String getFromAccount()
	{
		return fromAccount;
	}

	public void setFromAccount(String fromAccount)
	{
		this.fromAccount = fromAccount;
	}
}
