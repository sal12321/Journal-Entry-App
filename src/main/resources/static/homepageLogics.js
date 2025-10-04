
    const API_URL = 'https://journal-entry-app-production.up.railway.app';
    let authToken = '';
    let currentUser = {};
    console.log("JS file loaded!");


    // Helper function to log API calls for debugging
    function logAPICall(method, endpoint, data = null) {
        console.log('=== API CALL ===');
        console.log('Method:', method);
        console.log('URL:', `${API_URL}${endpoint}`);
        if (data) console.log('Body:', JSON.stringify(data, null, 2));
        if (authToken) console.log('Token:', authToken.substring(0, 20) + '...');
        console.log('===============');
    }

    function showError(elementId, message) {
        const el = document.getElementById(elementId);
        el.textContent = message;
        el.classList.remove('hidden');
        setTimeout(() => el.classList.add('hidden'), 5000);
    }

    function showSuccess(message) {
        alert(message);
    }

    function showLogin() {
        document.getElementById('loginSection').classList.remove('hidden');
        document.getElementById('signupSection').classList.add('hidden');
    }

    function showSignup() {
        document.getElementById('loginSection').classList.add('hidden');
        document.getElementById('signupSection').classList.remove('hidden');
    }

    async function login(e) {
        e.preventDefault();
        const userName = document.getElementById('loginUsername').value;
        const password = document.getElementById('loginPassword').value;

        const requestData = { userName, password };
        logAPICall('POST', '/public/login', requestData);

        try {
            const response = await fetch(`${API_URL}/public/login`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(requestData)
            });

            console.log('Response status:', response.status);

            // Your backend returns plain text (JWT token or empty string)
            const responseText = await response.text();
//            console.log('Response from backend:', responseText);

            // Check if we got a token (non-empty string)
            if (responseText && responseText.trim() !== '') {
                authToken = responseText.trim();
//                console.log('✅ Login successful! Token saved:', authToken.substring(0, 5) + '...');

                currentUser = { userName };

                // Hide login, show journal
                document.getElementById('loginSection').classList.add('hidden');
                document.getElementById('journalSection').classList.remove('hidden');
                document.getElementById('logoutBtn').style.display = 'block';
                document.getElementById('welcomeText').textContent = `Welcome, ${userName}!`;

                // Load user's journal entries
                loadJournalEntries();
            } else {
                // Empty string = login failed
                console.log('❌ Login failed - backend returned empty string');
                showError('loginError', 'Invalid username or password');
            }
        } catch (error) {
            console.error('Login error:', error);
            showError('loginError', 'Login failed. Please check if backend is running. Error: ' + error.message);
        }
    }

    async function signup(e) {
        e.preventDefault();
        const userName = document.getElementById('signupUsername').value;
        const email = document.getElementById('signupEmail').value;
        const password = document.getElementById('signupPassword').value;
        const sentimentAnalysis = document.getElementById('sentimentAnalysis').checked;


        const response = await fetch(`${API_URL}/public/signup`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ userName, email, password, sentimentAnalysis })
});

    const data = await response.json();


