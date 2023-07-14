package com.rssl.phizic.web.client.ext.sbrf.mobileWallet;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.PreConfirmObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.operations.ConfirmableOperation;
import com.rssl.phizic.business.web.ConfirmationManager;
import com.rssl.phizic.operations.payment.transactions.entry.EditMobileWalletEntry;
import com.rssl.phizic.operations.payment.transactions.entry.EditMobileWalletOperation;
import com.rssl.phizic.operations.sms.CallBackHandlerSmsImpl;
import com.rssl.phizic.security.CallBackHandler;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.common.confirm.ConfirmHelper;
import com.rssl.phizic.web.log.FormLogParametersReader;
import com.rssl.phizic.web.security.SecurityMessages;
import com.rssl.phizic.web.util.HttpSessionUtils;
import org.apache.struts.Globals;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author osminin
 * @ created 10.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditMobileWalletAction extends OperationalActionBase
{
	private static final SimpleService simpleService = new SimpleService();

	protected Map<String, String> getKeyMethodMap()
	{
		Map <String, String> map = new HashMap<String, String>();
		map.put("button.preConfirmSMS","preConfirm");
		map.put("button.confirm", "confirm");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditMobileWalletForm frm = (EditMobileWalletForm) form;
		EditMobileWalletOperation operation = createOperation("EditMobileWalletOperation", "MobileWallet");

		try
		{
			operation.initialize();
			operation.resetConfirmStrategy();
			ConfirmationManager.sendRequest(operation);
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
			return mapping.findForward(FORWARD_START);
		}

		updateStartForm(frm, operation);
		saveOperation(request, operation);

		ActionMessages msgs = HttpSessionUtils.getSessionAttribute(request, Globals.ERROR_KEY);
		if (msgs != null && !msgs.isEmpty())
			saveErrors(request, msgs);
		addLogParameters(new FormLogParametersReader("Данные формы", EditMobileWalletForm.EDIT_MOBILE_WALLET_FORM, frm.getFields()));
		return mapping.findForward(FORWARD_START);
	}

	public ActionForward preConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditMobileWalletForm frm = (EditMobileWalletForm) form;
		EditMobileWalletOperation operation = getOperation(request);
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new MapValuesSource(frm.getFields()), EditMobileWalletForm.EDIT_MOBILE_WALLET_FORM);

		if (processor.process())
		{
			try
			{
				operation.initialize((String) frm.getField("totalAmount"));
				addLogParameters(new FormLogParametersReader("Данные, введенные пользователем", EditMobileWalletForm.EDIT_MOBILE_WALLET_FORM, frm.getFields()));

				ConfirmationManager.sendRequest(operation);

				EditMobileWalletEntry entry = (EditMobileWalletEntry) operation.getConfirmableObject();
				CallBackHandler callBackHandler = new CallBackHandlerSmsImpl();
				callBackHandler.setConfirmableObject(entry);
				Login login = simpleService.findById(Login.class, entry.getProfile().getLoginId());
				callBackHandler.setLogin(login);

				operation.getRequest().setPreConfirm(true);
				PreConfirmObject preConfirmObject = operation.preConfirm(callBackHandler);
				operation.getRequest().addMessage(ConfirmHelper.getPreConfirmString(preConfirmObject));
			}
			catch (BusinessLogicException e)
			{
				ActionMessages msgs = new ActionMessages();
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
				saveErrors(request.getSession(), msgs);
			}
			catch (SecurityLogicException e)
			{
				ActionMessages msgs = new ActionMessages();
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(SecurityMessages.translateException(e), false));
				saveErrors(request.getSession(), msgs);
			}
		}
		else
		{
			saveErrors(request.getSession(), processor.getErrors());
		}
		
		updateForm(frm, operation);
		return mapping.findForward(FORWARD_START);
	}

	public ActionForward confirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditMobileWalletForm frm = (EditMobileWalletForm) form;
		EditMobileWalletOperation operation = getOperation(request);
		List<String> errors = ConfirmationManager.readResponse(operation, new RequestValuesSource(request));

		if (!errors.isEmpty())
		{
			operation.getRequest().setErrorMessage(errors.get(0));
			return mapping.findForward(FORWARD_START);
		}

		try
		{
			operation.confirm();
			operation.getRequest().setPreConfirm(false);
		}
		catch (SecurityLogicException e) // ошибка подтверждения
        {
	        ConfirmHelper.saveConfirmErrors(operation.getRequest(), Collections.singletonList(e.getMessage()));
        }

		updateForm(frm, operation);
		addLogParameters(new FormLogParametersReader("Данные, введенные пользователем", EditMobileWalletForm.EDIT_MOBILE_WALLET_FORM, frm.getFields()));
		return mapping.findForward(FORWARD_START);
	}

	protected void updateStartForm(EditMobileWalletForm frm, ConfirmableOperation operation) throws BusinessException, BusinessLogicException
	{
		EditMobileWalletOperation op = (EditMobileWalletOperation) operation;
		EditMobileWalletEntry entry = (EditMobileWalletEntry) op.getConfirmableObject();
		frm.setField("totalAmount", entry.getProfile().getMobileWallet() != null ? entry.getProfile().getMobileWallet().getDecimal() : null);
		frm.setConfirmableObject(entry);
		frm.setConfirmStrategy(op.getConfirmStrategy());
		frm.setLimitAmountValue(op.getLimit().getAmount().getDecimal());
	}

	protected void updateForm(EditMobileWalletForm form, ConfirmableOperation operation)
	{
		form.setConfirmableObject(operation.getConfirmableObject());
	}
}
