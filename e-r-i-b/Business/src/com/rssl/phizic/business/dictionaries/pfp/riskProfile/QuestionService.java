package com.rssl.phizic.business.dictionaries.pfp.riskProfile;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.PFPDictionaryServiceBase;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.common.types.SegmentCodeType;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Collection;
import java.util.List;

/**
 * @author akrenev
 * @ created 08.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class QuestionService
{
	private static final PFPDictionaryServiceBase service = new PFPDictionaryServiceBase();

	/**
	 * найти вопрос
	 * @param id идентификатор вопроса
	 * @param instance им€ инстанса модели Ѕƒ
	 * @return вопрос
	 * @throws BusinessException
	 */
	public Question getById(final Long id, String instance) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(Question.class);
		criteria.add(Expression.and(Expression.eq("id", id), Expression.eq("deleted", false)));
		return service.findSingle(criteria, instance);
	}

	/**
	 * ¬озвращает список вопросов дл€ определени€ риск профил€ клиента
	 * “ак как при текущей постановке в ‘“, весь список вопросов должен отображатьс€ пользователю на одной странице(при определении риск профил€), то
	 * используем этот метод.
	 * ≈сли будет мен€тьс€ способ ответов на вопросы, то крайне желательно данный матод TODO ”далить, можем получить проблемы по пам€ти
	 * @param segment сегмент клиентов дл€ которого строитс€ список
	 * @return список вопросов
	 * @throws BusinessException
	 */
	public List<Question> getAll(SegmentCodeType segment) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(Question.class);
		criteria.add(Expression.eq("deleted", false));
		criteria.add(Expression.eq("segment", segment));
		return service.find(criteria);
	}

	/**
	 * @param ids идентификаторы вопросов
	 * @return список вопросов
	 * @throws BusinessException
	 */
	public List<Question> getByIds(Collection<Long> ids) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(Question.class);
		criteria.add(Expression.in("id", ids));
		return service.find(criteria);
	}

	private void markDeleted(Session session, final Question question)
	{
		Query query = session.getNamedQuery(Question.class.getName() + ".markDeleted");
		query.setLong("questionId", question.getId());
		query.executeUpdate();
	}

	private boolean isMutable(Session session, final Question question)
	{
		Query query = session.getNamedQuery(Question.class.getName() + ".isMutable");
		query.setLong("questionId", question.getId());
		return (Boolean) query.uniqueResult();
	}

	/**
	 * сохранить вопрос
	 * @param question сохран€емый вопрос
	 * @param instance им€ инстанса модели Ѕƒ
	 * @return вопрос
	 * @throws BusinessException
	 */
	public Question addOrUpdate(final Question question, final String instance) throws BusinessException, BusinessLogicException
	{
		try
		{
			return HibernateExecutor.getInstance(instance).execute(new HibernateAction<Question>()
			{
				public Question run(Session session) throws Exception
				{
					Question savingQuestion = question;
					//если вопрос прив€зан к кому нибудь, то делаем копию, а старый помечаем удаленным
					if (savingQuestion.getId() != null && MultiBlockModeDictionaryHelper.needExternalCheck() && !isMutable(session, savingQuestion))
					{
						savingQuestion = new Question(question);
						markDeleted(session, question);
					}
					service.addOrUpdate(savingQuestion, instance);
					return savingQuestion;
				}
			}
			);
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (ConstraintViolationException e)
		{
			throw new BusinessLogicException("Ќевозможно добавить вопрос.", e);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * сохранить вопрос
	 * @param question сохран€емый вопрос
	 * @return вопрос
	 * @throws BusinessException
	 */
	public Question addOrUpdate(final Question question) throws BusinessException, BusinessLogicException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Question>()
			{
				public Question run(Session session) throws Exception
				{
					Question savingQuestion = question;
					//если вопрос прив€зан к кому нибудь, то делаем копию, а старый помечаем удаленным
					if (savingQuestion.getId() != null && !isMutable(session, savingQuestion))
					{
						savingQuestion = new Question(question);
						markDeleted(session, question);
					}
					service.addOrUpdate(savingQuestion);
					return savingQuestion;
				}
			}
			);
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (ConstraintViolationException e)
		{
			throw new BusinessLogicException("Ќевозможно добавить вопрос.", e);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * удалить вопрос
	 * @param question удал€емый вопрос
	 * @param instance им€ инстанса модели Ѕƒ
	 * @throws BusinessException
	 */
	public void remove(final Question question, final String instance) throws BusinessException, BusinessLogicException
	{
		try
		{
			HibernateExecutor.getInstance(instance).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					if (!MultiBlockModeDictionaryHelper.needExternalCheck() || isMutable(session, question))
						service.remove(question, instance);
					else
						markDeleted(session, question);

					return null;
				}
			}
			);
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (ConstraintViolationException e)
		{
			throw new BusinessLogicException("Ќевозможно удалить вопрос.", e);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * удалить вопрос
	 * @param question удал€емый вопрос
	 * @throws BusinessException
	 */
	public void remove(final Question question) throws BusinessException, BusinessLogicException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					if (isMutable(session, question))
						service.remove(question);
					else
						markDeleted(session, question);

					return null;
				}
			}
			);
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (ConstraintViolationException e)
		{
			throw new BusinessLogicException("Ќевозможно удалить вопрос.", e);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * подсчет веса ответов
	 * @param answerIds идентификаторы ответов
	 * @return вес ответов
	 * @throws BusinessException
	 */
	public Integer calculateAnswersWeight(final List<Long> answerIds) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Integer>()
			{
				public Integer run(Session session)
				{
					Query query = session.getNamedQuery(Question.class.getName() + ".calculateAnswersWeight");
					query.setParameterList("answerIds",answerIds);
					return (Integer)query.uniqueResult();
				}
			}
			);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
