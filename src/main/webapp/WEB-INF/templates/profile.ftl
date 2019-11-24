<#ftl encoding='UTF-8'>
<#include "base.ftl"/>
<#macro content>
    <script>
        $(function () {

            var $height1 = $('#profile-main-picture').height();
            var $height2 = $('#profile-container').width();
            var $margin = ($height2 - $height1) / 2
            $("#profile-main-picture").css("margin-left", $margin);
        });
    </script>

    <style>
        .profile-picture {
            background: url(${curr_user.getPhotoPath()});
            background-size: cover;
        }
    </style>

    <div class="profile-container">
        <div class="profile-inner-container">
            <div class="profile-main-container">
                <div class="profile-main">
                    <div class="profile-main-text">
                        <#if owner?has_content>
                            <h1 style="text-align: center; padding-bottom:2rem;">Ваш профиль</h1>
                        <#else><h1>Профиль участника</h1>
                        </#if>

                        <p style="top:25vh">Логин: ${curr_user.getNick()}</p>
                        <p>Email: ${curr_user.getEmail()}</p>
                        <#if viewBirth?has_content>
                            <p>
                                Дата рождения: ${curr_user.getBirth_date()}
                            </p>
                        </#if>
                        <p>Дата регистрации: ${curr_user.getRegiStrationDate()}</p>
                        <p>${timeOnBoard}</p>
                        <p>Количество постов: 00</p>
                        <p>Количество комметариев: 00</p>
                        <#if owner?has_content>
                            <input class="btn btn-danger" type="submit" value="logout"
                                   onclick="window.location='/logout';"/>
                            <form action="/changeProfile/" method="get">
                                <p><input type="hidden" name="user_id" id="user_id" value="${curr_user.getId()}"/></p>
                                <button type="submit" class="btn btn-danger">Edit</button>
                            </form>
                        </#if>
                    </div>
                </div>
            </div>

            <div class="profile-picture">
            </div>
        </div>
    </div>

</#macro>
<#macro title>
    <title>profile</title>
</#macro>
<@display_page />


