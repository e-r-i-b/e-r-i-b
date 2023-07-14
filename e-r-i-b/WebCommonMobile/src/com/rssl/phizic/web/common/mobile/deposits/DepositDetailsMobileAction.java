package com.rssl.phizic.web.common.mobile.deposits;

import com.rssl.common.forms.xslt.XmlConverter;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.deposits.DepositHtmlConverter;
import com.rssl.phizic.business.deposits.DepositProduct;
import com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositProductEntity;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.mobile.MobileApiConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.deposits.DepositDetailsOperation;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.utils.DepositConfig;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.mobile.MobileAPIVersions;
import com.rssl.phizic.utils.xml.StaticResolver;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.client.ext.sbrf.deposits.DepositDetailsExtendedAction;

import java.util.Map;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;

/**
 * @author Pankin
 * @ created 13.01.2012
 * @ $Author$
 * @ $Revision$
 */

public class DepositDetailsMobileAction extends DepositDetailsExtendedAction
{
	protected Source getTemplateSource(DepositDetailsOperation op) throws BusinessException
	{
		return op.getMobileTemplateSource();
	}

	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException
	{
		DepositDetailsMobileForm form =  (DepositDetailsMobileForm)frm;
		DepositDetailsOperation operation = createOperation("DepositDetailsOperation", "AccountOpeningClaim");
		operation.initialize(form.getDepositId());

		return operation;
	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase form)
			throws BusinessException, BusinessLogicException
	{
		DepositDetailsMobileForm frm = (DepositDetailsMobileForm)form;
		DepositDetailsOperation op = (DepositDetailsOperation)operation;

		if (ConfigFactory.getConfig(DepositConfig.class).isUseCasNsiDictionaries())
		{

			Long depositType = frm.getId();

			Long group;
			if (StringHelper.isEmpty(frm.getGroup()))
			{
				Map<String, String> oldCodes = ConfigFactory.getConfig(MobileApiConfig.class).getOldDepositCodesList();
				String oldCode = oldCodes.get(String.valueOf(depositType));
				if (StringHelper.isEmpty(oldCode))
					group = 0L;
				else
					group = Long.parseLong(oldCode);
				if (group < 0)
					group = 0L;
			}
			else
			{
				group = Long.parseLong(frm.getGroup());
			}
			String segment =  StringHelper.getEmptyIfNull(frm.getSegment());

			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			String tariff = StringHelper.getZeroIfEmptyOrNull(personData.getPerson().getTarifPlanCodeType());
			Department personTB = personData.getTb();

			DepositProductEntity depositProductEntity = op.getDepositProductEntity(depositType, group, segment, PersonHelper.isPensioner(), Long.valueOf(tariff), personTB.getRegion());
			frm.setEntity(depositProductEntity);
			frm.setEntityTDog(op.getDepositAdditionalInfo(depositType, group));
			frm.setMinAdditionalFee(op.getMinAdditionalFeeValues(depositType, group));
			return;
		}
		DepositProduct depositProduct = (DepositProduct) operation.getEntity();
		if (depositProduct != null)
		{
				frm.setName(depositProduct.getName());

			try
			{
				Source templateSource = getTemplateSource(op);
				TransformerFactory fact = TransformerFactory.newInstance();
				fact.setURIResolver(new StaticResolver());
				Templates templates = fact.newTemplates(templateSource);
				XmlConverter converter = new DepositHtmlConverter(templates);
				if (MobileApiUtil.isMobileApiGE(MobileAPIVersions.V8_00))
				{
					converter.setParameter("showNewDeposits", true);
					converter.setParameter("group", StringHelper.getEmptyIfNull(frm.getDepositGroup()));
				}
				frm.setHtml( getHtml(op, converter) );
			}
			catch (TransformerConfigurationException ex)
			{
				throw new BusinessException(ex);
			}
		}
	}

}
