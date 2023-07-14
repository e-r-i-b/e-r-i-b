package com.rssl.phizic.test.webgate.esberib.utils;

import com.rssl.phizic.test.webgate.esberib.generated.*;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizicgate.esberibgate.documents.PaymentsRequestHelper;

import java.util.Calendar;

/**
 * @author Ismagilova
 * @ created 16.12.2012
 * @ $Author$
 * @ $Revision$
 */

public class IMAPaymentResponseHelper extends BaseResponseHelper
{
	private static int counter = 0;

	public IFXRs_Type createCardToIMAAddRs(IFXRq_Type ifxRq)
	{
		CardToIMAAddRq_Type cardToIMAAddRq =  ifxRq.getCardToIMAAddRq();
		CardToIMAAddRs_Type cardToIMAAddRs = new CardToIMAAddRs_Type();

		Status_Type status = new Status_Type();
		counter++;
		if (counter % 10 == 5)
		{
			status.setStatusCode(500);
			status.setStatusDesc("“естова€ заглушка отказывает каждый 10 платеж");
		}
		else
		{
			status.setStatusCode(0);
		}
		cardToIMAAddRs.setOperUID(cardToIMAAddRq.getOperUID());
		cardToIMAAddRs.setRqTm(XMLDatatypeHelper.formatDateTimeWithoutTimeZone(Calendar.getInstance()));
		cardToIMAAddRs.setRqUID(PaymentsRequestHelper.generateUUID());
		cardToIMAAddRs.setStatus(status);

		IFXRs_Type ifxRs_type = new IFXRs_Type();
		ifxRs_type.setCardToIMAAddRs(cardToIMAAddRs);
		return ifxRs_type;
	}

	public IFXRs_Type createIMAToCardAddRs(IFXRq_Type ifxRq)
	{
		IMAToCardAddRq_Type imaToCardAddRq =  ifxRq.getIMAToCardAddRq();
		IMAToCardAddRs_Type imaToCardAddRs = new IMAToCardAddRs_Type();

		Status_Type status = new Status_Type();
		counter++;
		if (counter % 10 == 5)
		{
			status.setStatusCode(500);
			status.setStatusDesc("“естова€ заглушка отказывает каждый 10 платеж");
		}
		else
		{
			status.setStatusCode(0);
		}
		imaToCardAddRs.setOperUID(imaToCardAddRq.getOperUID());
		imaToCardAddRs.setRqTm(XMLDatatypeHelper.formatDateTimeWithoutTimeZone(Calendar.getInstance()));
		imaToCardAddRs.setRqUID(PaymentsRequestHelper.generateUUID());
		imaToCardAddRs.setStatus(status);

		IFXRs_Type ifxRs_type = new IFXRs_Type();
		ifxRs_type.setIMAToCardAddRs(imaToCardAddRs);
		return ifxRs_type;

	}
}
