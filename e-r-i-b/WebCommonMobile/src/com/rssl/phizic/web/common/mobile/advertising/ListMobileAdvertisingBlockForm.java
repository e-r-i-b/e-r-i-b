package com.rssl.phizic.web.common.mobile.advertising;

import com.rssl.phizic.business.advertising.AdvertisingBlock;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.List;
import java.util.Map;

/**
 * @author Dorzhinov
 * @ created 13.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListMobileAdvertisingBlockForm extends ActionFormBase
{
    private List<AdvertisingBlock> listBanners;
    private Map<Long, Boolean> mapButtonsEmpty; // арта <id рекламного блока, ѕризнак того, что ни одна кнопка не удовлетвор€ет критери€м отображени€>
    private Map<Long, Boolean> mapAreasEmpty; // арта <id рекламного блока, ѕризнак того, что ни одна зона не удовлетвор€ет критери€м отображени€>

    public List<AdvertisingBlock> getListBanners()
    {
        return listBanners;
    }

    public void setListBanners(List<AdvertisingBlock> listBanners)
    {
        this.listBanners = listBanners;
    }

    public Map<Long, Boolean> getMapButtonsEmpty()
    {
        return mapButtonsEmpty;
    }

    public void setMapButtonsEmpty(Map<Long, Boolean> mapButtonsEmpty)
    {
        this.mapButtonsEmpty = mapButtonsEmpty;
    }

    public Map<Long, Boolean> getMapAreasEmpty()
    {
        return mapAreasEmpty;
    }

    public void setMapAreasEmpty(Map<Long, Boolean> mapAreasEmpty)
    {
        this.mapAreasEmpty = mapAreasEmpty;
    }
}
