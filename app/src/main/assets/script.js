console.log("Javascript file detected");

function siteUrlClicked(url){
    console.log(url);
    Android.siteUrlClicked(url);
}
function imageClicked(index){
    console.log("Image Clicked");
    Android.imageClicked(index);
}
function youtubeUrl(url){
    Android.youtubeUrl(url);
}