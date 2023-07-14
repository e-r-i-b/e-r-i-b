package com.rssl.phizic.web.common.client.ext.sbrf.deposits;

import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.xslt.XmlConverter;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.clientPromoCodes.ClientPromoCode;
import com.rssl.phizic.business.clientPromoCodes.ClientPromoCodeService;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositProductEntity;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.account.DepositGroupOrderConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.deposits.GetDepositProductsListOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.common.client.deposits.ListDepositProductsAction;
import com.rssl.phizic.web.common.client.deposits.ListDepositProductsForm;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Source;

/**
 @author Pankin
 @ created 09.02.2011
 @ $Author$
 @ $Revision$
 */
public class ListDepositProductsExtendedAction extends ListDepositProductsAction
{
	private static final ClientPromoCodeService CLIENT_PROMO_CODE_SERVICE = new ClientPromoCodeService();

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(GetDepositProductsListOperation.class, ((ListDepositProductsForm) frm).getForm());
	}

	protected String getHtml(ListEntitiesOperation operation, XmlConverter converter, ListDepositProductsForm form) throws BusinessException
	{
		GetDepositProductsListOperation op = (GetDepositProductsListOperation) operation;

		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		Department personTB = personData.getTb();

		Source source = op.getListSource(true, personTB == null ? "" : personTB.getRegion());
		if (source == null)
			return null;

		converter.setData(source);
		DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
		converter.setParameter("curDate", df.format((new GregorianCalendar()).getTime()));
		converter.setParameter("isPension", PersonHelper.isPensioner());
		converter.setParameter("needInitialFee", form.getForm().equals(
				FormConstants.ACCOUNT_OPENING_CLAIM_FORM) ? "" : false);
		String tariff = personData.getPerson().getTarifPlanCodeType();
		converter.setParameter("tariff", StringHelper.getZeroIfEmptyOrNull(tariff));
		converter.setParameter("sortList", (Serializable) ConfigFactory.getConfig(DepositGroupOrderConfig.class).getDepositGroupOrder());
		setAdditionalParams(converter);
		return converter.convert().toString();
	}

	protected List<DepositProductEntity> getDepositProductEntities(ListEntitiesOperation operation, ListDepositProductsForm form) throws BusinessException
	{
		GetDepositProductsListOperation op = (GetDepositProductsListOperation) operation;

		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		String tariff = personData.getPerson().getTarifPlanCodeType();
		Department personTB = personData.getTb();

		op.initialize(PersonHelper.isPensioner(), Long.valueOf(StringHelper.getZeroIfEmptyOrNull(tariff)));

		boolean showWithInitialFeeOnly =  !form.getForm().equals(FormConstants.ACCOUNT_OPENING_CLAIM_FORM);
		return op.getDepositEntities(personTB.getRegion(), showWithInitialFeeOnly);
	}

	/**
	 * Метод для добавления параметров, которые могут различаться в зависимости от апи
	 * @param converter
	 */
	protected void setAdditionalParams(XmlConverter converter)
	{
		ApplicationConfig config = ApplicationConfig.getIt();
		converter.setParameter("isATM", config.getApplicationInfo().isATM());
	}
}
