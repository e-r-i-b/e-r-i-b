package ru.softlab.phizicgate.rsloansV64.claims;

import ru.softlab.phizicgate.rsloansV64.claims.parsers.FieldId;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author Omeliyanchuk
 * @ created 11.01.2008
 * @ $Author$
 * @ $Revision$
 */

public class QuestionnaireAnswerIdParser
{
	private static final String ID_DELIMETER = "||";
	private static final String USER_FIELD_DELIMETER = ":";
	
	public static FieldId parseAnswerId( String id) throws GateException
	{
		if(id==null || id.length()<=ID_DELIMETER.length())
			throw new GateException("Неверный формат заявки на кредит. Найдено поле без идентификатора.");

		String tmp = id.trim();

		int delimeterPos = tmp.indexOf(ID_DELIMETER);

		//разделитель по формату должен быть, если нет, то неверная настройка, падаем.
		if(delimeterPos == -1)
			throw new GateException("Неверный формат заявки на кредит. Для поля задан идентификатор без разделителя "+ ID_DELIMETER + ":" + id + ".");

		FieldId fieldId = new FieldId();

		if(delimeterPos !=0)
		{
			fieldId.setSystemId( tmp.substring(0,delimeterPos) );
		}

		if(tmp.length() > delimeterPos + ID_DELIMETER.length())
		{
			fieldId.setUserId( USER_FIELD_DELIMETER + tmp.substring(delimeterPos + ID_DELIMETER.length(),tmp.length()));
		}

		return fieldId;
	}
}
