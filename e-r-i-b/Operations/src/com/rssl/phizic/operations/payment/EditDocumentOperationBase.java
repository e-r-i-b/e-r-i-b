package com.rssl.phizic.operations.payment;

import com.rssl.common.forms.*;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.processing.CompositeFieldValuesSource;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.common.forms.validators.SimpleMessageHolder;
import com.rssl.common.forms.xslt.FormDataBuilder;
import com.rssl.common.forms.xslt.XmlConverter;
import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.modes.ConfirmStrategy;
import com.rssl.phizic.auth.modes.ConfirmStrategyProvider;
import com.rssl.phizic.auth.modes.UserVisitingMode;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.MessageBusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.documents.*;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.converters.FormDataConverter;
import com.rssl.phizic.business.documents.metadata.source.DocumentFieldValuesSource;
import com.rssl.phizic.business.documents.metadata.source.TemplateFieldValueSource;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.limits.GroupRisk;
import com.rssl.phizic.business.longoffer.autopayment.AutoPaymentHelper;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.operations.restrictions.RestrictionViolationException;
import com.rssl.phizic.business.payments.forms.ExtendedFieldBuilderHelper;
import com.rssl.phizic.business.payments.forms.meta.PFRStatementClaimHelper;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.GuestPerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.business.statemachine.MachineState;
import com.rssl.phizic.business.statemachine.PaymentStateMachineService;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.business.util.MaskPaymentFieldUtils;
import com.rssl.phizic.business.xslt.lists.AccountListSource;
import com.rssl.phizic.business.xslt.lists.CardListSource;
import com.rssl.phizic.business.xslt.lists.EntityListsConfig;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.gate.payments.systems.AbstractPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ext.sbrf.strategy.GuestStrategyProvider;
import com.rssl.phizic.operations.payment.support.DbDocumentTarget;
import com.rssl.phizic.operations.payment.support.DocumentTarget;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.utils.StringHelper;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMResult;

/**
 * @author Evgrafov
 * @ created 08.12.2005
 * @ $Author: rtishcheva $
 * @ $Revision: 83828 $
 */

public abstract class EditDocumentOperationBase extends OperationBase implements EditDocumentOperation
{
	private static final String CHARGE_OFF_ACCOUNTS_XML_DICTIONARY = "chargeOffAccounts.xml";
	private static final ServiceProviderService serviceProviderService = new ServiceProviderService();

	private static final String CHARGE_OFF_CARDS_XML_DICTIONARY = "chargeOffCards.xml";

	protected static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	@SuppressWarnings("ProtectedField")
	protected final PaymentStateMachineService stateMachineService = new PaymentStateMachineService();
	private final SimpleService simpleService = new SimpleService();

    protected DocumentTarget target = new DbDocumentTarget();
	private FieldValuesSource additionalFieldValuesSource;
	private FieldValuesSource validateFormFieldValuesSource;

	private String formName = null; //  форма документа
	private String providerName = null; // название поставщика

	protected Metadata metadata;
	protected BusinessDocument document;
	protected StateMachineExecutor executor;

	public void initialize(DocumentSource source) throws BusinessException, BusinessLogicException
	{
		document = source.getDocument();
	    metadata = source.getMetadata();

		executor = new StateMachineExecutor(stateMachineService.getStateMachineByFormName(document.getFormName()));
		executor.setStateMachineEvent(getStateMachineEvent());
		executor.initialize(document);
		setExternalPaymentParams();
    }

	public void initialize(DocumentSource source, FieldValuesSource additionalFieldValuesSource) throws BusinessException, BusinessLogicException
	{
		this.additionalFieldValuesSource = additionalFieldValuesSource;
		initialize(source);
	}

	public FieldValuesSource getFieldValuesSource() throws BusinessException
	{
		if (additionalFieldValuesSource != null)
		{
			if (document.isByTemplate())
			{
				return new CompositeFieldValuesSource(additionalFieldValuesSource, getDocumentFieldValuesSource(), new TemplateFieldValueSource(document));
			}
			return new CompositeFieldValuesSource(additionalFieldValuesSource, getDocumentFieldValuesSource());
		}
		return getDocumentFieldValuesSource();
	}

