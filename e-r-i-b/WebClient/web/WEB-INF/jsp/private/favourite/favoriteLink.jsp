<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html:form action="/private/async/favourite/add">
    <c:set var="form" value="${phiz:currentForm(pageContext)}" scope="request"/>
    <c:set var="name" value="<span>${phiz:escapeForJS(form.name,true)}</span>"/>
    <c:set var="reference" value="${form.reference}"/>
    <script type="text/javascript">
        var favouriteList = document.getElementById("favouriteLinkList");
        if (favouriteList && favouriteList.innerHTML.indexOf("${name}") == -1)
        {
            favouriteList.innerHTML = '<phiz:linksListItem title="${name}" href="${reference}" outputByItself="true" onClick="return redirectResolved();"/>' + favouriteList.innerHTML;
        }
    </script>
    <%--костыль для IE--%>
    &nbsp;
    <html:hidden styleId="favouriteLinkMessage" property="message"/>
    <%@ include file="../../../jsp-sbrf/personalMenuFavouriteLink.jsp"%>
</html:form>