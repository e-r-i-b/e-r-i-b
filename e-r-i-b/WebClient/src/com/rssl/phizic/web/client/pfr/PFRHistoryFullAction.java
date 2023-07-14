package com.rssl.phizic.web.client.pfr;

import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.gate.bankroll.AccountAbstract;
import com.rssl.phizic.operations.pfr.PFRHistoryFullOperation;
import com.rssl.phizic.operations.pfr.PFRHistoryFullPrintOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.*;

import java.text.ParseException;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * «апрос справки о видах и размерах пенсий
 * @author Jatsky
 * @ created 29.10.13
 * @ $Author$
 * @ $Revision$
 */

public class PFRHistoryFullAction extends OperationalActionBase
{
	private static final String WINDOW_FORWARD = "Window";
	private static final String ASYNC_PARAM = "async";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.printPFRHistoryFull","printPFRHistoryFull");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PFRHistoryFullForm frm = (PFRHistoryFullForm) form;
		updateForm(frm);
		if(frm.getIsWindow())
			return mapping.findForward(WINDOW_FORWARD);
		else
			return mapping.findForward(FORWARD_START);
	}

	public ActionForward printPFRHistoryFull(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PFRHistoryFullForm frm = (PFRHistoryFullForm)form;
		frm.setFilter("fromResource", frm.getFromResource());
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new MapValuesSource(frm.getFilters()), PFRHistoryFullForm.EDIT_FORM);
		if (!processor.process())
		{
			updateForm(frm);
			saveErrors(request, processor.getErrors());
			if(frm.getIsWindow() && !ASYNC_PARAM.equals(mapping.getParameter()))
				return mapping.findForward(WINDOW_FORWARD);
			else
				return mapping.findForward(FORWARD_START);
		}
		frm.setPrintAbstract(true);
		setPFRRecords(frm);
		updateForm(frm);
		if(frm.getIsWindow() && !ASYNC_PARAM.equals(mapping.getParameter()))
			return mapping.findForward(WINDOW_FORWARD);
		else
			return mapping.findForward(FORWARD_START);
	}

	private void setPFRRecords(PFRHistoryFullForm form) throws BusinessException, ParseException, BusinessLogicException
	{
		PFRHistoryFullPrintOperation op = createOperation(PFRHistoryFullPrintOperation.class);
		Calendar fromDate = DateHelper.getCurrentDate();
		Calendar toDate = DateHelper.getCurrentDate();
		if ("month".equals(form.getFilter("typePeriod")))
		{
			fromDate.add(Calendar.MONTH, -1);
		}
		else
		{
			fromDate = DateHelper.toCalendar(new DateParser("dd/MM/yyyy").parse((String)form.getFilter("fromPeriod")));
			toDate = DateHelper.toCalendar(new DateParser("dd/MM/yyyy").parse((String)form.getFilter("toPeriod")));
		}

		op.initialize(form.getFromResource());

		AccountAbstract accountAbstract = op.getAbstract(fromDate, toDate);
		boolean historyExistCondition = op.getAccount() != null && accountAbstract != null &&
											accountAbstract.getTransactions() != null && accountAbstract.getTransactions().size() > 0;

		if (historyExistCondition)
			form.setAccountHasPFRRecords(true);
	    else
			form.setAccountHasPFRRecords(false);
	}

	public void updateForm(PFRHistoryFullForm frm) throws BusinessException, BusinessLogicException, ParseException
	{
		PFRHistoryFullOperation op = createOperation(PFRHistoryFullOperation.class);
		frm.setChargeOffResources(op.getChargeOffResources());

		Calendar toDate;
		if(frm.getFilter("toPeriod") != null)
			toDate = DateHelper.toCalendar(new DateParser("dd/MM/yyyy").parse((String)frm.getFilter("toPeriod")));
		else
			toDate = DateHelper.getCurrentDate();

		Calendar fromDate;
		if(frm.getFilter("fromPeriod") != null)
			fromDate = DateHelper.toCalendar(new DateParser("dd/MM/yyyy").parse((String)frm.getFilter("fromPeriod")));
		else
			fromDate = DateHelper.toCalendar(DateHelper.add(DateHelper.getCurrentDate().getTime(), 0, -1, 0));

		if(frm.getFilter("fromPeriod") == null || frm.getFilter("toPeriod") == null)
			frm.setFilter("typePeriod", "month");

		frm.setFilter("fromPeriod", fromDate.getTime());
		frm.setFilter("toPeriod", toDate.getTime());
	}
}
