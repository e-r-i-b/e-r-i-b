package com.rssl.phizic.operations.forms;

import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.NotFoundException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.business.documents.forms.DocumentContext;
import com.rssl.phizic.business.documents.forms.FormInfo;
import com.rssl.phizic.business.documents.forms.OperationData;
import com.rssl.phizic.business.documents.forms.TransformInfo;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.converters.FormDataConverter;
import com.rssl.phizic.business.documents.metadata.source.DocumentFieldValuesSource;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.documents.payments.form.AtmPaymentFormBuilder;
import com.rssl.phizic.business.documents.payments.form.MobilePaymentFormBuilder;
import com.rssl.phizic.business.documents.payments.form.PaymentFormBuilder;
import com.rssl.phizic.business.documents.payments.form.SocialPaymentFormBuilder;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.statemachine.MachineState;
import com.rssl.phizic.business.statemachine.PaymentStateMachineService;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.business.util.MaskPaymentFieldUtils;
import com.rssl.phizic.business.util.MaskingInfo;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.payment.PaymentOperationHelper;
import com.rssl.phizic.operations.payment.support.DbDocumentTarget;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.person.PersonDocument;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.transform.Source;

/**
 * @author Roshka
 * @ created 29.11.2006
 * @ $Author$
 * @ $Revision$
 */

