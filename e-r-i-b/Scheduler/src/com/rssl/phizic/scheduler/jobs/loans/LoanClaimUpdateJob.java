package com.rssl.phizic.scheduler.jobs.loans;

import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.statemachine.PaymentStateMachineService;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.documents.DocumentService;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.LoanClaimStateUpdateInfo;
import com.rssl.phizic.gate.documents.StateUpdateInfo;
import com.rssl.phizic.gate.loans.LoanOpeningClaim;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Maleyev
 * @ created 10.04.2008
 * @ $Author$
 * @ $Revision$
 */
public class LoanClaimUpdateJob extends BaseJob implements StatefulJob
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_SCHEDULER);
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();
	private final PaymentStateMachineService paymentStateMachineService = new PaymentStateMachineService();
	private static final Map<String, DocumentEvent> events = new HashMap<String, DocumentEvent>();

	static {
			events.put("COMPLETION",    DocumentEvent.COMPLETION);
			events.put("CONSIDERATION", DocumentEvent.CONSIDERATION);
			events.put("APPROVED",      DocumentEvent.APPROVE);
			events.put("REFUSED",       DocumentEvent.REFUSE);
			events.put("EXECUTED",      DocumentEvent.EXECUTE);
		}

	@Override protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
        log.debug("Запуск проверки статусов заявок на кредит");

        try
        {
	        List<BusinessDocument> list = businessDocumentService.findByState("CONSIDERATION");
	        DocumentService service = GateSingleton.getFactory().service(DocumentService.class);
	        for (BusinessDocument document : list)
	        {
		        StateUpdateInfo state = service.update((GateDocument) document);
		        if (state instanceof LoanClaimStateUpdateInfo)
		        {
			        StateMachineExecutor executor = new StateMachineExecutor(paymentStateMachineService.getStateMachineByFormName(document.getFormName()));

			        executor.initialize(document);
			        executor.fireEvent(new ObjectEvent(events.get(state.getState().getCode()), "system"));

			        log.info("Заявка на кредит N "+document.getId()+"/"+((LoanOpeningClaim)document).getExternalId()+ " : "+state.getState().getDescription());
			        if (state.getState().equals(new State("EXECUTED")) ||
					    state.getState().equals(new State("APPROVED")))
			        {
		                ((LoanOpeningClaim) document).setApprovedAmount(((LoanClaimStateUpdateInfo)state).getApprovedAmount());
		                ((LoanOpeningClaim) document).setApprovedDuration(((LoanClaimStateUpdateInfo)state).getApprovedDuration());
			        }
			        businessDocumentService.addOrUpdate(document);
		        }
	        }

        }
        catch (Exception e)
        {
	        throw new JobExecutionException(e);
        }
		log.debug("Проверка статусов заявок на кредит завершена");
	}

}
