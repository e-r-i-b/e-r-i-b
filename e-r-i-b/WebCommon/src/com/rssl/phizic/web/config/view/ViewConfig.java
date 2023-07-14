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
	 * �������� TemplateManager ��� ��������� view
	 * @param view id ������
	 * @return TemplateManager
	 */
	public TemplateManager getListTemplateManager(String view);
}
