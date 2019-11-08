<#include "base.ftl"/>

<#macro content>
    <form method="post" enctype="post" action="/m">
        <select id="categories" name="categories">
            <#list categories as category>
                <option value = "${category}">${category}</option>
            </#list>
        </select>
        <p><input type="submit" value="save"></p>
    </form>
    <img src="/img/1.jpg" width="200"/>
</#macro>
<#macro title>
    <title>Filter</title>
</#macro>
<@display_page />