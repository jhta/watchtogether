# WatchTogether App: Application Flow Documentation

## Table of Contents
1. [Overview](#overview)
2. [User Journey Map](#user-journey-map)
3. [Screen Flows](#screen-flows)
   - [Onboarding Flow](#onboarding-flow)
   - [Home Flow](#home-flow)
   - [Groups Flow](#groups-flow)
   - [Poll Creation Flow](#poll-creation-flow)
4. [Component Architecture](#component-architecture)
5. [State Management](#state-management)
6. [Navigation Structure](#navigation-structure)
7. [Future Enhancements](#future-enhancements)

## Overview

WatchTogether is a social movie-watching application that enables users to create groups, share movie preferences, and create polls to decide what to watch together. The app focuses on creating a collaborative experience for movie selection through intuitive UI flows and interactive components.

## User Journey Map

```
┌─────────────┐     ┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│  Welcome    │────▶│    Home     │────▶│   Groups    │────▶│Group Details│
│   Screen    │     │   Screen    │     │   Screen    │     │   Screen    │
└─────────────┘     └──────┬──────┘     └──────┬──────┘     └──────┬──────┘
                           │                    │                   │
                           │                    │                   │
                           ▼                    ▼                   ▼
                    ┌─────────────┐     ┌─────────────┐     ┌─────────────┐
                    │  Movie      │     │Create Group │     │Create Poll  │
                    │  Details    │     │   Screen    │     │   Screen    │
                    └─────────────┘     └─────────────┘     └─────────────┘
```

## Screen Flows

### Onboarding Flow

**WelcomeScreen**
- Entry point for new users
- Introduces app concept and key features
- Provides sign-in/sign-up options
- Transitions to HomeScreen after authentication

### Home Flow

**HomeScreen**
- Central hub displaying:
  - Upcoming watch events
  - Recent group activities
  - Recommended movies
- Navigation to other main sections via bottom tab bar
- Quick access to movie details

**MovieDetailsScreen** (not fully implemented yet)
- Detailed view of a selected movie
- Synopsis, cast, and genre information
- Options to add to personal "Watch Later" list
- Share movie with groups

### Groups Flow

**GroupsScreen**
- Lists all groups the user belongs to
- Create new group button
- Group cards showing:
  - Group name
  - Member count
  - Upcoming watch events

**CreateGroupScreen**
- Two-step process:
  1. Enter group details (name, description)
  2. Generate and share invitation code
- Options to:
  - Copy invitation code to clipboard
  - Share via external apps (WhatsApp, etc.)
- Confirmation on successful group creation

**GroupDetailScreen**
- Group information and activity feed
- Member list with roles
- Upcoming and past watch events
- Create poll button
- Group settings access

### Poll Creation Flow

**CreatePollScreen**
- Multi-step process for creating movie polls:
  1. Movie selection from different sources
  2. Review and confirm selection
  3. Poll creation confirmation

**MovieSelectionStep**
- Search functionality for finding movies
- Three categorized movie sections:
  - "Watch Later" (horizontal list)
  - "Group Favorites" (horizontal list)
  - "Other Movies" (grid layout)
- Selection mechanism with visual indicators
- Persistent bottom bar showing selection count
- "Create Poll" button to proceed to review

**ReviewBottomSheet**
- Displays as a modal bottom sheet
- Shows horizontally scrollable list of selected movies
- Option to remove movies from selection
- Final "Create Poll" button to complete the process

## Component Architecture

The application follows a component-based architecture with a focus on reusability and maintainability:

```
com.watchtogether/
├── screens/                  # Screen-level components
│   ├── welcome/
│   ├── home/
│   ├── groups/
│   │   ├── components/       # Group-specific components
│   │   ├── CreateGroupScreen.kt
│   │   ├── GroupsScreen.kt
│   │   └── GroupDetailScreen.kt
│   └── createpoll/
│       ├── components/
│       │   ├── MovieSelectionStep/
│       │   │   ├── components/    # Sub-components
│       │   │   ├── modifiers/     # Custom modifiers
│       │   │   └── MovieSelectionStep.kt
│       │   ├── ReviewBottomSheet/
│       │   │   ├── components/    # Sub-components
│       │   │   └── ReviewBottomSheet.kt
│       │   └── PersistentBottomBar.kt
│       └── CreatePollScreen.kt
├── components/               # Shared components
│   ├── MovieItem.kt
│   ├── SearchBar.kt
│   └── ...
├── models/                   # Data models
│   ├── Movie.kt
│   ├── Group.kt
│   └── ...
└── navigation/               # Navigation configuration
    └── AppNavigation.kt
```

### Key Components

1. **MovieSelectionStep**
   - Main component for movie selection process
   - Manages filtering and display of movie categories
   - Handles selection state and search functionality

2. **ReviewBottomSheet**
   - Modal component for reviewing selected movies
   - Provides final confirmation before poll creation
   - Allows removing movies from selection

3. **PersistentBottomBar**
   - Fixed-position component showing selection status
   - Triggers the review process
   - Visual feedback on selection count

4. **MovieGridItem & HorizontalMovieItem**
   - Reusable components for displaying movies in different layouts
   - Handle selection state visualization
   - Consistent styling across the app

## State Management

The application uses Jetpack Compose's state management system:

1. **Local Component State**
   - `remember` and `mutableStateOf` for component-level state
   - Example: Search query in MovieSelectionStep

2. **Lifted State**
   - State hoisting pattern for sharing state between components
   - Example: Selected movies state is managed in CreatePollScreen and passed down

3. **Derived State**
   - Computed values based on other state
   - Example: Filtered movie lists based on search query

```kotlin
// Example of state management in MovieSelectionStep
var searchQuery by remember { mutableStateOf("") }
var showReviewSheet by remember { mutableStateOf(false) }

// Derived state example
val filteredWatchLater = remember(watchLaterMovies, searchQuery) {
    if (searchQuery.isEmpty()) watchLaterMovies
    else watchLaterMovies.filter { it.title.contains(searchQuery, ignoreCase = true) }
}
```

## Navigation Structure

The app uses Jetpack Navigation Compose for screen navigation:

```kotlin
NavHost(navController, startDestination = "welcome") {
    composable("welcome") { WelcomeScreen(navController) }
    composable("home") { HomeScreen(navController) }
    composable("groups") { GroupsScreen(navController) }
    composable("group/{groupId}") { backStackEntry ->
        val groupId = backStackEntry.arguments?.getString("groupId")
        GroupDetailScreen(navController, groupId)
    }
    composable("create_group") { CreateGroupScreen(navController) }
    composable("create_poll") { CreatePollScreen(navController) }
}
```

## Future Enhancements

1. **Poll Voting Flow**
   - Interface for group members to vote on polls
   - Real-time results visualization
   - Poll expiration and notification system

2. **Watch Event Scheduling**
   - Calendar integration for scheduling watch events
   - Reminder notifications
   - Virtual watch room preparation

3. **Movie Recommendation System**
   - Personalized recommendations based on watch history
   - Group taste profile generation
   - Integration with external movie databases

4. **Social Features**
   - In-app chat for groups
   - Movie reviews and ratings
   - Activity feed enhancements

5. **Cross-Platform Development**
   - iOS version with consistent user experience
   - Shared backend services
   - Synchronized data across platforms

---

This documentation provides a comprehensive overview of the WatchTogether app's current state, focusing on the application flow, screen interactions, and component architecture. As development continues, this document should be updated to reflect new features and architectural changes. 