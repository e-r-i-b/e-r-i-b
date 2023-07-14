package com.rssl.phizic.operations.loanclaim.questionnaire;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.loanclaim.questionnaire.LoanClaimQuestion;
import com.rssl.phizic.business.loanclaim.questionnaire.LoanClaimQuestionService;
import com.rssl.phizic.business.loanclaim.questionnaire.Status;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.loanclaim.LoanClaimConfig;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;

/**
 * @author Gulov
 * @ created 27.12.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * Операция для работы с вопросом заявки на кредит
 */
public class EditLoanClaimQuestionOperation extends OperationBase implements EditEntityOperation
{
	private static final LoanClaimQuestionService service = new LoanClaimQuestionService();

	private LoanClaimQuestion question;
	private boolean isNew = false;

	/**
	 * Инициализируем операцию.
	 * @param id id вопроса.
	 * @throws BusinessException
	 */
	public void initialize(Long id) throws BusinessException
	{
		question = service.findById(id);
	}

	/**
	 * Инициализируем операцию для нового вопроса.
	 * @throws BusinessException
	 */
	public void initializeNew() throws BusinessException
	{
		isNew = true;
		question = new LoanClaimQuestion();
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		if (isNew)
			service.add(question);
		else
			service.update(question);
	}


	public Object getEntity() throws BusinessException, BusinessLogicException
	{
		return question;
	}

	/**
	 * Опубликовать вопрос
	 * @param id идентификатор вопроса
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void publish(Long id) throws BusinessException, BusinessLogicException
	{
		service.publish(id);
	}

	/**
	 * Снять вопрос с публикации
	 * @param id идентификатор вопроса
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void unpublished(Long id) throws BusinessException, BusinessLogicException
	{
		if (ConfigFactory.getConfig(LoanClaimConfig.class).isUseQuestionnaire() && question.getStatus() == Status.PUBLISHED && service.countByStatus(Status.PUBLISHED) == 1)
		{
			throw new BusinessLogicException("Для отображения анкеты необходимо опубликовать хотя бы один вопрос.");
		}
		service.unpublished(id);
	}

}
