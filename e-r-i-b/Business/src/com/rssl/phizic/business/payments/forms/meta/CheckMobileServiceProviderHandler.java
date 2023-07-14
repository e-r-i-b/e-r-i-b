package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.config.mobile.MobileApiConfig;
import com.rssl.phizic.config.ConfigFactory;

/**
 * @author gulov
 * @ created 28.08.2012
 * @ $Authors$
 * @ $Revision$
 */
public class CheckMobileServiceProviderHandler extends BusinessDocumentHandlerBase
{
	private static final ServiceProviderService service = new ServiceProviderService();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof JurPayment))
			return;
		JurPayment payment = (JurPayment) document;
		Long providerId = payment.getReceiverInternalId();
		/**
		 * � ������� ���� ���:
		 * - ������� ����� � �����
		 * - ������� ����� �� ������ ����� ���������� �������, ������������� � ��. (�������� ������ � �� ����)
		 * �� id �� �������� �� �����, ������� �������� �� ���������.
		 */
		if (providerId == null)
			return;
		ServiceProviderShort spb = findProviderById(providerId);
		if (spb == null)
			throw new DocumentException("�� ������ ��������� ����� � ��������������� " + providerId);
		if (!(spb.getKind().equals("B")))
			throw new DocumentException("���������� � ��������������� " + providerId + " �� �������� ���������� ����������� �����");
		boolean isLongOffer = payment.isLongOffer(); //�������� ������� ����������� (CreateAutoSubscription) ?
		if ((!isLongOffer && (!ConfigFactory.getConfig(MobileApiConfig.class).isTemplateIgnoreProviderAvailability() && !spb.isAvailablePaymentsForMApi()))
				|| (isLongOffer && !spb.isAutoPaymentSupportedInApi()))
			throw new DocumentLogicException("��������� ���������� ��� ���������� ����������.");
	}

	private ServiceProviderShort findProviderById(Long id) throws DocumentException
	{
		try
		{
			return service.findShortProviderById(id);
		}
		catch (BusinessException e)
		{
			throw new DocumentException("������ ��� ������ ���������� � ��������������� " + id, e);
		}
	}
}
