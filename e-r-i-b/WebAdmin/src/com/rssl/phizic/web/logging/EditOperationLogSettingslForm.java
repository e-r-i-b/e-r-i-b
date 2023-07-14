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
		fieldBuilder.setDescription("������� �����������");
		fieldBuilder.setName(getLogPrefix() + "level");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������� �����������");
		fieldBuilder.setName(getLogPrefix() + "mode");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������� ����������� ������������ �����������");
		fieldBuilder.setName(getLogPrefix() + "extended.level");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������� ����������� ������������ �����������");
		fieldBuilder.setName(getLogPrefix() + "extended.mode");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());

		//���� com.rssl.phizic.logging.writers.OperationLogWriter
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ����� ����������� ������� �������� ������������");
		fieldBuilder.setName("com.rssl.phizic.logging.writers.OperationLogWriter");
		fieldBuilder.setType("string");
		RangeFieldValidator validator = new RangeFieldValidator("com.rssl.phizic.logging.operations.DatabaseOperationLogWriter", "com.rssl.phizic.logging.operations.JMSOperationLogWriter");
		validator.setMessage("��� ���� '" + fieldBuilder.getDescription() + "' �������� ��� ���������");
		fieldBuilder.addValidators(validator);
		fb.addField(fieldBuilder.build());

		//���� com.rssl.phizic.logging.writers.OperationLogWriter.backup
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ����� ���������� ����������� ������� �������� ������������");
		fieldBuilder.setName("com.rssl.phizic.logging.writers.OperationLogWriter.backup");
		fieldBuilder.setType("string");
		validator = new RangeFieldValidator("com.rssl.phizic.logging.operations.DatabaseOperationLogWriter", "com.rssl.phizic.logging.operations.JMSOperationLogWriter");
		validator.setMessage("��� ���� '" + fieldBuilder.getDescription() + "' �������� ��� ���������");
		fieldBuilder.addValidators(validator);
		fb.addField(fieldBuilder.build());
	}
}
