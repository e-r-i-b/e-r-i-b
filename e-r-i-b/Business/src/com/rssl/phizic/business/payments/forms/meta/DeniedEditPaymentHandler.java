package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderHelper;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;
import com.rssl.phizic.business.documents.payments.JurPayment;

/**
 * �������, ����������� �������������� �������
 * @author niculichev
 * @ created 12.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class DeniedEditPaymentHandler extends BusinessDocumentHandlerBase
{
	private static final String PROVIDER_UNSUPPORT_EDIT_PAYMENT = "���������� ��������� c id = %d �� ������������ �������������� �������";

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if(document instanceof JurPayment)
		{
			checkJurPayment((JurPayment) document);
		}
	}

	private void checkJurPayment(JurPayment payment) throws DocumentException
	{
		// ��� �������������� ������������ ������� ���
		if(payment.isLongOffer())
			return;

		try
		{
			ServiceProviderShort serviceProvider = ServiceProviderHelper.getServiceProvider(payment.getReceiverInternalId());

			// ���� ������ � ����� ���������� �� ���� ��������� �������
			if(serviceProvider == null || !(serviceProvider.getKind().equals("B")))
				return;

			if(!serviceProvider.isEditPaymentSupported())
				throw new DocumentException(String.format(PROVIDER_UNSUPPORT_EDIT_PAYMENT, serviceProvider.getId()));
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
