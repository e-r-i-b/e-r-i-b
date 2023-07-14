package com.rssl.phizic.web.config.view;

/**
 * @author Evgrafov
 * @ created 27.07.2007
 * @ $Author: Evgrafov $
 * @ $Revision: 4675 $
 */

public interface TemplateManager
{
	/**
	 * @param id id collectionItem
	 * @return true == collectionItem is visible
	 */
	boolean isVisible(String id);
}
