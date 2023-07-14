package com.rssl.phizic.web.client.quick.pay;

import com.rssl.phizic.business.departments.Department;
import static com.rssl.phizic.logging.quick.pay.Constants.CLICKED_BLOCK_IDS;
import com.rssl.phizic.logging.quick.pay.PanelLogEntryType;
import com.rssl.phizic.logging.quick.pay.QuickPaymentPanelLogHelper;
import com.rssl.phizic.web.actions.AsyncOperationalActionBase;
import com.rssl.phizic.web.ext.sbrf.DepartmentViewUtil;
import com.rssl.phizic.web.util.HttpSessionUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author komarov
 * @ created 20.11.2012
 * @ $Author$
 * @ $Revision$
 */

public class QuickPaymentPanelLogAction extends AsyncOperationalActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		return Collections.emptyMap();
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		QuickPaymentPanelLogForm frm = (QuickPaymentPanelLogForm) form;
		Long panelId    = frm.getQuickPaymentPanelBlokId();
		if(panelId == null)   
			return null;

		List<Long> ids = HttpSessionUtils.getSessionAttribute(request, CLICKED_BLOCK_IDS);
		if(CollectionUtils.isEmpty(ids))
			ids = new ArrayList<Long>();
		if(!ids.contains(panelId))
		{
			ids.add(panelId);
			request.getSession().setAttribute(CLICKED_BLOCK_IDS, ids);
		}

		addLogEntry(panelId, PanelLogEntryType.CLICK);
		return null;
	}

	private void addLogEntry(Long blokId, PanelLogEntryType type) throws Exception
	{
		if (blokId == null)
			return;

		Department department = DepartmentViewUtil.getCurrentDepartmentFromContext();
		if(department == null)
			return;

		QuickPaymentPanelLogHelper.addLogEntry(blokId, type, null, department.getRegion());
	}
}
