package com.rssl.phizic.business.payments.job;

import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.StaticPersonData;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscriptionService;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.CreateInvoiceSubscriptionPayment;
import com.rssl.phizic.business.documents.payments.source.SystemInvoiceSubscriptionSource;
import com.rssl.phizic.business.payments.BusinessOfflineDocumentException;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.statemachine.PaymentStateMachineService;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.basket.InvoiceSubscriptionState;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Session;
import org.quartz.*;

import java.util.List;

/**
 * Проводка автоматически сгенерированных подписок на инвойсы
 * @author niculichev
 * @ created 25.09.14
 * @ $Author$
 * @ $Revision$
 */
public class SendGeneratedInvoiceSubJob extends BaseJob implements StatefulJob, InterruptableJob
{
	protected static final Log log = PhizICLogFactory.getLog(LogModule.Scheduler);
	protected static final InvoiceSubscriptionService invoiceSubscriptionService = new InvoiceSubscriptionService();
	protected static final PaymentStateMachineService stateMachineService = new PaymentStateMachineService();
	protected static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();

	protected static final PersonService personService = new PersonService();

	protected static final State SAVED_STATE = new State("SAVED");
	protected static final State DISPATCH_STATE = new State("DISPATCHED");
	protected static final State UNKNOW_STATE = new State("UNKNOW");

	private volatile boolean isInterrupt = false;

	@Override
	protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		JobRefreshConfig config = ConfigFactory.getConfig(JobRefreshConfig.class);
		int maxRows = config.getMaxRowsFromFetch(getClass().getSimpleName());

		for (;!isInterrupt;)
		{
			List<InvoiceSubscription> subscriptions = findGeneratedSubscription(maxRows);
			if(CollectionUtils.isEmpty(subscriptions))
				return;

			for(final InvoiceSubscription subscription : subscriptions)
			{
				if(isInterrupt)
					return;

				initContext(subscription);

				try
				{
					HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
					{
						public Void run(Session session) throws Exception
						{
							process(subscription);
							return null;
						}
					});
				}
				catch (BusinessOfflineDocumentException e)
				{
					// сработал тех перерыв, прекращаем выполнение
					log.error(e.getMessage(), e);
					break;
				}
				catch (Exception e)
				{
					log.error(e.getMessage(), e);
					updateSubState(subscription, InvoiceSubscriptionState.DRAFT);
				}
				finally
				{
					clearContext();
				}
			}
		}
	}

	protected List<InvoiceSubscription> findGeneratedSubscription(int maxRows) throws JobExecutionException
	{
		try
		{
			return invoiceSubscriptionService.getGeneratedInvoiceSubscriptions(maxRows);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			throw new JobExecutionException(e);
		}
	}

	protected void process(InvoiceSubscription subscription) throws Exception
	{
		CreateInvoiceSubscriptionPayment claim = (CreateInvoiceSubscriptionPayment) new SystemInvoiceSubscriptionSource(subscription).getDocument();
		StateMachineExecutor executor = new StateMachineExecutor(stateMachineService.getStateMachineByFormName(claim.getFormName()));
		executor.initialize(claim);

		executor.fireEvent(new ObjectEvent(DocumentEvent.SAVE, ObjectEvent.SYSTEM_EVENT_TYPE));
		if(!SAVED_STATE.equals(claim.getState()))
		{
			updateSubState(subscription, InvoiceSubscriptionState.DRAFT);
			return;
		}

		executor.fireEvent(new ObjectEvent(DocumentEvent.CONFIRM, ObjectEvent.SYSTEM_EVENT_TYPE));
		if(!DISPATCH_STATE.equals(claim.getState()) && !UNKNOW_STATE.equals(claim.getState()))
		{
			updateSubState(subscription, InvoiceSubscriptionState.DRAFT);
			return;
		}

		updateSuccessSubscription(subscription, claim);
		businessDocumentService.addOrUpdate(claim);
	}

	protected void updateSubState(InvoiceSubscription subscription, InvoiceSubscriptionState state) throws JobExecutionException
	{
		try
		{
			invoiceSubscriptionService.updateState(state, subscription.getId());
		}
		catch (BusinessException e)
		{
			log.error(e.getMessage(), e);
			throw new JobExecutionException(e);
		}
	}

	private void updateSuccessSubscription(InvoiceSubscription subscription, CreateInvoiceSubscriptionPayment claim) throws JobExecutionException
	{
		try
		{
			invoiceSubscriptionService.updateInvoiceSubEntity(subscription, claim);
		}
		catch (BusinessException e)
		{
			log.error(e.getMessage(), e);
			throw new JobExecutionException(e);
		}
	}

	private void initContext(InvoiceSubscription subscription) throws JobExecutionException
	{
		try
		{
			PersonContext.getPersonDataProvider(Application.Scheduler).setPersonData(
					new StaticPersonData(personService.findByLogin(subscription.getLoginId())));
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			throw new JobExecutionException(e);
		}
	}

	private void clearContext()
	{
		PersonContext.getPersonDataProvider(Application.Scheduler).setPersonData(null);
	}

	public void interrupt() throws UnableToInterruptJobException
	{
		isInterrupt = true;
	}
}
