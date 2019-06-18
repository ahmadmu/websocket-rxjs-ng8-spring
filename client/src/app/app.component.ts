import { Component, OnInit } from '@angular/core';
import { webSocket, WebSocketSubject } from 'rxjs/webSocket';
import { Observable } from 'rxjs';

interface Message {
  name: string; message: string; type: string;
}
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  messages: Message[] = [];
  name: string;
  message: string;
  numberOfMessages = 0;

  ws: WebSocketSubject<any>;
  message$: Observable<Message>;
  messageNumber$: Observable<number>;

  connected: boolean;

  constructor() { }

  ngOnInit() {
    this.connect();
  }

  connect() {
    this.ws = webSocket('ws://localhost:8080');

    this.message$ = this.ws.multiplex(
      () => ({subscribe: 'message'}),
      () => ({unsubscribe: 'message'}),
      message => message.type === 'message'
    );

    this.messageNumber$ = this.ws.multiplex(
      () => ({ subscribe: 'msgno' }),
      () => ({ unsubscribe: 'msgno' }),
      message => message.type === 'msgno'
    );

    this.message$.subscribe(
      value => this.messages.push(value),
      error => this.disconnect(error),
      () => this.disconnect()
    )
    this.messageNumber$.subscribe(
      value => this.numberOfMessages = value,
      error => this.disconnect(error),
      () => this.disconnect()
    )

    this.setConnected(true);
  }

  disconnect(err?) {
    if (err) { console.error(err); }
    this.setConnected(false);
    console.log('Disconnected');
  }

  sendMessage() {
    this.ws.next({ name: this.name, message: this.message, type: 'message' });
    this.message = '';
  }

  setConnected(connected) {
    this.connected = connected;
    this.messages = [];
  }
}
