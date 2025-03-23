# WatchTogether App - Project Context

## Overview
WatchTogether is a social movie-watching application that allows users to create groups, share movie preferences, and create polls for collaborative movie selection. The app is built using Jetpack Compose for modern Android UI development.

## Project Structure
```
app/src/main/java/com/watchtogether/
├── components/         # Reusable UI components
├── models/            # Data models
├── navigation/        # Navigation setup and routes
├── screens/          # Screen components
│   ├── createpoll/   # Poll creation flow
│   ├── creategroup/  # Group creation flow
│   ├── groupdetail/  # Group details screen
│   ├── polldetail/   # Poll details and voting
│   └── HomeScreen.kt # Main home screen
└── ui/               # UI theme and styling
```

## Key Features

### 1. Group Management
- Create new groups with unique invitation codes
- Share invitation codes via system share or clipboard
- View group details and members
- Create polls within groups

### 2. Poll Creation
- Multi-step poll creation process
- Movie selection with horizontal scrolling cards
- Persistent bottom bar showing selection count
- Review bottom sheet for final confirmation

### 3. Voting System
- Tinder-like card interface for movie selection
- One vote per user
- Visual feedback for selection
- Success screen with animations after voting

## Navigation Flow

### Main Navigation Routes
1. `WELCOME_ROUTE` - Initial welcome screen
2. `HOME_ROUTE` - Main home screen with groups
3. `CREATE_GROUP_ROUTE` - Group creation
4. `GROUP_DETAIL_ROUTE` - Group details and polls
5. `CREATE_POLL_ROUTE` - Poll creation
6. `POLL_DETAIL_ROUTE` - Poll details and voting
7. `VOTE_SUCCESS_ROUTE` - Success screen after voting

### User Flow
1. **Welcome Screen**
    - User signs up or logs in
    - Navigates to Home Screen

2. **Home Screen**
    - Displays user's groups
    - Option to create new groups
    - Navigate to group details

3. **Group Creation**
    - Enter group name
    - Generate invitation code
    - Share code with others
    - Navigate back to home

4. **Group Details**
    - View group information
    - Create new polls
    - View existing polls
    - Navigate to poll details

5. **Poll Creation**
    - Select movies
    - Review selections
    - Create poll
    - Navigate to poll details

6. **Poll Details**
    - View poll information
    - Select movie to vote
    - Confirm vote
    - Navigate to success screen

7. **Vote Success**
    - Animated success feedback
    - Return to home screen

## Key Components

### 1. AppScaffold
- Reusable top app bar component
- Handles back navigation
- Consistent styling across screens

### 2. MovieCard
- Tinder-like card interface
- Visual feedback for selection
- Animated transitions

### 3. ReviewBottomSheet
- Modal bottom sheet for review
- Horizontal scrolling movie list
- Selection confirmation

### 4. PersistentBottomBar
- Shows selection count
- Create poll button
- Consistent positioning

## State Management
- Local component state using `remember` and `mutableStateOf`
- Navigation state handled by `NavController`
- Screen-specific state management

## UI/UX Guidelines
- Material Design 3 components
- Consistent spacing and typography
- Animated transitions between screens
- Clear visual feedback for user actions
- Responsive layouts for different screen sizes

## Future Enhancements
1. Real-time poll updates
2. Movie recommendations
3. Watch event scheduling
4. Social features (comments, reactions)
5. Cross-platform development

## Technical Stack
- Kotlin
- Jetpack Compose
- Material Design 3
- Navigation Compose
- Android Studio

## Development Notes
- Follow modular component structure
- Maintain consistent naming conventions
- Use composable functions for UI components
- Implement proper error handling
- Add loading states for async operations

## Testing Considerations
- Unit tests for business logic
- UI tests for critical user flows
- Integration tests for navigation
- Mock data for development

## Performance Considerations
- Lazy loading for lists
- Efficient image loading
- State hoisting for better performance
- Proper use of remember and derivedStateOf 