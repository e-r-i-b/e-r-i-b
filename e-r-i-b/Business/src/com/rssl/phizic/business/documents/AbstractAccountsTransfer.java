package com.rssl.phizic.business.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.MessageDocumentLogicException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

/**
 * @author Krenev
 * @ created 15.10.2008
 * @ $Author$
 * @ $Revision$
 */
public abstract class AbstractAccountsTransfer extends AbstractLongOfferDocument
{
	public static final String DESTINATION_RESOURCE_CURRENCY_ATTRIBUTE_NAME = "to-resource-currency";//валюта приемника зачисления
	public static final String TO_ACCOUNT_NAME_ATTRIBUTE_NAME               = "to-account-name";
	public static final String TO_ACCOUNT_SELECT_ATTRIBUTE_NAME             = "to-account-select";
	public static final String TO_RESOURCE_LINK_CODE_ATTRIBUTE_NAME         = "to-resource-link";

	public static final String RECEIVER_BANK_IS_OUR = "is-our-bank";
	public static final String RECEIVER_ACCOUNT_ATTRIBUTE_NAME = "receiver-account";
	public static final String RECEIVER_SUR_NAME_ATTRIBUTE_NAME = "receiver-surname";
	public static final String RECEIVER_FIRST_NAME_ATTRIBUTE_NAME = "receiver-first-name";
	public static final String RECEIVER_PATR_NAME_ATTRIBUTE_NAME = "receiver-patr-name";
	public static final String RECEIVER_RESOURCE_TYPE_ATTRIBUTE_NAME = "to-resource-type";
	public static final String RECEIVER_RESOURCE_ATTRIBUTE_NAME = "to-resource";
	public static final String IS_RECEIVER_CARD_EXTENAL_ATTRIBUTE_NAME = "is-external-card";
	public static final String RECEIVER_CARD_EXPIRE_DATE_ATTRIBUTE_NAME = "to-card-expire-date";
	public static final String IS_CONVERSION_OPERATION_NAME = "is-conversion";

	private String destinationResource;
	private String receiverName;

	/**
	 * Линк зачисления, соответствующий destinationResource
	 */
	private ExternalResourceLink destinationResourceLink = null;

	/**
	 * @return счет получателя.
	 */
	public String getReceiverAccount()
	{
		return destinationResource;
	}

	/**
	 * Установка счета получателя
	 * @param receiverAccount счет получателя.
	 */
	public void setReceiverAccount(String receiverAccount)
	{
		this.destinationResource = receiverAccount;
		this.destinationResourceLink = null;
	}

	/**
	 * @return Тип источника списания(счет/карта)
	 */
	public ResourceType getDestinationResourceType()
	{
		if (isReceiverCardExternal())
		{
			return ResourceType.EXTERNAL_CARD;
		}
		String type = getNullSaveAttributeStringValue(RECEIVER_RESOURCE_TYPE_ATTRIBUTE_NAME);
		if (StringHelper.isEmpty(type))
		{
			//Для поддержки старых платежей
			//приемник не задан явно. Считаем, что счет.
			return ResourceType.ACCOUNT;
		}
		if (AccountLink.class.getName().equals(type))
		{
			return ResourceType.ACCOUNT;
		}
		if (CardLink.class.getName().equals(type))
		{
			return ResourceType.CARD;
		}
		throw new IllegalStateException("Неизвестный тип приемника зачисления " + type);
	}

	/**
	 * Вернуть ссылку на приёмник зачисления
	 * Внимание: ссылка возвращается по текущему состоянию системы,
	 * т.е. может отсутствовать для старых документов (в данном случае возвращается null)
	 * @return ссылка или null, если линк-на-приёмник зачисления удалён либо номер источника зачисления не указан
	 */
	public ExternalResourceLink getDestinationResourceLink() throws DocumentException
	{
		if (destinationResourceLink != null)
			return destinationResourceLink;

		String destNumber = getReceiverAccount();
		if (StringHelper.isEmpty(destNumber))
			return null;

		ResourceType destResourceType = getDestinationResourceType();
		try
		{
			BusinessDocumentOwner documentOwner = getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
			ExternalResourceLink link = externalResourceService.findLinkByNumber(documentOwner.getLogin(), destResourceType, destNumber);
			// ресурс удалён либо никогда и не существовал
			if (link == null)
				return null;

			destinationResourceLink = link;
			return destinationResourceLink;
		}
		catch (BusinessException e)
		{
			throw new DocumentException("Сбой при получении линка-на-приёмник зачисления", e);
		}
	}


