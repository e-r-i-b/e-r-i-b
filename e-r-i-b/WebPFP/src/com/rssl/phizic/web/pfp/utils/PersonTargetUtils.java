package com.rssl.phizic.web.pfp.utils;

import com.rssl.phizic.business.dictionaries.pfp.targets.Target;
import com.rssl.phizic.business.dictionaries.pfp.targets.TargetService;
import com.rssl.phizic.business.BusinessException;

import java.util.List;

/**
 * @author mihaylov
 * @ created 05.09.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Утилитный класс для работы с целями клиента
 */
public class PersonTargetUtils
{
	private static final TargetService targetService = new TargetService();

	/**
	 * @return возвращает список возможных целей из справочника
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public static List<Target> getDictionaryTargetList() throws BusinessException
	{
		return targetService.getAll();
	}
}
