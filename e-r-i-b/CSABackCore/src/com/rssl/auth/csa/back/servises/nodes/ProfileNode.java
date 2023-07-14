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
 * ����� ������� � ������
 */

public class ProfileNode extends ActiveRecord
{
	/**
	 * ��������� ����� ������� � ������
	 */
	public static enum State
	{
		WAIT_MIGRATION,           //��������� �������� ������ �� ����� �����
		PROCESS_MIGRATION,        //���� ��������, ���������� ������� ������ �� ����� ����� � ������
		ACTIVE                    //������ ������ � ������, ������� �������� ��������
	}

	private Long id;
	private Calendar creationDate;
	private Profile profile;
	private Node node;
	private State state;
	private ProfileType profileType;

	/**
	 * @return ������������� �����
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id ������������� �����
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ���� �������� �����
	 */
	public Calendar getCreationDate()
	{
		return creationDate;
	}

	/**
	 * @param creationDate ���� �������� �����
	 */
	public void setCreationDate(Calendar creationDate)
	{
		this.creationDate = creationDate;
	}

	/**
	 * @return �������
	 */
	public Profile getProfile()
	{
		return profile;
	}

	/**
	 * @param profile �������
	 */
	public void setProfile(Profile profile)
	{
		this.profile = profile;
	}

	/**
	 * @return ����
	 */
	public Node getNode()
	{
		return node;
	}

	/**
	 * @param node ����
	 */
	public void setNode(Node node)
	{
		this.node = node;
	}

	/**
	 * @return ��������� �����
	 */
	public State getState()
	{
		return state;
	}

	/**
	 * @param state ��������� �����
	 */
	public void setState(State state)
	{
		this.state = state;
	}

	/**
	 * @return ��� ������� � �����
	 */
	public ProfileType getProfileType()
	{
		return profileType;
	}

	/**
	 * @param profileType ��� ������� � �����
	 */
	public void setProfileType(ProfileType profileType)
	{
		this.profileType = profileType;
	}

	/**
	 * ������� ������ ��� ��������� ������� � ������������ ������
	 * @param profile �������
	 * @return ������ ������� � �����
	 */
	public static ProfileNode create(Profile profile) throws Exception
	{
		if (profile == null)
		{
			throw new IllegalArgumentException("������� �� ����� ���� null");
		}
		return create(profile, Node.getFilling(), ProfileType.MAIN);
	}

	/**
	 * ������� ������ ������� � ������ � �������� ����� �������
	 * ������ ��������� � ������� �����
	 * @param profile �������
	 * @param node ����
	 * @param profileType ��� ������� � �����
	 * @return ������ ������� � ������
	 */
	public static ProfileNode create(Profile profile, Node node, ProfileType profileType) throws Exception
	{
		if (profile == null)
		{
			throw new IllegalArgumentException("������� �� ����� ���� null");
		}
		if (node == null)
		{
			throw new IllegalArgumentException("���� �� ����� ���� null");
		}
		if (profileType == null)
		{
			throw new IllegalArgumentException("��� ������� �� ����� ���� null");
		}
		ProfileNode profileNode = new ProfileNode();
		profileNode.setProfile(profile);
		profileNode.setNode(node);
		profileNode.setProfileType(profileType);
		profileNode.setState(ProfileType.MAIN == profileType ? State.ACTIVE : State.WAIT_MIGRATION);
		profileNode.save();
		log.debug("��� ������� " + profile.getId() + " ������� ����� ����� � ������ " + node.getId() + " ���� " + profileType);
		return profileNode;
	}

	/**
	 * �������� ������ ���� ������ ������� � �������
	 * @param profile �������
	 * @return ������ ������ ��� ������ ������
	 */
	public static List<ProfileNode> getByProfile(final Profile profile) throws Exception
	{
		if (profile == null)
		{
			throw new IllegalArgumentException("������� �� ����� ���� null");
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
	 * ������� ��� ����, ����������� � �������
	 * @param profile �������
	 * @throws Exception
	 */
	public static void removeProfileNodes(final Profile profile) throws Exception
	{
		if (profile == null)
		{
			throw new IllegalArgumentException("������� �� ����� ���� null");
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
	 * ������������� ����� ������� � ������� �� ������� oldProfile � ������� actualProfile.
	 * @param oldProfile - �������, �� �������� ������������ �����
	 * @param actualProfile - �������, � �������� ����������������� �����
	 * @return ���������� ��������������� �������. 0 - ���� �� ����� �����  �� ���������������
	 */
	public static Integer changeProfile(final Profile oldProfile, final Profile actualProfile) throws Exception
	{
		if (oldProfile == null)
		{
			throw new IllegalArgumentException("������ ������� �� ����� ���� null");
		}
		if (actualProfile == null)
		{
			throw new IllegalArgumentException("����� ������� �� ����� ���� null");
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
	 * ������� ��������� �������� �������� ��� ����� ������� � �������� ������.
	 * ���� � ������� ������� ����� � ���������� ������� � ����� ������� ��� ��� ��������� � �������� ��������,
	 * ������ ������ ��������� � ������ "����������" �������� �� ���������� ����� � ��������.
	 * @return ��������� �������� ��������
	 * @param profileNode ����� ������� � ������
	 * @throws Exception
	 */
	public static MigrationState getMigrationState(final ProfileNode profileNode) throws Exception
	{
		if (profileNode == null)
		{
			throw new IllegalArgumentException("����� ������� � ������ �� ����� ���� null");
		}
		//��������� �������� �������������� ��� ��������� �����
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
	 * ��������� ��� �� ������� ��� ���������� ��������� �����������
	 * @param profile ������� �������
	 * @return ���������� �� ���
	 * @throws Exception
	 */
	public static boolean lockProfileForExecuteDocument(final Profile profile) throws Exception
	{
		return changeProfileForExecuteDocument(profile.getId(), State.PROCESS_MIGRATION, State.WAIT_MIGRATION);
	}

	/**
	 * ����� ��� � ������� ����� ���������� ��������� �����������
	 * @param profile ������� �������
	 * @return ������ �� ���
	 * @throws Exception
	 */
	public static boolean unlockProfileForExecuteDocument(final Profile profile) throws Exception
	{
		return changeProfileForExecuteDocument(profile.getId(), State.WAIT_MIGRATION, State.PROCESS_MIGRATION);
	}

	/**
	 * �������� ��������� ������ ������� � ���������� �����
	 * @param profileId ������� �������
	 * @param newState ����� ������
	 * @param oldState ������ ������
	 * @return ��������� �� ������
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
