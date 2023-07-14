package com.rssl.auth.csa.back.servises;

import com.rssl.phizic.dataaccess.hibernate.HibernateAction;

import java.util.Calendar;

/**
 * @author mihaylov
 * @ created 24.04.14
 * @ $Author$
 * @ $Revision$
 *
 * ���������� ������� ������� ��� ������� CHG071536
 * �� ���� �� �� ����������, ��� � ProfileLock, �� �� �� ����� �� ������� � ��������� �����.
 * ������� ���������� ��� 13.03 ��� ��� ������ ���-�� ��������.
 */
public class ProfileLockCHG071536 extends ActiveRecord
{
	private Long id;
	private Long profileId;
	private Calendar from;
	private Calendar to;
	private String reason;
	private String lockerFIO;
	private Calendar creationDate;

	/**
	 * ��������� ����������� ��� hibernate
	 */
	public ProfileLockCHG071536()
	{
	}

	/**
	 * �����������
	 * @param profileId - ������������� �������
	 * @param from - ���� ������ ����������
	 * @param to - ���� ��������� ����������
	 * @param reason - ������� ����������
	 * @param lockerFIO - ��� ����������, ���������������� �������
	 */
	public ProfileLockCHG071536(Long profileId, Calendar from, Calendar to, String reason, String lockerFIO)
	{
		this.profileId = profileId;
		this.from = from;
		this.to = to;
		this.reason = reason;
		this.lockerFIO = lockerFIO;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getProfileId()
	{
		return profileId;
	}

	public void setProfileId(Long profileId)
	{
		this.profileId = profileId;
	}

	public Calendar getFrom()
	{
		return from;
	}

	public void setFrom(Calendar from)
	{
		this.from = from;
	}

	public Calendar getTo()
	{
		return to;
	}

	public void setTo(Calendar to)
	{
		this.to = to;
	}

	public String getReason()
	{
		return reason;
	}

	public void setReason(String reason)
	{
		this.reason = reason;
	}

	public String getLockerFIO()
	{
		return lockerFIO;
	}

	public void setLockerFIO(String lockerFIO)
	{
		this.lockerFIO = lockerFIO;
	}

	public Calendar getCreationDate()
	{
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate)
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
	public static ProfileLockCHG071536 create(final Profile profile, final Calendar from, final Calendar to, final String reason, final String lockerFIO) throws Exception
	{
		if (profile == null)
		{
			throw new IllegalArgumentException("������� �� ����� ���� null");
		}
		return executeAtomic(new HibernateAction<ProfileLockCHG071536>()
		{
			public ProfileLockCHG071536 run(org.hibernate.Session session) throws Exception
			{
				ProfileLockCHG071536 lock = new ProfileLockCHG071536(profile.getId(),from,to,reason,lockerFIO);
				lock.save();
				return lock;
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
	public static Integer unlock(final Profile profile) throws Exception
	{
		if (profile == null)
		{
			throw new IllegalArgumentException("������� �� ����� ���� null");
		}
		return getHibernateExecutor().execute(new HibernateAction<Integer>()
		{
			public Integer run(org.hibernate.Session session) throws Exception
			{
				return session.getNamedQuery("com.rssl.auth.csa.back.servises.ProfileLockCHG071536.unlock")
						.setParameter("profile_id", profile.getId())
						.executeUpdate();
			}
		});
	}
}
