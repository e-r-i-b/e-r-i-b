package com.rssl.phizic.business.dictionaries;

import com.rssl.phizic.gate.dictionaries.Country;

import java.util.Comparator;

/**
 * @author Kosyakova
 * @ created 07.12.2006
 * @ $Author$
 * @ $Revision$
 */
public class CountryComparator implements Comparator
{
    public int compare(Object o1, Object o2)
    {
        Country country1 = (Country) o1;
        Country country2 = (Country) o2;

        if(!country1.getSynchKey().equals(country2.getSynchKey()))
            return -1;

        if (!country1.getCode().equals(country2.getCode()))
            return -1;

        if (!country1.getName().equals(country2.getName()))
            return -1;

        return 0;
    }
}