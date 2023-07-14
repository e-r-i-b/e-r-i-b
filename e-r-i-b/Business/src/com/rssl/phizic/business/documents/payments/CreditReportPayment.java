package com.rssl.phizic.business.documents.payments;

/**
 * Платеж за кредитный отчет
 * @author Rtischeva
 * @ created 26.11.14
 * @ $Author$
 * @ $Revision$
 */
public interface CreditReportPayment
{
	/**
	 * статус запроса кредитного отчета
	 * @return
	 */
	String getCreditReportStatus();

	/**
	 * установить статус запроса кредитного отчета
	 * @param creditReportStatus - статус
	 */
	void setCreditReportStatus(String creditReportStatus);

}
