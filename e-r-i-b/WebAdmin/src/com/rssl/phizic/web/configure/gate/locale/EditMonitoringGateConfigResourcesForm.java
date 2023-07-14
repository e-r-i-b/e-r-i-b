package com.rssl.phizic.web.configure.gate.locale;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.MultiLineTextValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.configure.gate.EditMonitoringGateConfigForm;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseForm;

/**
 * Форма редактирования многоязычных текстовок для MonitoringServiceGateStateConfig
 * @author komarov
 * @ created 29.05.2015
 * @ $Author$
 * @ $Revision$
 */
public class EditMonitoringGateConfigResourcesForm extends EditLanguageResourcesBaseForm
{
	public static final Form EDIT_FORM = createEditForm();
	@SuppressWarnings({"OverlyLongMethod", "ReuseOfLocalVariable"})
	private static Form createEditForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(EditMonitoringGateConfigForm.SERVICE_NAME_FIELD_NAME);
		fieldBuilder.setDescription("Наименование сервиса");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator("Редактируемое наименование сервиса не известно."));
		formBuilder.addField(fieldBuilder.build());


		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(EditMonitoringGateConfigForm.MESSAGE_TEXT_FIELD_NAME);
		fieldBuilder.setDescription("Текст сообщения.");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator("Пожалуйста, введите текст сообщения."),
				new MultiLineTextValidator("Поле Текст сообщения не должно превышать", EditMonitoringGateConfigForm.MAX_TEXT_SYMBOL_COUNT));
		formBuilder.addField(fieldBuilder.build());


		return formBuilder.build();
	}

}
