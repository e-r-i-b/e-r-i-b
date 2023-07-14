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
 * Заголовок сообщения packetEPD
 */
class PacketHeader
{
	/**
	 * Уникальный идентификатор составителя ЭД.
	 * Всегда передается 1111111111.
	 */
	private String author = "1111111111";

	/**
	 * Количество ЭПД в пакете
	 */
	private int quantity;

	/**
	 * Признак системы обработки
	 * Фиксированное значение “99”
	 */
	private String systemCode = "99";

	/**
	 * Общая сумма
	 */
	private String sum;

	/**
	 * Рандом для генерации значений полей
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
