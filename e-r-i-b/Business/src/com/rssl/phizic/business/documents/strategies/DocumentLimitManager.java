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
 * �������� ��� ��������� ������� �� ���������
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
	private static final long DELAY_TIME = 300L;                //��������� ����� ����� ��������� ���������

	private String operationUID;
	private Calendar operationDate;
	private BusinessDocument document;
	private Person person;
	private List<ProcessDocumentStrategy> limitStrategies;

	/**
	 * �������� ���������  �� �������
	 * @param document ��������
	 * @param operationUID ��� ��������(������ ��� ������������� ����������)
	 * @param operationDate ���� ��������(������ ��� ������������� ����������)
	 * @param person ������� ��� ����� �������(��� ������ ����� null)
	 * @param strategies ���������, ������� ������ �����������
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
	 * �������� ��������� ��� ��������� �� �������
	 * @param document ��������
	 * @param operationUID ��� ��������(������ ��� ������������� ����������)
	 * @param operationDate ���� ��������(������ ��� ������������� ����������)
	 * @param person ������� ��� ����� �������(��� ������ ����� null)
	 * @param strategies ���������, ������� ������ �����������
	 * @return �������� ���������
	 * @throws BusinessException
	 */
	public static DocumentLimitManager createProcessLimitManager(BusinessDocument document, String operationUID, Calendar operationDate, Person person, List<Class<? extends ProcessDocumentStrategy>> strategies) throws BusinessLogicException, BusinessException
	{
		return new DocumentLimitManager(document, operationUID, operationDate, person, strategies);
	}

	/**
	 * �������� ��������� ��� �������� �� �������
	 * @param document ��������
	 * @param person �������, ������������ ������� ��������� ������
	 * @param strategies ���������, ������� ������ �����������
	 * @return �������� ���������
	 * @throws BusinessException
	 */
	public static DocumentLimitManager createCheckLimitManager(BusinessDocument document, Person person, List<Class<? extends ProcessDocumentStrategy>> strategies) throws BusinessLogicException, BusinessException
	{
		return new DocumentLimitManager(document, null, null, person, strategies);
	}

	/**
	 * �������� ��������� ��� ������ �������
	 * @param operationUID ��� ��������(������ ��� ������������� ����������)
	 * @param operationDate ���� ��������(������ ��� ������������� ����������)
	 * @return �������� ���������
	 * @throws BusinessException
	 */
	public static DocumentLimitManager createRevertLimitManager(String operationUID, Calendar operationDate) throws BusinessLogicException, BusinessException
	{
		return new DocumentLimitManager(null, operationUID, operationDate, null, null);
	}


	/**
	 * ���������� ������ ����� �������� � ����������� ��� ����������� ��������
	 * @param successLimitDocumentAction ������� � ������ ��������� ����������� �� �������
	 * @throws BusinessLogicException ��������� � ������ ���� �� ������ ���������� �� �������
	 * @throws BusinessException
	 */
	public void processLimits(DocumentAction successLimitDocumentAction) throws BusinessLogicException, BusinessException
	{
		if(operationUID == null && operationDate == null)
			throw new IllegalStateException("�� ������ ����������������� ������ ��� �������� ���������� �� �������.");

		// ������� �������� ����������
		LimitDocumentInfo documentInfo = createLimitDocumentInfo();

		// ���� ��� ��������� ������� �� ����� � ��� �������, ������������ ��������
		BusinessLogicException ex = processLimits(documentInfo);

		if(ex == null && successLimitDocumentAction != null)
		{
			try
			{
				// �� �����, � ��������� ���������� �������� �� ������,
				// ������ ��� ����, ������������ ��������
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
				//������� ��������, � ��� �� �����.
				revertLimits(documentInfo);
				throw e;
			}
		}

		// ��������� ��������� ������� ���� �����
		if(isNotifyLimitStorage() && isNeedSaveTransaction(documentInfo))
			notifyLimitProcessStorage(documentInfo, person);

		// ��������� ���������� ���, ��� �� ��� ������
		if(ex != null)
			throw ex;
	}

	/**
	 * ��������� �������� �� ��������� � ������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void checkAndThrowLimits() throws BusinessException, BusinessLogicException
	{
		ClientAccumulateLimitsInfo limitsInfo = buildLimitAmountInfoByPerson(person, LimitHelper.getChannelType(document));
		// ��������� ������
		for(ProcessDocumentStrategy strategy : limitStrategies)
		{
			if(!(strategy instanceof CheckDocumentStrategy))
				throw new BusinessException("��������� ��������� ������ �� ��������� " + strategy.getClass().getName() + ", �.�. ��� �� ���������� ��������� " + CheckDocumentStrategy.class.getName());

			((CheckDocumentStrategy) strategy).checkAndThrow(limitsInfo);
		}
	}

	/**
	 * �������� ������ �� ���������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void revertLimits() throws BusinessLogicException, BusinessException
	{
		if(operationUID == null && operationDate == null)
			throw new IllegalStateException("�� ������ ����������������� ������ ��� ������ ���������� �� �������.");

		// ���� ��� ��������� ������� �� ����� � ��� �������, ������������ ��������
		LimitDocumentInfo documentInfo = revertLimits(operationUID, operationDate);

		// ��������� ��������� �������
		if(documentInfo != null && isNotifyLimitStorage())
			notifyLimitRevertStorage(documentInfo);
	}

	private LimitDocumentInfo revertLimits(String operationUID, Calendar operationDate) throws BusinessException, BusinessLogicException
	{
		LimitDocumentInfo original = limitService.findLimitDocumentInfo(operationUID, operationDate);
		if(original == null)
		{
			log.error("�� ������� ������ ��� ������, ����������� � ��������� � operationUID = " + operationUID + " � ����� " + operationDate);
			return null;
		}

		revertLimits(original);
		return original;
	}

	private void revertLimits(LimitDocumentInfo documentInfo) throws BusinessLogicException, BusinessException
	{
		// ���� ��� ��������� ������� �� ����� � ��� �������, ������������ ��������
		Session session = null;
		Transaction transaction = null;

		try
		{
			//noinspection HibernateResourceOpenedButNotSafelyClosed
			session = HibernateExecutor.getSessionFactory().openSession();
			transaction = session.beginTransaction();

			// ��������� �� ���� � ���������� ������ ����
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
				// ��������� ������
				strategy.process(documentInfo, amountInfo);
			}
			catch (RequireAdditionConfirmLimitException e)
			{
				log.error(e.getLogMessage(document.getId()), e);
				// ������� ������ ���� ��������� ������� ��� ���������� ���������, ��������� ������ �� ��� ��� ������
				if(!LimitHelper.needAdditionalConfirm(document, e))
					return null;

				// ���� ��������� ������, ��������� ���. �������������, �� ��������� ����� ������ ��, ������� ����������� �� ���,
				// ������� ���������� �� �����������, � ������ ����������(����� ������ ��� ��� ����������� ���� �������)
				return e;
			}
		}

		return null;
	}

	/**
	 * ��������� �������� � �������� ������������ ������� �� ������� ��� �������� �������
	 * @param login ����� �������
	 * @param channelType �����
	 * @return �������������� ��������
	 * @throws BusinessException
	 */
	public static ClientAccumulateLimitsInfo buildLimitAmountInfoByLogin(CommonLogin login, ChannelType channelType) throws BusinessException
	{
		List<LimitDocumentInfo> limitDocumentInfos = limitService.getLimitDocumentInfoByLogin(login, DateHelper.getPreviousDay(), channelType);

		return new ClientAccumulateLimitsInfo(limitDocumentInfos);
	}

	/**
	 * ��������� �������� � �������� ������������ ������� �� ������� ��� �������� �������
	 * @param person �������
	 * @param channelType �����
	 * @return �������������� ��������
	 * @throws BusinessException
	 */
	public static ClientAccumulateLimitsInfo buildLimitAmountInfoByPerson(Person person, ChannelType channelType) throws BusinessException
	{
		List<LimitDocumentInfo> limitDocumentInfos = limitService.getLimitDocumentInfoByPerson(person, DateHelper.getPreviousDay(), channelType);

		return new ClientAccumulateLimitsInfo(limitDocumentInfos);
	}

	/**
	 * ���������� ������ �� ���������.
	 * @param documentInfo ������ ����� �������
	 * @return null - �������� � ���� ������ �������, BusinessDocumentLimitException ���������� � ������ ��������� ��� ������
	 * (�.�. ����� ��������� �������� ����������, �� ������ ������� ������ �� ���� ��������� ������ ����������,
	 * ��� ������ ����� ������ �� ������ ���������� ����� ������ ��������� )
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

			// ����� ��� ����������� ��������
			doLock(document, session);

			//�������� ��������� �� ��������� � ����������� �����
			BusinessDocumentLimitException e = doProcess(documentInfo);

			if(isNeedSaveTransaction(documentInfo))
			{
				// ��������� ������������ ��������
				session.save(documentInfo);
			}

			// ��������� � ���� ��, ��� ����������
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
			// ����� �� ����� ������ � �� => ������ ������
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
			//��������� �������� (���� ������������ ������)
			Thread.sleep(DELAY_TIME);
		}

		if (!lock)
		{
			//��������� ������������� ������
			session.lock(ownerDBLogin, LockMode.UPGRADE_NOWAIT);
		}
	}

	/**
	 * �������� ���������� ������ ������� ������� �������
	 * @param session hibernate ������
	 * @param owner ����� �������
	 * @return true - ���������� �������� ������� �������
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
			//�����-���� ������� �� ����� ��� ����������� ����������
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
	 * @param documentInfo - ���������� �� �������
	 * @return true - ���������� ��������� � ����
	 */
	private boolean isNeedSaveTransaction(LimitDocumentInfo documentInfo)
	{
		return CollectionUtils.isNotEmpty(documentInfo.getLimitInfos());
	}

	/**
	 * @return true - ���������� ���������� ���������� ����� �������
	 */
	private boolean isNotifyLimitStorage()
	{
		return ConfigFactory.getConfig(LimitsConfig.class).isUseReplicateLimits();
	}
}
