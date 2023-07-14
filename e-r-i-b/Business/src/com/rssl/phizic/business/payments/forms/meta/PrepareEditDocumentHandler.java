package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderHelper;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.regions.RegionHelper;
import com.rssl.phizic.gate.payments.systems.AbstractPaymentSystemPayment;

import java.util.Collections;

import static com.rssl.common.forms.FormConstants.*;

/**
 * ��� �������� �� �������������� ��������� ��������� ������� ���������,
 * ������� ������������� � ������������� ������� �� ������� �������
 * @author niculichev
 * @ created 04.03.2011
 * @ $Author$
 * @ $Revision$
 */
public class PrepareEditDocumentHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if(document instanceof GateExecutableDocument)
		{
			// ������� ��������
			GateExecutableDocument executableDocument = (GateExecutableDocument) document;
			executableDocument.setCommission(null);
			executableDocument.setWriteDownOperations(Collections.EMPTY_LIST);

			// ������� ������� �������������
			executableDocument.setExternalId(null);
		}

		//������� ������������� ������� �� ������� �������
		if (document instanceof AbstractPaymentSystemPayment)
		{
			AbstractPaymentSystemPayment abstractPaymentSystemPayment = (AbstractPaymentSystemPayment) document;
			abstractPaymentSystemPayment.setIdFromPaymentSystem(null);

			if (document instanceof JurPayment)
			{
				((JurPayment) document).removeAttribute(JurPayment.IS_CHANGE_KEY_FIELDS_ATTRIBUTE_NAME);
				/*
				 * ������������ ������������ �� ������ �� ����� ����. ����� ������ �� ����� ���� - �������� �������������� ���� �� �����.
				 */
				try
				{
					ServiceProviderShort provider = ServiceProviderHelper.getServiceProvider(((JurPayment) document).getReceiverInternalId());
					ServiceProviderShort regionProvider = RegionHelper.getBarCodeProvider();
					if (provider != null && regionProvider != null && regionProvider.getSynchKey().equals(provider.getSynchKey()))
						return;
				}
				catch (BusinessException e)
				{
					throw new DocumentException(e);
				}
			}

			// ������� ����������� ��������, ���� �� ������� ��������� ��������.
			boolean removeExtendedFields = true;
			if (document instanceof BusinessDocument)
			{
				String formName = ((BusinessDocument)document).getFormName();
				removeExtendedFields = !(
						FNS_PAYMENT_FORM.equals(formName) ||
						EXTERNAL_PROVIDER_PAYMENT_FORM.equals(formName) ||
						AIRLINE_RESERVATION_PAYMENT_FORM.equals(formName)
				);
			}
			if (removeExtendedFields)
				abstractPaymentSystemPayment.setExtendedFields(null);
		}
	}
}
