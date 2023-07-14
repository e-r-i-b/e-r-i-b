package com.rssl.phizicgate.esberibgate.types.wrappers;

import com.rssl.phizic.gate.bankroll.ReportDeliveryType;
import com.rssl.phizicgate.esberibgate.ws.generated.ReportDeliveryType_Type;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.ReportDeliveryTypeType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author akrenev
 * @ created 24.04.2014
 * @ $Author$
 * @ $Revision$
 *
 * Враппер значения типа подписки на отчеты по карте
 */

public class ReportDeliveryTypeWrapper
{
	private static final Map<ReportDeliveryType_Type, ReportDeliveryType> fromGate = new HashMap<ReportDeliveryType_Type, ReportDeliveryType>(2);
	private static final Map<ReportDeliveryType, ReportDeliveryTypeType> toGate = new HashMap<ReportDeliveryType, ReportDeliveryTypeType>(2);

	static
	{
		fromGate.put(ReportDeliveryType_Type.P, ReportDeliveryType.PDF);
		fromGate.put(ReportDeliveryType_Type.H, ReportDeliveryType.HTML);
		toGate.put(ReportDeliveryType.PDF,  ReportDeliveryTypeType.P);
		toGate.put(ReportDeliveryType.HTML, ReportDeliveryTypeType.H);
	}

	/**
	 * получить наше представление из прдставления внешней системы
	 * @param value прдставление внешней системы
	 * @return наше представление
	 */
	public static ReportDeliveryType fromGate(ReportDeliveryType_Type value)
	{
		if (value == null)
			return null;

		return fromGate.get(value);
	}

	/**
	 * получить прдставление внешней системы из нашего представления
	 * @param type наше представление
	 * @return прдставление внешней системы
	 */
	public static ReportDeliveryTypeType toGate(ReportDeliveryType type)
	{
		return toGate.get(type);
	}
}
