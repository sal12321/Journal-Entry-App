//    const API_URL = 'https://journal-entry-app-production.up.railway.app/';
//    const API_URL = 'https://journal-entry-app-production.up.railway.app';
//const API_URL = 'http://localhost:8080';
const API_URL = "";

let authToken = '';
let currentUser = {};
console.log("JS file loaded!");
let isAdmin = false;

// Toast Notification System
function showToast(message, type = 'success', title = '') {
    // Remove any existing toasts and backdrops
    const existingToast = document.querySelector('.toast');
    const existingBackdrop = document.querySelector('.toast-backdrop');
    if (existingToast) existingToast.remove();
    if (existingBackdrop) existingBackdrop.remove();

    const icons = {
        success: '✅',
        error: '❌',
        info: 'ℹ️',
        warning: '⚠️'
    };

    const titles = {
        success: title || 'Success!',
        error: title || 'Error!',
        info: title || 'Info',
        warning: title || 'Warning!'
    };

    // Create backdrop
    const backdrop = document.createElement('div');
    backdrop.className = 'toast-backdrop';
    document.body.appendChild(backdrop);

    // Create toast
    const toast = document.createElement('div');
    toast.className = `toast toast-${type}`;
    toast.innerHTML = `
        <div class="toast-icon">${icons[type]}</div>
        <div class="toast-content">
            <div class="toast-title">${titles[type]}</div>
            <div class="toast-message">${message}</div>
        </div>
        <button class="toast-close" onclick="closeToast()">×</button>
    `;

    document.body.appendChild(toast);

    // Trigger animations
    setTimeout(() => {
        backdrop.classList.add('show');
        toast.classList.add('show');
    }, 10);

    // Auto remove after 3.5 seconds
    setTimeout(() => {
        closeToast();
    }, 3500);
}

function closeToast() {
    const toast = document.querySelector('.toast');
    const backdrop = document.querySelector('.toast-backdrop');

    if (toast) {
        toast.classList.add('hide');
        setTimeout(() => toast.remove(), 300);
    }

    if (backdrop) {
        backdrop.classList.remove('show');
        setTimeout(() => backdrop.remove(), 300);
    }
}

function showSuccess(message) {
    showToast(message, 'success');
}

function showErrorToast(message) {
    showToast(message, 'error');
}

function showInfoToast(message) {
    showToast(message, 'info');
}

function showWarningToast(message) {
    showToast(message, 'warning');
}

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

        const data = await response.json();
        console.log('Response from backend:', data);

        if (data.jwt != "") {
            authToken = data.jwt;
            currentUser = { userName };

            // Hide login, show journal
            document.getElementById('loginSection').classList.add('hidden');
            document.getElementById('journalSection').classList.remove('hidden');
            document.getElementById('logoutBtn').style.display = 'block';
            document.getElementById('welcomeText').textContent = `Welcome, ${userName}!`;

            try {
                if (data.role && data.role == "ADMIN") {
                    isAdmin = true;
                }
            } catch (error) {
                console.error('Error checking admin status:', error);
            }

            if (isAdmin) {
                document.getElementById('adminBtn').style.display = 'block';
            }

            // Show success toast
            showSuccess(`Welcome back, ${userName}! 🎉`);

            // Load user's journal entries
            loadJournalEntries();
        } else {
            console.log('❌ Login failed - backend returned empty string');
            showError('loginError', 'Invalid username or password');
        }
    } catch (error) {
        console.error('Login error:', error);
        showError('loginError', 'Login failed. Please check if backend is running.');
        showErrorToast('Connection error. Please check your internet connection.');
    }
}

async function signup(e) {
    e.preventDefault();
    const userName = document.getElementById('signupUsername').value;
    const email = document.getElementById('signupEmail').value;
    const password = document.getElementById('signupPassword').value;
    const sentimentAnalysis = document.getElementById('sentimentAnalysis').checked;

    try {
        const response = await fetch(`${API_URL}/public/signup`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ userName, email, password, sentimentAnalysis })
        });

        const data = await response.json();

        if (response.status === 201) {
            showSuccess('Account created successfully! Please login to continue.');
            showLogin();
        } else if (response.status === 409) {
            showError('signupError', data.message);
            showWarningToast(data.message || 'Username already exists');
        } else {
            showError('signupError', 'Signup failed. Please try again.');
            showErrorToast('Signup failed. Please try again.');
        }
    } catch (error) {
        console.error('Signup error:', error);
        showError('signupError', 'Connection error. Please try again.');
        showErrorToast('Connection error. Please check your internet connection.');
    }
}

function logout() {
    authToken = '';
    currentUser = {};
    document.getElementById('journalSection').classList.add('hidden');
    document.getElementById('loginSection').classList.remove('hidden');
    document.getElementById('logoutBtn').style.display = 'none';
    document.getElementById('welcomeText').textContent = '';
    isAdmin = false;
    document.getElementById('adminBtn').style.display = 'none';
    document.getElementById('adminSection').classList.add('hidden');

    showInfoToast('You have been logged out successfully');
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
            showErrorToast('Failed to load journal entries');
        }
    } catch (error) {
        console.error('Error loading entries:', error);
        showErrorToast('Connection error while loading entries');
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
            showSuccess('Journal entry created successfully! ✍️');
            loadJournalEntries();
        } else {
            showError('entryError', 'Failed to create entry');
            showErrorToast('Failed to create entry. Please try again.');
        }
    } catch (error) {
        console.error('Create entry error:', error);
        showError('entryError', 'Failed to create entry');
        showErrorToast('Connection error. Please check your internet connection.');
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
            showErrorToast('Failed to load entry for editing');
        }
    } catch (error) {
        console.error('Edit entry error:', error);
        showError('editEntryError', 'Failed to load entry');
        showErrorToast('Connection error while loading entry');
    }
}

