package com.rssl.phizic.utils;

import com.rssl.phizic.common.types.exceptions.SystemException;
import org.apache.commons.lang.ArrayUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author bogdanov
 * @ created 29.06.2012
 * @ $Author$
 * @ $Revision$
 *
 * Хелпер для обработки массивов.
 */

public class ArraysHelper
{
	/**
	 * Соединяет несколько массивов из байт в один.
	 *
	 * @param arrays массивы из байт.
	 * @return общий массив из байт.
	 */
	public static byte[] concat(byte[] ... arrays) throws SystemException
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		for (int i = 0; i < arrays.length; i++)
		{
			if (arrays[i] == null)
				continue;

			try
			{
				bos.write(arrays[i]);
			}
			catch (IOException e)
			{
				throw new SystemException(e);
			}
		}

		return bos.toByteArray();
	}

	/**
	 * Возвращает байт массив из строки.
	 *
	 * @param s строка.
	 * @return байт-массив.
	 */
	public static byte[] getBytes(String s)
	{
		if (StringHelper.isEmpty(s))
			return new byte[0];

		return s.getBytes();
	}

	/**
	 * Объеденить перечисление массивов
	 * @param arrays перечисление массивов
	 * @param <T>
	 * @return объединение массивов
	 */
	public static <T> T[] join(T[] ... arrays)
	{
		if (arrays == null)
		{
			return null;
		}

		T[] result = null;
		for (int i = 0; i < arrays.length; i++)
		{
			result = (T[]) ArrayUtils.addAll(arrays[i], result);
		}
		return result;
	}
}
