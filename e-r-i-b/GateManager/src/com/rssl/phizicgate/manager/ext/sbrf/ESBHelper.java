package com.rssl.phizicgate.manager.ext.sbrf;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.clients.BackRefClientService;
import com.rssl.phizic.gate.dictionaries.billing.Billing;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.AccountToCardTransfer;
import com.rssl.phizic.gate.payments.CardToAccountTransfer;
import com.rssl.phizic.gate.payments.LoanTransfer;
import com.rssl.phizic.gate.payments.autosubscriptions.AutoSubscriptionClaim;
import com.rssl.phizic.gate.payments.longoffer.CardPaymentSystemPaymentLongOffer;
import com.rssl.phizic.gate.payments.systems.AbstractPaymentSystemPayment;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizic.utils.DocumentConfig;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.manager.config.AdaptersConfig;
import com.rssl.phizicgate.manager.routing.Adapter;
import com.rssl.phizicgate.manager.routing.AdapterType;
import com.rssl.phizicgate.manager.services.IDHelper;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * @author krenev
 * @ created 22.09.2010
 * @ $Author$
 * @ $Revision$
 */
public class ESBHelper
{
	public static final String ESB_SUPPORTED ="esbSupported";
	public static final String WITH_SACS_SERVICE = "LossPassbookApplicationThroughKSSH";
	private static final String QUERY_PREFIX = ESBHelper.class.getName();

	public static boolean isESBSupported(GateDocument document) throws GateException
	{
		Class<? extends GateDocument> type = document.getType();
		//если это погашение кредита, то сразу идем в шину. см запрос ENH032337: Дать функционал кредитов для СБОЛ-клиентов 
		if(type == LoanTransfer.class)
			return true;
		//если БП в подразделении клиента есть, то идем в шину.
		if (isESBSupported(document.getInternalOwnerId()))
			return true;
		if (type == AccountToCardTransfer.class || type == CardToAccountTransfer.class)
		{
			return isSRBPayment(document);
		}
		return false;
	}

	public static boolean isSRBPayment(GateDocument document) throws GateException
	{
		String tb = document.getOffice().getCode().getFields().get("region");
		return getSrbTBCodes().contains(tb);
	}

	public static List<String> getSrbTBCodes()
	{
		return ConfigFactory.getConfig(DocumentConfig.class).getAllTbCodesSrb();
	}

	/**
	 * подключен ли офис к шине
	 * @param office офис для проверки
	 * @return да/нет
	 */
	public static boolean isESBSupported(final Office office) throws GateException
	{
		return true;
	}

	/**
	 * Подключена ли сущность с идентификатором ID к шине
	 * @param id идентификатор для проверки
	 * @return да/нет
	 */
	public static boolean isESBSupported(String id) throws GateException
	{
		if (IDHelper.matchRoutableId(id))
			return false;
		Long loginId = parseLoginId(id);
		return isESBSupported(loginId);
	}

	/**
	 * Подключено ли подразделение клиента к шине
	 * @param loginId логин клиента
	 * @return да/нет
	 */
	public static boolean isESBSupported(final Long loginId) throws GateException
	{
		if (loginId == null)
		{
			throw new IllegalArgumentException("Не задан loginId");
		}

		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
				{
					public Boolean run(Session session)
					{
						Query query = session.getNamedQuery(QUERY_PREFIX + ".isESBSupportedByLoginId");
						query.setParameter("loginId", loginId);
						query.setMaxResults(1);
						Boolean result = (Boolean) BeanHelper.getProperty(query.uniqueResult(), ESB_SUPPORTED);
						return result != null ? result : false;
					}
				}
			);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	//todo убрать после того как во всех ТБ на КСШ будет реализован фукционал подачи заявки об утере сберкнижки (CHG039252)
	/**
	 * Определяет реализован ли для ТБ на КСШ функционал подачи завки об утере сберкнижки. Данный флаг выставляется в админке в правах.
	 * Если выставлено "WITH_SACS_SERVICE", то запрос вернет одну запись, иначе - нет. Выставленный флаг означает что для данного ТБ в шине
	 * реализован функционал подачи заявки (интерфейс SACS). 
	 * @param loginId - ид клиента
	 * @return boolean - да/нет
	 * @throws GateException
	 */
	public static boolean isSACSSupported(final Long loginId) throws GateException
	{
		if (loginId == null)
		{
			throw new IllegalArgumentException("Не задан loginId");
		}

		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
				{
					public Boolean run(Session session)
					{
						Query query = session.getNamedQuery(QUERY_PREFIX + ".isSACSSupported");
						query.setParameter("login_id", loginId);
						query.setParameter("service_key",WITH_SACS_SERVICE);
						query.setMaxResults(1);
						Integer result = (Integer) query.uniqueResult();
						return result != null;
					}
				}
			);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public static Long parseLoginId(String externalId)
	{
		return Long.valueOf(parseExternalId(externalId, 3));
	}

