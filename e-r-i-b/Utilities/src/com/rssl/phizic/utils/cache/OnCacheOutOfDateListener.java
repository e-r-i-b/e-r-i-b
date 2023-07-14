package com.rssl.phizic.utils.cache;

/**
 * ������, �������� ������, ������� ���������� ������������.
 *
 * @author bogdanov
 * @ created 18.09.14
 * @ $Author$
 * @ $Revision$
 */

public interface OnCacheOutOfDateListener<KeyType, ReturnType>
{
	/**
	 * @return �������� ��� �����������.
	 */
	ReturnType onRefresh(KeyType key);
}
