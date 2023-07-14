package com.rssl.phizic.locale.entities;

import com.rssl.phizic.common.types.Application;

/**
 * ћного€зычные приложени€
 * @author komarov
 * @ created 11.09.2014
 * @ $Author$
 * @ $Revision$
 */
public enum MultiLanguageApplications
{

	ERIB,
	mApi,
	atmApi,
	webApi,
	csa,
	ERMB;

	/**
	 * ѕолучение типа приложени€
	 * @param application тип приложени€ системы
	 * @return тип приложени€ в разрезе канала
	 */
	public static MultiLanguageApplications fromApplication(Application application)
	{
		switch (application)
		{
			case PhizIA:
			case PhizIC:
				return ERIB;
			case mobile5:
			case mobile6:
			case mobile7:
			case mobile8:
			case mobile9:
				return mApi;
			case WebAPI:
				return webApi;
			case atm:
				return atmApi;
			case ErmbSmsChannel:
			case ErmbAuxChannel:
			case ErmbTransportChannel:
			case ErmbOSS:
			case ERMBListMigrator:
			case CsaErmbListener:
				return ERMB;
			case CSAFront:
			case CSA:
				return csa;
			default:
				throw new IllegalArgumentException("Ќеизвестный тип приложени€ дл€ канала " + application.name());
		}
	}

}
