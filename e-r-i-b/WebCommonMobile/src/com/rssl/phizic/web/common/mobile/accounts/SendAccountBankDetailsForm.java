package com.rssl.phizic.web.common.mobile.accounts;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.LengthFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.common.EditFormBase;

import java.math.BigInteger;

/**
 * @author EgorovaA
 * @ created 28.11.13
 * @ $Author$
 * @ $Revision$
 */
public class SendAccountBankDetailsForm extends EditFormBase
{
	static final String MAIL_ADDRESS = "address";
	static final String MAIL_COMMENT = "comment";
	private static final BigInteger TEXT_LENGTH = new BigInteger("200");
	static final Form SEND_MAIL_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;
		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(MAIL_ADDRESS);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Выслать на EMAIL");
		fieldBuilder.addValidators(requiredFieldValidator, new RegexpFieldValidator("[A-Za-z0-9_\\.-]*@[A-Za-z0-9_\\.-]*\\.[A-Za-z0-9_]*", "Адрес электронной почты может состоять из букв латинского алфавита, цифр, дефиса и символа подчеркивания, а также в нем должен присутствовать символ \"@\" и \".\"."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(MAIL_COMMENT);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Комментарий к письму");
		LengthFieldValidator lengthFieldValidator = new LengthFieldValidator(TEXT_LENGTH);
		lengthFieldValidator.setMessage("Длина комментария к письму не должна превышать " + TEXT_LENGTH + " символов.");
		fieldBuilder.addValidators(lengthFieldValidator);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

}
