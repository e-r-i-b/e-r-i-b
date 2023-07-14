package com.rssl.common.forms.expressions;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author akrenev
 * @ created 30.08.13
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ����������� ���� �� ���������� ��������
 */

public class FieldRestrictedExpression implements Expression
{
	private String fieldName;
	private List enabledTypes;

	/**
	 * �����������
	 * @param fieldName ��� ����
	 * @param values ���������� ��������
	 */
	public FieldRestrictedExpression(String fieldName, Object... values)
	{
		this.fieldName = fieldName;
		this.enabledTypes = Arrays.asList(values);
	}

	public Object evaluate(Map<String, Object> form)
	{
		return enabledTypes.contains(form.get(fieldName));
	}
}