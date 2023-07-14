package com.rssl.phizic.web.configure;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.Map;

/**
 * ��������� socialAPI � ������� ��������
 * @author sergunin
* @ created 21.08.14
* @ $Author$
* @ $Revision$
*/

public class SocialPlatformConfigShowAction extends MobilePlatformConfigShowAction
{
   @Override protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
   {
       super.fillQuery(query, filterParams);
       query.setParameter("isSocial", true);
   }

    /**
     * ������� ����� ����������.
     * � ����������� �������(�� ����������� ENH00319) ����� ������������ frm.FILTER_FORM
     * @param frm struts-�����
     * @param operation ��������. ��������� �����(�������� ��������) ������������ ��������
     * @return ����� ����������
     */
    @Override protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
    {
        return SocialPlatformConfigShowForm.FILTER_FORM;
    }
}
