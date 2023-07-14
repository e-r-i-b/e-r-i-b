package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.expressions.Expression;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * @author Evgrafov
 * @ created 05.12.2005
 * @ $Author: rtishcheva $
 * @ $Revision: 73973 $
 */

public interface MultiFieldsValidator extends MessageHolder, Serializable
{
	/**
	 * @return ����� ������������� ����������
	 */
    String getMode();

	/**
	 * ���������� ����� ������������� ����������
	 * ������ �������� ������������ ���������� ���������
	 * @param value ��������
	 */
	void setMode(String value);

    /**
     * ���������� �������� ���������
     *
     * @param name  ��� ���������
     * @param value �������� ���������
     */
    void setParameter(String name, String value);

    /**
     * �������� ���������
     *
     * @param name ��� ���������
     * @return ��������
     */
    String getParameter(String name);

	/**
     * �������� ����� ���������� ���������
     * @return ������ �� ��� ����� �����
     */
	String[] getParametersNames();

    /**
     * ���������� ������������ ����� ������ ����������� ���������� � ����� �����
     * @param validatorField - ���� ����������
     * @param formField - ���� �����
     */
    void setBinding(String validatorField, String formField);

    /**
     * �������� ���� ����� ����������� � ���� ����������
     * @param validatorField - ���� ����������
     * @return ���� �����
     */
    String getBinding(String validatorField);

	/**
     * �������� ����� ����� �����, ����������� � ����� ����������
     * @return ������ �� ��� ����� �����
     */
	String[] getBindingsNames();
    /**
     * ��������� �������� ����� �� ���������� ���� ����� �� ���� ����������� ������ ���� THREAD SAFE!!!!!!!!!
     *
     * @param values �������� ��� ��������. Key - ��� ���� (� �����), Value - �������� ����.
     * @throws TemporalDocumentException ���������, ���� �������� �������� �� ����� ���� ���������
     */
    boolean validate(Map values) throws TemporalDocumentException;

	/**
	 * ���������� ���������� ���������
	 *
	 * @param expression
	 */
	void setEnabledExpression(Expression expression);

	Expression getEnabledExpression();

	/**
	 * ���������� ��������� ����
	 * @param name
	 * @param value
	 */
	void setErrorField(String name, String value);

	/**
	 * ���������� �������� ��������� ����� (������������ ��� ��������� ���� �����)
	 * @return
	 */
	Collection<String> getErrorFieldNames();
}
