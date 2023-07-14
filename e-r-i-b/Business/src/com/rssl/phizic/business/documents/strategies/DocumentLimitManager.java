package com.rssl.phizic.business.documents.strategies;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.documents.strategies.limits.CheckDocumentStrategy;
import com.rssl.phizic.business.documents.strategies.limits.Constants;
import com.rssl.phizic.business.limits.*;
import com.rssl.phizic.business.limits.users.LimitDocumentInfo;
import com.rssl.phizic.business.limits.users.OperType;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.common.types.limits.ChannelType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.limits.LimitsConfig;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.exceptions.PostConfirmCalcCommission;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.jms.JmsService;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.LockAcquisitionException;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.rssl.phizic.business.documents.strategies.limits.Constants.PROCESS_DOCUMENT_ERROR_MESSAGE;
import static com.rssl.phizic.common.types.limits.Constants.FACTORY_NAME;
import static com.rssl.phizic.common.types.limits.Constants.QUEUE_NAME;
import static com.rssl.phizic.logging.Constants.LOG_MODULE_CORE;

/**
 * Менеджер для обработки лимитов по документу
 * @author niculichev
 * @ created 15.01.14
 * @ $Author$
 * @ $Revision$
 */
public class DocumentLimitManager
{
	private static final Log log = PhizICLogFactory.getLog(LOG_MODULE_CORE);
	private static final LimitService limitService = new LimitService();
	private static final JmsService jmsService = new JmsService();
	private static final long DELAY_TIME = 300L;                //небольшая пауза перед следующей проверкой

	private String operationUID;
	private Calendar operationDate;
	private BusinessDocument document;
	private Person person;
	private List<ProcessDocumentStrategy> limitStrategies;

	/**
	 * Создание менеджера  по лимитам
	 * @param document документ
	 * @param operationUID уид операции(данные для идентификации транзакции)
	 * @param operationDate дата операции(данные для идентификации транзакции)
	 * @param person персона для учета лимитов(при откате можно null)
	 * @param strategies стратегии, которые должны учитываться
	 * @throws BusinessException
	 */
	private DocumentLimitManager(BusinessDocument document, String operationUID, Calendar operationDate, Person person, List<Class<? extends ProcessDocumentStrategy>> strategies) throws BusinessException, BusinessLogicException
	{
		this.document = document;
		this.operationUID = operationUID;
		this.operationDate = operationDate;
		this.person = person;
		this.limitStrategies = getInitializedLimitsStrategies(strategies, document);
	}

	/**
	 * Создание менеджера для ОБРАБОТКИ по лимитам
	 * @param document документ
	 * @param operationUID уид операции(данные для идентификации транзакции)
	 * @param operationDate дата операции(данные для идентификации транзакции)
	 * @param person персона для учета лимитов(при откате можно null)
	 * @param strategies стратегии, которые должны учитываться
	 * @return сущность менеджера
	 * @throws BusinessException
	 */
	public static DocumentLimitManager createProcessLimitManager(BusinessDocument document, String operationUID, Calendar operationDate, Person person, List<Class<? extends ProcessDocumentStrategy>> strategies) throws BusinessLogicException, BusinessException
	{
		return new DocumentLimitManager(document, operationUID, operationDate, person, strategies);
	}

	/**
	 * Создание менеджера для ПРОВЕРКИ по лимитам
	 * @param document документ
	 * @param person персона, относительно которой считаются лимиты
	 * @param strategies стратегии, которые должны учитываться
	 * @return сущность менеджера
	 * @throws BusinessException
	 */
	public static DocumentLimitManager createCheckLimitManager(BusinessDocument document, Person person, List<Class<? extends ProcessDocumentStrategy>> strategies) throws BusinessLogicException, BusinessException
	{
		return new DocumentLimitManager(document, null, null, person, strategies);
	}

	/**
	 * Создание менеджера для ОТКАТА лимитов
	 * @param operationUID уид операции(данные для идентификации транзакции)
	 * @param operationDate дата операции(данные для идентификации транзакции)
	 * @return сущность менеджера
	 * @throws BusinessException
	 */
	public static DocumentLimitManager createRevertLimitManager(String operationUID, Calendar operationDate) throws BusinessLogicException, BusinessException
	{
		return new DocumentLimitManager(null, operationUID, operationDate, null, null);
	}


