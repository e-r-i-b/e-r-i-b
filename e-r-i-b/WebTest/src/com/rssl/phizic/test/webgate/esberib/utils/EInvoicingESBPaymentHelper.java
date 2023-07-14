package com.rssl.phizic.test.webgate.esberib.utils;

import com.rssl.phizic.test.webgate.esberib.generated.*;
import com.rssl.phizicgate.esberibgate.documents.PaymentsRequestHelper;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Хелпер для формирования ответа на запрос подготовки биллингового платежа на оплату услуг поставщика Einvoicing
 * @author gladishev
 * @ created 26.06.2015
 * @ $Author$
 * @ $Revision$
 */

public class EInvoicingESBPaymentHelper
{
	public static void preparePayment(BillingPayPrepRq_Type request, BillingPayPrepRs_Type responce)
	{
		responce.setRqUID(PaymentsRequestHelper.generateUUID());
		responce.setRqTm(PaymentsRequestHelper.generateRqTm());
		responce.setOperUID(request.getOperUID());
		responce.setSystemId(request.getSystemId());
		responce.setStatus(new Status_Type(0, null, null, null));
		responce.setRecipientRec(preparePymentRecipientRec(request.getRecipientRec()));
		responce.setMadeOperationId("MadeOperationId");
		responce.setCommission(new BigDecimal("1.23"));
		responce.setCommissionCur("RUR");
	}

	private static RecipientRec_Type preparePymentRecipientRec(RecipientRec_Type recipientRec)
	{
		Requisite_Type result = new Requisite_Type();
		result.setNameVisible("Уникальный номер платежа(CУИП)");
		result.setNameBS("S19682282111A1");
		result.setDescription("Уникальный идентификатор платежа");
		result.setType("string");
		result.setNumberPrecision(BigInteger.valueOf(0L));
		result.setIsRequired(false);
		result.setIsKey(false);
		result.setIsSum(false);
		result.setIsEditable(false);
		result.setIsForBill(true);
		result.setIsVisible(false);
		result.setIncludeInSMS(false);
		result.setSaveInTemplate(false);
		result.setHideInConfirmation(false);
		AttributeLength_Type attributeLength = new AttributeLength_Type();
		attributeLength.setMaxLength(BigInteger.valueOf(0L));
		attributeLength.setMinLength(BigInteger.valueOf(0L));
		result.setAttributeLength(attributeLength);
		Requisite_Type[] oldRequisites = recipientRec.getRequisites();
		int requisitesSize = (oldRequisites == null) ? 0 : oldRequisites.length;
		List<Requisite_Type> newRequisites = new ArrayList<Requisite_Type>(requisitesSize + 1);
		if (oldRequisites != null)
		{
			for (Requisite_Type requisite : oldRequisites)
			{
				newRequisites.add(requisite);
			}
		}
		newRequisites.add(result);
		recipientRec.setRequisites(newRequisites.toArray(new Requisite_Type[newRequisites.size()]));
		return recipientRec;
	}
}
