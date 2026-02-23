(function () {
  'use strict';

  var formWrap = document.getElementById('checkout-form-wrap');
  var emptyEl = document.getElementById('checkout-empty');
  var orderSummary = document.getElementById('order-summary');
  var form = document.getElementById('payment-form');
  var successEl = document.getElementById('checkout-success');
  var successOrderId = document.getElementById('success-order-id');
  var successDate = document.getElementById('success-date');
  var successTotal = document.getElementById('success-total');

  function generateOrderId() {
    return 'ORD-' + Date.now() + '-' + Math.random().toString(36).slice(2, 8).toUpperCase();
  }

  function formatCardNumber(value) {
    var v = value.replace(/\s/g, '').replace(/\D/g, '');
    var parts = [];
    for (var i = 0; i < v.length && i < 16; i += 4) parts.push(v.slice(i, i + 4));
    return parts.join(' ');
  }

  function formatExpiry(value) {
    var v = value.replace(/\D/g, '');
    if (v.length >= 2) return v.slice(0, 2) + '/' + v.slice(2, 4);
    return v;
  }

  var cardNumber = document.getElementById('card-number');
  var cardExpiry = document.getElementById('card-expiry');
  if (cardNumber) {
    cardNumber.addEventListener('input', function () {
      this.value = formatCardNumber(this.value);
    });
  }
  if (cardExpiry) {
    cardExpiry.addEventListener('input', function () {
      this.value = formatExpiry(this.value);
    });
  }

  function renderSummary(cart, total) {
    if (!orderSummary) return;
    var html = '<h3 style="margin:0 0 0.5rem;">Order Summary</h3>';
    cart.forEach(function (item) {
      var qty = item.quantity || 1;
      html += '<p class="order-items" style="margin:0.25rem 0;">' + (item.title || '') + ' × ' + qty + ' — ₹' + ((item.price || 0) * qty).toLocaleString('en-IN') + '</p>';
    });
    html += '<p class="order-total" style="margin-top:0.5rem;">Total: ₹' + (total || 0).toLocaleString('en-IN') + '</p>';
    orderSummary.innerHTML = html;
  }

  document.addEventListener('DOMContentLoaded', function () {
    if (!Storage.isLoggedIn()) {
      window.location.href = 'index.html';
      return;
    }

    var cart = Storage.getCart();
    if (!cart.length) {
      if (formWrap) formWrap.style.display = 'none';
      if (emptyEl) emptyEl.style.display = 'block';
      return;
    }

    var total = cart.reduce(function (sum, item) {
      return sum + (item.price || 0) * (item.quantity || 1);
    }, 0);
    renderSummary(cart, total);
  });

  function showPaymentError(msg) {
    var existing = form.querySelector('.payment-error');
    if (existing) existing.remove();
    var p = document.createElement('p');
    p.className = 'auth-error visible payment-error';
    p.style.marginBottom = '1rem';
    p.textContent = msg;
    form.insertBefore(p, form.firstChild);
  }

  if (form) {
    form.addEventListener('submit', function (e) {
      e.preventDefault();
      var err = form.querySelector('.payment-error');
      if (err) err.remove();
      var cardNum = (document.getElementById('card-number').value || '').replace(/\s/g, '');
      var expiry = (document.getElementById('card-expiry').value || '').trim();
      var cvv = (document.getElementById('card-cvv').value || '').trim();
      if (cardNum.length < 13 || cardNum.length > 19) {
        showPaymentError('Please enter a valid card number.');
        return;
      }
      var expiryMatch = expiry.match(/^(\d{2})\/(\d{2})$/);
      if (!expiryMatch) {
        showPaymentError('Enter expiry as MM/YY.');
        return;
      }
      if (cvv.length < 3 || cvv.length > 4) {
        showPaymentError('Enter a valid CVV (3 or 4 digits).');
        return;
      }
      var cart = Storage.getCart();
      if (!cart.length) {
        if (formWrap) formWrap.style.display = 'none';
        if (emptyEl) emptyEl.style.display = 'block';
        return;
      }

      var total = cart.reduce(function (sum, item) {
        return sum + (item.price || 0) * (item.quantity || 1);
      }, 0);

      var orderId = generateOrderId();
      var date = new Date();
      var order = {
        id: orderId,
        date: date.toISOString(),
        items: cart.map(function (item) {
          return {
            id: item.id,
            title: item.title,
            price: item.price,
            quantity: item.quantity || 1
          };
        }),
        total: total
      };
      Storage.addOrder(order);
      Storage.setCart([]);
      if (window.Nav && window.Nav.updateCartCount) window.Nav.updateCartCount();

      if (formWrap) formWrap.style.display = 'none';
      if (successOrderId) successOrderId.textContent = orderId;
      if (successDate) successDate.textContent = date.toLocaleString();
      if (successTotal) successTotal.textContent = '₹' + total.toLocaleString('en-IN');
      if (successEl) successEl.style.display = 'block';
    });
  }
})();
