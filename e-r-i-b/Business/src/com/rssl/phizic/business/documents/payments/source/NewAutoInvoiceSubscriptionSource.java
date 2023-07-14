package com.rssl.phizic.business.documents.payments.source;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscriptionService;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.MetadataCache;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.CreateInvoiceSubscriptionPayment;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.operations.restrictions.OwnInvoiceSubscriptionRestriction;
import com.rssl.phizic.common.types.basket.InvoiceSubscriptionState;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author osminin
 * @ created 18.06.14
 * @ $Author$
 * @ $Revision$
 *
 * Создание заявки на подписку по сгенерированной подписке
 */
public class NewAutoInvoiceSubscriptionSource extends NewDocumentInvoiceSubscriptionSource<InvoiceSubscription>
{
	private static final InvoiceSubscriptionService invoiceSubscriptionService = new InvoiceSubscriptionService();

	/**
	 * ctor для клиентского создания(!)
	 * @param autoId идентификатор сгенерированной подписки
	 * @throws BusinessException
	 */
	public NewAutoInvoiceSubscriptionSource(Long autoId) throws BusinessException, BusinessLogicException
	{
		if (autoId == null)
			throw new IllegalArgumentException("Идентификатор услуги не может быть null.");

		InvoiceSubscription autoSub = getSubscription(autoId);
		if(autoSub == null)
			throw new BusinessException("Не найдена подписка с id = " + autoId);

		if(autoSub.getLoginId() != AuthModule.getAuthModule().getPrincipal().getLogin().getId())
			throw new BusinessException("Подписка с id = " + autoSub.getId() + " не принадлежит клиенту.");

		if(autoSub.getCreationType() != CreationType.system ||  autoSub.getState() != InvoiceSubscriptionState.DRAFT || StringHelper.isNotEmpty(autoSub.getAutoPayId()))
			throw new BusinessException("Невозможно создать заявку на основе подписки с id = " + autoSub.getId());

		initialize(autoSub, CreationType.internet, CreationSourceType.copy);
	}

	/**
	 * Коструктор
	 * @param subscription сущность подписки на инвойсы
	 * @param creationSourceType cпособ создания документа
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public NewAutoInvoiceSubscriptionSource(InvoiceSubscription subscription, CreationType creationType, CreationSourceType creationSourceType) throws BusinessException, BusinessLogicException
	{
		if (subscription == null)
			throw new IllegalArgumentException("subscription не может быть null.");

		initialize(subscription, creationType, creationSourceType);
	}

	protected BusinessDocument createDocument(InvoiceSubscription autoSub) throws BusinessException, BusinessLogicException
	{
		Metadata basicMetadata = MetadataCache.getBasicMetadata(FormConstants.CREATE_INVOICE_SUBSCRIPTION_PAYMENT);
		CreateInvoiceSubscriptionPayment payment =
				(CreateInvoiceSubscriptionPayment) documentService.createDocument(basicMetadata, new MapValuesSource(getInitialValues(autoSub)), null);

		try
		{
			payment.setReceiverInternalId(autoSub.getRecipientId());
			payment.setInvoiceSubscriptionId(autoSub.getId());
			payment.setLongOffer(true);
			payment.setExtendedFields(autoSub.getRequisitesAsList());
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}

		return payment;
	}

	private InvoiceSubscription getSubscription(Long id) throws BusinessException
	{
		InvoiceSubscription subscription = invoiceSubscriptionService.findById(id);
		//Проверяем существование услуги
		if (subscription == null)
		{
			throw new BusinessException("Услуга с id " + id + " не найдена.");
		}
		//Проверяем принадлежность услуги клиенту
		CommonLogin login = AuthModule.getAuthModule().getPrincipal().getLogin();
		if (login.getId() != subscription.getLoginId())
		{
			throw new BusinessException("Услуга с id = " + id + " не принадлежит клиенту.");
		}
		//Проверям, что услуга была сгенерирована
		if (InvoiceSubscriptionState.DRAFT != subscription.getState() || subscription.getCreationType() != CreationType.system)
		{
			throw new BusinessException("Услуга с id = " + id + " не является сгенерированной.");
		}

		return subscription;
	}
}
