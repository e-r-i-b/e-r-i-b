package com.rssl.phizic.business.documents.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.TypeOfPayment;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.resources.external.AutoSubscriptionLink;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoPayStatusType;
import com.rssl.phizic.gate.payments.longoffer.CloseCardPaymentSystemPaymentLongOffer;
import com.rssl.phizic.gate.payments.longoffer.EmployeeCloseCardPaymentSystemPaymentLongOffer;
import org.w3c.dom.Document;

/**
 * Заявка на закрытие автоплатежа
 *
 * @author khudyakov
 * @ created 24.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class CloseAutoSubscriptionPayment extends AutoSubscriptionPaymentBase implements CloseCardPaymentSystemPaymentLongOffer, EmployeeCloseCardPaymentSystemPaymentLongOffer
{
	public Class<? extends GateDocument> getType()
	{
		if (getCreatedEmployeeLoginId() != null)
			return EmployeeCloseCardPaymentSystemPaymentLongOffer.class;

		return CloseCardPaymentSystemPaymentLongOffer.class;
	}

	public void initialize(Document document, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		super.initialize(document, messageCollector);

		try
		{
			AutoSubscriptionLink subscriptionLink = findAutoSubscriptionLink();
			AutoPayStatusType statusType = subscriptionLink.getAutoPayStatusType();

			if(statusType != AutoPayStatusType.Confirmed
					&& statusType != AutoPayStatusType.ErrorRegistration
					&& statusType != AutoPayStatusType.Active
					&& statusType != AutoPayStatusType.Paused)
				throw new DocumentException("Отключение автоплатежа разрешено только, если он находится в статусе " +
						"Прошел регистрацию, Не зарегистрирован, Активный или Приостановлен");

			storeAutoSubscriptionData(subscriptionLink);
			storeFromResourceData(subscriptionLink.getCardLink());
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	public TypeOfPayment getTypeOfPayment()
	{
		return TypeOfPayment.NOT_PAYMENT_OPEATION;
	}

	public boolean needUserLimit() throws DocumentException
	{
		return false;
	}
}
