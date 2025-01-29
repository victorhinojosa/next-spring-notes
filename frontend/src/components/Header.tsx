import React from 'react';
import { AppBar, Toolbar, Typography, InputBase, Box } from '@mui/material';
import { Search as SearchIcon } from '@mui/icons-material';

interface HeaderProps {
  onSearch: (searchTerm: string) => void;
}

const Header: React.FC<HeaderProps> = ({ onSearch }) => {
  return (
    <AppBar position="static" color="default" elevation={0}>
      <Toolbar>
        <Typography variant="h6" noWrap sx={{ flexGrow: 1 }}>
          Mindkeep
        </Typography>
        <Box sx={{ display: 'flex', alignItems: 'center', bgcolor: 'background.paper', borderRadius: 1, px: 1 }}>
          <SearchIcon sx={{ mr: 1 }} />
          <InputBase
            placeholder="Search notes"
            onChange={(e) => onSearch(e.target.value)}
          />
        </Box>
      </Toolbar>
    </AppBar>
  );
};

export default Header;