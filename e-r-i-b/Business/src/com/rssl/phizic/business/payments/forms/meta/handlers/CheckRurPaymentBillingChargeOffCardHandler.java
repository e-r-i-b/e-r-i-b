package com.rssl.phizic.business.payments.forms.meta.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderHelper;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.resources.external.CardFilter;
import com.rssl.phizic.business.resources.external.CardLink;
import static com.rssl.phizic.business.payments.forms.meta.handlers.CheckBillingPaymentChargeOffCardHandler.ACTIVE_NOT_VIRTUAL_CARDS_FILTER;
import static com.rssl.phizic.business.payments.forms.meta.handlers.CheckBillingPaymentChargeOffCardHandler.ACTIVE_NOT_VIRTUAL_NOT_CREDIT_CARD_FILTER;
/**
 * @author gladishev
 * @ created 14.11.2014
 * @ $Author$
 * @ $Revision$
 * ������� �� �������� ����� �������� ������������ ������� (������������ ��� ������� � ������.������ � ����� p2p-��������)
 */

public class CheckRurPaymentBillingChargeOffCardHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		RurPayment payment = (RurPayment) document;

		ServiceProviderShort serviceProvider = null;
		try
		{
			serviceProvider = ServiceProviderHelper.getServiceProvider(payment.getReceiverInternalId());
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		if (serviceProvider == null)
			throw new DocumentException("�� ������ ���������");

		CardLink link = (CardLink) payment.getChargeOffResourceLink();
		if (link == null)
			throw new DocumentException("���������� �������� ����� �������� ��� ��������� " + payment.getId());

		CardFilter filter = serviceProvider.isCreditCardSupported() ? ACTIVE_NOT_VIRTUAL_CARDS_FILTER : ACTIVE_NOT_VIRTUAL_NOT_CREDIT_CARD_FILTER;

		if (!filter.accept(link.getCard()))
		{
			throw new DocumentLogicException("�� �� ������ ��������� ������� � ������ �����.");
		}
	}
}
