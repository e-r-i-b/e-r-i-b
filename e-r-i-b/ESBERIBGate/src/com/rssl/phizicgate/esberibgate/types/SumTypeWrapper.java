package com.rssl.phizicgate.esberibgate.types;

import com.rssl.phizic.gate.longoffer.SumType;

import java.util.Map;
import java.util.HashMap;

/**
 * @author osminin
 * @ created 17.09.2010
 * @ $Author$
 * @ $Revision$
 * Раппер для получения соответствующего SumType по коду суммы
 */
public class SumTypeWrapper
{
	private static final Map<String, SumType> sumTypes = new HashMap<String, SumType>();

	static
	{
		sumTypes.put("FIXED_SUMMA",                 SumType.FIXED_SUMMA);
		sumTypes.put("REMAIND_OVER_SUMMA",          SumType.REMAIND_OVER_SUMMA);
		sumTypes.put("PERCENT_OF_REMAIND",          SumType.PERCENT_OF_REMAIND);
		sumTypes.put("OVER_DRAFT",                  SumType.OVER_DRAFT);
		sumTypes.put("FIXED_SUMMA_IN_RECIP_CURR",   SumType.FIXED_SUMMA_IN_RECIP_CURR);
		sumTypes.put("REMAIND_IN_RECIP",            SumType.REMAIND_IN_RECIP);
		sumTypes.put("PERCENT_OF_CAPITAL",          SumType.PERCENT_OF_CAPITAL);
		sumTypes.put("SUMMA_OF_RECEIPT",            SumType.SUMMA_OF_RECEIPT);
		sumTypes.put("CREDIT_MINIMUM",              SumType.CREDIT_MINIMUM);
		sumTypes.put("CREDIT_MANUAL",               SumType.CREDIT_MANUAL);
		sumTypes.put("BY_TARIF",                    SumType.BY_TARIF);
		sumTypes.put("BY_BILLING",                  SumType.BY_BILLING);
		sumTypes.put("PERCENT_BY_ANY_RECEIPT",      SumType.PERCENT_BY_ANY_RECEIPT);
		sumTypes.put("PERCENT_BY_DEBIT",            SumType.PERCENT_BY_DEBIT);
		sumTypes.put("RUR_SUMMA",                   SumType.RUR_SUMMA);
	}

	public static SumType getSumType(String summaKindCode)
	{
		SumType sumType = sumTypes.get(summaKindCode);
		if (sumType != null)
			return sumType;
		throw new IllegalArgumentException("Неверный тип алгоритма рассчета суммы очередного автоплатежа: "+summaKindCode);
	}

	/**
	 * преобразовать енум SumType в строку для передачи на шину
	 * @param sumType тип суммы
	 * @return значение для шины
	 */
	public static String toESBValue(SumType sumType)
	{
		return sumType.name();
	}
}
