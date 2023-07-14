package com.rssl.phizic;

import com.rssl.phizic.common.types.annotation.MandatoryParameter;
import com.rssl.phizic.common.types.annotation.PlainOldJavaObject;
import com.rssl.phizic.person.ClientIdentity;
import com.rssl.phizic.utils.PhoneNumber;

/**
 * @author Erkin
 * @ created 02.08.2014
 * @ $Author$
 * @ $Revision$
 */
@PlainOldJavaObject
@SuppressWarnings("PublicField")
public final class RemoveErmbPhoneRequest extends Request
{
	public static final String REQUEST_NAME = "RemoveErmbPhoneRequest";

	@MandatoryParameter
	public ClientIdentity clientIdentity;

	@MandatoryParameter
	public PhoneNumber phone;

	@Override
	public String getName()
	{
		return REQUEST_NAME;
	}

	@Override
	public String toString()
	{
		return "удаление-ермб-телефона(ФИО-ДУЛ-ДР-ТБ = " + clientIdentity + ", телефон-для-удаления=" + phone + ")";
	}
}
