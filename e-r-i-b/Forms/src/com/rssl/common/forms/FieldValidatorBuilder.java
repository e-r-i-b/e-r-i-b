package com.rssl.common.forms;

import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.expressions.Expression;
import com.rssl.phizic.utils.ClassHelper;

/**
 * @author Kidyaev
 * @ created 29.12.2006
 * @ $Author$
 * @ $Revision$
 */
public class FieldValidatorBuilder
{
	private FieldValidator fieldValidator;

	/**
	 * ctor
	 * @param validatorClassName имя класса валидатора
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public FieldValidatorBuilder(String validatorClassName) throws ClassNotFoundException, IllegalAccessException, InstantiationException
	{
		if ( validatorClassName == null || validatorClassName.equals("") )
			throw new IllegalArgumentException("Не задано имя класса валидатора");

		Class validatorClass = ClassHelper.loadClass(validatorClassName);
		this.fieldValidator  = (FieldValidator) validatorClass.newInstance();
	}

	/**
	 * Установить режим использования валидатора
	 * Данное значение используется стратегией валидации
	 * @param value значение
	 */
	public void setMode(String value)
	{
	    fieldValidator.setMode(value);
	}

	/**
	 * @param name имя параметра
	 * @param value значение параметра
	 */
	public void setParameter(String name, String value)
	{
	    fieldValidator.setParameter(name, value);
	}

	/**
	 * @param value текст сообщения
	 */
	public void setMessage(String value)
	{
		fieldValidator.setMessage(value);
	}

	/**
	 * @return валидатор
	 */
	public FieldValidator build()
	{
		return fieldValidator;
	}

	public void setEnabledExpression(Expression enabledExpression)
	{
		fieldValidator.setEnabledExpression(enabledExpression);
	}
}
