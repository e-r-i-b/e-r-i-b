package com.rssl.phizic.business.documents.payments;

import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.payments.systems.contact.ContactPersonalPayment;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.common.forms.doc.TypeOfPayment;

import java.text.ParseException;
import java.util.Calendar;

/**
 * @author Krenev
 * @ created 16.10.2008
 * @ $Author$
 * @ $Revision$
 */
public class ContactPayment extends AbstractPaymentDocument implements ContactPersonalPayment
{
	public static final String RECEIVER_POINT_CODE = "receiver-bank-code";
	public static final String RECEIVER_ACCOUNT = "receiver-account";
	public static final String RECEIVER_SUR_NAME = "receiver-sur-name";
	public static final String RECEIVER_FIRST_NAME = "receiver-first-name";
	public static final String RECEIVER_PATR_NAME = "receiver-patr-name";
	public static final String RECEIVER_BIRTH_DAY = "receiver-birth-day";
	public static final String ADD_INFORMATION = "add-information";

	public Class<? extends GateDocument> getType()
	{
		return com.rssl.phizic.gate.payments.systems.contact.ContactPayment.class;
	}

	public String getReceiverPointCode()
	{
		return getNullSaveAttributeStringValue(RECEIVER_POINT_CODE);
	}

    public String getReceiverSurName()
	{
		return getNullSaveAttributeStringValue(RECEIVER_SUR_NAME);
	}

	public String getReceiverFirstName()
	{
		return getNullSaveAttributeStringValue(RECEIVER_FIRST_NAME );
	}

	public String getReceiverPatrName()
	{
		return getNullSaveAttributeStringValue(RECEIVER_PATR_NAME);
	}

	public Calendar getReceiverBornDate()
	{
		String taxDocumentDate = getNullSaveAttributeStringValue(RECEIVER_BIRTH_DAY );

		if (taxDocumentDate != null)
		{
           try
		   {
		      return DateHelper.parseCalendar(taxDocumentDate);
		   }
		   catch (ParseException e)
		   {
		      return null;
		   }
		}

		return null;
	}

	public String getGround()
	{
		return getNullSaveAttributeStringValue(ADD_INFORMATION );
	}
	public void setTransitAccount(String transitAccount)
	{
		setNullSaveAttributeStringValue(RECEIVER_ACCOUNT, transitAccount);
	}

	public String getTransitAccount()
	{
		return getNullSaveAttributeStringValue(RECEIVER_ACCOUNT); 
	}

	public TypeOfPayment getTypeOfPayment()
	{
		return TypeOfPayment.EXTERNAL_PAYMENT_OPERATION;
	}
}
