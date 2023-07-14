package com.rssl.phizic.web.unallowedbrowsers;

import com.rssl.auth.csa.front.unallowedbrowsers.ListDownloadLinksOperation;
import com.rssl.phizic.utils.store.StoreManager;
import com.rssl.phizic.web.common.LookupDispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Ёкшен ссылок на скачивание клиента дл€ мобильных устройств, зашедших через браузер.
 * @ author: Vagin
 * @ created: 28.11.2012
 * @ $Author
 * @ $Revision
 */
public class ListDownloadLinksAction extends LookupDispatchAction
{
	private static final String FULL_VERSION_KEY = "load.full_version";

	protected Map<String, String> getKeyMethodMap()
	{
		Map map = new HashMap<String,String>();
		map.put("loadFullVersion","loadFullVersion");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListDownloadLinksForm frm = (ListDownloadLinksForm)form;
		ListDownloadLinksOperation operation = new ListDownloadLinksOperation();
		frm.setLink(operation.getLink(request));
		return mapping.findForward("start");
	}

	public ActionForward loadFullVersion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		StoreManager.getCurrentStore().save(FULL_VERSION_KEY, true);
		return mapping.findForward("loadFullVersion");
	}
}
