package com.rssl.phizic.business.documents.templates.impl;

import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.payments.AccountToIMATransfer;
import com.rssl.phizic.gate.payments.CardToIMATransfer;
import com.rssl.phizic.gate.payments.IMAToAccountTransfer;
import com.rssl.phizic.gate.payments.IMAToCardTransfer;

/**
 * ������ �������/������� �������
 *
 * @author khudyakov
 * @ created 28.02.14
 * @ $Author$
 * @ $Revision$
 */
public class IMATransferTemplate extends InternalTransferTemplate
{
	public String getStateMachineName()
	{
		return getFormType().getName();
	}

	public FormType getFormType()
	{
		return FormType.IMA_PAYMENT;
	}

	public Class<? extends GateDocument> getType()
	{
		ResourceType chargeOffResourceType = getChargeOffResourceType();
		ResourceType destinationResourceType = getDestinationResourceType();

		if (chargeOffResourceType == ResourceType.NULL || destinationResourceType == ResourceType.NULL)
		{
			return null;
		}

		// ��� -> ����
		if (chargeOffResourceType == ResourceType.IM_ACCOUNT && destinationResourceType == ResourceType.ACCOUNT)
		{
			return IMAToAccountTransfer.class;
		}

		// ���� -> ���
		if (chargeOffResourceType == ResourceType.ACCOUNT && destinationResourceType == ResourceType.IM_ACCOUNT)
		{
			return AccountToIMATransfer.class;
		}

		// ��� -> �����
		if (chargeOffResourceType == ResourceType.IM_ACCOUNT && destinationResourceType == ResourceType.CARD)
		{
			return IMAToCardTransfer.class;
		}

		// ����� -> ���
		if (chargeOffResourceType == ResourceType.CARD && destinationResourceType == ResourceType.IM_ACCOUNT)
		{
			return CardToIMATransfer.class;
		}

		throw new IllegalStateException("��������� ���������� ��� ���������");
	}
}
