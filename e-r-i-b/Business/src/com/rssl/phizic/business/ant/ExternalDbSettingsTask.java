package com.rssl.phizic.business.ant;

/**
 * @author Omeliyanchuk
 * @ created 22.07.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���� ����� ���������� ������ ��������� ����������� � �� �� �������
 */
public interface ExternalDbSettingsTask
{
	/**
	 * ���������������� ����������� �� �������
	 * @return
	 */
	String getInitByParams();

	void setInitByParams(String initByParams);

	/**
	 * ����� ��� ������������� � ��
	 * @return
	 */
	String getLogin();

	void setLogin(String login);

	/**
	 * ������ ��� ������������� � ��
	 * @return
	 */
	String getPassword();

	void setPassword(String password);

}
