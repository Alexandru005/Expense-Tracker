import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

// Cererile catre /api sunt trimise la backend-ul Spring Boot (port 8080).
// Astfel nu e nevoie de configurare CORS in backend.
export default defineConfig({
  plugins: [react()],
  server: {
    port: 5173,
    proxy: { "/api": { target: "http://localhost:8080", changeOrigin: true } },
  },
});
