package com.rssl.auth.csa.back.servises;

import com.rssl.auth.csa.back.CSAUserInfo;
import com.rssl.auth.csa.back.Utils;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.Calendar;
import java.util.List;

/**
 * @author krenev
 * @ created 08.08.2013
 * @ $Author$
 * @ $Revision$
 * ������� ��������� ����������������� ������ ������������
 * ������� ������ ���������� ���������������� ������������ ������ � ������� �� �������, ���������� ���������� ������
 * � ����� ����������� ���� ������ (���� ��������� ����������������� ������ ������������ � �����).
 * ������� �� �������� ���������� ������ ������������. ���������� ������ ���������� � �������.
 */

public class ProfileHistory extends ActiveRecord
{
	private long id;
	private long profileId;
	private String firstname;
	private String surname;
	private String patrname;
	private Calendar birthdate;
	private String passport;
	private Calendar expireDate;
	private String tb;

	/**
	 * @return �������������� ������ � �������
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * @param id �������������� ������ � �������
	 */
	public void setId(long id)
	{
		this.id = id;
	}

	/**
	 * @return ������������ �������, ����������� ��������� ���������������� ������ ������������
	 */
	public long getProfileId()
	{
		return profileId;
	}

	/**
	 * @param profileId ������������ �������, ����������� ��������� ���������������� ������ ������������
	 */
	public void setProfileId(long profileId)
	{
		this.profileId = profileId;
	}

	/**
	 * @return ��� ������������
	 */
	public String getFirstname()
	{
		return firstname;
	}

	/**
	 * @param firstname ��� ������������
	 */
	public void setFirstname(String firstname)
	{
		this.firstname = firstname;
	}

	/**
	 * @return ������� ������������
	 */
	public String getSurname()
	{
		return surname;
	}

	/**
	 * @param surname ������� ������������
	 */
	public void setSurname(String surname)
	{
		this.surname = surname;
	}

	/**
	 * @return �������� ������������
	 */
	public String getPatrname()
	{
		return patrname;
	}

	/**
	 * @param patrname �������� ������������
	 */
	public void setPatrname(String patrname)
	{
		this.patrname = patrname;
	}

	/**
	 * @return  �� ������������
	 */
	public Calendar getBirthdate()
	{
		return birthdate;
	}

	/**
	 * @param birthdate �� ������������
	 */
	public void setBirthdate(Calendar birthdate)
	{
		this.birthdate = birthdate;
	}

	/**
	 * @return ������� WAY ������������
	 */
	public String getPassport()
	{
		return passport;
	}

	/**
	 * @param passport ������� WAY ������������
	 */
	public void setPassport(String passport)
	{
		this.passport = passport;
	}

	/**
	 * @return ������� ������������
	 */
	public String getTb()
	{
		return tb;
	}

	/**
	 * @param tb ������� ������������
	 */
	public void setTb(String tb)
	{
		this.tb = tb;
	}

	/**
	 * �������� ���� ��������� �������� ����������������� ������ ������������
	 * @return ���� ��������� �������� ����������������� ������ ������������
	 */
	public Calendar getExpireDate()
	{
		return expireDate;
	}

	/**
	 * ���������� ���� ��������� �������� ����������������� ������ ������������
	 * @param expireDate ���� ��������� ��������  ����������������� ������ ������������
	 */
	public void setExpireDate(Calendar expireDate)
	{
		this.expireDate = expireDate;
	}


	/**
	 * ������� ��� ������� ������ � �������
	 * @param profile ��������
	 * @return ��������� � ������ ������
	 */
	public static ProfileHistory create(Profile profile) throws Exception
	{
		ProfileHistory historyRecord = createInternal(profile);
		historyRecord.save();
		return  historyRecord;

	}

	/**
	 * �������� ������� ������� ��� ��������� � ����.
	 * @param profile �������.
	 * @return ������� �������.
	 */
	private static ProfileHistory createInternal(Profile profile)
	{
		if (profile == null)
		{
			throw new IllegalArgumentException("������� �� ������ ���� null");
		}

		ProfileHistory historyRecord = new ProfileHistory();
		historyRecord.setProfileId(profile.getId());
		historyRecord.setFirstname(profile.getFirstname());
		historyRecord.setPatrname(profile.getPatrname());
		historyRecord.setSurname(profile.getSurname());
		historyRecord.setBirthdate(profile.getBirthdate());
		historyRecord.setPassport(profile.getPassport());
		historyRecord.setTb(profile.getTb());

		return historyRecord;
	}

