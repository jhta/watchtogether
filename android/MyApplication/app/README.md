# Watch Together Android App

## Environment Setup

This app uses environment variables to manage sensitive configuration such as API keys and URLs. There are several ways to provide these variables:

### Option 1: Using local.properties (Recommended for development)

Add the following to your `local.properties` file (this file is not committed to git):

```
SUPABASE_URL=your_supabase_url
SUPABASE_KEY=your_supabase_anon_key
```

### Option 2: Using assets folder

Create a file at `app/src/main/assets/env/.env` with:

```
SUPABASE_URL=your_supabase_url
SUPABASE_KEY=your_supabase_anon_key
```

Note: This file is gitignored and won't be committed.

### Option 3: System environment variables

For CI/CD systems, you can set environment variables directly in your build system:

```
export SUPABASE_URL=your_supabase_url
export SUPABASE_KEY=your_supabase_anon_key
```

## Building the App

The app will automatically pick up environment variables from any of the above sources, with the following priority:

1. BuildConfig variables (from local.properties)
2. System environment variables
3. .env files
4. Default values (empty strings)

## Adding New Environment Variables

To add a new environment variable:

1. Add it to `local.properties` or your .env file
2. Add a buildConfigField in `app/build.gradle.kts` under both debug and release blocks
3. Update the `getFromBuildConfig()` method in `Environment.kt` to handle the new variable
4. Add a getter method in `Environment.kt` for your new variable 