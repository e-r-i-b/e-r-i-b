<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<tiles:insert definition="logMain">
    <tiles:put name="pageTitle" type="string">������</tiles:put>
    <tiles:put name="data" type="string">
        <tiles:insert definition="roundBorderLight" flush="false">
            <tiles:put name="color" value="orange"/>
            <tiles:put name="data">
                ��� ����������� ������, �������� ������ ����� � ����� ����.
            </tiles:put>
        </tiles:insert>
    </tiles:put>
</tiles:insert>