package com.rssl.phizic.operations.deposits.retail;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.deposits.*;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.impl.InnerSerializer;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;

/**
 * @author Evgrafov
 * @ created 06.04.2007
 * @ $Author: khudyakov $
 * @ $Revision: 40139 $
 */

public class EditDepositProductOperation extends OperationBase implements EditEntityOperation
{
	private static final DepositProductService depositProductService = new DepositProductService();
	private DepartmentService departmentService = new DepartmentService();

	private DepositProduct product;
	private List<String>   newRetailDeposits;
	private Long           newDepartmentId;

	/**
	 * инициализировать операцию существующим продуктом
	 * @param id id продукта
	 * @throws BusinessException
	 */
	public void initialize(Long id) throws BusinessException
	{
		product = depositProductService.findById(id);

		if(product == null)
			throw new BusinessException("Депозитный продукт не найден ID=" + id);
		newDepartmentId = product.getDepartmentId();
	}

	public void initialize(String name)
	{
		product.setName(name);
	}
	/**
	 * инициализировать операцию новым продуктом
 	 */
	public void ininitializeNew(Long departmentId) throws BusinessException
	{
		String xml = getDefaultDetails();

		product = new DepositProduct();
		product.setDescription("<product></product>");
		product.setDetails(xml);
		product.setName("");
		product.setProductDescription("");
		product.setDepartmentId(departmentId);
		setDepartmentId(departmentId);
	}

	/**
	 * @return депозитный продукт
	 */
	public DepositProduct getEntity()
	{
		return product;
	}

	/**
	 * @return Source для xml представления продукта
	 */
	public Source getProductSource()
	{
		String description = product.getDescription();
		return new StreamSource(new StringReader(description));
	}

	public String getDefaultDetails() throws BusinessException
	{
		DepositGlobal global = depositProductService.getGlobal();
		return global.getDefaultDetailsTransformation();
	}

	public Source getTemplateSource() throws BusinessException
	{
		DepositGlobal global = depositProductService.getGlobal();
		return new StreamSource(new StringReader(global.getAdminEditTransformation()));
	}

	/**
	 * @param ids список идентификаторов видов вкладов из ретейла
	 */
	public void setRetailDeposits(List<String> ids)
	{
		newRetailDeposits = new ArrayList<String>(ids);
	}

	private void updateProduct() throws BusinessException, BusinessLogicException
	{

		if(newRetailDeposits == null)
			throw new BusinessException("Не установлен список видов вкладов");

		//TODO depositProductQ.addParameter("filial", "0");в текущеёй версии в rtxml.mac по умолчанию проставляется нулевой филиал
		Document document = XmlHelper.getDocumentBuilder().newDocument();
        document.appendChild(document.createElement("depositProduct_q"));
		for (String id : newRetailDeposits)
		{
			Element deposit = XmlHelper.appendSimpleElement(document.getDocumentElement(), "deposit");
			XmlHelper.appendCDATAElement(deposit, "accountTypeId", id);
		}
		
		com.rssl.phizic.gate.deposit.DepositProductService depProdService =
				GateSingleton.getFactory().service(com.rssl.phizic.gate.deposit.DepositProductService.class);

		try
		{
			Department department = departmentService.findById(newDepartmentId);
			Document depositProductA = depProdService.getDepositProduct(document, department);
			Element root = depositProductA.getDocumentElement();
			depositProductA.renameNode(root, null, "product");
			root.setAttribute("name", product.getName());
			root.setAttribute("productDescription", product.getProductDescription());
			root.setAttribute("departmentName", department.getName());

			StringWriter writer = new StringWriter();
			InnerSerializer serializer = new InnerSerializer(writer);
			serializer.serialize(depositProductA);

			product.setDescription(writer.getBuffer().toString());
			product.setDepartmentId(newDepartmentId);
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}

	/**
	 * сохранить изменения
	 * @throws BusinessException
	 */
	public void save() throws BusinessException, BusinessLogicException
	{
		updateProduct();

		if(product.getId() == null)
			depositProductService.add(product);
		else
			depositProductService.update(product);
	}

	public void addOrUpdate() throws BusinessException, BusinessLogicException
	{
		updateProduct();

		DepositProduct addProduct = depositProductService.findByNameAndDepartmentId(product.getName(), newDepartmentId);
		if (addProduct == null)
		{
			depositProductService.add(product);
		}
		else
		{
			product.setId(addProduct.getId());
			depositProductService.update(product);
		}
	}

	public Department getDepartmentById(Long departmentId) throws BusinessException
	{
		return departmentService.findById(departmentId);
	}

	public Long getDepartmentId()
	{
		return product.getDepartmentId();
	}

	public void setDepartmentId(Long departmentId)
	{
		newDepartmentId = departmentId;
	}

	public void replice(Long departmentId, List<String> deposit) throws GateLogicException, GateException, BusinessException, TransformerException, BusinessLogicException
	{
		com.rssl.phizic.gate.deposit.DepositProductService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.deposit.DepositProductService.class);
		Document document = service.getDepositsInfo(getDepartmentById(departmentId));
		NodeList nodeList = XmlHelper.selectNodeList(document.getDocumentElement(),"deposit/accountTypeId");

		for (int i=0; i<nodeList.getLength(); i++)
		{
			String depositName = nodeList.item(i).getTextContent();
			ininitializeNew(departmentId);
			deposit.add(depositName);
			initialize(depositName);
			setRetailDeposits(deposit);
			setDepartmentId(departmentId);
			addOrUpdate();
		}
	}
}
