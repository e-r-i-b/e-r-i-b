package com.rssl.phizic.gate.ips;

import java.io.Serializable;

/**
 * @author Erkin
 * @ created 27.07.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��� ��������� ��������
 * ����������� ���� �� ��������� ���������� ��� ���������� ������� CardOperation
 */
public interface CardOperationType extends Serializable
{
	/**
	 * @return ������� ����� ����
	 */
	long getCode();

	/**
	 * @return ������� "�������� � ��������� / � ������������ ����������"
	 */
	boolean isCash();
}