public class ViewDocumentOperation extends OperationBase implements ViewEntityOperation
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final PersonService personService = new PersonService();

	private final PaymentStateMachineService stateMachineService = new PaymentStateMachineService();

	protected Metadata metadata;
	protected BusinessDocument document;
	private Person owner;
	private Office office = null;
	private Department department = null;
	private boolean fnsPayment = false; // по умолчанию платеж не из ФНС

	//Виды платежей, для которых разрешена печать чека
	private static final Set<String> printableFormNames = new HashSet<String>();

	static
	{
		printableFormNames.add(FormConstants.RUR_PAYMENT_FORM);
		printableFormNames.add(FormConstants.NEW_RUR_PAYMENT);
		printableFormNames.add(FormConstants.JUR_PAYMENT_FORM);
		printableFormNames.add(FormConstants.SERVICE_PAYMENT_FORM);
		printableFormNames.add(FormConstants.INTERNAL_PAYMENT_FORM);
		printableFormNames.add(FormConstants.CONVERT_CURRENCY_PAYMENT_FORM);
		printableFormNames.add(FormConstants.TAX_PAYMENT_FORM);
		printableFormNames.add(FormConstants.LOAN_PAYMENT_FORM);
		printableFormNames.add(FormConstants.EXTERNAL_PROVIDER_PAYMENT_FORM);
		printableFormNames.add(FormConstants.FNS_PAYMENT_FORM);
		printableFormNames.add(FormConstants.AIRLINE_RESERVATION_PAYMENT_FORM);
		printableFormNames.add(FormConstants.ACCOUNT_CLOSING_PAYMENT_FORM);
		printableFormNames.add(FormConstants.ACCOUNT_OPENING_CLAIM_FORM);
		printableFormNames.add(FormConstants.IMA_PAYMENT_FORM);
		printableFormNames.add(FormConstants.ACCOUNT_OPENING_CLAIM_WITH_CLOSE_FORM);
		printableFormNames.add(FormConstants.IMA_OPENING_CLAIM);
		printableFormNames.add(FormConstants.INVOICE_PAYMENT_FORM);
		printableFormNames.add(FormConstants.CREDIT_REPORT_PAYMENT);
		printableFormNames.add(FormConstants.EXTENDED_LOAN_CLAIM);
		printableFormNames.add(FormConstants.REMOTE_CONNECTION_UDBO_CLAIM_FORM);
		printableFormNames.add(FormConstants.REPORT_BY_CARD_CLAIM);
		printableFormNames.add(FormConstants.CHANGE_CREDIT_LIMIT_CLAIM);		printableFormNames.add(FormConstants.LOAN_CARD_CLAIM);
		printableFormNames.add(FormConstants.EARLY_LOAN_REPAYMENT_CLAIM);
	}

	//Статусы платежей, для которых разрешена печать чека
	private static final Set<String> printablePaymentStates = new HashSet<String>();

	static
	{
		printablePaymentStates.add("DELAYED_DISPATCH");
		printablePaymentStates.add("DISPATCHED");
		printablePaymentStates.add("EXECUTED");
		printablePaymentStates.add("TICKETS_WAITING");
		printablePaymentStates.add("ERROR");
		printablePaymentStates.add("UNKNOW");
		printablePaymentStates.add("SENT");
		printablePaymentStates.add("REFUSED");
		printablePaymentStates.add("OFFLINE_DELAYED");
		printablePaymentStates.add("RECEIVED");
		printablePaymentStates.add("PARTLY_EXECUTED");
		printablePaymentStates.add("SUCCESSED");
		printablePaymentStates.add("ADOPTED");
		printablePaymentStates.add("ISSUED");
		printablePaymentStates.add("PREADOPTED");
	}

	/**
	 * @return платеж
	 */
	public BusinessDocument getEntity()
	{
		return document;
	}

	/**
	 * @param source источник данных
	 */
	public void initialize(DocumentSource source) throws BusinessException
	{
		metadata = source.getMetadata();
		document = source.getDocument();
		owner = getDocumentOwner(document);
//TODO зачем это?
		if (document instanceof GateExecutableDocument)
		{
			GateExecutableDocument executableDocument = (GateExecutableDocument) document;
			office = executableDocument.getOffice() == null ? null : executableDocument.getOffice();
		}
		department = (Department) document.getDepartment();

		// платеж из ФНС
		if (document.getFormName().equals(FormConstants.FNS_PAYMENT_FORM))
			setFnsPayment(true);
	}

	protected Person getDocumentOwner(BusinessDocument document) throws BusinessException
	{
		BusinessDocumentOwner documentOwner = document.getOwner();
		if(documentOwner == null)
			return null;

		if (documentOwner.isGuest())
			return documentOwner.getPerson();

		Person res = documentOwner.getPerson();
		//не нашли в активных, ищем в удаленных
		if (res == null)
		{
			try
			{
				res = personService.findDeletedByLogin(documentOwner.getLogin());
			}
			catch(NotFoundException ignored)
			{
				throw new BusinessException("Владелец документа на найден ни в активных, ни в удаленных пользователях.");
			}
		}

		return res;
	}

	/**
	 * @return метаданные документа
	 */
	public Metadata getMetadata()
	{
		return metadata;
	}

	public String getMetadataPath() throws BusinessException
	{
		return PaymentOperationHelper.calculateMetadataPath(metadata, document);
	}

	/**
	 * @return Данные для преобразования
	 */
	public Source createFormData() throws BusinessException
	{
		FieldValuesSource fieldValuesSource = new DocumentFieldValuesSource(metadata, document);
		if(MaskPaymentFieldUtils.isRequireMasking(document))
			fieldValuesSource = MaskPaymentFieldUtils.wrapMaskValuesSource(getMaskingInfo(fieldValuesSource), fieldValuesSource);

		return new FormDataConverter(metadata.getForm(),fieldValuesSource).toFormData();
	}

	/**
	 * @return можно ли отозвать платеж
	 */
	public boolean canWithdraw()
	{
		return metadata.getWithdrawOptions().canDo(document);
	}

	/**
	 * @return можно ли редактировать платеж
	 */
	public boolean canEdit()
	{
		return metadata.getEditOptions().canDo(document);
	}

	public Person getOwner()
	{
		return owner;
	}

	public Office getOffice()
	{
		return office;
	}

	public Department getDepartment()
	{
		return department;
	}

	public Map<String, String> getFieldValues() throws BusinessException
	{
		DocumentFieldValuesSource fieldValuesSource = new DocumentFieldValuesSource(metadata, document);
		FormDataConverter converter = new FormDataConverter(metadata.getForm(), fieldValuesSource);
		converter.toFormData();
		return converter.getFieldValues();
	}

	/**
	 * Редактирование документа
	 * @return документ
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	@Transactional
	public MachineState edit() throws BusinessException, BusinessLogicException
	{
		StateMachineExecutor executor = getStateMachineExecutor();
		executor.initialize(document);
		executor.fireEvent(new ObjectEvent(DocumentEvent.EDIT, ObjectEvent.CLIENT_EVENT_TYPE));

		save();
		return executor.getCurrentState();
	}

	public void save() throws BusinessException
	{
		new DbDocumentTarget().save(document);
	}

	protected StateMachineExecutor getStateMachineExecutor()
	{
		return new StateMachineExecutor(stateMachineService.getStateMachineByFormName(document.getFormName()));
	}

	/**
	 * Проверка статуса заявки перед отправкой в архив.
	 * @return true - можно отправлять (статус заявки “Отказана”, “Утверждена” или "Кредит выдан")
	 */
	public boolean checkBeforeSendInArchive()
	{
		State state = document.getState();
		if (state.equals(new State("REFUSED")) || state.equals(new State("APPROVED")) || state.equals(new State("EXECUTED")))
			return true;

		return false;
	}

	/**
	 * Проверка статуса заявки перед установкой комментария.
	 * @return true - можно добавить комментарий (статус заявки не “Утверждена”)
	 */
	public boolean checkBeforeSetComment()
	{
		if (document.getState().equals(new State("APPROVED")))
			return false;

		return true;
	}

	/**
	 * Проверка статуса заявки перед печатью документов.
	 * @return true - можно печатать (статус заявки “Утверждена”)
	 */
	public boolean checkBeforePrintDocuments()
	{
		if (document.getState().equals(new State("APPROVED")))
			return true;

		return false;
	}

	/**
	 * Проверка возможности печати чека.
	 * @return true - можно печатать (статус заявки “Исполнен” или "Обрабатывается")
	 */
	public boolean checkPrintCheck()
	{
		return printablePaymentStates.contains(document.getState().getCode()) && checkPrintCheckForOperation();
	}

	/**
	 * Проверка разрешения печати чека для данной операции.
	 * @return true - можно печатать
	 */
	public boolean checkPrintCheckForOperation()
	{
		return printableFormNames.contains(document.getFormName());
	}

	public boolean getExternalPayment() throws BusinessException
	{
		return DocumentHelper.isExternalPayment(document);
	}

	public boolean getFnsPayment()
	{
		return fnsPayment;
	}

	public void setFnsPayment(boolean fnsPayment)
	{
		this.fnsPayment = fnsPayment;
	}

	/**
	 * Создать html описание формы документа
	 *
	 * @param transformInfo информация по преобразованию
	 * @param formInfo информация по форме
	 * @return html
	 * @throws BusinessException
	 */
	public String buildFormHtml(TransformInfo transformInfo, FormInfo formInfo) throws BusinessException
	{
		return new PaymentFormBuilder(getDocumentContext(), transformInfo, formInfo).build(createFormData());
	}

	/**
	 * Создать html описание формы документа
	 *
	 * @param transformInfo информация по преобразованию
	 * @param formInfo информация по форме
	 * @return html
	 * @throws BusinessException
	 */
	public String buildMobileXml(TransformInfo transformInfo, FormInfo formInfo) throws BusinessException
	{
		return new MobilePaymentFormBuilder(getDocumentContext(), transformInfo, formInfo).build(createFormData());
	}

	public String buildSocialXml(TransformInfo transformInfo, FormInfo formInfo) throws BusinessException
	{
		return new SocialPaymentFormBuilder(getDocumentContext(), transformInfo, formInfo).build(createFormData());
	}

	/**
	 * Создать html описание формы документа
	 *
	 * @param transformInfo информация по преобразованию
	 * @param formInfo информация по форме
	 * @return html
	 * @throws BusinessException
	 */
	public String buildATMXml(TransformInfo transformInfo, FormInfo formInfo) throws BusinessException
	{
		return new AtmPaymentFormBuilder(getDocumentContext(), transformInfo, formInfo).build(createFormData());
	}

	private DocumentContext getDocumentContext()
	{
		return new DocumentContext(getEntity(), getMetadata(), getOperationData());
	}

	private OperationData getOperationData()
	{
		return new OperationData(checkPrintCheck());
	}

	protected MaskingInfo getMaskingInfo(FieldValuesSource fieldValuesSource)
	{
		List<PersonDocument> personDocuments = PersonHelper.getDocumentForProfile(owner.getPersonDocuments());
		Map<String, String> documentFieldsInfo =
				MaskPaymentFieldUtils.buildDocumentSeriesAndNumberValues(personDocuments);

		return new MaskingInfo(metadata, fieldValuesSource, documentFieldsInfo.values());
	}
}
