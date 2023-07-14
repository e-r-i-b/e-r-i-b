package com.rssl.phizic.operations.widget;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.web.WidgetConfig;
import com.rssl.phizic.business.web.WidgetDefinition;
import com.rssl.phizic.business.web.WidgetDefinitionComparator;
import com.rssl.phizic.business.web.WidgetDefinitionService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.DbPropertyService;
import com.rssl.phizic.operations.OperationBase;

import java.util.*;

/**
 * User: moshenko
 * Date: 05.12.2012
 * Time: 11:05:17
 * Управление каталогом виджетов
 */
public class WidgetCatalogManagementOperation extends OperationBase
{
	private static final WidgetDefinitionService widgetDefinitionService = new WidgetDefinitionService();
	private Map<String, WidgetDefinition> WDMap = new HashMap<String, WidgetDefinition>();

	/**
	 * @return списко всех опредилений виджетов
	 * @throws BusinessException
	 */
	public List<WidgetDefinition> initialize() throws BusinessException
	{
		List<WidgetDefinition> WDList = widgetDefinitionService.getAll();
		for (WidgetDefinition wd : WDList)
		{
			WDMap.put(wd.getCodename(), wd);
		}

		Collections.sort(WDList,new WidgetDefinitionComparator());
		return WDList;
	}

	/**
	 * Сохранить WidgetDefinitions
	 * @param availabeWidget - доступые в системе виджеты
	 * @param sortWidget - порядок отображения
	 * @throws BusinessException
	 */
	public void save(String[] availabeWidget, String[] sortWidget) throws BusinessException
	{
		List availabeWidgetList = Arrays.asList(availabeWidget);
		WidgetDefinition curentWD = new WidgetDefinition();
		Long i = 0l;
		for (String ws : sortWidget)
		{
			i++;
			curentWD = WDMap.get(ws);
			curentWD.setIndex(i);

			if (availabeWidgetList.contains(ws))
				curentWD.setAvailability(true);
			else
				curentWD.setAvailability(false);
		}
		widgetDefinitionService.addOrUpdateList(new ArrayList<WidgetDefinition>(WDMap.values()));
	}

	/**
	 * Возвращает ID твиттера Сбербанка
	 * @return ID твиттера Сбербанка
	 * @throws BusinessException
	 */
	public String getTwitterId() throws BusinessException
	{
		return ConfigFactory.getConfig(WidgetConfig.class).getTwitterId();
	}

	/**
	 * Сохраняет ID твиттера Сбербанка
	 * @param twitterId - ID твиттера Сбербанка
	 * @throws BusinessException
	 */
	public void saveTwitterId(String twitterId) throws BusinessException
	{
		DbPropertyService.updateProperty(WidgetConfig.TWITTER_ID, twitterId);
	}
}
