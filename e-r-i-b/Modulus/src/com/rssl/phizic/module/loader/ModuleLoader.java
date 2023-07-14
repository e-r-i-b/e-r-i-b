package com.rssl.phizic.module.loader;

import com.rssl.phizic.common.types.annotation.ThreadSafe;

/**
 * @author Erkin
 * @ created 07.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��������� ������
 */
@ThreadSafe
public interface ModuleLoader
{
	/**
	 * ��������� ������
	 * �����! ����� �� ������ ����������� ����������
	 */
	public void start();

	/**
	 * ��������� ������
	 * �����! ����� �� ������ ����������� ����������
	 */
	public void stop();
}
