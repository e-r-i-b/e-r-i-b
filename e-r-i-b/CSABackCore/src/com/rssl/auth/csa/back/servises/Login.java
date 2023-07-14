package com.rssl.auth.csa.back.servises;

import com.rssl.auth.csa.back.servises.restrictions.security.LoginSecurityRestriction;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.query.ExecutorQuery;
import org.hibernate.*;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.sql.SQLException;

/**
 * Сущность логина для аутентификации
 * @author niculichev
 * @ created 31.01.15
 * @ $Author$
 * @ $Revision$
 */
public class Login extends ActiveRecord
{
	private String value;
	private Long connectorId;
	private Long guestId;

	/**
	 * ctor
	 */
	public Login()
	{}

	public Login(String value, Long connectorId, Long guestId)
	{
		this.value = value;
		this.connectorId = connectorId;
		this.guestId = guestId;
	}

	/**
	 * Создать сущность логина для гостевого профиля
	 * @param login логин
	 * @param guestId идентификатор гостевого профиля
	 * @return сохраненная сущность логина
	 * @throws Exception
	 */
	public static Login createGuestLogin(String login, Long guestId) throws Exception
	{
		LoginSecurityRestriction.getInstance().check(login);
		Login res = new Login(login, null, guestId);
		res.save();
		return res;
	}

	/**
	 * Создать сущность логина для полноценного клиента
	 * @param login логин
	 * @param connectorId идентификатор коннектора
	 * @return сохраненная сущность логина
	 * @throws Exception
	 */
	public static Login createClientLogin(String login, Long connectorId) throws Exception
	{
		LoginSecurityRestriction.getInstance().check(login);
		Login res = new Login(login, connectorId, null);
		res.save();
		return res;
	}

	/**
	 * Создать сущность логина для однорзового входа клиента
	 * @param login логин
	 * @param connectorId идентификатор коннектора
	 * @return сохраненная сущность логина
	 * @throws Exception
	 */
	public static Login createDisposableLogin(String login, Long connectorId) throws Exception
	{
		Login res = new Login(login, connectorId, null);
		res.save();
		return res;
	}

	/**
	 * Найти сущность логин по его значения
	 * @param login значение логина
	 * @return сущность логина
	 * @throws Exception
	 */
	public static Login findBylogin(final String login) throws Exception
	{
		return getHibernateExecutor().execute(new HibernateAction<Login>()
		{
			public Login run(Session session) throws Exception
			{
				Query query = session.getNamedQuery("com.rssl.auth.csa.back.servises.Login.findByLogin");
				query.setParameter("login", login);

				Object[] values = (Object[]) query.uniqueResult();
				if(values == null)
					return null;

				return new Login((String) values[0], (Long) values[1], (Long) values[2]);
			}
		});
	}

	/**
	 * Обновить логин новым значением
	 * @param newLogin - новое значение логина
	 * @param login - значение логина которе нужно обновить новым
	 */
	public static void updateLogin(String newLogin, String login) throws Exception
	{
		try
		{
			ExecutorQuery query = new ExecutorQuery(getHibernateExecutor(), "com.rssl.auth.csa.back.servises.Login.updateLogin");
			query.setParameter("newLogin", newLogin);
			query.setParameter("login", login);
			query.executeUpdate();
		}
		catch (DataAccessException e)
		{
			if(e.getCause() instanceof HibernateException)
				throw (HibernateException) e.getCause();

			throw e;
		}
	}

	public void save() throws Exception
	{
		try
		{
			ExecutorQuery query = new ExecutorQuery(getHibernateExecutor(), "com.rssl.auth.csa.back.servises.Login.createLogin");
			query.setParameter("login", getValue());
			query.setParameter("connectorId", getConnectorId());
			query.setParameter("guestId", getGuestId());
			query.executeUpdate();
		}
		catch (DataAccessException e)
		{
			if(e.getCause() instanceof HibernateException)
				throw (HibernateException) e.getCause();

			throw e;
		}
	}

	public void update() throws Exception
	{
		try
		{
			ExecutorQuery query = new ExecutorQuery(getHibernateExecutor(), "com.rssl.auth.csa.back.servises.Login.update");
			query.setParameter("login", getValue());
			query.setParameter("connectorId", getConnectorId());
			query.setParameter("guestId", getGuestId());
			query.executeUpdate();
		}
		catch (DataAccessException e)
		{
			if(e.getCause() instanceof HibernateException)
				throw (HibernateException) e.getCause();

			throw e;
		}
	}

