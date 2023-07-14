package com.rssl.phizic.web.common.client.favourite;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * ����� ��������� ������ ������� ����������� ������������ ����� ����
 * @ author gorshkov
 * @ created 27.09.13
 * @ $Author$
 * @ $Revision$
 */
public class AsyncAutoPaymentForm extends ActionFormBase
{
	private String[] sortAutoPayments;

	/**
	 * @return ������ ������ ������������ � ������� ����������
	 */
	public String[] getSortAutoPayments()
	{
		return sortAutoPayments;
	}

	/**
	 * @param sortAutoPayments - ������ ������ ������������ � ������� ����������
	 */
	public void setSortAutoPayments(String[] sortAutoPayments)
	{
		this.sortAutoPayments = sortAutoPayments;
	}
}
