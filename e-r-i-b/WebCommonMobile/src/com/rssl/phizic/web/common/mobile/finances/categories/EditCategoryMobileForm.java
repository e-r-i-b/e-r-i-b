package com.rssl.phizic.web.common.mobile.finances.categories;

import com.rssl.phizic.web.common.client.finances.AsyncCategoryForm;

/**
 * ƒобавление/редактирование/удаление категории
 * @author Dorzhinov
 * @ created 01.10.2013
 * @ $Author$
 * @ $Revision$
 */
public class EditCategoryMobileForm extends AsyncCategoryForm
{
	private String name;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
