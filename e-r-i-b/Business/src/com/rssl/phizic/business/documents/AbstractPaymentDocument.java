package com.rssl.phizic.business.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.common.types.*;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.documents.InputSumType;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.MockHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

/**
 * @author Krenev
 * @ created 15.10.2008
 * @ $Author$
 * @ $Revision$
 */
public abstract class AbstractPaymentDocument extends GateExecutableDocument
{
	private static final String NATIONAL_CURRENCY_ATTRIBUTE_NAME = "national-currency";//национальная валюта

	public static final String CHARGE_OFF_RESOURCE_ATTRIBUTE_NAME = "payer-account";
	public static final String CHARGE_OFF_RESOURCE_TYPE_ATTRIBUTE_NAME = "from-resource-type";
	public static final String CHARGE_OFF_RESOURCE_CURRENCY_ATTRIBUTE_NAME = "from-resource-currency";//валюта источника списания
	public static final String CHARGE_OFF_RESOURCE_NAME_ATTRIBUTE_NAME = "from-account-name";
	public static final String CHARGE_OFF_AMOUNT_ATTRIBUTE_NAME = "amount";//сумма списания

	public static final String PAYER_CARD_EXPIRE_DATE_ATTRIBUTE_NAME = "from-card-expire-date";
	public static final String FROM_CARD_DESCRIPTION_ATTRIBUTE_NAME = "from-charge-off-card-description";
	public static final String PAYER_CARD_ACCOUNT_ATTRIBUTE_NAME = "from-card-account";
	public static final String FROM_RESOURCE_LINK_CODE_ATTRIBUTE_NAME = "from-resource-link";
	public static final String PAYER_NAME_ATTRIBUTE_NAME = "payer-name";
	public static final String GROUND_ATTRIBUTE_NAME = "ground";

	public static final String DESTINATION_AMOUNT_ATTRIBUTE_NAME = "destination-amount";//сумма зачисления

	//поле, в котором точное значение суммы(введено пользовтелем)
	public static final String EXACT_AMOUNT_FIELD_ATTRIBUTE_NAME = "exact-amount";
	//точное значение в поле сумма списания
	public static final String CHARGE_OFF_FIELD_EXACT_AMOUNT_ATTRIBUTE_VALUE = "charge-off-field-exact";
	//точное значение в поле сумма зачисления
	public static final String DESTINATION_FIELD_EXACT_AMOUNT_ATTRIBUTE_VALUE = "destination-field-exact";
	//код операции
	public static final String OPERATION_CODE_ATTRIBUTE_NAME = "operation-code";

	protected static final ExternalResourceService externalResourceService = new ExternalResourceService();


	private String chargeOffResource;
	private Money chargeOffAmount;
	private Money destinationAmount;
	private String ground;
	private InputSumType inputSumType;
	private String confirmEmployee;             // сотрудник подтвердивший операцию
	private String receiverName;


	/**
	 * Линк списания, соответствующий сhargeOffResource
	 */
	private PaymentAbilityERL chargeOffResourceLink = null;

	/**
	 * При изменении суммы, она превзошла лимит (используется для шаблонов).
	 */
	private boolean sumIncreasedOverLimit = false;

    /**
     * При оплате по шаблону понадобилось подтвердить платеж одноразовым паролем (изменение суммы больше допустимой кратности, изменение ключевых полей и т.д.)
     */
    private boolean isPaymentFromTemplateNeedConfirm = false;


	/**
	 * @return счет/карта плательщика
	 */
	public String getChargeOffAccount()
	{
		return chargeOffResource;
	}

	/**
	 * Установка счета/карту плательщика
	 * @param chargeOffAccount счет плательщика
	 */
	public void setChargeOffAccount(String chargeOffAccount)
	{
		this.chargeOffResource = chargeOffAccount;
		// не забываем сбросить линк
		this.chargeOffResourceLink = null;
	}