if (response.status === 201) {


    showSuccess(data.message); // "Account created successfully"
    showLogin();
} else if (response.status === 409) {

    showError('signupError', data.message); // "Username already exists"
} else {

    showError('signupError', 'Signup failed. Please try again.');
}



    }

    function logout() {
        authToken = '';
        currentUser = {};
        document.getElementById('journalSection').classList.add('hidden');
        document.getElementById('loginSection').classList.remove('hidden');
        document.getElementById('logoutBtn').style.display = 'none';
        document.getElementById('welcomeText').textContent = '';
    }

    async function loadJournalEntries() {
        try {
            const response = await fetch(`${API_URL}/journal`, {
                headers: { 'Authorization': `Bearer ${authToken}` }
            });

            if (response.ok) {
                const entries = await response.json();
                displayEntries(entries);


            } else {
                showError('entryError', 'Failed to load entries');
            }
        } catch (error) {
            console.error('Error loading entries:', error);
        }
    }

    function displayEntries(entries) {
        const container = document.getElementById('journalEntries');

        if (!entries || entries.length === 0) {
            container.innerHTML = '<div class="no-entries">No entries yet. Create your first journal entry!</div>';
            return;
        }

        container.innerHTML = entries.map(entry => `


            <div class="entry-card">
                <div class="entry-title">${entry.title}</div>
                <div class="entry-date">${new Date(entry.date).toLocaleDateString('en-US', {
                    year: 'numeric',
                    month: 'long',
                    day: 'numeric',
                    hour: '2-digit',
                    minute: '2-digit'
                })}</div>
                ${entry.sentiment ? `<span class="sentiment-badge sentiment-${entry.sentiment}">${entry.sentiment}</span>` : ''}
                <div class="entry-content">${entry.content}</div>
                <div class="entry-actions">
                    <button class="btn btn-small" onclick="editEntry('${entry.id}')">Edit</button>
                    <button class="btn btn-small btn-danger" onclick="deleteEntry('${entry.id}')">Delete</button>
                </div>
            </div>
        `).join('');
    }

    function showNewEntryModal() {
        document.getElementById('newEntryModal').classList.add('active');
    }

    function closeNewEntryModal() {
        document.getElementById('newEntryModal').classList.remove('active');
        document.getElementById('entryTitle').value = '';
        document.getElementById('entryContent').value = '';
        document.getElementById('entrySentiment').value = '';
    }

    async function createEntry(e) {
        e.preventDefault();
        const title = document.getElementById('entryTitle').value;
        const content = document.getElementById('entryContent').value;
        const sentiment = document.getElementById('entrySentiment').value;

        const entry = { title, content };
        if (sentiment) entry.sentiment = sentiment;

        try {
            const response = await fetch(`${API_URL}/journal`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${authToken}`
                },
                body: JSON.stringify(entry)
            });

            if (response.ok) {
                closeNewEntryModal();
                showSuccess('Entry created successfully!');
                loadJournalEntries();
            } else {
                showError('entryError', 'Failed to create entry');
            }
        } catch (error) {
            showError('entryError', 'Failed to create entry');
        }
    }

    function closeEditEntryModal() {
        document.getElementById('editEntryModal').classList.remove('active');
    }



    async function editEntry(entryId) {
        try {
            const response = await fetch(`${API_URL}/journal/id/${entryId}`, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${authToken}`,
                    'Content-Type': 'application/json'
                }
            });

            if (response.ok) {
                const entry = await response.json();
                document.getElementById('editEntryId').value = entryId;
                document.getElementById('editEntryTitle').value = entry.title;
                document.getElementById('editEntryContent').value = entry.content;
                document.getElementById('editEntrySentiment').value = entry.sentiment || '';
                document.getElementById('editEntryModal').classList.add('active');


            } else {
                showError('editEntryError', `Failed to load entry: ${response.status}`);
            }
        } catch (error) {
            console.error(error);
            showError('editEntryError', 'Failed to load entry');
        }
    }


        async function updateEntry(e) {
        e.preventDefault();
        const entryId = document.getElementById('editEntryId').value;
        const title = document.getElementById('editEntryTitle').value;
        const content = document.getElementById('editEntryContent').value;
        const sentiment = document.getElementById('editEntrySentiment').value;


        const entry = { title, content };
        if (sentiment){
        entry.sentiment = sentiment;

        }

        try {
            const response = await fetch(`${API_URL}/journal/id/${entryId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${authToken}`
                },
                body: JSON.stringify(entry)
            });

            if (response.status == 200) {

                closeEditEntryModal();
                showSuccess('Entry updated successfully!');
                loadJournalEntries();

            } else {
                showError('editEntryError', 'Failed to update entry');
            }
        } catch (error) {
            showError('editEntryError', 'Failed to update entry');
        }
    }

    async function deleteEntry(entryId) {

        if (!confirm('Are you sure you want to delete this entry?')) return;

<!--        const entryId = { timestamp: parseInt(timestamp), date: date };-->

        try {
            const response = await fetch(`${API_URL}/journal/id/${entryId}`, {
                method: 'DELETE',
                headers: { 'Authorization': `Bearer ${authToken}` }
            });

            if (response.ok) {
                showSuccess('Entry deleted successfully!');
                loadJournalEntries();
            } else {
                alert('Failed to delete entry');
            }
        } catch (error) {
            alert('Failed to delete entry');
        }
    }

    async function sendSentimentEmail() {
        try {
            const response = await fetch(`${API_URL}/user/send-me-mail`, {
                method: 'POST',
                headers: { 'Authorization': `Bearer ${authToken}` }
            });

            if (response.ok) {
                showSuccess('Sentiment report sent to your email!');
            } else {
                alert('Failed to send sentiment report');
            }
        } catch (error) {
            alert('Failed to send sentiment report');
        }
    }