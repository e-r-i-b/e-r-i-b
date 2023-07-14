package com.rssl.phizic.utils.naming;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.naming.*;

/**
 * @author Roshka
 * @ created 30.08.2006
 * @ $Author$
 * @ $Revision$
 */

public final class NamingHelper
{

	private static final Log log = LogFactory.getLog(NamingHelper.class);

	public static InitialContext getInitialContext() throws NamingException
	{
		try
		{
			return new InitialContext();
		}
		catch (NamingException e)
		{
			log.error("Could not obtain initial context", e);
			throw e;
		}
	}

	/**
	 * Bind val to name in ctx, and make sure that all intermediate contexts exist.
	 *
	 * @param ctx  the root context
	 * @param name the name as a string
	 * @param val  the object to be bound
	 * @throws NamingException
	 */
	public static void bind(Context ctx, String name, Object val) throws NamingException
	{
		try
		{
			log.trace("binding: " + name);
			ctx.rebind(name, val);
		}
		catch (Exception e)
		{
			Name n = ctx.getNameParser("").parse(name);
			while (n.size() > 1)
			{
				String ctxName = n.get(0);

				Context subctx = null;
				try
				{
					log.trace("lookup: " + ctxName);
					subctx = (Context) ctx.lookup(ctxName);
				}
				catch (NameNotFoundException nfe)
				{
				}

				if (subctx != null)
				{
					log.debug("Found subcontext: " + ctxName);
					ctx = subctx;
				}
				else
				{
					log.info("Creating subcontext: " + ctxName);
					ctx = ctx.createSubcontext(ctxName);
				}
				n = n.getSuffix(1);
			}
			log.trace("binding: " + n);
			ctx.rebind(n, val);
		}
		log.debug("Bound name: " + name);
	}

	private NamingHelper()
	{
	}

}
