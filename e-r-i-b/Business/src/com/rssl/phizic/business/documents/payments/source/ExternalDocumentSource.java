package com.rssl.phizic.business.documents.payments.source;

import com.rssl.common.forms.PaymentFieldKeys;
import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.CompositeFieldValuesSource;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.NotFoundException;
import com.rssl.phizic.business.dictionaries.providers.InternetShopsServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.shop.ExternalPaymentService;
import com.rssl.phizic.business.shop.Order;
import com.rssl.phizic.business.shop.ShopHelper;
import com.rssl.phizic.gate.einvoicing.ShopOrder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Erkin
 * @ created 18.07.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Сорс для платежей ФНС и УЭК
 */
public class ExternalDocumentSource extends ExternalShopDocumentSource
{
	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param orderUUID - UUID заказа
	 * @param initialValuesSource
	 * @param type
	 * @param creationSourceType
	 */
	public ExternalDocumentSource(String orderUUID, FieldValuesSource initialValuesSource, CreationType type, CreationSourceType creationSourceType) throws BusinessException, BusinessLogicException
	{
		super(orderUUID, initialValuesSource, type, creationSourceType);
	}

	protected InternetShopsServiceProvider getRecipientByOrderUuid(String orderUUID) throws BusinessException
	{
		return ExternalPaymentService.get().getRecipientByOrderUuid(orderUUID);
	}
}
