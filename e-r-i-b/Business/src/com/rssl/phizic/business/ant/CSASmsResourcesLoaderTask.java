package com.rssl.phizic.business.ant;

import com.rssl.phizic.business.sms.CSASmsResource;
import com.rssl.phizic.business.messages.MessageResource;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 *
 * Отвечает за загрузку смс ресурсов в таблицу SMS_RESOURCES, схемы предназначенной для работы ЦСА
 *
 * @author  Balovtsev
 * @version 03.04.13 12:56
 */
public class CSASmsResourcesLoaderTask extends CSASafeTaskBase
{
	private String  configDir;

	private String  config;

	private boolean updateIfExist;
	private boolean deleteIfMissing;

	/**
	 *
	 * Указать, нужно ли обновлять существующие смс-шаблоны. По-умолчанию обновляется.
	 *
	 * @param updateIfExist true - обновлять.
	 */
	public void setUpdateIfExist(boolean updateIfExist)
	{
		this.updateIfExist = updateIfExist;
	}

	/**
	 *
	 * Указать, нужно ли удалять из БД не найденные в файлах источниках смс-шаблоны. По-умолчанию не удаляются.
	 *
	 * @param deleteIfMissing true - удалять.
	 */
	public void setDeleteIfMissing(boolean deleteIfMissing)
	{
		this.deleteIfMissing = deleteIfMissing;
	}

	public void setConfigDir(String configDir)
	{
		this.configDir = configDir;
	}

	public void setConfig(String config)
	{
		this.config = config;
	}

	@Override
	public void safeExecute()
	{
		Session     session     = null;
		Transaction transaction = null;

		try
		{
			session = getSession();

			List<CSASmsResource> configLoadedResources = parseDocument(new File(configDir + File.separatorChar + config));
			List<CSASmsResource> dbLoadedResources     = list(session);
			List<CSASmsResource> updated = new ArrayList<CSASmsResource>();

			for (CSASmsResource template : dbLoadedResources)
			{
				MessageResource found = findSmsResources(configLoadedResources, template);

				if (found != null)
				{
					template.setDescription(found.getDescription());
					template.setText(found.getText());
					template.setVariables(found.getVariables());

					template.setEmployeeLoginId(null);
					template.setPreviousText(null);
					template.setLastModified(null);
					updated.add(template);
				}
			}

			transaction = session.getTransaction();
			transaction.begin();

			if (deleteIfMissing)
			{
				removeList(session, excludeFromListOfRemove(updated, dbLoadedResources));
			}

			if (updateIfExist)
			{
				addOrUpdateList(session, updated);
			}

			addOrUpdateList(session, configLoadedResources);
			transaction.commit();
		}
		catch (HibernateException e)
		{
			if (transaction != null && transaction.isActive())
			{
				transaction.rollback();
			}

			throw e;
		}
		finally
		{
			if (session != null)
			{
				try
				{
					session.flush();
				}
				finally
				{
					session.close();
				}
			}
		}
	}

	private List<CSASmsResource> excludeFromListOfRemove(List<CSASmsResource> updated, List<CSASmsResource> loadedTemplates)
	{
		List<CSASmsResource> intersection = new ArrayList<CSASmsResource>();

		for (MessageResource template : updated)
		{
			for (CSASmsResource loaded : loadedTemplates)
			{
				if (loaded.getKey().equals(template.getKey()))
				{
					intersection.add(loaded);
				}
			}
		}

		loadedTemplates.removeAll(intersection);
		return loadedTemplates;
	}

	private CSASmsResource findSmsResources(List<CSASmsResource> templates, CSASmsResource template)
	{
		CSASmsResource found = null;

		for (CSASmsResource smsResources : templates)
		{
			if (template.getKey().equals(smsResources.getKey()))
			{
				found = smsResources;
				break;
			}
		}

		if (found != null)
		{
			templates.remove(found);
		}

		return found;
	}

	private List<CSASmsResource> parseDocument(File smsTemplateConfig)
	{
		SAXParserFactory factory = SAXParserFactory.newInstance();

		try
		{
			SAXParser                 parser  = factory.newSAXParser();
			CSASmsResourcesXMLHandler handler = new CSASmsResourcesXMLHandler();

			parser.parse(smsTemplateConfig, handler);

			return handler.getResources();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	private List<CSASmsResource> list(Session session) throws HibernateException
	{
		return session.getNamedQuery("com.rssl.phizic.operations.sms.CSASmsSettingsListOperation.list.oracle").list();
	}

	private void removeList(Session session, List<CSASmsResource> SMSResourcesBasic) throws HibernateException
	{
		for (CSASmsResource item : SMSResourcesBasic)
		{
			session.delete(item);
		}
	}

	private void addOrUpdateList(Session session, List<CSASmsResource> csaSmsResources) throws HibernateException
	{
		for (CSASmsResource item : csaSmsResources)
		{
			session.saveOrUpdate(item);
		}
	}
}
