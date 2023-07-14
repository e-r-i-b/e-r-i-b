package com.rssl.phizic.business.favouritelinks;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

/**
 * @author mihaylov
 * @ created 28.04.2010
 * @ $Author$
 * @ $Revision$
 */

class FavouriteLinkService
{
	private SimpleService simpleService = new SimpleService();

	/**
	 * ���������� ������
	 * @param link - ������
	 * @return ����������� ������
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	FavouriteLink addOrUpdate(final FavouriteLink link) throws BusinessException, BusinessLogicException
	{
		try
        {
			return simpleService.addOrUpdateWithConstraintViolationException(link);
        }
		catch(ConstraintViolationException e)
		{
			throw new BusinessLogicException("�������� ��� ��������� � ������ ����.", e);
		}
	}

	/**
	 * ����� ������ ������������
	 * @param loginId - ����� ������������
	 * @return List ������
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	List<FavouriteLink> findByUserId(final long loginId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<FavouriteLink>>()
				{
					public List<FavouriteLink> run(Session session)
					{
						Query query = session.getNamedQuery("com.rssl.phizic.business.favouritelinks.FavouriteLink.getUserLinks");
						query.setParameter("loginId", loginId);						
						return query.list();
					}
				}
			);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}				
	}


	/**
	 * ����� ������������ ������ ������������
	 * @param loginId - ����� ������������
	 * @return List ������
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	List<FavouriteLink> findUsedByUserId(final long loginId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<FavouriteLink>>()
				{
					public List<FavouriteLink> run(Session session)
					{
						Query query = session.getNamedQuery("com.rssl.phizic.business.favouritelinks.FavouriteLink.getUsedUserLinks");
						query.setParameter("loginId", loginId);
						return query.list();
					}
				}
			);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �����  ������ ������������, ���������� � ���� �������� �������
	 * @param loginId - ����� ������������
	 * @param pattern - ������� ��� ������
	 * @return List ������
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	List<FavouriteLink> findByUserIdPattern(final long loginId, final String pattern) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<FavouriteLink>>()
				{
					public List<FavouriteLink> run(Session session)
					{
						Query query = session.getNamedQuery("com.rssl.phizic.business.favouritelinks.FavouriteLink.getUserLinksByPattern");
						query.setParameter("loginId", loginId);
						query.setParameter("pattern", pattern);
						return query.list();
					}
				}
			);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� ������
	 * @param link - �������������
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	void remove(FavouriteLink link) throws BusinessException
	{
		simpleService.remove(link);
	}

	/**
	 * �������� ������
	 * @param id - �������������
	 * @param loginId - ����� ������������
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	void removeById(final Long id, final long loginId) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
				{
					public Void run(Session session)
					{
						Query query = session.getNamedQuery("com.rssl.phizic.business.favouritelinks.FavouriteLink.removeById");
						query.setParameter("id", id);
						query.setParameter("loginId", loginId);
						query.executeUpdate();
						return null;
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
