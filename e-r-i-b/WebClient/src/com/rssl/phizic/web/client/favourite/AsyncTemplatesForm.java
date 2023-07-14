package com.rssl.phizic.web.client.favourite;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * Форма получения нового порядка отображения шаблонов через аякс
 * @ author gorshkov
 * @ created 25.09.13
 * @ $Author$
 * @ $Revision$
 */
public class AsyncTemplatesForm extends ActionFormBase
{
	private Long[] sortTemplates;

	/**
	 * @return массив id шаблонов в порядке сортировки
	 */
	public Long[] getSortTemplates()
	{
		return sortTemplates;
	}

	/**
	 * @param sortTemplates - массив id шаблонов в порядке сортировки
	 */
	public void setSortTemplates(Long[] sortTemplates)
	{
		this.sortTemplates = sortTemplates;
	}
}
