package com.rssl.phizic.operations.payment.billing;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.PaymentFieldKeys;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.modes.ConfirmStrategy;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.dictionaries.providers.*;
import com.rssl.phizic.business.documents.AbstractAccountsTransfer;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.OperationMessagesConfig;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.documents.payments.AutoPaymentBase;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.payments.source.NewDocumentSource;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.TemplateHelper;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.business.documents.templates.source.NewTemplateSource;
import com.rssl.phizic.business.documents.templates.source.TemplateDocumentSource;
import com.rssl.phizic.business.ext.sbrf.mobilebank.MobileBankUtils;
import com.rssl.phizic.business.ext.sbrf.mobilebank.UncompatibleServiceProviderException;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.profile.addressbook.Contact;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.business.shop.Order;
import com.rssl.phizic.business.util.MaskPhoneNumberUtil;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.common.types.VersionNumber;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.MalformedVersionFormatException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.mobilebank.GatePaymentTemplate;
import com.rssl.phizic.gate.payments.autopayment.CreateAutoPayment;
import com.rssl.phizic.operations.payment.CreateFormPaymentOperation;
import com.rssl.phizic.profile.ProfileConfig;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.PhoneNumberFormat;
import com.rssl.phizic.utils.StringHelper;

import java.util.*;

import static com.rssl.common.forms.FormConstants.RUR_PAYMENT_FORM;
import static com.rssl.common.forms.FormConstants.SERVICE_PAYMENT_FORM;
import static com.rssl.common.forms.doc.CreationSourceType.mobiletemplate;

/**
 * @author krenev
 * @ created 14.01.2011
 * @ $Author$
 * @ $Revision$
 * Операция редактирования платежа оплаты услуг.
 * В отличии от обычного платежа в оплате услуг присутствует первый шаг,
 * форма которого требует получения дополнительных данных:
 * 1) Поставщики(услуги)
 * 2) Счета/карты для списания
 * 3) дополнительные реквизиты получателя.
 */
public class EditServicePaymentOperation extends CreateFormPaymentOperation
{
	public static final ServiceProviderService serviceProviderService = new ServiceProviderService();
	public static final SimpleService simpleService  = new SimpleService();

	private BillingServiceProviderBase provider;
	private TemplateDocument template;
	private Order order;
	/**
	 * Идентификатор пользователя в социальной сети.
	 */
	private String socialNetUserIdentifier;

	/**
	 * Идентификатор поставщика услуг социальных сетей (Внешний).
	 */
	private String socialNetProviderId;

	/**
	 * Поле, в котором хранится идентификатор пользователя для социальных сетей.
	 */
	private String socialNetPaymentFieldName;

	/**
	 * инициализировать операцию
	 * @param source  источник документа
	 * @param receiverId идентифкатор оплачиваемого получателя может отличаться от отого что в источнике документа(например при копировании и смене получателя)
	 */
	public void initialize(TemplateDocumentSource source, Long receiverId) throws BusinessException, BusinessLogicException
	{
	}

