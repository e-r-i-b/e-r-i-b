package com.rssl.phizic.business.resources;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 05.10.2005
 * Time: 15:32:24
 */
public interface Resource
{
    Long getId();

    <T extends Resource> Class<T> getResourceClass();
}
