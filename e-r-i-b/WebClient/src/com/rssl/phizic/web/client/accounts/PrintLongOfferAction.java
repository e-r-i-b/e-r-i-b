package com.rssl.phizic.web.client.accounts;

import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.operations.account.ShowLongOfferOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.phizic.logging.operations.CompositeLogParametersReader;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA. User: Omeliyanchuk Date: 07.08.2006 Time: 11:22:52 To change this template use
 * File | Settings | File Templates.
 */
public class PrintLongOfferAction extends OperationalActionBase
{
	public static final String PRINT_COMMAND = "print";

	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	                           HttpServletResponse response) throws Exception
	{
		PrintLongOfferForm frm = (PrintLongOfferForm) form;

		ShowLongOfferOperation operation;
        operation = createOperation(ShowLongOfferOperation.class);

		try
		{
			operation.setStartDateString(request.getParameter("fromDateString"));
			operation.setEndDateString(request.getParameter("toDateString"));
			operation.setOfferType( request.getParameter("type") );
			operation.setAccountList( request.getParameterValues("sel") );
			frm.setHTML( operation.buildLongOfferHTMLs() );

			addLogParameters(new CompositeLogParametersReader(
						new SimpleLogParametersReader("Начальная дата", request.getParameter("fromDateString")),
						new SimpleLogParametersReader("Конечная дата", request.getParameter("toDateString")),
						new SimpleLogParametersReader("Тип", request.getParameter("type"))
					));
		}
		catch (ParseException ex)
		{
			return goWithError(mapping, frm, "Неверный формат даты");
		}
		catch (GateLogicException ex)
		{
			return goWithError(mapping, frm, ex.getMessage());
		}

		frm.setIsValid(true);
		return mapping.findForward(PRINT_COMMAND);
	}

	public ActionForward goWithError(ActionMapping mapping, PrintLongOfferForm frm, String message)
	{
		frm.setMessage(message);
		frm.setIsValid(false);
		return mapping.findForward(PRINT_COMMAND);
	}

}

