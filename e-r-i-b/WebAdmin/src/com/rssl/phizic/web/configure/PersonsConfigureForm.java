package com.rssl.phizic.web.configure;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.validators.NumericRangeValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.passwords.PasswordValidationConfig;
import com.rssl.common.forms.validators.passwords.generated.Charset;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

import java.util.List;
import static com.rssl.phizic.security.config.Constants.*;

/**
 * User: IIvanova
 * Date: 14.02.2006
 * Time: 13:36:45
 */
public class PersonsConfigureForm extends EditPropertiesFormBase
{
	private static final String TIME_BLOCKING = "86400000";
	private static final Form FORM = createForm();

	@Override
	public Form getForm()
	{
		return FORM;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;

		//passwordLength
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(PASSWORD_LENGTH);
		fieldBuilder.setType("integer");
		fieldBuilder.setDescription("����� ������");

		RequiredFieldValidator requiredFieldValidatorCardPasswordLength = new RequiredFieldValidator();
		requiredFieldValidatorCardPasswordLength.setMessage("������� �������� � ����: " + fieldBuilder.getDescription());
		RegexpFieldValidator regexpFieldValidatorCardPasswordLength = new RegexpFieldValidator("\\d{1,2}");

		PasswordValidationConfig passwordValidationConfig = ConfigFactory.getConfig(PasswordValidationConfig.class);
		int passwordLength = passwordValidationConfig.getMinimalLength("employee");

		regexpFieldValidatorCardPasswordLength.setMessage("�������� ������ ������ � ����: " + fieldBuilder.getDescription() + ", ������� �������� �������� � ��������� "+passwordLength+"...20.");
		NumericRangeValidator numericRangeValidatorCardPasswordLength = new NumericRangeValidator();
		numericRangeValidatorCardPasswordLength.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, ""+passwordLength);
		numericRangeValidatorCardPasswordLength.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "20");
		numericRangeValidatorCardPasswordLength.setMessage("�������� ������ ������ � ����: " + fieldBuilder.getDescription() + ", ������� �������� �������� � ��������� "+passwordLength+"...20.");
		fieldBuilder.setValidators(new FieldValidator[]{requiredFieldValidatorCardPasswordLength, regexpFieldValidatorCardPasswordLength, numericRangeValidatorCardPasswordLength});
		formBuilder.addField(fieldBuilder.build());

		//passwordAllowedChars
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(PASSWORD_ALLOWED_CHARS);
		fieldBuilder.setDescription("����� ���������� ��������");

		RequiredFieldValidator requiredFieldValidatorCardPasswordAllowedChars = new RequiredFieldValidator();
		requiredFieldValidatorCardPasswordAllowedChars.setMessage("������� �������� � ����: " + fieldBuilder.getDescription());
		fieldBuilder.setValidators(new FieldValidator[]{requiredFieldValidatorCardPasswordAllowedChars});
		formBuilder.addField(fieldBuilder.build());

		//loginAllowedChars
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(LOGIN_ALLOWED_CHARS);
		fieldBuilder.setDescription("����� ���������� �������� ��� ������");

		RequiredFieldValidator requiredFieldValidatorLoginAllowedChars = new RequiredFieldValidator();
		requiredFieldValidatorCardPasswordAllowedChars.setMessage("������� �������� � ����: " + fieldBuilder.getDescription());
		fieldBuilder.setValidators(new FieldValidator[]{requiredFieldValidatorLoginAllowedChars});
		formBuilder.addField(fieldBuilder.build());

		//confirmAttempts
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(LOGIN_ATTEMPTS);
		fieldBuilder.setType("integer");
		fieldBuilder.setDescription("���������� ������� (�����������)");

		RequiredFieldValidator requiredFieldValidatorConfirmAttempts = new RequiredFieldValidator();
		requiredFieldValidatorConfirmAttempts.setMessage("������� �������� � ����: " + fieldBuilder.getDescription());
		RegexpFieldValidator regexpFieldValidatorConfirmAttempts = new RegexpFieldValidator("\\d{1,2}");
		regexpFieldValidatorConfirmAttempts.setMessage("�������� ������ ������ � ����: " + fieldBuilder.getDescription() + ", ������� �������� �������� � ��������� 1...10.");
		NumericRangeValidator numericRangeValidatorConfirmAttempts = new NumericRangeValidator();
		numericRangeValidatorConfirmAttempts.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "1");
		numericRangeValidatorConfirmAttempts.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "10");
		numericRangeValidatorConfirmAttempts.setMessage("�������� ������ ������ � ����: " + fieldBuilder.getDescription() + ", ������� �������� �������� � ��������� 1...10.");
		fieldBuilder.setValidators(new FieldValidator[]{requiredFieldValidatorConfirmAttempts, regexpFieldValidatorConfirmAttempts, numericRangeValidatorConfirmAttempts});
		formBuilder.addField(fieldBuilder.build());

		//blockedTimeout
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(BLOCKED_TIMEOUT);
		fieldBuilder.setDescription("����� ����������");

		RequiredFieldValidator requiredFieldValidatorBlockedTimeout = new RequiredFieldValidator();
		requiredFieldValidatorBlockedTimeout.setMessage("������� �������� � ����: " + fieldBuilder.getDescription());
		RegexpFieldValidator regexpFieldValidatorCardsCount = new RegexpFieldValidator("\\d{1,10}");
		regexpFieldValidatorCardsCount.setMessage("�������� ������ ������ � ����: " + fieldBuilder.getDescription() + " ������� �������� �������� � ��������� 1..." + TIME_BLOCKING + ".");
		NumericRangeValidator numericRangeValidatorCardsCount = new NumericRangeValidator();
		numericRangeValidatorCardsCount.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "1");
		numericRangeValidatorCardsCount.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, TIME_BLOCKING);
		numericRangeValidatorCardsCount.setMessage("�������� ������ ������ � ����: " + fieldBuilder.getDescription() + " , ������� �������� �������� � ��������� 1..." + TIME_BLOCKING + ".");
		fieldBuilder.setValidators(new FieldValidator[]{requiredFieldValidatorBlockedTimeout, regexpFieldValidatorCardsCount, numericRangeValidatorCardsCount});
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(API_PIN_LENGTH);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("����������� ����� ������ ��� ���������� � ����������� ����������");
		fieldBuilder.addValidators(new RequiredFieldValidator(), new RegexpFieldValidator("\\d+", "����������� ����� ������ ��� ���������� � ����������� ���������� ������ ���� ������� � ������."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(API_PIN_REGEXP);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("���������� ���������, ������������ ���������� ������� � ������ ��� ���������� � ����������� ����������");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	private List<Charset> passwordCharsets;

	public List<Charset> getPasswordCharsets()
	{
		return passwordCharsets;
	}

	public void setPasswordCharsets(List<Charset> passwordCharsets)
	{
		this.passwordCharsets = passwordCharsets;
	}
}
