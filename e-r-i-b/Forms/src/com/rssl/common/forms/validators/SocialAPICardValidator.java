package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.mobile.MobileApiConfig;
import com.rssl.phizic.utils.StringHelper;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * ��������� ��� �������� ������� ���� "card" � socialApi
 * @author sergunin
 * @ created 03.09.14
 * @ $Author$
 * @ $Revision$
 */
public class SocialAPICardValidator extends MultiFieldsValidatorBase
{
	public static final String CARD_FIELD = "card";
	public static final Pattern PATTERN = Pattern.compile("\\d{4}");
	private static final String MESSAGE = "������� 4 ��������� ����� �����";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		if (!ConfigFactory.getConfig(MobileApiConfig.class).isSocialApiRegistrationCheckCardNum())
			return true;
		String card = (String) retrieveFieldValue(CARD_FIELD, values);

		if (StringHelper.isEmpty(card) || !PATTERN.matcher(card).matches())
		{
			setMessage(MESSAGE);
			return false;
		}

		return true;
	}
}
