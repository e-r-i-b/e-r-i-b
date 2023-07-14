package com.rssl.phizic.business.documents.payments.source;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription;
import com.rssl.phizic.business.documents.AbstractLongOfferDocument;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.payments.forms.meta.NewDocumentHandler;
import com.rssl.phizic.common.types.client.LoginType;
import com.rssl.phizic.gate.payments.longoffer.ChannelType;

/**
 * Источник заявок на изменение статуса автопоисков, создаваемых с помощью системы
 * @author niculichev
 * @ created 23.07.15
 * @ $Author$
 * @ $Revision$
 */
public class NewSystemInvoiceSubscriptionClaimSource extends NewInvoiceSubscriptionClaimSource
{
	/**
	 * ctor
	 * @param subscription сущность автопоиска
	 * @param formName имя формы заявки
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public NewSystemInvoiceSubscriptionClaimSource(InvoiceSubscription subscription, String formName) throws BusinessException, BusinessLogicException
	{
		super(subscription, formName, CreationType.system, CreationSourceType.copy);
		// формально создает в клиентском приложении
		((AbstractLongOfferDocument) getDocument()).setChannelType(ChannelType.IB);
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
