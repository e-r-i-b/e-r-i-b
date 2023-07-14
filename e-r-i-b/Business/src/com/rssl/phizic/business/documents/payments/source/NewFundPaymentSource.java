package com.rssl.phizic.business.documents.payments.source;

import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.MetadataCache;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.fund.sender.FundSenderResponse;
import com.rssl.phizic.business.resources.external.ActiveCardFilter;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author osminin
 * @ created 22.12.14
 * @ $Author$
 * @ $Revision$
 *
 * Создание перевода клиенту сбербанка на карту в рамках краудгифтинга
 */
public class NewFundPaymentSource extends NewDocumentSource
{
	/**
	 * ctor
	 * @param response входящий запрос на сборк средств
	 * @param bayAmount сумма перевода
	 * @param message сообщение инициатору
	 * @param resourceLinkId карта списания
	 * @param isFundPayment привязан ли платеж к краудгифтингу
	 */
	public NewFundPaymentSource(FundSenderResponse response, BigDecimal bayAmount, String message, Long resourceLinkId, boolean isFundPayment) throws BusinessException, BusinessLogicException
	{
		if (response == null)
		{
			throw new IllegalArgumentException("Входящий запрос на сбор средств не может быть null.");
		}
		if (bayAmount == null)
		{
			throw new IllegalArgumentException("Сумма перевода не может быть null.");
		}
		if (resourceLinkId == null)
		{
			throw new IllegalArgumentException("Карта списания не может быть null.");
		}

		document = createFundPayment(response, bayAmount, message, resourceLinkId, isFundPayment);
		metadata = MetadataCache.getExtendedMetadata(document);
		initDocument(CreationType.mobile, CreationSourceType.copy);

		// недостающие данные
		RurPayment payment = (RurPayment) document;
		payment.setOffice(payment.getDepartment());
	}

	private BusinessDocument createFundPayment(FundSenderResponse response, BigDecimal bayAmount, String message, Long resourceLinkId, boolean isFundPayment) throws BusinessException, BusinessLogicException
	{
		Metadata basicMetadata = MetadataCache.getBasicMetadata(FormConstants.RUR_PAYMENT_FORM);
		FieldValuesSource source = createValuesSource(response, message, resourceLinkId, isFundPayment);
		RurPayment payment = (RurPayment) documentService.createDocument(basicMetadata, source, null);

		updatePayment(payment, bayAmount);
		return payment;
	}

	private FieldValuesSource createValuesSource(FundSenderResponse response, String message, Long resourceLinkId, boolean isFundPayment) throws BusinessException, BusinessLogicException
	{
		Map<String, String> fieldsMap = new HashMap<String, String>();

		fieldsMap.put("isFundPayment", Boolean.toString(isFundPayment));
		fieldsMap.put("fundResponseId", response.getExternalId());
		fieldsMap.put("messageToReceiver", message);

		fieldsMap.putAll(getCardFields(resourceLinkId));

		return new MapValuesSource(fieldsMap);
	}

	private Map<? extends String, ? extends String> getCardFields(Long resourceLinkId) throws BusinessLogicException, BusinessException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		CardLink cardLink = personData.getCard(resourceLinkId);

		if (!(new ActiveCardFilter().accept(cardLink.getCard())))
		{
			throw new BusinessLogicException("Оплата доступна только с Вашей активной карты.");
		}

		Map<String, String> values = new HashMap<String, String>();
		values.put("fromAccountSelect", cardLink.getNumber());
		values.put("fromAccountType", cardLink.getValue().getDescription());
		values.put("fromAccountName", cardLink.getName());
		values.put("fromResourceCurrency", cardLink.getCurrency().getCode());
		values.put("fromResourceType", cardLink.getClass().getName());
		values.put("fromResourceLink", cardLink.getCode());

		return values;
	}

	private void updatePayment(RurPayment payment, BigDecimal bayAmount) throws BusinessException
	{
		payment.setState(new State("INITIAL"));
		payment.setDestinationAmount(new Money(bayAmount, MoneyUtil.getNationalCurrency()));
		payment.setReceiverSubType(RurPayment.OUR_CARD_TRANSFER_TYPE_VALUE);
	}
}
