package com.rssl.phizic.operations.payment;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.business.documents.LoanClaim;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.converters.FormDataConverter;
import com.rssl.phizic.business.documents.metadata.source.DocumentFieldValuesSource;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.operations.restrictions.DocumentRestriction;
import com.rssl.phizic.business.statemachine.PaymentStateMachineService;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ViewEntityOperation;
import org.w3c.dom.Document;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;

/**
 * @author Kidyaev
 * @ created 20.12.2006
 * @ $Author$
 * @ $Revision$
 */
public class ProcessDocumentOperation extends OperationBase<DocumentRestriction> implements ViewEntityOperation
{
	private static final SimpleService simpleService = new SimpleService();

	private final PaymentStateMachineService stateMachineService = new PaymentStateMachineService();

	private BusinessDocument document;
	private Metadata metadata;
	private Office office = null;

	/**
	 * @return документ
	 */
	public BusinessDocument getEntity()
	{
		return document;
	}

	/**
	 * @return метаданные
	 */
	public Metadata getMetadata()
	{
		return metadata;
	}

	/**
	 * бэк-офис в который был отправлен документ.
	 * @return
	 */
	public Office getOffice()
	{
		return office;
	}

	/**
	 * Инициализация операции
	 * @param source DocumentSource
	 * @throws BusinessException
	 */
	public void initialize(DocumentSource source) throws BusinessException, BusinessLogicException
	{
		BusinessDocument temp = source.getDocument();

		if (temp.getFormName() == null)
			throw new BusinessException("claim.formName is null");

		document = temp;

		if(!getRestriction().accept(getEntity()))
			throw new BusinessLogicException("У Вас нет доступа к документу №"  + document.getDocumentNumber());

		metadata = source.getMetadata();
//TODO зачем это?
		if (document instanceof GateExecutableDocument)
		{
			GateExecutableDocument executableDocument = (GateExecutableDocument) document;
			office = executableDocument.getOffice() == null ? null : executableDocument.getOffice();
		}
	}

	/**
	 * Преобразование документа в данные формы
	 * @return данные формы
	 * @throws BusinessException
	 * @throws TransformerException
	 */
	public Source createFormData() throws BusinessException, TransformerException
	{
		DocumentFieldValuesSource fieldValuesSource = new DocumentFieldValuesSource(metadata, document);
		return new FormDataConverter(metadata.getForm(), fieldValuesSource).toFormData();
	}

