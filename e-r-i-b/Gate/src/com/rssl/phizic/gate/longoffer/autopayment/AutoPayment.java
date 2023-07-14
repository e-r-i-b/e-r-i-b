package com.rssl.phizic.gate.longoffer.autopayment;

import com.rssl.phizic.gate.longoffer.LongOffer;

import java.util.Calendar;

/**
 * @author osminin
 * @ created 28.01.2011
 * @ $Author$
 * @ $Revision$
 * ���������� �� ������������
 */
public interface AutoPayment extends LongOffer
{
	/**
	 * @return ��� ������� (��������)
	 */
	public String getCodeService();

	/**
	 * ���������� ��� ������� (��������)
	 * @param codeService ��� �������
	 */
	void setCodeService(String codeService);

	/**
	 * @return ��������� ����������
	 */
	public AutoPaymentStatus getReportStatus();

	/**
	 * @return ���� ���������� ������ �� ����������
	 */
	public Calendar getDateAccepted();

	/**
	* @return ����� �������� �����/��������
	*/
	public String getRequisite();

}
