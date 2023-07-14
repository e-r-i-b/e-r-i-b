package com.rssl.phizic.web.persons;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.limits.GroupRisk;
import com.rssl.phizic.business.limits.Limit;
import com.rssl.phizic.business.limits.LimitType;
import com.rssl.phizic.common.types.limits.ChannelType;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.person.ViewUsersLimitsOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.*;

/**
 * @author vagin
 * @ created 26.09.2012
 * @ $Author$
 * @ $Revision$
 * Экшен списка лимитов конкретного клиента
 */
public class ListUserLimitsAction extends ListActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		ListUserLimitsForm form = (ListUserLimitsForm) frm;
		ViewUsersLimitsOperation operation = createOperation("ViewUsersLimitsOperation", "ViewUsersLimits");
		operation.initialize(form.getPerson(), ChannelType.valueOf(form.getType()));
		return operation;
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		//устанавливаем списки лимитов на форме
		updateForm(frm, operation);
		//сохраняем параметры фильтрации
		frm.setFilters(filterParams);
	}

	private List<Limit> findLimits(ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		ListUserLimitsForm form = (ListUserLimitsForm) frm;
		ViewUsersLimitsOperation op = (ViewUsersLimitsOperation) operation;

		Query query = op.createQuery(getQueryName(frm));
		query.setParameter("login", op.getPerson().getLogin().getId());
		query.setParameter("channelType", form.getType());
		query.setParameter("tb", op.getTb());
		query.setParameter("securityType", op.getPerson().getSecurityType().name());

		return query.executeList();
	}

	private void updateForm(ListFormBase frm, ListEntitiesOperation operation) throws Exception
	{
		ListUserLimitsForm form = (ListUserLimitsForm) frm;
		ViewUsersLimitsOperation op = (ViewUsersLimitsOperation) operation;

		//Список всех лимитов клиента
		List<Limit> limits = findLimits(op, frm);

		form.setOverallAmountPerDayLimits(chooseLimits(LimitType.OVERALL_AMOUNT_PER_DAY, null, limits));
		form.setObstructionLimits(chooseLimits(LimitType.OBSTRUCTION_FOR_AMOUNT_OPERATIONS, null, limits));
		form.setImsiLimits(chooseLimits(LimitType.IMSI, null, limits));
		form.setGroupRiskLimitsMap(getGroupRiskLimits(limits, op));
		form.setLimitsInfo(op.getClientAccumulateLimitsInfo());
		form.setActivePerson(op.getPerson());
	}

	private Map<Long, Pair<String, List<Limit>>> getGroupRiskLimits(List<Limit> limits, ViewUsersLimitsOperation operation) throws BusinessException
	{
		//соответствие группы риска и лимитов, созданных в ней.
		//key карты - id группы риска для упорядоченного по id отображения
		//value - пара Название группы/список лимитов по ней
		Map <Long, Pair<String, List<Limit>>> map = new TreeMap<Long, Pair<String, List<Limit>>>();
		for (GroupRisk groupRisk : operation.getAllGroupRisk())
		{
			map.put(groupRisk.getId(), new Pair(groupRisk.getName(), chooseLimits(LimitType.GROUP_RISK, groupRisk.getId(), limits)));
		}

		return map;
	}

	private List<Limit> chooseLimits(LimitType limitType, Long id, List<Limit> limits)
	{
		List<Limit> chosenLimits = new ArrayList<Limit>(limits.size());
		for (Limit limit : limits)
		{
			if (limit.getType() == null)
			{
				continue;
			}

			if (id == null)
			{
				if (limitType == limit.getType())
				{
					return Collections.singletonList(limit);
				}
			}
			else
			{
				if (limitType == limit.getType() && id.equals(limit.getGroupRisk().getId()))
				{
					chosenLimits.add(limit);
				}
			}
		}
		return chosenLimits;
	}
}
