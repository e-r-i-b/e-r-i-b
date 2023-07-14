package com.rssl.phizic.module;

import com.rssl.phizic.common.types.annotation.ThreadSafe;

/**
 * @author Erkin
 * @ created 07.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * �������� �������
 * �����, ��� � ��� ������� ������
 */
@ThreadSafe
public interface ModuleManager
{
	/**
	 * ����������/������ ������ �� ��� ������
	 * @param moduleClass - ����� ������
	 * @return ������
	 */
	<T extends Module> T getModule(Class<T> moduleClass);
}
