package com.rssl.phizic.web.client.ext.sbrf.mobilebank;

import com.rssl.phizic.auth.modes.ConfirmRequest;
import com.rssl.phizic.auth.modes.PreConfirmObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.web.ConfirmationManager;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.mobilebank.QuickServiceStatusCode;
import com.rssl.phizic.logging.confirm.OperationConfirmLogConfig;
import com.rssl.phizic.logging.confirm.OperationConfirmLogWriter;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.ext.sbrf.mobilebank.ChangeQuickServiceStatusOperation;
import com.rssl.phizic.operations.ext.sbrf.mobilebank.ListPaymentsOperation;
import com.rssl.phizic.operations.ext.sbrf.mobilebank.ListRegistrationsOperation;
import com.rssl.phizic.operations.ext.sbrf.mobilebank.TbErmbProfileTestOperation;
import com.rssl.phizic.operations.sms.CallBackHandlerSmsImpl;
import com.rssl.phizic.security.CallBackHandler;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.utils.store.Store;
import com.rssl.phizic.utils.store.StoreManager;
import com.rssl.phizic.web.actions.MobileActionBase;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.common.confirm.ConfirmHelper;
import com.rssl.phizic.web.security.SecurityMessages;
import org.apache.struts.action.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Erkin
 * @ created 27.04.2010
 * @ $Author$
 * @ $Revision$
 * @deprecated избавление от МБК
 */
