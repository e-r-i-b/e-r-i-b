package com.rssl.phizic.sms.banking.commands;

import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.processing.StringErrorCollector;
import com.rssl.common.forms.validators.strategy.DocumentValidationStrategy;
import com.rssl.phizic.auth.modes.ConfirmRequest;
import com.rssl.phizic.auth.modes.PasswordCardConfirmRequest;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.claims.DocumentGuid;
import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.business.documents.payments.validators.IsOwnDocumentValidator;
import com.rssl.phizic.business.statemachine.PaymentStateMachineService;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.business.web.ConfirmationManager;
import com.rssl.phizic.business.web.SimpleSessionStore;
import com.rssl.phizic.operations.payment.ConfirmFormPaymentOperation;
import com.rssl.phizic.operations.payment.CreateFormPaymentOperation;
import com.rssl.phizic.operations.payment.EditDocumentOperation;
import com.rssl.phizic.sms.banking.security.UserSendException;
import com.rssl.phizic.utils.RandomHelper;
import com.rssl.phizic.utils.store.StoreManager;
import freemarker.template.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author khudyakov
 * @ created 11.11.2008
 * @ $Author$
 * @ $Revision$
 */
public abstract class PaymentCommandBase extends CommandBase
{
	private static final BusinessDocumentService documentService = new BusinessDocumentService();
	private static final SimpleService simpleService = new SimpleService();
	private final PaymentStateMachineService paymentStateMachineService = new PaymentStateMachineService();

	private static final String RESPONSE = "response";
	private static final String REQUEST = "request";

	public abstract DocumentSource getDocumentSource() throws BusinessException, UserSendException, BusinessLogicException;

	public abstract FieldValuesSource getSmsData() throws BusinessException, UserSendException, BusinessLogicException;

	public String execute() throws BusinessException, BusinessLogicException, UserSendException
	{
		SimpleSessionStore store = (SimpleSessionStore) StoreManager.getCurrentStore();

		DocumentSource source = getDocumentSource();
		BusinessDocument document = source.getDocument();

		StateMachineExecutor executor = new StateMachineExecutor(paymentStateMachineService.getStateMachineByFormName(document.getFormName()));
		executor.initialize(document);

		EditDocumentOperation editOperation = (EditDocumentOperation) createOperation(CreateFormPaymentOperation.class);
		editOperation.initialize(source);

		FormProcessor processor = new FormProcessor<List<String>, StringErrorCollector>(getSmsData(), source.getMetadata().getForm(), new StringErrorCollector(), DocumentValidationStrategy.getInstance());
		if (!processor.process())
		{
			return getMessageException(((List<String>) processor.getErrors()).get(0));
		}

		editOperation.updateDocument(processor.getResult());
		Long documentId = editOperation.save();

		createDocumentGuid(documentId);

		ExistingSource existingSource = new ExistingSource(documentId, new IsOwnDocumentValidator());

		ConfirmFormPaymentOperation confirmOperation = (ConfirmFormPaymentOperation) createOperation(ConfirmFormPaymentOperation.class);
		confirmOperation.initialize(existingSource);

		ConfirmationManager.sendRequest(confirmOperation);

		String ex = ConfirmationManager.currentConfirmRequest(document).getErrorMessage();
		if (ex != null)
		{
			return getMessageException(ex);
		}

		configuration = new Configuration();
		configuration.setTemplateLoader(new CommandLoader());

		store.save(RESPONSE + "_" + document.getId(), buildMessage(getResponse(document), RESPONSE));

		return buildMessage(getRequest(document), REQUEST);
	}

	private void createDocumentGuid(Long documentId) throws BusinessException
	{
		DocumentGuid documentGuid = new DocumentGuid();

		while (true)
		{
			String newGuid = RandomHelper.rand(4, RandomHelper.DIGITS);
			if (documentService.findIdByGuid(newGuid) == null)
			{
				documentGuid.setGuid(newGuid);
				break;
			}
		}

		documentGuid.setDocumentId(documentId);
		simpleService.add(documentGuid);
	}

	private Map<String, Object> getResponse(BusinessDocument document)
	{
		Map<String,  Object> map = new HashMap<String, Object>();
		map.put("document", document);
		map.put("template", parseString(message)[1]);
		if (((AbstractPaymentDocument) document).getCommission() == null)
			map.put("commision", "false");
		else
			map.put("commision", "true");

		return map;
	}

	private Map<String, Object> getRequest(BusinessDocument document) throws BusinessException
	{
		Map<String,  Object> map = new HashMap<String, Object>();
		map.put("document", document);
		map.put("template", parseString(message)[1]);
		map.put("paymentCode", documentService.findGuidById(document.getId()));
		if (((AbstractPaymentDocument) document).getCommission() == null)
			map.put("commision", "false");
		else
			map.put("commision", "true");

		ConfirmRequest confirmRequest = ConfirmationManager.currentConfirmRequest(document);
		if (confirmRequest instanceof PasswordCardConfirmRequest)
		{
			map.put("confirm", "true");
			map.put("cardNumber",((PasswordCardConfirmRequest) confirmRequest).getCardNumber());
			map.put("passwordNumber", ((PasswordCardConfirmRequest) confirmRequest).getPasswordNumber());
		}
		else
			map.put("confirm", "false");

		return map;
	}
}