	/**
	 * инициализировать операцию
	 * @param source  источник документа
	 * @param receiverId идентифкатор оплачиваемого получателя может отличаться от отого что в источнике документа(например при копировании и смене получателя)
	 */
	public void initialize(DocumentSource source, Long receiverId) throws BusinessException, BusinessLogicException
	{
		super.initialize(source);
		Long providerId = null;

		if(document instanceof CreateAutoPayment)
		{
			AutoPaymentBase payment = (AutoPaymentBase)document;
			//получаем поставщика
			providerId = receiverId != null ? receiverId : payment.getReceiverInternalId();
		}
		else
		{
			JurPayment payment = (JurPayment) document;
			//получаем поставщика
			providerId = receiverId != null ? receiverId : payment.getReceiverInternalId();
			//обнуляем идентфикатор платежа в биллинге. тк ввод документа должен продолжаться
			payment.setIdFromPaymentSystem(null);
			try
			{
				//убиваем все расширенные поля(если были) из платежа
				payment.setExtendedFields(null);
			}
			catch (DocumentException e)
			{
				throw new BusinessException(e);
			}
		}
		ServiceProviderBase serviceProvider = null;
		if (providerId != null) //если получатель не внешний
		{
			serviceProvider = serviceProviderService.findById(providerId);
			if (serviceProvider == null)
			{
				throw new BusinessLogicException(ConfigFactory.getConfig(OperationMessagesConfig.class).getOperationMessage(OperationMessagesConfig.NOT_ACTIVE_PROVIDER_MESSAGE));
			}

			if (ServiceProviderState.MIGRATION == serviceProvider.getState() && (!(document.isByTemplate() || document.isByMobileTemplate())))
			{
				throw new BusinessLogicException(ConfigFactory.getConfig(OperationMessagesConfig.class).getOperationMessage(OperationMessagesConfig.NOT_ACTIVE_PROVIDER_MESSAGE));
			}
		}
		//запоминаем инфу поставщика
		if (serviceProvider != null)
		{
			setProviderInfo(serviceProvider);

			//Находим идентификатор пользователя в социальной сети, если поставщик услуг - социальная сеть.
			socialNetProviderId = ConfigFactory.getConfig(ProfileConfig.class).getSocialNetProviderId();
			if (StringHelper.equalsNullIgnore((String) serviceProvider.getSynchKey(), socialNetProviderId) && PersonContext.isAvailable())
			{
				//Получаем идентификатор для соц сети Одноклассники.
				socialNetUserIdentifier = PersonContext.getPersonDataProvider().getPersonData().getUserIdForSocialNet("OK");
				socialNetPaymentFieldName = ConfigFactory.getConfig(ProfileConfig.class).getSocialNetUserIdFieldName();
			}
		}

		loadOrderInfo();
	}

	/**
	 * Оплата по шаблону Мобильного банка
	 * Это оплата поставщику, у которого ключевое поле (ровно одно) забито значением из шаблона
	 * @param templateId - ID шаблона SMS-платежа в МБ
	 * @param amount - сумма в валюте списания
	 * @param creationType
	 * @deprecated избавление от шаблонов МБК
	 */
	@Deprecated
	//todo CHG059738 удалить
	public void initializeMobileBankTemplatePayment(long templateId, double amount, CreationType creationType) throws BusinessException, BusinessLogicException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();

		// 1. Получим линк-на-шаблон-платежа (вместе с шаблоном)
		PaymentTemplateLink templateLink = personData.getMobileBankTemplateLinkById(templateId);
		GatePaymentTemplate template = templateLink.getTemplate();
		CardLink cardLink = personData.findCard(template.getCardNumber());
		if (cardLink == null)
			throw new AccessException("Карта " + MaskUtil.getCutCardNumber(template.getCardNumber()) + " не принадлежит пользователю");

		// 2. Убедимся, что поставщик "готов" к оплате
		BillingServiceProvider serviceProvider = templateLink.getServiceProvider();
		testProviderMobilebankCompatible(serviceProvider);
		boolean toCard = "PEREVOD".equals(serviceProvider.getMobilebankCode()); //платеж по шаблону МБ карта-карта

		// 3. Подготовим источник значений полей для нового документа
		FieldDescription keyField = getProviderKeyField(serviceProvider);
		String keyFieldValue = template.getPayerCode();

