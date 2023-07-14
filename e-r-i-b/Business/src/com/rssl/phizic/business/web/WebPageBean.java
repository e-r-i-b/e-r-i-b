package com.rssl.phizic.business.web;

import com.rssl.phizic.business.profile.Profile;
import com.rssl.phizic.common.types.Entity;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.EntityUtils;
import org.apache.commons.lang.ObjectUtils;

/**
 * @author Erkin
 * @ created 10.12.2012
 * @ $Author$
 * @ $Revision$
 */
public abstract class WebPageBean implements Entity
{
	private Long id;
	
	/**
	 * main, sidemenu и т.п.
	 */
	private String classname;
	
	/**
	 * Положение контейнера на странице
	 */
	private Location location;

	/**
	 * Массив раскладки
	 */
	private Layout layout;

	private Profile profile;

	///////////////////////////////////////////////////////////////////////////

	public Long getId()
	{
		return id;
	}

	void setId(Long id)
	{
		this.id = id;
	}

	public String getClassname()
	{
		return classname;
	}

	void setClassname(String classname)
	{
		this.classname = classname;
	}

	public Location getLocation()
	{
		return location;
	}

	public void setLocation(Location location)
	{
		this.location = location;
	}

	public Layout getLayout()
	{
		return layout;
	}

	public void setLayout(Layout layout)
	{
		this.layout = layout;
	}

	public Profile getProfile()
	{
		return profile;
	}

	void setProfile(Profile profile)
	{
		this.profile = profile;
	}

	@Override
	public int hashCode()
	{
		return classname.hashCode();
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (!(o instanceof WebPageBean))
			return false;

		WebPageBean other = (WebPageBean) o;

		boolean equals = ObjectUtils.equals(this.id, other.id);
		equals = equals && StringHelper.equalsNullIgnore(this.classname, other.classname);
		equals = equals && ObjectUtils.equals(this.layout, other.layout);
		equals = equals && (this.location == other.location);
		equals = equals && EntityUtils.equalsById(this.profile, other.profile);

		return equals;
	}
}
