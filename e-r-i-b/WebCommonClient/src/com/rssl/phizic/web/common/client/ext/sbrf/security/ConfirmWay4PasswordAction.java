package com.rssl.phizic.web.common.client.ext.sbrf.security;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.*;
import com.rssl.phizic.auth.passwordcards.PasswordCardNotAvailableException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.security.CallBackHandler;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.security.password.BlockedException;
import com.rssl.phizic.security.password.InvalidUserIdException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.store.Store;
import com.rssl.phizic.utils.store.StoreManager;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.common.confirm.ConfirmHelper;
import com.rssl.phizic.web.security.AuthenticationManager;
import com.rssl.phizic.web.security.ConfirmLoginActionBase;
import com.rssl.phizic.web.security.SecurityMessages;
import org.apache.struts.action.*;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author: Moshenko
 * Date: 13.04.2010
 * Time: 19:23:48
 */
public abstract class ConfirmWay4PasswordAction extends ConfirmLoginActionBase
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	protected static final String CONFIRM_REQUEST_KEY = ConfirmWay4PasswordAction.class.getName() + ".confirm_request";

	protected static final String FORWARD_SHOW  = "Show";
	private static final String FORWARD_BLOCK = "Block";

	///////////////////////////////////////////////////////////////////////////

	protected Map<String, String> getKeyMethodMap()
	{
		Map <String, String> keyMap= getAditionalKeyMethodMap();
		keyMap.put("button.preConfirm", "preConfirm");
        return keyMap;
	}

	protected Map<String, String> getAditionalKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		initConfirmForm((ConfirmWay4PasswordForm) form, request);

		return mapping.findForward(FORWARD_SHOW);
	}

	protected ConfirmStrategy initConfirmForm(ConfirmWay4PasswordForm form, HttpServletRequest request) throws Exception
	{
		ConfirmStrategy strategy = getStrategy(null);

		setAnotherStrategy(form,strategy);
		createSimpleStrategyRequest(strategy, form, request);
		return strategy;
	}

	protected ActionForward forwardError(ActionMapping mapping, ActionForm form, ConfirmRequest confirmRequest)
	{
		  return mapping.findForward(FORWARD_SHOW);
	}

	public ActionForward validate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException, BusinessLogicException, SecurityDbException
	{
		ConfirmWay4PasswordForm frm = (ConfirmWay4PasswordForm)form;
		Login login = (Login)getAuthenticationContext().getLogin();
		Store store = StoreManager.getCurrentStore();
		ConfirmRequest confirmRequest = (ConfirmRequest)store.restore(CONFIRM_REQUEST_KEY+login.getId());
		clearConfirmErrors(request, confirmRequest);

		try
		{
			ConfirmStrategy strategy = getStrategy(confirmRequest.getStrategyType());
			setAnotherStrategy(frm,strategy);
			ConfirmResponseReader reader = strategy.getConfirmResponseReader();
			reader.setValuesSource(new RequestValuesSource(request));
			
			frm.setConfirmRequest(confirmRequest);

			if(!reader.read())
			{
				saveConfirmErrors(reader.getErrors(), request, confirmRequest);

				return forwardError(mapping, frm, confirmRequest);
			}
			else
			{
				strategy.validate(login, confirmRequest, reader.getResponse());
			}
		}
		catch (SecurityLogicException e) // ошибка подтверждения
        {
	        saveConfirmErrors(Collections.singletonList(e.getMessage()), request, confirmRequest);

			// Если многократный ввод неверного пароля вызвал блокировку пользователя
	        if (e.getClass().equals(BlockedException.class))
			{
				Calendar blockUntil = Calendar.getInstance();
				SecurityConfig securityConfig = ConfigFactory.getConfig(SecurityConfig.class);
				blockUntil.add(Calendar.SECOND, securityConfig.getBlockedTimeout());
				CommonLogin loginAuth = AuthenticationContext.getContext().getLogin();
				PersonHelper.personLock(blockUntil, loginAuth, true);    // блокировка пользователя
				AuthenticationManager.abort();
				return forwardError(mapping, frm, confirmRequest);
			}
	        else
				return forwardError(mapping, frm, confirmRequest);
        }
	    catch (SecurityException e) //упал сервис
	    {
		    log.error(e.getMessage(), e);
		    saveConfirmErrors(Collections.singletonList("Сервис временно недоступен, попробуйте позже"), request, confirmRequest);
		    return  forwardError(mapping, frm, confirmRequest);
	    }

	  	completeStage();
		return nextStage(mapping, response);
	}

	protected ActionForward nextStage(ActionMapping mapping, HttpServletResponse response) throws BusinessException
	{
		return mapping.findForward(FORWARD_SHOW);
	}

	protected void doPreConfirm (HttpServletRequest request, ConfirmStrategy strategy, CallBackHandler callBackHandler, ConfirmRequest confirmRequest) throws SecurityDbException
	{
		try
		{
			PreConfirmObject preConfirmObject = strategy.preConfirmActions(callBackHandler);
			saveConfirmMessage(ConfirmHelper.getPreConfirmString(preConfirmObject),request,confirmRequest);
		}
		catch (SecurityException e)
		{
			ActionMessages errors  = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage( e.getMessage(), false));
			saveErrors(request, errors);
		}
		catch (SecurityLogicException e)
		{
			saveConfirmErrors(Collections.singletonList(SecurityMessages.translateException(e)), request, confirmRequest);
		}
	}

	public ActionForward preConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, CallBackHandler callBackHandler) throws Exception
	{
		Login login = (Login)getAuthenticationContext().getLogin();

		Store store = StoreManager.getCurrentStore();
		ConfirmRequest rq = (ConfirmRequest)store.restore(CONFIRM_REQUEST_KEY+login.getId());

		ConfirmWay4PasswordForm frm = (ConfirmWay4PasswordForm)form;
		frm.setConfirmRequest(rq);

		doPreConfirm(request, getStrategy(rq.getStrategyType()), fillCallBackHandler(login, callBackHandler), rq);

		return mapping.findForward(FORWARD_SHOW);
	}

	/**
	 * @param type - стратегия по умолчанию(если поддерживается композитная стратегия)
	 * @return стратегия подтверждения
	 */
	protected abstract ConfirmStrategy getStrategy(ConfirmStrategyType type);

	private ConfirmRequest createStrategyRequest(ConfirmStrategy strategy, ConfirmWay4PasswordForm form, HttpServletRequest request, boolean showAllErrors) throws BusinessException, BusinessLogicException
	{
		try
		{
			Login login = (Login) getAuthenticationContext().getLogin();
			ConfirmRequest rq = createChangeToReqest(login, form, strategy);

			if ((strategy instanceof iPasCompositeConfirmStrategy)&&
				(((iPasCompositeConfirmStrategy) strategy).getStrategy(ConfirmStrategyType.cap) != null))
						form.setHasCapButton(true);

			if (strategy.getWarning() != null)
			{
				Exception warning = strategy.getWarning();
				if(warning instanceof CompositeException)
				{
					for(Exception exception : ((CompositeException) warning).getExceptions())
					{
						saveRequestError(request, exception, showAllErrors);
						log.error(exception);
					}
				}
				else
				{
					saveRequestError(request, warning, showAllErrors);
				}
			}

			form.setConfirmRequest(rq);

			Store store = StoreManager.getCurrentStore();
			String key = CONFIRM_REQUEST_KEY+login.getId();
			store.save(key, rq);

			return rq;
		}
		catch (SecurityException e)
		{
			log.error(e.getMessage(), e);
			ActionMessages errors  = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage( e.getMessage(), false));
			saveErrors(request, errors);
			return null;
		}
	}

	private void saveRequestError(HttpServletRequest request, Exception exception, boolean showAllErrors)
	{
		if (showAllErrors || !(exception instanceof PasswordCardNotAvailableException))
			saveError(request, exception);
	}

	protected ConfirmRequest createSimpleStrategyRequest(ConfirmStrategy strategy, ConfirmWay4PasswordForm form, HttpServletRequest request) throws BusinessException, BusinessLogicException
	{
		return createStrategyRequest(strategy, form, request, false);
	}

	protected ConfirmRequest createStrategyRequest(ConfirmStrategy strategy, ConfirmWay4PasswordForm form, HttpServletRequest request) throws BusinessException, BusinessLogicException
	{
		return createStrategyRequest(strategy, form, request, true);
	}

	// устанавливаем наличие дополнительной стратегии подтверждения
	protected void setAnotherStrategy(ConfirmWay4PasswordForm form, ConfirmStrategy strategy)
	{
		form.setConfirmStrategy(strategy);
		if(strategy instanceof CompositeConfirmStrategy)
		{
			boolean anatherStrategy =((CompositeConfirmStrategy)strategy).getStrategies().size()>1;
			form.setAnotherStrategyAvailable(anatherStrategy);
		}
		else
		{
			form.setAnotherStrategyAvailable (false);
		}
	}

	/**
	 * Сохранение ошибок подтверждения. В зависимости от модуля(Веб или мобильное приложение) будет сохраняться либо в
	 * ConfirmRequest, либо в HttpServletRequest
	 * @param errors - список ошибок
	 * @param request
	 * @param confirmRequest
	 */
	protected abstract void saveConfirmErrors(List<String> errors, HttpServletRequest request, ConfirmRequest confirmRequest);

	/**
	 * Очищение ошибок. В зависимости от модуля очищаются либо в ConfirmRequest, либо в HttpServletRequest 
	 * @param httpServletRequest
	 * @param confirmRequest
	 */
	protected abstract void clearConfirmErrors(HttpServletRequest httpServletRequest, ConfirmRequest confirmRequest);
	/**
	 * Сохранение сообщений подтверждения. В зависимости от модуля(Веб или мобильное приложение) будет сохраняться либо в
	 * ConfirmRequest, либо в HttpServletRequest
	 * @param message - текст сообщения
	 * @param request
	 * @param confirmRequest
	 */
	protected abstract void saveConfirmMessage(String message, HttpServletRequest request, ConfirmRequest confirmRequest);

	private PreConfirmObject getPreConfirmObject(ConfirmWay4PasswordForm form, Login login, boolean useStoredValue) throws BusinessException, BusinessLogicException
	{
		String  confirmCardId = (String) form.getFields().get("confirmCardId");
		Boolean confirmByPan  = Boolean.parseBoolean((String) form.getFields().get("confirmByPan"));

		Long cardId = null;

		if (StringHelper.isNotEmpty(confirmCardId) || confirmByPan)
		{
			cardId = confirmByPan ? null : Long.valueOf(confirmCardId);
		}
		else
		{
			return null;
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("confirmUserId", ConfirmHelper.getUserIdByConfirmCard(cardId, login, useStoredValue));

		return new PreConfirmObject(params);
	}

	private CallBackHandler fillCallBackHandler(Login login, CallBackHandler callBackHandler)
	{
		callBackHandler.setConfirmableObject(login);
		callBackHandler.setLogin(login);
		if (login.getLastLogonDate() == null)
		{
			callBackHandler.setUseAlternativeRegistrations(true);
		}

		return callBackHandler;
	}

	private ConfirmRequest createChangeToReqest(Login login,ConfirmWay4PasswordForm form, ConfirmStrategy strategy) throws BusinessException, BusinessLogicException
	{
		ConfirmRequest rq;
		try
		{
			try
			{
				rq = strategy.createRequest(login, login, null, getPreConfirmObject(form, login, true));
			}
			catch (InvalidUserIdException ignore)
			{
				//в случае если по запросу вернулась ошибка неверного userId полученого по карте из базы.
				//Актуализируеим значение userId через МБ и отправляем запрос снова.
				rq = strategy.createRequest(login, login, null, getPreConfirmObject(form, login, false));
			}
		}
		catch (SecurityLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		return rq;
	}
}
