package com.rssl.auth.csa.back.servises.nodes;

import com.rssl.auth.csa.back.servises.ActiveRecord;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.phizic.common.types.csa.MigrationState;
import com.rssl.phizic.common.types.csa.ProfileType;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Calendar;
import java.util.List;

/**
 * @author krenev
 * @ created 21.08.2013
 * @ $Author$
 * @ $Revision$
 * Связь клиента с блоком
 */

public class ProfileNode extends ActiveRecord
{
	/**
	 * Состояние связи клиента с блоком
	 */
	public static enum State
	{
		WAIT_MIGRATION,           //ожидается миграция данных из этого блока
		PROCESS_MIGRATION,        //идет миграция, происходит перенос данных из этого блока в другой
		ACTIVE                    //клиент связан с блоком, процесс миграции завершен
	}

	private Long id;
	private Calendar creationDate;
	private Profile profile;
	private Node node;
	private State state;
	private ProfileType profileType;

	/**
	 * @return идентификатор связи
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id идентифкиатор связи
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return дата создания связи
	 */
	public Calendar getCreationDate()
	{
		return creationDate;
	}

	/**
	 * @param creationDate дата создания связи
	 */
	public void setCreationDate(Calendar creationDate)
	{
		this.creationDate = creationDate;
	}

	/**
	 * @return профиль
	 */
	public Profile getProfile()
	{
		return profile;
	}

	/**
	 * @param profile профиль
	 */
	public void setProfile(Profile profile)
	{
		this.profile = profile;
	}

	/**
	 * @return блок
	 */
	public Node getNode()
	{
		return node;
	}

	/**
	 * @param node блок
	 */
	public void setNode(Node node)
	{
		this.node = node;
	}

	/**
	 * @return состояние связи
	 */
	public State getState()
	{
		return state;
	}

	/**
	 * @param state состояние связи
	 */
	public void setState(State state)
	{
		this.state = state;
	}

	/**
	 * @return тип профиля в блоке
	 */
	public ProfileType getProfileType()
	{
		return profileType;
	}

	/**
	 * @param profileType тип профиля в блоке
	 */
	public void setProfileType(ProfileType profileType)
	{
		this.profileType = profileType;
	}

	/**
	 * Создать связку для основного профиля с наполняющимя блоком
	 * @param profile профиль
	 * @return связка профиля с узлом
	 */
	public static ProfileNode create(Profile profile) throws Exception
	{
		if (profile == null)
		{
			throw new IllegalArgumentException("Профиль не может быть null");
		}
		return create(profile, Node.getFilling(), ProfileType.MAIN);
	}

	/**
	 * Создать связку профиля с блоком с заданным типом профиля
	 * Связка создается в статусе новая
	 * @param profile профиль
	 * @param node блок
	 * @param profileType тип профиля в блоке
	 * @return связка профиля с блоком
	 */
	public static ProfileNode create(Profile profile, Node node, ProfileType profileType) throws Exception
	{
		if (profile == null)
		{
			throw new IllegalArgumentException("Профиль не может быть null");
		}
		if (node == null)
		{
			throw new IllegalArgumentException("Блок не может быть null");
		}
		if (profileType == null)
		{
			throw new IllegalArgumentException("Тип профиля не может быть null");
		}
		ProfileNode profileNode = new ProfileNode();
		profileNode.setProfile(profile);
		profileNode.setNode(node);
		profileNode.setProfileType(profileType);
		profileNode.setState(ProfileType.MAIN == profileType ? State.ACTIVE : State.WAIT_MIGRATION);
		profileNode.save();
		log.debug("Для профиля " + profile.getId() + " создана новая связь с блоком " + node.getId() + " типа " + profileType);
		return profileNode;
	}

	/**
	 * Получить список всех связей профиля с блоками
	 * @param profile профиль
	 * @return список связей или пустой список
	 */
	public static List<ProfileNode> getByProfile(final Profile profile) throws Exception
	{
		if (profile == null)
		{
			throw new IllegalArgumentException("Профиль не может быть null");
		}
		return getHibernateExecutor().execute(new HibernateAction<List<ProfileNode>>()
		{
			public List<ProfileNode> run(org.hibernate.Session session) throws Exception
			{
				return (List<ProfileNode>) session.getNamedQuery("com.rssl.auth.csa.back.servises.nodes.ProfileNode.getByProfile")
						.setParameter("profile", profile)
						.list();
			}
		});
	}

