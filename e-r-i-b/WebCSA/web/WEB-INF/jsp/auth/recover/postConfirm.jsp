<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<tiles:insert definition="stage" flush="false">
    <tiles:put name="title" value="������������� �������������� ������" type="string"/>
    <tiles:put name="data" type="string">
        <html:form action="/recover-password" styleClass="safetyRegulations" onsubmit="return false;">
            <c:set var="form" value="${csa:currentForm(pageContext)}"/>
            <c:set var="operationInfo" value="${form.operationInfo}"/>
                <tiles:insert definition="formRow" flush="false">
                    <tiles:put name="title" value="������� ����� ������:"/>
                    <tiles:put name="data">
                        <input id="password" type="password" size="30" name="field(password)" maxlength="30" tabindex="1"/>
                        <a href="#" onclick="win.open('passwordHelp'); return false;" tabindex="4">��� ��������� ��������� ���������� ������?</a>
                        <tiles:insert definition="complexIndicator" flush="false">
                            <tiles:put name="id" value="passwordField"/>
                        </tiles:insert>
                    </tiles:put>
                </tiles:insert>

                <%-- ����������� ��������� --%>
                <tiles:insert definition="window" flush="false">
                    <tiles:put name="id" value="passwordHelp"/>
                    <tiles:put name="data">
                        <div class="help-message">
                            <bean:message key="message.valid.password" bundle="commonBundle"/>
                            <ul>
                                <bean:message key="message.valid.password.requirementlist" bundle="commonBundle"/>
                            </ul>
                        </div>
                        <div class="buttonsArea">
                            <div class="clientButton" onclick="win.close('passwordHelp');">
                                <div class="buttonGrey">
                                    <div class="left-corner"></div>
                                    <div class="text">
                                        <span>�������</span>
                                    </div>
                                    <div class="right-corner"></div>
                                    <div class="clear"></div>
                                </div>
                            </div>
                        </div>
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="formRow" flush="false">
                    <tiles:put name="title" value="��������� ����� ������:"/>
                    <tiles:put name="data">
                        <input type="password" name="field(confirmPassword)" id="confirmPassword" size="30" maxlength="30" tabindex="1">
                    </tiles:put>
                </tiles:insert>

                 <tiles:insert definition="formRow" flush="false">
                    <tiles:put name="title" value="&nbsp;"/>
                    <tiles:put name="data">
                        <input id="changePasswordShow" type="checkbox"/> ���������� ������
                    </tiles:put>
                </tiles:insert>


                <div class="buttonsArea">
                    <div class="clientButton" onclick="stageForm.close();" onkeypress="clickIfEnterKeyPress(this,event)" tabindex="3">
                        <div class="buttonGrey">
                            <div class="left-corner"></div>
                            <div class="text">
                                <span>��������</span>
                            </div>
                            <div class="right-corner"></div>
                            <div class="clear"></div>
                        </div>
                    </div>

                    <div id="nextButton" class="commandButton" onclick="" onkeypress="clickIfEnterKeyPress(this,event)" tabindex="2">
                        <div class="buttonGreen disabled">
                            <div class="left-corner"></div>
                            <div class="text">
                                <span>���������</span>
                            </div>
                            <div class="right-corner"></div>
                            <div class="clear"></div>
                        </div>
                    </div>
                </div>
        </html:form>

        <script type="text/javascript">
            if(window.win)
                win.init(ensureElement("stageForm"));

            function enableNextButton(state)
            {
                if(state == "green")
                {
                    document.getElementById('nextButton').onclick = function(){stageForm.send('button.next');};
                    document.getElementById('nextButton').children[0].className = "buttonGreen";
                }
                else
                {
                    document.getElementById('nextButton').onclick = null;
                    document.getElementById('nextButton').children[0].className = "buttonGreen disabled";
                }
            }

            if(window.Indicator)
            {
                var indicator = new Indicator("passwordField", stateComplexPasswordCsa, enableNextButton);
                indicator.init("password", "stageForm");
            }

            if(window.$)
            {
                $("#changePasswordShow").change(function(){
                    showHidePassword('password', this.checked, 'stageForm');
                    showHidePassword('confirmPassword', this.checked, 'stageForm');
                });
            }
        </script>
    </tiles:put>
</tiles:insert>

