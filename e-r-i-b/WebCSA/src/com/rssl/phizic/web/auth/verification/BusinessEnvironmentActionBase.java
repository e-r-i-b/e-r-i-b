package com.rssl.phizic.web.auth.verification;

import com.rssl.phizic.config.FrontSettingHelper;
import com.rssl.phizic.config.CSAFrontConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.web.auth.AuthStageActionBase;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.auth.csa.front.operations.auth.ErrorMessages;
import com.rssl.auth.csa.front.operations.auth.OperationInfo;
import com.rssl.auth.csa.front.operations.auth.verification.ConfirmVerifyBusinessEnvironmentOperation;
import com.rssl.auth.csa.front.operations.auth.verification.BusinessEnvironmentOperationInfo;
import com.rssl.auth.csa.front.exceptions.NotCorrectToken;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Ёкшен дл€ интеграции с деловой средой
 *
 * @author akrenev
 * @ created 20.03.2013
 * @ $Author$
 * @ $Revision$
 */

public abstract class BusinessEnvironmentActionBase extends AuthStageActionBase
{
	protected static final String NOT_ACCESS_FORWARD_NAME = "notAccess";

	protected ActionForward doStart(ActionMapping mapping, BusinessEnvironmentForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		form.setOperationInfo(getOperationInfo(request, false));
		// все хорошо -- продолжаем выполн€ть "старт"
		return super.start(mapping, form, request, response);
	}

	protected String getCustId(HttpServletRequest request, BusinessEnvironmentForm form) throws NotCorrectToken
	{
		//берем внешний идентификатор клиента
		String clientExternalId = form.getCustId(request);
		//если на форме нет, то ищем в контексте
		if (StringHelper.isNotEmpty(clientExternalId))
			return clientExternalId;

		OperationInfo operationInfo = getOperationInfo(request, false);
		if (operationInfo instanceof BusinessEnvironmentOperationInfo)
			return ((BusinessEnvironmentOperationInfo) operationInfo).getClientExternalId();

		return StringUtils.EMPTY;
	}

	protected ActionForward processError(ActionMapping mapping, HttpServletRequest request, Exception exception, String errorForward)
	{
		log.error(exception.getMessage(), exception);
		CSAFrontConfig frontConfig = ConfigFactory.getConfig(CSAFrontConfig.class);
		saveError(request, ErrorMessages.getMessage(ConfirmVerifyBusinessEnvironmentOperation.class.getName(), frontConfig.getBusinessEnvironmentUserURL()));
	    return mapping.findForward(errorForward);
	}

	@Override
	public final ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			//есть ли доступ к функционалу
			if (!FrontSettingHelper.isAccessToBusinessEnvironment())
				return mapping.findForward(NOT_ACCESS_FORWARD_NAME);

			ActionForward forward = doStart(mapping, (BusinessEnvironmentForm) form, request, response);
			if (!forward.getRedirect())
				return forward;
			ActionRedirect redirect = new ActionRedirect(forward);
			String clientExternalId = getCustId(request, (BusinessEnvironmentForm) form);
			redirect.addParameter(BusinessEnvironmentForm.CUST_ID_PARAMETER_NAME, clientExternalId);
			return redirect;
		}
		catch (Exception e)
		{
			return processError(mapping, request, e, START_FORWARD);
		}
	}

	@Override
	protected OperationInfo getOperationInfo(HttpServletRequest request) throws NotCorrectToken
	{
		return getOperationInfo(request, false);
	}
}
