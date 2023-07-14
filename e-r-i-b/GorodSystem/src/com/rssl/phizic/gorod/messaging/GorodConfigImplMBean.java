package com.rssl.phizic.gorod.messaging;

/**
 * @author Omeliyanchuk
 * @ created 16.03.2011
 * @ $Author$
 * @ $Revision$
 */

public interface GorodConfigImplMBean
{
	/**
	 * ������������ �� ��������
	 * @return true - ��.
	 */
	boolean IsMock();

	/**
	 *
	 * @return ���� ������� �����
	 */
	String getHost();

	/**
	 *
	 * @return ���� ������� �����
	 */
	int getPort();

	/**
	 *
	 * @return ��� �����. ������������
	 */
	String getPAN();

	/**
	 *
	 * @return ��� �����. ������������.
	 */
	String getPIN();

	/**
	 *
	 * @return
	 */
	String getPrefix();


	/**
	 *
	 * @return
	 */
	String getPostFix();

	/**
	 *
	 * @return ��� ���������� �����
	 */
	String getClientPan();

}
