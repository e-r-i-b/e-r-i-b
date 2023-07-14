package com.rssl.phizic.web.client.passwordcards;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.auth.passwordcards.InvalidPasswordCardStateException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.operations.CompositeLogParametersReader;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.phizic.operations.passwordcards.LastPasswordOnCardOperation;
import com.rssl.phizic.operations.passwords.InvalidNewCardNumberException;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.security.password.PasswordValidationException;
import com.rssl.phizic.web.security.LoginStageActionSupport;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author Omeliyanchuk
 * @ created 27.11.2006
 * @ $Author$
 * @ $Revision$
 */

@SuppressWarnings({"JavaDoc"})
public class LastPasswordOnCardAction extends LoginStageActionSupport
{
	private static final String FORWARD_START = "Start";
	private static final String FORWARD_START_CARD_PASSWORD_ASSIGN_CLIENT_MODE = "StartClientAssignMode";

	protected Map<String, String> getKeyMethodMap()
    {
        Map<String,String> map = new HashMap<String, String>();
	    map.put("button.activate" , "activate");
        return map;
    }

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	                           HttpServletResponse response) throws Exception
    {
	    SecurityConfig securityConfig = ConfigFactory.getConfig(SecurityConfig.class);
	    LastPasswordOnCardForm frm = (LastPasswordOnCardForm)form;
	    LastPasswordOnCardOperation operation = createOperation(LastPasswordOnCardOperation.class);
	    operation.initialize(getAuthenticationContext());
	    ActionForward forward=mapping.findForward(FORWARD_START);
	    try
	    {
			if (operation.getCurrentCard() == null && securityConfig.isCardPasswordAutoAssign()){
				forward = mapping.findForward(FORWARD_START_CARD_PASSWORD_ASSIGN_CLIENT_MODE);
			}else{
		        frm.setNeedKeyNumber( operation.getNextUnusedPasswordNumber() );
		        getAuthenticationContext().setPasswordNumber(frm.getNeedKeyNumber());
		        frm.setOldCardId(operation.getActiveCardNumber());

				addLogParameters(new CompositeLogParametersReader(
						new SimpleLogParametersReader("Номер карты ключей", operation.getActiveCardNumber()),
						new SimpleLogParametersReader("Номер следующего пароля", frm.getNeedKeyNumber())
					));
			}
			if(frm.getReturnPath() == null)
			{
				frm.setReturnPath( request.getParameter("returnTo") );
			}
			frm.setSuccess(false);
	    }
	    catch(IllegalArgumentException ex)
	    {
			frm.setHasActiveCard(false);	
	    }
	    return forward;
    }

	public ActionForward activate (ActionMapping mapping, ActionForm form, HttpServletRequest request,
	                               HttpServletResponse response) throws Exception
	{
		SecurityConfig securityConfig = ConfigFactory.getConfig(SecurityConfig.class);
		LastPasswordOnCardForm frm = (LastPasswordOnCardForm)form;

		MapValuesSource valuesSource = new MapValuesSource(frm.getFields());
		LastPasswordOnCardOperation operation = createOperation(LastPasswordOnCardOperation.class);
		operation.initialize(getAuthenticationContext());
		Form editForm = LastPasswordOnCardForm.PSW_FORM;
		if (operation.getCurrentCard() == null && securityConfig.isCardPasswordAutoAssign()){
			editForm=LastPasswordOnCardForm.PSW_SELF_ASSIGN_FORM;
		}
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, editForm);
		String message = null;
		if(processor.process())
		{
			try
			{
				Map<String, Object> result = processor.getResult();

				if (operation.getCurrentCard() == null && securityConfig.isCardPasswordAutoAssign()){
					operation.activateNewCard((String)result.get("number"));
				}else{
					operation.activateNewCard((String)result.get("password"), (String)result.get("number"));
				}
				frm.setSuccess(true);

				addLogParameters(new SimpleLogParametersReader("Номер карты ключей", result.get("number")));

				if( (frm.getReturnPath() != null)&&(frm.getReturnPath().length() !=0) )
				{
					return new ActionForward(frm.getReturnPath(),true);
				}
				else
				{
					return mapping.findForward(FORWARD_GOTO_AUTHORIZED_INDEX);
				}
			}
			catch(PasswordValidationException ex)
			{
				message = ex.getMessage();
			}
			catch(InvalidNewCardNumberException ex)
			{
				message = ex.getMessage();
			}
			catch(InvalidPasswordCardStateException ex)
			{
				message = "Указанная карта ключей заблокированна или уже использована";
			}
			catch(SecurityLogicException ex)
			{
				message = ex.getMessage();
			}
		}
		else
		{
			saveErrors(request, processor.getErrors());
			frm.setSuccess(false);
		}

		if(message!= null)
		{
			ActionMessages msgs = new ActionMessages();
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage(message,false));
			saveMessages(request, msgs);
			frm.setSuccess(false);
		}
		return start( mapping, form, request, response);
	}
}
