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
 * �������� �������������� ������� ������ �����.
 * � ������� �� �������� ������� � ������ ����� ������������ ������ ���,
 * ����� �������� ������� ��������� �������������� ������:
 * 1) ����������(������)
 * 2) �����/����� ��� ��������
 * 3) �������������� ��������� ����������.
 */
public class EditServicePaymentOperation extends CreateFormPaymentOperation
{
	public static final ServiceProviderService serviceProviderService = new ServiceProviderService();
	public static final SimpleService simpleService  = new SimpleService();

	private BillingServiceProviderBase provider;
	private TemplateDocument template;
	private Order order;
	/**
	 * ������������� ������������ � ���������� ����.
	 */
	private String socialNetUserIdentifier;

	/**
	 * ������������� ���������� ����� ���������� ����� (�������).
	 */
	private String socialNetProviderId;

	/**
	 * ����, � ������� �������� ������������� ������������ ��� ���������� �����.
	 */
	private String socialNetPaymentFieldName;

	/**
	 * ���������������� ��������
	 * @param source  �������� ���������
	 * @param receiverId ������������ ������������� ���������� ����� ���������� �� ����� ��� � ��������� ���������(�������� ��� ����������� � ����� ����������)
	 */
	public void initialize(TemplateDocumentSource source, Long receiverId) throws BusinessException, BusinessLogicException
	{
	}

	/**
	 * ���������������� ��������
	 * @param source  �������� ���������
	 * @param receiverId ������������ ������������� ���������� ����� ���������� �� ����� ��� � ��������� ���������(�������� ��� ����������� � ����� ����������)
	 */
	public void initialize(DocumentSource source, Long receiverId) throws BusinessException, BusinessLogicException
	{
		super.initialize(source);
		Long providerId = null;

		if(document instanceof CreateAutoPayment)
		{
			AutoPaymentBase payment = (AutoPaymentBase)document;
			//�������� ����������
			providerId = receiverId != null ? receiverId : payment.getReceiverInternalId();
		}
		else
		{
			JurPayment payment = (JurPayment) document;
			//�������� ����������
			providerId = receiverId != null ? receiverId : payment.getReceiverInternalId();
			//�������� ������������ ������� � ��������. �� ���� ��������� ������ ������������
			payment.setIdFromPaymentSystem(null);
			try
			{
				//������� ��� ����������� ����(���� ����) �� �������
				payment.setExtendedFields(null);
			}
			catch (DocumentException e)
			{
				throw new BusinessException(e);
			}
		}
		ServiceProviderBase serviceProvider = null;
		if (providerId != null) //���� ���������� �� �������
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
		//���������� ���� ����������
		if (serviceProvider != null)
		{
			setProviderInfo(serviceProvider);

			//������� ������������� ������������ � ���������� ����, ���� ��������� ����� - ���������� ����.
			socialNetProviderId = ConfigFactory.getConfig(ProfileConfig.class).getSocialNetProviderId();
			if (StringHelper.equalsNullIgnore((String) serviceProvider.getSynchKey(), socialNetProviderId) && PersonContext.isAvailable())
			{
				//�������� ������������� ��� ��� ���� �������������.
				socialNetUserIdentifier = PersonContext.getPersonDataProvider().getPersonData().getUserIdForSocialNet("OK");
				socialNetPaymentFieldName = ConfigFactory.getConfig(ProfileConfig.class).getSocialNetUserIdFieldName();
			}
		}

		loadOrderInfo();
	}

	/**
	 * ������ �� ������� ���������� �����
	 * ��� ������ ����������, � �������� �������� ���� (����� ����) ������ ��������� �� �������
	 * @param templateId - ID ������� SMS-������� � ��
	 * @param amount - ����� � ������ ��������
	 * @param creationType
	 * @deprecated ���������� �� �������� ���
	 */
	@Deprecated
	//todo CHG059738 �������
	public void initializeMobileBankTemplatePayment(long templateId, double amount, CreationType creationType) throws BusinessException, BusinessLogicException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();

