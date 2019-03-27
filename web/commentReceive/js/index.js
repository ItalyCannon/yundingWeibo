function load_home1() {
    window.location.href = "/home"
}

function load_home2() {
    window.location.href = "/collection";
}

function load_home3() {
    window.location.href = "/praise";
}

function load_home4() {
    window.location.href = "/commentReceive";
}

function show_list() {
    window.location.href = "/list";
}

function loadDetail() {
    window.location.href = "/detail"
}

function load_attention() {
    window.location.href = "/attention"
}

function load_fans() {
    window.location.href = "/fans"
}

function load_space() {
    window.location.href = "/space"
}


function load_receive() {
    window.location.href = "/commentReceive/"
}

function load_send() {
    window.location.href = "/commentSend/"
}

function CommentReceive() {
    $.ajax({
        url: '/MyCommentServlet?type=receive',
        type: 'get',
        dataType: 'json',
        data: {},
        success: function (text) {
            var data = eval(text);
            var html = '';
            for (var i = 0; i < data.length; i++) {
                html +=
                    '<div class="collect" id="' + 'collect' + i + '">' +
                    '<img src="' + data[i].profilePicture + '" alt="" class="portrait">' +
                    '<div class="name">' +
                    '<p class="nickname">' + data[i].nickname + '</p>' +
                    '<p class="date">' + data[i].formatCommentTime + '</p>' +
                    '</div>' +
                    '<p class="text">回复<span>@' + user.nickname + '</span>：' + data[i].commentContent + '</p>' +
                    '<div class="reply">' +
                    '<p class="reply_text">' + user.nickname + ':' + data[i].weiboContent + '</p>' +
                    '</div>' +
                    '<div class="bottom">' +
                    '<p><i class="iconfont">&#xe643;</i> 回复</p>' +
                    '<p class="last"><i class="iconfont">&#xe60c;</i> ' + data[i].commentPraise + '</p>' +
                    '</div>' +
                    '</div>';
                noApplicationRecord.innerHTML = html
            }
            var main = document.getElementById("main");
            if(main.offsetHeight <= "730"){
                main.style.height = "730px";
            }
        },
        async: true
    });
}

var user;

function baseInfo() {
    $.ajax({
        url: '/BasicUserInfoServlet',
        type: 'get',
        dataType: 'json',
        data: {},
        success: function (text) {
            user = eval(text);
            $("#profilePicture").attr('src', user.profilePicture);
            $("#profile1").attr('src', user.profilePicture);
            $("#nicknameLeft").html(user.nickname);
            $("#subscribeNum").html(user.subscribeNum);
            $("#fansNum").html(user.fansNum);
            $("#weiboNum").html(user.weiboNum);
        },
        async: false
    });
}

function change(){
    $.ajax({
        url: "/BackgroundServlet",
        type: "get",
        datatype: "json",
        data:{},
        success:function (data) {
            var text = eval(data);
            // var content = document.getElementById("content");
            // alert(content.style.backgroundImage);
            $(".main").css("background-image","url("+'"'+text.img+'"'+")");
            // var content = $("#content").attr("class");
        }

    })
}

window.onload = function () {
    baseInfo();
    change();
    CommentReceive();
};
