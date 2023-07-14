<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<tiles:insert definition="mainWorkspace" flush="false">
    <tiles:put name="title" value="��������� ����"/>
    <tiles:put name="data">

        <p class="textHead">������ ���������� ���� �������� ��� ��������� �������� �� ������, ��������� SMS-���������:
            ���������� ������, ����������� ������, ����������� ����� � ��������� ������ ������ ��������.</p>

        <c:if test="${phiz:impliesService('MobileBankRegistration')}">
            <p class="textHead">���������� ������
                <a class="orangeText" target="_blank"
                   href="http://sberbank.ru/ru/person/dist_services/inner_mbank/"
                   title="������� �������������� ������"><span>��������� ����</span></a>
                (������ ��� ��������� �����).
                ��� ����� ������� �� ������
                <html:link styleClass="orangeText" action="/private/register-mobilebank/start"><span>����������</span></html:link>.
            </p>
        </c:if>
    </tiles:put>
</tiles:insert>