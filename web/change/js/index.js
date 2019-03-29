var img1 = document.getElementById("img1");
var img2 = document.getElementById("img2");
var img3 = document.getElementById("img3");
var img4 = document.getElementById("img4");
var img5 = document.getElementById("img5");
var img6 = document.getElementById("img6");
var img7 = document.getElementById("img7");
var img8 = document.getElementById("img8");

function click1() {

    var imgs;
    for (var i = 1; i <= 8; i++) {
        imgs = document.getElementById("img" + i);
        imgs.style.border = 'none';
    }

    img1.style.border = '3px solid #224584';
}

function click2() {

    var imgs;
    for (var i = 1; i <= 8; i++) {
        imgs = document.getElementById("img" + i);
        imgs.style.border = 'none';
    }

    img2.style.border = '3px solid #224584';
}

function click3() {

    var imgs;
    for (var i = 1; i <= 8; i++) {
        imgs = document.getElementById("img" + i);
        imgs.style.border = 'none';
    }

    img3.style.border = '3px solid #224584';
}

function click4() {

    var imgs;
    for (var i = 1; i <= 8; i++) {
        imgs = document.getElementById("img" + i);
        imgs.style.border = 'none';
    }

    img4.style.border = '3px solid #224584';
}

function click5() {

    var imgs;
    for (var i = 1; i <= 8; i++) {
        imgs = document.getElementById("img" + i);
        imgs.style.border = 'none';
    }

    img5.style.border = '3px solid #224584';
}

function click6() {

    var imgs;
    for (var i = 1; i <= 8; i++) {
        imgs = document.getElementById("img" + i);
        imgs.style.border = 'none';
    }

    img6.style.border = '3px solid #224584';
}

function click7() {

    var imgs;
    for (var i = 1; i <= 8; i++) {
        imgs = document.getElementById("img" + i);
        imgs.style.border = 'none';
    }

    img7.style.border = '3px solid #224584';
}

function click8() {

    var imgs;
    for (var i = 1; i <= 8; i++) {
        imgs = document.getElementById("img" + i);
        imgs.style.border = 'none';
    }

    img8.style.border = '3px solid #224584';
}

//取有边框盒子的id
var id;

function getId() {

    var imgs;
    for (var i = 1; i <= 8; i++) {
        imgs = document.getElementById("img" + i);
        if (imgs.style.border == "3px solid rgb(34, 69, 132)") {
            id = imgs.src;
            break;
        }
    }
}

function fun() {
    $.ajax({
        url: '/BackgroundServlet',
        type: 'get',
        dataType: 'json',
        data: {
            url: id
        },
        success: function () {
        }
    })
}