(function () {
  'use strict';

  var root = document.getElementById('product-root');
  var notFound = document.getElementById('product-not-found');
  var products = window.PAINT_PRODUCTS || [];

  function getProductId() {
    var params = new URLSearchParams(window.location.search);
    return params.get('id') || '';
  }

  function addToCart(product, quantity) {
    quantity = quantity || 1;
    var cart = Storage.getCart();
    var existing = cart.find(function (item) { return item.id === product.id; });
    if (existing) {
      existing.quantity = (existing.quantity || 1) + quantity;
    } else {
      cart.push({
        id: product.id,
        title: product.title,
        price: product.price,
        image: product.image,
        quantity: quantity
      });
    }
    Storage.setCart(cart);
    if (window.Nav && window.Nav.updateCartCount) window.Nav.updateCartCount();
  }

  function render(p) {
    if (!root) return;
    root.innerHTML =
      '<div class="product-image">' +
        '<img src="' + (p.image || '') + '" alt="' + (p.title || '') + '" width="600" height="600" fetchpriority="high" decoding="async">' +
      '</div>' +
      '<div class="product-info">' +
        '<h1>' + (p.title || '') + '</h1>' +
        '<p class="product-price">₹' + (p.price || 0).toLocaleString('en-IN') + '</p>' +
        '<div class="product-meta">' +
          (p.genre ? '<span>Genre: ' + p.genre + '</span>' : '') +
          (p.rating ? ' <span class="rating">★ ' + p.rating + '</span>' : '') +
        '</div>' +
        '<p class="product-desc">' + (p.description || '') + '</p>' +
        '<button type="button" class="btn btn-primary" id="add-to-cart">Add to Cart</button>' +
      '</div>';

    var btn = document.getElementById('add-to-cart');
    if (btn) {
      btn.addEventListener('click', function () {
        addToCart(p);
        btn.textContent = 'Added!';
        setTimeout(function () { btn.textContent = 'Add to Cart'; }, 1500);
      });
    }
  }

  document.addEventListener('DOMContentLoaded', function () {
    if (!Storage.isLoggedIn()) {
      window.location.href = 'index.html';
      return;
    }
    var id = getProductId();
    var product = products.find(function (p) { return String(p.id) === String(id); });
    if (product) {
      document.title = product.title + ' | Paint Store';
      render(product);
    } else {
      if (root) root.innerHTML = '';
      if (notFound) notFound.style.display = 'block';
    }
  });
})();
