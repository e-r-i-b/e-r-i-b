package com.rssl.phizic.operations.ext.sbrf.tariffs;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ext.sbrf.tariffs.Tariff;
import com.rssl.phizic.business.ext.sbrf.tariffs.TariffService;
import com.rssl.phizic.business.operations.restrictions.TariffRestriction;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;

/**
 * �������� �������� ������� ��������� �� ������� � ������ �� � ������ ����
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
			throw new BusinessException("����� � ��������������� " + id + " �� ������ � �������");

		if(!getRestriction().accept(tariff))
			throw new BusinessException("���������� ������� ����� � ��������������� " + id + " ������ ���������");

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
