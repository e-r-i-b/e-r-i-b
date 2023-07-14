package com.rssl.phizic.web.limits;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.limits.*;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.common.types.limits.ChannelType;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.limits.ListLimitsOperation;
import com.rssl.phizic.operations.limits.RemoveLimitOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.actions.SaveFilterParameterAction;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.util.*;

/**
 * @author gulov
 * @ created 19.08.2010
 * @ $Authors$
 * @ $Revision$
 */
public class ListLimitsAction extends SaveFilterParameterAction
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		ListLimitsOperation operation = createOperation(ListLimitsOperation.class);

		ListLimitsForm form = (ListLimitsForm) frm;
		operation.initialize(form.getDepartmentId());

		return operation;
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListLimitsForm.FILTER_FORM;
	}

	protected void fillQueryBase(Query query, Map<String, Object> filterParams, Long groupRiskId, String securityType) throws BusinessException, BusinessLogicException
	{
		query.setParameter("fromCreationDate", filterParams.get("fromCreationDate"));

		Object toCreationDate = filterParams.get("toCreationDate");
		query.setParameter("toCreationDate", toCreationDate == null ? null : DateHelper.add((Date) toCreationDate, 0, 0, 1));
		query.setParameter("fromStartDate", filterParams.get("fromStartDate"));
		query.setParameter("toStartDate", filterParams.get("toStartDate"));
		query.setParameter("amount", filterParams.get("amount"));
		query.setParameter("groupRiskId", groupRiskId);
		query.setParameter("status", filterParams.get("status"));
		query.setParameter("type", LimitType.GROUP_RISK.name());
		query.setParameter("restrictionType", filterParams.get("restrictionType"));
		query.setParameter("operationType", filterParams.get("operationType"));
		query.setParameter("operationCount", filterParams.get("operationCount"));
		query.setParameter("securityType", securityType);
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams, Long groupRiskId, String securityType) throws BusinessException, BusinessLogicException
	{
		fillQueryBase(query, filterParams, groupRiskId, securityType);
		query.setParameter("channel", ChannelType.INTERNET_CLIENT.name());
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation op, ListFormBase frm) throws Exception
	{
		ListLimitsForm form = (ListLimitsForm) frm;
		ListLimitsOperation operation = (ListLimitsOperation) op;

		List<GroupRisk> listGroupsRisk = operation.getAllGroupRisk();
		form.setGroupsRisForFilter(listGroupsRisk);

		List<Pair<String, List<Limit>>> data = new ArrayList<Pair<String, List<Limit>>>();
		Long filterGroupRiskId = (Long)filterParams.get("groupRisk");

		for (GroupRisk groupRisk : listGroupsRisk)
		{
			if (filterGroupRiskId != null && !filterGroupRiskId.equals(groupRisk.getId()))
				continue;

			Query query = operation.createQuery(getQueryName(frm));
			fillQuery(query, filterParams, groupRisk.getId(), form.getSecurityType());
			data.add(new Pair<String, List<Limit>>(groupRisk.getName(), query.<Limit>executeList()));
		}

		form.setData(data);
		form.setFilters(filterParams);

		updateFormAdditionalData(frm, operation);
	}

	protected void forwardFilterFail(ListFormBase frm, ListEntitiesOperation operation) throws Exception
	{
		ListLimitsForm form = (ListLimitsForm) frm;
		form.setData(Collections.<Pair<String, List<Limit>>>emptyList());
		super.forwardFilterFail(form, operation);
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemoveLimitOperation.class, "ConfirmLimitsManagment");
	}

	protected Map<String, Object> getDefaultFilterParameters(ListFormBase frm, ListEntitiesOperation operation)
			throws BusinessException, BusinessLogicException
	{
		return Collections.<String, Object> singletonMap("status", Status.ACTIVE.toValue());
	}

	protected ActionMessages doRemove(RemoveEntityOperation op, Long id) throws Exception
	{
		ActionMessages msgs = new ActionMessages();
		RemoveLimitOperation operation = (RemoveLimitOperation) op;
		try
		{
			operation.initialize(id);
			operation.remove();
		}
		catch (InactiveExternalSystemException e)
		{
			saveInactiveESMessage(currentRequest(), e);
		}
		catch (BusinessLogicException e)
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Невозможно удалить лимит id=" + operation.getEntity().getId() + ". Причина: " + e.getMessage(), false));
		}
		catch (BusinessException e)
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Невозможно удалить лимит id=" + operation.getEntity().getId() + ".", false));
		}
		return msgs;
	}
}