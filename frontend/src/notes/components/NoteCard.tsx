import React from 'react';
import { Card, CardContent, Typography} from '@mui/material';

interface NoteCardProps {
  content: string;
  onClick: () => void;
}

const NoteCard: React.FC<NoteCardProps> = ({ content, onClick }) => {
    return (
        <Card
            onClick={onClick}
            sx={{
                minHeight: 200,
                display: 'flex',
                flexDirection: 'column',
                justifyContent: 'space-between',
                position: 'relative',
                overflow: 'visible',
                cursor: 'pointer',
                transition: 'transform 0.2s, box-shadow 0.2s',
                '&:hover': {
                    transform: 'translateY(-4px)',
                    boxShadow: '0 8px 12px rgba(0, 0, 0, 0.3)',
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
        </Card>
    );
};

export default NoteCard;