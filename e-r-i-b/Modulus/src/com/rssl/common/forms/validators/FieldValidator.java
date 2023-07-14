package com.rssl.common.forms.validators;

import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.TemporalDocumentException;

import java.io.Serializable;

/**
 * �������� ������������ ���������� ���������� ����
 * @author Evgrafov
 * @ created 30.11.2005
 * @ $Author: erkin $
 * @ $Revision: 48660 $
 */

public interface FieldValidator extends MessageHolder, Serializable
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
     * @param name ��� ���������
     * @param value �������� ���������
     */
    void   setParameter(String name, String value);

    /**
     * �������� ���������
     * @param name ��� ���������
     * @return ��������
     */
    String getParameter(String name);

    /**
     * ��������� �������� ���� �� ����������
     * ���� ����� �� ���� ����������� ������ ���� THREAD SAFE!!!!!!!!!
     * @param value �������� ��� ��������
     * @throws TemporalDocumentException ���������, ���� �������� �������� �� ����� ���� ���������
     */
    boolean validate(String value) throws TemporalDocumentException;

	/**
	 * ���������� ���������� ���������
	 *
	 * @param enabledExpression
	 */
	void setEnabledExpression(Expression enabledExpression);

	/**
	 * @return ���������� ���������
	 */
	Expression getEnabledExpression();
}
