package com.rssl.phizic.web.configure.policy;

import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.auth.modes.AuthenticationConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.operations.CompositeLogParametersReader;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.scheme.EditDefaultSchemesOperation;
import com.rssl.phizic.operations.scheme.EditPolicyTemplateOperation;
import com.rssl.phizic.operations.scheme.MarkSchemeAsDefaultOperation;
import com.rssl.phizic.web.access.AssignAccessSubForm;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Evgrafov
 * @ created 18.04.2007
 * @ $Author: tisov $
 * @ $Revision: 72752 $
 */

@SuppressWarnings({"JavaDoc"})
public class EditPolicyTemplateAction extends OperationalActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.save", "save");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditPolicyTemplateForm frm = (EditPolicyTemplateForm) form;

		updateSubForm(frm.getSimpleAccess(), AccessType.simple);
		updateSubForm(frm.getSecureAccess(), AccessType.secure);

		updateFormAdditionalData(frm);

		return mapping.findForward(FORWARD_START);
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditDefaultSchemesOperation defaultSchemesOperation = createOperation(EditDefaultSchemesOperation.class);

		EditPolicyTemplateForm frm = (EditPolicyTemplateForm) form;
		ActionForward forward;

		try
		{
			// TODO сделать проход на проверку доступных AccessType в цикле
			AuthenticationConfig config = ConfigFactory.getConfig(AuthenticationConfig.class, Application.PhizIC);
			Set<AccessType> accessTypes = config.getAccessTypes();
			if (accessTypes.contains(AccessType.simple))
			{
				updateOperation(frm.getSimpleAccess(), AccessType.simple);
			}
			if (accessTypes.contains(AccessType.secure))
			{
				updateOperation(frm.getSecureAccess(), AccessType.secure);
			}

			defaultSchemesOperation.initialize(frm.getSchemesSBOL(), frm.getSchemesUDBO(), frm.getSchemeCARD(),
					frm.getSchemeCARD_TEMPORARY(), frm.getSchemeUDBO_TEMPORARY(), frm.getSchemeGUEST());
			defaultSchemesOperation.save();

			forward = sendRedirectToSelf(request);
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
			forward = mapping.findForward(FORWARD_START);
		}

		return forward;
	}

	private void updateSubForm(AssignAccessSubForm subForm, AccessType accessType) throws BusinessException, BusinessLogicException
	{
		MarkSchemeAsDefaultOperation schemeOperation = createOperation("ViewSchemeAsDefaultOperation");
		schemeOperation.initialize(accessType);

		Long schemeId = schemeOperation.getDefaultSchemeId();
		subForm.setAccessSchemeId(schemeId == null ? "" : schemeId.toString());
		subForm.setHelpers(schemeOperation.getHelpers());

		EditPolicyTemplateOperation templateOperation = createOperation("ViewPolicyTemplateOperation");
		templateOperation.initialize(accessType);

		subForm.setEnabled(templateOperation.isEnabled());
		subForm.setPolicy(templateOperation.getPolicy());
		subForm.setProperties((Map<String, Object>) templateOperation.getProperties().clone());

		addLogParameters(new CompositeLogParametersReader(
				new BeanLogParemetersReader("Политика доступа", templateOperation.getPolicy()),
				new SimpleLogParametersReader("Состояние политики ", templateOperation.isEnabled() ? "Разрешена" : "Запрещена")
			));

	}

	private void updateOperation(AssignAccessSubForm subForm, AccessType accessType) throws BusinessException, BusinessLogicException
	{
		EditPolicyTemplateOperation templateOperation = createOperation(EditPolicyTemplateOperation.class);
		templateOperation.initialize(accessType);
		boolean enabled = subForm.getEnabled();
		templateOperation.setEnabled(enabled);
		if(enabled)
		{
			Map<String, Object> propertiesMap = subForm.getProperties();
			for (Map.Entry<String, Object> entry : propertiesMap.entrySet())
			{
				templateOperation.setProperty(entry.getKey(), (String) entry.getValue());
			}

		}

		templateOperation.save();

		MarkSchemeAsDefaultOperation schemeOperation = createOperation(MarkSchemeAsDefaultOperation.class);
		schemeOperation.initialize(accessType);

		String schemeId = subForm.getAccessSchemeId();
		schemeOperation.setDefaultSchemeId(schemeId == null || schemeId.equals("") ? null : Long.valueOf(schemeId));
		schemeOperation.markSchemesAsDefault();

		addLogParameters(new CompositeLogParametersReader(
				new BeanLogParemetersReader("Политика доступа", templateOperation.getPolicy()),
				new SimpleLogParametersReader("Состояние политики ", templateOperation.isEnabled() ? "Разрешена" : "Запрещена")
			));

	}

	private void updateFormAdditionalData(EditPolicyTemplateForm frm) throws BusinessException, BusinessLogicException
	{
		EditDefaultSchemesOperation operation = createOperation(EditDefaultSchemesOperation.class);
		operation.initializeNew();
		frm.setHelpers(operation.getHelpers());
		frm.setTerbanks(operation.getTerbanks());
		frm.setSchemesSBOL(operation.getSchemesSBOL());
		frm.setSchemesUDBO(operation.getSchemesUDBO());
		frm.setSchemeCARD(operation.getSchemeCARD());
		frm.setSchemeCARD_TEMPORARY(operation.getSchemeCARD_TEMPORARY());
		frm.setSchemeUDBO_TEMPORARY(operation.getSchemeUDBO_TEMPORARY());
		frm.setSchemeGUEST(operation.getSchemeGUEST());
	}
}