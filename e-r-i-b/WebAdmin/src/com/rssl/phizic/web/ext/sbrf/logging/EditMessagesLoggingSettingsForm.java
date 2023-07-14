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
		//���� com.rssl.phizic.logging.writers.MessageLogWriter
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ����� ����������� ������� ���������");
		fieldBuilder.setName("com.rssl.phizic.logging.writers.MessageLogWriter");
		fieldBuilder.setType("string");
		RangeFieldValidator validator = new RangeFieldValidator("com.rssl.phizic.logging.messaging.DatabaseMessageLogWriter", "com.rssl.phizic.logging.messaging.JMSMessageLogWriter", "com.rssl.phizic.logging.messaging.ConsoleMessageLogWriter");
		validator.setMessage("��� ���� '" + fieldBuilder.getDescription() + "' �������� ��� ���������");
		fieldBuilder.addValidators(validator);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.phizic.logging.writers.MessageLogWriter.backup
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ����� ���������� ����������� ������� ���������");
		fieldBuilder.setName("com.rssl.phizic.logging.writers.MessageLogWriter.backup");
		fieldBuilder.setType("string");
		validator = new RangeFieldValidator("com.rssl.phizic.logging.messaging.DatabaseMessageLogWriter", "com.rssl.phizic.logging.messaging.JMSMessageLogWriter", "com.rssl.phizic.logging.messaging.ConsoleMessageLogWriter");
		validator.setMessage("��� ���� '" + fieldBuilder.getDescription() + "' �������� ��� ���������");
		fieldBuilder.addValidators(validator);
		fb.addField(fieldBuilder.build());
	}

	private void addLebelFields(FormBuilder fb, boolean isExtended)
	{
		String extendedPrefix = isExtended ? EXTENDED_PREFIX : "";
		String extendedDescriptionPrefix = isExtended ? "����������� �" : "�";
		FieldBuilder fieldBuilder;
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription(extendedDescriptionPrefix + "���������� ��������� �������������� ��� � iPas");
		fieldBuilder.setName(getLogPrefix() + extendedPrefix + "level.iPas");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RangeFieldValidator("on", "off"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription(extendedDescriptionPrefix + "���������� ��������� �������������� ���� � ESB");
		fieldBuilder.setName(getLogPrefix() + extendedPrefix + "level.esberib");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RangeFieldValidator("on", "off"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription(extendedDescriptionPrefix + "���������� ��������� �������������� ���� � ���");
		fieldBuilder.setName(getLogPrefix() + extendedPrefix + "level.csa");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RangeFieldValidator("on", "off"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription(extendedDescriptionPrefix + "���������� ��������� �������������� ���� � ���-Back");
		fieldBuilder.setName(getLogPrefix() + extendedPrefix + "level.CSA2");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RangeFieldValidator("on", "off"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription(extendedDescriptionPrefix + "���������� ��������� �������������� ���� � �obile");
		fieldBuilder.setName(getLogPrefix() + extendedPrefix + "level.mobile");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RangeFieldValidator("on", "off"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription(extendedDescriptionPrefix + "���������� ��������� �������������� ���� � ATM");
		fieldBuilder.setName(getLogPrefix() + extendedPrefix + "level.atm");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RangeFieldValidator("on", "off"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription(extendedDescriptionPrefix + "���������� ��������� �������������� ���� � ���");
		fieldBuilder.setName(getLogPrefix() + extendedPrefix + "level.pfr");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RangeFieldValidator("on", "off"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription(extendedDescriptionPrefix + "���������� ��������� �������������� ���� � SHOP");
		fieldBuilder.setName(getLogPrefix() + extendedPrefix + "level.shop");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RangeFieldValidator("on", "off"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription(extendedDescriptionPrefix + "���������� ��������� �������������� ���� � BARS");
		fieldBuilder.setName(getLogPrefix() + extendedPrefix + "level.bars");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RangeFieldValidator("on", "off"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription(extendedDescriptionPrefix + "���������� ��������� �������������� ���� � DEPO");
		fieldBuilder.setName(getLogPrefix() + extendedPrefix + "level.depo");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RangeFieldValidator("on", "off"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription(extendedDescriptionPrefix + "���������� ��������� �������������� ���� � MDM");
		fieldBuilder.setName(getLogPrefix() + extendedPrefix + "level.mdm");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RangeFieldValidator("on", "off"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription(extendedDescriptionPrefix + "���������� ��������� �������������� ���� � JDBC");
		fieldBuilder.setName(getLogPrefix() + extendedPrefix + "level.jdbc");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RangeFieldValidator("on", "off"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription(extendedDescriptionPrefix + "���������� ��������� �������������� ���� � ���");
		fieldBuilder.setName(getLogPrefix() + extendedPrefix + "level.IPS");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RangeFieldValidator("on", "off"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription(extendedDescriptionPrefix + "���������� ��������� �������������� ���� � WebAPI");
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
            fieldBuilder.setDescription("��������� ����������� ���������");
			fieldBuilder.setName(EditMessagesLoggingOperation.EXCLUDED_MESSAGES_CODE_KEY + i);
			fieldBuilder.setType(StringType.INSTANCE.getName());
			fieldBuilder.addValidators(new RequiredFieldValidator(),
				new RegexpFieldValidator(".{1,200}", "���� \"" + fieldBuilder.getDescription() + "\" ������ ��������� �� 1 �� 200 ��������."));
			formBuilder.addField(fieldBuilder.build());
		}
	}
}