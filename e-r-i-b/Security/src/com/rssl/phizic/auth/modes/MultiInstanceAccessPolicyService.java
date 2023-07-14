package com.rssl.phizic.auth.modes;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;

import java.util.Properties;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;

import org.hibernate.Session;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.ReplicationMode;

/**
 * @author Omeliyanchuk
 * @ created 10.07.2008
 * @ $Author$
 * @ $Revision$
 */

public class MultiInstanceAccessPolicyService
{
	/**
	 * ��������� ��� �������
	 *
	 * @param login ����� ��� �������� ���� ��������� �����
	 * @param accessType ����������� ��� �������
	 * @param properties ��������� �������
	 */
	public void enableAccess(final CommonLogin login, final AccessType accessType,
	                         final Properties properties,final  String instanceName) throws SecurityDbException
	{
		try
		{
		    HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
		    {
		        public Void run(Session session) throws Exception
		        {

			        session.lock(login, LockMode.NONE);
			        AccessPolicySettings aps = getLoginSettings(login, accessType,instanceName);
			        if(aps == null)
			        {
				        aps = new AccessPolicySettings();
				        aps.setLoginId(login.getId());
				        aps.setAccessType(accessType.getKey());
			        }

			        writeProperties(aps, properties);

			        session.saveOrUpdate(aps);

			        return null;
		        }
		    });
		}
		catch(SecurityDbException e)
		{
			throw e;
		}
		catch (Exception e)
		{
		   throw new SecurityDbException(e);
		}

	}

	/**
	 * ��������� ��� �������
	 *
	 * @param login      ����� ��� �������� ���� ��������� �����
	 * @param accessType ��� �������
	 */
	public void disableAccess(final CommonLogin login, final AccessType accessType,final String instanceName) throws SecurityDbException
	{
		try
		{
			HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					AccessPolicySettings aps = getLoginSettings(login, accessType,instanceName);
					if (aps != null)
					{
						session.delete(aps);
					}

					return null;
				}
			});
		}
		catch (SecurityDbException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new SecurityDbException(e);
		}
	}

	/**
	 * ����������� ������� �������
	 * @param login
	 * @param type
	 * @param to
	 * @param from
	 * @throws SecurityDbException
	 */
	public void replicateAccessPolicySetting(final CommonLogin login, final AccessType type, final String from, final String to) throws SecurityDbException
	{
		final AccessPolicySettings fromAps =  getLoginSettings(login, type, from);

		try
		{
			HibernateExecutor.getInstance(to).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					AccessPolicySettings toAps =  getLoginSettings(login, type, to);
					if(toAps!=null)
					{
						if(fromAps==null)
							session.delete(toAps);
						else
						{
							if(!toAps.getId().equals(fromAps.getId()))
								session.delete(toAps);
						}
					}
					
					if(fromAps!=null)
					{
						session.replicate(fromAps, ReplicationMode.OVERWRITE);
					}
					return null;
				}
			});
		}
		catch (SecurityDbException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new SecurityDbException(e);
		}
	}

	/**
	 * �������� ��������� ������� ������������ �� ���� �������
	 *
	 * @param login �����
	 * @param accessType ��� �������
	 * @return ���������, null ���� ��� ������� ��������
	 */
	public Properties getProperties(CommonLogin login, AccessType accessType, String instanceName) throws SecurityDbException
	{
		AccessPolicySettings aps = getLoginSettings(login, accessType, instanceName);

		if(aps == null)
			return null;

		return readProperties(aps);
	}

	/**
	 * �������� ��������� ��������� �� ���� �������
	 *
	 * @param accessType ��� �������
	 * @return ���������, null ���� ��� ������� ��������
	 */
	public Properties getTemplateProperties(AccessType accessType, String instanceName) throws SecurityDbException
	{
		AccessPolicySettings aps = getTemplateSettings(accessType, instanceName);

		if (aps == null)
			return null;

		return readProperties(aps);
	}

	/**
	 * ���������� ��������e ��������� �� ���� �������
	 *
	 * @param accessType ��� �������
	 * @param properties ��������
	 */
	public void enableTemplateAccess(final AccessType accessType, final Properties properties,final String instanceName) throws SecurityDbException
	{
		try
		{
			HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{

					AccessPolicySettings aps = getTemplateSettings(accessType,instanceName);
					if (aps == null)
					{
						aps = new AccessPolicySettings();
						aps.setLoginId(null);
						aps.setAccessType(accessType.getKey());
					}

					writeProperties(aps, properties);

					session.saveOrUpdate(aps);

					return null;
				}
			});
		}
		catch (SecurityDbException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new SecurityDbException(e);
		}
	}

	/**
	 * ��������� ��� ������� ��� �������
	 *
	 * @param accessType ��� �������
	 */
	public void disableTemplateAccess(final AccessType accessType,final String instanceName) throws SecurityDbException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					AccessPolicySettings aps = getTemplateSettings(accessType, instanceName);

					if (aps != null)
					{
						session.delete(aps);
					}

					return null;
				}
			});
		}
		catch (SecurityDbException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new SecurityDbException(e);
		}
	}

	private void writeProperties(AccessPolicySettings aps, Properties properties) throws IOException
	{
		if(properties.isEmpty())
		{
			aps.setProperties(null);
		}
		else
		{
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			properties.storeToXML(os, null);
			aps.setProperties(os.toByteArray());
		}
	}

	private Properties readProperties(AccessPolicySettings aps) throws SecurityDbException
	{
		byte[] bytes = aps.getProperties();
		Properties properties = new Properties();
		try
		{
			if(bytes != null)
				properties.loadFromXML(new ByteArrayInputStream(bytes));
		}
		catch (IOException e)
		{
			throw new SecurityDbException(e);
		}
		return properties;
	}

	/**
	 * �������� �� ������� ����� ��������������
	 * @param login �����
	 * @param accessType ��� �������
	 * @return true == ��������
	 * @throws SecurityDbException
	 */
	public boolean isAccessTypeAllowed(CommonLogin login, AccessType accessType, String instanceName) throws SecurityDbException
	{
		AccessPolicySettings aps = getLoginSettings(login, accessType, instanceName);
		return aps != null;
	}

	/**
	 * ������� ��� ��������� ��������� � �������.
	 * @param login �����
	 * @param instanceName ��������� ��
	 * @throws SecurityDbException
	 */
	public void removeAllLoginSettings(final CommonLogin login,final String instanceName) throws SecurityDbException
	{
		try
		{
		    HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
		    {
		        public Void run(Session session) throws Exception
		        {
			        if(login instanceof Login)
			        {
			            AccessPolicySettings aps = getLoginSettings(login, AccessType.simple , instanceName);
				        if(aps!=null)
				            session.delete(aps);
				        
						aps = getLoginSettings(login, AccessType.secure , instanceName);
				        if(aps!=null)
				            session.delete(aps);
			        }
			        else
			        {
				        AccessPolicySettings aps = getLoginSettings(login, AccessType.employee , instanceName);
				        if(aps!=null)
				            session.delete(aps);
			        }

			        return null;
		        }
		    });
		}
		catch (Exception e)
		{
		   throw new SecurityDbException(e);
		}
	}

	private AccessPolicySettings getLoginSettings(final CommonLogin login, final AccessType accessType, String instanceName) throws SecurityDbException
	{
		try
		{
		    return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<AccessPolicySettings>()
		    {
		        public AccessPolicySettings run(Session session) throws Exception
		        {
			        Object obj = session.get(login.getClass(), login.getId());
			        CommonLogin localLogin = obj==null?login:(CommonLogin)obj;
			        session.lock(localLogin, LockMode.NONE);
			        Query query = session.getNamedQuery("com.rssl.phizic.auth.modes.findUserSettings");

			        return (AccessPolicySettings) query
					        .setParameter("loginId", localLogin.getId())
					        .setParameter("accessType", accessType.getKey())
					        .uniqueResult();
		        }
		    });
		}
		catch (Exception e)
		{
		   throw new SecurityDbException(e);
		}
	}

	private AccessPolicySettings getTemplateSettings(final AccessType accessType, String instanceName) throws SecurityDbException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<AccessPolicySettings>()
			{
				public AccessPolicySettings run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.auth.modes.findTemplateSettings");

					return (AccessPolicySettings) query
							.setParameter("accessType", accessType.getKey())
							.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new SecurityDbException(e);
		}
	}
}
