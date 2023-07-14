package com.rssl.auth.csamapi.operations.restrictions;

import com.rssl.auth.csa.front.exceptions.FrontException;
import com.rssl.auth.csa.front.exceptions.FrontLogicException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author osminin
 * @ created 02.08.13
 * @ $Author$
 * @ $Revision$
 *
 * композитное ограничение для мАпи
 */
public class MobileDataCompositeRestriction implements MobileDataRestriction
{
	private List<MobileDataRestriction> restrictions = new ArrayList<MobileDataRestriction>();

	/**
	 * ctor
	 * @param restrictions набор ограничений
	 */
	public MobileDataCompositeRestriction(MobileDataRestriction... restrictions)
	{
		this.restrictions.addAll(Arrays.asList(restrictions));
	}

	public boolean accept(Map<String, Object> data) throws FrontException, FrontLogicException
	{
		for (MobileDataRestriction restriction : restrictions)
		{
			if (!restriction.accept(data))
			{
				return false;
			}
		}
		return true;
	}
}
