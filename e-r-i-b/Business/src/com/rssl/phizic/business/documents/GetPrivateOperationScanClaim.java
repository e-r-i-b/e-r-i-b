package com.rssl.phizic.business.documents;

import com.rssl.common.forms.doc.TypeOfPayment;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.gate.documents.GateDocument;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * Заявка на получение электронного документа на электронную почту
 * @author komarov
 * @ created 15.04.2014
 * @ $Author$
 * @ $Revision$
 */
public class GetPrivateOperationScanClaim extends GateExecutableDocument implements com.rssl.phizic.gate.claims.GetPrivateOperationScanClaim
{
	public static final String FORM_NAME                          = GetPrivateOperationScanClaim.class.getSimpleName();
	public static final String FIO_ATTRIBUTE_NAME                 = "client-fio";
	public static final String ACCOUNT_NUMBER_ATTRIBUTE_NAME      = "account-number";
	public static final String AMOUNT_ATTRIBUTE_NAME              = "amount";
	public static final String DEBIT_ATTRIBUTE_NAME               = "debit";
	public static final String AUTHORISATION_CODE_ATTRIBUTE_NAME  = "authorisation-code";
	public static final String E_MAIL_ATTRIBUTE_NAME              = "e-mail";
	public static final String OPERATION_SEND_DATE_ATTRIBUTE_NAME = "send-operation-date";

	public TypeOfPayment getTypeOfPayment()
	{
		return TypeOfPayment.NOT_PAYMENT_OPEATION;
	}

	public Class<? extends GateDocument> getType()
	{
		return com.rssl.phizic.gate.claims.GetPrivateOperationScanClaim.class;
	}

	public String getFIO()
	{
		return getNullSaveAttributeStringValue(FIO_ATTRIBUTE_NAME);
	}

	public String getAccountNumber()
	{
		return getNullSaveAttributeStringValue(ACCOUNT_NUMBER_ATTRIBUTE_NAME);
	}

	public BigDecimal getAmount()
	{
		return new BigDecimal(getNullSaveAttributeStringValue(AMOUNT_ATTRIBUTE_NAME));
	}

	public Calendar getSendOperationDate()
	{
		return getNullSaveAttributeCalendarValue(OPERATION_SEND_DATE_ATTRIBUTE_NAME);
	}

	public Long getAuthorisationCode()
	{
		return getNullSaveAttributeLongValue(AUTHORISATION_CODE_ATTRIBUTE_NAME);
	}

	public String getEMail()
	{
		return getNullSaveAttributeStringValue(E_MAIL_ATTRIBUTE_NAME);
	}

	public Boolean isDebit()
	{
		return Boolean.parseBoolean(getNullSaveAttributeStringValue(DEBIT_ATTRIBUTE_NAME));
	}
}
