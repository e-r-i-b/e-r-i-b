package com.rssl.phizic.web.ermb.tariff;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.ext.sbrf.mobilebank.ermb.tariff.ErmbChangeTariffListOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Moshenko
 * @ created 12.12.13
 * @ $Author$
 * @ $Revision$
 * Экшен для смены тарифа ЕРМБ
 */
public class ErmbChangeTariffListAction extends ListActionBase
{
	protected static final String SUCCESS = "Success";

	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.save", "changeTariff");
		return map;
	}

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ErmbChangeTariffListOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase form) throws Exception
	{
		ErmbChangeTariffListForm frm = (ErmbChangeTariffListForm) form ;

		if (frm.getId() == null)
		{
			throw new IllegalArgumentException("Для того осуществить переключение тарифа, необходимо передать  его id");
		}
		Map<String, Object> filterParameters = new HashMap<String, Object>();
		filterParameters.put("id",frm.getId());
		filterParams.putAll(filterParameters);
		super.doFilter(filterParams, operation, form);
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{

		query.setParameter("id", filterParams.get("id"));
	}

	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation) throws Exception
	{
		ErmbChangeTariffListForm frm = (ErmbChangeTariffListForm) form ;
		ErmbChangeTariffListOperation op = (ErmbChangeTariffListOperation) operation;
		((ErmbChangeTariffListOperation) operation).initialize(frm.getId());
		frm.setChangeTariff(op.getChangeTariff());
	}

	public final ActionForward changeTariff(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ErmbChangeTariffListForm frm = (ErmbChangeTariffListForm) form;
		ErmbChangeTariffListOperation op = createOperation(ErmbChangeTariffListOperation.class);
		op.changeTariff(frm.getId(),frm.getSelectedId());
		return getCurrentMapping().findForward(SUCCESS);
	}


}
