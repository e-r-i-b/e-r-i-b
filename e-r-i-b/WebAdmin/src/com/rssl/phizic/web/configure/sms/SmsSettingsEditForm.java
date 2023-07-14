package com.rssl.phizic.web.configure.sms;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.validators.LengthFieldValidator;
import com.rssl.common.forms.validators.NumericRangeValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.claims.forms.validators.SmsTemplateVariablesValidator;
import com.rssl.phizic.common.types.limits.ChannelType;
import com.rssl.phizic.web.common.EditFormBase;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * Форма редактирования смс сообщений
 *
 * @author Balovtsev
 * @since 22.03.13 11:33
 */
public class SmsSettingsEditForm extends EditFormBase
{
	public static final String FIELD_DESCRIPTION          = "description";
	public static final String FIELD_ERIBTEXT             = "eribText";
	public static final String FIELD_MAPITEXT             = "customMApiText";
	public static final String FIELD_ATMTEXT              = "customAtmText";
	public static final String FIELD_SOCIALTEXT           = "customSocialText";
	public static final String FIELD_MAPI_TEMPLATE_TYPE   = "mapiTemplateType";
	public static final String FIELD_SOCIAL_TEMPLATE_TYPE = "socialTemplateType";
	public static final String FIELD_ATM_TEMPLATE_TYPE    = "amtTemplateType";
	public static final String FIELD_MESSAGE_TEMPLATE     = "messageTemplate";
	public static final String FIELD_CSA_MESSAGE_TEMPLATE = "csaTemplate";
	public static final String FIELD_ERMB_MESSAGE_TEMPLATE  = "ermbTemplate";
	public static final String FIELD_ID                   = "id";
	public static final String FIELD_PRIORITY             = "priority";

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
		field.setName(FIELD_PRIORITY);
		field.setDescription("Приоритет");
		field.addValidators(
				new RequiredFieldValidator(),
				new NumericRangeValidator(BigDecimal.ZERO, BigDecimal.valueOf(9), "Неверно указан приоритет. Укажите число от 0 до 9"));
		formBuilder.addField( field.build() );

		field = new FieldBuilder();
		field.setName(FIELD_ERIBTEXT);
		field.setDescription("Текст SMS для ЕРИБ");
		field.clearValidators();
		field.addValidators(new RequiredFieldValidator());
		formBuilder.addField( field.build() );

		field = new FieldBuilder();
		field.setName(FIELD_MAPI_TEMPLATE_TYPE);
		field.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(field.build());

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

		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
		requiredFieldValidator.setEnabledExpression(new RhinoExpression("form.mapiTemplateType"));

		field = new FieldBuilder();
		field.setName(FIELD_MAPITEXT);
		field.setDescription("Текст SMS для мобильного приложения");
		field.clearValidators();
		field.addValidators(requiredFieldValidator);
		formBuilder.addField(field.build());


		field = new FieldBuilder();
		field.setName(FIELD_ATM_TEMPLATE_TYPE);
		field.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(field.build());

		requiredFieldValidator = new RequiredFieldValidator();
		requiredFieldValidator.setEnabledExpression(new RhinoExpression("form.amtTemplateType"));

		field = new FieldBuilder();
		field.setName(FIELD_ATMTEXT);
		field.setDescription("Текст SMS для устроств самообслуживания");
		field.clearValidators();
		field.addValidators(requiredFieldValidator);
		formBuilder.addField(field.build());

        field = new FieldBuilder();
        field.setName(FIELD_SOCIAL_TEMPLATE_TYPE);
        field.setType(BooleanType.INSTANCE.getName());
        formBuilder.addField(field.build());

        requiredFieldValidator = new RequiredFieldValidator();
        requiredFieldValidator.setEnabledExpression(new RhinoExpression("form.socialTemplateType"));

        field = new FieldBuilder();
        field.setName(FIELD_SOCIALTEXT);
        field.setDescription("Текст SMS для социального приложения");
        field.clearValidators();
        field.addValidators(requiredFieldValidator);
        formBuilder.addField(field.build());


		SmsTemplateVariablesValidator eribValidator = new SmsTemplateVariablesValidator(ChannelType.INTERNET_CLIENT);
		eribValidator.setBinding(SmsTemplateVariablesValidator.FIELD_ID, FIELD_ID);
		eribValidator.setBinding(SmsTemplateVariablesValidator.FIELD_TEMPLATE, FIELD_ERIBTEXT);
		eribValidator.setBinding(SmsTemplateVariablesValidator.FIELD_IS_CSA_MESSAGE_TEMPLATE, FIELD_CSA_MESSAGE_TEMPLATE);
		eribValidator.setBinding(SmsTemplateVariablesValidator.FIELD_IS_ERMB_MESSAGE_TEMPLATE, FIELD_ERMB_MESSAGE_TEMPLATE);

		SmsTemplateVariablesValidator mapiValidator = new SmsTemplateVariablesValidator(ChannelType.MOBILE_API);
		mapiValidator.setEnabledExpression(new RhinoExpression("form.mapiTemplateType"));
		mapiValidator.setBinding(SmsTemplateVariablesValidator.FIELD_ID, FIELD_ID);
		mapiValidator.setBinding(SmsTemplateVariablesValidator.FIELD_TEMPLATE, FIELD_MAPITEXT);
		mapiValidator.setBinding(SmsTemplateVariablesValidator.FIELD_IS_CSA_MESSAGE_TEMPLATE, FIELD_CSA_MESSAGE_TEMPLATE);
		mapiValidator.setBinding(SmsTemplateVariablesValidator.FIELD_IS_ERMB_MESSAGE_TEMPLATE, FIELD_ERMB_MESSAGE_TEMPLATE);

		SmsTemplateVariablesValidator atmValidator = new SmsTemplateVariablesValidator(ChannelType.SELF_SERVICE_DEVICE);
		atmValidator.setEnabledExpression(new RhinoExpression("form.amtTemplateType"));
		atmValidator.setBinding(SmsTemplateVariablesValidator.FIELD_ID, FIELD_ID);
		atmValidator.setBinding(SmsTemplateVariablesValidator.FIELD_TEMPLATE, FIELD_ATMTEXT);
		atmValidator.setBinding(SmsTemplateVariablesValidator.FIELD_IS_CSA_MESSAGE_TEMPLATE, FIELD_CSA_MESSAGE_TEMPLATE);
		atmValidator.setBinding(SmsTemplateVariablesValidator.FIELD_IS_ERMB_MESSAGE_TEMPLATE, FIELD_ERMB_MESSAGE_TEMPLATE);

        SmsTemplateVariablesValidator socialValidator = new SmsTemplateVariablesValidator(ChannelType.SELF_SERVICE_DEVICE);
		socialValidator.setEnabledExpression(new RhinoExpression("form.socialTemplateType"));
		socialValidator.setBinding(SmsTemplateVariablesValidator.FIELD_ID, FIELD_ID);
		socialValidator.setBinding(SmsTemplateVariablesValidator.FIELD_TEMPLATE, FIELD_SOCIALTEXT);
		socialValidator.setBinding(SmsTemplateVariablesValidator.FIELD_IS_CSA_MESSAGE_TEMPLATE, FIELD_CSA_MESSAGE_TEMPLATE);
		socialValidator.setBinding(SmsTemplateVariablesValidator.FIELD_IS_ERMB_MESSAGE_TEMPLATE, FIELD_ERMB_MESSAGE_TEMPLATE);

		formBuilder.addFormValidators(eribValidator, mapiValidator, atmValidator, socialValidator);
		return formBuilder.build();
	}
}
