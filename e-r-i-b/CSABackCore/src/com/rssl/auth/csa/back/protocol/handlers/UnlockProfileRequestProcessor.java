package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.protocol.LogableResponseInfo;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.servises.ActiveRecord;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.auth.csa.back.servises.ProfileLockCHG071536;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.auth.csa.back.servises.operations.LogIdentificationContext;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import org.hibernate.Session;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author krenev
 * @ created 12.10.2012
 * @ $Author$
 * @ $Revision$
 * ���������� ������� �� ������������� �������.
 *
 * �������� �������:
 * UserInfo                     ���������� � ������������                                   [1]
 *      firstname               ��� ������������                                            [1]
 *      patrname                �������� ������������                                       [0..1]
 *      surname                 ������� ������������                                        [1]
 *      birthdate               ���� �������� ������������                                  [1]
 *      passport                ��� ������������                                            [1]
 *      tb                      �� ������������                                             [1]
 *
 * ��������� ������:
 *
 */
public class UnlockProfileRequestProcessor extends LogableProcessorBase
{
	private static final String REQUEST_TYPE = "unlockProfileRq";
	private static final String RESPONCE_TYPE = "unlockProfileRs";

	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	protected String getResponceType()
	{
		return RESPONCE_TYPE;
	}

	@Override
	public boolean isAccessStandIn()
	{
		return true;
	}

	@Override
	protected LogableResponseInfo processRequest(RequestInfo requestInfo, IdentificationContext context) throws Exception
	{
		return unblockProfile(context.getProfile());
	}

	@Override
	protected LogIdentificationContext getIdentificationContext(RequestInfo requestInfo) throws Exception
	{
		Document body = requestInfo.getBody();
		Element element = body.getDocumentElement();
		return LogIdentificationContext.createByTemplateProfile(fillProfileTemplate(element));
	}

	private LogableResponseInfo unblockProfile(final Profile profile) throws Exception
	{
		return ActiveRecord.executeAtomic(new HibernateAction<LogableResponseInfo>()
		{
			public LogableResponseInfo run(Session session) throws Exception
			{
				ProfileLockCHG071536.unlock(profile);
				profile.unlock();
				Connector.unlockAll(profile);
				return new LogableResponseInfo(buildSuccessResponse());
			}
		});
	}
}