package com.rssl.phizic.web.common.client.cards.delivery;

import com.rssl.phizic.web.actions.AsyncOperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author akrenev
 * @ created 25.04.2014
 * @ $Author$
 * @ $Revision$
 *
 * сохранение состояния закрытия сообщения о возможности подписаться
 */

public class CloseEmailDeliveryMessageAction extends AsyncOperationalActionBase
{
	public static final String CLOSE_EMAIL_DELIVERY_MESSAGE_ATTRIBUTE_NAME = "CloseEmailDeliveryMessage";
	public static final String RESULT_FORWARD = "Result";

	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		return Collections.singletonMap("closeEmailDeliveryMessage", "close");
	}

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		throw new UnsupportedOperationException("Для аякс экшена CloseEmailDeliveryMessageAction не поддерживается метод start.");
	}

	/**
	 * закрытие окна
	 * @param mapping  стратс-маппинг
	 * @param frm     стратс-форма
	 * @param request  запрос
	 * @param response ответ
	 * @return форвард
	 * @throws Exception
	 */

	@SuppressWarnings("UnusedParameters")
	public ActionForward close(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CloseEmailDeliveryMessageForm form = (CloseEmailDeliveryMessageForm) frm;
		HttpSession session = request.getSession(false);
		//noinspection unchecked
		Map<Long, Boolean> visibility = (Map<Long, Boolean>) session.getAttribute(CLOSE_EMAIL_DELIVERY_MESSAGE_ATTRIBUTE_NAME);
		if (visibility == null)
		{
			visibility = new HashMap<Long, Boolean>();
			session.setAttribute(CLOSE_EMAIL_DELIVERY_MESSAGE_ATTRIBUTE_NAME, visibility);
		}
		visibility.put(form.getCardId(), true);
		return mapping.findForward(RESULT_FORWARD);
	}
}
