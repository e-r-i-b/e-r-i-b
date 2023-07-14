package com.rssl.phizic.business.ext.sbrf.mobilebank;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * @author Erkin
 * @ created 21.05.2010
 * @ $Author$
 * @ $Revision$
 */
public enum UpdateType implements Serializable
{
	ADD (1),

	REMOVE(2);

	public static UpdateType forCode(Integer code) {
		if (code == null)
			return null;

		for (UpdateType type : values()) {
			if (type.code == code)
				return type;
		}

		throw new IllegalArgumentException("Unexpected update-type code: " + code);
	}

	public int getCode()
	{
		return code;
	}

	///////////////////////////////////////////////////////////////////////////

	private final int code;

	private UpdateType(int code)
	{
		this.code = code;
	}

	private Object readResolve() throws ObjectStreamException
	{
		return forCode(code);
	}
}
