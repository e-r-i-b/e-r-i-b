package com.rssl.phizic.gate.mobilebank;

/**
 * @author osminin
 * @ created 15.01.2013
 * @ $Author$
 * @ $Revision$
 *
 * ���������� � ���������
 */
public interface MessageInfo
{
	/**
	 * @return ����� ���������
	 */
	String getText();

	/**
	 * @return �����, ������������ � ���
	 */
	String getTextToLog();

	/**
	 * @return ����������� �����
	 */
	String getStubText();
}
