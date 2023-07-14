package com.rssl.phizic.business.skins;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;

import java.util.List;

/**
 * @author egorova
 * @ created 18.03.2009
 * @ $Author$
 * @ $Revision$
 */
public class SkinsLoaderSynchronizer
{
	private final SkinsService skinsService = new SkinsService();
	private final SimpleService simpleService = new SimpleService();
	private List<Skin> skins;

	public SkinsLoaderSynchronizer(List<Skin> skins)
	{
		this.skins = skins;
	}

	/**
	 * Если записи о скине нет, то она добавляется. Существующая запись обновляется. 
	 * @throws BusinessException
	 */
	public void update() throws BusinessException
	{
		for (Skin skin: skins)
		{                         
			Skin existentSkin = skinsService.findBySystemName(skin.getSystemName());
			if(existentSkin != null)
			{
				skin.setId( existentSkin.getId() );
			}
			simpleService.addOrUpdate(skin);
		}
	}
}
