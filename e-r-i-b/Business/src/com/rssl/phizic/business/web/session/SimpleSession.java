package com.rssl.phizic.business.web.session;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.security.config.Constants;
import com.rssl.phizic.security.config.SecurityConfig;

import java.util.Calendar;
import java.util.Hashtable;
import java.util.Map;

/**
 * @author hudyakov
 * @ created 14.11.2008
 * @ $Author$
 * @ $Revision$
 */
public class SimpleSession
{
	private Map<String, Object> attribute = new Hashtable<String, Object>();
	private long lastTimeUsed;
	private String idSession;

	public SimpleSession(String idSession)
	{
		this.idSession = idSession;
		this.lastTimeUsed = Calendar.getInstance().getTimeInMillis();
	}

	public Object getAttribute(String key)
	{
		return attribute.get(key);
	}

	public void setAttribute(String key, Object obj)
	{
		attribute.put(key, obj);
	}

	public boolean isExpired()
	{
		int lifeTime   = ConfigFactory.getConfig(SecurityConfig.class).getSmsBankingSessionLifetime();

		long interval = Calendar.getInstance().getTimeInMillis() - this.getLastTimeUsed();
		if (interval > lifeTime)
			return true;
		return false;
	}

	public long getLastTimeUsed()
	{
		return lastTimeUsed;
	}

	public void setLastTimeUsed(long lastTimeUsed)
	{
		this.lastTimeUsed = lastTimeUsed;
	}

	public String getId()
	{
		return idSession;
	}

	public void invalidate()
	{
		attribute.clear();
		this.lastTimeUsed = Calendar.getInstance().getTimeInMillis();
	}

	public void remove(String key)
	{
		attribute.remove(key);
		this.lastTimeUsed = Calendar.getInstance().getTimeInMillis();
	}
}
