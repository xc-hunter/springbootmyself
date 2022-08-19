'use strict';

var singleUploadForm = document.querySelector('#singleUploadForm');
var singleFileUploadInput = document.querySelector('#singleFileUploadInput');
var singleFileUploadError = document.querySelector('#singleFileUploadError');
var singleFileUploadSuccess = document.querySelector('#singleFileUploadSuccess');

var multipleUploadForm = document.querySelector('#multipleUploadForm');
var multipleFileUploadInput = document.querySelector('#multipleFileUploadInput');
var multipleFileUploadError = document.querySelector('#multipleFileUploadError');
var multipleFileUploadSuccess = document.querySelector('#multipleFileUploadSuccess');

var qrcodeupload=document.querySelector('#qrcodeupload');
var qrpic=document.querySelector('#qrpic');
var qrcoderesolveError=document.querySelector('#qrcoderesolveError');
var qrcoderesolveSuccess=document.querySelector('#qrcoderesolveSuccess');

var qrrequestform=document.querySelector('#qrcoderequest');
var qrinput=document.querySelector('#contentinput');
var qrcoderequestError=document.querySelector('#qrcoderequestError');
var qrcoderequestSuccess=document.querySelector('#qrcoderequestSuccess');

function uploadSingleFile(file) {
    var formData = new FormData();
    formData.append("file", file);

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/uploadFile");

    xhr.onload = function() {
        console.log(xhr.responseText);
        var response = JSON.parse(xhr.responseText);
        if(xhr.status == 200) {
            singleFileUploadError.style.display = "none";
            singleFileUploadSuccess.innerHTML = "<p>文件上传成功.</p><p>下载地址 : <a href='" + response.fileDownloadUri + "' target='_blank'>" + response.fileDownloadUri + "</a></p>";
            singleFileUploadSuccess.style.display = "block";
        } else {
            singleFileUploadSuccess.style.display = "none";
            singleFileUploadError.innerHTML = (response && response.message) || "Some Error Occurred";
        }
    }

    xhr.send(formData);
}

function uploadMultipleFiles(files) {
    var formData = new FormData();
    for(var index = 0; index < files.length; index++) {
        formData.append("files", files[index]);
    }

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/uploadMultipleFiles");

    xhr.onload = function() {
        console.log(xhr.responseText);
        var response = JSON.parse(xhr.responseText);
        if(xhr.status == 200) {
            multipleFileUploadError.style.display = "none";
            var content = "<p>所有文件上传成功</p>";
            for(var i = 0; i < response.length; i++) {
                content += "<p>下载地址 : <a href='" + response[i].fileDownloadUri + "' target='_blank'>" + response[i].fileDownloadUri + "</a></p>";
            }
            multipleFileUploadSuccess.innerHTML = content;
            multipleFileUploadSuccess.style.display = "block";
        } else {
            multipleFileUploadSuccess.style.display = "none";
            multipleFileUploadError.innerHTML = (response && response.message) || "Some Error Occurred";
        }
    }

    xhr.send(formData);
}

function getQrCode(content){


    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/getQrCodeOne?content="+content);

    xhr.onload = function() {
        console.log(xhr.responseText);
        var response = xhr.responseText;
        if(xhr.status == 200) {
            qrcoderequestError.style.display = "none";
            var innerhtml = "<p>二维码图片生成完毕，点击图片可下载</p>";
            innerhtml += "<p><a href='data:image/png;base64," + response + "' download='"+content+"'><img src='data:image/png;base64," + response + "'> " + "</img></a></p>";
            qrcoderequestSuccess.innerHTML = innerhtml;
            qrcoderequestSuccess.style.display = "block";
        } else {
            qrcoderequestSuccess.style.display = "none";
            qrcoderequestError.innerHTML = (response && response.message) || "Some Error Occurred";
        }
    }
    xhr.send();
}

function resolveQrCode(img) {
    var formData = new FormData();
    formData.append("img", img);

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/resolveQrCode");

    xhr.onload = function() {
        console.log(xhr.responseText);
        var response = xhr.responseText;
        if(xhr.status == 200) {
            qrcoderequestError.style.display = "none";
            qrcoderesolveSuccess.innerHTML = "<p>二维码解析成功.</p><p>内容为 : <a href='" + response + "' target='_blank'>" + response+ "</a></p>";
            qrcoderesolveSuccess.style.display = "block";
        } else {
            qrcoderesolveSuccess.style.display = "none";
            qrcoderesolveError.innerHTML = (response && response.message) || "Some Error Occurred";
        }
    }

    xhr.send(formData);
}

singleUploadForm.addEventListener('submit', function(event){
    var files = singleFileUploadInput.files;
    if(files.length === 0) {
        singleFileUploadError.innerHTML = "请选择文件";
        singleFileUploadError.style.display = "block";
    }
    uploadSingleFile(files[0]);
    event.preventDefault();
}, true);

multipleUploadForm.addEventListener('submit', function(event){
    var files = multipleFileUploadInput.files;
    if(files.length === 0) {
        multipleFileUploadError.innerHTML = "请至少选择一个文件";
        multipleFileUploadError.style.display = "block";
    }
    uploadMultipleFiles(files);
    event.preventDefault();
}, true);

qrrequestform.addEventListener('submit', function(event){
    var content = qrinput.value;
    if(content =='') {
        qrcoderequestError.innerHTML = "请输入要生成的二维码信息";
        qrcoderequestError.style.display = "block";
    }
    getQrCode(content);
    event.preventDefault();
}, true);

qrcodeupload.addEventListener('submit', function(event){
    var img = qrpic.files;
    if(img.length===0) {
        qrcoderequestError.innerHTML = "请选择二维码图片文件";
        qrcoderequestError.style.display = "block";
    }
    resolveQrCode(img[0]);
    event.preventDefault();
}, true);
