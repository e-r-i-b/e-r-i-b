package com.rssl.phizic.business.ant;

import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.push.PushConfirmationResource;
import com.rssl.phizic.business.push.PushInformingResource;
import com.rssl.phizic.business.push.PushResource;
import com.rssl.phizic.business.push.PushResourcesService;
import com.rssl.phizic.utils.test.SafeTaskBase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Задача загружает из файлов push-confirm-config.xml и push-informing-config.xml push-шаблоны
 * в таблицу БД PUSH_MESSAGES.
 * @author basharin
 * @ created 04.10.13
 * @ $Author$
 * @ $Revision$
 */

public class PushResourcesLoaderTask extends SafeTaskBase
{
	private final SimpleService simpleService = new SimpleService();
	private final PushResourcesService pushResourceService = new PushResourcesService();

	private String configDir;

	private String confirmConfig;
	private String informingConfig;
	private boolean deleteIfMissing;
	private boolean updateIfExist;

	public void setConfigDir(String configDir)
	{
		this.configDir = configDir;
	}

	public void setConfirmConfig(String confirmConfig)
	{
		this.confirmConfig = confirmConfig;
	}

	public void setInformingConfig(String informingConfig)
	{
		this.informingConfig = informingConfig;
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

	/**
	 *
	 * Указать, нужно ли обновлять существующие смс-шаблоны. См. также описание класса. По-умолчанию обновляется.
	 *
	 * @param updateIfExist true - обновлять.
	 */
	public void setUpdateIfExist(boolean updateIfExist)
	{
		this.updateIfExist = updateIfExist;
	}

	public void safeExecute() throws Exception
	{
		List<PushResource> confirmationResources = parseDocument(new File(configDir + File.separatorChar + confirmConfig), PushConfirmationResource.class);
		List<PushResource> informResources = parseDocument(new File(configDir + File.separatorChar + informingConfig), PushInformingResource.class);
		List<PushResource> loadedResources = pushResourceService.getPushResources();
		List<PushResource> updated = new ArrayList<PushResource>();
		System.out.println("loaderResources " + loadedResources.size());
		int i = 0;
		for (PushResource template : loadedResources)
		{
			PushResource found = findPushResources(confirmationResources, template);
			found = (found == null) ? findPushResources(informResources, template) : found;

			if (found != null)
			{
				template.setDescription(found.getDescription());
				template.setText(found.getText());
				template.setVariables(found.getVariables());

				template.setShortMessage(found.getShortMessage());
				template.setFullMessage(found.getFullMessage());
				template.setPriority(found.getPriority());
				template.setTypeCode(found.getTypeCode());
				template.setPrivacyType(found.getPrivacyType());
				template.setPublicityType(found.getPublicityType());
				template.setSmsBackup(found.isSmsBackup());
				template.setAttributes(found.getAttributes());

				template.setEmployeeLoginId(null);
				template.setPreviousText(null);
				template.setLastModified(null);
				updated.add(template);
				i++;
			}
		}
		System.out.println(i);
		System.out.println("loaderResources " + loadedResources.size());
		System.out.println("updated " + updated.size());

		if (deleteIfMissing)
		{
			simpleService.removeList(excludeFromListOfRemove(updated, loadedResources));
		}

		if (updateIfExist)
		{
			simpleService.addOrUpdateList(updated);
		}

		simpleService.addOrUpdateList(informResources);
		simpleService.addOrUpdateList(confirmationResources);
	}

	private List<PushResource> excludeFromListOfRemove(List<PushResource> updated, List<PushResource> loadedTemplates)
	{
		List<PushResource> intersection = new ArrayList<PushResource>();

		for (PushResource template : updated)
		{
			for (PushResource loaded : loadedTemplates)
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

	private PushResource findPushResources(List<PushResource> templates, PushResource template)
	{
		PushResource found = null;

		for (PushResource pushResource : templates)
		{
			if (template.getKey().equals(pushResource.getKey()) && template.getClass().isInstance(pushResource))
			{
				found = pushResource;
				break;
			}
		}

		if (found != null)
		{
			templates.remove(found);
		}

		return found;
	}

	private List<PushResource> parseDocument(File pushTemplateConfig, Class<? extends PushResource> pushResourcesClass)
	{
		SAXParserFactory factory = SAXParserFactory.newInstance();

		try
		{
			SAXParser parser = factory.newSAXParser();
			PushResourcesXMLHandler handler = new PushResourcesXMLHandler(pushResourcesClass);

			parser.parse(pushTemplateConfig, handler);

			return handler.getResourcesList();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}