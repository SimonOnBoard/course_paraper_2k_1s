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
    <form method="post" action="/registration">
        <label for="user_name">Nick
            <input class="input-field" type="text" id="user_name" name="user_name">
        </label>
        <label for="name">User name
            <input class="input-field" type="text" id="name" name="name">
        </label>
        <label for="password">Password
            <input class="input-field" type="password" id="password" name="password">
        </label>
        <label for="email">Mail
            <input class="input-field" type="text" id="email" name="email">
        </label>
        <label for="birth">Your birthday :)

        <input type="date" id="birth" name="birth"
               value="${cur_date}"
               min="1900-01-01" max="${cur_date}">
        </label>
        <input type="submit" value="Registration">
    </form>
</div>
</body>
</html>