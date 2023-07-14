package com.rssl.phizic.business.groups;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.Loader;
import com.rssl.phizic.business.groups.generated.GroupDescriptor;
import com.rssl.phizic.business.groups.generated.GroupsType;
import com.rssl.phizic.business.skins.Skin;
import com.rssl.phizic.business.skins.SkinsService;
import com.rssl.phizic.utils.StringHelper;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBException;

/**
 * Загружает из xml дескриптора в БД сведения о группах
 * User: Balovtsev
 * Date: 25.05.2011
 * Time: 17:30:31
 */
public class GroupsLoader extends Loader
{
	private final SkinsService skinService = new SkinsService();
	
	/**
	 * @param path путь к директории содержащей файл xml
	 */
	public GroupsLoader(String path)
	{
		super(path);
	}

	public List<Group> load() throws BusinessException
	{
		try
		{
			GroupsType groupsDefinition  = (GroupsType) unmarshall();

			List<Group> groups = new ArrayList<Group>();
			for(GroupDescriptor descriptor : groupsDefinition.getGroup())
			{              
				Group item = new Group();
				item.setName( descriptor.getName() );
				/*
				 * Если приоритет не будет задан явно, группа получит приоритет равный 0
				 */
				item.setPriority( descriptor.getPriority() );
				item.setDescription( descriptor.getValue() );

				String category = descriptor.getCategory();
				item.setCategory( StringHelper.isEmpty(category) ? "C" : category );
				
				item.setSystemName( descriptor.getSystemName() );
				item.setSkin( getSkin(descriptor.getDefaultSkin()) );
				groups.add( item );
			}
			return groups;
		}
		catch(JAXBException e)
		{
			throw new BusinessException(e);			
		}
	}

	protected Skin getSkin(String defaultSkin) throws BusinessException
	{
		if ( StringHelper.isEmpty(defaultSkin) )
		{
			return null;
		}

		Skin skin = skinService.findBySystemName(defaultSkin);
		if (skin == null)
		{
			throw new BusinessException("Скин под системным именем '" + defaultSkin + "' не найден. Выполнение задачи по загрузке групп прекращено.");
		}

		return skin;
	}

	protected String getPackage()
	{
		return "com.rssl.phizic.business.groups.generated";
	}

	protected String getFileName()
	{
		return "user-groups.xml";
	}
}