		// 1. ������� ����-��-������-������� (������ � ��������)
		PaymentTemplateLink templateLink = personData.getMobileBankTemplateLinkById(templateId);
		GatePaymentTemplate template = templateLink.getTemplate();
		CardLink cardLink = personData.findCard(template.getCardNumber());
		if (cardLink == null)
			throw new AccessException("����� " + MaskUtil.getCutCardNumber(template.getCardNumber()) + " �� ����������� ������������");

		// 2. ��������, ��� ��������� "�����" � ������
		BillingServiceProvider serviceProvider = templateLink.getServiceProvider();
		testProviderMobilebankCompatible(serviceProvider);
		boolean toCard = "PEREVOD".equals(serviceProvider.getMobilebankCode()); //������ �� ������� �� �����-�����

		// 3. ���������� �������� �������� ����� ��� ������ ���������
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

		// 4. ������ ��������: ��� ������ �� ������� ����-����� - RurPayment, ����� - RurPayJurSB
		String formName = toCard ? RUR_PAYMENT_FORM : SERVICE_PAYMENT_FORM;
		DocumentSource source = new NewDocumentSource(formName, fieldValuesSource, creationType, mobiletemplate);
		super.initialize(source);

		// 5. ��������� ������ ����������
		if(!toCard)
				setProviderInfo(serviceProvider);

