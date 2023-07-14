package com.rssl.phizic.business.documents;

import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.common.forms.doc.TypeOfPayment;

import java.util.Calendar;
import java.util.Date;

/**
 * Заявка на закрытие депозита
 * @author Kidyaev
 * @ created 12.02.2007
 * @ $Author$
 * @ $Revision$
 */
public class AccountClosingClaim extends AbstractPaymentDocument implements com.rssl.phizic.gate.claims.AccountClosingClaim
{
	private static final String CLOSE_ACCOUNT_ATTRIBUTE_NAME = "account";
	public static final String CLOSING_DATE_ATTRIBUTE_NAME = "closing-date";

	public Class<? extends GateDocument> getType()
	{
		return com.rssl.phizic.gate.claims.AccountClosingClaim.class;
	}

	public GateDocument getTransferPayment()
	{
		return new AccountClosingTransferDocumentAdapter(this);
	}

	public String getClosedAccount()
	{
		return getNullSaveAttributeStringValue(CLOSE_ACCOUNT_ATTRIBUTE_NAME);
	}
	public Calendar getClosingDate()
	{
		return DateHelper.toCalendar((Date) getNullSaveAttributeValue(CLOSING_DATE_ATTRIBUTE_NAME));
	}

	public void setClosingDate(Calendar closingDate)
	{
		setNullSaveAttributeCalendarValue(CLOSING_DATE_ATTRIBUTE_NAME, closingDate);
	}

	public TypeOfPayment getTypeOfPayment()
	{
		return TypeOfPayment.NOT_PAYMENT_OPEATION;
	}
}
