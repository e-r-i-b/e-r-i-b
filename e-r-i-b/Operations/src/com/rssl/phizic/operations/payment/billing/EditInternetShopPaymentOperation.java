package com.rssl.phizic.operations.payment.billing;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.shop.ShopHelper;
import com.rssl.phizic.utils.StringHelper;

/**
 * ќпераци€ по редактированию интернет-заказа.
 *
 * @author bogdanov
 * @ created 14.03.14
 * @ $Author$
 * @ $Revision$
 */

public class EditInternetShopPaymentOperation extends EditServicePaymentOperation
{
	@Override
	public Long save() throws BusinessException, BusinessLogicException
	{
		Long documentId = super.save();

		onSave();

		return documentId;
	}

	private void onSave() throws BusinessException
	{
		JurPayment payment = (JurPayment) getDocument();
		String orderUUID = payment.getOrderUuid();
		if (StringHelper.isEmpty(orderUUID))
			return;
		BusinessDocument doc = DocumentHelper.getPaymentByOrder(orderUUID);
		if (doc != null)
			return;

		linkPayment(orderUUID, payment);
	}

	protected void linkPayment(String orderUuid, JurPayment payment) throws BusinessException
	{
		ShopHelper.get().linkPayment(orderUuid, payment);
	}
}
