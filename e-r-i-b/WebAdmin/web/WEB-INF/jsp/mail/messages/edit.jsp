<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/mail/stmessages/edit" enctype="multipart/form-data" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>

    <tiles:insert definition="mailEdit">
        <tiles:put name="submenu" type="string" value="MessagesEdit"/>

        <tiles:put name="menu" type="string">
            <tiles:insert definition="commandButton" flush="false" operation="EditMailStaticMessagesOperation">
                <tiles:put name="commandKey"     value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle"  value="mailBundle"/>
                <tiles:put name="image"   value=""/>
                <tiles:put name="postbackNavigation" value="true"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name">
                    <bean:message key="edit.messages.title" bundle="mailBundle"/>
                </tiles:put>
                <tiles:put name="description">
                    <bean:message key="edit.messages.descr" bundle="mailBundle"/>
                </tiles:put>
                <tiles:put name="data">

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.formQuestionClient" bundle="mailBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:textarea styleId="formText" property="field(formText)" cols="70" rows="7" style="text-align:justify;"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="data">
                            <table class="formatPanel">
                                <tr>
                                    <td><button onmousedown="changeSelText('b', ['formText']);"><b>Ж</b></button></td>
                                    <td><button onmousedown="changeSelText('i', ['formText']);"><i>К</i></button></td>
                                    <td><button onmousedown="changeSelText('u', ['formText']);"><u>Ч</u></button></td>
                                    <td><button onmousedown="addHyperlink(['formText']);"><span style="font-size:8pt">Гиперссылка</span></button></td>
                                    <td>
                                        <div id="fileInput1" class="fileInputContainer">
                                            <input type="file" size="1" onchange="fileSelected('formText', this.value);" onmousedown="updateCursorPosition('formText');" name="field(imageFile1)" class="inputFileOpacity">
                                            <div id="fileInput2" class="fileInputContainer"></div>
                                        </div>
                                        <button><span style="font-size:8pt;z-index:2;">Рисунок</span></button>
                                    </td>
                                </tr>
                            </table>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.confirmMessage" bundle="mailBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:textarea styleId="messageText" property="field(messageText)" cols="70" rows="4" style="text-align:justify;"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="data">
                            <table class="formatPanel">
                                <tr>
                                    <td><button onmousedown="changeSelText('b', ['messageText']);"><b>Ж</b></button></td>
                                    <td><button onmousedown="changeSelText('i', ['messageText']);"><i>К</i></button></td>
                                    <td><button onmousedown="changeSelText('u', ['messageText']);"><u>Ч</u></button></td>
                                    <td><button onmousedown="addHyperlink(['messageText']);"><span style="font-size:8pt">Гиперссылка</span></button></td>
                                </tr>
                            </table>
                        </tiles:put>
                    </tiles:insert>

                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false" operation="EditMailStaticMessagesOperation">
                        <tiles:put name="commandKey"     value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                        <tiles:put name="bundle"  value="mailBundle"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false" operation="EditMailStaticMessagesOperation">
                        <tiles:put name="commandKey"     value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                        <tiles:put name="bundle"  value="mailBundle"/>
                        <tiles:put name="postbackNavigation" value="false"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
            <script type="text/javascript">
                updateMarginLeftFile('field(imageFile1)');
            </script>
        </tiles:put>
    </tiles:insert>

</html:form>
