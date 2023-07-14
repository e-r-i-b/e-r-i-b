package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.dataaccess.query.FilterRestriction;

/**
 * Ограничение на работу с поставщиками услуг
 *
 * @author hudyakov
 * @ created 20.01.2010
 * @ $Author$
 * @ $Revision$
 */

public interface ServiceProvidersRestriction extends Restriction, FilterRestriction
{
	/**
	 * Проверка возможности работы с поставщиком услуг
	 * @param provider поставщик услуг
	 */
	boolean accept(ServiceProviderBase provider) throws BusinessException;
}

