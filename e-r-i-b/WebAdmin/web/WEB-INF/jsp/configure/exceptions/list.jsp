<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<c:set var="form" value="${ExceptionEntryListForm}"/>
<c:set var="exceptionEntryType" value="${form.exceptionEntryType}"/>
<c:choose>
    <c:when test="${exceptionEntryType eq 'internal'}">
        <c:set var="currentAction" value="/configure/exceptions/list"/>
        <c:set var="editAction" value="/configure/exceptions/edit"/>
    </c:when>
    <c:otherwise>
        <c:set var="currentAction" value="/configure/exceptions/external/list"/>
        <c:set var="editAction" value="/configure/exceptions/external/edit"/>
    </c:otherwise>
</c:choose>
<html:form action="${currentAction}" onsubmit="return setEmptyAction(event);">
    <c:set var="pageType" value="logMain"/>
    <c:if test="${form.dictionary}">
        <c:set var="pageType" value="dictionary"/>
    </c:if>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <tiles:insert definition="${pageType}">
        <c:choose>
            <c:when test="${exceptionEntryType eq 'internal'}">
                <tiles:put name="submenu" type="string" value="ExceptionEntryList"/>
            </c:when>
            <c:otherwise>
                <tiles:put name="submenu" type="string" value="ExternalExceptionEntryList"/>
            </c:otherwise>
        </c:choose>
        <tiles:put name="pageTitle" type="string">
            <bean:message key="label.${exceptionEntryType}.list.header" bundle="exceptionEntryBundle"/>
        </tiles:put>
        <tiles:put name="menu" type="string">
            <c:if test="${pageType == 'logMain'}">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey"    value="button.add"/>
                    <tiles:put name="commandHelpKey"    value="button.add.help"/>
                    <tiles:put name="bundle"            value="exceptionEntryBundle"/>
                    <tiles:put name="action"            value="${editAction}"/>
                    <tiles:put name="viewType" value="blueBorder"/>
                </tiles:insert>
            </c:if>
        </tiles:put>
        <tiles:put name="filter" type="string">
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label"  value="label.${exceptionEntryType}.filter.id"/>
                <tiles:put name="bundle" value="exceptionEntryBundle"/>
                <tiles:put name="name"   value="id"/>
            </tiles:insert>
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label"     value="label.${exceptionEntryType}.filter.operationType"/>
                <tiles:put name="bundle"    value="exceptionEntryBundle"/>
                <tiles:put name="name"      value="operationType"/>
                <tiles:put name="maxlength" value="50"/>
            </tiles:insert>
            <c:if test="${exceptionEntryType eq 'internal'}">
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="label.${exceptionEntryType}.filter.application"/>
                <tiles:put name="bundle" value="exceptionEntryBundle"/>
                <tiles:put name="data">
                    <html:select property="filter(application)" styleClass="select">
                        <html:option value="">Все</html:option>
                        <html:option value="PhizIA"><bean:message key="application.PhizIA" bundle="exceptionEntryBundle"/></html:option>
                        <html:option value="PhizIC"><bean:message key="application.PhizIC" bundle="exceptionEntryBundle"/></html:option>
                        <html:option value="mobile5"><bean:message key="application.mobile5" bundle="exceptionEntryBundle"/></html:option>
                        <html:option value="mobile6"><bean:message key="application.mobile6" bundle="exceptionEntryBundle"/></html:option>
                        <html:option value="mobile7"><bean:message key="application.mobile7" bundle="exceptionEntryBundle"/></html:option>
                        <html:option value="mobile8"><bean:message key="application.mobile8" bundle="exceptionEntryBundle"/></html:option>
                        <html:option value="mobile9"><bean:message key="application.mobile9" bundle="exceptionEntryBundle"/></html:option>
                        <html:option value="atm"><bean:message key="application.atm" bundle="exceptionEntryBundle"/></html:option>
                        <html:option value="socialApi"><bean:message key="application.socialApi" bundle="exceptionEntryBundle"/></html:option>
                        <html:option value="WebAPI"><bean:message key="application.WebAPI" bundle="exceptionEntryBundle"/></html:option>
                        <c:if test="${exceptionEntryType eq 'external'}">
                            <html:option value="Gate"><bean:message key="application.Gate" bundle="exceptionEntryBundle"/></html:option>
                            <html:option value="Scheduler"><bean:message key="application.Scheduler" bundle="exceptionEntryBundle"/></html:option>
                        </c:if>
                    </html:select>
                </tiles:put>
            </tiles:insert>
            </c:if>
            <c:if test="${exceptionEntryType eq 'external'}">
                <tiles:insert definition="filterTextField" flush="false">
                    <tiles:put name="label"     value="label.${exceptionEntryType}.filter.error"/>
                    <tiles:put name="bundle"    value="exceptionEntryBundle"/>
                    <tiles:put name="name"      value="errorCode"/>
                    <tiles:put name="maxlength" value="20"/>
                </tiles:insert>
                <tiles:insert definition="filterEntryField" flush="false">
                    <tiles:put name="label"     value="label.${exceptionEntryType}.filter.system"/>
                    <tiles:put name="bundle"    value="exceptionEntryBundle"/>
                    <tiles:put name="data">
                        <html:text property="filter(systemName)" styleClass="select"/>
                        <input id="externalSystemSelectButton" type="button" class="buttWhite smButt" onclick="openAdaptersDictionary(setAdapterInfo);" value="..."/>
                        <html:hidden property="filter(system)"/>
                    </tiles:put>
                </tiles:insert>
            </c:if>
        </tiles:put>
        <tiles:put name="data" type="string">
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="buttons">
                    <c:choose>
                        <c:when test="${pageType == 'logMain'}">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.edit"/>
                                <tiles:put name="commandHelpKey" value="button.edit.help"/>
                                <tiles:put name="bundle"         value="exceptionEntryBundle"/>
                                <tiles:put name="onclick"        value="editExceptionEntry();"/>
                            </tiles:insert>

                            <tiles:insert definition="commandButton" flush="false">
                                <tiles:put name="commandKey"     value="button.remove"/>
                                <tiles:put name="commandHelpKey" value="button.remove.help"/>
                                <tiles:put name="bundle"         value="exceptionEntryBundle"/>
                                <tiles:put name="validationFunction">
                                    checkSelection('selectedIds', 'Выберите записи для удаления')
                                </tiles:put>
                            </tiles:insert>

                            <c:url var="syncUrl" value="/configure/exceptions/synchronize.do">
                                <c:param name="exceptionEntryType" value="${exceptionEntryType}"/>
                            </c:url>
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.sync"/>
                                <tiles:put name="commandHelpKey" value="button.sync.help"/>
                                <tiles:put name="bundle" value="exceptionEntryBundle"/>
                                <tiles:put name="onclick" value="window.open('${syncUrl}', 'ExceptionEntrySynchronize','resizable=1,menubar=0,toolbar=0,scrollbars=1,width=800,height=500');"/>
                            </tiles:insert>
                        </c:when>
                        <c:when test="${pageType == 'dictionary'}">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.cancel"/>
                                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                                <tiles:put name="bundle"         value="exceptionEntryBundle"/>
                                <tiles:put name="onclick"        value="window.close();"/>
                            </tiles:insert>

                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.select"/>
                                <tiles:put name="commandHelpKey" value="button.select.help"/>
                                <tiles:put name="bundle"         value="exceptionEntryBundle"/>
                                <tiles:put name="onclick"        value="selectException();"/>
                            </tiles:insert>
                        </c:when>
                    </c:choose>
                </tiles:put>
                <tiles:put name="grid">
                    <sl:collection id="exceptionEntry" property="data" model="list" bundle="exceptionEntryBundle">
                        <sl:collectionParam id="selectType" value="checkbox"/>
                        <sl:collectionParam id="selectName" value="selectedIds"/>
                        <sl:collectionParam id="selectProperty" value="id"/>
                        <sl:collectionItem title="grid.label.${exceptionEntryType}.header.id">
                            <c:out value="${exceptionEntry.id}"/>
                        </sl:collectionItem>
                        <c:if test="${exceptionEntryType eq 'external'}">
                            <sl:collectionItem title="grid.label.${exceptionEntryType}.header.error">
                                <c:out value="${exceptionEntry.errorCode}"/>
                            </sl:collectionItem>
                            <sl:collectionItem title="grid.label.${exceptionEntryType}.header.system">
                                <c:out value="${phiz:getSystemName(exceptionEntry.system)}"/>
                            </sl:collectionItem>
                        </c:if>
                        <sl:collectionItem title="grid.label.${exceptionEntryType}.header.operationType">
                            <c:out value="${exceptionEntry.operation}"/>
                        </sl:collectionItem>
                        <sl:collectionItem title="grid.label.${exceptionEntryType}.header.detail">
                            <img class="plusImage pointer" src='${imagePath}/iconSm_triangleDown.gif' border='0'/>
                            <div class="displayNone">
                                <pre><c:out value="${exceptionEntry.detail}"/></pre>
                            </div>
                        </sl:collectionItem>
                        <c:if test="${exceptionEntryType == 'internal'}">
                            <sl:collectionItem title="grid.label.${exceptionEntryType}.header.application">
                                <c:if test="${not empty exceptionEntry}">
                                    <bean:message key="application.${exceptionEntry.application}" bundle="exceptionEntryBundle"/>
                                </c:if>
                            </sl:collectionItem>
                        </c:if>
                    </sl:collection>
                </tiles:put>
                <tiles:put name="emptyMessage">
                    <bean:message key="grid.label.${exceptionEntryType}.emptyMessage" bundle="exceptionEntryBundle"/>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
    <script type="text/javascript">
        function setAdapterInfo ( externalSystem )
        {
            $('[name=filter(systemName)]').val(externalSystem["name"]);
            $('[name=filter(system)]').val(externalSystem["UUID"]);
        }
        <c:choose>
            <c:when test="${pageType == 'logMain'}">
                <c:set var="url" value="${phiz:calculateActionURL(pageContext, editAction)}"/>
                function editExceptionEntry()
                {
                    checkIfOneItem("selectedIds");
                    if (!checkSelection("selectedIds", 'Укажите одну запись') || (!checkOneSelection("selectedIds", 'Укажите только одну запись')))
                        return;
                    var id = getRadioValue(document.getElementsByName("selectedIds"));
                    window.location = '${url}?id=' + id;
                }
            </c:when>
            <c:when test="${pageType == 'dictionary'}">
                function selectException()
                {
                    var ids = document.getElementsByName("selectedIds");
                    for (var i = 0; i < ids.length; i++)
                    {
                        if (ids.item(i).checked)
                        {
                            var r = ids.item(i).parentNode.parentNode;
                            <c:choose>
                                <c:when test="${exceptionEntryType eq 'external'}">
                                    var exceptionEntry = new Array(6);
                                    exceptionEntry['errorCode'] = trim(getElementTextContent(r.cells[2]));
                                    exceptionEntry['system'] = trim(getElementTextContent(r.cells[3]));
                                    <c:set var="offset" value="${2}"/>
                                </c:when>
                                <c:otherwise>
                                    var exceptionEntry = new Array(4);
                                    <c:set var="offset" value="${0}"/>
                                </c:otherwise>
                            </c:choose>
                            exceptionEntry['id'] = trim(ids.item(i).value);
                            exceptionEntry['operation'] = trim(getElementTextContent(r.cells[${offset + 2}]));
                            exceptionEntry['detail'] = trim(getElementTextContent(r.cells[${offset + 3}]));
                            <c:if test="${exceptionEntryType eq 'internal'}">
                            exceptionEntry['application'] = trim(getElementTextContent(r.cells[${offset + 4}]));
                            </c:if>
                            window.opener.setExceptionEntryInfo(exceptionEntry);
                            window.close();
                            return;
                        }
                    }
                    alert('Выберите ошибку');
                }
            </c:when>
        </c:choose>

        $('.plusImage').click(
            function()
            {
                var div = $(this).parent().find('div');
                if(div.is(":visible"))
                {
                    div.hide();
                    $(this).attr("src",'${imagePath}/iconSm_triangleDown.gif');
                }
                else
                {
                    div.show();
                    $(this).attr("src",'${imagePath}/iconSm_triangleUp.gif');
                }
            }
        );
    </script>
</html:form>