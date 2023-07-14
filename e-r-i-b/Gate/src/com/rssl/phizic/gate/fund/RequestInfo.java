package com.rssl.phizic.gate.fund;

import com.rssl.phizic.common.types.fund.FundRequestState;

import java.math.BigDecimal;

/**
 * @author osminin
 * @ created 29.12.14
 * @ $Author$
 * @ $Revision$
 *
 * ���������� � ������� �� ���� �������
 */
public interface RequestInfo
{
	/**
	 * @return ���������� �������������
	 */
	Long getInternalId();

	/**
	 * @return ������ ������� �� ���� �������
	 */
	FundRequestState getState();

	/**
	 * @return ������� ��������� �����
	 */
	BigDecimal getAccumulatedSum();
}
