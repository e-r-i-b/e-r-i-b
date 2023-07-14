package com.rssl.phizic.web.commissions;

import com.rssl.phizic.business.commission.CommissionRule;
import com.rssl.phizic.business.commission.CommissionType;
import com.rssl.phizic.operations.commission.ListCommissionOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.commons.collections.map.MultiValueMap;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Evgrafov
 * @ created 12.09.2007
 * @ $Author: Evgrafov $
 * @ $Revision: 4908 $
 */

public class ListCommissionAction extends OperationalActionBase
{
	private static final String FORWARD_START = "Start";

	protected Map<String, String> getKeyMethodMap()
	{
		return null;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListCommissionOperation operation = createOperation(ListCommissionOperation.class);
		ListCommissionForm frm = (ListCommissionForm) form;
		List<CommissionType> commissionTypes = operation.createQuery("listTypes").executeList();
		List<CommissionRule> commissionRules = operation.createQuery("listRules").executeList();

		MultiValueMap rulesByType = new MultiValueMap();

		for (CommissionRule rule : commissionRules)
		{
			rulesByType.put(rule.getType().getKey(), rule);
		}

		frm.setTypes(commissionTypes);
		frm.setRulesByType(rulesByType);

		return mapping.findForward(FORWARD_START);
	}
}