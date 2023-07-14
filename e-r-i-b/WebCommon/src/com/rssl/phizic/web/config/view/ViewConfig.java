package com.rssl.phizic.web.config.view;

/**
 * @author Evgrafov
 * @ created 10.08.2007
 * @ $Author: Evgrafov $
 * @ $Revision: 4675 $
 */

public interface ViewConfig
{
	/**
	 * Получить TemplateManager для заданного view
	 * @param view id списка
	 * @return TemplateManager
	 */
	public TemplateManager getListTemplateManager(String view);
}
