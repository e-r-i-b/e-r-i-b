package com.rssl.phizic.web.atm.service;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.LengthFieldValidator;
import com.rssl.phizic.web.common.EditFormBase;

import java.math.BigInteger;

/**
 * Форма редактирования служебной информации пользователя
 *
 * @author Balovtsev
 * @since 10.09.14.
 */
public class UserServiceInfoForm extends EditFormBase
{
	public static final Form   SERVICE_INFO_EDIT_FORM = createEditForm();
	public static final String FIELD_DATA             = "data";

	private String data;

	public String getData()
	{
		return data;
	}

	public void setData(String data)
	{
		this.data = data;
	}

	private static Form createEditForm()
	{
		FormBuilder  formBuilder  = new FormBuilder();
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(FIELD_DATA);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Служебная информация");
		fieldBuilder.addValidators (new LengthFieldValidator(new BigInteger("4000")));
		formBuilder.addField(fieldBuilder.build());
		return formBuilder.build();
	}
}
