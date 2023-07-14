package com.rssl.auth.csa.back.servises;

import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import org.hibernate.*;
import org.hibernate.Session;

import java.util.Calendar;
import java.util.List;

/**
 * @author krenev
 * @ created 13.08.2013
 * @ $Author$
 * @ $Revision$
 * ������� � ���������� ������������
 */

public class ProfileLock extends ActiveRecord
{

	private Long id;
	private Long profileId;
	private Calendar from;
	private Calendar to;
	private String reason;
	private String lockerFIO;
	private Calendar creationDate;

	/**
	 * @return ������������� ������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id ������������� ������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @param profileId ������������� �������
	 */
	public void setProfileId(Long profileId)
	{
		this.profileId = profileId;
	}

	/**
	 * @return ������������� �������
	 */
	public Long getProfileId()
	{
		return profileId;
	}

	/**
	 * @param from ���� ������ �������� ����������
	 */
	public void setFrom(Calendar from)
	{
		this.from = from;
	}

	/**
	 * @return ���� ������ �������� ����������
	 */
	public Calendar getFrom()
	{
		return from;
	}

	/**
	 * @param to ���� ��������� �������� ����������(null ��� ����������)
	 */
	public void setTo(Calendar to)
	{
		this.to = to;
	}

	/**
	 * @return ���� ��������� �������� ����������(null ��� ����������)
	 */
	public Calendar getTo()
	{
		return to;
	}

	/**
	 * @param reason ������� ����������
	 */
	public void setReason(String reason)
	{
		this.reason = reason;
	}

	/**
	 * @return ������� ����������
	 */
	public String getReason()
	{
		return reason;
	}

	/**
	 * @param blockerFIO ��� ����������, ������������� ������������
	 */
	public void setLockerFIO(String blockerFIO)
	{
		this.lockerFIO = blockerFIO;
	}

	/**
	 * @return ��� ����������, ������������� ������������
	 */
	public String getLockerFIO()
	{
		return lockerFIO;
	}

	/**
	 * @return ���� �������� ����������
	 */
	Calendar getCreationDate()
	{
		return creationDate;
	}

	/**
	 * @param creationDate ���� �������� ����������
	 */
	void setCreationDate(Calendar creationDate)
	{
		this.creationDate = creationDate;
	}

	/**
	 * ������� ���������� ��� �������
	 * @param profile �������, ��� �������� ��������� �������� ����������
	 * @param from ��������� ������ ����������
	 * @param to ��������� ��������� ����������(���������� ��� ����������)
	 * @param reason ������� ����������
	 * @param lockerFIO ��� ����������, ������������� ������������
	 * @return ������ � ����������
	 * @throws Exception
	 */
	public static ProfileLock create(final Profile profile, final Calendar from, final Calendar to, final String reason, final String lockerFIO) throws Exception
	{
		if (profile == null)
		{
			throw new IllegalArgumentException("������� �� ����� ���� null");
		}
		return executeAtomic(new HibernateAction<ProfileLock>()
		{
			public ProfileLock run(Session session) throws Exception
			{
				session.lock(profile, LockMode.UPGRADE_NOWAIT);
				ProfileLock block = new ProfileLock();
				block.setProfileId(profile.getId());
				block.setFrom(from);
				block.setTo(to);
				block.setReason(reason);
				block.setLockerFIO(lockerFIO);

				block.save();
				return block;
			}
		});
	}

	/**
	 /**
	 * ����� ��� ���������� � �������.
	 * ��� ������� ���������� ���������� ����������� ���� ��������� ������� ���������� ������� �����������.
	 *
	 * @param profile ������� ��� �������� ��������� ���� ����������
	 * @return ���������� ����������� �������
	 * @throws Exception
	 */
	public static Integer unblock(final Profile profile) throws Exception
	{
		if (profile == null)
		{
			throw new IllegalArgumentException("������� �� ����� ���� null");
		}
		return getHibernateExecutor().execute(new HibernateAction<Integer>()
		{
			public Integer run(Session session) throws Exception
			{
				return session.getNamedQuery("com.rssl.auth.csa.back.servises.ProfileLock.unblock")
						.setParameter("profile_id", profile.getId())
						.executeUpdate();
			}
		});
	}

	/**
	 * �������� ������ �������� �� ������� ������ ���������� ������� ������������
	 *
	 * @param profile ������� ������������
	 * @return ������ �������� ��������� ��� ������
	 */
	public static List<ProfileLock> findActiveByProfile(final Profile profile) throws Exception
	{
		if (profile == null)
		{
			throw new IllegalArgumentException("������� �� ����� ���� null");
		}
		return getHibernateExecutor().execute(new HibernateAction<List<ProfileLock>>()
		{
			public List<ProfileLock> run(Session session) throws Exception
			{
				return session.getNamedQuery("com.rssl.auth.csa.back.servises.ProfileLock.findActiveByProfile")
						.setParameter("profile_id", profile.getId())
						.list();
			}
		});
	}
	/**
	 * ������������� ���������� �� ������� oldProfile � ������� actualProfile.
	 * @param oldProfile - �������, �� �������� ������������ ����������
	 * @param actualProfile - �������, � �������� ����������������� ����������
	 * @return ���������� ��������������� ����������. 0 - ���� �� ���� ���������� �� ������������
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
				return session.getNamedQuery("com.rssl.auth.csa.back.servises.ProfileLock.changeProfile")
						.setParameter("old_profile", oldProfile.getId())
						.setParameter("new_profile", actualProfile.getId())
						.executeUpdate();
			}
		});
	}

}