	/**
	 * Источник данных документа
	 * @return  Источник значеий полей
	 * @throws BusinessException
	 */
	public FieldValuesSource getDocumentFieldValuesSource() throws BusinessException
	{
		return new DocumentFieldValuesSource(metadata, document);
	}

	public FieldValuesSource getValidateFormFieldValuesSource()
	{
		return validateFormFieldValuesSource;
	}

	protected void setValidateFormFieldValuesSource(FieldValuesSource fieldValuesSource)
	{
		this.validateFormFieldValuesSource = fieldValuesSource;
	}

	public Metadata getMetadata()
    {
        return metadata;
    }

	public String getMetadataPath() throws BusinessException
	{
		return PaymentOperationHelper.calculateMetadataPath(metadata, document);
	}

	/**
	 * Обновить платеж введенными данными
	 * @param formDataMap введенные данные
	 * @throws BusinessException
	 */
	public void updateDocument(Map<String, Object> formDataMap) throws BusinessException, BusinessLogicException
	{
	    try
	    {
			FormDataBuilder dataBuilder = new FormDataBuilder();
			dataBuilder.appentAllFileds(metadata.getForm(), formDataMap);

			XmlConverter converter = metadata.createConverter("xml");
		    converter.setParameter("documentState", document.getState().getCode());
		    converter.setParameter("mode", "update");
			converter.setData(dataBuilder.getFormData());

			DOMResult result = new DOMResult();
			converter.convert(result);

			document.readFromDom((Document) result.getNode(), null);
	    }
	    catch (DocumentException e)
	    {
		    throw new BusinessException(e);
	    }
	    catch (MessageDocumentLogicException e)
	    {
		    throw new MessageBusinessLogicException(e);
	    }
	    catch (DocumentLogicException e)
	    {
		    throw new BusinessLogicException(e);
	    }
    }

	public void saveDocument() throws BusinessException
	{
		target.save(document);
	}

	@Transactional
    public Long save() throws BusinessException, BusinessLogicException
	{
	    executor.fireEvent(new ObjectEvent(DocumentEvent.SAVE, getSourceEvent()));

	    saveDocument();

		resetConfirmStrategy();
        return document.getId();
    }

	protected void resetConfirmStrategy() throws BusinessException
	{
		try
		{
			// сброс стратегии подтверждения
			ConfirmStrategy confirmStrategy = getConfirmStrategyProvider().getStrategy();
			confirmStrategy.reset(AuthModule.getAuthModule().getPrincipal().getLogin(), getDocument());
		}
		catch(SecurityDbException e)
		{
			throw new BusinessException(e);
		}
	}

	public ConfirmStrategyProvider getConfirmStrategyProvider()
	{
		//Для гостевого входа
		if (PersonHelper.isGuest())
			return new GuestStrategyProvider(AuthModule.getAuthModule().getPrincipal());
		return super.getConfirmStrategyProvider();
	}

	@Transactional
    public Long edit() throws BusinessException, BusinessLogicException
	{
	    executor.fireEvent(new ObjectEvent(DocumentEvent.EDIT, getSourceEvent()));
	    saveDocument();
        return document.getId();
    }

	@Transactional
    public Long saveAsTemplate(String templateName) throws BusinessException, BusinessLogicException
	{
		// do nothing
		return null;
	}

	public void saveAsInitial() throws BusinessException, BusinessLogicException
	{
		executor.fireEvent(new ObjectEvent(DocumentEvent.INITIAL, getSourceEvent()));
		saveDocument();
	}

	@Transactional
	public Long saveDraft() throws BusinessException, BusinessLogicException
	{
	    executor.fireEvent(new ObjectEvent(DocumentEvent.SAVEASDRAFT, getSourceEvent()));
		saveDocument();
	    return document.getId();
	}

	public void doWaitTM() throws BusinessException, BusinessLogicException
	{
		ObjectEvent event = new ObjectEvent(DocumentEvent.DOWAITTM, ObjectEvent.EMPLOYEE_EVENT_TYPE);
		executor.fireEvent(event);
		saveDocument();
	}

	public BusinessDocument getDocument()
	{
		return document;
	}

	public CreationType getDocumentCreationType()
	{
		return document.getCreationType();
	}
	/**
	 * @return ID документа (для логгирования)
	 */
    public Long getDocumentId()
    {
        return document !=null ? document.getId() : null;
    }

