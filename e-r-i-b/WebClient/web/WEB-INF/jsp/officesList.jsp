<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%-- Список офисов.

     formAction - акшен, который вызывается.
     hintMessage - сообщение для формы
     userMessage - сообщение для информирования пользователя.
     additionalFilterFields - дополнительные поля для фильтрации
--%>

<tiles:useAttribute name="formAction"/>
<tiles:useAttribute name="hintMessage"/>
<tiles:useAttribute name="userMessage"/>
<tiles:useAttribute name="needParentSynchKey"/>
<tiles:useAttribute name="additionalFilterFields"/>

<html:form action="${formAction}">
    <tiles:importAttribute/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>

    <tiles:insert definition="empty">
        <tiles:put name="data" type="string">
            <script type="text/javascript">

                function sendDepartmentData()
                {
                    var ids = document.getElementsByName("selectedIds");
                    var synchKey = getRadioValue(ids);
                    for (var i = 0; i < ids.length; i++)
                    {
                        if (ids.item(i).checked)
                        {
                            var r = ids.item(i).parentNode.parentNode;
                            var a = new Array(2);
                            a['address'] = trim(r.cells[1].innerHTML);
                            a['name'] = trim(r.cells[2].innerHTML);
                            a['region'] = trim(r.cells[3].innerHTML);
                            a['branch'] = trim(r.cells[4].innerHTML);
                            a['office'] = trim(r.cells[5].innerHTML);
                            a['synchKey'] = synchKey;
                            window.opener.setOfficeInfo(a);
                            window.close();
                            return;
                        }
                    }
                    addError("Выберите подразделение.", "errors");
                }

                function buildOffice(cell)
                {
                    return trim(cell.innerHTML.replace(/&nbsp;/g,'0'));
                }
            </script>

            <div id="workspace">

                <c:if test="${needParentSynchKey == 'true'}">
                    <html:hidden property="parentSynchKey"/>
                </c:if>

                <div id="reference">
                    <tiles:insert page="/WEB-INF/jsp-sbrf/common/layout/messages.jsp" flush="false">
                        <tiles:put name="bundle" type="string" value="${messagesBundle}"/>
                        <c:set var="bundleName" value="${messagesBundle}"/>
                    </tiles:insert>
                    <tiles:insert definition="mainWorkspace" flush="false">
                        <tiles:put name="title">Справочник отделений</tiles:put>
                        <tiles:put name="data">
                            <tiles:insert definition="formHeader" flush="false">
                                <tiles:put name="image" value="${imagePath}/icon_reference.png"/>
                                <tiles:put name="width" value="64px"/>
                                <tiles:put name="height" value="64px"/>
                                <tiles:put name="description">
                                   <c:out value="${hintMessage}"/>
                                </tiles:put>
                            </tiles:insert>
                            <%-- Фильтр --%>
	                        <div id="filterArea">
								<tiles:insert definition="filter" flush="false">
									<tiles:put name="data">
										<table>
											<tr>
												<td width="30px"></td>
												<td width="100px" class="align-center">Поиск&nbsp;</td>
												<td><html:text property="filter(officeInfo)" size="70"/></td>
											</tr>
											<tr><td>&nbsp;</td></tr>
											<tr>
												<td></td>
												<td colspan="2" >Поиск осуществляется по номеру, названию или адресу подразделения</td>
											</tr>
											<tr><td>&nbsp;</td></tr>
										</table>
									</tiles:put>
									<tiles:put name="buttonKey" value="button.filter"/>
									<tiles:put name="buttonBundle" value="dictionaryBundle"/>
								</tiles:insert>
							</div>
                            <%-- /Фильтр --%>
                            <div class="clear"></div>


                            <tiles:insert definition="simpleTableTemplate" flush="false">
                                <tiles:put name="grid">
                                    <sl:collection id="listElement" model="simple-pagination" property="data" >
	                                    <sl:collectionItem title="&nbsp;" styleClass="align-center">
											<html:radio name="form" property="selectedIds" value="${listElement.id}" ondblclick="sendDepartmentData();"/>
										</sl:collectionItem>
                                        <sl:collectionItem title="Адрес" property="address"/>
                                        <sl:collectionItem title="Наименование" property="name"/>
                                        <sl:collectionItem hidden="true" property="code.fields.region"  />
                                        <sl:collectionItem hidden="true" property="code.fields.branch"  />
                                        <sl:collectionItem hidden="true" property="code.fields.office"  />
                                    </sl:collection>
                                </tiles:put>
                                <tiles:put name="isEmpty" value="${empty form.data}"/>
                                <tiles:put name="emptyMessage">
                                    Не найдено ни одного подразделения, соответствующего заданному фильтру
                                </tiles:put>
                            </tiles:insert>
                            <div class="buttonsArea">
                                <tiles:insert definition="clientButton" flush="false">
                                   <tiles:put name="commandTextKey" value="button.cancel"/>
                                   <tiles:put name="commandHelpKey" value="button.cancel"/>
                                   <tiles:put name="bundle"         value="dictionaryBundle"/>
                                   <tiles:put name="onclick"        value="window.close();"/>
                                    <tiles:put name="viewType" value="buttonGrey"/>
                                </tiles:insert>
                                <c:if test="${not empty form.data}">
                                    <tiles:insert definition="clientButton" flush="false">
                                       <tiles:put name="commandTextKey" value="button.choose"/>
                                       <tiles:put name="commandHelpKey" value="button.choose.help"/>
                                       <tiles:put name="bundle"         value="dictionaryBundle"/>
                                       <tiles:put name="onclick"        value="sendDepartmentData();"/>
                                    </tiles:insert>
                                </c:if>
                            </div>
                            <div class="clear"></div>
                        </tiles:put>
                    </tiles:insert>
                </div>

	            <script type="text/javascript">
					<c:if test="${not empty userMessage and userMessage != ''}">
                        addMessage("<c:out value="${userMessage}"/>");
                    </c:if>
	            </script>

                <c:if test="${not empty additionalFilterFields and additionalFilterFields != ''}">
                    <div>${additionalFilterFields}</div>
                </c:if>
            </div>
    </tiles:put>
</tiles:insert>
</html:form>