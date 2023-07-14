<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="appTitle"><bean:message bundle="commonBundle" key="application.title"/></c:set>
<tiles:insert definition="login">
    <tiles:put name="pageTitle" value="${appTitle}"/>
    <tiles:put name="data" type="string">
        <br/>
        <tiles:insert definition="mainWorkspace" flush="false">
            <tiles:put name="title" value="<div class='castleWhite'>�������� ��������</div>"/>
            <tiles:put name="data">
                <bean:message bundle="commonBundle" key="text.duplicateSession.notification"/>
                <div class="buttonsArea">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.repeatLogin"/>
                        <tiles:put name="commandHelpKey" value="button.repeatLogin.help"/>
                        <tiles:put name="bundle" value="commonBundle"/>
                        <tiles:put name="action" value="/login.do"/>
                    </tiles:insert>
                </div>
            </tiles:put>
        </tiles:insert>
        <tiles:insert definition="roundBorderLight" flush="false">
            <tiles:put name="color" value="orange"/>            
            <tiles:put name="data">
                        <div class="castleOrange"></div><p class="loginWarningMessage">&nbsp;������ ���������� ��� ������������� ��������� ������!</p>
                        <br/>
                        <p>
                            ������� ������� <span class="bold">�� ����������� ����� ���������� �������� � ������ �������������� ���������� ��� �����</span> � ������ �������, ����� ��������������, ����������� � ������������ �������.
                        </p>
                        <br/>
                        <p>
                            �������� ������� <span class="bold">�� ����������� ������ ��� ������ �������� ��� ��������</span> � ��������� ������. ���� ��� ������������ ������ ������ ��� ������ ��� ������������� ��������, ������� �� �� ���������, �� ���������� ����� ������������� ������ � ������ ���������� � ����.
                        </p>
                        <br/>
                        <p>
                            ����������� <span class="bold">������� ��������� ����� �������� � ����������� � ���������� SMS-���������</span>. �������, ��� ����� ����������� SMS-������, �� ������������� �������� � �����������, ���������� � SMS.
                        </p>
                        <br/>
                        <p>
                            ��������� � ����� ������������ ��� ������ � ��������� ������ ������� <a href="http://sberbank.ru/ru/person/dist_services/warning/" target="_blank">�����</a>.
                        </p>
            </tiles:put>
        </tiles:insert>
    </tiles:put>
</tiles:insert>