	/**
	 * Пропустить лимиты через документ с сохранением для последующих проверок
	 * @param successLimitDocumentAction дейсвие в случае успешного прохождения по лимитам
	 * @throws BusinessLogicException ислючение в случае если не смогли пропустить по лимитам
	 * @throws BusinessException
	 */
	public void processLimits(DocumentAction successLimitDocumentAction) throws BusinessLogicException, BusinessException
	{
		if(operationUID == null && operationDate == null)
			throw new IllegalStateException("Не заданы идентификационные данные для создания транзакции по лимитам.");

		// создаем сущность транзакции
		LimitDocumentInfo documentInfo = createLimitDocumentInfo();

		// если при обработке лимитов не упали и все успешно, обрабабываем документ
		BusinessLogicException ex = processLimits(documentInfo);

		if(ex == null && successLimitDocumentAction != null)
		{
			try
			{
				// не упали, и козырного исключения никакого не пришло,
				// значит все норм, обрабатываем документ
				successLimitDocumentAction.action(document);
			}
			catch (BusinessLogicException e)
			{
				log.error(String.format(PROCESS_DOCUMENT_ERROR_MESSAGE, document.getId()), e);
				revertLimits(documentInfo);
				throw e;
			}
			catch (BusinessException e)
			{
				log.error(String.format(PROCESS_DOCUMENT_ERROR_MESSAGE, document.getId()), e);
				revertLimits(documentInfo);
				throw e;
			}
			catch (InactiveExternalSystemException e)
			{
				log.error(String.format(PROCESS_DOCUMENT_ERROR_MESSAGE, document.getId()), e);
				revertLimits(documentInfo);
				throw e;
			}
			catch(PostConfirmCalcCommission e)
			{
				//штатная ситуация, в лог не пишем.
				revertLimits(documentInfo);
				throw e;
			}
		}

		// оповещаем хранилище лимитов если нужно
		if(isNotifyLimitStorage() && isNeedSaveTransaction(documentInfo))
			notifyLimitProcessStorage(documentInfo, person);

		// оповещаем вызывающий код, что не все гладко
		if(ex != null)
			throw ex;
	}

