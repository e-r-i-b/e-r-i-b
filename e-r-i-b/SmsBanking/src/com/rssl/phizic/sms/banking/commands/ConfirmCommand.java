package com.rssl.phizic.sms.banking.commands;

import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.source.GuidDocumentSource;
import com.rssl.phizic.business.smsbanking.pseudonyms.NullPseudonymException;
import com.rssl.phizic.business.statemachine.PaymentStateMachineService;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.business.web.ConfirmationManager;
import com.rssl.phizic.operations.payment.ConfirmFormPaymentOperation;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.security.config.Constants;
import com.rssl.phizic.sms.banking.security.UserSendException;
import com.rssl.phizic.utils.store.Store;
import com.rssl.phizic.utils.store.StoreManager;
import freemarker.template.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hudyakov
 * @ created 17.11.2008
 * @ $Author$
 * @ $Revision$
 */
public class ConfirmCommand extends CommandBase
{
	private static final BusinessDocumentService documentService = new BusinessDocumentService();

	private static final String RESPONSE = "response";

	private final PaymentStateMachineService stateMachineService = new PaymentStateMachineService();

	public String execute() throws UserSendException, BusinessException, BusinessLogicException, NullPseudonymException
	{
		String[] parseMessage = parseString(message);

		Store store = StoreManager.getCurrentStore();

		GuidDocumentSource source = new GuidDocumentSource(parseMessage[0]);
		BusinessDocument document = source.getDocument();

		ConfirmFormPaymentOperation confirmOperation = (ConfirmFormPaymentOperation) createOperation(ConfirmFormPaymentOperation.class);
        confirmOperation.initialize(source);
		confirmOperation.setStrategyType();

		configuration = new Configuration();
		configuration.setTemplateLoader(new ExceptionLoader());

		Map<String, Object> map = new HashMap <String, Object>();
		if (parseMessage.length == 2)
		{
			// если длина другая (то вообще то надо кинуть WrongCommandFormat)
			map.put(Constants.CONFIRM_CARD_PASSWORD_FIELD, parseMessage[1]);
		}

		List<String> errors = ConfirmationManager.readResponse(confirmOperation, new MapValuesSource(map));

		if (errors.size() != 0)
		{
			setDocumentRejected(document);
			return buildMessage(getRootMap(document), "ex1");
		}

		try
		{
			confirmOperation.confirm();
		}
		catch (SecurityLogicException e)
		{
			setDocumentRejected(document);
            return buildMessage(getRootMap(document), "ex1");
		}
		catch (BusinessLogicException e)
		{
			setDocumentRejected(document);
			return buildMessage(getRootMap(document), "ex2");
		}

		return (String) store.restore(RESPONSE + "_" + document.getId());
	}

	private void setDocumentRejected(BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		StateMachineExecutor executor = new StateMachineExecutor(stateMachineService.getStateMachineByFormName(document.getFormName()));

		executor.initialize(document);
		executor.fireEvent(new ObjectEvent(DocumentEvent.REFUSE, "system"));

		document.setRefusingReason("Ошибка при сохранении в ритейл");
		documentService.addOrUpdate(document);
	}

	private Map<String, Object> getRootMap(BusinessDocument document) throws BusinessException
	{
		Map<String,  Object> map = new HashMap<String, Object>();
			map.put("document", document);
		    map.put("paymentCode", documentService.findGuidById(document.getId()));

		return map;
	}
}
