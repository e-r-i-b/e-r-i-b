package com.rssl.phizic.operations.widget;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.OperationFactory;
import com.rssl.phizic.business.resources.external.AbstractStoredResource;
import com.rssl.phizic.business.resources.external.IMAccountLink;
import com.rssl.phizic.business.resources.external.StoredResourceMessages;
import com.rssl.phizic.business.web.IMAccountBlockWidget;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.gate.ima.IMAccountAbstract;
import com.rssl.phizic.operations.ima.GetIMAccountAbstractOperation;
import com.rssl.phizic.operations.ima.GetIMAccountOperation;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author lukina
 * @ created 12.12.2012
 * @ $Author$
 * @ $Revision$
 */

public class IMAccountBlockWidgetOperation extends ProductBlockWidgetOperationBase<IMAccountBlockWidget>
{
	private GetIMAccountOperation imAccountsOperation;
	private GetIMAccountAbstractOperation imAccountAbstractOperation;
	private List<IMAccountLink> allAccountLinks;
	private List<IMAccountLink> mainAccountLinks;

	@Override
	protected void initialize() throws BusinessException, BusinessLogicException
	{
		super.initialize();

		OperationFactory factory = getOperationFactory();

		imAccountsOperation = factory.create(GetIMAccountOperation.class);
		allAccountLinks = imAccountsOperation.getAllIMAccounts();
		mainAccountLinks = imAccountsOperation.getShowInMainLinks(allAccountLinks);
		
		imAccountAbstractOperation = factory.create("GetIMAccountShortAbstractOperation");
		List<IMAccountLink> showOperationAccountLinks = imAccountsOperation.getShowOperationLinks(allAccountLinks);
		imAccountAbstractOperation.initialize(showOperationAccountLinks, false);

		setUseStoredResource(imAccountsOperation.isUseStoredResource());

		actualizeHiddenProducts(allAccountLinks);
	}

	public List<IMAccountLink> getIMAccountLinks()
	{
		return Collections.unmodifiableList(allAccountLinks);
	}

	public boolean isAllAccountDown()
	{
		return CollectionUtils.isEmpty(mainAccountLinks) && (imAccountAbstractOperation.isBackError() || imAccountsOperation.isBackError());
	}

	public Map<IMAccountLink, IMAccountAbstract> getIMAccountAbstract(Long count)
	{
		return imAccountAbstractOperation.getIMAccountAbstractMap(count);
	}

	@Override
	public boolean checkUseStoredResource()
	{
		if (super.checkUseStoredResource())
		{
			throw new InactiveExternalSystemException(StoredResourceMessages.getUnreachableMessageSystem((AbstractStoredResource) allAccountLinks.get(0).getImAccount()));
		}

		return false;
	}
}
