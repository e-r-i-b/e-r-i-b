package com.rssl.phizic.web.atm.dictionaries;

import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.dictionaries.regions.RegionsListOperation;
import com.rssl.phizic.security.SecurityUtil;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Dorzhinov
 * @ created 10.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class ShowRegionsListATMAction extends OperationalActionBase
{
	protected static final String FORWARD_START = "Start";
	
	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowRegionsListATMForm frm = (ShowRegionsListATMForm) form;
		RegionsListOperation operation = null;

		//Для ATM делаем возможность получить справочник регионов без авторизации
		if (ApplicationUtil.isATMApi() && !SecurityUtil.isAuthenticationComplete())
			operation = new RegionsListOperation();
		else
			operation = createOperation("RegionsListOperation", "RurPayJurSB");

		Long parentId = frm.getParentId();
		if(parentId == null)
			operation.initialize();
		else
			operation.initialize(parentId);

		frm.setRegion(operation.getRegion());
		doFilter(operation, frm);

		return mapping.findForward(FORWARD_START);
	}

	protected void doFilter(RegionsListOperation operation, ShowRegionsListATMForm frm) throws Exception
	{
		Query query = operation.createQuery("list");
		Region region = operation.getRegion();
		query.setParameter("parent", region.getId());
		List<Region> regions = query.executeList();
		frm.setRegions(regions);
	}
}
