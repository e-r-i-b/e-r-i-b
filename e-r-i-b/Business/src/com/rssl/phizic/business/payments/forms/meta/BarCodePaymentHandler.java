package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.payments.PaymentsConfig;
import com.rssl.phizic.business.regions.RegionHelper;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.payments.systems.recipients.Field;

import java.util.ArrayList;
import java.util.List;

/**
 * Хендлер для платежей по штрих-коду
 * @author Jatsky
 * @ created 23.09.13
 * @ $Author$
 * @ $Revision$
 */

public class BarCodePaymentHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof JurPayment))
			return;

		JurPayment payment = (JurPayment) document;

		Long providerId = payment.getReceiverInternalId();

		try
		{
			if (providerId != null)
			{
				ServiceProviderShort barCodeProvider = RegionHelper.getBarCodeProvider();
				if (barCodeProvider != null && providerId.equals(barCodeProvider.getId()))
				{
					List<Field> extendedFields = payment.getExtendedFields();
					List<Field> newFields = new ArrayList<Field>();
					for (Field field : extendedFields)
					{
						//Удаление полей, т.к. могут быть проблемы при сохранении их значений в БД (5120 символов не полезут в varchar)
						if (!field.getExternalId().equals(ConfigFactory.getConfig(PaymentsConfig.class).getBarcodeField()) && !field.getExternalId().equals(ConfigFactory.getConfig(PaymentsConfig.class).getTerminalidField()) && !field.getExternalId().equals(ConfigFactory.getConfig(PaymentsConfig.class).getSourceSystemCodeField()))
							newFields.add(field);
					}
					payment.setExtendedFields(newFields);
				}
			}
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
