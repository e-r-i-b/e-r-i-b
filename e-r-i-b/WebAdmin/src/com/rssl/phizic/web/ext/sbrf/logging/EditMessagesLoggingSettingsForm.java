package com.rssl.phizic.web.ext.sbrf.logging;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RangeFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.operations.logging.EditMessagesLoggingOperation;
import com.rssl.phizic.utils.PropertyHelper;
import com.rssl.phizic.web.logging.EditLoggingSettingsFormBase;

import java.util.List;

import static com.rssl.phizic.logging.system.LogLevelConfig.EXTENDED_PREFIX;

/**
 * @author Krenev
 * @ created 10.09.2009
 * @ $Author$
 * @ $Revision$
 */
public class EditMessagesLoggingSettingsForm extends EditLoggingSettingsFormBase
{
	@Override
	protected String getLogPrefix()
	{
		return Constants.MESSAGE_LOG_PREFIX;
	}

	@Override
	public Form getForm()
	{
		return createFormWithListFields(PropertyHelper.getTableSettingNumbers(getFields(), EditMessagesLoggingOperation.EXCLUDED_MESSAGES_CODE_KEY));
	}

	public void addAdditionalFields(FormBuilder fb)
	{
		addLebelFields(fb, false);
		addLebelFields(fb, true);

		FieldBuilder fieldBuilder;
		//Поле com.rssl.phizic.logging.writers.MessageLogWriter
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Выбор места логирования журнала сообщений");
		fieldBuilder.setName("com.rssl.phizic.logging.writers.MessageLogWriter");
		fieldBuilder.setType("string");
		RangeFieldValidator validator = new RangeFieldValidator("com.rssl.phizic.logging.messaging.DatabaseMessageLogWriter", "com.rssl.phizic.logging.messaging.JMSMessageLogWriter", "com.rssl.phizic.logging.messaging.ConsoleMessageLogWriter");
		validator.setMessage("Для поля '" + fieldBuilder.getDescription() + "' значение вне диапазона");
		fieldBuilder.addValidators(validator);
		fb.addField(fieldBuilder.build());

		//Поле com.rssl.phizic.logging.writers.MessageLogWriter.backup
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Выбор места резервного логирования журнала сообщений");
		fieldBuilder.setName("com.rssl.phizic.logging.writers.MessageLogWriter.backup");
		fieldBuilder.setType("string");
		validator = new RangeFieldValidator("com.rssl.phizic.logging.messaging.DatabaseMessageLogWriter", "com.rssl.phizic.logging.messaging.JMSMessageLogWriter", "com.rssl.phizic.logging.messaging.ConsoleMessageLogWriter");
		validator.setMessage("Для поля '" + fieldBuilder.getDescription() + "' значение вне диапазона");
		fieldBuilder.addValidators(validator);
		fb.addField(fieldBuilder.build());
	}

