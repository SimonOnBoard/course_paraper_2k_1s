<!DOCTYPE html>
<html lang="en ru">
<head>
    <meta charset="UTF-8">
    <title>Registration</title>
</head>
<body>
<#if errors??>
    <#list errors as g>
        <p>${g}</p>
    </#list>
<#else>
    <div>
        <p lang="ru">Мир знаний биологии ждёт Вас</p>
    </div>
</#if>
<div class="form-style-2">
    <div class="form-style-2-heading">
        Just a registration form! PUT some information IN)
    </div>
    <form method="post" enctype="multipart/form-data">
        <input class="input-field" type="text" name="user_name"/>

        <input class="input-field" type="text" name="name"/>

        <input class="input-field" type="password" name="password">

        <input class="input-field" type="text" name="email"/>

        <input type="date" name="birth"
               value="${cur_date}"
               min="1900-01-01" max="2020-01-01"/>

        <p><input type="file" name="photo"/></p>

        <input type="submit" value="Registration"/>
    </form>
</div>
</body>
</html>