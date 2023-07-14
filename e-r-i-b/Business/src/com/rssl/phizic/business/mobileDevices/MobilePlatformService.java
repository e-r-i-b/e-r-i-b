package com.rssl.phizic.business.mobileDevices;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.common.types.VersionNumber;
import com.rssl.phizic.common.types.exceptions.MalformedVersionFormatException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.cache.Cache;
import com.rssl.phizic.utils.cache.OnCacheOutOfDateListener;
import com.rssl.phizic.utils.mobile.MobileAPIVersions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Сервис для работы с мобильными платформами
 * @author Jatsky
 * @ created 01.08.13
 * @ $Author$
 * @ $Revision$
 */

public class MobilePlatformService extends SimpleService
{
	private static final SimpleService simpleService = new SimpleService();

	public static final Cache<String, MobilePlatform> mobilePlatformById = new Cache<String, MobilePlatform>(new OnCacheOutOfDateListener<String, MobilePlatform>()
	{
		public MobilePlatform onRefresh(final String key)
		{
			try
			{
				return HibernateExecutor.getInstance().execute(new HibernateAction<MobilePlatform>()
				{
					public MobilePlatform run(Session session) throws Exception
					{
						Query query = session.getNamedQuery(MobilePlatform.class.getName() + ".findByPlatformId");
						query.setParameter("platformId", key);

						return (MobilePlatform) query.uniqueResult();
					}
				});
			}
			catch (Exception e)
			{
				throw new RuntimeException(e);
			}
		}
	}, 15L);

	/**
	 * Получение мобильной платформы по ИД платформы
	 * @param  platformId ИД платформы
	 * @return мобильная платформа
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public MobilePlatform findByPlatformId(final String platformId) throws BusinessException
	{
		try
		{
			return mobilePlatformById.getValue(platformId);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получение мобильной платформы по ИД платформы без учета регистра
	 * @param  platformId ИД платформы
	 * @return мобильная платформа
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public MobilePlatform findByPlatformIdIgnoreCase(final String platformId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<MobilePlatform>()
			{
				public MobilePlatform run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(MobilePlatform.class.getName() + ".findByPlatformIdIgnoreCase");
					query.setParameter("platformId", platformId);

					return (MobilePlatform) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получение мобильной платформы по названию платформы
	 * @param  platformName название платформы
	 * @return мобильная платформа
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public MobilePlatform findByPlatformName(final String platformName) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<MobilePlatform>()
			{
				public MobilePlatform run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(MobilePlatform.class.getName() + ".findByPlatformName");
					query.setParameter("platformName", platformName);

					return (MobilePlatform) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public List<MobilePlatform> getAll() throws BusinessException
	{
		return simpleService.getAll(MobilePlatform.class);
	}

	/*
	* Опорный объект: MOBILE_PLATFORMS
	* Предикаты доступа: fullscan
	* Кардинальность: не более 10
	*/
	public List<MobilePlatform> getMobile() throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(MobilePlatform.class);
		criteria.add(Expression.eq("social", false));
		return simpleService.find(criteria);
	}

	/*
	* Опорный объект: MOBILE_PLATFORMS
	* Предикаты доступа: fullscan
	* Кардинальность: не более 10
	*/
	public List<MobilePlatform> getSocial() throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(MobilePlatform.class);
		criteria.add(Expression.eq("social", true));
		return simpleService.find(criteria);
	}

	public Set<String> getSocialPlatformIds() throws BusinessException
	{
		Set<String> socialPlatformIds = new HashSet<String>();
		for (MobilePlatform socialPlatform : getSocial())
		{
			socialPlatformIds.add(socialPlatform.getPlatformId());
		}
		return socialPlatformIds;
	}

	/**
	 * @param appType    тип платформы
	 * @param version    версия протокола
	 * @return использовать ли капчу на регистрации в mAPI
	 */
	public boolean isRegistrationUseCaptcha(final String appType, final String version) throws BusinessException
	{
		VersionNumber versionNumber;
		try
		{
			versionNumber = VersionNumber.fromString(version);
		}
		catch (MalformedVersionFormatException e)
		{
			throw new IllegalArgumentException("Некорректный номер версии " + version, e);
		}

		if (StringHelper.isEmpty(appType))
			throw new IllegalArgumentException("Не указан параметр appType");
		MobilePlatform mobilePlatform = findByPlatformId(appType);

		return versionNumber.ge(MobileAPIVersions.V5_01) && mobilePlatform.isUseCaptcha();
	}

    /**
     * @param appType тип платформы
     * @return использовать ли капчу на регистрации в socialAPI
     */
    public boolean isRegistrationUseCaptcha(final String appType) throws BusinessException
    {
        if (StringHelper.isEmpty(appType))
            throw new IllegalArgumentException("Не указан параметр appType");
        MobilePlatform mobilePlatform = findByPlatformId(appType);

        return mobilePlatform.isUseCaptcha();
    }
}
