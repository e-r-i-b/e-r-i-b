package com.rssl.phizic.operations.userprofile;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.resources.external.ShowInMobileProductLink;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.validators.IsOwnLinkValidator;
import org.apache.commons.lang.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * �������� ��� ������� ��������� � ��������� ������
 * @author Pankin
 * @ created 26.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class HideProductMobileOperation extends OperationBase
{
	private static final SimpleService SERVICE = new SimpleService();

	private List<ShowInMobileProductLink> links;

	/**
	 * ������������� ��������
	 * @param products ���� ���������, ������� ���� ������
	 */
	public void initialize(String... products) throws BusinessException, BusinessLogicException
	{
		if (ArrayUtils.isEmpty(products) || !PersonContext.isAvailable())
			return;

		links = new ArrayList<ShowInMobileProductLink>(products.length);
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		IsOwnLinkValidator validator = new IsOwnLinkValidator();
		for (String productCode : products)
		{
			ExternalResourceLink link = personData.getByCode(productCode);
			validator.validate(link);
			links.add((ShowInMobileProductLink) link);
		}
	}

	/**
	 * ������ �������� �� ���������� ����������.
	 */
	public void hide() throws BusinessException
	{
		for (ShowInMobileProductLink link : links)
		{
			link.setShowInMobile(false);
		}
		SERVICE.addOrUpdateList(links);
	}
}
