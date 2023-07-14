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
 * ������� ����� ����� ��������� �������� ����������
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
	 * @return ������ � ������� �������� ����
	 */
	protected abstract String getStateCode();

	/**
	 * @return ������� ��� �������� ��������� � ����� ���������.
	 */
	protected DocumentEvent getNextEvent()
	{
		return DocumentEvent.DISPATCH;
	}

	/**
	 * @return ���������� ���������� ������
	 */
	protected abstract AtomicInteger getJobCounter();

	/**
	 * @param document �������������� ��������
	 * @return ���������� ���������� ������
	 */
	@Deprecated // ����� SendDelayedPaymentsJob
	protected boolean beforeFireEventCheckDocument(BusinessDocument document) throws BusinessException
	{
		return true;
	}

	/**
	 * @param document �������������� ��������
	 * @return ���������� ���������� ������
	 */
	@Deprecated // ����� SendOfflineDelayedPaymentsJob
	protected boolean afterFireEventCheckDocument(BusinessDocument document) throws BusinessException
	{
		return true;
	}

	/**
	 * @return ������� ������� ����
	 */
	protected Log getLog()
	{
		return log;
	}

	/**
	 * @return �������� �������� �����
	 */
	protected String getJobName()
	{
		return jobName;
	}

	/**
	 * @return ������� �������� �������� �����
	 */
	protected String getJobSimpleName()
	{
		return jobSimpleName;
	}

	/**
	 * @return ������� �� ����
	 */
	public boolean isInterrupt()
	{
		return isInterrupt;
	}

	/**
	 * ��������� �������� � ������ ��������������
	 * @param document ��������
	 * @throws BusinessException
	 */
	protected void addToProcessed(BusinessDocument document) throws BusinessException
	{
		addToProcessed(document.getId(), StringUtils.EMPTY);
	}

	/**
	 * ��������� �������� � ������ ��������������
	 * @param docId ������������� ���������
	 * @param additionalJobData �������������� ����� ��� ���������
	 * @throws BusinessException
	 */
	protected void addToProcessed(Long docId, String additionalJobData) throws BusinessException
	{
		processedDocumentService.add(docId, jobName + additionalJobData);
	}

	/**
	 * ������� �������� �� ������ ��������������
	 * @param docId ������������� ���������
	 * @param additionalJobData �������������� ����� ��� ���������
	 * @throws BusinessException
	 */
	protected void deleteFromProcessed(Long docId, String additionalJobData) throws BusinessException
	{
		processedDocumentService.delete(docId, jobName + additionalJobData);
	}

	/**
	 * ��������� �������� ���������
	 *
	 * @param docId   ������������� ���������
	 * @param session ��������-������
	 * @param recordMark �������������� ���������� ��� ������� �������������� ����������
	 * @return ������ �� �������� ��������
	 */
	private BusinessDocument getDocument(Long docId, Session session, String recordMark) throws BusinessException
	{
		log.trace("��������� �������� " + docId + " ��� ��������.");
		BusinessDocument doc;
		try
		{
			doc = businessDocumentService.findById(docId, LockMode.UPGRADE_NOWAIT);
			session.evict(doc);
		}
		catch (LockAcquisitionException ignored)
		{
			log.trace("������ ���������� ��������� " + docId + ". ���������� ��������.");
			return null;
		}
		finally
		{
			// � ������� ���������� ��������� ��������, � ������ �������� ������� ������ ��������
			deleteFromProcessed(docId, recordMark);
		}

		if (doc == null)
		{
			log.warn("�� ������ ��������� ��������� �������� � �������������� " + docId + ".");
			return null;
		}

		if (!getStateCode().equals(doc.getState().getCode()))
		{
			log.trace("�������� " + docId + " ������ ������������. �� ������ � ���� ��� ���������:" + doc.getState().getCode() + ". ���������� ��������.");
			return null;
		}

		log.trace("�������� " + docId + " ������ ������������. ������������ ���.");
		return doc;
	}

	/**
	 * ��������� ��� ���������� ��������
	 * ��� ����������� ������-���� �������
	 *
	 * @param executor - ����������
	 * @param event    - �������
	 * @param document - ��������
	 */
	protected void process(StateMachineExecutor executor, ObjectEvent event, BusinessDocument document) throws Exception
	{
		try
		{
			executor.fireEvent(event);
			if (!afterFireEventCheckDocument(document))
				return;

			businessDocumentService.addOrUpdate(document);
			log.info("�������� " + document.getId() + " ��������� � ������ " + document.getState().getCode());
		}
		catch (InactiveExternalSystemException e)
		{
			//������� ������� � ������ ������ ���������, ��������� ������� ����������
			log.error(e.getMessage() + "�������������� ������ id = " + document.getId(), e);
		}
		catch (Exception e)
		{
			log.error("������ ��������� ��������� " + document.getId() + " ��� ���������� ������� " + event.getEvent(), e);
			throw e;
		}
	}

	/**
	 * ��������� ����������� ���������
	 *
	 * @param doc ��������
	 * @return ����������
	 */
	protected StateMachineExecutor getStateMachineExecutor(BusinessDocument doc) throws Exception
	{
		StateMachineExecutor executor = new StateMachineExecutor(stateMachineService.getStateMachineByFormName(doc.getFormName()));
		executor.initialize(doc);
		return executor;
	}

	/**
	 * �������� �������� �� �����-������
	 *
	 * @param document - ��������
	 */
	protected void dispatch(BusinessDocument document, DocumentEvent nextEvent, String description) throws Exception
	{
		StateMachineExecutor executor = getStateMachineExecutor(document);

		try
		{
			log.debug("�������� �������� " + document.getId() + " �� ������� �������.");
			process(executor, new ObjectEvent(nextEvent, JOB_EVENT_TYPE, description), document);
		}
		catch (BusinessTimeOutException e)
		{
			log.error("��� ��������� ������� id = " + document.getId() + " �������� �������� time-out. ��������� �������� � ������ 'UNKNOW'", e);
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
			//������ �� ������, �������� �������� � ��������� ���, ����� �������� ��������������� ������.
			addToProcessed(document);
		}
		catch (BusinessLogicException e)
		{
			Throwable throwable = e.getCause();
			if (throwable != null && throwable.getCause() instanceof GateWrapperTimeOutException)
			{
				log.error("������ ��������� ��������� " + document.getId() + ". " + GateWrapperTimeOutException.TIMEOUT_WRAPPER_DOCUMENT_STATE_DESCRIPTION, e);
				return;
			}

			log.error("������ ��������� ��������� " + document.getId() + ". ��������� � ������ '�������'.", e);
			process(executor, new ObjectEvent(DocumentEvent.REFUSE, JOB_EVENT_TYPE, e.getCause().getMessage()), document);
		}
		catch (Exception e)
		{
			log.error("������ ��������� ��������� " + document.getId() + ". ��������� � ������ '������'.", e);
			process(executor, new ObjectEvent(DocumentEvent.ERROR, JOB_EVENT_TYPE, "�������������"), document);
		}
	}

	/**
	 * ��������� ���������, ���������� � ������� UNKNOW
	 * @param document ��������
	 * @throws BusinessException
	 */
	protected void processUnknownState(BusinessDocument document) throws BusinessException
	{
		addToProcessed(document);
	}

	/**
	 * ���������� ��������
	 *
	 * @param docId ������������� ���������
	 * @throws Exception
	 */
	private void processDocument(final Long docId) throws Exception
	{
		try
		{
			// ��������� �������� ��� ����������, ���� � ������, ����� � ��� ��������� ������ ������ � ���� ��������
			addToProcessed(docId, ADDITIONAL_JOB_MARK);
		}
		catch (Exception ignored)
		{
			// �� ������ �������� - ���������� ��������
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
			log.error("������ ��������� ��������� " + docId, e);
		}
	}

	protected void processDocument(BusinessDocument document) throws Exception
	{
		dispatch(document, getNextEvent(), null);
	}

	/**
	 * ������ �������������� ���������� ��� ��������� ������.
	 *
	 * @param stateCode ��������� ���������.
	 * @param maxRows ���������� ������� ��� �������.
	 * @param jobName �������� �����.
	 * @param paymentSortOrder ������� ���������� ��������.
	 * @return ������ ��������������� ����������.
	 * @throws BusinessException
	 */
	protected List<Long> getDocumentForProcess(String stateCode, int maxRows, String jobName, String paymentSortOrder) throws BusinessException
	{
		return businessDocumentService.findByStateForJob(stateCode, maxRows, jobName, paymentSortOrder);
	}

	/**
	 * ������ ������ �����
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
			log.trace("�������� ������ ���������� ��� ���������. ������������ ���������� �������:" + maxRows + ". �����������:" + documentIDs.size());
			if (documentIDs.isEmpty())
				return;

			for (Long documentID : documentIDs)
			{
				if(isInterrupt)
					break;

				log.trace("������ ��������� ��������� " + documentID);
				processDocument(documentID);
				log.trace("����� ��������� ��������� " + documentID);
			}
		}
	}

	@Override protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		try
		{
			//����������� ������� ���������� ��������� �����
			int jobNumber = getJobCounter().incrementAndGet();
			try
			{
				JobRefreshConfig config = ConfigFactory.getConfig(JobRefreshConfig.class);
				int maxJobCount = config.getMaxJobsCount(this.getJobName());
				getLog().info("�������� ������ ��������� ���������� � ������� '�������� ���������'");
				if (jobNumber > maxJobCount)
				{
					getLog().trace("������������ ���������� ������������� �����:" + maxJobCount + ". ������� ������:" + jobNumber + ". ��������� ������.");
					return;
				}
				getLog().trace("������������ ���������� ������������� �����:" + maxJobCount + ". ������� ������:" + jobNumber + ".���������� ������.");

				doJob();

			}
			finally
			{
				//��������� ������� ���������� ��������� �����
				int jobCount = getJobCounter().decrementAndGet();
				log.info("��������� ������ ��������� ���������� � ������� '�������� ���������'");
				log.trace("������� ���������� ���������� ������ " + jobCount);
			}
		}
		catch (Exception e)
		{
			log.error("������ ��������� ���������� ��������", e);
		}
	}

	public void interrupt() throws UnableToInterruptJobException
	{
		isInterrupt = true;
	}
}
