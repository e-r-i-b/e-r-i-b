package com.rssl.phizic.business.documents.payments.source;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderState;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.MetadataCache;
import com.rssl.phizic.business.documents.payments.AutoSubType;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.CreateInvoiceSubscriptionPayment;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.payments.forms.meta.NewDocumentHandler;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.common.types.client.LoginType;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.gate.longoffer.SumType;
import com.rssl.phizic.gate.payments.longoffer.CardPaymentSystemPaymentLongOffer;
import com.rssl.phizic.gate.payments.longoffer.DelayCardPaymentSystemPaymentLongOffer;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.StringHelper;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Niculichev
 * @ created 01.07.15
 * @ $Author$
 * @ $Revision$
 */
public class NewInvoiceSubscriptionByAutoPaymentSource extends NewDocumentInvoiceSubscriptionSource<CardPaymentSystemPaymentLongOffer>
{
	public NewInvoiceSubscriptionByAutoPaymentSource(CardPaymentSystemPaymentLongOffer claim) throws BusinessException, BusinessLogicException
	{
		if(claim == null)
			throw new IllegalArgumentException("claim не может быть null");

		initialize(claim, CreationType.system, CreationSourceType.copy);
	}

	@Override
	protected BusinessDocument createDocument(CardPaymentSystemPaymentLongOffer source) throws BusinessException, BusinessLogicException
	{
		Metadata basicMetadata = MetadataCache.getBasicMetadata(FormConstants.CREATE_INVOICE_SUBSCRIPTION_PAYMENT);
		CreateInvoiceSubscriptionPayment payment = (CreateInvoiceSubscriptionPayment) documentService.createDocument(basicMetadata, new MapValuesSource(getInitialValues(source)), null);

		try
		{
			payment.setReceiverInternalId(source.getReceiverInternalId());
			payment.setExtendedFields(source.getExtendedFields());
			payment.setState(new State("INITIAL")); // для метадаталоадера
			payment.setLongOffer(true);
			payment.setLongOfferExternalId(source.getLongOfferExternalId());

		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}

		return payment;
	}

	protected boolean isAnonymous()
	{
		return false;
	}

	protected Long getNodeTemporaryNumber()
	{
		return null;
	}

	protected LoginType getLoginType()
	{
		return null;
	}

	protected boolean isEmployeeCreate()
	{
		return false;
	}

	protected NewDocumentHandler getNewDocumentHandler() throws DocumentException, DocumentLogicException
	{
		return new NewDocumentHandler()
		{
			protected boolean isAnonymous()
			{
				return false;
			}
		};
	}
}

