package com.rssl.phizic.operations.loanOffer;

import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Moshenko
 * Date: 16.06.2011
 * Time: 9:28:02
 * Ридер предодобренных  предложений по карам
 */
public class LoanCardOfferReader extends OfferParserBase
{
    /**
     * читает запись и возвращаят значения в формате MapValuesSource
     * текущей записи reader-а
     * @return
     */
    public MapValuesSource getRowValueSource() throws BusinessException
    {
        Map map = new HashMap<String, String>();

        try
        {
            if (reader.readRecord())
            {
                map.put("surname", reader.get(0));
                map.put("name", reader.get(1));
                map.put("patrName", reader.get(2));
                map.put("seriesAndNumber", reader.get(3));
                map.put(Constants.BIRTH_DATE, reader.get(4));
                map.put("offerType", reader.get(5));
                map.put("creditLimit", reader.get(6));
                map.put("companyName", reader.get(7));
                map.put(Constants.INDEX, reader.get(8));
                map.put("adres1", reader.get(9));
                map.put("adres2", reader.get(10));
                map.put("adres3", reader.get(11));
                map.put("adres4", reader.get(12));
                map.put("idWay", reader.get(13));
                map.put("cardNumber", reader.get(14));
                map.put(Constants.TERBANK, reader.get(15));
                map.put(Constants.OSB, reader.get(16));
                map.put("vsp", reader.get(17));
                map.put("clientCodeType", reader.get(18));
            }
            else
                return null;
        }
        catch (IOException e)
        {
            throw new BusinessException("Текущий ридер не установлен", e);
        }
        return new MapValuesSource(map);
    }
}
