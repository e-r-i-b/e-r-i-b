package com.rssl.phizic.web.dictionaries.pfp.riskProfile;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.configure.SegmentHelper;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.RiskProfile;
import com.rssl.phizic.common.types.SegmentCodeType;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.dictionaries.pfp.riskProfile.ListRiskProfileOperation;
import com.rssl.phizic.operations.dictionaries.pfp.riskProfile.RemoveRiskProfileOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.ActionMessages;

import java.util.List;
import java.util.Map;

/**
 * @author akrenev
 * @ created 10.02.2012
 * @ $Author$
 * @ $Revision$
 *
 * экшен списка риск-профилей
 */

public class ListRiskProfileAction extends ListActionBase
{
	private static final List<SegmentCodeType> SEGMENT_LIST = SegmentHelper.getSegmentList();

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListRiskProfileOperation.class);
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemoveRiskProfileOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListRiskProfileForm.FILTER_FORM;
	}

	@Override
	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation) throws Exception
	{
		super.updateFormAdditionalData(form, operation);
		form.setField("segmentList", SEGMENT_LIST);
	}

	private List<RiskProfile> getListForSegment(ListEntitiesOperation operation, String segment) throws Exception
	{
		Query query = operation.createQuery("list");
		query.setParameter("segment", segment);
		return query.executeList();
	}

	@Override
	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		ListRiskProfileForm form = (ListRiskProfileForm) frm;
		for (SegmentCodeType segmentCodeType : SEGMENT_LIST)
		{
			String segment = StringHelper.getEmptyIfNull(segmentCodeType);
			form.addRiskProfiles(segment, getListForSegment(operation, segment));
		}
		updateFormAdditionalData(form, operation);
	}

	protected ActionMessages doRemove(RemoveEntityOperation operation, Long id) throws Exception
	{
		try
		{
			return super.doRemove(operation, id);
		}
		catch (BusinessLogicException ignore)
		{
			saveMessage(currentRequest(), "Невозможно удалить риск-профиль.");
		}
		return new ActionMessages();
	}
}
