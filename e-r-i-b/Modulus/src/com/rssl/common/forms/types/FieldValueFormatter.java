package com.rssl.common.forms.types;

import java.io.Serializable;

/**
 * @author Evgrafov
 * @ created 04.01.2007
 * @ $Author: khudyakov $
 * @ $Revision: 50095 $
 */

public interface FieldValueFormatter extends Serializable
{
	/**
	 * ����������� �������� ���� � ������ ��� �������
	 * @param value �������� ����
	 * @return ��������� ������������� ���� � ������� ��� �������
	 */
	String toSignableForm(String value);
}