package com.rssl.phizic.business.basket.invoice;

import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.StaticPersonData;
import com.rssl.phizic.business.basket.invoiceSubscription.ErrorInfo;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscriptionService;
import com.rssl.phizic.business.documents.BusinessDocumentBase;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.CreateInvoiceSubscriptionPayment;
import com.rssl.phizic.business.documents.payments.source.*;
import com.rssl.phizic.business.payments.forms.meta.handlers.basket.subscription.AutoCreateInvoiceSubscriptionChecker;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.resources.external.AutoSubscriptionLink;
import com.rssl.phizic.business.services.ServiceService;
import com.rssl.phizic.business.statemachine.PaymentStateMachineService;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.basket.InvoiceState;
import com.rssl.phizic.common.types.basket.InvoiceStatus;
import com.rssl.phizic.common.types.basket.InvoiceSubscriptionState;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.autopayments.AutoSubscriptionService;
import com.rssl.phizic.gate.basket.GateInvoice;
import com.rssl.phizic.gate.basket.InvoiceService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.longoffer.ExecutionEventType;
import com.rssl.phizic.gate.longoffer.SumType;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoSubscription;
import com.rssl.phizic.gate.payments.basket.CloseInvoiceSubscription;
import com.rssl.phizic.gate.payments.longoffer.AutoSubscriptionDetailInfo;
import com.rssl.phizic.gate.payments.longoffer.DelayCardPaymentSystemPaymentLongOffer;
import com.rssl.phizic.gate.payments.longoffer.RecoveryCardPaymentSystemPaymentLongOffer;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.mozilla.classfile.ClassFileWriter;
import org.quartz.JobExecutionException;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author vagin
 * @ created 28.04.14
 * @ $Author$
 * @ $Revision$
 * ������ ���������� ��������� ������� ������ � ���������.
 */
public class BusinessInvoiceService extends InvoiceServiceBase implements InvoiceService
{
	private static final PersonService personService = new PersonService();
	private static final long CORRECT_STATUS_CODE = 0L;
	private static final long TIME_OUT_STATUS_CODE = -105L;

	private static final State SAVED_STATE = new State("SAVED");
	private static final State DISPATCH_STATE = new State("DISPATCHED");
	private static final State UNKNOW_STATE = new State("UNKNOW");

	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	private final static PaymentStateMachineService stateMachineService = new PaymentStateMachineService();
	private final static InvoiceSubscriptionService invoiceSubscriptionService = new InvoiceSubscriptionService();

	private final GateFactory factory;

	public BusinessInvoiceService(GateFactory factory)
	{
		this.factory = factory;
	}

	public GateFactory getFactory()
	{
		return factory;
	}

