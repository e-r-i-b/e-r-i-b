package com.rssl.phizic.operations.payment;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.BankDictionaryService;
import com.rssl.phizic.business.dictionaries.billing.Billing;
import com.rssl.phizic.business.dictionaries.billing.BillingService;
import com.rssl.phizic.business.dictionaries.providers.AccountType;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderState;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.payments.source.NewDocumentSource;
import com.rssl.phizic.business.documents.payments.validators.DocumentValidator;
import com.rssl.phizic.business.documents.payments.validators.IsOwnDocumentValidator;
import com.rssl.phizic.business.documents.templates.TemplateHelper;
import com.rssl.phizic.business.documents.templates.validators.OwnerTemplateValidator;
import com.rssl.phizic.business.documents.templates.validators.StateTemplateValidator;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.business.statemachine.MachineState;
import com.rssl.phizic.business.statemachine.PaymentStateMachineService;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.AbstractPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.PaymentRecipientGateService;
import com.rssl.phizic.gate.payments.systems.recipients.Recipient;
import com.rssl.phizic.business.regions.RegionHelper;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.*;

/**
 * @author krenev
 * @ created 06.03.2011
 * @ $Author$
 * @ $Revision$
 * Операция для получения информации и обработки первого шага оплаты юрику
 * Предоставляет информацию по значениям полей получателя и содержит методы выбора следующего шага(формы платежа)
 */
public class EditJurPaymentOperation extends CreateFormPaymentOperation
{
	private static final SimpleService simpleService = new SimpleService();
	private static final ServiceProviderService serviceProviderService = new ServiceProviderService();
	private static final BankDictionaryService bankDictionaryService = new BankDictionaryService();
	private static final BillingService billingService = new BillingService();
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();
	private final PaymentStateMachineService stateMachineService = new PaymentStateMachineService();
	private static final DocumentValidator validator = new IsOwnDocumentValidator();
	private String receiverAccount;
	private String receiverINN;
	private String receiverBIC;
	private String receiverCodePoint;
	private String operationCode;
	private boolean isEditPayment;
	private boolean isAutoPayment;
	private PaymentAbilityERL chargeOffResource;
	private Long templateId;
	protected List<BillingServiceProvider> serviceProviders;
	private Map<String, String[]> regions = new HashMap<String, String[]>();
	/**
	 * Проинициализировать опрацию данными на основе существующего документа
	 * @param fromResource источник списания, если задан - используется вместо источника в документе
	 * @param paymentId идентфикатор платежа
	 * @param edit признак того, что соверщается редактирование существующего документа(не копия) 
	 */
	public void initialize(String fromResource, Long paymentId, boolean edit, boolean isAutoPayment) throws BusinessException, BusinessLogicException
	{
		this.isEditPayment = edit;
		this.isAutoPayment = isAutoPayment;
		if (paymentId == null)
			throw new BusinessException("Не задан идентификатор платежа");

		document = businessDocumentService.findById(paymentId);
		if (document == null)
		{
			throw new ResourceNotFoundBusinessException("Не найден документ с идентфикатором " + paymentId, BusinessDocument.class);
		}
		initialize(fromResource, document);
	}

	/**
	 * Проинициализировать опрацию данными на основе существующего шаблона
	 * @param fromResource источник списания, если задан - используется вместо источника в шаблоне
	 * @param reminder признак оплаты напоминания
	 * @param templateId идентфикатор шаблона
	 */
	public void initialize(String fromResource, Long templateId, boolean reminder) throws BusinessException, BusinessLogicException
	{
		this.templateId = templateId;
		if (templateId == null)
			throw new BusinessException("Не задан идентификатор шаблона");

		DocumentSource source = new NewDocumentSource(templateId, CreationType.internet, reminder, new OwnerTemplateValidator(), new StateTemplateValidator());
		initialize(fromResource, source.getDocument());
	}

	/**
	 * Проинициализировать опрацию данными на основе существующего шаблона
	 * @param fromResource источник списания по умолчанию
	 */
	public void initialize(String fromResource) throws BusinessException, BusinessLogicException
	{
		this.chargeOffResource = findFromResource(fromResource);
	}

	private PaymentAbilityERL findFromResource(String fromResource) throws BusinessException, BusinessLogicException
	{
		if (StringHelper.isEmpty(fromResource))
		{
			return null;
		}
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		ExternalResourceLink link = personData.getByCode(fromResource);
		if (!(link instanceof PaymentAbilityERL))
		{
			throw new BusinessException("Оплата с ресурса " + fromResource + " недопустима");
		}
		return (PaymentAbilityERL) link;
	}

