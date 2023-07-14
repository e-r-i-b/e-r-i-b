package com.rssl.phizic.business.profile.images;

import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.MultiInstanceSimpleService;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.ExecutorQuery;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.Session;

import java.util.*;

/**
 * Сервис для работы с журналом изменения аватаров.
 *
 * @author bogdanov
 * @ created 23.10.14
 * @ $Author$
 * @ $Revision$
 */

public class AvatarJurnalService
{
	private static final MultiInstanceSimpleService simpleService = new MultiInstanceSimpleService();
	private static final SimpleService simpleServiceInner = new SimpleService();
	private static final String CSA_ADMIN = "CSAAdmin";

	/**
	 * Добавляем информацию об аватаре.
	 *
	 * @param phones телефоны.
	 * @param avatarPath путь к аватару.
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public void setAvatar(final Collection<String> phones, final String avatarPath, final Long loginId) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance(CSA_ADMIN).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					for (String phone : phones)
					{
						if (StringHelper.isEmpty(avatarPath))
							deleteAvatarInner(phone);
						else
						{
							AvatarLink link = new AvatarLink();
							link.setPhone(phone);
							link.setAvatarPath(avatarPath);
							link.setLoginId(loginId);
							simpleServiceInner.addOrUpdate(link);
						}

						RemoteAvatarInfo info = new RemoteAvatarInfo();
						info.setAvatarPath(avatarPath);
						info.setLastUpdateTime(Calendar.getInstance());
						info.setPhone(phone);
						info.setNodeId(ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getNodeNumber());
						simpleService.add(info, CSA_ADMIN);
					}

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
	 * Добавление информации об аватаре в локальную таблицу.
	 *
	 * @param phone телефон.
	 * @param avatarPath аватар.
	 * @throws BusinessException
	 */
	public void addAvatarInner(String phone, String avatarPath) throws BusinessException
	{
		deleteAvatarInner(phone);
		AvatarLink link = new AvatarLink();
		link.setPhone(phone);
		link.setAvatarPath(avatarPath);
		simpleServiceInner.addOrUpdate(link);
	}

	/**
	 * Удаление аватара.
	 *
	 * @param phone номер телефона.
	 */
	public void deleteAvatarInner(String phone) throws BusinessException
	{
		try
		{
			ExecutorQuery executorQuery = new ExecutorQuery(HibernateExecutor.getInstance(), "com.rssl.phizic.business.profile.images.AvatarLink.deletePyPhone");
			executorQuery.setParameter("phone", phone).executeUpdate();
		}
		catch (DataAccessException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param startTime начальная дата.
	 * @return список изменений после даты.
	 * @throws BusinessException
	 */
	public List<RemoteAvatarInfo> getDelta(final Calendar startTime, final int startRow, final int finishRow) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(CSA_ADMIN).execute(new HibernateAction<List<RemoteAvatarInfo>>()
			{
				public List<RemoteAvatarInfo> run(Session session) throws Exception
				{
					return session.getNamedQuery("com.rssl.phizic.business.profile.images.RemoteAvatarInfo.getDelta")
							.setParameter("time", startTime)
							.setParameter("notNodeId", ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getNodeNumber())
							.setFirstResult(startRow)
							.setMaxResults(finishRow - startRow)
							.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Удаление старого аватара.
	 * С учетом изменения номеров телефонов.
	 *
	 * @param loginId логин.
	 */
	public void deleteByLoginId(final long loginId, Collection<String> newPhones) throws BusinessException
	{
		try
		{
			List<AvatarLink> links = HibernateExecutor.getInstance().execute(new HibernateAction<List<AvatarLink>>()
			{
				public List<AvatarLink> run(Session session) throws Exception
				{
					return session.getNamedQuery("com.rssl.phizic.business.profile.images.AvatarLink.getByLoginId")
							.setParameter("loginId", loginId)
							.list();
				}
			});

			Set<String> removePhones = new HashSet<String>();
			for (AvatarLink link : links)
			{
				removePhones.add(link.getPhone());
			}

			for (String ph : newPhones)
			{
				removePhones.remove(ph);
			}

			setAvatar(removePhones, null, loginId);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получение аватара по номеру телефона
	 * @param phone - номер телефона
	 * @return - относительный путь до аватара, или null, если запись в БД по номеру телефона не найдена
	 */
	public String getAvatar(final String phone) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<String>()
			{
				public String run(Session session) throws Exception
				{
					return (String) session.getNamedQuery("com.rssl.phizic.business.profile.images.AvatarLink.getAvatarByPhone")
							.setParameter("phone", phone)
							.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
