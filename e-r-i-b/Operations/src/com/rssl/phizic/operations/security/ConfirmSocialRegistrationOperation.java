package com.rssl.phizic.operations.security;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.auth.csa.wsclient.exceptions.FailureIdentificationException;
import com.rssl.auth.csa.wsclient.exceptions.InvalidCodeConfirmException;
import com.rssl.phizic.authgate.AuthParamsContainer;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.login.exceptions.RegistrationErrorException;
import com.rssl.phizic.business.login.exceptions.WrongCodeConfirmException;
import com.rssl.phizic.business.operations.restrictions.MobileDataCompositeRestriction;
import com.rssl.phizic.business.operations.restrictions.MobileMGUIDDataRestriction;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.DocumentConfig;

import java.util.Map;

/**
 * �������� ������������� ����������� ����������� ����������
 *
 * @author sergunin
 * @ created 03.09.14
 * @ $Author$
 * @ $Revision$
 */

public class ConfirmSocialRegistrationOperation extends OperationBase
{

    private AuthParamsContainer container = new AuthParamsContainer();

    /**
     * �������� ������������� ����������� ���������� ����������
     * @param data ������ �����
     * @throws BusinessLogicException
     * @throws BusinessException
     */
    public ConfirmSocialRegistrationOperation(Map<String, Object> data) throws BusinessLogicException, BusinessException
    {
        //������ �������� �����, � �� � �����, ������ ��-�� �������� � ���������
        new MobileDataCompositeRestriction(new MobileMGUIDDataRestriction()).accept(data);

        String mGUID = (String) data.get("mGUID");

        // ������������ �������� �������� mGUID.
        try
        {
            CSABackRequestHelper.sendConfirmOperationRq(mGUID, (String) data.get("smsPassword"));
        }
        catch (InvalidCodeConfirmException e)
        {
            throw new WrongCodeConfirmException(ConfigFactory.getConfig(DocumentConfig.class).getInvalidConfirmCodeRequest(), e.getTime(), e.getAttempts(), e);
        }
        catch (FailureIdentificationException e)
        {
            throw new RegistrationErrorException(e.getMessage(), e);
        }
        catch (BackLogicException e)
        {
            throw new BusinessLogicException(e.getMessage(), e);
        }
        catch (BackException e)
        {
            throw new BusinessException(e);
        }
    }

    /**
     * �������� �������������� ������ ��� ������������� �����������
     * @return AuthParamsContainer
     */
    public AuthParamsContainer getAuthParams()
    {
        return container;
    }
}
