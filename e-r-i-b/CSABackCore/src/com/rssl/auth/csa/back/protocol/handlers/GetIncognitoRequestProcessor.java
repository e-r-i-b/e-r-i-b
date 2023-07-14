package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.auth.csa.back.servises.operations.LogIdentificationContext;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author EgorovaA
 * @ created 21.10.13
 * @ $Author$
 * @ $Revision$
 *
 * ���������� ������� �� ��������� ��������� ����������� getIncognitoSettingRq
 *
 * ��������� �������:
 * SID		                ������������� ������      [1]
 * ����
 * UserInfo                     ���������� � ������������                                   [1]
 *      firstname               ��� ������������                                            [1]
 *      patrname                �������� ������������                                       [0..1]
 *      surname                 ������� ������������                                        [1]
 *      birthdate               ���� �������� ������������                                  [1]
 *      passport                ��� ������������                                            [1]
 *      tb                      �� ������������                                             [1]
 */
public class GetIncognitoRequestProcessor extends RequestProcessorBase
{
	public static final String REQUEST_TYPE = "getIncognitoSettingRq";
	public static final String RESPONCE_TYPE = "getIncognitoSettingRs";

	protected String getResponceType()
	{
		return RESPONCE_TYPE;
	}

	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	@Override
	public boolean isAccessStandIn()
	{
		return true;
	}

	protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		final IdentificationContext identificationContext = getIdentificationContextByUserInfoOrSID(requestInfo);
		return getIncognitoParam(identificationContext);
	}

	private ResponseInfo getIncognitoParam(IdentificationContext identificationContext) throws Exception
	{
		Profile profile = identificationContext.getProfile();
		return getSuccessResponseBuilder().addParameter(Constants.INCOGNITO_SETTING_PARAM_NAME, profile.isIncognito()).end();
	}
}
