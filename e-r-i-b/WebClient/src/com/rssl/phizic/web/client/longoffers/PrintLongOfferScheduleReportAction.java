package com.rssl.phizic.web.client.longoffers;

import com.rssl.common.forms.parsers.DateParser;
import com.rssl.phizic.operations.longoffers.GetLongOfferInfoOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.text.ParseException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author osminin
 * @ created 12.10.2010
 * @ $Author$
 * @ $Revision$
 */
public class PrintLongOfferScheduleReportAction extends OperationalActionBase
{
	private DateParser dateParser = new DateParser("dd/MM/yyyy");

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PrintLongOfferScheduleReportForm frm = (PrintLongOfferScheduleReportForm) form;
		GetLongOfferInfoOperation operation = createOperation(GetLongOfferInfoOperation.class);
		operation.initialize(frm.getId());
		frm.setScheduleItems(operation.getScheduleReport(parseDate(frm.getFromDateString()), parseDate(frm.getToDateString())));
		frm.setLongOfferLink(operation.getLongOfferLink());
		frm.setCurrentDepartment(operation.getCurrentDepartment());
		frm.setTopLevelDepartment(operation.getTopLevelDepartment());
		return mapping.findForward(FORWARD_SHOW);
	}

	private Calendar parseDate(String stringDate) throws ParseException
	{
		Calendar date = Calendar.getInstance();
		date.setTime(dateParser.parse(stringDate));
		return date;
	}
}
