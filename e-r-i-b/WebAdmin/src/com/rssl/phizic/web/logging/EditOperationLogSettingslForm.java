package com.rssl.phizic.web.logging;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.RangeFieldValidator;
import com.rssl.phizic.logging.Constants;

/**
 * @author Krenev
 * @ created 01.09.2009
 * @ $Author$
 * @ $Revision$
 */
public class EditOperationLogSettingslForm extends EditLoggingSettingsFormBase
{
	@Override
	protected String getLogPrefix()
	{
		return Constants.USER_LOG_PREFIX;
	}

	public void addAdditionalFields(FormBuilder fb)
	{
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Степень детализации");
		fieldBuilder.setName(getLogPrefix() + "level");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Уровень детализации");
		fieldBuilder.setName(getLogPrefix() + "mode");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Степень детализации расширенного логирования");
		fieldBuilder.setName(getLogPrefix() + "extended.level");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Уровень детализации расширенного логирования");
		fieldBuilder.setName(getLogPrefix() + "extended.mode");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.phizic.logging.writers.OperationLogWriter
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Выбор места логирования журнала действий пользователя");
		fieldBuilder.setName("com.rssl.phizic.logging.writers.OperationLogWriter");
		fieldBuilder.setType("string");
		RangeFieldValidator validator = new RangeFieldValidator("com.rssl.phizic.logging.operations.DatabaseOperationLogWriter", "com.rssl.phizic.logging.operations.JMSOperationLogWriter");
		validator.setMessage("Для поля '" + fieldBuilder.getDescription() + "' значение вне диапазона");
		fieldBuilder.addValidators(validator);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.phizic.logging.writers.OperationLogWriter.backup
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Выбор места резервного логирования журнала действий пользователя");
		fieldBuilder.setName("com.rssl.phizic.logging.writers.OperationLogWriter.backup");
		fieldBuilder.setType("string");
		validator = new RangeFieldValidator("com.rssl.phizic.logging.operations.DatabaseOperationLogWriter", "com.rssl.phizic.logging.operations.JMSOperationLogWriter");
		validator.setMessage("Для поля '" + fieldBuilder.getDescription() + "' значение вне диапазона");
		fieldBuilder.addValidators(validator);
		fb.addField(fieldBuilder.build());
	}
}
