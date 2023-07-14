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
 * @ created 09.06.2015
 * @ $Author$
 * @ $Revision$
 */
public class EditCSASmsSettingsResourcesForm extends EditLanguageResourcesBaseForm
{
	public static final Form EDIT_LANGUAGE_FORM = createForm();

	static final String FIELD_CSA_MESSAGE_TEMPLATE  = "csaTemplate";
	static final String FIELD_ERMB_MESSAGE_TEMPLATE = "ermbTemplate";
	static final String FIELD_ID                    = "id";
	static final String FIELD_CSATEXT               = "csaLocaleText";

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
