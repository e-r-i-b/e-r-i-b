package com.rssl.phizic.business.documents.payments.validators;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.payments.BusinessDocument;

import java.util.Arrays;
import java.util.List;

/**
 * ��������� ����������� ����������� ������� ������ �� ����� ���������
 *
 * @author khudyakov
 * @ created 09.02.14
 * @ $Author$
 * @ $Revision$
 */
public class CheckPermissionTemplateCreationValidator implements DocumentValidator
{
	private static final List<String> permittedStates = Arrays.asList("EXECUTED", "DISPATCHED", "DELAYED_DISPATCH", "WAIT_CONFIRM");

	public void validate(BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		String code = document.getState().getCode();
		if (!permittedStates.contains(code))
		{
			throw new BusinessLogicException("�� �� ������ ������� ������ �� ������� � ������� �" + DocumentHelper.getStateDescription(code) + "�.");
		}

		if (!DocumentHelper.isTemplateSupported(document))
		{
			throw new BusinessLogicException("�������� ������� �� ���������� ���� ��������� ���������.");
		}
	}
}
