# Paint Store

A complete paint shopping website built with **HTML, CSS, and Vanilla JavaScript**. No backend or frameworks. Uses **Local Storage** for user accounts, cart, and order history.

## Features

- **Login / Signup** – Email & password, stored in Local Storage. Redirects to Home when authenticated.
- **Home** – Grid of paint products with image, title, price, rating, and “View Details”. Search bar filters by name. Sticky nav: Home, Cart, Orders, Logout.
- **Product page** – Large image, title, price, description, genre, rating. Add to Cart (quantity increases if already in cart).
- **Cart** – List with name, price, quantity (increase/decrease), remove, line total. Grand total and **Checkout** button.
- **Checkout / Payment** – Order summary, then payment form: Name on card, Card number, Expiry (MM/YY), CVV, Billing address. On submit: order saved to history, cart cleared, success message with Order ID, date, total.
- **Orders** – All past orders with Order ID, items, total, date. All data in Local Storage.

## Running locally

To avoid CORS and get accurate Lighthouse scores, serve the folder over HTTP:

**Option 1 – Node (npx)**  
```bash
npx serve paint-store
```
Then open the URL shown (e.g. http://localhost:3000).

**Option 2 – Python 3**  
```bash
cd paint-store
python -m http.server 8080
```
Open http://localhost:8080.

**Option 3 – VS Code**  
Use the “Live Server” extension and open `index.html`.

## Lighthouse performance

- All scripts use `defer` to avoid render-blocking.
- Images use `width`/`height` and `loading="lazy"` where appropriate.
- Single CSS file, no @import.
- Favicon is inline SVG (no extra request).
- Run Lighthouse against the **served** URL (e.g. http://localhost:3000), not `file://`, for best and consistent results.

## File structure

```
paint-store/
├── index.html      # Login / Signup
├── home.html       # Product grid + search
├── product.html    # Product detail + Add to Cart
├── cart.html       # Cart + Checkout button
├── checkout.html   # Payment form + place order
├── orders.html     # Order history
├── css/
│   └── style.css   # Global styles (dark theme, responsive)
├── js/
│   ├── storage.js  # Local Storage helpers
│   ├── data.js     # Paint products list
│   ├── auth.js     # Login/Signup logic
│   ├── nav.js      # Nav bar + cart count + logout
│   ├── home.js     # Grid + search
│   ├── product.js  # Product page + Add to Cart
│   ├── cart.js     # Cart list + quantity + remove
│   ├── checkout.js # Payment form + place order
│   └── orders.js   # Orders list
└── README.md
```

## Defaults

- No pre-existing users; sign up from the login page.
- Passwords are stored in plain text in Local Storage (suitable for demo only).
"# capstone" 
