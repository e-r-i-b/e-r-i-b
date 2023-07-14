package com.rssl.phizic.business.documents.payments.source;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.documents.BusinessDocumentBase;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.MetadataCache;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.documents.payments.validators.DocumentValidator;
import com.rssl.phizic.business.documents.payments.validators.NullDocumentValidator;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.gate.einvoicing.ShopOrder;
import com.rssl.phizic.logging.operations.context.OperationContextUtil;

/**
 * На основе существующего
 * @author Evgrafov
 * @ created 13.12.2005
 * @ $Author: gulov $
 * @ $Revision: 74721 $
 */
public class ExistingSource extends PersonDocumentSourceBase implements DocumentSource
{
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();
	private static PersonService personService = new PersonService();

	private Metadata metadata;
	private BusinessDocument document;

	/**
	 * Дефолтный конструктор для наследников, которым требуется предварительные действия перед инициализацией.
	 * инициализаю подобные наслендники должны производить самостоятельно.
	 */
	protected ExistingSource()
	{}

	/**
	 * @param id ID платежа
	 * @param validator стратегия провеки допустимости редактирования данного документа
	 */
	public ExistingSource(Long id, DocumentValidator validator) throws BusinessException, BusinessLogicException
	{
		initialize(id, validator);
	}

	/**
	 * @param document документ
	 * @param validator стратегия провеки допустимости редактирования данного документа
	 */
	public ExistingSource(BusinessDocument document, DocumentValidator validator) throws BusinessException, BusinessLogicException
	{
		this.document = document;
		initialize(document, validator);
	}

	/**
	 * ctor
	 * @param order заказ
	 */
	public ExistingSource(ShopOrder order) throws BusinessException, BusinessLogicException
	{
		document = DocumentHelper.getPaymentByOrder(order.getUuid());
		initialize(document, new NullDocumentValidator());
	}

	protected void initialize(Long id, DocumentValidator validator) throws BusinessException, BusinessLogicException
	{
		if (id == null)
		{
			throw new BusinessException("null paymentId");
		}

		document = businessDocumentService.findById(id);

		initialize(document, validator);
	}

	private void initialize(BusinessDocument document, DocumentValidator validator) throws BusinessException, BusinessLogicException
	{
		if(isSynchContext())
			OperationContextUtil.synchronizeObjectAndOperationContext(document);

		if (document == null)
		{
			throw new ResourceNotFoundBusinessException("Document not found", BusinessDocument.class);
		}

		//Если подразделение было удалено, устанавливаем подразделение клиента.
		if (document.getDepartment() == null)
		{
			BusinessDocumentOwner documentOwner = document.getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("Для гостя невозможно восстановить департамент, для документа с ID = " + document.getId());
			else
			{
				Long loginId = document.getOwner().getLogin().getId();
				BusinessDocumentBase payment = (BusinessDocumentBase) document;
				payment.setDepartment(personService.getDepartmentByLoginId(loginId));
			}
		}

		validator.validate(document);

		metadata = getDocumentMetadata();
		document.setStateMachineName(metadata.getStateMachineInfo().getName()); //TODO для поддержки старых платежей без машины состояний, написать запрос на конвертирование, после убрать
	}

	public BusinessDocument getDocument()
	{
		return document;
	}

	public Metadata getMetadata()
	{
		return metadata;
	}

	/**
	 * @return true - режим с синхронизацией контекста
	 */
	protected boolean isSynchContext()
	{
		return true;
	}

	private Metadata getDocumentMetadata() throws BusinessLogicException, BusinessException
	{
		if (document.isByTemplate())
		{
			TemplateDocument template = TemplateDocumentService.getInstance().findById(document.getTemplateId());
			if (template != null)
			{
				return MetadataCache.getExtendedMetadata(document, template);
			}
		}

		return MetadataCache.getExtendedMetadata(document);
	}
}
