import React, { useState, useEffect } from 'react';
import { Container, Grid, Paper, TextField, Button, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from '@mui/material';
import axios from 'axios';

function App() {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    description: ''
  });
  const [file, setFile] = useState(null);
  const [redisData, setRedisData] = useState([]);
  const [allData, setAllData] = useState([]);

  const handleInputChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post('/api/data', formData);
      fetchData();
      setFormData({ name: '', email: '', description: '' });
    } catch (error) {
      console.error('Error submitting data:', error);
    }
  };

  const handleFileUpload = async () => {
    if (!file) {
      alert('Please select a file first!');
      return;
    }
    const formData = new FormData();
    formData.append('file', file);
    try {
      const response = await axios.post('http://localhost:8082/api/upload', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });
      alert('File uploaded successfully: ' + response.data);
      setFile(null);
      // Clear the file input
      document.querySelector('input[type="file"]').value = '';
    } catch (error) {
      console.error('Error uploading file:', error);
      alert('Error uploading file: ' + (error.response?.data || error.message));
    }
  };

  const fetchData = async () => {
    try {
      const [redisResponse, allDataResponse] = await Promise.all([
        axios.get('/api/data/recent'),
        axios.get('/api/data/all')
      ]);
      setRedisData(redisResponse.data);
      setAllData(allDataResponse.data);
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <Container maxWidth="lg" sx={{ mt: 4 }}>
      <Grid container spacing={3}>
        {/* Input Form */}
        <Grid item xs={6}>
          <Paper sx={{ p: 2 }}>
            <form onSubmit={handleSubmit}>
              <TextField
                fullWidth
                label="Name"
                name="name"
                value={formData.name}
                onChange={handleInputChange}
                margin="normal"
              />
              <TextField
                fullWidth
                label="Email"
                name="email"
                value={formData.email}
                onChange={handleInputChange}
                margin="normal"
              />
              <TextField
                fullWidth
                label="Description"
                name="description"
                value={formData.description}
                onChange={handleInputChange}
                margin="normal"
                multiline
                rows={4}
              />
              <Button type="submit" variant="contained" color="primary" sx={{ mt: 2 }}>
                Submit
              </Button>
            </form>
          </Paper>
        </Grid>

        {/* File Upload */}
        <Grid item xs={6}>
          <Paper sx={{ p: 2 }}>
            <input
              type="file"
              onChange={handleFileChange}
              style={{ marginBottom: '1rem' }}
            />
            <Button
              variant="contained"
              color="secondary"
              onClick={handleFileUpload}
              disabled={!file}
            >
              Upload File
            </Button>
          </Paper>
        </Grid>

        {/* Redis Data Table */}
        <Grid item xs={6}>
          <Paper sx={{ p: 2 }}>
            <h3>Recent Entries (Redis)</h3>
            <TableContainer>
              <Table>
                <TableHead>
                  <TableRow>
                    <TableCell>Name</TableCell>
                    <TableCell>Email</TableCell>
                    <TableCell>Description</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {redisData.map((item, index) => (
                    <TableRow key={index}>
                      <TableCell>{item.name}</TableCell>
                      <TableCell>{item.email}</TableCell>
                      <TableCell>{item.description}</TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </TableContainer>
          </Paper>
        </Grid>

        {/* PostgreSQL Data Table */}
        <Grid item xs={6}>
          <Paper sx={{ p: 2 }}>
            <h3>All Entries (PostgreSQL)</h3>
            <TableContainer>
              <Table>
                <TableHead>
                  <TableRow>
                    <TableCell>Name</TableCell>
                    <TableCell>Email</TableCell>
                    <TableCell>Description</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {allData.map((item) => (
                    <TableRow key={item.id}>
                      <TableCell>{item.name}</TableCell>
                      <TableCell>{item.email}</TableCell>
                      <TableCell>{item.description}</TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </TableContainer>
          </Paper>
        </Grid>
      </Grid>
    </Container>
  );
}

export default App;