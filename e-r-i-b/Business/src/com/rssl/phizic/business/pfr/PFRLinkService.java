package com.rssl.phizic.business.pfr;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Dorzhinov
 * @ created 18.02.2011
 * @ $Author $
 * @ $Revision $
 */
public class PFRLinkService
{
	private static final SimpleService simpleService = new SimpleService();

	/**
	 * ����������
	 * @param pfrLink
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public void addOrUpdate(final PFRLink pfrLink) throws BusinessException
	{
		simpleService.addOrUpdate(pfrLink, null);
	}

	/**
	 * ��������
	 * @param pfrLink
	 * @throws BusinessException
	 */
	public void remove(final PFRLink pfrLink) throws BusinessException
	{
		simpleService.remove(pfrLink, null);
	}

	/**
	 * ����� �������� �� id
	 * @param id �������������
	 * @return �������� ��� null
	 * @throws BusinessException
	 */
	public PFRStatement findById(Long id) throws BusinessException
	{
		return simpleService.findById(PFRStatement.class, id, null);
	}

	/**
	 * ����� �������� �� ������
	 * @param loginId id ������
	 * @return Statement ��� null
	 * @throws BusinessException
	 */
	public PFRLink findByLoginId(final Long loginId) throws BusinessException
	{
		if(loginId == null)
			throw new IllegalArgumentException("�������� 'loginId' �� ����� ���� null");

		try
		{
			return HibernateExecutor.getInstance(null).execute(new HibernateAction<PFRLink>()
			{
				public PFRLink run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.pfr.PFRLinkService.findByLoginId");
					query.setParameter("loginId", loginId);
					return (PFRLink)query.uniqueResult();
				}
			});
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
