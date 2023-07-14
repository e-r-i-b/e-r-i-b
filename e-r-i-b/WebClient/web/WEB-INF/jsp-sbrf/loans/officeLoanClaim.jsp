<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html:form action="/private/officeLoanClaim/show" onsubmit="return setEmptyAction(event)">
    <c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="document" value="${form.document}"/>
    <c:set var="mainmenuButton" value="Loans"/>
    <c:set var="isOfficeLoan" value="'true'"/>
    <c:set var="person" value="${phiz:getPersonInfo()}"/>
    <c:set var="mode"  value="${phiz:getUserVisitingMode()}"/>
    <tiles:insert definition="paymentCurrent">
        <tiles:put name="mainmenu" value="${mainmenuButton}" />
        <tiles:put name="pageTitle">Заявка на кредит</tiles:put>
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="title">Заявка на кредит</tiles:put>
                <tiles:put name="showHeader" value="false"/>
                <tiles:put name="customHeader">
                    <tiles:insert page="/WEB-INF/jsp/common/loan/loanClaimParametersBlock.jsp" flush="false">
                        <tiles:put name="document" beanName="form" beanProperty="document"/>
                        <tiles:put name="description" value=""/>
                        <tiles:put name="isOfficeLoan" value="true"/>
                    </tiles:insert>
                </tiles:put>

                <tiles:put name="alignTable" value="center"/>
                <tiles:put name="isServicePayment" value="false"/>
                <tiles:put name="data">
                    <div class="title_common subtitle_1_level tableCellBlock">
                        <bean:message bundle="loanclaimBundle" key="label.office.loan.info.title"/>
                    </div>
                    <tiles:insert definition="formRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="loanclaimBundle" key="label.office.loan.person.title"/>:
                        </tiles:put>
                        <tiles:put name="data">
                           <span class="bold">${phiz:getFormattedPersonFIO(person.firstName, person.surName, person.patrName)}</span>
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="formRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="loanclaimBundle" key="label.office.loan.channel.title"/>:
                        </tiles:put>
                        <tiles:put name="data">
                            <span class="bold"><bean:message bundle="loanclaimBundle" key="label.office.loan.channel.${document.channel}"/></span>
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="loanclaimBundle" key="label.office.loan.payment.type.title"/>:
                        </tiles:put>
                        <tiles:put name="data">
                            <span class="bold"><bean:message bundle="loanclaimBundle" key="label.office.loan.payment.type.${document.paymentType}"/></span>
                        </tiles:put>
                        <tiles:put name="description"><bean:message bundle="loanclaimBundle" key="label.office.loan.payment.type.${document.paymentType}.description"/></tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="loanclaimBundle" key="label.office.loan.vsp.service.title"/>:
                        </tiles:put>
                        <tiles:put name="data">
                            <span class="bold">${form.department.address}</span><br/>
                            <span class="maxValue">${form.department.name}</span><br/><br/>
                            <c:set var="officePhones" value="${form.department.telephone}"/>
                            <c:set var="workHourse" value="${form.department.weekOperationTimeBegin}"/>
                                <div>
                                    <table>
                                        <tr>
                                            <c:if test="${officePhones != null}">
                                                <th>Телефоны</th>
                                            </c:if>
                                            <c:if test="${workHourse != null}">
                                                <th style="padding-left: 15px;">Время работы</th>
                                             </c:if>
                                        </tr>
                                        <tr>
                                            <c:if test="${officePhones != null}">
                                                <td>${officePhones}</td>
                                            </c:if>
                                            <c:if test="${workHourse != null}">
                                                <td style="padding-left: 15px;">${phiz:getWorkTimeInDepartment(form.department)}</td>
                                            </c:if>
                                        </tr>
                                    </table>
                                </div>
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="loanclaimBundle" key="label.office.loan.state.title"/>:
                        </tiles:put>
                        <tiles:put name="data">
                            <div class="stateWidth">
                                <div id="divState" style="width: 220px;">
                                    <a class="blueGrayLinkDotted">
                                        <span onmouseover="showLayer('divState','stateDescription','hand');" onmouseout="hideLayer('stateDescription');">
                                            <c:set var="claimStatus" value="${document.state}"/>
                                            <c:choose>
                                                <c:when test="${claimStatus == 1}">
                                                    Заявка создана успешно
                                                </c:when>
                                                <c:when test="${claimStatus == 2}">
                                                    Кредит одобрен
                                                </c:when>
                                            </c:choose>
                                        </span>
                                    </a>
                                </div>
                            </div>
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="stamp" value="executed"/>
                <tiles:put name="id" value="OfficeLoanClaim"/>
            </tiles:insert>
        </tiles:put>


    </tiles:insert>
</html:form>