<%@ page import="com.rssl.phizic.common.types.documents.FormType" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:form action="/persons/temlates/list" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="fromStart" value="${form.fromStart}"/>
    <c:set var="viewUrl" value="${phiz:calculateActionURL(pageContext, '/persons/temlates/view.do')}"/>
    <tiles:insert definition="personEdit">
        <tiles:put name="submenu" type="string" value="TemplatesList"/>
        <tiles:put name="needSave" value="false"/>

        <c:set var="bundle" value="employeeTemplatesBundle"/>
        <tiles:put name="pageTitle" type="string">
	        <bean:message key="page.title" bundle="${bundle}"/>
        </tiles:put>

        <%-- ФИЛЬТР --%>
        <tiles:put name="filter" type="string">
            <c:set var="colCount" value="3" scope="request"/>
            <%-- ROW 1 --%>
            <%-- дата создания --%>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="filter.period.creation"/>
                <tiles:put name="bundle" value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                   <bean:message bundle="${bundle}" key="filter.from"/>&nbsp;
                   <span style="font-weight:normal;overflow:visible;cursor:default;">
                       <input type="text"
                               size="10" name="filter(creationDateFrom)"  class="dot-date-pick"
                               value="<bean:write name="form" property="filters.creationDateFrom" format="dd.MM.yyyy"/>"/>
                   </span>
                   &nbsp;<bean:message bundle="${bundle}" key="filter.to"/>&nbsp;
                   <span style="font-weight:normal;cursor:default;">
                       <input type="text"
                               size="10" name="filter(creationDateTo)"  class="dot-date-pick"
                               value="<bean:write name="form" property="filters.creationDateTo" format="dd.MM.yyyy"/>"/>
                   </span>
                </tiles:put>
            </tiles:insert>

            <%-- канал создания --%>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="filter.channel.create"/>
                <tiles:put name="bundle" value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                    <html:select property="filter(createChannelType)" >
                        <html:option value="">Все</html:option>
                        <html:option value="1">ЕРИБ</html:option>
                        <html:option value="3">МП</html:option>
                        <html:option value="4">УС</html:option>
                    </html:select>
                </tiles:put>
            </tiles:insert>

            <%-- вид операции --%>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="filter.operation.formType"/>
                <tiles:put name="bundle" value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                    <html:select property="filter(formType)" >
                        <html:option value="">Все</html:option>
                        <c:forEach items="${form.formTypes}" var="frm">
                            <c:set var="formName">
                                <bean:message bundle="auditBundle" key="paymentform.${frm.name}" failIfNone="false"/>
                            </c:set>
                            <c:set var="formType" value=""/>
                            <c:if test="${not empty formName}">
                                <c:forEach items="${frm.formType}" var="type">
                                    <c:set var="formType">
                                        <c:choose>
                                            <c:when test="${not empty formType}">
                                                <c:out value="${formType},${type}"/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:out value="${type}"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:set>
                                </c:forEach>
                                <html:option value="${formType}">
                                    <c:out value="${formName}"/>
                                </html:option>
                            </c:if>
                        </c:forEach>
                    </html:select>
                </tiles:put>
            </tiles:insert>

            <%-- ROW 2 --%>
            <%-- дата подтверждения --%>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="filter.period.confirm"/>
                <tiles:put name="bundle" value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                   <bean:message bundle="${bundle}" key="filter.from"/>&nbsp;
                   <span style="font-weight:normal;overflow:visible;cursor:default;">
                       <input type="text"
                               size="10" name="filter(confirmDateFrom)"  class="dot-date-pick"
                               value="<bean:write name="form" property="filters.confirmDateFrom" format="dd.MM.yyyy"/>"/>
                   </span>
                   &nbsp;<bean:message bundle="${bundle}" key="filter.to"/>&nbsp;
                   <span style="font-weight:normal;cursor:default;">
                       <input type="text"
                               size="10" name="filter(confirmDateTo)"  class="dot-date-pick"
                               value="<bean:write name="form" property="filters.confirmDateTo" format="dd.MM.yyyy"/>"/>
                   </span>
                </tiles:put>
            </tiles:insert>

            <%-- канал подтверждения --%>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="filter.channel.confirm"/>
                <tiles:put name="bundle" value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                    <html:select property="filter(confirmChannelType)" >
                        <html:option value="">Все</html:option>
                        <html:option value="1">ЕРИБ</html:option>
                        <html:option value="3">МП</html:option>
                        <html:option value="4">УС</html:option>
                    </html:select>
                </tiles:put>
             </tiles:insert>

            <%-- статус --%>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="filter.status"/>
                <tiles:put name="bundle" value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                    <html:select property="filter(status)" >
                        <html:option value="">Все</html:option>
                        <html:option value="DRAFTTEMPLATE,SAVED_TEMPLATE">Черновик</html:option>
                        <html:option value="WAIT_CONFIRM_TEMPLATE">Активный</html:option>
                        <html:option value="TEMPLATE">Сверхлимитный</html:option>
                    </html:select>
                </tiles:put>
            </tiles:insert>

            <%-- ROW 3 --%>
            <%-- дата перевода в статус "сверхлимитный" --%>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="filter.period.statechange"/>
                <tiles:put name="bundle" value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                   <bean:message bundle="${bundle}" key="filter.from"/>&nbsp;
                   <span style="font-weight:normal;overflow:visible;cursor:default;">
                       <input type="text"
                               size="10" name="filter(stateChangeDateFrom)"  class="dot-date-pick"
                               value="<bean:write name="form" property="filters.stateChangeDateFrom" format="dd.MM.yyyy"/>"/>
                   </span>
                   &nbsp;<bean:message bundle="${bundle}" key="filter.to"/>&nbsp;
                   <span style="font-weight:normal;cursor:default;">
                       <input type="text"
                               size="10" name="filter(stateChangeDateTo)"  class="dot-date-pick"
                               value="<bean:write name="form" property="filters.stateChangeDateTo" format="dd.MM.yyyy"/>"/>
                   </span>
                </tiles:put>
            </tiles:insert>

            <%-- подтвердил сотрудник --%>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="filter.employee.statechange"/>
                <tiles:put name="bundle" value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                    <html:text property="filter(employeeStateChange)" size="20" maxlength="50"/>
                </tiles:put>
             </tiles:insert>

            <%-- показывать удаленные --%>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="filter.template.showDeleted"/>
                <tiles:put name="bundle" value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                    <html:checkbox property="filter(showDeleted)"/>
                </tiles:put>
            </tiles:insert>

            <%-- ROW 4 --%>

            <%-- получатель --%>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="filter.receiver.name"/>
                <tiles:put name="bundle" value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                    <html:text property="filter(receiverName)" size="20" maxlength="100"/>
                </tiles:put>
             </tiles:insert>

            <%-- сумма операции --%>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="filter.template.amount"/>
                <tiles:put name="bundle" value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                   <bean:message bundle="${bundle}" key="filter.from"/>&nbsp;
                   <span style="font-weight:normal;overflow:visible;cursor:default;">
                       <input type="text"
                               size="10" name="filter(amountFrom)"
                               value="<bean:write name="form" property="filters.amountFrom"/>"
                               class="moneyField"/>
                   </span>
                   &nbsp;<bean:message bundle="${bundle}" key="filter.to"/>&nbsp;
                   <span style="font-weight:normal;cursor:default;">
                       <input type="text"
                               size="10" name="filter(amountTo)"
                               value="<bean:write name="form" property="filters.amountTo"/>"
                               class="moneyField"/>
                   </span>
                </tiles:put>
            </tiles:insert>

           <tiles:insert definition="filterEmptytField" flush="false"/>

            <%-- ROW 4 --%>
            <%-- название шаблона --%>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="filter.template.name"/>
                <tiles:put name="bundle" value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                    <html:text property="filter(templateName)" size="20" maxlength="100"/>
                </tiles:put>
            </tiles:insert>
            <%-- номер --%>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="filter.template.number"/>
                <tiles:put name="bundle" value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                    <html:text property="filter(templateNumber)" size="20" maxlength="100"/>
                </tiles:put>
             </tiles:insert>

        </tiles:put>

        <tiles:put name="data" type="string">
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="templatesList"/>
                <tiles:put name="grid">
                   <sl:collection id="template" property="data" model="list" bundle="${bundle}">
                       <sl:collectionItem title="filter.period.creation">
                           <a href="${viewUrl}?person=${form.person}&id=${template.id}"><fmt:formatDate value="${template.clientCreationDate.time}" pattern="dd.MM.yyyy HH:mm"/></a>
                       </sl:collectionItem>
                       <sl:collectionItem title="filter.channel.create">
                           <c:set var="createChannelKey" value="label.channel.${template.clientCreationChannel}"/>
                           <bean:message bundle="${bundle}" key="${createChannelKey}"/>
                       </sl:collectionItem>
                       <sl:collectionItem title="filter.template.docnumber">
                           <c:out value="${template.documentNumber}"/>
                       </sl:collectionItem>
                       <sl:collectionItem title="filter.template.name">
                           <c:if test="${template.templateInfo.state.code eq 'REMOVED'}">
                               <img src="${skinUrl}/images/iconSm_delete.gif" alt="Удален ">&nbsp;
                           </c:if>
                           <c:out value="${template.templateInfo.name}"/>
                       </sl:collectionItem>
                       <sl:collectionItem title="filter.period.confirm">
                           <fmt:formatDate value="${template.clientOperationDate.time}" pattern="dd.MM.yyyy HH:mm"/>
                       </sl:collectionItem>
                       <sl:collectionItem title="filter.channel.confirm">
                           <c:if test="${not empty template.clientOperationChannel}">
                               <c:set var="confirmChannelKey" value="label.channel.${template.clientOperationChannel}"/>
                               <bean:message bundle="${bundle}" key="${confirmChannelKey}"/>
                           </c:if>
                       </sl:collectionItem>
                       <sl:collectionItem title="filter.period.statechange">
                           <c:if test="${not empty template.additionalOperationDate}">
                            <fmt:formatDate value="${template.additionalOperationDate.time}" pattern="dd.MM.yyyy HH:mm"/>
                           </c:if>
                       </sl:collectionItem>
                       <sl:collectionItem title="filter.operation.formType">
                           <c:out value="${template.formType.description}"/>
                       </sl:collectionItem>
                       <sl:collectionItem title="filter.status">
                           <c:forEach items="${form.templateConformityStates}" var="conformityState">
                               <c:if test="${not empty template.state && conformityState.status.code == template.state.code && fn:contains(conformityState.formTypesAsString, template.formType)}">
                                   <c:out value="${conformityState.description}"/>
                               </c:if>
                           </c:forEach>
                       </sl:collectionItem>
                       <sl:collectionItem title="label.chargeoff.account">
                           <c:choose>
                               <c:when test="${template.chargeOffResourceType eq 'CARD'}">
                                   <c:out value="${phiz:getCutCardNumber(template.chargeOffResource)}"/>
                               </c:when>
                               <c:otherwise>
                                   <c:out value="${template.chargeOffResource}"/>
                               </c:otherwise>
                           </c:choose>
                       </sl:collectionItem>
                       <sl:collectionItem title="label.destination.account">
                           <c:choose>
                               <c:when test="${template.destinationResourceType eq 'CARD'}">
                                   <c:out value="${phiz:getCutCardNumber(template.destinationResource)}"/>
                               </c:when>
                               <c:otherwise>
                                   <c:out value="${template.destinationResource}"/>
                               </c:otherwise>
                           </c:choose>
                       </sl:collectionItem>
                       <sl:collectionItem title="label.amount">
                           <c:if test="${not empty template.exactAmount}">
                               <fmt:formatNumber value="${template.exactAmount.decimal}" pattern="0.00"/>
                           </c:if>
                       </sl:collectionItem>
                       <sl:collectionItem title="label.currency">
                           <c:if test="${not empty template.exactAmount}">
                               <c:out value="${template.exactAmount.currency.code}"/>
                           </c:if>
                       </sl:collectionItem>
                       <sl:collectionItem title="filter.receiver.name">
                           <c:out value="${template.receiverName}"/>
                       </sl:collectionItem>
                       <sl:collectionItem title="filter.employee.statechange">
                           <c:if test="${not empty template.confirmedEmployeeInfo.personName}">
                               <c:out value="${template.confirmedEmployeeInfo.personName.fullName}"/>
                           </c:if>
                       </sl:collectionItem>
                    </sl:collection>
                    <tiles:put name="isEmpty" value="${empty form.data}"/>
                </tiles:put>
                <tiles:put name="emptyMessage">
                    <bean:message key="list.payment.empty" bundle="${bundle}"/>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>