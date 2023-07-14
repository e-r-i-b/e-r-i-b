<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<fmt:setLocale value="ru-RU"/>

<html:form action="/login/chooseAgreement">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<tiles:insert definition="iphone" flush="false">
<tiles:put name="data">
    <loginCompleted>false</loginCompleted>
    <chooseAgreementStage>
        <person>
            <surName>${phiz:getFormattedSurName(form.persons[0].surName)}</surName>
            <firstName><c:out value="${form.persons[0].firstName}"/></firstName>
            <patrName><c:out value="${form.persons[0].patrName}"/></patrName>
        </person>

        <sl:collection id="pers" property="persons" model="xml-list" title="agreements">
            <sl:collectionItem title="agreement">
                <id>${pers.id}</id>
                <c:choose>
                    <c:when test="${form.personBlocked[pers]}">
                        <status>blocked</status>
                    </c:when>
                    <c:otherwise>
                        <status>active</status>
                    </c:otherwise>
                </c:choose>
                <agreementInfo>
                    <number><c:out value="${pers.agreementNumber}"/></number>
                    <c:if test="${pers.agreementDate ne null}">
                        <date><fmt:formatDate value="${pers.agreementDate.time}" pattern="dd.MM.yyyy"/></date>
                    </c:if>
                    <c:if test="${pers.prolongationRejectionDate ne null}">
                        <prolongationRejectionDate><fmt:formatDate value="${pers.prolongationRejectionDate.time}" pattern="dd.MM.yyyy"/></prolongationRejectionDate>
                    </c:if>
                </agreementInfo>
                <department>
                    <c:set var="department" value="${phiz:getDepartmentById(pers.departmentId)}"/>
                    <c:set var="terBank" value="${phiz:getTerBank(department.code)}"/>
                    <address><c:out value="${department.address}"/></address>
                    <c:if test="${terBank ne null}">
                        <tbName><c:out value="${terBank.name}"/></tbName>
                    </c:if>
                </department>
                <c:set var="cardLinks" value="${form.cardLinks[pers]}"/>
                <c:set var="accountLinks" value="${form.accountLinks[pers]}"/>
                <c:set var="loanLinks" value="${form.loanLinks[pers]}"/>
                <c:if test="${not empty cardLinks or not empty accountLinks or not empty loanLinks}">
                    <products>
                        <c:if test="${not empty cardLinks}">
                            <cards>
                                <c:forEach var="link" items="${cardLinks}">
                                    <card>
                                        <number><c:out value="${phiz:getCutCardNumber(link.number)}"/></number>
                                        <name><c:out value="${link.name}"/></name>
                                    </card>
                                </c:forEach>
                            </cards>
                        </c:if>
                        <c:if test="${not empty accountLinks}">
                            <accounts>
                                <c:forEach var="link" items="${accountLinks}">
                                    <account>
                                        <number><c:out value="${phiz:getFormattedAccountNumber(link.number)}"/></number>
                                        <name><c:out value="${link.name}"/></name>
                                    </account>
                                </c:forEach>
                            </accounts>
                        </c:if>
                        <c:if test="${not empty loanLinks}">
                            <loans>
                                <c:forEach var="link" items="${loanLinks}">
                                    <loan>
                                        <number><c:out value="${link.number}"/></number>
                                        <name><c:out value="${link.name}"/></name>
                                    </loan>
                                </c:forEach>
                            </loans>
                        </c:if>
                    </products>
                </c:if>
            </sl:collectionItem>
        </sl:collection>
    </chooseAgreementStage>
</tiles:put>
<tiles:put name="messagesBundle" value="securityBundle"/>
</tiles:insert>
</html:form>