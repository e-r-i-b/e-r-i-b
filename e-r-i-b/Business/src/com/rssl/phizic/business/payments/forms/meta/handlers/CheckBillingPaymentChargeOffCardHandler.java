package com.rssl.phizic.business.payments.forms.meta.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderHelper;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.documents.payments.AutoPaymentBase;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.resources.external.ActiveNotVirtualCardsFilter;
import com.rssl.phizic.business.resources.external.ActiveNotVirtualNotCreditCardFilter;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.CardFilter;

/**
 * @author krenev
 * @ created 29.05.2013
 * @ $Author$
 * @ $Revision$
 * ������� �� �������� ����� �������� ������������ �������
 */
public class CheckBillingPaymentChargeOffCardHandler extends BusinessDocumentHandlerBase
{
	public static final ActiveNotVirtualCardsFilter ACTIVE_NOT_VIRTUAL_CARDS_FILTER = new ActiveNotVirtualCardsFilter();
	public static final ActiveNotVirtualNotCreditCardFilter ACTIVE_NOT_VIRTUAL_NOT_CREDIT_CARD_FILTER = new ActiveNotVirtualNotCreditCardFilter();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		ServiceProviderShort serviceProvider;
		CardLink link;

		if (document instanceof AutoPaymentBase)
		{
			AutoPaymentBase autoPayment = (AutoPaymentBase) document;
			try
			{
				serviceProvider = ServiceProviderHelper.getServiceProvider(autoPayment.getReceiverInternalId());
			}
			catch (BusinessException e)
			{
				throw new DocumentException(e);
			}
			if (serviceProvider == null)
				throw new DocumentException("���������� �������� ���������� �����");
			link = (CardLink) autoPayment.getChargeOffResourceLink();
			if (link == null)
				throw new DocumentException("���������� �������� ����� �������� ��� ��������� " + autoPayment.getId());
		}
		else
		{
			if (!(document instanceof JurPayment))
			{
				throw new DocumentException("��������� JurPayment");
			}
			JurPayment payment = (JurPayment) document;
			if (payment.getChargeOffResourceType() != ResourceType.CARD)
			{
				//���������� ������ ������� � ����.
				return;
			}
			try
			{
				serviceProvider = ServiceProviderHelper.getServiceProvider(payment.getReceiverInternalId());
			}
			catch (BusinessException e)
			{
				throw new DocumentException(e);
			}
			if (serviceProvider == null)
			{
				//������ ��������� �� ���� ��� ���������� ���
				return;
			}
			link = (CardLink) payment.getChargeOffResourceLink();
			if (link == null)
			{
				throw new DocumentException("���������� �������� ����� �������� ��� ��������� " + payment.getId());
			}
		}
		CardFilter filter = serviceProvider.isCreditCardSupported() ? ACTIVE_NOT_VIRTUAL_CARDS_FILTER : ACTIVE_NOT_VIRTUAL_NOT_CREDIT_CARD_FILTER;

		if (!filter.accept(link.getCard()))
		{
			throw new DocumentLogicException("�� �� ������ ��������� ������� � ������ �����.");
		}
	}
}
