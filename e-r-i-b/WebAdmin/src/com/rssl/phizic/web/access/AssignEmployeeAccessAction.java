package com.rssl.phizic.web.access;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.schemes.AccessScheme;
import com.rssl.phizic.business.schemes.PersonalAccessScheme;
import com.rssl.phizic.business.services.Service;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.config.csaadmin.CSAAdminGateConfig;
import com.rssl.phizic.operations.access.AssignEmployeeAccessOperation;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.utils.StringHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Evgrafov
 * @ created 17.01.2006
 * @ $Author: krenev_a $
 * @ $Revision: 66431 $
 */
@SuppressWarnings({"JavaDoc"})
public class AssignEmployeeAccessAction extends AssignAccessActionBase
{
	public  AssignEmployeeAccessOperation createAssignAccessOperation(ActionForm form, boolean view) throws BusinessException, BusinessLogicException
	{
		AssignEmployeeAccessOperation  operation;
		if(view)
			operation = createOperation("ViewEmployeeAccessOperation");
		else
			operation = createOperation(AssignEmployeeAccessOperation.class);

		AssignEmployeeAccessForm frm = (AssignEmployeeAccessForm) form;
		Long personId = frm.getEmployeeId();
		operation.initialize(personId);
		return  operation;
	}

	/**
	 * Вывести список прав пользователя
	 */
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AssignEmployeeAccessOperation operation = createAssignAccessOperation(form, true);
		AssignEmployeeAccessForm frm = (AssignEmployeeAccessForm) form;
		frm.setEmployee(operation.getEntity());
		updateForm(frm, operation);
		return mapping.findForward(FORWARD_SHOW);
	}

	private void updateForm(AssignEmployeeAccessForm form, AssignEmployeeAccessOperation operation) throws BusinessException
	{
		AssignAccessSubForm subForm = form.getEmployeeAccess();
		subForm.setDenyCustomRights(getFeatureAccessibilityCustomRights());

		subForm.setHelpers(operation.getHelpers());
		subForm.setPolicy(operation.getPolicy());

		AccessScheme userScheme = operation.getOldAccessScheme();
		if (userScheme != null)
		{
			if (userScheme instanceof PersonalAccessScheme)
				subForm.setAccessSchemeId("personal" + userScheme.getCategory());
			else
				subForm.setAccessSchemeId(operation.getEntity().getScheme().getId().toString());

			List<Service> availableServices = userScheme.getServices();

			Long[] temp = new Long[availableServices.size()];
			Long[] caAdminTemp = new Long[availableServices.size()];
			int i = 0;
			int j = 0;
			for (Service s : availableServices)
			{
				temp[i++] = s.getId();
				if (s.isCaAdminService())
					caAdminTemp[j++] = s.getId();
			}
			subForm.setSelectedServices(temp);
			subForm.setCaadminServices(caAdminTemp);
			subForm.setCategory(userScheme.getCategory());
		}
		else
		{
			subForm.setSelectedServices(new Long[0]);
			subForm.setCaadminServices(new Long[0]);
		}

		form.setGroups(operation.getServicesGroups());
		subForm.setEnabled(true);
	}

	/**
	 * Назначить пользователю права доступа
	 */
	public ActionForward save(ActionMapping mapping, ActionForm saveForm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try
		{
			AssignEmployeeAccessForm form = (AssignEmployeeAccessForm) saveForm;
			AssignEmployeeAccessOperation operation = createAssignAccessOperation(form, false);

			AssignAccessSubForm frm = form.getEmployeeAccess();
			String schemeIdStr = StringHelper.getEmptyIfNull(frm.getAccessSchemeId());
			if (schemeIdStr.startsWith("personal"))
			{
				if (getFeatureAccessibilityCustomRights())
				{
					saveError(request, "Назначение индивидуальной схемы прав запрещено");
					return start(mapping, frm, request, response);
				}
				operation.setPersonalScheme(frm.getCategory(), frm.getSelectedServicesList());
			}
			else
			{
				Long schemeId = StringHelper.isEmpty(schemeIdStr)? null: Long.valueOf(schemeIdStr);
				operation.setNewAccessSchemeId(schemeId);

			}
			operation.assign();
		}
		catch(BusinessLogicException e)
		{
			saveError(request, e.getMessage());
			return start(mapping, saveForm, request, response);
		}

		return sendRedirectToSelf(request);
	}

	private boolean getFeatureAccessibilityCustomRights()
	{
		return ConfigFactory.getConfig(CSAAdminGateConfig.class).isMultiBlockMode() || ConfigFactory.getConfig(SecurityConfig.class).isDenyCustomRights();
	}
}
