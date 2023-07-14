package com.rssl.phizic.web.atm.common;

import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.UserPrincipal;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.templates.source.TemplateDocumentSource;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.documents.templates.EditTemplateOperation;
import com.rssl.phizic.operations.forms.ViewTemplateOperation;
import com.rssl.phizic.web.actions.payments.forms.EditTemplateForm;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.client.payments.forms.QuicklyCreateTemplateAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.security.AccessControlException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Balovtsev
 * Date: 25.12.2012
 * Time: 11:32:43
 */
public class QuicklyCreateTemplateAtmAction extends QuicklyCreateTemplateAction
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
		return CreationType.atm;
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		checkFullVersion();
		return super.save(mapping, form, request, response);
	}

	public ActionForward getUniqueTemplateName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		checkFullVersion();
		return super.getUniqueTemplateName(mapping, form, request, response);
	}

	private void checkFullVersion() throws BusinessLogicException
	{
		UserPrincipal principal = AuthModule.getAuthModule().getPrincipal();
		if (principal.isMobileLightScheme() || principal.isMobileLimitedScheme())
			throw new AccessControlException("В данной версии системы создание шаблонов запрещено.");
	}

	protected FieldValuesSource getFormProcessorValueSource(EditFormBase frm, EditEntityOperation operation)
	{
		EditTemplateAtmForm form = (EditTemplateAtmForm) frm;
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
