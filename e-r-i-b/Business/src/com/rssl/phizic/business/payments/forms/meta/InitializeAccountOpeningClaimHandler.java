package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.deposits.DepositProduct;
import com.rssl.phizic.business.deposits.DepositProductHelper;
import com.rssl.phizic.business.deposits.DepositProductService;
import com.rssl.phizic.business.documents.AccountOpeningClaim;
import com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositProductEntity;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.DepositConfig;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

/**
 * «апрещаем открывать форму редактировани€, если вид вклада запрещен или не найден.
 * ќпредел€ем тип открываемого вклада: с первоначальным взносом или без.
 * @author Pankin
 * @ created 01.06.2011
 * @ $Author$
 * @ $Revision$
 */

public class InitializeAccountOpeningClaimHandler extends BusinessDocumentHandlerBase
{
	private static final DepositProductService service = new DepositProductService();
	private static final String ILLEGAL_DEPOSIT_TYPE_MESSAGE = "¬ы не можете открыть этот вклад.";
	private static final String INITIAL_FEE_PATH = "/product/data/main/initialFee";
	private static final String MINIMUM_BALANCE_PATH = "/product/data/main/minimumBalance";

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof AccountOpeningClaim))
		{
			throw new DocumentException("Ќеверный тип платежа id=" + document.getId() +
					" (ќжидаетс€ AccountOpeningClaim)");
		}

		AccountOpeningClaim accountOpeningClaim = (AccountOpeningClaim) document;
		Long accountType = accountOpeningClaim.getAccountType();

		boolean isUseCasNsiDictionaries = ConfigFactory.getConfig(DepositConfig.class).isUseCasNsiDictionaries();
		try
		{
			if (isUseCasNsiDictionaries)
			{
				String accountGroup = accountOpeningClaim.getAccountGroup();
				Long depositProductGroup = StringHelper.isEmpty(accountGroup) ? 0L : Long.valueOf(accountGroup);
				depositProductGroup = -22L == depositProductGroup ? 0L : depositProductGroup;
				DepositProductEntity depositEntity = service.findDepositEntity(accountType, depositProductGroup);
				if (depositEntity == null)
					throw new DocumentLogicException(ILLEGAL_DEPOSIT_TYPE_MESSAGE);

				accountOpeningClaim.setNeedInitialFee(depositEntity.getInitialFee());
				accountOpeningClaim.setWithMinimumBalance(depositEntity.getWithMinimumBalance());
				accountOpeningClaim.setPension(PersonHelper.isPensioner());
			}
			else
			{
				DepositProduct depositProduct = service.findByProductId(accountType);

				if (depositProduct == null || !DepositProductHelper.isAvailableOnline(depositProduct, accountOpeningClaim.getDepartment()))
					throw new DocumentLogicException(ILLEGAL_DEPOSIT_TYPE_MESSAGE);

				Element description = XmlHelper.parse(depositProduct.getDescription()).getDocumentElement();

				accountOpeningClaim.setNeedInitialFee(getDepositTypeProperty(INITIAL_FEE_PATH, description));
				accountOpeningClaim.setWithMinimumBalance(getDepositTypeProperty(MINIMUM_BALANCE_PATH, description));
				accountOpeningClaim.setPension(PersonHelper.isPensioner());
			}
		}
		catch (Exception e)
		{
			throw new DocumentException(e);
		}
	}

	private boolean getDepositTypeProperty(String propertyPath, Element description) throws DocumentException
	{
		try
		{
			String propertyValue = XmlHelper.getElementValueByPath(description, propertyPath);
			return Boolean.parseBoolean(propertyValue);
		}
		catch (Exception e)
		{
			throw new DocumentException(e);
		}
	}
}
