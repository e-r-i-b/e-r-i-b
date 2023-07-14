package com.rssl.phizic.business.xslt.lists.cache.composers;

import com.rssl.phizic.business.BusinessException;

import java.util.List;

/**
 * �������� ��� ��������� ������ ���������� ������ �� �������.
 *
 * �������� �������� ����� ���� ��������� � ���� �������������
 * ����� ������� ����� getSessionKeys ������ ��������� �������������
 * � ������� ��� ��������� � �� �� ��������������� ������������ �����
 * @author gladishev
 * @ created 19.08.2011
 * @ $Author$
 * @ $Revision$
 */

public interface SessionCacheKeyComposer extends CacheKeyComposer
{
	/**
	 * @param object ������ �� �������� ����� �������� �����
	 * @return ������ ���������� ������
	 */
	List<String> getSessionKeys(Object object) throws BusinessException;
}
