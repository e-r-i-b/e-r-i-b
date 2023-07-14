package com.rssl.phizic.operations.basket;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizgate.common.basket.RequisitesHelper;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.basket.accountingEntity.AccountingEntity;
import com.rssl.phizic.business.basket.accountingEntity.AccountingEntityBase;
import com.rssl.phizic.business.basket.config.BasketConfig;
import com.rssl.phizic.business.basket.config.ServiceCategory;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscriptionService;
import com.rssl.phizic.business.dictionaries.payment.services.PaymentService;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.CreateInvoiceSubscriptionPayment;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.AutoSubscriptionLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.basket.InvoiceSubscriptionErrorType;
import com.rssl.phizic.common.types.basket.InvoiceSubscriptionState;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.basket.InvoiceConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.longoffer.ExecutionEventType;
import com.rssl.phizic.gate.longoffer.SumType;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoPayStatusType;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoSubscription;
import com.rssl.phizic.gate.payments.longoffer.AutoSubscriptionDetailInfo;
import com.rssl.phizic.gate.payments.longoffer.CardPaymentSystemPaymentLongOffer;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.access.PersonLoginSource;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.*;

/**
 * @author tisov
 * @ created 22.04.14
 * @ $Author$
 * @ $Revision$
 * операция просмотра страницы Корзина платежей
 */

public class ViewPaymentsBasketOperation extends OperationBase implements ViewEntityOperation
{
	private static final String NONE_KEY_NAME = "none";   //ключ для доступа в мапе к автоподпискам, не привязанных к какому-либо объекту учёта
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final List<InvoiceSubscriptionState> stoppedStates = Arrays.asList(
			InvoiceSubscriptionState.STOPPED,
			InvoiceSubscriptionState.ERROR);

	private static final List<InvoiceSubscriptionState> activeStates = Arrays.asList(
			InvoiceSubscriptionState.ACTIVE,
			InvoiceSubscriptionState.INACTIVE);

	private List<AccountingEntityBase> accountingEntities = new ArrayList<AccountingEntityBase>();                  //список объектов учёта
	private Map serviceCategories = new TreeMap<Long, List<ServiceCategory>>();                                     //мапа с категориями услуг для каждого объекта учёта
	private Map<String, List<InvoiceSubscription>> activeSubscriptions = new TreeMap<String, List<InvoiceSubscription>>();       //мапа подписок, находящихся в активном состоянии (ключ мапы - ид объекта учёта)
	private Map<String, List<InvoiceSubscription>> stoppedSubscriptions = new TreeMap<String, List<InvoiceSubscription>>();      //мапа остановленных подписок
	private Map<String, List<InvoiceSubscription>> recommendedSubscriptions = new TreeMap<String, List<InvoiceSubscription>>();  //мапа рекомендуемых(удалённых) подписок
	private List<AutoSubscriptionLink> autoSubscriptions = new ArrayList<AutoSubscriptionLink>();                //список отображаемых автоплатежей
	private Map<String, AutoSubscriptionDetailInfo> detailInfo = new TreeMap<String, AutoSubscriptionDetailInfo>();  //мапа детальной информации для линков (чтобы ловить исключения по их получению не во view-части
	private Set<InvoiceSubscription> allSubscriptions = new TreeSet<InvoiceSubscription>();                     //набор всех автоподписок

	private Map<String, String> imageIds = new TreeMap<String, String>();                   //мапа всех иконок поставщиков услуг
	private Map<String, List<Field>> requisites = new TreeMap<String, List<Field>>();       //мапа всех реквизитов поставщиков

	private static final SimpleService simpleService = new SimpleService();
	private static final ExternalResourceService resourceService = new ExternalResourceService();
	private static final InvoiceSubscriptionService subscriptionService = new InvoiceSubscriptionService();
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();

	private ActivePerson person;

	public ActivePerson getPerson() throws BusinessException
	{
		return person;
	}

	public Long getLoginId()
	{
		return person.getLogin().getId();
	}

	public void initialize() throws BusinessException, BusinessLogicException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		Login login = personData.getLogin();
		Long loginId = login.getId();


		DetachedCriteria criteria = DetachedCriteria.forClass(AccountingEntityBase.class);
		criteria.add(Expression.eq("loginId", loginId));
		accountingEntities = simpleService.find(criteria);

		Map<String, Long> codeToIdMap = getCodeToIdMap();
		BasketConfig config = ConfigFactory.getConfig(BasketConfig.class);
		for (AccountingEntity entity:accountingEntities)
		{
			serviceCategories.put(String.valueOf(entity.getId()), config.getServiceCategoryList(entity.getType()));
		}
		for (ServiceCategory item:config.getAllCategories())
		{
			item.setId(codeToIdMap.get(item.getCode()));
		}

		List<Object[]> fullInfo = getFullInfo(loginId);

