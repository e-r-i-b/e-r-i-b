package com.rssl.phizic.gate.mobilebank;

/**
 * @author Gulov
 * @ created 23.09.13
 * @ $Author$
 * @ $Revision$
 */

/**
 * Результат на запрос определение карты по номеру телефона и тербанку (P2P).
 */
public class MobilePaymentCardResult
{
	/**
	 * ИД запроса (полученный из процедуры dbo.ERMB_MobilePaymentCardRequests)
	 */
	private int id;

	/**
	 * Номер карты-получателя перевода. Может быть пустым, если карту-получателя определить не удалось.
	 */
	private String cardNumber;

	/**
	 * Имя Отчество и первая буква Фамилии держателя карты-получателя платежа. Может быть пустым.
	 * Информация передаётся ЕРИБ «как есть» без приведения к верхнему регистру. Точка после первой буквы фамилии НЕ указывается.
	 */
	private String clientName;

	/**
	 * Код характеризующий результат определения карты-получателя.
	 * 0 – Ошибки НЕТ.
	 * -1 – переданный телефон не принадлежит клиенту ЕРМБ
	 * -2 – карта получатель не может быть определена
	 * -3 – нет живых, активных карт у клиента по данным WAY4
	 * -4 – не задан тербанк
	 * -5 – не задан код отделения
	 */
	private int resultCode;

	/**
	 * Произвольный текст, на усмотрение ЕРИБ, поясняющий resultCode
	 */
	private String comment;

	//для веб-сервиса
	public MobilePaymentCardResult()
	{
	}

	public MobilePaymentCardResult(int id, String cardNumber, String clientName, int resultCode, String comment)
	{
		this.id = id;
		this.cardNumber = cardNumber;
		this.clientName = clientName;
		this.resultCode = resultCode;
		this.comment = comment;
	}

	public int getId()
	{
		return id;
	}

	public String getCardNumber()
	{
		return cardNumber;
	}

	public String getClientName()
	{
		return clientName;
	}

	public int getResultCode()
	{
		return resultCode;
	}

	public String getComment()
	{
		return comment;
	}
}
