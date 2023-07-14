package com.rssl.phizic.web.persons;

/**
 * @author Omeliyanchuk
 * @ created 22.08.2006
 * @ $Author$
 * @ $Revision$
 */

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.operations.person.PrintRecessionOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PrintRecessionAction  extends OperationalActionBase
{
	private static final String FORWARD_START = "Start";

	protected Map<String,String> getKeyMethodMap()
    {
        return new HashMap<String, String>();
    }

    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        PrintRecessionForm frm = (PrintRecessionForm) form;

        PrintRecessionOperation operation  =  createOperation(PrintRecessionOperation.class);
      	ActivePerson aperson = operation.getPerson(frm.getPerson());

		Date currentDate = new Date();

		frm.setActivePerson(aperson);
        frm.setCurrentDate(currentDate);
		frm.setIsProlongation( aperson.getProlongationRejectionDate()!=null );

	    addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", aperson));

		return mapping.findForward(FORWARD_START);
    }
}
