# GitHub Pages & Allure Report Setup

Your project is now configured with automated GitHub Actions workflows to generate and deploy Allure test reports to GitHub Pages.

## 📋 Files Added/Modified

### GitHub Actions Workflows
- **`.github/workflows/allure-report.yml`** - Main workflow that runs tests, generates Allure reports, and deploys to GitHub Pages
- **`.github/workflows/deploy-static-pages.yml`** - Static site deployment workflow (for HTML/CSS/JS content)

### Configuration Files
- **`_config.yml`** - Jekyll configuration for GitHub Pages metadata
- **`.nojekyll`** - Tells GitHub Pages to skip Jekyll processing

### Test Configuration
- **`demo/pom.xml`** - Maven configuration with Allure plugin setup
- **`demo/src/test/java/com/example/base/BaseTest.java`** - Selenium test base with headless Chrome support

## 🚀 How It Works

### Automated Workflow (`.github/workflows/allure-report.yml`)

When you push to `master` branch:

1. **Checkout Code** - GitHub Actions checks out your repository
2. **Setup Environment** - Installs Java 17, Node.js, and Google Chrome
3. **Start HTTP Server** - Starts a local server on port 5501 for tests to access your HTML files
4. **Run Tests** - Executes TestNG tests with Allure listener
   - Tests can fail without stopping the workflow (`continue-on-error: true`)
   - Chrome runs in headless mode for CI environments
5. **Generate Allure Report** - Converts test results to HTML report
6. **Prepare Content** - Copies Allure report to GitHub Pages root directory
7. **Upload Artifacts** - Makes report and test results downloadable
8. **Deploy to GitHub Pages** - Publishes the report online
9. **Publish Summary** - Shows results in the GitHub Actions job summary

### Report Availability

After workflow completes:

- **GitHub Pages URL**: `https://<username>.github.io/capstone/` (or your custom domain)
- **Artifacts**: Download from Actions tab → [Run] → Artifacts section
  - `allure-report` - Full HTML Allure report
  - `test-results` - Raw test result JSON files

## 🔧 Configuration Details

### Allure Results Directory
- Tests write results to: `demo/target/allure-results/`
- Report generated to: `demo/target/site/allure-maven-plugin/`
- Deployed from: `gh-pages/` directory

### Test Configuration
- **Suite File**: `demo/testng.xml` (main), `demo/testng-module1.xml` through `testng-module6.xml` (profiles)
- **Headless Mode**: Enabled when `CI=true` environment variable set
- **Base URL**: `http://127.0.0.1:5501/` (local server during tests)

### Environment Variables in CI
```bash
CI=true                          # Enables headless Chrome mode
ALLURE_RESULTS_DIRECTORY=...    # (set in pom.xml now)
```

## 📊 Allure Report Features

The generated Allure report includes:

- **Dashboard** - Test execution summary, statistics, trends
- **Tests** - Detailed results for each test case
- **Suites** - Organized by test class
- **Timeline** - Duration and execution order
- **Categories** - Failed tests grouped by type
- **Graphs** - Pie charts, trend charts
- **History** - Test execution history (if available)

## 🐛 Debugging

If the workflow fails or reports aren't generating:

1. **Check Workflow Logs**
   - Go to GitHub repo → Actions → [Workflow run] → [Job] → Scroll through logs
   - Look for:
     - "Test results directory exists" ✓
     - "Allure report generated successfully" ✓
     - "index.html found in gh-pages" ✓

2. **Common Issues**
   - Tests failing (shown in report)
   - Allure results not created (check test errors first)
   - Report not deployed (check GitHub Pages settings)

3. **Local Testing**
   ```bash
   cd demo
   mvn clean test -DsuiteXmlFile=testng.xml
   mvn allure:report -N
   # Report will be at: demo/target/site/allure-maven-plugin/index.html
   ```

## 📚 GitHub Pages Settings

Ensure GitHub Pages is configured:

1. Go to GitHub repo → Settings → Pages
2. **Build and deployment** section should show:
   - Source: Deploy from a branch
   - Branch: `gh-pages` (automatic from workflow)
   - Folder: `/ (root)`
3. Wait for "Your site is live at..." message

## ✅ Verification Checklist

- [ ] Repository has `.github/workflows/allure-report.yml`
- [ ] `demo/pom.xml` has Allure plugin configured
- [ ] Tests run locally: `mvn clean test -DsuiteXmlFile=testng.xml`
- [ ] Allure report generates locally: `mvn allure:report -N`
- [ ] Workflow runs on push to master
- [ ] GitHub Pages shows Allure report at your URL
- [ ] Test failures appear in the Allure dashboard

## 🔗 Useful Links

- [GitHub Pages Documentation](https://docs.github.com/en/pages)
- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [Allure Report Documentation](https://docs.qameta.io/allure/)
- [TestNG Documentation](https://testng.org/)
- [Allure TestNG Integration](https://docs.qameta.io/allure/#_testng)

## 📝 Next Steps

1. **Fix Test Failures** - Address the failing test in your browser automation suite
2. **Customize Report** - Modify `_config.yml` to customize site metadata
3. **Set Custom Domain** - Add CNAME file if using custom domain for GitHub Pages
4. **Integrate with CI/CD** - Link with other automation tools

---

**Last Updated:** February 23, 2026

For issues or questions, check the workflow logs in the Actions tab of your GitHub repository.
