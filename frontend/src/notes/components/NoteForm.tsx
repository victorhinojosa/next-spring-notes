// src/notes/components/NoteForm.tsx
import React, { useState } from 'react';
import { Dialog, DialogTitle, DialogContent, DialogActions, Button, TextField, CircularProgress} from '@mui/material';

interface NoteFormProps {
    open: boolean;
    onClose: () => void;
    onSave: (note: { content: string }) => void;
    isSaving?: boolean;
}

const NoteForm: React.FC<NoteFormProps> = ({ open, onClose, onSave, isSaving = false }) => {
    const [content, setContent] = useState('');

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        // No cerramos el formulario inmediatamente
        try {
            await onSave({ content });
            setContent('');
            onClose();
        } catch (error) {
            console.error('Error saving note:', error);
            // Aquí podrías mostrar un mensaje de error
        }
    };

    // Limpiar el contenido cuando se cierra el diálogo
    React.useEffect(() => {
        if (!open) {
            setContent('');
        }
    }, [open]);

    return (
        <Dialog
            open={open}
            onClose={onClose}
            maxWidth="sm"
            fullWidth
            sx={{
                '& .MuiDialog-paper': {
                    backgroundColor: '#1e1e1e',
                    color: 'white',
                }
            }}
        >
            <form onSubmit={handleSubmit}>
                <DialogTitle sx={{ color: 'white' }}>Nueva Nota</DialogTitle>
                <DialogContent>
                    <TextField
                        autoFocus
                        multiline
                        rows={4}
                        fullWidth
                        value={content}
                        onChange={(e) => setContent(e.target.value)}
                        placeholder="Escribe tu nota aquí..."
                        variant="outlined"
                        margin="normal"
                        sx={{
                            '& .MuiOutlinedInput-root': {
                                color: 'white',
                                '& fieldset': {
                                    borderColor: 'rgba(255, 255, 255, 0.23)',
                                },
                                '&:hover fieldset': {
                                    borderColor: 'rgba(255, 255, 255, 0.5)',
                                },
                                '&.Mui-focused fieldset': {
                                    borderColor: '#2196f3',
                                },
                            },
                            '& .MuiInputLabel-root': {
                                color: 'rgba(255, 255, 255, 0.7)',
                            },
                        }}
                    />
                </DialogContent>
                <DialogActions>
                    <Button
                        onClick={onClose}
                        disabled={isSaving}
                        sx={{ color: 'rgba(255, 255, 255, 0.7)' }}
                    >
                        Cancelar
                    </Button>
                    <Button
                        type="submit"
                        variant="contained"
                        color="primary"
                        disabled={isSaving || !content.trim()}
                    >
                        {isSaving ? <CircularProgress size={24} /> : 'Guardar'}
                    </Button>
                </DialogActions>
            </form>
        </Dialog>
    );
};

export default NoteForm;