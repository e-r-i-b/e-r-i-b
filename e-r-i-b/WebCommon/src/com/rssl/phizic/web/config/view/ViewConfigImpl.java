package com.rssl.phizic.web.config.view;

import java.io.InputStream;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * @author Evgrafov
 * @ created 10.08.2007
 * @ $Author: Evgrafov $
 * @ $Revision: 4675 $
 */
@SuppressWarnings({"unchecked"})
public class ViewConfigImpl implements ViewConfig
{
	private static final TemplateManager DEFAULT_TEMPLATE_MANAGER = new DefaultTemplateManager();

	private Map<String, TemplateManager> listTemplates;

	/**
	 * Прочитать настройки из потока
	 * @param is поток
	 */
	public ViewConfigImpl(InputStream is) throws JAXBException
	{
		listTemplates = new HashMap<String, TemplateManager>();

		if(is == null)
			return;

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		JAXBContext context = JAXBContext.newInstance("com.rssl.phizic.web.config.view", classLoader);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		ViewConfigRoot viewConfigRoot = (ViewConfigRoot) unmarshaller.unmarshal(is);

		readListSettings(viewConfigRoot);
	}

	private void readListSettings(ViewConfigRoot viewConfigRoot)
	{
		List<ListDefinition> list = viewConfigRoot.getLists().getListDefinitions();
		for (ListDefinition definition : list)
		{
			List<ColumnType> columns = definition.getColumn();
			List<String> ids = new ArrayList<String>(columns.size());

			for (ColumnType column : columns)
			{
				if(column.getAction() == ActionType.hide)
					ids.add(column.getId());
			}

			TemplateManager templateManager = new AllButListedTemplateManager(ids);
			listTemplates.put(definition.getId(), templateManager);
		}
	}

	/**
	 * Возвращает AllButListedTemplateManager если он задан в настройках,
	 * иначе возвращает DefaultTemplateManager
	 */
	public TemplateManager getListTemplateManager(String view)
	{
		if(view == null)
			return DEFAULT_TEMPLATE_MANAGER;
		
		TemplateManager templateManager = listTemplates.get(view);
		return templateManager == null ? DEFAULT_TEMPLATE_MANAGER : templateManager;
	}
}