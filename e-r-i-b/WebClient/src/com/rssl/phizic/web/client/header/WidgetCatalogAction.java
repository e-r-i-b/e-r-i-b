package com.rssl.phizic.web.client.header;

import com.rssl.phizic.operations.widget.WidgetDefinitionOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.business.persons.PersonHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Шапка: Виджеты
 * @author Dorzhinov
 * @ created 27.06.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Каталог виджетов, доступных пользователю для установки на страницу
 */
public class WidgetCatalogAction extends OperationalActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	protected boolean isAjax()
	{                                                      
		return true;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		WidgetCatalogForm frm = (WidgetCatalogForm) form;
		WidgetDefinitionOperation operation = createOperation(WidgetDefinitionOperation.class);
		frm.setPersonWidgetDefinitions(operation.getPersonWidgetDefinition(PersonHelper.getLastClientLogin().getLastLogonDate(), frm.getHaveMainContainer()));
		return mapping.findForward(FORWARD_START);
	}

	protected boolean needSaveMapping()
	{
		return false;
	}
}
