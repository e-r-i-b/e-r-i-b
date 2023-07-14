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
 * ������ ��� ������ � ��������� � ������ �� ������
 */
public class LoanClaimQuestionService
{
	private static final int MAX_RESULTS = 20;

	private static final SimpleService service = new SimpleService();

	/**
	 * ����� ��� ������
	 * @return ������
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
	 * �������� ������
	 * @param question ������
	 * @throws BusinessException
	 */
	public void update(LoanClaimQuestion question) throws BusinessException
	{
		service.addOrUpdate(question);
	}

	/**
	 * �������� ������
	 * @param question ������
	 * @throws BusinessException, BusinessLogicException
	 */
	public void add(LoanClaimQuestion question) throws BusinessException, BusinessLogicException
	{
		if (findById(question.getId()) != null)
			throw new BusinessLogicException("������ � ����� ��������������� ��� ������� � �������.");
		service.add(question);
	}

	/**
	 * ����� ������ �� ��������������
	 * @param id ������������� �������
	 * @return ������
	 * @throws BusinessException
	 */
	public LoanClaimQuestion findById(Long id) throws BusinessException
	{
		return service.findById(LoanClaimQuestion.class, id);
	}

	/**
	 * ������� ������ ��������
	 * @param question ������
	 * @throws BusinessException
	 */
	public void remove(LoanClaimQuestion question) throws BusinessException
	{
		service.remove(question);
	}

	/**
	 * ������������ ������
	 * @param id ������������� �������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void publish(Long id) throws BusinessException, BusinessLogicException
	{
		if (countByStatus(Status.PUBLISHED) >= 20)
			throw new BusinessLogicException("���������� �������������� �������� �� ������ ��������� 20 �������.");
		setStatusAndSave(id, Status.PUBLISHED);
	}

	/**
	 * ���������� �������� � �������� ��������
	 * @param status  - ������
	 * @return  ���������� ��������
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
	 * �������� ������� ���� �� ������ ��������������� �������
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
	 * ����� ������ � ����������
	 * @param id ������������ �������
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
			throw new BusinessLogicException("������ ��� " + (question.getStatus() == Status.PUBLISHED ? "�����������" : "���� � ����������"));
		question.setStatus(status);
		update(question);
	}
}
