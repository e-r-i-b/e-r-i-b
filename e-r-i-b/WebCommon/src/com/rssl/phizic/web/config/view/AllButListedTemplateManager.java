package com.rssl.phizic.web.config.view;

import java.util.Set;
import java.util.HashSet;
import java.util.Collection;

/**
 * ��������� ��� ����� ������
 * @author Evgrafov
 * @ created 27.07.2007
 * @ $Author: Evgrafov $
 * @ $Revision: 4675 $
 */

public class AllButListedTemplateManager implements TemplateManager
{
	private Set<String> restricted;

	/**
	 * @param restricted ��������� ��� ����� ����� ������
	 */
	public AllButListedTemplateManager(Collection<String> restricted)
	{
		this.restricted = new HashSet<String>(restricted);
	}

	public boolean isVisible(String id)
	{
		return !restricted.contains(id);
	}
}