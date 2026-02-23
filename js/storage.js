(function () {
  'use strict';

  const USERS_KEY = 'paint_store_users';
  const CURRENT_USER_KEY = 'paint_store_current_user';
  const CART_KEY = 'paint_store_cart';
  const ORDERS_KEY = 'paint_store_orders';

  window.Storage = {
    getUsers: function () {
      try {
        const data = localStorage.getItem(USERS_KEY);
        return data ? JSON.parse(data) : [];
      } catch (_) {
        return [];
      }
    },
    setUsers: function (users) {
      localStorage.setItem(USERS_KEY, JSON.stringify(users));
    },
    getCurrentUser: function () {
      try {
        const data = localStorage.getItem(CURRENT_USER_KEY);
        return data ? JSON.parse(data) : null;
      } catch (_) {
        return null;
      }
    },
    setCurrentUser: function (user) {
      if (user) localStorage.setItem(CURRENT_USER_KEY, JSON.stringify(user));
      else localStorage.removeItem(CURRENT_USER_KEY);
    },
    getCart: function () {
      try {
        const data = localStorage.getItem(CART_KEY);
        return data ? JSON.parse(data) : [];
      } catch (_) {
        return [];
      }
    },
    setCart: function (cart) {
      localStorage.setItem(CART_KEY, JSON.stringify(cart));
    },
    getOrders: function () {
      try {
        const data = localStorage.getItem(ORDERS_KEY);
        return data ? JSON.parse(data) : [];
      } catch (_) {
        return [];
      }
    },
    addOrder: function (order) {
      const orders = this.getOrders();
      orders.unshift(order);
      localStorage.setItem(ORDERS_KEY, JSON.stringify(orders));
    },
    isLoggedIn: function () {
      return !!this.getCurrentUser();
    },
    logout: function () {
      this.setCurrentUser(null);
    }
  };
})();
