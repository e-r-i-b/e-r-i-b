package com.rssl.phizic.utils.counter;

import org.apache.commons.collections.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author osminin
 * @ created 17.04.14
 * @ $Author$
 * @ $Revision$
 *
 * Экшен для формирования наименования со следующим номером
 */
public abstract class NameCounterAction
{
	private final NamingStrategy namingStrategy;

	/**
	 * ctor
	 * @param namingStrategy способ именования
	 */
	protected NameCounterAction(NamingStrategy namingStrategy)
	{
		this.namingStrategy = namingStrategy;
	}

	/**
	 * Получить наименование
	 * @param o объект
	 * @return наименование
	 */
	public abstract String getName(Object o);

	/**
	 * Рассчитать очередной номер, пишущийся в постфикс имени
	 * @param objects список объектов, по которым производится подсчет
	 * @param standardName наименование, для которого получаем номер
	 * @return номер
	 */
	final String calcNumber(List<?> objects, String standardName)
	{
		Set<String> names = new HashSet<String>();
		if (CollectionUtils.isNotEmpty(objects))
		{
			for (Object object : objects)
			{
				names.add(getName(object));
			}
		}
		return namingStrategy.unify(names, standardName);
	}
}
