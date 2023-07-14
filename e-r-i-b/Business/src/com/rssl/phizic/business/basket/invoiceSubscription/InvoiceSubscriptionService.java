package com.rssl.phizic.business.basket.invoiceSubscription;

import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizgate.common.basket.RequisitesHelper;
import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.basket.links.DocumentLinkToIncoive;
import com.rssl.phizic.business.basket.links.LinkInfo;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.documents.payments.CreateInvoiceSubscriptionPayment;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.common.types.basket.Constants;
import com.rssl.phizic.common.types.basket.InvoiceSubscriptionState;
import com.rssl.phizic.common.types.basket.InvoicesSubscriptionsPromoMode;
import com.rssl.phizic.common.types.limits.ChannelType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.basket.InvoiceConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.longoffer.ExecutionEventType;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.*;

/**
 * @author osminin
 * @ created 11.04.14
 * @ $Author$
 * @ $Revision$
 *
 * Сервис для работы с подписками (услугами)
 */
public class InvoiceSubscriptionService extends SimpleService
{
	private static final DepartmentService departmentService = new DepartmentService();
	private static final ServiceProviderService serviceProviderService = new ServiceProviderService();

	/**
	 * Найти подписку по внутреннему идентификатору
	 * @param id идентиифкатор подписки
	 * @return подписка
	 * @throws BusinessException
	 */
	public InvoiceSubscription findById(Long id) throws BusinessException
	{
		if (id == null)
		{
			throw new IllegalArgumentException("Идентификатор подписки не может быть null");
		}
		return super.findById(InvoiceSubscription.class, id);
	}

