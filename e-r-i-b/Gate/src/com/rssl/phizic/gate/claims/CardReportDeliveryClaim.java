package com.rssl.phizic.gate.claims;

import com.rssl.phizic.gate.bankroll.ReportDeliveryLanguage;
import com.rssl.phizic.gate.bankroll.ReportDeliveryType;
import com.rssl.phizic.gate.documents.SynchronizableDocument;

/**
 * @author akrenev
 * @ created 05.05.2014
 * @ $Author$
 * @ $Revision$
 *
 * ������ �� ��������� ���������� �������� �� �����
 */

public interface CardReportDeliveryClaim extends SynchronizableDocument
{
	/**
	 * @return ������� ������������� �������
	 */
	public String getCardClientIdReportDelivery();

	/**
	 * @return ������� ������������� �����
	 */
	public String getCardExternalIdReportDelivery();

	/**
	 * @return �������� �� ��������
	 */
	public boolean isUseReportDelivery();

	/**
	 * @return ����� ��������
	 */
	public String getEmailReportDelivery();

	/**
	 * @return ��� ������ ������
	 */
	public ReportDeliveryType getReportDeliveryType();

	/**
	 * @return ���� ������
	 */
	public ReportDeliveryLanguage getReportDeliveryLanguage();

	/**
	 * @return ����� ��������
	 */
	public String getContractNumber();
}