		Map<String, String> fields = new HashMap<String, String>();
		fields.put(PaymentFieldKeys.PROVIDER_KEY, serviceProvider.getId().toString());
		fields.put(PaymentFieldKeys.FROM_RESOURCE_KEY, cardLink.getCode());
		fields.put(PaymentFieldKeys.AMOUNT, String.valueOf(amount));
		fields.put(keyField.getExternalId(), keyFieldValue);
		if (toCard)
		{
			String cardNumber = template.getPayerCode();
			fields.put(PaymentFieldKeys.IS_CARD_TRANSFER, Boolean.toString(true));
			fields.put(PaymentFieldKeys.IS_OUR_BANK, Boolean.toString(true));
			fields.put(PaymentFieldKeys.RECEIVER_SUB_TYPE, "ourCard");
			fields.put(PaymentFieldKeys.FROM_RESOURCE_TYPE, CardLink.class.getName());
			fields.put(PaymentFieldKeys.EXTERNAL_CARD_NUMBER, cardNumber);
			fields.put(PaymentFieldKeys.EXACT_AMOUNT_FIELD_NAME, "charge-off-field-exact");
			fields.put(PaymentFieldKeys.FROM_ACCOUNT_SELECT, cardLink.getNumber());
			fields.put(PaymentFieldKeys.SELL_AMOUNT, Double.toString(amount));

			try
			{
				Pair<String, Office> request = new Pair<String, Office>(cardNumber, null);
				ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();

				BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
				GroupResult<Pair<String, Office>, Card> result = bankrollService.getCardByNumber(person.asClient(), request);
				Card card1 = GroupResultHelper.getOneResult(result);
				String buyAmountCurrency = card1.getCurrency().getCode();
				fields.put(PaymentFieldKeys.BUY_AMOUNT_CURRENCY, buyAmountCurrency);
			}
			catch (SystemException e)
			{
				throw new BusinessException(e);
			}
			catch (LogicException e)
			{
				throw new BusinessLogicException(e);
			}
		}

		FieldValuesSource fieldValuesSource = new MapValuesSource(fields);

		// 4. Создаём документ: для оплаты по шаблону карт-карта - RurPayment, иначе - RurPayJurSB
		String formName = toCard ? RUR_PAYMENT_FORM : SERVICE_PAYMENT_FORM;
		DocumentSource source = new NewDocumentSource(formName, fieldValuesSource, creationType, mobiletemplate);
		super.initialize(source);

		// 5. Добавляем данные поставщика
		if(!toCard)
				setProviderInfo(serviceProvider);

