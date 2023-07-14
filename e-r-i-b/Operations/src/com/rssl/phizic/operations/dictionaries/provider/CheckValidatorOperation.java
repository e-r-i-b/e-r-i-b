package com.rssl.phizic.operations.dictionaries.provider;

import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.TemporalDocumentException;

/**
 * @author akrenev
 * @ created 30.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class CheckValidatorOperation extends OperationBase
{
	private static final String VALID_EXPRESSION = "TRUE (valid)";

	private static final String INVALID_EXPRESSION = "FALSE (error)";

	@Override
	protected String getInstanceName()
	{
		return MultiBlockModeDictionaryHelper.getDBInstanceName();
	}

	/**
	 * Производит проверку валидатора.
	 *
	 * @param validatorExpression регулярное выражение.
	 * @param checkingValue проверяемое значение для заданного регулярного выражения.
	 * @return VALID_EXPRESSION, если валидатор сработал правильно или
	 *         INVALID_EXPRESSION, в остальных случаях.
	 */
	public String check(String validatorExpression, String checkingValue) throws TemporalDocumentException
	{
		RegexpFieldValidator regValidator = new RegexpFieldValidator(validatorExpression);
		return regValidator.validate(checkingValue) ? VALID_EXPRESSION : INVALID_EXPRESSION;
	}
}