	/**
	 * @return Тип источника списания(счет/карта)
	 */
	public ResourceType getChargeOffResourceType()
	{
		String type = getNullSaveAttributeStringValue(CHARGE_OFF_RESOURCE_TYPE_ATTRIBUTE_NAME);
		if (StringHelper.isEmpty(type))
		{
			return ResourceType.NULL;
		}
		if (AccountLink.class.getName().equals(type))
		{
			return ResourceType.ACCOUNT;
		}
		if (CardLink.class.getName().equals(type))
		{
			return ResourceType.CARD;
		}
		throw new IllegalStateException("Неизвестный тип источника списания " + type);
	}

	/**
	 * Установить параметр "тип ресурса списания"
	 * @param resourceType
	 */
	public void setChargeOffResourceType(String resourceType)
	{
		setAttributeValue("string", AbstractPaymentDocument.CHARGE_OFF_RESOURCE_TYPE_ATTRIBUTE_NAME,  resourceType);
	}

	/**
	 * Установить параметр "тип ресурса списания"
	 * @param resourceType тип источника
	 */
	public void setChargeOffResourceType(ResourceType resourceType)
	{
		setAttributeValue("string", AbstractPaymentDocument.CHARGE_OFF_RESOURCE_TYPE_ATTRIBUTE_NAME,  resourceType.getResourceLinkClass().getName());
	}

	/**
	 * Вернуть ссылку на источник списания
	 * Внимание: ссылка возвращается по текущему состоянию системы,
	 * т.е. может отсутствовать для старых документов (в данном случае возвращается null)
	 * @return ссылка или null, если линк-на-источник списания удалён либо номер источника списания не указан 
	 */
	public PaymentAbilityERL getChargeOffResourceLink() throws DocumentException
	{
		if (chargeOffResourceLink != null)
			return chargeOffResourceLink;

		String chargeOffNumber = getChargeOffAccount();
		if (StringHelper.isEmpty(chargeOffNumber))
			return null;

		ResourceType chargeOffResourceType = getChargeOffResourceType();
		try
		{
			BusinessDocumentOwner documentOwner = getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
			ExternalResourceLink link = externalResourceService.findLinkByNumber(documentOwner.getLogin(), chargeOffResourceType, chargeOffNumber);
			// ресурс удалён либо никогда и не существовал
			if (link == null)
				return null;

			if (!(link instanceof PaymentAbilityERL))
				throw new UnsupportedOperationException("Неизвесный тип источника списания " + chargeOffResourceType);

			chargeOffResourceLink = (PaymentAbilityERL) link;
			return chargeOffResourceLink;
		}
		catch (BusinessException e)
		{
			throw new DocumentException("Сбой при получении линка-на-источник списания", e);
		}
	}

	/**
	 * @return номер карты списания
	 */
	public String getChargeOffCard()
	{
		return chargeOffResource;
	}

	/**
	 * @return дата окончания срока действия карты списания
	 */
	public Calendar getChargeOffCardExpireDate()
	{
		return DateHelper.toCalendar((Date) getNullSaveAttributeValue(PAYER_CARD_EXPIRE_DATE_ATTRIBUTE_NAME));
	}

	/**
	 * Установить дату окончания срока действия карты списания
	 * @param date дата
	 */
	protected void setChargeOffCardExpireDate(Calendar date)
	{
		setNullSaveAttributeCalendarValue(PAYER_CARD_EXPIRE_DATE_ATTRIBUTE_NAME, date);
	}

	/**
	 * @return  Описание катры (Visa Classic, MasterCard, Maestro Cirrus etc)
	 */
	public String getChargeOffCardDescription()
	{
		return getNullSaveAttributeStringValue(FROM_CARD_DESCRIPTION_ATTRIBUTE_NAME);
	}

	/**
	 *  Установить описание карты (Visa Classic, MasterCard, Maestro Cirrus etc)
	 * @param description описание карты
	 */
	protected void setChargeOffCardDescription(String description)
	{
		setNullSaveAttributeStringValue(FROM_CARD_DESCRIPTION_ATTRIBUTE_NAME, description);
	}