	/**
	 * Проверить документ на вхождение в лимиты
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void checkAndThrowLimits() throws BusinessException, BusinessLogicException
	{
		ClientAccumulateLimitsInfo limitsInfo = buildLimitAmountInfoByPerson(person, LimitHelper.getChannelType(document));
		// проверяем лимиты
		for(ProcessDocumentStrategy strategy : limitStrategies)
		{
			if(!(strategy instanceof CheckDocumentStrategy))
				throw new BusinessException("Невозмжно проверить лимиты по стратегии " + strategy.getClass().getName() + ", т.к. она не реализиует интерфейс " + CheckDocumentStrategy.class.getName());

			((CheckDocumentStrategy) strategy).checkAndThrow(limitsInfo);
		}
	}

	/**
	 * Откатить лимиты по документу
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void revertLimits() throws BusinessLogicException, BusinessException
	{
		if(operationUID == null && operationDate == null)
			throw new IllegalStateException("Не заданы идентификационные данные для отката транзакции по лимитам.");

		// если при обработке лимитов не упали и все успешно, обрабабываем документ
		LimitDocumentInfo documentInfo = revertLimits(operationUID, operationDate);

		// оповещаем хранилище лимитов
		if(documentInfo != null && isNotifyLimitStorage())
			notifyLimitRevertStorage(documentInfo);
	}

	private LimitDocumentInfo revertLimits(String operationUID, Calendar operationDate) throws BusinessException, BusinessLogicException
	{
		LimitDocumentInfo original = limitService.findLimitDocumentInfo(operationUID, operationDate);
		if(original == null)
		{
			log.error("Не найдена запись для отката, относящаяся к документу с operationUID = " + operationUID + " и датой " + operationDate);
			return null;
		}

		revertLimits(original);
		return original;
	}

	private void revertLimits(LimitDocumentInfo documentInfo) throws BusinessLogicException, BusinessException
	{
		// если при обработке лимитов не упали и все успешно, обрабабываем документ
		Session session = null;
		Transaction transaction = null;

		try
		{
			//noinspection HibernateResourceOpenedButNotSafelyClosed
			session = HibernateExecutor.getSessionFactory().openSession();
			transaction = session.beginTransaction();

			// сохранять не надо и откатывать значит тоже
			if(isNeedSaveTransaction(documentInfo))
			{
				LimitDocumentInfo revertDocumentInfo = new LimitDocumentInfo(documentInfo);
				revertDocumentInfo.setOperationType(OperType.rollback);

				session.save(revertDocumentInfo);
			}

			transaction.commit();
		}
		catch (Exception e)
		{
			if (transaction != null)
			{
				transaction.rollback();
			}
			log.error(String.format(Constants.ROLLBACK_LIMIT_ERROR_MESSAGE, documentInfo.getExternalId()), e);
			throw new BusinessException(e);
		}
		finally
		{
			if (session != null)
			{
				session.close();
			}
		}
	}

	private LimitDocumentInfo createLimitDocumentInfo() throws BusinessLogicException, BusinessException
	{
		return new LimitDocumentInfo(
				operationUID,
				operationDate,
				person.getId(),
				LimitHelper.getOperationAmount(document),
				OperType.commit,
				LimitHelper.getChannelType(document));
	}

	private List<ProcessDocumentStrategy> getInitializedLimitsStrategies(List<Class<? extends ProcessDocumentStrategy>> strategies, BusinessDocument document) throws BusinessException
	{
		List<ProcessDocumentStrategy> result = new ArrayList<ProcessDocumentStrategy>();
		if(CollectionUtils.isEmpty(strategies))
			return result;

		for(Class<? extends ProcessDocumentStrategy> strategyClass : strategies)
			result.add(createStrategy(strategyClass, document));

		return result;
	}

	private BusinessDocumentLimitException doProcess(LimitDocumentInfo documentInfo) throws BusinessLogicException, BusinessException
	{
		ClientAccumulateLimitsInfo amountInfo = buildLimitAmountInfoByPerson(person, LimitHelper.getChannelType(document));

		for(ProcessDocumentStrategy strategy : limitStrategies)
		{
			try
			{
				// провеляем лимиты
				strategy.process(documentInfo, amountInfo);
			}
			catch (RequireAdditionConfirmLimitException e)
			{
				log.error(e.getLogMessage(document.getId()), e);
				// наверно должна сама стратегия преждем чем выкидывать ислючение, проверить должна ли она его кидать
				if(!LimitHelper.needAdditionalConfirm(document, e))
					return null;

				// Если сработали лимиты, требующие доп. подтвреждения, то сохранять нужно только те, которые проверялись до них,
				// поэтому исключение не прокидываем, а просто возвращаем(чтобы кинуть его для вызывающего кода позднее)
				return e;
			}
		}

		return null;
	}

	/**
	 * Построить сущнссть с текущими накопленными суммами по лимитам для ТЕКУЩЕГО клиента
	 * @param login логин персоны
	 * @param channelType канал
	 * @return сформированная сущность
	 * @throws BusinessException
	 */
	public static ClientAccumulateLimitsInfo buildLimitAmountInfoByLogin(CommonLogin login, ChannelType channelType) throws BusinessException
	{
		List<LimitDocumentInfo> limitDocumentInfos = limitService.getLimitDocumentInfoByLogin(login, DateHelper.getPreviousDay(), channelType);

		return new ClientAccumulateLimitsInfo(limitDocumentInfos);
	}

	/**
	 * Построить сущнссть с текущими накопленными суммами по лимитам для ТЕКУЩЕГО клиента
	 * @param person персона
	 * @param channelType канал
	 * @return сформированная сущность
	 * @throws BusinessException
	 */
	public static ClientAccumulateLimitsInfo buildLimitAmountInfoByPerson(Person person, ChannelType channelType) throws BusinessException
	{
		List<LimitDocumentInfo> limitDocumentInfos = limitService.getLimitDocumentInfoByPerson(person, DateHelper.getPreviousDay(), channelType);

		return new ClientAccumulateLimitsInfo(limitDocumentInfos);
	}

