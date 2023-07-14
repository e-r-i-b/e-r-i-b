package com.rssl.phizic.operations.ext.sbrf.tariffs;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.ext.sbrf.tariffs.Tariff;
import com.rssl.phizic.business.ext.sbrf.tariffs.TariffService;
import com.rssl.phizic.business.operations.restrictions.TariffRestriction;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;

/**
 * �������� ������������� �������
 * @author niculichev
 * @ created 18.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditTariffOperation extends OperationBase<TariffRestriction> implements EditEntityOperation<Tariff, TariffRestriction>
{
	private static final TariffService tariffService = new TariffService();
	private Tariff tariff;
	private boolean isNew;

	public void initialize(Long id) throws BusinessException
	{
		tariff = tariffService.findById(id);

		if(tariff == null)
			throw new ResourceNotFoundBusinessException("����� � ��������������� " + id + "�� ������ � �������", Tariff.class);

		if(!getRestriction().accept(tariff))
			throw new BusinessException("���������� ��������������� ����� � ��������������� " + id + " ������ ���������");

		isNew = false;
	}

	public void initializeNew()
	{
		tariff = new Tariff();
		isNew = true;
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		if(isNew && tariffService.findByTypeAndCur(tariff.getCurrencyCode(), tariff.getTransferType()) != null)
			throw new BusinessLogicException("��� ��������� ������ ��� ���������� �����. ����������, �������������� ������ �����.");

		tariffService.addOrUpdate(tariff);
	}

	public Tariff getEntity() throws BusinessException, BusinessLogicException
	{
		return tariff;  
	}
}
