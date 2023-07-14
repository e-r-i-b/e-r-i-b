package com.rssl.phizic.business.ext.sbrf.dictionaries;

import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.BusinessException;

/**
 * @author Mescheryakova
 * @ created 27.09.2011
 * @ $Author$
 * @ $Revision$
 *
 * Сервис для работы с картами, загруженными из справочника ЦАС НСИ
 */

public class CASNSICardProductService
{
	private static final SimpleService simpleService = new SimpleService();

	/**
	 * Сохраняет или обновляет переданную сущность в БД
	 * @param obj   - сущность для сохранения
	 * @return      - сохраненная сущность
	 * @throws BusinessException
	 */
	public CASNSICardProduct addOrUpdate(final CASNSICardProduct obj) throws BusinessException
	{
		return simpleService.addOrUpdate(obj, null);
	}

	/**
	 * Поиск карты из справочника ЦАС НСИ по id
	 * @throws BusinessException
	 */
	public CASNSICardProduct findById(long id) throws BusinessException
	{
		return simpleService.findById(CASNSICardProduct.class, id);
	}
}
