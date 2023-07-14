package com.rssl.phizic.gate.fund;

import com.rssl.phizic.common.types.fund.FundRequestState;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author osminin
 * @ created 17.09.14
 * @ $Author$
 * @ $Revision$
 *
 * ������ �� ���� �������
 */
public interface Request
{
	/**
	 * ������� �� ������ ����� ����������, ����������� '@' � ����������� ��������������
	 * @return ������� ������������� �������
	 */
	String getExternalId();

	/**
	 * @return ������ �������
	 */
	FundRequestState getState();

	/**
	 * @return ����������� ����� �����
	 */
	BigDecimal getRequiredSum();

	/**
	 * @return ��������������� �����
	 */
	BigDecimal getReccomendSum();

	/**
	 * @return ��������� ������������ �����
	 */
	String getMessage();

	/**
	 * @return ������ ��������
	 */
	String getResource();

	/**
	 * @return ���� �������� �������
	 */
	Calendar getClosedDate();

	/**
	 * @return ��������� ���� �������� �������
	 */
	Calendar getExpectedClosedDate();

	/**
	 * @return ���� �������� �������
	 */
	Calendar getCreatedDate();
}
