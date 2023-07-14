package com.rssl.phizic.web.client.userprofile;

import com.rssl.phizic.business.profile.TutorialProfileState;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.util.PersonInfoUtil;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Работа с признаками для новинок в профиле пользователя
 * @author miklyaev
 * @ created 22.04.14
 * @ $Author$
 * @ $Revision$
 */

public class ProfileNoveltiesAction extends OperationalActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.closeNoveltyState", "closeNoveltyState");
		map.put("button.setOldPersonalInfo", "setOldPersonalInfo");
		map.put("button.setOldBillsToPay", "setOldBillsToPay");
		map.put("button.setPromoMinimized", "setPromoMinimized");
		map.put("button.setPromoClosed", "setPromoClosed");
		map.put("button.setMobileItemsMovedClosed", "setMobileItemsMovedClosed");
		return map;
	}

	private ActionForward getEmptyPage(HttpServletResponse response) throws Exception
	{
	    response.getOutputStream().println(" ");
		return null;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return getEmptyPage(response);
	}

	/**
	 * Закрытие стейта новинок пользователем
	 * @param mapping - список экшенов
	 * @param form - форма
	 * @param request - запрос
	 * @param response - ответ
	 * @return форвард
	 * @throws Exception
	 */
	public ActionForward closeNoveltyState(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PersonInfoUtil.closeProfileBubble();
		return getEmptyPage(response);
	}

	/**
	 * Отключает необходимость показа знака новизны для пункта "Личная информация"
	 * @param mapping - список экшенов
	 * @param form - форма
	 * @param request - запрос
	 * @param response - ответ
	 * @return форвард
	 * @throws Exception
	 */
	public ActionForward setOldPersonalInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PersonInfoUtil.setOldPersonalInfo();
		return getEmptyPage(response);
	}

	/**
	 * Отключает необходимость показа знака новизны для пункта "Автопоиск счетов к оплате"
	 * @param mapping - список экшенов
	 * @param form - форма
	 * @param request - запрос
	 * @param response - ответ
	 * @return форвард
	 * @throws Exception
	 */
	public ActionForward setOldBillsToPay(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PersonInfoUtil.setOldBillsToPay();
		return getEmptyPage(response);
	}

	/**
	 * Устанавливает свёрнутое состояние промо при входе клиента в профиль
	 * @param mapping - список экшенов
	 * @param form - форма
	 * @param request - запрос
	 * @param response - ответ
	 * @return форвард
	 * @throws Exception
	 */
	public ActionForward setPromoMinimized(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PersonInfoUtil.setPromoState(TutorialProfileState.MINIMIZED);
		return getEmptyPage(response);
	}

	/**
	 * Устанавливает закрытое состояние промо при входе клиента в профиль
	 * @param mapping - список экшенов
	 * @param form - форма
	 * @param request - запрос
	 * @param response - ответ
	 * @return форвард
	 * @throws Exception
	 */
	public ActionForward setPromoClosed(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PersonInfoUtil.setPromoState(TutorialProfileState.CLOSED);
		return getEmptyPage(response);
	}

	/**
	 * Устанавливает состояние настройки показа сообщения о перемещении пунктов "Мобильный банк" и "Мобильные приложения" в закрыто
	 * @param mapping - список экшенов
	 * @param form - форма
	 * @param request - запрос
	 * @param response - ответ
	 * @return форвард
	 * @throws Exception
	 */
	public ActionForward setMobileItemsMovedClosed(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PersonInfoUtil.setMobileItemsMovedClosed();
		return getEmptyPage(response);
	}

	protected boolean getEmptyErrorPage()
	{
		return true;
	}
}