	public static String parseSystemId(String externalId)
	{
		return parseExternalId(externalId, 1);
	}

	private static String parseExternalId(String externalId, int paramIndex)
	{
		//В ESB идентификатор сущности имеет вид
		// <id сущности>^< systemId >^<rbBrchId>^<loginId>

		try
		{
			return externalId.trim().split("\\^")[paramIndex];
		}
		catch (Exception e)
		{
			throw new IllegalArgumentException("Неверный формат идентификатора "+ externalId+ " ,ожидается идентификатор в формате ESB", e);
		}
	}

	/**
	 * Найти офис по ESB идентификатору <id сущности>^< systemId >^<rbBrchId>^<loginId>
	 * @param externalId внешний идентификатор
	 * @return офис
	 * @throws GateException
	 * @throws GateLogicException
	 * @throws NumberFormatException - передан идентификатор не в формате ESB
	 * */
	public static Office findOfficeByESBId(String externalId) throws GateException, GateLogicException
	{
		Long loginId = parseLoginId(externalId);
		BackRefClientService clientService = GateSingleton.getFactory().service(BackRefClientService.class);
		return clientService.getClientById(loginId).getOffice();
	}

	/**
	 * @return адаптер iqwave.
	 */
	public static Adapter getIQWaveAdapter()
	{
		return ConfigFactory.getConfig(AdaptersConfig.class).getCardTransfersAdapter();
	}

	/**
	 * Проходит ли билинговый платеж через iqwave
	 * @param document -  документ
	 * @return true - является
	 * @throws GateException
	 */
	public static boolean isIQWavePayment(AbstractPaymentSystemPayment document) throws GateException
	{
		return isIQWavePayment(IDHelper.restoreRouteInfo(document.getReceiverPointCode()));
	}

	/**
	 * Проходит ли билинговый платеж через iqwave
	 * @param uuid внешний идентифкатор поставщика
	 * @return true - является
	 * @throws GateException
	 */
	public static boolean isIQWavePayment(String uuid) throws GateException
	{
		Adapter adapter = getIQWaveAdapter();
		if (adapter == null)
		{
			throw new GateException("Не задана внешняя система для карточных переводов");
		}

		return adapter.getUUID().equals(uuid);
	}

	/**
	 * @param document платеж
	 * @return подписка на автоплатеж? (автоплатеж в АС "Автоплатежи" - билинговый автоплатеж через шину)
	 */
	public static boolean isAutoSubscriptionPayment(GateDocument document)
	{
		return AutoSubscriptionClaim.class.isAssignableFrom(document.getType());
	}

	/**
	 * Является ли билинг шинным билингом
	 * @param billing билинг
	 * @return true - билинг является шинным билингом
	 */
	public static boolean isESBBilling(final Billing billing) throws GateException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session)
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + ".isESBBilling");
					query.setParameter("synchKey", billing.getSynchKey());
					query.setMaxResults(1);
					return StringHelper.getEmptyIfNull(query.uniqueResult()).equals("1");
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Является ли адаптер с заданным uuid шинным
	 * @param uuid адаптера
	 * @return true - адаптер является шинным
	 * @throws GateException
	 */
	public static boolean isESBAdapter(final String uuid) throws GateException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session)
				{
					Criteria criteria = session.createCriteria(Adapter.class);
					criteria.add(Restrictions.eq("UUID", uuid));
					criteria.setMaxResults(1);
					return ((Adapter)criteria.uniqueResult()).getType() == AdapterType.ESB;
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}
