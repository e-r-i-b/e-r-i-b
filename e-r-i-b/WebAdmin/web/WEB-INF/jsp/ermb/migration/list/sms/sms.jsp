<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>

<html:form action="/ermb/migration/sms">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:importAttribute/>

    <tiles:insert definition="migrationMain">
        <tiles:put name="submenu" type="string" value="Sms"/>

        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name">
                    <bean:message key="sms.title" bundle="migrationBundle"/>
                </tiles:put>

                <tiles:put name="data">
                    <script>
                        function switchSendOrNotSegment(segment)
                        {
                            var name = segment.parentElement.id;
                            if (segment.checked==true)
                            {
                                if (name.indexOf('SendsSegments')==0)
                                {
                                    document.getElementById('ban'+name).firstElementChild.checked=false;
                                }
                                else
                                {
                                    document.getElementById('SendsSegments'+name.substr(16,1)).firstElementChild.checked=false;
                                }
                            }
                        }
                    </script>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <span>Текст SMS-сообщения</span>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:textarea property="text" title="Введите текст" cols="50" rows="7"/><br>
                            <span>В тексте сообщения можно использовать переменные:</span><br>
                            <span>$NAME - Имя и отчество клиента</span>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="tableTemplate" flush="false">
                        <tiles:put name="text">
                            <bean:message key="label.segments" bundle="migrationBundle"/>
                        </tiles:put>
                        <tiles:put name="head">
                            <td width="100px" nowrap="true">
                                <bean:message key="label.segment" bundle="migrationBundle"/>
                            </td>
                            <td width="100px" nowrap="true">
                                <bean:message key="label.sms.send" bundle="migrationBundle"/>
                            </td>
                            <td width="100px" nowrap="true">
                                <bean:message key="label.sms.notSend" bundle="migrationBundle"/>
                            </td>
                        </tiles:put>
                        <tiles:put name="data">
                            <c:forEach var="segment" items="${form.data}">
                                <c:set var="lineNumber" value="${lineNumber+1}"/>
                                <tr class="listLine${lineNumber%2}">
                                    <td class="listItem" nowrap="true">
                                        &nbsp;
                                        <c:out value="${segment.value}"/>
                                        &nbsp;
                                    </td>
                                    <td class="listItem" nowrap="true" align="center" id="SendsSegments${lineNumber}">
                                        <html:multibox property="sendsSegments" style="border:none" onclick="switchSendOrNotSegment(this)">
                                            <bean:write name="segment" property="value"/>
                                        </html:multibox>
                                    </td>
                                    <td class="listItem" nowrap="true" align="center"id="banSendsSegments${lineNumber}">
                                        <html:multibox property="banSendsSegments" style="border:none" onclick="switchSendOrNotSegment(this)">
                                            <bean:write name="segment" property="value"/>
                                        </html:multibox>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tiles:put>
                        <tiles:put name="emptyMessage">
                            <bean:message key="empty.segments.message" bundle="migrationBundle"/>
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>

                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey" value="button.send"/>
                        <tiles:put name="commandHelpKey" value="button.send.help"/>
                        <tiles:put name="bundle" value="migrationBundle"/>
                    </tiles:insert>
                </tiles:put>

            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>