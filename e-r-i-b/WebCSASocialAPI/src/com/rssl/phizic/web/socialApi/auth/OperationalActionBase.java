package com.rssl.phizic.web.socialApi.auth;

import com.rssl.auth.csa.front.exceptions.BlockingRuleException;
import com.rssl.auth.csa.front.exceptions.FrontException;
import com.rssl.auth.csa.front.exceptions.FrontLogicException;
import com.rssl.auth.csa.front.operations.Operation;
import com.rssl.auth.csasocial.exceptions.*;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.Constants;
import com.rssl.phizic.config.CSASocialAPIConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.mobile.MobileApiConfig;
import com.rssl.phizic.utils.RandomHelper;
import com.rssl.phizic.utils.store.Store;
import com.rssl.phizic.utils.store.StoreManager;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.auth.ActionFormBase;
import com.rssl.phizic.web.auth.LookupDispatchAction;
import com.rssl.phizic.web.socialApi.auth.register.RegisterAppForm;
import com.rssl.phizic.web.struts.forms.FormHelper;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author osminin
 * @ created 03.08.13
 * @ $Author$
 * @ $Revision$
 *
 * ������� �����
 */
public abstract class OperationalActionBase extends LookupDispatchAction
{
	public static final String FORWARD_SHOW           = "show";
	public static final String FORWARD_RESET_MGUID    = "mGUIDError";
	public static final String FORWARD_START          = "start";
	public static final String FORWARD_BLOCK          = "blockError";

	protected static final String CONFIRM_PARAMS      = "confirmParams";
	protected static final String MGUID_PARAM         = "mGUIDParam";
	protected static final String ATTEMPTS_PARAM      = "attemptsParam";
	protected static final String TIMEOUT_PARAM       = "timeoutParam";
	protected static final String CREATION_TIME_PARAM = "creationTimeParam";

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionFormBase frm = (ActionFormBase) form;
		FormProcessor<ActionMessages, ?> formProcessor = FormHelper.newInstance(new RequestValuesSource(currentRequest()) , getForm());

		if (!formProcessor.process())
		{
			saveErrors(request, formProcessor.getErrors());
			return mapping.findForward(FORWARD_SHOW);
		}

		try
		{
			Operation operation = getOperation(formProcessor.getResult());
			operation.execute();
			updateForm(operation, frm);
		}
		catch (ResetSocialGUIDException e)
		{
			return mGUIDErrorForward(mapping, request, e);
		}
		catch (LoginOrPasswordWrongLoginException e)
		{
			return errorForward(mapping, request, "������ �����������. ������ � ������� ��������.", e);
		}
		catch (RegistrationErrorException e)
		{
			return errorForward(mapping, request, "� ���������, ������ ����������� �� ����� ���� ���������. ����������, ��������� ������� �����.", e);
		}
		catch (BlockingRuleException e)
		{
			return blockErrorForward(mapping, request, e.getMessage(), e);
		}
		catch (WrongCodeConfirmException e)
		{
			RegisterAppForm registerAppForm = (RegisterAppForm) frm;

			registerAppForm.setSmsPasswordLifeTime(e.getTime());
			registerAppForm.setSmsPasswordAttemptsRemain(e.getAttempts());

			return errorForward(mapping, request, e.getMessage(), e);
		}
		catch (FailureIdentificationFrontException ignore)
		{
			RegisterAppForm regFrm = (RegisterAppForm) form;
			//� ������ ��������������� ������ ���������� �������� �������� ��� ��� ������������� (������� �� ������� CHG059905).
			String mGUID = RandomHelper.rand(32, "1234567890ABCDEF");
			createMockConfirmParameters(regFrm, mGUID);
			return mapping.findForward(FORWARD_SHOW);
		}
		catch (FrontException e)
		{
			return errorForward(mapping, request, Constants.COMMON_EXCEPTION_MESSAGE, e);
		}
		catch (FrontLogicException e)
		{
			return errorForward(mapping, request, e.getMessage(), e);
		}

		return mapping.findForward(getForwardName());
	}

	protected abstract void updateForm(Operation operation, ActionFormBase form) throws FrontException, FrontLogicException;

	protected abstract Operation getOperation(Map<String, Object> data) throws FrontException, FrontLogicException;

	protected abstract Form getForm();

	protected String getForwardName()
	{
		return FORWARD_SHOW;
	}

	private ActionForward errorForward(ActionMapping mapping, HttpServletRequest request, String message, Throwable e)
	{
		return errorForwardBase(mapping, request, message, e, FORWARD_SHOW);
	}

	private ActionForward mGUIDErrorForward(ActionMapping mapping, HttpServletRequest request, Throwable e)
	{
		return errorForwardBase(mapping, request, "�������� �����������. ���������� mGUID.", e, FORWARD_RESET_MGUID);
	}

	private ActionForward blockErrorForward(ActionMapping mapping, HttpServletRequest request, String message, Throwable e)
	{
		return errorForwardBase(mapping, request, message, e, FORWARD_BLOCK);
	}

	private ActionForward errorForwardBase(ActionMapping mapping, HttpServletRequest request, String message, Throwable e, String forward)
	{
		log.error(message, e);
		saveError(request, new ActionMessage(message, false));
		return mapping.findForward(forward);
	}

	private void createMockConfirmParameters(RegisterAppForm frm, String mGUID) throws BusinessException
	{
		Random rand = new Random();
		Long timeout = ConfigFactory.getConfig(MobileApiConfig.class).getConfirmationTimeout() - rand.nextInt(2) - 2L;
		Long attempts = Long.valueOf(ConfigFactory.getConfig(MobileApiConfig.class).getConfirmationAttemptsCount());

		frm.setMguid(mGUID);
		frm.setSmsPasswordLifeTime(timeout);
		frm.setSmsPasswordAttemptsRemain(attempts);
		frm.setMinimumPINLength(Long.valueOf(ConfigFactory.getConfig(CSASocialAPIConfig.class).getMobilePINLength()));

		Map<String, Object> info = new HashMap<String, Object>();
		info.put(MGUID_PARAM,         mGUID);
		info.put(ATTEMPTS_PARAM,      attempts);
		info.put(TIMEOUT_PARAM,       timeout);
		info.put(CREATION_TIME_PARAM, System.currentTimeMillis());

		Store store = StoreManager.getCurrentStore();
		synchronized (store)
		{
			store.save(CONFIRM_PARAMS, info);
		}
	}
}
