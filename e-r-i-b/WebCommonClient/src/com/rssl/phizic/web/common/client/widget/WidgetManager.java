package com.rssl.phizic.web.common.client.widget;

import com.rssl.phizic.business.web.*;
import static com.rssl.phizic.business.web.WidgetUtils.mixWebPageMap;
import static com.rssl.phizic.business.web.WidgetUtils.mapWebPagesByClassname;
import static com.rssl.phizic.business.web.WidgetUtils.mapWidgetDefinitionsByCodename;
import com.rssl.phizic.business.profile.Profile;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.web.util.PersonInfoUtil;
import com.rssl.phizic.web.util.browser.BrowserUtils;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.system.LogModule;

import java.util.Map;
import java.util.List;

/**
 * �������� ��������
 * @author Dorzhinov
 * @ created 14.02.2013
 * @ $Author$
 * @ $Revision$
 */
public class WidgetManager
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Web);
	private static final WidgetDefinitionService widgetDefinitionService = new WidgetDefinitionService();
	private static final WidgetService widgetService = new WidgetService();

	/**
	 * ��������� �������� ������������ ������� � �������� � ������-����������
	 */
	public static void loadWidgets() throws BusinessException, BusinessLogicException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		Profile profile = personData.getProfile();

		if (!PersonInfoUtil.isAvailableWidget()) {
			log.trace("������� ��������� � ������� loginId = " + personData.getLogin().getId());
			return;
		}

		if (BrowserUtils.isOldBrowser()) {
			log.trace("������� ���������� �������� ������� loginId = " + personData.getLogin().getId());
			return;
		}

		// 1. �������� ������-���������, ��������� �� ������ �����
		List<WidgetDefinition> widgetDefinitionList = widgetDefinitionService.getAllAvailable();
		Map<String, WidgetDefinition> widgetDefinitions = mapWidgetDefinitionsByCodename(widgetDefinitionList);

		// 2. ������ ��������, ������� �������� ������������ ��-���������
		List<WebPage> defaultPageList = widgetService.getClientDefaultWebPages(profile, widgetDefinitions);
		Map<String, WebPage> defaultPages = mapWebPagesByClassname(defaultPageList);

		// 3. ������ ��������, ���������� � �������� ������
		List<WebPage> savedPageList = widgetService.getClientPersonalWebPages(profile, widgetDefinitions);
		Map<String, WebPage> savedPages = mapWebPagesByClassname(savedPageList);

		// 4. ��������� �������� ��.1-2 ������
		Map<String, WebPage> pages = mixWebPageMap(defaultPages, savedPages);

		// 5. �������� ���� �� ���������
		;

		// 6. ����� ��������� � ������
		personData.setWidgetDefinitions(widgetDefinitions);
		personData.setSavedPages(pages);
		personData.setPages(WidgetUtils.cloneWebPageMap(pages));
	}
}
