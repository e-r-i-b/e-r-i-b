package com.rssl.phizic.web.client.connectUdbo;

import com.rssl.phizic.business.clients.RemoteConnectionUDBOHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
//import com.rssl.phizic.web.ext.sbrf.SBRFLoginActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Экшен для страницы удалённого заключения договора УДБО
 * User: kichinova
 * Date: 15.12.14
 * Time: 10:00
 */
public class ConnectUdboAction extends OperationalActionBase
{
	private static final String FORWARD_ERROR = "Error";
	private static final String FORWARD_SUCCESS = "Success";
	private static final String FORWARD_CANCEL = "Cancel";

	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.put("button.connect", "connect");
		map.put("button.notNow",  "notNow");
		return map;
	}

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if (RemoteConnectionUDBOHelper.isShowMoreSbolNovelty())
			RemoteConnectionUDBOHelper.setShowMoreSbolNovelty(false);
		return mapping.findForward(FORWARD_SHOW);
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
		if (RemoteConnectionUDBOHelper.checkConnectAbility())
		{
			ActionRedirect redirect = new ActionRedirect(mapping.findForward(FORWARD_SUCCESS));
			redirect.addParameter("form", "RemoteConnectionUDBOClaim");
			return redirect;
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
		return new ActionRedirect(mapping.findForward(FORWARD_CANCEL));
	}
}