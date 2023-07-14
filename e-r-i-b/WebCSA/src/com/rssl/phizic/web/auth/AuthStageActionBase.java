package com.rssl.phizic.web.auth;

import com.rssl.auth.csa.front.exceptions.InterruptStageException;
import com.rssl.auth.csa.front.exceptions.ResetStageException;
import com.rssl.auth.csa.front.exceptions.ValidateException;
import com.rssl.auth.csa.front.operations.auth.OperationInfo;
import com.rssl.auth.csa.front.exceptions.FrontLogicException;
import com.rssl.auth.csa.front.operations.Operation;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.utils.StringHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author niculichev
 * @ created 19.09.2012
 * @ $Author$
 * @ $Revision$
 */
public abstract class AuthStageActionBase extends SecurityActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.put("button.begin", "begin");
		map.put("button.next", "next");
		return map;
	}

	/**
	 * Переключить состояние контекста операции
	 * @param info контекст операции
	 * @param valueSource источник значений для переключения
	 * @throws Exception
	 */
	protected void nextStage(OperationInfo info, FieldValuesSource valueSource) throws Exception
	{
		// синхронизируем, чтоб была хоть какая-то атомарность при переключения состояния
		synchronized (info)
		{
			// получает текущее состояние
			Stage currentStage = info.getCurrentStage();
			// валидируем в соответствии с ним данные
			Map<String, Object> formData =
					checkFormData(currentStage.getForm(info), valueSource);
			// берем его операцию
			Operation operation = currentStage.getOperation(info, formData);
			// исполняем
			operation.execute();
			// переключает состояние
			info.nextStage();
		}
    }

	/**
	 * Оболочка метода nextStage, для специфичных обработок ошибок из next
	 * @param mapping маппинг
	 * @param frm форма
	 * @param request запрос
	 * @param info контекст операции
	 * @return форвард в случае обработанной ошибки
	 * @throws Exception
	 */
	protected ActionForward doNextStage(ActionMapping mapping,  AuthStageFormBase frm, HttpServletRequest request, OperationInfo info) throws Exception
	{
		nextStage(info, getFieldValuesSource(frm));
		frm.setSuccessNextStage(true);

		return mapping.findForward(info.getCurrentName());
	}

	/**
	 * Оболочка метода nextStage, для специфичных обработок ошибок из begin
	 * @param mapping маппинг
	 * @param frm форма
	 * @param request запрос
	 * @param info контекст операции
	 * @return форвард в случае обработанной ошибки
	 * @throws Exception
	 */
	protected ActionForward doBeginStage(ActionMapping mapping,  AuthStageFormBase frm, HttpServletRequest request, OperationInfo info) throws Exception
	{
		nextStage(info, getFieldValuesSource(frm));
		frm.setSuccessNextStage(true);

		return mapping.findForward(info.getCurrentName());
	}

	/**
	 * Веб метод для обработки запроса на подтверждение по смс
	 * @param mapping маппинг
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return форвард
	 * @throws Exception
	 */
	public ActionForward begin(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AuthStageFormBase frm = (AuthStageFormBase) form;
		OperationInfo operationInfo = getOperationInfo();

		ActionForward forward = null;

		try
		{
			// переключаем состояние
			forward = doBeginStage(mapping, frm, request,  operationInfo);
		}
		catch (ResetStageException e)
        {
	        // сохраняем сообщение клиенту если есть
	        if(StringHelper.isNotEmpty(e.getMessage()))
	            saveError(request, e.getMessage());
            forward = mapping.findForward(RESET_FORWARD);
        }
		catch(ValidateException e)
		{
			saveErrors(request, e.getMessages());
			forward = mapping.findForward(ERROR_FORWARD);
		}
		catch (FrontLogicException e)
		{
			saveError(request, e.getMessage());
			forward = mapping.findForward(ERROR_FORWARD);
		}

		// обновляем на форме
		updateForm(operationInfo, frm, true);

		// если на этом закончили
		if(operationInfo.getCurrentStage() == null)
		{
			clearPayOrderData();
			return createCompleteForward(mapping, request);
		}

		// сохраняем контекст операции в сессии только если переключение прошло успешно
		if(frm.isSuccessNextStage())
			setOperationInfo(request, operationInfo);

		return forward;
	}

	/**
	 * Веб метод для обработки запроса на переключение состояния
	 * @param mapping маппинг
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return форвард
	 * @throws Exception
	 */
    public ActionForward next(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws java.lang.Exception
    {
	    AuthStageFormBase frm = (AuthStageFormBase) form;
        OperationInfo operationInfo = getOperationInfo(request);

        if(operationInfo == null)
        {
	        saveError(request, DEFAULT_ERROR_MESSAGE);
	        return useFullErrorPage() ? mapping.findForward(ERROR_FULL_PAGE_FORWARD) : mapping.findForward(ERROR_FORWARD);
        }

	    ActionForward forward = null;

        try
        {
	        forward = doNextStage(mapping, frm, request,  operationInfo);
        }
        catch (ResetStageException e)
        {
	        resetOperationInfo(request);
	        // сохраняем сообщение клиенту если есть
	        if(StringHelper.isNotEmpty(e.getMessage()))
	            saveError(request, e.getMessage());

	        forward = mapping.findForward(RESET_FORWARD);
        }
        catch (InterruptStageException e)
        {
	        saveError(request, e.getMessage());
	        resetOperationInfo(request);
	        forward = mapping.findForward(ERROR_FORWARD);
        }
        catch(ValidateException e)
		{
			saveErrors(request, e.getMessages());
			forward = mapping.findForward(operationInfo.getCurrentName());
		}
        catch (FrontLogicException e)
        {
            saveError(request, e.getMessage());
	        forward = mapping.findForward(operationInfo.getCurrentName());
        }

	    // обновляем форму
		updateForm(operationInfo, frm, frm.isSuccessNextStage());

	    if(operationInfo.getCurrentStage() == null)
		{
			resetOperationInfo(request);
			clearPayOrderData();
			return createCompleteForward(mapping, request);
		}

	    // Перезаписываем контекст операции и синхронизирующий токен если не был сброшен
	    if(!isResetOperationInfo(request))
		    setOperationInfo(request, operationInfo);

	    return forward;
    }

	protected ActionForward createCompleteForward(ActionMapping mapping, HttpServletRequest request)
	{
		return mapping.findForward(COMPLETE_FORWARD);
	}

	protected void updateForm(OperationInfo operationInfo, AuthStageFormBase frm, boolean isFirstShowForm)
	{
		frm.setOperationInfo(operationInfo);
	}

	protected List<String> getCaptchaMethodNames()
	{
		return Arrays.asList("begin");
	}

	protected boolean processSpecificCaptha(ActionForm form, HttpServletRequest request, String operationName) throws Exception
	{
		return false;
	}

	public ActionForward specificCaptcha(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AuthStageFormBase frm = (AuthStageFormBase) form;
		OperationInfo operationInfo = getOperationInfo(request);

		// контекста операции нет, начнем сначала
		if(operationInfo == null)
			return start(mapping, form, request, response);

		updateForm(operationInfo, frm, frm.isSuccessNextStage());
		// перезаписываем токен
		setOperationInfo(request, operationInfo);

		return mapping.findForward(operationInfo.getCurrentName());
	}

	/**
	 * @return контекста операции в начальном состоянии
	 */
	protected abstract OperationInfo getOperationInfo();

	/**
	 * очистка данных платежа, при оплате с внешней ссылки.
	 */
	protected void clearPayOrderData(){/*только для входа клиента*/};
}
