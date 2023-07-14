package com.rssl.auth.csamapi.operations;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.*;
import com.rssl.auth.csa.wsclient.requests.StartCreateMobileSessionRequestData;
import com.rssl.auth.csamapi.exceptions.ResetMobileGUIDException;
import com.rssl.auth.csamapi.operations.restrictions.MobileDataCompositeRestriction;
import com.rssl.auth.csamapi.operations.restrictions.MobileMGUIDDataRestriction;
import com.rssl.auth.csamapi.operations.restrictions.MobileVersionDataRestriction;
import com.rssl.auth.csa.front.exceptions.BlockingRuleException;
import com.rssl.auth.csa.front.exceptions.FrontException;
import com.rssl.auth.csa.front.exceptions.FrontLogicException;
import com.rssl.auth.csa.front.operations.LoginOperationChannelBase;
import com.rssl.phizic.common.types.MobileAppScheme;
import com.rssl.phizic.common.types.csa.AuthorizedZoneType;
import com.rssl.phizic.common.types.mobile.Constants;
import org.w3c.dom.Document;

import java.util.Map;

/**
 * @author osminin
 * @ created 01.08.13
 * @ $Author$
 * @ $Revision$
 *
 * Операция входа пользователя в МАПИ
 */
public class LoginOperation extends LoginOperationChannelBase
{
	private String version;
	private String password;
	private String mobileSDKData;

	protected String deviceId;
	protected String guid;

	protected boolean isLightScheme;

	@Override
	public void initialize(Map<String, Object> data) throws FrontLogicException, FrontException
	{
		super.initialize(data);

		deviceId = (String) data.get(Constants.DEVICE_ID_FIELD);
		guid = (String) data.get(Constants.MGUID_FIELD);
		isLightScheme = Boolean.parseBoolean((String) data.get(Constants.IS_LIGHT_SCHEME_FIELD));
		version = (String) data.get(Constants.VERSION_FIELD);
		password = (String) data.get(Constants.PASSWORD_FIELD);
		mobileSDKData = (String) data.get(Constants.MOBILE_SDK_DATA_FIELD);
	}

	@Override
	protected void checkRestrict(Map<String, Object> data) throws FrontException, FrontLogicException
	{
		new MobileDataCompositeRestriction(new MobileMGUIDDataRestriction(), new MobileVersionDataRestriction()).accept(data);
	}

	@Override
	protected Document doRequest() throws FrontException, FrontLogicException
	{
		return sendStartCreateMobileSessionRequest(AuthorizedZoneType.PRE_AUTHORIZATION);
	}

	protected Document sendStartCreateMobileSessionRequest(AuthorizedZoneType authorizedZoneType) throws FrontException, FrontLogicException
	{
		try
		{
			return CSABackRequestHelper.sendStartCreateMobileSessionRq(new StartCreateMobileSessionRequestData(guid,
																												isLightScheme ? MobileAppScheme.LIGHT.name() : null,
																												deviceId,
																												version,
																												authorizedZoneType,
																												password,
																												mobileSDKData));
		}
		catch (ConnectorBlockedException e)
		{
			throw new FrontLogicException(e.getMessage(), e);
		}
		catch (BlockingRuleActiveException e)
		{
			throw new BlockingRuleException(e.getMessage(), e.getDate(), e);
		}
		catch (RestrictionViolatedException e)
		{
			throw new FrontLogicException(e.getMessage(), e);
		}
		catch (FailureIdentificationException e)
		{
			throw new ResetMobileGUIDException(e);
		}
		catch (WrongMGUIDException e)
		{
			throw new ResetMobileGUIDException(e);
		}
		catch (BackException e)
		{
			throw new FrontException(e);
		}
		catch (BackLogicException e)
		{
			throw new FrontLogicException(e.getMessage(), e);
		}
	}
}
