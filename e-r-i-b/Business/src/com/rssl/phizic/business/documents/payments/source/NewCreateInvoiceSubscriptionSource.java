package com.rssl.phizic.business.documents.payments.source;

import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.metadata.MetadataCache;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.CreateInvoiceSubscriptionPayment;
import com.rssl.phizic.business.documents.payments.validators.CompositeDocumentValidator;
import com.rssl.phizic.business.documents.payments.validators.DocumentValidator;
import com.rssl.phizic.business.documents.payments.validators.IsOwnDocumentValidator;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.gate.documents.GateDocument;

/**
 * Источник новой заявки на создание подписки на инвойсы
 * @author niculichev
 * @ created 30.05.14
 * @ $Author$
 * @ $Revision$
 */
public class NewCreateInvoiceSubscriptionSource extends NewDocumentSource
{
	private static final String UNSUPPORT_OPERATION_PREFIX = "Невозможно создать подписку на инвойсы по документу с id = ";

	/**
	 * Конструктор
	 * @param paymentId идентификатор платежа, по коротому создается сущность заявки на подписку получения инвойсов
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public NewCreateInvoiceSubscriptionSource(Long paymentId, CreationType creationType) throws BusinessException, BusinessLogicException
	{
		BusinessDocument source = businessDocumentService.findById(paymentId);
		createDocumentValidator().validate(source);

		document = HelperSource.copyPersonDocument(source, CreateInvoiceSubscriptionPayment.class);
		// значения, которые не должны браться из копии
		document.setFormName(FormConstants.CREATE_INVOICE_SUBSCRIPTION_PAYMENT);
		document.setStateMachineName(null); // установится позднее из меты

		metadata = MetadataCache.getExtendedMetadata(document);
		initDocument(creationType, CreationSourceType.copy);
	}

	private DocumentValidator createDocumentValidator()
	{
		return new CompositeDocumentValidator(
				new IsOwnDocumentValidator(),
				new DocumentValidator()
				{
					public void validate(BusinessDocument document) throws BusinessException, BusinessLogicException
					{
						if(!DocumentHelper.isSupportCreateInvoiceSubscription(document))
							throw new BusinessException(UNSUPPORT_OPERATION_PREFIX + document.getId());
					}
				}
		);
	}
}
