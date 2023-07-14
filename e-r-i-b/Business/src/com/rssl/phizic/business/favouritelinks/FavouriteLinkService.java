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
	 * Сохранение ссылки
	 * @param link - ссылка
	 * @return сохраненная ссылка
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
			throw new BusinessLogicException("Операция уже добавлена в Личное меню.", e);
		}
	}

	/**
	 * Поиск ссылок пользователя
	 * @param loginId - логин пользователя
	 * @return List ссылок
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
	 * Поиск используемых ссылок пользователя
	 * @param loginId - логин пользователя
	 * @return List ссылок
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
	 * Поиск  ссылок пользователя, включающих в себя заданный паттерн
	 * @param loginId - логин пользователя
	 * @param pattern - паттерн для поиска
	 * @return List ссылок
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
	 * Удаление ссылки
	 * @param link - идентификатор
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	void remove(FavouriteLink link) throws BusinessException
	{
		simpleService.remove(link);
	}

	/**
	 * Удаление ссылки
	 * @param id - идентификатор
	 * @param loginId - логин пользователя
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
