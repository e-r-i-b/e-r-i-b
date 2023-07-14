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
 * ��������� ��������� packetEPD
 */
class PacketHeader
{
	/**
	 * ���������� ������������� ����������� ��.
	 * ������ ���������� 1111111111.
	 */
	private String author = "1111111111";

	/**
	 * ���������� ��� � ������
	 */
	private int quantity;

	/**
	 * ������� ������� ���������
	 * ������������� �������� �99�
	 */
	private String systemCode = "99";

	/**
	 * ����� �����
	 */
	private String sum;

	/**
	 * ������ ��� ��������� �������� �����
	 */
	private Random random;

	PacketHeader(Random random, int countPayment)
	{
		this.random = random;
		this.quantity = countPayment;
	}

	public String getNumber()
	{
		return String.valueOf(random.nextInt(999999999));
	}

	public String getDate()
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		return simpleDateFormat.format(Calendar.getInstance().getTime());
	}

	public String getAuthor()
	{
		return author;
	}

	public String getQuantity()
	{
		return String.valueOf(quantity);
	}

	public String getSum()
	{
		return sum;
	}

	public void setSum(String sum)
	{
		this.sum = sum;
	}

	public String getSystemCode()
	{
		return systemCode;
	}
}
