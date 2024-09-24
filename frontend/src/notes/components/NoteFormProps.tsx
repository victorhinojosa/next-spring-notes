import React, { useState, useEffect } from 'react';
import { Dialog, DialogTitle, DialogContent, DialogActions, TextField, Button } from '@mui/material';
import { Note } from '../domain/note';

interface NoteFormProps {
  open: boolean;
  onClose: () => void;
  onSave: (note: Note) => void;
  initialNote?: Note;
}

const NoteForm: React.FC<NoteFormProps> = ({ open, onClose, onSave, initialNote }) => {
  const [content, setContent] = useState('');

  useEffect(() => {
    if (initialNote) {
      setContent(initialNote.content);
    } else {
      setContent('');
    }
  }, [initialNote]);

  const handleSave = () => {
    const noteToSave = initialNote
      ? new Note({ ...initialNote, content })
      : new Note({ id: crypto.randomUUID(), content });
    onSave(noteToSave);
    onClose();
  };

  return (
    <Dialog open={open} onClose={onClose} fullWidth maxWidth="sm">
      <DialogTitle>{initialNote ? 'Edit Note' : 'Create New Note'}</DialogTitle>
      <DialogContent>
        <TextField
          autoFocus
          margin="dense"
          label="Note Content"
          type="text"
          fullWidth
          multiline
          rows={4}
          value={content}
          onChange={(e) => setContent(e.target.value)}
        />
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose}>Cancel</Button>
        <Button onClick={handleSave} variant="contained" color="primary">
          Save
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default NoteForm;