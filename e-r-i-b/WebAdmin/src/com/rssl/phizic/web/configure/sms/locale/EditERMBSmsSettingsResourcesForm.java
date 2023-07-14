package com.rssl.phizic.web.configure.sms.locale;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.claims.forms.validators.SmsTemplateVariablesValidator;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseForm;

/**
 * @author koptyaev
 * @ created 10.06.2015
 * @ $Author$
 * @ $Revision$
 */
public class EditERMBSmsSettingsResourcesForm   extends EditLanguageResourcesBaseForm
{
	static final String FIELD_CSA_MESSAGE_TEMPLATE   = "csaTemplate";
	static final String FIELD_ERMB_MESSAGE_TEMPLATE  = "ermbTemplate";
	static final String FIELD_ERMB_TEXT              = "ermbLocaleText";
	static final String FIELD_ID                     = "id";

	public static final Form EDIT_LANGUAGE_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder field = new FieldBuilder();
		field.setName(FIELD_ID);
		field.setType(LongType.INSTANCE.getName());
		field.setDescription("Идентификатор шаблона");
		formBuilder.addField( field.build() );


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
