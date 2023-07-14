package com.rssl.auth.csa.back.protocol.handlers.guest;

import com.rssl.auth.csa.back.Utils;
import com.rssl.auth.csa.back.exceptions.IdentificationFailedException;
import com.rssl.auth.csa.back.log.CSAActionLogHelper;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.protocol.handlers.RequestProcessorBase;
import com.rssl.auth.csa.back.servises.GuestOperation;
import com.rssl.auth.csa.back.servises.GuestProfile;
import com.rssl.auth.csa.back.servises.nodes.CreateProfileNodeMode;
import com.rssl.auth.csa.back.servises.nodes.Node;
import com.rssl.auth.csa.back.servises.operations.LogProfileIdentificationContext;
import com.rssl.auth.csa.back.servises.operations.SimpleLogIdentificationContext;
import com.rssl.auth.csa.back.servises.operations.guest.GuestLogonOperation;
import com.rssl.auth.csa.back.servises.operations.guest.GuestPhoneAuthenticationOperation;
import com.rssl.phizic.common.types.csa.IdentificationType;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

/**
 * @author niculichev
 * @ created 08.01.15
 * @ $Author$
 * @ $Revision$
 */
public class FinishCreateGuestSessionRequestProcessor extends RequestProcessorBase
{
	public static final String REQUEST_TYPE = "finishCreateGuestSessionRq";
	public static final String RESPONCE_TYPE = "finishCreateGuestSessionRs";

	@Override
	protected String getResponceType()
	{
		return RESPONCE_TYPE;
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
		Document body = requestInfo.getBody();
		String ouid = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.OUID_TAG);

		LogProfileIdentificationContext context = new SimpleLogIdentificationContext(IdentificationType.OUID, ouid);
		try
		{
			GuestLogonOperation operation =
					GuestOperation.findLifeByOUID(GuestLogonOperation.class, ouid, GuestOperation.getOperationLifeTime());

			if(operation == null)
				throw new IdentificationFailedException("Не найдена заявка с ouid = " + ouid);

			GuestProfile profile = operation.execute();

			ResponseInfo result = getSuccessResponseBuilder()
					.addGuestInfo(profile, operation)
					.end()
					.getResponceInfo();

			CSAActionLogHelper.writeToGuestActionLog(requestInfo, context);

			return result;
		}
		catch (Exception e)
		{
			CSAActionLogHelper.writeToGuestActionLog(requestInfo, context, e);
			throw e;
		}
	}
}
