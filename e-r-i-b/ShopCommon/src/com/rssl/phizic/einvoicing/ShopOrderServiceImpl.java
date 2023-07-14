package com.rssl.phizic.einvoicing;

import com.rssl.phizic.common.types.Period;
import com.rssl.phizic.common.types.shop.ShopConstants;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.DatabaseServiceBase;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.ExecutorQuery;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.einvoicing.exceptions.InactualOrderInInternetShopException;
import com.rssl.phizic.einvoicing.exceptions.OrderNotFoundException;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.einvoicing.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.shoplistener.generated.registration.DuplicateOrderException;
import com.rssl.phizic.shoplistener.generated.registration.Full;
import com.rssl.phizic.shoplistener.generated.registration.RegistrationException;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.shopclient.ShopInfoServiceWrapper;
import com.rssl.phizicgate.shopclient.generated.DocInfoRsType;
import com.rssl.phizicgate.shopclient.generated.DocInfoStatRsType;
import com.rssl.phizicgate.shopclient.generated.InfoType;
import com.rssl.phizicgate.shopclient.generated.RegRqDocumentType;
import org.apache.commons.lang.BooleanUtils;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.*;

/**
 * Одноблочный сервис для работы с интернет заказами.
 *
 * @author bogdanov
 * @ created 12.02.14
 * @ $Author$
 * @ $Revision$
 */