	/**
	 * @return дата окончания срока действия карты списания
	 */
	public String getChargeOffCardAccount()
	{
		return getNullSaveAttributeStringValue(PAYER_CARD_ACCOUNT_ATTRIBUTE_NAME);
	}

	/**
	 * Установить карточный счет карты списания
	 * @param number номер КС
	 */
	protected void setChargeOffCardAccount(String number)
	{
		setNullSaveAttributeStringValue(PAYER_CARD_ACCOUNT_ATTRIBUTE_NAME, number);
	}

	public String getFromAccountName()
	{
		return getNullSaveAttributeStringValue(CHARGE_OFF_RESOURCE_NAME_ATTRIBUTE_NAME);
	}

	/**
	 * @return сумма списания без учета комиссии.
	 */
	public Money getChargeOffAmount()
	{
		return chargeOffAmount;
	}

	/**
	 * Установка суммы документа
	 * @param amount сумма платежа.
	 */
	public void setChargeOffAmount(Money amount)
	{
		this.chargeOffAmount = amount;
	}

	/**
	 * Сумма зачисления. (без комиссии)
	 *
	 * @return сумма зачисления.
	 */
	public Money getDestinationAmount()
	{
		return destinationAmount;
	}

	/**
	 * Установить сумму зачисления(в том случае, если она изменилась в процессе исполнения платежа)
	 * @param amount сумма зачисленная на счет
	 */
	public void setDestinationAmount(Money amount)
	{
		destinationAmount = amount;
	}

	/**
	 * получить точную(введенную пользователем) сумму документа
	 * @return точная сумма
	 */
	public Money getExactAmount()
	{
		InputSumType sumType = getInputSumType();
		if (InputSumType.CHARGEOFF == sumType)
		{
			return getChargeOffAmount();
		}
		if (InputSumType.DESTINATION == sumType)
		{
			return getDestinationAmount();
		}
		if (isTemplate())
		{
			return null;
		}
		if (DocumentHelper.isLongOffer(this))
		{
			return null;
		}
		if (DocumentHelper.isRemoteConnectionUDBOClaim(this))
		{
			return null;
		}
		throw new IllegalStateException("Невозможно определить точную сумму: не задан атрибут " + EXACT_AMOUNT_FIELD_ATTRIBUTE_NAME);
	}

	/**
	 * @return oснование(назначение) платежа.
	 */
	public String getGround()
	{
		return ground;
	}

	/**
	 *  Установка oснования(назначения) платежа.
	 * @param ground oснование(назначение) платежа.
	 */
	public void setGround(String ground)
	{
		this.ground = ground;
	}

	private void setNationalCurrency(Currency currency)
	{
		setNullSaveAttributeStringValue(NATIONAL_CURRENCY_ATTRIBUTE_NAME, currency.getCode());
	}

