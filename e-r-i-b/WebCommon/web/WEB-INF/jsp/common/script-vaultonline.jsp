<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:if test="${phiz:useVaultOnline()}">
    <script type="text/javascript">(function(u){ s=document.createElement('script'); s.src=u; s.async=true; s.type="text/javascript"; var p = document.head || document.getElementsByTagName('script')[0].parentNode; p.appendChild(s); })("//ibjs.group-ib.ru/vaultonline-2.js");</script>
</c:if>