<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<tiles:importAttribute/>

<tiles:insert definition="login">
    <tiles:put name="pageTitle" type="string" value="�����������."/>
    <tiles:put name="data" type="string" >
        <%-- ��������� � ������������� ����������� ������������ � ���� ActionMessage � ����������� � ������ --%>
        <tiles:insert page="/WEB-INF/jsp-sbrf/common/layout/messages.jsp" flush="false">
            <tiles:put name="bundle" type="string" value="commonBundle"/>
        </tiles:insert>
    </tiles:put>
</tiles:insert>
