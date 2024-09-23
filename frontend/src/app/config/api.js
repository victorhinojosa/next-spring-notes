const API_BASE_URL = process.env.NEXT_PUBLIC_API_BASE_URL || 'http://localhost:8080/api';

export const API_ENDPOINTS = {
  NOTES: `${API_BASE_URL}/notes`,
  NOTE: (id) => `${API_BASE_URL}/notes/${id}`,
};