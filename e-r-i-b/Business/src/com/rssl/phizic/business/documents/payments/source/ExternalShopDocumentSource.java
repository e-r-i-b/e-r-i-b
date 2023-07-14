package com.rssl.phizic.business.documents.payments.source;

import com.rssl.common.forms.PaymentFieldKeys;
import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.CompositeFieldValuesSource;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.InternetShopsServiceProvider;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.documents.payments.validators.IsOwnDocumentValidator;
import com.rssl.phizic.business.shop.ExternalPaymentService;
import com.rssl.phizic.business.shop.ShopHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author bogdanov
 * @ created 13.03.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Сорс для платежей по технологии e-invoicing
 */
public class ExternalShopDocumentSource implements DocumentSource
{
	private final DocumentSource delegate;
	private Long personId;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param orderUUID - UUID заказа
	 * @param initialValuesSource
	 * @param type
	 * @param creationSourceType
	 */
	public ExternalShopDocumentSource(String orderUUID, FieldValuesSource initialValuesSource, CreationType type, CreationSourceType creationSourceType) throws BusinessException, BusinessLogicException
	{
		this.delegate = getDocumentSource(orderUUID, initialValuesSource, type, creationSourceType);
	}

	/**
	 * ctor
	 * @param personId - идентификатор клиента(если нет контекста)
	 * @param orderUUID - UUID заказа
	 * @param initialValuesSource  Источник значеий полей для работы с формами
	 * @param type Канал создания
	 * @param creationSourceType Способ создания документа
	 */
	public ExternalShopDocumentSource(Long personId, String orderUUID, FieldValuesSource initialValuesSource, CreationType type, CreationSourceType creationSourceType) throws BusinessException, BusinessLogicException
	{
		this.personId = personId;
		this.delegate = getDocumentSource(orderUUID, initialValuesSource, type, creationSourceType);
	}

	private DocumentSource getDocumentSource(String orderUUID, FieldValuesSource initialValuesSource, CreationType type, CreationSourceType creationSourceType) throws BusinessException, BusinessLogicException
	{
		InternetShopsServiceProvider provider = getRecipientByOrderUuid(orderUUID);
		FieldValuesSource valuesSource = prepareValueSource(initialValuesSource, provider);

		// Если к заказу уже привязан платёж, новый платёж не создаём
		JurPayment document = (JurPayment) (DocumentHelper.getPaymentByOrder(orderUUID));
		DocumentSource res = null;
		if (document == null)
			res = createNewDelegateSource(provider.getFormName(), valuesSource, type, creationSourceType);
		else
			res = createExistDelegateSource(document);

		JurPayment payment = (JurPayment) res.getDocument();
		payment.setOrderUuid(orderUUID);
		return res;
	}

	protected DocumentSource createNewDelegateSource(String formName, FieldValuesSource valuesSource, CreationType type, CreationSourceType creationSourceType) throws BusinessLogicException, BusinessException
	{
		return new NewDocumentSource(formName, valuesSource, type, creationSourceType) ;
	}

	protected DocumentSource createExistDelegateSource(JurPayment document) throws BusinessLogicException, BusinessException
	{
		return new ExistingSource(document, new IsOwnDocumentValidator());
	}

	protected InternetShopsServiceProvider getRecipientByOrderUuid(String orderUUID) throws BusinessException
	{
		return ShopHelper.get().getRecipientByOrderUuid(orderUUID);
	}

	private CompositeFieldValuesSource prepareValueSource(FieldValuesSource initialValuesSource, InternetShopsServiceProvider provider)
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put(PaymentFieldKeys.PROVIDER_KEY, String.valueOf(provider.getId()));
		return new CompositeFieldValuesSource(initialValuesSource, new MapValuesSource(map));
	}

	public BusinessDocument getDocument()
	{
		return delegate.getDocument();
	}

	public Metadata getMetadata()
	{
		return delegate.getMetadata();
	}

	/**
	 * @return проинициализированный идентификатор клиента
	 */
	public Long getPersonId()
	{
		return personId;
	}
}
