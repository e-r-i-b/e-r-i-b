package com.rssl.phizic.web.client.header;

import com.rssl.phizic.business.web.PersonWidgetDefinition;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.List;

/**
 * @author Dorzhinov
 * @ created 27.06.2012
 * @ $Author$
 * @ $Revision$
 */
public class WidgetCatalogForm extends ActionFormBase
{
	//out
	private List<PersonWidgetDefinition> personWidgetDefinitions;
	//in
	private String codename;
	private String pageName;
	private boolean haveMainContainer;//есть ли на странице центральный контейнер

	public List<PersonWidgetDefinition> getPersonWidgetDefinitions()
	{
		return personWidgetDefinitions;
	}

	public void setPersonWidgetDefinitions(List<PersonWidgetDefinition> personWidgetDefinitions)
	{
		this.personWidgetDefinitions = personWidgetDefinitions;
	}

	public String getCodename()
	{
		return codename;
	}

	public void setCodename(String codename)
	{
		this.codename = codename;
	}

	public String getPageName()
	{
		return pageName;
	}

	public void setPageName(String pageName)
	{
		this.pageName = pageName;
	}

	public boolean getHaveMainContainer()
	{
		return haveMainContainer;
	}

	public void setHaveMainContainer(boolean haveMainContainer)
	{
		this.haveMainContainer = haveMainContainer;
	}
}
