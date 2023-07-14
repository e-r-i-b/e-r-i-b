package com.rssl.phizic.web.pfp.ajax.risk.profile;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.ProductType;
import com.rssl.phizic.business.pfp.PersonalFinanceProfile;
import com.rssl.phizic.business.pfp.risk.profile.PersonRiskProfile;
import com.rssl.phizic.operations.pfp.ChangePersonRiskProfileOperation;
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
 * Изменение риск профиля
 * @author komarov
 * @ created 26.06.2013 
 * @ $Author$
 * @ $Revision$
 */

public class ChangePersonRiskProfileAction extends OperationalActionBase
{
	private static final String CHANGE_RISK_PROFILE = "Change";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String,String> map = new HashMap<String,String>();
		map.put("button.save.riskProfile", "change");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return null;
	}

	private void updatePersonRiskProfile(PersonRiskProfile profile, Map<String, Object> data)
	{
		Map<ProductType, Long> productsWeights = profile.getProductsWeights();
		for (Map.Entry<ProductType, Long> entry : productsWeights.entrySet())
			entry.setValue((Long) data.get(entry.getKey().name()));
	}

	/**
	 * Изменяет риск профиль клиента
	 * @param mapping иапинг
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward change(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ChangePersonRiskProfileForm frm = (ChangePersonRiskProfileForm) form;
		ChangePersonRiskProfileOperation operation = createOperation(ChangePersonRiskProfileOperation.class);
		operation.initialize(frm.getId(), checkAccess("SearchVIPForPFPOperation"));

		FieldValuesSource valuesSource = new MapValuesSource(frm.getFields());
		PersonalFinanceProfile personalFinanceProfile = operation.getProfile();
		Form newChangePersonRiskProfileForm = ChangePersonRiskProfileForm.createForm(personalFinanceProfile.getDefaultRiskProfile());
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, newChangePersonRiskProfileForm);
		if (processor.process())
		{
			updatePersonRiskProfile(personalFinanceProfile.getPersonRiskProfile(), processor.getResult());
			operation.save();
		}
		else
		{
			saveErrors(request, processor.getErrors());
		}
		return mapping.findForward(CHANGE_RISK_PROFILE);
	}

	protected boolean isAjax()
	{
		return true;
	}
}
