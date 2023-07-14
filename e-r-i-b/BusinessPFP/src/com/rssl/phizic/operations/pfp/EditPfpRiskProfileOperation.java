package com.rssl.phizic.operations.pfp;

import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.*;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.pfp.PersonalFinanceProfile;
import com.rssl.phizic.common.types.documents.State;

import java.util.Collection;
import java.util.List;

/**
 * @author mihaylov
 * @ created 25.03.2012
 * @ $Author$
 * @ $Revision$
 */

public class EditPfpRiskProfileOperation extends EditPfpOperationBase
{
	private static final State EDIT_RISK_PROFILE_FORM = new State("EDIT_RISK_PROFILE_FORM");
	private static final QuestionService questionService = new QuestionService();
	private static final RiskProfileService riskProfileService = new RiskProfileService();
	private static final AgeCategoryService ageCategoryService = new AgeCategoryService();

	protected void additionalCheckPersonalFinanceProfile(PersonalFinanceProfile profile) throws BusinessException, BusinessLogicException
	{
		checkState(EDIT_RISK_PROFILE_FORM);
	}

	/**
	 * @return список воросов для сегмента клиента
	 * @throws BusinessException
	 */
	public List<Question> getRiskProfileQuestionList() throws BusinessException
	{
		return questionService.getAll(getCurrentClientSegment());
	}

	public List<Question> getRiskProfileQuestionList(Collection<Long> queryIds) throws BusinessException
	{
		return questionService.getByIds(queryIds);
	}

	public void calculateAndSetRiskProfile(List<Long> answerIdList) throws BusinessException, BusinessLogicException
	{
		Integer weight = questionService.calculateAnswersWeight(answerIdList);
		Integer personAge = PersonHelper.getPersonAge(getPerson());		 
		weight = weight + ageCategoryService.getWeightByAge(personAge);
		RiskProfile riskProfile = riskProfileService.findRiskProfileForWeight(weight, getCurrentClientSegment());
		personalFinanceProfile.setRiskProfileWeight(weight);
		personalFinanceProfile.setDefaultRiskProfile(riskProfile);
	}

	public void saveRiskProfile() throws BusinessException, BusinessLogicException
	{
		fireEvent(DocumentEvent.SAVE_RISK_PROFILE);
		pfpService.addOrUpdateProfile(personalFinanceProfile);
	}

}
