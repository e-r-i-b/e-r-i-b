package com.rssl.phizic.gate.messaging;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Novikov_A
 * @ created 22.09.2006
 * @ $Author$
 * @ $Revision$
 */
public interface GateMessage
{
	/**
	 * �������� ��������� (������ ��� � xml ���������)
	 * @return �������� ����
	 */
	String getMessageName();

	/**
	 * ��������(�������� ������������) ��� � ���������
	 * @param name - �������� ����
	 * @param value - �������� ����
	 * @return - ���
	 */
	Element addParameter(String name, Object value);

	/**
	 * �������� ��� � ���������
	 * @param name - �������� ����
	 * @param value - �������� ����
	 * @return ������� � ������� ���������� ��������
	 */
	Element appendParameter(String name, Object value);

	/**
	 * xml �������� ���������
	 * @return - xml ��������
	 */
	Document getDocument ();
}
