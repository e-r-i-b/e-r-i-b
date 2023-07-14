package com.rssl.phizic.common.types;

/**
 * @author Erkin
 * @ created 23.05.2011
 * @ $Author$
 * @ $Revision$
 */
public abstract class AbstractEntity implements Entity
{
	private Long id;

	///////////////////////////////////////////////////////////////////////////

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
		return id != null ? id.hashCode() : 0;
	}

	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (!(o instanceof AbstractEntity))
			return false;

		AbstractEntity other = (AbstractEntity) o;

		if (this.id == null || other.id == null)
			return false;
		else return this.id.equals(other.id);
	}
}
