package com.rssl.phizic.web.configure.sms;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.validators.LengthFieldValidator;
import com.rssl.common.forms.validators.NumericRangeValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.claims.forms.validators.SmsTemplateVariablesValidator;
import com.rssl.phizic.web.common.EditFormBase;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Форма редактирования текста смс-шаблона ЕРМБ
 * @author Rtischeva
 * @created 31.07.13
 * @ $Author$
 * @ $Revision$
 */
public class ERMBSmsSettingsEditForm extends EditFormBase
{
	static final String FIELD_CSA_MESSAGE_TEMPLATE = "csaTemplate";
	static final String FIELD_DESCRIPTION          = "description";
	static final String FIELD_ERMB_MESSAGE_TEMPLATE  = "ermbTemplate";
	static final String FIELD_ERMB_TEXT             = "ermbText";
	static final String FIELD_ID                   = "id";
	static final String FIELD_PRIORITY             = "priority";

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
				new LengthFieldValidator(BigInteger.valueOf(255)));
		formBuilder.addField( field.build() );

		field=new FieldBuilder();
		field.setName(FIELD_PRIORITY);
		field.setType(LongType.INSTANCE.getName());
		field.setDescription("Приоритет");
		field.addValidators(new RequiredFieldValidator(),
				new NumericRangeValidator(BigDecimal.ZERO, BigDecimal.valueOf(9), "Неверно указан приоритет. Укажите число от 0 до 9"));
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
		field.setName(FIELD_ERMB_TEXT);
		field.setDescription("Текст SMS для мобильного банка");
		field.clearValidators();
		field.addValidators(new RequiredFieldValidator());
		formBuilder.addField( field.build() );

		SmsTemplateVariablesValidator ermbValidator = new SmsTemplateVariablesValidator();
		ermbValidator.setBinding(SmsTemplateVariablesValidator.FIELD_ID, FIELD_ID);
		ermbValidator.setBinding(SmsTemplateVariablesValidator.FIELD_TEMPLATE, FIELD_ERMB_TEXT);
		ermbValidator.setBinding(SmsTemplateVariablesValidator.FIELD_IS_CSA_MESSAGE_TEMPLATE, FIELD_CSA_MESSAGE_TEMPLATE);
		ermbValidator.setBinding(SmsTemplateVariablesValidator.FIELD_IS_ERMB_MESSAGE_TEMPLATE, FIELD_ERMB_MESSAGE_TEMPLATE);

		formBuilder.addFormValidators(ermbValidator);
		return formBuilder.build();
	}
}
