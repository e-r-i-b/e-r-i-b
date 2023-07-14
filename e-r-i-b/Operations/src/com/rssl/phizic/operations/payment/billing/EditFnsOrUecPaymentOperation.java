package com.rssl.phizic.operations.payment.billing;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.shop.ExternalPaymentService;

/**
 * Операция редактирования ФНС или УЭК платежа.
 *
 * @author bogdanov
 * @ created 14.03.14
 * @ $Author$
 * @ $Revision$
 */

public class EditFnsOrUecPaymentOperation extends EditInternetShopPaymentOperation
{
	@Override
	protected void linkPayment(String orderUuid, JurPayment payment) throws BusinessException
	{
		ExternalPaymentService.get().linkPayment(orderUuid, payment);
	}
}
