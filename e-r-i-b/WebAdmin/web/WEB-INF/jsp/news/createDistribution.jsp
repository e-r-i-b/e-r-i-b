<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:insert definition="dictionary">

    <tiles:put name="pageTitle" value="Создание email-рассылки о событиях\\новостях банка"/>

    <tiles:put name="menu" type="string">
        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey" value="button.distribution.create"/>
            <tiles:put name="commandHelpKey" value="button.distribution.create"/>
            <tiles:put name="bundle" value="newsBundle"/>
            <tiles:put name="onclick" value="distributionOn()"/>
            <tiles:put name="viewType" value="blueBorder"/>
        </tiles:insert>
        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey" value="button.cancel"/>
            <tiles:put name="commandHelpKey" value="button.cancel"/>
            <tiles:put name="bundle" value="newsBundle"/>
            <tiles:put name="onclick" value="javascript:window.close()"/>
            <tiles:put name="viewType" value="blueBorder"/>
        </tiles:insert>
    </tiles:put>

    <tiles:put name="data" type="string">
        <script type="text/javascript">
            var createUrl = "${phiz:calculateActionURL(pageContext, '/news/distribution/create')}";
            function validate(mailCount, timeout)
            {
                if(!mailCount)
                {
                    alert("Заполните поле Количество писем в пачке.");
                    return false;
                }

                if(!timeout)
                {
                    alert("Заполните поле Период времени между рассылками пачек.");
                    return false;
                }

                return true;
            }

            function distributionOn(event)
            {
                var mailCount = $('#mailCount').val();
                var timeout = $('#timeout').val();

                if (!validate(mailCount, timeout))
                    return;

                window.opener.createDistribution(mailCount, timeout);
                window.close();
            }
        </script>

        <table align="center" width="100%">
            <tr valign="middle">
                <td align="right" style="width:340px;">Количество писем в пачке:</td>
				<td align="left"><input type="text" id="mailCount" class="filterInput" size="10" maxlength="2"/></td>
            </tr>
            <tr>
                <td align="right">Период времени между рассылками пачек:</td>
                <td align="left"><input type="text" id="timeout" class="filterInput" size="10" maxlength="2"/> минут</td>
			</tr>
        </table>
    </tiles:put>
</tiles:insert>