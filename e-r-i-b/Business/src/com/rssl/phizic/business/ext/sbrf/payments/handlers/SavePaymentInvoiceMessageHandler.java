package com.rssl.phizic.business.ext.sbrf.payments.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.basket.InvoiceMessage;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.reminders.ReminderHelper;
import com.rssl.phizic.business.shop.ShopHelper;
import com.rssl.phizic.utils.StringHelper;

/**
 * Сохранение сообщения об оплате инвойса для последуюещего отображения на странице с инвойсами
 * @author niculichev
 * @ created 26.11.14
 * @ $Author$
 * @ $Revision$
 */
public class SavePaymentInvoiceMessageHandler extends BusinessDocumentHandlerBase<BusinessDocument<Department>>
{
	public void process(BusinessDocument<Department> document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		try
		{
			if(document.isMarkReminder())
			{
				TemplateDocument template = ReminderHelper.findReminderById(document.getTemplateId());
				if(template == null)
					return;

				InvoiceMessage.saveMessage(template, InvoiceMessage.Type.payment);
			}
			else if(DocumentHelper.isInternetShopOrAeroflotPayment(document))
			{
				JurPayment payment = (JurPayment) document;

				String orderUID = payment.getOrderUuid();
				if(StringHelper.isEmpty(orderUID))
					return;

				InvoiceMessage.saveMessage(ShopHelper.get().getShopOrder(orderUID), InvoiceMessage.Type.payment);
			}
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}
}
