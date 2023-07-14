package com.rssl.phizic.operations.widget;

import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.business.favouritelinks.FavouriteLink;
import com.rssl.phizic.business.favouritelinks.FavouriteLinkManager;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.web.PersonalMenuWidget;
import com.rssl.phizic.business.web.WidgetObjectVisibility;
import org.apache.commons.lang.ArrayUtils;

import java.util.*;

/**
 * операци€ виджета "Ћичное меню"
 * @author gulov
 * @ created 16.07.2012
 * @ $Authors$
 * @ $Revision$
 */
public class PersonalMenuWidgetOperation extends WidgetOperation<PersonalMenuWidget>
{
	private static final FavouriteLinkManager favouriteLinkManager = new FavouriteLinkManager();

	private Map<Long, TemplateDocument> templatesMap;

	private Map<Long, FavouriteLink> favouriteLinksMap;

	///////////////////////////////////////////////////////////////////////////

	protected void initialize() throws BusinessException, BusinessLogicException
	{
		List<WidgetObjectVisibility> templatesVisibility = getWidget().getTemplatesVisibility();
		List<TemplateDocument> templates = TemplateDocumentService.getInstance().findForPersonalMenu(PersonHelper.getContextPerson().asClient());
		templatesMap = new LinkedHashMap<Long, TemplateDocument>();
		for (TemplateDocument template : templates)
		{
			templatesMap.put(template.getId(), template);
		}

		for(TemplateDocument template : templates)
		{
			WidgetObjectVisibility visible = new WidgetObjectVisibility(template.getId(), true);
			WidgetObjectVisibility notVisible = new WidgetObjectVisibility(template.getId(), false);
			if(!(templatesVisibility.contains(visible) || templatesVisibility.contains(notVisible)))
			   templatesVisibility.add(new WidgetObjectVisibility(template.getId(), template.getTemplateInfo().isUseInERIB()));
		}

		for(Iterator<WidgetObjectVisibility> iterator = templatesVisibility.iterator(); iterator.hasNext();)
		{
			WidgetObjectVisibility currentTemplate = iterator.next();
			if(!templatesMap.containsKey(currentTemplate.getId()))
				iterator.remove();
		}

		getWidget().setTemplatesVisibility(templatesVisibility);

		List<WidgetObjectVisibility> favouriteLinksVisibility = getWidget().getFavouriteLinksVisibility();
		List<FavouriteLink> links = favouriteLinkManager.findByUserId(AuthModule.getAuthModule().getPrincipal().getLogin().getId());
		favouriteLinksMap = new LinkedHashMap<Long, FavouriteLink>();
		for (FavouriteLink link : links)
		{
			favouriteLinksMap.put(link.getId(), link);
		}

		for(FavouriteLink link : links)
		{
			WidgetObjectVisibility visible = new WidgetObjectVisibility(link.getId(), true);
			WidgetObjectVisibility notVisible = new WidgetObjectVisibility(link.getId(), false);
			if(!(favouriteLinksVisibility.contains(visible) || favouriteLinksVisibility.contains(notVisible)))
			   favouriteLinksVisibility.add(new WidgetObjectVisibility(link.getId(), true));
		}

		for(Iterator<WidgetObjectVisibility> iterator = favouriteLinksVisibility.iterator(); iterator.hasNext();)
		{
			WidgetObjectVisibility favouriteLink = iterator.next();
			if(!favouriteLinksMap.containsKey(favouriteLink.getId()))
				iterator.remove();
		}

		getWidget().setFavouriteLinksVisibility(favouriteLinksVisibility);
		
	}

	/**
	 * ѕолучить список всех шаблонов пользовател€
	 * @return
	 */
	public List<TemplateDocument> getAllTemplates()
	{
		List<WidgetObjectVisibility> templatesVisibility = getWidget().getTemplatesVisibility();
		List<TemplateDocument> allTemplates = new LinkedList<TemplateDocument>();
		for(WidgetObjectVisibility template : templatesVisibility)
		{
			TemplateDocument t = templatesMap.get(template.getId());
			allTemplates.add(t);
		}
		return allTemplates;
	}

	/**
	 * ѕолучить список шаблонов, которые отображаютс€ в данном виджете
	 * @return
	 */

	public List<TemplateDocument> getShowTemplates()
	{
		List<WidgetObjectVisibility> templatesVisibility = getWidget().getTemplatesVisibility();
		List<TemplateDocument> showTemplates = new LinkedList<TemplateDocument>();
		for(WidgetObjectVisibility template : templatesVisibility)
		{
			TemplateDocument t = templatesMap.get(template.getId());
			if(template.isVisible())
				showTemplates.add(t);
		}
		return showTemplates;
	}

	/**
	 * ѕолучить список всех избранных операций пользовател€
	 * @return
	 */
	public List<FavouriteLink> getAllFavouriteLinks()
	{
		List<WidgetObjectVisibility> favouriteLinksVisibility = getWidget().getFavouriteLinksVisibility();
		List<FavouriteLink> allFavouriteLinks = new LinkedList<FavouriteLink>();

		for(WidgetObjectVisibility favouriteLink : favouriteLinksVisibility)
		{
			FavouriteLink f = favouriteLinksMap.get(favouriteLink.getId());
			allFavouriteLinks.add(f);
		}
		return allFavouriteLinks;
	}

	/**
	 * ѕолучить список избранных операций, которые отображаютс€ в данном виджете
	 * @return
	 */

	public List<FavouriteLink> getShowFavouriteLinks()
	{
		List<WidgetObjectVisibility> favouriteLinksVisibility = getWidget().getFavouriteLinksVisibility();
		List<FavouriteLink> showFavouriteLinks = new LinkedList<FavouriteLink>();

		for(WidgetObjectVisibility favouriteLink : favouriteLinksVisibility)
		{
			FavouriteLink f = favouriteLinksMap.get(favouriteLink.getId());
			if(favouriteLink.isVisible())
				showFavouriteLinks.add(f);
		}
		return showFavouriteLinks;
	}

	public boolean shouldReloadClient()
	{
		return isSettingsChanged();
	}

	/**
	 * »зменилс€ ли список отображаемых шаблонов или избранных операций
	 * @return
	 */
	public boolean isSettingsChanged()
	{
		PersonalMenuWidget newWidget = getWidget();
		PersonalMenuWidget oldWidget = getOldWidget();

		if(ArrayUtils.isEquals(newWidget.getTemplatesVisibility().toArray(), oldWidget.getTemplatesVisibility().toArray()))
			if(ArrayUtils.isEquals(newWidget.getFavouriteLinksVisibility().toArray(), oldWidget.getFavouriteLinksVisibility().toArray()))
				return false;
		return true;
	}
	
}