	/**
	 * Удалить все узлы, привязанные к профилю
	 * @param profile профиль
	 * @throws Exception
	 */
	public static void removeProfileNodes(final Profile profile) throws Exception
	{
		if (profile == null)
		{
			throw new IllegalArgumentException("Профиль не может быть null");
		}
		getHibernateExecutor().execute(new HibernateAction<Void>()
		{
			public Void run(Session session) throws Exception
			{
				session.getNamedQuery("com.rssl.auth.csa.back.servises.nodes.ProfileNode.removeProfileNodes")
						.setParameter("profile", profile)
						.executeUpdate();
				return null;
			}
		});
	}

	/**
	 * Перепривязать связи клаента с блоками от профиля oldProfile к профилю actualProfile.
	 * @param oldProfile - профиль, от которого отвязываются связи
	 * @param actualProfile - профиль, к которому перепривязывается связи
	 * @return Количество перепривязанных записей. 0 - если ни одной связи  не перепривязанано
	 */
	public static Integer changeProfile(final Profile oldProfile, final Profile actualProfile) throws Exception
	{
		if (oldProfile == null)
		{
			throw new IllegalArgumentException("Старый профиль не может быть null");
		}
		if (actualProfile == null)
		{
			throw new IllegalArgumentException("Новый профиль не может быть null");
		}
		return getHibernateExecutor().execute(new HibernateAction<Integer>()
		{
			public Integer run(Session session) throws Exception
			{
				return session.getNamedQuery("com.rssl.auth.csa.back.servises.nodes.ProfileNode.changeProfile")
						.setParameter("old_profile", oldProfile)
						.setParameter("new_profile", actualProfile)
						.executeUpdate();
			}
		});
	}

	/**
	 * Рассчет состояния процесса миграции для связи клиента с основным блоком.
	 * Если у клиента имеются связи с резервными блоками и связи ожидают или уже находятся в процессе миграции,
	 * значит клиент находится в стадии "частичного" перехода из резервного блока в основной.
	 * @return состояние процесса миграции
	 * @param profileNode связь клиента с блоком
	 * @throws Exception
	 */
	public static MigrationState getMigrationState(final ProfileNode profileNode) throws Exception
	{
		if (profileNode == null)
		{
			throw new IllegalArgumentException("Связь клиента с блоком не может быть null");
		}
		//Состояние миграции рассчитывается для основного блока
		if (ProfileType.TEMPORARY == profileNode.getProfileType())
		{
			return null;
		}
		boolean isMigrationProcess = getHibernateExecutor().execute(new HibernateAction<Boolean>()
		{
			public Boolean run(Session session) throws Exception
			{
				return session.getNamedQuery("com.rssl.auth.csa.back.servises.nodes.ProfileNode.getMigratedTemporaryNodeCount")
						.setParameter("profile", profileNode.getProfile())
						.list().size() > 0;
			}
		});
		return isMigrationProcess ? MigrationState.PROCESS : MigrationState.COMPLETE;
	}

	/**
	 * поставить лок на профиль для исполнения документа сотрудником
	 * @param profile профиль клиента
	 * @return поставился ли лок
	 * @throws Exception
	 */
	public static boolean lockProfileForExecuteDocument(final Profile profile) throws Exception
	{
		return changeProfileForExecuteDocument(profile.getId(), State.PROCESS_MIGRATION, State.WAIT_MIGRATION);
	}

	/**
	 * снять лок с профиля после исполнения документа сотрудником
	 * @param profile профиль клиента
	 * @return снялся ли лок
	 * @throws Exception
	 */
	public static boolean unlockProfileForExecuteDocument(final Profile profile) throws Exception
	{
		return changeProfileForExecuteDocument(profile.getId(), State.WAIT_MIGRATION, State.PROCESS_MIGRATION);
	}

	/**
	 * изменить состояние связки профиля и резервного блока
	 * @param profileId профиль клиента
	 * @param newState новый статус
	 * @param oldState старый статус
	 * @return изменился ли статус
	 * @throws Exception
	 */
	private static boolean changeProfileForExecuteDocument(final Long profileId, final State newState, final State oldState) throws Exception
	{
		return getHibernateExecutor().execute(new HibernateAction<Boolean>()
		{
			public Boolean run(Session session) throws Exception
			{
				Query query = session.getNamedQuery("com.rssl.auth.csa.back.servises.nodes.ProfileNode.changeTemporaryNodeState");
				query.setParameter("profileId", profileId);
				query.setParameter("newState", newState.name());
				query.setParameter("oldState", oldState);
				return query.executeUpdate() > 0;
			}
		});
	}
}