		loadOrderInfo();
	}

	private void setProviderInfo(ServiceProviderBase provider) throws BusinessException, BusinessLogicException
	{
		if (!(provider instanceof BillingServiceProviderBase))
			throw new BusinessException("�� ������ ��� ���������� " + provider.getClass());

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
			// ������ ���� ����������
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
			//�������� ������������ �� ������ �������� �� ����������� �������� � ���
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
			log.error("�� �������� �������� ���������� �� ������", ex);
			getMessageCollector().addInactiveSystemError("���� ������ � ����� �������� ����������. " + ex.getMessage());
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
			log.error("�� �������� �������� ���������� �� ������", ex);
			getMessageCollector().addInactiveSystemError("���� ����� �������� ����������. " + ex.getMessage());
		}
		return result;
	}

	/**
	 * ���������� ��������� ��������, ������� ������������ ��������� ���������
	 * @return ������ ���������� ��������
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
				throw new RuntimeException("����������� ����� ��������� ��������");
		}
		return list;
	}


	/**
	 * �������� ������ "�����" ����������(�� ������ � �������� ����� ��� ����������� �������� �� view)
	 * ������ ���������� - ��� ��������� ���������� � ����������� CodeRecipientSBOL.
	 * �� ����� ���� ��� 1 ��������� � ����� �������(1 ����) �� ��������������� ������ ������ � ��������(Service)
	 * ��������, ��������� �������  �������������� 2 ��������:
	 * 1) �������� ������� � ����������� ������� "������� �����"
	 * 2) �������� ������� � ����������� ������� "��������"
	 * @return ������ "�����" (�����������).
	 */
	public List<ServiceProviderShort> getProviderAllServices() throws BusinessException
	{
		return serviceProviderService.getProviderAllServices(provider, !(document.isByTemplate() || document.isByMobileTemplate()));
	}

	/**
	 * �������� ������ ���� ����� ���������(��) ��� ���� ��� ����������� �����.
	 * @return ������������ �������������� ���� ����������� ���������� � ������� ������ ������ �������������� ��
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
		throw new IllegalStateException("�������� ��� �����������/�������������� ���������:" + document.getClass());
	}

	/**
	 * @return �����, ����������� � �������, ��� null
	 */
	public Order getOrder()
	{
		return order;
	}

	/**
	 * �������� ��� �������� ����� ���������.
	 * @return ������ �������� �����.
	 */
	public Map getDocumentFieldValues() throws BusinessException
	{
		return getFieldValuesSource().getAllValues();
	}

	/**
	 * @return ���������, � ����� �������� ����������� ������
	 */
	public BillingServiceProviderBase getProvider()
	{
		return provider;
	}

	/**
	 *
	 * ������������� ������� ��������� ����������� ������ ��� ����� ������ ����� (����� ����������), �
	 * ��������� ������� ����� ���������� ������� �������� ��������� �� ������
	 *
	 * @param  links ������ ���� ��� ������
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
					String resource = link.getResourceType() == ResourceType.CARD ? "������" : "������";
					getMessageCollector().addInactiveSystemError("���������� �� ����� " + resource + " ����� ���� ������������.");
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
		//������ �� ������ �������� ���� ���������
		if (TemplateHelper.isByOldCPFLTemplate(document))
		{
			return false;
		}

		//��������� ���������� ������ � �����
		if (!DocumentHelper.isFromCardPaymentAllow(document))
		{
			return false;
		}

		//��� ������ �� ������� ����� ������� �������� ���������
		if (document.isByTemplate() && DocumentHelper.isFromAccountPaymentAllow(document))
		{
			return false;
		}

		return AccountType.ALL == provider.getAccountType() || AccountType.CARD == provider.getAccountType();
	}

	protected boolean isFromAccountPaymentAllow() throws BusinessException
	{
		//��������� ���������� �� ������ �������� ���������� �� �����
		if (!DocumentHelper.isExternalJurAccountPaymentsAllowed())
		{
			return false;
		}

		//��������� ���������� ������ �� �����
		if (!DocumentHelper.isFromAccountPaymentAllow(document))
		{
			return false;
		}

		//��� ������ �� ������� ����� ������� �������� ���������
		if (document.isByTemplate() && DocumentHelper.isFromCardPaymentAllow(document))
		{
			return false;
		}

		return AccountType.ALL == provider.getAccountType() || AccountType.DEPOSIT == provider.getAccountType();
	}

	@SuppressWarnings("ThrowCaughtLocally")
	@Deprecated
	//todo CHG059738 �������
    private void testProviderMobilebankCompatible(BillingServiceProvider provider) throws BusinessException, BusinessLogicException
	{
		try
		{
            if (provider.getState() != ServiceProviderState.ACTIVE)
                throw new UncompatibleServiceProviderException("��������� �� ���������.", provider);
            if (StringHelper.isEmpty(provider.getMobilebankCode()))
                throw new UncompatibleServiceProviderException("��������� �� �������� ����������� ���������� �����.", provider);
            if (!provider.isAvailablePaymentsForMApi())
                throw new UncompatibleServiceProviderException("��������� �� ������� � ��������� API.", provider);
			VersionNumber providerMapiVersionNumber = provider.getMapiVersionNumber();
			if (providerMapiVersionNumber.gt(getClientAPIVersion()))
                throw new UncompatibleServiceProviderException("��������� �� ������� � ������ ������ API. ������ �������: " + getClientAPIVersion() +
                        ". ��������� ������� ������� � ������: " + providerMapiVersionNumber, provider);
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
		throw new BusinessException("� ���������� ��� �������� �����");
	}

	@Transactional
	public Long saveAsTemplate(String templateName) throws BusinessException, BusinessLogicException
	{
/*
		//TODO ��������� � ������ 13.0, ������ "ENH057379: ��������� �� �������� ����������", ����������� ������� �.
		document.setTemplate(true);
		executor.fireEvent(new ObjectEvent(DocumentEvent.SAVE, getSourceEvent()));

*/
		template = new NewTemplateSource(document, getDocumentCreationType()).getTemplate();

		TemplateDocumentService.getInstance().addOrUpdate(template);

		try
		{
			// ����� ��������� �������������
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
	 * @return ������ ���������
	 */
	public TemplateDocument getTemplate()
	{
		return template;
	}

	/**
	 * @return ������������� ���������� ���� ���������� ����.
	 */
	public String getSocialNetProviderId()
	{
		return socialNetProviderId;
	}

	/**
	 * @return ������������� ������������ � ���������� ����.
	 */
	public String getSocialNetUserIdentifier()
	{
		return socialNetUserIdentifier;
	}

	/**
	 * @return ��� ���� � ������� �������� ������������� ���������� ����.
	 */
	public String getSocialNetPaymentFieldName()
	{
		return socialNetPaymentFieldName;
	}

	/**
	 * ���������� ����� �������� ������������ �� �������������� ������
	 * @param id - ������������� ������
	 * @return ����� ��������
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
