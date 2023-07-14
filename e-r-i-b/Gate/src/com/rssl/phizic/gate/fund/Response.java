package com.rssl.phizic.gate.fund;

import com.rssl.phizic.common.types.fund.FundResponseState;

import java.math.BigDecimal;

/**
 * @author osminin
 * @ created 17.09.14
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ��������� ������� �� ���� �������
 */
public interface Response
{
	/**
	 * ������� �� ������ ����� ����������, ����������� '@' � ����������� �������������� ������
	 * @return ������� ������������� ������
	 */
	String getExternalId();

	/**
	 * ������� �� ������ ����� ����������, ����������� '@' � ����������� �������������� �������
	 * @return ������� ������������� �������
	 */
	String getExternalRequestId();

	/**
	 * @return ������ ��������� �������
	 */
	FundResponseState getState();

	/**
	 * @return ��������� ����������� �����
	 */
	String getMessage();

	/**
	 * @return ����� ��������
	 */
	BigDecimal getSum();
}
