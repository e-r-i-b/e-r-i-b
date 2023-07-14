package com.rssl.phizic.web.client.userprofile.templates;

import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.userprofile.EditClientTemplatesShowSettingsOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author osminin
 * @ created 05.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * Экшен редактирования настроек видимости шаблонов
 */
public class EditTemplatesShowSettingsAction extends OperationalActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.save", "save");
		map.put("button.filter", "filter");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditTemplatesShowSettingsForm frm = (EditTemplatesShowSettingsForm) form;
		EditClientTemplatesShowSettingsOperation operation = createOperation(EditClientTemplatesShowSettingsOperation.class);

		operation.initialize(CreationType.internet);
		updateForm(frm, operation);

		return mapping.findForward(FORWARD_START);
	}

	/**
	 * для работы пагинации на странице, тк фильтра нет
	 */
	public ActionForward filter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditTemplatesShowSettingsForm frm = (EditTemplatesShowSettingsForm) form;
		EditClientTemplatesShowSettingsOperation operation = createOperation(EditClientTemplatesShowSettingsOperation.class);

		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new MapValuesSource(frm.getFields()), EditTemplatesShowSettingsForm.FORM);
		if (processor.process())
		{
			Map<String, Object> result = processor.getResult();

			operation.initialize(CreationType.valueOf((String) result.get(EditTemplatesShowSettingsForm.CHANNEL_TYPE_NAME_FIELD)));
			updateForm(frm, operation);
		}
		else
		{
			operation.initialize(CreationType.internet);
			saveErrors(request, processor.getErrors());
			updateForm(frm, operation);
		}

		return mapping.findForward(FORWARD_START);
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditTemplatesShowSettingsForm frm = (EditTemplatesShowSettingsForm) form;
		EditClientTemplatesShowSettingsOperation operation = createOperation(EditClientTemplatesShowSettingsOperation.class);

		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new MapValuesSource(frm.getFields()), EditTemplatesShowSettingsForm.FORM);
		if (processor.process())
		{
			Map<String, Object> result = processor.getResult();
			String changedIds = (String) result.get(EditTemplatesShowSettingsForm.CHANGED_IDS_NAME_FIELD);

			operation.initialize(CreationType.valueOf((String) result.get(EditTemplatesShowSettingsForm.CHANNEL_TYPE_NAME_FIELD)), changedIds);
			operation.save();
			updateForm(frm, operation);
		}
		else
		{
			operation.initialize(CreationType.internet);
			saveErrors(request, processor.getErrors());
			updateForm(frm, operation);
		}

		return mapping.findForward(FORWARD_START);
	}

	private void updateForm(EditTemplatesShowSettingsForm frm, EditClientTemplatesShowSettingsOperation operation) throws BusinessException
	{
		frm.setTemplates(operation.getTemplates());
		frm.setField(EditTemplatesShowSettingsForm.CHANNEL_TYPE_NAME_FIELD, operation.getChannelType().name());
	}
}