	private void initialize(String fromResource, BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		if (document == null)
		{
			throw new IllegalArgumentException("document не может быть null");
		}
		if (!isAutoPayment && (isEditPayment && !editable(document)))
		{
			throw new BusinessLogicException("Документ не может быть отредактирован");
		}
		if (!(document instanceof RurPayment))
		{
			throw new BusinessException("Некорректный тип документа " + document.getId() + ". Ожидается наследник RurPayment");
		}
		this.document=document;
		RurPayment payment = (RurPayment) document;

		getDocumentValidator().validate(document);  //проверяем права
		//инициализируем источник списания


		if (StringHelper.isEmpty(fromResource))
		{
			try
			{
				//явно не задан берем из докумнета
				PaymentAbilityERL link = payment.getChargeOffResourceLink();
				if (link != null)
				{
					this.chargeOffResource = link;
				}
			}
			catch (DocumentException e)
			{
				throw new BusinessException(e);
			}
		}
		else
		{
			this.chargeOffResource = findFromResource(fromResource);
		}

		receiverAccount = payment.getReceiverAccount();
		receiverINN = payment.getReceiverINN();
		receiverBIC = payment.getReceiverBIC();
		operationCode = payment.getOperationCode();
		if (payment instanceof JurPayment)
		{
			JurPayment jurPayment = (JurPayment) payment;
			receiverCodePoint = jurPayment.getReceiverPointCode();
		}
	}

	/**
	 * проверка, возможно ли редактирование документа
	 * @param document документ для проверки.
	 * @return да/нет
	 */
	private boolean editable(BusinessDocument document)
	{
		MachineState state = stateMachineService.getStateMachineByFormName(document.getFormName()).getObjectMachineState(document);
		boolean isInitialAcceptable = state.isEventAcceptable(new ObjectEvent(DocumentEvent.INITIAL, ObjectEvent.CLIENT_EVENT_TYPE));
		boolean isEditAcceptable    = state.isEventAcceptable(new ObjectEvent(DocumentEvent.EDIT, ObjectEvent.CLIENT_EVENT_TYPE));

		return isInitialAcceptable || isEditAcceptable;
	}

	/**
	 * @return уникальный код источника списания.
	 */
	public PaymentAbilityERL getChargeOffResource()
	{
		return chargeOffResource;
	}

	public List<PaymentAbilityERL> getChargeOffResources() throws BusinessException, BusinessLogicException
	{
		List<PaymentAbilityERL> result = new ArrayList<PaymentAbilityERL>();

		//если подразделение поддерживает оплату со счета
		if (isFromAccountPaymentAllow())
		{
			List<AccountLink> list = PersonContext.getPersonDataProvider().getPersonData().getAccounts(new ActiveDebitRurAccountFilter());
			filterNotPermittedForFinancialTransactions(list);
			result.addAll(list);
		}

		if (isFromCardPaymentAllow())
		{
			result.addAll(PersonContext.getPersonDataProvider().getPersonData().getCards(new ActiveNotVirtualNotCreditOwnCardFilter()));
		}

		return result;
	}

	/**
	 * @return счет получателя
	 */
	public String getReceiverAccount()
	{
		return receiverAccount;
	}

	/**
	 * @return ИНН получателя
	 */
	public String getReceiverINN()
	{
		return receiverINN;
	}

	/**
	 * @return БИК бинка получателя
	 */
	public String getReceiverBIC()
	{
		return receiverBIC;
	}

	/**
	 * @return внешний идентфикатор получателя.
	 */
	public String getReceiverCodePoint()
	{
		return receiverCodePoint;
	}

	public void setChargeOffResource(PaymentAbilityERL chargeOffResource)
	{
		this.chargeOffResource = chargeOffResource;
	}

	public void setReceiverAccount(String receiverAccount)
	{
		this.receiverAccount = receiverAccount;
	}

	public void setReceiverINN(String receiverINN)
	{
		this.receiverINN = receiverINN;
	}

	public void setReceiverBIC(String receiverBIC)
	{
		this.receiverBIC = receiverBIC;
	}

