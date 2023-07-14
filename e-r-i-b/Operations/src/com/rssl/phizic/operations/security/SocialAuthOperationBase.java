package com.rssl.phizic.operations.security;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.auth.csa.wsclient.exceptions.BlockingRuleActiveException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.login.exceptions.ResetMobileGUIDException;
import com.rssl.phizic.common.types.csa.AuthorizedZoneType;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

/**
 * Базовый класс
 *
 * @author sergunin
 * @ created 03.09.14
 * @ $Author$
 * @ $Revision$
 */
public abstract class SocialAuthOperationBase extends OperationBase
{
	protected static final String NOT_SUPPORTED_PLATFORM_ERROR = "Вы не можете войти в социальное приложение. Пожалуйста, обратитесь в Контактный Центр по телефону 8 800 555 5550.";

    protected Document createSocialSession(String mguid, String appScheme, String deviceId, AuthorizedZoneType authorizedZoneType) throws BusinessException, BusinessLogicException
    {
        try
        {
            Document startResponse = CSABackRequestHelper.sendStartCreateSocialSessionRq(mguid, appScheme, deviceId, authorizedZoneType, null);
            String authToken = XmlHelper.getSimpleElementValue(startResponse.getDocumentElement(), "OUID");

            return CSABackRequestHelper.sendFinishCreateMobileSessionRq(authToken);
        }
        catch (BackException e)
        {
            throw new BusinessException(e);
        }
        catch (BlockingRuleActiveException e)
        {
            throw new BusinessLogicException(e.getMessage(), e);
        }
        catch (BackLogicException e)
        {
            throw new ResetMobileGUIDException(e.getMessage(), e);
        }
    }
}
