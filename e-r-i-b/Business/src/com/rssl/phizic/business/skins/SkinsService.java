package com.rssl.phizic.business.skins;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.groups.Group;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationType;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.List;

/**
 * @author egorova
 * @ created 12.03.2009
 * @ $Author$
 * @ $Revision$
 */
public class SkinsService
{
	private static final SimpleService simpleService = new SimpleService();

	/**
	 * Возвращает стиль по ID
	 * @param skinId - ID стиля
	 * @return стиль или null, если не найден
	 */
	public Skin getById(Long skinId) throws BusinessException
	{
		return simpleService.findById(Skin.class, skinId);
	}

	/**
	 * Список всех доступных скинов
	 * @return список всех скинов
	 * @throws BusinessException
	 */
	public List<Skin> getSkins() throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<Skin>>()
			{
				public List<Skin> run(Session session) throws Exception
				{
					//noinspection JpaQueryApiInspection
					Query query = session.getNamedQuery("com.rssl.phizic.business.skins.getAllSkins");
					//noinspection unchecked
					return (List<Skin>)query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException("Ошибка получения списка скинов. ", e);
		}
	}

	/**
	 * Получение активного скина в зависимости от имени приложения
	 * @param applType - имя приложения
	 * @return активный (текущий) скин
	 * @throws BusinessException
	 */
	public Skin getActiveSkin(final ApplicationType applType) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Skin>()
			{
				public Skin run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.skins.getActive"+applType+"Skin");
					//noinspection unchecked
					return (Skin)query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException("Ошибка получения активного скина. ", e);
		}
	}

	/**
	 * Получение активного скина в зависимости от имени приложения из SecurityConfig
	 * @return активный (текущий) скин
	 * @throws BusinessException
	 */
	public Skin getActiveSkin() throws BusinessException
	{
		return getActiveSkin(getApplType());
	}

	/**
	 * Получаем тип приложения.
	 * @return Если клиентское, то Client, любое другое Admin.
	 */
	private ApplicationType getApplType()
	{
		ApplicationConfig applicationConfig = ApplicationConfig.getIt();
		Application application = applicationConfig.getLoginContextApplication();
		switch (application)
		{
			case PhizIC:
				return ApplicationType.Client;
			case atm:
				return ApplicationType.Client;
			default:
				if(applicationConfig.getApplicationInfo().isMobileApi())
					return ApplicationType.Client;
				return ApplicationType.Admin;
		}
	}

	/**
	 * Получение настройки к глобальным стилям и картинкам.
	 * systemName='global', хранится также как и скины.
	 * @return глобальная настройка в формате скина
	 * @throws BusinessException
	 */
	public Skin getGlobalUrl() throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Skin>()
			{
				public Skin run(Session session) throws Exception
				{
					//noinspection JpaQueryApiInspection
					Query query = session.getNamedQuery("com.rssl.phizic.business.skins.getGlobalUrl");
					//noinspection unchecked
					return (Skin)query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException("Ошибка получения глобальной настройки. ", e);
		}
	}

	/**
	 * Найти скин по системному имени. Применяется при загрузке скина таском.
	 * @param systemName - системное имя скина
	 * @return скин или null
	 * @throws BusinessException
	 */
	public Skin findBySystemName(final String systemName) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Skin>()
			{
				public Skin run(Session session) throws Exception
				{
					//noinspection JpaQueryApiInspection
					Query query = session.getNamedQuery("com.rssl.phizic.business.skins.findBySystemName");
					query.setParameter("systemName", systemName);
					//noinspection unchecked
					return (Skin)query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException("Ошибка получения скина по системному имени. ", e);
		}
	}

	/**
	 * Смена активного (текущего) скина
	 * @param applType - тип приложения
	 * @param skinIdToActivate - новый активный скин
	 * @throws BusinessException
	 */
	public void changeCurrentSkin(ApplicationType applType, Long skinIdToActivate) throws BusinessException
	{
		Skin currentActiveSkin = getActiveSkin(applType);
		Skin skin = simpleService.findById(Skin.class, skinIdToActivate);

		if (currentActiveSkin.getId().equals(skin.getId()))
			return;

		if (applType.equals(ApplicationType.Client))
		{
			skin.setClientDefaultSkin(true);
			currentActiveSkin.setClientDefaultSkin(false);
		}
		
		if (applType.equals(ApplicationType.Admin))
		{
			skin.setAdminDefaultSkin(true);
			currentActiveSkin.setAdminDefaultSkin(false);
		}
		simpleService.update(skin);
		simpleService.update(currentActiveSkin);
	}

	/**
	 * Сменить url глобальной настройки
	 * @param newUrl - новый url
	 * @throws BusinessException
	 */
	public void changeGlobalUrl(String newUrl) throws BusinessException
	{
		Skin globalUrl = getGlobalUrl();
		if (globalUrl.getUrl().equals(newUrl))
			return;
		globalUrl.setUrl(newUrl);
		simpleService.update(globalUrl);
	}

	/**
	 * Удалить стиль
	 * @param skin - стиль
	 */
	public void remove(final Skin skin) throws BusinessException, BusinessLogicException
	{
		if (skin == null)
			throw new NullPointerException("Аргумент 'skin' не может быть null");

		try
		{
			//noinspection OverlyComplexAnonymousInnerClass
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				@SuppressWarnings({"JpaQueryApiInspection"})
				public Void run(Session session) throws Exception
				{
					//noinspection TooBroadScope
					Query query;

					// 1. Проверка
					if (skin.isSystem())
						throw new BusinessLogicException("Нельзя удалить системный стиль \""+skin.getName()+"\"");

					if (skin.isDefaultSkin())
						throw new BusinessLogicException("Нельзя удалить активный стиль \""+skin.getName()+"\"");

					query = session.getNamedQuery("com.rssl.phizic.business.skins.getAllSkins");
					query.setFirstResult(1);
					query.setMaxResults(1);
					if (query.uniqueResult() == null)
						throw new BusinessLogicException("Нельзя удалить единственный стиль");

					Group group = null;
					if (skin.getId() != null) {
						//noinspection ReuseOfLocalVariable
						query = session.getNamedQuery("com.rssl.phizic.business.skins.findGroupByDefaultSkin");
						query.setLong("skin_id", skin.getId());
						query.setMaxResults(1);
						group = (Group) query.uniqueResult();
					}
					if (group != null)
						throw new BusinessLogicException("Нельзя удалить стиль \""+skin.getName()+"\". " +
								"Стиль используется группой пользователей \"" + group.getName() + "\" в качестве стиля по-умолчанию");

					// 2. Удаление
					// Убираем индивидуальные стили у пользователей, т.к. удаляем стиль
					if (skin.getId() != null) {
						query = session.getNamedQuery("com.rssl.phizic.business.skins.removePersonSkin");
						query.setLong("skinId", skin.getId());
						query.executeUpdate();
					}

					session.delete(skin);

					return null;
				}
			});
		}
		catch (BusinessLogicException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить список стандартных стилей (стили для сотрудников и общие стили для клиентов)
 	 * @return List<Skin>
	 */
	public List<Skin> getStandartSkins() throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<Skin>>()
			{
				public List<Skin> run(Session session) throws Exception
				{
					//noinspection JpaQueryApiInspection
					Query query = session.getNamedQuery("com.rssl.phizic.business.skins.getStandartSkins");
					//noinspection unchecked
					return (List<Skin>) query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException("Ошибка получения списка стандартных скинов.", e);
		}
	}

	/**
	 * Сохраняет стиль
	 * @param skin - стиль
	 */
	public void addOrUpdate(final Skin skin) throws BusinessException, BusinessLogicException
	{
		validateSkin(skin);
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				@SuppressWarnings({"unchecked", "JpaQueryApiInspection"})
				public Void run(Session session) throws Exception
				{
					boolean isNewSkin = skin.getId() == null;
					session.saveOrUpdate(skin);
					session.flush();
					
					if (!isNewSkin)
					{
						// Убираем умолчательные стили у групп, если они стали им не доступны
						Query query = session.getNamedQuery("com.rssl.phizic.business.skins.findBadGroupDefaultSkin");
						List<Long> ids = query.list();
						if (!CollectionUtils.isEmpty(ids)) {
							query = session.getNamedQuery("com.rssl.phizic.business.skins.removeGroupDefaultSkin");
							query.setParameterList("groupIds", ids);
							query.executeUpdate();
						}
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
	 * Валидация скина перед сохранением
	 * @param skin - новый скин
	 * @throws BusinessLogicException, если скин не валиден
	 */
	private void validateSkin(Skin skin) throws BusinessException, BusinessLogicException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(Skin.class);
		criteria.add(Expression.eq("name",skin.getName()));
		if (skin.getId() != null)
			criteria.add(Expression.not(Expression.eq("id",skin.getId())));
		if(!CollectionUtils.isEmpty(simpleService.find(criteria, 1)))
			throw new BusinessLogicException("В системе уже существует скин с именем " + skin.getName());
	}

	/**
	 * Получить список доступных пользователю скинов, включая индивидуальный стиль пользователя
	 * @param personLoginId - ID логина пользователя
	 * @return List<Skin>
	 */
	public List<Skin> getPersonAvailableSkins(final long personLoginId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<Skin>>()
			{
				public List<Skin> run(Session session) throws Exception
				{
					//noinspection JpaQueryApiInspection
					Query query = session.getNamedQuery("com.rssl.phizic.business.skins.getPersonAvailableSkins");
					query.setLong("loginId", personLoginId);
					//noinspection unchecked
					return (List<Skin>) query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException("Ошибка получения списка доступных пользователю скинов", e);
		}
	}

	/**
	 * Возвращает текущий стиль пользователя
	 * @param personLoginId - ID логина пользователя
	 * @return текущий стиль пользователя:
	 * - либо индивидуальный,
	 * - либо умолчательный стиль наиболее приоритетной группы
	 * - либо стандартный стиль клиентского приложения
	 */
	public Skin getPersonActiveSkin(final long personLoginId) throws BusinessException
	{
		Skin personActiveSkin;
		try
		{
			personActiveSkin = HibernateExecutor.getInstance().execute(new HibernateAction<Skin>()
			{
				public Skin run(Session session) throws Exception
				{
					//noinspection JpaQueryApiInspection
					Query query = session.getNamedQuery("com.rssl.phizic.business.skins.getPersonActiveSkin");
					query.setLong("loginId", personLoginId);
					return (Skin) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException("Ошибка получения текущего скина для пользователя LOGIN_ID=" + personLoginId, e);
		}

		if (personActiveSkin == null)
			throw new BusinessException("Не найден текущий стиль для пользователя LOGIN_ID=" + personLoginId);
		return personActiveSkin;
	}

	/**
	 * Возврашает список доступных группе стилей
	 * @param groupId - ID группы (null, если группа новая)
	 * @return список стилей либо пустой список, если группе не доступен ни один стиль
	 */
	public List<Skin> getGroupAvailableSkins(final Long groupId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<Skin>>()
			{
				public List<Skin> run(Session session)
				{
					Query query;
					if (groupId == null)
					{
						//noinspection JpaQueryApiInspection
						query = session.getNamedQuery("com.rssl.phizic.business.skins.getNewGroupAvailableSkins");
					}
					else
					{
						//noinspection JpaQueryApiInspection
						query = session.getNamedQuery("com.rssl.phizic.business.skins.getExistsGroupAvailableSkins");
						query.setLong("groupId", groupId);
					}
					//noinspection unchecked
					return (List<Skin>) query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException("Ошибка получения списка доступных группе скинов", e);
		}
	}
}
