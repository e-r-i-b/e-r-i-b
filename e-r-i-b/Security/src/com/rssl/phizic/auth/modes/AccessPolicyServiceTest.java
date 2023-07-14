package com.rssl.phizic.auth.modes;

import com.rssl.phizic.auth.CheckLoginTest;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.test.SecurityTestBase;
import org.hibernate.Query;
import org.hibernate.Session;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Arrays;

/**
 * @author Evgrafov
 * @ created 18.12.2006
 * @ $Author: bogdanov $
 * @ $Revision: 57189 $
 */

@SuppressWarnings({"JavaDoc"})
public class AccessPolicyServiceTest extends SecurityTestBase
{
	private AuthenticationConfig authConfig;

	protected void setUp() throws Exception
	{
		super.setUp();
		authConfig = ConfigFactory.getConfig(AuthenticationConfig.class, Application.PhizIC);

	}

	public void testBLOB() throws Exception
	{
		final byte[] original = createPropertiesArray();

		testBLOB(original);
		testBLOB(null);
	}

	private void testBLOB(final byte[] data) throws Exception
	{
		final String hql = "select aps from com.rssl.phizic.auth.modes.AccessPolicySettings aps where aps.login is null and aps.accessType = 'Z'";
		HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
		{
			public Void run(Session session) throws Exception
			{

				Query query = session.createQuery(hql);
				AccessPolicySettings aps1 = (AccessPolicySettings) query.uniqueResult();

				if(aps1 != null)
				{
					session.delete(aps1);
					session.flush();
				}

				AccessPolicySettings aps2 = new AccessPolicySettings();
				aps2.setAccessType("Z");
				aps2.setProperties(data);

				session.save(aps2);

				return null;
			}
		});

		byte[] loaded = HibernateExecutor.getInstance().execute(new HibernateAction<byte[]>()
		{
			public byte[] run(Session session) throws Exception
			{

				Query query = session.createQuery(hql);

				AccessPolicySettings aps3 = (AccessPolicySettings) query.uniqueResult();

				session.delete(aps3);

				return aps3.getProperties();
			}
		});

		assertTrue(data == null? loaded == null : Arrays.equals(data, loaded));
	}

	private byte[] createPropertiesArray() throws IOException
	{
		Properties properties = new Properties();
		properties.setProperty("test","тест");

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		properties.storeToXML(stream, null);

		return stream.toByteArray();
	}

	public void testEnableDisable() throws Exception
	{
		Properties properties = new Properties();
		properties.setProperty("test", "проверка");


		AccessPolicy policy = authConfig.getPolicies().get(0);

		AccessPolicyService ams = new AccessPolicyService();
		Login testLogin = CheckLoginTest.getTestLogin();
		AccessType type = policy.getAccessType();


		ams.enableAccess(testLogin, type, properties);

		Properties prop1 = ams.getProperties(testLogin, type);
		assertTrue(ams.isAccessTypeAllowed(testLogin, type));
		assertNotNull(prop1);
		assertEquals("проверка", prop1.getProperty("test"));

		ams.disableAccess(testLogin, type);

		Properties prop2 = ams.getProperties(testLogin, type);
		assertFalse(ams.isAccessTypeAllowed(testLogin, type));
		assertNull(prop2);

	}

	public void testTemplate() throws SecurityDbException
	{
		Properties properties = new Properties();
		properties.setProperty("test", "проверка");

		AccessPolicy policy = authConfig.getPolicies().get(0);
		AccessPolicyService ams = new AccessPolicyService();

		AccessType type = policy.getAccessType();
		ams.enableTemplateAccess(type, properties);

		Properties prop1 = ams.getTemplateProperties(type);
		assertNotNull(prop1);
		assertEquals("проверка", prop1.getProperty("test"));
	}
}