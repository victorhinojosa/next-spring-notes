import React, { useEffect, useState } from 'react';
import { Dialog, DialogContent, DialogActions, Button, TextField} from '@mui/material';

interface NoteDialogProps {
    open: boolean;
    onClose: () => void;
    onSave: (note: { content: string }) => void;
    initialContent?: string;
    isNewNote?: boolean;
    isSaving?: boolean;
}

const NoteDialog: React.FC<NoteDialogProps> = ({ open, onClose, onSave, initialContent= '', isNewNote = false, isSaving = false }) => {
    const [content, setContent] = useState('');
    const [hasChanges, setHasChanges] = useState(false);

    useEffect(() => {
        if (open) {
            setContent(initialContent);
            setHasChanges(false);
        }
    }, [open, initialContent]);

    const handleSave = async () => {
        if (content.trim()) {
            try {
                await onSave({ content });
                setContent('');
                setHasChanges(false);
                onClose();
            } catch (error) {
                console.error('Error saving note:', error);
            }
        } else {
            onClose();
        }
    };

    const handleChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
        setContent(e.target.value);
        setHasChanges(true);
    };

    const handleKeyPress = (e: React.KeyboardEvent) => {
        if (e.key === 'Enter' && !e.shiftKey && isNewNote) {
            e.preventDefault();
            handleSave();
        }
    };

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
            <DialogContent>
                <TextField
                    autoFocus
                    multiline
                    rows={4}
                    fullWidth
                    value={content}
                    onChange={handleChange}
                    onKeyPress={handleKeyPress}
                    placeholder="Take a note..."
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
                    }}
                />
            </DialogContent>
            <DialogActions>
                <Button
                    onClick={handleSave}
                    disabled={isSaving || (!hasChanges && isNewNote)}
                    sx={{ color: 'rgba(255, 255, 255, 0.7)' }}
                >
                    {isSaving ? 'Saving...' : (
                        isNewNote ? (
                            hasChanges && content.trim() ? 'Save' : 'Cancel'
                        ) : (
                            'Close'
                        )
                    )}
                </Button>
            </DialogActions>
        </Dialog>
    );
};

export default NoteDialog;