	public void addInvoice(final GateInvoice invoice, final String operUID) throws GateException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					UpdateSubscriptionInfo subscription = getSubscriptionByOperUID(session, operUID);
					doAdd(invoice, subscription, session);
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public void updateInvoice(final GateInvoice invoice, final String operUID) throws GateException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					UpdateSubscriptionInfo subscription = getSubscriptionByOperUID(session, operUID);
					//������� �������� � ������ INACTIVE �� �������������� ��������(FK).
					doInactive(subscription.getId(), session);
					//���������� �������� � ��������.
					subscription.setDelayDate(null);
					doAdd(invoice, subscription, session);
					subscription.updateData(session, invoice.getAutoPaySubscriptionId());
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public void deleteInvoice(final String operUID, final String autoSubscriptionId) throws GateException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					UpdateSubscriptionInfo subscription = getSubscriptionByOperUID(session, operUID);
					//��������� ������� � INACTIVE �� �������������� ��������(FK).
					doInactive(subscription.getId(), session);
					//���������� �������� � ��������.
					subscription.setDelayDate(null);
					subscription.updateData(session, autoSubscriptionId);
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public void updatePaymentState(final String externalPaymentId, final InvoiceState state, final String nonExecReasonCode, final String nonExecReasonDesc, final Calendar execPaymentDate,final String operUID) throws GateException, GateLogicException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws GateException, GateLogicException, BusinessException
				{
					ObjectEvent objectEvent = null;
					switch (state)
					{
						case CANCELED:
							objectEvent = new ObjectEvent(DocumentEvent.REFUSE, ObjectEvent.SYSTEM_EVENT_TYPE, nonExecReasonDesc);
							break;
						case DONE:
							objectEvent = new ObjectEvent(DocumentEvent.EXECUTE, ObjectEvent.SYSTEM_EVENT_TYPE);
							break;
						default:
							throw new GateException("�������� �������� InvoicePaymentStatus: ��������� CANCELED � DONE");
					}
					Query query = session.getNamedQuery("com.rssl.phizic.business.basket.invoice.BusinessInvoiceService.findLastByPaymentId");
					query.setMaxResults(1);
					query.setParameter("autoPayId", externalPaymentId);
					Object foundedResult = query.uniqueResult();
					if (foundedResult == null)
					{
						if (state != InvoiceState.CANCELED)
							throw new GateLogicException("�� ������ ������ � PaymentId=" + externalPaymentId);
						//��������� �������� ������ ��� ��������
						PaymentErrorProcessor.process(nonExecReasonCode, nonExecReasonDesc, execPaymentDate, getSubscriptionByOperUID(session, operUID).getId());
						return null;
					}
					Object[] lastInvoice = (Object[]) foundedResult;
					Long paidInvoicePaymentId = (Long) lastInvoice[3];

					BusinessDocumentService businessDocumentService = new BusinessDocumentService();
					BusinessDocument payment = businessDocumentService.findById(paidInvoicePaymentId);
					if (payment == null)
						throw new GateLogicException("�� ������ �������� � externalId=" + paidInvoicePaymentId);
					//��������� ������.
					processPayment(payment, objectEvent);
					return null;
				}
			});
		}
		catch (GateLogicException e)
		{
			throw e;
		}
		catch (GateException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public void updatePaymentStatus(final String externalId, final Long statusCode, final String nonExecReasonDesc) throws GateException, GateLogicException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws GateException, GateLogicException
				{
					ObjectEvent objectEvent = null;
					//���� ������ ����������, ������ �� ������. ������� ������ �� ���������� �� �� "AutoPay".
					if (statusCode.equals(CORRECT_STATUS_CODE))
						return null;
					else if(statusCode.equals(TIME_OUT_STATUS_CODE))
					{
						objectEvent = new ObjectEvent(DocumentEvent.DOUNKNOW, ObjectEvent.SYSTEM_EVENT_TYPE, nonExecReasonDesc);
					}
					//��� ���� ��������� ����� ������ - ������ �����.
					else
						objectEvent = new ObjectEvent(DocumentEvent.REFUSE, ObjectEvent.SYSTEM_EVENT_TYPE, nonExecReasonDesc);

					try
					{
						BusinessDocumentService businessDocumentService = new BusinessDocumentService();
						GateExecutableDocument payment = businessDocumentService.findByExternalId(externalId);
						if (payment == null)
							throw new GateLogicException("�� ������ �������� � externalId=" + externalId);
						processPayment(payment, objectEvent);
					}
					catch (BusinessException e)
					{
						throw new GateException(e);
					}
					return null;
				}
			});
		}
		catch (GateException e)
		{
			throw e;
		}
		catch (GateLogicException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * �������������� ��������
	 * @param autoSubscriptionId - ������� �������������
	 * @param UID - ������������� ������.
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public void activateInvoiceSubscription(final String autoSubscriptionId, final String UID) throws GateException, GateLogicException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription.updateAutoPayId");
					query.setParameter("subscriptionId", autoSubscriptionId);
					query.setParameter("requestId", UID);
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

	public void createInvoiceSubscription(final DelayCardPaymentSystemPaymentLongOffer claim) throws GateException, GateLogicException
	{
		try
		{
			if(!AutoCreateInvoiceSubscriptionChecker.isAccessCreateInvoiceSubscription(claim))
				return;

			// �������������� �������� �������
			PersonContext.getPersonDataProvider().setPersonData(
					new StaticPersonData(personService.findByLogin(claim.getInternalOwnerId())));

			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					NewInvoiceSubscriptionByAutoPaymentSource source = new NewInvoiceSubscriptionByAutoPaymentSource(claim);
					CreateInvoiceSubscriptionPayment document = (CreateInvoiceSubscriptionPayment) source.getDocument();

					StateMachineExecutor executor = new StateMachineExecutor(stateMachineService.getStateMachineByFormName(document.getFormName()));
					executor.initialize(document);

					// TODO �������������� � ������������ �������(�� ��������� �� �����)
					executor.fireEvent(new ObjectEvent(DocumentEvent.SAVE, ObjectEvent.SYSTEM_EVENT_TYPE));
					if(!SAVED_STATE.equals(document.getState()))
					{
						invoiceSubscriptionService.saveInvoiceSubEntity(document, InvoiceSubscriptionState.DRAFT);
						return null;
					}

					// �������� � �������
					executor.fireEvent(new ObjectEvent(DocumentEvent.CONFIRM, ObjectEvent.SYSTEM_EVENT_TYPE));
					if(!DISPATCH_STATE.equals(document.getState()) && !UNKNOW_STATE.equals(document.getState()))
					{
						invoiceSubscriptionService.saveInvoiceSubEntity(document, InvoiceSubscriptionState.DRAFT);
						return null;
					}

					session.save(document);
					invoiceSubscriptionService.saveInvoiceSubEntity(document, InvoiceSubscriptionState.INACTIVE);
					return null;
				}
			});
		}
		catch (Exception e)
		{
			log.error(e);
		}
		finally
		{
			PersonContext.getPersonDataProvider().setPersonData(null);
		}

	}

	public void recoverAutoSubscription(final CloseInvoiceSubscription claim) throws GateException, GateLogicException
	{
		AutoSubscriptionService subscriptionService = GateSingleton.getFactory().service(AutoSubscriptionService.class);
		if(!claim.isRecoverAutoSubscription() || StringHelper.isEmpty(claim.getLongOfferExternalId()))
			return;

		final Long internalOwnerId = claim.getInternalOwnerId();

		try
		{
			GroupResult<AutoSubscription, AutoSubscriptionDetailInfo> res = subscriptionService.getAutoSubscription(claim.getLongOfferExternalId());
			// ����������� ������ ���� ��������
			final AutoSubscription subscription = res.getKeys().get(0);

			// ����� ������ ���� �������� � ���������(��������� ������ �������)
			PersonContext.getPersonDataProvider().setPersonData(new StaticPersonData(personService.findByLogin(claim.getInternalOwnerId()))
			{
				public List<AutoSubscriptionLink> getAutoSubscriptionLinks() throws BusinessException, BusinessLogicException
				{
					return Collections.singletonList(new AutoSubscriptionLink(internalOwnerId, subscription));
				}
			});

			// ������� �������� �� �������������� ������������
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					// ������� � �������������� ��������
					FieldValuesSource valueSource = new MapValuesSource(Collections.singletonMap("autoSubNumber", subscription.getNumber()));
					DocumentSource source = new SystemNewDocumentSource(
							FormConstants.RECOVERY_AUTOSUBSCRIPTION_PAYMENT_FORM, valueSource, CreationSourceType.ordinary, null);
					BusinessDocument document = source.getDocument();

					// �������������� ������ ���������
					StateMachineExecutor machineExecutor = new StateMachineExecutor(
							stateMachineService.getStateMachineByFormName(document.getFormName()), null, new StateMachineEvent());
					machineExecutor.initialize(document);

					// �������� �� ������� �������
					machineExecutor.fireEvent(new ObjectEvent(DocumentEvent.SAVE, ObjectEvent.SYSTEM_EVENT_TYPE));
					machineExecutor.fireEvent(new ObjectEvent(DocumentEvent.CONFIRM, ObjectEvent.SYSTEM_EVENT_TYPE));

					session.save(document);

					return null;
				}
			});
		}
		catch (Exception e)
		{
			log.error(e);
		}
		finally
		{
			// ������� ��������
			PersonContext.getPersonDataProvider().setPersonData(null);
		}
	}

	/**
	 * ���������� ������ �� ������ � ��������
	 * @param invoiceSubId ������������� ��������
	 * @param errorDesc �������� ������
	 * @throws BusinessException
	 */
	public static void updateInvoiceSubErrorDesc(final Long invoiceSubId, final ErrorInfo errorDesc) throws BusinessException
	{
		if(invoiceSubId == null)
			throw new IllegalArgumentException("invoiceSubId �� ����� ���� null.");

		if(errorDesc == null)
			throw new IllegalArgumentException("errorDesc �� ����� ���� null.");

		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription.updateErrorInfo");
					query.setParameter("errorDesc", ErrorInfo.buildErrorDesc(errorDesc));
					query.setParameter("id", invoiceSubId);
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
	 * �������� ������ ��� �� �����. ���� ������ ��� ���������.
	 * @param invoiceId - id �������
	 */
	public static void markViewed(final Long invoiceId) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.basket.invoice.BusinessInvoiceService.markViewed");
					query.setParameter("id", invoiceId);
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

	private void doAdd(GateInvoice invoice, UpdateSubscriptionInfo subscription, Session session) throws BusinessException
	{
		Invoice newInvoice = asBusinessInvoice(invoice, subscription.getId(), subscription.getLoginId());
		//����� ������� � ��� ���������.
		Object[] lastInvoice = getLastInvoiceData(invoice.getAutoPayId(), session);

		// ���� �� �������, �� ������ ���������
		if (lastInvoice == null)
		{
			session.save(newInvoice);
			//��������� ��������.
			subscription.updateData(session, invoice.getAutoPaySubscriptionId());
			return;
		}

		InvoiceStatus lastInvoiceState = InvoiceStatus.valueOf((String) lastInvoice[0]);
		Calendar lastInvoiceDelayedDate = (Calendar) lastInvoice[2];
		Long lastInvoiceId = (Long) lastInvoice[1];

		//���� ����� ���������� - "��������"(��������� ����� ���������, ������ - inactive)
		if (lastInvoiceState == InvoiceStatus.DELAYED)
		{
			newInvoice.setState(InvoiceStatus.DELAYED);
			newInvoice.setDelayedPayDate(lastInvoiceDelayedDate);
			newInvoice.setIsNew(false);
			inactivateInvoice(session, lastInvoiceId);
		}
		//���� ����� ���������� � ��������� - ��������� ����� ����������.
		else if (lastInvoiceState == InvoiceStatus.INACTIVE && lastInvoiceDelayedDate != null)
		{
			newInvoice.setState(InvoiceStatus.DELAYED);
			newInvoice.setDelayedPayDate(lastInvoiceDelayedDate);
			newInvoice.setIsNew(false);
			//��������� ���� ���������� ����������� � ��������.
			subscription.setDelayDate(getEarlyDate(subscription.getDelayDate(), lastInvoiceDelayedDate));
			subscription.updateData(session, invoice.getAutoPaySubscriptionId());
		}
		//NEW - ����������-�������, ����� ���������.
		else if (lastInvoiceState == InvoiceStatus.NEW)
		{
			inactivateInvoice(session, lastInvoiceId);
			newInvoice.setIsNew((Boolean)lastInvoice[4]);
		}
		//������ PAID - ���������� �����
		else if (lastInvoiceState == InvoiceStatus.PAID)
		{
			log.info("����������� ���� �� ������ � id="+invoice.getAutoPayId()+". ��� ���������� � ���������. ���������� ����� �����������.");
			return;
		}
		else
		{
			newInvoice.setIsNew((Boolean)lastInvoice[4]);
		}

		session.save(newInvoice);
	}

	private Object[] getLastInvoiceData(String autoPayId, Session session)
	{
		Query query = session.getNamedQuery("com.rssl.phizic.business.basket.invoice.BusinessInvoiceService.findLastByPaymentId");
		query.setMaxResults(1);
		query.setParameter("autoPayId", autoPayId);

		return (Object[]) query.uniqueResult();
	}

	private Calendar getEarlyDate(Calendar date1, Calendar date2)
	{
		if (date1 == null)
			return date2;
		if (date2 == null)
			return date1;
		return date1.after(date2) ? date2 : date1;
	}

	/**
	 * ������� ������� � ���������� ������.
	 * @param session - hibernate Session
	 * @param id - ������������� �������.
	 */
	private void inactivateInvoice(Session session, Long id)
	{
		updateStateById(session, id, InvoiceStatus.INACTIVE);
	}

	private UpdateSubscriptionInfo getSubscriptionByOperUID(Session session, String operUID) throws BusinessException
	{
		//����� �������� �� ����������� �������������� ������ �� ��������. ����� InvoiceSubscription.RequestID = operUID.
		Query query = session.getNamedQuery("com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription.findInfoByOperUID");
		query.setParameter("requestId", operUID);
		Object subscriptionInfo = query.uniqueResult();

		if (subscriptionInfo == null)
			throw new BusinessException("�� ������� �������� � requestId=" + operUID);
		Object[] info = (Object[]) subscriptionInfo;

		return new UpdateSubscriptionInfo((Long) info[0], (Calendar) info[1], (Long) info[2]);
	}

	private void processPayment(BusinessDocument payment, ObjectEvent event) throws GateException
	{
		BusinessDocumentService businessDocumentService = new BusinessDocumentService();
		try
		{
			StateMachineExecutor executor = new StateMachineExecutor(stateMachineService.getStateMachineByFormName(payment.getFormName()));
			executor.initialize(payment);
			executor.fireEvent(event);
			businessDocumentService.addOrUpdate(payment);
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new GateException(e);
		}
	}

	private Invoice asBusinessInvoice(GateInvoice invoice, Long subscriptionId, Long loginId)
	{
		if (invoice instanceof Invoice)
			return (Invoice) invoice;
		Invoice dest = new Invoice();
		BeanHelper.copyProperties(dest, invoice);
		dest.setInvoiceSubscriptionId(subscriptionId);
		dest.setState(InvoiceStatus.NEW);
		dest.setLoginId(loginId);
		dest.setCreatingDate(Calendar.getInstance());
		return dest;
	}

	/**
	 * ���������� ����������� ��� ��������� ��������.
	 */
	private class UpdateSubscriptionInfo
	{
		private Long id;                                       //id ��������
		private Calendar delayDate;                            //���� ���������� ����������� �������
		private Long loginId;                                  //������������� ������ �������

		UpdateSubscriptionInfo(Long id, Calendar delayDate, Long loginId)
		{
			this.delayDate = delayDate;
			this.loginId = loginId;
			this.id = id;
		}

		private void updateData(Session session, String autoPaySubscriptionId)
		{
			Query query = session.getNamedQuery("com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription.updateInfoParameters");
			query.setLong("id", id);
			query.setParameter("autoPayId", autoPaySubscriptionId);
			query.setCalendar("delayDate", delayDate);
			query.executeUpdate();
		}

		private Long getId()
		{
			return id;
		}

		private Calendar getDelayDate()
		{
			return delayDate;
		}

		private void setDelayDate(Calendar delayDate)
		{
			this.delayDate = delayDate;
		}

		private Long getLoginId()
		{
			return loginId;
		}
	}

	/**
	 * ��������� ������ �������� ������� ��������� �������.
	 */
	private static class PaymentErrorProcessor
	{
		static void process(String nonExecReasonCode, String nonExecReasonDesc, Calendar execPaymentDate, Long subscriptionId) throws BusinessException
		{
			int code = Integer.valueOf(nonExecReasonCode);
			ErrorInfo info;
			switch (code)
			{
				case 940:
					info = ErrorInfo.buildListErrorInfo("���� �� ������. ����������� �������������. ���� ������� ������������� " + DateHelper.formatDateToString2(execPaymentDate));
					break;
				case 903:
				case 981:
				case 991:
					info = ErrorInfo.buildFormErrorInfo(nonExecReasonDesc);
					break;
				//��� ��������� ����� ��������� ������ �� ��������.
				default:
					return;
			}
			updateInvoiceSubErrorDesc(subscriptionId, info);
		}
	}
}
