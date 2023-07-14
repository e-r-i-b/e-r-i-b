package com.rssl.phizic.web.common.mobile.profile;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * Сохранение данных в профиле ЕРИБ
 * @author Jatsky
 * @ created 04.06.14
 * @ $Author$
 * @ $Revision$
 */

public class EditSocialLoginForm extends ActionFormBase
{
	private String fieldName;
	private String fieldValue;

	public String getFieldName()
	{
		return fieldName;
	}

	public void setFieldName(String fieldName)
	{
		this.fieldName = fieldName;
	}

	public String getFieldValue()
	{
		return fieldValue;
	}

	public void setFieldValue(String fieldValue)
	{
		this.fieldValue = fieldValue;
	}
}
