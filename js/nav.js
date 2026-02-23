(function () {
  'use strict';

  if (typeof Storage === 'undefined') return;

  function updateCartCount() {
    var el = document.getElementById('cart-count');
    if (!el) return;
    var cart = Storage.getCart();
    var total = cart.reduce(function (sum, item) { return sum + (item.quantity || 1); }, 0);
    el.textContent = total ? '(' + total + ')' : '';
  }

  document.addEventListener('DOMContentLoaded', function () {
    if (!Storage.isLoggedIn()) {
      window.location.href = 'index.html';
      return;
    }
    updateCartCount();
    var logout = document.getElementById('nav-logout');
    if (logout) {
      logout.addEventListener('click', function (e) {
        e.preventDefault();
        Storage.logout();
        window.location.href = 'index.html';
      });
    }
  });

  window.Nav = { updateCartCount: updateCartCount };
})();
