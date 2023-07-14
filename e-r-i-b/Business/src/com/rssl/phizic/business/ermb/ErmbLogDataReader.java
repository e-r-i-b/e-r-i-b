package com.rssl.phizic.business.ermb;

import com.rssl.phizic.logging.operations.LogDataReader;

import java.util.Collections;
import java.util.LinkedHashMap;

/**
 * User: Moshenko
 * Date: 10.10.13
 * Time: 11:27
 * Ридер используемый в целях отслеживания изменений в данных по профилям ЕМРБ
 */
public class ErmbLogDataReader implements LogDataReader
{
    private LinkedHashMap parameters;
    private String key;
    private static final String desc = "Создание/изменение профиля ЕРМБ";

    public ErmbLogDataReader(LinkedHashMap parameters,String key)
    {
        this.parameters = parameters;
        this.key = key;
    }

    public String getOperationPath ()
    {
        return desc;
    }

    public String getOperationKey ()
    {
        return "ErmbUpdateProfile";
    }

    public String getDescription ()
    {
        return desc;
    }

    public String getKey (){
        return key;
    }

    public LinkedHashMap readParameters () throws Exception
    {
        return parameters;
    }

    public ResultType getResultType (){
        return ResultType.SUCCESS;
    }

}
