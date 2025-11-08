let recognition;
let finalTranscript = '';

if ('webkitSpeechRecognition' in window) {
    recognition = new webkitSpeechRecognition();
    recognition.continuous = true;
    recognition.interimResults = true;
    recognition.lang = 'en-US';

    recognition.onresult = (event) => {
        let interim = '';
        for (let i = event.resultIndex; i < event.results.length; ++i) {
            if (event.results[i].isFinal) {
                finalTranscript += event.results[i][0].transcript;
            } else {
                interim += event.results[i][0].transcript;
            }
        }
        document.getElementById('entryContent').value = finalTranscript + ' ' + interim;
    };

    recognition.onerror = (e) => console.error('Speech error:', e);
    recognition.onend = () => console.log('Speech recognition ended.');
} else {
    alert("Speech recognition not supported in this browser.");
}

document.getElementByClass('startBtn').onclick = () => {
    finalTranscript = '';
    recognition.start();
};

document.getElementByClass('stopBtn').onclick = () => {
    recognition.stop();
};

document.getElementByClass('saveBtn').onclick = async () => {
    const text = document.getElementById('entryContent').value.trim();
    if (!text) return alert("Empty entry!");
    await fetch("/api/journal/add", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ content: text })
    });
    alert("Journal saved!");
    document.getElementById('entryContent').value = '';
};
</script>