async function updateEntry(e) {
    e.preventDefault();
    const entryId = document.getElementById('editEntryId').value;
    const title = document.getElementById('editEntryTitle').value;
    const content = document.getElementById('editEntryContent').value;
    const sentiment = document.getElementById('editEntrySentiment').value;

    const entry = { title, content };
    if (sentiment) {
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
            showSuccess('Entry updated successfully! 📝');
            loadJournalEntries();
        } else {
            showError('editEntryError', 'Failed to update entry');
            showErrorToast('Failed to update entry. Please try again.');
        }
    } catch (error) {
        console.error('Update entry error:', error);
        showError('editEntryError', 'Failed to update entry');
        showErrorToast('Connection error while updating entry');
    }
}

async function deleteEntry(entryId) {
    if (!confirm('Are you sure you want to delete this entry?')) return;

    try {
        const response = await fetch(`${API_URL}/journal/id/${entryId}`, {
            method: 'DELETE',
            headers: { 'Authorization': `Bearer ${authToken}` }
        });

        if (response.ok) {
            showSuccess('Entry deleted successfully! 🗑️');
            loadJournalEntries();
        } else {
            showErrorToast('Failed to delete entry. Please try again.');
        }
    } catch (error) {
        console.error('Delete entry error:', error);
        showErrorToast('Connection error while deleting entry');
    }
}

async function sendSentimentEmail() {
    showInfoToast('Preparing your sentiment report... 📧');

    try {
        const response = await fetch(`${API_URL}/user/send-me-mail`, {
            method: 'POST',
            headers: { 'Authorization': `Bearer ${authToken}` }
        });

        if (response.ok) {
            showSuccess('Sentiment report sent to your email! Check your inbox. 📬');
        } else {
            showErrorToast('Failed to send sentiment report. Please try again.');
        }
    } catch (error) {
        console.error('Send email error:', error);
        showErrorToast('Connection error while sending email');
    }
}

async function checkIfAdmin() {
    try {
        const response = await fetch(`${API_URL}/user`, {
            headers: { 'Authorization': `Bearer ${authToken}` }
        });

        if (response.ok) {
            const userData = await response.json();
            if (userData.roles && userData.roles.includes('ADMIN')) {
                isAdmin = true;
            }
        }
    } catch (error) {
        console.error('Error checking admin status:', error);
    }
}

function showAdminPanel() {
    document.getElementById('journalSection').classList.add('hidden');
    document.getElementById('adminSection').classList.remove('hidden');
    document.getElementById('adminBtn').style.display = 'none';
    showInfoToast('Loading admin panel...');
    loadAllUsers();
}

function showBackToJournal() {
    document.getElementById('adminSection').classList.add('hidden');
    document.getElementById('journalSection').classList.remove('hidden');
    document.getElementById('adminBtn').style.display = 'block';
}

async function loadAllUsers() {
    try {
        const response = await fetch(`${API_URL}/admin/all-users`, {
            headers: { 'Authorization': `Bearer ${authToken}` }
        });

        if (response.ok) {
            const users = await response.json();
            displayAllUsers(users);
            showSuccess(`Loaded ${users.length} users successfully`);
        } else {
            showErrorToast('Failed to load users');
        }
    } catch (error) {
        console.error('Error loading users:', error);
        showErrorToast('Connection error while loading users');
    }
}

function displayAllUsers(users) {
    const container = document.getElementById('allUsersList');

    if (!users || users.length === 0) {
        container.innerHTML = '<div class="no-entries">No users found</div>';
        return;
    }

    container.innerHTML = users.map(user => `
        <div class="entry-card">
            <div class="entry-title">${user.userName}</div>
            <div class="entry-content">
                <strong>Email:</strong> ${user.email || 'N/A'}<br>
                <strong>Roles:</strong> ${user.roles ? user.roles.join(', ') : 'USER'}<br>
                <strong>Entries:</strong> ${user.journalEntries ? user.journalEntries.length : 0}
            </div>
        </div>
    `).join('');
}

function showCreateAdminModal() {
    document.getElementById('createAdminModal').classList.add('active');
}

function closeCreateAdminModal() {
    document.getElementById('createAdminModal').classList.remove('active');
    document.getElementById('adminUsername').value = '';
    document.getElementById('adminPassword').value = '';
    document.getElementById('signupEmailAdmin').value = '';
    document.getElementById('sentimentAnalysisAdmin').checked = false;
}

async function createAdmin(e) {
    e.preventDefault();
    const userName = document.getElementById('adminUsername').value;
    const password = document.getElementById('adminPassword').value;
    const sentiment = document.getElementById('sentimentAnalysisAdmin').checked;
    const email = document.getElementById('signupEmailAdmin').value;

    try {
        const response = await fetch(`${API_URL}/admin/create-admin`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${authToken}`
            },
            body: JSON.stringify({ userName, password, email, sentiment })
        });

        if (response.ok) {
            closeCreateAdminModal();
            showSuccess('Admin created successfully! 👤');
            loadAllUsers();
        } else {
            showError('adminError', 'Failed to create admin');
            showErrorToast('Failed to create admin. Please try again.');
        }
    } catch (error) {
        console.error('Create admin error:', error);
        showError('adminError', 'Failed to create admin');
        showErrorToast('Connection error while creating admin');
    }
}