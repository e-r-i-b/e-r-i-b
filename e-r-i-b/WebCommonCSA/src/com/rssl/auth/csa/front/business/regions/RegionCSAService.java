package com.rssl.auth.csa.front.business.regions;

import com.rssl.auth.csa.front.exceptions.FrontException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

/**
 * @author komarov
 * @ created 18.03.2013
 * @ $Author$
 * @ $Revision$
 */
public class RegionCSAService
{
	private static final String CSA_INSTANCE_NAME = "CSA2";

	/**
	 * Поиск региона по идентификатору
	 * @param id - идентификатор
	 * @return регион
	 * @throws FrontException
	 */
	public Region findById(final Long  id) throws FrontException
	{
		try
		{
			return HibernateExecutor.getInstance(CSA_INSTANCE_NAME).execute(new HibernateAction<Region>()
			{
				public Region run(Session session)
				{
					Criteria criteria = session.createCriteria(Region.class);
					criteria.add(Expression.eq("id", id));
					return (Region) criteria.uniqueResult();
				}
			});
		}
		catch (Exception ex)
		{
			throw new FrontException(ex);
		}
	}
	/**
	 * Поиск региона по идентификатору
	 * @param code - код региона
	 * @return регион
	 * @throws FrontException
	 */
	public Region getRegionByCode(final String code) throws FrontException
	{
		try
		{
			return HibernateExecutor.getInstance(CSA_INSTANCE_NAME).execute(new HibernateAction<Region>()
			{
				public Region run(Session session)
				{
					Criteria criteria = session.createCriteria(Region.class);
					criteria.add(Expression.eq("synchKey", code));
					return ((Region) criteria.uniqueResult());
				}
			});
		}
		catch (Exception ex)
		{
			throw new FrontException(ex);
		}
	}

	/**
	 * Поиск региона по названию на латинице.
	 * Если найденный регион является дочерним, то ищем для него родительский.
	 * @param enName - название региона на латинице
	 * @return регион
	 * @throws FrontException
	 */
	public Region findParentByEnName(final String enName) throws FrontException
	{
		try
		{
			return HibernateExecutor.getInstance(CSA_INSTANCE_NAME).execute(new HibernateAction<Region>()
			{
				public Region run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(Region.class.getName() + ".findParentByEnName");
					query.setParameter("enName", enName);
					return (Region)query.uniqueResult();
				}
			});
		}
		catch (Exception ex)
		{
			throw new FrontException(ex);
		}
	}

	/**
	 * Обновление данных о регионе пользователя
 	 * @param userRegion сущность для хранения данных о регионе пользователя
	 * @return UserRegion
	 * @throws FrontException
	 */
	public UserRegion addUserRegion(final UserRegion userRegion)  throws FrontException
	{
		try
		{
			return HibernateExecutor.getInstance(CSA_INSTANCE_NAME).execute(new HibernateAction<UserRegion>()
			{
				public UserRegion run(Session session) throws Exception
				{
					session.save(userRegion);
					return userRegion;
				}
			});
		}
		catch (Exception ex)
		{
			throw new FrontException(ex);
		}
	}
	/**
	 * Сохранение данных о регионе пользователя
     * @param userRegion сущность для хранения данных о регионе пользователя
	 * @return UserRegion
	 * @throws FrontException
	 */
	public UserRegion updateUserRegion(final UserRegion userRegion)  throws FrontException
		{
			try
			{
				return HibernateExecutor.getInstance(CSA_INSTANCE_NAME).execute(new HibernateAction<UserRegion>()
				{
					public UserRegion run(Session session) throws Exception
					{
						session.update(userRegion);
						return userRegion;
					}
				});
			}
			catch (Exception ex)
			{
				throw new FrontException(ex);
			}
		}


	/**
	 * Поиск региона по идентификатору
	 * @param cookie - идентификатор
	 * @return регион
	 * @throws FrontException
	 */
	public UserRegion findByCookie(final String  cookie) throws FrontException
	{
		try
		{
			return HibernateExecutor.getInstance(CSA_INSTANCE_NAME).execute(new HibernateAction<UserRegion>()
			{
				public UserRegion run(Session session)
				{
					Criteria criteria = session.createCriteria(UserRegion.class);
					criteria.add(Expression.eq("cookie", cookie));
					return (UserRegion) criteria.uniqueResult();
				}
			});
		}
		catch (Exception ex)
		{
			throw new FrontException(ex);
		}
	}

	/**
	 * Поиск региона по идентификатору
	 * @param userRegion - данные о регионе пользователя
	 * @throws FrontException
	 */
	public void deleteUserRegion(final UserRegion userRegion) throws FrontException
	{
		try
		{
			HibernateExecutor.getInstance(CSA_INSTANCE_NAME).execute(new HibernateAction<Void>()
			{
				public Void run(Session session)
				{
					session.delete(userRegion);
					return null;
				}
			});
		}
		catch (Exception ex)
		{
			throw new FrontException(ex);
		}
	}

	/**
	 * Поиск региона по идентификатору пользователя
	 * @param profileId - идентификатор
	 * @return регион
	 * @throws FrontException
	 */
	public UserRegion findByUserId(final Long profileId) throws FrontException
	{
		try
		{
			return HibernateExecutor.getInstance(CSA_INSTANCE_NAME).execute(new HibernateAction<UserRegion>()
			{
				public UserRegion run(Session session)
				{
					Criteria criteria = session.createCriteria(UserRegion.class);
					criteria.add(Expression.eq("profileId", profileId));
					return (UserRegion) criteria.uniqueResult();
				}
			});
		}
		catch (Exception ex)
		{
			throw new FrontException(ex);
		}
	}
}
