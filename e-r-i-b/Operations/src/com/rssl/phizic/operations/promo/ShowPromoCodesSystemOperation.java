package com.rssl.phizic.operations.promo;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.clientPromoCodes.ClientPromoCode;
import com.rssl.phizic.business.clientPromoCodes.ClientPromoCodeService;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Опреация просмотра промокодов
 *
 * @author sergunin
 * @ created 16.12.14
 * @ $Author$
 * @ $Revision$
 */

public class ShowPromoCodesSystemOperation extends OperationBase implements ListEntitiesOperation
{
    protected static final ClientPromoCodeService clientPromoCodesService = new ClientPromoCodeService();
    private List<ClientPromoCode> activeClientPromoCode;
    private List<ClientPromoCode> archiveClientPromoCode;

    /**
     * @param loginId id клиента
     * @throws BusinessException
     */
    public void initializeShow(Long loginId) throws BusinessException
    {
        List<ClientPromoCode> clientPromoCodes = clientPromoCodesService.getAllClientPromoCodes(loginId);
        activeClientPromoCode = new ArrayList<ClientPromoCode>();
        archiveClientPromoCode = new ArrayList<ClientPromoCode>();

        if (clientPromoCodes == null)
            return;

        for (ClientPromoCode promo : clientPromoCodes)
        {
            clientPromoCodesService.updateClientPromoCodeActivity(promo);
            if (promo.getActive())
                activeClientPromoCode.add(promo);
            else
                archiveClientPromoCode.add(promo);
        }

        sortActivePromoListByDate(activeClientPromoCode);
        sortArchivePromoListByDate(archiveClientPromoCode);

    }

    private void sortArchivePromoListByDate(List<ClientPromoCode> clientPromoCodeList)
    {
        Collections.sort(clientPromoCodeList, new Comparator<ClientPromoCode>()
        {
            public int compare(ClientPromoCode o1, ClientPromoCode o2)
            {
                return o2.getEndDate().compareTo(o1.getEndDate());
            }
        });
    }

    private void sortActivePromoListByDate(List<ClientPromoCode> clientPromoCodeList)
    {
        Collections.sort(clientPromoCodeList, new Comparator<ClientPromoCode>()
        {
            public int compare(ClientPromoCode o1, ClientPromoCode o2)
            {
                return o2.getInputDate().compareTo(o1.getInputDate());
            }
        });
    }

    public List<ClientPromoCode> getActiveClientPromoCode()
    {
        return activeClientPromoCode;
    }

    public List<ClientPromoCode> getArchiveClientPromoCode()
    {
        return archiveClientPromoCode;
    }
}
