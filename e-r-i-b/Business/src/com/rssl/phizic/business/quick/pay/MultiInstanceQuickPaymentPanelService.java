package com.rssl.phizic.business.quick.pay;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.dictionaries.synchronization.log.ChangeType;
import com.rssl.phizic.business.dictionaries.synchronization.log.DictionaryRecordChangeInfoService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dictionaries.synchronization.MultiBlockDictionaryRecord;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Calendar;
import java.util.List;

/**
 * @author komarov
 * @ created 21.02.2014
 * @ $Author$
 * @ $Revision$
 */
public class MultiInstanceQuickPaymentPanelService
{
	private static final DictionaryRecordChangeInfoService dictionaryRecordChangeInfoService = new DictionaryRecordChangeInfoService();
	protected static final SimpleService simpleService = new SimpleService();

	/**
	 * ���������� ��� ���������� ������ ������� ������(���).
	 * @param panel ���
	 * @param instance �������
	 * @return ������
	 * @throws com.rssl.phizic.business.BusinessException, BusinessLogicException
	 */
	public QuickPaymentPanel addOrUpdate(final QuickPaymentPanel panel, final String instance) throws BusinessException, BusinessLogicException
	{
		try
		{
			return HibernateExecutor.getInstance(instance).execute(new HibernateAction<QuickPaymentPanel>()
			{
				public QuickPaymentPanel run(Session session) throws Exception
				{
					QuickPaymentPanel saved = simpleService.addOrUpdateWithConstraintViolationException(panel, instance);
					addToLog(saved, ChangeType.update);
					return saved;
				}
			});
		}
		catch (ConstraintViolationException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� ������ ������� ������(���).
	 * @param panel ���
	 * @param instance �������
	 * @throws BusinessException
	 */
	public void remove(final QuickPaymentPanel panel, final String instance) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance(instance).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					simpleService.remove(panel, instance);
					addToLog(panel, ChangeType.delete);
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ����� ������ ������� ������(���) �� ��������������.
	 * @param id �������������
	 * @param instance �������
	 * @return ���
	 * @throws BusinessException
	 */
	public QuickPaymentPanel findById(Long id, String instance) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(QuickPaymentPanel.class);
		criteria.add(Expression.and(Expression.eq("id", id), Expression.ne("state", QuickPaymentPanelState.DELETED)));
		return simpleService.findSingle(criteria, instance);
	}

	/**
	 * ����� ������ ������ ������� ������(���) �� ��������.
	 * @param trb �������
	 * @param instance �������
	 * @return ����� ���
	 * @throws BusinessException
	 */
	public List<PanelBlock> findByTRB(final String trb, String instance) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instance).execute(new HibernateAction<List<PanelBlock>>()
			{
				public List<PanelBlock> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QuickPaymentPanel.class.getName() + ".findByTRB");
					query.setParameter("date", Calendar.getInstance());
					query.setParameter("TB", trb);
					//noinspection unchecked
					return (List<PanelBlock>) query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ����� ��������� ��� ������� ��� ������� ���.
	 * @param instance �������
	 * @return ��������
	 * @throws BusinessException
	 */
	public List<Department> findTRBwithQPP(String instance) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instance).execute(new HibernateAction<List<Department>>()
			{
				public List<Department> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QuickPaymentPanel.class.getName() + ".findTRBwithQPP");
					//noinspection unchecked
					return (List<Department>) query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private void addToLog(MultiBlockDictionaryRecord record, ChangeType changeType) throws BusinessException
	{
		dictionaryRecordChangeInfoService.addChangesToLog(record, changeType);
	}
}
