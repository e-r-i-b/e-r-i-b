package com.rssl.phizic.operations.deposits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.deposits.*;
import com.rssl.phizic.business.deposits.generated.DepositDictionaryElement;
import com.rssl.phizic.business.deposits.generated.DepositDictionaryProduct;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.util.AllowedDepartmentsUtil;
import com.rssl.phizic.business.xslt.lists.cache.event.XmlDictionaryCacheClearEvent;
import com.rssl.phizic.events.EventSender;
import com.rssl.phizic.operations.dictionaries.synchronization.EditDictionaryEntityOperationBase;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.utils.xml.jaxb.JAXBUtils;
import org.apache.commons.collections.CollectionUtils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;

/**
 * @author filimonova
 * @ created 16.03.2011
 * @ $Author$                                                
 * @ $Revision$
 * –едактирование информации о депозитном продукте
 */
public class EditDepositDetailsOperation extends EditDictionaryEntityOperationBase
{
	private static final DepositProductService depositProductService = new DepositProductService();
	private DepositProduct deposit;

	public void initialize(Long productId) throws BusinessException
	{
		deposit = depositProductService.findByProductId(productId,getInstanceName());
	}

	public Source getTemplateSource() throws BusinessException
	{
		DepositGlobal global = depositProductService.getGlobal(getInstanceName());
		return new StreamSource(new StringReader(global.getDefaultDetailsTransformation()));
	}

	public Source getVisibilityTemplateSource() throws BusinessException
	{
		DepositGlobal global = depositProductService.getGlobal(getInstanceName());
		return new StreamSource(new StringReader(global.getVisibilityDetailsTransformation()));
	}

	public void doSave() throws BusinessException, DublicateDepositProductNameException
	{
		depositProductService.update(deposit,getInstanceName());
		try
		{
			EventSender.getInstance().sendEvent(new XmlDictionaryCacheClearEvent(deposit.getId(), DepositProduct.class));
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public DepositProduct getEntity()
	{
		return deposit;
	}

	/**
	 * –азрешить дл€ открыти€ онлайн все виды вкладов
	 */
	public void allowAll() throws BusinessException
	{
		List<DepositProduct> depositProducts = depositProductService.getNotAvailableOnlineProducts(getInstanceName());
		for (DepositProduct depositProduct : depositProducts)
		{
			depositProduct.setAvailableOnline(true);
			depositProductService.update(depositProduct, getInstanceName());
		}

		MultiBlockModeDictionaryHelper.updateDictionary(DepositProduct.class);
	}

	/**
	 * ѕолучить подразделени€, в которых разрешено открытие вклада
	 * @param tbs идентификаторы подразделений, в которых разрешено открытие вклада
	 * @return список подразделений
	 * @throws BusinessException
	 */
	public List<String> getProductAllowedTBList(String[] tbs) throws BusinessException
	{
		List<String> chosedDepartments = new ArrayList<String>();
		for (String tb : tbs)
		{
			if(AllowedDepartmentsUtil.isDepartmentsAllowedByCode(tb, null, null))
				chosedDepartments.add(tb);
		}

		List<String> oldAllowedDepartments = deposit.getAllowedDepartments();
		if (!CollectionUtils.isEmpty(oldAllowedDepartments))
		{
			// „тобы не потер€ть настройку дл€ тех подразделений, к которым нет доступа у сотрудника,
			// провер€ем старый набор доступных “Ѕ
			List<String> notEmployeeAllowedTBs = new ArrayList<String>(oldAllowedDepartments);
			List<String> employeeAllowedTBs = AllowedDepartmentsUtil.getAllowedTerbanksNumbers();

			notEmployeeAllowedTBs.removeAll(employeeAllowedTBs);

			if (!CollectionUtils.isEmpty(chosedDepartments))
			{
				// »з доступных сотруднику подразделений добавл€ем те, которые он выбрал
				notEmployeeAllowedTBs.addAll(chosedDepartments);
			}
			return notEmployeeAllowedTBs;
		}

		return chosedDepartments;
	}

	/**
	 * ќбновить в описании вклада признак доступности подвидов вклада дл€ открыти€ онлайн
	 * @param depositSubTypeIds - массив идентификаторов подвидов вкладов, доступных дл€ открыти€ онлайн
	 * @return строка с описанием вклада
	 * @throws BusinessException
	 */
	public String updateDepositSubTypeParams(String[] depositSubTypeIds) throws BusinessException
	{
		try
		{
			List<String> checkedSubTypes = Arrays.asList(depositSubTypeIds);
			DepositDictionaryProduct product = JAXBUtils.unmarshalBean(DepositDictionaryProduct.class, deposit.getDescription());
			List<DepositDictionaryElement> options = product.getData().getOptions().getElement();
			for (DepositDictionaryElement element : options)
			{
				if (checkedSubTypes.contains(element.getId().toString()))
					element.setAvailToOpen(true);
				else
					element.setAvailToOpen(false);
			}

			List<String> wrapCdataFields = new ArrayList<String>();
			wrapCdataFields.add("text");
			String description = JAXBUtils.marshalBeanWithCDATA(product, wrapCdataFields);

			Document depositDocument = XmlHelper.parse(description);
			DepositProductHelper.updateProductName(depositDocument.getDocumentElement());
			String depositDescription = XmlHelper.convertDomToText(depositDocument, "windows-1251");
			return depositDescription;
		}
		catch (JAXBException e)
		{
			throw new BusinessException(e);
		}
		catch (ParserConfigurationException e)
		{
			throw new BusinessException(e);
		}
		catch (TransformerException e)
		{
			throw new BusinessException(e);
		}
		catch (SAXException e)
		{
			throw new BusinessException(e);
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
	}

	@Override
	protected String getInstanceName()
	{
		return MultiBlockModeDictionaryHelper.getDBInstanceName();
	}
}
