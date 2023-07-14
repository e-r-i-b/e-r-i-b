package com.rssl.phizic.gate.templates.impl;

import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.payments.AccountToCardTransfer;
import com.rssl.phizic.gate.payments.CardToAccountTransfer;
import com.rssl.phizic.gate.payments.ClientAccountsTransfer;
import com.rssl.phizic.gate.payments.InternalCardsTransfer;

/**
 * Шаблон перевода между моими счетами
 *
 * @author khudyakov
 * @ created 29.04.14
 * @ $Author$
 * @ $Revision$
 */
public class InternalTransferTemplate extends CommonTemplateBase
{
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

	public void setTariffPlanESB(String tariffPlanESB)
	{

	}
}
