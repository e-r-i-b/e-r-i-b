package com.rssl.phizic.utils.naming;

import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.naming.*;

/**
 * @author Erkin
 * @ created 11.07.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Служба JNDI
 */
public class NamingService
{
	private final Log log = LogFactory.getLog(NamingService.class);

	public <T> void bindObject(String jndiName, T object)
	{
		if (StringHelper.isEmpty(jndiName))
			throw new IllegalArgumentException("Не указано jndiName");

		if (object == null)
			throw new IllegalArgumentException("Не указан object");

		Context context = null;
		try
		{
			context = new InitialContext();
			context.rebind(jndiName, object);
		}
		catch (NamingException e)
		{
			log.warn(e);

			try
			{
				Name n = context.getNameParser("").parse(jndiName);
				while (n.size() > 1)
				{
					String ctxName = n.get(0);

					Context subctx = null;
					try
					{
						subctx = (Context) context.lookup(ctxName);
					}
					catch (NameNotFoundException ignored)
					{
					}

					if (subctx != null)
						context = subctx;
					else
						context = context.createSubcontext(ctxName);
					n = n.getSuffix(1);
				}
				context.rebind(n, object);
			}
			catch (NamingException e1)
			{
				throw new RuntimeException(e1);
			}
		}
		finally
		{
			try
			{
				if (context != null)
					context.close();
			}
			catch (NamingException ignored) {}
		}
	}

	public <T> T lookupObject(String jndiName)
	{
		if (StringHelper.isEmpty(jndiName))
			throw new IllegalArgumentException("Не указано jndiName");

		InitialContext context = null;
		try
		{
			context = new InitialContext();
			// noinspection unchecked
			return (T) context.lookup(jndiName);
		}
		catch (NameNotFoundException ignored)
		{
			return null;
		}
		catch (NamingException e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			try
			{
				if (context != null)
					context.close();
			}
			catch (NamingException ignored) {}
		}
	}

	public void unbindObject(String jndiName)
	{
		if (StringHelper.isEmpty(jndiName))
			throw new IllegalArgumentException("Не указано jndiName");

		InitialContext context = null;
		try
		{
			context = new InitialContext();
			context.unbind(jndiName);
		}
		catch (NameNotFoundException ignored)
		{
		}
		catch (NamingException e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			try
			{
				if (context != null)
					context.close();
			}
			catch (NamingException ignored) {}
		}
	}
}
