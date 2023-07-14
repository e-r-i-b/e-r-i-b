package com.rssl.phizic.web.client.component;

import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.favouritelinks.FavouriteLink;
import com.rssl.phizic.web.component.WidgetForm;

import java.util.List;

/**Форма виджета "Личное меню"
 * @ author Rtischeva
 * @ created 09.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class PersonalMenuWidgetForm extends WidgetForm
{
	private List<TemplateDocument> allTemplates;

	private List<TemplateDocument> showTemplates;

	private List<FavouriteLink> allFavouriteLinks;

	private List<FavouriteLink> showFavouriteLinks;

	public List<TemplateDocument> getAllTemplates()
	{
		return allTemplates;
	}

	public void setAllTemplates(List<TemplateDocument> allTemplates)
	{
		this.allTemplates = allTemplates;
	}

	public List<TemplateDocument> getShowTemplates()
	{
		return showTemplates;
	}

	public void setShowTemplates(List<TemplateDocument> showTemplates)
	{
		this.showTemplates = showTemplates;
	}

	public List<FavouriteLink> getAllFavouriteLinks()
	{
		return allFavouriteLinks;
	}

	public void setAllFavouriteLinks(List<FavouriteLink> allFavouriteLinks)
	{
		this.allFavouriteLinks = allFavouriteLinks;
	}

	public List<FavouriteLink> getShowFavouriteLinks()
	{
		return showFavouriteLinks;
	}

	public void setShowFavouriteLinks(List<FavouriteLink> showFavouriteLinks)
	{
		this.showFavouriteLinks = showFavouriteLinks;
	}

}
