package com.rssl.phizic.engine;

import com.rssl.phizic.module.loader.LoadOrder;

/**
 * @author Erkin
 * @ created 10.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * �������� (������)
 */
public interface Engine
{
	/**
	 * @return �������� ���������
	 */
	String getName();

	/**
	 * @return ���������� ����� � ������� ��������
	 */
	LoadOrder getLoadOrder();

	/**
	 * ��������� ��������
	 */
	void start();

	/**
	 * ���������� ��������
	 */
	void stop();
}
