<#ftl encoding='UTF-8'>
<#include "base.ftl"/>

<#macro content>

    <div class="feed-container">
        <div class="feed">
            <h1>Feed <a href="/newPost"><i class="fa fa-plus" aria-hidden="true"></i></a></h1>
            <div id="namer" style="margin-bottom:3rem;">
                <div id="namer-input" style="margin:auto;">
                    <input type="text" id="query" oninput="f()" placeholder="Search" style="margin-bottom:1rem;">
                </div>
                <div class="form-group">
                    <label for="entity">Что искать?</label>
                    <select class="form-control" id="entity" name="entity" onchange="getval(this);"
                            style="height:1.8em;">
                        <option selected value="post">post</option>
                        <option value="user">user</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="where">Где искать?</label>
                    <select class="form-control" id="where" name="where" onchange="getval(this);" style="height:1.8em;">
                        <option selected value="name">name</option>
                        <option>text</option>
                    </select>
                </div>

                <div class="form-group">
                    <form method="post" action="/mainSearch">
                        <label for="categories">Ввыберите категорию и получите последние 5 новостей</label>
                        <select class="form-control" id="categories" name="categories" onchange="getval(this);"
                                style="height:1.8em;">
                            <#list categories as category>
                                <option value="${category}">${category}</option>
                            </#list>
                        </select>
                    </form>
                </div>

                <div class="form-check">
                    <input type="checkbox" class="form-check-input" name="use_category" id="use_category" value="0"
                           onclick="f()" style="height:auto;">
                    <label class="form-check-label" for="use_category">Использовать фильтрацию по категориям</label>
                </div>
            </div>

            <div id="res" class="row">
                <#list posts as post>
                    <div class="col-sm-6">
                        <div class="post" style="width:95%; margin-right:5%">
                            <p>
                                <img src="${post.getPhotoPath()}" height="100"> &nbsp; <span
                                        class="big">${post.getName()}</span>
                            </p>

                            <p class="post-text">${post.getText()}</p>
                            <p>Категория: ${post.getCategory().toString()}</p>
                            <p>${post.getPublication().toString()}</p>
                            <p style="padding-top:2rem;"><a href="/showPost?id=${post.getId()}">
                                    Просмотреть запись
                                </a>
                            <p>
                                <#if post.getShowAuthor()>
                            <p style="padding-top:2rem;"><a href="/home?id=${post.getAuth_id()}">
                                    Просмотреть профиль автора
                                </a>
                            </p>
                            </#if>
                        </div>
                    </div>
                </#list>
            </div>

        </div>
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
                                div_r.setAttribute("id", "div_" + i);
                                div_r.setAttribute("class", "col-sm-6");
                                var div_r_1 = document.createElement("div");
                                div_r_1.setAttribute("class", "post");
                                div_r_1.setAttribute("id", "user_" + i);
                                div_r_1.setAttribute("style", "width:95%; margin-right:5%;");
                                $("#res").append(div_r);
                                $("#div_"+i).append(div_r_1);
                                $("#user_" + i).append("<p><img height=\"100\" src=\"" + msg.users[i].photoPath + "\"> &nbsp; <span class=\"big\">" + msg.users[i].nick + "</span></p>");
                                $("#user_" + i).append("<p class=\"post-text\">Email: " + msg.users[i].email + "</p>");
                                $("#user_" + i).append("<p class=\"post-text\">Birth: " + msg.users[i].birth + "</p>");
                                $("#user_" + i).append("<p style=\"padding-top:2rem;\"><a href=" + "/home?id=" + msg.users[i].id + "> Нажмите, чтобы просмотреть профиль</a></p>");

                            }
                        } else {
                            $("#res").html("No results..");
                        }
                    } else {
                        if (msg.posts.length > 0) {
                            $("#res").html("");
                            for (var i = 0; i < msg.posts.length; i++) {
                                var div_r = document.createElement("div");
                                div_r.setAttribute("id", "div_" + i);
                                div_r.setAttribute("class", "col-sm-6");
                                var div_r_1 = document.createElement("div");
                                div_r_1.setAttribute("class", "post");
                                div_r_1.setAttribute("id", "post_" + i);
                                div_r_1.setAttribute("style", "width:95%; margin-right:5%;");
                                $("#res").append(div_r);
                                $("#div_"+i).append(div_r_1);
                                $("#post_" + i).append("<p><img height=\"100\" src=\"" + msg.posts[i].photoPath + "\"> &nbsp; <span class=\"big\">" + msg.posts[i].name + "</span></p>");
                                $("#post_" + i).append("<p class=\"post-text\">" + msg.posts[i].text + "</p>");
                                $("#post_" + i).append("<p class=\"post-text\">" + msg.posts[i].category + "</p>");
                                var d = new Date(msg.posts[i].time);
                                $("#post_" + i).append("<p class=\"post-text\">" + d.toString() + "</p>");
                                if (msg.posts[i].showAuthor) {
                                    $("#post_" + i).append("<p class=\"post-text\"><a href=" + "/home?id=" + msg.posts[i].auth_id + "> Профиль автора </a></p>");
                                }
                                $("#post_" + i).append("<p class=\"post-text\"><a href=" + "/showPost?id=" + msg.posts[i].id + "> Текст записи </a></p>");
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