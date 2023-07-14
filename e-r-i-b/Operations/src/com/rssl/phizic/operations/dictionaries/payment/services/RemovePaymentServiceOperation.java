package com.rssl.phizic.operations.dictionaries.payment.services;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.payment.services.PaymentService;
import com.rssl.phizic.business.dictionaries.payment.services.PaymentServiceService;
import com.rssl.phizic.operations.dictionaries.synchronization.RemoveDictionaryEntityOperationBase;

/**
 * @author akrenev
 * @ created 01.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class RemovePaymentServiceOperation extends RemoveDictionaryEntityOperationBase
{
	private PaymentService paymentService;
	private static final PaymentServiceService paymentServiceService = new PaymentServiceService();

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		paymentService = paymentServiceService.findById(id, getInstanceName());
		if (paymentService == null)
		{
			throw new BusinessLogicException("Услуга с id " + id + " не найдена.");
		}
	}

	public void doRemove() throws BusinessException, BusinessLogicException
	{
		if (paymentService.isSystem())
			throw new BusinessLogicException("Невозможно удалить системную услугу");

		paymentServiceService.remove(paymentService, getInstanceName());
		paymentServiceService.deleteCardOperationCategoryToPaymentService(paymentService.getSynchKey().toString());
	}

	public PaymentService getEntity()
	{
		return paymentService;
	}
}
