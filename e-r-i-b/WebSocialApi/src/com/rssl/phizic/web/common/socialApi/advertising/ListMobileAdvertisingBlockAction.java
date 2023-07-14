package com.rssl.phizic.web.common.socialApi.advertising;

import com.rssl.phizic.business.advertising.AdvertisingBlock;
import com.rssl.phizic.business.advertising.AdvertisingButton;
import com.rssl.phizic.operations.advertising.ViewAdvertisingBlockOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Dorzhinov
 * @ created 13.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListMobileAdvertisingBlockAction extends OperationalActionBase
{
    protected Map<String, String> getKeyMethodMap()
    {
        return new HashMap<String, String>();
    }

    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        ViewAdvertisingBlockOperation operation = createOperation(ViewAdvertisingBlockOperation.class);
        ListMobileAdvertisingBlockForm frm = (ListMobileAdvertisingBlockForm) form;

        List<AdvertisingBlock> apiBannersList = operation.getApiBannersList();
        frm.setListBanners(apiBannersList);

        Map<Long, Boolean> mapButtonsEmpty = new HashMap<Long, Boolean>();
        Map<Long, Boolean> mapAreasEmpty = new HashMap<Long, Boolean>();
        for(AdvertisingBlock banner : apiBannersList)
        {
            /* ������������� ������� ����, ��� �� ���� ������ �� ������������� ��������� �����������.
            ������ �� ������������, ���� ����������� ���� �� ���� �� �������:
            1. show == false;
            2. �� ������ �� ��������, �� ����������� ������. */
            boolean buttonsEmpty = true;
            for(AdvertisingButton button : banner.getButtons())
            {
                if(button.getShow() && (button.getTitle() != null || button.getImage() != null))
                {
                    buttonsEmpty = false;
                    break;
                }
            }
            mapButtonsEmpty.put(banner.getId(), buttonsEmpty);

            //������������� ������� ����, ��� �� ���� ���� �� ������������� ��������� �����������.
            boolean areasEmpty = StringHelper.isEmpty(banner.getTitle()) //���������
                    && StringHelper.isEmpty(banner.getText()) //�����
                    && banner.getImage() == null //�����������
                    && buttonsEmpty; //������
            mapAreasEmpty.put(banner.getId(), areasEmpty);
        }
        frm.setMapAreasEmpty(mapAreasEmpty);
        frm.setMapButtonsEmpty(mapButtonsEmpty);

        return mapping.findForward(FORWARD_SHOW);
    }
}
