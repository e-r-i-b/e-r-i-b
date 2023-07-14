package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;

import java.util.Map;

/**
 * Валидатор логических полей.
 * Дает положительный ответ если два логических дают ответ true при действии логическим опрератором.
 *
 * @author bogdanov
 * @ created 22.04.2013
 * @ $Author$
 * @ $Revision$
 */

public class LogicOperatorValidator extends MultiFieldsValidatorBase
{
	public static final String TRUE_OPERATOR_TRUE = "true?true";
	public static final String TRUE_OPERATOR_FALSE = "true?false";
	public static final String FALSE_OPERATOR_TRUE = "false?true";
	public static final String FALSE_OPERATOR_FALSE = "false?false";

	public static final String FIELD_VALUE1 = "value1";
	public static final String FIELD_VALUE2 = "value2";

	private static final String[] OPERATOR_MAP = {FALSE_OPERATOR_FALSE, FALSE_OPERATOR_TRUE, TRUE_OPERATOR_FALSE, TRUE_OPERATOR_TRUE};

	public boolean validate(Map values) throws TemporalDocumentException
	{
		Boolean b1 = (Boolean)retrieveFieldValue(FIELD_VALUE1, values);
		Boolean b2 = (Boolean)retrieveFieldValue(FIELD_VALUE2, values);

		int v = (b1 ? 2 : 0) + (b2 ? 1 : 0);
		String param = getParameter(OPERATOR_MAP[v]);
		return Boolean.parseBoolean(param);
	}
}