	public void setReceiverCard(String receiverCard)
	{
		destinationResource = receiverCard;	
	}

	/**
	 * @return Карта зачисления.
	 */
	public String getReceiverCard()
	{
		return destinationResource;
	}

	/**
	 * @return дата окончания срока действия карты списания
	 */
	public Calendar getReceiverCardExpireDate()
	{
		return DateHelper.toCalendar((Date) getNullSaveAttributeValue(RECEIVER_CARD_EXPIRE_DATE_ATTRIBUTE_NAME));
	}

	/**
	 * Установить дату окончания срока действия карты списания
	 * @param date дата
	 */
	public void setReceiverCardExpireDate(Calendar date)
	{
		setNullSaveAttributeCalendarValue(RECEIVER_CARD_EXPIRE_DATE_ATTRIBUTE_NAME, date);
	}

	public void setReceiverCardExternal(boolean isReceiverCardExternal)
	{
		setNullSaveAttributeBooleanValue(IS_RECEIVER_CARD_EXTENAL_ATTRIBUTE_NAME, isReceiverCardExternal);
	}

	/**
	 * @return является ли приемник зачисления чужой картой.
	 */
	public boolean isReceiverCardExternal()
	{
		Boolean value = (Boolean) getNullSaveAttributeValue(IS_RECEIVER_CARD_EXTENAL_ATTRIBUTE_NAME);
		if (value == null)
		{
			//Для поддержки старых платежей
			//признак не задан. Считаем, что перевод на счет.
			return false;
		}
		return value;
	}

	public AbstractAccountsTransfer createCopy(Class<? extends BusinessDocument> instanceClass) throws DocumentException, DocumentLogicException
	{
		AbstractAccountsTransfer document = (AbstractAccountsTransfer) super.createCopy(instanceClass);
		document.setReceiverAccount(getReceiverAccount());
		document.setReceiverName(getReceiverName());
		document.setReceiverFirstName(getReceiverFirstName());
		document.setReceiverSurName(getReceiverSurName());
		document.setReceiverPatrName(getReceiverPatrName());
		return document;
	}

	public String getAuthorizeCode()
	{
		return getNullSaveAttributeStringValue(AUTHORIZE_CODE_ATTRIBUTE_NAME);
	}

	public void setAuthorizeCode(String authorizeCode)
	{
		setNullSaveAttributeStringValue(AUTHORIZE_CODE_ATTRIBUTE_NAME, authorizeCode);
	}

	public Calendar getAuthorizeDate()
	{
		return getNullSaveAttributeDateTimeValue(AUTHORIZE_DATE_ATTRIBUTE_NAME);
	}

	public void setAuthorizeDate(Calendar authorizeDate)
	{
		setNullSaveAttributeDateTimeValue(AUTHORIZE_DATE_ATTRIBUTE_NAME, authorizeDate);
	}

	/**
	 * @return валюта приемника зачисления
	 */
	public Currency getDestinationCurrency() throws GateException
	{
		String currencyCode = getNullSaveAttributeStringValue(DESTINATION_RESOURCE_CURRENCY_ATTRIBUTE_NAME);
		if (currencyCode == null)
		{
			return null;
		}
		return findCurrencyByISOCode(currencyCode);
	}

	/**
	 * Установить валюту приемника зачисления
	 * @param currency валюта
	 */
	protected void setDestinationCurrency(Currency currency)
	{
		setNullSaveAttributeStringValue(DESTINATION_RESOURCE_CURRENCY_ATTRIBUTE_NAME, currency.getCode());
	}

	public String getToAccountName()
	{
		return getNullSaveAttributeStringValue(TO_ACCOUNT_NAME_ATTRIBUTE_NAME);
	}

	public Document convertToDom() throws DocumentException
	{
		Document document = super.convertToDom();
		Element root = document.getDocumentElement();
		appendNullSaveString(root, RECEIVER_ACCOUNT_ATTRIBUTE_NAME, getReceiverAccount());
		appendNullSaveString(root, RECEIVER_NAME_ATTRIBUTE_NAME, getReceiverName());
		return document;
	}

