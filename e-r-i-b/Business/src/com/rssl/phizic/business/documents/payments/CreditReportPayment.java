package com.rssl.phizic.business.documents.payments;

/**
 * ������ �� ��������� �����
 * @author Rtischeva
 * @ created 26.11.14
 * @ $Author$
 * @ $Revision$
 */
public interface CreditReportPayment
{
	/**
	 * ������ ������� ���������� ������
	 * @return
	 */
	String getCreditReportStatus();

	/**
	 * ���������� ������ ������� ���������� ������
	 * @param creditReportStatus - ������
	 */
	void setCreditReportStatus(String creditReportStatus);

}
