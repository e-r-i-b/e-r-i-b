package com.rssl.phizic.limits.profile.information;

import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.limits.handlers.ProfileInfo;
import com.rssl.phizic.dataaccess.query.ExecutorQuery;
import com.rssl.phizic.limits.servises.ActiveRecord;
import com.rssl.phizic.limits.servises.Profile;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;


import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @author komarov
 * @ created 26.05.2014
 * @ $Author$
 * @ $Revision$
 */
public class ProfileInformation extends ActiveRecord implements Serializable
{
	private Long   profileId;
	private String informationType;
	private String data;

	public ProfileInformation(){}

	public ProfileInformation(Long profileId, String informationType, String data)
	{
		this.profileId = profileId;
		this.informationType = informationType;
		this.data = data;
	}

	public Long getProfileId()
	{
		return profileId;
	}

	public void setProfileId(Long profileId)
	{
		this.profileId = profileId;
	}

	public String getInformationType()
	{
		return informationType;
	}

	public void setInformationType(String informationType)
	{
		this.informationType = informationType;
	}

	public String getData()
	{
		return data;
	}

	public void setData(String data)
	{
		this.data = data;
	}

	/**
	 * Возвращает список с настройками по идентификатору профиля
	 * @param profileInfo - идентификатор профиля
	 * @return список с настройками
	 * @throws Exception
	 */
	public static List<ProfileInformation> find(final ProfileInfo profileInfo) throws Exception
	{
		return executeAtomic(new HibernateAction<List<ProfileInformation>>()
		{
			public List<ProfileInformation> run(Session session) throws Exception
			{
				Profile profile = Profile.findByProfileInfo(profileInfo);
				if(profile == null)
					return Collections.emptyList();

				/**
				 * Возвращает список настроек профиля из централизованного хранилища
				 * Опорный объект: PK_PROFILE_INFORMATION
				 * Предикаты доступа: "PROFILE_ID"=TO_NUMBER(:PROFILEID)
				 * Кардинальность: максимальная карднальность равна количеству настроек профиля, которые сохраняются в централизованное хранилище
				 */
				Criteria criteria = session.createCriteria(ProfileInformation.class);
				criteria.add(Expression.eq("profileId", profile.getId()));
				//noinspection unchecked
				return criteria.list();
			}
		});
	}

	/**
	 * Найти профиль по информации о профиле
	 * @return профиль
	 * @throws Exception
	 */
	public ProfileInformation addOrUpdate() throws Exception
	{
		ExecutorQuery executorQuery = new ExecutorQuery(getHibernateExecutor(), ProfileInformation.class.getName() + ".addOrUpdate");
		executorQuery.setParameter("profileId",         this.getProfileId());
		executorQuery.setParameter("informationType",   this.getInformationType());
		executorQuery.setParameter("data",              this.getData());
		executorQuery.executeUpdate();
		return this;
	}
}
