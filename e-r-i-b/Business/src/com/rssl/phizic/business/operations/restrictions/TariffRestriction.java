package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ext.sbrf.tariffs.Tariff;

/**
 * Интерфейс рестрикшинов для тарифов
 * @author niculichev
 * @ created 19.04.2012
 * @ $Author$
 * @ $Revision$
 */
public interface TariffRestriction extends Restriction
{
	boolean accept(Tariff tariff) throws BusinessException;
}
