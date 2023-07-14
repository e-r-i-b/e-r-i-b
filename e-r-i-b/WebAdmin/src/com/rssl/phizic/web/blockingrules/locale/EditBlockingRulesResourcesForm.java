package com.rssl.phizic.web.blockingrules.locale;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.web.blockingrules.EditConstants;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseForm;

/**
 * Форма редактирования многоязычных текстовок
 * @author komarov
 * @ created 15.05.2015
 * @ $Author$
 * @ $Revision$
 */
public class EditBlockingRulesResourcesForm extends EditLanguageResourcesBaseForm
{
	public static final Form EDIT_LANGUAGE_FORM = createForm();

	@SuppressWarnings("ReuseOfLocalVariable")
	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.ERIB_MESSAGE_FIELD);
		fieldBuilder.setDescription("Причина блокировки ЕРИБ");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("^(.|\\s){1,1024}$", "Вы неправильно указали причину блокировки ЕРИБ. Пожалуйста, введите не более 1024 символов."));
		formBuilder.addField(fieldBuilder.build());


		fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.MAPI_MESSAGE_FIELD);
		fieldBuilder.setDescription("Текст причины блокировки mApi");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("^(.|\\s){1,1024}$", "Вы неправильно указали причину блокировки mApi. Пожалуйста, введите не более 1024 символов."));
		formBuilder.addField(fieldBuilder.build());


		fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.ATM_MESSAGE_FIELD);
		fieldBuilder.setDescription("Текст причины блокировки atmApi");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("^(.|\\s){1,1024}$", "Вы неправильно указали причину блокировки atmApi. Пожалуйста, введите не более 1024 символов."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.ERMB_MESSAGE_FIELD);
		fieldBuilder.setDescription("Текст причины блокировки ЕРМБ");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("^(.|\\s){1,1024}$", "Вы неправильно указали причину блокировки ЕРМБ. Пожалуйста, введите не более 1024 символов."));
		formBuilder.addField(fieldBuilder.build());


		return formBuilder.build();
	}
}
