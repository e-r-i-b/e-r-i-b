package com.rssl.phizic.business.documents.templates.impl;

import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.payments.*;

/**
 * ������ �������� ����������� (����� �����, �� ������ � ������ ������ �� ���������� �� 3� �����)
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
		//������� ��. ���� � �����
		if (chargeOffResourceType == ResourceType.CARD)
		{
			if (isOurBank())
			{
				//�� ���� ������ �����
				return CardJurIntraBankTransfer.class;
			}
			//�� ���� � ������ ���� ����� ��������� ������� ������
			return CardJurTransfer.class;
		}
		//������� ��. ���� �� �����
		if (chargeOffResourceType == ResourceType.ACCOUNT || chargeOffResourceType == ResourceType.NULL)
		{
			if (isOurBank())
			{
				//�� ���� ������ �����
				return AccountJurIntraBankTransfer.class;
			}
				//�� ���� � ������ ���� ����� ��������� ������� ������
			return AccountJurTransfer.class;
		}
		throw new IllegalStateException("��������� ���������� ��� ���������");
	}

	public String getReceiverSubType()
	{
		return "juridicalExternalAccount";
	}

	public void setTariffPlanESB(String tariffPlanESB)
	{

	}
}
