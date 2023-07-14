<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%--
 name - имя полй фильтра
 periodFormat - формат ввода даты
--%>

<tiles:importAttribute/>
    <html:hidden styleId="filter(type${name})" property="filter(type${name})" value="period"/>
    c
    <input disabled="true" value='<bean:write name="org.apache.struts.taglib.html.BEAN" property="filter(from${name})" format="${periodFormat}"/>'
           size="10" name="filter(from${name})" id="filter(from${name})" class="${name}date-pick" />
    по
    <input disabled="true" value='<bean:write name="org.apache.struts.taglib.html.BEAN" property="filter(to${name})" format="${periodFormat}"/>'
           size="10" name="filter(to${name})" id="filter(to${name})" class="${name}date-pick"/>
<c:if test="${not empty name}">
    <script type="text/javascript">
        doOnLoad(filterDateChange, '${name}');
        doOnLoad(
                function()
                {
                    $(function()
                    {
                        var dpDateFormat = '${periodFormat}'.toLowerCase();
                        var dP;
                        if ($('.${name}date-pick').datePicker)
                            dP = $('.${name}date-pick').datePicker({displayClose: true, chooseImg: globalUrl + '/commonSkin/images/datePickerCalendar.gif', dateFormat:dpDateFormat});

                        dP.dpApplyMask();
                    });
                });
    </script>
</c:if>


<script type="text/javascript">
    addClearMasks(null,
            function (event)
            {
                var regexp = /[dmy]/gi;
                clearInputTemplate('filter(from${name})', '${periodFormat}'.replace(regexp, '_'));
                clearInputTemplate('filter(to${name})', '${periodFormat}'.replace(regexp, '_'));
            });
</script>

