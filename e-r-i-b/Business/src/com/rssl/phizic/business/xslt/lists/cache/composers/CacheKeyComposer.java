package com.rssl.phizic.business.xslt.lists.cache.composers;

/**
 * �������� ��� ������������ ����� � ���������� ��� �������
 * @author gladishev
 * @ created 09.08.2011
 * @ $Author$
 * @ $Revision$
 */

public interface CacheKeyComposer
{
	/**
	 * @param object - ������ �� �������� ��������� ����
	 * @return - ����
	 */
	String getKey(Object object);
}