	public void update(String newLogin) throws Exception
	{
		try
		{
			ExecutorQuery query = new ExecutorQuery(getHibernateExecutor(), "com.rssl.auth.csa.back.servises.Login.updateLogin");
			query.setParameter("newLogin", newLogin);
			query.setParameter("login", getValue());
			query.setParameter("connectorId", getConnectorId());
			query.setParameter("guestId", getGuestId());
			query.executeUpdate();
		}
		catch (DataAccessException e)
		{
			if(e.getCause() instanceof HibernateException)
				throw (HibernateException) e.getCause();

			throw e;
		}
	}

	/**
	 * Найти сущность логин по его значения
	 * @param login значение логина
	 * @return сущность логина
	 * @throws Exception
	 */
	public static boolean isExistLogin(final String login) throws Exception
	{
		return getHibernateExecutor().execute(new HibernateAction<Boolean>()
		{
			public Boolean run(Session session) throws Exception
			{
				Query query = session.getNamedQuery("com.rssl.auth.csa.back.servises.Login.isExistLogin");
				query.setParameter("login", login);
				return !query.list().isEmpty();
			}
		});
	}

	public static Login findLoginForConnector(final Long connectorId) throws Exception
	{
		return getHibernateExecutor().execute(new HibernateAction<Login>()
		{
			public Login run(Session session) throws Exception
			{
				Query query = session.getNamedQuery("com.rssl.auth.csa.back.servises.Login.findByConnectorId");
				query.setParameter("connectorId", connectorId);

				Object[] values = (Object[]) query.uniqueResult();
				if(values == null)
					return null;

				return new Login((String) values[0], (Long) values[1], (Long) values[2]);
			}
		});
	}

	public static String getLoginByConnectorId(final Long connectorId) throws Exception
	{
		return getHibernateExecutor().execute(new HibernateAction<String>()
		{
			public String run(Session session) throws Exception
			{
				Query query = session.getNamedQuery("com.rssl.auth.csa.back.servises.Login.getLoginValueByConnectorId");
				query.setParameter("connectorId", connectorId);
				return (String) query.uniqueResult();
			}
		});
	}

	public static String getLoginByGuestId(final Long guestId) throws Exception
	{
		return getHibernateExecutor().execute(new HibernateAction<String>()
		{
			public String run(Session session) throws Exception
			{
				Query query = session.getNamedQuery("com.rssl.auth.csa.back.servises.Login.getLoginValueByGuestId");
				query.setParameter("guestId", guestId);
				return (String) query.uniqueResult();
			}
		});
	}

	/**
	 * Изменить ссылку на коннектор
	 * @param connectorId идентификатор коннектора
	 * @throws Exception
	 */
	public void changeConnector(Long connectorId) throws Exception
	{
		setConnectorId(connectorId);
		update();
	}

	/**
	 * Изменить значение логина
	 * @param newValue новое значение логина
	 * @throws Exception
	 */
	public void changeValue(String newValue) throws Exception
	{
		update(newValue);
		setValue(newValue);
	}

	/**
	 * @return литеральное значение логина
	 */
	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	/**
	 * @return идентификатор коннектора, к которому привязан логин
	 */
	public Long getConnectorId()
	{
		return connectorId;
	}

	public void setConnectorId(Long connectorId)
	{
		this.connectorId = connectorId;
	}

	public Long getGuestId()
	{
		return guestId;
	}

	/**
	 * @param guestId идентификатор гостя, к которому привязан логин
	 */
	public void setGuestId(Long guestId)
	{
		this.guestId = guestId;
	}

	/**
	 * @return тип логина(гостевой, клиентский)
	 */
	public Type getType()
	{
		if(connectorId != null)
			return Type.CLIENT;

		if(guestId != null)
			return Type.GUEST;

		throw new IllegalStateException("Висячий логин: " + value);
	}

	public static enum Type
	{
		/**
		 * Логин полноценного клиента
		 */
		CLIENT,

		/**
		 * Логин гостя
		 */
		GUEST
	}
}
