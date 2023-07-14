package com.rssl.phizic.gate.payments;

import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.documents.Claim;

import java.util.Calendar;

/**
 * ������ �� ��������� ������� �� ����� �� e-mail
 *
 * @author bogdanov
 * @ created 28.05.15
 * @ $Author$
 * @ $Revision$
 */

public interface ReportByCardClaim extends Claim
{
	/**
	 * @return e-mail ����� ��� �������� ������� �� �����.
	 */
	public String getEmailAddress();

	/**
	 * @return ������ ������� �� �����.
	 */
	public String getReportFormat();

	/**
	 * @return ���� ������� �� �����.
	 */
	public String getReportLang();

	/**
	 * @return ��� ������� (������, ������ ��������).
	 */
	public String getReportOrderType();

	/**
	 * @return ���� ������ ������� ������� (��� ������ ������� �������� ����� � ���).
	 */
	public Calendar getReportStartDate();

	/**
	 * @return ���� ��������� ������� ������� (�� ������������ � ������ �������).
	 */
	public Calendar getReportEndDate();

	/**
	 * @return ��������������� ���� ��� ������ ������� �������.
	 */
	public String getReportStartDateFormated();

	/**
	 * @return ��������������� ���� ��� ��������� ������� �������.
	 */
	public String getReportEndDateFormated();

	/**
	 * @return �����.
	 */
	public String getCardNumber();

	/**
	 * @return ����� ���������� ���������
	 */
	public String getContractNumber();
}
