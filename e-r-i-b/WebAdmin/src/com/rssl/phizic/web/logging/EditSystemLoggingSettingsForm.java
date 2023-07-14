package com.rssl.phizic.web.logging;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.RangeFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.logging.Constants;

import static com.rssl.phizic.logging.system.LogLevelConfig.EXTENDED_PREFIX;

/**
 * @author Krenev
 * @ created 01.09.2009
 * @ $Author$
 * @ $Revision$
 */
public class EditSystemLoggingSettingsForm extends EditLoggingSettingsFormBase
{
	@Override
	protected String getLogPrefix()
	{
		return Constants.SYSTEM_LOG_PREFIX;
	}

	public void addAdditionalFields(FormBuilder fb)
	{
		addLevelFields(fb, false);
		addLevelFields(fb, true);

		FieldBuilder fieldBuilder;
		//Поле com.rssl.phizic.logging.writers.SystemLogWriter
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Выбор места логирования журнала системных действий");
		fieldBuilder.setName("com.rssl.phizic.logging.writers.SystemLogWriter");
		fieldBuilder.setType("string");
		RangeFieldValidator validator = new RangeFieldValidator("com.rssl.phizic.logging.system.DatabaseSystemLogWriter", "com.rssl.phizic.logging.system.JMSSystemLogWriter", "com.rssl.phizic.logging.system.ConsoleSystemLogWriter");
		validator.setMessage("Для поля '" + fieldBuilder.getDescription() + "' значение вне диапазона");
		fieldBuilder.addValidators(validator);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.phizic.logging.writers.SystemLogWriter.backup
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Выбор места резервного логирования журнала системных действий");
		fieldBuilder.setName("com.rssl.phizic.logging.writers.SystemLogWriter.backup");
		fieldBuilder.setType("string");
		validator = new RangeFieldValidator("com.rssl.phizic.logging.system.DatabaseSystemLogWriter", "com.rssl.phizic.logging.system.JMSSystemLogWriter", "com.rssl.phizic.logging.system.ConsoleSystemLogWriter");
		validator.setMessage("Для поля '" + fieldBuilder.getDescription() + "' значение вне диапазона");
		fieldBuilder.addValidators(validator);
		fb.addField(fieldBuilder.build());
	}

	private void addLevelFields(FormBuilder fb, boolean isExtended)
	{
		String extendedPrefix = isExtended ? EXTENDED_PREFIX : "";
		String extendedDescriptionPrefix = isExtended ? " расширенного" : "";
		FieldBuilder fieldBuilder;
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Уровень ведения" + extendedDescriptionPrefix + " логирования подсистемы Ядро");
		fieldBuilder.setName(getLogPrefix() + extendedPrefix + "level.Core");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Уровень ведения" + extendedDescriptionPrefix + " логирования подсистемы Шлюз");
		fieldBuilder.setName(getLogPrefix() + extendedPrefix + "level.Gate");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Уровень ведения" + extendedDescriptionPrefix + " логирования подсистемы Обработчик расписаний");
		fieldBuilder.setName(getLogPrefix() + extendedPrefix + "level.Scheduler");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Уровень ведения" + extendedDescriptionPrefix + " логирования подсистемы Система кеширования");
		fieldBuilder.setName(getLogPrefix() + extendedPrefix + "level.Cache");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Уровень ведения" + extendedDescriptionPrefix + " логирования подсистемы Веб");
		fieldBuilder.setName(getLogPrefix() + extendedPrefix + "level.Web");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());
	}
}
