<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<tiles:importAttribute/>
<b>Поля:</b>
<table frame="border" border="1" cellpadding="2" cellspacing="0">
    <tr>
        <th>name</th>
        <th>type</th>
        <th>title</th>
        <th>value</th>
        <th title="required">rq</th>
        <th title="editable">ed</th>
        <th title="visible">vi</th>
        <th title="isSum">su</th>
        <th title="changed">ch</th>
        <th>min</th>
        <th>max</th>
        <th title="description">descr</th>
        <th>hint</th>
    </tr>

    ${data}
</table>
