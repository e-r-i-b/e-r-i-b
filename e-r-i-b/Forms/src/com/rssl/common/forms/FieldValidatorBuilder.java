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
	 * @param validatorClassName ��� ������ ����������
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public FieldValidatorBuilder(String validatorClassName) throws ClassNotFoundException, IllegalAccessException, InstantiationException
	{
		if ( validatorClassName == null || validatorClassName.equals("") )
			throw new IllegalArgumentException("�� ������ ��� ������ ����������");

		Class validatorClass = ClassHelper.loadClass(validatorClassName);
		this.fieldValidator  = (FieldValidator) validatorClass.newInstance();
	}

	/**
	 * ���������� ����� ������������� ����������
	 * ������ �������� ������������ ���������� ���������
	 * @param value ��������
	 */
	public void setMode(String value)
	{
	    fieldValidator.setMode(value);
	}

	/**
	 * @param name ��� ���������
	 * @param value �������� ���������
	 */
	public void setParameter(String name, String value)
	{
	    fieldValidator.setParameter(name, value);
	}

	/**
	 * @param value ����� ���������
	 */
	public void setMessage(String value)
	{
		fieldValidator.setMessage(value);
	}

	/**
	 * @return ���������
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
