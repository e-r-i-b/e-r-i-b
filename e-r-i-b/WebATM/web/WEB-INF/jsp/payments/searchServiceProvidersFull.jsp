<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<%-- Поиск поставщиков --%>
<html:form action="/private/provider/search">
    <tiles:insert definition="atm">
        <tiles:put name="data" type="string">
            <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
            
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

                        <%-- Графическая подсказка --%>
                        <c:set var="providerImageHelpId"         value="${item[35]}"/>
                        <c:set var="providerImageHelpUpdateTime" value="${item[36]}"/>
                        <c:if test="${not empty providerImageHelpId and not empty providerImageHelpUpdateTime}">
                            <tiles:insert definition="imageType" flush="false">
                                <tiles:put name="name"       value="helpImg"/>
                                <tiles:put name="id"         value="${providerImageHelpId}"/>
                                <tiles:put name="updateTime" beanName="providerImageHelpUpdateTime"/>
                            </tiles:insert>
                        </c:if>
                        <c:if test="${not empty item[37]}">
                            <subType><c:out value="${item[37]}"/></subType>
                        </c:if>
                        <guid><c:out value="${item[10]}"/></guid>
                    </provider>

                    <autoPaymentSupported>${item[11]}</autoPaymentSupported>
                    <c:if test="${not empty item[12]}">
                        <INN>${item[12]}</INN>
                    </c:if>
                    <c:if test="${not empty item[13]}">
                        <accountNumber>${item[13]}</accountNumber>
                    </c:if>

                    <c:set var="firstNotEmptyServiceIndex">
                        <c:choose>
                            <c:when test="${not empty item[14]}">14</c:when>
                            <c:when test="${not empty item[21]}">21</c:when>
                            <c:when test="${not empty item[28]}">28</c:when>
                        </c:choose>
                    </c:set>
                    <c:if test="${not empty firstNotEmptyServiceIndex}">
                        <tiles:insert page="parent.jsp" flush="false">
                            <tiles:put name="name" value="category"/>
                            <tiles:put name="item" beanName="item" beanScope="page"/>
                            <tiles:put name="index" value="${firstNotEmptyServiceIndex}"/>
                        </tiles:insert>
                    </c:if>

                </sl:collectionItem>
            </sl:collection>

            <sl:collection id="item2" property="services" model="xml-list" title="services">
                <c:if test="${not empty item2}">
                    <tiles:insert page="parent.jsp" flush="false">
                        <tiles:put name="name" value="service"/>
                        <tiles:put name="item" beanName="item2" beanScope="page"/>
                        <tiles:put name="index" value="0"/>
                    </tiles:insert>
                </c:if>
            </sl:collection>
        </tiles:put>
    </tiles:insert>
</html:form>