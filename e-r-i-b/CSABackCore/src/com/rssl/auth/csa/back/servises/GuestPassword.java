package com.rssl.auth.csa.back.servises;

import com.rssl.auth.csa.back.servises.connectors.CSAConnector;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.List;

/**
 * Сущность гостевого пароля
 * @author niculichev
 * @ created 31.01.15
 * @ $Author$
 * @ $Revision$
 */
public class GuestPassword extends PasswordBase
{
	private Long guestId;

	public GuestPassword()
	{}

	public GuestPassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		super(password);
	}

	public static GuestPassword create(String password, Long guestId) throws Exception
	{
		GuestPassword res = new GuestPassword(password);
		res.setGuestId(guestId);
		res.save();
		return res;
	}

	/**
	 * Получить текущий пароль по профилю гостя.
	 * @param guestId идентификатор профиля гостя
	 * @return пароль
	 * @throws Exception
	 */
	public static GuestPassword findActiveByProfileId(final Long guestId) throws Exception
	{
		if (guestId == null)
		{
			throw new IllegalArgumentException("Идентификатор гостя не может быть null");
		}
		return getHibernateExecutor().execute(new HibernateAction<GuestPassword>()
		{
			public GuestPassword run(org.hibernate.Session session) throws Exception
			{
				return (GuestPassword) session.getNamedQuery("com.rssl.auth.csa.back.servises.GuestPassword.getByGuestId")
						.setParameter("guestId", guestId)
						.uniqueResult();
			}
		});
	}

	public static List<GuestPassword> getHistory(final Long guestId, final Calendar fromDate, final Calendar toDate) throws Exception
	{
		if (guestId == null)
		{
			throw new IllegalArgumentException("Идентификатор гостя не может быть null");
		}
		if (fromDate == null)
		{
			throw new IllegalArgumentException("Дата начала периода не может быть null");
		}
		if (toDate == null)
		{
			throw new IllegalArgumentException("Дата окончания периода не может быть null");
		}
		return getHibernateExecutor().execute(new HibernateAction<List<GuestPassword>>()
		{
			public List<GuestPassword> run(org.hibernate.Session session) throws Exception
			{
				return (List<GuestPassword>) session.getNamedQuery("com.rssl.auth.csa.back.servises.GuestPassword.getHistoryByConnector")
						.setParameter("guestId", guestId)
						.setParameter("fromDate", fromDate)
						.setParameter("toDate", toDate)
						.list();
			}
		});
	}

	public Long getGuestId()
	{
		return guestId;
	}

	public void setGuestId(Long guestId)
	{
		this.guestId = guestId;
	}
}
