<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="faqLink" value="${phiz:calculateActionURLWithAnchor(pageContext, '/faq.do', 'p11')}"/>
<html:form action="/login/departments" onsubmit="this.onsubmit = function(){ return false; }; return setEmptyAction();">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="login">
        <tiles:put name="headerGroup" value="true"/>
        <tiles:put name="data" type="string">
            <br/>
            <tiles:insert definition="warningBlock" flush="false">
                <tiles:put name="regionSelector" value="warnings"/>
                <tiles:put name="isDisplayed" value="true"/>
                <tiles:put name="data">
                    <bean:message key="choose.department.message" bundle="securityBundle"
                                  arg0="${form.persons[0].firstName}" arg1="${form.persons[0].patrName}"/>
                </tiles:put>
            </tiles:insert>
            <div class="choose-department">
                <br/>
                <tiles:insert definition="roundBorder" flush="false">
                    <tiles:put name="title">Вход в систему, выбор договора обслуживания</tiles:put>
                    <tiles:put name="data">
                        <p>Для выбора подразделения, в котором открыты интересующие Вас продукты, установите
                            флажок напротив нужного подразделения и нажмите на кнопку &laquo;Войти&raquo;.</p>
                            <tiles:insert definition="simpleTableTemplate" flush="false">
                                <tiles:put name="grid">
                                    <sl:collection id="pers" model="simple-pagination" property="persons"
                                                   bundle="securityBundle">
                                        <sl:collectionParam id="selectType" value="radio"/>
                                        <sl:collectionParam id="selectName" value="selectedIds"/>
                                        <sl:collectionParam id="selectProperty" value="id"/>
                                        <sl:collectionParam id="onRowClick"
                                                            value="selectRow(this,'selectedIds');"/>
                                        <sl:collectionItem title="label.agreement">
                                        <span style="font-weight:bold;">№<c:out
                                                value="${pers.agreementNumber}"/></span><br/>
                                            <c:if test="${pers.agreementDate ne null}">
                                                (<fmt:formatDate value="${pers.agreementDate.time}"
                                                                 pattern="dd MMM yyyy'г.'"/>
                                                <c:if test="${pers.prolongationRejectionDate ne null}">
                                                    -<fmt:formatDate
                                                        value="${pers.prolongationRejectionDate.time}"
                                                        pattern="dd MMM yyyy'г.'"/>
                                                </c:if>)
                                            </c:if>
                                            <c:if test="${form.personBlocked[pers]}">
                                                <br/><span class="text-red">заблокирован</span>
                                            </c:if>
                                        </sl:collectionItem>
                                        <sl:collectionItem title="label.department">
                                            <c:if test="${pers ne null}">
                                                <c:set var="department"
                                                       value="${phiz:getDepartmentById(pers.departmentId)}"/>
                                                <c:set var="terBank"
                                                       value="${phiz:getTerBank(department.code)}"/>
                                                <c:out value="${department.name}"/>,<br/>
                                                <c:out value="${department.address}"/>
                                                <c:if test="${terBank ne null}">
                                                    <br/><c:out value="${terBank.name}"/>
                                                </c:if>
                                            </c:if>
                                        </sl:collectionItem>
                                        <sl:collectionItem title="label.resources">
                                            <c:forEach var="link" items="${form.cardLinks[pers]}">
                                            <span style="font-weight:bold;"><c:out
                                                    value="${phiz:getCutCardNumber(link.number)}"/></span>
                                                <c:out value="${link.name}"/><br/>
                                            </c:forEach>
                                            <c:forEach var="link" items="${form.accountLinks[pers]}">
                                            <span style="font-weight:bold;"><c:out
                                                    value="${phiz:getFormattedAccountNumber(link.number)}"/></span>
                                                <c:out value="${link.name}"/><br/>
                                            </c:forEach>
                                            <c:forEach var="link" items="${form.loanLinks[pers]}">
                                            <span style="font-weight:bold;"><c:out
                                                    value="${link.number}"/></span>
                                                <c:out value="${link.name}"/><br/>
                                            </c:forEach>
                                        </sl:collectionItem>
                                    </sl:collection>
                                </tiles:put>
                            </tiles:insert>
                            <div class="buttonsArea">
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.backToCSA"/>
                                    <tiles:put name="commandHelpKey" value="button.backToCSA.help"/>
                                    <tiles:put name="bundle" value="commonBundle"/>
                                    <tiles:put name="viewType" value="buttonGrey"/>
                                    <tiles:put name="action" value="/login"/>
                                </tiles:insert>
                                <tiles:insert definition="commandButton" flush="false">
                                    <tiles:put name="commandKey" value="button.enter"/>
                                    <tiles:put name="commandHelpKey" value="button.enter"/>
                                    <tiles:put name="bundle" value="securityBundle"/>
                                    <tiles:put name="validationFunction">
                                        function()
                                        {
                                            checkIfOneItem("selectedIds");
                                            return checkOneSelection('selectedIds', 'Выберите один договор');
                                        }
                                    </tiles:put>
                                </tiles:insert>
                                <div class="clear"></div>
                            </div>
                    </tiles:put>
                </tiles:insert>
            </div>
            <br/>
            <tiles:insert definition="roundBorder" flush="false">
                <tiles:put name="title"><bean:message bundle="commonBundle" key="text.SBOL.security.info"/></tiles:put>
                <tiles:put name="data">
                    <div class="loginDataBlock">
                        <div  class="girl_right"><a href="#" onclick="openFAQ('${faqLink}')"><img src="${imagePath}/girl_right.gif" alt="Часто задаваемые вопросы"/></a></div>
                        <div class="security_warning">


                            <p><a href="https://esk.sberbank.ru/esClient/_ns/ProtectInfoPage.aspx" target="_blank">
                                <bean:message bundle="commonBundle" key="text.SBOL.security"/></a>
                            </p>

                            <p><a href="http://sberbank.ru/ru/person/dist_services/warning/"
                                  class="paperEnterLink" target="_blank">О рисках при дистанционном банковском
                                обслуживании</a></p>

                            <p class="FAQ" style="font-weight:bold;font-style:italic;">
                                <a href="#" onclick="openFAQ('${faqLink}')">Часто
                                    задаваемые вопросы</a></p>
                        </div>
                        <div class="clear"></div>
                    </div>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>