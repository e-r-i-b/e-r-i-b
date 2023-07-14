package com.rssl.phizic.web.loans.claims;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.loanclaim.questionnaire.AnswerType;
import com.rssl.phizic.business.loanclaim.questionnaire.LoanClaimQuestion;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.loanclaim.questionnaire.EditLoanClaimQuestionOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;

import java.math.BigInteger;
import java.util.Map;

/**
 * @author Gulov
 * @ created 27.12.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * Экшн редактирования вопроса заявки на кредит
 */
public class EditLoanClaimQuestionAction extends EditActionBase
{
	@Override
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditLoanClaimQuestionOperation operation = createOperation(EditLoanClaimQuestionOperation.class);
		Long id = frm.getId();
		if (id != null && id != 0)
			operation.initialize(id);
		else
			operation.initializeNew();
		return operation;
	}

	@Override
	protected Form getEditForm(EditFormBase frm)
	{
		return EditLoanClaimQuestionForm.FORM;
	}

	@Override
	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		LoanClaimQuestion question = (LoanClaimQuestion) entity;
		question.setId(new Long(((BigInteger) data.get("questionId")).intValue()));
		question.setQuestion((String) data.get("question"));
		question.setAnswerType(AnswerType.valueOf((String) data.get("answerType")));
	}

	@Override
	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> data) throws Exception {}

	@Override
	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		LoanClaimQuestion question = (LoanClaimQuestion) entity;
		if (question.getId() == null)
			return;
		frm.setField("questionId", question.getId());
		frm.setField("question", question.getQuestion());
		frm.setField("answerType", question.getAnswerType());
	}
}
