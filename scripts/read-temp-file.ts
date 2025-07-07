#!/usr/bin/env node

import * as fs from 'fs';
import * as path from 'path';

/**
 * TypeScript script to read and display temporary file contents
 * Created for GitHub Actions workflow demonstration
 */

const TEMP_FILE_PATH = '/tmp/workflow-data/success-message.txt';

function readAndDisplayFile(): void {
  console.log('🔍 TypeScript Script: Reading temporary file...');
  console.log(`📁 File path: ${TEMP_FILE_PATH}`);
  
  try {
    // Check if file exists
    if (!fs.existsSync(TEMP_FILE_PATH)) {
      console.error('❌ Error: Temporary file not found!');
      console.error(`   Expected location: ${TEMP_FILE_PATH}`);
      process.exit(1);
    }

    // Read file stats
    const stats = fs.statSync(TEMP_FILE_PATH);
    console.log(`📊 File size: ${stats.size} bytes`);
    console.log(`📅 Created: ${stats.birthtime.toISOString()}`);
    console.log(`🔄 Modified: ${stats.mtime.toISOString()}`);
    console.log('');

    // Read file content
    const content = fs.readFileSync(TEMP_FILE_PATH, 'utf8');
    
    console.log('==================== SUCCESS MESSAGE ====================');
    console.log(content);
    console.log('==========================================================');
    console.log('');
    
    // Display content analysis
    const lines = content.split('\n');
    const words = content.split(/\s+/).filter(word => word.length > 0);
    const characters = content.length;
    
    console.log('📈 Content Analysis:');
    console.log(`   📝 Lines: ${lines.length}`);
    console.log(`   🔤 Words: ${words.length}`);
    console.log(`   📊 Characters: ${characters}`);
    console.log(`   🎯 Non-empty lines: ${lines.filter(line => line.trim().length > 0).length}`);
    
    console.log('');
    console.log('✅ TypeScript script executed successfully!');
    console.log('🎉 File read and processed using TypeScript in GitHub Actions');
    
  } catch (error) {
    console.error('❌ Error reading file:', error);
    process.exit(1);
  }
}

// Main execution
if (require.main === module) {
  readAndDisplayFile();
}

export { readAndDisplayFile }; 