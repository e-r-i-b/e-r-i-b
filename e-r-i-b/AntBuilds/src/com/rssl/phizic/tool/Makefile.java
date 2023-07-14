package com.rssl.phizic.tool;

import com.rssl.phizic.common.types.annotation.Immutable;
import com.rssl.phizic.config.build.AppServerType;

import java.util.Collection;

/**
 * @author Erkin
 * @ created 10.03.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������� ������ �������
 */
@Immutable
interface Makefile
{
	/**
	 * @return ������ ����������, ��� �������� ���������� ������
	 */
	AppServerType getAppServerType();

	/**
	 * @return �������� (���) ����������, ������� ����� �������
	 */
	Collection<String> getApplications();
}