	/**
	 * получить национальную валюту на момент сохранения платежа
	 * @return национальная валюта
	 */
	public Currency getNationalCurrency() throws DocumentException
	{
		try
		{
			String currencyCode = getNullSaveAttributeStringValue(NATIONAL_CURRENCY_ATTRIBUTE_NAME);
			if (currencyCode == null)
			{
				return null;
			}
			return findCurrencyByISOCode(currencyCode);
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
	}

	/**
	 * @return валюта списания.
	 */
	public Currency getChargeOffCurrency() throws GateException
	{
		String currencyCode = getNullSaveAttributeStringValue(CHARGE_OFF_RESOURCE_CURRENCY_ATTRIBUTE_NAME);
		if (currencyCode == null)
		{
			return null;
		}
		return findCurrencyByISOCode(currencyCode);
	}

	public AbstractPaymentDocument createCopy(Class<? extends BusinessDocument> instanceClass) throws DocumentException, DocumentLogicException
	{
		AbstractPaymentDocument document = (AbstractPaymentDocument) super.createCopy(instanceClass);
		document.setChargeOffAmount(getChargeOffAmount());
		document.setDestinationAmount(getDestinationAmount());
		document.setChargeOffAccount(getChargeOffAccount());
		document.setPayerName(getPayerName());
		document.setGround(getGround());
		document.setInputSumType(getInputSumType());
		return document;
	}

	public Document convertToDom() throws DocumentException
	{
		Document document = super.convertToDom();
		Element root = document.getDocumentElement();
		appendNullSaveString(root, CHARGE_OFF_RESOURCE_ATTRIBUTE_NAME, getChargeOffAccount());
		appendNullSaveString(root, PAYER_NAME_ATTRIBUTE_NAME, getPayerName());
		appendNullSaveString(root, GROUND_ATTRIBUTE_NAME, getGround());
		appendNullSaveMoney(root, CHARGE_OFF_AMOUNT_ATTRIBUTE_NAME, getChargeOffAmount());
		appendNullSaveMoney(root, DESTINATION_AMOUNT_ATTRIBUTE_NAME, getDestinationAmount());
		appendNullSaveString(root, EXACT_AMOUNT_FIELD_ATTRIBUTE_NAME, getInputSumType()==null?null: getInputSumType().toValue());
		appendNullSaveString(root, RECEIVER_NAME_ATTRIBUTE_NAME, getReceiverName());
		return document;
	}

	protected void readFromDom(Document document, InnerUpdateState updateState, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		super.readFromDom(document, updateState, messageCollector);
		Element root = document.getDocumentElement();

		InputSumType sumType = InputSumType.fromValue(XmlHelper.getSimpleElementValue(document.getDocumentElement(), "exact-amount"));
		if (sumType == null)
		{
			setChargeOffAmount(null);
			setDestinationAmount(null);
		}
		else if (InputSumType.CHARGEOFF == sumType)
		{
			//передаем только одну сумму, вторую обнуляем
			if (XmlHelper.tagTest(CHARGE_OFF_AMOUNT_ATTRIBUTE_NAME, root))
			{
				setChargeOffAmount(getMoneyFromDom(root, CHARGE_OFF_AMOUNT_ATTRIBUTE_NAME));
			}
			setDestinationAmount(null);
		}
		else if (InputSumType.DESTINATION == sumType)
		{
			//передаем только одну сумму, вторую обнуляем
			if (XmlHelper.tagTest(DESTINATION_AMOUNT_ATTRIBUTE_NAME, root))
			{
				setDestinationAmount(getMoneyFromDom(root, DESTINATION_AMOUNT_ATTRIBUTE_NAME));
			}
			setChargeOffAmount(null);
		}

		if (XmlHelper.tagTest(PAYER_NAME_ATTRIBUTE_NAME, root))
			setPayerName(XmlHelper.getSimpleElementValue(root, PAYER_NAME_ATTRIBUTE_NAME));

		if(XmlHelper.tagTest(CHARGE_OFF_RESOURCE_ATTRIBUTE_NAME, root))
			setChargeOffAccount(XmlHelper.getSimpleElementValue(root, CHARGE_OFF_RESOURCE_ATTRIBUTE_NAME));

		if(XmlHelper.tagTest(RECEIVER_NAME_ATTRIBUTE_NAME, root))
			setReceiverName(XmlHelper.getSimpleElementValue(root, RECEIVER_NAME_ATTRIBUTE_NAME));

		setInputSumType(sumType);
		setGround(XmlHelper.getSimpleElementValue(root, GROUND_ATTRIBUTE_NAME));
		setNationalCurrency(findNationalCurrency());
		storeChargeOffCurrencyInfo(updateState);
		storeDocumentOfficeInfo();
	}

	/**
	 * Платежные доументы всегда отправляются в офис, в котором ведется источник списания.
	 * сохранянем данный офис в документе.
	 */
	protected void storeDocumentOfficeInfo() throws DocumentException
	{
		try
		{
			PaymentAbilityERL chargeOffLink = getChargeOffResourceLink();
			if (chargeOffLink == null)
			{
				return;
			}

			// если списание происходит с карты, то подразделение документа должно быть подразделением клиента
			if(chargeOffLink  instanceof CardLink)
			{
				setOffice(getDepartment());
				return;
			}

			//если списание происходит со счета, то ищем подразделение в нашей БД.
			if((chargeOffLink instanceof AccountLink) || (chargeOffLink instanceof IMAccountLink))
			{
				Code code = new ExtendedCodeImpl(chargeOffLink.getOffice().getCode());
				Department department = departmentService.findByCode(code);
				// если подразделение не нашлось - кидаем исключение
				if(department == null)
					throw new DocumentException("Не удалось найти подразделение с ТБ = "+ code.getFields().get("region")+" ОСБ = " +
							code.getFields().get("branch") + " ВСП = " + code.getFields().get("office") );

				setOffice(department);
				return;
			}

			throw new DocumentException("Счет списания должен быть либо карта, либо счет ");
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	/**
	 * сохранить информацию о валюте источника списания
	 * Метод вызывается при создании и сохранении документа
	 */
	private void storeChargeOffCurrencyInfo(InnerUpdateState updateState) throws DocumentLogicException, DocumentException
	{
		// 1. Если номера источника списания нет, то и валюту сохранять не нужно
		if (StringHelper.isEmpty(getChargeOffAccount()))
			return;

		// 2. Если линка нет, то это проблема
		PaymentAbilityERL link = getChargeOffResourceLink();
		if (link == null)
		{
			if(updateState == InnerUpdateState.INIT)
				return;

			throw new DocumentException("Не найден линк-на-источник списания типа " + getChargeOffResourceType());
		}

		Currency currency = link.getCurrency();
		if (currency == null)
			throw new DocumentLogicException("Невозможно определить валюту счета/карты списания");

		setChargeOffCurrency(currency);
	}

	/**
	 * установить валюту источника списания
	 * @param currency валюта.
	 */
	protected void setChargeOffCurrency(Currency currency)
	{
		setNullSaveAttributeStringValue(CHARGE_OFF_RESOURCE_CURRENCY_ATTRIBUTE_NAME, currency.getCode());
	}

	/**
	 * получить карту списания
	 * @param messageCollector хранилище ошибок, может быть null
	 * @throws DocumentException
	 * @throws DocumentLogicException
	 * @return получить карту списания
	 */
	protected Card receiveChargeOffCard(MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		ResourceType chargeOffResourceType = getChargeOffResourceType();
		if (chargeOffResourceType == ResourceType.CARD)
		{
			return receiveCardLinkByCardNumber(getChargeOffCard(), messageCollector).getCard();
		}
		throw new IllegalStateException("Неверный тип источника списания " + chargeOffResourceType);
	}

	/**
	 * Получить дату закрытия карты
	 * @param chargeOffResourceType тип источника списания
	 * @param messageCollector хранилище ошибок, может быть null
	 * @return дата закрытия карты
	 * @throws DocumentException
	 * @throws DocumentLogicException
	 */
	protected Calendar getChargeOffCardExpireDate(ResourceType chargeOffResourceType, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		if (chargeOffResourceType == ResourceType.CARD)
		{
			return receiveCardLinkByCardNumber(getChargeOffCard(), messageCollector).getExpireDate();
		}
		throw new IllegalStateException("Неверный тип источника списания " + chargeOffResourceType);
	}

	/**
	 * Получить кардлинк по номеру карты
	 * получение происходит из контекста. метод используется только для своих карт.
	 * @param cardNumber номер карты
	 * @param messageCollector хранилище ошибок, может быть null
	 * @return кардлинк
	 * @throws DocumentLogicException
	 * @throws DocumentException
	 */
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
				cardLink = processErrorCard(cardLink, messageCollector);
			}

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

	/**
	 * @return Курс продажи банком клиенту для валюты списания
	 */
	//TODO  убрать
	public CurrencyRate getDebetSaleRate()
	{
		return null;
	}

	/**
	 * @return Курс покупки банком клиенту для валюты списания
	 */
	//TODO  убрать
	public CurrencyRate getDebetBuyRate()
	{
		return null;
	}

	/**
	 * @return Курс продажи банком клиенту для валюты зачисления
	 */
	//TODO  убрать
	public CurrencyRate getCreditSaleRate()
	{
		return null;
	}

	/**
	 * @return Курс покупки банком у клиента для валюты зачисления
	 */
	//TODO  убрать
	public CurrencyRate getCreditBuyRate()
	{
		return null;
	}

	/**
	 * Сравнить документы по ключевым полям. Если у переданного документа не заполнено ключевое поле,
	 *  а у текущего заполнено, документы отличаются.
	 * @param template шаблон, на основе которого был создан документ
	 * @return true - ключевые поля у документов одинаковые, false - ключевые поля разные
	 */
	public boolean equalsKeyEssentials(TemplateDocument template) throws BusinessException
	{
		if (template == null)
		{
			return false;
		}

		return StringHelper.equalsNullIgnore(getChargeOffCardAccount(), template.getChargeOffCardAccount());
	}

	//выбрасывает исключение или возвращает новый cardLink != null
	protected CardLink processErrorCard(CardLink cardlink, MessageCollector messageCollector) throws DocumentLogicException, DocumentException
	{
		throw new DocumentLogicException("Вы не можете выполнить данную операцию. Для доступа измените настройки видимости Вашей карты " + MaskUtil.getCutCardNumber(cardlink.getNumber())
				+ " в пункте меню «Настройки» - «Настройка безопасности» - «Настройка видимости продуктов»");
	}

	/**
	 * Возвращает имя поля с формы вкотором пользователь ввел сумму списнаия
	 * @return enum, тип поля в котром сумма
	 */
	public InputSumType getInputSumType()
	{
		return inputSumType;
	}

	public void setInputSumType(InputSumType inputSumType)
	{
	  	this.inputSumType = inputSumType;
	}

	public void setInputSumType(String inputSumType)
	{
		if(inputSumType == null || inputSumType.trim().length() == 0)
			return;

		this.inputSumType = Enum.valueOf(InputSumType.class, inputSumType);
	}

	/**
	 * @return код операции
	 */
	public String getOperationCode()
	{
		return getNullSaveAttributeStringValue(OPERATION_CODE_ATTRIBUTE_NAME);
	}

	public String getConfirmEmployee()
	{
		return confirmEmployee;
	}

	public void setConfirmEmployee(String confirmEmployee)
	{
		this.confirmEmployee = confirmEmployee;
	}

	/**
	 * @return наименование получателя
	 */
	public String getReceiverName()
	{
		return receiverName;
	}

	/**
	 * @param receiverName наименование получателя
	 */
	public void setReceiverName(String receiverName)
	{
		this.receiverName = receiverName;
	}

	/**
	 * Является ли платеж по дополнительно подтвержденному шаблону
	 * @return true - является
	 * @throws DocumentException
	 */
	public boolean isPaymentByConfirmTemplate() throws DocumentException
	{
		// платеж не по шаблону
		if (!isByTemplate())
		{
			return false;
		}

		try
		{
			TemplateDocument template = TemplateDocumentService.getInstance().findById(getTemplateId());
			if (template == null)
			{
				return false;
			}

			return template.getAdditionalOperationChannel() != null && equalsKeyEssentials(template);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	public Set<ExternalResourceLink> getLinks() throws DocumentException
	{
		Set<ExternalResourceLink> result = super.getLinks();
		PaymentAbilityERL link = getChargeOffResourceLink();
		if (link != null)
		{
			result.add(link);
		}
		return result;
	}

	public void setSumIncreasedOverLimit(boolean sumIncreasedOverLimit)
	{
		this.sumIncreasedOverLimit = sumIncreasedOverLimit;
	}

	public boolean getSumIncreasedOverLimit()
	{
		return sumIncreasedOverLimit;
	}

    public boolean isPaymentFromTemplateNeedConfirm()
    {
        return isPaymentFromTemplateNeedConfirm;
    }

    public void setPaymentFromTemplateNeedConfirm(boolean paymentFromTemplateNeedConfirm)
    {
        this.isPaymentFromTemplateNeedConfirm = paymentFromTemplateNeedConfirm;
    }

    public BigDecimal getConvertionRate()
	{
		return null;
	}
}
