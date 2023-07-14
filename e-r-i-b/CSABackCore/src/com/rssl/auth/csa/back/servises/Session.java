package com.rssl.auth.csa.back.servises;

import com.rssl.auth.csa.back.Config;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.config.ConfigFactory;

import java.util.Calendar;
import java.util.List;

/**
 * @author krenev
 * @ created 25.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class Session extends ActiveRecord
{
	private String guid;
	private Calendar creationDate;
	private Calendar closeDate;
	private Calendar prevSessionDate;
	private String prevSessionGuid;
	private String connectorGuid;
	private SessionState state = SessionState.ACTIVE;

	public Session() {}

	public Session(Connector connector)
	{
		if (connector == null)
		{
			throw new IllegalArgumentException(" оннектор не может быть null");
		}
		this.connectorGuid = connector.getGuid();
		creationDate = Calendar.getInstance();
		prevSessionDate = connector.getCurrentSessionDate();
		prevSessionGuid = connector.getCurrentSessionId();
	}

	public String getGuid()
	{
		return guid;
	}

	public void setGuid(String guid)
	{
		this.guid = guid;
	}

	public Calendar getCreationDate()
	{
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate)
	{
		this.creationDate = creationDate;
	}

	public Calendar getPrevSessionDate()
	{
		return prevSessionDate;
	}

	public void setPrevSessionDate(Calendar prevSessionDate)
	{
		this.prevSessionDate = prevSessionDate;
	}

	public String getPrevSessionGuid()
	{
		return prevSessionGuid;
	}

	public void setPrevSessionGuid(String prevSessionGuid)
	{
		this.prevSessionGuid = prevSessionGuid;
	}

	public String getConnectorGuid()
	{
		return connectorGuid;
	}

	public void setConnectorGuid(String connectorGuid)
	{
		this.connectorGuid = connectorGuid;
	}

	public SessionState getState()
	{
		return state;
	}

	public void setState(SessionState state)
	{
		this.state = state;
	}

	public Calendar getCloseDate()
	{
		return closeDate;
	}

	public void setCloseDate(Calendar closeDate)
	{
		this.closeDate = closeDate;
	}

	public Connector getConnector() throws Exception
	{
		return Connector.findByGUID(connectorGuid);
	}

	public Calendar getExpireDate()
	{
		Calendar expireDate = ((Calendar) creationDate.clone());
		expireDate.add(Calendar.HOUR_OF_DAY, ConfigFactory.getConfig(Config.class).getSessionTimeout());
		return expireDate;
	}

	public boolean isValid()
	{
		return state == SessionState.ACTIVE && getExpireDate().after(Calendar.getInstance());
	}

	/**
	 * «акрыть сессию
	 * @throws Exception
	 */
	public void close() throws Exception
	{
		setCloseDate(Calendar.getInstance());
		setState(SessionState.CLOSED);
		save();
	}

	/**
	 * Ќайти сессию по идентифкатору. ѕоиск происходит за врем€ жизни сессии, начина€ от текущего времени минус lifeTime
	 * @param sid идентфикатор сессии
	 * @return операци€ или null если не найдена.
	 */
	public static Session findBySid(final String sid) throws Exception
	{
		return getHibernateExecutor().execute(new HibernateAction<Session>()
		{
			public Session run(org.hibernate.Session session) throws Exception
			{
				return (Session) session.getNamedQuery("com.rssl.auth.csa.back.servises.Session.getBySID")
						.setParameter("sid", sid)
						.uniqueResult();
			}
		});
	}

	/**
	 * ѕолучить активные сессии по коннектору.
	 * @param connector коннектор
	 * @param lifeTime врем€ жизни сессии/глубина поиска в часах
	 * @return список активных сесссий или пустой список если сесиии не найдены.
	 * @throws Exception
	 */
	public static List<Session> findActiveByConnector(final Connector connector, final int lifeTime) throws Exception
	{
		if (connector == null)
		{
			throw new IllegalArgumentException(" оннектор не может быть null");
		}
		return getHibernateExecutor().execute(new HibernateAction<List<Session>>()
		{
			public List<Session> run(org.hibernate.Session session) throws Exception
			{
				Calendar startDate = (Calendar.getInstance());
				startDate.add(Calendar.HOUR_OF_DAY, -lifeTime);
				return (List<Session>) session.getNamedQuery("com.rssl.auth.csa.back.servises.Session.getActiveByConnector")
						.setParameter("connectorGUID", connector.getGuid())
						.setParameter("start_date", startDate)
						.list();
			}
		});
	}

	/**
	 * —оздать сессию дл€ коннектора
	 * @param connector коннектор
	 * @return созданна€ сесси€
	 * @throws Exception
	 */
	public static Session create(final Connector connector) throws Exception
	{
		if (connector == null)
		{
			throw new IllegalArgumentException(" оннектор не может быть null");
		}
		return getHibernateExecutor().execute(new HibernateAction<Session>()
		{
			public Session run(org.hibernate.Session hibernateSession) throws Exception
			{
				Session session = new Session(connector);
				session.save();
				connector.setCurrentSessionDate(session.getCreationDate());
				connector.setCurrentSessionId(session.getGuid());
				connector.save();
				return session;
			}
		});
	}
}
