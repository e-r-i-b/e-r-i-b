package com.rssl.phizic.web.common.client.favourite;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * Форма получения нового порядка отображения автоплатежей через аякс
 * @ author gorshkov
 * @ created 27.09.13
 * @ $Author$
 * @ $Revision$
 */
public class AsyncAutoPaymentForm extends ActionFormBase
{
	private String[] sortAutoPayments;

	/**
	 * @return массив линков автоплатежей в порядке сортировки
	 */
	public String[] getSortAutoPayments()
	{
		return sortAutoPayments;
	}

	/**
	 * @param sortAutoPayments - массив линков автоплатежей в порядке сортировки
	 */
	public void setSortAutoPayments(String[] sortAutoPayments)
	{
		this.sortAutoPayments = sortAutoPayments;
	}
}
