package com.rssl.phizic.gate.templates.impl;

import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.payments.AccountToIMATransfer;
import com.rssl.phizic.gate.payments.CardToIMATransfer;
import com.rssl.phizic.gate.payments.IMAToAccountTransfer;
import com.rssl.phizic.gate.payments.IMAToCardTransfer;

/**
 * Шаблон покупки/продажи метала
 *
 * @author khudyakov
 * @ created 29.04.14
 * @ $Author$
 * @ $Revision$
 */
public class IMATransferTemplate extends InternalTransferTemplate
{
	@Override
	public FormType getFormType()
	{
		return FormType.IMA_PAYMENT;
	}

	@Override
	public Class<? extends GateDocument> getType()
	{
		ResourceType chargeOffResourceType = getChargeOffResourceType();
		ResourceType destinationResourceType = getDestinationResourceType();

		if (chargeOffResourceType == ResourceType.NULL || destinationResourceType == ResourceType.NULL)
		{
			return null;
		}

		// ОМС -> счёт
		if (chargeOffResourceType == ResourceType.IM_ACCOUNT && destinationResourceType == ResourceType.ACCOUNT)
		{
			return IMAToAccountTransfer.class;
		}

		// счёт -> ОМС
		if (chargeOffResourceType == ResourceType.ACCOUNT && destinationResourceType == ResourceType.IM_ACCOUNT)
		{
			return AccountToIMATransfer.class;
		}

		// ОМС -> карта
		if (chargeOffResourceType == ResourceType.IM_ACCOUNT && destinationResourceType == ResourceType.CARD)
		{
			return IMAToCardTransfer.class;
		}

		// карта -> ОМС
		if (chargeOffResourceType == ResourceType.CARD && destinationResourceType == ResourceType.IM_ACCOUNT)
		{
			return CardToIMATransfer.class;
		}

		throw new IllegalStateException("Невозмжно определить тип документа");
	}
}
