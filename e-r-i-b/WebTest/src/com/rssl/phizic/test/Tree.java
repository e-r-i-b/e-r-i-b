package com.rssl.phizic.test;

import java.util.List;

/**
 * @author egorova
 * @ created 14.04.2009
 * @ $Author$
 * @ $Revision$
 */
public class Tree
{
	private Long id;
	private Tree parent;
	private String name;
    private List<Tree> children;

    public Tree(String name, Long id)
    {
       this.name = name;
	   this.id = id;
    }

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public List<Tree> getChildren()
	{
		return children;
	}

	public void setChildren(List<Tree> children)
	{
		this.children = children;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Tree getParent()
	{
		return parent;
	}

	public void setParent(Tree parent)
	{
		this.parent = parent;
	}
}
