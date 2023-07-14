package com.rssl.phizic.business.exception;

import com.rssl.phizic.common.types.Application;

/**
 * @author mihaylov
 * @ created 02.07.14
 * @ $Author$
 * @ $Revision$
 *
 * ��� ���������� � ������� �������� ������
 */
public enum ExceptionEntryApplication
{
	PhizIC,
	PhizIA,
	mApi,
	atmApi,
	webApi,
	socialApi,
	ERMB;

	/**
	 * ��������� ���� ���������� � ������� �������� ������ �� ������ ���� ���������� �������
	 * @param application ��� ���������� �������
	 * @return ��� ���������� � ������� �������� ������
	 */
	public static ExceptionEntryApplication fromApplication(Application application)
	{
		switch (application)
		{
			case PhizIA : return PhizIA;
			case PhizIC : return PhizIC;
			case mobile5:
			case mobile6:
			case mobile7:
			case mobile8:
			case mobile9: return mApi;
			case WebAPI: return webApi;
			case socialApi: return socialApi;
			case atm:
			case WebATM : return atmApi;
			case ErmbSmsChannel         :
			case ErmbAuxChannel         :
			case ErmbTransportChannel   :
			case ErmbOSS                :
			case ERMBListMigrator       :
			case CsaErmbListener        : return ERMB;
			default: throw new IllegalArgumentException("����������� ��� ���������� ��� �������� ������");
		}
	}
}
