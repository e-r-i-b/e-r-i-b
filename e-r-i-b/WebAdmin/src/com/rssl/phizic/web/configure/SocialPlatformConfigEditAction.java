package com.rssl.phizic.web.configure;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.config.MobilePlatformEditOperation;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * ��������� socialAPI � ������� ��������: ��������������
 * @author sergunin
 * @ created 21.08.14
 * @ $Author$
 * @ $Revision$
 */

public class SocialPlatformConfigEditAction extends MobilePlatformConfigEditAction
{
    /**
     * ������� � ������������������� �������� (�������� ��������������).
     * @param frm �����
     * @return ��������� ��������.
     */
    @Override protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
    {
        SocialPlatformConfigEditForm form = (SocialPlatformConfigEditForm) frm;
        MobilePlatformEditOperation operation = createOperation(MobilePlatformEditOperation.class);
        return initializeOperation(form, operation);
    }

    /**
     * ������� ����� ��������������.
     * � ����������� �������(�� ����������� ENH00319) ����� ������������ frm.EDIT_FORM
     * @param frm struts-�����
     * @return ����� ��������������
     */
    @Override protected Form getEditForm(EditFormBase frm)
    {
        return SocialPlatformConfigEditForm.EDIT_FORM;
    }
}
