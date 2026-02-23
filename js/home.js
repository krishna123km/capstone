(function () {
  'use strict';

  var grid = document.getElementById('paint-grid');
  var searchInput = document.getElementById('search');
  var noResults = document.getElementById('no-results');
  var products = window.PAINT_PRODUCTS || [];

  function renderCard(p) {
    var card = document.createElement('article');
    card.className = 'card';
    card.setAttribute('role', 'listitem');
    card.innerHTML =
      '<div class="card-img-wrap">' +
        '<img src="' + (p.image || '') + '" alt="' + (p.title || '') + '" width="400" height="400" loading="lazy">' +
      '</div>' +
      '<div class="card-body">' +
        '<h2 class="card-title">' + (p.title || '') + '</h2>' +
        '<div class="card-meta">' +
          '<span>₹' + (p.price || 0).toLocaleString('en-IN') + '</span>' +
          (p.rating ? '<span class="rating">★ ' + p.rating + '</span>' : '') +
        '</div>' +
        '<a href="product.html?id=' + encodeURIComponent(p.id) + '" class="btn btn-primary mt-1" style="width:100%;">View Details</a>' +
      '</div>';
    return card;
  }

  function renderList(list) {
    if (!grid) return;
    grid.innerHTML = '';
    noResults.style.display = list.length ? 'none' : 'block';
    list.forEach(function (p) {
      grid.appendChild(renderCard(p));
    });
  }

  function filterProducts() {
    var q = (searchInput && searchInput.value) ? searchInput.value.trim().toLowerCase() : '';
    if (!q) {
      renderList(products);
      return;
    }
    var filtered = products.filter(function (p) {
      return (p.title || '').toLowerCase().indexOf(q) !== -1;
    });
    renderList(filtered);
  }

  if (searchInput) {
    searchInput.addEventListener('input', filterProducts);
    searchInput.addEventListener('search', filterProducts);
    searchInput.addEventListener('change', filterProducts);
  }

  document.addEventListener('DOMContentLoaded', function () {
    renderList(products);
  });
})();
