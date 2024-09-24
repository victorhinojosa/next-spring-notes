export class NoteApiClient {
  
    static async get(url: string): Promise<any> {
      const response = await fetch(url);
      if (!response.ok) {
        throw new Error('API request failed');
      }
      return response.json();
    }
  
    static async put(url: string, data: any): Promise<any> {
      const response = await fetch(url, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
      });
      if (!response.ok) {
        throw new Error('API request failed');
      }
      return response.json();
    }
  
    static async patch(url: string, data: any): Promise<any> {
      const response = await fetch(url, {
        method: 'PATCH',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
      });
      if (!response.ok) {
        throw new Error('API request failed');
      }
      return response.json();
    }
  }