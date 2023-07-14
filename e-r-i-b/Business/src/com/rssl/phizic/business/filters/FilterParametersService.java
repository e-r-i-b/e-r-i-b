package com.rssl.phizic.business.filters;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * @author mihaylov
 * created 15.10.2008
 * @ $Author$
 * @ $Revision$
 */
public class FilterParametersService
{
	private static final SimpleService simpleService = new SimpleService();

	/**
	 * ���������� ���������� ����������
	 * @param fp ����������� ��������� ����������
	 * @return ����������� ��������� ����������
	 * @throws BusinessException ������ ����������
	 */
	public FilterParametersByUrl addOrUpdate(final FilterParametersByUrl fp) throws BusinessException
	{
		return simpleService.addOrUpdate(fp);
	}

	/**
	 * �������� ���������� ����������
	 * @param fp ��������� ��������� ����������
	 * @throws BusinessException ������ ��������
	 */
	public void delete(final FilterParametersByUrl fp) throws BusinessException
	{
		simpleService.remove(fp);
	}

	/**
	 * @param sessionId ������������� ������
	 * @param url ��� �������� ��� ����������
	 * @return ��������� ����������
	 * @throws BusinessException
	 */
	public FilterParametersByUrl getBySessionIdAndUrl(final String sessionId, final String url) throws BusinessException
	{
		DetachedCriteria dc = DetachedCriteria.forClass(FilterParametersByUrl.class);
		dc.add(Expression.eq("sessionId", sessionId));
		dc.add(Expression.eq("url", url));
		List<FilterParametersByUrl> results = simpleService.find(dc);
		return results.size() == 1 ? results.get(0) : null;
	}

	/**
	 * ������� ��� ��������� �������� ���������� ������ (�� ���������)
	 * @param sessionId ������������� ������
	 * @throws BusinessException
	 */
	public void removeAllForSession(final String sessionId) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.filters.FilterParameterService.deleteAllForSession");
					query.setParameter("sessionId", sessionId);
					query.executeUpdate();
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
