package com.rssl.phizic.operations.basket.invoiceSubscription;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.basket.accountingEntity.AccountingEntityService;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscriptionService;
import com.rssl.phizic.business.operations.restrictions.InvoiceSubscriptionRestriction;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ViewEntityOperation;

/**
 * @author osminin
 * @ created 16.04.14
 * @ $Author$
 * @ $Revision$
 *
 * �������� ��������� �������� (������)
 */
public class ViewInvoiceSubscriptionOperation extends OperationBase<InvoiceSubscriptionRestriction> implements ViewEntityOperation<InvoiceSubscription>
{
	private static InvoiceSubscriptionService invoiceSubscriptionService = new InvoiceSubscriptionService();
	private static AccountingEntityService accountingEntityService = new AccountingEntityService();

	private InvoiceSubscription invoiceSubscription;

	/**
	 * ������������� ��������
	 * @param id ������������� �������� (������)
	 */
	public void initialize(Long id) throws BusinessException
	{
		if (id == null)
		{
			throw new IllegalArgumentException("������������� �������� �� ����� ���� null");
		}

		invoiceSubscription = invoiceSubscriptionService.findById(id);

		if (invoiceSubscription == null)
		{
			throw new BusinessException("�������� � id " + id + " �� �������.");
		}

		if(!getRestriction().accept(invoiceSubscription))
		{
			throw new BusinessException("�������� � id = " + id + " �� ����������� �������");
		}
	}

	/**
	 * ������������� ��������
	 * @param id ������������� �������� (������)
	 */
	public void initialize(Long id, boolean isAdmin) throws BusinessException
	{
		if (id == null)
		{
			throw new IllegalArgumentException("������������� �������� �� ����� ���� null");
		}

		invoiceSubscription = invoiceSubscriptionService.findById(id);

		if (invoiceSubscription == null)
		{
			throw new BusinessException("�������� � id " + id + " �� �������.");
		}

		if(!isAdmin && !getRestriction().accept(invoiceSubscription))
		{
			throw new BusinessException("�������� � id = " + id + " �� ����������� �������");
		}
	}

	public InvoiceSubscription getEntity() throws BusinessException, BusinessLogicException
	{
		return invoiceSubscription;
	}

	/**
	 * �������� ������������ ������� ����� �� ��������������
	 * @return ������������ ������� ����� ��� null, ���� �������� �� �������� � ������� �����
	 * @throws BusinessException
	 */
	public String getAccountingEntityName() throws BusinessException
	{
		if (invoiceSubscription.getAccountingEntityId() == null)
		{
			return null;
		}
		return accountingEntityService.getNameById(invoiceSubscription.getAccountingEntityId());
	}
}
