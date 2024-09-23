// frontend/src/services/note-services.js

import { API_ENDPOINTS } from '../config/api';

class Note {
  constructor({ id, content }) {
    this.id = id;
    this.content = content;
  }

  asJson() {
    return {
      id: this.id,
      content: this.content
    };
  }

  static fromArray(data) {
    return data.map(item => new Note(item));
  }
}

class ApiClient {
  static async get(url) {
    const response = await fetch(url);
    if (!response.ok) {
      throw new Error('API request failed');
    }
    return response.json();
  }

  static async put(url, data) {
    const response = await fetch(url, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data)
    });
    if (!response.ok) {
      throw new Error('API request failed');
    }
    return response.json();
  }

  static async patch(url, data) {
    const response = await fetch(url, {
      method: 'PATCH',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data)
    });
    if (!response.ok) {
      throw new Error('API request failed');
    }
    return response.json();
  }

  static async delete(url) {
    const response = await fetch(url, { method: 'DELETE' });
    if (!response.ok) {
      throw new Error('API request failed');
    }
  }
}

class NoteSearcher {
  static async search() {
    const response = await ApiClient.get(API_ENDPOINTS.NOTES);
    return Note.fromArray(response);
  }
}

class NoteRegister {
  static async register(note) {
    if (!(note instanceof Note)) {
      throw new Error('Invalid note');
    }
    const response = await ApiClient.put(API_ENDPOINTS.NOTES, note.asJson());
    return new Note(response);
  }
}

class NoteEditor {
  static async edit(note) {
    if (!(note instanceof Note)) {
      throw new Error('Invalid note');
    }
    const response = await ApiClient.patch(API_ENDPOINTS.NOTE(note.id), note.asJson());
    return new Note(response);
  }
}

class NoteDeleter {
  static async delete(id) {
    await ApiClient.delete(API_ENDPOINTS.NOTE(id));
  }
}

export { Note, NoteSearcher, NoteRegister, NoteEditor, NoteDeleter };