package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderHelper;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.extendedattributes.AttributableBase;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.config.ESBEribConfig;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizicgate.manager.config.AdaptersConfig;
import com.rssl.phizicgate.manager.routing.Adapter;
import com.rssl.phizicgate.manager.services.IDHelper;

/**
 * ������� ��� ����������� ������������� ������� ������, ��� ������� ����� �������� ���������� �������
 * @author Pankin
 * @ created 17.12.2012
 * @ $Author$
 * @ $Revision$
 */

public class OfflineDelayedBillingPaymentHandler extends OfflineDelayedHandlerBase
{
	public void innerProcess(BusinessDocument document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (getGateDocument(document).getType().equals(CardPaymentSystemPayment.class))
		{
			ServiceProviderShort serviceProvider = null;
			try
			{
				if (document instanceof JurPayment)
					serviceProvider = ServiceProviderHelper.getServiceProvider(((JurPayment) document).getReceiverInternalId());
				else if (document instanceof RurPayment)
				{
					RurPayment rurPayment = (RurPayment) document;
					if (!rurPayment.isServiceProviderPayment())
						throw new DocumentException("�������� ��� ���������");

					serviceProvider = ServiceProviderHelper.getServiceProvider(((RurPayment) document).getReceiverInternalId());
				}
				else
					throw new DocumentException("�������� ��� ���������");
			}
			catch (BusinessException e)
			{
				throw new DocumentException(e);
			}

			AttributableBase attrDoc = (AttributableBase) document;
			// ���� ������ ������������ �� �������� ����������, � ��������� ������������ ������ �������
			if (serviceProvider != null && serviceProvider.isOfflineAvailable())
			{
				AdaptersConfig config = ConfigFactory.getConfig(AdaptersConfig.class);
				String billingAdapterUUID = IDHelper.restoreRouteInfo(serviceProvider.getSynchKey());

				// ���� ���������� ������� ������� ��������
				if (checkSystemByUUID(billingAdapterUUID, attrDoc))
					return;

				Adapter cardTransferAdapter = config.getCardTransfersAdapter();

				// ���� ������� ������� �������� �� IQWave, � ��� ���� ���������� ���� � �����
				if (!cardTransferAdapter.getUUID().equals(billingAdapterUUID))
				{
					if (checkESB(attrDoc))
						return;

					// ���� ������� ������� ����� �������� ����������, �� ��� ���� �������� ������������ ������
					if (checkResource(document.getChargeOffResourceLink(), attrDoc))
						return;
				}
			}
			//����� �� ��������� ���������� ��� ������������� WAY
			if(serviceProvider == null && checkSystemByUUID(ConfigFactory.getConfig(ESBEribConfig.class).getEsbERIBCardSystemId99Way(), attrDoc))
				return;

		}
	}
}