	private static ProfileHistory createInternal(CSAUserInfo userInfo, Long profileId)
	{
		if (userInfo == null)
		{
			throw new IllegalArgumentException("���������� � ������������ �� ����� ���� null");
		}
		if (profileId == null)
		{
			throw new IllegalArgumentException("������������� ������� �� ����� ���� null");
		}
		ProfileHistory historyRecord = new ProfileHistory();
		historyRecord.setProfileId(profileId);
		historyRecord.setFirstname(userInfo.getFirstname());
		historyRecord.setSurname(userInfo.getSurname());
		historyRecord.setPatrname(userInfo.getPatrname());
		historyRecord.setBirthdate(userInfo.getBirthdate());
		historyRecord.setPassport(userInfo.getPassport());
		historyRecord.setTb(Utils.getTBByCbCode(userInfo.getCbCode()));

		return historyRecord;
	}

	/**
	 * ������������� ������� ��������� �������� �� ������� oldProfile � ������� actualProfile.
	 * @param oldProfile - �������, �� �������� ������������ �������
	 * @param actualProfile - �������, � �������� ����������������� �������
	 * @return ���������� ��������������� �������. 0 - ���� �� ���� ������ �� ������������
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
				return session.getNamedQuery("com.rssl.auth.csa.back.servises.ProfileHistory.changeProfile")
						.setParameter("old_profile", oldProfile.getId())
						.setParameter("new_profile", actualProfile.getId())
						.executeUpdate();
			}
		});
	}

	/**
	 * ���������� ������ ������� ��������� ������� (� ������� ����������).
	 * @param userInfo ������� �������.
	 * @return ������� ���������.
	 * @throws Exception
	 */
	public static List<ProfileHistory> getFullHistoryFor(final CSAUserInfo userInfo) throws Exception
	{
		if (userInfo == null)
		{
			throw new IllegalArgumentException("UserInfo �� ����� ���� null");
		}
		return getHibernateExecutor().execute(new HibernateAction<List<ProfileHistory>>()
		{
			public List<ProfileHistory> run(Session session) throws Exception
			{
				List<ProfileHistory> history = getHistoryFor(userInfo);

				if (CollectionUtils.isEmpty(history))
				{
					Profile profile = Profile.getByUserInfo(userInfo, true);

					if (profile != null)
					{
						history.add(ProfileHistory.createInternal(userInfo, profile.getId()));
					}
				}
				else
				{
					history.add(ProfileHistory.createInternal(userInfo, history.get(0).getProfileId()));
				}

				return history;
			}
		});
	}

	/**
	 * �������� ������ ������� ��������� ������� ��� �������� ���������
	 * @param userInfo ���������� � ������������
	 * @return ������� ���������
	 * @throws Exception
	 */
	public static List<ProfileHistory> getHistoryFor(final CSAUserInfo userInfo) throws Exception
	{
		if (userInfo == null)
		{
			throw new IllegalArgumentException("���������� � ������������ �� ����� ���� null");
		}
		return getHibernateExecutor().execute(new HibernateAction<List<ProfileHistory>>()
		{
			public List<ProfileHistory> run(Session session) throws Exception
			{
				return (List<ProfileHistory>) session.getNamedQuery("com.rssl.auth.csa.back.servises.ProfileHistory.getHistoryByUserInfo")
						.setParameter("firstname", userInfo.getFirstname())
						.setParameter("patrname", userInfo.getPatrname())
						.setParameter("surname", userInfo.getSurname())
						.setParameter("passport", userInfo.getPassport())
						.setParameter("birthdate", userInfo.getBirthdate())
						.setParameter("tb", Utils.getTBByCbCode(userInfo.getCbCode()))
						.list();
			}
		});
	}

	/**
	 * ����� ������� ��� �������
	 * @param profile - ������� ��� �������� ���������� ����� �������
	 * @return �������
	 * @throws Exception
	 */
	public static List<ProfileHistory> findHistoryForProfile(final Profile profile) throws Exception
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(ProfileHistory.class);
		criteria.add(Expression.eq("profileId",profile.getId()));
		return find(criteria, LockMode.NONE);
	}

	/**
	 * ����� ������ � ������� �� ������� �������.
	 * @param profile - ������ �������
	 * @return ������ �� �������. ���� �� ��������� ������� ����� ������� ������, ��� ���� �������, �� ���������� 1-� ����������.
	 * @throws Exception
	 */
	public static ProfileHistory findHistoryByProfileTemplate(final Profile profile) throws Exception
	{
		return getHibernateExecutor().execute(new HibernateAction<ProfileHistory>()
				{
					public ProfileHistory run(org.hibernate.Session session) throws Exception
					{
						List<ProfileHistory> profileHistoryList = (List<ProfileHistory>)session.getNamedQuery("com.rssl.auth.csa.back.servises.ProfileHistory.find")
								.setParameter("firstname", profile.getFirstname())
								.setParameter("patrname", profile.getPatrname())
								.setParameter("surname", profile.getSurname())
								.setParameter("passport", profile.getPassport())
								.setParameter("birthdate", profile.getBirthdate())
								.setParameter("tb", profile.getTb())
								.list();
						return profileHistoryList.size() > 0 ? profileHistoryList.get(0) : null;
					}
				});
	}
}
