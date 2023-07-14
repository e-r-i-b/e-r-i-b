package com.rssl.phizic.operations.dictionaries.payment.services.api;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.payment.services.api.PaymentServiceOld;
import com.rssl.phizic.business.dictionaries.payment.services.api.PaymentServiceOldService;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;

/**
 * @author lukina
 * @ created 27.05.2013
 * @ $Author$
 * @ $Revision$
 */

public class RemovePaymentServiceOldOperation extends OperationBase implements RemoveEntityOperation
{
	private PaymentServiceOld paymentService;
	private static final PaymentServiceOldService paymentServiceService = new PaymentServiceOldService();

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		paymentService = paymentServiceService.findById(id);
		if (paymentService == null)
		{
			throw new BusinessLogicException("Услуга с id " + id + " не найдена.");
		}
	}

	public void remove() throws BusinessException, BusinessLogicException
	{
		if (paymentService.isSystem())
			throw new BusinessLogicException("Невозможно удалить системную услугу");

		paymentServiceService.remove(paymentService);
	}

	public PaymentServiceOld getEntity()
	{
		return paymentService;
	}
}
