package com.rssl.phizic.web.dictionaries.pfp.riskProfile;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.configure.SegmentHelper;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.Question;
import com.rssl.phizic.common.types.SegmentCodeType;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.dictionaries.pfp.riskProfile.ListRiskProfileQuestionOperation;
import com.rssl.phizic.operations.dictionaries.pfp.riskProfile.RemoveRiskProfileQuestionOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.utils.StringHelper;
import org.apache.struts.action.ActionMessages;

import java.util.List;
import java.util.Map;

/**
 * @author akrenev
 * @ created 08.02.2012
 * @ $Author$
 * @ $Revision$
 *
 * Экшен списка вопросов
 */

public class ListRiskProfileQuestionAction extends ListActionBase
{
	private static final List<SegmentCodeType> SEGMENT_LIST = SegmentHelper.getSegmentList();

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListRiskProfileQuestionOperation.class);
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemoveRiskProfileQuestionOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	@Override
	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation) throws Exception
	{
		super.updateFormAdditionalData(form, operation);
		form.setField("segmentList", SEGMENT_LIST);
	}

	private List<Question> getListForSegment(ListEntitiesOperation operation, String segment) throws Exception
	{
		Query query = operation.createQuery("list");
		query.setParameter("segment", segment);
		return query.executeList();
	}

	@Override
	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		ListRiskProfileQuestionForm form = (ListRiskProfileQuestionForm) frm;
		for (SegmentCodeType segmentCodeType : SEGMENT_LIST)
		{
			String segment = StringHelper.getEmptyIfNull(segmentCodeType);
			form.addQuestions(segment, getListForSegment(operation, segment));
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
			saveMessage(currentRequest(), "Невозможно удалить вопрос.");
		}
		return new ActionMessages();
	}
}
