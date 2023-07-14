package com.rssl.phizic.web.configure.mapi.locale;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.LengthFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.locale.entities.ERIBLocale;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

import java.math.BigInteger;

import static com.rssl.phizic.config.mobile.MobileApiConfig.API_INVALID_VERSION_TEXT;
import static com.rssl.phizic.config.mobile.MobileApiConfig.JAILBREAK_DISABLED_TEXT;
import static com.rssl.phizic.config.mobile.MobileApiConfig.JAILBREAK_ENABLED_TEXT;

/**
 * @author koptyaev
 * @ created 09.10.2014
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings("UnusedDeclaration")
public class MobileAPIConfigureResourcesForm extends EditPropertiesFormBase
{
	private static final String SEPARATOR = ".";
	private static final BigInteger PROP_VAL_MAX_LEN = BigInteger.valueOf(500L);
	private ERIBLocale locale;
	private String localeId;

	private static String getSuffix(String localeId)
	{
		if (StringHelper.isEmpty(localeId))
			return "";
		else
			return SEPARATOR+localeId;
	}

	@Override
	public Form getForm()
	{
		return createForm(localeId);
	}

	@SuppressWarnings({"OverlyLongMethod", "TooBroadScope", "ReuseOfLocalVariable"})
	private static Form createForm(String localeId)
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;
		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
		//invalidVersionText
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(API_INVALID_VERSION_TEXT + getSuffix(localeId));
		fieldBuilder.setType(StringType.INSTANCE.getName());
		String desc = "��������� � ��������������� ���������";
		fieldBuilder.setDescription(desc);
		LengthFieldValidator lengthValidator = new LengthFieldValidator(PROP_VAL_MAX_LEN);
		lengthValidator.setMessage(desc + " �� ������ ��������� " + PROP_VAL_MAX_LEN + " ��������");
		fieldBuilder.addValidators(requiredFieldValidator, lengthValidator);
		formBuilder.addField(fieldBuilder.build());

		//jailbreakEnabledText
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(JAILBREAK_ENABLED_TEXT + getSuffix(localeId));
		fieldBuilder.setType(StringType.INSTANCE.getName());
		desc = "�����, ������������ �������, ���� ��������� ������ � ��������� �� ���������� iOS";
		fieldBuilder.setDescription(desc);
		lengthValidator = new LengthFieldValidator(PROP_VAL_MAX_LEN);
		lengthValidator.setMessage(desc + " ������ ���� �� ����� " + PROP_VAL_MAX_LEN + " ��������");
		fieldBuilder.addValidators(requiredFieldValidator, lengthValidator);
		formBuilder.addField(fieldBuilder.build());

		//jailbreakDisabledText
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(JAILBREAK_DISABLED_TEXT + getSuffix(localeId));
		fieldBuilder.setType(StringType.INSTANCE.getName());
		desc = "�����, ������������ �������, ���� ��������� ������ � ��������� �� ���������� iOS";
		fieldBuilder.setDescription(desc);
		lengthValidator = new LengthFieldValidator(PROP_VAL_MAX_LEN);
		lengthValidator.setMessage(desc + " ������ ���� �� ����� " + PROP_VAL_MAX_LEN + " ��������");
		fieldBuilder.addValidators(requiredFieldValidator, lengthValidator);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	/**
	 * @return ������
	 */
	public ERIBLocale getLocale()
	{
		return locale;
	}

	/**
	 * ���������� ������
	 * @param locale ������
	 */
	public void setLocale(ERIBLocale locale)
	{
		this.locale = locale;
	}

	/**
	 * @return ������������� ������
	 */
	public String getLocaleId()
	{
		return localeId;
	}

	/**
	 * ���������� ������������� ������
	 * @param localeId ������������� ������
	 */
	public void setLocaleId(String localeId)
	{
		this.localeId = localeId;
	}
}
