<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>

<html:form action="/private/dictionary/servicesPayments">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="atm" flush="false">
        <tiles:put name="data">
            <c:if test="${not empty form.list}">
                <c:choose>
                    <c:when test="${form.type == 'category'}">
                        <sl:collection id="element" property="list" model="xml-list" title="elementList">
                            <sl:collectionItem title="element">
                                <id><c:out value="${element[0]}"/></id>
                                <type>${form.type}</type>
                                <title><c:out value="${element[1]}"/></title>
                                <description><c:out value="${element[1]}"/></description>
                                <c:if test="${not empty element[2] or not empty element[3]}">
                                    <tiles:insert definition="imageType" flush="false">
                                        <tiles:put name="name" value="imgURL"/>
                                        <tiles:put name="id" value="${element[2]}"/>
                                        <tiles:put name="url" value="${element[3]}"/>
                                    </tiles:insert>
                                </c:if>
                                <guid><c:out value="${element[4]}"/></guid>
                            </sl:collectionItem>
                        </sl:collection>
                    </c:when>
                    <%--подуслуги--%>
                    <c:when test="${form.type == 'service'}">
                        <sl:collection id="element" property="list" model="xml-list" title="elementList">
                            <sl:collectionItem title="element">
                                <c:set var="id" value="0"/>
                                <c:if test="${id != element[0]}">
                                    <id>${element[0]}</id>
                                    <type>${form.type}</type>
                                    <title><c:out value="${element[1]}"/></title>
                                    <description><c:out value="${element[1]}"/></description>
                                    <tiles:insert definition="imageType" flush="false">
                                        <tiles:put name="name" value="imgURL"/>
                                        <tiles:put name="url" value="${element[3]}"/>
                                        <tiles:put name="id" value="${element[2]}"/>
                                    </tiles:insert>
                                    <c:if test="${empty element[6]}">
                                        <guid><c:out value="${element[5]}"/></guid>
                                    </c:if>
                                </c:if>
                                <c:set var="id" value="${element[0]}"/>
                            </sl:collectionItem>
                        </sl:collection>
                    </c:when>
                    <%--поставщики--%>
                    <c:when test="${form.type == 'provider'}">
                        <sl:collection id="element" property="list" model="xml-list" title="elementList">
                            <sl:collectionItem title="element">
                                <id>${element[0]}</id>
                                <type>${form.type}</type>
                                <title><c:out value="${element[1]}"/></title>
                                <description><c:out value="${element[1]}"/></description>
                                <tiles:insert definition="imageType" flush="false">
                                    <tiles:put name="name" value="imgURL"/>
                                    <tiles:put name="id" value="${element[2]}"/>
                                </tiles:insert>
                                <c:if test="${not empty element[8]}">
                                    <guid><c:out value="${element[8]}"/></guid>
                                </c:if>
                            </sl:collectionItem>
                        </sl:collection>
                    </c:when>
                </c:choose>
            </c:if>

        </tiles:put>
    </tiles:insert>
</html:form>
