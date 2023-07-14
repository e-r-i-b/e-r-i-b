package com.rssl.phizic.operations.claims.operation.scan.claim;

import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.GetPrivateOperationScanClaim;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.payments.source.NewDocumentSource;
import com.rssl.phizic.business.statemachine.PaymentStateMachineService;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.payment.support.DbDocumentTarget;
import com.rssl.phizic.utils.DateHelper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.rssl.phizic.operations.claims.operation.scan.claim.Constants.*;

/**
 * Операция отправки запроса на получение электронного документа на  электронную почту
 * @author komarov
 * @ created 16.04.2014
 * @ $Author$
 * @ $Revision$
 */
public class CreatePrivateScanClaimOperation extends OperationBase implements EditEntityOperation
{
	private GetPrivateOperationScanClaim claim;

	private final PaymentStateMachineService stateMachineService = new PaymentStateMachineService();

	private static final DbDocumentTarget documentTarget = new DbDocumentTarget();

	public void initialize(Map<String, Object> parameters) throws BusinessException, BusinessLogicException
	{
		Map<String, Object> values = new HashMap<String, Object>();
		String fio = PersonContext.getPersonDataProvider().getPersonData().getPerson().getFullName();

		values.put(GetPrivateOperationScanClaim.E_MAIL_ATTRIBUTE_NAME, parameters.get(E_MAIL_FIELD_NAME));
		values.put(GetPrivateOperationScanClaim.AUTHORISATION_CODE_ATTRIBUTE_NAME, parameters.get(AUTH_CODE_FIELD_NAME));
		values.put(GetPrivateOperationScanClaim.FIO_ATTRIBUTE_NAME, fio);
		values.put(GetPrivateOperationScanClaim.ACCOUNT_NUMBER_ATTRIBUTE_NAME, parameters.get(ACCOUNT_NUMBER_FIELD_NAME));
		values.put(GetPrivateOperationScanClaim.OPERATION_SEND_DATE_ATTRIBUTE_NAME, DateHelper.getXmlDateTimeFormat((Date)parameters.get(DATE_FIELD_NAME)));
		BigDecimal amount =  (BigDecimal)(new BigDecimal("0.00").equals(parameters.get(DEBIT_FIELD_NAME)) || parameters.get(DEBIT_FIELD_NAME) == null ? parameters.get(CREDIT_FIELD_NAME) : parameters.get(DEBIT_FIELD_NAME));
		values.put(GetPrivateOperationScanClaim.AMOUNT_ATTRIBUTE_NAME, amount);
		values.put(GetPrivateOperationScanClaim.DEBIT_ATTRIBUTE_NAME, (!new BigDecimal("0.00").equals(parameters.get(DEBIT_FIELD_NAME)) && parameters.get(DEBIT_FIELD_NAME) != null));

		claim = (GetPrivateOperationScanClaim)createDocument(values);
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		saveDocument(claim);
	}

	private void saveDocument(GetPrivateOperationScanClaim document) throws BusinessException, BusinessLogicException
	{
		StateMachineExecutor executor = new StateMachineExecutor(stateMachineService.getStateMachineByFormName(document.getFormName()));
		StateMachineEvent stateMachineEvent = new StateMachineEvent();
		executor.setStateMachineEvent(stateMachineEvent);
		executor.initialize(document);
		executor.fireEvent(new ObjectEvent(DocumentEvent.SAVE, ObjectEvent.CLIENT_EVENT_TYPE));
		documentTarget.save(document);
	}

	private BusinessDocument createDocument(Map<String,Object> values) throws BusinessException, BusinessLogicException
	{
		FieldValuesSource valuesSource = new MapValuesSource(values);
		DocumentSource source = new NewDocumentSource(GetPrivateOperationScanClaim.FORM_NAME, valuesSource, CreationType.internet, CreationSourceType.ordinary);
		return source.getDocument();
	}

	public Object getEntity() throws BusinessException, BusinessLogicException
	{
		return null;
	}
}
