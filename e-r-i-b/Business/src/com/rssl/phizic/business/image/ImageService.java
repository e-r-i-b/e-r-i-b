package com.rssl.phizic.business.image;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.ExecutorQuery;
import com.rssl.phizic.utils.ArraysHelper;
import com.rssl.phizic.utils.HashHelper;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

/**
 * @author akrenev
 * @ created 26.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class ImageService
{
	private static String IMAGE_CACHE_SEPARATOR = "#";
	private static final SimpleService simpleService = new SimpleService();
	private static Cache imageCache;

	static
	{
		imageCache = CacheProvider.getCache("image-cache");
	}

	/**
	 * ����� �������� �� id
	 * @param id ��������
	 * @param instanceName - ��� �������� ������ ��
	 * @return ������ � ��, ��������������� ������� id
	 * @throws BusinessException
	 */
	public Image findById(Long id, String instanceName) throws BusinessException
	{
		String key = getKey(id, instanceName);
		Element element = imageCache.get(key);
        if (element == null)
		{
			Image image = simpleService.findById(Image.class, id, instanceName);
			imageCache.put(new Element(key, image));
			return image;
		}
		return (Image) element.getObjectValue();
	}

	/**
	 * ����� �������� �� id(��� ��������������)
	 * @param id ��������
	 * @param instanceName - ��� �������� ������ ��
	 * @return ������ � ��, ��������������� ������� id
	 * @throws BusinessException
	 */
	public Image findByIdForUpdate(Long id, String instanceName) throws BusinessException
	{
		return simpleService.findById(Image.class, id, instanceName);
	}

	private String getKey(Long id, String instanceName)
	{
		if (instanceName == null)
			return id.toString();
		return id.toString() + IMAGE_CACHE_SEPARATOR + instanceName;
	}

	/**
	 * ���������� ������ ���� id � ������� ������ md5.
	 *
	 * @return ������ id.
	 * @throws BusinessException
	 */
	public List<Long> getEmptyMD5ImageId() throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(Image.class);
		criteria.add(Expression.isNull("md5"));
		criteria.setProjection(Projections.id());
		return simpleService.find(criteria);
	}

	public String getImageHash(Image image, String instanceName) throws BusinessException
	{
		return getImageHash(image, null, instanceName);
	}
	/**
	 * ���������� md5 ��������.
	 *
	 * @param image ������ � ��������.
	 * @param data ���� ��������.
	 * @return �� md5 (� ������������ md5 ������ ��������� extendImage, InnerImage � image).
	 */
	public String getImageHash(Image image, byte[] data, String instanceName) throws BusinessException
	{
		try
		{
			if (data == null && image.isHaveImageBlob())
				data = loadImageData(image, instanceName);

			byte[] bts = ArraysHelper.concat(
					ArraysHelper.getBytes(image.getExtendImage()),
					ArraysHelper.getBytes(image.getInnerImage()),
					data
			);

			return HashHelper.hash(bts);
		}
		catch (SystemException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� ������ � ������������ �� ��
	 * @param image - ������ � ��������
	 * @return ������ � ������������
	 * @throws BusinessException
	 */
	public byte[] loadImageData(final Image image, String instanceName) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<byte[]>()
			{
				public byte[] run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.image.loadImageData");
					query.setParameter("imageId", image.getId());
					return (byte[]) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ���������� ������ � �������� � ������ ����������� � ��
	 * @param image - ����������� ������
	 * @param imgData - ������ � ������������
	 * @param instanceName - ��� �������� ������ ��
	 * @return ����������� ������
	 * @throws BusinessException
	 */
	public Image addOrUpdateImageAndImageData(final Image image, final byte[] imgData, final String instanceName) throws BusinessException
	{
		image.setMd5(getImageHash(image, imgData, instanceName));

		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Image>()
			{
				public Image run(Session session) throws Exception
				{
					Image img = simpleService.addOrUpdate(image, instanceName);
					session.flush();
					imageCache.put(new Element(getKey(img.getId(), instanceName), img));
					ExecutorQuery executorQuery = new ExecutorQuery(HibernateExecutor.getInstance(instanceName), "com.rssl.phizic.business.image.saveImageData");
					executorQuery.setParameter("imageId", img.getId());
					executorQuery.setParameter("imgData", imgData);
					executorQuery.executeUpdate();
					return img;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ���������� ���������� � �������� � ��
	 *
	 * @param image - ����������� ������
	 * @param instanceName - ��� �������� ������ ��
	 * @return ����������� ������
	 * @throws BusinessException
	 */
	public Image addOrUpdate(Image image, String instanceName) throws BusinessException
	{
		image.setMd5(getImageHash(image, instanceName));
		Image img = simpleService.addOrUpdate(image, instanceName);
		imageCache.put(new Element(getKey(img.getId(), instanceName), img));
		return img;
	}

	/**
	 * �������� �� id ��������
	 * @param id ��������
	 * @param instance ��� �������� ������ ��
	 * @throws BusinessException
	 */
	public void removeImageById(final Long id, String instance) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance(instance).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.image.ImageService.removeImageById");
					query.setParameter("id", id);
			        query.executeUpdate();
					return null;
				}
			});
		}
		catch (ConstraintViolationException e)
		{
			//���� image �������� � ���-��, �� �� �������.
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ������� ��������.
	 * @param image ��������
	 * @param instanceName ��� �������� ������ ��
	 */
	public void remove(Image image, String instanceName) throws BusinessException
	{
		simpleService.remove(image, instanceName);
	}
}
