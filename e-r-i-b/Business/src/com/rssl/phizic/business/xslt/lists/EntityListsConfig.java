package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.xslt.lists.cache.composers.CacheKeyComposer;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.PropertyReader;

import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 03.11.2005
 * Time: 18:05:56
 */
public abstract class EntityListsConfig extends Config
{
	protected EntityListsConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * @param name название справочника
	 * @return определение справочника
	 */
    public abstract EntityListDefinition getListDefinition(String name);

	/**
	 * @return итератор всех определений справочников
	 */
    public abstract Iterator<EntityListDefinition> getListDefinitionsIterator();

	/**
	 * @param clazz класс для которого получаем композер
	 * @return композер для класса
	 */
    public abstract <T extends CacheKeyComposer> T getComposer(Class clazz);

	/**
	 * @param clazz класc
	 * @return список определений справочников, в которых используется класс clazz
	 */
    public abstract List<EntityListDefinition> getListDefinitionsByClassName(Class clazz);
}
