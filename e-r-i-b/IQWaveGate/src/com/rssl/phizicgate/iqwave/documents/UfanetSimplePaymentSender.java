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
 * � �������� ��������� ������� �� ������ ������ ������������ ������������������ ��������-�������� ����� ��������. ��� ������������� ��������-��������� ������ �������� � �������� ��� ���������� ������� �� ������� ��������� �������:

RRRSSNNNNNNYY - ����� 13 ��������, ���:

RRR � ������:
��� = 000,
����������� = 273,
���������� = 283,
������� = 294,
����������� = 267.

SS � ������� (�����) ��������:

A = 01
NA = 10
AA = 02
NK = 11
AD = 03
��� = 12
AL = 04
�� = 13
AO = 05
��� = 14
M = 06
�� = 15
MT = 07
�� = 16
MU = 08
ZK = 17
N = 09


NNNNNN � ����� �������� (����������� �� 6-�������� ������ �������, ��� ��� � ������ �������� ��������� �� 1 �� 999999, �.�. max ����� ������)

YY � ��� ���������� ��������.

��������:

      ������� �������� � ���, ����� ��������: ��151981-07, �� ��� ����, ����� ��������� ����� ��������� ����, ������ ����� ������������  ��������� �������������: 0001315198107, ��� 000 � ���, 13 � ��, 151981 � ����� ��������, 07 � ��� ���������� ��������;

      ���� ������� �������� � ������������, ����� ��������: 273NK05107-09, �� ��� ������ ����� ��������� ���� ������ ����� ������������ �������������: 2731100510709.
 * @author Dorzhinov
 * @ created 20.04.2011
 * @ $Author$
 * @ $Revision$
 */
public class UfanetSimplePaymentSender extends SimplePaymentSender
{
	private static final Pattern pattern = Pattern.compile("^\\d{0,3}[a-zA-Z�-��-߸�]{1,3}\\d{1,6}-\\d{2}$");
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
		symbols2code.put("���", "12");
		symbols2code.put("��",  "13");
		symbols2code.put("���", "14");
		symbols2code.put("��",  "15");
		symbols2code.put("��",  "16");
		symbols2code.put("ZK",  "17");
	}

	/**
	 * ctor
	 * @param factory - �������� �������
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
		field.setName("����� ��������");
		field.setRequired(true);
		field.setEditable(true);
		field.setVisible(true);
		field.setRequiredForBill(true);
		field.setSaveInTemplate(true);
		field.setMinLength((long)REC_IDENTIFIER_MIN_LENGTH);
		field.setMaxLength((long)REC_IDENTIFIER_MAX_LENGTH);
		field.setHint("������� ����� ��������. ��������, ��151981-07 ��� 273NK05107-09.");
		return field;
	}

	public Object getIdentifier(CardPaymentSystemPayment payment) throws GateException
	{
		String identifier = super.getIdentifier(payment).toString();
		if(!pattern.matcher(identifier).matches())
			throw new GateException("����� �������� '"+identifier+"' �� ������������� �������.");

		StringBuilder result = new StringBuilder();
		StringBuilder symbols = new StringBuilder(); //��������� ���
		String code; //�������� ������������ ���������� ����
		StringBuilder number = new StringBuilder(); //����� ��������
        int i = 0;
        int newSectionIndex;

		//������
        if(Character.isLetter(identifier.charAt(0))) //���
	        result.append("000");
        else //�� ���
            for(i = 0; Character.isDigit(identifier.charAt(i)); ++i)
	            result.append(identifier.charAt(i));
        newSectionIndex = i;

		//��������� ���
		for(i = newSectionIndex; Character.isLetter(identifier.charAt(i)); ++i)
            symbols.append(identifier.charAt(i));
		newSectionIndex = i;
		code = symbols2code.get(symbols.toString());
		if(code == null)
			throw new GateException("��������� ��� '"+symbols.toString()+"' � ������ �������� �� ����� ��������� ������������.");
		result.append(code);

        //���������� �����. ��������� �������� ������ �� 6 ������
        for(i = newSectionIndex; identifier.charAt(i) != '-'; ++i)
	        number.append(identifier.charAt(i));
		result.append(StringHelper.appendLeadingZeros(number.toString(), 6));

		//���
		for(i = identifier.indexOf('-') + 1; i < identifier.length(); ++i)
			result.append(identifier.charAt(i));

		return result.toString();
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		CardPaymentSystemPayment payment = (CardPaymentSystemPayment) document;
		String identifier = super.getIdentifier(payment).toString();
		if(!pattern.matcher(identifier).matches())
			throw new GateLogicException("����� �������� '"+identifier+"' �� ������������� �������.");

		StringBuilder symbols = new StringBuilder(); //��������� ���
		String code; //�������� ������������ ���������� ����
		int symbolsStartIndex = 0;

		//��������� ���
		while(!Character.isLetter(identifier.charAt(symbolsStartIndex)))
			++symbolsStartIndex;
		for(int i = symbolsStartIndex; Character.isLetter(identifier.charAt(i)); ++i)
			symbols.append(identifier.charAt(i));
		code = symbols2code.get(symbols.toString());
		if(code == null)
			throw new GateLogicException("��������� ��� '"+symbols.toString()+"' � ������ �������� �� ����� ��������� ������������.");
	}
}
