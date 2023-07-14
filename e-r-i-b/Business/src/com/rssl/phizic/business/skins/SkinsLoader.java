package com.rssl.phizic.business.skins;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.Loader;
import com.rssl.phizic.business.skins.generated.DefaultSkinsDescriptor;
import com.rssl.phizic.business.skins.generated.SkinDescriptor;
import com.rssl.phizic.business.skins.generated.SkinsType;
import com.rssl.phizic.utils.StringHelper;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBException;

/**
 * Загружает из xml дескриптора в БД сведения о скинах
 * @author egorova
 * @ created 18.03.2009
 * @ $Author$
 * @ $Revision$
 */
public class SkinsLoader extends Loader
{
	/**
	 * @param path путь к директории содержащей файл xml
	 */
	public SkinsLoader(String path)
	{
		super(path);
	}

	public List<Skin> load() throws BusinessException
	{
		try
		{
			SkinsType skinsDefinition = (SkinsType) unmarshall();

			DefaultSkinsDescriptor defaultSkins = skinsDefinition.getDefault();
			//noinspection unchecked
			List<SkinDescriptor> skinsDescriptors = skinsDefinition.getSkin();

			List<Skin> skins = new ArrayList<Skin>();
			for(SkinDescriptor skinObject : skinsDescriptors)
			{
				Skin skin = new Skin();
				skin.setName(skinObject.getName());
				skin.setSystemName(skinObject.getSystemName());
				skin.setUrl(skinObject.getUrl());
				if (defaultSkins.getClient().getValue().equals(skinObject.getSystemName()))
					skin.setClientDefaultSkin(true);
				if (defaultSkins.getAdmin().getValue().equals(skinObject.getSystemName()))
					skin.setAdminDefaultSkin(true);

				skin.setCommon(skinObject.isCommon());

				/**
				 * По-умолчанию, когда в skins.xml для скина на задана категория, выставляется CLIENT.
				 */
				String category = skinObject.getCategory();
				skin.setCategory( StringHelper.isEmpty(category) ? Category.CLIENT : Category.valueOf(category) );
				
				skins.add(skin);
			}

			Skin skin = new Skin();
			skin.setName("Системная настройка");
			skin.setSystemName("global");
			skin.setCategory(Category.NONE);
			skin.setUrl(skinsDefinition.getGlobalUrl());
			skins.add(skin);

			return skins;
		}
		catch (JAXBException e)
		{
			throw new BusinessException(e);
		}
	}

	protected String getPackage()
	{
		return "com.rssl.phizic.business.skins.generated";
	}

	protected String getFileName()
	{
		return "skins.xml";
	}
}
