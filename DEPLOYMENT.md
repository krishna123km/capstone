# GitHub Pages Deployment Guide

This project is configured to automatically deploy to GitHub Pages using GitHub Actions.

## Setup Instructions

1. **Enable GitHub Pages in Repository Settings**
   - Go to your repository on GitHub
   - Click **Settings** → **Pages**
   - Under "Build and deployment":
     - Set **Source** to **Deploy from a branch**
     - Set **Branch** to **master** (or **main**)
     - Set folder to **/ (root)**
   - Click **Save**

2. **Automatic Deployment**
   - Any push to `master` or `main` branch will trigger the workflow
   - The workflow builds and deploys your static site to GitHub Pages
   - Your site will be available at: `https://<username>.github.io/mavenTesting`

## Workflows

### `deploy-static-pages.yml`
Handles static site deployment:
- Uploads the root directory (HTML, CSS, JS files) to GitHub Pages
- Runs on push to `master` or `main`
- Can be manually triggered via **Actions** → **Deploy Static Site to GitHub Pages** → **Run workflow**

### `allure-report.yml`
Existing workflow that:
- Runs tests
- Generates Allure test reports
- Deploys reports to GitHub Pages

## Configuration Files

### `_config.yml`
Jekyll configuration for GitHub Pages metadata and site settings.

### `.nojekyll`
Tells GitHub Pages to serve files directly without Jekyll processing (recommended for vanilla HTML/CSS/JS sites).

## File Structure Served

The following files will be served:
```
/
├── index.html          (Entry point)
├── home.html
├── product.html
├── cart.html
├── checkout.html
├── orders.html
├── css/
│   └── style.css
├── js/
│   ├── auth.js
│   ├── home.js
│   ├── product.js
│   ├── cart.js
│   ├── checkout.js
│   ├── orders.js
│   ├── nav.js
│   ├── storage.js
│   └── data.js
```

## Excluded from Deployment

These directories are excluded and won't be served:
- `demo/` (test project)
- `reports/` (local reports)
- `.github/` (GitHub Actions config)
- `.vscode/` (editor config)

## Testing Locally

Before committing, test your site locally:

```bash
# Option 1: Python 3
python -m http.server 8080

# Option 2: Node.js
npx serve

# Option 3: VS Code Live Server extension
# Right-click index.html → "Open with Live Server"
```

## Troubleshooting

### Pages not updating after push
- Check the **Actions** tab for workflow errors
- Ensure your branch protection rules aren't blocking the workflow
- Verify Pages settings point to the correct branch

### CSS/JS not loading
- Check browser console for 404 errors
- Update `css/style.css` paths if using subdirectory (currently not needed with `.nojekyll`)
- Ensure relative paths in HTML match actual file structure

### CORS issues when testing locally
- Server files over HTTP, not `file://` protocol
- Use one of the local server options above

## More Information

- [GitHub Pages Documentation](https://docs.github.com/en/pages)
- [GitHub Actions Documentation](https://docs.github.com/en/actions)
