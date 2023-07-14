package com.rssl.common.forms;

import com.rssl.phizic.utils.ClassHelper;
import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.validators.MultiFieldsValidator;

/**
 * @author Egorova
 * @ created 09.04.2008
 * @ $Author$
 * @ $Revision$
 */
public class FormValidatorBuilder
{
	private MultiFieldsValidator multiFieldsValidator;

	/**
	 * ctor
	 * @param validatorClassName имя класса валидатора
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public FormValidatorBuilder(String validatorClassName) throws ClassNotFoundException, IllegalAccessException, InstantiationException
	{
		if ( validatorClassName == null || validatorClassName.equals("") )
			throw new IllegalArgumentException("Не задано имя класса валидатора");

		Class validatorClass = ClassHelper.loadClass(validatorClassName);
		this.multiFieldsValidator  = (MultiFieldsValidator) validatorClass.newInstance();
	}

	/**
	 * Установить режим использования валидатора
	 * Данное значение используется стратегией валидации
	 * @param value значение
	 */
	public void setMode(String value)
	{
	    multiFieldsValidator.setMode(value);
	}

	/**
	 * @param name имя параметра
	 * @param value значение параметра
	 */
	public void setParameter(String name, String value)
	{
	    multiFieldsValidator.setParameter(name, value);
	}

	/**
	 * @param value текст сообщения
	 */
	public void setMessage(String value)
	{
		multiFieldsValidator.setMessage(value);
	}

	/**
	 * @return валидатор
	 */
	public MultiFieldsValidator build()
	{
		return multiFieldsValidator;
	}

	public void setEnabledExpression(Expression enabledExpression)
	{
		multiFieldsValidator.setEnabledExpression(enabledExpression);
	}

	public void setBinding(String validatorField, String formField)
	{
		multiFieldsValidator.setBinding(validatorField, formField);
	}

	public void setErrorField(String validatorField, String formField)
	{
		multiFieldsValidator.setErrorField(validatorField, formField);
	}
}
