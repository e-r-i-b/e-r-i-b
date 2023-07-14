package com.rssl.phizic.web.client.certification;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.PreConfirmObject;
import com.rssl.phizic.business.web.ConfirmationManager;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.certification.ConfirmCertDemandClientOperation;
import com.rssl.phizic.operations.sms.CallBackHandlerSmsImpl;
import com.rssl.phizic.security.CallBackHandler;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.web.security.SecurityMessages;
import com.rssl.phizic.web.common.confirm.ConfirmHelper;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Omeliyanchuk
 * @ created 21.05.2007
 * @ $Author$
 * @ $Revision$
 */

public class ConfirmCertDemandCertificationAction extends OperationalActionBase
{
	private static final String FORWARD_START = "Start";
	private static final String FORWARD_VIEW = "View";

    protected Map<String, String> getKeyMethodMap()
    {
        Map<String,String> map = new HashMap<String, String>();
        map.put("button.confirm", "confirm");
	    map.put("button.install","install");
	    map.put("button.preConfirm", "preConfirm");
        return map;
    }

    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                               HttpServletResponse response)
            throws Exception
    {
	    ConfirmCertDemandCertificationForm frm = (ConfirmCertDemandCertificationForm) form;

	    ConfirmCertDemandClientOperation operation = createOperation( ConfirmCertDemandClientOperation.class);

	    operation.initialize(frm.getId());

	    frm.setDemand(operation.getConfirmableObject());
	    frm.setPerson(operation.getCertPerson());

	    //создание строки для подписи
	    ConfirmationManager.sendRequest(operation);

	    return mapping.findForward(FORWARD_START);
    }


	public ActionForward confirm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                             HttpServletResponse response) throws Exception
	{
		ConfirmCertDemandCertificationForm frm = (ConfirmCertDemandCertificationForm) form;

		ConfirmCertDemandClientOperation operation = createOperation( ConfirmCertDemandClientOperation.class);
		operation.initialize(frm.getId());

		List<String> errors =  null;
		//есди нет активного сертификата, то не подписываем		
		if(operation.getHasActiveCertificate())
			errors = ConfirmationManager.readResponse(operation, new RequestValuesSource(request));

	    if ( (errors!=null) && (!errors.isEmpty()) )
        {
	        saveErrors(request, errors);
            return start(mapping, form, request, response);
        }
        else
        {
	        try
	        {
		        addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));

		        operation.confirm();
	        }
	        catch(SecurityLogicException e){
                saveErrors(request, errors);
	            return start(mapping, form, request, response);
	        }
	        return new ActionForward(mapping.findForward( FORWARD_VIEW ).getPath() + "?id="+frm.getId(), true);
        }
	}

	public ActionForward preConfirm( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		ConfirmCertDemandClientOperation operation = createOperation(ConfirmCertDemandClientOperation.class);
		
		Login login             = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
		ConfirmCertDemandCertificationForm frm = (ConfirmCertDemandCertificationForm) form;

		operation.initialize(frm.getId());
		frm.setDemand(operation.getConfirmableObject());
		frm.setPerson(operation.getCertPerson());

		CallBackHandler callBackHandler = new CallBackHandlerSmsImpl();
		callBackHandler.setConfirmableObject(operation.getConfirmableObject());
		callBackHandler.setLogin(login);
		try
		{
			addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));

			PreConfirmObject preConfirmObject = operation.preConfirm(callBackHandler);
			saveMessages(request, ConfirmHelper.getPreConfirmMsg(preConfirmObject) );
		}
		catch (SecurityLogicException e)
		{
			ActionMessages errors = new ActionMessages();
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(SecurityMessages.translateException(e)));
            saveErrors(request, errors);
		}

		return mapping.findForward(FORWARD_START);
	}
}
