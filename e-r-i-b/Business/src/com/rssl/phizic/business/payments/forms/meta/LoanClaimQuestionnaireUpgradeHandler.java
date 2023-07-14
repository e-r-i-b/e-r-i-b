package com.rssl.phizic.business.payments.forms.meta;

/**
 * @author Gulov
 * @ created 09.02.15
 * @ $Author$
 * @ $Revision$
 */

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.business.extendedattributes.ExtendedAttribute;
import com.rssl.phizic.business.loanclaim.questionnaire.LoanClaimQuestion;
import com.rssl.phizic.business.loanclaim.questionnaire.LoanClaimQuestionService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.loanclaim.LoanClaimConfig;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Хэндлер актуализации анкеты в заявке на кредит
 */
public class LoanClaimQuestionnaireUpgradeHandler extends BusinessDocumentHandlerBase
{
	private static final String QUESTION_PREFIX = "question_";
	private static final String[] QUESTION_SUFFIXES = {"_answer", "_answer_type", "_id", "_text"};

	public static final String QUESTIONNAIRE_STATE_FIELD = "questionnaireState";
	public static final String QUESTIONNAIRE_STATE_ADDED = "added";
	public static final String QUESTIONNAIRE_STATE_DELETED = "deleted";
	public static final String QUESTIONNAIRE_STATE_CHANGED = "changed";

	private static final LoanClaimQuestionService questionService = new LoanClaimQuestionService();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		if (!(document instanceof ExtendedLoanClaim))
			throw new DocumentException("Неверный тип платежа id=" + document.getId() + " (Ожидается ExtendedLoanClaim)");
		ExtendedLoanClaim claim = (ExtendedLoanClaim) document;
		boolean useQuestionnaire = ConfigFactory.getConfig(LoanClaimConfig.class).isUseQuestionnaire();
		int questionCount = claim.getQuestionCount();
		// если анкета включена и ее в заявке нет, скопировать ее в заявку
		if (useQuestionnaire &&  questionCount == 0)
		{
			List<LoanClaimQuestion> questionnaire = findQuestionnaire();
			if (CollectionUtils.isEmpty(questionnaire))
				return;
			copyQuestionnaireToClaim(claim, questionnaire);
			stateMachineEvent.registerChangedField(QUESTIONNAIRE_STATE_FIELD, QUESTIONNAIRE_STATE_ADDED, "");
			return;
		}
		// анкету не использовать, в заявке она есть
		if (!useQuestionnaire && questionCount != 0)
		{
			removeQuestionnaire(claim);
			stateMachineEvent.registerChangedField(QUESTIONNAIRE_STATE_FIELD, QUESTIONNAIRE_STATE_DELETED, "");
			return;
		}
		// анкету использовать, в заявке она есть
		if (useQuestionnaire && questionCount != 0)
		{
			if (!equalsQuestionnaire(claim))
			{
				removeQuestionnaire(claim);
				List<LoanClaimQuestion> questionnaire = findQuestionnaire();
				if (CollectionUtils.isEmpty(questionnaire))
					return;
				copyQuestionnaireToClaim(claim, questionnaire);
				stateMachineEvent.registerChangedField(QUESTIONNAIRE_STATE_FIELD, QUESTIONNAIRE_STATE_CHANGED, "");
			}
		}
	}

	private void removeQuestionnaire(ExtendedLoanClaim claim)
	{
		claim.removeAttribute("question_count");
		for (int i = 1; i <= 20; i++)
			for (String suffix : QUESTION_SUFFIXES)
				claim.removeAttribute(QUESTION_PREFIX + i + suffix);
	}

	private boolean equalsQuestionnaire(ExtendedLoanClaim claim) throws DocumentException
	{
		try
		{
			Set<String> questions = questionsToStrings(questionService.findQuestionnaire());
			Set<String> questionnaire = findQuestionnaire(claim);
			return questions.equals(questionnaire);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	private Set<String> questionsToStrings(List<LoanClaimQuestion> questions)
	{
		Set<String> strings = new TreeSet<String>();
		for (LoanClaimQuestion question : questions)
		{
			strings.add(String.valueOf(question.getAnswerType().toValue()));
			strings.add(question.getId().toString());
			strings.add(question.getQuestion());
		}
		return strings;
	}

	private Set<String> findQuestionnaire(ExtendedLoanClaim claim)
	{
		Set<String> questionnaire = new TreeSet<String>();
		for (int i = 1; i <= 20; i++)
			for (String suffix : QUESTION_SUFFIXES)
			{
				if (!"_answer".equals(suffix))
				{
					String attributeName = QUESTION_PREFIX + i + suffix;
					ExtendedAttribute attribute = claim.getAttribute(attributeName);
					if (attribute != null && attribute.getValue() != null && StringHelper.isNotEmpty(attribute.getStringValue()))
						questionnaire.add(attribute.getStringValue());
				}
			}
		return questionnaire;
	}

	private List<LoanClaimQuestion> findQuestionnaire() throws DocumentException
	{
		try
		{
			return questionService.findQuestionnaire();
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	private void copyQuestionnaireToClaim(ExtendedLoanClaim claim, List<LoanClaimQuestion> questionnaire)
	{
		int i = 0;
		for (LoanClaimQuestion question : questionnaire)
		{
			i++;
			claim.setAttributeValue("string", QUESTION_PREFIX + i + QUESTION_SUFFIXES[3], question.getQuestion());
			claim.setAttributeValue("string", QUESTION_PREFIX + i + QUESTION_SUFFIXES[2], question.getId().toString());
			claim.setAttributeValue("string", QUESTION_PREFIX + i + QUESTION_SUFFIXES[1], String.valueOf(question.getAnswerType().toValue()));
			claim.setAttributeValue("string", QUESTION_PREFIX + i + QUESTION_SUFFIXES[0], "");
		}
		claim.setAttributeValue("string", "question_count", String.valueOf(i));
	}
}
