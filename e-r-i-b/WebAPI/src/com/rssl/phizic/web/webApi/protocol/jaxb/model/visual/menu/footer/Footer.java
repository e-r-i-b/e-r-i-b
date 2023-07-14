package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.footer;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.header.Helpdesc;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Balovtsev
 * @since 30.04.2014
 */
@XmlType(propOrder = {"network", "aboutBank", "helpdesc", "help", "phones"})
@XmlRootElement(name = "footer")
public class Footer
{
	private List<String>  phones;
	private List<Network> network;
	private AboutBank     aboutBank;
	private Helpdesc      helpdesc;
	private FooterHelp    help;

	/**
	 */
	public Footer() {}

	/**
	 * @param network ���� ���������� �����
	 * @param aboutBank ���� ���������� � �����
	 */
	public Footer(List<Network> network, AboutBank aboutBank)
	{
		this.network   = network;
		this.aboutBank = aboutBank;
	}

	/**
	 * @return ���� ���������� �����
	 */
	@XmlElementWrapper(name = "socialNetworks", required = false)
	@XmlElement(name = "network", required = true)
	public List<Network> getNetwork()
	{
		return network;
	}

	/**
	 * @return ������ ��������� �����
	 */
	@XmlElementWrapper(name = "phones", required = false)
	@XmlElement(name = "phone", required = true)
	public List<String> getPhones()
	{
		return phones;
	}

	/**
	 *
	 * @return ��������� � ������ ������ (������� � ����)
	 */
	@XmlElement(name = "helpdesc", required = false)
	public Helpdesc getHelpdesc()
	{
		return helpdesc;
	}

	/**
	 * @return ������� (������� ������)
	 */
	@XmlElement(name = "help", required = false)
	public FooterHelp getHelp()
	{
		return help;
	}

	/**
	 * @return ���� ���������� � �����
	 */
	@XmlElement(name = "aboutBank", required = true)
	public AboutBank getAboutBank()
	{
		return aboutBank;
	}

	public void setNetwork(List<Network> network)
	{
		this.network = network;
	}

	public void setAboutBank(AboutBank aboutBank)
	{
		this.aboutBank = aboutBank;
	}

	public void setPhones(List<String> phones)
	{
		this.phones = phones;
	}

	public void setHelpdesc(Helpdesc helpdesc)
	{
		this.helpdesc = helpdesc;
	}

	public void setHelp(FooterHelp help)
	{
		this.help = help;
	}
}
