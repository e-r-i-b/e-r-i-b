package com.rssl.phizic.startup;

/**
 * @author Omeliyanchuk
 * @ created 12.05.2009
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��������� �������, ������� ������ ���� ������� ��� ������ ����������.
 */
public interface StartupService
{
	/**
	 * ����� ���������� ��� ������ �������
	 * @throws Exception
	 */
	void start() throws Exception;

	/**
	 * ����� ���������� ��� ��������� ����������
	 */
	void stop();

	/**
	 * ��������������� �� ������ � ������ ����� ����������.
	 * @return
	 */
	boolean isInitialized();
}
