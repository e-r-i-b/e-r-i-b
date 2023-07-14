package com.rssl.phizic.web.guest.registration;

import com.rssl.auth.csa.wsclient.exceptions.LoginAlreadyRegisteredException;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.helpers.GuestRegistrationHelper;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.guestEntry.CreateGuestProfileOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.util.PersonSettingsUtil;
import org.apache.struts.Globals;
import org.apache.struts.action.*;
import org.apache.struts.util.TokenProcessor;

import java.security.AccessControlException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author usachev
 * @ created 30.01.15
 * @ $Author$
 * @ $Revision$
 * Action для регистрации нового гостя в СБОЛ
 */
public class CreateGuestProfileAction extends OperationalActionBase
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_WEB);
	private static final String ANSWER = "Answer";
	private static final String CAPTCHA_CODE = "captchaCode";
	private static final String POPUP = "popupError";
	private static final ActionMessage MESSAGE_ERROR_CAPTCHA = new ActionMessage(CAPTCHA_CODE, new ActionMessage("Вы неправильно ввели код с картинки", false));
	private final ActionMessage OPERATION_NOT_ACCESS = new ActionMessage(POPUP, new ActionMessage(getResourceMessage("commonBundle", "error.errorHeader"), false));

	/**
	 * Метод для регистрации пользователя
	 */
	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CreateGuestProfileForm frm = (CreateGuestProfileForm) form;
		frm.setSuccess(false);
		//Проверка токена
		try
		{
			checkToken(request);
		}catch (BusinessException e)
		{
			log.error(e);
			saveError(request, OPERATION_NOT_ACCESS);
			return mapping.findForward(ANSWER);
		}
		//Установка нового токена
		TokenProcessor.getInstance().saveToken(request);

		//Проверка CAPTCHA
		if (GuestRegistrationHelper.isNotCorrectCaptcha(request))
		{
			frm.setCaptcha(true);
			saveError(request, MESSAGE_ERROR_CAPTCHA);
			return mapping.findForward(ANSWER);
		}
		//Нужно ли отображать капчу
		frm.setCaptcha(GuestRegistrationHelper.needShowCaptcha(request));
		CreateGuestProfileOperation operation = null;
		try
		{
			operation = createOperation(CreateGuestProfileOperation.class);
		}
		catch (AccessControlException e)
		{
			log.error(e);
			saveError(request, new ActionMessage(POPUP, new ActionMessage(getResourceMessage("commonBundle", "error.accessDeny"), false)));
			return mapping.findForward(ANSWER);
		}catch (Exception e)
		{
			log.error(e);
			saveError(request, OPERATION_NOT_ACCESS);
			return mapping.findForward(ANSWER);
		}


		FormProcessor<ActionMessages, ?> processor =
				createFormProcessor(new MapValuesSource(frm.getFields()), CreateGuestProfileForm.FORM);

		if (!processor.process())
		{
			saveErrors(request, processor.getErrors());
		}
		else
		{
			Map<String, Object> res = processor.getResult();

			try
			{
				operation.execute(frm.getId(), frm.getType(),
						(String) res.get(CreateGuestProfileForm.LOGIN), (String) res.get(CreateGuestProfileForm.PASSWORD));
				frm.setSuccess(true);
				GuestRegistrationHelper.resetSecurityManager();
			}
			catch (LoginAlreadyRegisteredException e)
			{
				//Увеличиваем счётчик ввода логина
				GuestRegistrationHelper.incCheckLoginCount();
				//Проверяем ещё раз. Нужно ли показать капчу. В данном случа нельзя вызывать метод  GuestRegistrationHelper.needShowCaptcha, т.к. он уже обновил данные о последнем запросе
				frm.setCaptcha(frm.getCaptcha() || GuestRegistrationHelper.exceededCountCheckLogin());
				saveError(request, new ActionMessage(CreateGuestProfileForm.LOGIN, new ActionMessage(e.getMessage(), false)));
			}
			catch (BusinessLogicException e)
			{
				log.error(e);
				saveError(request, new ActionMessage(POPUP, new ActionMessage(e.getMessage(), false)));
			}
			catch (Exception e)
			{
				log.error(e);
				saveError(request, OPERATION_NOT_ACCESS);
			}
		}
		updateSession();
		return mapping.findForward(ANSWER);
	}

	private void updateSession()
	{
		currentRequest().getSession().setAttribute(PersonSettingsUtil.SHOW_GUEST_BANNER_ATTRIBUTE_NAME, true);
	}

	/**
	 * Проверка, валиден ли токен или нет. Если нет, то пробрасываем исключение
	 * @param request Запрос
	 * @throws BusinessException
	 */
	private void checkToken(HttpServletRequest request) throws BusinessException
	{
		TokenProcessor processor =  TokenProcessor.getInstance();
		if(!processor.isTokenValid(request))
		{
			String requiredToken =  (String) request.getSession().getAttribute(Globals.TRANSACTION_TOKEN_KEY);
			String foundToken = request.getParameter(org.apache.struts.taglib.html.Constants.TOKEN_KEY);
			processor.resetToken(request);
			throw new BusinessException("Не валиндный токен. Пришёл: " + foundToken + ". Ожидается: " + requiredToken);
		}
		processor.resetToken(request);
	}
}
