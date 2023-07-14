package com.rssl.phizic.operations.ext.sbrf.dictionaries.loanclaim.creditProduct.condition;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.loanclaim.creditProduct.CreditProduct;
import com.rssl.phizic.business.loanclaim.creditProduct.CreditProductService;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CreditProductCondition;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CreditProductConditionService;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CurrencyCreditProductCondition;
import com.rssl.phizic.operations.OperationBase;

import java.util.List;

/**
 * @author Moshenko
 * @ created 27.12.2013
 * @ $Author$
 * @ $Revision$
 * �������� ��������� ������� �������.
 */
public class ViewHistoryCreditProductConditionOperation  extends OperationBase
{

	private static final CreditProductConditionService conditionService = new CreditProductConditionService();
	private List<CurrencyCreditProductCondition> entity;
	/**
	 * @param id ������ �������.
	 * @param currCode ��� ������.
	 * @throws BusinessException
	 */
	public void initialize(Long id,String currCode) throws BusinessException
	{
		if (id == null)
			throw new BusinessException("���������� �������� �������� ������� �� ������� �������� id.");

		CreditProductCondition cond = conditionService.findeById(id);
		if (cond == null)
			throw new BusinessException("������� �� �������  � id: " + id + " �� �������.");

		entity = conditionService.getArchCurrCondition(cond,currCode);
	}
	/**
	 * @return ������ ������������ ���������� �������.
	 */
	public List<CurrencyCreditProductCondition> getEntity()
	{
		return entity;
	}

}
