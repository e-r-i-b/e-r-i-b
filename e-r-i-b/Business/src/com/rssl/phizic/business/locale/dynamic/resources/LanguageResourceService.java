package com.rssl.phizic.business.locale.dynamic.resources;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.locale.dynamic.resources.LanguageResource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.List;

/**
 * Сервис для работы с динамическими мультиязычными текстовками
 * @author komarov
 * @ created 02.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class LanguageResourceService<T extends LanguageResource> extends MultiInstanceLanguageResourceService<T>
{
	/**
	 * @param clazz класс
	 */
	public LanguageResourceService(Class<T> clazz)
	{
		super(clazz);
	}

	/**
	 * Поиск баннера по идентификатору.
	 * @param id идентификатор
	 * @param localeId идентификатор локали
	 * @return AdvertisingBlockRes
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public T findResById(Long id, String localeId) throws BusinessException
	{
		return super.findResById(id, localeId, null);
	}


	/**
	 * Добавление файла ресурса
	 * @param resource ресурс
	 * @return ресурс
	 * @throws BusinessException
	 */
	public T addOrUpdate(final T resource) throws BusinessException
	{
		return super.addOrUpdate(resource, null);
	}

	/**
	 * Добавление файла ресурса
	 * @param resources ресурс
	 * @return ресурс
	 * @throws BusinessException
	 */
	public List<T> addOrUpdate(final List<T> resources) throws BusinessException
	{
		return super.addOrUpdate(resources, null);
	}




	/**
	 * удаление текстовок
	 * @param id идентификатор
	 * @throws BusinessException
	 */
	public void removeRes(final Long id) throws BusinessException
	{
		super.removeRes(id, null);
	}
}
