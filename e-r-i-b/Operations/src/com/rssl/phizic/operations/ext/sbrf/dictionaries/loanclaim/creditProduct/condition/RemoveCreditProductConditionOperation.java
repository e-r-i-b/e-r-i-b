package com.rssl.phizic.operations.ext.sbrf.dictionaries.loanclaim.creditProduct.condition;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CreditProductCondition;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CreditProductConditionService;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;

/**
 * @author Moshenko
 * @ created 16.01.2014
 * @ $Author$
 * @ $Revision$
 * �������� �������� ������� �� ���������� ��������.
 */
public class RemoveCreditProductConditionOperation extends OperationBase implements RemoveEntityOperation
{
	private static final CreditProductConditionService conditionService = new CreditProductConditionService();
	private CreditProductCondition entity;

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		entity = conditionService.findeById(id);

		if (entity == null)
			throw new BusinessException("������� �� ���������� �������� � Id = " + id + " �� �������.");
	}

	public void remove() throws BusinessException, BusinessLogicException
	{
		if (entity.isPublished())
			throw new BusinessLogicException("�� �� ������ ������� ������ ��������� �������, ��� ��� �� �������� �������� ��� ���������� �������. ����� ������� ��������� �������, ������� ��� � ����������.");
		else
			conditionService.remove(entity);
	}

	public CreditProductCondition getEntity()
	{
		return entity;
	}
}
