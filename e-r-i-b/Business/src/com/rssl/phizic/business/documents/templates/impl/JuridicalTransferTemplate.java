package com.rssl.phizic.business.documents.templates.impl;

import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.payments.*;

/**
 * Ўаблон перевода организации (обща€ форма, не путать с формой оплаты по реквизитам из 3х полей)
 *
 * @author khudyakov
 * @ created 28.02.14
 * @ $Author$
 * @ $Revision$
 */
public class JuridicalTransferTemplate extends ExternalTransferTemplate
{
	public String getStateMachineName()
	{
		return getFormType().getName();
	}

	public FormType getFormType()
	{
		return FormType.JURIDICAL_TRANSFER;
	}

	public Class<? extends GateDocument> getType()
	{
		ResourceType chargeOffResourceType = getChargeOffResourceType();
		//перевод юр. лицу с карты
		if (chargeOffResourceType == ResourceType.CARD)
		{
			if (isOurBank())
			{
				//на счет внутри банка
				return CardJurIntraBankTransfer.class;
			}
			//на счет в другой банк через платежную систему –оссии
			return CardJurTransfer.class;
		}
		//перевод юр. лицу со счета
		if (chargeOffResourceType == ResourceType.ACCOUNT || chargeOffResourceType == ResourceType.NULL)
		{
			if (isOurBank())
			{
				//на счет внутри банка
				return AccountJurIntraBankTransfer.class;
			}
				//на счет в другой банк через платежную систему –оссии
			return AccountJurTransfer.class;
		}
		throw new IllegalStateException("Ќевозмжно определить тип документа");
	}

	public String getReceiverSubType()
	{
		return "juridicalExternalAccount";
	}

	public void setTariffPlanESB(String tariffPlanESB)
	{

	}
}
