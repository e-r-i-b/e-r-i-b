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
 * ������ ��� ������ � ������������� �������������� �����������
 * @author komarov
 * @ created 02.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class LanguageResourceService<T extends LanguageResource> extends MultiInstanceLanguageResourceService<T>
{
	/**
	 * @param clazz �����
	 */
	public LanguageResourceService(Class<T> clazz)
	{
		super(clazz);
	}

	/**
	 * ����� ������� �� ��������������.
	 * @param id �������������
	 * @param localeId ������������� ������
	 * @return AdvertisingBlockRes
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public T findResById(Long id, String localeId) throws BusinessException
	{
		return super.findResById(id, localeId, null);
	}


	/**
	 * ���������� ����� �������
	 * @param resource ������
	 * @return ������
	 * @throws BusinessException
	 */
	public T addOrUpdate(final T resource) throws BusinessException
	{
		return super.addOrUpdate(resource, null);
	}

	/**
	 * ���������� ����� �������
	 * @param resources ������
	 * @return ������
	 * @throws BusinessException
	 */
	public List<T> addOrUpdate(final List<T> resources) throws BusinessException
	{
		return super.addOrUpdate(resources, null);
	}




	/**
	 * �������� ���������
	 * @param id �������������
	 * @throws BusinessException
	 */
	public void removeRes(final Long id) throws BusinessException
	{
		super.removeRes(id, null);
	}
}
