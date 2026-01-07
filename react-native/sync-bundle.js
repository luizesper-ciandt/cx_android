#!/usr/bin/env node

/**
 * Sync bundle from rnapp-bundle package to app assets
 * This script runs automatically after npm install (postinstall hook)
 */

const fs = require('fs');
const path = require('path');

const sourceDir = path.join(__dirname, 'node_modules', 'rnapp-bundle');
const targetDir = path.join(__dirname, '..', 'app', 'src', 'main', 'assets');
const files = ['index.android.bundle', 'metadata.json'];

console.log('Syncing RN bundle from rnapp-bundle package...\n');

// Check if rnapp-bundle is installed
if (!fs.existsSync(sourceDir)) {
    console.log('rnapp-bundle not installed yet, skipping sync.');
    process.exit(0);
}

// Create assets directory if it doesn't exist
if (!fs.existsSync(targetDir)) {
    fs.mkdirSync(targetDir, { recursive: true });
    console.log(`Created directory: ${targetDir}`);
}

// Copy files
let copied = 0;
for (const file of files) {
    const src = path.join(sourceDir, file);
    const dest = path.join(targetDir, file);

    if (fs.existsSync(src)) {
        fs.copyFileSync(src, dest);
        const size = (fs.statSync(dest).size / 1024).toFixed(2);
        console.log(`  Copied ${file} (${size} KB)`);
        copied++;
    } else {
        console.warn(`  Warning: ${file} not found in rnapp-bundle`);
    }
}

// Show metadata info
const metadataPath = path.join(targetDir, 'metadata.json');
if (fs.existsSync(metadataPath)) {
    try {
        const metadata = JSON.parse(fs.readFileSync(metadataPath, 'utf8'));
        console.log('\nBundle info:');
        console.log(`  Version: ${metadata.version}`);
        console.log(`  Min Android Version: ${metadata.minAndroidVersion}`);
        console.log(`  React Native: ${metadata.reactNativeVersion}`);
        console.log(`  Hermes: ${metadata.hermesEnabled ? 'enabled' : 'disabled'}`);
    } catch (e) {
        console.warn('  Warning: Could not parse metadata.json');
    }
}

if (copied > 0) {
    console.log('\nBundle synced successfully!');
} else {
    console.error('\nError: No files were copied!');
    process.exit(1);
}