		loadOrderInfo();
	}

	private void setProviderInfo(ServiceProviderBase provider) throws BusinessException, BusinessLogicException
	{
		if (!(provider instanceof BillingServiceProviderBase))
			throw new BusinessException("не верный тип получателя " + provider.getClass());

		this.provider = (BillingServiceProviderBase) provider;

		if (!validateProvider(this.provider))
			throw new BusinessLogicException(ConfigFactory.getConfig(OperationMessagesConfig.class).getOperationMessage(OperationMessagesConfig.NOT_ACTIVE_PROVIDER_MESSAGE));

		if (provider instanceof InternetShopsServiceProvider)
		{
			InternetShopsServiceProvider internetShopsServiceProvider = (InternetShopsServiceProvider) provider;
			setFormName(internetShopsServiceProvider.getFormName());
			if (internetShopsServiceProvider.isFacilitator())
				setProviderName(((AbstractAccountsTransfer) document).getReceiverName());
			else
				setProviderName(provider.getName());
			document.setFormName(internetShopsServiceProvider.getFormName());
		}

		try
		{
			// сеттим поля поставщика
			if(document instanceof JurPayment)
			{
				JurPayment payment = (JurPayment) document;
				payment.setExtendedFields((List) provider.getFieldDescriptions());
			}
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
	}

	private void loadOrderInfo() throws BusinessException
	{
		order = DocumentHelper.getUECOrder(document);
	}

	protected boolean validateProvider(BillingServiceProviderBase providerBase)
    {
	    return (providerBase.getState() == ServiceProviderState.ACTIVE || providerBase.getState() == ServiceProviderState.MIGRATION) && (document instanceof CreateAutoPayment || isAvailablePaymentsForApplication(providerBase));
    }

	private boolean isAvailablePaymentsForApplication(BillingServiceProviderBase providerBase)
	{
		ApplicationConfig applicationConfig = ApplicationConfig.getIt();
		ApplicationInfo info = applicationConfig.getApplicationInfo();
		if (info.isWeb())
		{
			return providerBase.isAvailablePaymentsForInternetBank();
		}
		if (info.isMobileApi())
		{
			return document.isByTemplate() || providerBase.isAvailablePaymentsForMApi();
		}
		if (info.isATM())
		{
			//Создание автоплатежей не должно зависеть от доступности платежей в АТМ
			return providerBase.isAvailablePaymentsForAtmApi() ||
					(providerBase instanceof BillingServiceProvider && ((BillingServiceProvider)providerBase).isAutoPaymentSupportedInATM());
		}
		if (info.isSocialApi())
		{
			return providerBase.isAvailablePaymentsForSocialApi();
		}
		if (providerBase instanceof InternetShopsServiceProvider && (info.getApplication() == Application.WSGateListener || info.getApplication() == Application.ErmbSmsChannel || info.getApplication() == Application.WebShopListener))
		{
			return ((InternetShopsServiceProvider)providerBase).isAvailableMobileCheckout();
		}
		return providerBase.isAvailablePaymentsForErmb();
	}

	public List<PaymentAbilityERL> getChargeOffResources() throws BusinessException, BusinessLogicException
	{
		List<PaymentAbilityERL> result = new ArrayList<PaymentAbilityERL>();

		try
		{
			if (isFromAccountPaymentAllow())
			{
				List<? extends PaymentAbilityERL> list = PersonContext.getPersonDataProvider().getPersonData().getAccounts(new ActiveDebitRurAccountFilter());
				filterStoredResource(list);
				filterNotPermittedForFinancialTransactions(list);
				result.addAll(list);
			}
		}
		catch (InactiveExternalSystemException ex)
		{
			log.error("Не возможно получить информацию по счетам", ex);
			getMessageCollector().addInactiveSystemError("Ваши вклады и счета временно недоступны. " + ex.getMessage());
		}

		try
		{
			if (isFromCardPaymentAllow())
			{
				List<? extends PaymentAbilityERL> list = PersonContext.getPersonDataProvider().getPersonData().getCards(getCardFilter());
				filterStoredResource(list);
				result.addAll(list);
			}
		}
		catch (InactiveExternalSystemException ex)
		{
			log.error("Не возможно получить информацию по картам", ex);
			getMessageCollector().addInactiveSystemError("Ваши карты временно недоступны. " + ex.getMessage());
		}
		return result;
	}

	/**
	 * Возвращает источники списания, которые поддерживает выбранный поставщик
	 * @return список источников списания
	 */
	public List<PaymentAbilityERL> getProviderChargeOffResources() throws BusinessException, BusinessLogicException
	{
		List<PaymentAbilityERL> list;
		switch (provider.getAccountType())
		{
			case ALL:
				list = new ArrayList<PaymentAbilityERL>();
				boolean isByTemplate = document != null && document.isByTemplate();
				PaymentAbilityERL fromResource = getChargeOffResourceLink();
				boolean isFromCard    = (fromResource != null && fromResource.getResourceType() == ResourceType.CARD) || ResourceType.CARD == getChargeOffResourceType();
				boolean isFromAccount = (fromResource != null && fromResource.getResourceType() == ResourceType.ACCOUNT) || ResourceType.ACCOUNT == getChargeOffResourceType();
				for (PaymentAbilityERL link : getChargeOffResources())
				{
					ResourceType type = link.getResourceType();
					if (!isByTemplate || (isFromCard && type == ResourceType.CARD) || (isFromAccount && type == ResourceType.ACCOUNT))
						list.add(link);
				}
				break;
			case CARD:
				list = new ArrayList<PaymentAbilityERL>();
				for (PaymentAbilityERL link : getChargeOffResources())
				{
					if (link.getResourceType() == ResourceType.CARD)
						list.add(link);
				}
				break;
			case DEPOSIT:
				list = new ArrayList<PaymentAbilityERL>();
				for (PaymentAbilityERL link : getChargeOffResources())
				{
					if (link.getResourceType() == ResourceType.ACCOUNT)
						list.add(link);
				}
				break;
			default:
				throw new RuntimeException("Неожиданный класс источника списания");
		}
		return list;
	}


	/**
	 * получить список "услуг" поставщика(не путать с группами услуг для организации иерархии во view)
	 * Услуги поставщика - это множество постащиков с одинаковыми CodeRecipientSBOL.
	 * На самом деле это 1 поставщик с точки природы(1 лицо) но предоставляющий разные услуги в биллинге(Service)
	 * например, поставщик мегафон  представляется 2 записями:
	 * 1) постащик мегафон с биллинговой услугой "сотовая связь"
	 * 2) постащик мегафон с биллинговой услугой "интернет"
	 * @return список "услуг" (поставщиков).
	 */
	public List<ServiceProviderShort> getProviderAllServices() throws BusinessException
	{
		return serviceProviderService.getProviderAllServices(provider, !(document.isByTemplate() || document.isByMobileTemplate()));
	}

	/**
	 * получить список всех полей постащика(ов) для всех его биллинговых услуг.
	 * @return идентифкация принадлежности поля конкретному поставщику в разрезе услуги должна осуществляться по
	 * FieldDescriptio#getHolderId.
	 */
	public List<FieldDescription> getProviderAllServicesFields() throws BusinessException
	{
		return serviceProviderService.getProviderAllServicesFields(provider);
	}

	public BusinessDocument getDocument()
	{
		if (document instanceof JurPayment || document instanceof RurPayment)
			return document;
		throw new IllegalStateException("Неверный тип создавамого/редактируемого документа:" + document.getClass());
	}

	/**
	 * @return заказ, привязанный к платежу, или null
	 */
	public Order getOrder()
	{
		return order;
	}

	/**
	 * Получить мап значений полей документа.
	 * @return список значений полей.
	 */
	public Map getDocumentFieldValues() throws BusinessException
	{
		return getFieldValuesSource().getAllValues();
	}

	/**
	 * @return поставщик, в адрес которого совершается оплата
	 */
	public BillingServiceProviderBase getProvider()
	{
		return provider;
	}

	/**
	 *
	 * Использование оффлайн продуктов разрешается только для формы оплаты услуг (платёж поставщику), в
	 * остальных случаях линки содержащие оффлайн продукты удаляются из списка
	 *
	 * @param  links списки карт или счетов
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	protected void filterStoredResource(List<? extends PaymentAbilityERL> links) throws BusinessException, BusinessLogicException
	{
		Iterator<? extends PaymentAbilityERL> i = links.listIterator();
		while (i.hasNext())
		{
			PaymentAbilityERL link = i.next();
			if (link.getValue() instanceof AbstractStoredResource)
			{
				if (SERVICE_PAYMENT_FORM.equals(getFormName()))
				{
					String resource = link.getResourceType() == ResourceType.CARD ? "картам" : "счетам";
					getMessageCollector().addInactiveSystemError("Информация по Вашим " + resource + " может быть неактуальной.");
					break;
				}
				else
				{
					i.remove();
				}
			}
		}
	}

	protected CardFilter getCardFilter() throws BusinessException
	{
		CardFilter filter = getProvider().isCreditCardSupported() ?
				new CardFilterConjunction(new ActiveNotVirtualCardsFilter(), new CardOwnFilter()) : new ActiveNotVirtualNotCreditOwnCardFilter();
		if (isExternal())
		{
			return new CardFilterConjunction(filter, new RURCardFilter());
		}
		return filter;
	}

	protected boolean isFromCardPaymentAllow() throws BusinessException
	{
		//оплата по старым шаблонам ЦПФЛ запрещена
		if (TemplateHelper.isByOldCPFLTemplate(document))
		{
			return false;
		}

		//проверяем разрешение оплаты с карты
		if (!DocumentHelper.isFromCardPaymentAllow(document))
		{
			return false;
		}

		//при оплате по шаблону смена ресурса списания запрещена
		if (document.isByTemplate() && DocumentHelper.isFromAccountPaymentAllow(document))
		{
			return false;
		}

		return AccountType.ALL == provider.getAccountType() || AccountType.CARD == provider.getAccountType();
	}

	protected boolean isFromAccountPaymentAllow() throws BusinessException
	{
		//проверяем разрешение на оплату внешнему получателю со счета
		if (!DocumentHelper.isExternalJurAccountPaymentsAllowed())
		{
			return false;
		}

		//проверяем разрешение оплаты со счета
		if (!DocumentHelper.isFromAccountPaymentAllow(document))
		{
			return false;
		}

		//при оплате по шаблону смена ресурса списания запрещена
		if (document.isByTemplate() && DocumentHelper.isFromCardPaymentAllow(document))
		{
			return false;
		}

		return AccountType.ALL == provider.getAccountType() || AccountType.DEPOSIT == provider.getAccountType();
	}

	@SuppressWarnings("ThrowCaughtLocally")
	@Deprecated
	//todo CHG059738 удалить
    private void testProviderMobilebankCompatible(BillingServiceProvider provider) throws BusinessException, BusinessLogicException
	{
		try
		{
            if (provider.getState() != ServiceProviderState.ACTIVE)
                throw new UncompatibleServiceProviderException("Поставщик не подключен.", provider);
            if (StringHelper.isEmpty(provider.getMobilebankCode()))
                throw new UncompatibleServiceProviderException("Поставщик не является поставщиком Мобильного Банка.", provider);
            if (!provider.isAvailablePaymentsForMApi())
                throw new UncompatibleServiceProviderException("Поставщик не активен в мобильном API.", provider);
			VersionNumber providerMapiVersionNumber = provider.getMapiVersionNumber();
			if (providerMapiVersionNumber.gt(getClientAPIVersion()))
                throw new UncompatibleServiceProviderException("Поставщик не активен в данной версии API. Версия клиента: " + getClientAPIVersion() +
                        ". Поставщик активен начиная с версии: " + providerMapiVersionNumber, provider);
			MobileBankUtils.testServiceProviderMobilebankCompatible(provider);
		}
		catch (UncompatibleServiceProviderException ex)
		{
			log.warn(ex.getMessage());
			throw new BusinessLogicException(message("paymentsBundle", "message.payment.not.available"), ex);
		}
		catch (MalformedVersionFormatException e)
		{
			throw new BusinessException(e);
		}
	}

	private FieldDescription getProviderKeyField(BillingServiceProvider provider)
			throws BusinessException
	{
		for (FieldDescription field : provider.getFieldDescriptions()) {
			if (field.isKey())
				return field;
		}
		throw new BusinessException("У поставщика нет ключевых полей");
	}

	@Transactional
	public Long saveAsTemplate(String templateName) throws BusinessException, BusinessLogicException
	{
/*
		//TODO поправить в релизе 13.0, запрос "ENH057379: Доработки по шаблонам документов", исполнитель Худяков А.
		document.setTemplate(true);
		executor.fireEvent(new ObjectEvent(DocumentEvent.SAVE, getSourceEvent()));

*/
		template = new NewTemplateSource(document, getDocumentCreationType()).getTemplate();

		TemplateDocumentService.getInstance().addOrUpdate(template);

		try
		{
			// сброс стратегии подтверждения
			ConfirmStrategy confirmStrategy = getConfirmStrategyProvider().getStrategy();
			confirmStrategy.reset(AuthModule.getAuthModule().getPrincipal().getLogin(), template);
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException(e);
		}

		return template.getId();
	}

	/**
	 * @return шаблон документа
	 */
	public TemplateDocument getTemplate()
	{
		return template;
	}

	/**
	 * @return идентификатор поставщика услу социальной сети.
	 */
	public String getSocialNetProviderId()
	{
		return socialNetProviderId;
	}

	/**
	 * @return идентификатор пользователя в социальной сети.
	 */
	public String getSocialNetUserIdentifier()
	{
		return socialNetUserIdentifier;
	}

	/**
	 * @return имя поля в котором хранится идентификатор социальной сети.
	 */
	public String getSocialNetPaymentFieldName()
	{
		return socialNetPaymentFieldName;
	}

	/**
	 * Возвращает номер телефона пользователя по идентификатору номера
	 * @param id - идентификатор номера
	 * @return номер телефона
	 */
	public String getSelfPhone(Long id) throws BusinessException, BusinessLogicException
	{
		for (String phone : PersonContext.getPersonDataProvider().getPersonData().getPhones(false))
		{
			if (id.equals(Long.parseLong(PhoneNumberFormat.formatPhoneId(phone))))
				return phone;
		}
		return "";
	}

    public String getTrustedRecipientPhone(Long id) throws BusinessException
    {
        Contact contact = simpleService.findById(Contact.class, id);
        if(contact == null)
            return "";
        return contact.getPhone();
    }
}
