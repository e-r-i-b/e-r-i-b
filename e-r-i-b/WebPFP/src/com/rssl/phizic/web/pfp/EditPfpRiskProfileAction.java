package com.rssl.phizic.web.pfp;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.pfp.EditPfpOperationBase;
import com.rssl.phizic.operations.pfp.EditPfpRiskProfileOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mihaylov
 * @ created 16.03.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Операция определения риск профиля клиента.
 */
public class EditPfpRiskProfileAction extends PassingPFPActionBase
{
	private static final String START = "Start";


	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> keyMap = super.getKeyMethodMap();
		keyMap.put("button.next","next");
		return keyMap;
	}

	protected EditPfpRiskProfileOperation getOperation(PassingPFPFormInterface form) throws BusinessException, BusinessLogicException
	{
		return getOperation(EditPfpRiskProfileOperation.class, form);
	}

	private boolean isViewMode(EditPfpRiskProfileOperation operation)
	{
		return CollectionUtils.isNotEmpty(operation.getProfile().getPortfolioList());
	}

	protected void updateForm(PassingPFPFormInterface form, EditPfpOperationBase op) throws BusinessException, BusinessLogicException
	{
		EditPfpRiskProfileForm frm = (EditPfpRiskProfileForm) form;
		EditPfpRiskProfileOperation operation = (EditPfpRiskProfileOperation) op;

		//просматриваем или можем редактировать
		boolean isViewMode = isViewMode(operation);
		frm.setField("isViewMode", isViewMode);

		//вытаскиваем список вопросов на которые пользователь отвечал
		Map<Long, Long> questions = operation.getProfile().getQuestions();
		//если клиент уже отвечал, то проставляем ответы на отвеченные вопросы
		if (MapUtils.isNotEmpty(questions))
		{
			for (Map.Entry<Long, Long> question: questions.entrySet())
				frm.setField(question.getKey().toString(), question.getValue());
		}

		if (isViewMode)
			frm.setQuestionList(operation.getRiskProfileQuestionList(questions.keySet()));//если просматриваем, то отображаем список вопросов на которые отвечали
		else
			frm.setQuestionList(operation.getRiskProfileQuestionList());//если не просматриваем, то отображаем список актуальных вопросов
	}

	/**
	 *  идем дальше
	 * @param mapping маппинг
	 * @param form    форма
	 * @param request  реквест
	 * @param response респунс
	 * @return экшен-форвард для перехода
	 * @throws Exception
	 */
	@SuppressWarnings({"UnusedDeclaration"})
	public ActionForward next(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditPfpRiskProfileForm frm = (EditPfpRiskProfileForm) form;
		EditPfpRiskProfileOperation operation = getOperation(EditPfpRiskProfileOperation.class, frm);

		boolean isViewMode = isViewMode(operation);
		if (isViewMode)
		{
			operation.saveRiskProfile();
			return getRedirectForward(operation);
		}

		Form editForm = frm.createForm(operation.getRiskProfileQuestionList());
		MapValuesSource mapValuesSource = new MapValuesSource(frm.getFields());
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(mapValuesSource, editForm);

		if(!processor.process())
		{
			saveErrors(request, processor.getErrors());
			frm.setQuestionList(operation.getRiskProfileQuestionList());
			return mapping.findForward(START);
		}
		List<Long> answerIds = new ArrayList<Long>();
		Map<Long, Long> questions = new HashMap<Long, Long>();
		Map<Long, Long> prevQuestions = operation.getProfile().getQuestions();
		Map<String, Object> result = processor.getResult();
		boolean isSame = result.size() == prevQuestions.size();
		for (Map.Entry<String, Object> question: result.entrySet())
		{
			Long answerId = (Long) question.getValue();
			Long questionId = new Long(question.getKey());
			questions.put(questionId, answerId);
			answerIds.add(answerId);
			Long oldAnswerId = prevQuestions.get(questionId);
			if(isSame && (oldAnswerId == null || !answerId.equals(oldAnswerId)))
				isSame = false;
		}
		operation.getProfile().setQuestions(questions);
		operation.calculateAndSetRiskProfile(answerIds);
		if(!isSame)
			operation.getProfile().getPersonRiskProfile().getProductsWeights().clear();
		operation.saveRiskProfile();
		return getRedirectForward(operation);				
	}
}
