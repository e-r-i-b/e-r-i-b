package com.rssl.phizic.web.webApi.protocol.jaxb.model.response;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.fastpaymentpanel.FastPaymentContainer;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Формирует ответ на запрос данных панели быстрой оплаты
 * Xml-представление данного класса имеет вид:
 *
 * <pre>
 * {@code
	<response>
		<status>
			<code>3</code>
			<errors>                              <!--Необязательный элемент-->
				<error>                           <!--Должен повторяться 1 или более раз-->
					<elementId>string</elementId> <!--Необязательный элемент-->
					<text>string</text>
				</error>
			</errors>
			<warnings>                            <!--Необязательный элемент-->
				<warning>                         <!--Должен повторяться 1 или более раз-->
					<elementId>string</elementId> <!--Необязательный элемент-->
					<text>string</text>
				</warning>
			</warnings>
		</status>

		<fastPaymentContainer>                    <!--Необязательный элемент-->
			<customerPhonePayment>
				<phoneNumber>string</phoneNumber>
				<picture>
					<link>string</link>
					<url>string</url>
				</picture>
			</customerPhonePayment>

			<supplierContainer>                   <!--Необязательный элемент-->
				<supplier>                        <!--Должен повторяться 1 или более раз-->
					<id>3</id>
					<picture>
						<link>string</link>
						<url>string</url>
					</picture>
					<title>string</title>
				</supplier>
			</supplierContainer>
		</fastPaymentContainer>
	</response>
 * }
 * </pre>
 *
 * @author Balovtsev
 * @since 23.04.14
 */
@XmlType(propOrder = {"status", "fastPaymentContainer"})
@XmlRootElement(name = "message")
public class FastPaymentResponse extends Response
{
	private FastPaymentContainer fastPaymentContainer;

	@XmlElement(name = "fastPaymentContainer", required = false)
	public FastPaymentContainer getFastPaymentContainer()
	{
		return fastPaymentContainer;
	}

	public void setFastPaymentContainer(FastPaymentContainer fastPaymentContainer)
	{
		this.fastPaymentContainer = fastPaymentContainer;
	}
}