	protected void readFromDom(Document document, InnerUpdateState updateState, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		super.readFromDom(document, updateState, messageCollector);
		Element root = document.getDocumentElement();

		if(XmlHelper.tagTest(RECEIVER_ACCOUNT_ATTRIBUTE_NAME, root))
			setReceiverAccount(XmlHelper.getSimpleElementValue(root, RECEIVER_ACCOUNT_ATTRIBUTE_NAME));

		ResourceType destinationResourceType = getDestinationResourceType();
		ResourceType chargeOffResourceType = getChargeOffResourceType();

		storeCardsInfo(chargeOffResourceType, destinationResourceType, updateState, messageCollector);
		storeDestinationCurrencyInfo(destinationResourceType, updateState, messageCollector);
	}

	/**
	 * Сохранить информацию о валюте приемника зачисления.
	 * @param destinationResourceType тип источника зачисления
	 */
	protected void storeDestinationCurrencyInfo(ResourceType destinationResourceType, InnerUpdateState updateState, MessageCollector messageCollector) throws DocumentLogicException, DocumentException
	{
		try
		{
			if (StringHelper.isEmpty(destinationResource))
			{
				return;
			}
			Currency currency = null;
			switch (destinationResourceType)
			{
				case CARD:
				case ACCOUNT:
				case IM_ACCOUNT:
					ExternalResourceLink link = getDestinationResourceLink();
					if (link == null)
						throw new DocumentException("Не найден линк-на-приёмник зачисления типа " + getDestinationResourceType());
					currency = link.getCurrency();
					break;
				case EXTERNAL_CARD:
				{
					if(isOurBank())
					{
						Card card = receiveDestinationCard(messageCollector);
						currency = card.getCurrency();
					}
					// для перевода на карту в другой банк валюта всегда рубли
					else
					{
						currency = getNationalCurrency();
					}
					break;
				}
				case EXTERNAL_ACCOUNT:
				{
					//Валюту счета определяем  из самого счета -  это 6-8 позиция в структуре счета.
					String currencyCode = getReceiverAccount().substring(5, 8);
					currency = findCurrencyByNumericCode(currencyCode);
					break;
				}
			}
			if (currency == null)
			{
				throw new DocumentLogicException("Невозможно определить валюту счета/карты зачисления");
			}
			setDestinationCurrency(currency);
		}
		catch (DocumentLogicException e)
		{
			// при инициализации идем дальше
			if(updateState != InnerUpdateState.INIT)
				throw e;

			log.warn(e);
		}
		catch (DocumentException e)
		{
			// при инициализации идем дальше
			if(updateState != InnerUpdateState.INIT)
				throw e;

			log.warn(e);
		}
	}

	/**
	 * Сохранить доп информацию о картах(при необходимости)
	 * @param chargeOffResourceType тип источника списания
	 * @param destinationResourceType тип источника зачисления
	 * @throws DocumentLogicException
	 * @throws DocumentException
	 */
	protected void storeCardsInfo(ResourceType chargeOffResourceType, ResourceType destinationResourceType, InnerUpdateState updateState, MessageCollector messageCollector) throws DocumentLogicException, DocumentException
	{
		if (chargeOffResourceType == ResourceType.CARD)
		{
			try
			{
				//Сохраняем инфу о сроке действия карты списания
				Card chargeOffCard = receiveChargeOffCard(messageCollector);
				setChargeOffCardExpireDate(chargeOffCard.getExpireDate());
			}
			catch (DocumentLogicException e)
			{
				// при инициализации идем дальше
				if(updateState != InnerUpdateState.INIT)
					throw e;

				log.warn(e);
			}
		}
		if  (destinationResourceType == ResourceType.CARD || destinationResourceType == ResourceType.EXTERNAL_CARD)
		{
			try
			{
				//Сохраняем инфу о сроке действия карты зачисления
				Card destinationCard = receiveDestinationCard(messageCollector);
				setReceiverCardExpireDate(destinationCard == null ? null: destinationCard.getExpireDate());
			}
			catch (DocumentLogicException e)
			{
				// при инициализации идем дальше
				if(updateState != InnerUpdateState.INIT)
					throw e;

				log.warn(e);
			}
		}
	}

