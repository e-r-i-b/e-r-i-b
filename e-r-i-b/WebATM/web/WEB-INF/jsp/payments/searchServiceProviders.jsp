<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- Поиск поставщиков --%>
<html:form action="/private/provider/search">
    <tiles:insert definition="atm">
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
                        <guid><c:out value="${item[5]}"/></guid>
                    </service>

                    <provider>
                        <id>${item[6]}</id>
                        <name><c:out value="${item[7]}"/></name>
                        <c:set var="providerImgUpdate" value="${item[9]}"/>
                        <c:if test="${not empty item[8] and not empty providerImgUpdate}">
                            <tiles:insert definition="imageType" flush="false">
                                <tiles:put name="name" value="img"/>
                                <tiles:put name="id" value="${item[8]}"/>
                                <tiles:put name="updateTime" beanName="providerImgUpdate"/>
                            </tiles:insert>
                        </c:if>
                        <guid><c:out value="${item[10]}"/></guid>
                    </provider>

                    <autoPaymentSupported>${item[11]}</autoPaymentSupported>
                    <c:if test="${not empty item[13]}">
                        <INN>${item[13]}</INN>
                    </c:if>
                    <c:if test="${not empty item[14]}">
                        <accountNumber>${item[14]}</accountNumber>
                    </c:if>

                    <c:if test="${not empty item[15]}">
                        <category>
                            <id>${item[15]}</id>
                            <title><c:out value="${item[16]}"/></title>
                            <description><c:out value="${item[16]}"/></description>
                            <tiles:insert definition="imageType" flush="false">
                                <tiles:put name="name" value="imgURL"/>
                                <tiles:put name="id" value="${item[18]}"/>
                                <c:set var="categoryImgUpdate" value="${item[19]}"/>
                                <c:if test="${not empty categoryImgUpdate}">
                                    <tiles:put name="updateTime" beanName="categoryImgUpdate"/>
                                </c:if>
                                <tiles:put name="url" value="${item[20]}"/>
                            </tiles:insert>
                            <guid><c:out value="${item[21]}"/></guid>
                        </category>
                    </c:if>
                </sl:collectionItem>
            </sl:collection>
        </tiles:put>
    </tiles:insert>
</html:form>