package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.*;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.payments.TitledLogicException;
import com.rssl.phizic.business.resources.external.AbstractStoredResource;
import com.rssl.phizic.business.resources.external.PaymentAbilityERL;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.MockHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.manager.ext.sbrf.ESBHelper;

/**
 * Базовый хендлер на проверку возможности совершения операции. ограничение на сумму документа(с учетом комиссий):
 * 1) сумма документа не должна превышать остаток на источнике списания.
 * 2) сумма документа не должна превышать максимально допустимую сумму списания(если задана для источника списания)
 * @author Rtischeva
 * @created 16.09.13
 * @ $Author$
 * @ $Revision$
 */
public class DocumentSumValidationHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (ApplicationUtil.isErmbSms())
			return;

		AbstractPaymentDocument doc = (AbstractPaymentDocument) document;

		if (doc instanceof AbstractLongOfferDocument)
		{
			//если длительное поручение/автоплатеж и ввод суммы платежа не нужен
			AbstractLongOfferDocument longOfferDocument = (AbstractLongOfferDocument) doc;
			if (longOfferDocument.isLongOffer() && !longOfferDocument.needInputAmount())
				return;
			//если подписка на автоплатеж(автоплатеж в АС "Автоплатежи" - билинговый автоплатеж через шину) проверять не нужно (BUG037512)
			if (ESBHelper.isAutoSubscriptionPayment(longOfferDocument))
				return;
		}

		if (doc instanceof AccountOpeningClaim)
		{
			AccountOpeningClaim accountOpeningClaim = (AccountOpeningClaim) document;
			if (!accountOpeningClaim.isNeedInitialFee())
				return;
		}

		Money amount = doc.getChargeOffAmount();
		//Если отсуствует сумма списания (при конверсии валюты), это означает, что указана только сумма в валюте зачисления и следовательно проверить непревышаемый остаток нельзя
		if (amount == null)
		{
			if(doc.getDestinationAmount() != null && !compareCurrency(doc))
				return;

			amount = doc.getDestinationAmount();
		}

		Money commission = doc.getCommission();
		if (commission != null)
		{
			amount = amount.add(commission);
		}

		try
		{
			if (StringHelper.isEmpty(doc.getChargeOffAccount()))
				throw new DocumentException("В платёжном документе не указан источник списания. DOC_ID=" + doc.getId());

			PaymentAbilityERL chargeOffResourceLink = doc.getChargeOffResourceLink();
			if (chargeOffResourceLink == null)
				throw new DocumentException("Не найден линк-на-источник списания типа " + doc.getChargeOffResourceType());

			if (chargeOffResourceLink.getValue() instanceof AbstractStoredResource)
				return;

			if (MockHelper.isMockObject(chargeOffResourceLink.getValue()))
				throw new DocumentLogicException("Операция временно недоступна. Повторите попытку позже.");

			checkAmounts(document, amount, chargeOffResourceLink.getRest(), chargeOffResourceLink.getMaxSumWrite(), chargeOffResourceLink.getResourceType());
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new DocumentLogicException(e);
		}
	}

	private boolean compareCurrency(AbstractPaymentDocument doc) throws DocumentException
	{
		try
		{
			return doc.getChargeOffCurrency().compare(doc.getDestinationAmount().getCurrency());
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
	}

	/**
	 * проверить суммы:
	 * 1) сумма документа не должна превышать остаток на источнике списания.
	 * 2) сумма документа не должна превышать максимально допустимую сумму списания(если задана)
	 * @param documentSum сумма документа
	 * @param balance остаток на источнике списания
	 * @param maxSumWrite максимальная сумма списания(может отсутствать)
	 * @param resourceType тип источника списания
	 */
	private void checkAmounts(StateObject document, Money documentSum, Money balance, Money maxSumWrite, ResourceType resourceType) throws BusinessLogicException, BusinessException, DocumentException
	{
		if (documentSum == null)
		{
			throw new IllegalArgumentException("Не задана сумма документа");
		}
		Currency documentCurrency = documentSum.getCurrency();
		//1)проверяем остаток на источнике списания
		if (balance == null)
		{
			throw new IllegalArgumentException("Не задан остаток источника списания для проверки");
		}
		Currency balanceCurrency = balance.getCurrency();
		if (!documentCurrency.compare(balanceCurrency))
		{
			throw new BusinessException("Не совпадают валюты суммы документа(" + documentCurrency.getCode() + ") и остатка на "+ toStringResourceType(resourceType, false) +"(" + balanceCurrency.getCode() + ")");
		}
		if (documentSum.compareTo(balance) > 0)
		{
			if (DocumentHelper.postConfirmCommissionSupport((BusinessDocument) document) && ((AbstractPaymentDocument) document).getCommission() != null)
				throw new TitledLogicException("На счете списния недостаточно средств для перевода с учетом комиссии и перерасчетов. Попробуйте повторить платеж, уменьшив сумму перевода." +
										"<a href='http://www.sberbank.ru/common/img/uploaded/files/pdf/person/bank_cards/Perevody__Tarify.pdf' target='_blank'>Информация о коммиссиях Сбербанка.</a>", "Недостаточно средств на счете для списания");
			String message = getNotEnoughMoneyMessage(document, resourceType);
			throw new BusinessLogicException(message);
		}
		//2)проверяем максимальную сумму списания
		if (maxSumWrite == null)
		{
			return;//сумма не задана. допустимо списание любо суммы
		}
		Currency maxSumWriteCurrency = maxSumWrite.getCurrency();
		if (!documentCurrency.compare(maxSumWriteCurrency))
		{
			throw new BusinessException("Не совпадают валюты суммы документа(" + documentCurrency.getCode() + ") и максимальной суммы списания(" + maxSumWriteCurrency.getCode() + ")");
		}
		if (documentSum.compareTo(maxSumWrite) > 0)
		{
			throw new BusinessLogicException("Остаток на "+ toStringResourceType(resourceType, false) +" после совершения операции не может быть меньше неснижаемого.");
		}
	}

	protected String getNotEnoughMoneyMessage(StateObject document, ResourceType resourceType) throws DocumentException
	{
		AbstractPaymentDocument doc = (AbstractPaymentDocument) document;
		boolean isDepo = document instanceof RurPayment && ((RurPayment) document).isDepoPayment();
		boolean isAirlineReservation = DocumentHelper.isAirlineReservationPayment(doc);
		boolean isAeroexpressReservation = DocumentHelper.isAeroexpressReservationPayment(doc);
		boolean isInvoice = DocumentHelper.isInvoicePayment(doc);
		if(isDepo)
			return "На Вашей карте недостаточно средств для оплаты задолженности. Пожалуйста, выберите другую карту.";
		if(isAirlineReservation)
			return "Сумма списания превышает остаток на карте. Пожалуйста, выберите другую карту для оплаты брони.";
		if(isAeroexpressReservation){
			return "Сумма списания превышает остаток на Вашей карте. Пожалуйста, выберите другую карту для покупки билетов.";
		}
		if(isInvoice)
		{
			return "Сумма списания превышает остаток средств на Вашей карте. Пожалуйста, пополните карту списания.";
		}
		return toStringResourceType(resourceType, true);
	}

	private String toStringResourceType(ResourceType resourceType, boolean useTreatment)
	{
		if (useTreatment)
		{
			String message = new String("Вы ввели сумму, превышающую остаток средств на ");
			switch (resourceType)
			{
				case ACCOUNT:
				{
					message += "Вашем счете. Пожалуйста, введите другую сумму.";
					break;
				}
				case IM_ACCOUNT:
				{
					return "Сумма/масса перевода превышает остаток на счете списания. Пожалуйста, укажите другую сумму/массу списания.";
				}
				default:
				{
					message += "Вашей карте. Пожалуйста, введите другую сумму.";
					break;
				}
			}
			return message;
		}
		else
		{
			return resourceType == ResourceType.CARD ? "карте": "счете";
		}
	}
}