	/**
	 * Обработать лимиты по документу.
	 * @param documentInfo объект учета лимитов
	 * @return null - проверка и учет прошли успешно, BusinessDocumentLimitException исключение в случае обработки без отката
	 * (т.е. когда стратегия выкинула исключение, но лимиты которые учлись до этой стратегии должны сохранится,
	 * для случае когда лимиты НЕ должны сохранятся метод кидает ислючение )
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	private BusinessDocumentLimitException processLimits(LimitDocumentInfo documentInfo) throws BusinessException, BusinessLogicException
	{
		Session session = null;
		Transaction transaction = null;

		try
		{
			//noinspection HibernateResourceOpenedButNotSafelyClosed
			session = HibernateExecutor.getSessionFactory().openSession();
			transaction = session.beginTransaction();

			// лочим для синхронного подсчета
			doLock(document, session);

			//проверка документа по вхождению в разрешенную сумму
			BusinessDocumentLimitException e = doProcess(documentInfo);

			if(isNeedSaveTransaction(documentInfo))
			{
				// сохраняем получившуюся сущность
				session.save(documentInfo);
			}

			// сохраняем в базе то, что получилось
			transaction.commit();

			return e;
		}
		catch (LockAcquisitionException e)
		{
			if (transaction != null)
			{
				transaction.rollback();
			}
			log.error(String.format(Constants.USER_LOCK_ERROR_MESSAGE, document.getId()), e);
			throw new BusinessException(e);
		}
		catch (BusinessDocumentLimitException e)
		{
			if (transaction != null)
			{
				transaction.rollback();
			}
			log.error(e.getLogMessage(document.getId()), e);
			throw e;
		}
		catch (BusinessLogicException e)
		{
			if (transaction != null)
			{
				transaction.rollback();
			}
			log.error(String.format(Constants.PROCESS_DOCUMENT_ERROR_MESSAGE, document.getId()), e);
			throw e;
		}
		catch (BusinessException e)
		{
			if (transaction != null)
			{
				transaction.rollback();
			}
			log.error(String.format(Constants.PROCESS_DOCUMENT_ERROR_MESSAGE, document.getId()), e);
			throw e;
		}
		catch (Exception e)
		{
			if (transaction != null)
			{
				transaction.rollback();
			}
			log.error(String.format(Constants.PROCESS_DOCUMENT_ERROR_MESSAGE, document.getId()), e);
			throw new BusinessException(e);
		}
		finally
		{
			if (session != null)
			{
				session.close();
			}
		}
	}

	private void notifyLimitProcessStorage(LimitDocumentInfo documentInfo, Person person)
	{
		try
		{
			jmsService.sendMessageToQueue(JMSLimitsBuilder.buildCommit(documentInfo, person), QUEUE_NAME, FACTORY_NAME, null, null);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}

	private void notifyLimitRevertStorage(LimitDocumentInfo documentInfo)
	{
		try
		{
			jmsService.sendMessageToQueue(JMSLimitsBuilder.buildRevert(documentInfo), QUEUE_NAME, FACTORY_NAME, null, null);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}

	private void doLock(BusinessDocument document, Session session) throws InterruptedException
	{
		Login ownerDBLogin;
		try
		{
			// Гости не имеют учёток в БД => лочить нечего
			BusinessDocumentOwner documentOwner = document.getOwner();
			if (documentOwner.isGuest())
				return;

			ownerDBLogin = documentOwner.getLogin();
		}
		catch (BusinessException e)
		{
			throw new RuntimeException(e);
		}

		boolean lock = false;
		for (int i = 0; i < 3; i++)
		{
			if (isLock(session, ownerDBLogin))
			{
				lock = true;
				break;
			}
			//небольшая задержка (ждем освобождения логина)
			Thread.sleep(DELAY_TIME);
		}

		if (!lock)
		{
			//блокируем использование логина
			session.lock(ownerDBLogin, LockMode.UPGRADE_NOWAIT);
		}
	}

	/**
	 * Проверка блокировки логина клиента текущим потоком
	 * @param session hibernate сессия
	 * @param owner логин клиента
	 * @return true - блокировка наложена текущим потоком
	 */
	private boolean isLock(Session session, Login owner)
	{
		try
		{
			session.lock(owner, LockMode.UPGRADE_NOWAIT);
			return true;
		}
		catch (LockAcquisitionException ignore)
		{
			//каким-либо потоком на логин уже установлена блокировка
			return false;
		}
	}

	private ProcessDocumentStrategy createStrategy(Class<? extends ProcessDocumentStrategy> strategyClass, BusinessDocument document) throws BusinessException
	{
		try
		{
			Constructor constructor = strategyClass.getDeclaredConstructor(BusinessDocument.class);
			return (ProcessDocumentStrategy) constructor.newInstance(document);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			throw  new BusinessException(e);
		}
	}

	/**
	 * @param documentInfo - информация по лимитам
	 * @return true - необходимо сохранить в базе
	 */
	private boolean isNeedSaveTransaction(LimitDocumentInfo documentInfo)
	{
		return CollectionUtils.isNotEmpty(documentInfo.getLimitInfos());
	}

	/**
	 * @return true - необходимо оповестить приложение учета лимитов
	 */
	private boolean isNotifyLimitStorage()
	{
		return ConfigFactory.getConfig(LimitsConfig.class).isUseReplicateLimits();
	}
}
