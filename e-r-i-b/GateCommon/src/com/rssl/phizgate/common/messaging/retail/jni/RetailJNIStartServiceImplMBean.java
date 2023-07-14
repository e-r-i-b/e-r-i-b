package com.rssl.phizgate.common.messaging.retail.jni;

/**
 * @author Omeliyanchuk
 * @ created 25.11.2009
 * @ $Author$
 * @ $Revision$
 */

/**
 * описание параметров смотреть в RetailJniFactory и GenericKeyedObjectPool
 */
public interface RetailJNIStartServiceImplMBean
{
	public void setMaxActive(String maxActive);
	public void setMaxIdle(String maxIdle);
	public void setMaxTotal(String maxTotal);
	public void setMaxWait(String maxWait);
	public void setMinEvictableIdleTimeMillis(String minEvictableIdleTimeMillis);
	public void setMinPoolSize(String minPoolSize);
	public void setTimeBetweenEvictionRunsMillis(String timeBetweenEvictionRunsMillis);
	public String getMaxActive();
	public String getMaxIdle();
	public String getMaxTotal();
	public String getMaxWait();
	public String getMinEvictableIdleTimeMillis();
	public String getMinPoolSize();
	public String getTimeBetweenEvictionRunsMillis();
	public boolean getDebug();
	public void setDebug(boolean debug);
	public boolean getRemote();
	public void setRemote(boolean remote);
	public Boolean getStarted();
	public void setStarted(Boolean started);
}
