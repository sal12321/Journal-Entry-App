

function setupVoiceButton(buttonId, textareaId) {
  const button = document.getElementById(buttonId);
  const textarea = document.getElementById(textareaId);

  if (!('webkitSpeechRecognition' in window)) {
    alert("Voice input not supported in this browser. Use Chrome or Edge.");
    return;
  }

  const recognition = new webkitSpeechRecognition();
  recognition.continuous = true;
  recognition.interimResults = true;
  recognition.lang = 'en-US';

  recognition.onresult = (event) => {
    let currentText = textarea.value;
    let finalChunk = '';

    for (let i = event.resultIndex; i < event.results.length; ++i) {
      const transcript = event.results[i][0].transcript.trim();
      if (event.results[i].isFinal) finalChunk += transcript + ' ';
    }

    if (finalChunk) {
      textarea.value = currentText + (currentText ? ' ' : '') + finalChunk;
    }
  };

  recognition.onerror = (e) => console.error('Speech recognition error:', e);
  recognition.onend = () => {

    button.textContent = 'START TALKING';
  };

  button.onclick = () => {
    if (button.textContent === 'START TALKING') {

      recognition.start();
      button.textContent = 'STOP';
    } else {

      recognition.stop();
      button.textContent = 'START TALKING';
    }
  };
}

// setting up both the button on page load
setupVoiceButton('voiceBtnCreateEntry', 'entryContent');
setupVoiceButton('voiceBtnUpdateEntry', 'editEntryContent');