	public boolean isLongOffer()
	{
		if (!(document instanceof AbstractLongOfferDocument))
		{
			return false;
		}
		return document.isLongOffer();
	}

	@Transactional
	public void makeLongOffer(Map<String, Object> formDataMap) throws BusinessException, BusinessLogicException
	{
		if (!(document instanceof AbstractLongOfferDocument))
		{
			throw new RestrictionViolationException("Документ не может быть регулярным");
		}
		AbstractLongOfferDocument longOffer = (AbstractLongOfferDocument) document;
		longOffer.setLongOffer(true);
		// обновляем документ в случае если пришли данные для обновления
		if(formDataMap != null)
			updateDocument(formDataMap);

		// если автоподписка или автоплатеж
		if (DocumentHelper.isTwoStepLongOffer(longOffer))
		{
			try
			{
				//инициируем вызов обработчиков события сохранить
				executor.fireEvent(new ObjectEvent(DocumentEvent.SAVE, getSourceEvent()));

				saveDocument();
			}
			catch (BusinessException e)
			{
				longOffer.setLongOffer(false);
				throw e;
			}
			catch (BusinessLogicException e)
			{
				longOffer.setLongOffer(false);
				throw e;
			}
		}
	}

	public <E> ErrorsCollector<E> getDocumentErrors(ErrorsCollector<E> errorsCollector) throws BusinessException
	{
		// TODO: CHG026415: Обобщить механизм передачи ошибок/сообщений уровня поля

		if (document instanceof PFRStatementClaim)
		{
			String error = PFRStatementClaimHelper.getClaimErrorText((PFRStatementClaim) document);
			if (!StringHelper.isEmpty(error))
				errorsCollector.add(new SimpleMessageHolder(error));
			return errorsCollector;
		}

		if (!(document instanceof AbstractPaymentSystemPayment))
		{
			//не биллинговые платежи - возвращаем коллектор.
			return  errorsCollector;
		}
		AbstractPaymentSystemPayment billingPayment = (AbstractPaymentSystemPayment) document;
		try
		{

			List<Field> extendedFields = billingPayment.getExtendedFields();
			if (extendedFields == null)
			{
				return errorsCollector;
			}
			for (Field field : extendedFields)
			{
				if (!StringHelper.isEmpty(field.getError()))
				{
					errorsCollector.add(StringHelper.getEmptyIfNull(field.getValue()), ExtendedFieldBuilderHelper.adaptField(field), new SimpleMessageHolder(field.getError()));
				}
			}
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
		return errorsCollector;
	}

	/**
	 * получить ссылку на ресурс списания.
	 * @return ссылка. может отсутствовать для новых документов, для несохраненных копий или оплат по старым шаблонам
	 * @throws BusinessException
	 */
	public PaymentAbilityERL getChargeOffResourceLink() throws BusinessException
	{
		if (!(document instanceof AbstractAccountsTransfer))
		{
			//неплатежные документы не содержат источник списания.
			return  null;
		}

		try
		{
			return ((AbstractAccountsTransfer)document).getChargeOffResourceLink();
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * получить тип ресурса списания.
	 * @return тип ресурса списания.
	 * @throws BusinessException
	 */
	public ResourceType getChargeOffResourceType() throws BusinessException
	{
		if (!(document instanceof AbstractAccountsTransfer))
		{
			//неплатежные документы не содержат источник списания.
			return null;
		}

		try
		{
			return ((AbstractAccountsTransfer) document).getChargeOffResourceType();
		}
		catch (IllegalStateException e)
		{
			throw new BusinessException(e);
		}
	}

	private void setExternalPaymentParams()
	{
		setFormName(document.getFormName());
		if (document instanceof AbstractAccountsTransfer)
			setProviderName(((AbstractAccountsTransfer) document).getReceiverName());
	}

	public String getFormName()
	{
		return formName;
	}

	public void setFormName(String formName)
	{
		this.formName = formName;
	}

	public boolean isExternal() throws BusinessException
	{
		return DocumentHelper.isExternalPayment(document);
	}

	public String getProviderName()
	{
		return providerName;
	}

	public void setProviderName(String providerName)
	{
		this.providerName = providerName;
	}

	/**
	 * @return получить статус документа в терминах машины состояний. или null в случае некорректного состояния.
	 */
	public MachineState getDocumentSate()
	{
		return executor.getCurrentState();
	}

	/**
	 * получить перечень доступных источников списания для платежей
	 * @return список ссылок на платежные внешние ресурсы (never null can be empty)
	 */
	public List<PaymentAbilityERL> getChargeOffResources() throws BusinessException, BusinessLogicException
	{
		List<PaymentAbilityERL> result = new ArrayList<PaymentAbilityERL>();
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		result.addAll(personData.getAccounts(new ActiveDebitRurAccountFilter()));
		result.addAll(personData.getCards(new ActiveNotVirtualNotCreditOwnCardFilter()));
		return result;
	}

	public String buildFormHtml(FieldValuesSource fieldValuesSource, String webRoot, String resourceRoot, String skinUrl, boolean isDefaultShow) throws BusinessException
	{
		Source formData = new FormDataConverter(metadata.getForm(), fieldValuesSource).toFormData();
		XmlConverter converter = metadata.createConverter("html");
		converter.setData(formData);
		converter.setParameter("mode", "edit");
		converter.setParameter("personAvailable", PersonContext.isAvailable());
		converter.setParameter("webRoot", webRoot);
        converter.setParameter("resourceRoot", resourceRoot);
        converter.setParameter("application", ApplicationInfo.getCurrentApplication().name());
        converter.setParameter("skinUrl", skinUrl);
		converter.setParameter("longOffer", isLongOffer());
		converter.setParameter("documentState", getDocument().getState().getCode());
		converter.setParameter("isTemplate", false);
		converter.setParameter("byTemplate", document.isByTemplate());
		converter.setParameter("isDefaultShow", isDefaultShow);
		converter.setParameter("postConfirmCommission", DocumentHelper.postConfirmCommissionSupport(document));
		boolean isAnonymous = true;
		boolean isGuest = false;
		PersonDataProvider personDataProvider = PersonContext.getPersonDataProvider();
		if (personDataProvider != null && personDataProvider.getPersonData() != null)
		{
			ActivePerson person = personDataProvider.getPersonData().getPerson();
			if (person != null)
				isAnonymous = person.getCreationType() == com.rssl.phizic.common.types.client.CreationType.ANONYMOUS;
			if (person instanceof GuestPerson)
				isGuest = true;
		}
		converter.setParameter("isAnonymous", isAnonymous);
		converter.setParameter("isGuest", isGuest);

		if (!isGuest)
		{
			converter.setDictionary(CHARGE_OFF_ACCOUNTS_XML_DICTIONARY, buildChargeOffAccountsXMLDictionary());
			converter.setDictionary(CHARGE_OFF_CARDS_XML_DICTIONARY,    buildChargeOffCardsXMLDictionary());
			converter.setParameter("loginedFromEmployeeByCard", PersonHelper.getLastClientLogin().getLastUserVisitingMode() == UserVisitingMode.EMPLOYEE_INPUT_BY_CARD);
		}

		try
		{
			return converter.convert().toString();
		}
		catch (FormException e)
		{
			throw new BusinessException("Ошибка при создании HTML с экранной формой платежа " + metadata.getName() +
					", id документа " + document.getId(), e);
		}
	}

	/**
	 * @return Source со счетами, доступными в качестве источника списания
	 */
	private Source buildChargeOffAccountsXMLDictionary() throws BusinessException
	{
		try
		{
			List<PaymentAbilityERL> chargeOffResources = getChargeOffResources();
			final List<AccountLink> accountLinks = ExternalResourceUtils.selectAccountLinks(chargeOffResources);

			EntityListsConfig config = ConfigFactory.getConfig(EntityListsConfig.class);
			AccountListSource accountListSource = new AccountListSource(config.getListDefinition(CHARGE_OFF_ACCOUNTS_XML_DICTIONARY))
			{
				@Override
				protected List<AccountLink> getAccountsList()
				{
					return accountLinks;
				}
			};

			return accountListSource.getSource(Collections.<String, String>emptyMap());
		}
		catch (BusinessLogicException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @return Source с картами, доступными в качестве источника списания
	 */
	private Source buildChargeOffCardsXMLDictionary() throws BusinessException
	{
		try
		{
			List<PaymentAbilityERL> chargeOffResources = getChargeOffResources();
			final List<CardLink> cardLinks = ExternalResourceUtils.selectCardLinks(chargeOffResources);

			EntityListsConfig config = ConfigFactory.getConfig(EntityListsConfig.class);
			CardListSource cardListSource = new CardListSource(config.getListDefinition(CHARGE_OFF_CARDS_XML_DICTIONARY))
			{
				@Override
				protected List<CardLink> getCards(PersonData personData)
				{
					return cardLinks;
				}
			};

			return cardListSource.getSource(Collections.<String, String>emptyMap());
		}
		catch (BusinessLogicException e)
		{
			throw new BusinessException(e);
		}
	}

	public String buildMobileXml(FieldValuesSource fieldValuesSource) throws BusinessException
	{
		try
		{
			FieldValuesSource valuesSource = new CompositeFieldValuesSource(fieldValuesSource, getDocumentFieldValuesSource());

			Source formData = new FormDataConverter(metadata.getForm(), valuesSource).toFormData();
			XmlConverter converter = metadata.createConverter("mobile");
			converter.setData(formData);
			if (document.getId() != null)
				converter.setParameter("documentId", document.getId());
			converter.setParameter("documentStatus", document.getState().getCode());
			converter.setParameter("mode", "edit");
			converter.setParameter("mobileApiVersion", getClientAPIVersion().toString());
			converter.setParameter("longOffer", isLongOffer());
			converter.setParameter("isByTemplate", document.isByTemplate());
			//степень риска ПУ
			if (document instanceof JurPayment)
			{
				GroupRisk groupRisk = serviceProviderService.findGroupRiskById(((JurPayment) document).getReceiverInternalId());
				if (groupRisk != null)
					converter.setParameter("groupRiskRank", groupRisk.getRank().toString());
			}
			//из доавторизационной зоны
			converter.setParameter("isMobileLimitedScheme", MobileApiUtil.isLimitedScheme());
			converter.setParameter("isMobileLightScheme", MobileApiUtil.isLightScheme());

			return converter.convert().toString();
		}
		catch (FormException e)
		{
			throw new BusinessException("Ошибка при создании XML-представления платежа mAPI " + metadata.getName() +
					", id документа " + document.getId(), e);
		}
	}

	public String buildSocialXml(FieldValuesSource fieldValuesSource) throws BusinessException
	{
		try
		{
			FieldValuesSource valuesSource = new CompositeFieldValuesSource(fieldValuesSource, getDocumentFieldValuesSource());

			Source formData = new FormDataConverter(metadata.getForm(), valuesSource).toFormData();
			XmlConverter converter = metadata.createConverter("social");
			converter.setData(formData);
			if (document.getId() != null)
				converter.setParameter("documentId", document.getId());
			converter.setParameter("documentStatus", document.getState().getCode());
			converter.setParameter("mode", "edit");
			converter.setParameter("longOffer", isLongOffer());
			converter.setParameter("isByTemplate", document.isByTemplate());
			//степень риска ПУ
			if (document instanceof JurPayment)
			{
				GroupRisk groupRisk = serviceProviderService.findGroupRiskById(((JurPayment) document).getReceiverInternalId());
				if (groupRisk != null)
					converter.setParameter("groupRiskRank", groupRisk.getRank().toString());
			}
			//из доавторизационной зоны
			converter.setParameter("isMobileLimitedScheme", MobileApiUtil.isLimitedScheme());
			converter.setParameter("isMobileLightScheme", MobileApiUtil.isLightScheme());

			return converter.convert().toString();
		}
		catch (FormException e)
		{
			throw new BusinessException("Ошибка при создании XML-представления платежа mAPI " + metadata.getName() +
					", id документа " + document.getId(), e);
		}
	}

	public String buildATMXml(FieldValuesSource fieldValuesSource) throws BusinessException
	{
		try
		{
			FieldValuesSource valuesSource = new CompositeFieldValuesSource(fieldValuesSource, getDocumentFieldValuesSource());

			Source formData = new FormDataConverter(metadata.getForm(), valuesSource).toFormData();
			XmlConverter converter = metadata.createConverter("atm");
			converter.setData(formData);
			if (document.getId() != null)
				converter.setParameter("documentId", document.getId());
			converter.setParameter("documentStatus", document.getState().getCode());
			converter.setParameter("longOffer", isLongOffer());
			converter.setParameter("mode", "edit");
			converter.setParameter("longOffer", isLongOffer());
			converter.setParameter("isByTemplate", document.isByTemplate());
			converter.setParameter("quickLongOfferAvailable", AutoPaymentHelper.isAutoPaymentAllowed(document));
			//степень риска ПУ
			if (document instanceof JurPayment)
			{
				GroupRisk groupRisk = serviceProviderService.findGroupRiskById(((JurPayment) document).getReceiverInternalId());
				if (groupRisk != null)
					converter.setParameter("groupRiskRank", groupRisk.getRank().toString());
			}

			return converter.convert().toString();
		}
		catch (FormException e)
		{
			throw new BusinessException("Ошибка при создании XML-представления платежа atmAPI " + metadata.getName() +
					", id документа " + document.getId(), e);
		}
	}

	/**
	 * Источник событий для машины состояния
	 * @return client|employee|system
	 */
	protected String getSourceEvent()
	{
		return ObjectEvent.CLIENT_EVENT_TYPE;
	}

	/**
	 * @return персона
	 * @throws BusinessException
	 */
	public ActivePerson getPerson() throws BusinessException
	{
		return PersonContext.getPersonDataProvider().getPersonData().getPerson();
	}

	/**
	 * @return информация по полям документов из профиля, которые нужно маскировать
	 * @throws BusinessException
	 */
	public Map<String, String> getPersonDocumentsMaskInfo() throws BusinessException
	{
		return MaskPaymentFieldUtils.buildDocumentSeriesAndNumberValues(
				PersonHelper.getDocumentForProfile(getPerson().getPersonDocuments()));
	}

	public boolean upgrade() throws BusinessLogicException, BusinessException
	{
		// проверка поддержки документом актуализации, если не поддерживает редирект не требуется
		if (!document.isUpgradable())
			return false;
		ApplicationInfo applicationInfo = ApplicationConfig.getIt().getApplicationInfo();
		Application application = applicationInfo.getApplication();
		MachineState oldState = executor.getCurrentState();
		String oldClientForm = "";
		if (Application.PhizIC == application || applicationInfo.isMobileApi() || applicationInfo.isATM() || applicationInfo.isSocialApi())
		{
		    oldClientForm = executor.getCurrentState().getClientForm();
		}
		if (Application.PhizIA == application)
		{
			oldClientForm = executor.getCurrentState().getEmployeeForm();
		}
		// запускаем событие актализации
		executor.fireEvent(new ObjectEvent(DocumentEvent.UPGRADE, getSourceEvent()));
		MachineState newState = executor.getCurrentState();
		String newClientForm = "";

		if (Application.PhizIC == application || applicationInfo.isMobileApi() || applicationInfo.isATM() || applicationInfo.isSocialApi())
		{
			newClientForm = executor.getCurrentState().getClientForm();
		}
		if (Application.PhizIA == application)
		{
			newClientForm = executor.getCurrentState().getEmployeeForm();
		}

		//Если статус документа изменился, апдейтим документ в базе
		if (!oldState.equals(newState))
		{
			simpleService.update(document);
		}

		// если client-form не изменился - актулизация произведена, редирект не требуется
		return !oldClientForm.equals(newClientForm);
	}

	public void saveNewAccountInLoanClaim(Long accountClaimId) throws BusinessException
	{
		if (! (document instanceof ExtendedLoanClaim))
			throw new BusinessException("Ожидается ExtendedLoanClaim");

		ExtendedLoanClaim extendedLoanClaim = (ExtendedLoanClaim) document;

		AccountOpeningClaim accountClaim = simpleService.findById(AccountOpeningClaim.class, accountClaimId);
		if (accountClaim == null)
			throw new BusinessException("Не найдена заявка на открытие счета с id " + accountClaimId);

		String accountNumber = accountClaim.getReceiverAccount();
		if (StringHelper.isEmpty(accountNumber))
			throw new BusinessException("Не указан номер счета в заявке на открытие счета с id " + accountClaimId);

		extendedLoanClaim.setNewOpenedAccount(accountNumber);
		saveDocument();
	}
}
