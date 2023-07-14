package com.rssl.phizicgate.manager.services;

import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.StringUtils;

import java.util.regex.Pattern;

/**
 * @author Krenev
 * @ created 30.04.2009
 * @ $Author$
 * @ $Revision$
 */
public class IDHelper
{
	//формат идентификатора: <оригинальный_id><DELIMITER><UUID_адаптера>
	public static final String DELIMITER = "|";
	public static final String ESB_DELIMITER = "^";
	//дополнительный разделитель <info><ADDITIONAL_DELIMITER><оригинальный_id>
	public static final String ADDITIONAL_DELIMITER = "@";

	/**
	 * Сохранение в идентификаторе информации о маршрутизации
	 * @param id - идентификатор
	 * @param info - информация о маршрутизации
	 * @return id|info
	 */
	public static String storeRouteInfo(String id, String info)
	{
		return id + DELIMITER + info;
	}

	/**
	 * Получение из идентификатора информации о маршрутизации
	 * @param id - идентификатор
	 * @return - info
	 */
	public static String restoreRouteInfo(String id)
	{
		if (id == null)
		{
			return null;
		}
		int i = id.lastIndexOf(DELIMITER);
		if (i < 0)
		{
			return null;
		}
		return id.substring(i + 1, id.length());
	}

	/**
	 * Удаление из идентификатора информации о маршрутизации
	 * @param id - идентификатор
	 * @return - info
	 */
	public static String restoreOriginalId(String id)
	{
		if (StringHelper.isEmpty(id))
		{
			return null;
		}

		int i = id.lastIndexOf(DELIMITER);
		if (i < 0)
		{
			int j = id.indexOf(ESB_DELIMITER);
			if (j<0)
			{
				return id;
			}
			return id.substring(0, j);
		}
		return id.substring(0, i);
	}

	/**
	 * Удаление из идентификаторов информации о маршрутизации
	 * @param id - идентификатор
	 * @return - info
	 */
	public static String[] restoreOriginalId(String... id)
	{
		String[] res = new String[id.length];
		for (int i=0;i<id.length;i++)
		{
			 res[i]=restoreOriginalId(id[i]);
		}
		return res;
	}


	/**
	 * Сохранение в идентификаторе дополнительной информации
	 * @param id - идентификатор
	 * @param info - дополнительная информация
	 * @return info|id
	 */
	public static String storeAdditionalInfo(String id, String info)
	{
		return info + ADDITIONAL_DELIMITER + id;
	}

	/**
	 * Получение из идентификатора дополнительной информации
	 * @param id - идентификатор
	 * @return - info
	 */
	public static String restoreAdditionalInfo(String id)
	{
		if (StringHelper.isEmpty(id))
		{
			return null;
		}
		int i = id.indexOf(ADDITIONAL_DELIMITER);
		if (i < 0)
		{
			return null;
		}
		return id.substring(0, i);
	}

	/**
	 * Удаление из идентификатора дополнительной информации
	 * @param id - идентификатор
	 * @return - info
	 */
	public static String restoreOriginalIdWithAdditionalInfo(String id)
	{
		if (id == null)
		{
			return null;
		}
		int i = id.lastIndexOf(ADDITIONAL_DELIMITER);
		if (i < 0)
		{
			return id;
		}
		return id.substring(i + 1, id.length());
	}

	/**
	 * Соответсвует ли переданный идентификатор виду <id>|<routeInfo>
	 * @param id - внешний идентификатор сущности
	 * @return true - идентификатор соответсвует виду <id>|<routeInfo>
	 * */
	public static boolean matchRoutableId(String id)
	{
		Pattern pattern = Pattern.compile("[0-9]+\\|[^\\^]+$");
		return pattern.matcher(id).matches();
	}

	/**
	 * Соответствует ли идентификатор офиса ритейловскому (v6)
	 * @param id - внешний идентификатор офиса
	 * @return true - идентификатор соответствует
	 */
	public static boolean isRetailOfficeId(String id)
	{
		return StringUtils.countMatches(id, DELIMITER) == 1;
	}

}
