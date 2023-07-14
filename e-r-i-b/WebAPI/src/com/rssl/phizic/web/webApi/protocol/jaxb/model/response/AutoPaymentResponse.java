package com.rssl.phizic.web.webApi.protocol.jaxb.model.response;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.Link;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * ��������� ����� �� ������ ������������ ��� ����������� � ������ ����
 * Xml-������������� ������� ������ ����� ���:
 *
 * <pre>
 * {@code
	<response>
		<status>
			<code>3</code>
			<errors>                                <!--�������������� �������-->
				<error>                             <!--������ ����������� 1 ��� ����� ���-->
					<elementId>string</elementId>   <!--�������������� �������-->
					<text>string</text>
				</error>
			</errors>
			<warnings>                              <!--�������������� �������-->
				<warning>                           <!--������ ����������� 1 ��� ����� ���-->
					<elementId>string</elementId>   <!--�������������� �������-->
					<text>string</text>
				</warning>
			</warnings>
		</status>

		<commandsContainer>
			<link>                                  <!--������ ����������� 1 ��� ����� ���-->
				<title>string</title>
				<url>string</url>
			</link>
		</commandsContainer>

		<linksContainer>                            <!--�������������� ������� � ������ ���������� � ������� ������������-->
			<link>                                  <!--������ ����������� 1 ��� ����� ���-->
				<title>string</title>
				<url>string</url>
			</link>
		</linksContainer>
	</response>
 * }
 * </pre>
 *
 * @author Balovtsev
 * @since 23.04.14
 */
@XmlType(propOrder = {"status", "links", "commands"})
@XmlRootElement(name = "message")
public class AutoPaymentResponse extends Response
{
	private List<Link> links;
	private List<Link> commands;

	/**
	 * @return ��������� ������ ���������� ������ ������������
	 */
	@XmlElementWrapper(name = "linksContainer", required = false)
	@XmlElement(name = "link", required = true)
	public List<Link> getLinks()
	{
		return links;
	}

	/**
	 * @return ��������� ������
	 */
	@XmlElementWrapper(name = "commandsContainer", required = true)
	@XmlElement(name = "link", required = true)
	public List<Link> getCommands()
	{
		return commands;
	}

	public void setLinks(List<Link> links)
	{
		this.links = links;
	}

	public void setCommands(List<Link> commands)
	{
		this.commands = commands;
	}
}
