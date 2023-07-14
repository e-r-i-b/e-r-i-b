package com.rssl.phizic.utils.test;

/**
 * @author Omeliyanchuk
 * @ created 21.07.2008
 * @ $Author$
 * @ $Revision$
 */

public interface JUnitDatabaseConfig
{
	/**
	 * ������ ��� ������ ��������
	 * @return
	 */
    String getDriver();

	/**
	 * ������ ������ ����������� 
	 * @return
	 */
    String getURI();

	/**
	 * ��� ������������
	 * @return
	 */
    String getLogin();

	/**
	 * ������
	 * @return
	 */
    String getPassword();

	/**
	 * ��� ���������
	 * @return
	 */
	String getDataSourceName();
}
