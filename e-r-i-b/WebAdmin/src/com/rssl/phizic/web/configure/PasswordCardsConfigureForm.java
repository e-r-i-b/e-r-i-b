package com.rssl.phizic.web.configure;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.validators.NumericRangeValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;
import static com.rssl.phizic.security.config.Constants.*;
/**
 * User: IIvanova
 * Date: 14.02.2006
 * Time: 13:36:45
 */
public class PasswordCardsConfigureForm  extends EditPropertiesFormBase
{
	private static final Form FORM = createForm();

	@Override
	public Form getForm()
	{
		return FORM;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		Field[]      fields = new Field[3];
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(CARDS_COUNT);
		fieldBuilder.setDescription("�����");

		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
		requiredFieldValidator.setMessage("������� �������� � ����: " + fieldBuilder.getDescription());
		RegexpFieldValidator regexpFieldValidatorCardsCount = new RegexpFieldValidator("\\d{1,3}");
		regexpFieldValidatorCardsCount.setMessage("� ��������� \\u0022�������� ����\\u0022 � ���� \\u0022" + fieldBuilder.getDescription()+ " \\u0022 ������� ���������� ���� � ��������� 1..100");
		NumericRangeValidator numericRangeValidatorCardsCount = new NumericRangeValidator();
		numericRangeValidatorCardsCount.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "1");
		numericRangeValidatorCardsCount.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "100");
		numericRangeValidatorCardsCount.setMessage("� ��������� \\u0022�������� ����\\u0022 � ���� \\u0022" + fieldBuilder.getDescription()+ " \\u0022 ������� ���������� ���� � ��������� 1..100");

		fieldBuilder.setValidators(new FieldValidator[]{requiredFieldValidator, regexpFieldValidatorCardsCount, numericRangeValidatorCardsCount});


		fields[0] = fieldBuilder.build();

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(CARDPASSWORDS_COUNT);
		fieldBuilder.setType("integer");
		fieldBuilder.setDescription("�����");

		RequiredFieldValidator requiredFieldValidatorKeysCount = new RequiredFieldValidator();
		requiredFieldValidatorKeysCount.setMessage("������� �������� � ����: "+ fieldBuilder.getDescription());
		RegexpFieldValidator regexpFieldValidatorKeysCount = new RegexpFieldValidator("\\d{1,3}");
		regexpFieldValidatorKeysCount.setMessage("� ��������� \\u0022�������� ����\\u0022 � ���� \\u0022" + fieldBuilder.getDescription()+ " \\u0022 ������� ���������� ������ � ��������� 1..500");
		NumericRangeValidator numericRangeValidatorKeysCount = new NumericRangeValidator();
		numericRangeValidatorKeysCount.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "1");
		numericRangeValidatorKeysCount.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "500");
		numericRangeValidatorKeysCount.setMessage("� ��������� \\u0022�������� ����\\u0022 � ���� \\u0022" + fieldBuilder.getDescription()+ " \\u0022 ������� ���������� ������ � ��������� 1..500");

		fieldBuilder.setValidators(new FieldValidator[]{requiredFieldValidatorKeysCount, regexpFieldValidatorKeysCount, numericRangeValidatorKeysCount});

		fields[1] = fieldBuilder.build();

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(CARDPASSWORD_LENGTH);
		fieldBuilder.setType("integer");
		fieldBuilder.setDescription("����� �����");

		RequiredFieldValidator requiredFieldValidatorCardPasswordLength = new RequiredFieldValidator();
		requiredFieldValidatorCardPasswordLength.setMessage("������� �������� � ����: " + fieldBuilder.getDescription());
		RegexpFieldValidator regexpFieldValidatorCardPasswordLength = new RegexpFieldValidator("\\d{1,2}");
		regexpFieldValidatorCardPasswordLength.setMessage("� ���� \\u0022" + fieldBuilder.getDescription() + "\\u0022  ������� ����� ������ � ��������� 1..20");
		NumericRangeValidator numericRangeValidatorCardPasswordLength = new NumericRangeValidator();
		numericRangeValidatorCardPasswordLength.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "1");
		numericRangeValidatorCardPasswordLength.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "20");
		numericRangeValidatorCardPasswordLength.setMessage("� ���� \\u0022" + fieldBuilder.getDescription() + "\\u0022  ������� ����� ������ � ��������� 1..20");
		fieldBuilder.setValidators(new FieldValidator[]{requiredFieldValidatorCardPasswordLength, regexpFieldValidatorCardPasswordLength, numericRangeValidatorCardPasswordLength});

		//�ardPasswordAllowedChars
		fields[2] = fieldBuilder.build();

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(CARDPASSWORD_ALLOWED_CHARS);
		fieldBuilder.setDescription("����� ���������� ��������");

		RequiredFieldValidator requiredFieldValidatorCardPasswordAllowedChars = new RequiredFieldValidator();
		requiredFieldValidatorCardPasswordAllowedChars.setMessage("������� �������� � ����: " + fieldBuilder.getDescription());
		fieldBuilder.setValidators(new FieldValidator[]{requiredFieldValidatorCardPasswordAllowedChars});

//		fields[3] = fieldBuilder.getField();
//
//		//confirmAttempts
//		fieldBuilder = new FieldBuilder();
//		fieldBuilder.setDictionaryName("confirmAttempts");
//		fieldBuilder.setType("integer");
//		fieldBuilder.setDescription("���������� ����������� ������� ��� �������������");
//
//		RequiredFieldValidator requiredFieldValidatorConfirmAttempts = new RequiredFieldValidator();
//		requiredFieldValidator.setMessage("������� �������� � ����: " + fieldBuilder.getDescription());
//		RegexpFieldValidator regexpFieldValidator�onfirmAttempts = new RegexpFieldValidator("\\d{1,2}");
//		regexpFieldValidator�onfirmAttempts.setMessage("�������� ������ ������ � ����: " + fieldBuilder.getDescription() + ", ������� �������� �������� � ��������� 1...10.");
//		NumericRangeValidator numericRangeValidator�onfirmAttempts = new NumericRangeValidator();
//		numericRangeValidator�onfirmAttempts.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "1");
//		numericRangeValidator�onfirmAttempts.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "10");
//		numericRangeValidator�onfirmAttempts.setMessage("�������� ������ ������ � ����: " + fieldBuilder.getDescription() + ", ������� �������� �������� � ��������� 1...10.");
//		fieldBuilder.setValidators(new FieldValidator[]{requiredFieldValidatorConfirmAttempts, regexpFieldValidatorCardPasswordLength, numericRangeValidatorCardPasswordLength});

		formBuilder.setFields(fields);

		return formBuilder.build();
	}
}
