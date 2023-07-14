package com.rssl.phizic.web.client.login;

import com.rssl.phizic.business.clients.RemoteConnectionUDBOHelper;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.remoteConnectionUDBO.RemoteConnectionUDBOConfig;
import com.rssl.phizic.utils.store.Store;
import com.rssl.phizic.utils.store.StoreManager;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Предложение об удалённом заключении договора УДБО при входе в СБОЛ
 * User: kichinova
 * Date: 15.12.14
 * Time: 10:00
 */
public class ConnectUdboLoginAction extends OperationalActionBase
{
	private static final String FORWARD_BACK = "Back";
	protected static final String FORWARD_ERROR = "Error";
	protected static final String FORWARD_CANCEL = "Cancel";
	private static final String FORWARD_CONNECT_UDBO = "ConnectUdbo";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String,String> map = new HashMap<String, String>();
		map.put("button.connect",       "connect");
		map.put("button.notNow",        "notNow");
		map.put("button.back.to.main",  "backToMain");
		return map;
	}

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		//клиент не ВИП и карточный или СБОЛ и его ТБ разрешает заключение УДБО
		if (RemoteConnectionUDBOHelper.isShowMoreSbolMenuItem())
		{
			//если первый вход в 16 релиз - отображаем баннер предложения
			if (RemoteConnectionUDBOHelper.isFirstUserEnter16Release())
			{
				return mapping.findForward(FORWARD_SHOW);
			}
			//непервый вход
			else
			{
				// возможность работы без УДБО
				if (!RemoteConnectionUDBOHelper.isWorkWithoutUDBO())
				{
					//проверяем тип и карты клиента - переводим на форму заключения
					if (RemoteConnectionUDBOHelper.checkConnectAbility())
					{
						RemoteConnectionUDBOHelper.getFromSession().setRedirectToPayment(true);
						return mapping.findForward(FORWARD_CONNECT_UDBO);
					}
					else
					{
						//отображаем форму о невозможности продолжения работы
						updateFormData(form);
						return mapping.findForward(FORWARD_CANCEL);
					}
				}
				//работа без УДБО не запрещена, не должны были попасть на эту форму, только если неожиданно сменилось с запрещена на разрешена, кидаем на главную
				else
				{
					return mapping.findForward(FORWARD_BACK);
				}
			}
		}
		//клиент не подходит по типу не понятно как сюда попал, кидаем на главную
		else
		{
			return mapping.findForward(FORWARD_BACK);
		}
	}

	/**
	 * Запуск процедуры заключения договора
	 * @param mapping - маппинг
	 * @param form - форма
	 * @param request - реквест
	 * @param response - респонс
	 * @return экшенфорвард
	 * @throws Exception
	 */
	public ActionForward connect(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		RemoteConnectionUDBOHelper.setFirstUserEnter16Release(false);
		if (RemoteConnectionUDBOHelper.checkConnectAbility())
		{
			if (!ConfigFactory.getConfig(RemoteConnectionUDBOConfig.class).isWorkWithoutUDBO())
				RemoteConnectionUDBOHelper.getFromSession().setRedirectToPayment(true);
			return mapping.findForward(FORWARD_CONNECT_UDBO);
		}
		else
			return mapping.findForward(FORWARD_ERROR);
	}

	/**
	 * Отмена процедуры заключения договора
	 * @param mapping - маппинг
	 * @param form - форма
	 * @param request - реквест
	 * @param response - респонс
	 * @return экшенфорвард
	 * @throws Exception
	 */
	public ActionForward notNow(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		RemoteConnectionUDBOHelper.setFirstUserEnter16Release(false);
		if (ConfigFactory.getConfig(RemoteConnectionUDBOConfig.class).isWorkWithoutUDBO())
		{
			//кидаем на главную, там покажем сообщение
			RemoteConnectionUDBOHelper.getFromSession().setShowCancelUdboMessage(true);
			return mapping.findForward(FORWARD_BACK);
		}
		updateFormData(form);
		//закрываем сессию
		Store store = StoreManager.getCurrentStore();
		if (store != null)
		{
			store.clear();
		}
		return mapping.findForward(FORWARD_CANCEL);
	}

	/**
	 * Возврат на главную
	 * @param mapping - маппинг
	 * @param form - форма
	 * @param request - реквест
	 * @param response - респонс
	 * @return экшенфорвард
	 * @throws Exception
	 */
	public ActionForward backToMain(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return mapping.findForward(FORWARD_BACK);
	}

	private void updateFormData(ActionForm frm)
	{
		ConnectUdboLoginForm form = (ConnectUdboLoginForm)frm;
		form.setCancelMessageTitle(RemoteConnectionUDBOHelper.getCancelConnectUDBOMessageTitle());
		form.setCancelMessageText(RemoteConnectionUDBOHelper.getCancelConnectUDBOMessageText());
	}
}