package com.rssl.phizic.web.actions.payments.forms;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProviderBase;
import com.rssl.phizic.business.resources.external.PaymentAbilityERL;
import com.rssl.phizic.operations.ext.sbrf.payment.CreateESBAutoPayOperation;
import com.rssl.phizic.operations.payment.billing.EditServicePaymentOperation;

import java.util.Map;

/**
 * Экшн для создания шинного автоплатежа(автоподписки)
 * @author niculichev
 * @ created 12.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class CreateAutoSubscriptionActionBase extends EditServicePaymentAction
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.put("button.next", "makeLongOffer");
		map.remove("button.changeConditions");
		map.remove("afterAccountOpening");
		return map;
	}
	
	protected void updateFormAdditionalData(EditServicePaymentForm frm, EditServicePaymentOperation editServicePaymentOperation) throws BusinessException, BusinessLogicException
	{
		CreateESBAutoPayOperation operation = (CreateESBAutoPayOperation) editServicePaymentOperation;

		PaymentAbilityERL chargeOffResourceLink = operation.getChargeOffResourceLink();
		if (chargeOffResourceLink != null)
			frm.setFromResource(chargeOffResourceLink.getCode());

		BillingServiceProviderBase provider = operation.getProvider();
		frm.setRecipient(provider.getId());
		frm.setProviderImageId(provider.getImageId());
		frm.setProviders(operation.getProviderAllServices());
		frm.setFieldsDescription(operation.getProviderAllServicesFields());

		frm.setChargeOffResources(operation.getChargeOffResources());
		frm.setDocument(editServicePaymentOperation.getDocument());
		frm.setMetadata(editServicePaymentOperation.getMetadata());
	}
}
