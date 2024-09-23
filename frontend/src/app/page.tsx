'use client';
import React, { useState, useEffect } from 'react'
import {
  AppBar,
  Toolbar,
  Typography,
  InputBase,
  IconButton,
  Grid,
  Card,
  CardContent,
  CardActions,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
  Fab,
  Box,
  ThemeProvider,
  createTheme,
} from '@mui/material'
import {
  Search as SearchIcon,
  Add as AddIcon,
  Delete as DeleteIcon,
  Edit as EditIcon,
  ColorLens as ColorLensIcon,
} from '@mui/icons-material'
import { Note, NoteSearcher, NoteRegister, NoteEditor, NoteDeleter } from './services/note-services'

// Create a custom theme that closely resembles Material Design 3
const theme = createTheme({
  palette: {
    primary: {
      main: '#006495',
    },
    secondary: {
      main: '#625b71',
    },
    background: {
      default: '#fffbfe',
      paper: '#ffffff',
    },
  },
  shape: {
    borderRadius: 16,
  },
  components: {
    MuiAppBar: {
      defaultProps: {
        elevation: 2,
      },
    },
    MuiCard: {
      defaultProps: {
        elevation: 1,
      },
    },
    MuiFab: {
      styleOverrides: {
        root: {
          boxShadow: '0px 3px 5px -1px rgba(0,0,0,0.2), 0px 6px 10px 0px rgba(0,0,0,0.14), 0px 1px 18px 0px rgba(0,0,0,0.12)',
        },
      },
    },
  },
})

interface NoteWithColor extends Note {
  color: string;
}

export default function Component() {
  const [notes, setNotes] = useState<NoteWithColor[]>([])
  const [searchTerm, setSearchTerm] = useState('')
  const [openDialog, setOpenDialog] = useState(false)
  const [currentNote, setCurrentNote] = useState<NoteWithColor | null>(null)

  useEffect(() => {
    fetchNotes()
  }, [])

  const fetchNotes = async () => {
    try {
      const fetchedNotes = await NoteSearcher.search()
      setNotes(fetchedNotes.map(note => ({ ...note, color: '#ffffff' })))
    } catch (error) {
      console.error('Failed to fetch notes:', error)
    }
  }

  const handleAddNote = () => {
    setCurrentNote({ id: '', content: '', color: '#ffffff' })
    setOpenDialog(true)
  }

  const handleEditNote = (note: NoteWithColor) => {
    setCurrentNote(note)
    setOpenDialog(true)
  }

  const handleDeleteNote = async (id: string) => {
    try {
      await NoteDeleter.delete(id)
      setNotes(notes.filter(note => note.id !== id))
    } catch (error) {
      console.error('Failed to delete note:', error)
    }
  }

  const handleSaveNote = async () => {
    if (currentNote) {
      try {
        const noteToSave = new Note({ id: currentNote.id, content: currentNote.content })
        let savedNote
        if (currentNote.id) {
          savedNote = await NoteEditor.edit(noteToSave)
        } else {
          savedNote = await NoteRegister.register(noteToSave)
        }
        await fetchNotes()
        setOpenDialog(false)
        setCurrentNote(null)
      } catch (error) {
        console.error('Failed to save note:', error)
      }
    }
  }

  const handleChangeColor = (id: string) => {
    const colors = ['#ffffff', '#f28b82', '#fbbc04', '#fff475', '#ccff90', '#a7ffeb', '#cbf0f8', '#aecbfa', '#d7aefb', '#fdcfe8']
    const currentIndex = notes.findIndex(note => note.id === id)
    const currentColorIndex = colors.indexOf(notes[currentIndex].color)
    const newColorIndex = (currentColorIndex + 1) % colors.length
    const newNotes = [...notes]
    newNotes[currentIndex] = { ...newNotes[currentIndex], color: colors[newColorIndex] }
    setNotes(newNotes)
  }

  const filteredNotes = notes.filter(note =>
    note.content.toLowerCase().includes(searchTerm.toLowerCase())
  )

  return (
    <ThemeProvider theme={theme}>
      <Box sx={{ flexGrow: 1, height: '100vh', bgcolor: 'background.default' }}>
        <AppBar position="static" color="inherit">
          <Toolbar>
            <Typography variant="h6" noWrap sx={{ flexGrow: 1, display: { xs: 'none', sm: 'block' } }}>
              MaterialKeep
            </Typography>
            <Box sx={{ position: 'relative', borderRadius: 1, bgcolor: 'rgba(0,0,0,0.04)', mr: 2, ml: { xs: 0, sm: 3 } }}>
              <IconButton sx={{ p: '10px' }} aria-label="search">
                <SearchIcon />
              </IconButton>
              <InputBase
                sx={{ ml: 1, flex: 1 }}
                placeholder="Search notes"
                inputProps={{ 'aria-label': 'search notes' }}
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
              />
            </Box>
          </Toolbar>
        </AppBar>
        <Box sx={{ p: 3 }}>
          <Grid container spacing={3}>
            {filteredNotes.map((note) => (
              <Grid item key={note.id} xs={12} sm={6} md={4} lg={3}>
                <Card sx={{ bgcolor: note.color }}>
                  <CardContent>
                    <Typography variant="body2">
                      {note.content}
                    </Typography>
                  </CardContent>
                  <CardActions disableSpacing>
                    <IconButton aria-label="edit note" onClick={() => handleEditNote(note)}>
                      <EditIcon />
                    </IconButton>
                    <IconButton aria-label="change color" onClick={() => handleChangeColor(note.id)}>
                      <ColorLensIcon />
                    </IconButton>
                    <IconButton aria-label="delete note" onClick={() => handleDeleteNote(note.id)}>
                      <DeleteIcon />
                    </IconButton>
                  </CardActions>
                </Card>
              </Grid>
            ))}
          </Grid>
        </Box>
        <Fab color="primary" aria-label="add" sx={{ position: 'fixed', bottom: 16, right: 16 }} onClick={handleAddNote}>
          <AddIcon />
        </Fab>
        <Dialog open={openDialog} onClose={() => setOpenDialog(false)}>
          <DialogTitle>{currentNote && currentNote.id ? 'Edit Note' : 'New Note'}</DialogTitle>
          <DialogContent>
            <TextField
              autoFocus
              margin="dense"
              label="Content"
              type="text"
              fullWidth
              multiline
              rows={4}
              value={currentNote ? currentNote.content : ''}
              onChange={(e) => setCurrentNote(currentNote ? { ...currentNote, content: e.target.value } : null)}
            />
          </DialogContent>
          <DialogActions>
            <IconButton onClick={() => setOpenDialog(false)} color="inherit">
              <DeleteIcon />
            </IconButton>
            <IconButton onClick={handleSaveNote} color="primary">
              <AddIcon />
            </IconButton>
          </DialogActions>
        </Dialog>
      </Box>
    </ThemeProvider>
  )
}