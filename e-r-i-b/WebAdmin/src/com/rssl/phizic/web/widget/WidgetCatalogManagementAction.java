package com.rssl.phizic.web.widget;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.web.WidgetDefinition;
import com.rssl.phizic.operations.widget.WidgetCatalogManagementOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: moshenko
 * Date: 05.12.2012
 * Time: 11:23:50
 * Экшен управления каталогом виджетов
 */
public class WidgetCatalogManagementAction extends OperationalActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.save", "save");
		map.put("button.cancel", "start");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		WidgetCatalogManagementForm frm = (WidgetCatalogManagementForm) form;
		WidgetCatalogManagementOperation operation = createOperation(WidgetCatalogManagementOperation.class);

		frm.setWidgetDefList(operation.initialize());
		frm.setField("twitterID", operation.getTwitterId());

		updateFormSelectedWidget(frm);

		return mapping.findForward(FORWARD_START);
	}

	protected void updateFormSelectedWidget(WidgetCatalogManagementForm form) throws BusinessException
	{
		List<WidgetDefinition> list = form.getWidgetDefList();
		List<String> selectedWidget = new ArrayList<String>();
		for (WidgetDefinition wd : list)
		{
			if (wd.isAvailability())
				selectedWidget.add(wd.getCodename());
		}
		String[] selectedWidgetArray = new String[selectedWidget.size()];
		selectedWidget.toArray(selectedWidgetArray);
		form.setSelectedWidget(selectedWidgetArray);
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		WidgetCatalogManagementForm frm = (WidgetCatalogManagementForm) form;
		WidgetCatalogManagementOperation operation = createOperation(WidgetCatalogManagementOperation.class);
		MapValuesSource mapValuesSource = new MapValuesSource(frm.getFields());
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(mapValuesSource, WidgetCatalogManagementForm.FORM);		

		if(!processor.process())
		{
			saveErrors(request, processor.getErrors());
			return start(mapping, form, request, response);
		}

		operation.initialize();
		operation.save(frm.getSelectedWidget(), frm.getSortWidget());
		operation.saveTwitterId(processor.getResult().get("twitterID").toString());

		return start(mapping, form, request, response);
	}
}
