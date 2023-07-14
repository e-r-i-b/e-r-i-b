package com.rssl.phizic.web.config.view;

/**
 * TemplateManager поумолчанию
 * @author Evgrafov
 * @ created 27.07.2007
 * @ $Author: Evgrafov $
 * @ $Revision: 4675 $
 */
public class DefaultTemplateManager implements TemplateManager
{
	/**
	 * @return всегда true
	 */
	public boolean isVisible(String id)
	{
		return true;
	}
}