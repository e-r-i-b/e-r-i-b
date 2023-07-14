package com.rssl.phizic.business.payments.job;

import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.forJob.ProcessedDocumentIdService;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.log.ContextFillHelper;
import com.rssl.phizic.business.payments.BusinessOfflineDocumentException;
import com.rssl.phizic.business.payments.BusinessTimeOutException;
import com.rssl.phizic.business.statemachine.PaymentStateMachineService;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.exceptions.GateWrapperTimeOutException;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.service.StartupServiceDictionary;
import org.apache.commons.lang.StringUtils;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.exception.LockAcquisitionException;
import org.quartz.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author akrenev
 * @ created 13.02.2013
 * @ $Author$
 * @ $Revision$
 *
 * Базовый класс джоба повторной отправки документов
 */

public abstract class ReworkingPaymentJob extends BaseJob implements Job, InterruptableJob
{
	protected static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_SCHEDULER);

	protected static final String UNKNOW_STATE = "UNKNOW";
	protected static final String JOB_EVENT_TYPE = ObjectEvent.SYSTEM_EVENT_TYPE;

	protected static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();
	protected static final ProcessedDocumentIdService processedDocumentService = new ProcessedDocumentIdService();
	private final PaymentStateMachineService stateMachineService = new PaymentStateMachineService();

	private static final String ADDITIONAL_JOB_MARK = ".unexpected.error";

	private final String jobName;
	private final String jobSimpleName;
	private volatile boolean isInterrupt = false;

	protected ReworkingPaymentJob() throws JobExecutionException
	{
		jobName = this.getClass().getName();
		jobSimpleName = this.getClass().getSimpleName();

		StartupServiceDictionary serviceStarter = new StartupServiceDictionary();
		serviceStarter.startMBeans();
	}

	/**
	 * @return статус с которым работает джоб
	 */
	protected abstract String getStateCode();

	/**
	 * @return событие для перевода документа в новое состояние.
	 */
	protected DocumentEvent getNextEvent()
	{
		return DocumentEvent.DISPATCH;
	}

	/**
	 * @return количество оставшихся джобов
	 */
	protected abstract AtomicInteger getJobCounter();

	/**
	 * @param document обрабатываемый документ
	 * @return количество оставшихся джобов
	 */
	@Deprecated // нужды SendDelayedPaymentsJob
	protected boolean beforeFireEventCheckDocument(BusinessDocument document) throws BusinessException
	{
		return true;
	}

	/**
	 * @param document обрабатываемый документ
	 * @return количество оставшихся джобов
	 */
	@Deprecated // нужды SendOfflineDelayedPaymentsJob
	protected boolean afterFireEventCheckDocument(BusinessDocument document) throws BusinessException
	{
		return true;
	}

	/**
	 * @return текущий инстанс лога
	 */
	protected Log getLog()
	{
		return log;
	}

	/**
	 * @return название текущего джоба
	 */
	protected String getJobName()
	{
		return jobName;
	}

	/**
	 * @return простое название текущего джоба
	 */
	protected String getJobSimpleName()
	{
		return jobSimpleName;
	}

	/**
	 * @return прерван ли джоб
	 */
	public boolean isInterrupt()
	{
		return isInterrupt;
	}

	/**
	 * добавляет документ в список обрабатываемых
	 * @param document документ
	 * @throws BusinessException
	 */
	protected void addToProcessed(BusinessDocument document) throws BusinessException
	{
		addToProcessed(document.getId(), StringUtils.EMPTY);
	}

	/**
	 * добавляет документ в список обрабатываемых
	 * @param docId идентификатор документа
	 * @param additionalJobData дополнительная метка для документа
	 * @throws BusinessException
	 */
	protected void addToProcessed(Long docId, String additionalJobData) throws BusinessException
	{
		processedDocumentService.add(docId, jobName + additionalJobData);
	}

	/**
	 * удаляет документ из список обрабатываемых
	 * @param docId идентификатор документа
	 * @param additionalJobData дополнительная метка для документа
	 * @throws BusinessException
	 */
	protected void deleteFromProcessed(Long docId, String additionalJobData) throws BusinessException
	{
		processedDocumentService.delete(docId, jobName + additionalJobData);
	}

	/**
	 * Получение текущего документа
	 *
	 * @param docId   идентификатор документа
	 * @param session хибернет-сессия
	 * @param recordMark дополнительтая информация для очереди обрабатываемых документов
	 * @return прошел ли документ проверку
	 */
	private BusinessDocument getDocument(Long docId, Session session, String recordMark) throws BusinessException
	{
		log.trace("Блокируем документ " + docId + " без ожидания.");
		BusinessDocument doc;
		try
		{
			doc = businessDocumentService.findById(docId, LockMode.UPGRADE_NOWAIT);
			session.evict(doc);
		}
		catch (LockAcquisitionException ignored)
		{
			log.trace("Ошибка блокировки документа " + docId + ". Пропускаем документ.");
			return null;
		}
		finally
		{
			// в очередь выполнения добавляем удаление, в случае удачного коммита запись удалится
			deleteFromProcessed(docId, recordMark);
		}

		if (doc == null)
		{
			log.warn("Не найден ожидающий обработки документ с идентфикатором " + docId + ".");
			return null;
		}

		if (!getStateCode().equals(doc.getState().getCode()))
		{
			log.trace("Документ " + docId + " удачно заблокирован. Но статус у него уже изменился:" + doc.getState().getCode() + ". Пропускаем документ.");
			return null;
		}

		log.trace("Документ " + docId + " удачно заблокирован. Обрабатываем его.");
		return doc;
	}

	/**
	 * Выполнить над документом действие
	 * при наступлении какого-либо события
	 *
	 * @param executor - обработчик
	 * @param event    - событие
	 * @param document - документ
	 */
	protected void process(StateMachineExecutor executor, ObjectEvent event, BusinessDocument document) throws Exception
	{
		try
		{
			executor.fireEvent(event);
			if (!afterFireEventCheckDocument(document))
				return;

			businessDocumentService.addOrUpdate(document);
			log.info("Документ " + document.getId() + " переведен в статус " + document.getState().getCode());
		}
		catch (InactiveExternalSystemException e)
		{
			//внешняя система в данный момент неактивна, обработку платежа пропускаем
			log.error(e.getMessage() + "Обрабатываемый платеж id = " + document.getId(), e);
		}
		catch (Exception e)
		{
			log.error("Ошибка обработки документа " + document.getId() + " при выполнении события " + event.getEvent(), e);
			throw e;
		}
	}

	/**
	 * Получение обработчика документа
	 *
	 * @param doc документ
	 * @return обработчик
	 */
	protected StateMachineExecutor getStateMachineExecutor(BusinessDocument doc) throws Exception
	{
		StateMachineExecutor executor = new StateMachineExecutor(stateMachineService.getStateMachineByFormName(doc.getFormName()));
		executor.initialize(doc);
		return executor;
	}

	/**
	 * прогнать документ по стейт-машине
	 *
	 * @param document - документ
	 */
	protected void dispatch(BusinessDocument document, DocumentEvent nextEvent, String description) throws Exception
	{
		StateMachineExecutor executor = getStateMachineExecutor(document);

		try
		{
			log.debug("Отсылаем документ " + document.getId() + " во внешнюю систему.");
			process(executor, new ObjectEvent(nextEvent, JOB_EVENT_TYPE, description), document);
		}
		catch (BusinessTimeOutException e)
		{
			log.error("При обработке платежа id = " + document.getId() + " возникла ситуация time-out. Переводим документ в статус 'UNKNOW'", e);
			Throwable throwable = e.getCause();
			if (getStateCode().equals(UNKNOW_STATE))
				processUnknownState(document);
			else if (throwable.getCause() instanceof GateWrapperTimeOutException)
				process(executor, new ObjectEvent(DocumentEvent.DOUNKNOW, JOB_EVENT_TYPE, GateWrapperTimeOutException.TIMEOUT_WRAPPER_DOCUMENT_STATE_DESCRIPTION), document);
			else
				process(executor, new ObjectEvent(DocumentEvent.DOUNKNOW, JOB_EVENT_TYPE), document);
		}
		catch (BusinessOfflineDocumentException ignore)
		{
			//ничего не делаем, проведем документ в следующий раз, когда запустят соответствующий сервис.
			addToProcessed(document);
		}
		catch (BusinessLogicException e)
		{
			Throwable throwable = e.getCause();
			if (throwable != null && throwable.getCause() instanceof GateWrapperTimeOutException)
			{
				log.error("Ошибка обработки документа " + document.getId() + ". " + GateWrapperTimeOutException.TIMEOUT_WRAPPER_DOCUMENT_STATE_DESCRIPTION, e);
				return;
			}

			log.error("Ошибка обработки документа " + document.getId() + ". Переводим в статус 'отказан'.", e);
			process(executor, new ObjectEvent(DocumentEvent.REFUSE, JOB_EVENT_TYPE, e.getCause().getMessage()), document);
		}
		catch (Exception e)
		{
			log.error("Ошибка обработки документа " + document.getId() + ". Переводим в статус 'ошибка'.", e);
			process(executor, new ObjectEvent(DocumentEvent.ERROR, JOB_EVENT_TYPE, "Приостановлен"), document);
		}
	}

	/**
	 * Обработка документа, пришедшего в статусе UNKNOW
	 * @param document документ
	 * @throws BusinessException
	 */
	protected void processUnknownState(BusinessDocument document) throws BusinessException
	{
		addToProcessed(document);
	}

	/**
	 * Обработать документ
	 *
	 * @param docId идентификатор документа
	 * @throws Exception
	 */
	private void processDocument(final Long docId) throws Exception
	{
		try
		{
			// добавляем документ как проблемный, чтоб в случае, когда с ним произошла ошибка запись в базе осталась
			addToProcessed(docId, ADDITIONAL_JOB_MARK);
		}
		catch (Exception ignored)
		{
			// не смогли добавить - пропускаем документ
			return;
		}

		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					BusinessDocument document = getDocument(docId, session, ADDITIONAL_JOB_MARK);
					if (document == null)
						return null;

					BusinessDocumentOwner documentOwner = document.getOwner();
					ContextFillHelper.fillContextByLogin(documentOwner.getLogin());

	                if (!beforeFireEventCheckDocument(document))
	                    return null;

					processDocument(document);
					return null;
				}
			});
		}
		catch (Exception e)
		{
			log.error("Ошибка обработки документа " + docId, e);
		}
	}

	protected void processDocument(BusinessDocument document) throws Exception
	{
		dispatch(document, getNextEvent(), null);
	}

	/**
	 * Список иднтификаторов документов для обработки джобом.
	 *
	 * @param stateCode состояние документа.
	 * @param maxRows количество записей для выборки.
	 * @param jobName название джоба.
	 * @param paymentSortOrder порядок сортировки платежей.
	 * @return список идентификаторов документов.
	 * @throws BusinessException
	 */
	protected List<Long> getDocumentForProcess(String stateCode, int maxRows, String jobName, String paymentSortOrder) throws BusinessException
	{
		return businessDocumentService.findByStateForJob(stateCode, maxRows, jobName, paymentSortOrder);
	}

	/**
	 * начало работы джоба
	 *
	 * @throws Exception
	 */
	protected void doJob() throws Exception
	{

		processedDocumentService.deleteAllForJob(jobName);

		for (;!isInterrupt;)
		{
			JobRefreshConfig config = ConfigFactory.getConfig(JobRefreshConfig.class);
			int maxRows = config.getMaxRowsFromFetch(this.getJobName());
			String paymentSortOrder = config.getPaymentSortOrder(this.getJobName());
			List<Long> documentIDs = getDocumentForProcess(getStateCode(), maxRows, jobName, paymentSortOrder);
			log.trace("Получили список документов для обработки. Максимальное количество записей:" + maxRows + ". Фактическое:" + documentIDs.size());
			if (documentIDs.isEmpty())
				return;

			for (Long documentID : documentIDs)
			{
				if(isInterrupt)
					break;

				log.trace("Начало обработки документа " + documentID);
				processDocument(documentID);
				log.trace("Конец обработки документа " + documentID);
			}
		}
	}

	@Override protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		try
		{
			//увеличиваем счетчик работающих инстансов джоба
			int jobNumber = getJobCounter().incrementAndGet();
			try
			{
				JobRefreshConfig config = ConfigFactory.getConfig(JobRefreshConfig.class);
				int maxJobCount = config.getMaxJobsCount(this.getJobName());
				getLog().info("Запущена задача обработки документов в статусе 'ожидание обработки'");
				if (jobNumber > maxJobCount)
				{
					getLog().trace("Максимальное количество одновременных задач:" + maxJobCount + ". Текущая задача:" + jobNumber + ". Завершаем работу.");
					return;
				}
				getLog().trace("Максимальное количество одновременных задач:" + maxJobCount + ". Текущая задача:" + jobNumber + ".Продолжаем работу.");

				doJob();

			}
			finally
			{
				//уменьшаем счетчик работающих инстансов джоба
				int jobCount = getJobCounter().decrementAndGet();
				log.info("Завершена задача обработки документов в статусе 'ожидание обработки'");
				log.trace("Текущее количество запущенных джобов " + jobCount);
			}
		}
		catch (Exception e)
		{
			log.error("Ошибка обработки отложенных платежей", e);
		}
	}

	public void interrupt() throws UnableToInterruptJobException
	{
		isInterrupt = true;
	}
}
