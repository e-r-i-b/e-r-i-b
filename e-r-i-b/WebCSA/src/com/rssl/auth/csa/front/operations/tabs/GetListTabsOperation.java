package com.rssl.auth.csa.front.operations.tabs;

import com.rssl.auth.csa.front.business.tabs.Tabs;
import com.rssl.auth.csa.front.business.tabs.TabsService;
import com.rssl.phizic.cache.CacheProvider;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import java.util.List;

/**
 * @author osminin
 * @ created 16.11.2012
 * @ $Author$
 * @ $Revision$
 * Операция получения списка вкладок/разделов
 */
public class GetListTabsOperation
{
	private static final TabsService tabsService = new TabsService();
	private static final String CACHE_KEY        = "tabsListCSA";
	private static Cache tabsListCache           = CacheProvider.getCache("tabs-client-csa-cache");

	private List<Tabs> tabs;

	public void initialize() throws Exception
	{
		Element element = tabsListCache.get(CACHE_KEY);
		if (element == null)
			setNewTabs();
		else
			setCacheTabs(element);
	}

	public List<Tabs> getAllTabs()
	{
		return tabs;
	}

	//Установить вклдаки, загруженные из БД
	private void setNewTabs() throws Exception
	{
		tabs = tabsService.getAll();
		tabsListCache.put(new Element(CACHE_KEY, tabs));
	}

	//Установить вкладки из кеша
	private void setCacheTabs(Element element)
	{
		tabs = (List<Tabs>) element.getObjectValue();
	}
}