		for (Object[] item : fullInfo)
		{
			InvoiceSubscription fakeSubscription = buildInvoiceSubscription(item);

			String accountingEntityId = NONE_KEY_NAME;
			if (fakeSubscription.getAccountingEntityId() != null)
				accountingEntityId = String.valueOf(fakeSubscription.getAccountingEntityId());

			//сначала проверяем, должны ли подписки попадать в приостановленные, тк у подписок с ошибками статусы не меняются
			if (isRecommendedSubscription(fakeSubscription))
			{
				addSubscriptionToMap(recommendedSubscriptions, fakeSubscription, accountingEntityId);
			}
			else if (isStoppedSubscription(fakeSubscription))
			{
				addSubscriptionToMap(stoppedSubscriptions, fakeSubscription, accountingEntityId);
			}
			else if (isActiveSubscription(fakeSubscription))
			{
				addSubscriptionToMap(activeSubscriptions, fakeSubscription, accountingEntityId);
			}

			allSubscriptions.add(fakeSubscription);
			imageIds.put(String.valueOf(fakeSubscription.getId()), StringHelper.getEmptyIfNull(item[13]));
		}

		fillInvoiceSubscriptionPayments(login.getId());
		try
		{
			fillAutoSubscriptions(login);
		}
		catch (Exception e)
		{
			//молча не показываем автоплатежи в случае падения.
			log.error(e.getMessage(), e);
		}

		try
		{
			requisites = fillRequisites(allSubscriptions);
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
		pushAutoSubscriptionsRequisites(requisites, autoSubscriptions);
	}

