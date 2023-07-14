package com.rssl.phizic.operations.promo;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.clientPromoCodes.ClientPromoCode;
import com.rssl.phizic.business.clientPromoCodes.ClientPromoCodeService;
import com.rssl.phizic.business.clientPromoCodes.CloseReason;
import com.rssl.phizic.context.PersonContext;

/**
 * �������� ��������� ����������
 *
 * @author sergunin
 * @ created 16.12.14
 * @ $Author$
 * @ $Revision$
 */

public class RemovePromoCodesSystemOperation extends ShowPromoCodesSystemOperation
{
    private ClientPromoCode clientPromoCode;

    public void initializeRemove(Long promoId) throws BusinessException, BusinessLogicException
    {
        if(promoId == null)
            throw new BusinessLogicException("ID ����� ���� ������� �� ����� ���� ������");
        clientPromoCode = clientPromoCodesService.findById(ClientPromoCode.class, promoId);
        if(clientPromoCode == null)
            throw new BusinessLogicException("����� ��� ������� �� ����� ���� ������");
        if (!PersonContext.getPersonDataProvider().getPersonData().getLogin().getId().equals(clientPromoCode.getLoginId()))
            throw new BusinessException("����� ��� �� ����������� �������.");
    }

    public void save() throws BusinessException
    {
        clientPromoCode.setActive(false);
        clientPromoCode.setCloseReason(CloseReason.DELETED_BY_CLIENT);
        clientPromoCodesService.update(clientPromoCode);
        ClientPromoCodeService.clearCache();
    }

}

