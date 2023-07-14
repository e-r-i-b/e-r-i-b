package com.rssl.phizic.web.config.view;

import junit.framework.TestCase;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * @author Evgrafov
 * @ created 06.08.2007
 * @ $Author: Evgrafov $
 * @ $Revision: 4675 $
 */
@SuppressWarnings({"IOResourceOpenedButNotSafelyClosed", "unchecked"})
public class ViewConfigTest extends TestCase
{
	private static final String VIEW_CONFIG_XML = "WebAdmin/web/WEB-INF/config/demo-view-config.xml";

	public void testNullConfig() throws JAXBException, IOException
	{
		ViewConfigImpl viewConfig = new ViewConfigImpl(null);

		TemplateManager templateManager = viewConfig.getListTemplateManager("sdfsdfsdfsdf");
		assertNotNull(templateManager);
		assertTrue(templateManager instanceof DefaultTemplateManager);
	}

	public void testConfig() throws JAXBException, IOException
	{
		InputStream is = null;

		try
		{
			File file = new File(VIEW_CONFIG_XML);
			is = new FileInputStream(file);
			ViewConfigImpl viewConfig = new ViewConfigImpl(is);

			assertNotNull(viewConfig);

			TemplateManager manager1 = viewConfig.getListTemplateManager("test-list");
			assertNotNull(manager1);
			assertTrue(manager1 instanceof AllButListedTemplateManager);

			TemplateManager manager2 = viewConfig.getListTemplateManager("sdkfsdkfhskdfhsldkfhkdsjhfskld");
			assertNotNull(manager2);
			assertTrue(manager2 instanceof DefaultTemplateManager);
		}
		finally
		{
			IOUtils.closeQuietly(is);
		}
	}

	public void testUnmarshal() throws JAXBException, IOException
	{
		Unmarshaller unmarshaller = createUnmarshaler();
		ViewConfigRoot viewConfigRoot = loadFromFile(unmarshaller, VIEW_CONFIG_XML);

		assertNotNull(viewConfigRoot);

		List<ListDefinition> list = viewConfigRoot.getLists().getListDefinitions();
		assertNotNull(list);

		for (ListDefinition definition : list)
		{
			List<ColumnType> columns = definition.getColumn();
			assertNotNull(definition.getId());
			assertNotNull(definition.getDescription());

			for (ColumnType column : columns)
			{
				assertNotNull(column.getId());
				assertNotNull(column.getAction());
			}
		}
	}
	private Unmarshaller createUnmarshaler() throws JAXBException
	{
		ClassLoader  classLoader   = Thread.currentThread().getContextClassLoader();
		JAXBContext context       = JAXBContext.newInstance("com.rssl.phizic.web.config.view", classLoader);
		return context.createUnmarshaller();
	}

	private ViewConfigRoot loadFromFile(Unmarshaller unmarshaller, String pathname) throws FileNotFoundException, JAXBException
	{
		ViewConfigRoot viewConfigRoot;
		InputStream is = null;

		try
		{
			File file = new File(pathname);
			is = new FileInputStream(file);
			viewConfigRoot = (ViewConfigRoot) unmarshaller.unmarshal(is);
		}
		finally
		{
			IOUtils.closeQuietly(is);
		}
		return viewConfigRoot;
	}
}