package com.rssl.phizic.business.ermb.migration.mbk;

import com.rssl.phizic.gate.mobilebank.MBKRegistrationResultCode;

import java.util.Date;

/**
 * @author Nady
 * @ created 02.07.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * Результат обработки связки
 */
@SuppressWarnings("PublicField")
public class MBKRegistrationResult
{
	public long id;

	public MBKRegistrationResultCode resultCode;

	public String errorDescr;

	public Date lastModified = new Date();
}
