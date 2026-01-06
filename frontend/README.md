# StudyConnect Frontend

A modern Vue.js 3 + TypeScript frontend for the StudyConnect student productivity application.

## Features

- ✅ Task management (create, edit, delete, complete)
- ✅ Dashboard with statistics and overview
- ✅ Calendar view for task scheduling
- ✅ Responsive design for mobile and desktop
- ✅ Modern UI with appealing design
- ✅ Ready for Selenium and Cypress testing (data-testid attributes)

## Technology Stack

- **Vue 3** - Progressive JavaScript framework
- **TypeScript** - Type-safe JavaScript
- **Vite** - Fast build tool and dev server
- **Vue Router** - Client-side routing
- **Pinia** - State management
- **Axios** - HTTP client for API requests
- **date-fns** - Date utility library

## Getting Started

### Prerequisites

- Node.js 18+ and npm/yarn/pnpm

### Installation

```bash
cd frontend
npm install
```

### Development

Start the development server:

```bash
npm run dev
```

The app will be available at `http://localhost:3000`

### Building for Production

```bash
npm run build
```

The built files will be in the `dist` directory.

### Preview Production Build

```bash
npm run preview
```

## Configuration

### API Base URL

The frontend is configured to connect to the backend at `http://localhost:8080/api` by default.

To change this, create a `.env` file in the frontend directory:

```env
VITE_API_BASE_URL=http://localhost:8080/api
```

### User ID

The application uses a simplified authentication system for development. The current user ID is stored in localStorage and sent as the `X-User-Id` header in API requests.

You can change the user ID in the Profile page.

## Project Structure

```
frontend/
├── src/
│   ├── assets/          # Static assets (images, etc.)
│   ├── components/      # Reusable Vue components
│   ├── layouts/         # Layout components
│   ├── router/          # Vue Router configuration
│   ├── services/        # API services
│   ├── stores/          # Pinia stores (state management)
│   ├── types/           # TypeScript type definitions
│   ├── views/           # Page components
│   ├── App.vue          # Root component
│   ├── main.ts          # Application entry point
│   └── style.css        # Global styles
├── index.html           # HTML template
├── package.json         # Dependencies and scripts
├── tsconfig.json        # TypeScript configuration
└── vite.config.ts       # Vite configuration
```

## Testing

The application includes `data-testid` attributes on key elements to facilitate automated testing with Selenium and Cypress.

### Example Test Selectors

- `data-testid="dashboard"` - Dashboard page
- `data-testid="tasks-view"` - Tasks list page
- `data-testid="task-card-{id}"` - Individual task card
- `data-testid="btn-create-task"` - Create task button
- `data-testid="task-form"` - Task creation form

## API Integration

The frontend communicates with the Spring Boot backend using REST APIs:

- `GET /api/v1/tasks` - List tasks
- `GET /api/v1/tasks/{id}` - Get task details
- `POST /api/v1/tasks` - Create task
- `PUT /api/v1/tasks/{id}` - Update task
- `PATCH /api/v1/tasks/{id}/status` - Update task status
- `DELETE /api/v1/tasks/{id}` - Delete task

All requests include the `X-User-Id` header for user identification.

## Browser Support

- Chrome (latest)
- Firefox (latest)
- Safari (latest)
- Edge (latest)

## License

See LICENSE file in the project root.

