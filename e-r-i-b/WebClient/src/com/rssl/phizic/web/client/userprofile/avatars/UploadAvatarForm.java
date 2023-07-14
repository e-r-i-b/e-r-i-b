package com.rssl.phizic.web.client.userprofile.avatars;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

/**
 * ����� �������� �������.
 *
 * @author bogdanov
 * @ created 25.04.14
 * @ $Author$
 * @ $Revision$
 */

public class UploadAvatarForm extends ActionForm
{
	private FormFile avatarFile;
	private int x;
	private int y;
	private int width;
	private int height;
	private String state; //load, loading, error, save, cancel, delete
	private String error;
	private String fileName;
	private boolean hasAvatar;

	private int maxFileSize;
	private String fileTypes;
	private String mimeTypes;
	private double imageWidth;
	private double imageHeight;

	/**
	 * @return ����������� ������������� ���� �������.
	 */
	public FormFile getAvatarFile()
	{
		return avatarFile;
	}

	/**
	 * @param avatarFile ����������� ������������� ���� �������.
	 */
	public void setAvatarFile(FormFile avatarFile)
	{
		this.avatarFile = avatarFile;
	}

	/**
	 * @return ������ ����������� ����� �������.
	 */
	public int getHeight()
	{
		return height;
	}

	/**
	 * @param height ������ ����������� ����� �������.
	 */
	public void setHeight(int height)
	{
		this.height = height;
	}

	/**
	 * @return ������ ����������� ����� �������.
	 */
	public int getWidth()
	{
		return width;
	}

	/**
	 * @param width ������ ����������� ����� �������.
	 */
	public void setWidth(int width)
	{
		this.width = width;
	}

	/**
	 * @return x ����� �������.
	 */
	public int getX()
	{
		return x;
	}

	/**
	 * @param x x ����� �������.
	 */
	public void setX(int x)
	{
		this.x = x;
	}

	/**
	 * @return y ����� �������.
	 */
	public int getY()
	{
		return y;
	}

	/**
	 * @param y y ����� �������.
	 */
	public void setY(int y)
	{
		this.y = y;
	}

	/**
	 * @return ��������� ��������.
	 */
	public String getState()
	{
		return state;
	}

	/**
	 * @param state ��������� ��������.
	 */
	public void setState(String state)
	{
		this.state = state;
	}

	/**
	 * @return ��������� �� ������, ������������ ������������.
	 */
	public String getError()
	{
		return error;
	}

	/**
	 * @param error ��������� �� ������, ������������ ������������.
	 */
	public void setError(String error)
	{
		this.error = error;
	}

	/**
	 * @return ��� ������������ �����.
	 */
	public String getFileName()
	{
		return fileName;
	}

	/**
	 * @param fileName ��� ������������ �����.
	 */
	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	/**
	 * @return ��� �����.
	 */
	public String getFileTypes()
	{
		return fileTypes;
	}

	/**
	 * @param fileTypes ��� �����.
	 */
	public void setFileTypes(String fileTypes)
	{
		this.fileTypes = fileTypes;
	}

	/**
	 * @return ������������ ������ �����.
	 */
	public int getMaxFileSize()
	{
		return maxFileSize;
	}

	/**
	 * @param maxFileSize ������������ ������ �����.
	 */
	public void setMaxFileSize(int maxFileSize)
	{
		this.maxFileSize = maxFileSize;
	}

	/**
	 * @return ���� ������, ��������� ��� ��������.
	 */
	public String getMimeTypes()
	{
		return mimeTypes;
	}

	/**
	 * @param mimeTypes ���� ������, ��������� ��� ��������.
	 */
	public void setMimeTypes(String mimeTypes)
	{
		this.mimeTypes = mimeTypes;
	}

	/**
	 * @return ���������� �� ������.
	 */
	public boolean isHasAvatar()
	{
		return hasAvatar;
	}

	/**
	 * @param hasAvatar ���������� �� ������.
	 */
	public void setHasAvatar(boolean hasAvatar)
	{
		this.hasAvatar = hasAvatar;
	}

	/**
	 * @param imageWidth ������ �����������.
	 */
	public void setImageWidth(double imageWidth)
	{
		this.imageWidth = imageWidth;
	}

	/**
	 * @return ������ �����������.
	 */
	public double getImageWidth()
	{
		return imageWidth;
	}

	/**
	 * @param imageHeight ������ �����������.
	 */
	public void setImageHeight(double imageHeight)
	{
		this.imageHeight = imageHeight;
	}

	/**
	 * @return ������ �����������.
	 */
	public double getImageHeight()
	{
		return imageHeight;
	}
}
