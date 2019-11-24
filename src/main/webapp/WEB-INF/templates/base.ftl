<#ftl encoding='UTF-8'>
<#macro content></#macro>
<#macro title></#macro>

<#macro display_page>
<html>
<head>
    <meta charset="utf-8">
    <@title />
</head>

<body>
<@content />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
</body>
</html>
</#macro>