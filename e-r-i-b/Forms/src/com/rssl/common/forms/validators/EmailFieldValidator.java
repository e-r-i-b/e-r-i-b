package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.utils.StringHelper;

import java.util.regex.Pattern;

/**
 * @author koptyaev
 * @ created 07.08.2013
 * @ $Author$
 * @ $Revision$
 */
public class EmailFieldValidator extends FieldValidatorBase
{
	private ThreadLocal<String> message = new ThreadLocal<String>();
	private static final String regexp = "^(([A-Za-z0-9])+(-|\\.|_)*)*([A-Za-z0-9])+(_)*@(([A-Za-z0-9])+(-|\\.))*([A-Za-z0-9])+$";
	private static final String regexp1 = "^.{0,35}$";
	private static final String regexp2 = "^\\S*$";
	private static final String regexp3_1 = "(.*@-.*)|(.*-@.*)";
	private static final String regexp3_2 = "(.*@\\..*)|(.*\\.@.*)";
	private static final String regexp3_3 = "(.*-\\..*)|(.*\\.-.*)";
	private static final String regexp3_5 = "(^-.*|.*-@.*)";
	private static final String regexp3_6 = "(^\\..*|.*\\.@.*)";

	private static final Pattern pattern = Pattern.compile(regexp);
	private static final Pattern pattern1 = Pattern.compile(regexp1);
	private static final Pattern pattern2 = Pattern.compile(regexp2);
	private static final Pattern pattern3_1 = Pattern.compile(regexp3_1);
	private static final Pattern pattern3_2 = Pattern.compile(regexp3_2);
	private static final Pattern pattern3_3 = Pattern.compile(regexp3_3);
	private static final Pattern pattern3_5 = Pattern.compile(regexp3_5);
	private static final Pattern pattern3_6 = Pattern.compile(regexp3_6);

	public String getMessageKey()
	{
		return message.get();
	}

	/**
	 * Установить ключ сообщения
	 * @param value ключ
	 */
	public void setMessageKey(String value)
	{
		message.set(value);
	}

	private boolean checkPattern(String value)
	{
		setMessageKey(null);

		if (!pattern1.matcher(value).matches())
		{
			setMessageKey("com.rssl.common.forms.validators.EmailFieldValidator.length");
			return false;
		}
		if (!pattern2.matcher(value).matches())
		{
			setMessageKey("com.rssl.common.forms.validators.EmailFieldValidator.spaces");
			return false;
		}
		if (pattern3_1.matcher(value).matches())
		{
			setMessageKey("com.rssl.common.forms.validators.EmailFieldValidator.atHyphen");
			return false;
		}
		if (pattern3_2.matcher(value).matches())
		{
			setMessageKey("com.rssl.common.forms.validators.EmailFieldValidator.atPoint");
			return false;
		}
		if (pattern3_3.matcher(value).matches())
		{
			setMessageKey("com.rssl.common.forms.validators.EmailFieldValidator.pointHyphen");
			return false;
		}
		if (pattern3_5.matcher(value).matches())
		{
			setMessageKey("com.rssl.common.forms.validators.EmailFieldValidator.startEndHyphen");
			return false;
		}
		if (pattern3_6.matcher(value).matches())
		{
			setMessageKey("com.rssl.common.forms.validators.EmailFieldValidator.startEndPoint");
			return false;
		}
		if (!pattern.matcher(value).matches())
		{
			setMessageKey("com.rssl.common.forms.validators.EmailFieldValidator.format");
			return false;
		}
		return true;
	}

	public boolean validate(String value) throws TemporalDocumentException
	{
		if (StringHelper.isEmpty(value))
			return true;
		return checkPattern(value);
	}
}
