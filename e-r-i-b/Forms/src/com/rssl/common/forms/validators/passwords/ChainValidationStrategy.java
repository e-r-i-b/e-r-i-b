package com.rssl.common.forms.validators.passwords;

import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.common.types.transmiters.Pair;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Roshka
 * @ created 01.02.2007
 * @ $Author$
 * @ $Revision$
 */

public class ChainValidationStrategy implements ValidationStrategy
{
	private List<FieldValidator> validators;

	/**
	 * конструктор
	 * @param validators - валидаторы
	 */
	public ChainValidationStrategy(FieldValidator ... validators)
	{
		this(Arrays.asList(validators));
	}

	/**
	 * конструктор
	 * @param validators - валидаторы
	 */
	public ChainValidationStrategy(List<FieldValidator> validators)
	{
		this.validators = new ArrayList<FieldValidator>();
		this.validators.addAll(validators);
	}

	public Pair<Boolean, String> validate(String value) throws TemporalDocumentException
	{
		for (FieldValidator validator : validators)
		{
			if (!validator.validate(value))
			{
				return new Pair<Boolean, String>(false, validator.getMessage());
			}
		}
		return new Pair<Boolean, String>(true, null);
	}
}