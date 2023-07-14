package com.rssl.phizic.operations.skins;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.common.types.ApplicationType;
import com.rssl.phizic.business.skins.Skin;
import com.rssl.phizic.business.skins.SkinsService;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.config.ConfigFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author egorova
 * @ created 12.03.2009
 * @ $Author$
 * @ $Revision$
 */
public class ChangeSkinOperation extends OperationBase
{
	private static final SkinsService skinsService = new SkinsService();

	private List<Skin> employeeSkins = new ArrayList<Skin>();
	private List<Skin> clientSkins   = new ArrayList<Skin>();

	/**
	 * ������������� ��������
	 */
	public void initialize() throws BusinessException
	{
		List<Skin> standartSkins = skinsService.getStandartSkins();

		if(standartSkins == null || standartSkins.isEmpty())
		{
			return;
		}

		for(Skin item : standartSkins)
		{
			/*
			 * ��������� � ������ ������ ���������� �����
			 */
			if(item.getCategory().isClient() && item.isCommon())
			{
				clientSkins.add(item);
			}

			if(item.getCategory().isAdmin())
			{
				employeeSkins.add(item);
			}
		}
	}

	/**
	 * @return true, ���� ��������� ������ ���� ��� ����������
	 */
	public boolean doesChangeAdminSkinAllowed()
	{
		return ConfigFactory.getConfig(SkinsConfig.class).doesChangeAdminSkinAllowed();
	}
	
	/**
	 * ��������� � ���������� ������ (� systemAll.css � ����� ���������)
	 * @return ���������� ��������� ��� null
	 * @throws BusinessException
	 */
	public Skin getGlobalUrl() throws BusinessException
	{
		return skinsService.getGlobalUrl();
	}

	/**
	 * �������� ������ ������ ��������
	 * @return List<Skin> ������ ������
	 */
	public List<Skin> getClientSkins()
	{
		return clientSkins;
	}

	/**
	 * �������� ������ ������ �����������
	 * @return List<Skin> ������ ������
	 */
	public List<Skin> getEmployeeSkins()
	{
		return employeeSkins;
	}

	/**
	 * ����� ��������� (�������� �����)
	 * @param skinIdAdmin - id ������ ��������� ����� ��� ����������� ����������
	 * @param skinIdClient - id ������ ��������� ����� ��� ��� ����������
	 * @throws BusinessException
	 */
	public void changeCurrentSkins(Long skinIdAdmin, Long skinIdClient) throws BusinessException
	{
		skinsService.changeCurrentSkin(ApplicationType.Admin, skinIdAdmin);
		skinsService.changeCurrentSkin(ApplicationType.Client, skinIdClient);
	}

	/**
	 * ����� ��������� � ���������� ������ (� systemAll.css � ����� ���������)
	 * @param newUrl - ����� url
	 * @throws BusinessException
	 */
	public void changeGlobalUrl(String newUrl) throws BusinessException
	{
		skinsService.changeGlobalUrl(newUrl);
	}

	public Skin getCurrentApplicationSkin(ApplicationType appType) throws BusinessException
	{
		return skinsService.getActiveSkin(appType);
	}
}
