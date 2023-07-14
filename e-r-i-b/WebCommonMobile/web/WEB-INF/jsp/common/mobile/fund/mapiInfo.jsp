<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<html:form action="/private/fund/mapi/info">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="phones" value="${form.phoneNumbers}"/>
    <c:set var="infoMap" value="${form.infoMap}"/>

    <tiles:insert definition="iphone">
        <tiles:put name="data" type="string">
            <phonesInfo>
                <c:forEach items="${phones}" var="phone">
                    <info>
                        <phone>
                            <c:out value="${phone}"/>
                        </phone>
                        <containsPro>
                            <c:out value="${infoMap[phone]}"/>
                        </containsPro>
                    </info>
                </c:forEach>
            </phonesInfo>
        </tiles:put>
    </tiles:insert>
</html:form>