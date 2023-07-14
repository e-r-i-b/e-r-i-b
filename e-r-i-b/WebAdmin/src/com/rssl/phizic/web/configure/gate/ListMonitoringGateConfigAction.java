package com.rssl.phizic.web.configure.gate;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.gate.monitoring.MonitoringGateState;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.config.gate.ListMonitoringGateConfigOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akrenev
 * @ created 03.12.2012
 * @ $Author$
 * @ $Revision$
 *
 * Экшен списка сервисов IQWave
 */

public class ListMonitoringGateConfigAction extends ListActionBase
{
	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String, String> map = super.getAditionalKeyMethodMap();
		map.put("button.changeState", "changeServiceState");
		return map;
	}

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListMonitoringGateConfigOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	private void changeServiceState(String serviceId, MonitoringGateState newState) throws BusinessException, BusinessLogicException
	{
		try
		{
			ListMonitoringGateConfigOperation operation = createOperation("ChangeMonitoringGateServiceStateOperation");
			operation.changeServiceState(serviceId, newState);
		}
		catch (BusinessLogicException e)
		{
			saveError(currentRequest(), e);
		}
	}

	/**
	 * изменить состояние сервиса
	 * @param mapping текущий маппинг
	 * @param form текущая форма
	 * @param request текущий реквест
	 * @param response текущий респунс
	 * @return форвард
	 * @throws Exception
	 */
	@SuppressWarnings("UnusedDeclaration") // действие
	public ActionForward changeServiceState(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListMonitoringGateConfigForm frm = (ListMonitoringGateConfigForm) form;
		String[] selectedIds = frm.getSelectedIds();
		if (ArrayUtils.getLength(selectedIds) == 1)
			changeServiceState(selectedIds[0], MonitoringGateState.valueOf(frm.getNewState()));
		else
			saveError(currentRequest(), "Для запуска нужно выбрать один сервис.");

		return start(mapping, form, request, response);
	}
}