	/**
	 * Получить дату закрытия карты
	 * @param destinationResourceType тип источника зачисления
	 * @return дата закрытия
	 * @throws DocumentException
	 * @throws DocumentLogicException
	 */
	protected Calendar getDestinationCardExpireDate(ResourceType destinationResourceType, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		if (destinationResourceType == ResourceType.CARD)
		{
			return receiveCardLinkByCardNumber(getReceiverCard(), messageCollector).getExpireDate();
		}
		if (destinationResourceType == ResourceType.EXTERNAL_CARD)
		{
			// если не внутрибанковкий перевод, то карты нет
			if(!isOurBank())
				return null;

			Card card = receiveExternalCard(getReceiverCard());
			return card != null? card.getExpireDate() : null;
		}
		throw new IllegalStateException("Неверный тип приемника зачисления " + destinationResourceType);
	}

	/**
	 * получить карту зачисления
	 * @throws DocumentException
	 * @throws DocumentLogicException
	 * @return карта зачисления
	 */
	protected Card receiveDestinationCard(MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		String receiverCard = getReceiverCard();
		ResourceType destinationResourceType = getDestinationResourceType();
		if (destinationResourceType == ResourceType.CARD)
		{
			return receiveCardLinkByCardNumber(receiverCard, messageCollector).getCard();
		}
		if (destinationResourceType == ResourceType.EXTERNAL_CARD)
		{
			return isOurBank() ? receiveExternalCard(receiverCard) : null;
		}
		throw new IllegalStateException("Неверный тип приемника зачисления " + destinationResourceType);
	}

	/**
	 * получить СКС карты зачисления
	 * @throws DocumentException
	 * @throws DocumentLogicException
	 * @return СКС карты зачисления
	 */
	protected Account receiveDestinationCardAccount(MessageCollector messageCollector) throws DocumentLogicException, DocumentException
	{
		String receiverCard = getReceiverCard();
		ResourceType destinationResourceType = getDestinationResourceType();
		if (destinationResourceType == ResourceType.CARD)
		{
			Account cardAccount = receiveCardLinkByCardNumber(receiverCard, messageCollector).getCardAccount();
			if (cardAccount != null)
				return cardAccount;

			throw new MessageDocumentLogicException("Для данной карты операция пополнения невозможна.");
		}
		if (destinationResourceType == ResourceType.EXTERNAL_CARD)
		{
			return isOurBank() ? receiveExternalCardAccount(receiverCard) : null;
		}
		throw new IllegalStateException("Неверный тип приемника зачисления " + destinationResourceType);
	}

	/**
	 * Получить карту через банкролл
	 * @param cardNumber номер карты
	 * @return карта
	 */
	protected Card getCardIfOurBank(String cardNumber) throws DocumentLogicException, DocumentException
	{
		if (StringHelper.isEmpty(cardNumber))
		{
			return null;
		}

		BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
		ActivePerson activePerson = null;
		try
		{
			BusinessDocumentOwner documentOwner = getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
			activePerson = documentOwner.getPerson();
		}
		catch (BusinessException e)
		{
			throw new DocumentLogicException("Невозможно получить информацию о владельце документа");
		}
		if (activePerson == null)
		{
			throw new DocumentLogicException("Невозможно получить информацию о владельце документа");
		}
		try
		{
			return GroupResultHelper.getOneResult(bankrollService.getCardByNumber(activePerson.asClient(), new Pair<String, Office>(cardNumber, null)));
		}
		catch (LogicException e)
		{
			throw new DocumentLogicException(e);
		}
		catch (SystemException e)
		{
			throw new DocumentException(e);
		}
	}

	/**
	 * Получить карту через банкролл
	 * @param cardNumber номер карты
	 * @return карта
	 */
	public Card receiveExternalCard(String cardNumber) throws DocumentLogicException, DocumentException
	{
		Card card = getCardIfOurBank(cardNumber);
		if (card == null)
		{
			throw new DocumentLogicException("Не найдена информация по карте " + MaskUtil.getCutCardNumber(cardNumber));
		}
		return card;
	}

