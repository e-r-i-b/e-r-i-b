package com.rssl.phizic.operations.skins;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.skins.Skin;
import com.rssl.phizic.business.skins.SkinsService;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;

/**
 * @author Erkin
 * @ created 27.05.2011
 * @ $Author$
 * @ $Revision$
 */
public class RemoveSkinOperation extends OperationBase implements RemoveEntityOperation
{
	private static final SkinsService skinsService = new SkinsService();

	private Skin skin;

	///////////////////////////////////////////////////////////////////////////

	public void initialize(Long id) throws BusinessException
	{
		skin = skinsService.getById(id);
		if (skin == null)
			throw new BusinessException("—кин с Id = " + id + " не найден");
	}

	public Skin getEntity()
	{
		return skin;
	}

	public void remove() throws BusinessException, BusinessLogicException
	{
		skinsService.remove(skin);
	}
}
