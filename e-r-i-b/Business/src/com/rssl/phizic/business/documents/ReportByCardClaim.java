package com.rssl.phizic.business.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.TypeOfPayment;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.gate.documents.GateDocument;
import org.w3c.dom.Document;

import java.util.Calendar;

/**
 * «ав€вка на получении выписки по карте на e-mail.
 *
 * @author bogdanov
 * @ created 28.05.15
 * @ $Author$
 * @ $Revision$
 */

public class ReportByCardClaim extends AbstractPaymentDocument implements com.rssl.phizic.gate.payments.ReportByCardClaim
{
	public static final String E_MAIL = "e-mail";
	public static final String REPORT_FORMAT = "report-format";
	public static final String REPORT_LANG = "report-lang";
	public static final String REPORT_ORDER_TYPE = "report-order-type";
	public static final String REPORT_START_DATE = "report-start-date";
	public static final String REPORT_END_DATE = "report-end-date";
	public static final String REPORT_START_DATE_FORMATED = "report-start-date-formated";
	public static final String REPORT_END_DATE_FORMATED = "report-end-date-formated";
	public static final String REPORT_CARD = "report-card-number";
	public static final String REPORT_CARD_CONTRACT = "report-card-contract";
	public static final String REPORT_CARD_NAME = "report-card-name";
	public static final String REPORT_CARD_ID = "report-card-id";

	@Override
	public Class<? extends GateDocument> getType()
	{
		return com.rssl.phizic.gate.payments.ReportByCardClaim.class;
	}

	public TypeOfPayment getTypeOfPayment()
	{
		return TypeOfPayment.NOT_PAYMENT_OPEATION;
	}

	public String getEmailAddress()
	{
		return getNullSaveAttributeStringValue(E_MAIL);
	}

	public String getReportFormat()
	{
		return getNullSaveAttributeStringValue(REPORT_FORMAT);
	}

	public String getReportLang()
	{
		return getNullSaveAttributeStringValue(REPORT_LANG);
	}

	public String getReportOrderType()
	{
		return getNullSaveAttributeStringValue(REPORT_ORDER_TYPE);
	}

	public Calendar getReportStartDate()
	{
		return getNullSaveAttributeCalendarValue(REPORT_START_DATE);
	}

	public Calendar getReportEndDate()
	{
		return getNullSaveAttributeCalendarValue(REPORT_END_DATE);
	}

	public String getReportStartDateFormated()
	{
		return getNullSaveAttributeStringValue(REPORT_START_DATE_FORMATED);
	}

	public String getReportEndDateFormated()
	{
		return getNullSaveAttributeStringValue(REPORT_END_DATE_FORMATED);
	}

	public String getCardNumber()
	{
		return getNullSaveAttributeStringValue(REPORT_CARD);
	}

	public String getContractNumber()
	{
		return getNullSaveAttributeStringValue(REPORT_CARD_CONTRACT);
	}

	public String getCardName()
	{
		return getNullSaveAttributeStringValue(REPORT_CARD_NAME);
	}

	public Long getCardId()
	{
		return getNullSaveAttributeLongValue(REPORT_CARD_ID);
	}

	protected void readFromDom(Document document, InnerUpdateState updateState, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		super.readFromDom(document, updateState, messageCollector);
		setChargeOffAccount(getNullSaveAttributeStringValue(REPORT_CARD));
	}
}
