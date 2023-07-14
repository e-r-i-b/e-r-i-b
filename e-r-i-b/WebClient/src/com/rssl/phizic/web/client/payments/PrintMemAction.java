package com.rssl.phizic.web.client.payments;

import com.rssl.phizic.business.ext.sbrf.payments.forms.MemOrder;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.operations.payment.PrintMemOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.logging.operations.CompositeLogParametersReader;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Omeliyanchuk
 * @ created 28.07.2006
 * @ $Author$
 * @ $Revision$
 */

public class PrintMemAction extends OperationalActionBase
{
    private static final String REDIRECT_METHOD    = "success";
	private static final String FAILURE_METHOD    = "failed";

	protected Map<String, String> getKeyMethodMap()
    {
	    return new HashMap<String, String>();
    }

    public ActionForward start(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request, HttpServletResponse response)throws Exception
    {
        return success( mapping, form, request, response);
    }

    public ActionForward success(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)throws Exception
    {
        PrintMemForm printForm = ((PrintMemForm)form);

	    PrintMemOperation operation = createOperation(PrintMemOperation.class);

        Long id = printForm.getId();

		operation.setPayment(id);
		try
		{
			MemOrder order = operation.buildMemOrder();
			PersonData data = PersonContext.getPersonDataProvider().getPersonData();
            printForm.setPerson( data.getPerson() );
			printForm.setOrder(order);

			addLogParameters(new CompositeLogParametersReader(
						new BeanLogParemetersReader("Информация о клиенте", data.getPerson()),
						new BeanLogParemetersReader("Обрабатываемая сущность", order)
					));
		}
		catch(BusinessLogicException ex)
		{
			ActionMessages actionErrors = new ActionMessages();
            actionErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(ex.getMessage(), false));
            saveErrors(request, actionErrors);
			return mapping.findForward(FAILURE_METHOD);
		}

		return mapping.findForward(REDIRECT_METHOD);
    }

}
