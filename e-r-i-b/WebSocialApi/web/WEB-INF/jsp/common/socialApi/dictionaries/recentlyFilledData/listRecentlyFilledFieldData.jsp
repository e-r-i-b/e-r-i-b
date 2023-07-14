<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>

<html:form action="/private/payments/field/dictionary">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="type" value="${form.type}"/>
    <tiles:insert definition="iphone" flush="false">
        <c:if test="${not empty form.contacts or not empty form.phones}">
            <tiles:put name="data">
                <receivers>
                    <tiles:insert page="by_${type}.jsp" flush="false">
                        <c:if test="${not empty form.contacts}">
                            <tiles:put name="data" beanName="form" beanProperty="contacts"/>
                        </c:if>
                        <c:if test="${not empty form.phones}">
                            <tiles:put name="phones" beanName="form" beanProperty="phones"/>
                        </c:if>
                    </tiles:insert>
                </receivers>
            </tiles:put>
        </c:if>
    </tiles:insert>
</html:form>
