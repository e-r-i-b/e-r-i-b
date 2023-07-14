package com.rssl.common.forms.processing;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.validators.MultiFieldsValidator;

/**
 * @author krenev
 * @ created 16.09.2010
 * @ $Author$
 * @ $Revision$
 * ��������� ��������� ����� ��� ��������� ���� �����������
 */
public interface ValidationStrategy
{
	/**
	 * ���������� �� ������������ ��������� ��� ��������� �����
	 * @param field ����
	 * @return ��/���
	 */
	boolean accepted(Field field);

	/**
	 * ���������� �� ������������ ��������� ��� ��������� �����
	 * @param validator ���������
	 * @return ��/���
	 */
	boolean accepted(MultiFieldsValidator validator);

	/**
	 * ���������� �� ������������ ��������� ��� ��������� �����
	 * @param validator ���������
	 * @param validatedValue ��������� �������� ����
	 * @return ��/���
	 */
	boolean accepted(FieldValidator validator, String validatedValue);
}
