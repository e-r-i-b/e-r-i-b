package com.rssl.phizic.business.dictionaries.basketident.validators;

import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.common.types.basket.fieldFormula.Constants;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author osminin
 * @ created 31.07.15
 * @ $Author$
 * @ $Revision$
 *
 * Базовый валидатор для проверки дубликатов в связках параметров ПУ и документов профиля
 */
public abstract class DuplicateFieldValidatorBase extends MultiFieldsValidatorBase
{
	private String[] fieldIdsArr;
	private String[] newFieldIdsArr;

	protected DuplicateFieldValidatorBase(String fieldsIds, String newFieldsIds)
	{
		fieldIdsArr = initializeArray(fieldsIds);
		newFieldIdsArr = initializeArray(newFieldsIds);
	}

	private String[] initializeArray(String fieldIds)
	{
		if (StringHelper.isEmpty(fieldIds))
		{
			return new String[0];
		}
		return fieldIds.split(Constants.ID_SPLITTER);
	}

	protected String[] getNewFieldIdsArr()
	{
		return newFieldIdsArr;
	}

	protected String[] getFieldIdsArr()
	{
		return fieldIdsArr;
	}
}
