package com.rssl.phizic.scheduler.jobs;

import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.business.statemachine.PaymentStateMachineService;
import com.rssl.phizic.business.statemachine.StateMachine;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.loanclaim.LoanClaimConfig;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import java.util.Calendar;
import java.util.List;

/**
 * @author Gulov
 * @ created 17.02.15
 * @ $Author$
 * @ $Revision$
 */

/**
 * Джоб по обработке протухших заявок на кредит
 */
// todo по согласованию с Креневым А. и Краевым А. джобов по обработке бизнес документов быть не должно.
// Данный джоб необходимо убрать и проверять статус заявки в момент ее отображения в истории операций.
public class LoanClaimOldProcessJob extends BaseJob implements StatefulJob
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_SCHEDULER);
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();

	private final PaymentStateMachineService stateMachineService = new PaymentStateMachineService();

	protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		log.debug("Запуск джоба перевода заявок «В работе ТМ» -> «Отказ»");
		try
		{
			LoanClaimConfig config = ConfigFactory.getConfig(LoanClaimConfig.class);
			List<ExtendedLoanClaim> claims = findClaims(config.getHistoryAvailableDays());
			if (CollectionUtils.isEmpty(claims))
				return;
			for (ExtendedLoanClaim claim : claims)
			{
				if (claim.getBkiConfirmDate() == null)
					continue;
				Calendar callTime = Calendar.getInstance();
				callTime.setTimeInMillis(claim.getBkiConfirmDate().getTimeInMillis());
				callTime.add(Calendar.DATE, config.getWaitTmResponseDays());
				if (callTime.before(Calendar.getInstance()))
					setRefuseStateClaim(claim);
			}
		}
		finally
		{
			log.debug("Оконачние работы джоба перевода заявок «В работе ТМ» -> «Отказ»");
		}
	}

	private void setRefuseStateClaim(ExtendedLoanClaim claim)
	{
		try
		{
			StateMachineExecutor executor = getStateMachineExecutor(claim);
			// если executor не инициализировался, пропускаем документ
			if(executor == null)
				return;

			//обрабатываем документ
			executor.fireEvent(new ObjectEvent(DocumentEvent.REFUSE, ObjectEvent.SYSTEM_EVENT_TYPE));

			// обновляем в базе
			businessDocumentService.addOrUpdate(claim);
		}
		catch (Exception e)
		{
			log.error("Ошибка перевода документа в статус ОТКАЗАН " + claim.getId(), e);
		}
	}

	private StateMachineExecutor getStateMachineExecutor(BusinessDocument document)
	{
		try
		{
			StateMachine stateMachine = stateMachineService.getStateMachineByFormName(document.getFormName());
			StateMachineExecutor executor = new StateMachineExecutor(stateMachine);
			executor.initialize(document);
			return executor;
		}
		catch (Exception e)
		{
			log.error("Ошибка получения машины состояний для документа " + document.getId(), e);
			return null;
		}
	}

	private List<ExtendedLoanClaim> findClaims(int historyAvailableDays) throws JobExecutionException
	{
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DATE, historyAvailableDays);
		try
		{
			return businessDocumentService.findOldClaim("WAIT_TM", date);
		}
		catch (BusinessException e)
		{
			log.error("Ошибка поиска заявок", e);
			throw new JobExecutionException(e);
		}
	}
}
