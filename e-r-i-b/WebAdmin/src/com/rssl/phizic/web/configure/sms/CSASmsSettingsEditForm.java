package com.rssl.phizic.web.configure.sms;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.validators.LengthFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.claims.forms.validators.SmsTemplateVariablesValidator;
import com.rssl.phizic.web.common.EditFormBase;

import java.math.BigInteger;

/**
 *
 * Форма редактирования смс ресурсов из ЦСА
 *
 * @author  Balovtsev
 * @version 05.04.13 12:07
 */
public class CSASmsSettingsEditForm extends EditFormBase
{
	static final String FIELD_MESSAGE_TEMPLATE      = "messageTemplate";
	static final String FIELD_CSA_MESSAGE_TEMPLATE  = "csaTemplate";
	static final String FIELD_ERMB_MESSAGE_TEMPLATE  = "ermbTemplate";
	static final String FIELD_DESCRIPTION           = "description";
	static final String FIELD_CSATEXT               = "csaText";
	static final String FIELD_ID                    = "id";

	public static final Form FORM = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder field = new FieldBuilder();
		field.setName(FIELD_ID);
		field.setType(LongType.INSTANCE.getName());
		field.setDescription("Идентификатор шаблона");
		formBuilder.addField( field.build() );

		field = new FieldBuilder();
		field.setName(FIELD_DESCRIPTION);
		field.setDescription("Название шаблона");
		field.addValidators(
				new RequiredFieldValidator(),
				new LengthFieldValidator(new BigInteger("255")));
		formBuilder.addField( field.build() );

		field = new FieldBuilder();
		field.setName(FIELD_MESSAGE_TEMPLATE);
		field.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(field.build());

		field = new FieldBuilder();
		field.setName(FIELD_CSA_MESSAGE_TEMPLATE);
		field.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(field.build());

		field = new FieldBuilder();
		field.setName(FIELD_ERMB_MESSAGE_TEMPLATE);
		field.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(field.build());

		field = new FieldBuilder();
		field.setName(FIELD_CSATEXT);
		field.setDescription("Текст SMS для ЦСА");
		field.clearValidators();
		field.addValidators(new RequiredFieldValidator());
		formBuilder.addField( field.build() );

		SmsTemplateVariablesValidator csaValidator = new SmsTemplateVariablesValidator();
		csaValidator.setBinding(SmsTemplateVariablesValidator.FIELD_ID, FIELD_ID);
		csaValidator.setBinding(SmsTemplateVariablesValidator.FIELD_TEMPLATE, FIELD_CSATEXT);
		csaValidator.setBinding(SmsTemplateVariablesValidator.FIELD_IS_CSA_MESSAGE_TEMPLATE, FIELD_CSA_MESSAGE_TEMPLATE);
		csaValidator.setBinding(SmsTemplateVariablesValidator.FIELD_IS_ERMB_MESSAGE_TEMPLATE, FIELD_ERMB_MESSAGE_TEMPLATE);

		formBuilder.addFormValidators(csaValidator);
		return formBuilder.build();
	}
}
