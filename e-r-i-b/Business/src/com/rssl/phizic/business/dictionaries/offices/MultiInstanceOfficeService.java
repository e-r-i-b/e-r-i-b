package com.rssl.phizic.business.dictionaries.offices;

import com.rssl.phizic.business.MultiInstanceSimpleService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.NullParameterType;
import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.type.TypeFactory;
import org.apache.commons.beanutils.PropertyUtils;

import java.beans.PropertyDescriptor;

/**
 * @author Egorova
 * @ created 15.10.2008
 * @ $Author$
 * @ $Revision$
 */
public class MultiInstanceOfficeService
{
	private static final MultiInstanceSimpleService simpleService = new MultiInstanceSimpleService();
	public static final String OFFICE_SHADOW_INSTANCE_NAME = "Shadow";

	/**
	 * Найти офис по коду
	 * @param code код офиса
	 * @return офис
	 * @throws BusinessException
	 */
    public Office findByCode (final Code code, String instanceName) throws BusinessException
    {
        try
        {
	        HibernateExecutor trnExecutor = HibernateExecutor.getInstance(instanceName);

	        //noinspection OverlyComplexAnonymousInnerClass
	        return trnExecutor.execute(new HibernateAction<Office>()
            {
                public Office run(Session session) throws Exception
                {
                    Query query               = session.getNamedQuery("office.By." + code.getClass().getName());
                    PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(code);

                    for ( PropertyDescriptor propertyDescriptor : propertyDescriptors )
                    {
                        String propertyName  = propertyDescriptor.getName();
                        Object propertyValue = PropertyUtils.getProperty(code, propertyName);

                        if ( "class".equals(propertyName) )
                            continue;

	                    if ( propertyValue == null )
	                    {
		                    query.setParameter(propertyName, NullParameterType.VALUE, NullParameterType.INSTANCE);
	                    }
	                    else
	                    {
		                    String propertyValueClassName = propertyValue.getClass().getName();
		                    query.setParameter(propertyName, propertyValue, TypeFactory.heuristicType(propertyValueClassName));
	                    }
	                }

                    return (Office) query.uniqueResult();
                }
            });
        }
        catch (Exception e)
        {
            throw new BusinessException(e);
        }
    }

	/**
	 * Найти офис по id
	 * @param  className имя класса используемого офиса
	 * @param  id офиса
	 * @return офис
	 * @throws BusinessException
	 */
    public Office findBySynchKey (final String className, final String id, String instanceName) throws BusinessException
    {
        try
        {
	        HibernateExecutor trnExecutor = HibernateExecutor.getInstance(instanceName);

	        //noinspection OverlyComplexAnonymousInnerClass
	        return trnExecutor.execute(new HibernateAction<Office>()
            {
                public Office run(Session session) throws Exception
                {
                    Query query = session.getNamedQuery("office.by.synchKey." + className);
	                query.setParameter("synchKey", id);
                    return (Office) query.uniqueResult();
                }
            });
        }
        catch (Exception e)
        {
            throw new BusinessException(e);
        }
    }	


	public void addOrUpdate(Office office, String instanceName) throws BusinessException
	{
		simpleService.addOrUpdate(office, instanceName);
	}

	public void remove(Office office, String instanceName) throws BusinessException
	{
		simpleService.remove(office, instanceName);
	}

}
