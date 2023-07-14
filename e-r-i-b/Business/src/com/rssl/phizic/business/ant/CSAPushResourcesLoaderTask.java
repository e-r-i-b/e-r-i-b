package com.rssl.phizic.business.ant;

import com.rssl.phizic.business.messages.MessageResource;
import com.rssl.phizic.business.push.CSAPushResource;
import com.rssl.phizic.business.push.PushResource;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Отвечает за загрузку push ресурсов в таблицу PUSH_PARAMS, схемы предназначенной для работы ЦСА
 * @author basharin
 * @ created 19.09.14
 * @ $Author$
 * @ $Revision$
 */

public class CSAPushResourcesLoaderTask extends CSASafeTaskBase
{
	private String  configDir;

	private String  config;

	private boolean updateIfExist;
	private boolean deleteIfMissing;

	/**
	 *
	 * Указать, нужно ли обновлять существующие push-шаблоны. По-умолчанию обновляется.
	 *
	 * @param updateIfExist true - обновлять.
	 */
	public void setUpdateIfExist(boolean updateIfExist)
	{
		this.updateIfExist = updateIfExist;
	}

	/**
	 *
	 * Указать, нужно ли удалять из БД не найденные в файлах источниках push-шаблоны. По-умолчанию не удаляются.
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
		Session session     = null;
		Transaction transaction = null;

		try
		{
			session = getSession();

			List<PushResource> configLoadedResources = parseDocument(new File(configDir + File.separatorChar + config));
			List<PushResource> dbLoadedResources     = list(session);
			List<PushResource> updated = new ArrayList<PushResource>();

			for (PushResource template : dbLoadedResources)
			{
				MessageResource found = findPushResources(configLoadedResources, template);

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

	private List<PushResource> excludeFromListOfRemove(List<PushResource> updated, List<PushResource> loadedTemplates)
	{
		List<PushResource> intersection = new ArrayList<PushResource>();

		for (MessageResource template : updated)
		{
			for (PushResource loaded : loadedTemplates)
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

	private PushResource findPushResources(List<PushResource> templates, PushResource template)
	{
		PushResource found = null;

		for (PushResource pushResources : templates)
		{
			if (template.getKey().equals(pushResources.getKey()))
			{
				found = pushResources;
				break;
			}
		}

		if (found != null)
		{
			templates.remove(found);
		}

		return found;
	}

	private List<PushResource> parseDocument(File pushTemplateConfig)
	{
		SAXParserFactory factory = SAXParserFactory.newInstance();

		try
		{
			SAXParser parser  = factory.newSAXParser();
			PushResourcesXMLHandler handler = new PushResourcesXMLHandler(CSAPushResource.class);

			parser.parse(pushTemplateConfig, handler);

			return handler.getResourcesList();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	private List<PushResource> list(Session session) throws HibernateException
	{
		return session.getNamedQuery("com.rssl.phizic.operations.push.CSAPushResourceListOperation.list.oracle").list();
	}

	private void removeList(Session session, List<PushResource> pushResourcesBasic) throws HibernateException
	{
		for (PushResource item : pushResourcesBasic)
		{
			session.delete(item);
		}
	}

	private void addOrUpdateList(Session session, List<PushResource> csaPushResources) throws HibernateException
	{
		for (PushResource item : csaPushResources)
		{
			session.saveOrUpdate(item);
		}
	}
}
