package com.rssl.phizic.web.actions.payments.forms;

import java.io.Serializable;

/**
 * @author Erkin
 * @ created 27.11.2010
 * @ $Author$
 * @ $Revision$
 */
public interface FormField extends Serializable
{
	/**
	 * ���������� ��� ����,
	 * ����, �� �������� ����� ���������� � ���� � Jsp � �.�.
	 * @return ��� ����
	 */
	String getName();

	/**
	 * ���������� ��� ����
	 * @return ��� ����
	 */
	FormFieldType getType();

	/**
	 * ���������� �������� ���� ��� ����������� ������������
	 * @return ��� ���� ��� ������������
	 */
	String getUserName();

	void setUserName(String userName);

	/**
	 * ���������� ������ ����
	 * ������ ������������ ��� ����������� ����� �� ������-�� ��������
	 * @return
	 */
	String getMarker();

	void setMarker(String marker);
}
