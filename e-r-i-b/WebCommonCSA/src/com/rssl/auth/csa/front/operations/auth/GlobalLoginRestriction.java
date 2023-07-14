package com.rssl.auth.csa.front.operations.auth;

import com.rssl.auth.csa.front.business.blockingrules.CheckBlockingRuleService;
import com.rssl.auth.csa.front.exceptions.FrontException;
import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.common.types.exceptions.SystemException;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.apache.commons.lang.BooleanUtils;

/**
 * ���������� ����������� �����, �������� � �������.
 * @author niculichev
 * @ created 03.12.2012
 * @ $Author$
 * @ $Revision$
 */
public class GlobalLoginRestriction implements Restriction
{
	private static final String GLOBAL_LOGIN_RESTRICTION_KEY = GlobalLoginRestriction.class.getName();
	private static final CheckBlockingRuleService blockingRulesService = new CheckBlockingRuleService();

	/**
	 * @return true - ����������� �� ���� ���, false - �������� ��������� ����������� �����
	 */
	public boolean check() throws FrontException
	{
		try
		{
			Cache cache = CacheProvider.getCache("global-bloking-rule-csa-cache");
			Element element = cache.get(GLOBAL_LOGIN_RESTRICTION_KEY);
			if(element != null)
				return BooleanUtils.isNotTrue((Boolean) element.getValue());

			boolean flag = blockingRulesService.isGlobalBloking();

		    cache.put(new Element(GLOBAL_LOGIN_RESTRICTION_KEY, flag));
			return !flag;
		}
		catch (SystemException e)
		{
			throw new FrontException(e);
		}
	}
}
