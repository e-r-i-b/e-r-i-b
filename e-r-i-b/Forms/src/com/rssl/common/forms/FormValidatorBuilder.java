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
	 * @param validatorClassName ��� ������ ����������
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public FormValidatorBuilder(String validatorClassName) throws ClassNotFoundException, IllegalAccessException, InstantiationException
	{
		if ( validatorClassName == null || validatorClassName.equals("") )
			throw new IllegalArgumentException("�� ������ ��� ������ ����������");

		Class validatorClass = ClassHelper.loadClass(validatorClassName);
		this.multiFieldsValidator  = (MultiFieldsValidator) validatorClass.newInstance();
	}

	/**
	 * ���������� ����� ������������� ����������
	 * ������ �������� ������������ ���������� ���������
	 * @param value ��������
	 */
	public void setMode(String value)
	{
	    multiFieldsValidator.setMode(value);
	}

	/**
	 * @param name ��� ���������
	 * @param value �������� ���������
	 */
	public void setParameter(String name, String value)
	{
	    multiFieldsValidator.setParameter(name, value);
	}

	/**
	 * @param value ����� ���������
	 */
	public void setMessage(String value)
	{
		multiFieldsValidator.setMessage(value);
	}

	/**
	 * @return ���������
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
