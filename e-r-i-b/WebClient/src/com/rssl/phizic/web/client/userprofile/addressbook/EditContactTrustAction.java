package com.rssl.phizic.web.client.userprofile.addressbook;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.ConfirmRequest;
import com.rssl.phizic.auth.modes.PreConfirmObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.profile.addressbook.Contact;
import com.rssl.phizic.business.web.ConfirmationManager;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.addressbook.AddressBookConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.sms.CallBackHandlerSmsImpl;
import com.rssl.phizic.operations.userprofile.addressbook.EditContactOperation;
import com.rssl.phizic.security.CallBackHandler;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.web.actions.OperationalActionBase;
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
 * Редиктирование уровня доверия контакта
 *
 * @author shapin
 * @ created 08.10.14
 * @ $Author$
 * @ $Revision$
 */

public class EditContactTrustAction extends OperationalActionBase
{
	private static final String CONFIRM_FORWARD = "ConfirmForm";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.confirmSMS", "changeToSMS");
		map.put("button.confirm",    "confirm");
		return map;
	}
    @Override
    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditContactTrustForm frm = (EditContactTrustForm) form;

		EditContactOperation operation = createOperation(EditContactOperation.class);
		operation.initialize(frm.getId());
		Contact contact = operation.getEntity();
		contact.setTrusted(!frm.getTrusted());

		saveOperation(request, operation);

		if (!frm.getTrusted() && needConfirm() )
			return changeToSMS(mapping, form, request, response);

		operation.save();
        return mapping.findForward(FORWARD_START);
	}

	// определяем, нужно ли подтверждать изменения одноразовым паролем
	private boolean needConfirm()
	{
		boolean allowOneConfirm = ConfigFactory.getConfig(AddressBookConfig.class).isAllowOneConfirm();
		return !allowOneConfirm || (allowOneConfirm && !PersonContext.getPersonDataProvider().getPersonData().isTrustedContactConfirmed());
	}

    public ActionForward confirm(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditContactOperation  operation = getOperation(request);
		ConfirmRequest confirmRequest = operation.getRequest();
		ConfirmHelper.clearConfirmErrors(confirmRequest);

		EditContactTrustForm form = (EditContactTrustForm) frm;

		List<String> errors = ConfirmationManager.readResponse(operation, new RequestValuesSource(request));
		if (errors.isEmpty())
			return doConfirm(mapping, operation, form, request);

		ConfirmHelper.saveConfirmErrors(confirmRequest, errors);

		return  forwardShow(mapping, operation, form,request);
	}
	private ActionForward doConfirm(ActionMapping mapping, EditContactOperation operation, EditContactTrustForm form, HttpServletRequest request) throws Exception
	{
		try
		{
			operation.confirm();
			addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));
			if (PersonContext.isAvailable())
				PersonContext.getPersonDataProvider().getPersonData().setTrustedContactConfirmed(true);
			return mapping.findForward(FORWARD_START);
		}
		catch (SecurityLogicException e)
		{
			ConfirmHelper.saveConfirmErrors(operation.getRequest(), Collections.singletonList(e.getMessage()));

			return forwardShow(mapping, operation, form,request);
		}
	}

    public ActionForward changeToSMS(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditContactOperation  operation = getOperation(request);
		EditContactTrustForm frm = (EditContactTrustForm) form;

		operation.setUserStrategyType(ConfirmStrategyType.sms);

		ConfirmationManager.sendRequest(operation);
		Login login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
		operation.getRequest().setPreConfirm(true);
		CallBackHandler callBackHandler = new CallBackHandlerSmsImpl();
		callBackHandler.setConfirmableObject(operation.getConfirmableObject());
		callBackHandler.setLogin(login);
		callBackHandler.setAdditionalCheck();

		try
		{
			PreConfirmObject preConfirmObject = operation.preConfirm(callBackHandler);
			ConfirmRequest confirmRequest = operation.getRequest();
			confirmRequest.addMessage(ConfirmHelper.getPreConfirmString(preConfirmObject));
		}
		catch (SecurityException e)
		{
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
			saveErrors(request, errors);
		}
		catch (SecurityLogicException e)
		{
			ConfirmHelper.saveConfirmErrors(operation.getRequest(), Collections.singletonList(SecurityMessages.translateException(e)));
		}

		return forwardShow(mapping, operation, frm,request);
	}

	private ActionForward forwardShow(ActionMapping mapping, EditContactOperation operation, EditContactTrustForm frm, HttpServletRequest request) throws BusinessException, BusinessLogicException
	{

		frm.setConfirmStrategy(operation.getConfirmStrategy());
		frm.setConfirmableObject(operation.getConfirmableObject());
		frm.setAllowOneConfirm(ConfigFactory.getConfig(AddressBookConfig.class).isAllowOneConfirm());
		frm.setId(operation.getEntity().getId());

		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));
		return mapping.findForward(CONFIRM_FORWARD);
	}


}
