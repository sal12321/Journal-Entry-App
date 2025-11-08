if (!('webkitSpeechRecognition' in window)) {
  alert("Voice input not supported in this browser. Use Chrome or Edge.");
} else {
  const recognition = new webkitSpeechRecognition();
  recognition.continuous = true;
  recognition.interimResults = true;
  recognition.lang = 'en-US';

  let tempTranscript = '';

  recognition.onresult = (event) => {
    let currentText = document.getElementById('editEntryContent').value;
    let finalChunk = '';
    for (let i = event.resultIndex; i < event.results.length; ++i) {
      const transcript = event.results[i][0].transcript.trim();
      if (event.results[i].isFinal) finalChunk += transcript + ' ';
    }

    if (finalChunk) {
      // append the new spoken text
      document.getElementById('editEntryContent').value = currentText + (currentText ? ' ' : '') + finalChunk;
    }

  };

  recognition.onerror = (e) => console.error('Speech recognition error:', e);
  recognition.onend = () => console.log('Stopped listening.');
  document.getElementById('startBtn').onclick = () => {
      recognition.start();
      console.log('Listening...');
    };
    document.getElementById('stopBtn').onclick = () => {
        recognition.stop();
        console.log('stop Listening...');
      };

}

if (recognitionListening) { recognition.stop(); }
else { recognition.start(); }