	/**
	 * @return xml-представление документа
	 * @throws BusinessException
	 */
	public Document createDocumentXml() throws BusinessException
	{
		try
		{
			return document.convertToDom();
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Одобрить документ (документ приобретает статус - Одобрен)
	 * @throws BusinessException
	 */
	@Transactional
	public void accept() throws BusinessException, BusinessLogicException
	{
		StateMachineExecutor executor = getStateMachineExecutor();
		executor.initialize(document);
		executor.fireEvent(new ObjectEvent(DocumentEvent.ACCEPT, "employee"));

		simpleService.addOrUpdate(document);
	}

	@Transactional
	public void send() throws BusinessException, BusinessLogicException
	{
		StateMachineExecutor executor = getStateMachineExecutor();
		executor.initialize(document);
		executor.fireEvent(new ObjectEvent(DocumentEvent.SEND, "employee"));

		simpleService.addOrUpdate(document);
	}

	/**
	 * Отклонить документ (документ приобретает статус - Отказан)
	 *
	 * @param reason - причина отказа.
	 * @throws BusinessException
	 */
	@Transactional
	public void refuse(String reason) throws Exception
	{
		StateMachineExecutor executor = getStateMachineExecutor();
		executor.initialize(document);
		executor.fireEvent(new ObjectEvent(DocumentEvent.REFUSE, "employee", reason));

		simpleService.addOrUpdate(document);
	}

	/**
	 * Отправить документ клиенту на доработку (документ приобретает статус "Требуется доработка")
	 * @throws BusinessException
	 */
	@Transactional
	public void completion() throws BusinessException, BusinessLogicException
	{
		StateMachineExecutor executor = getStateMachineExecutor();
		executor.initialize(document);
		executor.fireEvent(new ObjectEvent(DocumentEvent.COMPLETION, "employee"));
		simpleService.addOrUpdate(document);
	}

	/**
	 * Добавить комментарий
	 * @throws BusinessException
	 */
	@Transactional
	public void setComment(String comment) throws BusinessException
	{
		if (!checkBeforeSetComment())
			throw new BusinessException("wrong claim state");

		if (!(document instanceof LoanClaim))
			throw new BusinessException("Неверный тип документа " + document.getClass().getName() + ". Ожидается LoanClaim");

		LoanClaim claim = (LoanClaim) document;
		claim.setComment(comment);
		simpleService.addOrUpdate(document);
	}

	/**
	 * Отправить документ в архив
	 * @throws BusinessException
	 */
	public void sendInArchive() throws BusinessException
	{
		if (!checkBeforeSendInArchive())
			throw new BusinessException("wrong claim state");

		document.setArchive(true);
		simpleService.addOrUpdate(document);
	}

	/**
	 * Вернуть документ из архива
	 * @throws BusinessException
	 */
	public void returnFromArchive() throws BusinessException
	{
		if (!checkBeforeSendInArchive())
			throw new BusinessException("wrong claim state");

		document.setArchive(false);
		simpleService.addOrUpdate(document);
	}


	/**
	 * Исполнить документ (документ приобретает статус - Исполнен)
	 * @throws BusinessException
	 */
	@Transactional
	public void execute() throws BusinessException, BusinessLogicException
	{
		StateMachineExecutor executor = getStateMachineExecutor();
		executor.initialize(document);
		executor.fireEvent(new ObjectEvent(DocumentEvent.EXECUTE, "employee"));

		simpleService.addOrUpdate(document);
	}

	/**
	 * Исполнить документ (документ приобретает статус - Исполнен), от лица системы
	 * @throws BusinessException
	 */
	@Transactional
	public void executeSystem() throws BusinessException, BusinessLogicException
	{
		setDocumentStatus(DocumentEvent.EXECUTE);
	}

	/**
	 * Принять заявку
	 * @throws BusinessException
	 */
	@Transactional
	public void adoptSystem() throws BusinessException, BusinessLogicException
	{
		setDocumentStatus(DocumentEvent.ADOPT);
	}

	/**
	 * Проверка статуса документа.
	 * @return true - можно отправлять (статус документа "Принят")
	 */
	public boolean checkState()
	{
		return document.getState().equals(new State("DISPATCHED"));
	}

	/**
	 * Проверка статуса документа перед установкой комментария.
	 * @return true - можно добавить комментарий (статус документа не “Утвержден”)
	 */
	public boolean checkBeforeSetComment()
	{
		if (document.getState().equals(new State("APPROVED")))
			return false;

		return true;
	}

	/**
	 * Проверка статуса документа перед отправкой в архив.
	 * @return true - можно отправлять (статус документа “Отказан”, “Утвержден” или "Кредит выдан")
	 */
	public boolean checkBeforeSendInArchive()
	{
		State state = document.getState();
		if (state.equals(new State("REFUSED")) || state.equals(new State("APPROVED")) || state.equals(new State("EXECUTED")))
			return true;

		return false;
	}

	/**
	 * Проверка статуса документа перед печатью
	 * @return true - можно печатать (статус документа “Утвержден”)
	 */
	public boolean checkBeforePrintDocuments()
	{
		if (document.getState().equals(new State("APPROVED")))
			return true;

		return false;
	}

	private StateMachineExecutor getStateMachineExecutor() throws BusinessException
	{
		return new StateMachineExecutor(stateMachineService.getStateMachineByFormName(document.getFormName()));
	}

	private void setDocumentStatus(DocumentEvent documentEvent) throws BusinessException, BusinessLogicException
	{
		StateMachineExecutor executor = getStateMachineExecutor();
		executor.initialize(document);
		executor.fireEvent(new ObjectEvent(documentEvent, "system"));

		simpleService.addOrUpdate(document);
	}
}
