package com.rssl.phizic.business.ant;

import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.email.EmailResource;
import com.rssl.phizic.business.email.EmailResourceService;
import com.rssl.phizic.utils.test.SafeTaskBase;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Отвечает за загрузку email-шаблонов в таблицу EMAIL_RESOURCES
 * @author lukina
 * @ created 16.10.2013
 * @ $Author$
 * @ $Revision$
 */
public class EmailResourcesLoaderTask  extends SafeTaskBase
{
	private final SimpleService simpleService      = new SimpleService();
	private final EmailResourceService emailResourceService = new EmailResourceService();

	private String  configDir;
	private String  config;
	private boolean deleteIfMissing;
	private boolean updateIfExist;

	public void setConfigDir(String configDir)
	{
		this.configDir = configDir;
	}

	public void setConfig(String config)
	{
		this.config = config;
	}

	public void setUpdateIfExist(boolean updateIfExist)
	{
		this.updateIfExist = updateIfExist;
	}

	/**
	 *
	 * Указать, нужно ли удалять из БД не найденные в файлах источниках email-шаблоны. По-умолчанию не удаляются.
	 *
	 * @param deleteIfMissing true - удалять.
	 */
	public void setDeleteIfMissing(boolean deleteIfMissing)
	{
		this.deleteIfMissing = deleteIfMissing;
	}

	@Override
	public void safeExecute() throws Exception
	{
		List<EmailResource> confirmationResources = parseDocument(new File(configDir + File.separatorChar + config));

		List<EmailResource> loadedResources       = emailResourceService.getEmailResources();
		List<EmailResource> updated               = new ArrayList<EmailResource>();
		for (EmailResource resource : loadedResources)
		{
			EmailResource found = findEmailResources(confirmationResources, resource);

			if (found != null)
			{
				resource.setDescription(found.getDescription());
				resource.setHtmlText(found.getHtmlText());
				resource.setPlainText(found.getPlainText());
				resource.setTheme(found.getTheme());
				resource.setVariables(found.getVariables());

				resource.setEmployeeLoginId(null);
				resource.setPreviousHtmlText(null);
				resource.setPreviousPlainText(null);
				resource.setLastModified(null);
				updated.add(resource);
			}
		}

		if (deleteIfMissing)
		{
			simpleService.removeList(excludeFromListOfRemove(updated, loadedResources));
		}

		if (updateIfExist)
		{
			for (EmailResource res : updated)
				simpleService.addOrUpdate(res);
		}

		for (EmailResource res : confirmationResources)
			simpleService.addOrUpdate(res);

	}

	private List<EmailResource> excludeFromListOfRemove(List<EmailResource> updated, List<EmailResource> loadedTemplates)
	{
		List<EmailResource> intersection = new ArrayList<EmailResource>();

		for (EmailResource template : updated)
		{
			for (EmailResource loaded : loadedTemplates)
			{
				if (loaded.getKey().equals(template.getKey()) && loaded.getClass().isInstance(template))
				{
					intersection.add(loaded);
				}
			}
		}

		loadedTemplates.removeAll(intersection);
		return loadedTemplates;
	}

	private  EmailResource findEmailResources(List<EmailResource> resources, EmailResource resource)
	{
		EmailResource found = null;
		for (EmailResource emailResource : resources)
		{
			if (resource.getKey().equals(emailResource.getKey()))
			{
				found = emailResource;
				break;
			}
		}
		if (found != null)
		{
			resources.remove(found);
		}
		return found;
	}

	private List<EmailResource> parseDocument(File smsTemplateConfig)
	{
		SAXParserFactory factory = SAXParserFactory.newInstance();

		try
		{
			SAXParser parser  = factory.newSAXParser();
			EmailResourcesXMLHandler handler = new EmailResourcesXMLHandler();

			parser.parse(smsTemplateConfig, handler);

			return handler.getResourcesList();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}
