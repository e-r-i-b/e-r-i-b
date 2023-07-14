package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author tisov
 * @ created 18.12.14
 * @ $Author$
 * @ $Revision$
 * ¬алидаци€ списка номеров телефонов по формату, используемому в сообщени€х с мјѕ»
 */
public class MAPIPhoneNumberListValidator extends MAPIPhoneNumberValidator
{
	private char separator;

	public MAPIPhoneNumberListValidator(String fieldName, char separator)
	{
		setMessage("¬ведите корректное значение в поле "+ fieldName+ ", каждый из номеров должен начинатьс€ с семЄрки и состо€ть из 11 цифр");
		this.separator = separator;
	}

	public boolean validate(String value) throws TemporalDocumentException
	{
		List<String> phonesList = Arrays.asList(StringUtils.split(value, separator));
		for (String phone:phonesList)
		{
			if (!super.validate(phone))
			{
				return false;
			}
		}
		return true;
	}
}
