package com.rssl.phizic.business.documents.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.business.resources.external.comparator.CardAmountComparator;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.bankroll.AccountState;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.InputSumType;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.longoffer.SumType;
import com.rssl.phizic.gate.payments.autosubscriptions.AutoSubscriptionClaim;
import com.rssl.phizic.gate.payments.longoffer.AutoSubscriptionDetailInfo;
import com.rssl.phizic.gate.payments.longoffer.EditCardToAccountLongOffer;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.MockHelper;
import org.w3c.dom.Document;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * @author vagin
 * @ created 13.10.14
 * @ $Author$
 * @ $Revision$
 * Заявка на редактирование копилки.
 */
public class EditMoneyBoxClaim extends CreateMoneyBoxPayment implements AutoSubscriptionClaim
{
	public static final String AUTO_SUBSCRIPTION_NUMBER = "auto-sub-number";

	public Class<? extends GateDocument> getType()
	{
		return EditCardToAccountLongOffer.class;
	}

	public Calendar getUpdateDate()
	{
		return getAdmissionDate();
	}

	public void initialize(Document document, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		super.initialize(document, messageCollector);

		AutoSubscriptionLink subscriptionLink = findAutoSubscriptionLink();
		try
		{
			storeAutoSubscriptionData(subscriptionLink);
		}
		catch (BusinessLogicException e)
		{
			throw new DocumentLogicException(e);
		}
		storeCardsInfo(getChargeOffResourceType(), getDestinationResourceType(), InnerUpdateState.INIT, messageCollector);
		storeDestinationCurrencyInfo(getDestinationResourceType(), InnerUpdateState.INIT, messageCollector);

		PaymentAbilityERL chargeOffResourceLink = getChargeOffResourceLink();
		if (chargeOffResourceLink == null || !chargeOffResourceLink.getShowInSystem())
		{
			processCard(messageCollector);
			chargeOffResourceLink = getChargeOffResourceLink();
		}
		if (chargeOffResourceLink != null)
		{
			//Проверяем, не арестована ли карта
			Card card = ((CardLink)chargeOffResourceLink).getCard();
			if (card.getCardAccountState() == AccountState.ARRESTED)
			{
				throw new DocumentException("Вы не можете отредактировать копилку, так как в ней используется арестованная карта.");
			}
		}
		ExternalResourceLink destinationResourceLink = getDestinationResourceLink();
		if (destinationResourceLink == null || !destinationResourceLink.getShowInSystem())
		{
			processAccount(messageCollector);
		}
	}

