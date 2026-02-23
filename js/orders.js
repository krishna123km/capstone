(function () {
  'use strict';

  var list = document.getElementById('orders-list');
  var emptyEl = document.getElementById('orders-empty');

  document.addEventListener('DOMContentLoaded', function () {
    if (!Storage.isLoggedIn()) {
      window.location.href = 'index.html';
      return;
    }

    var orders = Storage.getOrders();
    if (!orders.length) {
      if (list) list.innerHTML = '';
      if (emptyEl) emptyEl.style.display = 'block';
      return;
    }

    if (emptyEl) emptyEl.style.display = 'none';
    if (!list) return;

    list.innerHTML = '';
    orders.forEach(function (order) {
      var card = document.createElement('div');
      card.className = 'order-card';
      var date = new Date(order.date);
      var itemsHtml = (order.items || []).map(function (item) {
        return '<li>' + (item.title || '') + ' × ' + (item.quantity || 1) + ' — ₹' + ((item.price || 0) * (item.quantity || 1)).toLocaleString('en-IN') + '</li>';
      }).join('');
      card.innerHTML =
        '<h3>Order ID: ' + (order.id || '') + '</h3>' +
        '<p style="margin:0; color: var(--text-secondary); font-size:0.9rem;">' + date.toLocaleString() + '</p>' +
        '<ul class="order-items">' + itemsHtml + '</ul>' +
        '<p class="order-total">Total: ₹' + (order.total || 0).toLocaleString('en-IN') + '</p>';
      list.appendChild(card);
    });
  });
})();
