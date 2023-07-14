package com.rssl.phizic.web.client.advertising;

import com.rssl.phizic.logging.advertising.AdvertisingLogEntryType;
import com.rssl.phizic.logging.advertising.AdvertisingLogHelper;
import com.rssl.phizic.operations.advertising.ViewAdvertisingBlockOperation;
import com.rssl.phizic.web.actions.AsyncOperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lukina
 * @ created 23.12.2011
 * @ $Author$
 * @ $Revision$
 */

public class ViewAdvertisingBlockAction extends AsyncOperationalActionBase
{
	protected static final String FORWARD_START = "Start";
	private static final String FROM_BANNER_ATTRIBUTE = "fromBanner";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.click", "click");
		map.put("button.operation", "operation");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ViewAdvertisingBlockForm frm = (ViewAdvertisingBlockForm) form;
		ViewAdvertisingBlockOperation operation = createOperation(ViewAdvertisingBlockOperation.class);
		operation.initialize(frm.getCurrentBannerId());
		frm.setAdvertisingBlock(operation.getAdvertisingBlock());
		frm.setBannersList(operation.getBannersList());

		if (operation.getAdvertisingBlock() != null)
			AdvertisingLogHelper.addLogEntry(operation.getAdvertisingBlock().getId(), AdvertisingLogEntryType.SHOW);

		return mapping.findForward(FORWARD_START);
	}

	public ActionForward click(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ViewAdvertisingBlockForm frm = (ViewAdvertisingBlockForm) form;
		Long currentBannerId = frm.getCurrentBannerId();
		request.getSession().setAttribute(FROM_BANNER_ATTRIBUTE, currentBannerId);
		AdvertisingLogHelper.addLogEntry(currentBannerId, AdvertisingLogEntryType.CLICK);
		return null;
	}

	public ActionForward operation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ViewAdvertisingBlockForm frm = (ViewAdvertisingBlockForm) form;
		AdvertisingLogHelper.addLogEntry(frm.getCurrentBannerId(), AdvertisingLogEntryType.OPERATION);
		return null;
	}

}
