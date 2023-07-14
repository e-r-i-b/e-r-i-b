package com.rssl.phizicgate.rsretailV6r4.bankroll;

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

	private long     longId;
	private long     ownerId;
	private long     paymentSystemId;

	/**
	 * ��������� ������� id �� id ������������ � id �����
	 * @param ownerId - id �������
	 * @param cardId - id �����
	 * @return ������� id
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
	 * ���������� id ������� �� �������� id
	 * @param externalId
	 * @return id �������
	 */
	public static long parseOwnerId(String externalId)
	{
		int separatorPos = externalId.indexOf(ID_SEPARATOR);
		return Long.parseLong( externalId.substring(0, separatorPos) );
	}

	/**
	 * ���������� ���������� id �� �������� id
	 * @param externalId
	 * @return ���������� id �����
	 */
	public static long parseCardId(String externalId)
	{
		int separatorPos = externalId.indexOf(ID_SEPARATOR);
		return Long.parseLong( externalId.substring(separatorPos + 1));
	}

	long getLongId()
	{
	    return longId;
	}

    public void setLongId(long longId)
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
	 * ������� ID - ������������ ����� ������ ��������� �� "ID �������" + "*" + "ID �����"
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
