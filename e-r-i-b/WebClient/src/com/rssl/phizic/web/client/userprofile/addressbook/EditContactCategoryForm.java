package com.rssl.phizic.web.client.userprofile.addressbook;

import com.rssl.phizic.web.common.EditFormBase;

/**
 * Форма редактирования категории контакта
 *
 * @author shapin
 * @ created 07.10.14
 * @ $Author$
 * @ $Revision$
 */

public class EditContactCategoryForm extends EditFormBase
{

	private String category;
	private String error;

	public String getCategory()
	{
		return this.category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public String getError()
	{
		return error;
	}

	public void setError(String error)
	{
		this.error = error;
	}

}
