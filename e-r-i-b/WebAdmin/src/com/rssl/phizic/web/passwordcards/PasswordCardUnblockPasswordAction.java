package com.rssl.phizic.web.passwordcards;

import com.rssl.phizic.auth.passwordcards.InvalidPasswordCardStateException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.passwordcards.ChangeUserPasswordCardLockingOperation;
import com.rssl.phizic.operations.passwordcards.InvalidPaswordCardValueException;
import com.rssl.phizic.operations.passwordcards.UnlockManualPasswordCardException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import org.apache.struts.action.*;
import org.apache.struts.util.MessageResources;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Omeliyanchuk
 * @ created 24.11.2006
 * @ $Author$
 * @ $Revision$
 */

public class PasswordCardUnblockPasswordAction extends OperationalActionBase
{
	private static final String FORWARD_SHOW  = "Show";
	private static final String FORWARD_CLOSE = "Close";

    protected Map<String, String> getKeyMethodMap()
    {
        Map<String,String> map = new HashMap<String, String>();
	    map.put("button.unlock", "unlock");
        return map;
    }

    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
	    PasswordCardUnblockPasswordForm         frm       = (PasswordCardUnblockPasswordForm)form;

		ChangeUserPasswordCardLockingOperation operation = createOperation(ChangeUserPasswordCardLockingOperation.class);

		operation.initialize( frm.getCard() );
		operation.setPersonId( frm.getPerson() );
	    initializeForm(frm, operation);

	    addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getCard()));

	    return mapping.findForward(FORWARD_SHOW);
    }

	private void initializeForm(PasswordCardUnblockPasswordForm frm, ChangeUserPasswordCardLockingOperation operation) throws BusinessException
	{
		frm.setUnusedPasswordNumber( operation.getUnusedPasswordNumber() );
		frm.setActivePerson( operation.getPerson() );
		frm.setCardNumber(operation.getCard().getNumber());
	}

	public ActionForward unlock ( ActionMapping mapping, ActionForm form, HttpServletRequest request,
	                              HttpServletResponse response ) throws Exception
	{
		PasswordCardUnblockPasswordForm         frm       = (PasswordCardUnblockPasswordForm)form;
		MessageResources messageResources     = getResources(request, "personsBundle");
		String           msgNum               = "";
        String           motive               = messageResources.getMessage("com.rssl.phizic.web.persons.passwordcard.operation.unblockingText");
        final String     OPERATION_UNBLOCKING = messageResources.getMessage("com.rssl.phizic.web.persons.passwordcard.operation.unblocking");

		ChangeUserPasswordCardLockingOperation operation = createOperation(ChangeUserPasswordCardLockingOperation.class);
        ActionMessages msgs = new ActionMessages();

		operation.initialize( frm.getCard() );
		operation.setPersonId( frm.getPerson() );
        operation.setPassword( frm.getUnblockPassword() );
		operation.setCheckPassword(frm.getUnusedPasswordNumber().toString());

		frm.setActivePerson(operation.getPerson());        
        try
		{
			initializeForm(frm, operation);
			operation.unlock();
		}
		catch (InvalidPasswordCardStateException e)
		{
            if ( StringHelper.isEmpty(msgNum) )
                msgNum = "№: " + e.getCard().getNumber();
            else msgNum += (", " + e.getCard().getNumber());
		}
		catch( UnlockManualPasswordCardException ex)
		{
			String message =
                    messageResources.getMessage("com.rssl.phizic.web.persons.passwordcard.operation.unblockError");
			saveMessage(request, msgs, message);
            return mapping.findForward(FORWARD_SHOW);
		}
		catch(InvalidPaswordCardValueException ex)
		{
			String message =
                    messageResources.getMessage("com.rssl.phizic.web.persons.passwordcard.operation.unblockInvalid");
        	saveMessage(request, msgs, message);
			return mapping.findForward(FORWARD_SHOW);
		}
		if ( !StringHelper.isEmpty(msgNum))
		{
			generateInvalidPasswordCardStateMessage(request, OPERATION_UNBLOCKING, msgs, msgNum, motive);
			return mapping.findForward(FORWARD_SHOW);
		}

		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getCard()));

		return new ActionForward( mapping.findForward(FORWARD_CLOSE).getPath() + "?person=" + frm.getPerson(),true);
	}

    private void saveMessage(HttpServletRequest request, ActionMessages msgs, String message)
	{
		ActionMessage  msg  = new ActionMessage(message, false);
		msgs.add(ActionMessages.GLOBAL_MESSAGE, msg);
	    saveMessages(request, msgs);
	}

	private void generateInvalidPasswordCardStateMessage(HttpServletRequest request, String operationName,ActionMessages msgs, String msgNum, String motive)
	{
		ActionMessage  msg  = new ActionMessage( "com.rssl.phizic.web.persons.invalidPasswordCardState", new String[] {operationName} );
        msgs.add(ActionMessages.GLOBAL_MESSAGE, msg);
        if (msgNum != null)
        {
            msg = new ActionMessage( "com.rssl.phizic.web.persons.numberCards", msgNum );
            msgs.add(ActionMessages.GLOBAL_MESSAGE, msg);
        }
        msg = new ActionMessage( "com.rssl.phizic.web.persons.numberCards", motive );
        msgs.add(ActionMessages.GLOBAL_MESSAGE, msg);

        saveMessages(request, msgs);
	}
}

