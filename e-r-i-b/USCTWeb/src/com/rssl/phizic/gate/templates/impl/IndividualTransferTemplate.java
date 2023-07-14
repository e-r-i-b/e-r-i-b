package com.rssl.phizic.gate.templates.impl;

import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.payments.*;
import org.apache.commons.lang.BooleanUtils;

/**
 * ������ �������� �������� ����/�����������
 *
 * @author khudyakov
 * @ created 30.04.14
 * @ $Author$
 * @ $Revision$
 */
public class IndividualTransferTemplate extends ExternalTransferTemplate
{
	public Class<? extends GateDocument> getType()
	{
		ResourceType chargeOffResourceType = getChargeOffResourceType();

		if (chargeOffResourceType == ResourceType.NULL)
		{
			return null;
		}

		ResourceType destinationResourceType = getDestinationResourceType();
		//������� ���. ���� � �����
		if (Constants.PHIZ_RECEIVER_TYPE_ATTRIBUTE_VALUE.equals(getReceiverType()))
		{
			if (chargeOffResourceType == ResourceType.CARD)
			{
				//�� �����
				if (destinationResourceType == ResourceType.EXTERNAL_CARD)
				{
					// ���� ������� ����������������
					return isOurBank() ? ExternalCardsTransferToOurBank.class : ExternalCardsTransferToOtherBank.class;
				}

				//�� ���� ������ �����
				if (isOurBank())
				{
					return CardIntraBankPayment.class;
				}
				//�� ���� � ������ ���� ����� ��������� ������� ������
				return CardRUSPayment.class;
			}
			//������� ���. ���� �� �����
			if (chargeOffResourceType == ResourceType.ACCOUNT)
			{
				//�� �����
				if (destinationResourceType == ResourceType.EXTERNAL_CARD)
				{
					throw new UnsupportedOperationException("������� �� ����� �� ����� ����� �� ��������������.");
				}
				//�� ���� ������ �����
				if (isOurBank())
				{
					return AccountIntraBankPayment.class;
				}
				//�� ���� � ������ ���� ����� ��������� ������� ������
				return AccountRUSPayment.class;
			}
		}

		if (Constants.JUR_RECEIVER_TYPE_ATTRIBUTE_VALUE.equals(getReceiverType()))
		{
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
		}
		throw new IllegalStateException("��������� ���������� ��� ���������");
	}

	public FormType getFormType()
	{
		if (Constants.JUR_RECEIVER_TYPE_ATTRIBUTE_VALUE.equals(getReceiverType()))
		{
			return FormType.JURIDICAL_TRANSFER;
		}
		return FormType.INDIVIDUAL_TRANSFER;
	}

	@Override
	public ResourceType getDestinationResourceType()
	{
		String receiverSubType = getReceiverSubType();
		if (isCardTransfer(receiverSubType))
		{
			return ResourceType.EXTERNAL_CARD;
		}
		return ResourceType.EXTERNAL_ACCOUNT;
	}

	@Override
	public String getReceiverPhone()
	{
		return getNullSaveAttributeStringValue(Constants.RECEIVER_EXTERNAL_PHONE_NUMBER_ATTRIBUTE_NAME);
	}

	@Override
	public void setReceiverPhone(String receiverPhone)
	{
		setNullSaveAttributeStringValue(Constants.RECEIVER_EXTERNAL_PHONE_NUMBER_ATTRIBUTE_NAME, receiverPhone);
	}

	@Override
	public ResidentBank getReceiverBank()
	{
		if (FormType.INDIVIDUAL_TRANSFER == getFormType() || FormType.INDIVIDUAL_TRANSFER_NEW == getFormType())
		{
			//��������� ������������ � pfd.xml ����
			return super.getReceiverBank();
		}
		return new ResidentBank(
				getNullSaveAttributeStringValue(Constants.RECEIVER_BANK_NAME_ATTRIBUTE_NAME),
				getNullSaveAttributeStringValue(Constants.RECEIVER_BANK_BIK_ATTRIBUTE_NAME),
				getNullSaveAttributeStringValue(Constants.RECEIVER_BANK_COR_ACCOUNT_ATTRIBUTE_NAME)
		);
	}

	@Override
	public void setReceiverBank(ResidentBank receiverBank)
	{
		if (FormType.INDIVIDUAL_TRANSFER == getFormType() || FormType.INDIVIDUAL_TRANSFER_NEW == getFormType())
		{
			//��������� ������������ � pfd.xml ����
			super.setReceiverBank(receiverBank);
			return;
		}

		setNullSaveAttributeStringValue(Constants.INDIVIDUAL_RECEIVER_BANK_NAME_ATTRIBUTE_NAME, receiverBank == null ? null : receiverBank.getName());
		setNullSaveAttributeStringValue(Constants.RECEIVER_BANK_BIK_ATTRIBUTE_NAME,  receiverBank == null ? null : receiverBank.getBIC());
		setNullSaveAttributeStringValue(Constants.RECEIVER_BANK_COR_ACCOUNT_ATTRIBUTE_NAME, receiverBank == null ? null : receiverBank.getAccount());
	}

	/**
	 * @return ��� ���������� (�����/����)
	 */
	public String getReceiverType()
	{
		return getNullSaveAttributeStringValue(Constants.RECEIVER_TYPE_ATTRIBUTE_NAME);
	}

	/**
	 * @return ������ ���������� (������� ����, ����� �����, ������� �����(visa/maestro)...)
	 */
	public String getReceiverSubType()
	{
		if (Constants.JUR_RECEIVER_TYPE_ATTRIBUTE_VALUE.equals(getReceiverType()))
		{
			return "juridicalExternalAccount";
		}

		return getNullSaveAttributeStringValue(Constants.RECEIVER_SUB_TYPE_ATTRIBUTE_NAME);
	}

	private boolean isCardTransfer(String receiverSubType)
	{
		return Constants.VISA_CARD_RECEIVER_SUB_TYPE_ATTRIBUTE_NAME.equals(receiverSubType) || Constants.MASTERCARD_CARD_RECEIVER_SUB_TYPE_ATTRIBUTE_NAME.equals(receiverSubType) ||
				Constants.OUR_PHONE_RECEIVER_SUB_TYPE_ATTRIBUTE_NAME.equals(receiverSubType) || Constants.OUR_CARD_RECEIVER_SUB_TYPE_ATTRIBUTE_NAME.equals(receiverSubType);
	}

	public void setTariffPlanESB(String tariffPlanESB)
	{

	}
}
