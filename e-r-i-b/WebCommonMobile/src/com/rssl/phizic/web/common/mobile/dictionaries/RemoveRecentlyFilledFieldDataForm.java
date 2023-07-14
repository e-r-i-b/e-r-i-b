package com.rssl.phizic.web.common.mobile.dictionaries;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @ author: Vagin
 * @ created: 25.07.2013
 * @ $Author
 * @ $Revision
 * Форма для удаления записи по присланному id из справочника доверенных получателей
 */
public class RemoveRecentlyFilledFieldDataForm extends ActionFormBase
{
	private Long id;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}
}
