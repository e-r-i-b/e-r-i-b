package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;

import java.util.Map;

/**
 * @author tisov
 * @ created 24.07.15
 * @ $Author$
 * @ $Revision$
 * Валидатор, инкапсулирующий дизьюнкцию валидирующих функций формы
 */
public class DisjunctionLogicValidator extends CompositeLogicValidatorBase
{

	public boolean validate(Map values) throws TemporalDocumentException
	{
		for (MultiFieldsValidatorBase validator : this.validatorsChain)
		{
			if (validator.validate(values))
			{
				return true;
			}
		}

		return false;
	}
}
