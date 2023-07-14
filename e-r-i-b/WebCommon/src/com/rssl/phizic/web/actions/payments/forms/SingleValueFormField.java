package com.rssl.phizic.web.actions.payments.forms;

/**
 * @author Erkin
 * @ created 27.11.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Поле из одной переменной
 * @param <T>
 */
public class SingleValueFormField<T> extends FormFieldBase
{
	private T value = null;

	///////////////////////////////////////////////////////////////////////////

	public FormFieldType getType()
	{
		return FormFieldType.SINGLE_VALUE;
	}

	public T getValue()
	{
		return value;
	}

	public void setValue(T value)
	{
		this.value = value;
	}
}
