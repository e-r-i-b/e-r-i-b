package com.rssl.auth.csasocial.operations.restrictions;

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
public class SocialDataCompositeRestriction implements SocialDataRestriction
{
	private List<SocialDataRestriction> restrictions = new ArrayList<SocialDataRestriction>();

	/**
	 * ctor
	 * @param restrictions набор ограничений
	 */
	public SocialDataCompositeRestriction(SocialDataRestriction... restrictions)
	{
		this.restrictions.addAll(Arrays.asList(restrictions));
	}

	public boolean accept(Map<String, Object> data) throws FrontException, FrontLogicException
	{
		for (SocialDataRestriction restriction : restrictions)
		{
			if (!restriction.accept(data))
			{
				return false;
			}
		}
		return true;
	}
}
