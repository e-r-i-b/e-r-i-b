package com.rssl.phizic.web.ext.sbrf.groups;

import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.groups.GetPersonListDictionaryOperation;
import com.rssl.phizic.web.groups.ShowPersonDictionaryAction;
import com.rssl.phizic.web.groups.ShowPersonDictionaryForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Barinov
 * @ created 29.11.2011
 * @ $Author$
 * @ $Revision$
 */

public class SBRFShowPersonDictionaryAction extends ShowPersonDictionaryAction
{
	private static final Long MAX_INSERT = 1000L;
	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String, String> map = super.getAditionalKeyMethodMap();
		map.put("button.addAll","addAll");
		return map;
	}

	public ActionForward addAll(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowPersonDictionaryForm frm =  (ShowPersonDictionaryForm)form;
		GetPersonListDictionaryOperation operation = createOperation(GetPersonListDictionaryOperation.class);
		operation.initialize(frm.getGroupId());
		//Ѕерем старые параметры, т.к. сотрудник может добавить пачку клиентов лишь с теми же параметрами, что были в предыдущем фильтре
		// ≈сли сотрудник открывает фильтр - кнопка "добавить 1000" пр€четс€.
		//Ёто сделано дл€ того, чтобы сотрудник всегда видел, что добавл€ет
		Map<String,Object> filterParams = loadFilterParameters(frm,operation);
		Query query = operation.createQuery("addAll");
		query.setParameter("maxRowsNum",MAX_INSERT);
		fillQuery(query, filterParams);
		frm.setRowsNum(query.executeUpdate());
		
		return filter(mapping,form,request,response);
	}

}
