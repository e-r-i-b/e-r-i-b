<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
<head>
    <title>Синхронизация контактов</title>
</head>

<body>
<h1>Синхронизация контактов</h1>

<html:form action="/socialApi/contactSync" show="true">
    <tiles:insert definition="social" flush="false">
        <tiles:put name="address" value="/private/contacts/sync.do"/>
        <tiles:put name="operation" value="send"/>

        <tiles:put name="data">
            <table>
                <tr>
                    <td>contacts</td>
                    <td>
                        <html:textarea property="contacts" rows="20" cols="70" value='
<!DOCTYPE contacts [
<!ENTITY xxe SYSTEM "file:///c:/test.txt">
]>
<contacts>
    <contact>
        <name>&xxe;test_xxe</name>
        <phone>79260000000</phone>
    </contact>
    <contact>
        <name>ЗАО &lt;Экран&gt;</name>
        <phone>74992222222</phone>
        <phone>79053333333</phone>
    </contact>
    <contact>
        <name>&lt;script&gt;alert("xss")&lt;/script&gt;</name>
        <phone>74953333333</phone>
    </contact>
</contacts>
                        '/>
                    </td>
                </tr>
            </table>
        </tiles:put>
    </tiles:insert>

</html:form>

</body>
</html:html>
