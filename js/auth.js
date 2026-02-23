(function () {
  'use strict';

  if (typeof Storage === 'undefined') return;

  var form = document.getElementById('auth-form');
  var title = document.getElementById('auth-title');
  var errorEl = document.getElementById('auth-error');
  var toggleText = document.getElementById('auth-toggle-text');
  var toggleLink = document.getElementById('auth-toggle-link');
  var isSignup = false;

  function showError(msg) {
    errorEl.textContent = msg;
    errorEl.classList.add('visible'); 
  }
  function clearError() {
    errorEl.textContent = '';
    errorEl.classList.remove('visible');
  }

  function setMode(signup) {
    isSignup = signup;
    title.textContent = signup ? 'Sign up' : 'Login';
    form.querySelector('button[type="submit"]').textContent = signup ? 'Sign up' : 'Login';
    toggleText.textContent = signup ? 'Already have an account?' : "Don't have an account?";
    toggleLink.textContent = signup ? 'Login' : 'Sign up';
    clearError();
  }

  toggleLink.addEventListener('click', function (e) {
    e.preventDefault();
    setMode(!isSignup);
  });

  form.addEventListener('submit', function (e) {
    e.preventDefault();
    clearError();
    var email = (document.getElementById('email').value || '').trim().toLowerCase();
    var password = document.getElementById('password').value;

    if (!email || !password) {
      showError('Please fill in all fields.');
      return;
    }
    if (password.length < 6) {
      showError('Password must be at least 6 characters.');
      return;
    }

    var users = Storage.getUsers();

    if (isSignup) {
      if (users.some(function (u) { return u.email === email; })) {
        showError('An account with this email already exists.');
        return;
      }
      users.push({ email: email, password: password });
      Storage.setUsers(users);
      Storage.setCurrentUser({ email: email });
      window.location.href = 'home.html';
    } else {
      var user = users.find(function (u) { return u.email === email && u.password === password; });
      if (!user) {
        showError('Invalid email or password.');
        return;
      }
      Storage.setCurrentUser({ email: email });
      window.location.href = 'home.html';
    }
  });

  if (Storage.isLoggedIn()) {
    window.location.href = 'home.html';
  }
})();
