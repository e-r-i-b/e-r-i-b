package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.Constants;
import org.hibernate.HibernateException;
import org.hibernate.PropertyNotFoundException;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.property.Getter;
import org.hibernate.property.PropertyAccessor;
import org.hibernate.property.Setter;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author Evgrafov
 * @ created 15.11.2006
 * @ $Author: erkin $
 * @ $Revision: 73843 $
 */

public class FormIdPropertyAccessor implements PropertyAccessor
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final NoopSetter SETTER = new NoopSetter();
	private static final FormIdGetter GETTER = new FormIdGetter();

	public Getter getGetter(Class theClass, String propertyName) throws PropertyNotFoundException
	{
		return GETTER;
	}

	public Setter getSetter(Class theClass, String propertyName) throws PropertyNotFoundException
	{
		return SETTER;
	}

	private static class FormIdGetter implements Getter
	{

		public Object get(Object owner) throws HibernateException
		{
			return null; // обрабатываем только insert
		}

		public Object getForInsert(Object owner, Map mergeMap, SessionImplementor session) throws HibernateException
		{
			Connection connection = session.connection();

			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;

			try
			{
				preparedStatement = connection.prepareStatement("SELECT ID FROM PAYMENTFORMS WHERE NAME=?");
				BusinessDocument payment = (BusinessDocument) owner;
				String formName = payment.getFormName();

				if (formName == null)
					throw new HibernateException("Ќе задана форма платежа");


				preparedStatement.setString(1, formName);

				preparedStatement.execute();

				resultSet = preparedStatement.getResultSet();

				if (!resultSet.next())
					throw new HibernateException("Ќе найдена форма платежа: " + formName);

				return resultSet.getLong(1);

			}
			catch (SQLException e)
			{
				throw new HibernateException(e);
			}
			finally
			{
				try
				{
					if (preparedStatement != null)
						preparedStatement.close();

					if (resultSet != null)
						resultSet.close();
				}
				catch (SQLException e)
				{
					log.error(e.getMessage(), e); // больше тут ничего не сделать
				}
			}

		}

		public Class getReturnType()
		{
			return Long.class;
		}

		public String getMethodName()
		{
			return null;
		}

		public Method getMethod()
		{
			return null;
		}
	}

	private static class NoopSetter implements Setter
	{

		public void set(Object target, Object value, SessionFactoryImplementor factory) throws HibernateException
		{
		}

		public String getMethodName()
		{
			return null;
		}

		public Method getMethod()
		{
			return null;
		}
	}
}