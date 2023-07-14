<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/finances/operations/list">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <c:if test="${not empty form.cardOperations}">
                <sl:collection id="operation" property="cardOperations" model="xml-list" title="operations">
                    <%--@elvariable id="operation" type="com.rssl.phizic.operations.finances.CardOperationDescription"--%>
                    <sl:collectionItem title="operation">
                        <id>${operation.id}</id>
                        <tiles:insert definition="mobileDateTimeType" flush="false">
                            <tiles:put name="name" value="date"/>
                            <tiles:put name="calendar" beanName="operation" beanProperty="date"/>
                        </tiles:insert>
                        <comment><c:out value="${operation.title}"/></comment>
                        <categoryId>${operation.categoryId}</categoryId>
                        <categoryName><c:out value="${operation.category.name}"/></categoryName>
                        <hidden>
                            <c:choose>
                                <c:when test="${operation.hidden}">
                                    true
                                </c:when>
                                <c:otherwise>
                                    false
                                </c:otherwise>
                            </c:choose>
                        </hidden>
                        <country>
                            <c:out value="${operation.country}"/>
                        </country>
                        <cardNumber>
                            <c:choose>
                                <c:when test="${not empty operation.card}">
                                    <c:out value="${phiz:getCutCardNumber(operation.card.number)}"/>
                                </c:when>
                                <c:otherwise>
                                    Наличные
                                </c:otherwise>
                            </c:choose>
                        </cardNumber>
                        <c:choose>
                            <c:when test="${not empty operation.cardAmount}">
                                <tiles:insert definition="mobileMoneyType" flush="false">
                                    <tiles:put name="name" value="cardAmount"/>
                                    <tiles:put name="money" beanName="operation" beanProperty="cardAmount"/>
                                </tiles:insert>
                            </c:when>
                            <c:otherwise>
                                <cardAmount></cardAmount>
                            </c:otherwise>
                        </c:choose>
                        <tiles:insert definition="mobileMoneyType" flush="false">
                            <tiles:put name="name" value="nationalAmount"/>
                            <tiles:put name="money" beanName="operation" beanProperty="nationalAmount"/>
                        </tiles:insert>
                        <c:if test="${not empty operation.availableCategories}">
                            <availableCategories>
                                <c:forEach items="${operation.availableCategories}" var="category">
                                    <category>
                                        <id>${category.id}</id>
                                        <name><c:out value="${category.name}"/></name>
                                    </category>
                                </c:forEach>
                            </availableCategories>
                        </c:if>
                    </sl:collectionItem>
                </sl:collection>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>