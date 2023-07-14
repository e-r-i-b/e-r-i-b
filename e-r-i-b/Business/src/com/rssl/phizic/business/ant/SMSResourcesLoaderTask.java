package com.rssl.phizic.business.ant;

import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.sms.*;
import com.rssl.phizic.utils.test.SafeTaskBase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 *
 * Задача загружает из файлов sms-refuse-config.xml, sms-password-config.xml и sms-informing-config.xml смс-шаблоны
 * в таблицу БД SMS_RESOURCES. Если соответствующие смс-шаблоны уже есть в БД, обновляются только записи имеющие
 * в столбце CHANNEL значение равное "INTERNET_CLIENT" (все значения которые может принимать данный столбец можно
 * посмотреть в com.rssl.phizic.business.limits.ChannelType).
 *
 * @author  Balovtsev
 * @version 18.03.13 12:13
 */
public class SMSResourcesLoaderTask extends SafeTaskBase
{
	private final SimpleService       simpleService      = new SimpleService();
	private final SMSResourcesService smsResourceService = new SMSResourcesService();

	private String  configDir;

	private String  refuseConfig;
	private String  confirmConfig;
	private String  informingConfig;
	private String  ermbConfig;

	private boolean updateIfExist;
	private boolean deleteIfMissing;

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

	public void setConfirmConfig(String confirmConfig)
	{
		this.confirmConfig = confirmConfig;
	}

	public void setRefuseConfig(String refuseConfig)
	{
		this.refuseConfig = refuseConfig;
	}

	public void setInformingConfig(String informingConfig)
	{
		this.informingConfig = informingConfig;
	}

	public void setErmbConfig(String ermbConfig)
	{
		this.ermbConfig = ermbConfig;
	}

	public void safeExecute() throws Exception
	{
		List<SMSResource> confirmationResources = parseDocument(new File(configDir + File.separatorChar + confirmConfig),   SMSConfirmationResource.class);
		List<SMSResource> refuseResources       = parseDocument(new File(configDir + File.separatorChar + refuseConfig),    SMSRefusingResource.class);
		List<SMSResource> informResources       = parseDocument(new File(configDir + File.separatorChar + informingConfig), SMSInformingResource.class);
		List<SMSResource> ermbResources         = parseErmbDocument(new File(configDir + File.separatorChar + ermbConfig), SMSInformingResource.class);
		informResources.addAll(ermbResources);
		List<SMSResource> loadedResources       = smsResourceService.getSMSResources(null);
		List<SMSResource> updated               = new ArrayList<SMSResource>();
		for (SMSResource template : loadedResources)
		{
			SMSResource found = findSmsResources(confirmationResources, template);
			found = (found == null) ? findSmsResources(refuseResources, template) : found;
			found = (found == null) ? findSmsResources(informResources, template) : found;

			if (found != null)
			{
				template.setDescription(found.getDescription());
				template.setText(found.getText());
				template.setVariables(found.getVariables());
				template.setCustom(found.getCustom());
				template.setPriority(found.getPriority());

				template.setEmployeeLoginId(null);
				template.setPreviousText(null);
				template.setLastModified(null);
				updated.add(template);
			}
		}

		if (deleteIfMissing)
		{
			simpleService.removeList( excludeFromListOfRemove(updated, loadedResources) );
		}

		if (updateIfExist)
		{
			simpleService.addOrUpdateList( updated );
		}

		simpleService.addOrUpdateList( refuseResources );
		simpleService.addOrUpdateList( informResources );
		simpleService.addOrUpdateList( confirmationResources );
	}

	private List<SMSResource> excludeFromListOfRemove(List<SMSResource> updated, List<SMSResource> loadedTemplates)
	{
		List<SMSResource> intersection = new ArrayList<SMSResource>();

		for (SMSResource template : updated)
		{
			for (SMSResource loaded : loadedTemplates)
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

	private SMSResource findSmsResources(List<SMSResource> templates, SMSResource template)
	{
		SMSResource found = null;

		for (SMSResource smsResource : templates)
		{
			if (template.getKey().equals(smsResource.getKey()) && smsResource.getChannel() == template.getChannel() && template.getClass().isInstance(smsResource))
			{
				found = smsResource;
				break;
			}
		}

		if (found != null)
		{
			templates.remove(found);
		}

		return found;
	}

	private List<SMSResource> parseDocument(File smsTemplateConfig, Class<? extends SMSResource> smsResourcesClass)
	{
		SAXParserFactory factory = SAXParserFactory.newInstance();

		try
		{
			SAXParser              parser  = factory.newSAXParser();
			SmsResourcesXMLHandler handler = new SmsResourcesXMLHandler(smsResourcesClass);

			parser.parse(smsTemplateConfig, handler);

			return handler.getResourcesList();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	private List<SMSResource> parseErmbDocument(File smsTemplateConfig, Class<? extends SMSResource> smsResourcesClass)
	{
		SAXParserFactory factory = SAXParserFactory.newInstance();

		try
		{
			SAXParser              parser  = factory.newSAXParser();
			SmsErmbResourcesXMLHandler handler = new SmsErmbResourcesXMLHandler(smsResourcesClass);

			parser.parse(smsTemplateConfig, handler);

			return handler.getResourcesList();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}
