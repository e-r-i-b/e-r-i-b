package com.rssl.phizic.web.util;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.city.City;
import com.rssl.phizic.business.dictionaries.city.CityService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author lepihina
 * @ created 29.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class CityUtil
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final CityService cityService = new CityService();

	/**
	 * ѕолучение названи€ города по коду из справочника
	 * @param code код города в справочнике
	 * @return название города
	 */
	public static City getCityByCode(String code)
	{
		if (StringHelper.isEmpty(code))
			return null;

		try
		{
			return cityService.getByCode(code);
		}
		catch(BusinessException be)
		{
			log.error("ќшибка поиска города в справочнике", be);
			return null;
		}
	}
}
