const videoElem = document.getElementById("video");
const logElem = document.getElementById("log");
const startElem = document.getElementById("start");
const stopElem = document.getElementById("stop");
const downloadElem = document.getElementById("download");
const downloadLink = document.getElementById("downloadLink");

let recording = null;
let mediaRecorder;
let chunks = [];

console.log = msg => logElem.innerHTML += `${msg}<br>`;
console.error = msg => logElem.innerHTML += `<span class="error">${msg}</span><br>`;
console.warn = msg => logElem.innerHTML += `<span class="warn">${msg}<span><br>`;
console.info = msg => logElem.innerHTML += `<span class="info">${msg}</span><br>`

// Options for getDisplayMedia()
var displayMediaOptions = {
  video: {
    cursor: "always"
  },
  audio: false
};

// Set event listeners for the start, stop and download buttons
startElem.addEventListener("click", function(evt) {
  startCapture();
}, false);

stopElem.addEventListener("click", function(evt) {
  stopCapture();
}, false);

downloadElem.addEventListener("click", function(evt) {
  downloadCapture();
}, false);

async function startCapture() {
    logElem.innerHTML = "";
    if(recording){
      window.URL.revokeObjectURL(this.recording);
    }
    try {
      recording = null;
      videoElem.srcObject = await navigator.mediaDevices.getDisplayMedia(displayMediaOptions);
      dumpOptionsInfo();
      recordMedia();
    } catch(err) {
      console.error("Error: " + err);
    }
  }

  function stopCapture(evt) {
    let tracks = videoElem.srcObject.getTracks();
    mediaRecorder.stop();
    tracks.forEach(track => track.stop());
    videoElem.srcObject = null;
    recording = window.URL.createObjectURL(new Blob(chunks, {type: 'video/webm'}));
  }

  function downloadCapture(evt){
    console.info("Download recording.");
    downloadLink.addEventListener('progress', e => console.info(e));
    downloadLink.href = recording;
    downloadLink.download = 'screen-recording.webm';
    downloadLink.click();
  }

  function recordMedia(){
    videoElem.addEventListener('inactive', e => {
      console.info("Capture stream inactive - stop recording!");
      this.stopCapture(e);
    });
    mediaRecorder = new MediaRecorder(videoElem.srcObject, {mimeType: 'video/webm'});
    chunks = [];
    mediaRecorder.addEventListener('dataavailable', event => {
      if (event.data && event.data.size > 0) {
        chunks.push(event.data);
      }
    });
    mediaRecorder.start(10);
  }

  function dumpOptionsInfo() {
    const videoTrack = videoElem.srcObject.getVideoTracks()[0];   
    console.info("Track settings:");
    console.info(JSON.stringify(videoTrack.getSettings(), null, 2));
    console.info("Track constraints:");
    console.info(JSON.stringify(videoTrack.getConstraints(), null, 2));
  }