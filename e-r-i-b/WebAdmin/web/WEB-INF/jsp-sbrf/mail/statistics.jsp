<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:form action="/mail/statistics" onsubmit="return setEmptyAction(event)">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="mailMain">
        <tiles:put name="needSave" value="false"/>
        <tiles:put name="submenu" type="string" value="MailStatistics"/>

        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name">
                    <bean:message key="statistic.title" bundle="mailBundle"/>
                </tiles:put>
                <tiles:put name="data">

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.fromDate" bundle="mailBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            с  <input value='<bean:write name="org.apache.struts.taglib.html.BEAN" property="fields(fromDate)" format="dd.MM.yyyy"/>'
                                   name="fields(fromDate)" class="dot-date-pick"
                                   size="10"/>
                            по  <input value='<bean:write name="org.apache.struts.taglib.html.BEAN" property="fields(toDate)" format="dd.MM.yyyy"/>'
                                   name="fields(toDate)" class="dot-date-pick"
                                   size="10"/>
                            <script type="text/javascript">
                                addClearMasks(null,
                                        function(event)
                                        {
                                            clearInputTemplate('field(fromDate)', '__.__.____');
                                            clearInputTemplate('field(toDate)', '__.__.____');
                                        });
                                <c:if test="${form.fields.relocateToDownload != null && form.fields.relocateToDownload == 'true'}">
                                $(document).ready(
                                        function()
                                        {
                                            <c:set var="downloadFileURL" value="${phiz:calculateActionURL(pageContext,'/download')}?fileType=MailStatisticsFileType&clientFileName=${form.fields.clientFileName}"/>
                                            clientBeforeUnload.showTrigger=false;
                                            goTo('${downloadFileURL}');
                                            clientBeforeUnload.showTrigger=false;
                                        });
                                </c:if>
                            </script>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.status" bundle="mailBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <table width="380px">
                                <tr>
                                    <td>
                                        <html:checkbox name="form" property="field(NEW)" value="true">
                                            <bean:message bundle="mailBundle"  key="label.statusNew"/>
                                        </html:checkbox>
                                    </td>
                                    <td>
                                        <html:checkbox name="form" property="field(NEW_EPLOYEE_MAIL)" value="true">
                                            <bean:message  bundle="mailBundle" key="label.statusNewEmplM"/>
                                        </html:checkbox>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <html:checkbox name="form" property="field(READ)" value="true">
                                            <bean:message bundle="mailBundle" key="label.statusReceived"/>
                                        </html:checkbox>
                                    </td>
                                    <td>
                                        <html:checkbox name="form" property="field(ANSWER_EPLOYEE_MAIL)" value="true">
                                            <bean:message  bundle="mailBundle" key="label.statusAnswerEmpl"/>
                                        </html:checkbox>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <html:checkbox name="form" property="field(DRAFT)" value="true">
                                            <bean:message  bundle="mailBundle" key="label.statusDraft"/>
                                        </html:checkbox>
                                    </td>
                                    <td>
                                        <html:checkbox name="form" property="field(NONE)" value="true">
                                            <bean:message  bundle="mailBundle" key="label.statusNone"/>
                                        </html:checkbox>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <html:checkbox name="form" property="field(ANSWER)" value="true">
                                            <bean:message bundle="mailBundle" key="label.statusAnswer"/>
                                        </html:checkbox>
                                    </td>
                                    <td>
                                        <html:checkbox name="form" property="field(DELETED)" value="true">
                                            <bean:message bundle="mailBundle" key="label.status.deleted"/>
                                        </html:checkbox>
                                    </td>
                                </tr>
                            </table>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.messageType" bundle="mailBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <table width="500px">
                                <tr>
                                    <td>
                                        <html:checkbox name="form" property="field(CONSULTATION)" value="true">
                                            <bean:message bundle="mailBundle"  key="mailType.CONSULTATION"/>
                                        </html:checkbox>
                                    </td>
                                    <td>
                                        <html:checkbox name="form" property="field(GRATITUDE)" value="true">
                                            <bean:message bundle="mailBundle" key="mailType.GRATITUDE"/>
                                        </html:checkbox>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <html:checkbox name="form" property="field(COMPLAINT)" value="true">
                                            <bean:message bundle="mailBundle" key="mailType.COMPLAINT"/>
                                        </html:checkbox>
                                    </td>
                                    <td>
                                        <html:checkbox name="form" property="field(IMPROVE)" value="true">
                                            <bean:message  bundle="mailBundle" key="mailType.IMPROVE"/>
                                        </html:checkbox>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <html:checkbox name="form" property="field(CLAIM)" value="true">
                                            <bean:message  bundle="mailBundle" key="mailType.CLAIM"/>
                                        </html:checkbox>
                                    </td>
                                    <td>
                                        <html:checkbox name="form" property="field(OTHER)" value="true">
                                            <bean:message  bundle="mailBundle" key="mailType.OTHER"/>
                                        </html:checkbox>
                                    </td>
                                </tr>
                            </table>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.mail.theme" bundle="mailBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:select property="fields(theme)" styleClass="select">
                                <html:option value=""><bean:message key="label.All" bundle="mailBundle"/></html:option>
                                <c:forEach items="${phiz:getAllMailSubjects()}" var="theme">
                                    <html:option value="${theme.id}">${theme.description}</html:option>
                                </c:forEach>
                            </html:select>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.responceMethod" bundle="mailBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:select property="fields(response_method)" styleClass="select">
                                <html:option value=""><bean:message key="label.All" bundle="mailBundle"/></html:option>
                                <html:option value="BY_PHONE"><bean:message key="method.by_phone" bundle="mailBundle"/></html:option>
                                <html:option value="IN_WRITING"><bean:message key="method.in_writing" bundle="mailBundle"/></html:option>
                            </html:select>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.tb" bundle="mailBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <table>
                                <tr>
                                    <c:forEach var="TB" items="${phiz:getAllowedTB()}" varStatus="cursor">
                                        <c:if test="${cursor.index ne 0 and cursor.index % 2 eq 0}">
                                            </tr>
                                            <tr>
                                        </c:if>
                                        <td>
                                            <c:set var="tbRegion"><c:out value="${TB.region}"/></c:set>
                                            <input type="checkbox" name="userTBs" <c:if test="${phiz:containsInArray(form.userTBs, tbRegion)}">checked='checked'</c:if> value="${tbRegion}">
                                            <c:out value="${TB.name}"/>
                                        </td>
                                    </c:forEach>
                                </tr>
                            </table>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.kc" bundle="mailBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <c:forEach var="area" items="${form.fields.areas}">
                                <c:set var="areaUUID"><c:out value="${area.uuid}"/></c:set>
                                <input type="checkbox" name="areaUUIDs" <c:if test="${phiz:containsInArray(form.areaUUIDs, areaUUID)}">checked='checked'</c:if> value="${areaUUID}">
                                <c:out value="${area.name}"/>
                            </c:forEach>
                        </tiles:put>
                    </tiles:insert>

                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false">
						<tiles:put name="commandTextKey"     value="button.clear"/>
						<tiles:put name="commandHelpKey" value="button.clear.help"/>
						<tiles:put name="bundle"  value="mailBundle"/>
						<tiles:put name="onclick" value="javascript:clearFields($('.pmntData')[0])"/>
					</tiles:insert>
                    <tiles:insert definition="commandButton" flush="false" operation="MailStatisticsOperation">
                       <tiles:put name="commandKey" value="button.filter"/>
                       <tiles:put name="commandHelpKey" value="button.filter.help"/>
                       <tiles:put name="bundle" value="mailBundle"/>
                       <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>

            <div class="clear"></div>

            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="MailStatistics"/>
                <tiles:put name="text">
                    <bean:message key="label.client.mails" bundle="mailBundle"/>&nbsp;
                    <bean:message key="label.from" bundle="mailBundle"/>&nbsp;${form.fields.fromDate}&nbsp;
                    <bean:message key="label.to" bundle="mailBundle"/>&nbsp;${form.fields.toDate}
                    (<bean:message key="label.first.letter" bundle="mailBundle"/>&nbsp;<fmt:formatDate value="${form.firstMailDate.time}" pattern="dd.MM.yyyy"/>)
                    <br/>
                    <bean:message key="label.avg.answer" bundle="mailBundle"/>&nbsp;${form.averageTime}
                </tiles:put>

                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false" operation="EmployeeMailStatisticsExportOperation">
                        <tiles:put name="commandKey" value="button.export.employee"/>
                        <tiles:put name="commandHelpKey" value="button.export.employee.help"/>
                        <tiles:put name="bundle" value="mailBundle"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false" operation="MailStatisticsExportOperation">
                        <tiles:put name="commandKey" value="button.export.excel"/>
                        <tiles:put name="commandHelpKey" value="button.export.excel.help"/>
                        <tiles:put name="bundle" value="mailBundle"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                </tiles:put>

                <tiles:put name="grid">
                    <sl:collection id="listElement" model="list"  property="data" bundle="mailBundle">
                        <sl:collectionItem title="label.status">
                            <c:if test="${not empty listElement.state}">
                                <bean:message key="label.${listElement.state}" bundle="mailBundle"/>
                            </c:if>
                        </sl:collectionItem>
                        <sl:collectionItem title="label.counter">
                            <c:out value="${listElement.counter}"/>
                        </sl:collectionItem>
                    </sl:collection>
                </tiles:put>
                <tiles:put name="isEmpty" value="${empty form.data}"/>
                <tiles:put name="emptyMessage" value="Не найдено ни одного письма."/>
            </tiles:insert>

        </tiles:put>
    </tiles:insert>
</html:form>