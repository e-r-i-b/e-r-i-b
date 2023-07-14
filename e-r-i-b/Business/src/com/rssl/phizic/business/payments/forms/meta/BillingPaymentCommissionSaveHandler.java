package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderHelper;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author bogdanov
 * @ created 12.04.2012
 * @ $Author$
 * @ $Revision$
 *
 * ������� ��� ���������� �������� � ������ ��������� ��� �� ����������.
 */

public class BillingPaymentCommissionSaveHandler extends DefaultCommissionSaveHandler
{
	private static final ServiceProviderService providerService = new ServiceProviderService();

	private static final String DEFAULT_MAY_COMMISSION_MESSAGE = "�� ������ �������� ����� ��������� �������� � ������������ � �������� �����. " +
			"����� �������� �� ������ ���������� <a href='http://www.sberbank.ru/common/img/uploaded/files/pdf/person/bank_cards/Perevody__Tarify.pdf' class='paperEnterLink' target='_blank'>�����</a>.";
	private static final String DEFAULT_MAY_COMMISSION_MESSAGE_MOBILE = "�� ������ �������� ����� ��������� �������� � ������������ � �������� �����. " +
			"����� �������� �� ������ ���������� �� ����� �����.";
	private static final String ZERO_COMMISSION_MASSEGE = "�� ���������� ������ �������� �������� �� ���������.";

	@Override
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof JurPayment) && !(document instanceof RurPayment && ((RurPayment)document).isServiceProviderPayment()))
			throw new DocumentException("document ������ ���� JurPayment ��� RurPayment");

		super.process(document, stateMachineEvent);

		try
		{
			RurPayment payment = (RurPayment) document;

			// ���� ��������� ����������, �� ���������� � ������� ��������� � ���� �����������
			if (payment.isLongOffer())
				return;

			ServiceProviderShort provider = ServiceProviderHelper.getServiceProvider(payment.getReceiverInternalId());
			//���� ���� ��������� � �������� � ����������, �� ���������� ������ ��� ����������.
			if (provider != null && !StringHelper.isEmpty(provider.getCommissionMessage()))
			{
				stateMachineEvent.addMessage(provider.getCommissionMessage());
				//������ ��������� ��������� (� ���, ��� ��������� �� ������ � �������� �������) �������� �� �����.
				return;
			}

			//���� �������� �� ������, �� ������� ����������� ��������� � ����������� �������� ��������.
			if (payment.getCommission() == null)
			{
				stateMachineEvent.addMessage(ApplicationUtil.isApi() ? DEFAULT_MAY_COMMISSION_MESSAGE_MOBILE : DEFAULT_MAY_COMMISSION_MESSAGE);
				return;
			}

			//���� �������� �������, �� �������� �� ���� ������������.
			if (payment.getCommission().isZero())
				stateMachineEvent.addMessage(ZERO_COMMISSION_MASSEGE);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
