package com.rssl.common.forms.validators;


import java.util.ArrayList;
import java.util.List;

/**
 * @author tisov
 * @ created 24.07.15
 * @ $Author$
 * @ $Revision$
 * Композитный валидатор, инкапсулирующий логические операции над валидирующими функциями формы
 */
public abstract class CompositeLogicValidatorBase extends MultiFieldsValidatorBase
{
	protected List<MultiFieldsValidatorBase> validatorsChain = new ArrayList<MultiFieldsValidatorBase>();

	/**
	 * Добавление валидатора
	 * @param validator
	 */
	public void addValidator(MultiFieldsValidatorBase validator)
	{
		this.validatorsChain.add(validator);
	}
}
