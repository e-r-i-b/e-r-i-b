package com.rssl.phizicgate.rsretailV6r20.bankroll;

import com.rssl.phizgate.common.routable.CardBase;

/**
 * @author Danilov
 * @ created 03.10.2007
 * @ $Author$
 * @ $Revision$
 */
public class CardImpl extends CardBase
{
	static final char ID_SEPARATOR = '*';
	static final int MAX_ID_LENGTH = 20;
	static final char ID_SEPARATOR_EXT = '*';  // то что используем в маппинге дл€ разделени€ id*fnCash

	private String   longId;
	private String   fnCash;
	private long     ownerId;
	private long     paymentSystemId;

	/**
	 * ‘ормирует внешний id из id пользовател€ и id карты
	 * @param ownerId - id клиента
	 * @param cardId - id карты
	 * @return внешний id
	 */
	//public static String composeExternalId(long ownerId, long cardId)
	public static String composeExternalId(long ownerId, String cardId, String fnCash)
	{
		StringBuffer buf = new StringBuffer(MAX_ID_LENGTH);

		buf.append(ownerId);
	    buf.append(ID_SEPARATOR);
		buf.append(cardId);

		if (cardId.indexOf(ID_SEPARATOR_EXT) == -1)
		{
			buf.append(ID_SEPARATOR_EXT);
			buf.append(fnCash);
		}

		return buf.toString();
	}

	/**
	 * ¬озвращает id клиента из внешнего id
	 * @param externalId
	 * @return id клиента
	 */
	public static long parseOwnerId(String externalId)
	{
		int separatorPos = externalId.indexOf(ID_SEPARATOR);
		return Long.parseLong( externalId.substring(0, separatorPos) );
	}

	/**
	 * ¬озвращает внутренний id из внешнего id
	 * @param externalId
	 * @return внутренний id карты
	 */
	public static long parseCardId(String externalId)
	{
		int separatorPos = externalId.indexOf(ID_SEPARATOR);
		externalId = externalId.substring(separatorPos + 1);
		separatorPos = externalId.indexOf(ID_SEPARATOR_EXT);
		return Long.parseLong( externalId.substring(0, separatorPos));
	}

	/**
	 * ¬озвращает внутренний fnCash из внешнего id
	 * @param externalId
	 * @return fnCash карты
	 */
	public static long parse_fnCash(String externalId)
	{
		int separatorPos = externalId.indexOf(ID_SEPARATOR);
		externalId = externalId.substring(separatorPos + 1);
		separatorPos = externalId.indexOf(ID_SEPARATOR_EXT);
		return Long.parseLong( externalId.substring(separatorPos + 1));
	}


	String getLongId()
	{
	    return longId;
	}

    private void setLongId(String longId)
    {
        this.longId = longId;
    }

	String getFnCash()
	{
	    return fnCash;
	}

	private void setFnCash(String fnCash)
	{
	    this.fnCash = fnCash;
	}

	long getOwnerId()
	{
		return ownerId;
	}

	void setOwnerId(long ownerId)
	{
		this.ownerId = ownerId;
	}

	/**
	 * ¬нешний ID - представл€ет собой строку состо€щую из "ID клиента" + "*" + "ID карты"
	 * @return ID
	 */
	public String getId()
    {
        if (super.getId() != null)
            return super.getId();

        return composeExternalId(ownerId, longId, fnCash);
    }

	long getPaymentSystemId()
	{
		return paymentSystemId;
	}

	private void setPaymentSystemId(long paymentSystemId)
	{
		this.paymentSystemId = paymentSystemId;
	}

	public boolean equals(Object o)
    {
	    if ( this == o )
	    {
		    return true;
	    }
	    if ( o == null || getClass() != o.getClass() )
	    {
		    return false;
	    }

        final CardImpl card = (CardImpl) o;

	    if ( longId != card.longId )
	    {
		    return false;
	    }

        return true;
    }

    public int hashCode()
    {
	    int separatorPos = longId.indexOf(ID_SEPARATOR_EXT);
	    long loc_longId = Long.parseLong( longId.substring(separatorPos + 1));
        return (int) (loc_longId ^ (loc_longId >>> 32));
    }
}
