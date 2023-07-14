package com.rssl.phizic.web.auth.verification;

import com.rssl.auth.csa.front.operations.auth.verification.BusinessEnvironmentOperationInfo;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.auth.verification.satges.Stages;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Экшен входя клиента для деловой среды
 *
 * @author akrenev
 * @ created 01.04.2013
 * @ $Author$
 * @ $Revision$
 */

public class LoginBusinessEnvironmentAction extends BusinessEnvironmentActionBase
{
	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> keyMethodMap = new HashMap<String, String>();
		keyMethodMap.put("button.login", "login");
		return keyMethodMap;
	}

	@Override
	protected BusinessEnvironmentOperationInfo getOperationInfo()
	{
		return new BusinessEnvironmentOperationInfo(Stages.LOGIN);
	}

	@Override
	public ActionForward doStart(ActionMapping mapping, BusinessEnvironmentForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		//берем внешний идентификатор клиента
		String clientExternalId = getCustId(request, form);
		//если он не валиден сразу рисуем jsp (больше никаких действий не нужно производить)
		if (StringHelper.isEmpty(clientExternalId))
			return mapping.findForward(START_FORWARD);

		//сохраняем информацию о клиенте
		BusinessEnvironmentOperationInfo operationInfo = getOperationInfo();
		operationInfo.setClientExternalId(clientExternalId);
		setOperationInfo(request, operationInfo);
		return super.doStart(mapping, form, request, response);
	}

	/**
	 * @param mapping   мапинг текущего экшена
	 * @param form      текущая форма
	 * @param request   текущий реквест
	 * @param response  текущий респунс
	 * @return форвард на страницу соглашения или выбора коннектора
	 */
	public ActionForward login(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			ActionForward forward = next(mapping, form, request, response);
			if (!forward.getRedirect())
				return forward;
			ActionRedirect redirect = new ActionRedirect(forward);
			String clientExternalId = getCustId(request, (BusinessEnvironmentForm) form);
			redirect.addParameter(BusinessEnvironmentForm.CUST_ID_PARAMETER_NAME, clientExternalId);
			return redirect;
		}
		catch (Exception e)
		{
			return processError(mapping, request, e, ERROR_FORWARD);
		}
	}
}
