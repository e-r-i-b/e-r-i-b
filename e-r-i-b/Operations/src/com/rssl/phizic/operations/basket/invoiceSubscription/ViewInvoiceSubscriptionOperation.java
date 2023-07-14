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
 * Операция просмотра подписки (услуги)
 */
public class ViewInvoiceSubscriptionOperation extends OperationBase<InvoiceSubscriptionRestriction> implements ViewEntityOperation<InvoiceSubscription>
{
	private static InvoiceSubscriptionService invoiceSubscriptionService = new InvoiceSubscriptionService();
	private static AccountingEntityService accountingEntityService = new AccountingEntityService();

	private InvoiceSubscription invoiceSubscription;

	/**
	 * Инициализация операции
	 * @param id идентификатор подписки (услуги)
	 */
	public void initialize(Long id) throws BusinessException
	{
		if (id == null)
		{
			throw new IllegalArgumentException("Идентификатор подписки не может быть null");
		}

		invoiceSubscription = invoiceSubscriptionService.findById(id);

		if (invoiceSubscription == null)
		{
			throw new BusinessException("Подписка с id " + id + " не найдена.");
		}

		if(!getRestriction().accept(invoiceSubscription))
		{
			throw new BusinessException("Подписка с id = " + id + " не принадлежит клиенту");
		}
	}

	/**
	 * Инициализация операции
	 * @param id идентификатор подписки (услуги)
	 */
	public void initialize(Long id, boolean isAdmin) throws BusinessException
	{
		if (id == null)
		{
			throw new IllegalArgumentException("Идентификатор подписки не может быть null");
		}

		invoiceSubscription = invoiceSubscriptionService.findById(id);

		if (invoiceSubscription == null)
		{
			throw new BusinessException("Подписка с id " + id + " не найдена.");
		}

		if(!isAdmin && !getRestriction().accept(invoiceSubscription))
		{
			throw new BusinessException("Подписка с id = " + id + " не принадлежит клиенту");
		}
	}

	public InvoiceSubscription getEntity() throws BusinessException, BusinessLogicException
	{
		return invoiceSubscription;
	}

	/**
	 * Получить наименование объекта учета по идентификатору
	 * @return наименование объекта учета или null, если подписка не привзяна к объекту учета
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
