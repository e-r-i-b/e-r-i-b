package com.rssl.phizic.web.util;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.pages.PageService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

/** класс для работы со страницами
 * @author akrenev
 * @ created 02.09.2011
 * @ $Author$
 * @ $Revision$
 */
public class PageUtil
{
	public static final PageService pageService = new PageService();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * является ли страница с id == parentId группой
	 * @param parentId id группы
	 * @return имеет ли страница дочерние страницы
	 */
	public static boolean isGroupPage(Long parentId)
	{
		try
		{
			return pageService.isGroupPage(parentId);
		}
		catch (BusinessException e)
		{
			log.error("Ошибка определения списка страниц по parent", e);
			return false;
		}
	}

}
