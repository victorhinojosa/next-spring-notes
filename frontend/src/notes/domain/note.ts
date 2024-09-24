export class Note {
    id: string;
    content: string;

    constructor({ id, content }: { id: string; content: string }) {
      this.id = id;
      this.content = content;
    }
  
    asJson(): { id: string; content: string } {
      return {
        id: this.id,
        content: this.content
      };
    }
  
    static fromArray(data: Array<{ id: string; content: string }>): Note[] {
      return data.map(item => new Note(item));
    }
  }