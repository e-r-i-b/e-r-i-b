package com.rssl.phizic.web.configure.addressBookSettings;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.IntegerType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.config.addressbook.AddressBookSynchronizationConfig;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author lepihina
 * @ created 09.06.14
 * $Author$
 * $Revision$
 * �������������� �������� ���������� � ���������� ������
 */
public class EditThresholdNotificationSettingsForm extends EditPropertiesFormBase
{

	private static final BigInteger maxLength = BigInteger.valueOf(500L);
	private static final Form EDIT_FORM = createForm();

	public Form getForm()
	{
		return EDIT_FORM;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
		LengthFieldValidator lengthFieldValidator = new LengthFieldValidator(maxLength);
		lengthFieldValidator.setMessage("����� email-����������� �� ������ ��������� ����� " + maxLength + " ��������.");

		FieldBuilder fieldBuilder;
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(AddressBookSynchronizationConfig.PERIOD_TYPE_KEY);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("������ �������");
		fieldBuilder.addValidators(requiredFieldValidator);
		formBuilder.addField(fieldBuilder.build());

		String message = "���� \"����� ��������� � ������� �������������\" ������ ��������� ������������� �����";
		RegexpFieldValidator regexpFieldValidator = new RegexpFieldValidator("(-?\\d+)|\\d*", message);
		NumericRangeValidator numericRangeValidator = new NumericRangeValidator(BigDecimal.valueOf(0L), BigDecimal.valueOf(99999L), message);
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(AddressBookSynchronizationConfig.THRESHOLD_COUNT_KEY);
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		fieldBuilder.setDescription("����� ��������� � ������� �������������");
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredFieldValidator, regexpFieldValidator, numericRangeValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(AddressBookSynchronizationConfig.EMAIL_TEXT_KEY);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("����� email-�����������");
		fieldBuilder.addValidators(requiredFieldValidator, lengthFieldValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(AddressBookSynchronizationConfig.EMAIL_KEY);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("email-�����, �� ������� ����� ������������ �������������� �����");
		fieldBuilder.addValidators(requiredFieldValidator, new EmailFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
