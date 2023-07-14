package com.rssl.phizic.operations.widget;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.web.TwitterWidget;
import com.rssl.phizic.business.web.WidgetConfig;
import com.rssl.phizic.config.ConfigFactory;

/**
 @author: Egorovaa
 @ created: 01.08.2012
 @ $Author$
 @ $Revision$
 */
public class TwitterWidgetOperation extends WidgetOperation<TwitterWidget>
{

	/**
	 * Взвращает id твиттера для отображения виджета
	 * @return id твиттера
	 * @throws BusinessException
	 */
	public String getTwitterId() throws BusinessException
	{
		return ConfigFactory.getConfig(WidgetConfig.class).getTwitterId();
	}
}
