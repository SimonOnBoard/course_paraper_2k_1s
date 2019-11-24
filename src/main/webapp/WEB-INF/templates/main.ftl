<#ftl encoding='UTF-8'>
<#include "base.ftl"/>
<#macro content>
    <p><input id="query" oninput="f()"/></p>
    <select id="entity" name="entity" onchange="getval(this);">
        <option selected value="post">post</option>
        <option value="user">user</option>
    </select>
    <select id="where" name="where" onchange="getval(this);">
        <option selected value="name">name</option>
        <option value="text">text</option>
    </select>
    <input name="use_category" id="use_category" type="checkbox" value="0"
           onclick="f()"/>Использовать фильтрацию по категориям
    <form method="post" action="/mainSearch">
        <select id="categories" name="categories" onchange="getval(this);">
            <#list categories as category>
                <option value="${category}">${category}</option>
            </#list>
        </select>
        <p>Просто измените категорию, если хотите получить 5 последних актуальных новостей по ней</p>
    </form>

    <div id="res">
        <#list posts as post>
            <div id="post_${post.getId()}">
                <img src="${post.getPhotoPath()}" width="100"/>
                <h1>${post.getName()}</h1>
                <p>${post.getText()}</p>
                <p>${post.getCategory().toString()}</p>
                <p>${post.getPublication().toString()}</p>
                <a href="/showPost?id=${post.getId()}">
                    Просмотреть запись
                </a>
                <#if post.getShowAuthor()>
                    <a href="/home?id=${post.getAuth_id()}">
                        Просмотреть профиль автора
                    </a>
                <#else>
                </#if>
            </div>
        </#list>
    </div>

    <script type="application/javascript">
        function getval(sel) {
            f();
        }

        function f() {
            var category = $("#categories").val();
            var query = $("#query").val();
            var entity = $("#entity").val();
            var where = $("#where").val();
            var use_c = $("#use_category").serialize();
            $.ajax({
                url: "/mainSearch",
                data: {
                    "query": query,
                    "categories": category,
                    "entity": entity,
                    "where": where,
                    "use_c": use_c
                },
                type: "post",
                success: function (msg) {
                    var msg = JSON.parse(msg);
                    if (msg.type == "user") {
                        if (msg.users.length > 0) {
                            $("#res").html("");
                            for (var i = 0; i < msg.users.length; i++) {
                                var div_r = document.createElement("div");
                                div_r.setAttribute("id", "user_" + i);
                                $("#res").append(div_r);
                                $(div_r).append("<img width=" + "\"200\"" + " src=" + "\"" + msg.users[i].photoPath + "\"" + " />");
                                $(div_r).append("<div>" + msg.users[i].nick + "</div>");
                                $(div_r).append("<div>" + msg.users[i].email + "</div>");
                                $(div_r).append("<div>" + msg.users[i].birth + "</div>");
                                $(div_r).append("<a href=" + "/home?id=" + msg.users[i].id + "> Нажмите, чтобы просмотреть профиль</a><br>");

                            }
                        } else {
                            $("#res").html("No results..");
                        }
                    } else {
                        if (msg.posts.length > 0) {
                            $("#res").html("");
                            for (var i = 0; i < msg.posts.length; i++) {
                                var div_r = document.createElement("div");
                                div_r.setAttribute("id", "post_" + i);
                                $("#res").append(div_r);
                                $(div_r).append("<img width=" + "\"200\"" + " src=" + "\"" + msg.posts[i].photoPath.toString() + "\"" + " />");
                                $(div_r).append("<div>" + msg.posts[i].name + "</div>");
                                $(div_r).append("<div>" + msg.posts[i].text + "</div>");
                                $(div_r).append("<div>" + msg.posts[i].category + "</div>");
                                var d = new Date(msg.posts[i].time);
                                $(div_r).append("<div>" + d.toString() + "</div>");
                                if (msg.posts[i].showAuthor) {
                                    $(div_r).append("<a href=" + "/home?id=" + msg.posts[i].auth_id + "> Нажмите, чтобы просмотреть профиль автора </a><br>");
                                }
                                $(div_r).append("<a href=" + "/showPost?id=" + msg.posts[i].id + "> Нажмите, чтобы просмотреть текст записи </a>");

                            }
                        } else {
                            $("#res").html("No results..");
                        }
                    }
                }
            });
        };
    </script>
</#macro>
<#macro title>
    <title>Search</title>
</#macro>
<@display_page />