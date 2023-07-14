package com.rssl.phizic.logging;

import java.io.Serializable;

/**
 * @author Erkin
 * @ created 12.04.2011
 * @ $Author$
 * @ $Revision$
 */
public class CommonLogEntryId implements Serializable
{
	private LogType type;

	private Long id;

	///////////////////////////////////////////////////////////////////////////

	public LogType getType()
	{
		return type;
	}

	public void setType(LogType type)
	{
		this.type = type;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public int hashCode()
	{
		return id.hashCode();
	}

	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		CommonLogEntryId that = (CommonLogEntryId) o;

		return type == that.type && id.equals(that.id);
	}

	public String toString()
	{
		return type.toString() + id;
	}
}
