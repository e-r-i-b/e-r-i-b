package com.rssl.phizic.test.fns;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

/**
 * @author gulov
 * @ created 24.01.2011
 * @ $Authors$
 * @ $Revision$
 */

/**
 * ������� ED101 ��������� packetED
 */
class PacketItem
{
	/**
	 * ��� �������� ������������� �������� "01"
	 */
	private String transKind = "01";

	/**
	 * ����������� ������� ������������� �������� "0"
	 */
	private String priority = "0";

	/**
	 * ��� ������� ������������� �������� "0"
	 */
	private String paymentKind = "0";

	/**
	 * ������� ���������
	 */
	private String signValue;

	/**
	 * ��������� �������� ������� ��� ����������� ������� ��� ������.
	 */
	private String purpose = "����� �� ������ ���������� ���";

	/**
	 * ��������� ���������� �������
	 */
	private String paymentReason = "��";

	/**
	 * ��� ���������� �������
	 */
	private String taxPaymentKind = "0";

	/**
	 * ������������ ����������
	 */
	private String payeeTitle = "�� ���� 1370 (����������� ��������� ���  �7 �� ������������� �������)";

	/**
	 * ����� �������
	 */
	private String sum;

	/**
	 * ������ ��� ��������� �������� �����
	 */
	private Random random;

	PacketItem(Random random)
	{
		this.random = random;
	}

	public String getTransKind()
	{
		return transKind;
	}

	public String getPriority()
	{
		return priority;
	}

	public String getReceiptDate()
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		return simpleDateFormat.format(Calendar.getInstance().getTime());
	}

	public String getPaymentKind()
	{
		return paymentKind;
	}

	public String getSum()
	{
		sum = String.valueOf(random.nextInt(99999));

		return sum;
	}

	public String getSignValue()
	{
		return signValue;
	}

	public String getPurpose()
	{
		return purpose;
	}

	public String getDrawerStatus()
	{
		return String.valueOf(random.nextInt(20));
	}

	public String getKbk()
	{
		return getRandomText(20);
	}

	public String getOkato()
	{
		return getRandomText(11);
	}

	public String getPaymentReason()
	{
		return paymentReason;
	}

	public String getTaxPeriod()
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		return simpleDateFormat.format(Calendar.getInstance().getTime());
	}

	public String getDocumentNumber()
	{
		long currentTime = System.currentTimeMillis();

		String result = String.valueOf(currentTime) + getRandomText(2);

		return result.length() > 20 ? result.substring(0, 20) : result;
	}

	public String getDocumentDate()
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		return simpleDateFormat.format(Calendar.getInstance().getTime());
	}

	public String getTaxPaymentKind()
	{
		return taxPaymentKind;
	}

	public String getPayerINN()
	{
		return getRandomText(12);
	}

	public String getPersonalAccount()
	{
		String text1 = getRandomText(5);
		String text2 = getRandomText(12);
		return text1 + "810" + text2; 

	}

	public String getPayeeINN()
	{
		return getRandomText(12);
	}

	public String getPayeeKPP()
	{
		return getRandomText(9);
	}

	public String getPayeeTitle()
	{
		return payeeTitle;
	}

	public String getPayeeBIC()
	{
		return getRandomText(9);
	}

	public String getPayeeCorrespondentAccount()
	{
		return getRandomText(20);
	}

	private String getRandomText(int max)
	{
		StringBuilder sb = new StringBuilder(max);

		for (int i = 0; i < max; i++)
		{
			sb.append(random.nextInt(10));
		}

		return sb.toString();
	}
}
