package com.rssl.phizic.business.shop;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.CSAResponseConstants;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.providers.InternetShopsServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.documents.*;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocumentToOrder;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.payments.source.SystemWithdrawDocumentSource;
import com.rssl.phizic.business.payments.BusinessTimeOutException;
import com.rssl.phizic.business.persons.ClientDataImpl;
import com.rssl.phizic.business.persons.PersonBase;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.statemachine.PaymentStateMachineService;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.cache.CacheService;
import com.rssl.phizic.gate.documents.WithdrawMode;
import com.rssl.phizic.gate.einvoicing.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Session;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.xml.transform.TransformerException;

/**
 * Хелпер для работы с интернет-заказами.
 *
 * @author bogdanov
 * @ created 19.02.14
 * @ $Author$
 * @ $Revision$
 */

public class ShopHelper
{
	private static volatile ShopHelper it;
	private static final Object LOCK = new Object();
	private final BusinessDocumentService businessDocumentService = new BusinessDocumentService();
	private final SimpleService simpleService = new SimpleService();
	private final PaymentStateMachineService stateMachineService = new PaymentStateMachineService();
	private final DepartmentService departmentService = new DepartmentService();
	private final Ehcache profileHistoryCache;

	private static final Map<String, OrderState> ORDER_STATES_MAP = new HashMap<String, OrderState>();
	private static final Map<String, RecallState> RECALL_STATES_MAP = new HashMap<String, RecallState>();

	static {
		ORDER_STATES_MAP.put("INITIAL",         OrderState.PAYMENT);
		ORDER_STATES_MAP.put("SAVED",           OrderState.PAYMENT);
		ORDER_STATES_MAP.put("ERROR",           OrderState.ERROR);
		ORDER_STATES_MAP.put("TICKETS_WAITING", OrderState.EXECUTED);
		ORDER_STATES_MAP.put("EXECUTED",        OrderState.EXECUTED);
		ORDER_STATES_MAP.put("REFUSED",         OrderState.REFUSED);
		ORDER_STATES_MAP.put("RECALLED",        OrderState.REFUND);
		ORDER_STATES_MAP.put("UNKNOW",          OrderState.WRITE_OFF);
		ORDER_STATES_MAP.put("DISPATCHED",      OrderState.WRITE_OFF);

		RECALL_STATES_MAP.put("INITIAL",    RecallState.CREATED);
		RECALL_STATES_MAP.put("SAVED",      RecallState.CREATED);
		RECALL_STATES_MAP.put("UNKNOW",     RecallState.CREATED);
		RECALL_STATES_MAP.put("DISPATCHED", RecallState.CREATED);
		RECALL_STATES_MAP.put("EXECUTED",   RecallState.EXECUTED);
		RECALL_STATES_MAP.put("REFUSED",    RecallState.REFUSED);
		RECALL_STATES_MAP.put("ERROR",      RecallState.ERROR);

	}

	public static ShopHelper get()
	{
		if (it != null)
			return it;

		synchronized (LOCK)
		{
			if (it != null)
				return it;

			it = new ShopHelper();
			return it;
		}
	}

	private ShopHelper() {
		profileHistoryCache = new Cache("ShopHelper.ProfileHistory", 500, true, false, 900, 900);
		CacheManager.getInstance().addCache(profileHistoryCache);
	}

	private ServiceProviderService providerService = new ServiceProviderService();

	/**
	 * Возвращает поставщика по имени поставщика.
	 * @param systemName название поставщика.
	 * @return поставщик.
	 */
	public InternetShopsServiceProvider getRecipientBySystemName(String systemName) throws BusinessException
	{
		return providerService.getRecipientActivityBySystemName(systemName);
	}

