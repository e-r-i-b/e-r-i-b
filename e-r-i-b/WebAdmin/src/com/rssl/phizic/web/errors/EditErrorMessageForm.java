package com.rssl.phizic.web.errors;

import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.errors.ErrorMessage;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;

/**
 * @author gladishev
 * @ created 19.11.2007
 * @ $Author$
 * @ $Revision$
 */

public class EditErrorMessageForm extends EditFormBase
{
	protected ErrorMessage errorMessage;

	public ErrorMessage getErrorMessage()
	{
		return errorMessage;
	}

	public void setErrorMessage(ErrorMessage errorMessage)
	{
		this.errorMessage = errorMessage;
	}

	public static final Form FORM  = createForm();

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;

		// –егул€рное выражение
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("–егул€рное выражение");
		fieldBuilder.setName("regExp");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RegexpFieldValidator(".{0,256}", "–егул€рное выражение должно быть не более 256 символов")
		);

		fb.addField(fieldBuilder.build());

		//“ип ошибки
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("“ип ошибки");
		fieldBuilder.setName("errorType");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(new RequiredFieldValidator());

		fb.addField(fieldBuilder.build());

		//—истема
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("—истема");
		fieldBuilder.setName("system");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(new RequiredFieldValidator());

		fb.addField(fieldBuilder.build());

		// —ообщение в системе » ‘Ћ
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("—ообщение в » ‘Ћ");
		fieldBuilder.setName("message");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RegexpFieldValidator(".{0,256}", "—ообщение в » ‘Ћ должно быть не более 256 символов")
		);
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
