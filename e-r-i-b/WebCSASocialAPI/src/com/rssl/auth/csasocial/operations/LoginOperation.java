package com.rssl.auth.csasocial.operations;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.*;
import com.rssl.auth.csasocial.exceptions.ResetSocialGUIDException;
import com.rssl.auth.csasocial.operations.restrictions.SocialDataCompositeRestriction;
import com.rssl.auth.csasocial.operations.restrictions.SocialMGUIDDataRestriction;
import com.rssl.auth.csa.front.exceptions.BlockingRuleException;
import com.rssl.auth.csa.front.exceptions.FrontException;
import com.rssl.auth.csa.front.exceptions.FrontLogicException;
import com.rssl.auth.csa.front.operations.LoginOperationChannelBase;
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
	private   String  password;
	protected String  clientId;
	protected String  guid;
	protected String  platformId;

	@Override
	public void initialize(Map<String, Object> data) throws FrontLogicException, FrontException
	{
		super.initialize(data);
        platformId = (String) data.get(Constants.APP_TYPE_FIELD);
        clientId = (String) data.get(Constants.CLIENT_ID_FIELD);
		guid     = (String) data.get(Constants.MGUID_FIELD);
		password = (String) data.get(Constants.PASSWORD_FIELD);
	}

	@Override
	protected void checkRestrict(Map<String, Object> data) throws FrontException, FrontLogicException
	{
		new SocialDataCompositeRestriction(new SocialMGUIDDataRestriction()).accept(data);
	}

	@Override
	protected Document doRequest() throws FrontException, FrontLogicException
	{
		return sendStartCreateSocialSessionRequest(AuthorizedZoneType.PRE_AUTHORIZATION);
	}

	protected Document sendStartCreateSocialSessionRequest(AuthorizedZoneType authorizedZoneType) throws FrontException, FrontLogicException
	{
		try
		{
			return CSABackRequestHelper.sendStartCreateSocialSessionRq(guid, null, clientId, authorizedZoneType, password);
		}
		catch (ConnectorBlockedException e)
		{
			throw new FrontLogicException(e.getMessage(), e);
		}
		catch (BlockingRuleActiveException e)
		{
			throw new BlockingRuleException(e.getMessage(), e);
		}
		catch (RestrictionViolatedException e)
		{
			throw new FrontLogicException(e.getMessage(), e);
		}
		catch (FailureIdentificationException e)
		{
			throw new ResetSocialGUIDException(e);
		}
		catch (WrongMGUIDException e)
		{
			throw new ResetSocialGUIDException(e);
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
