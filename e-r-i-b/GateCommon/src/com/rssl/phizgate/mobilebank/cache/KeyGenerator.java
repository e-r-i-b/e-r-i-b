package com.rssl.phizgate.mobilebank.cache;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * ��������� ������ ��� ����
 * @author Puzikov
 * @ created 28.08.13
 * @ $Author$
 * @ $Revision$
 */

public class KeyGenerator
{
	private static final char KEY_DELIMITER = '|';
	private static final String EMPTY_KEY = "empty_key";

	/**
	 * �������� ����� ���� �� ������ ������
	 * @param keys �����
	 * @return ����� ����
	 */
	public static final String getKey(String... keys)
	{
		if (ArrayUtils.isEmpty(keys))
			return EMPTY_KEY;

		return StringUtils.join(keys, KEY_DELIMITER);
	}
}
