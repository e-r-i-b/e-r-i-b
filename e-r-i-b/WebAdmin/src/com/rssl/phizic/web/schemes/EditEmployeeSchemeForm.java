package com.rssl.phizic.web.schemes;

import com.rssl.phizic.business.services.groups.ServicesGroupIterator;

/**
 * @author akrenev
 * @ created 22.08.2014
 * @ $Author$
 * @ $Revision$
 *
 * Форма редактирования схемы прав сотрудника
 */

public class EditEmployeeSchemeForm extends EditSchemeForm
{
	private ServicesGroupIterator groups;

	/**
	 * @return группы сервисов
	 */
	@SuppressWarnings("UnusedDeclaration") //jsp
	public ServicesGroupIterator getGroups()
	{
		groups.refresh();
		return groups;
	}

	/**
	 * задать группы сервисов
	 * @param groups группы
	 */
	public void setGroups(ServicesGroupIterator groups)
	{
		this.groups = groups;
	}
}
