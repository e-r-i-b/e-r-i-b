package com.rssl.phizic.web.regions;

import com.rssl.auth.csa.front.operations.regions.RegionListOperation;
import com.rssl.auth.csa.front.exceptions.FrontException;
import com.rssl.phizic.web.common.LookupDispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author komarov
 * @ created 18.03.2013
 * @ $Author$
 * @ $Revision$
 */
public class ShowRegionListAction extends LookupDispatchAction
{
	protected static final String FORWARD_START = "Start";
	
	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	protected void doFilter(RegionListOperation operation, ShowRegionListForm frm) throws Exception
	{
		frm.setRegions(operation.getRegionsList());
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowRegionListForm frm = (ShowRegionListForm) form;
		RegionListOperation operation = new RegionListOperation();
		Long id = frm.getId();
		operation.initialize(id);
		if(id == null || id == 0)
			doFilter(operation, frm);
		else
			saveRegion(operation, frm);

		return mapping.findForward(FORWARD_START);
	}

	private void saveRegion(RegionListOperation operation, ShowRegionListForm form) throws FrontException
	{
		operation.saveRegion();
		form.setSaved(true);
	}
}
