package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.basket.BasketHelper;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscriptionService;
import com.rssl.phizic.business.basket.links.DocumentLinkToIncoive;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.documents.payments.CreateInvoiceSubscriptionPayment;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Session;

import java.util.Set;

/**
 * @author niculichev
 * @ created 28.05.14
 * @ $Author$
 * @ $Revision$
 */
public class CreateInvoiceSubscriptionEntityAction extends BusinessDocumentHandlerBase
{
	private static final InvoiceSubscriptionService invoiceSubscriptionService = new InvoiceSubscriptionService();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		if(!(document instanceof CreateInvoiceSubscriptionPayment))
			throw new DocumentException("Ожидался класс " +  CreateInvoiceSubscriptionPayment.class.getName());

		CreateInvoiceSubscriptionPayment payment = (CreateInvoiceSubscriptionPayment) document;
		try
		{
			final InvoiceSubscription entity = invoiceSubscriptionService.createInvoiceSubEntity(payment);
			final Set<String> linkTypes = BasketHelper.getUsedPersonDocuments(payment);
			BusinessDocumentOwner documentOwner = payment.getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
			final Long loginId = payment.getOwner().getLogin().getId();

			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					invoiceSubscriptionService.save(entity);
					if(CollectionUtils.isNotEmpty(linkTypes))
					{
						for(String linkType : linkTypes)
							invoiceSubscriptionService.saveDocumentLink(new DocumentLinkToIncoive(loginId, linkType, entity.getId()));
					}

					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new DocumentException(e);
		}
	}
}
