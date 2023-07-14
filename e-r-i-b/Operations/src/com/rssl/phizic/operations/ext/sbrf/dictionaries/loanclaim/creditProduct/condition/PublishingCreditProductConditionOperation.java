package com.rssl.phizic.operations.ext.sbrf.dictionaries.loanclaim.creditProduct.condition;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.loanclaim.LoanClaimHelper;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CreditProductCondition;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CreditProductConditionService;
import com.rssl.phizic.operations.OperationBase;

/**
 * @author Moshenko
 * @ created 24.01.2014
 * @ $Author$
 * @ $Revision$
 * �������� � ������������ ��������� �������.
 */
public class PublishingCreditProductConditionOperation extends OperationBase
{
	private static final CreditProductConditionService conditionService = new CreditProductConditionService();
	/**
	 * ������������ �������.
	 * @param id �������.
	 */
	public void publish(Long id) throws BusinessException, BusinessLogicException
	{
		CreditProductCondition condition = getCondition(id);
		if (condition.isPublished())
			throw new BusinessLogicException("������ ��������� ������� ��� �����������");
		if (!LoanClaimHelper.hasActiveCurrencyCondition(condition))
			throw new BusinessLogicException("��� ��������������� �������� ������ ���� ������ ���� �� ���� " +
					"������� � ������� �����");

		condition.setPublished(true);
		conditionService.addOrUpdate(condition);

	}
	/**
	 * ����� � ����������� �������.
	 * @param id �������.
	 */
	public void unpublish(Long id) throws BusinessLogicException, BusinessException
	{
		CreditProductCondition condition = getCondition(id);
		if (!condition.isPublished())
			throw new BusinessLogicException("������ ��������� ������� ��� ���� � ����������");

		condition.setPublished(false);
		conditionService.addOrUpdate(condition);
	}

	private  CreditProductCondition getCondition(Long id) throws BusinessException, BusinessLogicException
	{
		CreditProductCondition condition = conditionService.findeById(id);
		if (condition == null)
			throw new BusinessLogicException("������� � Id: " + id + " �� ������� � �������.");
		return condition;
	}
}
