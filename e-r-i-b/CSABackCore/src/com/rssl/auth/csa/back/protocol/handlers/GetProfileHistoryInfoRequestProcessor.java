package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.CSAUserInfo;
import com.rssl.auth.csa.back.Utils;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.ProfileHistory;
import org.w3c.dom.Document;

import java.util.List;

/**
 * ���������� ������� �� ��������� ������ ��������� ������ ������������ �� ��� ��� �� ��.
 *
 *
 * ��������� �������:
 * firstname               ��� ������������                                            [1]
 * patrname                �������� ������������                                       [1]
 * surname                 ������� ������������                                        [1]
 * birthdate               ���� �������� ������������                                  [1]
 * passport                ��� ������������                                            [1]
 * cbCode                  ������������� ������������                                  [1]
 *
 * ��������� ������:
 * history                      ������� ��������                                          [1]
 *   hisotryItem                ������ ������� �������                                    [0..n]
 *      firstname               ��� ������������                                          [1]
 *      patrname                �������� ������������                                     [1]
 *      surname                 ������� ������������                                      [1]
 *      birthdate               ���� �������� ������������                                [1]
 *      passport                ��� ������������                                          [1]
 *      cbCode                  ������������� ������������                                [1]
 *      expireDate              ���� ����������� ������                                   [0..1]
 *
 * @author bogdanov
 * @ created 25.10.13
 * @ $Author$
 * @ $Revision$
 */
public class GetProfileHistoryInfoRequestProcessor extends RequestProcessorBase
{
	private static final String RESPONSE_TYPE   = "getProfileHistoryInfoRs";
	private static final String REQUEST_TYPE    = "getProfileHistoryInfoRq";

	@Override
	protected String getResponceType()
	{
		return RESPONSE_TYPE;
	}

	@Override
	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	@Override
	public boolean isAccessStandIn()
	{
		return true;
	}

	@Override
	protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		Document document = requestInfo.getBody();
		CSAUserInfo userInfo = fillUserInfo(document.getDocumentElement());

		List<ProfileHistory> history = ProfileHistory.getFullHistoryFor(userInfo);

		ResponseBuilderHelper builder = getSuccessResponseBuilder();
		builder.openTag(Constants.HISTORY_TAG);
		for (ProfileHistory hist: history)
		{
			builder.openTag(Constants.HISTORY_ITEM_TAG)
					.addParameter(Constants.FIRSTNAME_TAG, hist.getFirstname())
					.addParameter(Constants.SURNAME_TAG, hist.getSurname())
					.addParameter(Constants.PATRNAME_TAG, hist.getPatrname())
					.addParameter(Constants.BIRTHDATE_TAG, hist.getBirthdate())
					.addParameter(Constants.PASSPORT_TAG, hist.getPassport())
					.addParameter(Constants.TB_TAG, Utils.getTBByCbCode(hist.getTb()));
			if (hist.getExpireDate() != null)
				builder.addParameter(Constants.EXPIRE_DATE_TAG, hist.getExpireDate());
			builder.closeTag();
		}
		builder.closeTag();

		return builder.end();
	}
}

