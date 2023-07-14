package com.rssl.phizicgate.esberibgate.types.wrappers;

import com.rssl.phizic.gate.bankroll.ReportDeliveryLanguage;
import com.rssl.phizicgate.esberibgate.ws.generated.ReportLangType_Type;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.ReportLangTypeType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author akrenev
 * @ created 24.04.2014
 * @ $Author$
 * @ $Revision$
 *
 * ������� �������� ����� �������� �� ������ �� �����
 */

public class ReportDeliveryLanguageWrapper
{
	private static final Map<ReportLangType_Type, ReportDeliveryLanguage> fromGate = new HashMap<ReportLangType_Type, ReportDeliveryLanguage>(2);
	private static final Map<ReportDeliveryLanguage, ReportLangTypeType> toGate = new HashMap<ReportDeliveryLanguage, ReportLangTypeType>(2);

	static
	{
		fromGate.put(ReportLangType_Type.RUS, ReportDeliveryLanguage.RU);
		fromGate.put(ReportLangType_Type.ENG, ReportDeliveryLanguage.EN);
		toGate.put(ReportDeliveryLanguage.RU, ReportLangTypeType.RUS);
		toGate.put(ReportDeliveryLanguage.EN, ReportLangTypeType.ENG);
	}

	/**
	 * �������� ���� ������������� �� ������������ ������� �������
	 * @param value ������������ ������� �������
	 * @return ���� �������������
	 */
	public static ReportDeliveryLanguage fromGate(ReportLangType_Type value)
	{
		if (value == null)
			return null;
		return fromGate.get(value);
	}

	/**
	 * �������� ������������ ������� ������� �� ������ �������������
	 * @param language ���� �������������
	 * @return ������������ ������� �������
	 */
	public static ReportLangTypeType toGate(ReportDeliveryLanguage language)
	{
		return toGate.get(language);
	}
}
