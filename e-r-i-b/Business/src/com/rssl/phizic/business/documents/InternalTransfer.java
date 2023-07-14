package com.rssl.phizic.business.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.TypeOfPayment;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.MetadataCache;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.documents.payments.ExchangeCurrencyTransferBase;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.TemplateHelper;
import com.rssl.phizic.business.persons.clients.ClientImpl;
import com.rssl.phizic.business.resources.external.IMAccountLink;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.payments.*;
import com.rssl.phizic.gate.payments.longoffer.ClientAccountsLongOffer;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.counter.DefaultNamingStrategy;

import java.util.Calendar;

/**
 * @author Krenev
 * @ created 15.10.2008
 * @ $Author$
 * @ $Revision$
 * ��������� ������������� ������� ����� ������ �������/������� ������� �� �� ���.
 */
public class InternalTransfer extends ExchangeCurrencyTransferBase implements ClientAccountsTransfer, InternalCardsTransfer, CardToAccountTransfer, AccountToCardTransfer, LongOffer, IMAToAccountTransfer, AccountToIMATransfer, IMAToCardTransfer, CardToIMATransfer
{
	public Class<? extends GateDocument> getType()
	{
		ResourceType chargeOffResourceType = getChargeOffResourceType();
		ResourceType destinationResourceType = getDestinationResourceType();
		if (chargeOffResourceType == ResourceType.NULL)
		{
			return null;
		}
		
		if (chargeOffResourceType == ResourceType.CARD && destinationResourceType == ResourceType.CARD)
		{
			if (isLongOffer())
				throw new UnsupportedOperationException("�������� ����������� ��������� ��� �������� � ����� �� ����� �� ��������������.");

			return InternalCardsTransfer.class;
		}

		if (chargeOffResourceType == ResourceType.CARD && destinationResourceType == ResourceType.ACCOUNT)
		{
			return CardToAccountTransfer.class;
		}

		if (chargeOffResourceType == ResourceType.ACCOUNT && destinationResourceType == ResourceType.CARD)
		{
			if (isLongOffer())
				throw new UnsupportedOperationException("�������� ����������� ��������� ��� �������� �� ������ �� ����� �� ��������������.");
			return AccountToCardTransfer.class;
		}

		if (chargeOffResourceType == ResourceType.ACCOUNT && destinationResourceType == ResourceType.ACCOUNT)
		{
			if (isLongOffer())
				return ClientAccountsLongOffer.class;
			return ClientAccountsTransfer.class;
		}

		// ��� -> ����
		if (chargeOffResourceType == ResourceType.IM_ACCOUNT && destinationResourceType == ResourceType.ACCOUNT)
		{
			if (isLongOffer())
				throw new UnsupportedOperationException("�������� ����������� ��������� ��� �������� � ��� �� ��������������.");
			return IMAToAccountTransfer.class;
		}

		// ���� -> ���
		if (chargeOffResourceType == ResourceType.ACCOUNT && destinationResourceType == ResourceType.IM_ACCOUNT)
		{
			if (isLongOffer())
				throw new UnsupportedOperationException("�������� ����������� ��������� ��� �������� � ��� �� ��������������.");
			return AccountToIMATransfer.class;
		}

		// ��� -> �����
		if (chargeOffResourceType == ResourceType.IM_ACCOUNT && destinationResourceType == ResourceType.CARD)
		{
			if (isLongOffer())
				throw new UnsupportedOperationException("�������� ����������� ��������� ��� �������� � ��� �� ��������������.");
			return IMAToCardTransfer.class;
		}

		// ����� -> ���
		if (chargeOffResourceType == ResourceType.CARD && destinationResourceType == ResourceType.IM_ACCOUNT)
		{
			if (isLongOffer())
				throw new UnsupportedOperationException("�������� ����������� ��������� ��� �������� � ��� �� ��������������.");
			return CardToIMATransfer.class;
		}

		throw new IllegalStateException("��������� ���������� ��� ���������");
	}

	public FormType getFormType()
	{
		if (FormType.IMA_PAYMENT.getName().equals(getFormName()))
		{
			return FormType.IMA_PAYMENT;
		}
		//����� ����� ���������� �� ����� ����� �������.
		if (FormType.CONVERT_CURRENCY_TRANSFER.getName().equals(getFormName()))
		{
			return FormType.CONVERT_CURRENCY_TRANSFER;
		}

		return FormType.INTERNAL_TRANSFER;
	}

