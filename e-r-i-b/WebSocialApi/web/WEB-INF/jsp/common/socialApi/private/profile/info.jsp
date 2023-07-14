<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<html:form action="/private/profile/info">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <detail>
                <c:if test="${not empty form.avatarPath}">
                    <avatarPath>${form.avatarPath}</avatarPath>
                </c:if>
                <surName>${form.surName}</surName>
                <firstName>${form.firstName}</firstName>
                <c:if test="${not empty form.patrName}">
                    <patrName>${form.patrName}</patrName>
                </c:if>
                <mobilePhone><c:out value="${form.mobilePhone}"/></mobilePhone>
                <c:if test="${not empty form.jobPhone}">
                    <jobPhone>${form.jobPhone}</jobPhone>
                </c:if>
                <c:if test="${not empty form.homePhone}">
                    <homePhone>${form.homePhone}</homePhone>
                </c:if>
                <c:if test="${not empty form.email}">
                    <email>${form.email}</email>
                </c:if>
                <mainDocuments>
                    <c:forEach var="document" items="${form.mainDocuments}">
                        <document>
                            <documentName>
                                <bean:message key="document.type.${document.documentType}" bundle="profileBundle"/>
                            </documentName>
                            <documentSeries>${document.documentSeries}</documentSeries>
                            <documentNumber>${phiz:getCutDocumentNumber(document.documentNumber)}</documentNumber>
                        </document>
                    </c:forEach>
                </mainDocuments>
                <c:if test="${not empty form.additionalDocuments}">
                    <additionalDocuments>
                        <c:forEach var="document" items="${form.additionalDocuments}">
                            <c:if test="${document.documentType == 'SNILS'}">
                                <snils>
                                    <documentId>${document.id}</documentId>
                                    <documentNumber>${document.number}</documentNumber>
                                </snils>
                            </c:if>
                        </c:forEach>
                        <c:forEach var="document" items="${form.additionalDocuments}">
                            <c:if test="${document.documentType == 'DL'}">
                                <drivingLicence>
                                    <documentId>${document.id}</documentId>
                                    <documentSeries>${document.series}</documentSeries>
                                    <documentNumber>${document.number}</documentNumber>
                                    <issueBy>${document.issueBy}</issueBy>
                                    <c:if test="${not empty document.expireDate}">
                                        <tiles:insert definition="mobileDateType" flush="false">
                                            <tiles:put name="name" value="expireDate"/>
                                            <tiles:put name="calendar" beanName="document" beanProperty="expireDate"/>
                                        </tiles:insert>
                                    </c:if>
                                    <c:if test="${not empty document.issueDate}">
                                        <tiles:insert definition="mobileDateType" flush="false">
                                            <tiles:put name="name" value="issueDate"/>
                                            <tiles:put name="calendar" beanName="document" beanProperty="issueDate"/>
                                        </tiles:insert>
                                    </c:if>
                                </drivingLicence>
                            </c:if>
                        </c:forEach>
                        <c:forEach var="document" items="${form.additionalDocuments}">
                            <c:if test="${document.documentType == 'RC'}">
                                <registrationCertificate>
                                    <documentId>${document.id}</documentId>
                                    <documentSeries>${document.series}</documentSeries>
                                    <documentNumber>${document.number}</documentNumber>
                                </registrationCertificate>
                            </c:if>
                        </c:forEach>
                        <c:forEach var="document" items="${form.additionalDocuments}">
                            <c:if test="${document.documentType == 'INN'}">
                                <inn>
                                    <documentId>${document.id}</documentId>
                                    <documentNumber>${document.number}</documentNumber>
                                </inn>
                            </c:if>
                        </c:forEach>
                    </additionalDocuments>
                </c:if>
            </detail>
        </tiles:put>
    </tiles:insert>
</html:form>