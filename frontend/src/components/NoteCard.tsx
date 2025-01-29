import React from 'react';
import { Card, CardContent, CardActions, Typography, IconButton } from '@mui/material';
import { Edit as EditIcon, Delete as DeleteIcon, ColorLens as ColorLensIcon } from '@mui/icons-material';

interface NoteCardProps {
  content: string;
  onEdit: () => void;
  onDelete: () => void;
}

const NoteCard: React.FC<NoteCardProps> = ({ content, onEdit, onDelete }) => {
    return (
        <Card
            sx={{
                minHeight: 200,
                display: 'flex',
                flexDirection: 'column',
                justifyContent: 'space-between',
                position: 'relative',
                overflow: 'visible',
                '&:hover .actions': {
                    opacity: 1,
                },
            }}
        >
            <CardContent sx={{ p: 3 }}>
                <Typography
                    variant="body1"
                    sx={{
                        fontSize: '1rem',
                        lineHeight: 1.6,
                        color: 'text.primary',
                    }}
                >
                    {content}
                </Typography>
            </CardContent>
            <CardActions
                className="actions"
                sx={{
                    position: 'absolute',
                    right: 8,
                    top: 8,
                    opacity: 0,
                    transition: 'opacity 0.2s',
                    backgroundColor: 'rgba(30, 30, 30, 0.9)',
                    borderRadius: '20px',
                    padding: '4px',
                }}
            >
                <IconButton
                    size="small"
                    onClick={onEdit}
                    sx={{
                        color: 'rgba(255, 255, 255, 0.7)',
                        '&:hover': {
                            backgroundColor: 'rgba(255, 255, 255, 0.1)',
                            color: 'rgba(255, 255, 255, 0.9)'
                        }
                    }}
                >
                    <EditIcon fontSize="small" />
                </IconButton>
                <IconButton
                    size="small"
                    onClick={onDelete}
                    sx={{
                        color: 'rgba(255, 255, 255, 0.7)',
                        '&:hover': {
                            backgroundColor: 'rgba(255, 255, 255, 0.1)',
                            color: 'rgba(255, 255, 255, 0.9)'
                        }
                    }}
                >
                    <DeleteIcon fontSize="small" />
                </IconButton>
            </CardActions>
        </Card>
    );
};

export default NoteCard;