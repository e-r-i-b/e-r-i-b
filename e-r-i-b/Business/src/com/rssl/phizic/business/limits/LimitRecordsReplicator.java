package com.rssl.phizic.business.limits;

import com.rssl.phizic.auth.LogonSession;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.limits.users.LimitDocumentInfo;
import com.rssl.phizic.business.limits.users.OperType;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.persons.PersonKey;
import com.rssl.phizic.common.types.ApplicationType;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.limits.ChannelType;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.limits.handlers.ProfileInfo;
import com.rssl.phizic.limits.servises.DocumentTransaction;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;

import java.util.*;

/**
 * Репликатор лимитов из общей базы в базу блока
 * @author niculichev
 * @ created 28.01.14
 * @ $Author$
 * @ $Revision$
 */
public class LimitRecordsReplicator
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private static final LimitRecordsReplicator limitRecordsReplicator = new LimitRecordsReplicator();
	private static final LimitService limitService = new LimitService();

	private LimitRecordsReplicator() {}

	public static LimitRecordsReplicator getIt()
	{
		return limitRecordsReplicator;
	}

	/**
	 * Реплицировать недостающие лимиты из общей базы лимитов
	 * @param person персона
	 * @param channelType канал репликации
	 * @param fromDate дата отчета
	 */
	public void replicate(Person person, ChannelType channelType, Calendar fromDate)
	{
		Session session = null;
		Transaction transaction = null;

		try
		{
			//noinspection HibernateResourceOpenedButNotSafelyClosed
			session = HibernateExecutor.getSessionFactory().openSession();
			transaction = session.beginTransaction();

			// лочим запись чтоб никто не входил в данном канале
			lockLogonSession(session, person.getLogin().getId(), getApplicationType(channelType));

			// добавляем недостающие записи
			List<LimitDocumentInfo> addedLimitsRecord = getDifferentLimitRecords(person, fromDate, channelType);
			if(CollectionUtils.isNotEmpty(addedLimitsRecord))
				limitService.addLimitDocumentInfos(addedLimitsRecord);

			// коммитим, снимая блокировку
			transaction.commit();
		}
		catch (Exception e)
		{
			log.error("Ошибка при репликации лимитов для персоны с id = " + person.getId() + " в канале " + channelType, e);
			if(transaction != null)
			{
				transaction.rollback();
			}
		}
		finally
		{
			if(session != null)
			{
				session.close();
			}
		}
	}

	private void lockLogonSession(Session session, Long loginId, ApplicationType applicationType)
	{
		session.createCriteria(LogonSession.class)
				.add(Expression.eq("loginId", loginId))
				.add(Expression.eq("applicationType", applicationType))
				.setLockMode(LockMode.UPGRADE)
				.list();
	}

	private List<LimitDocumentInfo> getDifferentLimitRecords(Person person, Calendar fromDate, ChannelType channelType) throws BusinessException
	{
		List<PersonKey> oldIdentities = getOldPersonKes(person, fromDate);

		List<DocumentTransaction> documentTransactions = getExternalLimitRecords(person, fromDate, channelType, oldIdentities);
		if(CollectionUtils.isEmpty(documentTransactions))
			return Collections.emptyList();

		Map<String, LimitDocumentInfo> limitDocumentInfos = convertToMap(getInternalLimitRecords(person, fromDate, channelType));
		List<LimitDocumentInfo> result = new ArrayList<LimitDocumentInfo>();

		for(DocumentTransaction documentTransaction : documentTransactions)
		{
			LimitDocumentInfo documentInfo = limitDocumentInfos.get(documentTransaction.getExternalId());
			if(documentInfo != null)
				continue;

			try
			{
				result.add(createLimitDocumentInfoByDocumentTransaction(documentTransaction, person));
			}
			catch (BusinessException e)
			{
				log.error(e.getMessage(), e);
			}
		}

		return result;
	}

	/**
	 * Получить записи по лимитам по персоне из внешней базы начиная с даты отчета
	 * @param person персона
	 * @param fromChangeDate дата отсчета
	 * @param channelType канал
	 * @param oldPersonKeys старые идентификационные данные
	 * @return записи по лимитам
	 */
	private List<DocumentTransaction> getExternalLimitRecords(Person person,  Calendar fromChangeDate, ChannelType channelType, List<PersonKey> oldPersonKeys)
	{
		List<DocumentTransaction> result = new ArrayList<DocumentTransaction>();

		try
		{
			result.addAll(DocumentTransaction.find(createExternalLimitsProfileInfo(person), fromChangeDate, channelType));

			if(CollectionUtils.isEmpty(oldPersonKeys))
				return result;

			for(PersonKey personKey : oldPersonKeys)
				result.addAll(DocumentTransaction.find(createExternalLimitsProfileInfo(personKey), fromChangeDate, channelType));
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}

		return result;
	}

	/**
	 * Получить записи по лимитам по персоне из внутренней базы начиная с даты отчета
	 * @param person персона
	 * @param fromChangeDate дата отсчета
	 * @param channelType канал
	 * @return записи по лимитам
	 */
	private List<LimitDocumentInfo> getInternalLimitRecords(Person person, Calendar fromChangeDate, ChannelType channelType) throws BusinessException
	{
		return limitService.getLimitDocumentInfoByPerson(person, fromChangeDate, channelType);
	}

	/**
	 * Создать запись по лимитам внутренней базы по сущности из внешней базы по лимитам
	 * @param transaction сущность базы по лимитам
	 * @param person персона
	 * @return сущность внутренней базы
	 */
	private LimitDocumentInfo createLimitDocumentInfoByDocumentTransaction(DocumentTransaction transaction, Person person) throws BusinessException
	{
		LimitDocumentInfo res = new LimitDocumentInfo();

		try
		{
			CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
			res.setAmount(new Money(transaction.getAmountValue(),
					currencyService.findByAlphabeticCode(transaction.getAmountCurrency())));
		}
		catch (GateException e)
		{
			throw  new BusinessException(e);
		}

		res.setExternalId(transaction.getExternalId());
		res.setDocumentExternalId(transaction.getDocumentExternalId());
		res.setProfileId(person.getId());
		res.setChannelType(transaction.getChannelType());
		res.setOperationDate(transaction.getOperationDate());
		res.setLimitInfosAsString(transaction.getLimitsInfo());

		switch(transaction.getOperationType())
		{
			case ADD:
				res.setOperationType(OperType.commit);
				break;
			case ROLL_BACK:
				res.setOperationType(OperType.rollback);
				break;
		}

		return res;
	}

	/**
	 * Создать профиль внешней базы лимитов по персоне
	 * @param person персона
	 * @return профиль для базы лимитов
	 */
	private ProfileInfo createExternalLimitsProfileInfo(Person person) throws BusinessException
	{
		PersonDocument document = PersonHelper.getPersonPassportWay(person);
		return new ProfileInfo(
				person.getFirstName(),
				person.getSurName(),
				person.getPatrName(),
				StringHelper.getEmptyIfNull(document.getDocumentSeries()) + StringHelper.getEmptyIfNull(document.getDocumentNumber()),
				PersonHelper.getPersonTb(person),
				person.getBirthDay());
	}

	/**
	 * Создать профиль внешней базы лимитов по записи из истории
	 * @param personKey сменненные данные клиента
	 * @return профиль для базы лимитов
	 */
	private ProfileInfo createExternalLimitsProfileInfo(PersonKey personKey)
	{
		return new ProfileInfo(
				personKey.getFirstName(),
				personKey.getSurName(),
				personKey.getPatrName(),
				personKey.getPassport(),
				personKey.getTb(),
				personKey.getBirthDay());
	}

	private Map<String, LimitDocumentInfo> convertToMap(List<LimitDocumentInfo> limitDocumentInfos)
	{
		if(CollectionUtils.isEmpty(limitDocumentInfos))
			return Collections.emptyMap();

		Map<String, LimitDocumentInfo> result = new HashMap<String, LimitDocumentInfo>(limitDocumentInfos.size());
		for(LimitDocumentInfo documentInfo : limitDocumentInfos)
			result.put(documentInfo.getExternalId(), documentInfo);

		return result;
	}

	private List<PersonKey> getOldPersonKes(final Person person, final Calendar fromDate) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<PersonKey>>()
			{
				public List<PersonKey> run(Session session) throws Exception
				{
					Criteria criteria = session.createCriteria(PersonKey.class);
					criteria.add(Expression.eq("loginId", person.getLogin().getId()));
					criteria.add(Expression.ge("creationDate", fromDate));

					return criteria.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private ApplicationType getApplicationType(ChannelType channelType)
	{
		switch (channelType)
		{
			case CALL_CENTR:
				return ApplicationType.Admin;
			case INTERNET_CLIENT:
				return ApplicationType.Client;
			case MOBILE_API:
				return ApplicationType.Mobile;
			default:
				return ApplicationType.Other;
		}
	}
}
