package com.rssl.auth.csa.front.business.blockingrules;

import com.rssl.auth.csa.front.business.regions.RegionHelper;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.utils.pattern.PatternUtils;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author komarov
 * @ created 10.04.2013
 * @ $Author$
 * @ $Revision$
 */

public class BlockingRulesUtils
{
	private static final String ALL_NOTIFICATION_KEY = "all-notification-key";
	private static final CheckBlockingRuleService service = new CheckBlockingRuleService();


	/**
	 * Найти соответсвующее cbCode правило активное блокировки
	 * @return найденное, активное правило или null, если ограничения нет
	 */
	public static BlockingRules findBlockingRule() throws BusinessException
	{
		String cbCode = RegionHelper.getRegionTB();
		if(cbCode == null)
			return null;

		List<BlockingRules> rules = findAll();
		for (BlockingRules rule : rules)
		{
			if (matches(cbCode, rule.getDepartments()))
			{
				return rule;
			}
		}
		return null;
	}

	/**
	 * Cоответсвует ли переданный cbCode ограничению
	 * @param cbCode код подразделения
	 * @param departments департаменты указанные в формате RbTbBranchId
	 * @return да/нет
	 */
	private static boolean matches(String cbCode, String departments)
	{
		if (cbCode == null)
		{
			throw new IllegalArgumentException("Параметр не может быть null");
		}
		String[] codes = departments.split(",");
		StringBuilder dep = new StringBuilder();
		for(String code : codes)
		{
			if(dep.length() > 0)
				dep.append(",");
			if(code.length() > 2)
				dep.append(code.substring(0,2));
			else
				dep.append(code);
		}

		Pattern pattern = PatternUtils.compileDepartmentsPatten(dep.toString());
		return pattern.matcher(cbCode).matches();
	}


	/**
	 * @return все (активные) ограничения
	 */
	private static List<BlockingRules> findAll() throws BusinessException
	{
		Cache cache = CacheProvider.getCache("bloking-rule-notification-csa-cache");
		Element element = cache.get(ALL_NOTIFICATION_KEY);
		if(element == null)
		{
			List<BlockingRules> list = service.getRegionBlokingRules();
		 	cache.put(new Element(ALL_NOTIFICATION_KEY, list));
			return list;
		}
	    return (List<BlockingRules>)element.getObjectValue();
	}
}