	/**
	 * Найти поставщиков в биллинге по умолчанию (если есть)
	 * поиск производится по реквизитам ReceiverAccount, ReceiverINN, ReceiverBIC, с учетом типа источника списания ChargeOffResource
	 * (Для оплаты с карт не испольуется поск в биллинге по умолчанию)
	 * @return список поставщиков или пустой список, если отсутсвует биллинг или поставщиков в нем нет
	 */
	public List<Recipient> findDefaultBillingRecipients() throws BusinessException, BusinessLogicException
	{
		if (isCardsTransfer())
		{
			return Collections.emptyList(); //для карт не идем в биллинг по умолчанию.
		}

		//получаем биллинг по умолчанию для ТБ счета списания
		Billing billing = getDefaultBilling();
		if (billing == null)
		{
			return Collections.emptyList();//Биллинга по умолчанию нет - пустой список.
		}
		//биллинг нашли - ищем поставщиков в нем.
		PaymentRecipientGateService service = GateSingleton.getFactory().service(PaymentRecipientGateService.class);
		try
		{
			return service.getRecipientList(receiverAccount, receiverBIC, receiverINN, billing);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}

	/**
	 * получить биллинг по умолчанию для ресурса источника списания
	 * @return биллинг по умолчанию для ресурса источника списания
	 * @throws BusinessException
	 */
	public Billing getDefaultBilling() throws BusinessException
	{
		return billingService.findDefaultBilling(chargeOffResource.getOffice());
	}

	/**
	 * Получить по внешнему идентификатору наименование получателя.
	 * @param externalProviderId внешний идентфикатор поставщика.
	 * @return наименование
	 */
	public String getExtenalProviderName(String externalProviderId) throws BusinessException, BusinessLogicException
	{
		List<Recipient> billingRecipients = findDefaultBillingRecipients();
		for (Recipient billingRecipient : billingRecipients)
		{
			if (billingRecipient.getSynchKey().equals(externalProviderId))
			{
				return billingRecipient.getName();
			}
		}
		throw new BusinessException("Не найдено имя получателя с идентфикатором" + externalProviderId);
	}

	/**
	 * Поиск поставщика по ID в нашей базе
	 * @param id - id поставщика
	 * @return  поставщик
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public BillingServiceProvider findRecipient(Long id)  throws BusinessException, BusinessLogicException
	{
		return (BillingServiceProvider) serviceProviderService.findById(id);
	}
	/**
	 * Найти поставщиков в справочнике поставщиков
	 * поиск производится по реквизитам ReceiverAccount, ReceiverINN, ReceiverBIC
	 * с учетои типа источника списания и доступных для него поставщиков.
	 */
	public void findRecipient() throws BusinessException, BusinessLogicException
	{
		//зависимости от типа источника списания используем нужные типы поставщиков
		//если клиент списывает со счета, проверяем также поддерживает ли подразделение клиента списание со счетов
		AccountType[] accountTypes = (AccountType[]) getAccountType(false);

		// Если доступных поставщиков для подразделения клиента нет, то будет платить через обычный перевод юрику.
		if (accountTypes == null)
		{
			serviceProviders = null;
			return;
		}

		DetachedCriteria criteria = DetachedCriteria.forClass(BillingServiceProvider.class);
		criteria.add(Expression.eq("state", ServiceProviderState.ACTIVE));
		criteria.add(Expression.eq("account", receiverAccount));
		criteria.add(Expression.eq("INN", receiverINN));
		criteria.add(Expression.eq("BIC", receiverBIC));

		if (ApplicationUtil.isMobileApi())
			criteria.add(Expression.eq("availablePaymentsForMApi", true));

		criteria.add(Expression.in("accountType", accountTypes));

		serviceProviders = simpleService.find(criteria);
	}

	public List<BillingServiceProvider> getServiceProviders()
	{
		return serviceProviders;
	}

	/**
	 * Найти поставщиков в справочнике поставщиков
	 * поиск производится по реквизитам ReceiverAccount, ReceiverINN, ReceiverBIC
	 * с учетои типа источника списания и доступных для него поставщиков.
	 * @return список доступных поставщиков, отсортированный по регионам, или null, если остутствуют удовлетворяющие условию
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public List<Object[]> findRecipients() throws BusinessException, BusinessLogicException
	{
		//Далее в зависимости от типа источника списания используем нужные типы поставщиков
		//если клиент списывает со счета, проверяем также поддерживает ли подразделение клиента списание со счетов
		String[]  accountType = (String[]) getAccountType(true);

		// Если доступных поставщиков для подразделения клиента нет, то будет платить через обычный перевод юрику.
		if (accountType == null)
		{
			return Collections.emptyList();
		}

		List<Object[]> result = new ArrayList<Object[]>();
		// Добавляем поставщиков из иерархии домашнего региона
		result.addAll(getProviderByRequisitesAndRegion(accountType, RegionHelper.getCurrentRegion()));
		// Добавляем поставщиков из всех остальных регионов
		result.addAll(getProviderByRequisites(accountType));
		if (!result.isEmpty())
			updateRegionsList(result);

		return result;
	}

	/**
	 * Подготовить общий платеж юрику
	 * @return идентфикатор подготовленного платежа. может отсутсвовать. если платеж не нуждается в подготовке.
	 */
	@Transactional
	public Long prepareJurPayment() throws BusinessException, BusinessLogicException
	{
		if (!isEditPayment)
		{
			//если не происходит редактирование существующего документа ничего делать не надо - будет создан новый документ
			return null;
		}
		//смотрим - сменился ли тип платежа
		if (!isBillingPayment())
		{
			ResidentBank bank = bankDictionaryService.findByBIC(receiverBIC);
			if (bank == null)
			{
				throw new BusinessException("не найден банк в справочнике с БИКом " + receiverBIC);
			}
			//не сменился  - просто обновляем реквизиты получателя.
			RurPayment payment = (RurPayment) document;
			payment.setReceiverBank(bank);
			payment.setReceiverAccount(receiverAccount);
			payment.setReceiverINN(receiverINN);
			businessDocumentService.addOrUpdate(payment);
			return payment.getId();
		}
		//сменился - убиваем старый платеж
		removeOldDocument();
		//документ будет создаваться заново.
		return null;
	}

	/**
	 * Подготовить биллинговый платеж для оплаты клиентом в адрес внешнего поставщика.
	 * @param externalProviderId идентфикатор внешнего получателя
	 * @return идентфикатор подготовленного платежа. может отсутсвовать. если платеж не нуждается в подготовке.
	 */
	@Transactional
	public Long prepareExternalProviderPayment(String externalProviderId) throws BusinessException, BusinessLogicException
	{
		if (!isEditPayment)
		{
			//если не происходит редактирование существующего документа ничего делать не надо - будет созхдан новый документа
			return null;
		}
		//смотрим - сменился ли тип платежа
		if (isBillingPayment())
		{
			//не сменился  - смотрим сменился ли получатель.
			JurPayment jurPayment = (JurPayment) document;
			if (externalProviderId != null && externalProviderId.equals(jurPayment.getReceiverPointCode()))
			{
				//не сменился - менять нечего.
				return document.getId();
			}
		}
		//либо получатель сменился либо тип платежа сменился
		//убиваем старый платеж
		removeOldDocument();
		//документ будет создаваться заново.
		return null;
	}

	private void removeOldDocument() throws BusinessException, BusinessLogicException
	{
		StateMachineExecutor executor = new StateMachineExecutor(stateMachineService.getStateMachineByFormName(document.getFormName()));
		executor.initialize(document);
		executor.fireEvent(new ObjectEvent(DocumentEvent.DELETE, ObjectEvent.SYSTEM_EVENT_TYPE));
		businessDocumentService.addOrUpdate(document);
	}

	/**
	 * @return является ли платеж на основе, которого создается документ или редактируется биллинговым
	 */
	public boolean isBillingPayment()
	{
		GateExecutableDocument gateDocument = (GateExecutableDocument) document;
		return AbstractPaymentSystemPayment.class.isAssignableFrom(gateDocument.getType());
	}

	/**
	 * Подготовить биллинговый платеж для оплаты клиентом в адрес поставщика из справочника.
	 * @return идентфикатор подготовленного платежа. может отсутсвовать. если платеж не нуждается в подготовке.
	 */
	@Transactional
	public Long prepareInternalProviderPayment(Long serviceProviderId) throws BusinessException, BusinessLogicException
	{
		if (!isEditPayment)
		{
			//если не происходит редактирование существующего документа ничего делать не надо - будет создан новый документ
			return null;
		}
		//смотрим - сменился ли тип платежа
		if (isBillingPayment())
		{
			//не сменился  - смотрим сменился ли получатель.
			JurPayment jurPayment = (JurPayment) document;
			if (serviceProviderId.equals(jurPayment.getReceiverInternalId()))
			{
				//не сменился - менять нечего.
				return document.getId();
			}
		}
		//либо получатель сменился либо тип платежа сменился
		//убиваем старый платеж
		removeOldDocument();
		//документ будет создаваться заново.
		return null;
	}

	/**
	 * Получить идентифкатор шаблона на основе которого редактируется платеж
	 * @return идентфикатор шаблона или null, если редактирование документа происходит без участия шаблона.
	 */
	public Long getTemplateId()
	{
		return templateId;
	}

	public boolean isEditPayment()
	{
		return isEditPayment;
	}

	public boolean isCardsTransfer()
	{
		return chargeOffResource instanceof CardLink;
	}

	public String getOperationCode()
	{
		return operationCode;
	}

	protected DocumentValidator getDocumentValidator()
	{
		return validator;
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

		if (document == null)
		{
			return true;
		}

		//при оплате по шаблону смена ресурса списания запрещена
		return !(document.isByTemplate() && DocumentHelper.isFromAccountPaymentAllow(document));
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

		if (document == null)
		{
			return true;
		}

		//при оплате по шаблону смена ресурса списания запрещена
		return !(document.isByTemplate() && DocumentHelper.isFromCardPaymentAllow(document));
	}

	/**
	 * @param isStringType вернуть ли строковый массив
	 * @return источники списания
	 * @throws BusinessException
	 */
	private Object[] getAccountType(boolean isStringType) throws BusinessException
	{
		if (isCardsTransfer())
		{
			if (isStringType)
				return new String[]{AccountType.ALL.toString(), AccountType.CARD.toString()};

			return new AccountType[]{AccountType.ALL, AccountType.CARD};
		}
		else
		{
			//для списания со счета подходят поставщики с типом "счета" и "карта/счет"
			if (isStringType)
				return new String[]{AccountType.ALL.toString(), AccountType.DEPOSIT.toString()};

			return new AccountType[]{AccountType.ALL, AccountType.DEPOSIT};
		}
	}

	/**
	 * Добавить поставщиков, найденных в иерархии домашнего региона клиента
	 * @param accountType источники списания
	 * @param currentRegion "домашний" регион
	 * @return списка поставщиков по реквизитам с учетом "домашнего" региона
	 * @throws BusinessException
	 */
	private List<Object[]> getProviderByRequisitesAndRegion(String[] accountType, Region currentRegion) throws BusinessException
	{
		Long currentRegionId = currentRegion == null ? null : currentRegion.getId();
		Long parentRegionId = currentRegion == null || currentRegion.getParent() == null ? null : currentRegion.getParent().getId();
		return serviceProviderService.getProviderByRequisitesAndRegion(receiverAccount,receiverINN, receiverBIC, accountType, parentRegionId, currentRegionId);
	}

	/**
	 * @param accountType источники списания
	 * @return список поставщиков по реквизитам
	 * @throws BusinessException
	 */
	private List<Object[]> getProviderByRequisites(String[] accountType) throws BusinessException
	{
		return serviceProviderService.getProviderByRequisites(receiverAccount, receiverINN, receiverBIC, accountType);
	}

	/**
	 * Получаем регионы для поставщиков ля отображения в результатах поиска
	 * @param providersList  - список поставщиков
	 * @throws BusinessException
	 */
	private void updateRegionsList(List<Object[]> providersList) throws BusinessException
	{
		List<String> providers = new ArrayList<String>();
		for (Object[] res : providersList){
			if (!providers.contains((String) res[6]))
				providers.add((String) res[6]);
		}

		List<Object[]> resultList = serviceProviderService.getRegionsForProviders(providers);
		//если регионов не нашлось выходим
		if (CollectionUtils.isEmpty(resultList))
			return;

		StringBuilder  nameRegions = new StringBuilder();
		String providerId = "";
		String firstRegion = ""; //регион, который отображается в серой рамочке
		boolean needComma = false; //нужен ли разделитель между регионами
		for (Object[] result : resultList)
		{
			String currProviderId = (String) result[0];
			if (providerId.equals(""))
				providerId = currProviderId;
			if (!providerId.equals(currProviderId))
			{
				regions.put(providerId, new String[]{firstRegion, nameRegions.toString()});
				providerId = currProviderId;
				nameRegions =  new StringBuilder();
				needComma = false;
				firstRegion = "";
			}
			if (needComma)
				nameRegions.append(", ");
			if (firstRegion.equals(""))
			{
				firstRegion = (String) result[1];
			}
			else
			{
				nameRegions.append("<a>"+result[1]+"</a>");
				needComma = true;
			}
		}
		regions.put(providerId, new String[]{firstRegion, nameRegions.toString()});
	}

	public Map<String, String[]> getRegions()
	{
		return regions;
	}
}