	public void initialize(Long personId) throws BusinessException, BusinessLogicException
	{
		PersonLoginSource source = new PersonLoginSource(personId);
		person = source.getPerson();
		Long loginId = person.getLogin().getId();
		Login login =  person.getLogin();
				DetachedCriteria criteria = DetachedCriteria.forClass(AccountingEntityBase.class);
		criteria.add(Expression.eq("loginId", loginId));
		accountingEntities = simpleService.find(criteria);

		Map<String, Long> codeToIdMap = getCodeToIdMap();
		BasketConfig config = ConfigFactory.getConfig(BasketConfig.class);
		for (AccountingEntity entity:accountingEntities)
		{
			serviceCategories.put(String.valueOf(entity.getId()), config.getServiceCategoryList(entity.getType()));
		}
		for (ServiceCategory item:config.getAllCategories())
		{
			item.setId(codeToIdMap.get(item.getCode()));
		}

		List<Object[]> fullInfo = getFullInfo(loginId);

		for (Object[] item : fullInfo)
		{
			InvoiceSubscription fakeSubscription = buildInvoiceSubscription(item);

			String accountingEntityId = NONE_KEY_NAME;
			if (fakeSubscription.getAccountingEntityId() != null)
				accountingEntityId = String.valueOf(fakeSubscription.getAccountingEntityId());

			//сначала проверяем, должны ли подписки попадать в приостановленные, тк у подписок с ошибками статусы не меняются
			if (isRecommendedSubscription(fakeSubscription))
			{
				addSubscriptionToMap(recommendedSubscriptions, fakeSubscription, accountingEntityId);
			}
			else if (isStoppedSubscription(fakeSubscription))
			{
				addSubscriptionToMap(stoppedSubscriptions, fakeSubscription, accountingEntityId);
			}
			else if (isActiveSubscription(fakeSubscription))
			{
				addSubscriptionToMap(activeSubscriptions, fakeSubscription, accountingEntityId);
			}

			allSubscriptions.add(fakeSubscription);
			imageIds.put(String.valueOf(fakeSubscription.getId()), StringHelper.getEmptyIfNull(item[13]));
		}

		fillInvoiceSubscriptionPayments(login.getId());
		try
		{
			fillAutoSubscriptions(login);
		}
		catch (Exception e)
		{
			//молча не показываем автоплатежи в случае падения.
			log.error(e.getMessage(), e);
		}

		try
		{
			requisites = fillRequisites(allSubscriptions);
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
		pushAutoSubscriptionsRequisites(requisites, autoSubscriptions);
	}

	private Map<String, Long> getCodeToIdMap() throws BusinessException
	{
		Map<String, Long> result = new TreeMap<String,Long>();
		List<PaymentService> paymentServices = simpleService.getAll(PaymentService.class);
		for (PaymentService item:paymentServices)
		{
			result.put(item.getSynchKey().toString(), item.getId());
		}
		return result;
	}

	private boolean isRecommendedSubscription(InvoiceSubscription subscription)
	{
		// активная неподтвержденный автопоиск
		boolean isActiveUnConfirm = subscription.getState() == InvoiceSubscriptionState.ACTIVE && subscription.getConfirmStrategyType() == null;

		return (subscription.getState() == InvoiceSubscriptionState.DRAFT || isActiveUnConfirm)  && subscription.getCreationType() == CreationType.system;
	}

	private boolean isActiveSubscription(InvoiceSubscription subscription) throws BusinessException
	{
		InvoiceSubscriptionState state = subscription.getState();
		//если подписка перешла в статус "ожидает обработки" из статуса "приостановлена"
		boolean isWaitFromActive = InvoiceSubscriptionState.WAIT == state && InvoiceSubscriptionState.ACTIVE == subscription.getPreviousState();
		//если подписка в приостановленном статусе
		boolean isActiveState = activeStates.contains(state);

		return isWaitFromActive || isActiveState;
	}

	private boolean isStoppedSubscription(InvoiceSubscription subscription) throws BusinessException
	{
		InvoiceSubscriptionState state = subscription.getState();
		//если подписка перешла в статус "ожидает обработки" из статуса "приостановлена"
		boolean isWaitFromStopped = InvoiceSubscriptionState.WAIT == state && InvoiceSubscriptionState.STOPPED == subscription.getPreviousState();
		//если подписка в приостановленном статусе
		boolean isStoppedState = stoppedStates.contains(state);
		//Если из Autopay вернулась ошибка на операцию по подписке, отображаем подписку в приостановленных
		boolean isError = subscription.getErrorType() != null;

		return isWaitFromStopped || isStoppedState || isError;
	}

	private InvoiceSubscription buildInvoiceSubscription(Object[] item)
	{
		InvoiceSubscription fakeSubscription = new InvoiceSubscription();
		fakeSubscription.setId((Long)item[0]);
		fakeSubscription.setName((String)item[1]);
		fakeSubscription.setRequisites((String)item[2]);
		fakeSubscription.setAccountingEntityId((Long) item[3]);
		fakeSubscription.setRecName((String)item[4]);
		fakeSubscription.setErrorDesc((String)item[5]);
		if(StringHelper.isNotEmpty((String)item[6]))
			fakeSubscription.setErrorType(InvoiceSubscriptionErrorType.valueOf((String)item[6]));
		String stateValue = (String)item[7];
		if (StringHelper.isNotEmpty(stateValue))
			fakeSubscription.setBaseState(stateValue);

		fakeSubscription.setCreationType(CreationType.fromValue((String) item[8]));
		if(StringHelper.isNotEmpty((String) item[9]))
			fakeSubscription.setConfirmStrategyType(ConfirmStrategyType.valueOf((String) item[9]));

		fakeSubscription.setNumberOfNotPaidInvoices((Long) item[10]);
		fakeSubscription.setNumberOfDelayedInvoices((Long)item[11]);
		fakeSubscription.setDelayDate((Calendar)item[12]);
		return fakeSubscription;
	}

	private void fillInvoiceSubscriptionPayments(Long loginId) throws BusinessException
	{
		InvoiceConfig config = ConfigFactory.getConfig(InvoiceConfig.class);
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DATE, -config.getDaysCreateInvoiceRequestLive());
		List<String> stateCodes = new ArrayList();
		stateCodes.add("INITIAL");
		stateCodes.add("SAVED");
		stateCodes.add("DRAFT");
		List<Object[]> payments = businessDocumentService.getCreateInvoiceSubscriptionPayments(loginId, date, stateCodes);
		for (Object[] item:payments)
		{
			InvoiceSubscription subEntity = subscriptionService.documentToFakeSubscription((CreateInvoiceSubscriptionPayment)item[1]);
			String accountingEntityId = NONE_KEY_NAME;
			if (subEntity.getAccountingEntityId() != null)
				accountingEntityId = subEntity.getAccountingEntityId().toString();
			addSubscriptionToMap(stoppedSubscriptions, subEntity, accountingEntityId);
			allSubscriptions.add(subEntity);
			String imageId = null;
			if (item[0] != null)
				imageId = item[0].toString();
			imageIds.put(subEntity.getId().toString(), imageId);
		}
	}

