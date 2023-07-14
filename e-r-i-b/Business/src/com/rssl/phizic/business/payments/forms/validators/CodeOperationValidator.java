package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.utils.StringHelper;

import java.util.Map;

/**
 * @author niculichev
 * @ created 15.07.2010
 * @ $Author$
 * @ $Revision$
 */

/* Валидатор проверяет обязательность заполнения поля кода операции по счету списания и счету зачисления по
 алгоритму из BUG022870.

Если счет списания начинается с 426 или 40820, то счет зачисления не проверять, код валютной операции отображать.
Иначе проверяем счет зачисления. Если он начинается с 426, 40820 или 40807, то код валютной операции отображаем.
Иначе не отображаем.
*/
public class CodeOperationValidator extends MultiFieldsValidatorBase
{

	public static final String TO_ACCOUNT = "toAccount"; //счет зачисления
	public static final String FROM_ACCOUNT = "fromAccount"; //счет списания
	public static final String OPERATION_CODE = "operationCode"; //код операции
	public static final String EXTERNAL_CARD = "externalCard"; //код операции

	public boolean validate(Map values) throws TemporalDocumentException
	{
		String operationCode = (String) retrieveFieldValue(OPERATION_CODE, values);

		String fromAccount = (String) retrieveFieldValue(FROM_ACCOUNT, values);
		if (!StringHelper.isEmpty(fromAccount) && (fromAccount.startsWith("426") || fromAccount.startsWith("40820")))
		{
			if (StringHelper.isEmpty(operationCode))
				return false;
		}

		String binding = getBinding(EXTERNAL_CARD);
		String externalCard = null;
		if (StringHelper.isNotEmpty(binding))
			externalCard = (String) values.get(binding);

		if (!Boolean.valueOf(externalCard))
		{
			String toAccount = (String) retrieveFieldValue(TO_ACCOUNT, values);
			if (!StringHelper.isEmpty(toAccount) && (toAccount.startsWith("426") || toAccount.startsWith("40820") ||
					toAccount.startsWith("40807")))
			{
				if (StringHelper.isEmpty(operationCode))
					return false;
			}
		}

		return true;
	}
}
