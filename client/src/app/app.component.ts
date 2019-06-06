import { Component } from '@angular/core';
import { webSocket, WebSocketSubject } from 'rxjs/webSocket';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'app';

  greetings: string[] = [];
  showConversation = false;
  ws: WebSocketSubject<any>;
  name: string;
  disabled: boolean;

  constructor() { }

  connect() {
    this.ws = webSocket('ws://localhost:8080/name');
    this.ws.subscribe(
      value => console.log(value),
      error => console.error(error),
      () => this.setConnected(false)
    );
    this.setConnected(true);
  }

  disconnect() {
    this.ws.complete();
    this.setConnected(false);
    console.log('Disconnected');
  }

  sendName() {
    this.ws.next({ name: this.name});
    this.showGreeting(this.name);
  }

  showGreeting(message) {
    this.showConversation = true;
    this.greetings.push(message);
  }

  setConnected(connected) {
    this.disabled = connected;
    this.showConversation = connected;
    this.greetings = [];
  }
}
