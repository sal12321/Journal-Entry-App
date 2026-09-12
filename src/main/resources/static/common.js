// const API_URL = 'http://localhost:8080/api';
import API_URL from "./urls"
const API_URL = API_URL+"/api";
// const API_URL = 'https://mindlog-production-97b0.up.railway.app/api';

// ---------- Auth helpers (persisted so they survive page navigation) ----------
function getAuthToken() {
    return localStorage.getItem('authToken') || '';
}
function setAuthToken(token) {
    localStorage.setItem('authToken', token);
}
function clearAuthToken() {
    localStorage.removeItem('authToken');
    localStorage.removeItem('currentUser');
    localStorage.removeItem('isAdmin');
}
function getCurrentUser() {
    try { return JSON.parse(localStorage.getItem('currentUser')) || {}; }
    catch (e) { return {}; }
}
function setCurrentUser(user) {
    localStorage.setItem('currentUser', JSON.stringify(user));
}
function getIsAdmin() {
    return localStorage.getItem('isAdmin') === 'true';
}
function setIsAdmin(value) {
    localStorage.setItem('isAdmin', value ? 'true' : 'false');
}

// Redirects to login.html if there's no token. Call at the top of protected pages.
function requireAuth() {
    if (!getAuthToken()) {
        window.location.href = '/login';
    }
}

function logout() {
    clearAuthToken();
    showInfoToast('You have been logged out successfully');
    setTimeout(() => window.location.href = '/login', 500);
}

// ---------- Navbar (welcome text / logout / admin button) ----------
function renderNavbar() {
    const user = getCurrentUser();
    const welcomeText = document.getElementById('welcomeText');
    const logoutBtn = document.getElementById('logoutBtn');
    const adminBtn = document.getElementById('adminBtn');

    if (welcomeText && user.userName) welcomeText.textContent = `Welcome, ${user.userName}!`;
    if (logoutBtn) logoutBtn.style.display = getAuthToken() ? 'block' : 'none';
    if (adminBtn) adminBtn.style.display = (getIsAdmin() && !window.location.pathname.endsWith('admin.html')) ? 'block' : 'none';
}

// ---------- Toast Notification System ----------
function showToast(message, type = 'success', title = '') {
    const existingToast = document.querySelector('.toast');
    const existingBackdrop = document.querySelector('.toast-backdrop');
    if (existingToast) existingToast.remove();
    if (existingBackdrop) existingBackdrop.remove();

    const icons = { success: '✅', error: '❌', info: 'ℹ️', warning: '⚠️' };
    const titles = {
        success: title || 'Success!',
        error: title || 'Error!',
        info: title || 'Info',
        warning: title || 'Warning!'
    };

    const backdrop = document.createElement('div');
    backdrop.className = 'toast-backdrop';
    document.body.appendChild(backdrop);

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

    setTimeout(() => {
        backdrop.classList.add('show');
        toast.classList.add('show');
    }, 10);

    setTimeout(() => closeToast(), 3500);
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

function showSuccess(message) { showToast(message, 'success'); }
function showErrorToast(message) { showToast(message, 'error'); }
function showInfoToast(message) { showToast(message, 'info'); }
function showWarningToast(message) { showToast(message, 'warning'); }

function showError(elementId, message) {
    const el = document.getElementById(elementId);
    if (!el) return;
    el.textContent = message;
    el.classList.remove('hidden');
    setTimeout(() => el.classList.add('hidden'), 5000);
}
