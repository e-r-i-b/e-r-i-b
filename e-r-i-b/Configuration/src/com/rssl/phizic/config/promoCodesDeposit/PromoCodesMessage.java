package com.rssl.phizic.config.promoCodesDeposit;

/**
 * Сообщение промо - кода
 *
 * @ author: Gololobov
 * @ created: 18.12.14
 * @ $Author$
 * @ $Revision$
 */
public class PromoCodesMessage
{
	private String number;              //номер сообщения
	private PromoCodesMessageType type; //тип сообщения
	private String title;               //заголовок
	private String text;                //текст сообщения
	private String event;               //описание события, при котором отображается сообщение (значение из "iccs.properties", "com.rssl.iccs.promocodes.event")

    public PromoCodesMessage()
    {
    }

    public PromoCodesMessage(String number, PromoCodesMessageType type, String title, String text, String event)
    {
        this.number = number;
        this.type = type;
        this.title = title;
        this.text = text;
        this.event = event;
    }

	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public PromoCodesMessageType getType()
	{
		return type;
	}

	public void setType(PromoCodesMessageType type)
	{
		this.type = type;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public String getEvent()
	{
		return event;
	}

	public void setEvent(String event)
	{
		this.event = event;
	}

    public PromoCodesMessage clone()
    {
        return new PromoCodesMessage(number, type, title, text, event);
	}
}
