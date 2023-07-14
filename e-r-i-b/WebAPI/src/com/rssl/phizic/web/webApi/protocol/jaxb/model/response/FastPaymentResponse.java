package com.rssl.phizic.web.webApi.protocol.jaxb.model.response;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.fastpaymentpanel.FastPaymentContainer;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * ��������� ����� �� ������ ������ ������ ������� ������
 * Xml-������������� ������� ������ ����� ���:
 *
 * <pre>
 * {@code
	<response>
		<status>
			<code>3</code>
			<errors>                              <!--�������������� �������-->
				<error>                           <!--������ ����������� 1 ��� ����� ���-->
					<elementId>string</elementId> <!--�������������� �������-->
					<text>string</text>
				</error>
			</errors>
			<warnings>                            <!--�������������� �������-->
				<warning>                         <!--������ ����������� 1 ��� ����� ���-->
					<elementId>string</elementId> <!--�������������� �������-->
					<text>string</text>
				</warning>
			</warnings>
		</status>

		<fastPaymentContainer>                    <!--�������������� �������-->
			<customerPhonePayment>
				<phoneNumber>string</phoneNumber>
				<picture>
					<link>string</link>
					<url>string</url>
				</picture>
			</customerPhonePayment>

			<supplierContainer>                   <!--�������������� �������-->
				<supplier>                        <!--������ ����������� 1 ��� ����� ���-->
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
