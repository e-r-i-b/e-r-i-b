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
 * ������ ��� ������ � ��������-��������.
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
	 * ���������� ���������� �� ����� ����������.
	 * @param systemName �������� ����������.
	 * @return ���������.
	 */
	public InternetShopsServiceProvider getRecipientBySystemName(String systemName) throws BusinessException
	{
		return providerService.getRecipientActivityBySystemName(systemName);
	}

	/**
	 * ���������� �����, ����������� � ������
	 *
	 * @param orderUuid - �����
	 * @return ����� ��� null, ���� � ������ ��� ���������� �������
	 * @throws BusinessException ���� ���-�� ����� �� ���
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
	 * ����� �������������� ������ �� ��� ��������� � ����.
	 * @param documentId id ���������.
	 * @return ������������� ������.
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
	 * ����� ������� ��������� ������ ������.
	 * @param orderUuid - ������������� ������.
	 * @return ������ ������, null - ��������� ���.
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
	 * ����� ������� ��������� ������ �������/�������� ������ �� ������.
	 * @param orderUuid - ������������� ������, �� �������� ����������� ������/�������.
	 * @param recallUuid - ������������� ������/��������.
	 * @return ������ ������.
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
	 * ���������� ���������� �� ������ ������
	 * @param uuid - ������������� ��������
	 * @return ��������� ���������� ������� ������, ���� Null
	 * @throws BusinessException
	 */
	public InternetShopsServiceProvider getRecipientByOrderUuid(String uuid) throws BusinessException
	{
		ShopOrder order = getShopOrder(uuid);
		if (order == null)
			return null;

		InternetShopsServiceProvider provider = getRecipientBySystemName(order.getReceiverCode());

		if (provider == null)    // ��� ������ ����������
			return null;

		return provider;
	}

	/**
	 * �������� ������ �� ������
	 * @param uuid  - ������������� �������
	 * @return null - ���� ������ �� �������; ����� ����������� ������
	 * @throws BusinessException: ������� null, ������� ������ 1-�� ������
	 */
	public ShopOrder getShopOrder(String uuid) throws BusinessException
	{
		try
		{
			if (StringHelper.isEmpty(uuid))
				throw new BusinessException("����������� ����� uuid="+uuid);

			ShopOrder order = GateSingleton.getFactory().service(ShopOrderService.class).getOrder(uuid);
			if (order == null)
				throw new BusinessException("�� ������� ����� ����� �� uuid=" + uuid);

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
	 * �������� ������ � �������
	 * @param uuid - ����� ������ � ����� �������
	 * @param payment - ������
	 */
	public void linkPayment(String uuid, BusinessDocumentBase payment) throws BusinessException
	{
		try
		{
			if (payment instanceof JurPayment)
			{
				ShopOrder order = getShopOrder(uuid);

				if (order == null)
					throw new BusinessException("����� �� uuid=" + uuid + " �� ������");
			}

			BusinessDocumentToOrder bdto = new BusinessDocumentToOrder();
			bdto.setOrderUuid(uuid);
			bdto.setId(payment.getId());
			simpleService.add(bdto);
		}
		catch(BusinessException e)
		{
			throw new BusinessException("������ �������� ������ � �������", e);
		}
	}

	/**
	 * ��������� ����� �� �������������� ������� � �������������
	 * @param uuid  - ������������� �������
	 * @param profile - �������
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
	 * ��������� ������/������� �� ���������.
	 * @param withdrawMode ����� ��������.
	 * @param valuesSource ������ �����.
	 * @param parent ������������ ��������.
	 * @param money ����� �������.
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
	 * ���� � ��� �� ������.
	 *
	 * @param profile ������� ������� �� ������.
	 * @param context ���������� � ����� �������.
	 * @return ���� � ��� �� ������ ��� ���.
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
	 * ��������� ������ ������� �� ������� ������ ������������ �� ��� ������� ������� �������.
	 *
	 * @param context �������� ��������������.
	 * @return ������ ��� �������.
	 * @throws BusinessException
	 */
	public List<ShopProfile> getProfileHistory(AuthenticationContext context) throws BusinessException
	{
		return getProfileHistory(getUserInfo(context));
	}

	/**
	 * ��������� �������� ��������-������� �� �������� "�������" �������
	 * @param person - �������
	 * @return - ������ ��������
	 * @throws BusinessException
	 */
	public List<ShopProfile> getProfileHistory(PersonBase person) throws BusinessException
	{
		return getProfileHistory(getUserInfo(person));
	}

	/**
	 * @param userInfo ���������� � ������������.
	 * @return ������� ��������� �������.
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
	 * ����������� Person � ShopPerson.
	 * @param context ������� ��������������.
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
			throw new BusinessException("������ ��� �������������� ���� �������� �������", e);
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
	 * @param orderUuid ������� ������������� ������.
	 * @return ������ ������� ��� ���������� � ����� �����������.
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
	 * �������� ������ ���������.
	 *
	 * @param uuid ������������� ������.
	 * @param newState ����� ���������.
	 * @param utrnno ������.
	 * @param paidBy ������ � ...
	 * @throws BusinessException
	 */
	public void changeOrderStatus(String uuid, OrderState newState, String utrnno, String paidBy) throws BusinessException, BusinessLogicException
	{
		changeOrderStatus(uuid, newState, utrnno, paidBy, null);
	}

	/**
	 * �������� ������ ���������.
	 *
	 * @param uuid ������������� ������.
	 * @param newState ����� ���������.
	 * @param utrnno ������.
	 * @param paidBy ������ � ...
	 * @param delayDate ���� �� ������ ������������� ������ � ������ ��������� ������� �� DELAYED
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
	 * �������� ������ ������ ������� ��� �������� �������.
	 *
	 * @param uuid ������������� ������.
	 * @param newState ����� ���������.
	 * @param utrnno ������.
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
	 * ��������� ������ ������� � �������� � ������� ��� ������� � "��������"
	 * @param orderUuid - uuid ������
	 * @param ticketInfo - xml � ��������� �������
	 */
	public void setTicketsInfo(String orderUuid, String ticketInfo) throws BusinessException, BusinessLogicException
	{

		BusinessDocument document = DocumentHelper.getPaymentByOrder(orderUuid);
		if (!document.getFormName().equals(FormConstants.AIRLINE_RESERVATION_PAYMENT_FORM))
			throw new BusinessException("������ ��������� ������ �������: ������������ ��� ��������� c id=" + document.getId());

		setTicketsInfo((JurPayment) document, ticketInfo);
	}

	/**
	 * ��������� ������ ������� � �������� � ������� ��� ������� � "��������"
	 * @param payment - �������� ������ �����
	 * @param ticketInfo - xml � ��������� �������
	 */
	public void setTicketsInfo(JurPayment payment, String ticketInfo) throws BusinessException, BusinessLogicException
	{

		if (!payment.getState().getCode().equals(DocumentEvent.TICKETS_WAITING.name()))
			throw new BusinessException("������ ��������� ������ �������: ������������ ������ ��������� c id=" + payment.getId());

		payment.setTicketInfo(ticketInfo);
		StateMachineExecutor executor = new StateMachineExecutor(stateMachineService.getStateMachineByFormName(payment.getFormName()));
		executor.initialize(payment);
		executor.fireEvent(new ObjectEvent(DocumentEvent.EXECUTE, ObjectEvent.SYSTEM_EVENT_TYPE));

		businessDocumentService.addOrUpdate(payment);
	}
}
