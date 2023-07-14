package com.rssl.phizic.web.actions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.timeoutsessionrequest.TimeoutSession;
import com.rssl.phizic.business.timeoutsessionrequest.TimeoutSessionService;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.http.UrlBuilder;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 19.07.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Базовый класс для востановления данных протухшей сессии.   
 */
public abstract class SessionDataParameterAction extends EditActionBase
{
	private static final TimeoutSessionService service = new TimeoutSessionService();

	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws BusinessException
	{
		SessionDataParameterForm form = (SessionDataParameterForm) frm;
		String sessionData = form.getSessionData();
		if(StringHelper.isEmpty(sessionData))
			return;

		TimeoutSession timeoutSession = service.getByRandomRecordId(sessionData);
		if (timeoutSession != null)
		{
			updateFormBySessionData(form,timeoutSession);
			service.remove(timeoutSession);
		}
	}

	private void updateFormBySessionData(SessionDataParameterForm form, TimeoutSession timeoutSession) throws BusinessException
	{
		String parametersString = timeoutSession.getParametres();
		if(StringHelper.isEmpty(parametersString))
			return;
		UrlBuilder urlBuilder = new UrlBuilder();
		urlBuilder.setUrl(timeoutSession.getFullUrl());
		Map<String, String> parameters = urlBuilder.getDecodedParametersMap();

		try
		{
			//заполняем форму параметрами от "протухшей" сессии
			BeanUtils.populate(form,parameters);			
		}
		catch (IllegalAccessException e)
		{
			throw new BusinessException("Не удалось восстановить данные протухшей сессии",e);
		}
		catch (InvocationTargetException e)
		{
			throw new BusinessException("Не удалось восстановить данные протухшей сессии",e);
		}
	}

}