	/**
	 * Возвращает платёж, привязанный к заказу
	 *
	 * @param orderUuid - заказ
	 * @return платёж или null, если у заказа нет связанного платежа
	 * @throws BusinessException если что-то пошло не так
	 */
	public List<BusinessDocument> getRecallsByOrder(final String orderUuid) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<BusinessDocument>>()
			{
				public List<BusinessDocument> run(Session session)
				{
					return session.getNamedQuery("com.rssl.phizic.business.documents.BusinessDocumentService.findByOrderUUID")
							.setParameter("order_uuid", orderUuid)
							.setParameter("type", "RG")
							.list();
				}
			});
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Поиск идентификатора заказа по его документу и типу.
	 * @param documentId id Документа.
	 * @return идентификатор заказа.
	 * @throws BusinessException
	 */
	public String getOrderUuidByPayment(final Long documentId) throws BusinessException
	{
		if (documentId == null)
			return null;

		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<String>()
			{
				public String run(Session session)
				{
					return (String) session.getNamedQuery("com.rssl.phizic.business.documents.BusinessDocumentService.findOrderUUID")
							.setParameter("documentID", documentId)
							.uniqueResult();
				}
			});
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Поиск статуса документа оплаты заказа.
	 * @param orderUuid - идентификатор заказа.
	 * @return статус заказа, null - документа нет.
	 */
	public static OrderStateInfo getOrderStateByDocument(final String orderUuid) throws BusinessException
	{
		try
		{
			List<Object[]> info = HibernateExecutor.getInstance().execute(new HibernateAction<List<Object[]>>()
			{
				public List<Object[]> run(Session session)
				{
					return session.getNamedQuery("com.rssl.phizic.business.documents.BusinessDocumentService.getOrderDocumentState")
							.setParameter("orderUuid", orderUuid)
							.list();
				}
			});

			if(info.isEmpty())
				return null;

			Object[] res = info.get(0);
			return new OrderStateInfo(ORDER_STATES_MAP.get(res[0]), (String)res[1]);
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Поиск статуса документа отмены платежа/возврата товара по заказу.
	 * @param orderUuid - идентификатор заказа, по которому проводилась отмена/возврат.
	 * @param recallUuid - идентификатор отмены/возврата.
	 * @return статус отмены.
	 */
	public RecallStateInfo getRecallStateByDocument(final String orderUuid, final String recallUuid, final RecallType type) throws BusinessException
	{
		try
		{
			List<Object[]> info = HibernateExecutor.getInstance().execute(new HibernateAction<List<Object[]>>()
			{
				public List<Object[]> run(Session session)
				{
					return session.getNamedQuery("com.rssl.phizic.business.documents.BusinessDocumentService.getRecallDocumentState")
							.setParameter("orderUuid", orderUuid)
							.setParameter("recallUuid", recallUuid)
							.setParameter("kind", type == RecallType.FULL ? "RO" : "RG")
							.list();
				}
			});

			Object[] res = info.get(0);
			return new RecallStateInfo(RECALL_STATES_MAP.get(res[0]), (String)res[1]);
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Возвращает поставщика по номеру заказа
	 * @param uuid - идентификатор операции
	 * @return активного поставщика внешней услуги, либо Null
	 * @throws BusinessException
	 */
	public InternetShopsServiceProvider getRecipientByOrderUuid(String uuid) throws BusinessException
	{
		ShopOrder order = getShopOrder(uuid);
		if (order == null)
			return null;

		InternetShopsServiceProvider provider = getRecipientBySystemName(order.getReceiverCode());

		if (provider == null)    // нет такого поставщика
			return null;

		return provider;
	}

	/**
	 * Получает данные по заказу
	 * @param uuid  - идентификатор запроса
	 * @return null - если ничего не найдено; иначе заполненный объект
	 * @throws BusinessException: передан null, найдено больше 1-ой строки
	 */
	public ShopOrder getShopOrder(String uuid) throws BusinessException
	{
		try
		{
			if (StringHelper.isEmpty(uuid))
				throw new BusinessException("Неизвестный заказ uuid="+uuid);

			ShopOrder order = GateSingleton.getFactory().service(ShopOrderService.class).getOrder(uuid);
			if (order == null)
				throw new BusinessException("Не удается найти заказ по uuid=" + uuid);

			return order;
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Привязка заказа к платежу
	 * @param uuid - номер заказа в нашей системе
	 * @param payment - платеж
	 */
	public void linkPayment(String uuid, BusinessDocumentBase payment) throws BusinessException
	{
		try
		{
			if (payment instanceof JurPayment)
			{
				ShopOrder order = getShopOrder(uuid);

				if (order == null)
					throw new BusinessException("Заказ по uuid=" + uuid + " не найден");
			}

			BusinessDocumentToOrder bdto = new BusinessDocumentToOrder();
			bdto.setOrderUuid(uuid);
			bdto.setId(payment.getId());
			simpleService.add(bdto);
		}
		catch(BusinessException e)
		{
			throw new BusinessException("Ошибка привязки заказа к платежу", e);
		}
	}

	/**
	 * Связываем заказ по идентификатору запроса с пользователем
	 * @param uuid  - идентификатор запроса
	 * @param profile - персона
	 * @throws BusinessException
	 */
	public void linkPerson(String uuid, ClientDataImpl profile)  throws BusinessException
	{
		try
		{
			GateSingleton.getFactory().service(ShopOrderService.class).linkOrderToClient(uuid, profile);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Выполнить отмену/возврат по документу.
	 * @param withdrawMode режим возврата.
	 * @param valuesSource список полей.
	 * @param parent родительский документ.
	 * @param money сумма платежа.
	 */
	public void withdrawDocument(WithdrawMode withdrawMode, FieldValuesSource valuesSource, BusinessDocument parent, Money money, String orderUuid) throws BusinessException, BusinessTimeOutException
	{
		RecallDocument document = null;
		try
		{
			String form = withdrawMode == WithdrawMode.Partial ? FormConstants.REFUND_GOODS_FORM : FormConstants.RECALL_ORDER_FORM;
			DocumentSource source = new SystemWithdrawDocumentSource(form, valuesSource, parent);
			document = (RecallDocument) source.getDocument();
			document.setChargeOffAmount(money);

			StateMachineExecutor executor = new StateMachineExecutor(stateMachineService.getStateMachineByFormName(document.getFormName()));
			executor.initialize(document);
			executor.fireEvent(new ObjectEvent(DocumentEvent.DISPATCH, ObjectEvent.SYSTEM_EVENT_TYPE));

			businessDocumentService.addOrUpdate(document);
			ShopHelper.get().linkPayment(orderUuid, document);
		}
		catch (BusinessTimeOutException e)
		{
			try
			{
				StateMachineExecutor executor = new StateMachineExecutor(stateMachineService.getStateMachineByFormName(document.getFormName()));
				executor.initialize(document);
				DocumentHelper.fireDounknowEvent(executor, ObjectEvent.SYSTEM_EVENT_TYPE, e);
			}
			catch (BusinessLogicException ble)
			{
				throw new BusinessException(ble);
			}
			businessDocumentService.addOrUpdate(document);
			ShopHelper.get().linkPayment(valuesSource.getValue("refundUuid"), document);

			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Один и тот же клиент.
	 *
	 * @param profile профиль клиента из заказа.
	 * @param context информация о входе клиента.
	 * @return один и тот же клиент или нет.
	 * @throws BusinessException
	 */
	public boolean isSameClient(ShopProfile profile, AuthenticationContext context) throws BusinessException
	{
		UserInfo userInfo = getUserInfo(context);
		if (profile.getFirstName().equals(userInfo.getFirstname()) &&
				profile.getSurName().equals(userInfo.getSurname()) &&
				StringHelper.equals(profile.getPatrName(), userInfo.getPatrname()) &&
				profile.getTb().equals(userInfo.getCbCode()) &&
				profile.getPassport().equals(userInfo.getPassport()) &&
				DateUtils.isSameDay(profile.getBirthdate(), userInfo.getBirthdate()))
			return true;

		for (ShopProfile shopProfile : getProfileHistory(userInfo))
		{
			if (profile.getFirstName().equals(shopProfile.getFirstName()) &&
				profile.getSurName().equals(shopProfile.getSurName()) &&
				StringHelper.equals(profile.getPatrName(), shopProfile.getPatrName()) &&
				profile.getTb().equals(shopProfile.getTb()) &&
				profile.getPassport().equals(shopProfile.getPassport()) &&
				DateUtils.isSameDay(profile.getBirthdate(), shopProfile.getBirthdate()))
			return true;
		}

		return false;
	}

	/**
	 * Получение строки запроса на выборку одного пользователя по его истории измения профиля.
	 *
	 * @param context контекст аутентификации.
	 * @return строка для запроса.
	 * @throws BusinessException
	 */
	public List<ShopProfile> getProfileHistory(AuthenticationContext context) throws BusinessException
	{
		return getProfileHistory(getUserInfo(context));
	}

	/**
	 * Получение профилей интернет-заказов по сущности "персоны" клиента
	 * @param person - персона
	 * @return - список профилей
	 * @throws BusinessException
	 */
	public List<ShopProfile> getProfileHistory(PersonBase person) throws BusinessException
	{
		return getProfileHistory(getUserInfo(person));
	}

	/**
	 * @param userInfo информация о пользователе.
	 * @return история изменения профиля.
	 */
	private List<ShopProfile> getProfileHistory(UserInfo userInfo) throws BusinessException
	{
		StringBuilder sb = new StringBuilder(200);
		sb.append(userInfo.getPassport()).append(userInfo.getBirthdate().getTimeInMillis()).append(userInfo.getFirstname()).append(userInfo.getSurname()).append(userInfo.getPatrname()).append(userInfo.getTb());
		String key = sb.toString();
		net.sf.ehcache.Element cacheElement = profileHistoryCache.get(key);
		if (cacheElement != null)
			return (List<ShopProfile>) cacheElement.getValue();

		try
		{
			Document response = CSABackRequestHelper.sendGetProfileHistoryInfo(userInfo);
			Element element = response.getDocumentElement();
			Element historyTag = XmlHelper.selectSingleNode(element, CSAResponseConstants.HISTORY_TAG);
			final List<ShopProfile> profiles_n = new LinkedList<ShopProfile>();
			XmlHelper.foreach(historyTag, CSAResponseConstants.HISTORY_ITEM_TAG, new ForeachElementAction()
			{
				public void execute(Element element) throws Exception
				{
					Calendar calendar = XMLDatatypeHelper.parseDate(XmlHelper.getSimpleElementValue(element, CSAResponseConstants.BIRTHDATE_TAG));
					ClientDataImpl profile = new ClientDataImpl();
					profile.setFirstName(XmlHelper.getSimpleElementValue(element, CSAResponseConstants.FIRSTNAME_TAG));
					profile.setSurName(XmlHelper.getSimpleElementValue(element, CSAResponseConstants.SURNAME_TAG));
					profile.setPatrName(XmlHelper.getSimpleElementValue(element, CSAResponseConstants.PATRNAME_TAG));
					profile.setBirthdate(calendar);
					profile.setPassport(XmlHelper.getSimpleElementValue(element, CSAResponseConstants.PASSPORT_TAG));
					profile.setTb(XmlHelper.getSimpleElementValue(element, CSAResponseConstants.TB_TAG));
					profiles_n.add(profile);
				}
			});

			profileHistoryCache.put(new net.sf.ehcache.Element(key, profiles_n));
			return profiles_n;
		}
		catch (BackLogicException ex)
		{
			throw new BusinessException(ex);
		}
		catch(BackException ex)
		{
			throw new BusinessException(ex);
		}
		catch (TransformerException e)
		{
			throw new BusinessException(e);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Конвертация Person в ShopPerson.
	 * @param context контект аутентификации.
	 * @return shopPerson.
	 */
	private UserInfo getUserInfo(AuthenticationContext context) throws BusinessException
	{
		Calendar calendar = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try
		{
			calendar.setTime(dateFormat.parse(context.getBirthDate()));
		}
		catch (ParseException e)
		{
			throw new BusinessException("Ошибка при преобразовании даты рождения клиента", e);
		}
		return new UserInfo(
			context.getTB(),
			context.getFirstName(),
			context.getLastName(),
			context.getMiddleName(),
			context.getDocumentNumber(),
			calendar
		);
	}

	private UserInfo getUserInfo(PersonBase person) throws BusinessException
	{
		return new UserInfo(
				departmentService.getNumberTB(person.getDepartmentId()),
				person.getFirstName(),
				person.getSurName(),
				person.getPatrName(),
				PersonHelper.getPersonDocument(person, PersonDocumentType.PASSPORT_WAY).getDocumentSeries(),
				person.getBirthDay()
		);
	}

	/**
	 * @param orderUuid внешний идентификатор заказа.
	 * @return список товаров или информация о броне авиабилетов.
	 */
	public String getOrderDetailInfo(String orderUuid) throws BusinessException, BusinessLogicException
	{
		try
		{
			return GateSingleton.getFactory().service(ShopOrderService.class).getOrderInfo(orderUuid);
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
	 * Изменить статус документа.
	 *
	 * @param uuid идентификтаор заказа.
	 * @param newState новое состояние.
	 * @param utrnno утрнно.
	 * @param paidBy оплата с ...
	 * @throws BusinessException
	 */
	public void changeOrderStatus(String uuid, OrderState newState, String utrnno, String paidBy) throws BusinessException, BusinessLogicException
	{
		changeOrderStatus(uuid, newState, utrnno, paidBy, null);
	}

	/**
	 * Изменить статус документа.
	 *
	 * @param uuid идентификтаор заказа.
	 * @param newState новое состояние.
	 * @param utrnno утрнно.
	 * @param paidBy оплата с ...
	 * @param delayDate дата на коорую откладывается инвойс в случае изменения статуса на DELAYED
	 * @throws BusinessException
	 */
	public void changeOrderStatus(String uuid, OrderState newState, String utrnno, String paidBy, Calendar delayDate) throws BusinessException, BusinessLogicException
	{
		try
		{
			GateFactory factory = GateSingleton.getFactory();
			factory.service(ShopOrderService.class).changeOrderStatus(
				uuid, newState, ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getNodeNumber(), utrnno, paidBy, delayDate
			);
			factory.service(CacheService.class).clearShopOrderCache(uuid);
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
	 * Изменить статус отмены платежа или возврата товаров.
	 *
	 * @param uuid идентификтаор заказа.
	 * @param newState новое состояние.
	 * @param utrnno утрнно.
	 * @throws BusinessException
	 */
	public void changeRefundStatus(String uuid, RecallState newState, String utrnno, RollbackOrderClaim document) throws BusinessException, BusinessLogicException
	{
		try
		{
			GateFactory factory = GateSingleton.getFactory();
			factory.service(ShopOrderService.class).changeRecallStatus(
				uuid, newState, utrnno, document instanceof RefundGoodsClaim ? RecallType.PARTIAL : RecallType.FULL
			);
			factory.service(CacheService.class).clearShopOrderCache(getOrderUuidByPayment(document.getWithdrawInternalId()));
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
	 * Установка списка билетов в документ и перевод его статуса в "Исполнен"
	 * @param orderUuid - uuid заказа
	 * @param ticketInfo - xml с описанием билетов
	 */
	public void setTicketsInfo(String orderUuid, String ticketInfo) throws BusinessException, BusinessLogicException
	{

		BusinessDocument document = DocumentHelper.getPaymentByOrder(orderUuid);
		if (!document.getFormName().equals(FormConstants.AIRLINE_RESERVATION_PAYMENT_FORM))
			throw new BusinessException("Ошибка установки списка билетов: некорректный тип документа c id=" + document.getId());

		setTicketsInfo((JurPayment) document, ticketInfo);
	}

	/**
	 * Установка списка билетов в документ и перевод его статуса в "Исполнен"
	 * @param payment - документ оплаты брони
	 * @param ticketInfo - xml с описанием билетов
	 */
	public void setTicketsInfo(JurPayment payment, String ticketInfo) throws BusinessException, BusinessLogicException
	{

		if (!payment.getState().getCode().equals(DocumentEvent.TICKETS_WAITING.name()))
			throw new BusinessException("Ошибка установки списка билетов: некорректный статус документа c id=" + payment.getId());

		payment.setTicketInfo(ticketInfo);
		StateMachineExecutor executor = new StateMachineExecutor(stateMachineService.getStateMachineByFormName(payment.getFormName()));
		executor.initialize(payment);
		executor.fireEvent(new ObjectEvent(DocumentEvent.EXECUTE, ObjectEvent.SYSTEM_EVENT_TYPE));

		businessDocumentService.addOrUpdate(payment);
	}
}
