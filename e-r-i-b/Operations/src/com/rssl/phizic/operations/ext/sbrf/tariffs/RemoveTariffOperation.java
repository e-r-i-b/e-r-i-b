package com.rssl.phizic.operations.ext.sbrf.tariffs;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ext.sbrf.tariffs.Tariff;
import com.rssl.phizic.business.ext.sbrf.tariffs.TariffService;
import com.rssl.phizic.business.operations.restrictions.TariffRestriction;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;

/**
 * Операция удаления тарифов коммиссий на перевод в другой ТБ и другой банк
 * @author niculichev
 * @ created 19.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class RemoveTariffOperation extends OperationBase<TariffRestriction> implements RemoveEntityOperation<Tariff, TariffRestriction>
{
	private static final TariffService tariffService = new TariffService();
	private Tariff tariff;

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		tariff = tariffService.findById(id);
		if(tariff == null)
			throw new BusinessException("Тариф с идентификатором " + id + " не найден в системе");

		if(!getRestriction().accept(tariff))
			throw new BusinessException("Невозможно удалить тариф с идентификатором " + id + " данной операцией");

	}

	public void remove() throws BusinessException
	{
		tariffService.remove(tariff);
	}

	public Tariff getEntity()
	{
		return tariff;
	}
}
