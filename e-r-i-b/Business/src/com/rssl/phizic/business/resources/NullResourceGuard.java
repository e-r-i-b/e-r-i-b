package com.rssl.phizic.business.resources;

/** Created by IntelliJ IDEA. User: Evgrafov Date: 12.10.2005 Time: 12:01:10 */
public final class NullResourceGuard implements ResourceGuard
{
    private static NullResourceGuard instance = new NullResourceGuard();

    public static NullResourceGuard getInstance()
    {
        return instance;
    }

    private NullResourceGuard(){}
    public void onResourceOwnRemoved(Resource resource)
    {
    }

    public void onResourceOwnAdded(Resource resource)
    {
    }
}
