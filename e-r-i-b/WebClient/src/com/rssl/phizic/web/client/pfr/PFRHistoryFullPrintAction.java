package com.rssl.phizic.web.client.pfr;

import com.rssl.common.forms.parsers.DateParser;
import com.rssl.phizic.operations.pfr.PFRHistoryFullPrintOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Печать справки о видах и размерах пенсий
 * @author Jatsky
 * @ created 30.10.13
 * @ $Author$
 * @ $Revision$
 */

public class PFRHistoryFullPrintAction extends OperationalActionBase
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PFRHistoryFullPrintOperation op = createOperation(PFRHistoryFullPrintOperation.class);
		PFRHistoryFullPrintForm frm = (PFRHistoryFullPrintForm) form;
		Calendar fromDate = DateHelper.getCurrentDate();
		Calendar toDate = DateHelper.getCurrentDate();
		if ("month".equals(frm.getTypePeriod()))
		{
			fromDate.add(Calendar.MONTH, -1);
		}
		else
		{
			fromDate = DateHelper.toCalendar(new DateParser("dd/MM/yyyy").parse(frm.getFromDateString()));
			toDate = DateHelper.toCalendar(new DateParser("dd/MM/yyyy").parse(frm.getToDateString()));
		}

		op.initialize(frm.getFromResource());

		frm.setClient(op.getClient());
		frm.setAccount(op.getAccount());
		frm.setCard(op.getCard());
		if (op.getAccount() != null && fromDate != null && toDate != null)
			frm.setAccountAbstract(op.getAbstract(fromDate, toDate));
		return mapping.findForward(FORWARD_START);
	}
}
