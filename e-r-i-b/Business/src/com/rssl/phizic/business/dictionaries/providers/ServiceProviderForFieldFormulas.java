package com.rssl.phizic.business.dictionaries.providers;

/**
 * @author osminin
 * @ created 14.07.15
 * @ $Author$
 * @ $Revision$
 *
 * �������� ��� ������ � ����������� ����� � ����������� ������ � ����������� �������
 */
public class ServiceProviderForFieldFormulas
{
	private Long id;
	private String  name;
	private String  INN;
	private String  account;
	private ServiceProviderState state;
	private Long imageId;
	private String nameService;
	private Long imageHelpId;

	/**
	 * @return �������������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id �������������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ������������
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name ������������
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return ���
	 */
	public String getINN()
	{
		return INN;
	}

	/**
	 * @param INN ���
	 */
	public void setINN(String INN)
	{
		this.INN = INN;
	}

	/**
	 * @return ����
	 */
	public String getAccount()
	{
		return account;
	}

	/**
	 * @param account ����
	 */
	public void setAccount(String account)
	{
		this.account = account;
	}

	/**
	 * @return ������
	 */
	public ServiceProviderState getState()
	{
		return state;
	}

	/**
	 * @param state ������
	 */
	public void setState(ServiceProviderState state)
	{
		this.state = state;
	}

	/**
	 * @return ������������� ������
	 */
	public Long getImageId()
	{
		return imageId;
	}

	/**
	 * @param imageId ������������� ������
	 */
	public void setImageId(Long imageId)
	{
		this.imageId = imageId;
	}

	/**
	 * @return ������
	 */
	public String getNameService()
	{
		return nameService;
	}

	/**
	 * @param nameService ������
	 */
	public void setNameService(String nameService)
	{
		this.nameService = nameService;
	}

	/**
	 * @return ������������� ����������� ���������
	 */
	public Long getImageHelpId()
	{
		return imageHelpId;
	}

	/**
	 * @param imageHelpId ������������� ����������� ���������
	 */
	public void setImageHelpId(Long imageHelpId)
	{
		this.imageHelpId = imageHelpId;
	}
}
