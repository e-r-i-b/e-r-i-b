package com.rssl.phizic.web.auth;

import com.rssl.auth.csa.front.business.promo.PromoterSession;
import com.rssl.auth.csa.front.exceptions.NotCorrectToken;
import com.rssl.auth.csa.front.exceptions.ValidateException;
import com.rssl.auth.csa.front.operations.auth.OperationInfo;
import com.rssl.auth.csa.front.operations.promo.PromoSessionOperation;
import com.rssl.auth.csa.front.security.IPSecurityManager;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.validators.strategy.DefaultValidationStrategy;
import com.rssl.phizic.captcha.CaptchaServlet;
import com.rssl.phizic.config.CSAFrontConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.store.Store;
import com.rssl.phizic.utils.store.StoreManager;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.auth.payOrder.PayOrderHelper;
import com.rssl.phizic.web.common.LookupDispatchAction;
import com.rssl.phizic.web.struts.forms.FormHelper;
import com.rssl.phizic.web.util.HttpSessionUtils;
import com.rssl.phizic.web.util.RequestHelper;
import org.apache.commons.lang.BooleanUtils;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Базоый класс для аутентификации
 * @author niculichev
 * @ created 17.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class AuthenticationActionBase extends LookupDispatchAction
{
	protected static final String DEFAULT_ERROR_MESSAGE = StrutsUtils.getMessage("message.default.error", "commonBundle");
	protected static final String OPERATION_INFO_TOKEN  = new RandomGUID().getStringValue();
	protected static final String OPERATION_INFO_RESET = OperationInfo.class.getName() + ".RESET";
	protected static final String DEFAULT_CAPTCHA_SERVLET_NAME  = "captchaServlet";

	protected static final String START_FORWARD     = "start";
	protected static final String REDIRECT_FORWARD  = "redirect";
	protected static final String RESET_FORWARD     = "reset";
	protected static final String ERROR_FORWARD     = "error";
	protected static final String COMPLETE_FORWARD  = "complete";
	protected static final String ERROR_FULL_PAGE_FORWARD  = "errorFullPage";

	protected static final IPSecurityManager ipSecurityManager = IPSecurityManager.getIt();

	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		setMainCurrentUrl();
        AuthenticationFormBase frm = (AuthenticationFormBase) form;

		//Показывать ссылку на стр. промоутера только если есть открытая смена промоутера
		PromoSessionOperation promoSessionOperation = new PromoSessionOperation();
		PromoterSession promoterSession = promoSessionOperation.getPromoterOpenedSessionBySessionIdFromCookie(request);
	    frm.setIsPromoCookie(promoterSession != null);
		storePayOrderInfo(request, frm);

		// установка признаков активности капчи
		setActiveCaptha(frm, request);
		return mapping.findForward(START_FORWARD);
	}

	private void setActiveCaptha(AuthenticationFormBase frm, HttpServletRequest request)
	{
		CSAFrontConfig config = ConfigFactory.getConfig(CSAFrontConfig.class);
		if(!config.isUseCaptchaRestrict())
		{
			frm.setField(AuthenticationFormBase.NEED_TURING_TEST, false);
			return;
		}

		String ipAddress = RequestHelper.getIpAddress(request);
		try
		{
			// обновляем таблицы с последними входами по ip адресам
			ipSecurityManager.processUserAction(ipAddress);
		}
		catch (Throwable t)
		{
			log.error("Ошибка при обновлении кэша для проверки частоты запросов", t);
		}

		// уже установлена или должна быть установлена
		if (isNeedConstantTuringTest() || !ipSecurityManager.userTrusted(ipAddress))
			CaptchaServlet.setActiveCaptha(request, DEFAULT_CAPTCHA_SERVLET_NAME);

		frm.setField(AuthenticationFormBase.NEED_TURING_TEST, CaptchaServlet.isActiveCaptha(request, DEFAULT_CAPTCHA_SERVLET_NAME));
	}

	protected Map<String, Object> checkFormData(Form form, FieldValuesSource valuesSource) throws ValidateException
	{
		FormProcessor<ActionMessages, ?> processor =
				FormHelper.newInstance(valuesSource, form, DefaultValidationStrategy.getInstance());
		
		if(!processor.process())
			throw new ValidateException(processor.getErrors());

		return processor.getResult();
	}

	protected Form getEditForm(AuthenticationFormBase form)
	{
		return FormBuilder.EMPTY_FORM;	
	}

	protected FieldValuesSource getFieldValuesSource(AuthenticationFormBase form)
	{
		return new MapValuesSource(form.getFields()) ;
	}

	protected String getToken(HttpServletRequest request)
    {
	    return HttpSessionUtils.getSessionAttribute(request, Globals.TRANSACTION_TOKEN_KEY);
    }

	protected OperationInfo getOperationInfo(HttpServletRequest request, boolean check) throws NotCorrectToken
	{
		Store store = StoreManager.getCurrentStore();
		synchronized (store)
		{
			if(check && !this.isTokenValid(request))
			{
				throw new NotCorrectToken();
			}

			return (OperationInfo) store.restore(OPERATION_INFO_TOKEN);
		}
	}

	protected void resetOperationInfo(HttpServletRequest request) throws NotCorrectToken
	{
		Store store = StoreManager.getCurrentStore();
		synchronized (store)
		{
			if(!this.isTokenValid(request, true))
			{
				throw new NotCorrectToken();
			}

			store.remove(OPERATION_INFO_TOKEN);
			resetToken(request);
			request.setAttribute(OPERATION_INFO_RESET, true);
		}
	}

	/**
	 * Был ли сброшена информация об операция в текущем запросе
	 * @param request текущий запрос
	 * @return true - в текущем запросе была сброшена информация об операции
	 */
	protected boolean isResetOperationInfo(HttpServletRequest request)
	{
		return BooleanUtils.isTrue((Boolean)request.getAttribute(OPERATION_INFO_RESET));
	}

	protected OperationInfo getOperationInfo(HttpServletRequest request) throws NotCorrectToken
	{
		return getOperationInfo(request, true);
	}

	protected void setOperationInfo(HttpServletRequest request, OperationInfo info)
	{
		Store store = StoreManager.getCurrentStore();
		synchronized (store)
		{
			saveToken(request);
			store.save(OPERATION_INFO_TOKEN, info);
		}
	}

	/**
	 * Заполняем данными из запроса поля формы и параметры в сессии.
	 * Если в реквесте нет, ищем в хранилище и заполняем поля формы.
	 * @param request - текущий request
	 * @param frm - главная форма.
	 */
	protected void storePayOrderInfo(HttpServletRequest request, AuthenticationFormBase frm)
	{
		PayOrderHelper.clearPayOrderSessionData();
		frm.setField(AuthenticationFormBase.IS_PAYORDER, false);
	}

	protected boolean isNeedConstantTuringTest()
	{
		CSAFrontConfig config = ConfigFactory.getConfig(CSAFrontConfig.class);
		return config.isUseCaptchaRestrict() && config.isConstantCaptchaControlEnabled();
	}
}
