package com.rssl.phizic.web.common.socialApi.mail;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.common.client.ext.sbrf.mail.ListMailAction;
import com.rssl.phizic.web.common.client.mail.ListMailFormBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * —писок вход€щих писем
 * @author Dorzhinov
 * @ created 08.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListInboxMailMobileAction extends ListMailAction
{
	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String, String> map = super.getAditionalKeyMethodMap();
		map.put("remove", "remove");
		return map;
	}

	protected FieldValuesSource getFilterValuesSource(ListFormBase form)
	{
		ListInboxMailMobileForm frm = (ListInboxMailMobileForm)form;
		//формируем пол€ фильтрации дл€ валидации
	    Map<String,Object> filter = new HashMap<String,Object>();
	    filter.put(ListMailFormBase.FIELD_FROM_DATE_NAME, frm.getFrom());
	    filter.put(ListMailFormBase.FIELD_TO_DATE_NAME, frm.getTo());
		filter.put(ListMailFormBase.FIELD_NUM_NAME, frm.getNum());
		filter.put(ListMailFormBase.FIELD_IS_ATTACH_NAME, frm.hasAttach() ? "on" : null);
		filter.put(ListMailFormBase.FIELD_SUBJECT_NAME, frm.getSubject());
		filter.put("state", frm.getRecipientState());
	    return new MapValuesSource(filter);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListInboxMailMobileForm.FILTER_FORM;
	}

	protected void updateFormAdditionalData(ListFormBase frm, ListEntitiesOperation operation) throws Exception
	{
		/* в super-методе добавл€етс€ сообщение в риквест. Ёто сообщение не нужно выводить, при выполнении любого из условий:
			- произошла ошибка
			- это удаление письма
		  */
		HttpServletRequest request = currentRequest();
		if (getErrors(request).isEmpty() && !"remove".equals(request.getParameter("operation")))
			super.updateFormAdditionalData(frm, operation);
	}

	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListInboxMailMobileForm frm = (ListInboxMailMobileForm) form;
		Long id = frm.getId();
		if (id == null || id == 0)
			throw new BusinessLogicException("Ќе задан id удал€емого письма.");
		frm.setSelectedIds(new String[] {id.toString()});
		return super.remove(mapping, form, request, response);
	}
}
