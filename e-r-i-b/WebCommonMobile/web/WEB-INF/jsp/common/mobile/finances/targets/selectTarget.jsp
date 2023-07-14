<%@ page contentType="text/xml;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"  prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"           prefix="c"%>
<%@ taglib uri="http://rssl.com/tags"                        prefix="phiz"%>

<html:form action="/private/finances/targets/selectTarget">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">

            <c:set var="targets" value="${form.targets}"/>
            <c:if test="${not empty targets}">
                <targets>
                    <c:forEach var="target" items="${targets}">
                        <target>
                            <type>${target}</type>
                            <description>${target.description}</description>
                         </target>
                    </c:forEach>
                </targets>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>