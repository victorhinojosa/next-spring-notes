import { NoteApiClient } from '../infrastructure/note-api-client';
import { Note } from '../domain/note';
import { API_ENDPOINTS } from '../../config/api-endpoints';

export class NoteRegister {
  static async register(note: Note): Promise<Note> {
    if (!(note instanceof Note)) {
      throw new Error('Invalid note');
    }
    const response = await NoteApiClient.put(API_ENDPOINTS.NOTES, note.asJson());
    return new Note(response);
  }
}

export class NoteSearcher {
  static async search(): Promise<Note[]> {
    const response = await NoteApiClient.get(API_ENDPOINTS.NOTES);
    return Note.fromArray(response);
  }
}

export class NoteEditor {
  static async edit(note: Note): Promise<Note> {
    if (!(note instanceof Note)) {
      throw new Error('Invalid note');
    }
    const response = await NoteApiClient.patch(API_ENDPOINTS.NOTE(note.id), note.asJson());
    return new Note(response);
  }
}