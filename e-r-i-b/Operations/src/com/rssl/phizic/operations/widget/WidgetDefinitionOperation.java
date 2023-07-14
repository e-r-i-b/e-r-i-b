package com.rssl.phizic.operations.widget;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.web.*;
import com.rssl.phizic.business.web.widget.strategy.WidgetAccessor;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonContext;

import java.util.*;

/**
 * User: moshenko
 * Date: 04.12.2012
 * Time: 12:39:31
 * Операция для получения описания виджетов  
 */
public class WidgetDefinitionOperation extends OperationBase
{
	private static final WidgetService widgetService = new WidgetService();

	/**
	 * @return все доступные описания виджетов
	 * @throws BusinessException
	 */
	private List<WidgetDefinition> getDefinitions() throws BusinessException, BusinessLogicException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		Map<String, WidgetDefinition> map = personData.getWidgetDefinitions();
		List<WidgetDefinition> allAvailabe =  new LinkedList<WidgetDefinition>(map.values());

		for (Iterator<WidgetDefinition> iterator = allAvailabe.iterator(); iterator.hasNext(); )
		{
			WidgetDefinition widgetDefinition = iterator.next();

			WidgetAccessor accessStrategy = widgetDefinition.getAccessor();

			//проверяем доступность widgetDefinition-а
			if (!accessStrategy.access(widgetDefinition))
				iterator.remove();
		}
		Collections.sort(allAvailabe,new WidgetDefinitionComparator());
		return allAvailabe;
	}

	/**
	 * Получение списка описания виджетов с признаком новинки
	 * @param lastLogonDate - дата последнего входа в систему
	 * @return - список описаний виджетов с признаком новинки
	 * @throws BusinessException
	 */
	public List<PersonWidgetDefinition> getPersonWidgetDefinition(Calendar lastLogonDate, boolean haveMainContainer) throws BusinessException, BusinessLogicException
	{
		List<WidgetDefinition> definitions = getDefinitions();
		if (definitions.isEmpty())
			return Collections.emptyList();
		List<PersonWidgetDefinition> result = new LinkedList<PersonWidgetDefinition>();
		for (WidgetDefinition definition : definitions)
		{
			//если на странице нет центрального контейнера, добавляем только виджеты которые могут иметь компактный режим
			if (haveMainContainer || definition.isSizeable() || (!definition.isSizeable() && definition.getInitialSize().equals("compact")))
			{
				boolean novelty = lastLogonDate == null || (lastLogonDate != null && lastLogonDate.before(definition.getAddingDate()));
				result.add(new PersonWidgetDefinition(definition, widgetService.getDefinitionAsJson(definition), novelty));
			}
		}
		return result;
	}
}
