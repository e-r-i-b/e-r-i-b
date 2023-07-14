package com.rssl.phizic.business.loanclaim.questionnaire;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.loanclaim.LoanClaimConfig;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.List;

/**
 * @author Gulov
 * @ created 25.12.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * Сервис для работы с вопросами в заявке на кредит
 */
public class LoanClaimQuestionService
{
	private static final int MAX_RESULTS = 20;

	private static final SimpleService service = new SimpleService();

	/**
	 * Найти все записи
	 * @return список
	 * @throws BusinessException
	 */
	public List<LoanClaimQuestion> findAll() throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<LoanClaimQuestion>>()
			{
				public List<LoanClaimQuestion> run(Session session)
				{
					//noinspection unchecked
					return session.getNamedQuery("com.rssl.phizic.operations.loanclaim.questionnaire.ListLoanClaimQuestionOperation.list").list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public List<LoanClaimQuestion> findQuestionnaire() throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(LoanClaimQuestion.class);
		criteria.add(Expression.eq("status", Status.PUBLISHED));
		return service.find(criteria, MAX_RESULTS);
	}

	/**
	 * Обновить вопрос
	 * @param question вопрос
	 * @throws BusinessException
	 */
	public void update(LoanClaimQuestion question) throws BusinessException
	{
		service.addOrUpdate(question);
	}

	/**
	 * Добавить вопрос
	 * @param question вопрос
	 * @throws BusinessException, BusinessLogicException
	 */
	public void add(LoanClaimQuestion question) throws BusinessException, BusinessLogicException
	{
		if (findById(question.getId()) != null)
			throw new BusinessLogicException("Вопрос с таким идентификатором уже заведен в системе.");
		service.add(question);
	}

	/**
	 * Найти вопрос по идентификатору
	 * @param id идентификатор вопроса
	 * @return вопрос
	 * @throws BusinessException
	 */
	public LoanClaimQuestion findById(Long id) throws BusinessException
	{
		return service.findById(LoanClaimQuestion.class, id);
	}

	/**
	 * Удалить список вопросов
	 * @param question вопрос
	 * @throws BusinessException
	 */
	public void remove(LoanClaimQuestion question) throws BusinessException
	{
		service.remove(question);
	}

	/**
	 * Опубликовать вопрос
	 * @param id идентификатор вопроса
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void publish(Long id) throws BusinessException, BusinessLogicException
	{
		if (countByStatus(Status.PUBLISHED) >= 20)
			throw new BusinessLogicException("Количество опубликованных вопросов не должно превышать 20 записей.");
		setStatusAndSave(id, Status.PUBLISHED);
	}

	/**
	 * Количество вопросов с заданным статусом
	 * @param status  - статус
	 * @return  количество вопросов
	 * @throws BusinessException
	 */
	public int countByStatus(final Status status) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Integer>()
			{
				public Integer run(Session session)
				{
					return (Integer) session.getNamedQuery("com.rssl.phizic.business.loanclaim.questionnaire.count")
							.setParameter("status", status)
							.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Проверка наличия хотя бы одного опубликованного вопроса
	 * @return
	 * @throws BusinessException
	 */
	public boolean isPublishedExist() throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session)
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.loanclaim.question.isPublishedExist");
					query.setMaxResults(1);
					return query.uniqueResult() != null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Снять вопрос с публикации
	 * @param id идентфикатор вопроса
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void unpublished(Long id) throws BusinessException, BusinessLogicException
	{
		setStatusAndSave(id, Status.UNPUBLISHED);
	}

	private void setStatusAndSave(Long id, Status status) throws BusinessException, BusinessLogicException
	{
		LoanClaimQuestion question = findById(id);
		if (question == null)
			return;
		if (question.getStatus() == status)
			throw new BusinessLogicException("Вопрос уже " + (question.getStatus() == Status.PUBLISHED ? "опубликован" : "снят с публикации"));
		question.setStatus(status);
		update(question);
	}
}
