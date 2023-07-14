package com.rssl.phizicgate.esberibgate.types;

import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.bankroll.StatusDescExternalCode;
import com.rssl.phizic.utils.StringHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author niculichev
 * @ created 09.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class CardExternalCodeWrapper
{
	private static final Map<String, StatusDescExternalCode> externalCode = new HashMap<String, StatusDescExternalCode>();

	static
	{
		externalCode.put("+", StatusDescExternalCode.S_PLUS);
		externalCode.put("0", StatusDescExternalCode.S_0);
		externalCode.put("A", StatusDescExternalCode.S_A);
		externalCode.put("B", StatusDescExternalCode.S_B);
		externalCode.put("C", StatusDescExternalCode.S_C);
		externalCode.put("D", StatusDescExternalCode.S_D);
		externalCode.put("E", StatusDescExternalCode.S_E);
		externalCode.put("F", StatusDescExternalCode.S_F);
		externalCode.put("1", StatusDescExternalCode.S_1);
		externalCode.put("G", StatusDescExternalCode.S_G);
		externalCode.put("H", StatusDescExternalCode.S_H);
		externalCode.put("I", StatusDescExternalCode.S_I);
		externalCode.put("J", StatusDescExternalCode.S_J);
		externalCode.put("K", StatusDescExternalCode.S_K);
		externalCode.put("L", StatusDescExternalCode.S_L);
		externalCode.put("M", StatusDescExternalCode.S_M);
		externalCode.put("N", StatusDescExternalCode.S_N);
		externalCode.put("O", StatusDescExternalCode.S_O);
		externalCode.put("P", StatusDescExternalCode.S_P);
		externalCode.put("Q", StatusDescExternalCode.S_Q);
		externalCode.put("R", StatusDescExternalCode.S_R);
		externalCode.put("S", StatusDescExternalCode.S_S);
		externalCode.put("T", StatusDescExternalCode.S_T);
		externalCode.put("U", StatusDescExternalCode.S_U);
		externalCode.put("V", StatusDescExternalCode.S_V);
		externalCode.put("W", StatusDescExternalCode.S_W);
		externalCode.put("X", StatusDescExternalCode.S_X);
		externalCode.put("Y", StatusDescExternalCode.S_Y);
		externalCode.put("Z", StatusDescExternalCode.S_Z);
		externalCode.put("9", StatusDescExternalCode.S_9);
		externalCode.put("y", StatusDescExternalCode.S_y);
		externalCode.put("m", StatusDescExternalCode.S_m);
	}

	/**
	 * Распарсить строку описания статуса на внешний код и его описание, если не получилось, возвращаем просто описание
 	 * @param value строка для парсинга
	 * @return пара(код,описание)
	 */
	public static Pair<StatusDescExternalCode, String> parseDescription(String value)
	{
		if(StringHelper.isEmpty(value))
			return new Pair<StatusDescExternalCode, String>(null, null);

		// если ли в начале строки что нибудь похожее на статус
		if(value.indexOf("-") == -1)
			return new Pair<StatusDescExternalCode, String>(null, value);

		String code = value.substring(0, value.indexOf("-")).trim();
		String description = value.substring(value.indexOf("-") + 1, value.length()).trim();

		StatusDescExternalCode exCode = externalCode.get(code);
		// в таблице со стаусами такого нет
		if(exCode == null)
			return new Pair<StatusDescExternalCode, String>(null, value);

		return new Pair<StatusDescExternalCode, String>(exCode, description);
	}
}
