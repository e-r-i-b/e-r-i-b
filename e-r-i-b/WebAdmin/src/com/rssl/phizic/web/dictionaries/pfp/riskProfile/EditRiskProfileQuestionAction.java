package com.rssl.phizic.web.dictionaries.pfp.riskProfile;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.Question;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.Answer;
import com.rssl.phizic.business.dictionaries.pfp.configure.SegmentHelper;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.pfp.riskProfile.EditRiskProfileQuestionOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.common.types.SegmentCodeType;

import java.util.Map;
import java.util.List;

/**
 * @author akrenev
 * @ created 08.02.2012
 * @ $Author$
 * @ $Revision$
 *
 * Ёкшен редактировани€ вопроса
 */

public class EditRiskProfileQuestionAction extends EditActionBase
{
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditRiskProfileQuestionOperation operation = createOperation(EditRiskProfileQuestionOperation.class);
		operation.initialize(frm.getId());
		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return ((EditRiskProfileQuestionForm)frm).createEditForm();
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		Question question = (Question) entity;
		question.setSegment((SegmentCodeType) data.get("segment"));
		question.setText((String) data.get("text"));
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		EditRiskProfileQuestionOperation operation = (EditRiskProfileQuestionOperation) editOperation;
		Long answerCount = (Long) validationResult.get("answerCount");
		operation.clearAnswers();
		for (int i = 0; i < answerCount; i++)
		{
			String text = (String) validationResult.get("answerTextFor" + i);
			Long weight = (Long) validationResult.get("answerWeightFor" + i);
			if (!StringHelper.isEmpty(text) && weight != null)
				operation.addAnswer(text, weight);
		}
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		Question question = (Question) entity;
		frm.setField("segment", question.getSegment());
		frm.setField("text", question.getText());
		List<Answer> answerList = question.getAnswers();
		int size = answerList.size();
		frm.setField("answerCount", size);
		for (int i = 0; i < size; i++)
		{
			frm.setField("answerTextFor" + i, answerList.get(i).getText());
			frm.setField("answerWeightFor" + i, answerList.get(i).getWeight());
		}
	}

	@Override
	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		super.updateFormAdditionalData(frm, operation);
		frm.setField("segmentList", SegmentHelper.getSegmentList());
	}
}