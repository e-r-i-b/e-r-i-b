package com.rssl.phizic.web.common.mobile.stash;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.LengthFieldValidator;
import com.rssl.phizic.web.common.EditFormBase;

import java.math.BigInteger;

/**
 * "избранное" в мобильном приложении
 * @author Dorzhinov
 * @ created 06.09.13
 * @ $Author$
 * @ $Revision$
 */
public class EditStashForm extends EditFormBase
{
	private static final String MAX_STASH_LENGTH = "500";
	static final String STASH = "stash";

	private String stash; //строка с избранным

	public String getStash()
	{
		return stash;
	}

	public void setStash(String stash)
	{
		this.stash = stash;
	}

	public static final Form FORM = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("»збранное");
		fieldBuilder.setName(STASH);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		LengthFieldValidator lengthValidator = new LengthFieldValidator(BigInteger.ZERO, new BigInteger(MAX_STASH_LENGTH));
		lengthValidator.setMessage("ћаксимальна€ длина 'избранного' составл€ет " + MAX_STASH_LENGTH + " символов.");
		fieldBuilder.addValidators(lengthValidator);
		formBuilder.addField(fieldBuilder.build());
		return formBuilder.build();
	}
}
