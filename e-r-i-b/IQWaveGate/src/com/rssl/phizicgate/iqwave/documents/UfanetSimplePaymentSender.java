package com.rssl.phizicgate.iqwave.documents;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.FieldDataType;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizicgate.iqwave.messaging.Constants;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * В качестве реквизита платежа за услуги УФАНЕТ используется трансформированный буквенно-цифровой номер договора. Для трансформации буквенно-цифрового номера договора в цифровой код необходимо довести до клиента следующие правила:

RRRSSNNNNNNYY - всего 13 символов, где:

RRR – регион:
Уфа = 000,
Стерлитамак = 273,
Нефтекамск = 283,
Ишимбай = 294,
Октябрьский = 267.

SS – символы (буквы) договора:

A = 01
NA = 10
AA = 02
NK = 11
AD = 03
ГТФ = 12
AL = 04
КФ = 13
AO = 05
КФИ = 14
M = 06
ЦФ = 15
MT = 07
ЦК = 16
MU = 08
ZK = 17
N = 09


NNNNNN – номер договора (дополняется до 6-значного нулями спереди, так как в Уфанет диапазон договоров от 1 до 999999, т.е. max шесть знаков)

YY – год заключения договора.

Например:

      договор заключен в Уфе, номер договора: КФ151981-07, то для того, чтобы заплатить через мобильный банк, клиент будет использовать  следующий идентификатор: 0001315198107, где 000 – Уфа, 13 – КФ, 151981 – номер договора, 07 – год заключения договора;

      если договор заключен в Стерлитамаке, номер договора: 273NK05107-09, то для оплаты через мобильный банк клиент будет использовать идентификатор: 2731100510709.
 * @author Dorzhinov
 * @ created 20.04.2011
 * @ $Author$
 * @ $Revision$
 */
public class UfanetSimplePaymentSender extends SimplePaymentSender
{
	private static final Pattern pattern = Pattern.compile("^\\d{0,3}[a-zA-Zа-яА-ЯёЁ]{1,3}\\d{1,6}-\\d{2}$");
	private static final int REC_IDENTIFIER_MIN_LENGTH = 5;
	private static final int REC_IDENTIFIER_MAX_LENGTH = 15;
	private static final Map<String, String> symbols2code = new HashMap<String, String>();

	static
	{
		symbols2code.put("A",   "01");
		symbols2code.put("AA",  "02");
		symbols2code.put("AD",  "03");
		symbols2code.put("AL",  "04");
		symbols2code.put("AO",  "05");
		symbols2code.put("M",   "06");
		symbols2code.put("MT",  "07");
		symbols2code.put("MU",  "08");
		symbols2code.put("N",   "09");
		symbols2code.put("NA",  "10");
		symbols2code.put("NK",  "11");
		symbols2code.put("ГТФ", "12");
		symbols2code.put("КФ",  "13");
		symbols2code.put("КФИ", "14");
		symbols2code.put("ЦФ",  "15");
		symbols2code.put("ЦК",  "16");
		symbols2code.put("ZK",  "17");
	}

	/**
	 * ctor
	 * @param factory - гейтовая фабрика
	 */
	public UfanetSimplePaymentSender(GateFactory factory)
	{
		super(factory);
	}

	protected Field createIdentifierField()
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.string);
		field.setExternalId(Constants.REC_IDENTIFIER_FIELD_NAME);
		field.setName("Номер договора");
		field.setRequired(true);
		field.setEditable(true);
		field.setVisible(true);
		field.setRequiredForBill(true);
		field.setSaveInTemplate(true);
		field.setMinLength((long)REC_IDENTIFIER_MIN_LENGTH);
		field.setMaxLength((long)REC_IDENTIFIER_MAX_LENGTH);
		field.setHint("Введите номер договора. Например, КФ151981-07 или 273NK05107-09.");
		return field;
	}

	public Object getIdentifier(CardPaymentSystemPayment payment) throws GateException
	{
		String identifier = super.getIdentifier(payment).toString();
		if(!pattern.matcher(identifier).matches())
			throw new GateException("Номер договора '"+identifier+"' не соответствует формату.");

		StringBuilder result = new StringBuilder();
		StringBuilder symbols = new StringBuilder(); //буквенный код
		String code; //цифровое соответствие буквенного кода
		StringBuilder number = new StringBuilder(); //номер договора
        int i = 0;
        int newSectionIndex;

		//регион
        if(Character.isLetter(identifier.charAt(0))) //Уфа
	        result.append("000");
        else //Не Уфа
            for(i = 0; Character.isDigit(identifier.charAt(i)); ++i)
	            result.append(identifier.charAt(i));
        newSectionIndex = i;

		//буквенный код
		for(i = newSectionIndex; Character.isLetter(identifier.charAt(i)); ++i)
            symbols.append(identifier.charAt(i));
		newSectionIndex = i;
		code = symbols2code.get(symbols.toString());
		if(code == null)
			throw new GateException("Буквенный код '"+symbols.toString()+"' в номере договора не имеет цифрового соответствия.");
		result.append(code);

        //собственно номер. дополняем ведущими нулями до 6 знаков
        for(i = newSectionIndex; identifier.charAt(i) != '-'; ++i)
	        number.append(identifier.charAt(i));
		result.append(StringHelper.appendLeadingZeros(number.toString(), 6));

		//год
		for(i = identifier.indexOf('-') + 1; i < identifier.length(); ++i)
			result.append(identifier.charAt(i));

		return result.toString();
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		CardPaymentSystemPayment payment = (CardPaymentSystemPayment) document;
		String identifier = super.getIdentifier(payment).toString();
		if(!pattern.matcher(identifier).matches())
			throw new GateLogicException("Номер договора '"+identifier+"' не соответствует формату.");

		StringBuilder symbols = new StringBuilder(); //буквенный код
		String code; //цифровое соответствие буквенного кода
		int symbolsStartIndex = 0;

		//буквенный код
		while(!Character.isLetter(identifier.charAt(symbolsStartIndex)))
			++symbolsStartIndex;
		for(int i = symbolsStartIndex; Character.isLetter(identifier.charAt(i)); ++i)
			symbols.append(identifier.charAt(i));
		code = symbols2code.get(symbols.toString());
		if(code == null)
			throw new GateLogicException("Буквенный код '"+symbols.toString()+"' в номере договора не имеет цифрового соответствия.");
	}
}
