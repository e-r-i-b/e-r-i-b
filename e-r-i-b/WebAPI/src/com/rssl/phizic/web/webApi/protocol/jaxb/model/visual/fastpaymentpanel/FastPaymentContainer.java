package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.fastpaymentpanel;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Описывает элемент fastPaymentContainer содержащий основные данные по быстрой панеле оплаты.
 * Xml-представление данного класса имеет вид:
 *
 * <pre>
 * {@code
 *	<fastPaymentContainer>
 *		<customerPhonePayment>
 *			<phoneNumber>string</phoneNumber>
 *			<picture>
 *				<link>string</link>
 *				<url>string</url>
 *			</picture>
 *		</customerPhonePayment>
 *		<supplierContainer> <!--Необязательный элемент-->
 *			<supplier>      <!--Должен повторяться 1 или более раз-->
 *				<id>3</id>
 *				<picture>
 *					<link>string</link>
 *					<url>string</url>
 *				</picture>
 *				<title>string</title>
 *			</supplier>
 *		</supplierContainer>
 *	</fastPaymentContainer>
 * }
 * </pre>
 *
 * @author Balovtsev
 * @since 22.04.14
 */
@XmlType(propOrder = {"customerPhonePayment", "suppliers"})
@XmlRootElement(name = "fastPaymentContainer")
public class FastPaymentContainer
{
	private List<Supplier>       suppliers;
	private CustomerPhonePayment customerPhonePayment;

	/**
	 * @return данные для оплаты телефона клиента
	 */
	@XmlElement(required = true)
	public CustomerPhonePayment getCustomerPhonePayment()
	{
		return customerPhonePayment;
	}

	/**
	 * @return контейнер поставщиков услуг
	 */
	@XmlElementWrapper(name="supplierContainer", required = false)
	@XmlElement(name="supplier", required = true)
	public List<Supplier> getSuppliers()
	{
		return suppliers;
	}

	public void setCustomerPhonePayment(CustomerPhonePayment customerPhonePayment)
	{
		this.customerPhonePayment = customerPhonePayment;
	}

	public void setSuppliers(List<Supplier> suppliers)
	{
		this.suppliers = suppliers;
	}
}
