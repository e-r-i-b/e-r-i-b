<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:form action="/persons/temlates/showsettings" onsubmit="return setEmptyAction(event);">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<tiles:insert definition="personEdit">
    <c:set var="bundle" value="employeeTemplatesBundle"/>
    <c:set var="channel" value="${form.fields.channel}"/>
    <c:set var="availableLabelKey" value="label.available.for.${channel}"/>
    <c:set var="unavailableLabelKey" value="label.unavailable.for.${channel}"/>
    <c:set var="availableMessage"><bean:message key="${availableLabelKey}" bundle="${bundle}"/></c:set>
    <c:set var="unavailableMessage"><bean:message key="${unavailableLabelKey}" bundle="${bundle}"/></c:set>
    <c:set var="searchPage" value="${form.searchPage}"/>
    <c:set var="itemsPerPage" value="${form.itemsPerPage}"/>
    <c:set var="sizeResult" value="${fn:length(form.data)}"/>
    <c:set var="pageItems">
        <c:choose>
            <c:when test="${sizeResult<itemsPerPage}">
                ${sizeResult}
            </c:when>
            <c:otherwise>
                ${itemsPerPage}
            </c:otherwise>
        </c:choose>
    </c:set>
    <c:set var="viewUrl" value="${phiz:calculateActionURL(pageContext, '/persons/temlates/view.do')}"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>

    <tiles:put name="submenu" type="string" value="${channel}"/>
    <tiles:put name="needSave" value="false"/>
    <c:set var="titleKey" value="page.channel.title.${channel}"/>

    <tiles:put name="menu" type="string">
        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey" value="button.list"/>
            <tiles:put name="commandHelpKey" value="button.list.help"/>
            <tiles:put name="bundle" value="${bundle}"/>
            <tiles:put name="image" value=""/>
            <tiles:put name="action" value="/persons/temlates/list.do?person=${form.person}"/>
            <tiles:put name="viewType" value="blueBorder"/>
        </tiles:insert>
    </tiles:put>

    <%-- ДАННЫЕ --%>
    <tiles:put name="data" type="string">
        <tiles:insert definition="tableTemplate" flush="false">
            <tiles:put name="buttons">
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey" value="button.save"/>
                    <tiles:put name="commandHelpKey" value="button.save.help"/>
                    <tiles:put name="bundle" value="${bundle}"/>
                    <tiles:put name="isDefault" value="true"/>
                    <tiles:put name="postbackNavigation" value="true"/>
                </tiles:insert>
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.cancel"/>
                    <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                    <tiles:put name="bundle" value="${bundle}"/>
                    <tiles:put name="image" value=""/>
                    <tiles:put name="action"
                               value="/persons/temlates/showsettings.do?person=${form.person}&field(channel)=${channel}"/>
                </tiles:insert>
            </tiles:put>
            <tiles:put name="id" value="templatesList"/>
            <tiles:put name="text"><bean:message key="${titleKey}" bundle="${bundle}"/></tiles:put>
            <tiles:put name="data">
            <c:set var="templates" value="${form.data}"/>
            <!-- заголовок списка -->
            <tr class="tblInfHeader">
                <td width="30px" align="center">
                    <html:checkbox property="field(selectAll)" style="border:none" styleId="checkbx_selectAll"
                                   onchange="switchSelection('field(selectAll)'); changeVisibilityLabelForAll();"/>
                </td>
                <td align="center">№</td>
                <td align="center"><bean:message key="label.created" bundle="${bundle}"/></td>
                <td align="center"><bean:message key="filter.template.name" bundle="${bundle}"/></td>
                <td align="center"><bean:message key="filter.status" bundle="${bundle}"/></td>
                <td align="center">&nbsp;</td>
                <td align="center"><bean:message key="label.amount" bundle="${bundle}"/></td>
                <td align="center"><bean:message key="label.visibility" bundle="${bundle}"/></td>
            </tr>
            <!-- строки списка -->
            <% int lineNumber = 0;%>
            <c:set var="endList">
                <c:choose>
                    <c:when test="${sizeResult > itemsPerPage}">
                        ${itemsPerPage -1}
                    </c:when>
                    <c:otherwise>
                        ${sizeResult - 1}
                    </c:otherwise>
                </c:choose>
            </c:set>
            <script type="text/javascript">
                function changeVisibilityLabel(templateId)
                {
                    var checkbx = document.getElementById('chkbx_' + templateId);
                    var label = document.getElementById('visibility_' + templateId);
                    if (checkbx.checked)
                        label.innerHTML = '&nbsp;<c:out value="${availableMessage}"/>';
                    else
                        label.innerHTML = '&nbsp;<c:out value="${unavailableMessage}"/>';
                };

                function changeVisibilityLabelForAll()
                {
                    var checkbx = document.getElementById('checkbx_selectAll');
                    <c:if test="${sizeResult != 0}">
                        <c:forEach var="i" begin="0" end="${endList}">
                            <c:if test="${templates[i].state.code !='DRAFTTEMPLATE' && templates[i].state.code !='SAVED_TEMPLATE'}">
                                var label = document.getElementById('visibility_' + ${templates[i].id});
                                if (label != null)
                                {
                                    if (checkbx.checked)
                                        label.innerHTML = '&nbsp;<c:out value="${availableMessage}"/>';
                                    else
                                        label.innerHTML = '&nbsp;<c:out value="${unavailableMessage}"/>';
                                }
                            </c:if>
                        </c:forEach>
                    </c:if>
                };
            </script>
            <c:if test="${sizeResult!=0}">
                <c:forEach var="i" begin="0" end="${endList}">
                    <c:set var="listElement" value="${templates[i]}"/>
                    <% lineNumber++;%>
                    <c:set var="visibility">
                        <c:choose>
                            <c:when test="${channel == 'mobile'}">
                                ${listElement.templateInfo.useInMAPI}
                            </c:when>
                            <c:when test="${channel == 'atm'}">
                                ${listElement.templateInfo.useInATM}
                            </c:when>
                            <c:when test="${channel == 'sms'}">
                                ${listElement.templateInfo.useInERMB}
                            </c:when>
                            <c:when test="${channel == 'internet'}">
                                ${listElement.templateInfo.useInERIB}
                            </c:when>
                        </c:choose>
                    </c:set>
                    <tr class="ListLine<%=lineNumber%2%>" style="padding:2">
                        <c:set var="chkBox">
                            <input type="checkbox" value="${listElement.id}"
                                   onchange="changeVisibilityLabel('${listElement.id}');"
                                   id="chkbx_${listElement.id}"
                            <c:choose>
                            <c:when test="${listElement.state.code =='DRAFTTEMPLATE' || listElement.state.code =='SAVED_TEMPLATE'}">
                                   disabled="true"
                            </c:when>
                            <c:otherwise>
                            <c:if test="${visibility}"> checked="true" </c:if>
                                   name="selectedIds"
                            </c:otherwise>
                            </c:choose>
                            >
                        </c:set>
                        <td align=center class="ListItem" width="20px">
                            ${chkBox}
                        </td>
                        <td class="ListItem">&nbsp;${listElement.documentNumber}</td>
                        <td class="ListItem" align="center">
                            &nbsp;<fmt:formatDate value="${listElement.clientCreationDate.time}" pattern="dd.MM.yyyy"/>
                        </td>
                        <td class="ListItem">&nbsp;<a
                                href="${viewUrl}?person=${form.person}&id=${listElement.id}">${listElement.templateInfo.name}</a>
                        </td>
                        <td class="ListItem">
                            <c:set var="stateDescription">
                                <c:forEach items="${form.templateConformityStates}" var="conformityState">
                                    <c:if test="${conformityState.status.code == listElement.state.code && fn:contains(conformityState.formTypesAsString, listElement.formType)}">
                                        ${conformityState.description}
                                    </c:if>
                                </c:forEach>
                            </c:set>
                            <c:choose>
                                <c:when test="${listElement.state.code =='DRAFTTEMPLATE' || listElement.state.code =='SAVED_TEMPLATE'}">
                                    <div style="color:#898989;">
                                        &nbsp;<c:out value="${stateDescription}"/>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    &nbsp;<c:out value="${stateDescription}"/>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td class="ListItem" align="center" width="30px">
                            <c:choose>
                                <c:when test="${channel == 'mobile'}">
                                    <img border="0" alt="" width="10" height="15" src="${imagePath}/icon-mobile.gif"/>
                                </c:when>
                                <c:when test="${channel == 'atm'}">
                                    <img border="0" alt="" width="16" height="16" src="${imagePath}/icon-atm.gif"/>
                                </c:when>
                                <c:when test="${channel == 'sms'}">
                                    <img border="0" alt="" width="23" height="16" src="${imagePath}/icon_sms.gif"/>
                                </c:when>
                                <c:when test="${channel == 'internet'}">
                                    <img border="0" alt="" width="16" height="15" src="${imagePath}/icon-logo.png"/>
                                </c:when>
                            </c:choose>
                        </td>
                        <td class="ListItem">
                            <c:choose>
                                <c:when test="${listElement.inputSumType == 'CHARGEOFF'}">
                                    &nbsp;<c:out value="${phiz:formatAmount(listElement.chargeOffAmount)}"/>
                                </c:when>
                                <c:otherwise>
                                    &nbsp;<c:out value="${phiz:formatAmount(listElement.destinationAmount)}"/>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td class="ListItem">
                            <c:choose>
                                <c:when test="${visibility}">
                                    <div id="visibility_${listElement.id}">
                                        &nbsp;<c:out value="${availableMessage}"/>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div id="visibility_${listElement.id}">
                                        &nbsp;<c:out value="${unavailableMessage}"/>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${searchPage > 0 || sizeResult > 10}">
                    <input type="hidden" id="searchPage" name="searchPage" value="${searchPage}"/>
                    <table cellspacing="0" cellpadding="0" class="tblNumRec">
                        <tr><td colspan="6">&nbsp;</td></tr>
                        <tr>
                            <td style="width:50%;">&nbsp;</td>
                            <td nowrap="nowrap" style="border:0;">
                                <c:choose>
                                    <c:when test="${searchPage>0}">
                                        <a href="#" onclick="setElement('searchPage', ${searchPage-1});
                                                callOperation(event,'button.filter'); return false" href="#">
                                            <div class="activePaginLeftArrow"></div>
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="inactivePaginLeftArrow"></div>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td nowrap="nowrap" style="padding: 5px;">
                            <span class="tblNumRecIns">
                                ${searchPage * itemsPerPage + 1}
                                &nbsp;-&nbsp;
                                ${searchPage * itemsPerPage + pageItems}
                            </span>
                            </td>
                            <td nowrap="nowrap" style="border:0;">
                                <c:choose>
                                    <c:when test="${itemsPerPage < sizeResult}">
                                        <a href="#" onclick="setElement('searchPage', ${searchPage+1});
                                                callOperation(event,'button.filter'); return false">
                                            <div class="activePaginRightArrow"></div>
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="inactivePaginRightArrow"></div>
                                    </c:otherwise>
                                </c:choose>
                            </td>

                            <td nowrap="nowrap" align="right" width="100%">
                                <div>
                                    <div class="floatRight">
                                        <div class="float paginationItemsTitle">Показывать по:</div>
                                        <input type="hidden" name="itemsPerPage" value="${itemsPerPage}"/>
                                        <c:choose>
                                            <c:when test="${itemsPerPage == 10}">
                                                <div class="float ${phiz:isIE() ? "circleImage" : "circle"}" style="display:inline-block"><div class="greenSelector"><span style="padding-right:2px;">10</span></div></div>
                                                <div class="paginationSize float"><a onclick="addElementToForm('itemsPerPage', '20'); setElement('itemsPerPage', '20'); callOperation(event,'button.filter');" href="#" style="padding-right:2px;">20</a></div>
                                                <div class="paginationSize float"><a onclick="addElementToForm('itemsPerPage', '50'); setElement('itemsPerPage', '50'); callOperation(event,'button.filter');" href="#" style="padding-right:2px;">50</a></div>
                                            </c:when>
                                            <c:when test="${itemsPerPage == 20}">
                                                <div class="paginationSize float"><a onclick="addElementToForm('itemsPerPage', '10'); setElement('itemsPerPage', '10'); callOperation(event,'button.filter');" href="#" style="padding-right:2px;">10</a></div>
                                                <div class="float ${phiz:isIE() ? "circleImage" : "circle"}" style="display:inline-block"><div class="greenSelector"><span style="padding-right:2px;">20</span></div></div>
                                                <div class="paginationSize float"><a onclick="addElementToForm('itemsPerPage', '50'); setElement('itemsPerPage', '50'); callOperation(event,'button.filter');" href="#" style="padding-right:2px;">50</a></div>
                                            </c:when>
                                            <c:when test="${itemsPerPage == 50}">
                                                <div class="paginationSize float"><a onclick="addElementToForm('itemsPerPage', '10'); setElement('itemsPerPage', '10'); callOperation(event,'button.filter');" href="#" style="padding-right:2px;">10</a></div>
                                                <div class="paginationSize float"><a onclick="addElementToForm('itemsPerPage', '20'); setElement('itemsPerPage', '20'); callOperation(event,'button.filter');" href="#" style="padding-right:2px;">20</a></div>
                                                <div class="float ${phiz:isIE() ? "circleImage" : "circle"}" style="display:inline-block"><div class="greenSelector"><span style="padding-right:2px;">50</span></div></div>
                                            </c:when>
                                        </c:choose>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </table>
                </c:if>

            </c:if>
            </tiles:put>
            <tiles:put name="isEmpty" value="${sizeResult == 0}"/>
            <tiles:put name="emptyMessage">
                <bean:message key="list.payment.empty" bundle="${bundle}"/>
            </tiles:put>
        </tiles:insert>
    </tiles:put>
</tiles:insert>
</html:form>