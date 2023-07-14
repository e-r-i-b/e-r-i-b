package com.rssl.phizicgate.rsV51.bankroll;

import com.rssl.phizgate.common.routable.CardBase;

/**
 * Created by IntelliJ IDEA.
 * User: Kidyaev
 * Date: 17.10.2005
 * Time: 11:48:22
 * To change this template use File | Settings | File Templates.
 */
public class CardImpl extends CardBase
{
	static final char ID_SEPARATOR = '*';
	static final int MAX_ID_LENGTH = 20;

	/**
	 * ‘ормирует внешний id из id пользовател€ и id карты
	 * @param ownerId - id клиента
	 * @param cardId - id карты
	 * @return внешний id
	 */
	public static String composeExternalId(long ownerId, long cardId)
	{
		StringBuffer buf = new StringBuffer(MAX_ID_LENGTH);

		buf.append(ownerId);
	    buf.append(ID_SEPARATOR);
		buf.append(cardId);

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
		return Long.parseLong( externalId.substring(separatorPos + 1));
	}

    private long     longId;
	private long     ownerId;
	private long     paymentSystemId;
	private long     branch; //fnCash подразделени€

	public long getBranch()
	{
		return branch;
	}

	public void setBranch(long branch)
	{
		this.branch = branch;
	}

	public long getLongId()
	{
	    return longId;
	}

    private void setLongId(long longId)
    {
        this.longId = longId;
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

	    return composeExternalId(ownerId, longId);
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
        return (int) (longId ^ (longId >>> 32));
    }
}
