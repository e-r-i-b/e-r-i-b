package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.utils.StringHelper;

import java.util.Map;

/**
 * ¬алидатор, провер€ющий совпедаение string-значений двух полей.
 * ¬ зависимоси от параметра isCaseSensitive с учетом и без учета регистра соответственно.
 * 
 * @ author: Vagin
 * @ created: 20.03.2013
 * @ $Author
 * @ $Revision
 */
public class CompareStringValidator extends MultiFieldsValidatorBase
{
	public static final String FIELD_S1  = "str1";
	public static final String FIELD_S2  = "str2";
	private boolean isCaseSensitive;
	private boolean useInvert;

	/**
	 * @param useInvert - параметр инвертирующий результат проверки на эквивалентность.
	 * @param isCaseSensitive - учитывать ли регистр при сравнении строк.
	 */
	public CompareStringValidator(boolean useInvert, boolean isCaseSensitive)
	{
		this.isCaseSensitive = isCaseSensitive;
		this.useInvert = useInvert;
	}

	public boolean validate(Map values) throws TemporalDocumentException
	{
		String string1 = (String) retrieveFieldValue(FIELD_S1, values);
		String string2 = (String) retrieveFieldValue(FIELD_S2, values);

		if (StringHelper.isEmpty(string1) && StringHelper.isEmpty(string2))
			return !useInvert;
		if (StringHelper.isNotEmpty(string1))
		{
			boolean isEqals = isCaseSensitive ? string1.equals(string2) : string1.equalsIgnoreCase(string2);
			return useInvert ? !isEqals : isEqals;
		}
		return useInvert;
	}
}
