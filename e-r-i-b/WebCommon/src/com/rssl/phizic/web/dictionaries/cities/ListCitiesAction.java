package com.rssl.phizic.web.dictionaries.cities;

import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.dictionaries.cities.CitiesListOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lepihina
 * @ created 28.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListCitiesAction extends OperationalActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String,String> map = new HashMap<String,String>();
		map.put("button.searchCities", "searchCities");
		return map;
	}

	/**
	 * ћетод выполн€ющийс€ на старте
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public final ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListCitiesForm frm = (ListCitiesForm) form;
		CitiesListOperation operation = createOperation("CitiesListOperation");

		frm.setSearchPage(0);
		frm.setResOnPage(10);
		doFilter(operation, frm);
		return mapping.findForward(FORWARD_START);
	}

	/**
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public final ActionForward searchCities(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListCitiesForm frm = (ListCitiesForm) form;
		CitiesListOperation operation = createOperation("CitiesListOperation");
		doFilter(operation, frm);
		return mapping.findForward(FORWARD_START);
	}

	protected void doFilter(CitiesListOperation operation, ListCitiesForm frm) throws Exception
	{
		int currentPage = frm.getSearchPage();
		int resOnPage = frm.getResOnPage();

		Query query = operation.createQuery("list");
		query.setMaxResults(resOnPage+1);
		query.setFirstResult(resOnPage*currentPage);
		query.setParameter("cityName", frm.getField("cityName"));
		frm.setData(query.executeList());
	}

	protected boolean getEmptyErrorPage()
	{
		return true;
	}

	protected boolean isAjax()
	{
		return true;
	}
}
