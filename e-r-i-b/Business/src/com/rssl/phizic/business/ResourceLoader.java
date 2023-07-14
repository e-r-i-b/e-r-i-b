package com.rssl.phizic.business;

import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.resources.Resource;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 04.10.2005
 * Time: 18:54:49
 */
public interface ResourceLoader
{
    public Object load(Long resourceId) throws BusinessException;

    public List<Resource> getPersonResources(Person person) throws BusinessException;
}
