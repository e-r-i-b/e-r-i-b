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
 * Объектное представление первода между своими счетами/картами включая ДП по ним.
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
				throw new UnsupportedOperationException("Создание длительного поручения для перевода с карты на карту не поддерживается.");

			return InternalCardsTransfer.class;
		}

		if (chargeOffResourceType == ResourceType.CARD && destinationResourceType == ResourceType.ACCOUNT)
		{
			return CardToAccountTransfer.class;
		}

		if (chargeOffResourceType == ResourceType.ACCOUNT && destinationResourceType == ResourceType.CARD)
		{
			if (isLongOffer())
				throw new UnsupportedOperationException("Создание длительного поручения для перевода со вклада на карту не поддерживается.");
			return AccountToCardTransfer.class;
		}

		if (chargeOffResourceType == ResourceType.ACCOUNT && destinationResourceType == ResourceType.ACCOUNT)
		{
			if (isLongOffer())
				return ClientAccountsLongOffer.class;
			return ClientAccountsTransfer.class;
		}

		// ОМС -> счёт
		if (chargeOffResourceType == ResourceType.IM_ACCOUNT && destinationResourceType == ResourceType.ACCOUNT)
		{
			if (isLongOffer())
				throw new UnsupportedOperationException("Создание длительного поручения для операций с ОМС не поддерживается.");
			return IMAToAccountTransfer.class;
		}

		// счёт -> ОМС
		if (chargeOffResourceType == ResourceType.ACCOUNT && destinationResourceType == ResourceType.IM_ACCOUNT)
		{
			if (isLongOffer())
				throw new UnsupportedOperationException("Создание длительного поручения для операций с ОМС не поддерживается.");
			return AccountToIMATransfer.class;
		}

		// ОМС -> карта
		if (chargeOffResourceType == ResourceType.IM_ACCOUNT && destinationResourceType == ResourceType.CARD)
		{
			if (isLongOffer())
				throw new UnsupportedOperationException("Создание длительного поручения для операций с ОМС не поддерживается.");
			return IMAToCardTransfer.class;
		}

		// карта -> ОМС
		if (chargeOffResourceType == ResourceType.CARD && destinationResourceType == ResourceType.IM_ACCOUNT)
		{
			if (isLongOffer())
				throw new UnsupportedOperationException("Создание длительного поручения для операций с ОМС не поддерживается.");
			return CardToIMATransfer.class;
		}

		throw new IllegalStateException("Невозмжно определить тип документа");
	}

	public FormType getFormType()
	{
		if (FormType.IMA_PAYMENT.getName().equals(getFormName()))
		{
			return FormType.IMA_PAYMENT;
		}
		//обмен валют определяем по имени формы платежа.
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
				//Сохраняем инфу о сроке действия
				setChargeOffCardExpireDate(getChargeOffCardExpireDate(chargeOffResourceType, messageCollector));
			}
			catch (DocumentLogicException e)
			{
				if(updateState != InnerUpdateState.INIT)
					throw e;
				// при инициализации идем дальше, обновим нормальными данными в свое время
				log.warn(e);
			}
		}
		if (destinationResourceType == ResourceType.CARD)
		{
			try
			{
				//Сохраняем инфу о сроке действия
				setReceiverCardExpireDate(getDestinationCardExpireDate(destinationResourceType, messageCollector));
			}
			catch (DocumentLogicException e)
			{
				if(updateState != InnerUpdateState.INIT)
					throw e;
				// при инициализации идем дальше, обновим нормальными данными в свое время
				log.warn(e);
			}
		}
	}

	protected boolean needRates() throws DocumentException
	{
		//курсы нужно пробовать получать:
		// 1) если это перевод со счета(карточные курсы у нас отсутствуют)
		// 2) это конверсионная операция
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
		//считаем два InternalTransfer одинаковыми
		//по причинам описанным в запросе ENH046724

		//но если у документов разные счет списания или счет зачисления, то говорим что документ необходимо подтверждать по смс.
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
	 * Установка идентификатора операции
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
	 * Установка даты и времени передачи сообщения
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
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
			return TemplateHelper.addCounter(new ClientImpl(documentOwner.getPerson()), getDefaultTemplateName(metadata), new DefaultNamingStrategy());
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
	}
}