	//Является ли банк "нашим", т.е. это внутрибанковский перевод
	public boolean isOurBank()
	{
		String isOurBank = getNullSaveAttributeStringValue(RECEIVER_BANK_IS_OUR);
		return Boolean.parseBoolean(isOurBank);
	}

	public void setOurBank(boolean isOur)
	{
		setNullSaveAttributeBooleanValue(RECEIVER_BANK_IS_OUR, isOur);
	}

	/**
	 * Получить СКС карты через банкролл
	 * @param cardNumber номер карты
	 * @return СКС карты
	 */
	protected Account receiveExternalCardAccount(String cardNumber) throws DocumentLogicException, DocumentException
	{
		try
		{
			Card card = receiveExternalCard(cardNumber);
			BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
			Account account = GroupResultHelper.getOneResult(bankrollService.getCardPrimaryAccount(card));
			if (account == null)
				throw new DocumentLogicException("Не найден карточный счет для карты №" + MaskUtil.getCutCardNumber(cardNumber));
			return account;
		}
		catch (LogicException e)
		{
			throw new DocumentLogicException(e);
		}
		catch (SystemException e)
		{
			throw new DocumentException(e);
		}
	}

	/**
	 * Сравнить документы по ключевым полям. Если у переданного документа не заполнено ключевое поле,
	 *  а у текущего заполнено, документы отличаются.
	 * @param template шаблон, на основе которого был создан документ
	 * @return true - ключевые поля у документов одинаковые, false - ключевые поля разные
	 */
	public boolean equalsKeyEssentials(TemplateDocument template) throws BusinessException
	{
		if (!super.equalsKeyEssentials(template))
		{
			return false;
		}

		FormType formType = template.getFormType();
		if (FormType.INDIVIDUAL_TRANSFER == formType || FormType.INDIVIDUAL_TRANSFER_NEW == formType)
		{
			boolean temp = StringHelper.equalsNullIgnore(getReceiverSurName(), template.getReceiverSurName())
						&& StringHelper.equalsNullIgnore(getReceiverFirstName(), template.getReceiverFirstName());
			if (!temp)
			{
				return false;
			}
		}

		if (FormType.JURIDICAL_TRANSFER == formType)
		{
			boolean temp = StringHelper.equalsNullIgnore(getReceiverName(), template.getReceiverName());
			if (!temp)
			{
				return false;
			}
		}

		return StringHelper.equalsNullIgnore(getReceiverAccount(), template.getReceiverAccount())
			&& StringHelper.equalsNullIgnore(getReceiverCard(), template.getReceiverCard());
	}

	public String getReceiverSurName()
	{
		return getNullSaveAttributeStringValue(RECEIVER_SUR_NAME_ATTRIBUTE_NAME);
	}

	public void setReceiverSurName(String value)
	{
		setAttributeValue("string", RECEIVER_SUR_NAME_ATTRIBUTE_NAME, value);
	}

	public String getReceiverFirstName()
	{
		return getNullSaveAttributeStringValue(RECEIVER_FIRST_NAME_ATTRIBUTE_NAME);
	}

	public void setReceiverFirstName(String value)
	{
		setAttributeValue("string", RECEIVER_FIRST_NAME_ATTRIBUTE_NAME, value);
	}

	public String getReceiverPatrName()
	{
		return getNullSaveAttributeStringValue(RECEIVER_PATR_NAME_ATTRIBUTE_NAME);
	}

	public void setReceiverPatrName(String value)
	{
		setAttributeValue("string", RECEIVER_PATR_NAME_ATTRIBUTE_NAME, value);
	}

	/**
	 * @return конверсионная операция или нет.
	 */
	public boolean getIsConversionOperation()
	{
		return Boolean.parseBoolean(getNullSaveAttributeStringValue(IS_CONVERSION_OPERATION_NAME));
	}

	/**
	 * @param conversion признак конверсионной операции
	 */
	public void setIsConversionOperation(boolean conversion)
	{
		setNullSaveAttributeBooleanValue(IS_CONVERSION_OPERATION_NAME, conversion);
	}

	public Set<ExternalResourceLink> getLinks() throws DocumentException
	{
		Set<ExternalResourceLink> links = super.getLinks();
		ExternalResourceLink link = getDestinationResourceLink();
		if (link != null)
		{
			links.add(link);
		}
		return links;
	}
}