	private void addLebelFields(FormBuilder fb, boolean isExtended)
	{
		String extendedPrefix = isExtended ? EXTENDED_PREFIX : "";
		String extendedDescriptionPrefix = isExtended ? "Расширенное л" : "Л";
		FieldBuilder fieldBuilder;
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription(extendedDescriptionPrefix + "огирование сообщений взаимодействия ЦСА и iPas");
		fieldBuilder.setName(getLogPrefix() + extendedPrefix + "level.iPas");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RangeFieldValidator("on", "off"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription(extendedDescriptionPrefix + "огирование сообщений взаимодействия ЕРИБ и ESB");
		fieldBuilder.setName(getLogPrefix() + extendedPrefix + "level.esberib");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RangeFieldValidator("on", "off"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription(extendedDescriptionPrefix + "огирование сообщений взаимодействия ЕРИБ и ЦСА");
		fieldBuilder.setName(getLogPrefix() + extendedPrefix + "level.csa");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RangeFieldValidator("on", "off"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription(extendedDescriptionPrefix + "огирование сообщений взаимодействия ЕРИБ и ЦСА-Back");
		fieldBuilder.setName(getLogPrefix() + extendedPrefix + "level.CSA2");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RangeFieldValidator("on", "off"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription(extendedDescriptionPrefix + "огирование сообщений взаимодействия ЕРИБ и Мobile");
		fieldBuilder.setName(getLogPrefix() + extendedPrefix + "level.mobile");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RangeFieldValidator("on", "off"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription(extendedDescriptionPrefix + "огирование сообщений взаимодействия ЕРИБ и ATM");
		fieldBuilder.setName(getLogPrefix() + extendedPrefix + "level.atm");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RangeFieldValidator("on", "off"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription(extendedDescriptionPrefix + "огирование сообщений взаимодействия ЕРИБ и ПФР");
		fieldBuilder.setName(getLogPrefix() + extendedPrefix + "level.pfr");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RangeFieldValidator("on", "off"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription(extendedDescriptionPrefix + "огирование сообщений взаимодействия ЕРИБ и SHOP");
		fieldBuilder.setName(getLogPrefix() + extendedPrefix + "level.shop");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RangeFieldValidator("on", "off"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription(extendedDescriptionPrefix + "огирование сообщений взаимодействия ЕРИБ и BARS");
		fieldBuilder.setName(getLogPrefix() + extendedPrefix + "level.bars");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RangeFieldValidator("on", "off"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription(extendedDescriptionPrefix + "огирование сообщений взаимодействия ЕРИБ и DEPO");
		fieldBuilder.setName(getLogPrefix() + extendedPrefix + "level.depo");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RangeFieldValidator("on", "off"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription(extendedDescriptionPrefix + "огирование сообщений взаимодействия ЕРИБ и MDM");
		fieldBuilder.setName(getLogPrefix() + extendedPrefix + "level.mdm");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RangeFieldValidator("on", "off"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription(extendedDescriptionPrefix + "огирование сообщений взаимодействия ЕРИБ и JDBC");
		fieldBuilder.setName(getLogPrefix() + extendedPrefix + "level.jdbc");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RangeFieldValidator("on", "off"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription(extendedDescriptionPrefix + "огирование сообщений взаимодействия ЕРИБ и ИПС");
		fieldBuilder.setName(getLogPrefix() + extendedPrefix + "level.IPS");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RangeFieldValidator("on", "off"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription(extendedDescriptionPrefix + "огирование сообщений взаимодействия ЕРИБ и WebAPI");
		fieldBuilder.setName(getLogPrefix() + extendedPrefix + "level.WebAPI");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RangeFieldValidator("on", "off"));
		fb.addField(fieldBuilder.build());
	}
	
	private Form createFormWithListFields(List<Integer> fieldsExcludedMessagesPropertiesNumber)
	{
		FormBuilder fb = new FormBuilder();
		addBaseField(fb);
		addAdditionalFields(fb);
		addListFields(fb, fieldsExcludedMessagesPropertiesNumber);
		return fb.build();
	}

	private void addListFields(FormBuilder formBuilder, List<Integer> fieldsExcludedMessagesPropertiesNumber)
	{
		FieldBuilder fieldBuilder;

		for (Integer i : fieldsExcludedMessagesPropertiesNumber)
		{
			fieldBuilder = new FieldBuilder();
            fieldBuilder.setDescription("Настройка исключаемых сообщений");
			fieldBuilder.setName(EditMessagesLoggingOperation.EXCLUDED_MESSAGES_CODE_KEY + i);
			fieldBuilder.setType(StringType.INSTANCE.getName());
			fieldBuilder.addValidators(new RequiredFieldValidator(),
				new RegexpFieldValidator(".{1,200}", "Поле \"" + fieldBuilder.getDescription() + "\" должно содержать от 1 до 200 символов."));
			formBuilder.addField(fieldBuilder.build());
		}
	}
}