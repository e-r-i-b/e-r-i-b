package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.utils.ListUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Композитрый рестрикшен mAPI
 *
 * @author khudyakov
 * @ created 23.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class MobileDataCompositeRestriction implements MobileDataRestriction
{
	private List<MobileDataRestriction> restrictions = new ArrayList<MobileDataRestriction>();

	public MobileDataCompositeRestriction(MobileDataRestriction ... restrictions)
	{
		this.restrictions.addAll(ListUtil.fromArray(restrictions));
	}

	public boolean accept(Map<String, Object> data) throws BusinessException, BusinessLogicException
	{
		for (MobileDataRestriction restriction : restrictions)
		{
			if (!restriction.accept(data))
				return false;
		}
		return true;
	}
}
