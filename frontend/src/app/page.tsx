'use client';
import React, { useEffect, useState } from 'react';
import { Box, Container, Grid, Fab, ThemeProvider, createTheme } from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import { NoteEditor, NoteRegister, NoteSearcher } from '../notes/app/note-services';
import { Note } from '../notes/domain/note';
import Header from './components/Header';
import Footer from './components/Footer';
import NoteCard from '../notes/components/NoteCard';
import NoteForm from '../notes/components/NoteFormProps';

const theme = createTheme({
  palette: {
    primary: {
      main: '#006495',
    },
    secondary: {
      main: '#625b71',
    },
    background: {
      default: '#f0e68c', 
      paper: '#ffffff',
    },
  },
  shape: {
    borderRadius: 16,
  },
});

export default function Home() {
  const [notes, setNotes] = useState<Note[]>([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [isNoteFormOpen, setIsNoteFormOpen] = useState(false);
  const [editingNote, setEditingNote] = useState<Note | null>(null);

  useEffect(() => {
    fetchNotes();
  }, []);

  const fetchNotes = async () => {
    try {
      const fetchedNotes = await NoteSearcher.search();
      setNotes(fetchedNotes);
    } catch (error) {
      console.error('Failed to fetch notes:', error);
    }
  };

  const handleAddNote = () => {
    setEditingNote(null);
    setIsNoteFormOpen(true);
  };

  const handleEditNote = (note: Note) => {
    setEditingNote(note);
    setIsNoteFormOpen(true);
  };

  const handleSaveNote = async (note: Note) => {
    try {
      let savedNote: Note;
      if (editingNote) {
        savedNote = await NoteEditor.edit(note);
        setNotes(notes.map(n => n.id === savedNote.id ? savedNote : n));
      } else {
        savedNote = await NoteRegister.register(note);
        setNotes([...notes, savedNote]);
      }
    } catch (error) {
      console.error('Failed to save note:', error);
    }
  };

  const handleDeleteNote = async (id: string) => {
    // Implementar lógica para eliminar nota
  };

  const filteredNotes = notes.filter(note =>
    note.content.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <ThemeProvider theme={theme}>
      <Box sx={{ display: 'flex', flexDirection: 'column', minHeight: '100vh' }}>
        <Header onSearch={setSearchTerm} />
        <Container component="main" sx={{ mt: 4, mb: 2, flex: 1 }}>
          <Grid container spacing={3}>
            {filteredNotes.map((note) => (
              <Grid item key={note.id} xs={12} sm={6} md={4}>
                <NoteCard
                  content={note.content}
                  onEdit={() => handleEditNote(note)}
                  onDelete={() => handleDeleteNote(note.id)}
                />
              </Grid>
            ))}
          </Grid>
        </Container>
        <Fab 
          color="primary" 
          aria-label="add" 
          sx={{ position: 'fixed', bottom: 16, right: 16 }}
          onClick={handleAddNote}
        >
          <AddIcon />
        </Fab>
        <Footer />
      </Box>
    </ThemeProvider>
  );
}