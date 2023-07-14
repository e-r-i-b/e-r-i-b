<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- Поиск поставщиков --%>
<html:form action="/private/provider/search">
    <tiles:insert definition="iphone">
        <tiles:put name="data" type="string">
            <sl:collection id="item" property="searchResults" model="xml-list" title="payments">
                <sl:collectionItem title="payment">
                    <billing>${item[0]}</billing>

                    <service>
                        <id>${item[1]}</id>
                        <name><c:out value="${item[2]}"/></name>
                        <c:set var="serviceImgUpdate" value="${item[4]}"/>
                        <c:if test="${not empty item[3] and not empty serviceImgUpdate}">
                            <tiles:insert definition="imageType" flush="false">
                                <tiles:put name="name" value="img"/>
                                <tiles:put name="id" value="${item[3]}"/>
                                <tiles:put name="updateTime" beanName="serviceImgUpdate"/>
                            </tiles:insert>
                        </c:if>
                    </service>

                    <provider>
                        <id>${item[5]}</id>
                        <name><c:out value="${item[6]}"/></name>
                        <c:set var="providerImgUpdate" value="${item[8]}"/>
                        <c:if test="${not empty item[7] and not empty providerImgUpdate}">
                            <tiles:insert definition="imageType" flush="false">
                                <tiles:put name="name" value="img"/>
                                <tiles:put name="id" value="${item[7]}"/>
                                <tiles:put name="updateTime" beanName="providerImgUpdate"/>
                            </tiles:insert>
                        </c:if>
                    </provider>

                    <autoPaymentSupported>${item[9]}</autoPaymentSupported>
                    <isBarSupported>${item[10]}</isBarSupported>
                    <c:if test="${not empty item[11]}">
                        <INN>${item[11]}</INN>
                    </c:if>
                    <c:if test="${not empty item[12]}">
                        <accountNumber>${item[12]}</accountNumber>
                    </c:if>

                    <c:if test="${not empty item[13]}">
                        <category>
                            <id>${item[13]}</id>
                            <title><c:out value="${item[14]}"/></title>
                            <description><c:out value="${item[15]}"/></description>
                            <tiles:insert definition="imageType" flush="false">
                                <tiles:put name="name" value="imgURL"/>
                                <tiles:put name="id" value="${item[16]}"/>
                                <c:set var="groupImgUpdate" value="${item[17]}"/>
                                <c:if test="${not empty groupImgUpdate}">
                                    <tiles:put name="updateTime" beanName="groupImgUpdate"/>
                                </c:if>
                                <tiles:put name="url" value="${item[18]}"/>
                            </tiles:insert>
                            <c:if test="${not empty item[19]}">
                                <parent>
                                    <id>${item[19]}</id>
                                    <title><c:out value="${item[20]}"/></title>
                                    <description><c:out value="${item[21]}"/></description>
                                    <tiles:insert definition="imageType" flush="false">
                                        <tiles:put name="name" value="imgURL"/>
                                        <tiles:put name="id" value="${item[22]}"/>
                                        <c:set var="parentImgUpdate" value="${item[23]}"/>
                                        <c:if test="${not empty parentImgUpdate}">
                                            <tiles:put name="updateTime" beanName="parentImgUpdate"/>
                                        </c:if>
                                        <tiles:put name="url" value="${item[24]}"/>
                                    </tiles:insert>
                                </parent>
                            </c:if>
                        </category>
                    </c:if>

                </sl:collectionItem>
            </sl:collection>
        </tiles:put>
    </tiles:insert>
</html:form>