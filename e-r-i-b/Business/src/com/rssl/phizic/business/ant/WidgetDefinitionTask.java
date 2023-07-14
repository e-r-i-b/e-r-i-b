package com.rssl.phizic.business.ant;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.rssl.phizic.business.web.WidgetDefinition;
import com.rssl.phizic.business.web.WidgetDefinitionService;
import com.rssl.phizic.business.web.WidgetGsonSingleton;
import com.rssl.phizic.business.web.WidgetUtils;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.utils.test.SafeTaskBase;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * User: moshenko
 * Date: 03.12.2012
 * Time: 21:49:14
 * Загрузка описания (класса) виджета
 */
public class WidgetDefinitionTask extends SafeTaskBase
{
	private static final Log log = LogFactory.getLog(Constants.LOG_MODULE_CORE.toValue());
	private static final WidgetDefinitionService widgetDefinitionService = new WidgetDefinitionService();
	private String resourcePath;

	public void setResourcePath(String resourcePath)
	{
		this.resourcePath = resourcePath;
	}

	public void safeExecute() throws Exception
	{
		log.trace("Инициализация WidgetDefinition");
		List<WidgetDefinition> definitions = new LinkedList<WidgetDefinition>();
		InputStream resourceStream = new FileInputStream(resourcePath);
		Reader streamReader = new InputStreamReader(resourceStream);
		try
		{
			Gson gson = WidgetGsonSingleton.getGson();
			//1.1 - читаем описания виджетов из widget.cfg.json
			CollectionUtils.addAll(definitions, gson.fromJson(streamReader, WidgetDefinition[].class));
			
			//1.2 - достаем описания виджетов, которые хранятся в БД
			List<WidgetDefinition> oldDefinitions =  widgetDefinitionService.getAll();

			//2. Делаем слияние старых и новых описаний виджетов для того, чтобы сохранить настройки, сделанные в админке
			WidgetUtils.mergeDefinitions(definitions, oldDefinitions);

			//3. - очищаем таблицу в БД перед заполнением
			widgetDefinitionService.removeAll();
			//4. - записываем в БД
			widgetDefinitionService.addOrUpdateList(definitions);
			log.trace("Загрузка WidgetDefinition в бд выполнена успешна.");
		}
		catch (JsonSyntaxException e)
		{
			throw new ConfigurationException("Синтаксическая ошибка при разборе widget.cfg.json", e);
		}
		finally
		{

			if (streamReader != null)
				try
				{
					streamReader.close();
				}
				catch (IOException ignored)
				{
				}
			if (resourceStream != null)
				try
				{
					resourceStream.close();
				}
				catch (IOException ignored)
				{
				}
		}
	}
}
