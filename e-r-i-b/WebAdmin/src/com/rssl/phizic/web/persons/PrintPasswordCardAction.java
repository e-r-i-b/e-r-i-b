package com.rssl.phizic.web.persons;

import com.rssl.phizic.operations.passwordcards.*;
import com.rssl.phizic.web.actions.*;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.auth.passwordcards.PasswordCard;

import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


/**
 * @author Kidyaev
 * @ created 29.09.2005
 * @ $Author$
 * @ $Revision$
 */

public class PrintPasswordCardAction extends OperationalActionBase
{
	private static final String FORWARD_START     = "Start";
	private static final String FORWARD_CLOSE     = "Close";
	private static final String FORWARD_PASSCARDS = "PassCards";

    protected Map<String, String> getKeyMethodMap()
    {
        Map<String,String> map = new HashMap<String, String>();
        map.put("button.close", "cancel");
        return map;
    }

   public ActionForward cancel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        String person = request.getParameter("person");
        if (person.length()==0) {
            return mapping.findForward(FORWARD_PASSCARDS);
        }
        else {
            return PersonUtils.redirectWithPersonId( mapping.findForward(FORWARD_CLOSE),request);
        }
    }

    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        PrintPassworCardForm frm = (PrintPassworCardForm) form;
        PasswordCard card = null;
        List passwords = null;

        PrintUnassignedPasswordCardOperation operation = createOperation(PrintUnassignedPasswordCardOperation.class);
        card = operation.initialize(frm.getCardId());
        passwords = operation.getPaswords();

        frm.setPasswords(passwords);
        frm.setCard(card);

        if (card == null || passwords == null || passwords.size() == 0)
        {
            ActionMessages msgs = new ActionMessages();
            msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("com.rssl.phizic.web.security.error.noactive.card"));
            saveErrors(request, msgs);
        }

	    addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", card));
	    
        return mapping.findForward(FORWARD_START);
    }

}
