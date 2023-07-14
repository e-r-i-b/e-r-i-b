package com.rssl.phizic.web.auth.verification;

import com.rssl.auth.csa.front.operations.auth.GetConfirmationInfoOperation;
import com.rssl.auth.csa.front.operations.auth.OperationInfo;
import com.rssl.auth.csa.front.operations.auth.verification.BusinessEnvironmentOperationInfo;
import com.rssl.auth.csa.front.operations.auth.verification.InitializeVerifyBusinessEnvironmentOperation;
import com.rssl.auth.csa.front.operations.auth.verification.VerificationState;
import com.rssl.auth.csa.front.exceptions.FrontException;
import com.rssl.auth.csa.front.exceptions.FrontLogicException;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.config.FrontSettingHelper;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.auth.AuthStageFormBase;
import com.rssl.phizic.web.auth.verification.satges.Stages;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Экшен верификации данных клиента в деловой среде
 *
 * @author akrenev
 * @ created 01.04.2013
 * @ $Author$
 * @ $Revision$
 */

public class ConfirmVerifyBusinessEnvironmentAction extends BusinessEnvironmentActionBase
{
	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> keyMethodMap = new HashMap<String, String>();
		keyMethodMap.put("button.initializeVerify.sms",              "initializeVerifyBySMS");
		keyMethodMap.put("button.initializeVerify.push",             "initializeVerifyByPush");
		keyMethodMap.put("button.initializeVerify.card",             "initializeVerifyByCard");
		keyMethodMap.put("button.initializeVerify.alternative.sms",  "initializeVerifyByCard");
		keyMethodMap.put("button.initializeVerify.alternative.push", "initializeVerifyByPush");
		keyMethodMap.put("button.initializeVerify.alternative.card", "initializeVerifyBySMS");
		keyMethodMap.put("button.verify",                            "verify");
		return keyMethodMap;
	}

	@Override
	protected ActionForward doStart(ActionMapping mapping, BusinessEnvironmentForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// вытаскиваем контекст операции не проверяя токен (если нужно то провериться дальше)
		OperationInfo operationInfo = getOperationInfo(request, false);
		// если нет контекста операции, то посылаем на логин
		if (operationInfo == null)
			return mapping.findForward(Stages.LOGIN.getName());

		// если текущая стадия не соответствует стадии экшена -- посылаем на текущую (из контекста) стадию
		String realStageName = operationInfo.getCurrentStage().getName();
		if (!StringUtils.equals(mapping.getParameter(), realStageName))
			return mapping.findForward(realStageName);

		try
		{
			//noinspection SynchronizationOnLocalVariableOrMethodParameter
			synchronized (operationInfo)
			{
				// получаем инфу о способах подтверждения (предпочтительный способ подтверждения и список карт для подтверждения)
				GetConfirmationInfoOperation op = new GetConfirmationInfoOperation();
				op.initialize((BusinessEnvironmentOperationInfo) operationInfo);
				op.execute();
			}
		}
		catch (FrontLogicException e)
		{
			saveError(request, e.getMessage());
		}

		// все хорошо -- продолжаем выполнять "старт"
		return super.doStart(mapping, form, request, response);
	}

	@Override
	protected void updateForm(OperationInfo operationInfo, AuthStageFormBase frm, boolean isSuccessNextStage)
	{
		super.updateForm(operationInfo, frm, isSuccessNextStage);
		if (!isSuccessNextStage)
			return;

		//если верификация прошла (не важно удачно или нет), то посылаем обратно в деловую среду
		BusinessEnvironmentOperationInfo context = (BusinessEnvironmentOperationInfo) operationInfo;
		VerificationState verificationState = context.getVerificationState();
		if (VerificationState.NOT_START != verificationState)
		{
			frm.setRedirect(FrontSettingHelper.getBusinessEnvironmentAfterVerifyURL(verificationState));
			return;
		}

		if (ConfirmStrategyType.card == context.getConfirmType() && !context.isValid())
		{
			((BusinessEnvironmentForm) frm).setConfirmationError("card.info.not.found.in.mb");
			return;
		}

		// для формы подтверждения выводим время и попытки
		Map<String, Object> confirmParams = context.getConfirmParams();
		Long timeout = (Long) confirmParams.get("Timeout");
		Long passwordsLeft = (Long) confirmParams.get("PasswordsLeft");

		if (timeout != null)
			saveMessage(currentRequest(), StrutsUtils.getMessage("message.confirm.timeout", "commonBundle", timeout));

		if (passwordsLeft != null)
			saveMessage(currentRequest(), StrutsUtils.getMessage("message.confirm.passwords.left", "commonBundle", passwordsLeft));

	}

	protected OperationInfo getOperationInfo()
	{
		throw new UnsupportedOperationException("Создание контекста операции не возможно.");
	}

	@SuppressWarnings({"UnusedDeclaration"})
	private ActionForward initializeVerify(ConfirmStrategyType confirmType, ActionMapping mapping, BusinessEnvironmentForm form, HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			BusinessEnvironmentOperationInfo operationInfo = (BusinessEnvironmentOperationInfo) getOperationInfo(request);
			operationInfo.setConfirmType(confirmType);
			//noinspection SynchronizationOnLocalVariableOrMethodParameter
			synchronized (operationInfo)
			{
				InitializeVerifyBusinessEnvironmentOperation operation = new InitializeVerifyBusinessEnvironmentOperation();
				operation.initialize(operationInfo, form.getCardId());
				operation.execute();
			}
			updateForm(operationInfo, form, true);
			return mapping.findForward(operationInfo.getCurrentStage().getName());
		}
		catch (FrontLogicException e)
		{
			saveError(request, e.getMessage());
			return mapping.findForward(ERROR_FORWARD);
		}
		catch (FrontException e)
		{
			saveError(request, e.getMessage());
			return mapping.findForward(ERROR_FORWARD);
		}
		catch (Exception e)
		{
			return processError(mapping, request, e, ERROR_FORWARD);
		}
	}

	/**
	 * @param mapping   мапинг текущего экшена
	 * @param form      текущая форма
	 * @param request   текущий реквест
	 * @param response  текущий респунс
	 * @return форвард на страницу подтверждения смс паролем
	 */
	public ActionForward initializeVerifyBySMS(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		return initializeVerify(ConfirmStrategyType.sms, mapping, (BusinessEnvironmentForm) form, request, response);
	}

	/**
	 * @param mapping   мапинг текущего экшена
	 * @param form      текущая форма
	 * @param request   текущий реквест
	 * @param response  текущий респунс
	 * @return форвард на страницу подтверждения push паролем
	 */
	public ActionForward initializeVerifyByPush(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		return initializeVerify(ConfirmStrategyType.push, mapping, (BusinessEnvironmentForm) form, request, response);
	}

	/**
	 * @param mapping   мапинг текущего экшена
	 * @param form      текущая форма
	 * @param request   текущий реквест
	 * @param response  текущий респунс
	 * @return форвард на страницу подтверждения чековым паролем
	 */
	public ActionForward initializeVerifyByCard(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		return initializeVerify(ConfirmStrategyType.card, mapping, (BusinessEnvironmentForm) form, request, response);
	}

	/**
	 * @param mapping   мапинг текущего экшена
	 * @param form      текущая форма
	 * @param request   текущий реквест
	 * @param response  текущий респунс
	 * @return форвард на страницу результата верификации
	 */
	public ActionForward verify(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			return next(mapping, form, request, response);
		}
		catch (Exception e)
		{
			return processError(mapping, request, e, ERROR_FORWARD);
		}
	}
}
