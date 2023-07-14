package com.rssl.phizic.business.payments;

import com.rssl.phizic.business.xslt.lists.EntitiesListSourcesFactory;
import com.rssl.phizic.business.xslt.lists.EntityListSource;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;

import java.util.HashMap;
import java.util.Map;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;

/**
 * @author Evgrafov
 * @ created 03.11.2005
 * @ $Author: niculichev $
 * @ $Revision: 30464 $
 */
public class ListsURIResolver implements URIResolver
{
	private static Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	public Source resolve(String href, String base) throws TransformerException
	{
		try
		{
			String entityListName = getEntityListName(href);

			EntityListSource entityListSource = EntitiesListSourcesFactory.getInstance().getEntityListSource(entityListName);
			if (entityListSource==null)
                return null; // пусть кто то другой разрешает эту ссылку
			return entityListSource.getSource(getEntityListParams(href));
		}
		catch (InactiveExternalSystemException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			//трасса теряется в xslt процессоре. выводим сами
			log.error("ошибка при формировании динамического справочника "+href, e);
			throw new TransformerException(e);
		}
	}

	/**
	 * Получение имени справочника по урлу
	 * @param href урл
	 * @return имя справочника
	 */
	public static String getEntityListName(String href)
	{
		return href.split("\\?")[0];
	}

	/**
	 * Получение параметров справочника по урлу
	 * @param href урл
	 * @return мап параметров
	 */
	public static Map<String, String> getEntityListParams(String href)
	{
		Map<String, String> paramsMap = new HashMap<String, String>();
		String[] array = href.split("\\?");
		if (array.length > 1)
		{
			String[] params = array[1].split("&");
			for (String param1 : params)
			{
				String[] param = param1.split("=");
				if (param.length == 2)
				{
					paramsMap.put(param[0], param[1]);
				}
			}
		}
		return paramsMap;
	}
}
