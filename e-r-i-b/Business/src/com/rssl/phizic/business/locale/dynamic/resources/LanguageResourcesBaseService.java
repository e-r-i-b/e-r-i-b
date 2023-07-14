package com.rssl.phizic.business.locale.dynamic.resources;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.synchronization.log.ChangeType;
import com.rssl.phizic.business.dictionaries.synchronization.log.DictionaryRecordChangeInfoService;
import com.rssl.phizic.business.locale.dynamic.resources.multi.block.MultiBlockLanguageResources;
import com.rssl.phizic.business.locale.dynamic.resources.multi.block.MultiInstanceLanguageResourcesService;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * @author komarov
 * @ created 25.09.2014
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings("UnusedDeclaration")
public class LanguageResourcesBaseService<T extends MultiBlockLanguageResources> extends MultiInstanceLanguageResourcesService<T>
{
	private static final DictionaryRecordChangeInfoService dictionaryRecordChangeInfoService = new DictionaryRecordChangeInfoService();

	/**
	 * ����������� ��� ����������� ������, � ������� ������� ��������
	 * @param clazz �����
	 */
	public LanguageResourcesBaseService(Class<T> clazz)
	{
		super(clazz);
	}


	/**
	 * ����� ������� �� ��������������.
	 * @param uuid �������������
	 * @param localeId ������������� ������
	 * @return AdvertisingBlockRes
	 * @throws BusinessException
	 */
	public T findResById(String uuid, String localeId) throws BusinessException
	{

		return findResById(uuid, localeId, null);
	}

	/**
	 * ���������� ����� �������
	 * @param res ������
	 * @return ������
	 * @throws BusinessException
	 */
	public T addOrUpdate(T res) throws BusinessException
	{
		return addOrUpdate(res, null);
	}

	/**
	 * ���������� ����� �������
	 * @param res ������ ��������
	 * @return ������
	 * @throws BusinessException
	 */
	public List<T> addOrUpdate(List<T> res) throws BusinessException
	{
		return addOrUpdate(res, null);
	}

	/**
	 * �������� ��������� ��� ������
	 * @param uuid �������������
	 * @throws BusinessException
	 */
	public void removeRes(final String uuid) throws BusinessException
	{
		removeRes(uuid, null);
	}

	/**
	 * �������� ��������������� �������� � ������ ���������� ������
	 * @param session ������ ���������� � ������ ������� ���������� ������� �������
	 * @param uuid �������������
	 * @throws BusinessException
	 */
	public void removeResources(Session session, final String uuid) throws BusinessException
	{
		try
		{
			Query query = session.createQuery("delete from " + getClazz().getName() +" resource where resource.uuid = :uuid");
			query.setParameter("uuid", uuid);
			query.executeUpdate();
			dictionaryRecordChangeInfoService.removeLocaleResourceChangesToLog(session, uuid, getClazz());
		}
		catch (Exception e)
		{
			throw new BusinessException("�� ������� ������� ��������������� ������ ��� ������ " + getClazz(),e);
		}
	}


}