public class ShopOrderServiceImpl extends AbstractService implements ShopOrderService
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_WEB);
	private static final DatabaseServiceBase simpleService = new DatabaseServiceBase();
	private static final FacilitatorProviderService facilitatorProviderService = new FacilitatorProviderService(null);

	private static final Map<OrderState, OrderState[]> NEXT_STATE = new HashMap<OrderState, OrderState[]>();

	private static final Map<RecallState, RecallState[]> NEXT_STATE_REFUND = new HashMap<RecallState, RecallState[]>();

	private static final List<OrderState> REISSUE_ALLOWED_STATES = new LinkedList<OrderState>();
	private static final List<OrderState> NOTIFICATION_ORDER_STATES = new LinkedList<OrderState>();

	static {
		NEXT_STATE.put(OrderState.CREATED, new OrderState[]{OrderState.RELATED, OrderState.DELAYED});
		NEXT_STATE.put(OrderState.RELATED, new OrderState[]{OrderState.PAYMENT, OrderState.CANCELED, OrderState.DELAYED});
		NEXT_STATE.put(OrderState.PAYMENT, new OrderState[]{OrderState.PAYMENT, OrderState.WRITE_OFF, OrderState.DELAYED, OrderState.CANCELED});
		NEXT_STATE.put(OrderState.WRITE_OFF, new OrderState[]{OrderState.EXECUTED, OrderState.ERROR, OrderState.REFUSED});
		NEXT_STATE.put(OrderState.EXECUTED, new OrderState[]{OrderState.PARTIAL_REFUND, OrderState.REFUND});
		NEXT_STATE.put(OrderState.REFUSED, new OrderState[]{});
		NEXT_STATE.put(OrderState.PARTIAL_REFUND, new OrderState[]{OrderState.PARTIAL_REFUND, OrderState.REFUND});
		NEXT_STATE.put(OrderState.REFUND, new OrderState[]{});
		NEXT_STATE.put(OrderState.ERROR, new OrderState[]{OrderState.EXECUTED, OrderState.REFUSED});
		NEXT_STATE.put(OrderState.CANCELED, new OrderState[]{});
		NEXT_STATE.put(OrderState.DELAYED, new OrderState[]{OrderState.DELAYED, OrderState.RELATED, OrderState.PAYMENT, OrderState.CANCELED});

		NEXT_STATE_REFUND.put(RecallState.CREATED, new RecallState[]{RecallState.EXECUTED, RecallState.ERROR, RecallState.REFUSED});
		NEXT_STATE_REFUND.put(RecallState.EXECUTED, new RecallState[]{});
		NEXT_STATE_REFUND.put(RecallState.ERROR, new RecallState[]{});
		NEXT_STATE_REFUND.put(RecallState.REFUSED, new RecallState[]{});

		REISSUE_ALLOWED_STATES.add(OrderState.CREATED);
		REISSUE_ALLOWED_STATES.add(OrderState.RELATED);
		REISSUE_ALLOWED_STATES.add(OrderState.PAYMENT);

		NOTIFICATION_ORDER_STATES.add(OrderState.EXECUTED);
		NOTIFICATION_ORDER_STATES.add(OrderState.REFUSED);
		NOTIFICATION_ORDER_STATES.add(OrderState.ERROR);
	}

	public ShopOrderServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public void linkOrderToClient(final String orderUUID, ShopProfile profile) throws GateException, GateLogicException
	{
		try
		{
			final ShopProfileImpl shopProfile = new ShopProfileImpl(profile);

			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					ShopOrderImpl order = findOrder(orderUUID, null);
					if (order == null)
						throw new OrderNotFoundException();

					//Проверяем есть ли запись в таблице SHOP_PROFILE
					ShopProfile shopProfileDb = findShopProfile(shopProfile);

					//Если профиля нет, добавляем его.
					if (shopProfileDb == null)
					{
						session.save(shopProfile);
						session.flush();
						shopProfileDb = shopProfile;
					}

					//Связываем заказ и клиента.
					order.setProfile(shopProfileDb);
					setState(order, OrderState.RELATED);
					session.update(order);

					return null;
				}
			});
		}
		catch (GateLogicException gle)
		{
			throw gle;
		}
		catch (Exception ex)
		{
			throw new GateException(ex);
		}
	}

	private ShopProfile findShopProfile(final ShopProfile profile) throws GateException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<ShopProfile>()
			{
				public ShopProfile run(Session session) throws Exception
				{
					return (ShopProfile) session.getNamedQuery("com.rssl.phizic.einvoicing.ShopOrders.findShopPerson")
									.setParameter("firstname", profile.getFirstName())
									.setParameter("surname", profile.getSurName())
									.setParameter("patrname", profile.getPatrName())
									.setParameter("passport", profile.getPassport())
									.setParameter("tb", profile.getTb())
									.setParameter("birthdate", profile.getBirthdate())
									.uniqueResult();
				}
			});
		}
		catch (Exception ex)
		{
			throw new GateException(ex);
		}
	}

	public ShopOrderImpl getOrder(String orderUUID) throws GateException, GateLogicException
	{
		try
		{
			ShopOrderImpl shopOrder = findOrder(orderUUID, null);
			if (shopOrder == null)
				return null;

			//Для частичных заказов мы должны предварительно заполнить заказ данными.
			if (shopOrder.getType() == TypeOrder.P)
			{
				ShopProvider provider = GateSingleton.getFactory().service(InvoiceGateBackService.class).getActiveProvider(shopOrder.getReceiverCode());
				ShopInfoServiceWrapper shopInfoServiceWrapper = new ShopInfoServiceWrapper(provider);
				String[] uuid = {shopOrder.getUuid()};
				DocInfoRsType response = shopInfoServiceWrapper.getOrdersInfo(uuid);
				shopOrder = fillOrders(shopOrder, response);
			}

			return shopOrder;
		}
		catch (Exception ex)
		{
			throw new GateException(ex);
		}
	}

	/**
	 * Поиск заказа в БД
	 * @param orderUUID - UUID заказа
	 * @param mode - режим блокировки
	 * @return заказ
	 */
	public ShopOrderImpl findOrder(String orderUUID, LockMode mode) throws GateException
	{
		try
		{
			return simpleService.findSingle(DetachedCriteria.forClass(ShopOrderImpl.class).add(Restrictions.eq("uuid", orderUUID)), mode, null);
		}
		catch (Exception ex)
		{
			throw new GateException(ex);
		}
	}

	private ShopOrderImpl fillOrders(ShopOrderImpl order, DocInfoRsType response) throws Exception
	{
		String externalId = order.getExternalId();
		String responseSPName = response.getSPName();
		if (!order.getReceiverCode().equals(responseSPName))
			throw new GateException("Для заказа с extendedId = " + externalId + " не найден соответствующий заказ c systemName=" + responseSPName);

		InfoType infoType = response.getDocuments()[0];
		if (infoType == null)
			throw new GateException("Для заказа с extendedId = " + externalId + " не найден соответствующий заказ из внешней системы");

		// проверяем статус пришедшего заказа
		if (!Long.valueOf(0).equals(infoType.getStatus().getStatusCode()))
		{
		    log.error("Ошибка при проверке статуса пришедшего заказа: " + infoType.getStatus().getStatusDesc());
			throw new GateException("Для заказа с extendedId = " + externalId + " из внешней системы вернулся заказ со статусом ошибки = " + infoType.getStatus().getStatusCode());
		}

		RegRqDocumentType document = infoType.getDocument();

		if (!document.getId().equals(order.getExternalId()))
			throw new GateException("Ошибка при получении информации по заказу " + externalId);

		Full full = new Full();
		full.fillOrder(document, order);
		// сохраняем в таблицу
		ShopOrderImpl updateOrder = simpleService.update(order, null);
		full.saveOrderDetailInfo(updateOrder);
		return updateOrder;
	}

	public List<ShopOrder> getOrdersByProfileHistory(List<ShopProfile> profiles, Calendar dateFrom, Calendar dateTo, Calendar dateDelayedTo, BigDecimal amountFrom, BigDecimal amountTo, String currency, OrderState... status) throws GateException, GateLogicException
	{
		return getOrdersListByProfileHistory(profiles, dateFrom, dateTo, dateDelayedTo, amountFrom, amountTo, currency, null, null, status);
	}

	public List<ShopOrder> getOrdersByProfileHistory(List<ShopProfile> profiles, Calendar dateFrom, Calendar dateTo, Calendar dateDelayedTo, BigDecimal amountFrom, BigDecimal amountTo, String currency, Long limit, Boolean orderByDelayDate, OrderState... status) throws GateException, GateLogicException
	{
		return getOrdersListByProfileHistory(profiles, dateFrom, dateTo, dateDelayedTo, amountFrom, amountTo, currency, limit, orderByDelayDate, status);
	}

	public List<ShopOrder> getOrdersByProfileHistoryForMainPage(List<ShopProfile> profiles, Calendar dateFrom, Calendar dateTo, BigDecimal amountFrom, BigDecimal amountTo, String currency, OrderState... status) throws GateException, GateLogicException
	{
		return getOrdersListByProfileHistory(profiles, dateFrom, dateTo, dateTo, amountFrom, amountTo, currency, null, null, status);
	}

	private String getOrdersByProfilesQueryName(Boolean orderByDelayDate)
	{
		if (BooleanUtils.isTrue(orderByDelayDate))
		{
			return "com.rssl.phizic.einvoicing.ShopOrders.selectUserOrders.employee.delayed";
		}
		else
		{
			return "com.rssl.phizic.einvoicing.ShopOrders.selectUserOrders";
		}
	}

	private List<ShopOrder> getOrdersListByProfileHistory(List<ShopProfile> profiles, Calendar dateFrom, Calendar dateTo, Calendar dateDelayedTo, BigDecimal amountFrom, BigDecimal amountTo, String currency, Long limit, Boolean orderByDelayDate, OrderState... status) throws GateException, GateLogicException
	{
		try
		{
			List list = new ArrayList<ShopOrder>();
			for (ShopProfile profile : profiles)
			{
				Query query = new ExecutorQuery(HibernateExecutor.getInstance(), getOrdersByProfilesQueryName(orderByDelayDate))
				    .setParameter("personFirstName", profile.getFirstName()).setParameter("personSurName", profile.getSurName()).setParameter("personPatrName", profile.getPatrName())
					.setParameter("personTb", profile.getTb()).setParameter("personPassport", profile.getPassport()).setParameter("personBirthdate", profile.getBirthdate())
				    .setParameter("from_date", dateFrom).setParameter("to_date", dateTo).setParameter("to_delayed_date", dateDelayedTo).setParameter("from_amount", amountFrom).setParameter("to_amount", amountTo)
				    .setParameter("currency", currency);
				query.setParameter("limit", limit == null ? "" : limit);

				if (status.length == 0)
					query.setParameter("noStates", "true");
				else
					query.setParameter("noStates", "false");
				String st[] = new String[status.length];
				int i = 0;
				for (OrderState state : status)
				{
					st[i++] = state.name();
				}
				List<String> states = new ArrayList();
				Boolean getDelayed = false;
				for (int index = 0; index < st.length; index++)
				{
					if (st[index].equals(OrderState.DELAYED.name()))
						getDelayed = true;
					else
						states.add(st[index]);
				}
				query.setParameter("getDelayed", Boolean.toString(getDelayed));
				String state1 = null;
				String state2 = null;
				String state3 = null;
				if (states.size() > 0)
					state1 = states.get(0);
				if (states.size() > 1)
					state2 = states.get(1);
				if (states.size() > 2)
					state3 = states.get(2);
				query.setParameter("state1", state1);
				query.setParameter("state2", state2);
				query.setParameter("state3", state3);
				list.addAll(query.executeList());
			}

			Collections.sort(list, new Comparator<ShopOrder>()
			{
				public int compare(ShopOrder o1, ShopOrder o2)
				{
					return (int)(o2.getDate().getTimeInMillis() - o1.getDate().getTimeInMillis());
				}
			});

			return list;
		}
		catch (DataAccessException e)
		{
			throw new GateException(e);
		}
	}

	public String getOrderInfo(final String orderUuid) throws GateException, GateLogicException
	{
		if (StringHelper.isEmpty(orderUuid))
			return "";

		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<String>()
			{
				public String run(Session session) throws Exception
				{
					return (String) (session.getNamedQuery("com.rssl.phizic.einvoicing.ShopOrders.getOrderInfo")
							.setParameter("uuid", orderUuid)
							.uniqueResult());
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public void changeOrderStatus(final String orderUUID, final OrderState newState, Long nodeId, final String utrrno, String paidBy, Calendar delayDate) throws GateException, GateLogicException
	{
		try
		{
			ShopOrderImpl order = findOrder(orderUUID, LockMode.UPGRADE_NOWAIT);
			if (order == null)
				throw new OrderNotFoundException();

			if (nodeId != null)
			{
				if (order.getNodeId() == null || order.getState() != OrderState.PAYMENT)
					order.setNodeId(nodeId);
				else if (!order.getNodeId().equals(nodeId))
					throw new GateException("Привязка заказа " + orderUUID + " к другому блоку запрещена.");
			}
			else if (newState == OrderState.PAYMENT)
				throw new GateException("Не указан блок для привязки к заказу");


			if ((newState == OrderState.PAYMENT && !order.isMobileCheckout()) || newState == OrderState.WRITE_OFF)
				checkOrder(order, paidBy);

			setState(order, newState);
			if (StringHelper.isNotEmpty(utrrno))
				order.setUtrrno(utrrno);

			if (delayDate != null)
				order.setDelayedPayDate(delayDate);

			simpleService.update(order, null);
			createNotification(order);
			return;
		}
		catch (GateLogicException gle)
		{
			throw gle;
		}
		catch (GateException ge)
		{
			throw ge;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public void changeRecallStatus(final String recallUUID, final RecallState newState, final String utrrno, final RecallType recallType) throws GateException, GateLogicException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					final ShopRecallImpl recalls = simpleService.findSingle(DetachedCriteria.forClass(ShopRecall.class).add(Restrictions.eq("uuid", recallUUID)), null, null);
					setState(recalls, newState);
					final ShopOrderImpl order = findOrder(recalls.getOrderUuid(), LockMode.UPGRADE_NOWAIT);
					if (newState == RecallState.EXECUTED)
						setState(order, recallType == RecallType.PARTIAL ? OrderState.PARTIAL_REFUND : OrderState.REFUND);

					if (StringHelper.isNotEmpty(utrrno))
						recalls.setUtrrno(utrrno);

					simpleService.update(recalls, null);
					simpleService.update(order, null);
					createNotification(recalls);
					return null;
				}
			});
		}
		catch (GateLogicException gle)
		{
			throw gle;
		}
		catch (GateException ge)
		{
			throw ge;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	private void checkOrder(ShopOrderImpl order, String paidBy) throws GateException, GateLogicException
	{
		ShopProvider provider = GateSingleton.getFactory().service(InvoiceGateBackService.class).getActiveProvider(order.getReceiverCode());
		if (!provider.isCheckOrder())
			return;

		//необходима проверка заказа перед оплатой
		ShopInfoServiceWrapper shopInfo = new ShopInfoServiceWrapper(provider);
		try
		{
			if (StringHelper.isEmpty(paidBy) && order.getKind() == OrderKind.AEROFLOT)
				paidBy = ShopConstants.UNKNOWN_CHARGE_OFF_CARD_TYPE;

			DocInfoStatRsType checkRs = shopInfo.getOrdersInfoStat(order.getUuid(), provider.isSendChargeOffInfo() ? paidBy : null);
			if (checkRs.getDocuments().getResult().getStatus().getStatusCode() != 0)
			{
				log.error("Ошибка при проверке интернет-заказа перед оплатой: " + checkRs.getDocuments().getResult().getStatus().getStatusDesc());
				throw new InactualOrderInInternetShopException();
			}
		}
		catch (RemoteException e)
		{
			throw new GateException(e);
		}
	}

	public String getTicketInfo(final String orderUuid) throws GateException, GateLogicException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<String>()
			{
				public String run(Session session) throws Exception
				{
					return (String) (session.getNamedQuery("com.rssl.phizic.einvoicing.ShopOrders.getTicketsInfo")
							.setParameter("uuid", orderUuid)
							.uniqueResult());
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public void markViewed(String orderUUID) throws GateException, GateLogicException
	{
		try
		{
			ShopOrderImpl order = findOrder(orderUUID, LockMode.UPGRADE_NOWAIT);
			if (order == null)
				throw new OrderNotFoundException();
			order.setIsNew(false);
			simpleService.update(order, null);
		}
		catch (GateLogicException gle)
		{
			throw gle;
		}
		catch (GateException ge)
		{
			throw ge;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public List<FacilitatorProvider> findEndPointProviderByCode(String facilitatorCode, int firstResult, int maxResult) throws GateException, GateLogicException
	{
		return facilitatorProviderService.findByFacilitatorCode(facilitatorCode, firstResult, maxResult);
	}

	public List<FacilitatorProvider> findEndPointProviderByName(String name, String inn, int firstResult, int maxResult) throws GateException, GateLogicException
	{
		return facilitatorProviderService.findByName(name, inn, firstResult, maxResult);
	}

	public FacilitatorProvider getEndPointProvider(long providerId) throws GateLogicException, GateException
	{
		return facilitatorProviderService.findById(providerId);
	}

	public void updateEndPointProvider(long providerId, Boolean mcheckoutEnabled, Boolean eInvoicingEnabled, Boolean mbCheckEnabled) throws GateLogicException, GateException
	{
		facilitatorProviderService.update(providerId, mcheckoutEnabled, eInvoicingEnabled, mbCheckEnabled);
	}

	public void updateEndPointProviders(String facilitatorCode, Boolean mcheckoutEnabled, Boolean eInvoicingEnabled, Boolean mbCheckEnabled) throws GateLogicException, GateException
	{
		if (mcheckoutEnabled != null)
			facilitatorProviderService.updateMcheckoutByFacilitator(facilitatorCode, mcheckoutEnabled);
		else if (eInvoicingEnabled != null)
			facilitatorProviderService.updateEInvoicingByFacilitator(facilitatorCode, eInvoicingEnabled);
		else if (mbCheckEnabled != null)
			facilitatorProviderService.updateMbCheckByFacilitator(facilitatorCode, mbCheckEnabled);
		else
			throw new GateException("Не задано свойство для обновления");
	}

	/**
	 * Поиск заказа
	 * @param externalId - внешний идентификатор заказа
	 * @param receiverCode - код внешней системы (поставщика услуг)
	 * @return заказ
	 */
	public ShopOrder getOrder(String externalId, String receiverCode, LockMode mode) throws GateException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(ShopOrder.class)
				.add(Restrictions.eq("externalId", externalId))
				.add(Restrictions.eq("receiverCode", receiverCode));
		try
		{
			return simpleService.findSingle(criteria, mode, null);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Добавить заказ в БД
	 * @param order - заказ
	 * @return заказ
	 */
	public ShopOrder addOrder(final ShopOrder order) throws GateException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<ShopOrder>()
			{
				public ShopOrder run(Session session) throws Exception
				{
					ShopOrder oldOrder = getOrder(order.getExternalId(), order.getReceiverCode(), LockMode.UPGRADE_NOWAIT);
					if (oldOrder != null)
					{
						if (!REISSUE_ALLOWED_STATES.contains(oldOrder.getState()))
							throw new DuplicateOrderException();

						session.delete(oldOrder);
						session.flush();
					}

					ShopProfile shopProfile = order.getProfile();
					if (shopProfile != null)
					{
						ShopProfile profile = findShopProfile(shopProfile);
						if (profile == null)
							profile = simpleService.add(shopProfile, null);

						ShopOrderImpl orderImpl = (ShopOrderImpl) order;
						orderImpl.setProfile(profile);
					}
					order.setIsNew(true);
					return simpleService.add(order, null);
				}
			});
		}
		catch (RegistrationException re)
		{
			throw re;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Обновление заказа в базе. Используется для изменения статуса mobileCheckout
	 *
	 * @param order заказ.
	 * @throws GateException
	 */
	public void updateOrder(final ShopOrder order) throws GateException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					simpleService.update(order, null);
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * @param id ид заказа.
	 * @return получение заказа по ид для MobileCheckout.
	 * @throws GateException
	 */
	public ShopOrderImpl getOrder(Long id) throws GateException
	{
		try
		{
			return simpleService.findSingle(DetachedCriteria.forClass(ShopOrderImpl.class).add(Restrictions.eq("id", id)), null, null);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Добавить запись отмены платежа/возврата товара в БД
	 * @param recall - отмена платежа/возврат товара
	 * @return сохраненный объект
	 */
	public ShopRecallImpl addShopRecall(ShopRecallImpl recall) throws GateException
	{
		try
		{
			return simpleService.add(recall, null);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Выборка оповещений за период
	 * @param period - период выборки
	 * @return список оповещений
	 */
	public List<ShopNotification> getNotifications(final Period period) throws GateException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<ShopNotification>>()
			{
				public List<ShopNotification> run(Session session) throws Exception
				{
					org.hibernate.Query query = session.getNamedQuery("com.rssl.phizic.einvoicing.ShopOrders.getNotifications");
					query.setParameter("periodStart", period.getFromDate());
					query.setParameter("periodEnd", period.getToDate());
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	private ShopNotificationImpl addShopNotification(ShopNotificationImpl notification) throws GateException
	{
		try
		{
			return simpleService.add(notification, null);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Поиск в БД отмен платежа/возвратов товара
	 *
	 * Опорный объект: IDX_SH_RECALLS_ORDER_UUID
	 * Предикаты доступа:  access("ORDER_UUID"=:T)
	 * Кардинальность: максимальное количество отмен/возвратов по заказу
	 *
	 * @param orderUuid - uuid заказа, для которого ищем отмену/возврат
	 * @param date - дата создания заказа
	 * @param receiverCode - код получателя
	 * @param externalId - внешний id отмены/возврата
	 * @param state - статус отмены/возврата
	 * @return отмены/возвраты
	 */
	public List<ShopRecallImpl> findShopRecalls(final String orderUuid, Calendar date, final String receiverCode, final String externalId, final OrderState state) throws GateException
	{
		try
		{
			DetachedCriteria criteria = DetachedCriteria.forClass(ShopRecallImpl.class);
			criteria.add(Restrictions.eq("orderUuid", orderUuid));
			criteria.add(Restrictions.gt("date", date));
			criteria.add(Restrictions.eq("receiverCode", receiverCode));
			criteria.add(Restrictions.eq("externalId", externalId));
			criteria.add(Restrictions.eq("state", state));
			return simpleService.find(criteria, null, null);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Поиск в БД всех отмен платежа/возвратов товара по заказу
	 *
	 * Опорный объект: IDX_SH_RECALLS_ORDER_UUID
	 * Предикаты доступа:  access("ORDER_UUID"=:T)
	 * Кардинальность: максимальное количество отмен/возвратов по заказу
	 *
	 * @param orderUuid - uuid заказа, для которого ищем отмену/возврат
	 * @param date - дата создания заказа
	 * @return отмены/возвраты
	 */
	public List<ShopRecallImpl> findAllShopOrderRecalls(final String orderUuid, Calendar date) throws GateException
	{
		try
		{
			DetachedCriteria criteria = DetachedCriteria.forClass(ShopRecallImpl.class);
			criteria.add(Restrictions.eq("orderUuid", orderUuid));
			criteria.add(Restrictions.gt("date", date));
			return simpleService.find(criteria, null, null);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Сохранить список билетов в БД
	 * @param orderUuid uuid заказа
	 * @param ticketsInfo информация по заказам
	 */
	public void setTicketsInfo(final String orderUuid, final String ticketsInfo) throws GateException
	{
		try
		{
			ExecutorQuery query = new ExecutorQuery(HibernateExecutor.getInstance(), "com.rssl.phizic.einvoicing.ShopOrders.setTicketsInfo");
			query.setParameter("uuid", orderUuid);
			query.setParameter("ticketsInfo", ticketsInfo);
			query.executeUpdate();
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	private void setState(ShopOrderImpl order, OrderState newState) throws GateException
	{
		for (OrderState state : NEXT_STATE.get(order.getState()))
		{
			if(state == newState)
			{
				order.setState(state);
				return;
			}
		}
		throw new GateException("Невозможно установить статус " + newState + " заказу в статусе " + order.getState());
	}

	private void setState(ShopRecallImpl recall, RecallState newState) throws GateException
	{
		for (RecallState state : NEXT_STATE_REFUND.get(recall.getState()))
		{
			if(state == newState)
			{
				recall.setState(state);
				return;
			}
		}
		throw new GateException("Невозможно установить статус " + newState + " для отмены платежа/возврата товара в статусе " + recall.getState());
	}

	private void createNotification(ShopOrder order)
	{
		if (!NOTIFICATION_ORDER_STATES.contains(order.getState()))
			return;

		ShopNotificationImpl notification = new ShopNotificationImpl(order);
		try
		{
			addShopNotification(notification);
		}
		catch (GateException e)
		{
			log.error("Ошибка при создании оповещения для заказа uuid=" + order.getUuid(), e);
		}
	}

	private void createNotification(ShopRecallImpl recall)
	{
		if (RecallState.EXECUTED != recall.getState())
			return;

		ShopNotificationImpl notification = new ShopNotificationImpl(recall);
		try
		{
			addShopNotification(notification);
		}
		catch (GateException e)
		{
			log.error("Ошибка при создании оповещения для отмены/возврата uuid=" + recall.getUuid(), e);
		}
	}

	/**
	 * Обновить оповещение в БД
	 * @param notification - оповещение
	 */
	public void updateNotification(ShopNotification notification) throws GateException
	{
		try
		{
			simpleService.update(notification, null);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Удаление старых заказов, несвязанных с клиентом, созданных более n дней назад
	 * @param days - кол-во дней назад от текущей даты
	 */
	public void removeOrders(Integer days) throws GateException
	{
		final Calendar date = DateHelper.getCurrentDate();
		date.add(Calendar.DAY_OF_MONTH, -days);

		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					org.hibernate.Query query = session.getNamedQuery("com.rssl.phizic.einvoicing.ShopOrders.removeOldOrders");
					query.setParameter("date", date);
					query.executeUpdate();
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Список зависших заказов в статусах «списание средств» или «ошибка исполнения»
	 * @param period - период выборки
	 * @return список заказов
	 */
	public List<ShopOrder> getDelayedOrders(final Period period) throws GateException
	{
		if (period == null)
			return null;

		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<ShopOrder>>()
			{
				public List<ShopOrder> run(Session session) throws Exception
				{
					org.hibernate.Query query = session.getNamedQuery("com.rssl.phizic.einvoicing.ShopOrders.getDelayedOrders");
					query.setParameter("periodStart", period.getFromDate());
					query.setParameter("periodEnd", period.getToDate());
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Список зависших отмен/возвратов в статусах «принят» или «ошибка»
	 * @param period - период выборки
	 * @return список отмен/возвратов
	 */
	public List<ShopRecall> getDelayedRecalls(final Period period) throws GateException
	{
		if (period == null)
			return null;

		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<ShopRecall>>()
			{
				public List<ShopRecall> run(Session session) throws Exception
				{
					org.hibernate.Query query = session.getNamedQuery("com.rssl.phizic.einvoicing.ShopOrders.getDelayedRecalls");
					query.setParameter("periodStart", period.getFromDate());
					query.setParameter("periodEnd", period.getToDate());
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}
