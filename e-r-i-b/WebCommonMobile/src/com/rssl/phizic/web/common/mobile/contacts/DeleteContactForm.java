package com.rssl.phizic.web.common.mobile.contacts;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * Форма удаление контактов из адресной книги.
 *
 * @author bogdanov
 * @ created 28.10.14
 * @ $Author$
 * @ $Revision$
 */

public class DeleteContactForm extends ActionFormBase
{
	private long id;
	private boolean deleted;

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public boolean isDeleted()
	{
		return deleted;
	}

	public void setDeleted(boolean deleted)
	{
		this.deleted = deleted;
	}
}
