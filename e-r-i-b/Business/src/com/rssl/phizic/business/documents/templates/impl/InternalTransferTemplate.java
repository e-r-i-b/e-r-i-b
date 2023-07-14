package com.rssl.phizic.business.documents.templates.impl;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.resources.external.PaymentAbilityERL;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.AccountToCardTransfer;
import com.rssl.phizic.gate.payments.CardToAccountTransfer;
import com.rssl.phizic.gate.payments.ClientAccountsTransfer;
import com.rssl.phizic.gate.payments.InternalCardsTransfer;
import java.util.Map;

import static com.rssl.phizic.business.documents.templates.Constants.*;

/**
 * Шаблон платежа на внутренние счета/карты клиента
 *
 * @author khudyakov
 * @ created 16.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class InternalTransferTemplate extends TransferTemplateBase
{
	public String getStateMachineName()
	{
		return getFormType().getName();
	}

	public FormType getFormType()
	{
		return FormType.INTERNAL_TRANSFER;
	}

	public Class<? extends GateDocument> getType()
	{
		ResourceType chargeOffResourceType = getChargeOffResourceType();
		ResourceType destinationResourceType = getDestinationResourceType();

		if (chargeOffResourceType == ResourceType.NULL || destinationResourceType == ResourceType.NULL)
		{
			return null;
		}

		if (chargeOffResourceType == ResourceType.CARD && destinationResourceType == ResourceType.CARD)
		{
			return InternalCardsTransfer.class;
		}

		if (chargeOffResourceType == ResourceType.CARD && destinationResourceType == ResourceType.ACCOUNT)
		{
			return CardToAccountTransfer.class;
		}

		if (chargeOffResourceType == ResourceType.ACCOUNT && destinationResourceType == ResourceType.CARD)
		{
			return AccountToCardTransfer.class;
		}

		if (chargeOffResourceType == ResourceType.ACCOUNT && destinationResourceType == ResourceType.ACCOUNT)
		{
			return ClientAccountsTransfer.class;
		}

		throw new IllegalStateException("Невозмжно определить тип документа");
	}

	public Map<String, String> getFormData() throws BusinessException
	{
		Map<String, String> formData = super.getFormData();
		appendNullSaveMoney(formData, BUY_AMOUNT_VALUE_ATTRIBUTE_NAME, getDestinationAmount());

		PaymentAbilityERL toResourceLink = getDestinationResourceLink();
		if (toResourceLink != null)
		{
			appendNullSaveString(formData, TO_RESOURCE_ATTRIBUTE_NAME, toResourceLink.getCode());
		}

		return formData;
	}

	public void setTariffPlanESB(String tariffPlanESB)
	{

	}
}