	public ResourceType getChargeOffResourceType()
	{
		String type = getNullSaveAttributeStringValue(CHARGE_OFF_RESOURCE_TYPE_ATTRIBUTE_NAME);
		if (!StringHelper.isEmpty(type))
		{
			if (IMAccountLink.class.getName().equals(type))
				return ResourceType.IM_ACCOUNT;
		}
		return super.getChargeOffResourceType();
	}

	public ResourceType getDestinationResourceType()
	{
		String type = getNullSaveAttributeStringValue(RECEIVER_RESOURCE_TYPE_ATTRIBUTE_NAME);
		if (!StringHelper.isEmpty(type))
		{
			if (IMAccountLink.class.getName().equals(type))
				return ResourceType.IM_ACCOUNT;
		}
		return super.getDestinationResourceType();
	}

	protected void storeCardsInfo(ResourceType chargeOffResourceType, ResourceType destinationResourceType, InnerUpdateState updateState, MessageCollector messageCollector) throws DocumentLogicException, DocumentException
	{
		if (chargeOffResourceType == ResourceType.CARD)
		{
			try
			{
				//��������� ���� � ����� ��������
				setChargeOffCardExpireDate(getChargeOffCardExpireDate(chargeOffResourceType, messageCollector));
			}
			catch (DocumentLogicException e)
			{
				if(updateState != InnerUpdateState.INIT)
					throw e;
				// ��� ������������� ���� ������, ������� ����������� ������� � ���� �����
				log.warn(e);
			}
		}
		if (destinationResourceType == ResourceType.CARD)
		{
			try
			{
				//��������� ���� � ����� ��������
				setReceiverCardExpireDate(getDestinationCardExpireDate(destinationResourceType, messageCollector));
			}
			catch (DocumentLogicException e)
			{
				if(updateState != InnerUpdateState.INIT)
					throw e;
				// ��� ������������� ���� ������, ������� ����������� ������� � ���� �����
				log.warn(e);
			}
		}
	}

	protected boolean needRates() throws DocumentException
	{
		//����� ����� ��������� ��������:
		// 1) ���� ��� ������� �� �����(��������� ����� � ��� �����������)
		// 2) ��� ������������� ��������
		switch (getChargeOffResourceType())
		{
			case ACCOUNT:
			case IM_ACCOUNT:
				return isConvertion();
			case CARD:
				return getDestinationResourceType() == ResourceType.IM_ACCOUNT && isConvertion();
			
			default:
				return false;
		}
	}

	public boolean equalsKeyEssentials(TemplateDocument template)
	{
		//������� ��� InternalTransfer �����������
		//�� �������� ��������� � ������� ENH046724

		//�� ���� � ���������� ������ ���� �������� ��� ���� ����������, �� ������� ��� �������� ���������� ������������ �� ���.
		if (template.getType() != ClientAccountsTransfer.class && template.getType() != AccountToCardTransfer.class)
			return true;

		if (!StringHelper.equalsNullIgnore(template.getChargeOffAccount(), getChargeOffAccount()))
			return false;

		if (!StringHelper.equalsNullIgnore(template.getReceiverAccount(), getReceiverAccount()))
			return false;
		return true;
	}

	public ReceiverCardType getReceiverCardType()
	{
		return ReceiverCardType.SB;
	}

	public TypeOfPayment getTypeOfPayment()
	{
		return TypeOfPayment.INTERNAL_PAYMENT_OPERATION;
	}

	public String getOperUId()
	{
		return getNullSaveAttributeStringValue(OPER_UID);
	}

	/**
	 * ��������� �������������� ��������
	 * @param operUid
	 */
	public void setOperUId(String operUid)
	{
		setNullSaveAttributeStringValue(OPER_UID, operUid);
	}

	public Calendar getOperTime()
	{
		return getNullSaveAttributeCalendarValue(OPER_TIME);
	}

	/**
	 * ��������� ���� � ������� �������� ���������
	 * @param operTime
	 */
	public void setOperTime(Calendar operTime)
	{
		setNullSaveAttributeCalendarValue(OPER_TIME, operTime);
	}

	public String getDefaultTemplateName() throws BusinessException, BusinessLogicException
	{
		if (isLongOffer())
		{
			return super.getDefaultTemplateName();
		}

		try
		{
			Metadata metadata = MetadataCache.getBasicMetadata(getFormName());
			BusinessDocumentOwner documentOwner = getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("�������� � �������� ������ �� ��������������");
			return TemplateHelper.addCounter(new ClientImpl(documentOwner.getPerson()), getDefaultTemplateName(metadata), new DefaultNamingStrategy());
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
	}
}
