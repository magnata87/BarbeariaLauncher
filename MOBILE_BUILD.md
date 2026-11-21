# Build APK from mobile using GitHub Actions

This project includes a GitHub Actions workflow at `.github/workflows/build-apk.yml`.

1. Create a GitHub repository and upload the project files (including the hidden `.github/workflows` folder).
2. Open the repository in a browser and go to **Actions**.
3. Choose **Build BarbeariaLauncher APK** and press **Run workflow** (or push to main/master).
4. Wait for the workflow to complete successfully.
5. Open the completed run and download the artifact named **BarbeariaLauncher-debug-apk**.
6. Unzip the artifact to obtain `app-debug.apk`.

The APK is for Android devices only, not iPhone/iOS.
