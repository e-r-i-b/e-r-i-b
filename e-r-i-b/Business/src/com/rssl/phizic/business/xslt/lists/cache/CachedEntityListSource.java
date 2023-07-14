package com.rssl.phizic.business.xslt.lists.cache;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.xslt.lists.EntityListDefinition;
import com.rssl.phizic.business.xslt.lists.EntityListSource;
import com.rssl.phizic.common.types.transmiters.Pair;

import java.util.List;
import java.util.Map;

/**
 * кешируемый сорс
 * @author gladishev
 * @ created 28.07.2011
 * @ $Author$
 * @ $Revision$
 */

public interface CachedEntityListSource extends EntityListSource
{
	/**
	 * @return определение справочника
	 */
	EntityListDefinition getListDefinition();

	/**
	 * @param params - параметры построения справочника
	 * @return - пара <построенная xml в виде строки,
	 *                 список объектов по которым создаем ссылку в колбак-кеше на кеш данного справочника>
	 * В списке объектов должны быть возвращены объекты с типами объявленными в определении справочника (в lists.xml)
	 * в теге calbackcache-dependences
	 */
	Pair<String, List<Object>> buildEntityList(Map<String, String> params) throws BusinessException, BusinessLogicException;
}
