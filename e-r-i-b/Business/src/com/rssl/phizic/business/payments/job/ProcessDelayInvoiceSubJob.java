package com.rssl.phizic.business.payments.job;

import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.source.NewSystemInvoiceSubscriptionClaimSource;
import com.rssl.phizic.business.payments.BusinessTimeOutException;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.common.types.basket.InvoiceSubscriptionState;
import com.rssl.phizic.utils.StringHelper;
import org.quartz.JobExecutionException;

import java.util.List;

/**
 * ƒжоб проводки отложенных действий по автопоискам
 * @author niculichev
 * @ created 22.07.15
 * @ $Author$
 * @ $Revision$
 */
public class ProcessDelayInvoiceSubJob extends SendGeneratedInvoiceSubJob
{
	protected List<InvoiceSubscription> findGeneratedSubscription(int maxRows) throws JobExecutionException
	{
		try
		{
			return invoiceSubscriptionService.getDelayExecuteInvoiceSubscriptions(maxRows);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			throw new JobExecutionException(e);
		}
	}

	protected void process(InvoiceSubscription subscription) throws Exception
	{
		switch (subscription.getState())
		{
			case DELAY_CREATE:
			{
				super.process(subscription);
				break;
			}
			case DELAY_DELETE:
			{
				processDelete(subscription);
				break;
			}
			default:
				throw new BusinessException("Ќе подход€щий дл€ обработки статус " + subscription.getState());
		}
	}

	private void processDelete(InvoiceSubscription subscription) throws BusinessLogicException, BusinessException, JobExecutionException
	{
		// без внешнего идентификатора ничего сделать не можем
		if(StringHelper.isEmpty(subscription.getAutoPayId()))
			return;

		try
		{
			NewSystemInvoiceSubscriptionClaimSource source =
					new NewSystemInvoiceSubscriptionClaimSource(subscription, FormConstants.CLOSE_INVOICE_SUBSCRIPTION_CLAIM);

			BusinessDocument claim = source.getDocument();
			StateMachineExecutor executor = new StateMachineExecutor(stateMachineService.getStateMachineByFormName(claim.getFormName()));
			executor.initialize(claim);

			// пытаемс€ провести во внешней системе
			fireEvent(subscription, executor);

			businessDocumentService.addOrUpdate(claim);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			updateSubState(subscription, InvoiceSubscriptionState.ERROR);
		}
	}

	private void fireEvent(InvoiceSubscription subscription, StateMachineExecutor executor) throws Exception
	{
		try
		{
			executor.fireEvent(new ObjectEvent(DocumentEvent.SEND, ObjectEvent.SYSTEM_EVENT_TYPE));
		}
		catch (BusinessTimeOutException e)
		{
			log.error(e.getMessage(), e);

			executor.fireEvent(new ObjectEvent(DocumentEvent.DOUNKNOW, ObjectEvent.SYSTEM_EVENT_TYPE));
			updateSubState(subscription, InvoiceSubscriptionState.ERROR);
		}
	}
}
