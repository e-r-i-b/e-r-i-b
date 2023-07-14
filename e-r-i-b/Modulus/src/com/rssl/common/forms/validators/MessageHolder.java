package com.rssl.common.forms.validators;

/**
 * @author krenev
 * @ created 30.12.2010
 * @ $Author$
 * @ $Revision$
 * ��������� - �������� ���������.
 */
public interface MessageHolder
{
	/**
	 * @return �����-�� ���������
	 */
	String getMessage();

	/**
	* @return ���� ������-�� ���������
	*/
	String getMessageKey();
	/**
	 * ���������� ���������
	 * @param message ���������
	 */
	void setMessage(String message);
}