	private List<Object[]> getFullInfo(Long loginId) throws BusinessException
	{
		InvoiceConfig config = ConfigFactory.getConfig(InvoiceConfig.class);
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DATE, -config.getDaysInvoiceLive());
		return subscriptionService.selectAll(loginId, date);
	}

	private void addSubscriptionToMap(Map<String, List<InvoiceSubscription>> map, InvoiceSubscription subscription, String key)
	{
		if (!map.keySet().contains(key))
			map.put(key, new ArrayList<InvoiceSubscription>());
		map.get(key).add(subscription);
	}

	public List<AccountingEntityBase> getAccountingEntities()
	{
		return accountingEntities;
	}

	public Map getServiceCategories()
	{
		return serviceCategories;
	}

	public Map<String, List<InvoiceSubscription>> getActiveSubscriptions()
	{
		return activeSubscriptions;
	}

	public Map<String, List<InvoiceSubscription>> getStoppedSubscriptions()
	{
		return stoppedSubscriptions;
	}

	public Map<String, List<InvoiceSubscription>> getRecommendedSubscriptions()
	{
		return recommendedSubscriptions;
	}

	public Set<InvoiceSubscription> getAllSubscriptions()
	{
		return allSubscriptions;
	}

	public Map<String, String> getImageIds()
	{
		return imageIds;
	}

	public void setImageIds(Map<String, String> imageIds)
	{
		this.imageIds = imageIds;
	}

	public Map<String, List<Field>> getRequisites()
	{
		return requisites;
	}

	public void setRequisites(Map<String, List<Field>> requisites)
	{
		this.requisites = requisites;
	}

	public List<AutoSubscriptionLink> getAutoSubscriptions()
	{
		return autoSubscriptions;
	}

	public Object getEntity() throws BusinessException, BusinessLogicException
	{
		return null;
	}

	public Map<String, AutoSubscriptionDetailInfo> getDetailInfo()
	{
		return detailInfo;
	}

	private void fillAutoSubscriptions(Login login) throws BusinessLogicException, BusinessException
	{
		if(!PermissionUtil.impliesService("AutoSubscriptionLinkManagment"))
		{
			return;
		}

		List<AutoSubscriptionLink> bufList = resourceService.getLinks(login, AutoSubscriptionLink.class);

		for (AutoSubscriptionLink item: bufList)
		{
			AutoSubscription autoSub = item.getValue();

			if (checkSuitableAutoSubscription(item))
			{
				autoSubscriptions.add(item);
				try
				{
					detailInfo.put(autoSub.getNumber(), item.getAutoSubscriptionInfo());
				}
				catch (BusinessLogicException e)
				{
					autoSubscriptions.remove(item);
					log.error(e.getMessage(), e);
				}
			}
		}
	}

	private boolean checkSuitableAutoSubscription(AutoSubscriptionLink link) throws BusinessLogicException, BusinessException
	{
		AutoSubscription autoSub = link.getValue();

		return autoSub.getType() == CardPaymentSystemPaymentLongOffer.class
				&& link.getAutoPayStatusType() != AutoPayStatusType.Closed
				&& autoSub.getSumType() == SumType.BY_BILLING
				&& checkExecutionEventType(autoSub.getExecutionEventType())
				&& checkProvider(link);
	}

	private boolean checkProvider(AutoSubscriptionLink link) throws BusinessException, BusinessLogicException
	{
		BillingServiceProvider provider = link.getServiceProvider();
		return provider == null || !InvoiceSubscriptionService.isServiceProviderExceptedFromBasket(provider.getId());
	}

	private boolean checkExecutionEventType(ExecutionEventType type)
	{
		return type == ExecutionEventType.ONCE_IN_WEEK || type == ExecutionEventType.ONCE_IN_MONTH || type == ExecutionEventType.ONCE_IN_QUARTER;
	}

	private Map<String, List<Field>> fillRequisites(Set<InvoiceSubscription> subscriptions) throws DocumentException
	{
		Map<String, List<Field>> result = new TreeMap<String, List<Field>>();
		for (InvoiceSubscription subscription:subscriptions)
		{
			List<Field> reqs = RequisitesHelper.deserialize(subscription.getRequisites());
			List<Field> bufList = new ArrayList<Field>();
			for (Field field:reqs)
			{
				if (field.isKey())
					bufList.add(field);
			}
			result.put(String.valueOf(subscription.getId()), bufList);
		}
		return result;
	}

	private void pushAutoSubscriptionsRequisites(Map<String, List<Field>> requisites, List<AutoSubscriptionLink> autoSubscriptions) throws BusinessException, BusinessLogicException
	{
		for (AutoSubscriptionLink item:autoSubscriptions)
		{
			BillingServiceProvider provider = item.getServiceProvider();
			if (provider == null || subscriptionService.isServiceProviderExceptedFromBasket(provider.getId()))     //если идентификатор поставщика в списке исключённых - пропускаем его
				continue;
			requisites.put("auto" + item.getId(), (List)(provider.getKeyFields()));
		}
	}

    public boolean isInvoiceActive(Long id)
    {
        for(Map.Entry<String, List<InvoiceSubscription>> item : activeSubscriptions.entrySet()) {
            for(InvoiceSubscription invoice : item.getValue()) {
                if(id.equals(invoice.getId())) {
                    if ((InvoiceSubscriptionState.ACTIVE == invoice.getState() || InvoiceSubscriptionState.STOPPED == invoice.getState())
                            && StringHelper.isEmpty(invoice.getAutoPayId()))
                        return true;
                    else
                        return false;
                }
            }
        }
        return false;
    }
}
