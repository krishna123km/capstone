(function () {
  'use strict';

  var content = document.getElementById('cart-content');
  var totalSection = document.getElementById('cart-total-section');
  var grandTotalEl = document.getElementById('grand-total');

  function updateCart() {
    var cart = Storage.getCart();
    if (window.Nav && window.Nav.updateCartCount) window.Nav.updateCartCount();

    if (!cart.length) {
      if (content) content.innerHTML = '<p class="empty-cart">Your cart is empty. <a href="home.html">Browse paint</a></p>';
      if (totalSection) totalSection.style.display = 'none';
      return;
    }

    var total = 0;
    var table = '<table class="cart-table" role="table"><thead><tr><th>Item</th><th>Price</th><th>Quantity</th><th>Total</th><th></th></tr></thead><tbody>';
    cart.forEach(function (item, index) {
      var qty = item.quantity || 1;
      var itemTotal = (item.price || 0) * qty;
      total += itemTotal;
      table +=
        '<tr data-index="' + index + '">' +
          '<td><strong>' + (item.title || '') + '</strong></td>' +
          '<td>₹' + (item.price || 0).toLocaleString('en-IN') + '</td>' +
          '<td><div class="cart-qty">' +
            '<button type="button" aria-label="Decrease quantity" data-action="minus">−</button>' +
            '<span>' + qty + '</span>' +
            '<button type="button" aria-label="Increase quantity" data-action="plus">+</button>' +
          '</div></td>' +
          '<td>₹' + itemTotal.toLocaleString('en-IN') + '</td>' +
          '<td><button type="button" class="btn btn-danger" data-action="remove">Remove</button></td>' +
        '</tr>';
    });
    table += '</tbody></table>';
    if (content) content.innerHTML = table;
    if (grandTotalEl) grandTotalEl.textContent = '₹' + total.toLocaleString('en-IN');
    if (totalSection) totalSection.style.display = 'block';

    content.querySelectorAll('[data-action]').forEach(function (btn) {
      btn.addEventListener('click', function () {
        var row = btn.closest('tr');
        var idx = row ? parseInt(row.getAttribute('data-index'), 10) : -1;
        var action = btn.getAttribute('data-action');
        var cart = Storage.getCart();
        if (idx < 0 || idx >= cart.length) return;
        var item = cart[idx];
        if (action === 'plus') {
          item.quantity = (item.quantity || 1) + 1;
        } else if (action === 'minus') {
          item.quantity = Math.max(1, (item.quantity || 1) - 1);
        } else if (action === 'remove') {
          cart.splice(idx, 1);
        }
        Storage.setCart(cart);
        updateCart();
      });
    });
  }

  document.addEventListener('DOMContentLoaded', function () {
    if (!Storage.isLoggedIn()) {
      window.location.href = 'index.html';
      return;
    }
    updateCart();
  });
})();
