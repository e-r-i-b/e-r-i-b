package com.rssl.phizic.web.common.socialApi.mail;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.common.client.ext.sbrf.mail.ArchiveMailAction;
import com.rssl.phizic.web.common.client.mail.ListMailFormBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Список удаленных писем
 * @author Dorzhinov
 * @ created 08.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListArchiveMailMobileAction extends ArchiveMailAction
{
	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String, String> map = super.getAditionalKeyMethodMap();
		map.put("rollback", "rollback");
		return map;
	}
	
	protected FieldValuesSource getFilterValuesSource(ListFormBase form)
	{
		ListArchiveMailMobileForm frm = (ListArchiveMailMobileForm)form;
		//формируем поля фильтрации для валидации
	    Map<String,Object> filter = new HashMap<String,Object>();
	    filter.put(ListMailFormBase.FIELD_FROM_DATE_NAME, frm.getFrom());
	    filter.put(ListMailFormBase.FIELD_TO_DATE_NAME, frm.getTo());
		filter.put(ListMailFormBase.FIELD_NUM_NAME, frm.getNum());
		filter.put(ListMailFormBase.FIELD_IS_ATTACH_NAME, frm.hasAttach() ? "on" : null);
		filter.put(ListMailFormBase.FIELD_SUBJECT_NAME, frm.getSubject());
		filter.put("mailType", frm.getDirection());
	    return new MapValuesSource(filter);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListArchiveMailMobileForm.FILTER_FORM;
	}

	public ActionForward rollback(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListArchiveMailMobileForm frm = (ListArchiveMailMobileForm) form;
		Long id = frm.getId();
		if (id == null || id == 0)
			throw new BusinessLogicException("Не задан id восстанавливаемого письма.");
		frm.setSelectedIds(new String[] {id.toString()});
		return super.rollback(mapping, form, request, response);
	}
}
