package com.rssl.phizic.business.web.widget.strategy;

import com.rssl.phizic.business.web.WidgetDefinition;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author Dorzhinov
 * @ created 14.01.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Стратегия доступа к виджету
 * ВАЖНО! Стратегия отрабатывает в контексте текушего клиента
 */
public interface WidgetAccessor
{
	/**
	 * Определяет доступность виджетов указанной дефиниции
	 * @param widgetDefinition - виджет-дефиниция
	 * @return true - widgetDefinition доступен клиенту
	 */
	boolean access(WidgetDefinition widgetDefinition) throws BusinessException, BusinessLogicException;
}