@Deprecated
//todo CHG059738 удалить
public class ListRegistrationsAction extends MobileActionBase
{
	protected static final String FORWARD_SUCCESS = "Success";
	protected static final String FORWARD_CONFIRM = "Confirm";
	protected static final String FORWARD_ERMB_OTHER_TB = "ErmbInOtherTb";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> key = new HashMap<String, String>();
		key.put("button.change",     "change");
		key.put("button.confirmSMS", "changeToSMS");
		key.put("button.confirmCap", "changeToCap");
		key.put("button.confirm",    "confirm");
		key.put("button.nextStage",  "next");
		return key;
	}

    private ListRegistrationsForm initForm(ActionForm frm) throws BusinessException, BusinessLogicException
    {
        ListRegistrationsOperation operation = createOperation("ListMobileBankRegistrationsOperation");
        operation.initialize();
        ListRegistrationsForm form = (ListRegistrationsForm) frm;
	    try
	    {
		    form.setRegistrations(operation.getRegistrationProfiles());
	    }
	    catch (InactiveExternalSystemException e)
	    {
		    saveMessage(currentRequest(), e.getMessage());
		    form.setTechnoBreak(true);

	    }

        return form;
    }

    public ActionForward start(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
	    TbErmbProfileTestOperation operation = createOperation("TbErmbProfileTestOperation");
	    operation.initialize();
	    if (super.isErmbProfile())
		    return new ActionRedirect(mapping.findForward(FORWARD_ERMB));
	    else if (operation.isErmbProfileInOtherTb())
	    {
		    ListRegistrationsForm form = (ListRegistrationsForm) frm;
		    form.setFo(operation.getPersonName());
		    return mapping.findForward(FORWARD_ERMB_OTHER_TB);
	    }


		initForm(frm);
        return mapping.findForward(FORWARD_START);
    }

    public ActionForward change(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        ListRegistrationsForm form = initForm(frm);

	    ListPaymentsOperation paymentsOperation = createOperation("ListMobileBankPaymentsOperation");
	    paymentsOperation.initialize();

		ChangeQuickServiceStatusOperation operation = createOperation("QuickServiceStatusOperation");
		operation.initialize( paymentsOperation.getRegistrationProfile(form.getPhoneCode(), form.getCardCode()) );
        operation.setStrategyType();

        operation.resetConfirmStrategy();
        ConfirmationManager.sendRequest(operation);

        updateForm(form, operation);

        saveOperation(request, operation);
        return mapping.findForward(FORWARD_START);
    }

	public ActionForward preConfirm(ActionMapping mapping, ActionForm frm,	HttpServletRequest request,	HttpServletResponse response, ChangeQuickServiceStatusOperation operation) throws Exception
	{
		ListRegistrationsForm form = (ListRegistrationsForm) frm;

		CallBackHandler callBackHandler = new CallBackHandlerSmsImpl();
		callBackHandler.setLogin(operation.getPerson().getLogin());
		callBackHandler.setConfirmableObject(operation.getConfirmableObject());

		try
		{
			PreConfirmObject preConfirmObject = operation.preConfirm(callBackHandler);
			operation.getRequest().addMessage(ConfirmHelper.getPreConfirmString(preConfirmObject));
            
			updateForm(form, operation);

			addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));
		}
		catch (SecurityException e)
		{
			ActionMessages errors = new ActionMessages();
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
            saveErrors(request, errors);		
		}
		catch (SecurityLogicException e)
		{
            operation.getRequest().setErrorMessage(SecurityMessages.translateException(e));
            operation.getRequest().setPreConfirm(true);

            updateForm(form, operation);
        }

		return mapping.findForward(FORWARD_CONFIRM);
	}


    public ActionForward changeToCap(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        ListRegistrationsForm form = initForm(frm);
        ChangeQuickServiceStatusOperation operation = getOperation(request);
        operation.setUserStrategyType(ConfirmStrategyType.cap);

		ConfirmationManager.sendRequest(operation);
        
		ConfirmRequest confirmRequest = operation.getRequest();
        confirmRequest.setPreConfirm(true);
        ConfirmStrategyType currentType = confirmRequest.getStrategyType();
		if (currentType != ConfirmStrategyType.cap)
		{
            //noinspection ThrowableResultOfMethodCallIgnored
			ConfirmHelper.saveConfirmErrors(operation.getRequest(), Collections.singletonList(operation.getWarning().getMessage()));
			return preConfirm(mapping, frm, request, response, operation);
		}

        updateForm(form, operation);
        
		return mapping.findForward(FORWARD_CONFIRM);
    }

    public ActionForward changeToSMS(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        initForm(frm);
        ChangeQuickServiceStatusOperation operation = getOperation(request);
		operation.setUserStrategyType(ConfirmStrategyType.sms);

		ConfirmationManager.sendRequest(operation);
        
        operation.getRequest().setPreConfirm(true);
		return preConfirm(mapping, frm, request, response, operation);
    }

    private void updateForm(ListRegistrationsForm form, ChangeQuickServiceStatusOperation operation)
    {
        form.setConfirmableRegistration(operation.getConfirmableObject());
        form.setConfirmStrategy(operation.getConfirmStrategy());
    }

	public ActionForward confirm(ActionMapping mapping, ActionForm frm,	HttpServletRequest request,	HttpServletResponse response) throws Exception
	{
		ListRegistrationsForm form = initForm(frm);
		ChangeQuickServiceStatusOperation operation = getOperation(request);
        ConfirmRequest confirmRequest = ConfirmationManager.currentConfirmRequest(operation.getConfirmableObject());
	    ConfirmHelper.clearConfirmErrors(confirmRequest);
		try
		{
			List<String> errors = ConfirmationManager.readResponse(operation, new RequestValuesSource(request));
			if (errors.isEmpty())
			{
				operation.confirm();

				QuickServiceStatusCode status = operation.getConfirmableObject().getQuickServiceStatusCode();
				if (status == QuickServiceStatusCode.QUICK_SERVICE_STATUS_UNKNOWN ||
					status == QuickServiceStatusCode.QUICK_SERVICE_STATUS_REPEAT_QUERY)
				{
					saveSessionMessage(ActionMessages.GLOBAL_MESSAGE,
							           new ActionMessage("Данная операция временно недоступна. Пожалуйста, повторите попытку позже.", false), null);
				}
                resetOperation(request);
			}
			else
			{
				operation.getRequest().setErrorMessage(errors.get(0));
			}
		}
		catch (BusinessException e)
		{
			saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false), null);
		}
		catch (SecurityException e)
		{
			saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false), null);
		}
		catch (SecurityLogicException e)
		{
            updateForm(form, operation);
			operation.getRequest().setErrorMessage(e.getMessage());

			return mapping.findForward(FORWARD_CONFIRM);
		}
		catch (InactiveExternalSystemException e)
		{
			saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false), null);
		}

		return next(mapping, frm, request, response);
	}

	public ActionForward next(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws BusinessException, BusinessLogicException
	{
		saveSessionMessages(request, getMessages(request));
		return mapping.findForward(FORWARD_SUCCESS);
	}
}
