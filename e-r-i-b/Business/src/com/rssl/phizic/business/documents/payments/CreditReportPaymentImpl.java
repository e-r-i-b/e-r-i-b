package com.rssl.phizic.business.documents.payments;

/**
 * Реализация Платежа за кредитный отчет
 * @author Rtischeva
 * @ created 26.11.14
 * @ $Author$
 * @ $Revision$
 */
public class CreditReportPaymentImpl extends JurPayment implements CreditReportPayment
{
	public void setCreditReportStatus(String creditReportStatus)
	{
		setNullSaveAttributeStringValue(CREDIT_REPORT_STATUS, creditReportStatus);
	}

	public String getCreditReportStatus()
	{
		return getNullSaveAttributeStringValue(CREDIT_REPORT_STATUS);
	}
}
