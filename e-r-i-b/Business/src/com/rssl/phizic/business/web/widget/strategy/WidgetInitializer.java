package com.rssl.phizic.business.web.widget.strategy;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.web.Widget;
import com.rssl.phizic.common.types.annotation.Stateless;

/**
 * @author Erkin
 * @ created 22.01.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Стратегия инициализации виджета
 * ВАЖНО! Стратегия отрабатывает в контексте текушего клиента 
 */
@Stateless
public interface WidgetInitializer<TWidget extends Widget>
{
	/**
	 * Инициализировать виджет
	 * @param widget - виджет, который нужно настроить
	 */
	void init(TWidget widget) throws BusinessException, BusinessLogicException;
}
