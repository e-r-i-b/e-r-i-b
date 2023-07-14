package com.rssl.phizic.web.common.mobile.payments.forms;

import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.documents.templates.EditTemplateOperation;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.web.actions.payments.forms.EditTemplateForm;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.client.payments.forms.QuicklyCreateTemplateAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Dorzhinov
 * @ created 19.12.2012
 * @ $Author$
 * @ $Revision$
 */
public class QuicklyCreateTemplateMobileAction extends QuicklyCreateTemplateAction
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("save", "save");
		map.put("getTemplateName", "getUniqueTemplateName");
		return map;
	}

	protected CreationType getCreationType()
	{
		return CreationType.mobile;
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		MobileApiUtil.checkFullVersion();
		return super.save(mapping, form, request, response);
	}

	public ActionForward getUniqueTemplateName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		MobileApiUtil.checkFullVersion();
		return super.getUniqueTemplateName(mapping, form, request, response);
	}

	protected FieldValuesSource getFormProcessorValueSource(EditFormBase frm, EditEntityOperation operation)
	{
		EditTemplateMobileForm form = (EditTemplateMobileForm) frm;
		Map<String,Object> fields = new HashMap<String,Object>();
		fields.put(EditTemplateForm.TEMPLATE_NAME_FIELD_NAME, form.getTemplateName());
		return new MapValuesSource(fields);
	}

	@Override
	protected EditTemplateOperation createEditOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		return (EditTemplateOperation)createViewOperation(form);
	}
}
