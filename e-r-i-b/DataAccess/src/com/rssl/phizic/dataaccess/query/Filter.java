package com.rssl.phizic.dataaccess.query;

/**
 * @author Roshka
 * @ created 06.02.2006
 * @ $Author$
 * @ $Revision$
 */
public interface Filter
{
    /**
     * Установить параметр фильтра
     * @param name имя
     * @param value значение
     */
    Filter setParameter(String name, Object value);

    /**
     * @return имя фильтра
     */
    String getName();
}
