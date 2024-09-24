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
    <Card sx={{ minHeight: 200, display: 'flex', flexDirection: 'column', justifyContent: 'space-between' }}>
      <CardContent>
        <Typography variant="body2">{content}</Typography>
      </CardContent>
      <CardActions disableSpacing>
        <IconButton aria-label="edit" onClick={onEdit}>
          <EditIcon />
        </IconButton>
        <IconButton aria-label="delete" onClick={onDelete}>
          <DeleteIcon />
        </IconButton>
      </CardActions>
    </Card>
  );
};

export default NoteCard;