	protected AutoSubscriptionLink findAutoSubscriptionLink() throws DocumentException, DocumentLogicException
	{
		try
		{
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			return personData.getAutoSubscriptionLink(Long.parseLong(getNullSaveAttributeStringValue(AUTO_SUBSCRIPTION_NUMBER)));
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

	protected void storeAutoSubscriptionData(AutoSubscriptionLink link) throws DocumentException, DocumentLogicException, BusinessLogicException
	{
		setNullSaveAttributeEnumValue(LONG_OFFER_SUM_TYPE_ATTRIBUTE_NAME, link.getSumType());
		setNullSaveAttributeStringValue(MONEY_BOX_NAME_FIELD_NAME, link.getFriendlyName());
		setNullSaveAttributeStringValue(SUM_TYPE_FIELD_NAME, link.getSumType().name());
		//сохраняем номер автоплатежа, пришедший от autoPay.
		setNullSaveAttributeStringValue(AUTOPAY_NUMBER_ATTRIBUTE_NAME, link.getValue().getAutopayNumber());

		setExecutionEventType(link.getValue().getExecutionEventType());

		if (link.getValue().getPercent() != null)
			setNullSaveAttributeIntegerValue(LONG_OFFER_PERCENT_ATTRIBUTE_NAME, link.getValue().getPercent().intValue());

		//для подписок(копилок) по процентам сумма хранитсья в детальной инфе как максимальная в месяц.
		if(link.getSumType() == SumType.PERCENT_BY_ANY_RECEIPT || link.getSumType() == SumType.PERCENT_BY_DEBIT)
		{
			setChargeOffAmount(link.getAutoSubscriptionInfo().getMaxSumWritePerMonth());
		}
		else
		{
			setChargeOffAmount(link.getValue().getAmount());
			setNullSaveAttributeCalendarValue(LONG_OFFER_START_DATE_ATTRIBUTE_NAME, link.getPayDay());
		}

		storePaymentData(link);
	}

	protected void storePaymentData(AutoSubscriptionLink link) throws DocumentException, DocumentLogicException
	{
		try
		{
			AutoSubscriptionDetailInfo detailInfo = link.getAutoSubscriptionInfo();
			setChargeOffResourceType(ResourceType.CARD);
			setNullSaveAttributeStringValue(RECEIVER_RESOURCE_TYPE_ATTRIBUTE_NAME, ResourceType.ACCOUNT.getResourceLinkClass().getName());
			setNumber(detailInfo.getNumber());
			setOffice(detailInfo.getOffice());
			setChannelType(detailInfo.getChannelType());
			setLongOffer(true);

			setReceiverAccount(link.getValue().getAccountNumber());
			setChargeOffAccount(link.getValue().getCardNumber());

			setInputSumType(InputSumType.CHARGEOFF);
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new DocumentLogicException(e);
		}
	}

	@Override
	protected Calendar getChargeOffCardExpireDate(ResourceType chargeOffResourceType, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		if (chargeOffResourceType == ResourceType.CARD)
		{
			CardLink cardLink = receiveCardLinkByCardNumber(getChargeOffCard(), messageCollector);
			return cardLink == null ? null : cardLink.getCard().getExpireDate();
		}
		throw new IllegalStateException("Неверный тип источника списания " + chargeOffResourceType);
	}

	@Override
	protected CardLink receiveCardLinkByCardNumber(String cardNumber, MessageCollector messageCollector) throws DocumentLogicException, DocumentException
	{
		try
		{
			BusinessDocumentOwner documentOwner = getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
			CardLink cardLink = externalResourceService.findLinkByNumber(documentOwner.getLogin(), ResourceType.CARD, cardNumber);
			if (cardLink == null)
			{
				throw new DocumentLogicException("Невозможно получить информацию по карте " + MaskUtil.getCutCardNumber(cardNumber));
			}

			if (!LinkHelper.isVisibleInChannel(cardLink))
			{
				cardLink = processCard(messageCollector);
			}
			if (cardLink == null)
				return null;
			Card card = cardLink.getCard();
			if (MockHelper.isMockObject(card))
			{
				throw new DocumentLogicException("Невозможно получить информацию по карте " + MaskUtil.getCutCardNumber(cardNumber));
			}
			return cardLink;
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	private CardLink processCard(MessageCollector messageCollector) throws DocumentLogicException, DocumentException
	{
		try
		{
			BusinessDocumentOwner documentOwner = getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");

			List<CardLink> links = externalResourceService.getLinks(documentOwner.getLogin(), CardLink.class);
			Collections.sort(links, new CardAmountComparator());
			int count = 0;
			CardLink resultCardLink = null;
			for (CardLink cardLink: links)
			{
				if (cardLink.getCard() == null || !DocumentHelper.isCardForMoneyBoxMatched(cardLink))
				{
					continue;
				}
				count++;
				if (LinkHelper.isVisibleInChannel(cardLink))
				{
					resultCardLink = cardLink;
					break;
				}
			}
			if (resultCardLink != null)
			{
				setChargeOffAccount(resultCardLink.getNumber());
				setChargeOffCardExpireDate(resultCardLink.getCard().getExpireDate());
				if (messageCollector != null)
					messageCollector.addMessage("Обратите внимание! Карта списания изменена, так как выбранная Вами карта недоступна в Сбербанк Онлайн.");
				return resultCardLink;
			}
			if (count > 0)
			{
				//если нет доступных карт списания, но есть скрытые
				throw new DocumentLogicException("Вы не можете выполнить данную операцию. Для доступа измените настройки видимости Вашей карты в пункте меню «Настройки» - «Безопасность и доступы» - «Настройка видимости продуктов»");
			}
			setChargeOffAccount(null);
			setChargeOffCardExpireDate(null);
			return null;
		}
		catch (BusinessLogicException e)
		{
			throw new DocumentLogicException(e);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	private AccountLink processAccount(MessageCollector messageCollector) throws DocumentLogicException, DocumentException
	{
		try
		{
			BusinessDocumentOwner documentOwner = getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
			AccountLink resultAccountLink = null;
			List<AccountLink> links = externalResourceService.getLinks(documentOwner.getLogin(), AccountLink.class);
			int count = 0;
			for (AccountLink accountLink: links)
			{
				if (accountLink.getAccount() == null || !DocumentHelper.isAccountForMoneyBoxMatched(accountLink))
				{
					continue;
				}
				count++;
				if (LinkHelper.isVisibleInChannel(accountLink))
				{
					resultAccountLink = accountLink;
					break;
				}
			}
			if (resultAccountLink != null)
			{
				setReceiverAccount(resultAccountLink.getNumber());
				setDestinationCurrency(resultAccountLink.getCurrency());
				if (messageCollector != null)
					messageCollector.addMessage("Обратите внимание! Счет зачисления изменен, так как выбранный Вами счет недоступен в Сбербанк Онлайн.");
				return resultAccountLink;
			}
			if (count > 0)
			{
				//если нет доступных счетов зачисления, но есть скрытые
				throw new DocumentLogicException("Вы не можете выполнить данную операцию. Для доступа измените настройки видимости Вашего счета зачисления в пункте меню «Настройки» - «Безопасность и доступы» - «Настройка видимости продуктов»");
			}
			setReceiverAccount(null);
			setDestinationCurrency(null);
			return null;
		}
		catch (BusinessLogicException e)
		{
			throw new DocumentLogicException(e);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	public String getAutoSubscriptionNumber()
	{
		return getNullSaveAttributeStringValue(AUTO_SUBSCRIPTION_NUMBER);
	}

	public FormType getFormType()
	{
		return FormType.EDIT_CARD_TO_ACCOUNT_AUTO_SUBSCRIPTION_CLAIM;
	}
}
