package com.rssl.phizic.web.limits;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.limits.Limit;
import com.rssl.phizic.business.limits.LimitType;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.common.types.limits.ChannelType;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.limits.ListLimitsOperation;
import com.rssl.phizic.operations.limits.RemoveLimitOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

/**
 * @author sergunin
 * @ created: 26.01.2015
 * @ $Author$
 * @ $Revision$
 * Экшен для получения списка единых суточных комулятивных лимитов
 */
public class ListOverallLimitsAction extends ListDailyLimitsAction
{
	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListOverallLimitsForm.FILTER_FORM;
	}

    protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
    {
        ListLimitsOperation operation = createOperation(ListLimitsOperation.class, "LimitsManagment");

        operation.initialize();

        return operation;
    }

    protected Map<String, Object> getDefaultFilterParameters(ListFormBase frm, ListEntitiesOperation operation) throws BusinessException, BusinessLogicException
   	{
   		return Collections.emptyMap();
   	}

	protected void fillQuery(Query query, Map<String, Object> filterParams, LimitType type, Integer paginationOffset, Integer paginationSize)
	{
		query.setParameter("fromCreationDate", filterParams.get("fromCreationDate"));
		Object toCreationDate = filterParams.get("toCreationDate");
		query.setParameter("toCreationDate", toCreationDate == null ? null : DateHelper.add((Date) toCreationDate, 0, 0, 1));
		query.setParameter("fromStartDate", filterParams.get("fromStartDate"));
		query.setParameter("toStartDate", filterParams.get("toStartDate"));
		query.setParameter("amount", filterParams.get("amount"));
		query.setParameter("status", filterParams.get("status"));
		query.setParameter("operationType", null);
		query.setParameter("restrictionType", null);
		query.setParameter("type", type.name());
		query.setParameter("groupRiskId", null);
		query.setParameter("operationCount", null);
		query.setParameter("securityType", null);
		query.setParameter("channel", getChannelType().name());
		query.setMaxResults((paginationSize != null) ? paginationSize+1 : INIT_PAGINATION_SIZE);
		query.setFirstResult((paginationOffset != null) ? paginationOffset : INIT_PAGINATION_OFFSET);
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
        ListOverallLimitsForm form = (ListOverallLimitsForm) frm;

        Query limitsQuery = operation.createQuery(getQueryName(frm));
        fillQuery(limitsQuery, filterParams, LimitType.OVERALL_AMOUNT_PER_DAY, form.getPagination_offset0(), form.getPagination_size0());
        form.setData(limitsQuery.<Limit>executeList());

		form.setFilters(filterParams);
		updateFormAdditionalData(frm, operation);
	}

	protected ChannelType getChannelType()
	{
		return ChannelType.ALL;
	}

    protected ActionMessages doRemove(RemoveEntityOperation op, Long id) throws Exception
    {
        ActionMessages msgs = new ActionMessages();
        RemoveLimitOperation operation = (RemoveLimitOperation) op;
        try
        {
            operation.initialize(id, false);
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
