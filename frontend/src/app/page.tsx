'use client';
import React, { useEffect, useState } from 'react';
import { Box, Container, Grid, Fab, ThemeProvider, createTheme } from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import { NoteEditor, NoteRegister, NoteSearcher } from '@/notes/app/note-services';
import { Note } from '@/notes/domain/note';
import Header from '@/components/Header';
import Footer from '@/components/Footer';
import NoteCard from '@/notes/components/NoteCard';
import NoteDialog from '@/notes/components/NoteDialog';

const theme = createTheme({
  palette: {
    mode: 'dark',
    primary: {
      main: '#2196f3',
    },
    secondary: {
      main: '#f50057',
    },
    background: {
      default: '#121212',
      paper: '#1e1e1e',
    },
  },
  shape: {
    borderRadius: 12,
  },
  components: {
    MuiCard: {
      styleOverrides: {
        root: {
          boxShadow: '0 4px 6px rgba(0, 0, 0, 0.2)',
          transition: 'transform 0.2s, box-shadow 0.2s',
          '&:hover': {
            transform: 'translateY(-4px)',
            boxShadow: '0 8px 12px rgba(0, 0, 0, 0.3)',
          },
        },
      },
    },
  },
});

export default function Home() {
  const [notes, setNotes] = useState<Note[]>([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [isNoteDialogOpen, setIsNoteDialogOpen] = useState(false);
  const [editingNote, setEditingNote] = useState<Note | null>(null);
  const [isSaving, setIsSaving] = useState(false);

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
    setIsNoteDialogOpen(true);
  };

  const handleEditNote = (note: Note) => {
    setEditingNote(note);
    setIsNoteDialogOpen(true);
  };

  const handleSaveNote = async (noteData: { content: string }) => {
    setIsSaving(true);
    try {
      if (editingNote) {
        const updatedNote = new Note({
          id: editingNote.id,
          content: noteData.content
        });
        const savedNote = await NoteEditor.edit(updatedNote);
        const noteToUpdate = savedNote instanceof Note ? savedNote : new Note(savedNote);
        setNotes(prevNotes => prevNotes.map(n => n.id === noteToUpdate.id ? noteToUpdate : n));
      } else {
        const newNote = new Note({
          id: crypto.randomUUID(),
          content: noteData.content
        });
        const savedNote = await NoteRegister.register(newNote);
        const noteToAdd = savedNote instanceof Note ? savedNote : new Note(savedNote);
        setNotes(prevNotes => [...prevNotes, noteToAdd]);
      }
      // Solo cerramos el formulario después de que la operación se complete exitosamente
      setIsNoteDialogOpen(false);
      setEditingNote(null);
      // Opcional: recargar las notas después de guardar
      await fetchNotes();
    } catch (error) {
      console.error('Failed to save note:', error);
    } finally {
      setIsSaving(false);
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
        <Container component="main" sx={{ mt: 4, mb: 2, flex: 1, maxWidth: 'lg' }}>
          <Grid container spacing={3} sx={{ py: 2 }}>
            {filteredNotes.map((note) => (
                <Grid item key={note.id} xs={12} sm={6} md={4} lg={3}>
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
            sx={{
              position: 'fixed',
              bottom: 24,
              right: 24,
              boxShadow: '0 4px 12px rgba(33, 150, 243, 0.4)',
              '&:hover': {
                transform: 'scale(1.1)',
              },
              transition: 'transform 0.2s',
            }}
            onClick={handleAddNote}
        >
          <AddIcon />
        </Fab>
        <Footer />
        <NoteDialog
            open={isNoteDialogOpen}
            onClose={() => setIsNoteDialogOpen(false)}
            onSave={handleSaveNote}
            isSaving={isSaving}
        />
      </Box>
    </ThemeProvider>
  );
}