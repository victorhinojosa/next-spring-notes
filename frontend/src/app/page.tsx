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

// Crear un tema personalizado que se acerque a Material Design 3
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

interface Note {
  id: number
  title: string
  content: string
  color: string
}

// Notas de ejemplo
const initialNotes: Note[] = [
  { id: 1, title: 'Bienvenido a MaterialKeep', content: 'Esta es tu primera nota. ¡Empieza a organizar tus ideas!', color: '#ffffff' },
  { id: 2, title: 'Tip del día', content: 'Usa diferentes colores para categorizar tus notas.', color: '#f28b82' },
  { id: 3, title: 'Recordatorio', content: 'Haz clic en el botón + para añadir una nueva nota.', color: '#fbbc04' },
]

export default function Component() {
  const [notes, setNotes] = useState<Note[]>(initialNotes)
  const [searchTerm, setSearchTerm] = useState('')
  const [openDialog, setOpenDialog] = useState(false)
  const [currentNote, setCurrentNote] = useState<Note | null>(null)

  useEffect(() => {
    const savedNotes = localStorage.getItem('notes')
    if (savedNotes) {
      setNotes(JSON.parse(savedNotes))
    }
  }, [])

  useEffect(() => {
    localStorage.setItem('notes', JSON.stringify(notes))
  }, [notes])

  const handleAddNote = () => {
    setCurrentNote({ id: Date.now(), title: '', content: '', color: '#ffffff' })
    setOpenDialog(true)
  }

  const handleEditNote = (note: Note) => {
    setCurrentNote(note)
    setOpenDialog(true)
  }

  const handleDeleteNote = (id: number) => {
    setNotes(notes.filter(note => note.id !== id))
  }

  const handleSaveNote = () => {
    if (currentNote) {
      const newNotes = currentNote.id
        ? notes.map(note => (note.id === currentNote.id ? currentNote : note))
        : [...notes, currentNote]
      setNotes(newNotes)
      setOpenDialog(false)
      setCurrentNote(null)
    }
  }

  const handleChangeColor = (id: number) => {
    const colors = ['#ffffff', '#f28b82', '#fbbc04', '#fff475', '#ccff90', '#a7ffeb', '#cbf0f8', '#aecbfa', '#d7aefb', '#fdcfe8']
    const currentIndex = notes.findIndex(note => note.id === id)
    const currentColorIndex = colors.indexOf(notes[currentIndex].color)
    const newColorIndex = (currentColorIndex + 1) % colors.length
    const newNotes = [...notes]
    newNotes[currentIndex] = { ...newNotes[currentIndex], color: colors[newColorIndex] }
    setNotes(newNotes)
  }

  const filteredNotes = notes.filter(note =>
    note.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
    note.content.toLowerCase().includes(searchTerm.toLowerCase())
  )

  console.log('Notas filtradas:', filteredNotes) // Para depuración

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
                placeholder="Buscar notas"
                inputProps={{ 'aria-label': 'buscar notas' }}
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
                    <Typography variant="h6" component="div">
                      {note.title}
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                      {note.content}
                    </Typography>
                  </CardContent>
                  <CardActions disableSpacing>
                    <IconButton aria-label="editar nota" onClick={() => handleEditNote(note)}>
                      <EditIcon />
                    </IconButton>
                    <IconButton aria-label="cambiar color" onClick={() => handleChangeColor(note.id)}>
                      <ColorLensIcon />
                    </IconButton>
                    <IconButton aria-label="eliminar nota" onClick={() => handleDeleteNote(note.id)}>
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
          <DialogTitle>{currentNote && currentNote.id ? 'Editar Nota' : 'Nueva Nota'}</DialogTitle>
          <DialogContent>
            <TextField
              autoFocus
              margin="dense"
              label="Título"
              type="text"
              fullWidth
              value={currentNote ? currentNote.title : ''}
              onChange={(e) => setCurrentNote(currentNote ? { ...currentNote, title: e.target.value } : null)}
            />
            <TextField
              margin="dense"
              label="Contenido"
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