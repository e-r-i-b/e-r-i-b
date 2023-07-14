package com.rssl.phizic.operations.widget;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.profile.Profile;
import com.rssl.phizic.business.profile.ProfileService;
import com.rssl.phizic.business.web.Location;
import com.rssl.phizic.business.web.WebPage;
import com.rssl.phizic.business.web.WidgetDefinition;
import com.rssl.phizic.business.web.WidgetService;
import static com.rssl.phizic.business.web.WidgetUtils.cloneWebPageMap;
import static com.rssl.phizic.business.web.WidgetUtils.mapWebPagesByClassname;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.StringHelper;

import java.util.List;
import java.util.Map;

/**
 * @author lukina
 * @ created 25.11.2012
 * @ $Author$
 * @ $Revision$
 */

public class WebPageOperation extends OperationBase
{
	private static final ProfileService profileService = new ProfileService();

	private static final WidgetService widgetService = new WidgetService();

	private PersonData personData;

	private Profile profile;
	
	private Map<String, WidgetDefinition> definitions;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Инициализация операции
	 */
	public void initialize() throws BusinessException
	{
		this.initialize(null);	
	}

	public void initialize(String sideMenuLocation) throws BusinessException
	{
		personData = PersonContext.getPersonDataProvider().getPersonData();
		profile = personData.getProfile();
		definitions = personData.getWidgetDefinitions();
		if (!StringHelper.isEmpty(sideMenuLocation))
		{
			WebPage sideMenu = personData.getPages().get("sidemenu");
			if (sideMenu != null)
			{
				sideMenu.setLocation(Location.valueOf(sideMenuLocation));
			}
		}
	}

	/**
	 * сохраняем контейнер с виджетами
	 * @throws BusinessException
	 */
	public void save() throws BusinessException
	{
		boolean changed = saveChanges();
		if (changed)
			personData.setSavedPages(cloneWebPageMap(personData.getPages()));
	}

	@Transactional
	private boolean saveChanges() throws BusinessException
	{
		Map<String, WebPage> currentPages = personData.getPages();
		Map<String, WebPage> savedPages = personData.getSavedPages();

		boolean changed = false;
		for (WebPage currentPage : currentPages.values()) {
			String classname = currentPage.getClassname();
			WebPage savedPage = savedPages.get(classname);
			if (!currentPage.equals(savedPage)) {
				widgetService.saveWebPage(currentPage);
				changed = true;
			}
		}
		return changed;
	}

	/**
	 * отменяем несохраненные изменения
	 */
	public void cancel() throws BusinessException
	{
		Map<String, WebPage> savedPages = personData.getSavedPages();
		personData.setPages(cloneWebPageMap(savedPages));
	}

	/**
	 * восстанавливаем настройки по умолчанию
	 */
	public void reset() throws BusinessException, BusinessLogicException
	{
		widgetService.removeClientPersonalWebPages(profile);

		List<WebPage> defaultPagesList = widgetService.getClientDefaultWebPages(profile, definitions);
		Map<String, WebPage> defaultPages = mapWebPagesByClassname(defaultPagesList);

		personData.setSavedPages(defaultPages);
		personData.setPages(cloneWebPageMap(defaultPages));
	}
}
