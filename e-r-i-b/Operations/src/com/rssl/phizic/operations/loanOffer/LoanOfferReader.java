package com.rssl.phizic.operations.loanOffer;

import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Moshenko
 * Date: 10.06.2011
 * Time: 10:53:20
 * Ридер предодобренных кредитных предложений
 */
public class LoanOfferReader extends OfferParserBase
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
	            map.put(Constants.TERBANK, reader.get(0));
                map.put(Constants.FIO, reader.get(1));
                map.put(Constants.SEX, reader.get(2));
                map.put(Constants.BIRTH_DATE, reader.get(3));
                map.put(Constants.PASPORT_NUMBER, reader.get(4));
                map.put(Constants.SERIES, reader.get(5));
                map.put(Constants.PASPORT_DATE, reader.get(6));
                map.put(Constants.ISSUE_PLACE, reader.get(7));
                map.put(Constants.REGION, reader.get(8));
                map.put(Constants.DISTRICT, reader.get(9));
                map.put(Constants.LOCATION, reader.get(10));
                map.put(Constants.INDEX, reader.get(11));
                map.put(Constants.STREET, reader.get(12));
                map.put(Constants.HOME, reader.get(13));
                map.put(Constants.HOUSING, reader.get(14));
                map.put(Constants.APARTMENT, reader.get(15));
                map.put(Constants.REGION2, reader.get(16));
                map.put(Constants.DISTRICT2, reader.get(17));
                map.put(Constants.LOCATION2, reader.get(18));
                map.put(Constants.INDEX2, reader.get(19));
                map.put(Constants.STREET2, reader.get(20));
                map.put(Constants.HOME2, reader.get(21));
                map.put(Constants.HOUSING2, reader.get(22));
                map.put(Constants.APARTENT2, reader.get(23));
                map.put(Constants.PHONE1, reader.get(24));
                map.put(Constants.PHONE2, reader.get(25));
                map.put(Constants.PHONE3, reader.get(26));
	            map.put(Constants.MONTH6, reader.get(27));
	            map.put(Constants.MONTH12, reader.get(28));
                map.put(Constants.MONTH18, reader.get(29));
                map.put(Constants.MONTH24, reader.get(30));
                map.put(Constants.MONTH36, reader.get(31));
                map.put(Constants.MONTH48, reader.get(32));
                map.put(Constants.MONTH60, reader.get(33));
                map.put(Constants.PRODUCT_NAME, reader.get(34));
                map.put(Constants.PRODUCT_CODE, reader.get(35));
                map.put(Constants.SUB_PRODUCT_CODE, reader.get(36));
                map.put(Constants.PRACENT_RATE, reader.get(37));
	            map.put(Constants.END_DATE, reader.get(38));
                map.put(Constants.CURRENCY, reader.get(39));
	            map.put(Constants.CAMPAIGN_MEMBER_ID, reader.get(40));
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
