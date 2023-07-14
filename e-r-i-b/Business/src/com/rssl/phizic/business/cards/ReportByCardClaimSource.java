package com.rssl.phizic.business.cards;

import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.ReportByCardClaim;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.payments.source.NewDocumentSource;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.utils.DateHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Данные для заполнения документа на заявку на выписку по карте на e-mail.
 *
 * @author bogdanov
 * @ created 01.06.15
 * @ $Author$
 * @ $Revision$
 */

public class ReportByCardClaimSource implements DocumentSource
{
	private final DocumentSource delegate;
	private static final String ER_REPORT = "ER";
	private static final String IR_REPORT = "IR";

	public ReportByCardClaimSource(CardLink cardLink, String email, String format, String lang, String fillReport, Calendar fromDate, Calendar toDate) throws BusinessLogicException, BusinessException
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(ReportByCardClaim.E_MAIL, email);
		map.put(ReportByCardClaim.REPORT_CARD, cardLink.getNumber());
		map.put(ReportByCardClaim.REPORT_CARD_NAME, cardLink.getName());
		map.put(ReportByCardClaim.REPORT_CARD_CONTRACT, cardLink.getContractNumber());
		map.put(ReportByCardClaim.REPORT_FORMAT, format);
		map.put(ReportByCardClaim.REPORT_LANG, lang);
		map.put(ReportByCardClaim.REPORT_ORDER_TYPE, fillReport);
		map.put(ReportByCardClaim.REPORT_START_DATE_FORMATED, fillReport.equals(ER_REPORT) ? new SimpleDateFormat("MMMM yyyy").format(fromDate.getTime()) : DateHelper.formatDateWithMonthString(fromDate));
		map.put(ReportByCardClaim.REPORT_END_DATE_FORMATED, DateHelper.formatDateWithMonthString(toDate));
		if (ER_REPORT.equals(fillReport))
		{
			fromDate.set(Calendar.DAY_OF_MONTH, cardLink.getCard().getIssueDate().get(Calendar.DAY_OF_MONTH));
			map.put(ReportByCardClaim.REPORT_START_DATE, DateHelper.toXMLDateFormat(DateHelper.toDate(fromDate)));
			map.put(ReportByCardClaim.REPORT_END_DATE, DateHelper.toXMLDateFormat(DateHelper.toDate(fromDate)));
		}
		else if (IR_REPORT.equals(fillReport))
		{
			map.put(ReportByCardClaim.REPORT_START_DATE, DateHelper.toXMLDateFormat(DateHelper.toDate(fromDate)));
			map.put(ReportByCardClaim.REPORT_END_DATE, DateHelper.toXMLDateFormat(DateHelper.toDate(toDate)));
		}
		map.put(ReportByCardClaim.REPORT_CARD_ID, Long.toString(cardLink.getId()));
		FieldValuesSource valuesSource = new MapValuesSource(map);
		delegate = new NewDocumentSource("ReportByCardClaim", valuesSource, CreationType.internet, CreationSourceType.ordinary);
		((ReportByCardClaim) delegate.getDocument()).setChargeOffAccount(cardLink.getNumber());
		((ReportByCardClaim) delegate.getDocument()).setChargeOffResourceType(CardLink.class.getName());
	}

	public BusinessDocument getDocument()
	{
		return delegate.getDocument();
	}

	public Metadata getMetadata()
	{
		return delegate.getMetadata();
	}
}
