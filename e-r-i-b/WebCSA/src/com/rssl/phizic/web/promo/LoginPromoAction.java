package com.rssl.phizic.web.promo;

import com.rssl.auth.csa.front.business.promo.PromoterSession;
import com.rssl.phizic.business.promoters.PromoChannel;
import com.rssl.auth.csa.front.operations.promo.PromoSessionOperation;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.validators.strategy.DefaultValidationStrategy;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.LookupDispatchAction;
import com.rssl.phizic.web.struts.forms.FormHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.*;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ author: Gololobov
 * @ created: 10.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class LoginPromoAction extends LookupDispatchAction
{
	private static final String FORWARD_START = "start";

	protected Map<String, String> getKeyMethodMap()
	{
		Map <String, String> keyMap= new HashMap<String,String>();
		keyMap.put("button.loginPromo", "loginPromo");
		return keyMap;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		PromoSessionOperation operation = new PromoSessionOperation();
		LoginPromoForm frm = (LoginPromoForm) form;
		try
		{
			//Заполняем данные формы из Cookies, если они есть
			fillFormDataFromCookie(frm, request, operation);
			frm.setPromoChannels(operation.getPromoChannels());
			frm.setPromoTbList(operation.getPromoterTbList());
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
		return mapping.findForward(FORWARD_START);
	}

	/**
	 * Заполнение формы данными из Cookies, если есть
	 * @param frm
	 * @param request
	 * @param operation
	 */
	private void fillFormDataFromCookie(LoginPromoForm frm, HttpServletRequest request, PromoSessionOperation operation) throws BusinessException, UnsupportedEncodingException
	{
		Map<String, Object> promoterDataMap = operation.getPromoterDataFromCookie(request);

		if (CollectionUtils.isEmpty(frm.getFields().entrySet()) && 
			CollectionUtils.isNotEmpty(promoterDataMap.entrySet()))
			frm.setFields(promoterDataMap);
	}

	/**
	 * Регистрация промоутера по введенным данным
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward loginPromo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PromoSessionOperation operation = new PromoSessionOperation();
		LoginPromoForm frm = (LoginPromoForm) form;
		if (CollectionUtils.isNotEmpty(frm.getFields().entrySet()) &&
			isLogingPromoter(form, request, response, operation))
			return mapping.findForward(FORWARD_START);
		return start(mapping, form, request, response);
	}

	private boolean isLogingPromoter(ActionForm form, HttpServletRequest request, HttpServletResponse response, PromoSessionOperation operation) throws Exception
	{
		LoginPromoForm frm = (LoginPromoForm) form;
		//валидация данных
		Map<String, Object> formData = checkFormData(request, getEditForm(), getFieldValuesSource(frm));
		if(formData != null)
		{
			//проверка промоутера по БД и открытие смены (с закрытием старой смены, если есть)
			PromoterSession promoterSession = operation.addPromoSession(operation.getPromoterData(frm.getFields()));
			//Добавление/обновление cookie данных промоутера
			if (promoterSession != null)
			{
				operation.addOrUpdateAllCookies(promoterSession, request, response);
				frm.setTempCookie(operation.getTempCookieData(promoterSession, request));
				return true;
			}
		}
		return false;
	}

	private Form getEditForm()
	{
		return LoginPromoForm.FORM;
	}

	protected FieldValuesSource getFieldValuesSource(LoginPromoForm form)
	{
		return new MapValuesSource(form.getFields()) ;
	}

    protected Map<String, Object> checkFormData(HttpServletRequest request, Form form, FieldValuesSource valuesSource)
	{
		FormProcessor<ActionMessages, ?> processor =
				FormHelper.newInstance(valuesSource, form, DefaultValidationStrategy.getInstance());

		if(!processor.process())
		{
			saveErrors(request, processor.getErrors());
			return null;
		}

		Map<String, Object> result = processor.getResult();
		if (CollectionUtils.isNotEmpty(result.entrySet()))
		{
			String channelId = (String) result.get("channelId");
			String vsp = (String) result.get("vsp");
			if (!channelId.equalsIgnoreCase(PromoChannel.BAW.name()) && StringHelper.isEmpty(vsp))
			{
				ActionMessage msg = new ActionMessage("Укажите код ВСП", false);
				ActionMessages msgs = new ActionMessages();
				msgs.add(ActionMessages.GLOBAL_MESSAGE, msg);
				saveErrors(request, msgs);
				return null;
			}
		}

		return processor.getResult();
	}
}
