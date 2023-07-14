package com.rssl.phizic.business.documents.payments.source;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderState;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.MetadataCache;
import com.rssl.phizic.business.documents.payments.AutoSubType;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.gate.longoffer.SumType;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Создание документа по сущности подписки на инвойсы
 * @author niculichev
 * @ created 01.06.14
 * @ $Author$
 * @ $Revision$
 */
public class NewAutoPaymentByInvoiceSubscriptionSource extends NewDocumentInvoiceSubscriptionSource<InvoiceSubscription>
{
	/**
	 * Конструктор создания заявки на автоплатеж по внутренний
	 * идентификатор сущности подписки на инвойсы
	 * @param subscription внутренний идентификатор
	 * @throws BusinessException
	 */
	public NewAutoPaymentByInvoiceSubscriptionSource(InvoiceSubscription subscription) throws BusinessException, BusinessLogicException
	{
		if(subscription == null)
			throw new IllegalArgumentException("параметр subscription не может быть null");

		initialize(subscription, CreationType.internet, CreationSourceType.copy);
	}

	protected BusinessDocument createDocument(InvoiceSubscription subscription) throws BusinessException, BusinessLogicException
	{
		Metadata basicMetadata = MetadataCache.getBasicMetadata(FormConstants.SERVICE_PAYMENT_FORM);
		JurPayment payment = (JurPayment) documentService.createDocument(basicMetadata, new MapValuesSource(getInitialValues(subscription)), null);

		try
		{
			payment.setReceiverInternalId(subscription.getRecipientId());
			payment.setInvoiceSubscriptionId(subscription.getId());
			payment.setExtendedFields(subscription.getRequisitesAsList());
			payment.setState(new State("INITIAL")); // для метадаталоадера
			payment.setLongOffer(true);
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}

		return payment;
	}
}