	/**
	 * Разгруппировать подписки, привязанные к объекту учета(группе услуг)
	 * @param accountingEntityId идентификатор объекта учета
	 */
	public void ungroupSubscriptions(final Long accountingEntityId) throws BusinessException
	{
		if (accountingEntityId == null)
		{
			throw new IllegalArgumentException("Идентификатор объекта учета не может быть null");
		}

		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					session.getNamedQuery("com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription.ungroup")
							.setParameter("accountingEntityId", accountingEntityId)
							.executeUpdate();
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param accountingEntityId идентификатор объекта учета
	 * @return Необходимо ли разруппировать подписки (есть ли подписки, привязанные к данному объекту учета)?
	 * @throws BusinessException
	 */
	public boolean needUngroupSubscriptions(final Long accountingEntityId) throws BusinessException
	{
		if (accountingEntityId == null)
		{
			throw new IllegalArgumentException("Идентификатор объекта учета не может быть null");
		}

		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					return session.getNamedQuery("com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription.needUngroup")
							.setParameter("accountingEntityId", accountingEntityId)
							.list().size() > 0;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить состояние отображения баннера для формы "счета к оплате"
	 * @param loginId - идентификатор логина
	 * @return режим отображения
	 * @throws BusinessException
	 */
	public InvoicesSubscriptionsPromoMode isInvoicesExist(final Long loginId) throws BusinessException
	{
		try
		{
			int responseSize = HibernateExecutor.getInstance().execute(new HibernateAction<Integer>()
			{
				public Integer run(Session session) throws Exception
				{
					return session.getNamedQuery("com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription.invoiceSubscriptionExist")
							.setParameter("loginId", loginId)
							.list().size();
				}
			});
			if (responseSize == 0)
				return InvoicesSubscriptionsPromoMode.NOT_EXIST;
			else if (responseSize == 1)
				return InvoicesSubscriptionsPromoMode.ONLY_AUTO;
			else
				return InvoicesSubscriptionsPromoMode.EXIST;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Привязывание автоподписки к объекту учёта
	 * @param subscriptionId - ид автоподписки
	 * @param entityId - ид нового объекта учёта
	 * @param loginId - идентификатор логина, в контексте которого меняется привязка
	 * @throws BusinessException
	 */
	public void bindSubscriptionToAccountingEntity(final Long subscriptionId, final Long entityId, final Long loginId) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Object>()
			{
				public Object run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription.move");
					query.setParameter("accountingEntityId", entityId);
					query.setParameter("subscriptionId", subscriptionId);
					query.setParameter("loginId", loginId);
					query.executeUpdate();
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * разрывание связи между автоподпиской и соответствующим ей объектом учёта
	 * @param subscriptionId - ид автоподписки
	 * @param loginId - идентификатор логина, в контексте которого меняется привязка
	 * @throws BusinessException
	 */
	public void unbindSubscriptionToAccountingEntity(final Long subscriptionId, final Long loginId) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Object>()
			{
				public Object run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription.remove");
					query.setParameter("subscriptionId", subscriptionId);
					query.setParameter("loginId", loginId);
					query.executeUpdate();
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * выборка данных, отображаемых на странице Корзины платежей
	 * @param loginId - ид текущего клиента
	 * @return список из массивов с необходимыми данными
	 * @throws BusinessException
	 */
	public List selectAll(final Long loginId, final Calendar fromDate) throws BusinessException
	{
		try
		{
			return (List)HibernateExecutor.getInstance().execute(new HibernateAction<Object>()
			{
				public Object run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription.selectAll");
					query.setParameter("loginId", loginId);
					query.setParameter("fromDate", fromDate);
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * TODO реализовать, исполнитель Никуличев
	 * Поиск автопоисков по логину статусам
	 * @param loginId идентификатор логина
	 * @return список автопоисков
	 * @throws BusinessException
	 * @deprecated возможно в корне изменяться параметры и результат, создан для компиляции
	 */
	@Deprecated
	public List<InvoiceSubscription> findByLoginWithFormulas(final Long loginId, final Long id1, final Long id2) throws BusinessException
	{
		try
		{
			//noinspection unchecked
			return (List<InvoiceSubscription>)HibernateExecutor.getInstance().execute(new HibernateAction<Object>()
			{
				public Object run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription.findByLoginWithFormulas");
					query.setParameter("loginId", loginId);
					query.setParameter("id1", id1);
					query.setParameter("id2", id2);
					//noinspection unchecked
					return (List<InvoiceSubscription>) query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param loginId id логина пользователя.
	 * @param documentType тип документа.
	 * @return информация по подпискам, связанным с конкретным документом пользователя.
	 */
	public List<LinkInfo> getLinkInfoForUserDocument(final long loginId, final String documentType) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<LinkInfo>>()
			{
				public List<LinkInfo> run(Session session) throws Exception
				{
					return session.getNamedQuery("com.rssl.phizic.business.basket.links.getByLoginDocId")
							.setParameter("loginId", loginId)
							.setParameter("documentType", documentType)
							.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Сохранение сущности подписки на инвойсы
	 * @param subscription подписка
	 * @throws BusinessException
	 */
	public void save(InvoiceSubscription subscription) throws BusinessException
	{
		super.addOrUpdate(subscription);
	}

	/**
	 * Сохранить связь подписки с документом профиля
	 * @param link связь подписки с документом профиля
	 * @throws BusinessException
	 */
	public void saveDocumentLink(DocumentLinkToIncoive link) throws BusinessException
	{
		super.addOrUpdate(link);
	}

	/**
	 * Удаление всех связанных с пользовательским документом услуг.
	 * @param loginId id логина пользователя.
	 * @param documentType тип документа.
	 * @throws BusinessException
	 */
	public void removeUserDocumentsLinks(final long loginId, final String documentType) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					session.getNamedQuery("com.rssl.phizic.business.basket.links.deleteAll")
							.setParameter("loginId", loginId)
							.setParameter("documentType", documentType)
							.executeUpdate();

					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Создание сущности подписки на инвойсы по заявке на подписку на инвойсы
	 * @param subscriptionPayment заявка на подписку на инвойсы
	 * @return сущность подписки на инвойсы
	 * @throws BusinessException
	 */
	public InvoiceSubscription createInvoiceSubEntity(CreateInvoiceSubscriptionPayment subscriptionPayment) throws BusinessException
	{
		InvoiceSubscription res = new InvoiceSubscription();
		updateInvoiceSubByClaim(res, subscriptionPayment);
		return res;
	}

	/**
	 * Обновить подписку в базе параметрами из заявки
	 * @param subscription подписка
	 * @param claim заявка
	 * @throws BusinessException
	 */
	public void updateInvoiceSubEntity(InvoiceSubscription subscription, CreateInvoiceSubscriptionPayment claim) throws BusinessException
	{
		updateInvoiceSubByClaim(subscription, claim);
		update(subscription);
	}

	/**
	 * Обновить подписку в базе параметрами из заявки
	 * @param claim заявка
	 * @param state статус
	 * @return сохраненная подписка
	 * @throws BusinessException
	 */
	public InvoiceSubscription saveInvoiceSubEntity(CreateInvoiceSubscriptionPayment claim, InvoiceSubscriptionState state) throws BusinessException
	{
		InvoiceSubscription subscription = new InvoiceSubscription();
		updateInvoiceSubByClaim(subscription, claim);
		subscription.setBaseState(state.name());
		save(subscription);

		return subscription;
	}

	public InvoiceSubscription documentToFakeSubscription(CreateInvoiceSubscriptionPayment doc) throws BusinessException
	{
		InvoiceSubscription result = new InvoiceSubscription();
		result.setId(doc.getId());
		result.setBaseState(InvoiceSubscriptionState.FAKE_SUBSCRIPTION_PAYMENT.name());
		result.setName("заявка на создание: " + doc.getFriendlyName());
		result.setRecName(doc.getReceiverName());
		result.setRequisites(getRequisites(doc));
		result.setAccountingEntityId(doc.getAccountingEntityId());
		result.setDocumentStatus(doc.getState().getCode());
		return result;
	}

	public static boolean isServiceProviderExceptedFromBasket(Long providerId)
	{
		if (providerId == null)
			return true;
		InvoiceConfig config = ConfigFactory.getConfig(InvoiceConfig.class);
		List<String> exceptedProvidersList = config.getExceptedProvidersList();
		return exceptedProvidersList.contains(providerId.toString());
	}

	private String getRequisites(CreateInvoiceSubscriptionPayment payment) throws BusinessException
	{
		try
		{
			return RequisitesHelper.serialize(payment.getExtendedFields());
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private String getRequisites(List<FieldDescription> providerFields, Map<String, Object> fieldValues) throws BusinessException
	{
		List<Field> fields = new ArrayList<Field>();
		for(FieldDescription fieldDescription : providerFields)
		{
			CommonField field = new CommonField(fieldDescription);
			field.setValue(fieldValues.get(fieldDescription.getExternalId()));
			fields.add(field);
		}

		try
		{
			return RequisitesHelper.serialize(fields);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Обновить статус подписки
	 * @param state статус
	 * @param id идентификатор подписки
	 * @throws BusinessException
	 */
	public void updateState(final InvoiceSubscriptionState state, final Long id) throws BusinessException
	{
		if (state == null)
		{
			throw new IllegalArgumentException("Статус не может быть null.");
		}
		if (id == null)
		{
			throw new IllegalArgumentException("Идентификатор подписки не может быть null.");
		}
		updateState(state.name(), id);
	}

	/**
	 * установить статус ожидания
	 * @param nextState статус, в который ожидается переход подписки
	 * @param id идентификатор подписки
	 * @throws BusinessException
	 */
	public void setWaitState(final String nextState, final Long id) throws BusinessException
	{
		if (StringHelper.isEmpty(nextState))
		{
			throw new IllegalArgumentException("Статус не может быть null.");
		}
		if (id == null)
		{
			throw new IllegalArgumentException("Идентификатор подписки не может быть null.");
		}

		LightInvoiceSubscription subscription = getLightSubscriptionById(id);
		if (subscription == null)
		{
			throw new BusinessException("Не найден авопоиск счетов по id " + id);
		}

		String waitState = subscription.getState().name() + Constants.STATE_DELIMITER + nextState;
		updateState(waitState, id);
	}

	private void updateState(final String state, final Long id) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					session.getNamedQuery("com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription.updateState")
							.setParameter("state", state)
							.setParameter("id", id)
							.executeUpdate();
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Удалить автоматически сгенерированную подписку в статусе DRAFT
	 * @param id идентификатор подписки
	 * @throws BusinessException
	 */
	public void removeGeneratedSubscription(final Long id) throws BusinessException
	{
		if (id == null)
		{
			throw new IllegalArgumentException("Идентификатор подписки не может быть null.");
		}
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					session.getNamedQuery("com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription.removeGeneratedSub")
							.setParameter("id", id)
							.executeUpdate();
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить лайт версию подписки (возвращать можно все данные по подписке, кроме реквизитов, тк CLOB)
	 * @param id идентификатор
	 * @return услуга
	 * @throws BusinessException
	 */
	public LightInvoiceSubscription getLightSubscriptionById(final Long id) throws BusinessException
	{
		if (id == null)
		{
			throw new IllegalArgumentException("Идентификатор услуги не может быть null");
		}
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<LightInvoiceSubscription>()
			{
				public LightInvoiceSubscription run(Session session) throws Exception
				{
					return (LightInvoiceSubscription) session.getNamedQuery("com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription.getLightSubscriptionById")
							.setParameter("id", id)
							.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить список сгенерированных подписок для проводки
	 * @param maxRows maxresult
	 * @return список подписок
	 * @throws BusinessException
	 */
	public List<InvoiceSubscription> getGeneratedInvoiceSubscriptions(final int maxRows) throws BusinessException
	{
		InvoiceConfig invoiceConfig = ConfigFactory.getConfig(InvoiceConfig.class);
		final Calendar fromDate = Calendar.getInstance();
		fromDate.add(Calendar.DAY_OF_MONTH, -invoiceConfig.getGeneratedSubsLifeDays());

		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<InvoiceSubscription>>()
			{
				public List<InvoiceSubscription> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(InvoiceSubscription.class.getName() + ".getGeneratedInvoiceSubscriptions");
					query.setParameter("startDate", fromDate);
					query.setMaxResults(maxRows);
					//noinspection unchecked
					return (List<InvoiceSubscription>) query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить подписки для отложенной проводки
	 * @param maxRows ограничение выборки
	 * @return список подписок
	 * @throws BusinessException
	 */
	public List<InvoiceSubscription> getDelayExecuteInvoiceSubscriptions(final int maxRows) throws BusinessException
	{
		InvoiceConfig invoiceConfig = ConfigFactory.getConfig(InvoiceConfig.class);
		final Calendar fromDate = Calendar.getInstance();
		fromDate.add(Calendar.DAY_OF_MONTH, -invoiceConfig.getGeneratedSubsLifeDays());

		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<InvoiceSubscription>>()
			{
				public List<InvoiceSubscription> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(InvoiceSubscription.class.getName() + ".getRuntimeExecuteInvoiceSubscriptions");
					query.setParameter("startDate", fromDate);
					query.setParameter("maxrows", maxRows);
					//noinspection unchecked
					return (List<InvoiceSubscription>) query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public InvoiceSubscription findInvoiceSubByInvoiceId(final Long invoiceId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<InvoiceSubscription>()
			{
				public InvoiceSubscription run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(InvoiceSubscription.class.getName() + ".getInvoiceSubscriptionByInvoiceId");
					query.setParameter("invoiceId", invoiceId);
					return (InvoiceSubscription) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Создать и сохранить автопоиск по привязкам
	 * @param providerId идентификатор поставщика
	 * @param fieldValues значения полей поставщика
	 */
	public void createAndSaveInvoiceSubscription(final Long providerId, final Map<String, Object> fieldValues) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					ServiceProviderBase providerBase = serviceProviderService.findById(providerId);
					if(providerBase == null)
						throw new BusinessException("Не найден поставщик с id = " + providerId);

					if(!(providerBase instanceof BillingServiceProvider))
						throw new BusinessException("Поставщик[id = " + providerId + "] не является биллинговым");

					session.save(createByProvider((BillingServiceProvider) providerBase, fieldValues));
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private InvoiceSubscription createByProvider(BillingServiceProvider provider, Map<String, Object> fieldValues) throws BusinessException, BusinessLogicException
	{
		com.rssl.phizic.gate.payments.systems.recipients.Service service = provider.getService();

		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		ActivePerson person = personData.getPerson();

		InvoiceSubscription subscription = new InvoiceSubscription();
		subscription.setRequisites(getRequisites(provider.getFieldDescriptions(), fieldValues));
		subscription.setLoginId(person.getLogin().getId());
		subscription.setAccountingEntityId(null);
		subscription.setName(provider.getName());
		subscription.setRequestId("AA" + new RandomGUID().getStringValue().substring(2));
		subscription.setStartDate(Calendar.getInstance());
		subscription.setChannelType(ChannelType.INTERNET_CLIENT);
		subscription.setTb(departmentService.getNumberTB(person.getDepartmentId()));
		subscription.setExecutionEventType(ExecutionEventType.ONCE_IN_MONTH);
		subscription.setPayDate(Calendar.getInstance());
		subscription.setBaseState(InvoiceSubscriptionState.DELAY_CREATE.name());
		subscription.setCodeRecipientBs(provider.getSynchKey().toString());
		subscription.setRecName(provider.getName());
		subscription.setCodeService(service.getCode());
		subscription.setNameService(service.getName());
		subscription.setRecInn(provider.getINN());
		subscription.setRecCorAccount(provider.getCorrAccount());
		subscription.setRecKpp(provider.getKPP());
		subscription.setRecBic(provider.getBIC());
		subscription.setRecAccount(provider.getAccount());
		subscription.setRecNameOnBill(provider.getNameOnBill());
		subscription.setRecPhoneNumber(provider.getPhoneNumber());
		subscription.setRecTb(departmentService.getNumberTB(provider.getDepartmentId()));
		subscription.setRecipientId(provider.getId());
		subscription.setBillingCode(provider.getBilling().getCode());
		subscription.setCreationType(CreationType.internet);
		subscription.setConfirmStrategyType(null);
		subscription.setAutoSubExternalId(null);

		List<CardLink> cardLinks = personData.getCards();
		if(CollectionUtils.isEmpty(cardLinks))
			throw new BusinessException("Не найдено подходящих карт для создания подписки [personId = " + person.getId() + "]");
		subscription.setChargeOffCard(cardLinks.get(0).getNumber());

		return subscription;
	}

	private void updateInvoiceSubByClaim(InvoiceSubscription subscription, CreateInvoiceSubscriptionPayment claim) throws BusinessException
	{
		ExtendedCodeImpl extendedCode = new ExtendedCodeImpl(claim.getReceiverOfficeCode());
		com.rssl.phizic.gate.payments.systems.recipients.Service service = claim.getService();

		subscription.setRequisites(getRequisites(claim));
		BusinessDocumentOwner documentOwner = claim.getOwner();
		if (documentOwner.isGuest())
			throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
		subscription.setLoginId(documentOwner.getLogin().getId());
		subscription.setAccountingEntityId(claim.getAccountingEntityId());
		subscription.setName(claim.getFriendlyName());
		subscription.setRequestId(claim.getOperationUID());
		subscription.setStartDate(Calendar.getInstance());
		subscription.setChannelType(ChannelType.INTERNET_CLIENT);  // TODO убить канал лимитов, исполнитель Никуличев
		subscription.setTb(claim.getDepartment().getRegion());
		subscription.setExecutionEventType(claim.getExecutionEventType());
		subscription.setPayDate(claim.getNextPayDate());
		subscription.setChargeOffCard(claim.getChargeOffCard());
		subscription.setBaseState(InvoiceSubscriptionState.INACTIVE.name());
		subscription.setCodeRecipientBs(claim.getReceiverPointCode());
		subscription.setRecName(claim.getReceiverName());
		subscription.setCodeService(service.getCode());
		subscription.setNameService(service.getName());
		subscription.setRecInn(claim.getReceiverINN());
		subscription.setRecCorAccount(claim.getReceiverCorAccount());
		subscription.setRecKpp(claim.getReceiverKPP());
		subscription.setRecBic(claim.getReceiverBIC());
		subscription.setRecAccount(claim.getReceiverAccount());
		subscription.setRecNameOnBill(claim.getReceiverNameForBill());
		subscription.setRecPhoneNumber(claim.getReceiverPhone());
		subscription.setRecTb(extendedCode.getRegion());
		subscription.setRecipientId(claim.getReceiverInternalId());
		subscription.setBillingCode(claim.getBillingCode());
		subscription.setCreationType(claim.getCreationType());
		subscription.setConfirmStrategyType(claim.getConfirmStrategyType());
		subscription.setAutoSubExternalId(claim.getLongOfferExternalId());
	}
}
