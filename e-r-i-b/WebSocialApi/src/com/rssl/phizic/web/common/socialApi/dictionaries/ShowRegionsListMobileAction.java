package com.rssl.phizic.web.common.socialApi.dictionaries;

import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.operations.dictionaries.regions.RegionsListOperation;
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
 * Справочник регионов
 * @author Dorzhinov
 * @ created 10.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class ShowRegionsListMobileAction extends OperationalActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	protected RegionsListOperation createRegionListOperation() throws BusinessException
	{
		return createOperation("RegionsListOperation", "RegionsList");
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException
	{
		ShowRegionsListMobileForm frm = (ShowRegionsListMobileForm) form;
		RegionsListOperation operation = createRegionListOperation();

		Long parentId = frm.getParentId();
		if(parentId == null)
			operation.initialize();
		else
			operation.initialize(parentId);

		frm.setRegion(operation.getRegion());
		doFilter(operation, frm);

		return mapping.findForward(FORWARD_START);
	}

	protected void doFilter(RegionsListOperation operation, ShowRegionsListMobileForm frm) throws BusinessException
	{
		try
		{
			Query query = operation.createQuery("list");
			Region region = operation.getRegion();
			query.setParameter("parent", region.getId());
			List<Region> regions = query.executeList();
			frm.setRegions(regions);
		}
		catch (DataAccessException e)
		{
			throw new BusinessException(e);
		}
	}
}
