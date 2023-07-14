package com.rssl.phizic.operations.basket.accountingEntity;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.basket.accountingEntity.AccountingEntity;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscriptionService;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;

/**
 * @author osminin
 * @ created 13.04.14
 * @ $Author$
 * @ $Revision$
 *
 * �������� �������� ������� �����
 */
public class RemoveAccountingEntityOperation extends OperationBase implements RemoveEntityOperation
{
	private static final SimpleService simpleService = new SimpleService();
	private static final InvoiceSubscriptionService invoiceSubscriptionService = new InvoiceSubscriptionService();

	private AccountingEntity entity;

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		entity = simpleService.findById(AccountingEntity.class, id);

		if (entity == null)
		{
			throw new BusinessException("������ ����� � ��������������� " + id + " �� ������.");
		}
	}

	public void remove() throws BusinessException, BusinessLogicException
	{
		//��� ��������, ����������� � ������� �����, ��������� � ���� "������ ������"
		invoiceSubscriptionService.ungroupSubscriptions(entity.getId());
		//������� ������ �����
		simpleService.remove(entity);
	}

	public Object getEntity()
	{
		return entity;
	}
}
