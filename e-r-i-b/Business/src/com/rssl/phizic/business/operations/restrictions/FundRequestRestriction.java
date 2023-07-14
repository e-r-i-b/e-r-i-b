/**
 * User: usachev
 * Date: 09.12.14
 */
package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.fund.initiator.FundRequest;

/**
 *   ����������� �� "��������� ������" �� �������������
 */
public interface FundRequestRestriction extends Restriction
{
	/**
	 * �������� �� "��������� ������" ��� �����������
	 * @param fundRequest ��������� ������
	 * @return ��, ���� �� �������� ��� �����������. ���, ���� ��������.
	 */
	public boolean accept(FundRequest fundRequest);
}
