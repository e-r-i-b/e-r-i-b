package com.rssl.phizic.config;

import com.rssl.phizic.utils.test.RSSLTestCaseBase;

import java.util.Properties;

/**
 * @author Roshka
 * @ created 06.02.2006
 * @ $Author: bogdanov $
 * @ $Revision: 57189 $
 */
@SuppressWarnings({"JavaDoc"})
public class DbConfigTest extends RSSLTestCaseBase
{
	private DbPropertyReader notResource;
	private DbPropertyReader iccs;
	private static final String NOT_RESOURCE_CATEGORY = "not-resource-category";

	protected void setUp() throws Exception
	{
		super.setUp();
		notResource = new DbPropertyReader(NOT_RESOURCE_CATEGORY, "");
		iccs = notResource;
	}

	protected void tearDown() throws Exception
	{
		super.tearDown();
		notResource = null;
		iccs = null;
	}

	public void testDbConfig() throws Exception
    {
        //add property
	    /*
        String key = "rssl.test.propery";
        String value = "test";
        notResource.setProperty(key, value);
	    notResource.refresh();

        String propertyValue = notResource.getProperty(key);
	    assertEquals(value, propertyValue);

	    Property property = notResource.findProperty(key, NOT_RESOURCE_CATEGORY);
	    assertNotNull(property);

	    notResource.removeProperty(property);
	    assertNull(notResource.findProperty(key, NOT_RESOURCE_CATEGORY));/* */
    }

	public void testRemove() throws Exception
	{
	}

	public void testGetPropertiesMethod() throws Exception
	{
		Properties properties = iccs.getProperties("com.rssl.iccs.dictionaries");
		assertTrue(properties.size() > 0);
	}
}
